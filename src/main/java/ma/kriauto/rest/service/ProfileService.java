package ma.kriauto.rest.service;

import java.util.List;

import ma.kriauto.rest.domain.Profile;

public interface ProfileService {
	public Profile getProfileByLogin(String login);
	public Profile getProfileByToken(String token);
	public Profile getProfileByMail(String mail);
	public Profile getProfileById(int id);
	public void updateProfile (Profile profile);
	public void addPushNotifProfile (Profile profile);
	public void deletePushNotifProfile (Profile profile);
	public List<Profile> getAllProfiles();
	public String sendPassword(String to, String from, String subject, String content);
	public String hash256Profile(Profile profile);
	public String getKeyMap();
}
