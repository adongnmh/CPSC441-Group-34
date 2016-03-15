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

	
	private Socket clientSocket;
	
    public TCPClient(InetAddress IP, int portNum) throws Exception
    {
    	// Initialize a client socket connection to the server
        clientSocket = new Socket(IP,portNum); 
    }
    
    public void SendAway(int oldX, int oldY, int newX, int newY) throws Exception
    {
        // Initialize input and an output stream for the connection(s)
        DataOutputStream outBuffer = 
          new DataOutputStream(clientSocket.getOutputStream()); 
        BufferedReader inBuffer = 
          new BufferedReader(new
          InputStreamReader(clientSocket.getInputStream())); 
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        
        dos.writeInt(oldX);
        dos.writeChar('\t');//the tab character is used as a separator
        dos.writeInt(oldY);
        
        dos.writeInt(newX);
        dos.writeChar('\t');//the tab character is used as a separator
        dos.writeInt(newY);
        
        dos.close();
        
        byte[]data=baos.toByteArray();
        outBuffer.write(data);
    }
    
    public void TCPClientClose() throws Exception 
    {
    	clientSocket.close();
    }

} 
