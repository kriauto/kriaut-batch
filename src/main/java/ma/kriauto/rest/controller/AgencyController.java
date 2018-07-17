package ma.kriauto.rest.controller;

import ma.kriauto.rest.domain.Agency;
import ma.kriauto.rest.domain.Profile;
import ma.kriauto.rest.domain.ResponseMessage;
import ma.kriauto.rest.service.AgencyService;
import ma.kriauto.rest.service.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for agency actions.
 */
@Controller
public class AgencyController {
	
	@Autowired
    private AgencyService agencyService;
	
	@Autowired
    private ProfileService profileService;
	
	@RequestMapping(value = "/getAgency", method = RequestMethod.GET)
    @ResponseBody
    public Agency getAgency(@RequestHeader(value="Authorization") String authorization) {
    	System.out.println("Begin getAgency -->"+authorization);
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token);
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	Agency agency = agencyService.getAgencyByToken(token);
    	return agency;
    }
	
	@RequestMapping(value = "/updateAgency", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage updateAgency(@RequestHeader(value="Authorization") String authorization, @RequestBody Agency agency) {
    	System.out.println("Begin updateAgency -->"+agency);
    	String token = authorization.replaceAll("Basic", "");
    	Profile profile = profileService.getProfileByToken(token);
    	if(null == profile){
    		throw new IllegalArgumentException("ACTION_FAILED");
    	}
    	agencyService.updateAgency(agency);
    	return new ResponseMessage(ResponseMessage.Type.success, null,null);
    }

}
