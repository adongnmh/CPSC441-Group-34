package Network;
import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.*;

public class MainServer extends Thread{
	public static int BUFFERSIZE = 32;
	private int port = 9000;
	private ServerSocketChannel serverChannel;
	private Selector selector;
	private HashMap<Integer, SocketChannel> clientList;
	private int numberOfClients = 1;
	private HashMap<String, String> userAccounts;

	private static final String LOGIN_REQUEST = "0x02";
	private static final String CREATE_CANVAS_REQUEST = "0x04";
	private static final String INVITE_FRIEND_REQUEST = "0x06";
	private static final String CANVAS_ACCEPT = "0x09";
	private static final String EDIT_CANVAS = "0x11";
	private static final String BAN_REQUSET = "0x13";
	private static final String FRIEND_REQUEST = "0x16";
	private static final String LIST_REQUEST = "0x18";
	private static final String DISCONNECT = "0x20";


	
	public void run(){
		
		try
		{
			//Initializing the server on startup
			selector = Selector.open();
			serverChannel = ServerSocketChannel.open();
			serverChannel.configureBlocking(false);
			serverChannel.socket().bind(new InetSocketAddress(port));
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
			clientList = new HashMap<>();
			userAccounts = new HashMap<>();
			userAccounts.put("Tan", "Quach");

			while(true)
			{
				System.out.println("Waiting for select...");
				int noOfKeys = selector.select();
				
				System.out.println("Number of selected keys: " + noOfKeys);
				Set selectedKeys = selector.selectedKeys();
				Iterator iter = selectedKeys.iterator();
				
				while(iter.hasNext()) 
				{
					SelectionKey key = (SelectionKey)iter.next();
					iter.remove();
					//Accept new connections
					if(key.isAcceptable()) 
					{
						addClient(key);
					}
					//Read incoming messages
					else if(key.isReadable())
					{
						readKey(key);
					}
					
				}
			}
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}

	//Used to add new client to the server channel
	//Will add the new client along with an integer to the clientList
	private void addClient(SelectionKey key) throws IOException
	{
		ServerSocketChannel acceptSocket = (ServerSocketChannel) key.channel();
		SocketChannel newClient = acceptSocket.accept();
		SelectionKey clientKey;
		
		newClient.configureBlocking(false);
		clientKey = newClient.register(this.selector, SelectionKey.OP_READ);
		clientList.put(numberOfClients, newClient);
		numberOfClients++;
		System.out.println("Accepted new connection from client " + newClient);
	}
	
	private void readKey(SelectionKey key) throws IOException
	{
		SocketChannel cchannel = (SocketChannel)key.channel();
		Socket socket = cchannel.socket();
		Charset charset = Charset.forName( "us-ascii" );
		ByteBuffer inBuffer = ByteBuffer.allocateDirect(BUFFERSIZE);
        CharBuffer cBuffer = CharBuffer.allocate(BUFFERSIZE);
		CharsetDecoder decoder = charset.newDecoder();
		CharsetEncoder encoder = charset.newEncoder();
        
        // Read from socket
        int bytesRecv = cchannel.read(inBuffer);
        if (bytesRecv <= 0)
        {
            System.out.println("read() error, or connection closed");
            key.cancel();  // deregister the socket
        }
        inBuffer.flip();      // make buffer available  
        decoder.decode(inBuffer, cBuffer, false);
        cBuffer.flip();
        String request;
        request = cBuffer.toString();
		//Parse the message code
		String[] code = request.split("\t");
		ByteBuffer responseMessage;

		switch(code[0])
		{
			case LOGIN_REQUEST:
			{
				//Do the login request
				if(checkCredentials(code[1], code[2]))
				{
					responseMessage = encoder.encode(CharBuffer.wrap("0" + '\n'));
					cchannel.write(responseMessage);
				}
				else
				{
					responseMessage = encoder.encode(CharBuffer.wrap("1" + '\n'));
					cchannel.write(responseMessage);
				}
			}
			case CREATE_CANVAS_REQUEST:
			{
				//Create a new canvas
			}
			case INVITE_FRIEND_REQUEST:
			{
				//Send invite to the list of clients
			}
			case CANVAS_ACCEPT:
			{
				//Send request to client to join the canvas
			}
			case EDIT_CANVAS:
			{

			}
			case BAN_REQUSET:
			{

			}
			case FRIEND_REQUEST:
			{

			}
			case LIST_REQUEST:
			{

			}
			case DISCONNECT:
			{

			}
		}
	}

	//Check the username and password with the list of users
	private boolean checkCredentials(String username, String password)
	{
		//make sure to check that if username doesnt exist first else null pointer exception
		if(userAccounts.get(username).equals(password))
			return true;
		else
			return false;
	}
}
