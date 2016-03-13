/*

 * A simple TCP client that sends messages to a server and display the message
   from the server. 
 * For use in CPSC 441 lectures
 * Instructor: Prof. Mea Wang
 */

package Network;
import java.io.*; 
import java.net.*; 

public class TCPClient
{ 
	private int port = 9000;
	private String stringIP = "192.168.0.5";
	
    public TCPClient() throws Exception
    {
    	InetAddress ip = InetAddress.getByName(stringIP);
    	
    	// Initialize a client socket connection to the server
        Socket clientSocket = new Socket(ip,port); 

        // Initialize input and an output stream for the connection(s)
        DataOutputStream outBuffer = 
          new DataOutputStream(clientSocket.getOutputStream()); 
        BufferedReader inBuffer = 
          new BufferedReader(new
          InputStreamReader(clientSocket.getInputStream())); 

        // Initialize user input stream
        String line; 
        BufferedReader inFromUser = 
        new BufferedReader(new InputStreamReader(System.in)); 

        // Get user input and send to the server
        // Display the echo meesage from the server
        System.out.print("Please enter a message to be sent to the server ('logout' to terminate): ");
        line = inFromUser.readLine(); 
        while (!line.equals("logout"))
        {
            // Send to the server
            outBuffer.writeBytes(line + '\n'); 
            
            // Getting response from the server
            line = inBuffer.readLine();
            System.out.println("Server: " + line);
             
            System.out.print("Please enter a message to be sent to the server ('logout' to terminate): ");
            line = inFromUser.readLine(); 
        }

        // Close the socket
        clientSocket.close();  
    }

    public static void main(String args[]) throws Exception 
    { 
    	TCPClient client = new TCPClient();
        if (args.length != 2)
        {
            System.out.println("Usage: TCPClient <Server IP> <Server Port>");
            System.exit(1);
        }
                 
    } 
    

} 
