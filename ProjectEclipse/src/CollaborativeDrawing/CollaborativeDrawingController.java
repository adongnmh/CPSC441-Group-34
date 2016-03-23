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

    public static void main(String[] args) throws Exception
    {

		//Initialize the main server
    	System.out.println("available");
    	MainServer server = new MainServer();
    	server.start();

		//Connect the client to the server -- does not mean that the client is "logged" in
		CanvasClient client = new CanvasClient();
    }


}
