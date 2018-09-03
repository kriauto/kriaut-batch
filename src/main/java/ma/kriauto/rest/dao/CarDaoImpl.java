package ma.kriauto.rest.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ma.kriauto.rest.domain.Car;
import ma.kriauto.rest.domain.Consumption;
import ma.kriauto.rest.domain.Course;
import ma.kriauto.rest.domain.Event;
import ma.kriauto.rest.domain.Item;
import ma.kriauto.rest.domain.Location;
import ma.kriauto.rest.domain.Notification;
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
				+ " from profile p, agency a, car c " 
				+ " where p.token = ? "
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
				  Location location = getLastLocationByCar(car.getDeviceid(),date,token);
				  if(null != location){
				     locations.add(location);
				  }
		}
				
		for(int j = 0; j<locations.size(); j++){
			Location loc = locations.get(j);
			loc.setSpeed((double)Math.round((loc.getSpeed()*1.85)*10)/10);
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
		
		locations = jdbcTemplate.query(" select distinct ps.longitude, ps.latitude, ps.speed, ps.course, ps.fixtime -'1 hour'::interval AS servertime, to_char(ps.fixtime -'1 hour'::interval,'HH24:MI:SS') AS fixtime,ps.attributes, c.immatriculation, c.vin, c.mark, c.model, c.photo, c.color, ps.deviceid, c.colorCode" 
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

		double log = 0,lat = 0;
		if(locations.size() > 0){
		  for(int i = 0 ; i< locations.size(); i++){
			if(null != locations.get(i)){
			 if(0 == i){
				if(null != locations.get(i))
				log = locations.get(i).getLongitude();
				lat = locations.get(i).getLatitude();
				locations.get(i).setSpeed((double)Math.round((locations.get(i).getSpeed()*1.85)*10)/10);				
				if(null != locations.get(i).getAttributes() && getDistance(locations.get(i).getAttributes()) <= 500){
				   locations1.add(locations.get(i));
				}
			  }else{
				if(log == locations.get(i).getLongitude() && lat == locations.get(i).getLatitude()){
				  }else{
					log = locations.get(i).getLongitude();
					lat = locations.get(i).getLatitude();
					locations.get(i).setSpeed((double)Math.round((locations.get(i).getSpeed()*1.85)*10)/10);				
					double dist = distance(locations.get(i-1).getLatitude(), locations.get(i-1).getLongitude(), locations.get(i).getLatitude(), locations.get(i).getLongitude(), 'K');
					  if(dist <= 1){
						  locations1.add(locations.get(i));
					  }
				   }
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
	public List<Course> getTotalCourseByCar(Integer deviceid, String token) {
		System.out.println("getTotalCourseByCar " + deviceid);
		List<Course> cours = new ArrayList<Course>();
		for(int i =1; i<30; i++){
			GregorianCalendar date = new GregorianCalendar();
			date.add(Calendar.DATE, -i);
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
			String day = formater.format(date.getTime());
			Statistic stat = getCarStatistic(deviceid, day, token);
			Course cor = new Course();
			cor.setDay(day);
			cor.setTotalCourse(String.valueOf(stat.getCourse()));
			cours.add(cor);
		}
		return cours;
	}

	@Override
	public List<Speed> getMaxSpeedByCar(Integer deviceid, String token) {
		System.out.println("getMaxSpeedByCar " + deviceid);
		List<Speed> speeds = new ArrayList<Speed>();
		for(int i =1; i<30; i++){
			GregorianCalendar date = new GregorianCalendar();
			date.add(Calendar.DATE, -i);
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
			String day = formater.format(date.getTime());
			Statistic stat = getCarStatistic(deviceid, day, token);
			Speed speed = new Speed();
			speed.setDay(day);
			speed.setMaxSpeed(String.valueOf(stat.getSpeed()));
			speeds.add(speed);
		}
		return speeds;
	}

	@Override
	public List<Consumption> getTotalConsumptionByCar(Integer deviceid, String token) {
		System.out.println("getTotalConsumptionByCar " + deviceid);
		List<Consumption> consumptions = new ArrayList<Consumption>();
		for(int i =1; i<30; i++){
			GregorianCalendar date = new GregorianCalendar();
			date.add(Calendar.DATE, -i);
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
			String day = formater.format(date.getTime());
			Statistic stat = getCarStatistic(deviceid, day, token);
			Consumption consumption = new Consumption();
			consumption.setDay(day);
			consumption.setConsumption(String.valueOf(stat.getConsumption()));
			consumptions.add(consumption);
		}
		return consumptions;
	}
	
	@Override
	public Statistic getCarStatistic(Integer deviceid, String date, String token) {
		System.out.println("getCarStatistic " + deviceid+date);
		Statistic statistic = new Statistic();
		double speed = 0, cours=0;
		String hour;
		StatisticValues maximalspeed = new StatisticValues();
		StatisticValues maximalcourse = new StatisticValues();
		StatisticValues fuelconsommation = new StatisticValues();
		StatisticValues enginetemperature = new StatisticValues();
		StatisticValues fridgetemperature = new StatisticValues();
		StatisticValues fuellevel = new StatisticValues();
		Map<String,Double> maxspeed = new HashMap<String,Double>();
		Map<String,Double> maxcourse = new HashMap<String,Double>();
		Map<String,Double> maxfuel = new HashMap<String,Double>();
		Car car = getCarByDevice(deviceid,token);		
		List<Location> locations = getAllLocationsByCar(deviceid, date, token);		
		for(int i =0; i < locations.size(); i++){
			Location location = locations.get(i);
			if(null != location && null != location.getFixtime()){
				hour = location.getFixtime().split(":", 0)[0];
				if(null != location.getFixtime() && null != maxspeed.get(hour)){
					if(location.getSpeed() < 150 && maxspeed.get(hour) < location.getSpeed())
						maxspeed.put(hour, location.getSpeed());
				}else{
					if(location.getSpeed() < 150)
					    maxspeed.put(hour, location.getSpeed());
				}
				if(null != location.getFixtime() && null != maxcourse.get(hour)){
					  if( i != 0){
						  double dist = distance(locations.get(i-1).getLatitude(), locations.get(i-1).getLongitude(), locations.get(i).getLatitude(), locations.get(i).getLongitude(), 'K');
						  if(dist <= 1){
							  Double distance = maxcourse.get(hour)+dist;
							  maxcourse.put(hour, distance);
						  }
					   }
				}else{
					   maxcourse.put(hour, 0.0);
				}
			}
			
			if(location.getSpeed() < 150 && location.getSpeed() > speed){
				speed = location.getSpeed();
			}
			if( i != 0){
			  double dist = distance(locations.get(i-1).getLatitude(), locations.get(i-1).getLongitude(), locations.get(i).getLatitude(), locations.get(i).getLongitude(), 'K');
			  if(dist <= 1){
			    cours = cours + dist;
			  }
			}
		}

		if(cours > 0){
		   statistic.setConsumption((double)Math.round((cours*car.getConsumption()/100)*10)/10);
		   statistic.setSpeed(speed);
		   statistic.setCourse((double)Math.round((cours)*10)/10);
		}else{
		   statistic.setConsumption(0.0);
		   statistic.setSpeed(0.0);
		   statistic.setCourse(0.0);
		}
		if(null != car)
		  statistic.setEnable(car.getEnable());
		maximalspeed.setV00(null != maxspeed.get("00") ? maxspeed.get("00") : 0.0);
		maximalspeed.setV01(null != maxspeed.get("01") ? maxspeed.get("01") : 0.0);
		maximalspeed.setV02(null != maxspeed.get("02") ? maxspeed.get("02") : 0.0);
		maximalspeed.setV03(null != maxspeed.get("03") ? maxspeed.get("03") : 0.0);
		maximalspeed.setV04(null != maxspeed.get("04") ? maxspeed.get("04") : 0.0);
		maximalspeed.setV05(null != maxspeed.get("05") ? maxspeed.get("05") : 0.0);
		maximalspeed.setV06(null != maxspeed.get("06") ? maxspeed.get("06") : 0.0);
		maximalspeed.setV07(null != maxspeed.get("07") ? maxspeed.get("07") : 0.0);
		maximalspeed.setV08(null != maxspeed.get("08") ? maxspeed.get("08") : 0.0);
		maximalspeed.setV09(null != maxspeed.get("09") ? maxspeed.get("09") : 0.0);
		maximalspeed.setV10(null != maxspeed.get("10") ? maxspeed.get("10") : 0.0);
		maximalspeed.setV11(null != maxspeed.get("11") ? maxspeed.get("11") : 0.0);
		maximalspeed.setV12(null != maxspeed.get("12") ? maxspeed.get("12") : 0.0);
		maximalspeed.setV13(null != maxspeed.get("13") ? maxspeed.get("13") : 0.0);
		maximalspeed.setV14(null != maxspeed.get("14") ? maxspeed.get("14") : 0.0);
		maximalspeed.setV15(null != maxspeed.get("15") ? maxspeed.get("15") : 0.0);
		maximalspeed.setV16(null != maxspeed.get("16") ? maxspeed.get("16") : 0.0);
		maximalspeed.setV17(null != maxspeed.get("17") ? maxspeed.get("17") : 0.0);
		maximalspeed.setV18(null != maxspeed.get("18") ? maxspeed.get("18") : 0.0);
		maximalspeed.setV19(null != maxspeed.get("19") ? maxspeed.get("19") : 0.0);
		maximalspeed.setV20(null != maxspeed.get("20") ? maxspeed.get("20") : 0.0);
		maximalspeed.setV21(null != maxspeed.get("21") ? maxspeed.get("21") : 0.0);
		maximalspeed.setV22(null != maxspeed.get("22") ? maxspeed.get("22") : 0.0);
		maximalspeed.setV23(null != maxspeed.get("23") ? maxspeed.get("23") : 0.0);
		
		maximalcourse.setV00(null != maxcourse.get("00") ? (double) Math.round(maxcourse.get("00")*10)/10 : 0.0);
		maximalcourse.setV01(null != maxcourse.get("01") ? (double) Math.round(maxcourse.get("01")*10)/10 : 0.0);
		maximalcourse.setV02(null != maxcourse.get("02") ? (double) Math.round(maxcourse.get("02")*10)/10 : 0.0);
		maximalcourse.setV03(null != maxcourse.get("03") ? (double) Math.round(maxcourse.get("03")*10)/10 : 0.0);
		maximalcourse.setV04(null != maxcourse.get("04") ? (double) Math.round(maxcourse.get("04")*10)/10 : 0.0);
		maximalcourse.setV05(null != maxcourse.get("05") ? (double) Math.round(maxcourse.get("05")*10)/10 : 0.0);
		maximalcourse.setV06(null != maxcourse.get("06") ? (double) Math.round(maxcourse.get("06")*10)/10 : 0.0);
		maximalcourse.setV07(null != maxcourse.get("07") ? (double) Math.round(maxcourse.get("07")*10)/10 : 0.0);
		maximalcourse.setV08(null != maxcourse.get("08") ? (double) Math.round(maxcourse.get("08")*10)/10 : 0.0);
		maximalcourse.setV09(null != maxcourse.get("09") ? (double) Math.round(maxcourse.get("09")*10)/10 : 0.0);
		maximalcourse.setV10(null != maxcourse.get("10") ? (double) Math.round(maxcourse.get("10")*10)/10 : 0.0);
		maximalcourse.setV11(null != maxcourse.get("11") ? (double) Math.round(maxcourse.get("11")*10)/10 : 0.0);
		maximalcourse.setV12(null != maxcourse.get("12") ? (double) Math.round(maxcourse.get("12")*10)/10 : 0.0);
		maximalcourse.setV13(null != maxcourse.get("13") ? (double) Math.round(maxcourse.get("13")*10)/10 : 0.0);
		maximalcourse.setV14(null != maxcourse.get("14") ? (double) Math.round(maxcourse.get("14")*10)/10 : 0.0);
		maximalcourse.setV15(null != maxcourse.get("15") ? (double) Math.round(maxcourse.get("15")*10)/10 : 0.0);
		maximalcourse.setV16(null != maxcourse.get("16") ? (double) Math.round(maxcourse.get("16")*10)/10 : 0.0);
		maximalcourse.setV17(null != maxcourse.get("17") ? (double) Math.round(maxcourse.get("17")*10)/10 : 0.0);
		maximalcourse.setV18(null != maxcourse.get("18") ? (double) Math.round(maxcourse.get("18")*10)/10 : 0.0);
		maximalcourse.setV19(null != maxcourse.get("19") ? (double) Math.round(maxcourse.get("19")*10)/10 : 0.0);
		maximalcourse.setV20(null != maxcourse.get("20") ? (double) Math.round(maxcourse.get("20")*10)/10 : 0.0);
		maximalcourse.setV21(null != maxcourse.get("21") ? (double) Math.round(maxcourse.get("21")*10)/10 : 0.0);
		maximalcourse.setV22(null != maxcourse.get("22") ? (double) Math.round(maxcourse.get("22")*10)/10 : 0.0);
		maximalcourse.setV23(null != maxcourse.get("23") ? (double) Math.round(maxcourse.get("23")*10)/10 : 0.0);
		
		fuelconsommation.setV00(null != maxcourse.get("00") ? (double)Math.round((maxcourse.get("00")*car.getConsumption()/100)*10)/10 : 0.0);
		fuelconsommation.setV01(null != maxcourse.get("01") ? (double)Math.round((maxcourse.get("01")*car.getConsumption()/100)*10)/10 : 0.0);
		fuelconsommation.setV02(null != maxcourse.get("02") ? (double)Math.round((maxcourse.get("02")*car.getConsumption()/100)*10)/10 : 0.0);
		fuelconsommation.setV03(null != maxcourse.get("03") ? (double)Math.round((maxcourse.get("03")*car.getConsumption()/100)*10)/10 : 0.0);
		fuelconsommation.setV04(null != maxcourse.get("04") ? (double)Math.round((maxcourse.get("04")*car.getConsumption()/100)*10)/10 : 0.0);
		fuelconsommation.setV05(null != maxcourse.get("05") ? (double)Math.round((maxcourse.get("05")*car.getConsumption()/100)*10)/10 : 0.0);
		fuelconsommation.setV06(null != maxcourse.get("06") ? (double)Math.round((maxcourse.get("06")*car.getConsumption()/100)*10)/10 : 0.0);
		fuelconsommation.setV07(null != maxcourse.get("07") ? (double)Math.round((maxcourse.get("07")*car.getConsumption()/100)*10)/10 : 0.0);
		fuelconsommation.setV08(null != maxcourse.get("08") ? (double)Math.round((maxcourse.get("08")*car.getConsumption()/100)*10)/10 : 0.0);
		fuelconsommation.setV09(null != maxcourse.get("09") ? (double)Math.round((maxcourse.get("09")*car.getConsumption()/100)*10)/10 : 0.0);
		fuelconsommation.setV10(null != maxcourse.get("10") ? (double)Math.round((maxcourse.get("10")*car.getConsumption()/100)*10)/10 : 0.0);
		fuelconsommation.setV11(null != maxcourse.get("11") ? (double)Math.round((maxcourse.get("11")*car.getConsumption()/100)*10)/10 : 0.0);
		fuelconsommation.setV12(null != maxcourse.get("12") ? (double)Math.round((maxcourse.get("12")*car.getConsumption()/100)*10)/10 : 0.0);
		fuelconsommation.setV13(null != maxcourse.get("13") ? (double)Math.round((maxcourse.get("13")*car.getConsumption()/100)*10)/10 : 0.0);
		fuelconsommation.setV14(null != maxcourse.get("14") ? (double)Math.round((maxcourse.get("14")*car.getConsumption()/100)*10)/10 : 0.0);
		fuelconsommation.setV15(null != maxcourse.get("15") ? (double)Math.round((maxcourse.get("15")*car.getConsumption()/100)*10)/10 : 0.0);
		fuelconsommation.setV16(null != maxcourse.get("16") ? (double)Math.round((maxcourse.get("16")*car.getConsumption()/100)*10)/10 : 0.0);
		fuelconsommation.setV17(null != maxcourse.get("17") ? (double)Math.round((maxcourse.get("17")*car.getConsumption()/100)*10)/10 : 0.0);
		fuelconsommation.setV18(null != maxcourse.get("18") ? (double)Math.round((maxcourse.get("18")*car.getConsumption()/100)*10)/10 : 0.0);
		fuelconsommation.setV19(null != maxcourse.get("19") ? (double)Math.round((maxcourse.get("19")*car.getConsumption()/100)*10)/10 : 0.0);
		fuelconsommation.setV20(null != maxcourse.get("20") ? (double)Math.round((maxcourse.get("20")*car.getConsumption()/100)*10)/10 : 0.0);
		fuelconsommation.setV21(null != maxcourse.get("21") ? (double)Math.round((maxcourse.get("21")*car.getConsumption()/100)*10)/10 : 0.0);
		fuelconsommation.setV22(null != maxcourse.get("22") ? (double)Math.round((maxcourse.get("22")*car.getConsumption()/100)*10)/10 : 0.0);
		fuelconsommation.setV23(null != maxcourse.get("23") ? (double)Math.round((maxcourse.get("23")*car.getConsumption()/100)*10)/10 : 0.0);
		
		fuellevel.setV00(20.0);fuellevel.setV01(15.3);fuellevel.setV02(23.0);fuellevel.setV03(23.5);fuellevel.setV04(45.0);fuellevel.setV05(63.0);
		fuellevel.setV06(12.0);fuellevel.setV07(52.0);fuellevel.setV08(63.0);fuellevel.setV09(45.0);fuellevel.setV10(63.0);fuellevel.setV11(63.2);
		fuellevel.setV12(14.0);fuellevel.setV13(21.0);fuellevel.setV14(63.0);fuellevel.setV15(45.0);fuellevel.setV16(63.0);fuellevel.setV17(45.3);
		fuellevel.setV18(23.0);fuellevel.setV19(52.0);fuellevel.setV20(36.2);fuellevel.setV21(36.5);fuellevel.setV22(12.0);fuellevel.setV23(63.0);
		
		enginetemperature.setV00(20.0);enginetemperature.setV01(15.3);enginetemperature.setV02(23.0);enginetemperature.setV03(23.5);enginetemperature.setV04(45.0);enginetemperature.setV05(63.0);
		enginetemperature.setV06(12.0);enginetemperature.setV07(52.0);enginetemperature.setV08(63.0);enginetemperature.setV09(45.0);enginetemperature.setV10(63.0);enginetemperature.setV11(63.2);
		enginetemperature.setV12(14.0);enginetemperature.setV13(21.0);enginetemperature.setV14(63.0);enginetemperature.setV15(45.0);enginetemperature.setV16(63.0);enginetemperature.setV17(45.3);
		enginetemperature.setV18(23.0);enginetemperature.setV19(53.0);enginetemperature.setV20(36.2);enginetemperature.setV21(36.5);enginetemperature.setV22(12.0);enginetemperature.setV23(63.0);
		
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
				    + " where p.token = ? "
				    + " and p.agencyid = a.id "
				    + " and a.id = c.agencyid "
				    + " and c.deviceid = ? "
				    + " and c.deviceid = ps.deviceid "
				    + " and ps.id =  (select MAX(ps.id) from profile p, agency a, car c, positions ps where p.token = ? and p.agencyid = a.id and a.id = c.agencyid and c.deviceid = ps.deviceid and ps.deviceid = ?  and to_char(fixtime,'YYYY-MM-DD') <= ? and valid = true )",new Object[] {token, deviceid, token, deviceid, date}, new BeanPropertyRowMapper(Location.class));
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
				+ " and   ps.fixtime  >= '"+ time + "'"
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
		if(distance.indexOf("temp1") == 0){
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
		return 0;
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
			    + " and   (ps.attributes like '%\"power\":0.0%' or ps.attributes like '%\"alarm\":\"lowBattery\"%') "
				+ " and   to_char(ps.fixtime,'yyyy-MM-ss HH24:MI:SS')  >= ? ",new Object[] {deviceid, date}, new BeanPropertyRowMapper(Location.class));
		if(null != locations && locations.size() > 0){
			return true;
		}else{
			return false;
		}
	}
}
