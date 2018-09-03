package ma.kriauto.rest.domain;

public class Car {
	
	private Integer id;
	private Integer agencyid;
	private String imei;
	private String simnumber;
	private String immatriculation;
	private String vin;
	private String mark;
	private String model;
	private String color;
	private String photo;
	private Integer status;
	private Integer rolling;
	private Integer deviceid;
	private Integer mileage;
	private Double totaldistance;
	private Double emptyingtotaldistance;
	private Double course;
	private Double speed;
	private Double consumption;
	private String fuel;
	private String address;
	private Double latitude1;
	private Double longitude1;
	private Double latitude2;
	private Double longitude2;
	private Double latitude3;
	private Double longitude3;
	private Double latitude4;
	private Double longitude4;
	private Double latitude5;
	private Double longitude5;
	private Double latitude6;
	private Double longitude6;
	private String colorcode;
	private String devicetype;
	private Boolean enable;
	private String  technicalcontroldate;
	private Double  emptyingkilometre;
	private Integer  emptyingkilometreindex;
	private String  emptyingkilometredate; 
	private String insuranceenddate;
	private Double maxspeed;
	private Double maxcourse;
	private Double minlevelfuel;
	private Double maxenginetemperature;
	private Double minfridgetemperature;
	private Double maxfridgetemperature;
	private String  autorisationcirculationenddate;
	private Boolean notiftechnicalcontroldate;
	private Boolean notifemptyingkilometre;
	private Boolean notifemptyingkilometredate;
	private Boolean notifinsuranceenddate;
	private Boolean notifmaxspeed;
	private Boolean notifmaxcourse;
	private Boolean notifminlevelfuel;
	private Boolean notifmaxenginetemperature;
	private Boolean notifminfridgetemperature;
	private Boolean notifmaxfridgetemperature;
	private Boolean notifautorisationcirculationenddate;
	private Boolean notifinzone;
	private Boolean notifoutzone;
	private Boolean inzone;
	
	public Car() {
		super();
	}
	
	public Car(String immatriculation, Integer deviceid) {
		super();
		this.immatriculation = immatriculation;
		this.deviceid = deviceid;
	}

