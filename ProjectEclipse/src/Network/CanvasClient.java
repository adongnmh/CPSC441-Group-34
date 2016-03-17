package Network;
import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class CanvasClient extends Thread{

	private int port = 9000;
	private Socket clientSocket;
	
	public CanvasClient () 
	{
		try {
			clientSocket = new Socket("169.254.245.161", 9000);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void run()
	{
		
	}

	
	//Send the username and password to the server for validation
	public boolean loginRequest(String username, String password) throws IOException
	{
		DataOutputStream outBuffer = new DataOutputStream(clientSocket.getOutputStream()); 
		BufferedReader inBuffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
		outBuffer.writeBytes(username + " " + password + '\n'); 
		
		// Getting response from the server
        String line = inBuffer.readLine();
        System.out.println("Server: " + line);
        
        if(line.equals("Tan Quach"))
		{
        	System.out.println("we innn");
			return true;
		}
		return false;
	}
}
