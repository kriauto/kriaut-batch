package ma.kriauto.rest.domain;

public class Agency {
	
	private Integer id;
	private String name;
	private Integer code;
	private String city;
	private String address;
	private String phone;
	private String fax;
	private Integer carnumber;
	
	public Agency() {
	}

	public Agency(Integer id, String name, Integer code, String city, String address,
			String phone, String fax, Integer carnumber) {
		this.id = id;
		this.name = name;
		this.code = code;
		this.city = city;
		this.address = address;
		this.phone = phone;
		this.fax = fax;
		this.carnumber = carnumber;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Integer getCarnumber() {
		return carnumber;
	}

	public void setCarnumber(Integer carnumber) {
		this.carnumber = carnumber;
	}

	@Override
	public String toString() {
		return "Agency [id=" + id + ", name=" + name + ", code=" + code
				+ ", city=" + city + ", address=" + address + ", phone="
				+ phone + ", fax=" + fax + ", carnumber=" + carnumber + "]";
	}
}
