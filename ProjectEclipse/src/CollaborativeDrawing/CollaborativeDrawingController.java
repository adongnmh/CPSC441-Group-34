package CollaborativeDrawing;

import GUI.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;

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
	
    public static void main(String[] args)
    {
        // TODO code application logic here
    	
    	
    	
    	// Initializing Main Screen
    	ApplicationMainScreen mainScreenFrame = new ApplicationMainScreen();
    	Container content = mainScreenFrame.getContentPane();
    	content.setLayout(new BorderLayout());
    	ApplicationMainScreenPanel mainPanel = new ApplicationMainScreenPanel();
    	content.add(mainPanel, BorderLayout.CENTER);
    	
    	content.setVisible(true);
    	mainScreenFrame.setVisible(true);
    }

}
