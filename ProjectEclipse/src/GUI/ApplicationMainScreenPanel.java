package GUI;

import javax.swing.*;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;

import Network.CanvasClient;
import com.sun.security.ntlm.Client;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

public class ApplicationMainScreenPanel extends JPanel implements ActionListener{

	
	// Variables for the main 
	private JButton createAccountbtn;
	private JButton loginBtn;
	private CanvasClient client;
	private ApplicationMainScreen frame;
	private Image img;
	
	/**
	 * Create the panel.
	 * @param mainScreenFrame 
	 */
	public ApplicationMainScreenPanel(CanvasClient c, ApplicationMainScreen mainScreenFrame) 
	{
		frame = mainScreenFrame;
		
		// This will grab the file path to the the background image of the JPanel
		// if you want to update the background image, just change the file path to the image
		try 
		{                
		   File sourceimage = new File("doge.jpg");
		
		   img = ImageIO.read(sourceimage);
        } 
		catch (IOException ex) 
		{
             // handle exception...
        }
       	repaint();
	       
		// Initializes the MainScreenPanel using the code generated from windows builder.
		initializeMainScreenPanel();
		frame = mainScreenFrame;
		client = c;

	}
    
	/**
	 * Paint method to paint the image onto the JPanel. Image is from the constructor method from above.
	 */
	public void paintComponent(Graphics g)
	{
	    super.paintComponent(g);
	    g.drawImage(img, 0, 0, null );
	}
	
	/**
	 * This method will generate all the required components for the Application Main screen
	 * code is generated from the windowsBuilder GUI builder pluggin
	 */
	public void initializeMainScreenPanel()
	{

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setOpaque(false);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
		);
		
		createAccountbtn = new JButton("Create Account");
		createAccountbtn.addActionListener(this);
		
		createAccountbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		loginBtn = new JButton("Login");
		loginBtn.addActionListener(this);
		
		JLabel lblCollaborativeDrawing = new JLabel("COLLABORATIVE DRAWING");
		lblCollaborativeDrawing.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(161)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(loginBtn, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
								.addComponent(createAccountbtn, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(111)
							.addComponent(lblCollaborativeDrawing)))
					.addContainerGap(126, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(56)
					.addComponent(lblCollaborativeDrawing)
					.addGap(78)
					.addComponent(createAccountbtn, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(loginBtn, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(61, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);
	}

	@Override
	/**
	 * This method will handle all the action events that will occur on the 
	 * main screen. 
	 */
	public void actionPerformed(ActionEvent e) 
	{
		// Action taken for the CreateAccountBtn button
		if(e.getSource() == createAccountbtn)
		{
			client.createAccountFrame(frame);
			//CreateAccountFrame newAccount = new CreateAccountFrame(client,frame);
			//newAccount.setVisible(true);
			//frame.setVisible(false);
		}
		// Action taken for the Loginbtn button
		else if(e.getSource() == loginBtn)
		{
			client.createLoginFrame(frame);
			LoginScreenFrame newLoginScreen = new LoginScreenFrame(client,frame);
			newLoginScreen.setVisible(true);
			frame.setVisible(false);
		}
	}
}
