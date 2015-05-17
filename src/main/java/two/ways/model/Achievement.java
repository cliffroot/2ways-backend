package two.ways.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="achievement")
public class Achievement {

	String name;
	String date;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	User user;

	@Id
	String id;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Achievement(String name, String date) {
		this.name = name;
		this.date = date;
	}

	public Achievement() { }

}
