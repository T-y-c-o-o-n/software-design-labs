package fitness.manager_admin.controller;

import fitness.manager_admin.dao.SubscriptionDao;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/manager-admin/subscriptions")
public class SubscriptionController {

    private final SubscriptionDao subscriptionDao;

    public SubscriptionController(SubscriptionDao subscriptionDao) {
        this.subscriptionDao = subscriptionDao;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void addSubscription(@RequestParam int clientId) {
        subscriptionDao.addSubscription(clientId);
    }
}
