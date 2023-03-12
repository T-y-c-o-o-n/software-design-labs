package exchange.model;

public class Company {
    public int companyId;
    public String companyName;
    public double stockPrice;
    public int companyStockCount;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public int getCompanyStockCount() {
        return companyStockCount;
    }

    public void setCompanyStockCount(int companyStockCount) {
        this.companyStockCount = companyStockCount;
    }
}
