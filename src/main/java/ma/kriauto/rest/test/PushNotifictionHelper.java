package ma.kriauto.rest.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class PushNotifictionHelper {
	 
public final static String AUTH_KEY_FCM = "AAAAGCh6u8g:APA91bGM-jPzZI1BIasa0IdW6SUNCXAa78mWXI0mACvYXmawU5ptyT3iCIjcEhS1_b7V6XaEwsuL-rppJ_AgH_O1Q_XBXttUYoVIlwVamJEr6grmo4qxWGWPMELZar1bRsXCpJCaEaFq";
public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
 
    public static String sendPushNotification(String deviceToken)
            throws IOException {
        String result = "";
        URL url = new URL(API_URL_FCM);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
 
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
 
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + AUTH_KEY_FCM);
        conn.setRequestProperty("Content-Type", "application/json");
 
        
        try {
        	JSONObject json = new JSONObject();
        	 
            json.put("to", deviceToken.trim());
            JSONObject info = new JSONObject();
            info.put("sound", "default"); // Notification title
            info.put("body", "ceci est un test de notifications toto titiceci est un test de notifications toto titiceci est un test de notifications toto titiceci est un test de notifications toto titi"); // Notification
                                                                    // body
            json.put("notification", info);
            OutputStreamWriter wr = new OutputStreamWriter(
                    conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();
 
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
 
            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            result = "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            result = "FAILURE";
        }
        System.out.println("GCM Notification is sent successfully");
 
        return result;
}
    public static void main(String[] args){
    	String token = "dBcZ-8QWw1w:APA91bF6y6ARs9FtdFjks7ceReXGxHxgAm1f2NPrNHiiZYp-gy35JJQ"
+"wUgwr0YoCGvgXmvBrxPSNTcSXg_1A9mdJUR7lsR1JLHN9TLxj51Z8sO9gmER50FOnbeDANVM3ozedXgS"
+"LxOSZfkghbEa2CVDmy_EC0_8dnQ";
//    	String token = "8e44805c397a33b88b340730361b9bd674fd64a582219ee78cc57d597ba4a625";
    	try {
			sendPushNotification(token);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
