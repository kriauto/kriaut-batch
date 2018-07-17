package ma.kriauto.rest.dao;

import ma.kriauto.rest.domain.Agency;

public interface AgencyDao {
	public Agency getAgencyByToken(String token);
	public void updateAgency(Agency agency);

}
