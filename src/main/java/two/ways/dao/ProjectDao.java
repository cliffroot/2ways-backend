package two.ways.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import two.ways.model.Project;

@Service
public class ProjectDao {

	@Autowired
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void save(Project p) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(p);
		tx.commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public List<Project> list() {
		Session session = sessionFactory.openSession();
		List<Project> project = session.createQuery("from Project").list();
		session.close();


		return project;
	}

}