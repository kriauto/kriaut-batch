package ma.kriauto.rest.domain;


public class Notification {
	private String login;
	private String pushnotiftoken;
	private String pushplateforme;
	private String deviceid;
	private String texte;
	private String creationdate;
	
	public Notification() {
		super();
	}
	
	public Notification(String deviceid, String texte) {
		super();
		this.deviceid = deviceid;
		this.texte = texte;
	}

	public Notification(String login, String pushnotiftoken,
			String pushplateforme, String deviceid, String texte, String ceationdate) {
		super();
		this.login = login;
		this.pushnotiftoken = pushnotiftoken;
		this.pushplateforme = pushplateforme;
		this.deviceid = deviceid;
		this.texte = texte;
		this.creationdate = creationdate;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
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
	
	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}
	
	public String getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(String creationdate) {
		this.creationdate = creationdate;
	}

	@Override
	public String toString() {
		return "Notification [login=" + login + ", pushnotiftoken="
				+ pushnotiftoken + ", pushplateforme=" + pushplateforme
				+ ", deviceid=" + deviceid + ", texte=" + texte + ", creationdate=" + creationdate + "]";
	}
}
