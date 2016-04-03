package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

/**
 * This class will upload the image to the canvas
 * @author Group 34
 * Last Modified: March 24th 2016
 *
 */
public class UploadImageFrame extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField imageFilePath;
	private JButton btnBack;
	private JButton btnUpload;
	private String filePath;
	private DrawingCanvas theCanvas;

	/**
	 * Create the frame.
	 * @param drawingCanvas 
	 */
	public UploadImageFrame(DrawingCanvas drawingCanvas) 
	{
		theCanvas = drawingCanvas;
		initialize();
	}
	
	public String getFilePath()
	{
		return filePath;
	}
	
	public void initialize()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 470, 310);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(this);
		
		btnUpload = new JButton("Upload");
		btnUpload.addActionListener(this);

		
		imageFilePath = new JTextField();
		imageFilePath.setColumns(10);
		
		JLabel lblEnterTheFile = new JLabel("Enter the file path of the  image source that you want to upload to the canvas");
		lblEnterTheFile.setFont(new Font("Tahoma", Font.BOLD, 10));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(136)
					.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnUpload, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(148, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(22)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblEnterTheFile, GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(imageFilePath, GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
							.addGap(30))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(49)
					.addComponent(lblEnterTheFile, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(imageFilePath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(54)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnBack)
						.addComponent(btnUpload))
					.addContainerGap(91, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == btnBack)
		{
			this.dispose();
		}
		else if(e.getSource() == btnUpload)
		{
			filePath = imageFilePath.getText();
			
			this.dispose();
			
			try {
				theCanvas.Upload(filePath);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
