package ma.kriauto.rest.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import ma.kriauto.rest.dao.ProfileDao;
import ma.kriauto.rest.domain.Profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("profileService")
public class ProfileServiceImpl implements ProfileService {
	
	private static int count = 0 ;
	
	@Autowired
	ProfileDao profiledao;

	@Override
	public Profile getProfileByLogin(String login) {
		// TODO Auto-generated method stub
		return profiledao.getProfileByLogin(login);
	}

	@Override
	public Profile getProfileByToken(String token) {
		// TODO Auto-generated method stub
		return profiledao.getProfileByToken(token);
	}

	@Override
	public Profile getProfileByMail(String mail) {
		// TODO Auto-generated method stub
		return profiledao.getProfileByMail(mail);
	}
	
	@Override
	public Profile getProfileById(int id) {
		// TODO Auto-generated method stub
		return profiledao.getProfileById(id);
	}

	@Override
	public void updateProfile(Profile profile) {
		// TODO Auto-generated method stub
		profiledao.updateProfile(profile);
	}
	
	@Override
	public void addPushNotifProfile(Profile profile) {
		// TODO Auto-generated method stub
		profiledao.addPushNotifProfile(profile);
	}

	@Override
	public void deletePushNotifProfile(Profile profile) {
		// TODO Auto-generated method stub
		profiledao.deletePushNotifProfile(profile);
	}
	
	@Override
	public List<Profile> getAllProfiles() {
		// TODO Auto-generated method stub
		return profiledao.getAllProfiles();
	}

	@Override
	public String sendPassword(String to, String from, String subject,
			String content) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String hash256Profile(Profile profile){
		String textToHash = profile.getLogin()+":"+profile.getPassword(), encoded = null;
		MessageDigest digest;
        try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] byteOfTextToHash = textToHash.getBytes("UTF-8");
		    byte[] hashedByetArray = digest.digest(byteOfTextToHash);
		    encoded = Base64.getEncoder().encodeToString(hashedByetArray);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return encoded;
	}

	@Override
	public String getKeyMap() {
		String key = null;
		if(count == 0){
			key = "AIzaSyB5DReW25MUyC1U8q7KKqI_7fbecvv6qkI";
		}else if(count == 0){
			key = "AIzaSyBflunQHVzkUf1EU5RYvybXCUAzUtQiTBY";
		}else{
			count = 0 ;
			key = "AIzaSyC2ahLDzb8KTzhU2QF_ByfOMuZW4_fQwTs";
		}
		return key ;
	}
}
