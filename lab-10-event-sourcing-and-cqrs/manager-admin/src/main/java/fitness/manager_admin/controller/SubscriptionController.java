package fitness.manager_admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("/api/v1/manager-admin")
public class SubscriptionController {
    @RequestMapping(value = "/get-subscription", method = RequestMethod.GET)
    public String getSubscription(@RequestParam int subscriptionId) {
        return "subscription";
    }
}
