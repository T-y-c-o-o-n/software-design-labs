package fitness.manager_admin.controller;

import fitness.manager_admin.dao.ClientDao;
import fitness.manager_admin.dao.SubscriptionDao;
import fitness.manager_admin.model.Client;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/v1/manager-admin/clients")
public class ClientController {

    private final ClientDao clientDao;

    private final SubscriptionDao subscriptionDao;

    public ClientController(ClientDao clientDao, SubscriptionDao subscriptionDao) {
        this.clientDao = clientDao;
        this.subscriptionDao = subscriptionDao;
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    List<Client> listClients() {
        return clientDao.listClients();
    }

    @RequestMapping(path = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    Client getClient(@RequestParam int clientId) {
        Optional<Client> client = clientDao.getClient(clientId);
        client.ifPresent(c -> c.setSubscriptions(
            subscriptionDao.listSubscriptionsByClientId(clientId)
        ));
        return client.orElseThrow();
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    void addClient(@RequestParam String name) {
        clientDao.addClient(name);
    }
}
