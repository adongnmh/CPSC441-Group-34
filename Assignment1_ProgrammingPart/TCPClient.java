/*
 * A simple TCP client that sends messages to a server and display the message
   from the server. 
 * For use in CPSC 441 lectures
 * Instructor: Prof. Mea Wang
 */


import java.io.*; 
import java.net.*; 

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
          int bytesRead;

        // Initialize user input stream
        String line; 
        BufferedReader inFromUser = 
        new BufferedReader(new InputStreamReader(System.in)); 
        String workingDirect = System.getProperty("user.dir");
        FileOutputStream fos = null;
        File outputFile;
        BufferedOutputStream bos = null;
        String fileName = "";
        String testLine;
        // Get user input and send to the server
        // Display the echo meesage from the server
        System.out.print("Please enter a message to be sent to the server ('logout' to terminate): ");
        line = inFromUser.readLine(); 
        while (!line.equals("logout"))
        {
            // Send to the server
            outBuffer.writeBytes(line + "\n"); 
            if(line.substring(0, 3).equalsIgnoreCase("get"))
            {
                fileName = line.substring(4, line.length()) + "-" + args[1];
            }
            line = inBuffer.readLine();
            String textFile = "";
            String lineAppender = "";
            if(line.equals("start file transfer"))
            {
                while(!line.equals("done"))
                {
                    line = inBuffer.readLine();
                    while(line.equals(""))
                    {
                        line = inBuffer.readLine();
                    }
                    if(!line.equals("done"))
                    {
                        lineAppender = line;
                        textFile += lineAppender + "\n";
                    }
                    outputFile = new File(fileName);
                    fos = new FileOutputStream(fileName);
                } 
                fos.write(textFile.getBytes());
            }
            else {
                System.out.println(line);
            }
            //char[] mybytearray  = new char [6022386];
            //line = inBuffer.readLine();
            //System.out.println(line);
            // Getting response from the server
            //bytesRead = inBuffer.read(mybytearray,0,mybytearray.length);
            
            System.out.print("Please enter a message to be sent to the server ('logout' to terminate): ");
            line = inFromUser.readLine();
        }

        // Close the socket
        clientSocket.close();           
    } 
} 