	public Car(Integer id, Integer agencyid, String imei, String simnumber,
			String immatriculation, String vin, String mark, String model,
			String color, String photo, Integer status, Integer rolling,
			Integer deviceid, Integer mileage, Double totaldistance,
			Double emptyingtotaldistance, Double course, Double speed,
			Double consumption, String fuel, String address, Double latitude1,
			Double longitude1, Double latitude2, Double longitude2,
			Double latitude3, Double longitude3, Double latitude4,
			Double longitude4, Double latitude5, Double longitude5,
			Double latitude6, Double longitude6, String colorcode,
			String devicetype, Boolean enable, String technicalcontroldate,
			Double emptyingkilometre, Integer emptyingkilometreindex,
			String emptyingkilometredate, String insuranceenddate,
			Double maxspeed, Double maxcourse, Double minlevelfuel,
			Double maxenginetemperature, Double minfridgetemperature,
			Double maxfridgetemperature, String autorisationcirculationenddate,
			Boolean notiftechnicalcontroldate, Boolean notifemptyingkilometre,
			Boolean notifemptyingkilometredate, Boolean notifinsuranceenddate,
			Boolean notifmaxspeed, Boolean notifmaxcourse,
			Boolean notifminlevelfuel, Boolean notifmaxenginetemperature,
			Boolean notifminfridgetemperature,
			Boolean notifmaxfridgetemperature,
			Boolean notifautorisationcirculationenddate,
			Boolean notifinzone,
			Boolean notifoutzone,
			Boolean inzone) {
		super();
		this.id = id;
		this.agencyid = agencyid;
		this.imei = imei;
		this.simnumber = simnumber;
		this.immatriculation = immatriculation;
		this.vin = vin;
		this.mark = mark;
		this.model = model;
		this.color = color;
		this.photo = photo;
		this.status = status;
		this.rolling = rolling;
		this.deviceid = deviceid;
		this.mileage = mileage;
		this.totaldistance = totaldistance;
		this.emptyingtotaldistance = emptyingtotaldistance;
		this.course = course;
		this.speed = speed;
		this.consumption = consumption;
		this.fuel = fuel;
		this.address = address;
		this.latitude1 = latitude1;
		this.longitude1 = longitude1;
		this.latitude2 = latitude2;
		this.longitude2 = longitude2;
		this.latitude3 = latitude3;
		this.longitude3 = longitude3;
		this.latitude4 = latitude4;
		this.longitude4 = longitude4;
		this.latitude5 = latitude5;
		this.longitude5 = longitude5;
		this.latitude6 = latitude6;
		this.longitude6 = longitude6;
		this.colorcode = colorcode;
		this.devicetype = devicetype;
		this.enable = enable;
		this.technicalcontroldate = technicalcontroldate;
		this.emptyingkilometre = emptyingkilometre;
		this.emptyingkilometreindex = emptyingkilometreindex;
		this.emptyingkilometredate = emptyingkilometredate;
		this.insuranceenddate = insuranceenddate;
		this.maxspeed = maxspeed;
		this.maxcourse = maxcourse;
		this.minlevelfuel = minlevelfuel;
		this.maxenginetemperature = maxenginetemperature;
		this.minfridgetemperature = minfridgetemperature;
		this.maxfridgetemperature = maxfridgetemperature;
		this.autorisationcirculationenddate = autorisationcirculationenddate;
		this.notiftechnicalcontroldate = notiftechnicalcontroldate;
		this.notifemptyingkilometre = notifemptyingkilometre;
		this.notifemptyingkilometredate = notifemptyingkilometredate;
		this.notifinsuranceenddate = notifinsuranceenddate;
		this.notifmaxspeed = notifmaxspeed;
		this.notifmaxcourse = notifmaxcourse;
		this.notifminlevelfuel = notifminlevelfuel;
		this.notifmaxenginetemperature = notifmaxenginetemperature;
		this.notifminfridgetemperature = notifminfridgetemperature;
		this.notifmaxfridgetemperature = notifmaxfridgetemperature;
		this.notifautorisationcirculationenddate = notifautorisationcirculationenddate;
		this.notifinzone = notifautorisationcirculationenddate;
		this.notifoutzone = notifautorisationcirculationenddate;
		this.inzone= notifautorisationcirculationenddate;
	}

	public Double getTotaldistance() {
		return totaldistance;
	}


	public void setTotaldistance(Double totaldistance) {
		this.totaldistance = totaldistance;
	}


	public Boolean getEnable() {
		return enable;
	}


	public void setEnable(Boolean enable) {
		this.enable = enable;
	}


	public String getTechnicalcontroldate() {
		return technicalcontroldate;
	}


	public void setTechnicalcontroldate(String technicalcontroldate) {
		this.technicalcontroldate = technicalcontroldate;
	}


	public String getEmptyingkilometredate() {
		return emptyingkilometredate;
	}


	public void setEmptyingkilometredate(String emptyingkilometredate) {
		this.emptyingkilometredate = emptyingkilometredate;
	}


	public String getInsuranceenddate() {
		return insuranceenddate;
	}


	public void setInsuranceenddate(String insuranceenddate) {
		this.insuranceenddate = insuranceenddate;
	}


	public Double getMaxcourse() {
		return maxcourse;
	}


	public void setMaxcourse(Double maxcourse) {
		this.maxcourse = maxcourse;
	}


	public Double getMinlevelfuel() {
		return minlevelfuel;
	}


	public void setMinlevelfuel(Double minlevelfuel) {
		this.minlevelfuel = minlevelfuel;
	}


	public Double getMaxenginetemperature() {
		return maxenginetemperature;
	}


