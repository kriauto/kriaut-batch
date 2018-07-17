package ma.kriauto.rest.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ma.kriauto.rest.domain.Car;
import ma.kriauto.rest.domain.Consumption;
import ma.kriauto.rest.domain.Course;
import ma.kriauto.rest.domain.Device;
import ma.kriauto.rest.domain.Item;
import ma.kriauto.rest.domain.Location;
import ma.kriauto.rest.domain.Notification;
import ma.kriauto.rest.domain.Profile;
import ma.kriauto.rest.domain.ResponseMessage;
import ma.kriauto.rest.domain.Search;
import ma.kriauto.rest.domain.Speed;
import ma.kriauto.rest.domain.Statistic;
import ma.kriauto.rest.service.CarService;
import ma.kriauto.rest.service.NotificationService;
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
 * Controller for car actions.
 */
@Controller
public class CarController {
	
	@Autowired
    private CarService carService;
	
	@Autowired
    private SenderService senderService;
	
	@Autowired
    private ProfileService profileService;
	
	@Autowired
    private NotificationService notificationService;
	
	@RequestMapping(value = "/getCars", method = RequestMethod.POST)
    @ResponseBody
    public List<Car> getAllCarsByToken(@RequestHeader(value="Authorization") String authorization, @RequestBody Boolean isgroup) {
    	System.out.println("Begin getAllCarsByToken -->"+authorization);
    	List<Car> cars = new ArrayList<Car>();
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token);
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	cars = carService.getAllCarsByToken(isgroup, token);
    	return cars;
    }
	
	@RequestMapping(value = "/getCarByDevice", method = RequestMethod.POST)
    @ResponseBody
    public Car getCarByDevice(@RequestHeader(value="Authorization") String authorization, @RequestBody Integer deviceid) {
    	System.out.println("Begin getCarByDevice -->"+deviceid);
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token);
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	Car car = carService.getCarByDevice(deviceid,token);
    	return car;
    }
	
	@RequestMapping(value = "/updateMaintenance", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage updateMaintenance(@RequestHeader(value="Authorization") String authorization, @RequestBody Car car) throws ParseException {
    	System.out.println("Begin updateCar -->"+car);
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token);
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	Car cartmp = carService.getCarByDevice(car.getDeviceid(),token);
    	if(null != cartmp){
    	  if(null != car && null != car.getTechnicalcontroldate()){
    		if(isThisDateValid(car.getTechnicalcontroldate(), "yyyy-MM-dd")){
    		  cartmp.setTechnicalcontroldate(car.getTechnicalcontroldate());
    	   }else{
    		  throw new IllegalArgumentException("TECHDATE_FAILED");
    	   }
    		cartmp.setNotiftechnicalcontroldate(car.getNotiftechnicalcontroldate());
    	 }
    	 if(null != car && null != car.getEmptyingkilometre()){
    		if(car.getEmptyingkilometre() > 0 && (car.getEmptyingkilometre() != cartmp.getEmptyingkilometre())){
    			cartmp.setEmptyingkilometre(car.getEmptyingkilometre());
    			cartmp.setEmptyingtotaldistance(0.0);
    			cartmp.setEmptyingkilometreindex(1);
    	   }else{
    		  throw new IllegalArgumentException("EMPKILO_FAILED");
    	   }
    		cartmp.setNotifemptyingkilometre(car.getNotifemptyingkilometre());
    	 }    	
    	 if(null != car && null != car.getInsuranceenddate()){
    		if(isThisDateValid(car.getInsuranceenddate(), "yyyy-MM-dd")){
    			cartmp.setInsuranceenddate(car.getInsuranceenddate());
    	   }else{
    		  throw new IllegalArgumentException("INSDATE_FAILED");
    	   }
    		cartmp.setNotifinsuranceenddate(car.getNotifinsuranceenddate());
    	 }   	
    	 if(null != car && null != car.getAutorisationcirculationenddate()){
    		if(isThisDateValid(car.getAutorisationcirculationenddate(), "yyyy-MM-dd")){
    			cartmp.setAutorisationcirculationenddate(car.getAutorisationcirculationenddate());
    	   }else{
    		  throw new IllegalArgumentException("CIRDATE_FAILED");
    	   }
    	   cartmp.setNotifautorisationcirculationenddate(car.getNotifautorisationcirculationenddate());
    	 }   	
    	 carService.updateCar(cartmp);
    	}else{
    		throw new IllegalArgumentException("CAR_NOTFOUND");
    	}
    	return new ResponseMessage(ResponseMessage.Type.success, "GEOFENCE_SUCCES",Constant.getLabels().get("GEOFENCE_SUCCES").toString());
    }
	
	@RequestMapping(value = "/updateDaily", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage updateDaily(@RequestHeader(value="Authorization") String authorization, @RequestBody Car car) throws ParseException {
    	System.out.println("Begin updateCar -->"+car);
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token);
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	Car cartmp = carService.getCarByDevice(car.getDeviceid(),token);
    	if(null != cartmp){
    	if(null != car && null != car.getEmptyingkilometre()){
    		if(car.getEmptyingkilometre() > 0){
    			cartmp.setMaxspeed(car.getMaxspeed());
    	   }else{
    		  throw new IllegalArgumentException("SPEED_FAILED");
    	   }
    	   cartmp.setNotifmaxspeed(car.getNotifmaxspeed());
    	}  
    	if(null != car && null != car.getMaxcourse()){
    		if(car.getMaxcourse() > 0){
    			cartmp.setMaxcourse(car.getMaxcourse());
    	   }else{
    		  throw new IllegalArgumentException("COURSE_FAILED");
    	   }
    	   cartmp.setNotifmaxcourse(car.getNotifmaxcourse());
    	} 
    	if(null != car && null != car.getMinlevelfuel()){
    		if(car.getMinlevelfuel() > 0){
    			cartmp.setMinlevelfuel(car.getMinlevelfuel());
    	   }else{
    		  throw new IllegalArgumentException("FUEL_FAILED");
    	   }
    	   cartmp.setNotifminlevelfuel(car.getNotifminlevelfuel());
    	}
    	if(null != car && null != car.getMaxenginetemperature()){
    		if(car.getMaxenginetemperature() > 0){
    			cartmp.setMaxenginetemperature(car.getMaxenginetemperature());
    	   }else{
    		  throw new IllegalArgumentException("TEMENGINE_FAILED");
    	   }
    	   cartmp.setNotifmaxenginetemperature(car.getNotifmaxenginetemperature());
    	}  
    	if(null != car && null != car.getMaxfridgetemperature()){
    		if(car.getMaxfridgetemperature() > 0){
    			cartmp.setMaxfridgetemperature(car.getMaxfridgetemperature());
    	   }else{
    		  throw new IllegalArgumentException("FRIDGEMAX_FAILED");
    	   }
    	   cartmp.setNotifmaxfridgetemperature(car.getNotifmaxfridgetemperature());
    	}  
    	if(null != car && null != car.getMinfridgetemperature()){
    		if(car.getMinfridgetemperature() > -100){
    			cartmp.setMinfridgetemperature(car.getMinfridgetemperature());
    	   }else{
    		  throw new IllegalArgumentException("FRIDGEMIN_FAILED");
    	   }
    	   cartmp.setNotifminfridgetemperature(car.getNotifminfridgetemperature());
    	}  
    	carService.updateCar(car);
    	}else{
    		throw new IllegalArgumentException("CAR_NOTFOUND");
    	}
    	return new ResponseMessage(ResponseMessage.Type.success, "GEOFENCE_SUCCES",Constant.getLabels().get("GEOFENCE_SUCCES").toString());
    }
	
	@RequestMapping(value = "/updateGeofence", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage updateGeofence(@RequestHeader(value="Authorization") String authorization, @RequestBody Car car) throws ParseException {
    	System.out.println("Begin updateCar -->"+car);
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token);
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	Car cartmp = carService.getCarByDevice(car.getDeviceid(),token);
    	if(null != cartmp){
    	 if(null != car){
    	   cartmp.setLongitude1(car.getLongitude1());
    	   cartmp.setLatitude1(car.getLatitude1());
    	   cartmp.setLongitude2(car.getLongitude2());
    	   cartmp.setLatitude2(car.getLatitude2());
    	   cartmp.setLongitude3(car.getLongitude3());
    	   cartmp.setLatitude3(car.getLatitude3());
    	   cartmp.setLongitude4(car.getLongitude4());
    	   cartmp.setLatitude4(car.getLatitude4());
    	   cartmp.setLongitude5(car.getLongitude5());
    	   cartmp.setLatitude5(car.getLatitude5());
    	   cartmp.setLongitude6(car.getLongitude6());
    	   cartmp.setLatitude6(car.getLatitude6());
    	   cartmp.setNotifinzone(car.getNotifinzone());
    	   cartmp.setNotifoutzone(car.getNotifoutzone());
    	 }
    	 carService.updateCar(car);
    	}else{
    		throw new IllegalArgumentException("CAR_NOTFOUND");
    	}
    	return new ResponseMessage(ResponseMessage.Type.success, "GEOFENCE_SUCCES",Constant.getLabels().get("GEOFENCE_SUCCES").toString());
    }
	
	@RequestMapping(value = "/updateCar", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage updateCar(@RequestHeader(value="Authorization") String authorization, @RequestBody Car car) throws ParseException {
    	System.out.println("Begin updateCar -->"+car);
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token);
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	carService.updateCar(car);
    	return new ResponseMessage(ResponseMessage.Type.success, "GEOFENCE_SUCCES",Constant.getLabels().get("GEOFENCE_SUCCES").toString());
    }
	
	@RequestMapping(value = "/getDates", method = RequestMethod.POST)
    @ResponseBody
    public List<Item> getDates(@RequestHeader(value="Authorization") String authorization, @RequestBody Integer deviceid) {
    	System.out.println("Begin getDates -->"+deviceid);
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token);
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	List<Item> dates = new ArrayList<Item>();
    	if("111111".equals(String.valueOf(deviceid))){
    		dates = carService.getAllDatesByToken(token);
    	}else{
    		dates = carService.getAllDatesByCar(deviceid);
    	}
    	return dates;
    }
	
	@RequestMapping(value = "/getTotalCours", method = RequestMethod.POST)
    @ResponseBody
    public List<Course> getTotalCours(@RequestHeader(value="Authorization") String authorization, @RequestBody Integer deviceid) {
    	System.out.println("Begin getTotalCours -->"+deviceid);
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token);
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	List<Course> cours = new ArrayList<Course>();
    	cours = carService.getTotalCourseByCar(deviceid);
    	return cours;
    }
	
	@RequestMapping(value = "/getMaxSpeed", method = RequestMethod.POST)
    @ResponseBody
    public List<Speed> getMaxSpeed(@RequestHeader(value="Authorization") String authorization, @RequestBody Integer deviceid) {
    	System.out.println("Begin getMaxSpeed -->"+deviceid);
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token);
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	List<Speed> speed = new ArrayList<Speed>();
    	speed = carService.getMaxSpeedByCar(deviceid);
    	return speed;
    }
	
	@RequestMapping(value = "/getConsumption", method = RequestMethod.POST)
    @ResponseBody
    public List<Consumption> getConsumption(@RequestHeader(value="Authorization") String authorization, @RequestBody Integer deviceid) {
    	System.out.println("Begin getConsumption -->"+deviceid);
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token);
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	List<Consumption> consumption = new ArrayList<Consumption>();
    	consumption = carService.getTotalConsumptionByCar(deviceid);
    	return consumption;
    }
	
	@RequestMapping(value = "/getNotification", method = RequestMethod.POST)
    @ResponseBody
    public List<Notification> getNotification(@RequestHeader(value="Authorization") String authorization, @RequestBody Notification notification) {
    	System.out.println("Begin getNotification -->");
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token);
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	List<Notification> notifications = new ArrayList<Notification>();
    	notifications = notificationService.getNotificationByDevice(Integer.valueOf(notification.getDeviceid()),notification.getCreationdate());
    	return notifications;
    }
	
	@RequestMapping(value = "/getStatistic", method = RequestMethod.POST)
    @ResponseBody
    public Statistic getStatistic(@RequestHeader(value="Authorization") String authorization, @RequestBody Device device) {
    	System.out.println("Begin getStatistic -->"+device);
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token);
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	Statistic statistic = carService.getCarStatistic(device.getDeviceid(), device.getDate(),token);
    	return statistic;
    }
	
	@RequestMapping(value = "/stopCar", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage stopCar(@RequestHeader(value="Authorization") String authorization, @RequestBody Integer deviceid) throws ParseException {
    	System.out.println("Begin stopCar -->"+deviceid);
    	int status;
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token);
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	Car car = carService.getCarByDevice(deviceid,token);
    	Location location = carService.getLastLocationByCar(deviceid,token);
    	if(null != location && location.getSpeed() <= 10){
    		if(car.getDevicetype().equals("TK103")){
    			status = senderService.sendSms("KriAuto.ma", car.getSimnumber(), "stop135791");
    	    }else{
    	    	status = senderService.sendSms("KriAuto.ma", car.getSimnumber(), "  setdigout 00");
    	    }
    		
    		if(status == 0){
    		    //senderService.sendSms("KriAuto.ma", profile.getPhone(), "Voiture+Arrete+"+car.getMark()+"+"+car.getModel()+"+"+car.getColor()+"+"+car.getImmatriculation());
    		    car.setStatus(1);
    		    try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		    carService.updateCar(car);
    		    System.out.println("voiture arreté : "+car.getMark()+car.getModel()+car.getColor()+" "+car.getImmatriculation()+" ");
    		}else{
    			car.setStatus(2);
          	    carService.updateCar(car);
    		}
    	}else{
    		car.setStatus(2);
      	    carService.updateCar(car);
    	}
    	return new ResponseMessage(ResponseMessage.Type.success, "STOP_CAR",Constant.getLabels().get("STOP_CAR").toString());

    }
	
	@RequestMapping(value = "/startCar", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage startCar(@RequestHeader(value="Authorization") String authorization, @RequestBody Integer deviceid) throws ParseException {
    	System.out.println("Begin startCar -->"+deviceid);
    	int status;
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token);
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	Car car = carService.getCarByDevice(deviceid,token);
    	if(car.getDevicetype().equals("TK103")){
    	  status = senderService.sendSms("KriAuto.ma", car.getSimnumber(), "resume135791");
    	}else{
    	  status = senderService.sendSms("KriAuto.ma", car.getSimnumber(), "  setdigout 11");
    	}
    	if(status == 0){
    	  //senderService.sendSms("KriAuto.ma", profile.getPhone(), "Voiture+Demarre+"+car.getMark()+"+"+car.getModel()+"+"+car.getColor()+"+"+car.getImmatriculation());
    		try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	  car.setStatus(0);
    	  carService.updateCar(car);
    	}else{
    	  car.setStatus(3);
      	  carService.updateCar(car);
    	}
    	return new ResponseMessage(ResponseMessage.Type.success, "START_CAR",Constant.getLabels().get("START_CAR").toString());
    }
	
	@RequestMapping(value = "/loadAllData", method = RequestMethod.POST)
    @ResponseBody
    public List<Location> loadAllData(@RequestHeader(value="Authorization") String authorization, @RequestBody Search search) {
    	System.out.println("Begin loadAllData -->"+authorization);
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token);
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	List<Location> locations = new ArrayList<Location>();
    	locations = carService.getAllLocationsByToken(token, search.getDate());
    	return locations;
    }
	
	@RequestMapping(value = "/loadCarData", method = RequestMethod.POST)
    @ResponseBody
    public List<Location> loadCarData(@RequestHeader(value="Authorization") String authorization, @RequestBody Search search) {
    	System.out.println("Begin loadAllData -->"+authorization);
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token);
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	List<Location> locations = new ArrayList<Location>();
    	locations = carService.getAllLocationsByCar(Integer.valueOf(search.getDeviceid()), search.getDate(),token);
    	return locations;
    }
	
   public boolean isThisDateValid(String dateToValidate, String dateFromat){
		
		if(dateToValidate == null){
			return false;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);
		
		try {
			//if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
			System.out.println(date);
		
		} catch (ParseException e) {
			
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
