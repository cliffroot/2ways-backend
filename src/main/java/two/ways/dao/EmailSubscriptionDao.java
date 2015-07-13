package two.ways.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import two.ways.model.EmailSubscription;

@Service
public class EmailSubscriptionDao {

	@Autowired
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void save(EmailSubscription es) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(es);
		tx.commit();
		session.flush();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public List<EmailSubscription> list() {
		Session session = sessionFactory.openSession();
		List<EmailSubscription> emailSubscriptions = session.createQuery("from EmailSubscription").list();
		session.flush();
		session.close();

		return emailSubscriptions;
	}

	public void delete (String socialId) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.createQuery("delete from EmailSubscription e where e.userSocialId = :userId").setParameter("userId", socialId).executeUpdate();
		tx.commit();
		session.flush();
		session.close();
	}


}
