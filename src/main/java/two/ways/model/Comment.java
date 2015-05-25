package two.ways.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "comment")
public class Comment {

	String body;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="person_id")
	User person;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="project_id")
	@JsonIgnore
	Project project;

	@JsonDeserialize(using=Project.DateDeserializer.class)
	@JsonSerialize(using=Project.DateSerializer.class)
	Date date;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public User getPerson() {
		return person;
	}

	public void setPerson(User person) {
		this.person = person;
	}



}
