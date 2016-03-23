package GUI;

import Network.CanvasClient;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

public class SelectCanvasServer extends JFrame implements ActionListener{

	private JPanel contentPane;
	JButton btnJoin;
	JButton btnBack;
	String IP;
	int sPort;
	int dPort;
	private CanvasClient client;


	/**
	 * Create the frame.
	 * @param dstPort 
	 * @param srcPort 
	 * @param stringIP 
	 */
	public SelectCanvasServer(CanvasClient c)
	{
		client = c;
		initialize();
	}
	
	public void initialize()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 594, 483);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblSelectAServer = new JLabel("Select A Server to Join:");
		lblSelectAServer.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JRadioButton rdbtnServer = new JRadioButton("Server#1");
		rdbtnServer.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JRadioButton rdbtnServer_1 = new JRadioButton("Server#2");
		rdbtnServer_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JRadioButton rdbtnServer_2 = new JRadioButton("Server#3");
		rdbtnServer_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JRadioButton rdbtnServer_3 = new JRadioButton("Server#4");
		rdbtnServer_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		
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

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == btnJoin)
		{
			this.dispose();
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
	        newFrame.setVisible(true);
		}
		else if(e.getSource() == btnBack)
		{
			this.dispose();
		}
		
	}

}
