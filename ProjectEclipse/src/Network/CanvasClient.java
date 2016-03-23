package Network;
import GUI.*;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.*;

public class CanvasClient extends Thread{

	private ApplicationMainScreenPanel mainGUI;
	private LoginScreenFrame loginGUI;
	private CreateAccountFrame createAccGUI;
	private CreatingCanvas createCanvasGUI;
	private DrawingCanvas canvasGUI;
	private String username;

	private int port = 9000;
	private Socket clientSocket;
	private String currentMachineIP = "";

	private static final String CREATE_ACCOUNT = "0x00";
	private static final String LOGIN_REQUEST = "0x02";
	private static final String CREATE_CANVAS_REQUEST = "0x04";
	private static final String EDIT_CANVAS = "0x11";
	private static final String JOIN_REQUEST = "0x21";

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
	@Override
	public void run()
	{
		while(true)
		{
			try
			{
				BufferedReader inBuffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				String line = inBuffer.readLine();
				String[] code = line.split("\t");
				canvasGUI.UpdatedLine(Integer.parseInt(code[0]), Integer.parseInt(code[1]), Integer.parseInt(code[2]), Integer.parseInt(code[3]));
				System.out.println("LOLOLOLOL: " + line);
			}
			catch(Exception ex)
			{
				//ignore
			}
		}
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
			this.username = username;
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

	public void createCanvasRequest() throws Exception
	{
		//Send request to the server
		DataOutputStream outBuffer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inBuffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		outBuffer.writeBytes(CREATE_CANVAS_REQUEST + '\t' + username + '\t');

		//Getting response from the server
		String line = inBuffer.readLine();

		if(line.equals("0"))
		{
			System.out.println("canvas createdddd");
			DrawingScreenFrame newFrame = new DrawingScreenFrame();

			Container content = newFrame.getContentPane();
			content.setLayout(new BorderLayout());


			try {
				canvasGUI = new DrawingCanvas(this);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			content.add(canvasGUI, BorderLayout.CENTER);
			content.setVisible(true);
			newFrame.setVisible(true);
		}
		//TODO: Add an error message saying that all servers are currently taken
		else if(line.equals("1"))
		{
			System.out.println("ALL SERVERS ARE TAKEN");
			//SHOULD OUTPUT AN ERROR MESSAGE
		}
	}

	public void joinRequest(String serverNumber) throws Exception
	{
		//Send request to the server
		DataOutputStream outBuffer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inBuffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		outBuffer.writeBytes(JOIN_REQUEST + '\t' + serverNumber + '\t' + this.username);

		//Getting response from the server
		String line = inBuffer.readLine();
		System.out.println("Server: " + line);
		if(line.equals("0"))
		{
			System.out.println("joining canvas");
			DrawingScreenFrame newFrame = new DrawingScreenFrame();

			Container content = newFrame.getContentPane();
			content.setLayout(new BorderLayout());


			try {
				canvasGUI = new DrawingCanvas(this);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			content.add(canvasGUI, BorderLayout.CENTER);
			content.setVisible(true);
			newFrame.setVisible(true);
			//TODO: UPDATE REQUEST IMMEDIATELY JOINING TO GET THE CANVAS
		}
		else if(line.equals("1"))
		{
			//TODO: ADD MESSAGE SAYING THAT SERVER IS FULL
			System.out.println("Server is full");
		}
	}

	public void updateCanvas(int oldX, int oldY, int newX, int newY) throws Exception
	{
		System.out.println("original coords: " + oldX + " " + oldY + " " + newX + " " + newY);
		DataOutputStream outBuffer = new DataOutputStream(clientSocket.getOutputStream());
		outBuffer.write((EDIT_CANVAS + '\t' + oldX + '\t' + oldY + '\t' + newX + '\t' + newY + '\t').getBytes(Charset.forName("us-ascii")));
	}
}
