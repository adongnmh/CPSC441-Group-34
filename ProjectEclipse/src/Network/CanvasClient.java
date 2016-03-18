package Network;
import GUI.*;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

public class CanvasClient extends Thread{

	private ApplicationMainScreenPanel mainGUI;
	private LoginScreenFrame loginGUI;
	private CreateAccountFrame createAccGUI;
	private CreatingCanvas createCanvasGUI;
	private DrawingCanvas canvasGUI;

	private int port = 9000;
	private Socket clientSocket;
	private String currentMachineIP = "";

	private static final String CREATE_ACCOUNT = "0x00";
	private static final String LOGIN_REQUEST = "0x02";
	public CanvasClient () 
	{
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		currentMachineIP = addr.getHostAddress();
		

		
		try {
			clientSocket = new Socket(currentMachineIP, 9000);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		/*loginGUI = new LoginScreenFrame(this);
		createAccGUI = new CreateAccountFrame(this);
		createCanvasGUI = new CreatingCanvas(this);
		canvasGUI = new DrawingCanvas(this);*/

		ApplicationMainScreen mainScreenFrame = new ApplicationMainScreen();
		mainGUI = new ApplicationMainScreenPanel(this,mainScreenFrame);
		Container content = mainScreenFrame.getContentPane();
		content.setLayout(new BorderLayout());

		content.add(mainGUI, BorderLayout.CENTER);
		content.setVisible(true);
		mainScreenFrame.setVisible(true);
	}
	public void run()
	{

	}

	public void close() throws IOException
	{
		clientSocket.close();
	}

	
	//Send the username and password to the server for validation
	public void loginRequest(String username, String password) throws IOException
	{
		DataOutputStream outBuffer = new DataOutputStream(clientSocket.getOutputStream()); 
		BufferedReader inBuffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
		outBuffer.writeBytes(LOGIN_REQUEST + '\t' + username + '\t' + password);
		
		// Getting response from the server
        String line = inBuffer.readLine();
        System.out.println("Server: " + line);

		//Check the response from the server
        if(line.equals("0"))
		{
			CreatingCanvas createDrawing = new CreatingCanvas(this);
			createDrawing.setVisible(true);
		}
		else if(line.equals("1"))
		{
			System.out.println("invalid credentials");
			//SHOULD OUTPUT AN ERROR MESSAGE
		}
	}

	//send message to server for account creation
	public void createAccount(String username, String password) throws IOException
	{
		DataOutputStream outBuffer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inBuffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		outBuffer.writeBytes(CREATE_ACCOUNT + '\t' + username + '\t' + password);

		// Getting response from the server
		String line = inBuffer.readLine();
		System.out.println("Server: " + line);

		//Check the response from the server
		if(line.equals("0"))
		{
			mainGUI.setVisible(true);
			//CreatingCanvas createDrawing = new CreatingCanvas();
			//createDrawing.setVisible(true);
		}
		else if(line.equals("1"))
		{
			System.out.println("invalid credentials");
			//SHOULD OUTPUT AN ERROR MESSAGE
		}
	}
}
