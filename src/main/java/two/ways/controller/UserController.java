package two.ways.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import two.ways.dao.UserDao;
import two.ways.model.Project;
import two.ways.model.User;

@RestController
public class UserController {

	@Autowired
	UserDao userDao;

	@RequestMapping("/users")
	public List<User> greeting() {
		return userDao.list();
	}

	@RequestMapping("/users/{socialId}/projects/")
	public List<Project> userProjects (@PathVariable String socialId) {
		return userDao.getProjectByUsedId(socialId);
	}

}
