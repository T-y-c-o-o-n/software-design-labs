package exchange.client_cabinet.model;

import java.util.List;

public class Client {
    public int clientId;
    public String clientLogin;
    public double money;
    public List<Stock> clientStocks;

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientLogin() {
        return clientLogin;
    }

    public void setClientLogin(String clientLogin) {
        this.clientLogin = clientLogin;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public List<Stock> getClientStocks() {
        return clientStocks;
    }

    public void setClientStocks(List<Stock> clientStocks) {
        this.clientStocks = clientStocks;
    }
}
