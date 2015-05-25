package two.ways.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

	private static final String IMAGE_DIR = "image_dir/";

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
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(IMAGE_DIR + name)));
				stream.write(bytes);
				stream.close();

				Project project = projectDao.getProjectById(projectId);
				if (project.getPhotos() == null) {
					project.setPhotos(new LinkedList<String>());
				}

				project.getPhotos().add("http://192.168.0.106:8080/projects/photo/" + name);
				projectDao.update(project);

				return "You successfully uploaded " + name + "!";
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name + " because the file was empty.";
		}
	}

	@RequestMapping(value = "/projects/photo/{photoName}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<InputStreamResource> downloadUserAvatarImage(@PathVariable String photoName) throws FileNotFoundException {
		System.out.println(new File(IMAGE_DIR).listFiles());
		File file = new File(IMAGE_DIR + photoName);

		return ResponseEntity.ok()
				.contentLength(file.length()).contentType(MediaType.IMAGE_PNG)
				.body(new InputStreamResource(new FileInputStream(file)));
	}

}
