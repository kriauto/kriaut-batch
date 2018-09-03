package ma.kriauto.rest.service;

import ma.kriauto.rest.dao.AgencyDao;
import ma.kriauto.rest.domain.Agency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("agencyService")
public class AgencyServiceImpl implements AgencyService {
	
	@Autowired
	AgencyDao agencydao;

	@Override
	public void updateAgency(Agency agency) {
		agencydao.updateAgency(agency);
	}

	@Override
	public Agency getAgencyByToken(String token) {
		// TODO Auto-generated method stub
		return agencydao.getAgencyByToken(token);
	}

}
