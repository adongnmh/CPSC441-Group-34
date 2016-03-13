package Network;
import java.net.*;
import java.io.*;
import java.awt.*;
import GUI.*;


public class Receiver extends Thread {
    
    private int myPort;
    private DrawingCanvas paper;
    private DatagramSocket socket;
    
    
    public Receiver(int myPort,DrawingCanvas paper) {
    
        this.myPort = myPort;
        this.paper = paper;
        this.socket = socket;
        try {
            socket = new DatagramSocket(myPort);
        }
        catch(SocketException sE) {
        }
    }
    
    //Receives datagram packets
    public void run() {
        
     byte[] buffer = new byte[512];
        while(true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
                InputStream in=new ByteArrayInputStream(packet.getData(), packet.getOffset(),
                packet.getLength());
                DataInputStream din=new DataInputStream(in);
                    
                //Reads 3 values: an int, then a char, and then an int again
                int x = din.readInt(); // x-position of a dot/point
                char c = din.readChar(); //reads away the tab character
                int y = din.readInt(); // y-position of a dot/point
                Point p = new Point(x , y);
                //paper.addPoint(p);
                din.close();
            }	
            catch(IOException iE) {
            }
        } 
    }
    
    
    // Closes the receiving socket
    public void closeReceiveSocket() {
        
        try {
            socket.close();
        }
        catch(Exception e) {
        }
    }
} // End class Receiver