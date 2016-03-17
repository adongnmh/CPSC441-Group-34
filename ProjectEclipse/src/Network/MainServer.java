package Network;
import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class MainServer extends Thread{

	private Selector selector;
	private ServerSocketChannel channel;
	private int port = 9000;
	
	public MainServer() throws Exception{
		//Initialize
		Selector selector = Selector.open();
		
		ServerSocketChannel channel = ServerSocketChannel.open();
		channel.configureBlocking(false);
		
		// bind the port number
		
		InetSocketAddress isa = new InetSocketAddress(port);
		channel.socket().bind(isa);
		
		//register that the server selector is interested in connection requests
		channel.register(selector, SelectionKey.OP_ACCEPT);
		
		try {
			boolean terminated = false;
			while(!terminated) {
				
				if(selector.select(500) < 0) {
					System.out.println("select failed");
					System.exit(1);
				}
				//Get set of ready sockets
				Set readyKeys = selector.selectedKeys();
				Iterator readyItor = readyKeys.iterator();
				System.out.println("wein");
				//Walk through the ready set
				while(readyItor.hasNext()); {
					//Get Key from set
					SelectionKey key = (SelectionKey)readyItor.next();
					readyItor.remove();
					
					//Accept new connections
					if(key.isAcceptable()) {
						System.out.println("hello");
						SocketChannel cchannel = ((ServerSocketChannel)key.channel()).accept();
						cchannel.configureBlocking(false);
						System.out.println("Accept connection from" + cchannel.socket().toString());
						
						//register new channel
						cchannel.register(selector, SelectionKey.OP_READ);
					}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

	
}
