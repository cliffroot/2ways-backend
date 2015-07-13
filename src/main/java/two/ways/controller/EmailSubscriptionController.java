package two.ways.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import two.ways.dao.EmailSubscriptionDao;
import two.ways.model.EmailSubscription;

@RestController
public class EmailSubscriptionController {

	@Autowired
	EmailSubscriptionDao emailSubDao;


	@RequestMapping(value = "/addEmailSubscription/{socialId}/{frequency}", method = RequestMethod.POST)
	void addSubscription (@PathVariable String socialId, @PathVariable int frequency) {
		EmailSubscription emailSubscription = new EmailSubscription(socialId, EmailSubscription.Frequency.fromInt(frequency));
		emailSubDao.save(emailSubscription);
	}

	@RequestMapping(value = "/removeEmailSubscription/{socialId}/{frequency}", method = RequestMethod.POST)
	void removeSubscription (@PathVariable String socialId) {
		emailSubDao.delete(socialId);
	}

}
