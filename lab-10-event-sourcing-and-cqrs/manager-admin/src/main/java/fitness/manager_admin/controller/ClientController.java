package fitness.manager_admin.controller;

import fitness.manager_admin.dao.ClientDao;
import fitness.manager_admin.model.Client;
import fitness.manager_admin.service.ClientService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("")
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
//
//    private final ClientDao clientDao;
//
//    public ClientController(ClientDao clientDao) {
//        this.clientDao = clientDao;
//    }

    @RequestMapping(path = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    List<Client> listClients() {
        return clientService.listClients();
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    void addClient(@RequestParam String name) {
        clientService.addClient(name);
    }
}
