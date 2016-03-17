package CollaborativeDrawing;

import GUI.*;
import Network.CanvasClient;
import Network.MainServer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.Socket;

/**
 * Controller of the application. Will Create/Initialize a Frame for the whole
 * application
 *
 * @author CPSC 441 Group 34 Date Last Modified: 02/07/2016
 */
public class CollaborativeDrawingController
{

	private int srcPort = 9000;
	private int dstPort = 8000;
	private String stringIP = "192.168.0.5";
    public static void main(String[] args) throws Exception
    {
        // TODO code application logic here
    	
    	
    	
    	// Initializing Main Screen
    	ApplicationMainScreen mainScreenFrame = new ApplicationMainScreen();
    	Container content = mainScreenFrame.getContentPane();
    	content.setLayout(new BorderLayout());
    	ApplicationMainScreenPanel mainPanel = new ApplicationMainScreenPanel();
    	content.add(mainPanel, BorderLayout.CENTER);
    	
    	//Initialize the main server
    	if(!hostAvailabilityCheck())
    	{
    		System.out.println("available");
    		MainServer server = new MainServer();
    		CanvasClient client = new CanvasClient();
    	}
    	else
    	{
    		System.out.println("Client");
    		CanvasClient client = new CanvasClient();
    	}
    	
    	content.setVisible(true);
    	mainScreenFrame.setVisible(true);
    }
    
    // Checks if the main server is already created 
    public static boolean hostAvailabilityCheck() { 
        try (Socket s = new Socket("169.254.245.161", 9000)) {
        	s.close();
            return true;
        } catch (IOException ex) {
            /* ignore */
        }
        return false;
    }

}
