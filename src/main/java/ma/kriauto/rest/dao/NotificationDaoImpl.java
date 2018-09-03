package ma.kriauto.rest.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import ma.kriauto.rest.domain.Item;
import ma.kriauto.rest.domain.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("notificationDao")
public class NotificationDaoImpl implements NotificationDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void addNotification(Notification notification) {
		// TODO Auto-generated method stub
		System.out.println("addNotification ");
	    jdbcTemplate.update("INSERT INTO messages(deviceid, texte) VALUES (?,?)", new Object[] {Integer.valueOf(notification.getDeviceid()),notification.getTexte()});
		
	}

	@Override
	public List<Notification> getNotificationByDevice(Integer deviceid, String date, String token) {
		System.out.println("getNotificationByDevice " + deviceid);
		GregorianCalendar currentdate = new GregorianCalendar();
		//currentdate.add(Calendar.MONTH, -1);
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		String day = formater.format(currentdate.getTime());
		if(null == date)
			 date = day ;
		List<Notification> notifications = new ArrayList<Notification>();
		notifications = jdbcTemplate.query(" select to_char(m.creationdate , 'HH24:MI:SS') AS creationdate, m.texte "
						+ " from  profile p, agency a, car c, messages m" 
						+ " where p.token = ? "
					    + " and   p.agencyid = a.id "
					    + " and   a.id = c.agencyid "
					    + " and   c.deviceid = m.deviceid "
					    + " and   m.deviceid = ? "
						+ " and   to_char(creationdate,'YYYY-MM-DD') = ? order by creationdate desc",new Object[] {token,deviceid,date}, new BeanPropertyRowMapper(Notification.class));
		return notifications;
	}
	
	@Override
	public List<Item> getDatesNotificationByDevice(Integer deviceid) {
		System.out.println("getDatesNotificationByDevice " + deviceid);
		List<Item> items = new ArrayList<Item>();
		items = jdbcTemplate.query(" select distinct to_char(creationdate , 'YYYY-MM-DD') AS code, to_char(creationdate , 'YYYY-MM-DD') AS label "
						+ "  from  messages" 
				        + "  where deviceid = ?  order by code desc",new Object[] { deviceid }, new BeanPropertyRowMapper(Item.class));
		return items;
	}

	@Override
	public List<Notification> getPushTokenByProfile(String login) {
		System.out.println("getPushTokenByUser " + login);
		GregorianCalendar date = new GregorianCalendar();
		date.add(Calendar.MONTH, -1);
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		String day = formater.format(date.getTime());
		List<Notification> notifications = new ArrayList<Notification>();
		notifications = jdbcTemplate.query(" SELECT * FROM pushnotification WHERE login = ? ",new Object[] {login}, new BeanPropertyRowMapper(Notification.class));
		return notifications;
	}

}
