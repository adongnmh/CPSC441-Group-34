package GUI;

import Network.CanvasClient;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout.Alignment;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * This Class will generate the Select Server page for the user. GUI was design in windows builder and 
 * the initialization code is generated by windows builder
 * @author Group 34
 * Last Modified: March 24th 2016
 *
 */
public class SelectCanvasServer extends JFrame implements ActionListener{

	// Initializing the variables for the class
	private JPanel contentPane;
	JButton btnJoin;
	JButton btnBack;
	String IP;
	int sPort;
	int dPort;
	private CanvasClient client;
	private ButtonGroup radioGroup;


	
	/**
	 * Method will initialize the canvas
	 * @param c
	 */
	public SelectCanvasServer(CanvasClient c)
	{
		client = c;
		initialize();
	}
	
	/**
	 * Initialize the components for the canvas
	 */
	public void initialize()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 594, 483);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		radioGroup = new ButtonGroup();
		JLabel lblSelectAServer = new JLabel("Select A Server to Join:");
		lblSelectAServer.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JRadioButton rdbtnServer = new JRadioButton("Server#1");
		rdbtnServer.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdbtnServer.setActionCommand(rdbtnServer.getText());

		JRadioButton rdbtnServer_1 = new JRadioButton("Server#2");
		rdbtnServer_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdbtnServer_1.setActionCommand(rdbtnServer_1.getText());
		
		JRadioButton rdbtnServer_2 = new JRadioButton("Server#3");
		rdbtnServer_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdbtnServer_2.setActionCommand(rdbtnServer_2.getText());
		
		JRadioButton rdbtnServer_3 = new JRadioButton("Server#4");
		rdbtnServer_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdbtnServer_3.setActionCommand(rdbtnServer_3.getText());
		radioGroup.add(rdbtnServer);
		radioGroup.add(rdbtnServer_1);
		radioGroup.add(rdbtnServer_2);
		radioGroup.add(rdbtnServer_3);
		
		btnJoin = new JButton("Join Canvas");
		btnJoin.addActionListener(this);
		
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(this);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(164, Short.MAX_VALUE)
					.addComponent(lblSelectAServer, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE)
					.addGap(177))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(29)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnServer_3, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
						.addComponent(rdbtnServer_2, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
						.addComponent(rdbtnServer_1, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
						.addComponent(rdbtnServer, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(415, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(99)
					.addComponent(btnJoin, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
					.addGap(108)
					.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(119, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblSelectAServer, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(rdbtnServer, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnServer_1, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnServer_2, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnServer_3, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnJoin, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
					.addGap(42))
		);
		contentPane.setLayout(gl_contentPane);
	}

	/**
	 * Handles all the action listener events for the buttons.
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == btnJoin)
		{
			System.out.println(radioGroup.getSelection().getActionCommand());
			try
			{
				if(radioGroup.getSelection().getActionCommand() == "Server#1")
					this.client.joinRequest("1");
				else if(radioGroup.getSelection().getActionCommand() == "Server#2")
					this.client.joinRequest("2");
				else if(radioGroup.getSelection().getActionCommand() == "Server#3")
					this.client.joinRequest("3");
				else if(radioGroup.getSelection().getActionCommand() == "Server#4")
					this.client.joinRequest("4");
			}
			catch(Exception ex)
			{
				//ignore
			}
			/*this.dispose();
			DrawingScreenFrame newFrame = new DrawingScreenFrame();

	        Container content = newFrame.getContentPane();
	        content.setLayout(new BorderLayout());
	        
	        
	        DrawingCanvas newPiece = null;
			try {
				newPiece = new DrawingCanvas(client);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        content.add(newPiece, BorderLayout.CENTER);
	        content.setVisible(true);
	        newFrame.setVisible(true);*/
		}
		else if(e.getSource() == btnBack)
		{
			this.dispose();
		}
		
	}

}
