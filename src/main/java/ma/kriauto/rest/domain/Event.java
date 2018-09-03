package ma.kriauto.rest.domain;

public class Event {
     private Integer id;
	 private String  type;
	 private String  servertime;
	 private Integer deviceid;
	 private Integer positionid;
	 private String  attributes;
	 
	 public Event() {
		super();
	 }

	 public Event(Integer id, String type, String servertime, Integer deviceid,
			Integer positionid, String attributes) {
		super();
		this.id = id;
		this.type = type;
		this.servertime = servertime;
		this.deviceid = deviceid;
		this.positionid = positionid;
		this.attributes = attributes;
	 }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getServertime() {
		return servertime;
	}

	public void setServertime(String servertime) {
		this.servertime = servertime;
	}

	public Integer getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(Integer deviceid) {
		this.deviceid = deviceid;
	}

	public Integer getPositionid() {
		return positionid;
	}

	public void setPositionid(Integer positionid) {
		this.positionid = positionid;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", type=" + type + ", servertime="
				+ servertime + ", deviceid=" + deviceid + ", positionid="
				+ positionid + ", attributes=" + attributes + "]";
	}
}
