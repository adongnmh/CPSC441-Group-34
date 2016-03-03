package GUI;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class CreateAccountFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField usernameField;
	private JTextField passwordField;
	private JTextField passwordFieldConfirm;
	private JButton btnRegister;
	private JButton btnBack;

	/**
	 * Create the frame.
	 */
	public CreateAccountFrame() 
	{
		initialize();
	}
	
	/**
	 * This method will initialize all the components for the create account page
	 */
	public void initialize()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 513, 515);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblUsername = new JLabel("Username:");
		
		usernameField = new JTextField();
		usernameField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		
		passwordField = new JTextField();
		passwordField.setColumns(10);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password:");
		
		passwordFieldConfirm = new JTextField();
		passwordFieldConfirm.setColumns(10);
		
		JLabel lblPleaseEnterA = new JLabel("Please enter a username and a password to register for an account!");
		lblPleaseEnterA.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		btnRegister = new JButton("Sign Up");
		btnRegister.addActionListener(this);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(this);
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(53)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(passwordFieldConfirm, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblConfirmPassword)
								.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPassword)
								.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblUsername)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnRegister, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
									.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
									.addGap(72))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblPleaseEnterA)))
					.addContainerGap(11, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblPleaseEnterA)
					.addGap(46)
					.addComponent(lblUsername)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(43)
					.addComponent(lblPassword)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(47)
					.addComponent(lblConfirmPassword)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(passwordFieldConfirm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(58)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnRegister, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(62, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		
	}

	@Override
	/**
	 * Method will handle all the Action Listener of the buttons and text field.
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnRegister)
		{
			System.out.println("Register Successful");
			//Logic for the register account will be done here
			//Logic methods will be implemented later on in the project
			
			// Closes current frame, bring the user back to the main screen
			this.dispose();
		}
		else if(e.getSource() == btnBack)
		{
			System.out.println("Bring user back to the main screen");
			this.dispose();
		}
	}

}
