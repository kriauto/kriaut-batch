package ma.kriauto.rest.controller;

import java.util.List;

import ma.kriauto.rest.domain.ContactCity;
import ma.kriauto.rest.domain.Profile;
import ma.kriauto.rest.service.ContactService;
import ma.kriauto.rest.service.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for contact actions.
 */
@Controller
public class ContactController {
	
	@Autowired
    private ContactService contactService;
	
	@Autowired
    private ProfileService profileService;
	
	@RequestMapping(value = "/getContacts", method = RequestMethod.GET)
    @ResponseBody
    public List<ContactCity> getContacts(){
       System.out.println("getContacts -->");
       return contactService.getContacts();
    }
}