	public void setMaxenginetemperature(Double maxenginetemperature) {
		this.maxenginetemperature = maxenginetemperature;
	}


	public Double getMinfridgetemperature() {
		return minfridgetemperature;
	}


	public void setMinfridgetemperature(Double minfridgetemperature) {
		this.minfridgetemperature = minfridgetemperature;
	}


	public Double getMaxfridgetemperature() {
		return maxfridgetemperature;
	}


	public void setMaxfridgetemperature(Double maxfridgetemperature) {
		this.maxfridgetemperature = maxfridgetemperature;
	}


	public Boolean getNotiftechnicalcontroldate() {
		return notiftechnicalcontroldate;
	}


	public void setNotiftechnicalcontroldate(Boolean notiftechnicalcontroldate) {
		this.notiftechnicalcontroldate = notiftechnicalcontroldate;
	}


	public Boolean getNotifemptyingkilometre() {
		return notifemptyingkilometre;
	}


	public void setNotifemptyingkilometre(Boolean notifemptyingkilometre) {
		this.notifemptyingkilometre = notifemptyingkilometre;
	}


	public Boolean getNotifemptyingkilometredate() {
		return notifemptyingkilometredate;
	}


	public void setNotifemptyingkilometredate(Boolean notifemptyingkilometredate) {
		this.notifemptyingkilometredate = notifemptyingkilometredate;
	}


	public Boolean getNotifinsuranceenddate() {
		return notifinsuranceenddate;
	}


	public void setNotifinsuranceenddate(Boolean notifinsuranceenddate) {
		this.notifinsuranceenddate = notifinsuranceenddate;
	}


	public Boolean getNotifmaxspeed() {
		return notifmaxspeed;
	}


	public void setNotifmaxspeed(Boolean notifmaxspeed) {
		this.notifmaxspeed = notifmaxspeed;
	}


	public Boolean getNotifmaxcourse() {
		return notifmaxcourse;
	}


	public void setNotifmaxcourse(Boolean notifmaxcourse) {
		this.notifmaxcourse = notifmaxcourse;
	}


	public Boolean getNotifminlevelfuel() {
		return notifminlevelfuel;
	}


	public void setNotifminlevelfuel(Boolean notifminlevelfuel) {
		this.notifminlevelfuel = notifminlevelfuel;
	}


	public Boolean getNotifmaxenginetemperature() {
		return notifmaxenginetemperature;
	}


	public void setNotifmaxenginetemperature(Boolean notifmaxenginetemperature) {
		this.notifmaxenginetemperature = notifmaxenginetemperature;
	}


	public Boolean getNotifminfridgetemperature() {
		return notifminfridgetemperature;
	}


	public void setNotifminfridgetemperature(Boolean notifminfridgetemperature) {
		this.notifminfridgetemperature = notifminfridgetemperature;
	}


	public Boolean getNotifmaxfridgetemperature() {
		return notifmaxfridgetemperature;
	}


	public void setNotifmaxfridgetemperature(Boolean notifmaxfridgetemperature) {
		this.notifmaxfridgetemperature = notifmaxfridgetemperature;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getAgencyid() {
		return agencyid;
	}


	public void setAgencyid(Integer agencyid) {
		this.agencyid = agencyid;
	}


	public String getImei() {
		return imei;
	}


	public void setImei(String imei) {
		this.imei = imei;
	}


	public String getSimnumber() {
		return simnumber;
	}


	public void setSimnumber(String simnumber) {
		this.simnumber = simnumber;
	}


	public String getImmatriculation() {
		return immatriculation;
	}


	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
	}


	public String getVin() {
		return vin;
	}


	public void setVin(String vin) {
		this.vin = vin;
	}


	public String getMark() {
		return mark;
	}


	public void setMark(String mark) {
		this.mark = mark;
	}


	public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public String getPhoto() {
		return photo;
	}


