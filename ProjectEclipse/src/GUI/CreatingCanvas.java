package GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Network.*;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;


public class CreatingCanvas extends JFrame implements ActionListener
{

	private JPanel contentPane;
	private JButton btnCreateNewCanvas;
	private JButton btnJoinExistingCanvas;
	//private TCPClient client;
	//private TCPServer server;

	private int srcPort = 9000;
	private int dstPort = 8000;
	private String stringIP = "";

	/**
	 * Create the frame.
	 */
	public CreatingCanvas() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ip = addr.getHostAddress();
		System.out.println("Ip: " + ip);
		stringIP = ip;
		initialiaze();
	}


	private void initialiaze() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 556, 538);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		btnCreateNewCanvas = new JButton("Create New Canvas");
		btnCreateNewCanvas.addActionListener(this);
		 
		btnJoinExistingCanvas = new JButton("Join Existing Canvas");
		btnJoinExistingCanvas.addActionListener(this);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(181)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(btnJoinExistingCanvas, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
							.addGap(169))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnCreateNewCanvas, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(169, Short.MAX_VALUE))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(144)
					.addComponent(btnCreateNewCanvas, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
					.addGap(38)
					.addComponent(btnJoinExistingCanvas, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(185, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		
	}


	@Override()
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == btnCreateNewCanvas)
		{
			this.dispose();
			DrawingScreenFrame newFrame = new DrawingScreenFrame();

	        Container content = newFrame.getContentPane();
	        content.setLayout(new BorderLayout());
	        
	        
	        DrawingCanvas newPiece = null;
			try {
				newPiece = new DrawingCanvas(stringIP,srcPort,dstPort);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        content.add(newPiece, BorderLayout.CENTER);
	        content.setVisible(true);
	        newFrame.setVisible(true);
	        

		}
		else if(e.getSource() == btnJoinExistingCanvas)
		{
			this.dispose();
			SelectCanvasServer toJoinCanvas = new SelectCanvasServer(stringIP,dstPort,srcPort);
			toJoinCanvas.setVisible(true);
			
		}
		
	}

}
