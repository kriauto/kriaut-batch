package ma.kriauto.rest.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import ma.kriauto.rest.domain.Car;
import ma.kriauto.rest.domain.Consumption;
import ma.kriauto.rest.domain.Course;
import ma.kriauto.rest.domain.Event;
import ma.kriauto.rest.domain.Item;
import ma.kriauto.rest.domain.Location;
import ma.kriauto.rest.domain.Notification;
import ma.kriauto.rest.domain.Profile;
import ma.kriauto.rest.domain.Speed;
import ma.kriauto.rest.domain.Statistic;
import ma.kriauto.rest.domain.StatisticValues;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

@Repository
@Qualifier("carDao")
public class CarDaoImpl implements CarDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static int countkey = 0 ;  
	
	@Override
	public Car getCarByDevice(Integer deviceid, String token) {
		System.out.println("getCarByDevice "+deviceid);
		try{
		   Car car = (Car) jdbcTemplate.queryForObject("SELECT c.* FROM profile p, agency a, car c where p.token = ? and p.agencyid = a.id and a.id = c.agencyid and c.deviceid = ?", new Object[] {token,deviceid}, new BeanPropertyRowMapper(Car.class));
		   return car;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public void updateCar(Car car) throws ParseException {
	     System.out.println("updateCar "+car);
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
	     Date technicaldate = ( null != car.getTechnicalcontroldate() ? sdf.parse(car.getTechnicalcontroldate()) : null);
	     Date insurancedate = ( null != car.getInsuranceenddate() ? sdf.parse(car.getInsuranceenddate()) : null);
	     Date circulationdate = ( null != car.getAutorisationcirculationenddate() ? sdf.parse(car.getAutorisationcirculationenddate()) : null);
	     jdbcTemplate.update("UPDATE car set agencyid =  ?, imei =  ?, simnumber =  ?"
	     		+ ", immatriculation =  ?, vin =  ?, mark =  ?, model =  ?"
	     		+ ", color =  ?, photo =  ?, status =  ?, deviceid =  ? "
	     		+ ", mileage =  ? , fuel =  ?, latitude1 =  ?, longitude1 =  ?, latitude2 =  ?"
	     		+ ", longitude2 =  ?, latitude3 =  ?, longitude3 =  ?, latitude4 =  ?"
	     		+ ", longitude4 =  ?, latitude5 =  ?, longitude5 =  ?, latitude6 =  ?"
	     		+ ", longitude6 =  ?, technicalcontroldate = ?, emptyingkilometre = ?"
	     		+ ", insuranceenddate = ?, maxspeed = ?, maxcourse = ?, totaldistance = ?, minlevelfuel = ?, maxenginetemperature = ?"
	     		+ ", minfridgetemperature = ?, maxfridgetemperature = ?, notiftechnicalcontroldate = ?, notifemptyingkilometre = ?"
	     		+ ", notifinsuranceenddate = ?, notifmaxspeed = ?, notifmaxcourse = ?"  
	     		+ ", notifminlevelfuel = ?, notifmaxenginetemperature = ?, notifminfridgetemperature = ?, notifautorisationcirculationenddate = ?"
	     		+ ", notifmaxfridgetemperature = ?, emptyingkilometreindex = ?, autorisationcirculationenddate = ?, notifinzone = ?, notifoutzone = ?"   
	     		+ "  WHERE id = ?  "
	     		, new Object[] { car.getAgencyid(), car.getImei(), car.getSimnumber()
	     		, car.getImmatriculation(), car.getVin(), car.getMark(), car.getModel()
	     		, car.getColor(), car.getPhoto(), car.getStatus(), car.getDeviceid()
	     		, car.getMileage(), car.getFuel(), car.getLatitude1()
	     		, car.getLongitude1(), car.getLatitude2(), car.getLongitude2(), car.getLatitude3()
	     		, car.getLongitude3(), car.getLatitude4(), car.getLongitude4(), car.getLatitude5()
	     		, car.getLongitude5(), car.getLatitude6(), car.getLongitude6(), technicaldate
	     		, car.getEmptyingkilometre(), insurancedate
	     		, car.getMaxspeed(), car.getMaxcourse(), car.getTotaldistance(), car.getMinlevelfuel(), car.getMaxenginetemperature()
	     		, car.getMinfridgetemperature(), car.getMaxfridgetemperature(), car.getNotiftechnicalcontroldate()
	     		, car.getNotifemptyingkilometre(), car.getNotifinsuranceenddate(), car.getNotifmaxspeed(), car.getNotifmaxcourse()
	     		, car.getNotifminlevelfuel(), car.getNotifmaxenginetemperature(), car.getNotifminfridgetemperature(), car.getNotifautorisationcirculationenddate()
	     		, car.getNotifmaxfridgetemperature(), car.getEmptyingkilometreindex(), circulationdate, car.getNotifinzone(), car.getNotifoutzone()
	     		, car.getId()});
	}

	@Override
	public List<Car> getAllCarsByToken(boolean group, String token) {
		System.out.println("getAllCarsByToken " + group + "," + token);
		List<Car> cars = new ArrayList<Car>();
		List<Car> carstmp = new ArrayList<Car>();
		if (group) {
			cars.add(new Car("Toutes", 111111));
		}
		carstmp = jdbcTemplate.query("select c.* "
				+ " from profile p, agency a, car c " + " where p.token = ? "
				+ " and p.agencyid = a.id " 
				+ " and a.id = c.agencyid order by c.immatriculation",
				new Object[] { token }, new BeanPropertyRowMapper(Car.class));
		cars.addAll(carstmp);
		return cars;
	}
	
	@Override
	public List<Car> getAllCarsByProfile(String login) {
		System.out.println("getAllCarsByUser " + login);
		List<Car> cars = new ArrayList<Car>();
		cars = jdbcTemplate.query("SELECT c.* "
				+" FROM profile p, car c  WHERE p.login = ? and p.agencyid = c.agencyid ",new Object[] { login }, new BeanPropertyRowMapper(Car.class));
		return cars;
	}

	@Override
	public List<Item> getAllDatesByCar(Integer deviceid) {
		System.out.println("getAllDatesByCar " + deviceid);
		GregorianCalendar date = new GregorianCalendar();
		date.add(Calendar.MONTH, -1);
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		String day = formater.format(date.getTime());
		List<Item> dates = new ArrayList<Item>();
		dates = jdbcTemplate.query(" select distinct to_char(servertime,'YYYY-MM-DD') as code, to_char(servertime,'YYYY-MM-DD') as label"
						+ "  from  positions" 
				        + "  where deviceid = ? "
				        + "  and   valid = true "
						+ "  and   to_char(servertime,'YYYY-MM-DD') >= '"
						+ day + "'" + " order by code desc",
				new Object[] { deviceid }, new BeanPropertyRowMapper(
						Item.class));
		return dates;
	}

	@Override
	public List<Item> getAllDatesByToken(String token) {
		System.out.println("getAllDatesByToken " + token);
		GregorianCalendar date = new GregorianCalendar();
		date.add(Calendar.MONTH, -1);
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		String day = formater.format(date.getTime());
		List<Item> dates = new ArrayList<Item>();

		dates = jdbcTemplate.query(" select distinct to_char(ps.servertime,'YYYY-MM-DD') as code,to_char(ps.servertime,'YYYY-MM-DD') as label"
						+ " from car c, agency a, positions ps, profile p "
						+ " where p.token = ? "
						+"  and   p.agencyid = a.id "
						+"  and   a.id = c.agencyid "
						+"  and   c.deviceid = ps.deviceid  "
						+ " and   ps.valid = true "
						+ " and   to_char(ps.servertime,'YYYY-MM-DD') >= '"+ day + "'"
						+ " order by code desc",
						new Object[] { token }, new BeanPropertyRowMapper(Item.class));
		return dates;
	}

	@Override
	public List<Location> getAllLocationsByToken(String token, String date) {
		System.out.println("getAllLocationsByToken " + token);
		List<Location> locations = new ArrayList<Location>();
		List<Car> cars = getAllCarsByToken(false, token);
		for(int i = 0; i<cars.size(); i++){
			Car car = cars.get(i);
//			Event event3 = getLastEvent(car.getDeviceid(), date);
//			  if(null != event3 && "{\"alarm\":\"powerOff\"}".equals(event3.getAttributes())){
//				  Location location = getLocationById(event3.getPositionid());
//				  locations.add(location);
//			  }else{
				  Location location = getLastLocationByCar(car.getDeviceid(),date,token);
				  if(null != location){
				     locations.add(location);
				  }
//			  }
		}
				
		for(int i = 0; i<locations.size(); i++){
			Location loc = locations.get(i);
			loc.setAddress(getGoodleAdresse(loc.getLatitude(), loc.getLongitude()));
		}
		return locations;
	}

	@Override
	public List<Location> getAllLocationsByCar(Integer deviceid, String date, String token) {
		System.out.println("getAllLocationsByCar " + deviceid);
		List<Location> locations = new ArrayList<Location>();
		List<Location> locations1 = new ArrayList<Location>();
		List<Location> tmplocations = new ArrayList<Location>();
		List<Event> events = new ArrayList<Event>();
		
		locations = jdbcTemplate.query(" select distinct ps.longitude, ps.latitude, ps.speed, ps.course, ps.fixtime -'1 hour'::interval AS servertime,ps.attributes, c.immatriculation, c.vin, c.mark, c.model, c.photo, c.color, ps.deviceid, c.colorCode" 
				+ " from profile p, agency a,car c , positions ps "
				+ " where p.token = ? "
				+ " and   p.agencyid = a.id "
				+ " and   a.id = c.agencyid "
				+ " and   c.deviceid = ps.deviceid" 
				+ " and   ps.deviceid = ? "
				+ " and   ps.attributes not like '%alarm%' "
				+ " and   ps.valid = true "
				+ " and   to_char(ps.fixtime -'1 hour'::interval,'YYYY-MM-DD') = '"+ date + "'"
				+ " order by servertime ",new Object[] {token,deviceid}, new BeanPropertyRowMapper(Location.class));

//		events = jdbcTemplate.query(" select distinct positionid, attributes,servertime from events where to_char(servertime - interval '1 hour', 'yyyy-mm-dd') = ? and deviceid = ? and attributes like '%powerO%' order by positionid ",new Object[] {date, deviceid}, new BeanPropertyRowMapper(Event.class));
//		if(events.size() > 0){
//		  for(int i = 0; i < events.size() ; i++){
//			Event event1 = (null != events.get(i) ? events.get(i) : null);
//			if(null != event1 &&  "{\"alarm\":\"powerOff\"}".equals(event1.getAttributes()) && i == 0){
//				tmplocations = getLocationsBefore(deviceid,event1.getPositionid(), date);
//				locations.addAll(tmplocations);
//				tmplocations.clear();
//			}
//			if(null != event1 && "{\"alarm\":\"powerOn\"}".equals(event1.getAttributes())){
//				boolean exist = false;
//				for(int j = i+1 ; j < events.size(); j++){
//					Event event2 = (j < events.size() ? events.get(j) : null);
//					Event event22 = ((j+1) < events.size() ? events.get(j+1) : null);
//					if(null != event2 && "{\"alarm\":\"powerOff\"}".equals(event2.getAttributes()) && ((null != event22 && "{\"alarm\":\"powerOn\"}".equals(event22.getAttributes())) || null == event22)){
//						tmplocations = getLocationsBetween(deviceid,event1.getPositionid(),event2.getPositionid(), date);
//						locations.addAll(tmplocations);
//						tmplocations.clear();
//						i = j ;
//						exist = true;
//						break;
//					}
//				}
//				if(!exist){
//					tmplocations = getLocationsAfter(deviceid, event1.getPositionid(), date);
//					locations.addAll(tmplocations);
//					tmplocations.clear();
//				}
//				
//			}
//		 }
//		}else{
//			 Event event3 = getLastEvent(deviceid, date);
//			  if(null != event3 && "{\"alarm\":\"powerOff\"}".equals(event3.getAttributes())){
//				  Location location = getLocationById(event3.getPositionid());
//				  locations.add(location);
//			  }else if(null != event3 && "{\"alarm\":\"powerOn\"}".equals(event3.getAttributes())) {
//				  locations = getLocationsAfter(deviceid, event3.getPositionid(), date);
//				  if(locations.size() == 0){
//					  Location location = getLocationById(event3.getPositionid());
//					  locations.add(location);
//				  }
//			  }else{
//				  locations = getLocationsByDate(deviceid,date);
//			  }
//		}
		double log = 0,lat = 0;
		if(locations.size() > 0){
		  for(int i = 0 ; i< locations.size(); i++){
			if(0 == i){
				log = locations.get(i).getLongitude();
				lat = locations.get(i).getLatitude();
				if(null != locations.get(i).getAttributes() && locations.get(i).getAttributes().indexOf("temp1") == 0  && getDistance(locations.get(i).getAttributes()) <= 500){
				   locations1.add(locations.get(i));
				}
			}else{
				if(log == locations.get(i).getLongitude() && lat == locations.get(i).getLatitude()){

				}else{
					log = locations.get(i).getLongitude();
					lat = locations.get(i).getLatitude();
					double dist = distance(locations.get(i-1).getLatitude(), locations.get(i-1).getLongitude(), locations.get(i).getLatitude(), locations.get(i).getLongitude(), 'K');
					  if(dist <= 1){
						  locations1.add(locations.get(i));
					  }
//					if(null != locations.get(i).getAttributes() && getDistance(locations.get(i).getAttributes()) <= 500){
//						   locations1.add(locations.get(i));
//					}
				}
			  }
			}
		  }else{
			  Location location = getLastLocationByCar(deviceid,date,token);
			  if(null != location){
			    locations1.add(location);
			  }
		  }
		return locations1;
	}
	
	@Override
	public Event getLastEvent(Integer deviceid, String date) {
		System.out.println("getLastEvent " + deviceid);
		try{
		    Event event = (Event) jdbcTemplate.queryForObject(" select distinct positionid, attributes " 
				+ " from events "
				+ " where attributes like '%powerO%' and positionid = (select MAX(positionid) AS positionid from events where deviceid = ? and to_char(servertime - interval '1 hour', 'yyyy-mm-dd') <= ? and attributes like '%powerO%') ",new Object[] {deviceid, date}, new BeanPropertyRowMapper(Event.class));
		    return event;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public Event getLastEvent(Integer deviceid) {
		System.out.println("getLastEvent " + deviceid);
		try{
		    Event event = (Event) jdbcTemplate.queryForObject(" select distinct positionid, attributes " 
				+ " from events "
				+ " where attributes like '%powerO%' and positionid = (select MAX(positionid) AS positionid from events where deviceid = ? and  attributes like '%powerO%') ",new Object[] {deviceid}, new BeanPropertyRowMapper(Event.class));
		    return event;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}


	@Override
	public List<Location> getLocationsBefore(Integer deviceid, Integer to, String date) {
		System.out.println("getLocationsBefore " + to);
		List<Location> locations = new ArrayList<Location>();
		locations = jdbcTemplate.query(" select distinct ps.longitude, ps.latitude, ps.speed, ps.course, ps.fixtime -'1 hour'::interval AS servertime,ps.attributes, c.immatriculation, c.vin, c.mark, c.model, c.photo, c.color, ps.deviceid, c.colorCode" 
				+ " from positions ps, car c "
				+ " where c.deviceid = ps.deviceid"
				+ " and   ps.deviceid = ? "
				+ " and   ps.id <= ? "
				+ " and   ps.attributes not like '%alarm%' "
				+ " and   ps.network = 'null' "
				+ " and   to_char(ps.fixtime -'1 hour'::interval,'YYYY-MM-DD') = '"+ date + "'"
				+ " order by servertime ",new Object[] {deviceid, to}, new BeanPropertyRowMapper(Location.class));
		return locations;
	}
	
	@Override
	public List<Location> getLocationsAfter(Integer deviceid, Integer from, String date) {
		System.out.println("getLocationsAfter " + from);
		List<Location> locations = new ArrayList<Location>();
		locations = jdbcTemplate.query(" select distinct ps.longitude, ps.latitude, ps.speed, ps.course, ps.fixtime -'1 hour'::interval AS servertime,ps.attributes, c.immatriculation, c.vin, c.mark, c.model, c.photo, c.color, ps.deviceid, c.colorCode" 
				+ " from positions ps, car c "
				+ " where c.deviceid = ps.deviceid" 
				+ " and   ps.deviceid = ? "
				+ " and   ps.id >= ? "
				+ " and   ps.attributes not like '%alarm%' "
				+ " and   ps.network = 'null' "
				+ " and   to_char(ps.fixtime -'1 hour'::interval,'YYYY-MM-DD') = '"+ date + "'"
				+ " order by servertime ",new Object[] { deviceid, from }, new BeanPropertyRowMapper(Location.class));
		return locations;
	}
	
	@Override
	public List<Location> getLocationsByDate(Integer deviceid,String date) {
		System.out.println("getLocationsByDate " + date);
		List<Location> locations = new ArrayList<Location>();
		locations = jdbcTemplate.query(" select distinct ps.longitude, ps.latitude, ps.speed, ps.course, ps.fixtime -'1 hour'::interval AS servertime,ps.attributes, c.immatriculation, c.vin, c.mark, c.model, c.photo, c.color, ps.deviceid, c.colorCode" 
				+ " from positions ps, car c "
				+ " where c.deviceid = ps.deviceid " 
				+ " and   ps.deviceid = ? "
				+ " and   ps.attributes not like '%alarm%' "
				+ " and   ps.network = 'null' "
				+ " and   to_char(ps.fixtime -'1 hour'::interval,'YYYY-MM-DD') = '"+ date + "'"
				+ " order by servertime ",new Object[] { deviceid }, new BeanPropertyRowMapper(Location.class));
		return locations;
	}
	
	@Override
	public List<Location> getLocationsBetween(Integer deviceid, Integer from, Integer to, String date) {
		System.out.println("getLocationsBetween " + from+" "+to);
		List<Location> locations = new ArrayList<Location>();
		locations = jdbcTemplate.query(" select distinct ps.longitude, ps.latitude, ps.speed, ps.course, ps.fixtime -'1 hour'::interval AS servertime,ps.attributes, c.immatriculation, c.vin, c.mark, c.model, c.photo, c.color, ps.deviceid, c.colorCode" 
				+ " from positions ps, car c "
				+ " where c.deviceid = ps.deviceid" 
				+ " and   ps.deviceid = ? "
				+ " and   ps.id >= ? "
				+ " and   ps.id <= ? "
				+ " and   ps.attributes not like '%alarm%' "
				+ " and   ps.network = 'null' "
				+ " and   to_char(ps.fixtime -'1 hour'::interval,'YYYY-MM-DD') = '"+ date + "'"
				+ " order by servertime ",new Object[] { deviceid, from, to }, new BeanPropertyRowMapper(Location.class));
		return locations;
	}
	
	@Override
	public Location getLocationById(Integer id) {
		System.out.println("getLocationsById " + id);
		try{
			Location location = (Location) jdbcTemplate.queryForObject(" select distinct ps.longitude, ps.latitude, ps.speed, ps.course, ps.fixtime -'1 hour'::interval AS servertime,ps.attributes, c.immatriculation, c.vin, c.mark, c.model, c.photo, c.color, ps.deviceid, c.colorCode" 
				+ " from positions ps, car c "
				+ " where c.deviceid = ps.deviceid" 
				+ " and   ps.id = ? ",new Object[] {id}, new BeanPropertyRowMapper(Location.class));
		    return location;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Course> getTotalCourseByCar(Integer deviceid) {
		System.out.println("getTotalCourseByCar " + deviceid);
		GregorianCalendar date = new GregorianCalendar();
		date.add(Calendar.MONTH, -1);
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		String day = formater.format(date.getTime());
		List<Course> cours = new ArrayList<Course>();
		cours = jdbcTemplate
				.query(" select distinct to_char(servertime,'YYYY-MM-DD') AS day, SUM(course) AS totalCourse "
						+ " from positions "
						+ " where deviceid = ? "
						+ " and   to_char(servertime,'YYYY-MM-DD') >= '"
						+ day
						+ "' "
						+ " and   valid = true "
						+ " group by deviceid,to_char(servertime,'YYYY-MM-DD') "
						+ " order by day desc", new Object[] { deviceid },
						new BeanPropertyRowMapper(Course.class));
		for(int i = 0; i<cours.size(); i++){
			Course cor = cours.get(i);
			cor.setTotalCourse(String.valueOf(Double.valueOf(cor.getTotalCourse())/1000));
		}
		return cours;
	}

	@Override
	public List<Speed> getMaxSpeedByCar(Integer deviceid) {
		System.out.println("getMaxSpeedByCar " + deviceid);
		GregorianCalendar date = new GregorianCalendar();
		date.add(Calendar.MONTH, -1);
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		String day = formater.format(date.getTime());
		List<Speed> speed = new ArrayList<Speed>();
		speed = jdbcTemplate
				.query(" select distinct to_char(servertime,'YYYY-MM-DD') AS day, MAX(speed) AS maxSpeed "
						+ " from positions ps "
						+ " where deviceid = ? "
						+ " and speed < 100 "
						+ " and   to_char(servertime,'YYYY-MM-DD') >= '"
						+ day
						+ "' "
						+ " and   valid = true "
						+ " group by to_char(servertime,'YYYY-MM-DD') "
						+ " order by day desc", new Object[] { deviceid },
						new BeanPropertyRowMapper(Speed.class));
		for(int i = 0; i<speed.size(); i++){
			Speed spe = speed.get(i);
			spe.setMaxSpeed(String.valueOf(Double.valueOf(spe.getMaxSpeed())*1.85));
		}
		return speed;
	}

	@Override
	public List<Consumption> getTotalConsumptionByCar(Integer deviceid) {
		System.out.println("getTotalConsumptionByCar " + deviceid);
		GregorianCalendar date = new GregorianCalendar();
		date.add(Calendar.MONTH, -1);
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		String day = formater.format(date.getTime());
		List<Consumption> consumption = new ArrayList<Consumption>();
		consumption = jdbcTemplate
				.query(" select distinct to_char(servertime,'YYYY-MM-DD') AS day, SUM(course) AS consumption "
						+ " from positions ps "
						+ " where deviceid = ? "
						+ " and   to_char(servertime,'YYYY-MM-DD') >= '"
						+ day
						+ "' "
						+ " and   valid = true "
						+ " group by to_char(servertime,'YYYY-MM-DD') "
						+ " order by day desc", new Object[] { deviceid },
						new BeanPropertyRowMapper(Consumption.class));
		for(int i = 0; i<consumption.size(); i++){
			Consumption con = consumption.get(i);
			con.setConsumption(String.valueOf((Float.valueOf(con.getConsumption())/100000)*5));
		}
		return consumption;
	}
	
	@Override
	public Statistic getCarStatistic(Integer deviceid, String date, String token) {
		System.out.println("getCarStatistic " + deviceid+date);
		Statistic statistic = new Statistic();
		double speed = 0, cours=0;
		Car car = getCarByDevice(deviceid,token);		
		List<Location> locations = getAllLocationsByCar(deviceid, date, token);
		
		for(int i =0; i < locations.size(); i++){
			if(locations.get(i).getSpeed() < 97 && locations.get(i).getSpeed() > speed){
				speed = locations.get(i).getSpeed();
			}
			if( i != 0){
			  double dist = distance(locations.get(i-1).getLatitude(), locations.get(i-1).getLongitude(), locations.get(i).getLatitude(), locations.get(i).getLongitude(), 'K');
			  if(dist <= 1){
			    cours = cours + dist;
			  }
			}
		}

		if(cours > 0){
		   statistic.setConsumption((double)Math.round(((cours/100)*car.getConsumption())*100)/100);
		   statistic.setSpeed((double)Math.round((speed*1.85)*100)/100);
		   statistic.setCourse((double)Math.round((cours)*100)/100);
		}else{
		   statistic.setConsumption(0.0);
		   statistic.setSpeed(0.0);
		   statistic.setCourse(0.0);
		}
		statistic.setEnable(car.getEnable());
		StatisticValues maximalspeed = new StatisticValues();
		maximalspeed.setV00(20.0);maximalspeed.setV01(15.3);maximalspeed.setV02(23.0);maximalspeed.setV03(23.5);maximalspeed.setV04(45.0);maximalspeed.setV05(63.0);
		maximalspeed.setV06(12.0);maximalspeed.setV07(52.0);maximalspeed.setV08(63.0);maximalspeed.setV09(45.0);maximalspeed.setV10(63.0);maximalspeed.setV11(63.2);
		maximalspeed.setV12(14.0);maximalspeed.setV13(21.0);maximalspeed.setV14(63.0);maximalspeed.setV15(45.0);maximalspeed.setV16(63.0);maximalspeed.setV17(45.3);
		maximalspeed.setV18(23.0);maximalspeed.setV19(52.0);maximalspeed.setV20(36.2);maximalspeed.setV21(36.5);maximalspeed.setV22(12.0);maximalspeed.setV23(63.0);
		StatisticValues maximalcourse = new StatisticValues();
		maximalcourse.setV00(20.0);maximalcourse.setV01(15.3);maximalcourse.setV02(23.0);maximalcourse.setV03(23.5);maximalcourse.setV04(45.0);maximalcourse.setV05(63.0);
		maximalcourse.setV06(12.0);maximalcourse.setV07(52.0);maximalcourse.setV08(63.0);maximalcourse.setV09(45.0);maximalcourse.setV10(63.0);maximalcourse.setV11(63.2);
		maximalcourse.setV12(14.0);maximalcourse.setV13(21.0);maximalcourse.setV14(63.0);maximalcourse.setV15(45.0);maximalcourse.setV16(63.0);maximalcourse.setV17(45.3);
		maximalcourse.setV18(23.0);maximalcourse.setV19(52.0);maximalcourse.setV20(36.2);maximalcourse.setV21(36.5);maximalcourse.setV22(12.0);maximalcourse.setV23(63.0);
		StatisticValues fuelconsommation = new StatisticValues();
		fuelconsommation.setV00(20.0);fuelconsommation.setV01(15.3);fuelconsommation.setV02(23.0);fuelconsommation.setV03(23.5);fuelconsommation.setV04(45.0);fuelconsommation.setV05(63.0);
		fuelconsommation.setV06(1.0);fuelconsommation.setV07(52.0);fuelconsommation.setV08(63.0);fuelconsommation.setV09(45.0);fuelconsommation.setV10(63.0);fuelconsommation.setV11(63.2);
		fuelconsommation.setV12(14.0);fuelconsommation.setV13(21.0);fuelconsommation.setV14(63.0);fuelconsommation.setV15(45.0);fuelconsommation.setV16(63.0);fuelconsommation.setV17(45.3);
		fuelconsommation.setV18(23.0);fuelconsommation.setV19(52.0);fuelconsommation.setV20(36.2);fuelconsommation.setV21(36.5);fuelconsommation.setV22(12.0);fuelconsommation.setV23(63.0);
		StatisticValues fuellevel = new StatisticValues();
		fuellevel.setV00(20.0);fuellevel.setV01(15.3);fuellevel.setV02(23.0);fuellevel.setV03(23.5);fuellevel.setV04(45.0);fuellevel.setV05(63.0);
		fuellevel.setV06(12.0);fuellevel.setV07(52.0);fuellevel.setV08(63.0);fuellevel.setV09(45.0);fuellevel.setV10(63.0);fuellevel.setV11(63.2);
		fuellevel.setV12(14.0);fuellevel.setV13(21.0);fuellevel.setV14(63.0);fuellevel.setV15(45.0);fuellevel.setV16(63.0);fuellevel.setV17(45.3);
		fuellevel.setV18(23.0);fuellevel.setV19(52.0);fuellevel.setV20(36.2);fuellevel.setV21(36.5);fuellevel.setV22(12.0);fuellevel.setV23(63.0);
		StatisticValues enginetemperature = new StatisticValues();
		enginetemperature.setV00(20.0);enginetemperature.setV01(15.3);enginetemperature.setV02(23.0);enginetemperature.setV03(23.5);enginetemperature.setV04(45.0);enginetemperature.setV05(63.0);
		enginetemperature.setV06(12.0);enginetemperature.setV07(52.0);enginetemperature.setV08(63.0);enginetemperature.setV09(45.0);enginetemperature.setV10(63.0);enginetemperature.setV11(63.2);
		enginetemperature.setV12(14.0);enginetemperature.setV13(21.0);enginetemperature.setV14(63.0);enginetemperature.setV15(45.0);enginetemperature.setV16(63.0);enginetemperature.setV17(45.3);
		enginetemperature.setV18(23.0);enginetemperature.setV19(53.0);enginetemperature.setV20(36.2);enginetemperature.setV21(36.5);enginetemperature.setV22(12.0);enginetemperature.setV23(63.0);
		StatisticValues fridgetemperature = new StatisticValues();
		fridgetemperature.setV00(20.0);fridgetemperature.setV01(15.3);fridgetemperature.setV02(23.0);fridgetemperature.setV03(23.5);fridgetemperature.setV04(45.0);fridgetemperature.setV05(63.0);
		fridgetemperature.setV06(12.0);fridgetemperature.setV07(52.0);fridgetemperature.setV08(66.0);fridgetemperature.setV09(40.0);fridgetemperature.setV10(63.0);fridgetemperature.setV11(63.2);
		fridgetemperature.setV12(14.0);fridgetemperature.setV13(21.0);fridgetemperature.setV14(63.0);fridgetemperature.setV15(40.0);fridgetemperature.setV16(63.0);fridgetemperature.setV17(45.3);
		fridgetemperature.setV18(23.0);fridgetemperature.setV19(52.0);fridgetemperature.setV20(36.2);fridgetemperature.setV21(36.5);fridgetemperature.setV22(12.0);fridgetemperature.setV23(63.0);
		statistic.setMaximalspeed(maximalspeed);
		statistic.setMaximalcourse(maximalcourse);
		statistic.setFuelconsommation(fuelconsommation);
		statistic.setFuellevel(fuellevel);
		statistic.setEnginetemperature(enginetemperature);
		statistic.setFridgetemperature(fridgetemperature);
		return statistic;
	}
	
	@Override
	public String getGoodleAdresse(Double Lat, Double Lng){
		GeoApiContext gtx = new GeoApiContext().setApiKey("AIzaSyD-w27Lhidw00LPBW7UWHp1TBPv4O3v650");
		GeocodingResult[] gResp = null ;
		try 
		  {
		    gResp = GeocodingApi.newRequest(gtx).latlng(new LatLng(Lat, Lng)).await();
		    System.out.println(gResp[0].formattedAddress);
		  } catch (Exception e) {
		    e.printStackTrace();
		  }
	    return gResp[0].formattedAddress;
	}
	
	@Override
	public Location getLastLocationByCar(Integer deviceid, String date, String token) {
		System.out.println("getAllLocationsByCar " + deviceid);
        try
        {
        	Location location = (Location) jdbcTemplate.queryForObject(" select distinct ps.longitude, ps.latitude, ps.speed, ps.course, ps.address, ps.fixtime -'1 hour'::interval AS servertime,ps.attributes , c.immatriculation, c.vin, c.mark, c.model, c.photo, c.color, c.deviceid, c.colorCode "
				    + " from profile p, agency a, car c, positions ps "
				    + " where   ps.id =  (select MAX(ps.id) from profile p, agency a, car c, positions ps where p.token = ? and p.agencyid = a.id and a.id = c.agencyid and c.deviceid = ps.deviceid and ps.deviceid = ?  and to_char(fixtime,'YYYY-MM-DD') <= ? and valid = true ) "
				    + " and p.token = ? "
				    + " and p.agencyid = a.id "
				    + " and a.id = c.agencyid "
				    + " and c.deviceid = ps.deviceid ",new Object[] {token, deviceid, date, token}, new BeanPropertyRowMapper(Location.class));
        	return location;
        } catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Notification> getDataNotification(int type) {
		String query = "";
		if(type == 1){
			query = " select distinct p.*, c.* "
					+ " from profile p, car c, agency a  "
					+ " where p.agencyid = a.id "
					+ " and   a.id = c.agencyid "
					+ " and   c.status = 2 ";
		}
		if(type == 2){
			query = " select distinct p.*, c.* "
					+ " from profile p, car c, agency a  "
					+ " where p.agencyid = a.id "
					+ " and   a.id = c.agencyid "
					+ " and   c.isgeofence = true "
					+ " and   c.isnotifgeofence = false ";
		}
		if(type == 3){
			query = " select distinct p.*, c.* "
					+ " from profile p, car c, agency a  "
					+ " where p.agencyid = a.id "
					+ " and   a.id = c.agencyid "
					+ " and   c.isnotifdefaultgeofence = false ";
		}
		
		List<Notification> notifications = new ArrayList<Notification>();
		notifications = jdbcTemplate.query(query, new Object[] {},new BeanPropertyRowMapper(Notification.class));
		// TODO Auto-generated method stub
		return notifications;
	}

	@Override
	public Location getLastLocationByCar(Integer deviceid, String token) {
		System.out.println("getAllLocationsByCar " + deviceid);
        try
        {
        	Location location = (Location) jdbcTemplate.queryForObject(" select distinct ps.longitude, ps.latitude, ps.speed, ps.course, ps.address, ps.fixtime -'1 hour'::interval AS servertime,ps.attributes , c.immatriculation, c.vin, c.mark, c.model, c.photo, c.color, c.deviceid, c.colorCode "
				    + " from profile p, agency a, car c, positions ps "
				    + " where p.token = ? "
				    + " and   p.agencyid = a.id "
				    + " and   a.id = c.agencyid "
				    + " and   c.deviceid = ps.deviceid "
				    + " and   ps.id =  (select MAX(id) from positions where deviceid = ? and   attributes not like '%alarm%' and network = 'null')",new Object[] {token,deviceid}, new BeanPropertyRowMapper(Location.class));
        	return location;
        } catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Location> getAllLocationByCarTime(Integer deviceid, String time) {
		System.out.println("getAllLocationsByCar " + deviceid);
		List<Location> locations = new ArrayList<Location>();
		locations = jdbcTemplate.query(" select distinct ps.* "
				+ " from  positions ps "
				+ " where ps.deviceid = ? "
				+ " and   ps.valid = true "
				+ " and   ps.attributes not like '%alarm%' "
				+ " and   ps.network = 'null' "
				+ " and   to_char(ps.fixtime,'yyyy-MM-ss HH24:MI:SS')  >= '"+ time + "'"
				+ " order by ps.fixtime ",new Object[] { deviceid }, new BeanPropertyRowMapper(Location.class));
		return locations;
	}

	@Override
	public void initGeoFence() {
		System.out.println("initGeoFence ");
	    jdbcTemplate.update("UPDATE car set isnotifgeofence =  false, isnotifdefaultgeofence =  false ", new Object[] {});
		
	}

	@Override
	public Location getMaxTotalDistanceByCar(Integer deviceid, String date) {
		// TODO Auto-generated method stub
		System.out.println("getMaxTotalDistanceByCar " + deviceid);
        try
        {
        	Location location = (Location) jdbcTemplate.queryForObject(" select distinct ps.longitude, ps.latitude, ps.speed, ps.course, ps.address, ps.fixtime -'1 hour'::interval AS servertime,ps.attributes"
				    + " from positions ps"
				    + " where   ps.attributes not like '%alarm%' "
				    + " and   ps.network = 'null' "
				    + " and   ps.id =  (select MAX(id) from positions where deviceid = ? and attributes like '%totalDistance%' and to_char(fixtime,'yyyy-mm-dd') = ? ",new Object[] { deviceid, date}, new BeanPropertyRowMapper(Location.class));
        	return location;
        } catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Location getMinTotalDistanceByCar(Integer deviceid, String date) {
		// TODO Auto-generated method stub
		System.out.println("getMinTotalDistanceByCar " + deviceid);
		try
        {
        	Location location = (Location) jdbcTemplate.queryForObject(" select distinct ps.longitude, ps.latitude, ps.speed, ps.course, ps.address, ps.fixtime -'1 hour'::interval AS servertime,ps.attributes"
				    + " from positions ps"
				    + " where ps.attributes not like '%alarm%' "
				    + " and   ps.network = 'null' "
				    + " and   ps.id =  (select MIN(id) from positions where deviceid = ? and attributes like '%totalDistance%'and to_char(fixtime,'yyyy-mm-dd') = ? ",new Object[] { deviceid, date}, new BeanPropertyRowMapper(Location.class));
        	return location;
        } catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Location getMaxTotalDistanceByCar(Integer deviceid) {
		// TODO Auto-generated method stub
		System.out.println("getAllLocationsByCar " + deviceid);
		try
        {
        	Location location = (Location) jdbcTemplate.queryForObject(" select distinct ps.longitude, ps.latitude, ps.speed, ps.course, ps.address, ps.fixtime -'1 hour'::interval AS servertime,ps.attributes"
				    + " from positions ps"
				    + " where ps.attributes not like '%alarm%' "
				    + " and   ps.network = 'null' "
				    + " and   ps.id =  (select MAX(id) from positions where deviceid = ? and attributes like '%totalDistance%') ",new Object[] { deviceid}, new BeanPropertyRowMapper(Location.class));
        	return location;
        } catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Location getMinTotalDistanceByCar(Integer deviceid) {
		// TODO Auto-generated method stub
		System.out.println("getAllLocationsByCar " + deviceid);
		try
        {
        	Location location = (Location) jdbcTemplate.queryForObject(" select distinct ps.longitude, ps.latitude, ps.speed, ps.course, ps.address, ps.fixtime -'1 hour'::interval AS servertime,ps.attributes"
				    + " from positions ps"
				    + " where   ps.attributes not like '%alarm%' "
				    + " and   ps.network = 'null' "
				    + " and   ps.id =  (select MIN(id) from positions where deviceid = ? and attributes like '%totalDistance%') ",new Object[] { deviceid}, new BeanPropertyRowMapper(Location.class));
        	return location;
        } catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public double getDistance(String distance) {
		// TODO Auto-generated method stub
		if(distance.indexOf("alarm") >= 0 && distance.indexOf("io1") == -1 ){
		   distance.replace("\"", "");
		   return Double.valueOf(distance.replace(',',':').split(":", 0)[5]);
		}else if(distance.indexOf("alarm") >= 0 && distance.indexOf("io1") >= 0 ){
			distance.replace("\"", "");
			return Double.valueOf(distance.replace(',',':').split(":", 0)[11]);
	    }else if(distance.indexOf("io1") >= 0 && distance.indexOf("alarm") == -1){
			distance.replace("\"", "");
			return Double.valueOf(distance.replace(',',':').split(":", 0)[7]);
		}else if(distance.indexOf("distance") >= 0 ){
			distance.replace("\"", "");
			return Double.valueOf(distance.replace(',',':').split(":", 0)[1]);
		}else{
		   return 0;
		}
	}

	@Override
	public double getTotalDistance(String distance) {
		// TODO Auto-generated method stub totalDistance
//		if(distance.indexOf("alarm") >= 0 ){
//		   distance.replace("\"", "");
//		   return Double.valueOf(distance.replace(',',':').split(":", 0)[7]);
//		}else if(distance.indexOf("io1") >= 0 ){
//			return Double.valueOf(distance.replace(',',':').split(":", 0)[9]);
//		}else if(distance.indexOf("totalDistance") >= 0 ){
//			   distance.replace("\"", "");
//			   return Double.valueOf(distance.replace(',',':').split(":", 0)[3]);
//		}else{
//		   return 0;
//		}
		
		if(distance.indexOf("alarm") >= 0 && distance.indexOf("io1") == -1 ){
			   distance.replace("\"", "");
			   return Double.valueOf(distance.replace(',',':').split(":", 0)[7]);
			}else if(distance.indexOf("alarm") >= 0 && distance.indexOf("io1") >= 0 ){
				distance.replace("\"", "");
				return Double.valueOf(distance.replace(',',':').split(":", 0)[13]);
		    }else if(distance.indexOf("io1") >= 0 && distance.indexOf("alarm") == -1){
				distance.replace("\"", "");
				return Double.valueOf(distance.replace(',',':').split(":", 0)[9]);
			}else if(distance.indexOf("distance") >= 0 ){
				distance.replace("\"", "");
				return Double.valueOf(distance.replace(',',':').split(":", 0)[3]);
			}else{
			   return 0;
			}
	}
	
	@Override
	public double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
	      double theta = lon1 - lon2;
	      double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
	      dist = Math.acos(dist);
	      dist = rad2deg(dist);
	      dist = dist * 60 * 1.1515;
	      if (unit == 'K') {
	        dist = dist * 1.609344;
	      } else if (unit == 'N') {
	        dist = dist * 0.8684;
	        }
	      return (dist);
	    }

	    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	    /*::  This function converts decimal degrees to radians             :*/
	    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	 public double deg2rad(double deg) {
	      return (deg * Math.PI / 180.0);
	    }

	    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	    /*::  This function converts radians to decimal degrees             :*/
	    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	 public double rad2deg(double rad) {
	      return (rad * 180.0 / Math.PI);
	    }

	@Override
	public Speed getMaxSpeedByCarTime(Integer deviceid, String date) {
		System.out.println("getMaxSpeedByCarTime " + deviceid);
		Speed speed = null ;
		List<Speed> speeds = new ArrayList<Speed>();
		speeds = jdbcTemplate.query("SELECT to_char(fixtime,'HH24:MI:SS') AS day, speed AS maxspeed FROM positions where speed = "
				+ "(SELECT max(speed) FROM positions WHERE to_char(fixtime,'yyyy-MM-dd HH24:MI:SS') >= ? and deviceid = ? )", new Object[] { date,deviceid },new BeanPropertyRowMapper(Speed.class));
			if(null != speeds && speeds.size() > 0){
				speed = speeds.get(0);
				speed.setMaxSpeed(String.valueOf(Math.round(Double.valueOf(speed.getMaxSpeed())*1.85)));
			}
			return speed;
	}

	@Override
	public boolean isDeviceDisconnected(Integer deviceid, String date) {
		System.out.println("isDeviceDisconnected " +deviceid+ date);
		List<Location> locations = new ArrayList<Location>();
		locations = jdbcTemplate.query(" select distinct ps.* "
				+ " from  positions ps "
			    + " where ps.deviceid = ? "
			    + " and   (ps.attributes like '%power:\"0.0\"%' or ps.attributes like '%alarm:\"lowBattery\"%') "
				+ " and   to_char(ps.fixtime,'yyyy-MM-ss HH24:MI:SS')  >= ? ",new Object[] {deviceid, date}, new BeanPropertyRowMapper(Location.class));
		if(null != locations && locations.size() > 0){
			return true;
		}else{
			return false;
		}
	}
}
