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
	private ByteBuffer inBuffer = null;
    private CharBuffer cBuffer = null;
    private CharsetDecoder decoder;
    private CharsetEncoder encoder;

	
	public void run(){
		
		try
		{
			Charset charset = Charset.forName( "us-ascii" );  
	        decoder = charset.newDecoder();  
	        encoder = charset.newEncoder();
			selector = Selector.open();
			serverChannel = ServerSocketChannel.open();
			serverChannel.configureBlocking(false);
			serverChannel.socket().bind(new InetSocketAddress(port));
			
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
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
					if(key.isAcceptable()) 
					{
						addClient(key);
					}
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
		inBuffer = ByteBuffer.allocateDirect(BUFFERSIZE);
        cBuffer = CharBuffer.allocate(BUFFERSIZE);
        
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
        String line = "";
        line = cBuffer.toString();
        System.out.print("Client: " + line);
        

        if(checkCredentials(line))
        {
        	inBuffer.flip();
            cchannel.write(inBuffer);
        }
        
        inBuffer.flip();
        cchannel.write(inBuffer);
	}
	
	private boolean checkCredentials(String username)
	{
		if(username.equals("Tan Quach" + '\n'))
		{
			System.out.println(username);
			return true;
		}
		return false;
	}
	

		
		
		
		
		
		
		/*try {
			
				ServerSocket serverSocket = new ServerSocket(port);
				ArrayList<Socket> clients = new ArrayList<Socket>();
				while (true) {
					//connectionSocket = serverSocket.accept();
					
				    clients.add(connectionSocket);    
				    System.out.println(clients.size());
				}
			
		}
		catch(IOException e)
		{
			
		}*/
}
