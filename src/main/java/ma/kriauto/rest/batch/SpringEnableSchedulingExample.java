package ma.kriauto.rest.batch;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import ma.kriauto.rest.domain.Car;
import ma.kriauto.rest.domain.Location;
import ma.kriauto.rest.domain.Notification;
import ma.kriauto.rest.domain.Profile;
import ma.kriauto.rest.domain.Speed;
import ma.kriauto.rest.domain.Statistic;
import ma.kriauto.rest.service.CarService;
import ma.kriauto.rest.service.NotificationService;
import ma.kriauto.rest.service.ProfileService;
import ma.kriauto.rest.service.SenderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
 
@Configuration
@EnableScheduling
public class SpringEnableSchedulingExample {
	
	@Autowired
	ProfileService profileservice;
	
	@Autowired
	CarService carservice;
	
	@Autowired
	NotificationService notificationservice;
	
	@Autowired
	SenderService senderservice;
	
	@Bean
	public TaskScheduler taskScheduler() {
	    return new ConcurrentTaskScheduler(); //single threaded by default
	}
	
	/**** Empting kilometre * @throws ParseException ***/
	@Scheduled(cron = "00 00 02 * * *")
//	@Scheduled(fixedDelay = 3000)
    public void calculateTotalDistance() throws ParseException {
       List<Profile> profiles = profileservice.getAllProfiles();
       for(int i=0; i < profiles.size(); i++){
    	   Profile profile = profiles.get(i);
    	   if(null != profile && null != profile.getLogin()){
    		   List<Car> cars = carservice.getAllCarsByProfile(profile.getLogin());
    		   for(int j=0; j<cars.size(); j++){
    			   Car car = cars.get(j);
    			   if(null != car){
					    Calendar calendar = Calendar.getInstance();
					    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Integer deviceid = car.getDeviceid();
                        String token = profile.getToken();
                        //for(int k=1; k<=210; k++){
                        	calendar = Calendar.getInstance();
                        	calendar.add(Calendar.DATE, -1);
                        	String date = sdf.format(calendar.getTime());
                        	Statistic statistic = carservice.getCarStatistic(deviceid, date, token);
                        	if(null != statistic && null != statistic.getCourse()){
                    		  Car currentcar = carservice.getCarByDevice(deviceid, token);
                    		  currentcar.setTotaldistance(Double.valueOf(Math.round(statistic.getCourse()+currentcar.getTotaldistance())));
                    		  currentcar.setEmptyingtotaldistance(Double.valueOf(Math.round(statistic.getCourse()+currentcar.getEmptyingtotaldistance())));
                    		  carservice.updateCar(currentcar);
                        	}
                        //}
    			    }
    		    }
    	    }
        }
    }
	
