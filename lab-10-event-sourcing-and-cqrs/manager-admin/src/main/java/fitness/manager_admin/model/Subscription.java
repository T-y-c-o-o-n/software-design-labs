package fitness.manager_admin.model;

import java.sql.Date;

public class Subscription {
    private int subscriptionId;
    private Date until;

    public Subscription() {
    }

    public Subscription(int subscriptionId, Date until) {
        this.subscriptionId = subscriptionId;
        this.until = until;
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Date getUntil() {
        return until;
    }

    public void setUntil(Date until) {
        this.until = until;
    }
}
