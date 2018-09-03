package ma.kriauto.rest.domain;

public class Contact {
	private Long id;
	private String city;
	private String name;
	private String mail;
	private String phone;
	private String photo;
	
	public Contact() {
		super();
	}
	
	public Contact(Long id, String city, String name, String mail,
			String phone, String photo) {
		super();
		this.id = id;
		this.city = city;
		this.name = name;
		this.mail = mail;
		this.phone = phone;
		this.photo = photo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", city=" + city + ", name=" + name
				+ ", mail=" + mail + ", phone=" + phone + ", photo=" + photo
				+ "]";
	}
}
