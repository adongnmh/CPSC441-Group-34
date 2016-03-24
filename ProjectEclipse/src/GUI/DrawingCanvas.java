package GUI;



import javax.swing.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.GroupLayout.Alignment;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.BorderLayout;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Network.*;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

public class DrawingCanvas extends JPanel implements ActionListener, ChangeListener {
	
	 // Image that we will draw using the paint.
    private Image image;
    
    private Image imageToUpload;
    // Graphics2D object this is what we will use to draw on
    private Graphics2D drawing;

    //Coordinates of the mouse X/Y old and new coordinates used
    // for drawing the image of the user
    private int oldXCoord;
    private int oldYCoord;
    private int currentXCoord;
    private int currentYCoord;
    private int intSizeOfPen = 5;
    private double doubleSizeOfPen;
    
    private UploadImageFrame uploadFrame;
    String currentDir = "";
    
    private String imageToUploadPath;

	private CanvasClient client;
	private JPanel panel;
	
	//Declaring button variables
	private JButton btnRed;
	private JButton btnBlack;
	private JButton btnGreen;
	private JButton btnYellow;
	private JButton btnBlue;
	private JButton btnMagenta;
	private JButton btnOrange;
	private JButton btnPink;
	private JSlider penSlider;
	private JButton btnEraser;
	private JButton btnClear;
	private JButton btnUpload;
	private JTextField addFriendField;
	private JButton btnAddFriend;
	private JButton btnListFriends;
	private JButton btnBanUser;
	private JTextField textField;
	private JPanel friendPanel;
	private List<String> friendsList = new ArrayList<String>();
    
