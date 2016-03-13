package Network;
import java.awt.*;
import java.net.*;
import java.io.*;


public class Sender {
    private int toPort;
    private InetAddress toAdr;
    private DatagramSocket socket;
    
   
    public Sender(InetAddress toAdr, int toPort) {
        this.toAdr = toAdr;
        this.toPort = toPort;
        try {
            socket=new DatagramSocket();
        }
        catch(SocketException e){
        }
    }
    
    /*Sends datagram packets*/
    public  void sendAway(Point p) {
        
        try {
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            DataOutputStream dos=new DataOutputStream(baos);
            dos.writeInt(p.x);
            dos.writeChar('\t');//the tab character is used as a separator
            dos.writeInt(p.y);
            dos.close();
            byte[]data=baos.toByteArray();
            DatagramPacket packet=new DatagramPacket(data,
            data.length, toAdr, toPort);
            socket.send(packet);
            baos.close();
            dos.close();
        }
        catch(IOException ioe) {
            System.err.println(ioe);
        } 
    } 
    
    
    // Closes the socket
    public void closeSendSocket() {
        
        try {
            socket.close();
        }
        catch(Exception e) {
        }
    }
} //End class Sender



