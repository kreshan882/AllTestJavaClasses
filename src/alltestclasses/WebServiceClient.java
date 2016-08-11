package alltestclasses;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author kreshan
 */
public class WebServiceClient {
    public static void main(String[] args) {
       	URL url				= null;
	HttpURLConnection conn 		= null;
	BufferedReader br 		= null;
	DataOutputStream dataout 	= null;
	String response                 = null;
	try{

                url = new URL("http://127.0.0.1:8080/WebApplication1/webresources/generic/checkStatus");
                String input = "0000000014|123456";
		
                conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setConnectTimeout(10000);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "text/plain");
		
		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

		if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			response = br.readLine();
			System.out.println("response>>>"+response);

		}
	}catch(Exception e){
		e.printStackTrace();
	}finally{
                try {
                    if(conn !=null)conn.disconnect();
                    if(dataout !=null)   dataout.close();
                    if(br !=null)br.close();
                } catch (IOException ex) {
                    
                }
	}

    }
}
