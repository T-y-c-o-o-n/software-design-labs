package fitness.turnstile.controller;

import fitness.turnstile.dao.TurnstileDao;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/turnstile")
public class TurnstileController {
    private final TurnstileDao turnstileDao;

    public TurnstileController(TurnstileDao turnstileDao) {
        this.turnstileDao = turnstileDao;
    }

    @RequestMapping(path = "/in", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    boolean in(@RequestParam int subscriptionId) {
        return turnstileDao.in(subscriptionId);
    }

    @RequestMapping(path = "/out", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    boolean out(@RequestParam int subscriptionId) {
        return turnstileDao.out(subscriptionId);
    }
}
