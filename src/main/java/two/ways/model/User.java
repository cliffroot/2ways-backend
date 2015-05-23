package two.ways.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="user")
public class User{

	@Id
	@Column(name = "socialId")
	String socialId;

	String photo;
	String name;
	String email;
	String coverPhotoUrl;

	@OneToMany(targetEntity=Project.class, mappedBy="owner", fetch=FetchType.EAGER)
	@JsonIgnore
	List<Project> projects;

	String bio;

	@OneToMany(targetEntity=Achievement.class, mappedBy="user", fetch=FetchType.EAGER)
	List<Achievement> achievements;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "usersSubscribed")
	@JsonIgnore
	List<Project> projectsSubscribed;


	public List<Project> getProjectsSubscribed() {
		return projectsSubscribed;
	}


	public void setProjectsSubscribed(List<Project> projectsSubscribed) {
		this.projectsSubscribed = projectsSubscribed;
	}

	public String getCoverPhotoUrl() {
		return coverPhotoUrl;
	}


	public void setCoverPhotoUrl(String coverPhotoUrl) {
		this.coverPhotoUrl = coverPhotoUrl;
	}


	public User () { 

	}


	public User (String name, String email, String photo, String socialId) {
		this.name = name;
		this.email = email;
		this.photo = photo;
		this.socialId = socialId;
	}

	public String getEmail () {
		return email;
	}

	public void setEmail (String email) {
		this.email = email;
	}


	public String getSocialId() {
		return socialId;
	}

	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public List<Achievement> getAchievements() {
		return achievements;
	}

	public void setAchievements(List<Achievement> achievements) {
		this.achievements = achievements;
	}

}
