package two.ways.controller;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import two.ways.dao.UserDao;
import two.ways.model.Project;
import two.ways.model.User;
import two.ways.service.MailMail;

@RestController
public class UserController {

	@Autowired
	UserDao userDao;

	@Autowired
	MailMail mailMail;

	@RequestMapping(value = "/users/",  method = RequestMethod.GET)
	public List<User> greeting() {

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				mailMail.sendMail("paradise.octopus@gmail.com", "danilenko93@gmail.com",
						"Registration succesful", "Dear " + "Yehor"
								+ ", you have succesfully registered!");
			}
		});

		thread.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				e.printStackTrace();
			}
		});
		thread.start();

		return userDao.list();
	}

	@RequestMapping(value = "/users/{socialId}/projects", method = RequestMethod.GET)
	public List<Project> userProjects (@PathVariable String socialId) {
		return userDao.getProjectByUsedId(socialId);
	}

	@RequestMapping(value = "/users/{socialId}", method = RequestMethod.GET) 
	public User getUserById (@PathVariable String socialId) {
		return userDao.getUserById(socialId);
	}

	@RequestMapping(value = "/users/", method = RequestMethod.POST)
	public void createUser (@RequestBody User user) {
		userDao.save(user);
	}

}
