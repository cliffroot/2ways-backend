package two.ways.model;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name="project")
public class Project{

	String name;

	@Temporal(value=TemporalType.DATE)
	@JsonDeserialize(using= DateDeserializer.class)
	@JsonSerialize(using=DateSerializer.class)
	Date date;

	String description;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="owner_id")
	User owner;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	Integer id;

	@ElementCollection(fetch=FetchType.EAGER)
	List<String> photos;

	@OneToMany(targetEntity=Comment.class, mappedBy="project", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	List<Comment> comments;

	String goal; //TODO: not string ok;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinTable(name = "users_subscriptions",  joinColumns = { 
			@JoinColumn(name = "project_id", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "user_id",  nullable = false, updatable = false) })
	List<User> usersSubscribed;

	String place;

	public static class DateDeserializer extends JsonDeserializer<Date> {

		@Override
		public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			String dateString = jp.getValueAsString();
			System.out.println("DATE:" + dateString);
			DateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss");
			Date date = null;
			try {
				date = sdf.parse(dateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;
		}
	}

	public static class DateSerializer extends JsonSerializer<Date> {

		@Override
		public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
			DateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss");
			jgen.writeString(sdf.format(value));
		}

	}

	public Project () {
		//empty constructor for spring
	}

	public Project (String name, List<String> photos) {
		this.name = name;
		this.photos = photos;
	}

	//less dummy constructor
	// title, description, id, location, goal, date
	public Project (String name, String description, int id, String location, String goal, long date) {
		this.name = name;
		this.id = id;
		this.description = description;
		place = location;//new LatLng(Double.valueOf(location.split(";")[0]), Double.valueOf(location.split(";")[1]));
		this.goal = goal;
		this.date = new Date(date);
	}

	//TODO: fuck me if we're not supposed to use builder pattern here
	public Project(String name, Date date, String description, 
			User owner, List<String> photos, List<Comment> comments, String goal, List<User> usersSubscribed, String place) {
		this.name = name;
		this.date = date;
		this.description = description;
		this.owner = owner;
		id = id;
		this.photos = photos;
		this.comments = comments;
		this.goal = goal;
		this.usersSubscribed = usersSubscribed;
		this.place = place;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<String> getPhotos() {
		return photos;
	}

	public void setPhotos(List<String> photos) {
		this.photos = photos;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public List<User> getUsersSubscribed() {
		return usersSubscribed;
	}

	public void setUsersSubscribed(List<User> usersSubscribed) {
		this.usersSubscribed = usersSubscribed;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	@Override
	public String toString() {
		return "Project{" +
				"name='" + name + '\'' +
				", date=" + date +
				", description='" + description + '\'' +
				", owner=" + owner +
				", id=" + id +
				", photos=" + photos +
				", comments=" + comments +
				", goal='" + goal + '\'' +
				", usersSubscribed=" + usersSubscribed +
				", place=" + place +
				'}';
	}

}
