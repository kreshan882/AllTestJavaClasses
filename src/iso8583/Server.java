
package iso8583;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.packager.ISO87BPackager;



public class Server {
 private static byte []actualRequest  = null;
 private static byte []printRequest   = null;

    public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = null;
    Socket socket = null;

    
    try {
        serverSocket = new ServerSocket(1234);
        socket = serverSocket.accept();
        socket.setKeepAlive(true);
        socket.setSoTimeout(50000);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.flush();
  
        byte []request = new byte[2048];

        int relen = in.read(request, 0,500);
        System.out.println("resived from switch lenth: " + relen);
        if(relen > 2){
         actualRequest = new byte[relen-2];
         printRequest  = new byte[relen];
         for(int i=0 ; i<relen;i++){
          printRequest[i]=request[i];
          if(i>=2){
           actualRequest[i-2]=request[i];
          }
         } 
        }
        System.out.println("Recived from Switch > "+ISOUtil.hexString(printRequest));
  
     ISOMsg m = new ISOMsg();
        m.setPackager(new ISO87BPackager());
        m.unpack(printRequest);
        m.unset(43);
        m.unset(57);
        m.set(39,"00");
  
  
        byte responseForSwitch[] = m.pack();
        System.out.println("Response to Switch > "+ ISOUtil.hexString(responseForSwitch));

        out.write(responseForSwitch);
        out.flush();

    } catch (Exception ex) {
        ex.printStackTrace();
    }


    socket.close();
    serverSocket.close();
}
}
