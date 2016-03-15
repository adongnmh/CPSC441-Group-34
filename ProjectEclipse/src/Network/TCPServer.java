/*
 * A simple TCP server that echos messages back to the client.
 * For use in CPSC 441 lectures
 * Instructor: Prof. Mea Wang
 */
package Network;
import java.io.*;
import java.net.*;

import GUI.DrawingCanvas;

public class TCPServer extends Thread{
	
	private static final String DataInputStream = null;
	private Socket clientSocket;
	private ServerSocket echoServer;
	private DrawingCanvas paper;
	
	private int port = 9000;
	
	public  TCPServer(int myPort, DrawingCanvas paper) throws Exception
	{
		
		System.out.println("Hi");
		
        this.port = myPort;
        this.paper = paper;
        this.echoServer = echoServer;
        
        // Try to open a server socket on the given port
        // Note that we can't choose a port less than 1023 if we are not
        // privileged users (root)
        try {
            echoServer = new ServerSocket(port);
        }
        catch (IOException e) {
            System.out.println(e);
        }

	}
	
	public void run()
	{
		try {
			clientSocket = echoServer.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	     byte[] buffer = new byte[5000];
	     System.out.println("Yes Recieve");
         InputStream in=new ByteArrayInputStream(buffer);

         try {
			DataOutputStream outBuffer = new DataOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         DataInputStream din = new DataInputStream(in);
         

	     while(true)
	     {
	    	 try
	    	 {
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
	
}

        

