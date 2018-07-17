package ma.kriauto.rest.dao;

import java.util.ArrayList;
import java.util.List;

import ma.kriauto.rest.domain.Contact;
import ma.kriauto.rest.domain.ContactCity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("contactDao")
public class ContactDaoImpl implements ContactDao {
	
	@Autowired
    JdbcTemplate jdbcTemplate;

	@Override
	public List<ContactCity> getContacts() {
		System.out.println("getContacts ");
		 List<ContactCity> contactcitys = new ArrayList<ContactCity>();
		 List<Contact>  citys = new ArrayList<Contact>();
		 
		 citys = jdbcTemplate.query("select distinct city "
	               +" from contact order by city",new Object[] {}, new BeanPropertyRowMapper(Contact.class));
		 
         for(int i =0; i<citys.size();i++){
        	 ContactCity contactcity = new ContactCity();
        	 contactcity.setCity(citys.get(i).getCity());
        	 List<Contact> contacts = new ArrayList<Contact>();
        	 contacts = jdbcTemplate.query("select * "
  	               +" from contact "
  	               +" where city = ? ",new Object[] {citys.get(i).getCity()}, new BeanPropertyRowMapper(Contact.class));
        	 contactcity.setContact(contacts);
        	 contactcitys.add(contactcity);
		 }
		 
		 return contactcitys; 
	}
}
