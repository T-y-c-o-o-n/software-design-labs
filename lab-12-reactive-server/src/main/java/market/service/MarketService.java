package market.service;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import market.db.MarketDB;
import market.exception.ParseUserException;
import market.logic.Converter;
import market.model.Product;
import market.model.User;
import market.model.Wallet;
import rx.Observable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MarketService {
    private final MarketDB db = new MarketDB();

    public Observable<User> getAllUsers() {
        return db.getAllUsers();
    }

    public Observable<String> addUser(HttpServerRequest<ByteBuf> req) {
        Map<String, List<String>> param = req.getQueryParameters();
        try {
            User user = parseUser(param);
            db.addUser(user);
            return Observable.just("User " + user.name + " added");
        } catch (ParseUserException e) {
            return Observable.just(e.getMessage());
        }
    }

    public Observable<Product> getProductsForUser(HttpServerRequest<ByteBuf> req) {
        try {
            String login = getParam("login", req.getQueryParameters());
            Wallet userWallet = db.getWalletByUserLogin(login);
            Observable<Product> allProducts = db.getAllProducts();
            return allProducts.map(p -> new Product(
                p.id,
                p.name,
                Converter.convertTo(p.wallet, p.value, userWallet),
                userWallet
            ));
        } catch (ParseUserException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Observable<String> addProduct(HttpServerRequest<ByteBuf> req) {
        Map<String, List<String>> param = req.getQueryParameters();
        try {
            Product product = parseProduct(param);
            db.addProduct(product);
            return Observable.just("Product " + product.name + " added");
        } catch (ParseUserException e) {
            return Observable.just(e.getMessage());
        }
    }

    private User parseUser(Map<String, List<String>> param) throws ParseUserException {
        int id;
        try {
            id = Integer.parseInt(
                getParam("id", param)
            );
        } catch (NumberFormatException e) {
            throw new ParseUserException("id is not int");
        }
        String name = getParam("name", param);
        String login = getParam("login", param);
        String walletParam = "";
        Wallet wallet;
        try {
            walletParam = getParam("wallet", param);
            wallet = Wallet.valueOf(
                walletParam
            );
        } catch (IllegalArgumentException e) {
            throw new ParseUserException("no such wallet " + walletParam + ". Allowed: " + Arrays.toString(Wallet.values()));
        }
        return new User(
            id,
            name,
            login,
            wallet
        );

    }

    private Product parseProduct(Map<String, List<String>> param) throws ParseUserException {
        int id;
        try {
            id = Integer.parseInt(
                getParam("id", param)
            );
        } catch (NumberFormatException e) {
            throw new ParseUserException("id is not int");
        }
        String name = getParam("name", param);
        double value;
        try {
            value = Double.parseDouble(
                getParam("value", param)
            );
        } catch (NumberFormatException e) {
            throw new ParseUserException("value is not int");
        }
        String walletParam = "";
        Wallet wallet;
        try {
            walletParam = getParam("wallet", param);
            wallet = Wallet.valueOf(
                walletParam
            );
        } catch (IllegalArgumentException e) {
            throw new ParseUserException("no such wallet " + walletParam + ". Allowed: " + Arrays.toString(Wallet.values()));
        }
        return new Product(
            id,
            name,
            value,
            wallet
        );

    }

    private String getParam(String paramName, Map<String, List<String>> param) throws ParseUserException {
        try {
            return param.get(paramName).get(0);
        } catch (NullPointerException e) {
            throw new ParseUserException("no " + paramName + " param");
        }
    }
}