	public void setPhoto(String photo) {
		this.photo = photo;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public Integer getDeviceid() {
		return deviceid;
	}


	public void setDeviceid(Integer deviceid) {
		this.deviceid = deviceid;
	}


	public Double getMaxspeed() {
		return maxspeed;
	}


	public void setMaxspeed(Double maxspeed) {
		this.maxspeed = maxspeed;
	}


	public Integer getMileage() {
		return mileage;
	}


	public void setMileage(Integer mileage) {
		this.mileage = mileage;
	}
	
	
	public Double getConsumption() {
		return consumption;
	}


	public void setConsumption(Double consumption) {
		this.consumption = consumption;
	}


	public String getFuel() {
		return fuel;
	}


	public void setFuel(String fuel) {
		this.fuel = fuel;
	}

	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLatitude1() {
		return latitude1;
	}


	public void setLatitude1(Double latitude1) {
		this.latitude1 = latitude1;
	}


	public Double getLongitude1() {
		return longitude1;
	}


	public void setLongitude1(Double longitude1) {
		this.longitude1 = longitude1;
	}


	public Double getLatitude2() {
		return latitude2;
	}


	public void setLatitude2(Double latitude2) {
		this.latitude2 = latitude2;
	}


	public Double getLongitude2() {
		return longitude2;
	}


	public void setLongitude2(Double longitude2) {
		this.longitude2 = longitude2;
	}


	public Double getLatitude3() {
		return latitude3;
	}


	public void setLatitude3(Double latitude3) {
		this.latitude3 = latitude3;
	}


	public Double getLongitude3() {
		return longitude3;
	}


	public void setLongitude3(Double longitude3) {
		this.longitude3 = longitude3;
	}


	public Double getLatitude4() {
		return latitude4;
	}


	public void setLatitude4(Double latitude4) {
		this.latitude4 = latitude4;
	}


	public Double getLongitude4() {
		return longitude4;
	}


	public void setLongitude4(Double longitude4) {
		this.longitude4 = longitude4;
	}


	public Double getLatitude5() {
		return latitude5;
	}


	public void setLatitude5(Double latitude5) {
		this.latitude5 = latitude5;
	}


	public Double getLongitude5() {
		return longitude5;
	}


	public void setLongitude5(Double longitude5) {
		this.longitude5 = longitude5;
	}


	public Double getLatitude6() {
		return latitude6;
	}


	public void setLatitude6(Double latitude6) {
		this.latitude6 = latitude6;
	}


	public Double getLongitude6() {
		return longitude6;
	}

	public void setLongitude6(Double longitude6) {
		this.longitude6 = longitude6;
	}

	public Double getCourse() {
		return course;
	}

	public void setCourse(Double course) {
		this.course = course;
	}


	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Double getEmptyingkilometre() {
		return emptyingkilometre;
	}

	public void setEmptyingkilometre(Double emptyingkilometre) {
		this.emptyingkilometre = emptyingkilometre;
	}

	public String getDevicetype() {
		return devicetype;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	public String getColorcode() {
		return colorcode;
	}

	public void setColorcode(String colorcode) {
		this.colorcode = colorcode;
	}
	
	public Integer getRolling() {
		return rolling;
	}

	public void setRolling(Integer rolling) {
		this.rolling = rolling;
	}
	
	public Double getEmptyingtotaldistance() {
		return emptyingtotaldistance;
	}

	public void setEmptyingtotaldistance(Double emptyingtotaldistance) {
		this.emptyingtotaldistance = emptyingtotaldistance;
	}

	public Integer getEmptyingkilometreindex() {
		return emptyingkilometreindex;
	}

	public void setEmptyingkilometreindex(Integer emptyingkilometreindex) {
		this.emptyingkilometreindex = emptyingkilometreindex;
	}
	
	public String getAutorisationcirculationenddate() {
		return autorisationcirculationenddate;
	}

	public void setAutorisationcirculationenddate(
			String autorisationcirculationenddate) {
		this.autorisationcirculationenddate = autorisationcirculationenddate;
	}

	public Boolean getNotifautorisationcirculationenddate() {
		return notifautorisationcirculationenddate;
	}

	public void setNotifautorisationcirculationenddate(
			Boolean notifautorisationcirculationenddate) {
		this.notifautorisationcirculationenddate = notifautorisationcirculationenddate;
	}
	
	public Boolean getNotifinzone() {
		return notifinzone;
	}

	public void setNotifinzone(Boolean notifinzone) {
		this.notifinzone = notifinzone;
	}

	public Boolean getNotifoutzone() {
		return notifoutzone;
	}

	public void setNotifoutzone(Boolean notifoutzone) {
		this.notifoutzone = notifoutzone;
	}

	public Boolean getInzone() {
		return inzone;
	}

	public void setInzone(Boolean inzone) {
		this.inzone = inzone;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", agencyid=" + agencyid + ", imei=" + imei
				+ ", simnumber=" + simnumber + ", immatriculation="
				+ immatriculation + ", vin=" + vin + ", mark=" + mark
				+ ", model=" + model + ", color=" + color + ", photo=" + photo
				+ ", status=" + status + ", rolling=" + rolling + ", deviceid="
				+ deviceid + ", mileage=" + mileage + ", totaldistance="
				+ totaldistance + ", emptyingtotaldistance="
				+ emptyingtotaldistance + ", course=" + course + ", speed="
				+ speed + ", consumption=" + consumption + ", fuel=" + fuel
				+ ", address=" + address + ", latitude1=" + latitude1
				+ ", longitude1=" + longitude1 + ", latitude2=" + latitude2
				+ ", longitude2=" + longitude2 + ", latitude3=" + latitude3
				+ ", longitude3=" + longitude3 + ", latitude4=" + latitude4
				+ ", longitude4=" + longitude4 + ", latitude5=" + latitude5
				+ ", longitude5=" + longitude5 + ", latitude6=" + latitude6
				+ ", longitude6=" + longitude6 + ", colorcode=" + colorcode
				+ ", devicetype=" + devicetype + ", enable=" + enable
				+ ", technicalcontroldate=" + technicalcontroldate
				+ ", emptyingkilometre=" + emptyingkilometre
				+ ", emptyingkilometreindex=" + emptyingkilometreindex
				+ ", emptyingkilometredate=" + emptyingkilometredate
				+ ", insuranceenddate=" + insuranceenddate + ", maxspeed="
				+ maxspeed + ", maxcourse=" + maxcourse + ", minlevelfuel="
				+ minlevelfuel + ", maxenginetemperature="
				+ maxenginetemperature + ", minfridgetemperature="
				+ minfridgetemperature + ", maxfridgetemperature="
				+ maxfridgetemperature + ", autorisationcirculationenddate="
				+ autorisationcirculationenddate
				+ ", notiftechnicalcontroldate=" + notiftechnicalcontroldate
				+ ", notifemptyingkilometre=" + notifemptyingkilometre
				+ ", notifemptyingkilometredate=" + notifemptyingkilometredate
				+ ", notifinsuranceenddate=" + notifinsuranceenddate
				+ ", notifmaxspeed=" + notifmaxspeed + ", notifmaxcourse="
				+ notifmaxcourse + ", notifminlevelfuel=" + notifminlevelfuel
				+ ", notifmaxenginetemperature=" + notifmaxenginetemperature
				+ ", notifminfridgetemperature=" + notifminfridgetemperature
				+ ", notifmaxfridgetemperature=" + notifmaxfridgetemperature
				+ ", notifautorisationcirculationenddate="
				+ notifautorisationcirculationenddate + ", notifinzone="
				+ notifinzone + ", notifoutzone=" + notifoutzone + ", inzone="
				+ inzone + "]";
	}
	
}
