package ma.kriauto.rest.service;

import ma.kriauto.rest.domain.Agency;

public interface AgencyService {

	public Agency getAgencyByToken(String token);
	public void updateAgency(Agency agency);

}
