package two.ways.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="email_subscription")
public class EmailSubscription {

	@Id
	String userSocialId;

	@Enumerated(EnumType.ORDINAL)
	Frequency frequency;

	public enum Frequency {
		DAILY, WEEKLY, MONTHLY;

		public static Frequency fromInt (int value) {
			switch (value) {
				case 0:
					return DAILY;
				case 1:
					return WEEKLY;
				case 2: 
					return MONTHLY;
				default:
					throw new IllegalArgumentException ();
			}
		}
	}

	public String getUserSocialId() {
		return userSocialId;
	}

	public void setUserSocialId(String userSocialId) {
		this.userSocialId = userSocialId;
	}

	public Frequency getFrequency() {
		return frequency;
	}

	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}

	public EmailSubscription(String userSocialId, Frequency frequency) {
		super();
		this.userSocialId = userSocialId;
		this.frequency = frequency;
	}

	public EmailSubscription() {
		super();
	}

	@Override
	public String toString() {
		return "EmailSubscription [userSocialId=" + userSocialId + ", frequency=" + frequency + "]";
	}



}
