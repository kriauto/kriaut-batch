package ma.kriauto.rest.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ma.kriauto.rest.domain.Profile;
import ma.kriauto.rest.domain.ResponseMessage;
import ma.kriauto.rest.service.ProfileService;
import ma.kriauto.rest.service.SenderService;
import ma.kriauto.rest.util.Constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for profile actions.
 */
@Controller
public class ProfileController {  
	
	@Autowired
    private ProfileService profileService;
	
	@Autowired
    private SenderService senderService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Profile login(@RequestBody Profile profile, HttpServletRequest request, HttpServletResponse response) {
    	System.out.println("Begin login -->"+profile);
    	if(profile.getLogin() == null || profile.getLogin().equals("")){
    		throw new IllegalArgumentException("LOGIN_REQUIRED");
    	}else if(profile.getPassword() == null || profile.getPassword().equals("")){
    		throw new IllegalArgumentException("PASSWORD_REQUIRED");
    	}
    	
    	Profile currentprofile = profileService.getProfileByLogin(profile.getLogin());
    	if(null == currentprofile){
    		throw new IllegalArgumentException("LOGIN_NOT_FOUND");
    	}else if(!currentprofile.getPassword().equals(profile.getPassword())){
    		throw new IllegalArgumentException("PASSWORD_FAILED");
    	}else{
    		String token = profileService.hash256Profile(currentprofile);
    		String key = profileService.getKeyMap();
    		currentprofile.setToken(token);
    		currentprofile.setGooglekey(key);
    		profileService.updateProfile(currentprofile);
        	System.out.println("End login -->"+profile);
    		return currentprofile;
    	}
    }
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage logout(@RequestHeader(value="Authorization") String authorization) {
    	System.out.println("Begin logout -->"+authorization);
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token);
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	System.out.println("End logout -->");
        return new ResponseMessage(ResponseMessage.Type.success, "LOGOUT_SUCCES",Constant.getLabels().get("LOGOUT_SUCCES").toString());
    }
	
	@RequestMapping(value = "/loginpushnotif", method = RequestMethod.POST)
    @ResponseBody
    public Profile loginpushnotif(@RequestBody Profile profile) {
    	System.out.println("Begin loginpushnotif -->"+profile);
    	if(profile.getLogin() == null || profile.getLogin().equals("")){
    		throw new IllegalArgumentException("LOGIN_REQUIRED");
    	}else if(profile.getPassword() == null || profile.getPassword().equals("")){
    		throw new IllegalArgumentException("PASSWORD_REQUIRED");
    	}
    	
    	Profile currentprofile = profileService.getProfileByLogin(profile.getLogin());
    	if(null == currentprofile){
    		throw new IllegalArgumentException("LOGIN_NOT_FOUND");
    	}else if(!currentprofile.getPassword().equals(profile.getPassword())){
    		throw new IllegalArgumentException("PASSWORD_FAILED");
    	}else{
    		String token = profileService.hash256Profile(currentprofile);
    		String key = profileService.getKeyMap();
    		currentprofile.setToken(token);
    		currentprofile.setGooglekey(key);
    		profileService.updateProfile(currentprofile);
    		profileService.addPushNotifProfile(profile);
    		currentprofile.setPushplateforme(profile.getPushplateforme());
    		currentprofile.setPushnotiftoken(profile.getPushnotiftoken());
        	System.out.println("End loginpushnotif -->"+profile);
    		return currentprofile;
    	}
    }
	
	@RequestMapping(value = "/logoutpushnotif", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage logoutpushnotif(@RequestBody Profile profile ,@RequestHeader(value="Authorization") String authorization) {
    	System.out.println("Begin logoutpushnotif -->"+authorization);
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile1 = profileService.getProfileByToken(token);
    	if(null == profile1){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	profileService.deletePushNotifProfile(profile);
    	System.out.println("End logoutpushnotif -->");
        return new ResponseMessage(ResponseMessage.Type.success, "LOGOUT_SUCCES",Constant.getLabels().get("LOGOUT_SUCCES").toString());
    }
	
	@RequestMapping(value = "/getProfile", method = RequestMethod.GET)
    @ResponseBody
    public Profile getProfile(@RequestHeader(value="Authorization") String authorization) {
    	System.out.println("Begin login -->"+authorization);
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token);
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	return profile;
    }
	
	@RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage updateProfile(@RequestHeader(value="Authorization") String authorization, @RequestBody Profile profile) {
    	System.out.println("Begin login -->"+profile);
    	String token = authorization.replaceAll("Basic", "");
    	Profile profil = profileService.getProfileByToken(token);
    	if(null == profil){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	profileService.updateProfile(profile);
    	if(profile.getPassword() == null || profile.getPassword().equals("")){
    		throw new IllegalArgumentException("PASSWORD_REQUIRED");
    	}
    	return new ResponseMessage(ResponseMessage.Type.success, null,null);
    }
	
	@RequestMapping(value = "/initPassword", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage initPassword(@RequestBody Profile profile) {
    	System.out.println("Begin initPassword -->"+profile);
    	if(null == profile.getMail()){
    		throw new IllegalArgumentException("MAIL_REQUIRED");
    	}
    	Profile profiletmp = profileService.getProfileByMail(profile.getMail());
    	if(null == profiletmp){
    		throw new IllegalArgumentException("MAIL_NOT_FOUND");
    	}
    	String from = "contact@kriauto.ma";
    	String to = profiletmp.getMail();
    	String subject = "Identifiants de connexion";
    	String message = "Bonjour "+profiletmp.getName()+", <br/><br/> Veuillez trouver vos identifiants de connexion : <br/><br/> - login : "+profiletmp.getLogin()+" <br/> - Mot de passe : "+profiletmp.getPassword()+" <br/><br/> l'équipe KriAuto.";
    	senderService.sendMail(from, to, subject, message);
    	return new ResponseMessage(ResponseMessage.Type.success, "PASSWORD_SEND",Constant.getLabels().get("PASSWORD_SEND").toString());
    }
	
	@RequestMapping(value = "/pushnotification", method = RequestMethod.GET)
    @ResponseBody
    public void pushnotification() {
    	System.out.println("Begin pushnotification -->");
    	String token1 = "dhUF18ynK_U:APA91bF-7O1HmueduVCl3HWIIW7yKXGW7z5DfM4z7Er698zTdgd0cWT_Q3newSCCWV_WHVOVQeOmiH0b9E8uCBm9G58z2pq2L6jKS4BKZu3SI9xKoqvPaouIEPt-Y7xd3"
                       +"JxdxpxjyLVxPJaCQ4qVh0l29TSjw-PNvg";
    	String token2 = "eQpUHiw3Hfw:APA91bEFZnHHbyZYuTJSY7kHAbbrrPr-ldTmRT7BSY3knO78f2IJR1K590hVHFo54HWerlRSQ4DdPJDoljr15tArS13bVhn2tbGYtnwjNomQ7Xw8DPtblCWEAOC6OHRkM"
    	               +"JrjF2xJlVoOc8a2FSA3F0udkwiyAtLdYg";
    	try {
			senderService.sendPushNotification(token1, "ceci est un teste de notif pour abdelhaq");
			senderService.sendPushNotification(token2, "ceci est un teste de notif pour haitam");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
