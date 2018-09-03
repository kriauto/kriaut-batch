package ma.kriauto.rest.domain;

public class Device {
	private String  date;
	private Integer deviceid;
	
	public Device() {
		super();
	}

	public Device(String date, Integer deviceid) {
		super();
		this.date = date;
		this.deviceid = deviceid;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(Integer deviceid) {
		this.deviceid = deviceid;
	}

	@Override
	public String toString() {
		return "Device [date=" + date + ", deviceid=" + deviceid + "]";
	}
}
