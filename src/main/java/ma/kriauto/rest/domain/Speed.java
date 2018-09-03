package ma.kriauto.rest.domain;

public class Speed {
	private String maxSpeed;
	private String day;
	private String hour;
	
	public Speed() {
		super();
	}

	public Speed(String maxSpeed, String day, String hour) {
		super();
		this.maxSpeed = maxSpeed;
		this.day = day;
		this.hour = hour;
	}

	public String getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(String maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
	
	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	@Override
	public String toString() {
		return "Speed [maxSpeed=" + maxSpeed + ", day=" + day + ", hour="
				+ hour + "]";
	}
}
