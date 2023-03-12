package exchange.client_cabinet.model;

public class Stock {
    public String clientName;
    public String companyName;
    public int clientStockCount;
    public double stockPrice;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getClientStockCount() {
        return clientStockCount;
    }

    public void setClientStockCount(int clientStockCount) {
        this.clientStockCount = clientStockCount;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }
}
