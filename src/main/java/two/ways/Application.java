package two.ways;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import two.ways.dao.ProjectDao;
import two.ways.dao.UserDao;
import two.ways.model.Comment;
import two.ways.model.Project;
import two.ways.model.User;

@SpringBootApplication
@ImportResource("classpath:spring.xml")
public class Application {

	public static void main(String[] args) throws ClassNotFoundException {

		Class.forName("org.sqlite.JDBC");

		SpringApplication.run(Application.class, args);

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		UserDao userDao = context.getBean(UserDao.class);
		User user = new User("John Doe", "doe@gmail.com", "some_photo", "DUMMY_ID");
		Project project = new Project("Get some food", new Date(), "Plz for kittens", user,
				new LinkedList<String> (), new LinkedList<Comment> (), "500 people", new LinkedList<User> (), "45.0;45.0");

		List<Project> projects=  new LinkedList<Project> ();
		projects.add(project);
		user.setProjects(projects);
		userDao.save(user);
		ProjectDao personDAO = context.getBean(ProjectDao.class);
		personDAO.save(project);
		context.close();


	}
}