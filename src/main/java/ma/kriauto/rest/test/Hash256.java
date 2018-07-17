package ma.kriauto.rest.test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

public class Hash256 {
	
	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {

	    // TODO Auto-generated method stub

	    String textToHash = "admin:admin";

	    MessageDigest digest = MessageDigest.getInstance("SHA-256");

	  byte[] byteOfTextToHash = textToHash.getBytes("UTF-8");

	  byte[] hashedByetArray = digest.digest(byteOfTextToHash);

	  String encoded = Base64.getEncoder().encodeToString(hashedByetArray);

	  System.out.println("encoded -> "+encoded);

	 

	  GeoApiContext gtx = new GeoApiContext().setApiKey("AIzaSyB5DReW25MUyC1U8q7KKqI_7fbecvv6qkI");

	  try {

	      GeocodingResult[] gResp = GeocodingApi.newRequest(gtx).latlng(new LatLng(33.979693, -6.837063)).await();

	      System.out.println(gResp[0].formattedAddress);

	  } catch (Exception e) {

	      // TODO Auto-generated catch block

	      e.printStackTrace();

	  }
	}

}
