package exchange.client_cabinet.controller;

import exchange.client_cabinet.dao.ClientDao;
import exchange.client_cabinet.model.Client;
import exchange.client_cabinet.service.ExchangeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController {

    public final ClientDao clientDao;

    public final ExchangeService exchangeService;

    public ClientController(ClientDao clientDao, ExchangeService exchangeService) {
        this.clientDao = clientDao;
        this.exchangeService = exchangeService;
    }

    @RequestMapping("/add-client")
    public boolean addClient(
        @RequestParam String clientLogin,
        @RequestParam double money
    ) {
        Client client = new Client();
        client.clientLogin = clientLogin;
        client.money = money;
        return clientDao.addClient(client);
    }

    @RequestMapping("/get-client")
    public Client getClient(
        @RequestParam int clientId
    ) {
        Client client = clientDao.getClient(clientId);
        client.clientStocks = clientDao.getClientStocks(client.clientId);
        return client;
    }

    @RequestMapping("/get-clients")
    public List<Client> getClients(
    ) {
        return clientDao.getClients();
    }

    @RequestMapping("/add-money")
    public boolean addMoney(
        @RequestParam int clientId,
        @RequestParam double amount
    ) {
        return clientDao.addMoney(clientId, amount);
    }

    @RequestMapping("/get-client-stocks-value")
    public double getClientStocksValue(
        @RequestParam int clientId
    ) {
        return clientDao.getClientStocksValue(clientId).stockSum;
    }

    @RequestMapping("/buy-stocks")
    public boolean buyStocks(
        @RequestParam int clientId,
        @RequestParam int companyId,
        @RequestParam int count
    ) {
        return exchangeService.buyStocks(companyId, count)
            && clientDao.addClientStock(clientId, companyId, count);
    }
}
