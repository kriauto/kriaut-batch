package ma.kriauto.rest.domain;

public class Consumption {
	private String consumption;
	private String day;
	
	public Consumption() {
		super();
	}

	public Consumption(String consumption, String day) {
		super();
		this.consumption = consumption;
		this.day = day;
	}

	public String getConsumption() {
		return consumption;
	}

	public void setConsumption(String consumption) {
		this.consumption = consumption;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	@Override
	public String toString() {
		return "Consumption [consumption=" + consumption + ", day=" + day + "]";
	}
}
