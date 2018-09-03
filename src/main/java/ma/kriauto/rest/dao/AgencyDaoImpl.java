package ma.kriauto.rest.dao;

import ma.kriauto.rest.domain.Agency;
import ma.kriauto.rest.domain.Profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("agencyDao")
public class AgencyDaoImpl implements AgencyDao {
	
	@Autowired
    JdbcTemplate jdbcTemplate;

	@Override
	public void updateAgency(Agency agency) {
		   System.out.println("updateAgency "+agency);
		   jdbcTemplate.update("UPDATE agency set name =  ?, code =  ?, city =  ?, address =  ?, phone =  ?, fax =  ?, carnumber =  ?  WHERE id = ?  ", new Object[] { agency.getName(), agency.getCode(), agency.getCity(), agency.getAddress(), agency.getPhone(), agency.getFax(), agency.getCarnumber(),agency.getId()});
    }

	@Override
	public Agency getAgencyByToken(String token) {
		System.out.println("getProfileByToken "+token);
		try{
		    Agency agency = (Agency) jdbcTemplate.queryForObject("SELECT a.* FROM profile p, agency a where p.agencyid = a.id and  p.token = ? ", new Object[] { token }, new BeanPropertyRowMapper(Agency.class));
            return agency;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
