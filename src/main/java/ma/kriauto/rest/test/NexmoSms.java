package ma.kriauto.rest.test;

import java.io.IOException;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import com.nexmo.client.sms.SmsSubmissionResult;
import com.nexmo.client.sms.messages.TextMessage;

public class NexmoSms {

	public static void main(String[] args) throws IOException, NexmoClientException {
		AuthMethod auth = new TokenAuthMethod("9db9ffd9", "DKADpFPTj0RJbRq2");
		NexmoClient client = new NexmoClient(auth);
		System.out.println("FROM_NUMBER");

		SmsSubmissionResult[] responses = client.getSmsClient().submitMessage(new TextMessage(
		        "kriauto",
		        "0033617638348",
		        "la voiture est arreté "));
		for (SmsSubmissionResult response : responses) {
		    System.out.println(response);
		}
	}
}
