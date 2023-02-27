package fitness.manager_admin.service;

import fitness.manager_admin.dao.ClientDao;
import fitness.manager_admin.model.Client;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
public class ClientService {

    private ClientDao clientDao;

    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public List<Client> listClients() {
        return clientDao.listClients();
    }

    public void addClient(String name) {
        clientDao.addClient(name);
    }
}
