package two.ways.controller;

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

@RestController
public class UserController {

	@Autowired
	UserDao userDao;

	@RequestMapping(value = "/users/",  method = RequestMethod.GET)
	public List<User> greeting() {
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