    /**
     * This method will first initialize the canvas and all the GUI components of the JPanel
     * such as the Buttons, slider,etc. The method will also initialize all the ActionListener and 
     * ChangeListener for all the mouse events and all the action events.
     * @param dstPort
     * @param srcPort
     * @param stringIP
     * @throws Exception 
     */
    public DrawingCanvas(CanvasClient c) 
    {
		client = c;
		InitializeCanvas();
		c.start();
		
		// Listening for whenever the mouse is pressed. If the mouse is pressed,
        // when pressed we will know where on the canvas the line will start using
        // the get methods.
        //SOURCE: http://www.tutorialspoint.com/awt/awt_mouseadapter.htm
		addMouseListener(new MouseAdapter()
        {
        	// ActionListener for when the user presses the mouse this will get the coordinates of when the user
        	// presses the mouse so that it can be paired with the end point of the line
            public void mousePressed(MouseEvent e)
            {
                // save coord x,y when mouse is pressed
                oldXCoord = e.getX();
                oldYCoord = e.getY();
            }
        });

        // Listening for whenever the mouse is being dragged across the JPanel
        // new cordinates of the image/line will be calculated using the get methods
        // SOURCE: http://www.tutorialspoint.com/awt/awt_mouseadapter.htm
        addMouseMotionListener(new MouseMotionAdapter()
        {
        	// ActionListener for the mouse dragged event. Calculates the the current X and Y coordinates when
        	// the mouse has stop 
            public void mouseDragged(MouseEvent e)
            {
                // coord x,y when drag mouse
                currentXCoord = e.getX();
                currentYCoord = e.getY();

                if (drawing != null)
                {
                    // draw line if drawing context not null
                    drawing.setStroke(new BasicStroke(intSizeOfPen));
                	drawing.drawLine(oldXCoord, oldYCoord, currentXCoord, currentYCoord);
					try
					{
						c.updateCanvas(oldXCoord, oldYCoord, currentXCoord, currentYCoord);
					}
					catch(Exception ex)
					{
						//ignore
					}
                    // refresh draw area to repaint
                    repaint();

                    // Storing the old x and y coordinates as the current x and y
                    // coordinates
                    oldXCoord = currentXCoord;
                    oldYCoord = currentYCoord;
					try
					{
						c.updateCanvas(oldXCoord, oldYCoord, currentXCoord, currentYCoord);
					}
					catch(Exception ex)
					{
						//ignore
					}
                }
            }
        });

        // Listening for whenever the mouse is being dragged across the JPanel
        // new cordinates of the image/line will be calculated using the get methods
        // SOURCE: http://www.tutorialspoint.com/awt/awt_mouseadapter.htm
        addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                // coord x,y when drag mouse
                currentXCoord = e.getX();
                currentYCoord = e.getY();

                if (drawing != null)
                {
                    // draw line if drawing context not null
                    drawing.setStroke(new BasicStroke(intSizeOfPen));
                    drawing.drawLine(oldXCoord, oldYCoord, currentXCoord, currentYCoord);
                    // refresh draw area to repaint
                    
                    repaint();

                    // Storing the old x and y coordinates as the current x and y
                    // coordinates
                    oldXCoord = currentXCoord;
                    oldYCoord = currentYCoord;
					try
					{
						c.updateCanvas(oldXCoord, oldYCoord, currentXCoord, currentYCoord);
					}
					catch(Exception ex)
					{
						//ignore
					}
                }
            }
        });
    }

	
       

    protected  synchronized void paintComponent(Graphics g)
    {
    	super.paintComponent(g);
        if (image == null)
        {
            // image to draw null initializing the image
            image = createImage(getSize().width, getSize().height);
            drawing = (Graphics2D) image.getGraphics();
            // enable initializing - smoother Lines
            drawing.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // clear draw area
            clear();
        }

        g.drawImage(image, 0, 0, null);
        repaint();
    }

    /**
     * Clear method will clear the whole canvas. This will be implemented with
     * the clear button Date Last Modified: 02/07/2016
     */
    public void clear()
    {
        drawing.setPaint(Color.white);
        // draw white on entire draw area to clear
        drawing.fillRect(0, 0, getSize().width, getSize().height);
        drawing.setPaint(Color.black);
        repaint();
    }
    
    public void ChangePenSize(int penSize)
    {
    	doubleSizeOfPen = (penSize)/10;
    	intSizeOfPen = (int) Math.round(doubleSizeOfPen);
    	System.out.println(doubleSizeOfPen);
    }
    
    public void UpdatedLine(int oldX, int oldY, int newX, int newY)
    {
    	System.out.println("UPDATELINE");
    	if(image != null)
    	{
	        drawing.setStroke(new BasicStroke(intSizeOfPen));
	    	drawing.drawLine(oldX, oldY, newX, newY);
	    	repaint();
    	}
    }
    
    public void save()
    {

    	BufferedImage bi = new BufferedImage(this.getSize().width,this.getSize().height, BufferedImage.TYPE_INT_ARGB);	
    	drawing = bi.createGraphics();
    	drawing.drawImage(image, 0, 0, null);
        try {
			ImageIO.write(bi, "PNG", new File("yourImageName.PNG"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        repaint();

    }
    //upload function
    public void upload(String path)
    {
    	ImageIcon img = new ImageIcon(path);
		imageToUpload = img.getImage();
		drawing.drawImage(imageToUpload,450,0,null);
		repaint();
    }

    /**
     * Initializes canvas
     */
    public void InitializeCanvas()
    {
	    	
	    	panel = new JPanel();
	    	panel.setBackground(Color.LIGHT_GRAY);
	    	
	    	friendPanel = new JPanel(new BorderLayout());
	    	friendPanel.setBackground(Color.GREEN);
	    	friendPanel.setVisible(false);
	    	GroupLayout groupLayout = new GroupLayout(this);
	    	groupLayout.setHorizontalGroup(
	    		groupLayout.createParallelGroup(Alignment.LEADING)
	    			.addGroup(groupLayout.createSequentialGroup()
	    				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
	    				.addPreferredGap(ComponentPlacement.RELATED)
	    				.addComponent(friendPanel, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
	    				.addContainerGap(731, Short.MAX_VALUE))
	    	);
	    	groupLayout.setVerticalGroup(
	    		groupLayout.createParallelGroup(Alignment.LEADING)
	    			.addComponent(panel, GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
	    			.addGroup(groupLayout.createSequentialGroup()
	    				.addComponent(friendPanel, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
	    				.addContainerGap(400, Short.MAX_VALUE))
	    	);
	    	
	    	btnBlack = new JButton("");
	    	btnBlack.addActionListener(this);
	    	btnBlack.setBackground(Color.BLACK);
	    	
	    	btnRed = new JButton("");
	    	btnRed.addActionListener(this);
	    	btnRed.setBackground(Color.RED);
	    	
	    	btnGreen = new JButton("");
	    	btnGreen.addActionListener(this);
	    	btnGreen.setBackground(Color.GREEN);
	    	
	    	btnYellow = new JButton("");
	    	btnYellow.addActionListener(this);
	    	btnYellow.setBackground(Color.YELLOW);
	    	
	    	btnBlue = new JButton("");
	    	btnBlue.addActionListener(this);
	    	btnBlue.setBackground(Color.BLUE);
	    	
	    	btnMagenta = new JButton("");
	    	btnMagenta.addActionListener(this);
	    	btnMagenta.setBackground(Color.MAGENTA);
	    	
	    	btnOrange = new JButton("");
	    	btnOrange.addActionListener(this);
	    	btnOrange.setBackground(Color.ORANGE);
	    	
	    	btnPink = new JButton("");
	    	btnPink.addActionListener(this);
	    	btnPink.setBackground(Color.PINK);
	    	
	    	penSlider = new JSlider();
	    	penSlider.addChangeListener(this);
	    	
	    	JLabel lblPenSize = new JLabel("Pen Size");
	    	lblPenSize.setFont(new Font("Tahoma", Font.BOLD, 10));
	    	
	    	btnEraser = new JButton("");
	    	btnEraser.addActionListener(this);
	    	btnEraser.setIcon(new ImageIcon("Eraser-512.png"));
	    	btnEraser.setFont(new Font("Tahoma", Font.BOLD, 7));
	    	
	    	btnClear = new JButton("CLEAR");
	    	btnClear.addActionListener(this);
	    	btnClear.setFont(new Font("Tahoma", Font.BOLD, 6));
	    	
	    	btnUpload = new JButton("UPLOAD");
	    	btnUpload.addActionListener(this);
	    	btnUpload.setFont(new Font("Tahoma", Font.BOLD, 6));
	    	
	    	addFriendField = new JTextField();
	    	addFriendField.setColumns(10);
	    	
	    	btnAddFriend = new JButton("Add Friend:");
	    	btnAddFriend.addActionListener(this);
	    	
	    	btnListFriends = new JButton("List Friends:");
	    	btnListFriends.addActionListener(this);
	    	
	    	btnBanUser = new JButton("Ban User:");
	    	btnBanUser.addActionListener(this);
	    	
	    	
	    	textField = new JTextField();
	    	textField.setColumns(10);
	    	GroupLayout gl_panel = new GroupLayout(panel);
	    	gl_panel.setHorizontalGroup(
	    		gl_panel.createParallelGroup(Alignment.LEADING)
	    			.addGroup(gl_panel.createSequentialGroup()
	    				.addContainerGap()
	    				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
	    					.addComponent(btnBlack, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
	    					.addComponent(btnGreen, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
	    					.addComponent(btnBlue, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
	    					.addComponent(btnOrange, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
	    				.addPreferredGap(ComponentPlacement.UNRELATED)
	    				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
	    					.addComponent(btnRed, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
	    					.addGroup(gl_panel.createSequentialGroup()
	    						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
	    							.addComponent(btnYellow, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
	    							.addComponent(btnMagenta, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
	    							.addComponent(btnPink, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
	    						.addGap(7)
	    						.addComponent(btnEraser, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)))
	    				.addContainerGap(11, Short.MAX_VALUE))
	    			.addGroup(gl_panel.createSequentialGroup()
	    				.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
	    					.addGroup(gl_panel.createSequentialGroup()
	    						.addGap(25)
	    						.addComponent(lblPenSize, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
	    					.addComponent(penSlider, GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
	    				.addGap(20))
	    			.addGroup(gl_panel.createSequentialGroup()
	    				.addGap(3)
	    				.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
	    					.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 115, Short.MAX_VALUE)
	    					.addComponent(btnUpload, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 115, Short.MAX_VALUE))
	    				.addGap(17))
	    			.addGroup(gl_panel.createSequentialGroup()
	    				.addGap(3)
	    				.addComponent(btnAddFriend)
	    				.addContainerGap(43, Short.MAX_VALUE))
	    			.addGroup(gl_panel.createSequentialGroup()
	    				.addGap(2)
	    				.addComponent(addFriendField, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
	    				.addContainerGap(50, Short.MAX_VALUE))
	    			.addGroup(gl_panel.createSequentialGroup()
	    				.addGap(2)
	    				.addComponent(btnBanUser)
	    				.addContainerGap(54, Short.MAX_VALUE))
	    			.addGroup(gl_panel.createSequentialGroup()
	    				.addGap(1)
	    				.addComponent(textField, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
	    				.addContainerGap(51, Short.MAX_VALUE))
	    			.addGroup(gl_panel.createSequentialGroup()
	    				.addContainerGap()
	    				.addComponent(btnListFriends)
	    				.addContainerGap(21, Short.MAX_VALUE))
	    	);
	    	gl_panel.setVerticalGroup(
	    		gl_panel.createParallelGroup(Alignment.LEADING)
	    			.addGroup(gl_panel.createSequentialGroup()
	    				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
	    					.addGroup(gl_panel.createSequentialGroup()
	    						.addComponent(btnRed, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
	    						.addPreferredGap(ComponentPlacement.RELATED)
	    						.addComponent(btnYellow, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
	    						.addPreferredGap(ComponentPlacement.RELATED)
	    						.addComponent(btnMagenta, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
	    						.addPreferredGap(ComponentPlacement.RELATED)
	    						.addComponent(btnPink, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
	    					.addGroup(gl_panel.createSequentialGroup()
	    						.addComponent(btnBlack, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
	    						.addPreferredGap(ComponentPlacement.RELATED)
	    						.addComponent(btnGreen, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
	    						.addPreferredGap(ComponentPlacement.RELATED)
	    						.addComponent(btnBlue, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
	    						.addPreferredGap(ComponentPlacement.RELATED)
	    						.addComponent(btnOrange, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
	    					.addGroup(gl_panel.createSequentialGroup()
	    						.addGap(55)
	    						.addComponent(btnEraser, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)))
	    				.addPreferredGap(ComponentPlacement.UNRELATED)
	    				.addComponent(lblPenSize)
	    				.addPreferredGap(ComponentPlacement.RELATED)
	    				.addComponent(penSlider, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
	    				.addGap(15)
	    				.addComponent(btnClear)
	    				.addPreferredGap(ComponentPlacement.RELATED)
	    				.addComponent(btnUpload, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
	    				.addGap(4)
	    				.addComponent(btnAddFriend)
	    				.addGap(5)
	    				.addComponent(addFriendField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    				.addGap(4)
	    				.addComponent(btnBanUser)
	    				.addGap(4)
	    				.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    				.addGap(107)
	    				.addComponent(btnListFriends)
	    				.addContainerGap(112, Short.MAX_VALUE))
	    	);
	    	panel.setLayout(gl_panel);
	    	setLayout(groupLayout);
	 }

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == btnBlack)
		{
			drawing.setPaint(Color.black);
		}
		else if(e.getSource()==btnRed)
		{
			drawing.setPaint(Color.red);
		}
		else if(e.getSource()==btnBlue)
		{
			drawing.setPaint(Color.blue);
		}
		else if(e.getSource()==btnYellow)
		{
			drawing.setPaint(Color.YELLOW);
		}
		else if(e.getSource()==btnOrange)
		{
			drawing.setPaint(Color.orange);
		}
		else if(e.getSource()==btnMagenta)
		{
			drawing.setPaint(Color.magenta);
		}
		else if(e.getSource()==btnPink)
		{
			drawing.setPaint(Color.pink);
		}
		else if(e.getSource()==btnGreen)
		{
			drawing.setPaint(Color.green);
		}
		else if(e.getSource() == btnClear)
		{
			clear();
		}
		else if(e.getSource() == btnEraser)
		{
			drawing.setPaint(Color.WHITE);
		}
		
		else if(e.getSource() == btnListFriends)
		{
			try{
				client.listFriends();
			}
			catch(Exception ex)
			{
				//ignore
			}
	    	friendPanel.setVisible(true);
		}
		else if(e.getSource() == btnBanUser)
		{
			
		}
		else if(e.getSource() == btnUpload)
		{
	    	uploadFrame = new UploadImageFrame(this);
	    	uploadFrame.setVisible(true);
		}
		
	}

	@Override
	public void stateChanged(ChangeEvent e) 
	{
		JSlider source = (JSlider)e.getSource();
		int sliderNum;
		if(source.getValueIsAdjusting())
		{
			if((int)source.getValue() == 0)
			{
				sliderNum = 10;
				ChangePenSize(sliderNum);
			}
			else
			{	
				sliderNum = (int)source.getValue();
				ChangePenSize(sliderNum);
			}
		}
	}

	//Populate the friends list array with string from server
	public void listFriends(String list)
	{
		String[] friendsList = list.split("\t");
		System.out.println(friendsList[0]);
		//ListModel model = new DefaultListModel();
		//friendPanel.add(new JScrollPane(new JList(friendsList)));
		//friendPanel.add(new JList(friendsList));


	}
}
