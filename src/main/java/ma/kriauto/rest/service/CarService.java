package ma.kriauto.rest.service;

import java.text.ParseException;
import java.util.List;

import ma.kriauto.rest.domain.Car;
import ma.kriauto.rest.domain.Consumption;
import ma.kriauto.rest.domain.Course;
import ma.kriauto.rest.domain.Event;
import ma.kriauto.rest.domain.Item;
import ma.kriauto.rest.domain.Location;
import ma.kriauto.rest.domain.Notification;
import ma.kriauto.rest.domain.Speed;
import ma.kriauto.rest.domain.Statistic;

public interface CarService {
	public Car getCarByDevice(Integer deviceid, String token);
	public void updateCar(Car car) throws ParseException;
	public List<Car> getAllCarsByToken(boolean group, String token);
	public List<Car> getAllCarsByProfile(String login);
	public List<Item> getAllDatesByCar(Integer deviceid);
	public List<Item> getAllDatesByToken(String token);
	public List<Location> getAllLocationsByToken(String token, String date);
	public List<Location> getAllLocationsByCar(Integer deviceid, String date, String token);
	public List<Course> getTotalCourseByCar(Integer deviceid, String token);
	public List<Speed> getMaxSpeedByCar(Integer deviceid, String token);
	public Speed getMaxSpeedByCarTime(Integer deviceid, String date);
	public List<Consumption> getTotalConsumptionByCar(Integer deviceid, String token);
	public Statistic getCarStatistic(Integer deviceid, String date, String token);
	public List<Notification> getDataNotification(int type);
	//public Location getLastPositionByCar(Integer deviceid);
	public List<Location> getAllLocationByCarTime(Integer deviceid, String time);
	public void initGeoFence();
	public Event getLastEvent(Integer deviceid, String date);
	public List<Location> getLocationsBefore(Integer deviceid, Integer to, String date);
	public List<Location> getLocationsAfter(Integer deviceid, Integer from, String date);
	public List<Location> getLocationsByDate(Integer deviceid, String date);
	public List<Location> getLocationsBetween(Integer deviceid, Integer from, Integer to, String date);
	public Location getLocationById(Integer id);
	public Location getLastLocationByCar(Integer deviceid, String date, String token);
	public Location getLastLocationByCar(Integer deviceid, String token);
	public String getGoodleAdresse(Double Lat, Double Lng);
	public Event getLastEvent(Integer deviceid);
	public double distance(double lat1, double lon1, double lat2, double lon2, char unit);
	public List<Car> getCarsWithAddress(boolean group, String token, boolean withaddress);
	public boolean isDeviceDisconnected(Integer deviceid, String date);
}
