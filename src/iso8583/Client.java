
package iso8583;

/**
 *
 * @author kreshan
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.packager.ISO87BPackager;

public class Client {

public static void main(String[] args)  {
    Socket socket = null;
    String host = "127.0.0.1";
    byte[] requ=null;
    byte[] response  = new byte[500];
   try{
    socket = new Socket(host, 1234);
    ISOMsg m = new ISOMsg();
        m.setPackager(new ISO87BPackager());
        m.set(0, "0101");
        m.set(3, "002200");
        m.set(43, "111");
        m.set(57, "15|10");
 
        //requ=m.pack();
        requ=ISOUtil.hex2byte("006701002220000008A000880301000529094741000004303532393039343734313434353030303030303531313120202020202020202020202020202020202020202020202020202020202020202020202020001630317C3838383838383838385600000000023031");
           System.out.println("Req:"+ISOUtil.hexString(requ));
           DataOutputStream dataout = new DataOutputStream(socket.getOutputStream());
           DataInputStream datain = new DataInputStream(socket.getInputStream());
        dataout.flush();
        System.out.println("writing.....");
        dataout.write(requ);
        dataout.flush();
 
 
        int reslent = datain.read(response,0,response.length);
        System.out.println("Recived massage from Host lenth : " + reslent);
        byte [] printResponse = new byte[reslent-2];
        byte [] length = new byte[2]; //remove header(lenth)

        for(int i=0;i<reslent;i++){
         if (i >= 2)
          printResponse[i-2]=response[i];
         else
          length[i] = response[i];
        }
        System.out.println("Recived massage from Host :"+ISOUtil.hexString(length));
        System.out.println("Recived massage from Host :"+ISOUtil.hexString(printResponse));
 
        dataout.flush();
        dataout.close();
        datain.close();
        socket.close();
   }catch(Exception e){
    e.printStackTrace();
   }
}
}