	/**** Technical Controle Notifications ***/
	@Scheduled(cron = "00 00 12 * * *")
    public void technicalControleNotifications() throws IOException {
       List<Profile> profiles = profileservice.getAllProfiles();
       for(int i=0; i < profiles.size(); i++){
    	   Profile profile = profiles.get(i);
    	   if(null != profile && null != profile.getLogin()){
    		   List<Notification> notifications = notificationservice.getPushTokenByProfile(profile.getLogin());
    		   List<Car> cars = carservice.getAllCarsByProfile(profile.getLogin());
    		   for(int j=0; j<cars.size(); j++){
    			   Car car = cars.get(j);
    			   if(null != car && null != car.getTechnicalcontroldate() && null != car.getNotiftechnicalcontroldate() && true == car.getNotiftechnicalcontroldate()){
						try {
							 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
							 Date now = new Date();
	    				     Date currentdate = sdf.parse(sdf.format(now));
	    				     Date technicaldate = sdf.parse(car.getTechnicalcontroldate());
							 long diffInMillies = technicaldate.getTime() - currentdate.getTime();
		    				 long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		    				 String message = "La prochaine date de controle technique est dans "+diff+" jour(s) pour la voiture : "+car.getMark()+" "+car.getModel()+" "+car.getColor()+" ("+car.getImmatriculation()+")";
		    				 if(0<= diff && diff <=15){
		    					 for(int k=0; k<notifications.size(); k++){
		    					   Notification notification = notifications.get(k);
		    					   if(null != notification && null != notification.getPushnotiftoken()){
		    					      senderservice.sendPushNotification(notification.getPushnotiftoken(), message);
		    					   }
		    					 }
		    					 Notification notif = new Notification(car.getDeviceid().toString(), message);
		    					 notificationservice.addNotification(notif);
		    				 }
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    			   }
    		   }
    	   }
       }
    }
	
	
	@Scheduled(cron = "00 00 14 * * *")
    public void emptyKilometreNotifications() throws ParseException, IOException {
       List<Profile> profiles = profileservice.getAllProfiles();
       for(int i=0; i < profiles.size(); i++){
    	   Profile profile = profiles.get(i);
    	   //System.out.println("Profile --> " + profile);
    	   if(null != profile && null != profile.getLogin()){
    		   List<Notification> notifications = notificationservice.getPushTokenByProfile(profile.getLogin());
    		   List<Car> cars = carservice.getAllCarsByProfile(profile.getLogin());
    		   for(int j=0; j<cars.size(); j++){
    			   Car car = cars.get(j);
    			   String message = "Vous devriez faire le vidange pour la voiture : "+car.getMark()+" "+car.getModel()+" "+car.getColor()+" ("+car.getImmatriculation()+")";
    			   if(null != car && null != car.getNotifemptyingkilometre() && true == car.getNotifemptyingkilometre()){
    				   if(Math.round(car.getEmptyingtotaldistance()/car.getEmptyingkilometre()) > car.getEmptyingkilometreindex()){
	    					 for(int k=0; k<notifications.size(); k++){
	    					   Notification notification = notifications.get(k);
	    					   if(null != notification && null != notification.getPushnotiftoken()){
	    					      senderservice.sendPushNotification(notification.getPushnotiftoken(), message);
	    					   }
	    					 }
	    					 Integer deviceid = car.getDeviceid();
	                         String token = profile.getToken();
	    					 Car currentcar = carservice.getCarByDevice(deviceid, token);
	                    	 currentcar.setEmptyingkilometreindex(car.getEmptyingkilometreindex()+1);
	                    	 carservice.updateCar(currentcar);
	    					 Notification notif = new Notification(car.getDeviceid().toString(), message);
	    					 notificationservice.addNotification(notif);
	    				 }
    			   }
    		   }
    	   }
       }
    }
	
	/**** Insurance Notifications ***/
	@Scheduled(cron = "00 00 16 * * *")
    public void insuranceendNotifications() throws IOException {
       List<Profile> profiles = profileservice.getAllProfiles();
       for(int i=0; i < profiles.size(); i++){
    	   Profile profile = profiles.get(i);
    	   System.out.println("Profile --> " + profile);
    	   if(null != profile && null != profile.getLogin()){
    		   List<Notification> notifications = notificationservice.getPushTokenByProfile(profile.getLogin());
    		   List<Car> cars = carservice.getAllCarsByProfile(profile.getLogin());
    		   for(int j=0; j<cars.size(); j++){
    			   Car car = cars.get(j);
    			   if(null != car && null != car.getInsuranceenddate() && null != car.getNotifinsuranceenddate() && true == car.getNotifinsuranceenddate()){
						try {
							 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
							 Date now = new Date();
	    				     Date currentdate = sdf.parse(sdf.format(now));
	    				     Date insurancedate = sdf.parse(car.getInsuranceenddate());
							 long diffInMillies = insurancedate.getTime() - currentdate.getTime();
		    				 long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		    				 String message = "L'assurance prendra fin dans "+diff+" jour(s) pour la voiture : "+car.getMark()+" "+car.getModel()+" "+car.getColor()+" ("+car.getImmatriculation()+")";
		    				 if(0<= diff && diff <=15){
		    					 for(int k=0; k<notifications.size(); k++){
		    					   Notification notification = notifications.get(k);
		    					   if(null != notification && null != notification.getPushnotiftoken()){
		    					      senderservice.sendPushNotification(notification.getPushnotiftoken(), message);
		    					   }
		    					 }
		    					 Notification notif = new Notification(car.getDeviceid().toString(), message);
		    					 notificationservice.addNotification(notif);
		    				 }
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    			   }
    		   }
    	   }
       }
    }
	
	/**** Circulation Notifications ***/
	@Scheduled(cron = "00 00 18 * * *")
    public void circulationendNotifications() throws IOException {
       List<Profile> profiles = profileservice.getAllProfiles();
       for(int i=0; i < profiles.size(); i++){
    	   Profile profile = profiles.get(i);
    	   System.out.println("Profile --> " + profile);
    	   if(null != profile && null != profile.getLogin()){
    		   List<Notification> notifications = notificationservice.getPushTokenByProfile(profile.getLogin());
    		   List<Car> cars = carservice.getAllCarsByProfile(profile.getLogin());
    		   for(int j=0; j<cars.size(); j++){
    			   Car car = cars.get(j);
    			   if(null != car && null != car.getAutorisationcirculationenddate() && null != car.getNotifautorisationcirculationenddate() && true == car.getNotifautorisationcirculationenddate()){
						try {
							 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
							 Date now = new Date();
	    				     Date currentdate = sdf.parse(sdf.format(now));
	    				     Date technicaldate = sdf.parse(car.getTechnicalcontroldate());
							 long diffInMillies = technicaldate.getTime() - currentdate.getTime();
		    				 long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		    				 String message = "L'autorisation de circulation prendra fin dans "+diff+" jour(s) pour la voiture : "+car.getMark()+" "+car.getModel()+" "+car.getColor()+" ("+car.getImmatriculation()+")";
		    				 if(0<= diff && diff <=15){
		    					 for(int k=0; k<notifications.size(); k++){
		    					   Notification notification = notifications.get(k);
		    					   if(null != notification && null != notification.getPushnotiftoken()){
		    					      senderservice.sendPushNotification(notification.getPushnotiftoken(), message);
		    					   }
		    					 }
		    					 Notification notif = new Notification(car.getDeviceid().toString(), message);
		    					 notificationservice.addNotification(notif);
		    				 }
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    			   }
    		   }
    	   }
       }
    }
	
	/**** Max speed Notifications ***/
	@Scheduled(fixedDelay = 3600000)
    public void maxspeedNotifications() throws IOException {
       List<Profile> profiles = profileservice.getAllProfiles();
       Calendar calendar = Calendar.getInstance();
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   calendar.add(Calendar.HOUR, -1);
	   Date d2 = calendar.getTime();
       String date = sdf.format(d2);
       for(int i=0; i < profiles.size(); i++){
    	   Profile profile = profiles.get(i);
    	   System.out.println("Profile --> " + profile);
    	   if(null != profile && null != profile.getLogin()){
    		   List<Notification> notifications = notificationservice.getPushTokenByProfile(profile.getLogin());
    		   List<Car> cars = carservice.getAllCarsByProfile(profile.getLogin());
    		   for(int j=0; j<cars.size(); j++){
    			   Car car = cars.get(j);
    			   if(null != car && null != car.getMaxspeed() && null != car.getNotifmaxspeed() && true == car.getNotifmaxspeed()){
	                         Speed speed = carservice.getMaxSpeedByCarTime(car.getDeviceid(), date);
		    				 if(null != speed && Double.valueOf(speed.getMaxSpeed()) > car.getMaxspeed()){
		    					 String message = "La vitesse journalière maximale autorisée ("+car.getMaxspeed()+") est dépassée pour la voiture : "+car.getMark()+" "+car.getModel()+" "+car.getColor()+" ("+car.getImmatriculation()+")";
		    					 for(int k=0; k<notifications.size(); k++){
		    					   Notification notification = notifications.get(k);
		    					   if(null != notification && null != notification.getPushnotiftoken()){
		    					      senderservice.sendPushNotification(notification.getPushnotiftoken(), message);
		    					   }
		    					 }
		    					 Notification notif = new Notification(car.getDeviceid().toString(), message);
		    					 notificationservice.addNotification(notif);
		    				}
    			   }
    		   }
    	   }
       }
    }
	
	/**** Max course Notifications ***/
	@Scheduled(cron = "00 00 23 * * *")
    public void maxcourseNotifications() throws IOException {
       List<Profile> profiles = profileservice.getAllProfiles();
       for(int i=0; i < profiles.size(); i++){
    	   Profile profile = profiles.get(i);
    	   System.out.println("Profile --> " + profile);
    	   if(null != profile && null != profile.getLogin()){
    		   List<Notification> notifications = notificationservice.getPushTokenByProfile(profile.getLogin());
    		   List<Car> cars = carservice.getAllCarsByProfile(profile.getLogin());
    		   for(int j=0; j<cars.size(); j++){
    			   Car car = cars.get(j);
    			   if(null != car && null != car.getMaxcourse() && null != car.getNotifmaxcourse() && true == car.getNotifmaxcourse() && null != profile && null != profile.getToken()){
							 Calendar calendar = Calendar.getInstance();
						     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	                         String date = sdf.format(calendar.getTime());
	                         Statistic statistic = carservice.getCarStatistic(car.getDeviceid(), date, profile.getToken());
		    				 if(null != statistic && statistic.getCourse() > car.getMaxcourse()){
		    					 String message = "La distance journalière maximale autorisée ("+car.getMaxcourse()+") est dépassée pour la voiture : "+car.getMark()+" "+car.getModel()+" "+car.getColor()+" ("+car.getImmatriculation()+")";
		    					 for(int k=0; k<notifications.size(); k++){
		    					   Notification notification = notifications.get(k);
		    					   if(null != notification && null != notification.getPushnotiftoken()){
		    					      senderservice.sendPushNotification(notification.getPushnotiftoken(), message);
		    					   }
		    					 }
		    					 Notification notif = new Notification(car.getDeviceid().toString(), message);
		    					 notificationservice.addNotification(notif);
		    				}
    			   }
    		   }
    	   }
       }
    }
 
	
	/**** exit zone Notifications ***/
	@Scheduled(fixedDelay = 3600000)
    public void exitzoneNotifications() throws IOException {
       List<Profile> profiles = profileservice.getAllProfiles();
       Calendar calendar = Calendar.getInstance();
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   calendar.add(Calendar.HOUR, -1);
	   Date d2 = calendar.getTime();
       String date = sdf.format(d2);
       for(int i=0; i < profiles.size(); i++){
    	   Profile profile = profiles.get(i);
    	   System.out.println("Profile --> " + profile);
    	   if(null != profile && null != profile.getLogin()){
    		   List<Notification> notifications = notificationservice.getPushTokenByProfile(profile.getLogin());
    		   List<Car> cars = carservice.getAllCarsByProfile(profile.getLogin());
    		   for(int j=0; j<cars.size(); j++){
    			   Car car = cars.get(j);
    			   if(null != car){
    				         boolean isceuta = false, ismelilia=false,isalgerie=false,ismauritanie=false;							
	                         List<Location> locations = carservice.getAllLocationByCarTime(car.getDeviceid(), date);
	                 	     for(int k=0 ; k<locations.size() ; k++){
	                 		   Location location = locations.get(k);
	                 		   if(!isceuta && null != location && isInCeuta(location.getLatitude(), location.getLongitude())){
	                 			     String message = "Sortie de territoire (sebta) : "+car.getMark()+" "+car.getModel()+" "+car.getColor()+" ("+car.getImmatriculation()+")";
			    					 for(int v=0; v<notifications.size(); v++){
			    					   Notification notification = notifications.get(v);
			    					   if(null != notification && null != notification.getPushnotiftoken()){
			    					      senderservice.sendPushNotification(notification.getPushnotiftoken(), message);
			    					   }
			    					 }
			    					 Notification notif = new Notification(car.getDeviceid().toString(), message);
			    					 notificationservice.addNotification(notif);
			    					 isceuta = true;
	                 		   }
	                 		   if(!ismelilia && null != location && isInMelilea(location.getLatitude(), location.getLongitude())){
	                 			     String message = "Sortie de territoire (melilia) : "+car.getMark()+" "+car.getModel()+" "+car.getColor()+" ("+car.getImmatriculation()+")";
			    					 for(int v=0; v<notifications.size(); v++){
			    					   Notification notification = notifications.get(v);
			    					   if(null != notification && null != notification.getPushnotiftoken()){
			    					      senderservice.sendPushNotification(notification.getPushnotiftoken(), message);
			    					   }
			    					 }
			    					 Notification notif = new Notification(car.getDeviceid().toString(), message);
			    					 notificationservice.addNotification(notif);
			    					 ismelilia = true;
	                 		   }
	                 		   if(!isalgerie && null != location && isInAlgerie(location.getLatitude(), location.getLongitude())){
                 			     String message = "Sortie de territoire (algerie) : "+car.getMark()+" "+car.getModel()+" "+car.getColor()+" ("+car.getImmatriculation()+")";
		    					 for(int v=0; v<notifications.size(); v++){
		    					   Notification notification = notifications.get(v);
		    					   if(null != notification && null != notification.getPushnotiftoken()){
		    					      senderservice.sendPushNotification(notification.getPushnotiftoken(), message);
		    					   }
		    					 }
		    					 Notification notif = new Notification(car.getDeviceid().toString(), message);
		    					 notificationservice.addNotification(notif);
		    					 isalgerie = true;
                 		      }
	                 		  if(!ismauritanie && null != location && isInMauritanie(location.getLatitude(), location.getLongitude())){
                			     String message = "Sortie de territoire (mauritanie) : "+car.getMark()+" "+car.getModel()+" "+car.getColor()+" ("+car.getImmatriculation()+")";
		    					 for(int v=0; v<notifications.size(); v++){
		    					   Notification notification = notifications.get(v);
		    					   if(null != notification && null != notification.getPushnotiftoken()){
		    					      senderservice.sendPushNotification(notification.getPushnotiftoken(), message);
		    					   }
		    					  }
		    					  Notification notif = new Notification(car.getDeviceid().toString(), message);
		    					  notificationservice.addNotification(notif);
		    					  ismauritanie = true;
                		        }
	                 	      }
    			          }
    			       }
    	            }
                 }

    }
	
	@Scheduled(fixedDelay = 3600000)
    public void inoutzoneNotification() throws IOException, ParseException {
        List<Profile> profiles = profileservice.getAllProfiles();
        Calendar calendar = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    calendar.add(Calendar.HOUR, -1);
	    Date d2 = calendar.getTime();
        String date = sdf.format(d2);
        for(int i=0; i < profiles.size(); i++){
 	       Profile profile = profiles.get(i);
 	       System.out.println("Profile --> " + profile);
 	       if(null != profile && null != profile.getLogin()){
 		     List<Notification> notifications = notificationservice.getPushTokenByProfile(profile.getLogin());
 		     List<Car> cars = carservice.getAllCarsByProfile(profile.getLogin());
 		     for(int j=0; j<cars.size(); j++){
 			   Car car = cars.get(j);
 			   if(null != car)
 			     {						
	              List<Location> locations = carservice.getAllLocationsByCar(car.getDeviceid(), date,profile.getToken());
	              for(int k=0 ; k<locations.size() ; k++){
	                 Location location = locations.get(k);
	                 if(isInZone(car, location.getLatitude(), location.getLongitude()) == false){
	                	 if((null == car.getInzone() || (null != car.getInzone() && car.getInzone() == true)) && null != car.getNotifoutzone() && car.getNotifoutzone() == true){
	                	    String message = "Sortie de zone virtuelle : "+car.getMark()+" "+car.getModel()+" "+car.getColor()+" ("+car.getImmatriculation()+")";
	                	    for(int v=0; v<notifications.size(); v++){
	    					   Notification notification = notifications.get(v);
	    					   if(null != notification && null != notification.getPushnotiftoken()){
	    					      senderservice.sendPushNotification(notification.getPushnotiftoken(), message);
	    					   }
	    				    }
	    				    Notification notif = new Notification(car.getDeviceid().toString(), message);
	    				    notificationservice.addNotification(notif);
	                	 }
	    				 car.setInzone(false);
	    				 carservice.updateCar(car);
	                     break;
	                 }
	                 
	                 if(isInZone(car, location.getLatitude(), location.getLongitude()) == true){
	                	 if((null == car.getInzone() || (null != car.getInzone() && car.getInzone() == false))  && null != car.getNotifinzone() && car.getNotifinzone() == true){
	                	   String message = "Entrée en zone virtuelle : "+car.getMark()+" "+car.getModel()+" "+car.getColor()+" ("+car.getImmatriculation()+")";
	                	   for(int v=0; v<notifications.size(); v++){
	    					   Notification notification = notifications.get(v);
	    					   if(null != notification && null != notification.getPushnotiftoken()){
	    					      senderservice.sendPushNotification(notification.getPushnotiftoken(), message);
	    					   }
	    				   }
	    				   Notification notif = new Notification(car.getDeviceid().toString(), message);
	    				   notificationservice.addNotification(notif);
	                	 }
	    				 car.setInzone(true);
	    				 carservice.updateCar(car);
	                     break;
	                 }
 			       }
 			     }
 		      }
 		   }
 	   }
    }

	@Scheduled(fixedDelay = 900000)
    public void executeStopEngine() throws IOException, ParseException {
      List<Profile> profiles = profileservice.getAllProfiles();
      Calendar calendar = Calendar.getInstance();
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  calendar.add(Calendar.MINUTE, -15);
	  Date d2 = calendar.getTime();
      String date = sdf.format(d2);
      int status ;
      for(int i=0; i < profiles.size(); i++){
	       Profile profile = profiles.get(i);
	       System.out.println("Profile --> " + profile);
	       if(null != profile && null != profile.getLogin()){
		     List<Notification> notifications = notificationservice.getPushTokenByProfile(profile.getLogin());
		     List<Car> cars = carservice.getAllCarsByProfile(profile.getLogin());
		     for(int j=0; j<cars.size(); j++){
			   Car car = cars.get(j);
			   if(null != car && car.getStatus() == 2)
			     {						
				   Location location = carservice.getLastLocationByCar(car.getDeviceid(),date);
	               if(car.getSpeed() < 10){
	                  String message = "Voiture arretée : "+car.getMark()+" "+car.getModel()+" "+car.getColor()+" ("+car.getImmatriculation()+")";
	                  if(car.getDevicetype().equals("TK103")){
	          			status = senderservice.sendSms("KriAuto.ma", car.getSimnumber(), "stop135791");
	          	      }else{
	          	    	status = senderservice.sendSms("KriAuto.ma", car.getSimnumber(), "kauto 13579 setdigout 00");
	          	      }
	                  for(int v=0; v<notifications.size(); v++){
	    					   Notification notification = notifications.get(v);
	    					   if(null != notification && null != notification.getPushnotiftoken()){
	    					      senderservice.sendPushNotification(notification.getPushnotiftoken(), message);
	    					   }
	    			  }
	                  car.setStatus(1);
	             	  carservice.updateCar(car);
	    			  Notification notif = new Notification(car.getDeviceid().toString(), message);
	    			  notificationservice.addNotification(notif);
			       }
			     }
		      }
		   }
	   }
    }
	
	/**** Disconnected device Notifications 
	 * @throws ParseException ***/
	@Scheduled(fixedDelay = 300000)
    public void isDisconnectedNotifications() throws IOException, ParseException {
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   Date start = new Date();
	   Date start1 = sdf.parse(sdf.format(start));
       Calendar calendar = Calendar.getInstance();
	   calendar.add(Calendar.MINUTE, -5);
	   Date d2 = calendar.getTime();
       String date = sdf.format(d2);
       List<Profile> profiles = profileservice.getAllProfiles();
       for(int i=0; i < profiles.size(); i++){
    	   Profile profile = profiles.get(i);
    	   System.out.println("Profile --> " + profile);
    	   if(null != profile && null != profile.getLogin()){
    		   List<Notification> notifications = notificationservice.getPushTokenByProfile(profile.getLogin());
    		   List<Car> cars = carservice.getAllCarsByProfile(profile.getLogin());
    		   for(int j=0; j<cars.size(); j++){
    			   Car car = cars.get(j);
    			   if(null != car && carservice.isDeviceDisconnected(car.getDeviceid(), date)){
		    					 String message = "Boitier Gps débranchée ou batterie morte pour la voiture : "+car.getMark()+" "+car.getModel()+" "+car.getColor()+" ("+car.getImmatriculation()+")";
		    					 for(int k=0; k<notifications.size(); k++){
		    					   Notification notification = notifications.get(k);
		    					   if(null != notification && null != notification.getPushnotiftoken()){
		    					      senderservice.sendPushNotification(notification.getPushnotiftoken(), message);
		    					   }
		    					 }
		    					 Notification notif = new Notification(car.getDeviceid().toString(), message);
		    					 notificationservice.addNotification(notif);
		    		}
    			 }
    		  }
         }
       Date end = new Date();
	   Date end1 = sdf.parse(sdf.format(end));
	   long diffInMillies = end1.getTime() - start1.getTime();
	   long diff = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	   System.out.println("diffInMillies --> " + diffInMillies+"diff --> "+diff);
    }
	
	public boolean isInZone(Car car, double lat, double lon) {
		int j=0;
        boolean inBound = false;
        double x = lon;
        double y = lat;
        if(null != car.getLatitude1() && null != car.getLongitude1() && null != car.getLatitude2() && null != car.getLongitude2() && null != car.getLatitude3() && null != car.getLongitude3() && null != car.getLatitude4() && null != car.getLongitude4() && null != car.getLatitude5() && null != car.getLongitude5() && null != car.getLatitude6() && null != car.getLongitude6()){
         double zone[][]  = {{car.getLatitude1(),car.getLongitude1()},{car.getLatitude2(),car.getLongitude2()},{car.getLatitude3(),car.getLongitude3()},{car.getLatitude4(),car.getLongitude4()},{car.getLatitude5(),car.getLongitude5()},{car.getLatitude6(),car.getLongitude6()}};
         for (int i=0; i < 4 ; i++) {
          j++;
          if (j == 4) {j = 0;}
          if (((zone[i][0] < y) && (zone[j][0]  >= y)) || ((zone[j][0] < y) && (zone[i][0] >= y))) {
            if ( zone[i][1] + (y - zone[i][0])/(zone[j][0]-zone[i][0])*(zone[j][1] - zone[i][1])<x ) 
               {
            	inBound = !inBound;
               }
            }
         } 
        }
	    return inBound;
	}
	
	public boolean isInCeuta(double lat, double lon) {
		int j=0;
        boolean inBound = false;
        double x = lon;
        double y = lat;
        double ceuta[][]  = {{35.912663,-5.382453},{35.896116,-5.378333},{35.880818,-5.371639},{35.868856,-5.344344},{35.899315,-5.261947},{35.933793,-5.379192}};
        for (int i=0; i < 4 ; i++) {
          j++;
          if (j == 4) {j = 0;}
          if (((ceuta[i][0] < y) && (ceuta[j][0]  >= y)) || ((ceuta[j][0] < y) && (ceuta[i][0] >= y))) {
            if ( ceuta[i][1] + (y - ceuta[i][0])/(ceuta[j][0]-ceuta[i][0])*(ceuta[j][1] - ceuta[i][1])<x ) 
               {
            	inBound = !inBound;
               }
            }
        }
	    return inBound;
	}
	
	public boolean isInMauritanie(double lat, double lon) {
		int j=0;
        boolean inBound = false;
        double x = lon;
        double y = lat;
        double ceuta[][]  = {{21.333039,-13.014105},{21.333039,-16.940144},{20.784382,-17.064262},{21.284352,-16.914014},{21.284352,-13.014105}};
        for (int i=0; i < 4 ; i++) {
          j++;
          if (j == 4) {j = 0;}
          if (((ceuta[i][0] < y) && (ceuta[j][0]  >= y)) || ((ceuta[j][0] < y) && (ceuta[i][0] >= y))) {
            if ( ceuta[i][1] + (y - ceuta[i][0])/(ceuta[j][0]-ceuta[i][0])*(ceuta[j][1] - ceuta[i][1])<x ) 
               {
            	inBound = !inBound;
               }
            }
        }
	    return inBound;
	}
	
	public boolean isInMelilea(double lat, double lon) {
		int j=0;
        boolean inBound = false;
        double x = lon;
        double y = lat;
        double ceuta[][]  = {{35.319974,-2.952852},{35.316266,-2.960067},{35.288948,-2.970539},{35.265965,-2.950454},{35.271992,-2.929511},{35.295818,-2.913552}};
        for (int i=0; i < 4 ; i++) {
          j++;
          if (j == 4) {j = 0;}
          if (((ceuta[i][0] < y) && (ceuta[j][0]  >= y)) || ((ceuta[j][0] < y) && (ceuta[i][0] >= y))) {
            if ( ceuta[i][1] + (y - ceuta[i][0])/(ceuta[j][0]-ceuta[i][0])*(ceuta[j][1] - ceuta[i][1])<x ) 
               {
            	inBound = !inBound;
               }
            }
        }
	    return inBound;
	}
	
	public boolean isInAlgerie(double lat, double lon) {
		int j=0;
        boolean inBound = false;
        double x = lon;
        double y = lat;
        double ceuta[][]  = {{34.936012,-1.973600},{34.879471,-1.972144},{34.842021,-1.893973},{34.806529,-1.888122},{34.802300,-1.859627},{34.743703,-1.739356},{34.855287, -1.860319}};
        for (int i=0; i < 4 ; i++) {
          j++;
          if (j == 4) {j = 0;}
          if (((ceuta[i][0] < y) && (ceuta[j][0]  >= y)) || ((ceuta[j][0] < y) && (ceuta[i][0] >= y))) {
            if ( ceuta[i][1] + (y - ceuta[i][0])/(ceuta[j][0]-ceuta[i][0])*(ceuta[j][1] - ceuta[i][1])<x ) 
               {
            	inBound = !inBound;
               }
            }
        }
	    return inBound;
	}
}
