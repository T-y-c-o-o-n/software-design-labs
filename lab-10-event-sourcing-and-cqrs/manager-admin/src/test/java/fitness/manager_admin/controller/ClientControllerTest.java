package fitness.manager_admin.controller;

import fitness.manager_admin.dao.ClientDao;
import fitness.manager_admin.dao.SubscriptionDao;
import fitness.manager_admin.model.Client;
import fitness.manager_admin.model.Subscription;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClientControllerTest {

    @Mock
    private ClientDao clientDao;

    @Mock
    private SubscriptionDao subscriptionDao;

    private ClientController clientController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        clientController = new ClientController(clientDao, subscriptionDao);
    }

    @Test
    public void emptyClientsTest() {
        List<Client> expectedClients = List.of();
        when(clientDao.listClients())
            .thenReturn(expectedClients);
        List<Client> actualClients = clientController.listClients();
        assertEquals(expectedClients, actualClients);
    }

    @Test
    public void addClientTest() {
        String clientName = "TestName";
        clientController.addClient(clientName);
        verify(clientDao).addClient(clientName);
    }

    @Test
    public void getClientTest() {
        int clientId = 1;
        String clientName = "TestName";
        List<Subscription> subscriptions = List.of(new Subscription());
        Client expectedClientWithSubscriptions = new Client();
        expectedClientWithSubscriptions.setClientId(clientId);
        expectedClientWithSubscriptions.setName(clientName);
        expectedClientWithSubscriptions.setSubscriptions(subscriptions);

        Client expectedClient = new Client();
        expectedClient.setClientId(clientId);
        expectedClient.setName(clientName);

        when(clientDao.getClient(clientId))
            .thenReturn(Optional.of(expectedClient));

        when(subscriptionDao.listSubscriptionsByClientId(clientId))
            .thenReturn(subscriptions);

        Client actualClient = clientController.getClient(clientId);
        assertEquals(expectedClientWithSubscriptions, actualClient);
    }
}
