package market;

import io.reactivex.netty.protocol.http.server.HttpServer;
import market.model.Product;
import market.model.User;
import market.service.MarketService;
import rx.Observable;

public class Server {

    private static final MarketService service = new MarketService();

    public static void main(String[] args) {
        HttpServer
            .newServer(8080)
            .start((req, resp) -> {
                try {
                    String path = req.getDecodedPath();
                    if ("/get-all-users".equals(path)) {
                        Observable<String> response = Observable.concat(
                            Observable.just("Users:\n"),
                            service.getAllUsers().map(User::toString)
                        );
                        return resp.writeString(response);
                    }
                    if ("/add-user".equals(path)) {
                        Observable<String> response = Observable.concat(
                            Observable.just("Add result:\n"),
                            service.addUser(req)
                        );
                        return resp.writeString(response);
                    }
                    if ("/get-products-for-user".equals(path)) {
                        service.getProductsForUser(req).map(Product::toString).forEach(System.out::println);
                        Observable<String> response = Observable.concat(
                            Observable.just("Products:\n"),
                            service.getProductsForUser(req).map(Product::toString)
                        );
                        return resp.writeString(response);
                    }
                    if ("/add-product".equals(path)) {
                        Observable<String> response = Observable.concat(
                            Observable.just("Add product result:\n"),
                            service.addProduct(req)
                        );
                        return resp.writeString(response);
                    }
                    return resp.writeString(
                        Observable.just("incorrect request")
                    );
                } catch (RuntimeException e) {
                    System.err.println(e.getMessage());
                    return resp.writeString(Observable.just(e.getMessage()));
                }
            })
            .awaitShutdown();
    }
}
