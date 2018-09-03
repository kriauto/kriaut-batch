package ma.kriauto.rest.domain;

public class Profile {
	private Integer id;
	private Integer agencyid;
	private String login;
	private String password;
	private String name;
	private String mail;
	private String phone;
	private Integer job;
	private String label;
	private String token;
	private String pushnotiftoken;
	private String pushplateforme;
	private String googlekey;
	
	public Profile() {
		super();
	}

	public Profile(Integer id, Integer agencyid, String login, String password,
			String name, String mail, String phone, Integer job, String label,
			String token, String pushnotiftoken, String pushplateforme,
			String googlekey) {
		super();
		this.id = id;
		this.agencyid = agencyid;
		this.login = login;
		this.password = password;
		this.name = name;
		this.mail = mail;
		this.phone = phone;
		this.job = job;
		this.label = label;
		this.token = token;
		this.pushnotiftoken = pushnotiftoken;
		this.pushplateforme = pushplateforme;
		this.googlekey = googlekey;
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Integer getJob() {
		return job;
	}

	public void setJob(Integer job) {
		this.job = job;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getPushnotiftoken() {
		return pushnotiftoken;
	}

	public void setPushnotiftoken(String pushnotiftoken) {
		this.pushnotiftoken = pushnotiftoken;
	}
	
	public String getPushplateforme() {
		return pushplateforme;
	}

	public void setPushplateforme(String pushplateforme) {
		this.pushplateforme = pushplateforme;
	}

	public String getGooglekey() {
		return googlekey;
	}

	public void setGooglekey(String googlekey) {
		this.googlekey = googlekey;
	}

	@Override
	public String toString() {
		return "Profile [id=" + id + ", agencyid=" + agencyid + ", login="
				+ login + ", password=" + password + ", name=" + name
				+ ", mail=" + mail + ", phone=" + phone + ", job=" + job
				+ ", label=" + label + ", token=" + token + ", pushnotiftoken="
				+ pushnotiftoken + ", pushplateforme=" + pushplateforme
				+ ", googlekey=" + googlekey + "]";
	}
}
