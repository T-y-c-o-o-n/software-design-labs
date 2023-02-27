package fitness.manager_admin.model;

import org.springframework.lang.Nullable;

import java.util.List;

public class Client {
    private int id;
    private String name;
    @Nullable
    private List<Subscription> subscriptions;

    public Client() {
    }

    public Client(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(@Nullable List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
