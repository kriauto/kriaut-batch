package ma.kriauto.rest.service;

import java.util.List;

import ma.kriauto.rest.dao.ContactDao;
import ma.kriauto.rest.domain.ContactCity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("contactService")
public class ContactServiceImpl implements ContactService {
	
	@Autowired
	ContactDao contactdao;

	@Override
	public List<ContactCity> getContacts() {
		// TODO Auto-generated method stub
		return contactdao.getContacts();
	}
}
