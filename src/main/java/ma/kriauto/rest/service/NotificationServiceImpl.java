package ma.kriauto.rest.service;

import java.util.List;

import ma.kriauto.rest.dao.NotificationDao;
import ma.kriauto.rest.domain.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {
	
	@Autowired
	NotificationDao notificationdao;

	@Override
	public void addNotification(Notification notification) {
		// TODO Auto-generated method stub
		notificationdao.addNotification(notification);
		
	}

	@Override
	public List<Notification> getNotificationByDevice(Integer deviceid, String date) {
		// TODO Auto-generated method stub
		return notificationdao.getNotificationByDevice(deviceid,date);
	}

	@Override
	public List<Notification> getPushTokenByProfile(String login) {
		// TODO Auto-generated method stub
		return notificationdao.getPushTokenByProfile(login);
	}

}
