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
        
        try {
            socket = new DatagramSocket(myPort);
        }
        catch(SocketException sE) {
        }
    }
    
    //Receives datagram packets
    public void run() {
        
     byte[] buffer = new byte[5000];
     System.out.println("Yes Recieve");
        while(true) {
            System.out.println("Yes Recieve while");
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
                InputStream in=new ByteArrayInputStream(packet.getData(), packet.getOffset(),
                packet.getLength());
                DataInputStream din=new DataInputStream(in);
                    
                //Reads 3 values: an int, then a char, and then an int again
                int oldx = din.readInt(); // x-position of a dot/point
                char tabOne = din.readChar(); //reads away the tab character
                int oldy = din.readInt(); // y-position of a dot/point
                
                int newX = din.readInt();
                char tabTwo = din.readChar();
                int newY = din.readInt();
                
                // Updates the canvas with the line that we got from the client to the server.
                
                paper.UpdatedLine(oldx, oldy, newX, newY);
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