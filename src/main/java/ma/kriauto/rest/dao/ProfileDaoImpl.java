package ma.kriauto.rest.dao;

import java.util.ArrayList;
import java.util.List;

import ma.kriauto.rest.domain.Car;
import ma.kriauto.rest.domain.Profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("profileDao")
public class ProfileDaoImpl implements ProfileDao {
	
	@Autowired
    JdbcTemplate jdbcTemplate;

	@Override
	public Profile getProfileByLogin(String login) {
		System.out.println("getProfileByIdentifiants "+login);
		try{
		   Profile profile = (Profile) jdbcTemplate.queryForObject("SELECT * FROM profile where login = ?", new Object[] { login}, new BeanPropertyRowMapper(Profile.class));
		   return profile;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Profile getProfileByToken(String token) {
		System.out.println("getProfileByToken "+token); 
		try{
			Profile profile = (Profile) jdbcTemplate.queryForObject("SELECT * FROM profile where token = ? ", new Object[] { token }, new BeanPropertyRowMapper(Profile.class));
			return profile;
	    } catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Profile getProfileByMail(String mail) {
		System.out.println("getProfileByMail "+mail);
		try{
			Profile profile = (Profile) jdbcTemplate.queryForObject("SELECT * FROM profile where mail = ? ", new Object[] { mail }, new BeanPropertyRowMapper(Profile.class));
			return profile;
	    } catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public Profile getProfileById(int id) {
		System.out.println("getProfileById "+id);
		try{
			Profile profile = (Profile) jdbcTemplate.queryForObject("SELECT * FROM profile where id = ? ", new Object[] { id }, new BeanPropertyRowMapper(Profile.class));
			return profile;
	    } catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void updateProfile(Profile profile) {
		System.out.println("updateProfile "+profile);
		jdbcTemplate.update("UPDATE profile set agencyid =  ?, login =  ?, password =  ?, name =  ?, mail =  ?, phone =  ?, job =  ?, label =  ?, token =  ?, googlekey =  ? WHERE id = ?  ", new Object[] { profile.getAgencyid(), profile.getLogin(), profile.getPassword(), profile.getName(), profile.getMail(), profile.getPhone(), profile.getJob(), profile.getLabel(), profile.getToken(), profile.getGooglekey(), profile.getId()});
		
	}

	@Override
	public void addPushNotifProfile(Profile profile) {
		System.out.println("addPushNotifProfile "+profile);
		jdbcTemplate.update("INSERT INTO pushnotification(login, pushnotiftoken,pushplateforme) VALUES(?,?,?) ", new Object[] {profile.getLogin(), profile.getPushnotiftoken(), profile.getPushplateforme()});
		
	}

	@Override
	public void deletePushNotifProfile(Profile profile) {
		System.out.println("deletePushNotifProfile "+profile);
		jdbcTemplate.update("DELETE FROM pushnotification WHERE login = ? and pushnotiftoken = ?", new Object[] {profile.getLogin(), profile.getPushnotiftoken()});
		
	}

	@Override
	public List<Profile> getAllProfiles() {
		// TODO Auto-generated method stub
		System.out.println("getAllProfiles");
		List<Profile> profiles = new ArrayList<Profile>();
		profiles = jdbcTemplate.query("SELECT * FROM profile ",new Object[] {  }, new BeanPropertyRowMapper(Profile.class));
		return profiles;
	}
}
