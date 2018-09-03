package ma.kriauto.rest.test;

import java.util.*;

public class SendBlue {
      public static void main(String[] args) {
        Mailin http = new Mailin("https://api.sendinblue.com/v2.0","UfsbL9W8tCO3FQA0");
		Map < String, String > to = new HashMap < String, String > ();
			to.put("contact@kriauto.ma", "contact@kriauto.ma");
		Map < String, String > cc = new HashMap < String, String > ();
			cc.put("contact@kriauto.ma", "contact@kriauto.ma");
		Map < String, String > bcc = new HashMap < String, String > ();
			bcc.put("contact@kriauto.ma", "contact@kriauto.ma");
//		Map < String, String > attachment = new HashMap < String, String > ();
//    		attachment.put("myfilename.pdf", "your_pdf_files_base64_encoded_chunk_data");
		Map < String, String > headers = new HashMap < String, String > ();
			headers.put("Content-Type", "text/html; charset=iso-8859-1");
			headers.put("X-param1", "value1");
			headers.put("X-param2", "value2");
			headers.put("X-Mailin-custom", "my custom value");
			headers.put("X-Mailin-IP", "102.102.1.2");
			headers.put("X-Mailin-Tag", "My tag");
//		Map < String, String > inline_image = new HashMap < String, String > ();
//				inline_image.put("myinlineimage1.png", "your_png_files_base64_encoded_chunk_data");
//				inline_image.put("myinlineimage2.jpg", "your_jpg_files_base64_encoded_chunk_data");
		Map < String, Object > data = new HashMap < String, Object > ();
			data.put("to", to);
			data.put("cc", cc);
			data.put("bcc", bcc);
			data.put("replyto", new String [] {"contact@kriauto.ma","contact@kriauto.ma"});
			data.put("from", new String [] {"contact@kriauto.ma","contact@kriauto.ma"});
			data.put("subject", "My subject");
			data.put("html", "This is the <h1>HTML</h1><br/>");
//				" This is inline image 1.<br/>"
//				<img src=\"{myinlineimage1.png}\" alt=\"image1\" border=\"0\"><br/>
//				Some text<br/>
//				This is inline image 2.<br/>
//				<img src=\"{myinlineimage2.jpg}\" alt=\"image2\" border=\"0\"><br/>
//				Some more text<br/>
//				Re-used inline image 1.<br/>
//				<img src=\"{myinlineimage1.png}\" alt=\"image3\" border=\"0\">");
			data.put("text", "This is text");
			//data.put("attachment", attachment);
			//data.put("headers", headers);
			//data.put("inline_image", inline_image);
 
		String str = http.send_email(data);
        System.out.println(str);
      }
    }