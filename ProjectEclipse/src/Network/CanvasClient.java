package Network;
import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class CanvasClient {

	private int port = 9000;
	
	public CanvasClient() throws Exception{
		
		Socket clientSocket = new Socket("169.254.245.161", 9000);
		if(clientSocket.isConnected())
			System.out.println("connected");
		else
			System.out.println("not connected");
		
	}
}
