package two.ways.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import two.ways.model.Project;
import two.ways.model.User;

public class UserDao {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void save(User u) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(u);
		tx.commit();
		session.close();
	}

	public void update(User u) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(u);
		tx.commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public List<User> list() {
		Session session = sessionFactory.openSession();
		List<User> users = session.createQuery("from User").list();
		session.close();

		return users;
	}

	public List<Project> getProjectByUsedId (String userId) { 
		Session session = sessionFactory.openSession();
		List<Project> projects = session.createQuery("from Project as P where P.owner = '" + userId + "'").list();
		session.close();

		return projects;
	}

	public User getUserById(String id) {
		Session session = sessionFactory.openSession();
		User user = (User) session.createQuery("from User as U where U.socialId = '" + id + "'").uniqueResult();
		session.close();

		return user;
	}

}