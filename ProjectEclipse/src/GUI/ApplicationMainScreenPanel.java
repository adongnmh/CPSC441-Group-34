package GUI;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.sun.security.ntlm.Client;

import javax.swing.JLabel;
import java.awt.Font;

public class ApplicationMainScreenPanel extends JPanel implements ActionListener{

	
	// Variables for the main 
	JButton createAccountbtn;
	JButton loginBtn;
	
	
	/**
	 * Create the panel.
	 */
	public ApplicationMainScreenPanel() {
		
		initializeMainScreenPanel();

	}
	
	public void initializeMainScreenPanel()
	{
		
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
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
	 * 
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == createAccountbtn)
		{
			System.out.println("Create Account button!");
			CreateAccountFrame newAccount = new CreateAccountFrame();
			newAccount.setVisible(true);
		}
		else if(e.getSource() == loginBtn)
		{
			System.out.println("YES");
			LoginScreenFrame newLoginScreen = new LoginScreenFrame();
			newLoginScreen.setVisible(true);
		}
	}
}
