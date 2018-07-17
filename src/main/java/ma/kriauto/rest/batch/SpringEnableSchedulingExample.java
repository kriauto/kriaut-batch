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
import ma.kriauto.rest.domain.Notification;
import ma.kriauto.rest.domain.Profile;
import ma.kriauto.rest.domain.Speed;
import ma.kriauto.rest.domain.Statistic;
import ma.kriauto.rest.service.CarService;
import ma.kriauto.rest.service.NotificationService;
import ma.kriauto.rest.service.ProfileService;
import ma.kriauto.rest.service.SenderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
 
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
	
	/**** Empting kilometre * @throws ParseException ***/
	@Scheduled(cron = "00 00 01 * * *")
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
                        for(int k=1; k<=1; k++){
                        	calendar = Calendar.getInstance();
                        	calendar.add(Calendar.DATE, -k);
                        	String date = sdf.format(calendar.getTime());
                        	Statistic statistic = carservice.getCarStatistic(deviceid, date, token);
                        	if(null != statistic && null != statistic.getCourse()){
                    		Car currentcar = carservice.getCarByDevice(deviceid, token);
                    		currentcar.setTotaldistance(Double.valueOf(Math.round(statistic.getCourse()+currentcar.getTotaldistance())));
                    		currentcar.setEmptyingtotaldistance(Double.valueOf(Math.round(statistic.getCourse()+currentcar.getEmptyingtotaldistance())));
                    		carservice.updateCar(currentcar);
                        	}
                        }
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
    			   if(null != car && null != car.getTechnicalcontroldate() && true == car.getNotiftechnicalcontroldate()){
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
    			   if(null != car && null != car.getInsuranceenddate() && true == car.getNotifinsuranceenddate()){
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
    			   if(null != car && null != car.getAutorisationcirculationenddate() && true == car.getNotifautorisationcirculationenddate()){
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
       for(int i=0; i < profiles.size(); i++){
    	   Profile profile = profiles.get(i);
    	   System.out.println("Profile --> " + profile);
    	   if(null != profile && null != profile.getLogin()){
    		   List<Notification> notifications = notificationservice.getPushTokenByProfile(profile.getLogin());
    		   List<Car> cars = carservice.getAllCarsByProfile(profile.getLogin());
    		   for(int j=0; j<cars.size(); j++){
    			   Car car = cars.get(j);
    			   if(null != car && null != car.getMaxspeed() && true == car.getNotifmaxspeed()){
							 Calendar calendar = Calendar.getInstance();
						     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH24:MM:SS");
						     calendar.add(Calendar.MINUTE, -60);
	                         String date = sdf.format(calendar.getTime());
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
    			   if(null != car && null != car.getMaxcourse() && true == car.getNotifmaxcourse() && null != profile && null != profile.getToken()){
							 Calendar calendar = Calendar.getInstance();
						     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						     //calendar.add(Calendar.DATE, -35);
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
 
//	@Scheduled(fixedDelay = 60000)
//    public void executeStopEngine() {
//        System.out.println("Start Start/Stop Job " + new Date());
//        List<Notification> notifs = carservice.getDataNotification(1);
//        for(int i =0; i < notifs.size() ; i++){
//        	Notification notif = notifs.get(i);
//        	Location location = carservice.getLastLocationByCar(notif.getDeviceid());
//        	if(null != location && location.getSpeed() <= 10){
//        		String message = "voiture arrete "+notif.getMark()+notif.getModel()+notif.getColor()+notif.getImmatriculation();
//        		senderservice.sendSms("KriAuto.ma", notif.getSimnumber(), "stop135791");
//        		try {
//					Thread.sleep(10000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//        		senderservice.sendSms("KriAuto.ma", notif.getPhone(), message);
//        		Car car = carservice.getCarByDevice(notif.getDeviceid());
//        		car.setStatus(1);
//        		carservice.updateCar(car);
//        		notif.setTexte(message);
//        		notificationservice.addNotification(notif);
//        		System.out.println(message);
//        	}
//        }
//        System.out.println("End Start/Stop Job " + new Date());
//    }
//	
////	@Scheduled(fixedDelay = 120000)
////    public void executeDefaultGeoFence() {
////        System.out.println("Start Sortie Territoire Job "+new Date());
////        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////        Calendar now1 = Calendar.getInstance();
////	    now1.add(Calendar.DAY_OF_YEAR, -120);
////	    Date now2 = now1.getTime(); 
////	    String time = df.format(now2);
////        List<Notification> notifs = carservice.getDataNotification(3);
////        for(int i =0; i < notifs.size() ; i++){
////        	Notification notif = notifs.get(i);
////        	List<Location> locations = carservice.getAllLocationByCarTime(notif.getDeviceid(), time);
////        	for(int j=0 ; j<locations.size() ; j++){
////        		Location location = locations.get(j);
////        		if(isInCeuta(location.getLatitude(), location.getLongitude())){
////        			String message = "La"+notif.getMark()+notif.getModel()+notif.getColor()+notif.getImmatriculation()+"est+a+ceuta";
////        			senderservice.sendSms("KriAuto.ma", notif.getPhone(), message);
////            		Car car = carservice.getCarByDevice(notif.getDeviceid());
////            		car.setIsnotifdefaultgeofence(true);
////            		carservice.updateCar(car);
////            		notif.setTexte(message);
////            		notificationservice.addNotification(notif);
////            		System.out.println(message);
////            		break;
////        		}
////        		if(isInMelilea(location.getLatitude(), location.getLongitude())){
////        			String message = "La"+notif.getMark()+notif.getModel()+notif.getColor()+notif.getImmatriculation()+"est+a+melilia";
////        			senderservice.sendSms("KriAuto.ma", notif.getPhone(), message);
////            		Car car = carservice.getCarByDevice(notif.getDeviceid());
////            		car.setIsnotifdefaultgeofence(true);
////            		carservice.updateCar(car);
////            		notif.setTexte(message);
////            		notificationservice.addNotification(notif);
////            		System.out.println(message);
////            		break;
////        		}
////        		if(isInAlgerie(location.getLatitude(), location.getLongitude())){
////        			String message = "La"+notif.getMark()+notif.getModel()+notif.getColor()+notif.getImmatriculation()+"est+en+algerie";
////        			senderservice.sendSms("KriAuto.ma", notif.getPhone(), message);
////            		Car car = carservice.getCarByDevice(notif.getDeviceid());
////            		car.setIsnotifdefaultgeofence(true);
////            		carservice.updateCar(car);
////            		notif.setTexte(message);
////            		notificationservice.addNotification(notif);
////            		System.out.println(message);
////            		break;
////        		}
////        		if(isInMauritanie(location.getLatitude(), location.getLongitude())){
////        			String message = "La"+notif.getMark()+notif.getModel()+notif.getColor()+notif.getImmatriculation()+"est+en+mauritanie";
////        			senderservice.sendSms("KriAuto.ma", notif.getPhone(), message);
////            		Car car = carservice.getCarByDevice(notif.getDeviceid());
////            		car.setIsnotifdefaultgeofence(true);
////            		carservice.updateCar(car);
////            		notif.setTexte(message);
////            		notificationservice.addNotification(notif);
////            		System.out.println(message);
////            		break;
////        		}
////        	}
////        }
////        System.out.println("End Sortie Territoire Job " + new Date());
////    }
////	
////	@Scheduled(fixedDelay = 180000)
////    public void executeGeoFence() {
////        System.out.println("Start Sortie Zone Job "+new Date());
////        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////        Calendar now1 = Calendar.getInstance();
////	    now1.add(Calendar.DAY_OF_YEAR, -120);
////	    Date now2 = now1.getTime();
////	    String time = df.format(now2);
////        List<Notification> notifs = carservice.getDataNotification(2);
////        for(int i =0; i < notifs.size() ; i++){
////        	Notification notif = notifs.get(i);
////        	List<Location> locations = carservice.getAllLocationByCarTime(notif.getDeviceid(), time);
////        	for(int j=0 ; j<locations.size() ; j++){
////        		Location location = locations.get(j);
////        		if(!isInZone(notif, location.getLatitude(), location.getLongitude())){
////        			String message = "La"+notif.getMark()+notif.getModel()+notif.getColor()+notif.getImmatriculation()+"a+quitter+la+zone+virtuelle";
////        			senderservice.sendSms("KriAuto.ma", notif.getPhone(), message);
////            		Car car = carservice.getCarByDevice(notif.getDeviceid());
////            		car.setIsnotifgeofence(true);
////            		carservice.updateCar(car);
////            		notif.setTexte(message);
////            		notificationservice.addNotification(notif);
////            		System.out.println(message);
////            		break;
////        		}
////        	}
////        }
////        System.out.println("End Sortie Zone Job " +new Date());
////    }
////	
////	@Scheduled(cron = "59 59 23 * * *")
////    public void executeInitGeFence() {
////        System.out.println("Start Init GeFence " + new Date());
////        carservice.initGeoFence();
////        System.out.println("End Init GeFence " + new Date());
////    }
////	
////	public boolean isInZone(Notification notif, double lat, double lon) {
////		int j=0;
////        boolean inBound = false;
////        double x = lon;
////        double y = lat;
////        if(null != notif.getLatitude1() && null != notif.getLongitude1() && null != notif.getLatitude2() && null != notif.getLongitude2() && null != notif.getLatitude3() && null != notif.getLongitude3() && null != notif.getLatitude4() && null != notif.getLongitude4() && null != notif.getLatitude5() && null != notif.getLongitude5() && null != notif.getLatitude6() && null != notif.getLongitude6()){
////         double zone[][]  = {{notif.getLatitude1(),notif.getLongitude1()},{notif.getLatitude2(),notif.getLongitude2()},{notif.getLatitude3(),notif.getLongitude3()},{notif.getLatitude4(),notif.getLongitude4()},{notif.getLatitude5(),notif.getLongitude5()},{notif.getLatitude6(),notif.getLongitude6()}};
////         for (int i=0; i < 4 ; i++) {
////          j++;
////          if (j == 4) {j = 0;}
////          if (((zone[i][0] < y) && (zone[j][0]  >= y)) || ((zone[j][0] < y) && (zone[i][0] >= y))) {
////            if ( zone[i][1] + (y - zone[i][0])/(zone[j][0]-zone[i][0])*(zone[j][1] - zone[i][1])<x ) 
////               {
////            	inBound = !inBound;
////               }
////            }
////         } 
////        }
////	    return inBound;
////	}
////	
////	public boolean isInCeuta(double lat, double lon) {
////		int j=0;
////        boolean inBound = false;
////        double x = lon;
////        double y = lat;
////        double ceuta[][]  = {{35.912663,-5.382453},{35.896116,-5.378333},{35.880818,-5.371639},{35.868856,-5.344344},{35.899315,-5.261947},{35.933793,-5.379192}};
////        for (int i=0; i < 4 ; i++) {
////          j++;
////          if (j == 4) {j = 0;}
////          if (((ceuta[i][0] < y) && (ceuta[j][0]  >= y)) || ((ceuta[j][0] < y) && (ceuta[i][0] >= y))) {
////            if ( ceuta[i][1] + (y - ceuta[i][0])/(ceuta[j][0]-ceuta[i][0])*(ceuta[j][1] - ceuta[i][1])<x ) 
////               {
////            	inBound = !inBound;
////               }
////            }
////        }
////	    return inBound;
////	}
////	
////	public boolean isInMauritanie(double lat, double lon) {
////		int j=0;
////        boolean inBound = false;
////        double x = lon;
////        double y = lat;
////        double ceuta[][]  = {{21.333039,-13.014105},{21.333039,-16.940144},{20.784382,-17.064262},{21.284352,-16.914014},{21.284352,-13.014105}};
////        for (int i=0; i < 4 ; i++) {
////          j++;
////          if (j == 4) {j = 0;}
////          if (((ceuta[i][0] < y) && (ceuta[j][0]  >= y)) || ((ceuta[j][0] < y) && (ceuta[i][0] >= y))) {
////            if ( ceuta[i][1] + (y - ceuta[i][0])/(ceuta[j][0]-ceuta[i][0])*(ceuta[j][1] - ceuta[i][1])<x ) 
////               {
////            	inBound = !inBound;
////               }
////            }
////        }
////	    return inBound;
////	}
////	
////	public boolean isInMelilea(double lat, double lon) {
////		int j=0;
////        boolean inBound = false;
////        double x = lon;
////        double y = lat;
////        double ceuta[][]  = {{35.319974,-2.952852},{35.316266,-2.960067},{35.288948,-2.970539},{35.265965,-2.950454},{35.271992,-2.929511},{35.295818,-2.913552}};
////        for (int i=0; i < 4 ; i++) {
////          j++;
////          if (j == 4) {j = 0;}
////          if (((ceuta[i][0] < y) && (ceuta[j][0]  >= y)) || ((ceuta[j][0] < y) && (ceuta[i][0] >= y))) {
////            if ( ceuta[i][1] + (y - ceuta[i][0])/(ceuta[j][0]-ceuta[i][0])*(ceuta[j][1] - ceuta[i][1])<x ) 
////               {
////            	inBound = !inBound;
////               }
////            }
////        }
////	    return inBound;
////	}
////	
////	public boolean isInAlgerie(double lat, double lon) {
////		int j=0;
////        boolean inBound = false;
////        double x = lon;
////        double y = lat;
////        double ceuta[][]  = {{34.936012,-1.973600},{34.879471,-1.972144},{34.842021,-1.893973},{34.806529,-1.888122},{34.802300,-1.859627},{34.743703,-1.739356},{34.855287, -1.860319}};
////        for (int i=0; i < 4 ; i++) {
////          j++;
////          if (j == 4) {j = 0;}
////          if (((ceuta[i][0] < y) && (ceuta[j][0]  >= y)) || ((ceuta[j][0] < y) && (ceuta[i][0] >= y))) {
////            if ( ceuta[i][1] + (y - ceuta[i][0])/(ceuta[j][0]-ceuta[i][0])*(ceuta[j][1] - ceuta[i][1])<x ) 
////               {
////            	inBound = !inBound;
////               }
////            }
////        }
////	    return inBound;
////	}
}
