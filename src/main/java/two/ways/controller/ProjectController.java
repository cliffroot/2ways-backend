package two.ways.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import two.ways.dao.ProjectDao;
import two.ways.dao.UserDao;
import two.ways.model.Project;
import two.ways.model.User;

@RestController
public class ProjectController {

	@Autowired
	ProjectDao projectDao;

	@Autowired
	UserDao userDao;

	@RequestMapping(value = "/projects", method = RequestMethod.GET)
	public List<Project> greeting() {
		return projectDao.list();
	}

	@RequestMapping(value = "/projects", method = RequestMethod.POST)
	public Integer createProject (@RequestBody Project project) {
		project.setUsersSubscribed(new LinkedList<User>());
		project.getUsersSubscribed().add(project.getOwner());
		projectDao.save(project);
		return project.getId();

	}

	@RequestMapping(value = "/projects/{projectId}/subscribe/{userId}", method = RequestMethod.POST)
	public void subscribeUser (@PathVariable String projectId, @PathVariable String userId) {
		Project project = projectDao.getProjectById(projectId);
		User user = userDao.getUserById(userId);

		List<User> subscribedUsers = project.getUsersSubscribed();

		if (subscribedUsers == null) {
			throw new IllegalStateException ("bullshit");
		}

		project.getUsersSubscribed().add(user);
		projectDao.update(project);
	}

	@RequestMapping(value = "/projects/{projectId}/subscribedUsers")
	public List<User> getSubscribedUsers (@PathVariable String projectId) {
		Project project = projectDao.getProjectById(projectId);

		return project.getUsersSubscribed();

	}

	@RequestMapping(value="/projects/{projectId}/addPhoto", method=RequestMethod.POST)
	public @ResponseBody String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("name") String name, @PathVariable String projectId) {
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(name)));
				stream.write(bytes);
				stream.close();
				return "You successfully uploaded " + name + "!";
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name + " because the file was empty.";
		}
	}

}
