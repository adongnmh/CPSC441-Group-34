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
	//private Image img;
	
	/**
	 * Create the panel.
	 * @param mainScreenFrame 
	 */
	public ApplicationMainScreenPanel(CanvasClient c, ApplicationMainScreen mainScreenFrame) 
	{
		/**setBackground(Color.WHITE);
		frame = mainScreenFrame;
		
		// This will grab the file path to the the background image of the JPanel
		// if you want to update the background image, just change the file path to the image
		try 
		{                
		   File sourceimage = new File("/doge.jpg");
		
		   img = ImageIO.read(sourceimage);
        } 
		catch (IOException ex) 
		{
             // handle exception...
        }
       	repaint();
	       **/
		// Initializes the MainScreenPanel using the code generated from windows builder.
		initializeMainScreenPanel();
		frame = mainScreenFrame;
		client = c;

	}
    
	/**
	 * Paint method to paint the image onto the JPanel. Image is from the constructor method from above.
	 */
	/**public void paintComponent(Graphics g)
	{
	    super.paintComponent(g);
	    g.drawImage(img, 0, 0, null );
	}
	**/
	/**
	 * This method will generate all the required components for the Application Main screen
	 * code is generated from the windowsBuilder GUI builder pluggin
	 */
	public void initializeMainScreenPanel()
	{
		
			JPanel panel = new JPanel();
			panel.setBackground(Color.WHITE);
			GroupLayout groupLayout = new GroupLayout(this);
			groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 841, Short.MAX_VALUE)
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 503, Short.MAX_VALUE)
						.addContainerGap())
			);
			
			createAccountbtn = new JButton("Create Account");
			createAccountbtn.setForeground(Color.WHITE);
			createAccountbtn.setBackground(Color.DARK_GRAY);
			createAccountbtn.setFont(new Font("Letter Gothic Std", Font.PLAIN, 27));
			createAccountbtn.addActionListener(this);
			
			createAccountbtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				}
			});
			
			loginBtn = new JButton("Login");
			loginBtn.setForeground(Color.WHITE);
			loginBtn.setBackground(Color.DARK_GRAY);
			loginBtn.setFont(new Font("Letter Gothic Std", Font.PLAIN, 27));
			loginBtn.addActionListener(this);
			
			JLabel lblCollaborativeDrawing = new JLabel("COLLABORATIVE");
			lblCollaborativeDrawing.setForeground(Color.DARK_GRAY);
			lblCollaborativeDrawing.setBackground(Color.DARK_GRAY);
			lblCollaborativeDrawing.setFont(new Font("Letter Gothic Std", Font.BOLD, 32));
			
			JLabel lblDrawing = new JLabel("DRAWING");
			lblDrawing.setForeground(Color.DARK_GRAY);
			lblDrawing.setFont(new Font("Letter Gothic Std", Font.BOLD, 33));
			
			JLabel lblNewLabel = new JLabel("");
			Image img = new ImageIcon(this.getClass().getResource("/a.jpg")).getImage();
			lblNewLabel.setIcon(new ImageIcon(img));
			
			JLabel lblNewLabel_1 = new JLabel("");
			Image img1 = new ImageIcon(this.getClass().getResource("/b.jpg")).getImage();
			lblNewLabel_1.setIcon(new ImageIcon(img1));
			
			GroupLayout gl_panel = new GroupLayout(panel);
			gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 348, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
									.addGap(255)
									.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addContainerGap())
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(94)
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(lblCollaborativeDrawing)
										.addComponent(loginBtn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
										.addComponent(createAccountbtn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addContainerGap()))
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(159)
								.addComponent(lblDrawing)
								.addContainerGap())))
			);
			gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
								.addGap(44)
								.addComponent(lblCollaborativeDrawing, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(lblDrawing)
								.addGap(67)
								.addComponent(loginBtn, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(createAccountbtn, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 437, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(66, Short.MAX_VALUE))
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
