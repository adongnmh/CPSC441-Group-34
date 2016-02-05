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
        String outputLine;
        String line = ""; 
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
        outputLine = inFromUser.readLine(); 
        while (!outputLine.equals("logout"))
        {


            // Send to the server
            if(!clientSocket.isClosed())
                outBuffer.writeBytes(outputLine + "\n"); 

            // check if client socket is closed
            if(!outputLine.equals("terminate") && !clientSocket.isClosed())
                line = inBuffer.readLine();
            else
            {
                outBuffer.close();
                inBuffer.close();
            }
                

            // Check if command is "list" -- outputs the list of files in current directory of server
            if(outputLine.equals("list"))
            {
                while(!line.equals(""))
                {
                    System.out.println(line);
                    line = inBuffer.readLine();
                }
            }
            // Starts file transfer
            else if(line.equals("start file transfer"))
            {
                String textFile = "";
                String lineAppender = "";
                fileName = outputLine.substring(4, outputLine.length()) + "-" + args[1];
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
                fos.close();
                outputFile = new File(fileName);
                System.out.println("File saved in " + fileName + " ("+outputFile.length() + " bytes)");
            }
            else if(clientSocket.isClosed())
            {
                System.out.println("Not connected");
            }
            else {
                System.out.println("Server: " + line);
            }

            // Get input from client
            System.out.print("Please enter a message to be sent to the server ('logout' to terminate): ");
            outputLine = inFromUser.readLine();
        }

        // Close the socket
        clientSocket.close();           
    } 
} 
