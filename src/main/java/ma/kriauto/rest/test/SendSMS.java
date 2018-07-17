package ma.kriauto.rest.test;

import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;






public class SendSMS {
	public static void main(String[] args) {
	//HttpClient httpClient = new DefaultHttpClient(); //Deprecated
	HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 

	try {
	    HttpPost request = new HttpPost("http://panel.smspm.com/gateway/c9ecee9259af98dccfef3603371fea26e267739a/api.v1/send?phone=0033617638348&sender=SMSPM.com&message=This+is+a+test+message&output=json");
	    HttpResponse response = httpClient.execute(request);
	    System.out.println("status -> "+response.getStatusLine().getStatusCode());
	    // handle response here...
	}catch (Exception ex) {
	    // handle exception here
	} finally {
	    httpClient.getConnectionManager().shutdown(); //Deprecated
	}
  }
    
}


