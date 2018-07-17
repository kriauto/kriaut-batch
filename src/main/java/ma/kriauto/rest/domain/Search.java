package ma.kriauto.rest.domain;

public class Search {
	private String deviceid;
	private String date;
	
	public Search() {
	}

	public Search(String deviceid, String date) {
		super();
		this.deviceid = deviceid;
		this.date = date;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Search [deviceid=" + deviceid + ", date=" + date + "]";
	}
}
