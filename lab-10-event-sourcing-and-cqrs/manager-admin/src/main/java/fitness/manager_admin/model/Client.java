package fitness.manager_admin.model;

import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Objects;

public class Client {
    private int clientId;
    private String name;
    @Nullable
    private List<Subscription> subscriptions;

    public Client() {
    }

    public Client(int id, String name) {
        this.clientId = id;
        this.name = name;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return clientId == client.clientId && Objects.equals(name, client.name) && Objects.equals(subscriptions, client.subscriptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, name, subscriptions);
    }
}
