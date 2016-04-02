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
	public static int BUFFERSIZE = 1000;
	private int port = 9000;
	private ServerSocketChannel serverChannel;
	private Selector selector;
	private HashMap<String, SocketChannel> clientList;
	private int numberOfClients = 0;
	private HashMap<String, String> userAccounts;
	private HashMap<String, String> userServer;
	private HashMap<String, List<String>> userFriendsList;

	private static final String CREATE_ACCOUNT = "0x00";
	private static final String LOGIN_REQUEST = "0x02";
	private static final String CREATE_CANVAS_REQUEST = "0x04";
	private static final String INVITE_FRIEND_REQUEST = "0x06";
	private static final String CANVAS_ACCEPT = "0x09";
	private static final String EDIT_CANVAS = "0x11";
	private static final String BAN_REQUEST = "0x13";
	private static final String FRIEND_REQUEST = "0x16";
	private static final String LIST_REQUEST = "0x18";
	private static final String DISCONNECT = "0x20";
	private static final String JOIN_REQUEST = "0x21";


	//Canvas Servers
	private List<String> server1 = new ArrayList<String>();
	private List<String> server2 = new ArrayList<String>();
	private List<String> server3 = new ArrayList<String>();
	private List<String> server4 = new ArrayList<String>();


	
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
			userServer = new HashMap<>();
			userAccounts = new HashMap<>();
			userFriendsList = new HashMap<>();
			userAccounts.put("Tan", "Quach");
			userAccounts.put("asdf", "asdf");
			userAccounts.put("1", "1");
			userAccounts.put("2", "2");
			userAccounts.put("3", "3");
			List<String> testList1 = new ArrayList<>();
			List<String> testList2 = new ArrayList<>();
			testList1.add("bob");
			testList1.add("jim");
			userFriendsList.put("Tan", testList1);
			userFriendsList.put("asdf", testList2);


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

	//Create a new client on startup of each application
	//Does not mean that the client is "logged" into the server
	private void addClient(SelectionKey key) throws IOException
	{
		ServerSocketChannel acceptSocket = (ServerSocketChannel) key.channel();
		SocketChannel newClient = acceptSocket.accept();
		SelectionKey clientKey;
		
		newClient.configureBlocking(false);
		clientKey = newClient.register(this.selector, SelectionKey.OP_READ);
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
		ByteBuffer responseMessage = ByteBuffer.allocate(BUFFERSIZE);
		//TODO: SOMETIMES IT READS NOTHING SO IT CAUSES NULL REFERENCE -- NEED TO DEBUG (temporary fix)
		if(code.length == 0)
		{
			return;
		}
		switch(code[0])
		{
			case CREATE_ACCOUNT:
			{
				if(code.length != 3)
					return;
				if(checkUserAccount(code[1]))
				{
					userAccounts.put(code[1], code[2]);
					List<String> friendsList = new ArrayList<>();
					userFriendsList.put(code[1], friendsList);
					responseMessage = encoder.encode(CharBuffer.wrap("0" + '\n'));
					cchannel.write(responseMessage);
				}
				else
				{
					responseMessage = encoder.encode(CharBuffer.wrap("1" + '\n'));
					cchannel.write(responseMessage);
				}
				break;
			}
			case LOGIN_REQUEST:
			{
				//Do the login request
				if(checkCredentials(code[1], code[2]))
				{
					responseMessage = encoder.encode(CharBuffer.wrap("0" + '\n'));
					cchannel.write(responseMessage);
					//Store the username and the socket information to hashmap
					clientList.put(code[1], cchannel);
					System.out.println("NUMBER OF CLIENTS LOGGED IN: " + clientList.size());
				}
				else
				{
					System.out.println("why not");
					responseMessage = encoder.encode(CharBuffer.wrap("1" + '\n'));
					cchannel.write(responseMessage);
					System.out.println("NUMBER OF CLIENTS LOGGED IN: " + clientList.size());
				}
				break;
			}
			case CREATE_CANVAS_REQUEST:
			{
				//Create a new canvas
				//Check available 4 canvas servers
				if(checkServers(code[1]))
				{
					System.out.println("succesfully created canvas");
					responseMessage = encoder.encode(CharBuffer.wrap("0" + '\n'));
					cchannel.write(responseMessage);
				}
				else
				{
					responseMessage = encoder.encode(CharBuffer.wrap("1" + '\n'));
					cchannel.write(responseMessage);
				}
				break;
			}
			//TODO: check if joining is available
			case JOIN_REQUEST:
			{
				if(joinServer(code[1], code[2]))
				{
					responseMessage = encoder.encode(CharBuffer.wrap("0" + '\n'));
					cchannel.write(responseMessage);
				}
				else
				{
					responseMessage = encoder.encode(CharBuffer.wrap("1" + '\n'));
					cchannel.write(responseMessage);
				}
				break;
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
				System.out.println("OH WHAT WE IN THE EDIT CANVAS");
				if(code.length != 7)
					return;
				responseMessage = encoder.encode(CharBuffer.wrap(code[1] + '\t' + code[2] + '\t' + code[3] + '\t' +  code[4] + '\t' + code[5] +'\t' + code[6] +'\n'));
				SocketChannel channel1 = clientList.get("asdf");
				channel1.write(responseMessage);
				//cchannel.write(responseMessage);
				System.out.println(code[1] + " " + code[2] + " " + code[3] + " " + code[4] + " " + code[5] + " "  + code[6]);
				break;
			}
			case BAN_REQUEST:
			{
				System.out.println("    " + code[1] + "   " + code[2]);
				if(userServer.containsKey(code[1]) && userServer.containsKey(code[2]))
				{
					System.out.println("hello we in here");
					banUser(code[2], userServer.get(code[1]));
					SocketChannel client = clientList.get(code[2]);
					responseMessage = encoder.encode(CharBuffer.wrap(BAN_REQUEST + '\n'));
					client.write(responseMessage);
				}
				else
				{
					System.out.println("out");
				}

				break;

			}
			case FRIEND_REQUEST:
			{
				userFriendsList.get(code[1]).add(code[2]);
				System.out.println(code[2]);
				break;
			}
			case LIST_REQUEST:
			{
				if(userFriendsList.get(code[1]).size() != 0)
				{
					responseMessage = encoder.encode(CharBuffer.wrap(getFriendsList(code[1])));
					System.out.println("In here");
					cchannel.write(responseMessage);
				}
				break;
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
		if((userAccounts.containsKey(username)) && (userAccounts.get(username).equals(password)))
			return true;
		else
			return false;
	}

	//check if username is already taken
	private boolean checkUserAccount(String username)
	{
		for(String key : userAccounts.keySet())
		{
			if(key.equalsIgnoreCase(username))
				return false;
		}
		return true;
	}

	//Check the server to see if it has been used as a host already
	private boolean checkServers(String username)
	{
		if(server1.size() == 0)
		{
			server1.add(username);
			userServer.put(username, "1");
			return true;
		}
		else if(server2.size() == 0)
		{
			server2.add(username);
			userServer.put(username, "2");
			return true;
		}
		else if(server3.size() == 0)
		{
			server3.add(username);
			userServer.put(username, "3");
			return true;
		}
		else if(server4.size() == 0)
		{
			server4.add(username);
			userServer.put(username, "4");
			return true;
		}
		return false;
	}

	// Add the username to the requested server
	//TODO: add a limit to each server so there will be a check for number of users
	private boolean joinServer(String serverNum, String username)
	{
		if(serverNum.equals("1") && server1.size() < 4)
		{
			server1.add(username);
			userServer.put(username, "1");
			for(int i = 0; i < server1.size(); i++)
			{
				System.out.println(server1.get(i));
			}
			return true;
		}
		else if(serverNum.equals("2") && server2.size() < 4)
		{
			server2.add(username);
			userServer.put(username, "2");
			return true;
		}
		else if(serverNum.equals("3") && server3.size() < 4)
		{
			server3.add(username);
			userServer.put(username, "3");
			return true;
		}
		else if(serverNum.equals("4") && server4.size() < 4)
		{
			server4.add(username);
			userServer.put(username, "4");
			return true;
		}
		return false;
	}

	private String getFriendsList(String username)
	{
		String friendString = LIST_REQUEST + '\t';
		List<String> friendList = userFriendsList.get(username);
		for(int i = 0; i < friendList.size(); i++)
		{
			friendString += friendList.get(i) + '.';
		}
		friendString += '\n';

		return friendString;
	}

	private void banUser(String username, String serverNum)
	{
		userServer.remove(username);
		if(serverNum=="1")
			server1.remove(username);
		else if(serverNum=="2")
			server2.remove(username);
		else if(serverNum=="3")
			server3.remove(username);
		else if(serverNum=="4")
			server4.remove(username);


	}
}
