/*
 * A simple TCP client that sends messages to a server and display the message
   from the server. 
 * For use in CPSC 441 lectures
 * Instructor: Prof. Mea Wang
 */


import java.io.*; 
import java.net.*; 
import java.util.*;

class TCPClient { 

    public static void main(String args[]) throws Exception 
    { 
        if (args.length != 2)
        {
            System.out.println("Usage: TCPClient <Server IP> <Server Port>");
            System.exit(1);
        }

        // Initialize a client socket connection to the server
        Socket clientSocket = new Socket(args[0], Integer.parseInt(args[1])); 

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
		
		//Initialzing Variables for the functions
		
		//Gets the current working directory 
		String workingDirect = System.getProperty("user.dir");
		
		File folder = new File(workingDirect);
		//Array of all the files in the current directory 
		File[] filesInDirectory = folder.listFiles();


        // Get user input and send to the server
        // Display the echo meesage from the server
        System.out.print("Please enter a message to be sent to the server ('logout' to terminate): ");
        line = inFromUser.readLine(); 
        while (!line.equals("logout"))
        {
			if(line.equalsIgnoreCase("list"))
			{
				System.out.println( "Current Directory:" + workingDirect);
				
				//Printing all files and folders in the current directory 
				for(int i = 0; i < filesInDirectory.length;i++)
				{
					//If element i is a folder
					if(filesInDirectory[i].isDirectory())
					{
						System.out.println(filesInDirectory[i].getName());
					}
					else if(filesInDirectory[i].isFile())
					{
						System.out.println(filesInDirectory[i].getName());
					}
				}
			}

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
} 
