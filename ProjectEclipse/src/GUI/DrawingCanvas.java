package GUI;



import javax.swing.JPanel;
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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JSlider;

import Network.*;

public class DrawingCanvas extends JPanel implements ActionListener, ChangeListener {
	
	 // Image that we will draw using the paint.
    private Image image;
    // Graphics2D object this is what we will use to draw on
    private Graphics2D drawing;

    //Coordinates of the mouse X/Y old and new coordinates used
    // for drawing the image of the user
    private int oldXCoord;
    private int oldYCoord;
    private int currentXCoord;
    private int currentYCoord;
    private JButton btnRed;
    private JButton btnBlue;
    private JButton btnGreen;
    private JButton btnClear;
    private JButton btnEraser;
    private JSlider penSlider;
    private JButton btnExport;
    private JButton btnUpload;
    private int intSizeOfPen = 5;
    private double doubleSizeOfPen;
    
    

	private CanvasClient client;

    
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
                 
                    // refresh draw area to repaint
                    repaint();

                    // Storing the old x and y coordinates as the current x and y
                    // coordinates
                    oldXCoord = currentXCoord;
                    oldYCoord = currentYCoord;                    
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


    /**
     * Initializes canvas
     */
    public void InitializeCanvas()
    {

		 	// Initializing the header bar
	    	JPanel panel = new JPanel();
	    	panel.setBackground(Color.LIGHT_GRAY);
	    	GroupLayout groupLayout = new GroupLayout(this);
	    	groupLayout.setHorizontalGroup(
	    		groupLayout.createParallelGroup(Alignment.TRAILING)
	    			.addComponent(panel, GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
	    	);
	    	groupLayout.setVerticalGroup(
	    		groupLayout.createParallelGroup(Alignment.LEADING)
	    			.addGroup(groupLayout.createSequentialGroup()
	    				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
	    				.addContainerGap(380, Short.MAX_VALUE))
	    	);
	    	
	    	// Initializing Buttons and adding an actionListener to the corresponding button
			btnRed = new JButton("");
			btnRed.setBackground(Color.RED);
			btnRed.addActionListener(this);
			
			btnBlue = new JButton("");
			btnBlue.setBackground(Color.BLUE);
			btnBlue.addActionListener(this);
			
			btnGreen = new JButton("");
			btnGreen.setBackground(Color.GREEN);
			btnGreen.addActionListener(this);
			
			btnClear = new JButton("Clear");
			btnClear.addActionListener(this);
	    	
	    	btnEraser = new JButton("Eraser");
	    	btnEraser.addActionListener(this);
	    	
	    	penSlider = new JSlider();
	    	penSlider.addChangeListener(this);
	    	
	    	btnExport = new JButton("Export");
	    	btnExport.addActionListener(this);
	    	btnUpload = new JButton("Upload");
	    	btnUpload.addActionListener(this);
			
			
	    	GroupLayout gl_panel = new GroupLayout(panel);
	    	gl_panel.setHorizontalGroup(
	    		gl_panel.createParallelGroup(Alignment.LEADING)
	    			.addGroup(gl_panel.createSequentialGroup()
	    				.addContainerGap()
	    				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
	    					.addGroup(gl_panel.createSequentialGroup()
	    						.addComponent(btnRed, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
	    						.addPreferredGap(ComponentPlacement.RELATED)
	    						.addComponent(btnGreen, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
	    						.addPreferredGap(ComponentPlacement.RELATED)
	    						.addComponent(btnBlue, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
	    					.addComponent(penSlider, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE))
	    				.addPreferredGap(ComponentPlacement.RELATED, 241, Short.MAX_VALUE)
	    				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
	    					.addGroup(gl_panel.createSequentialGroup()
	    						.addComponent(btnEraser, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
	    						.addPreferredGap(ComponentPlacement.UNRELATED)
	    						.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
	    					.addGroup(gl_panel.createSequentialGroup()
	    						.addComponent(btnExport)
	    						.addPreferredGap(ComponentPlacement.RELATED)
	    						.addComponent(btnUpload)))
	    				.addContainerGap())
	    	);
	    	gl_panel.setVerticalGroup(
	    		gl_panel.createParallelGroup(Alignment.LEADING)
	    			.addGroup(gl_panel.createSequentialGroup()
	    				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
	    					.addGroup(gl_panel.createSequentialGroup()
	    						.addContainerGap()
	    						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
	    							.addComponent(btnClear)
	    							.addComponent(btnEraser)))
	    					.addComponent(btnRed, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
	    					.addComponent(btnGreen, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
	    					.addComponent(btnBlue, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
	    				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
	    					.addGroup(gl_panel.createSequentialGroup()
	    						.addPreferredGap(ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
	    						.addComponent(penSlider, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
	    						.addContainerGap())
	    					.addGroup(gl_panel.createSequentialGroup()
	    						.addPreferredGap(ComponentPlacement.UNRELATED)
	    						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
	    							.addComponent(btnExport)
	    							.addComponent(btnUpload))
	    						.addContainerGap())))
	    	);
	    	panel.setLayout(gl_panel);
	    	setLayout(groupLayout);
	 }

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		if(e.getSource() == btnRed)
		{
	        drawing.setPaint(Color.RED);
		}
		else if(e.getSource() == btnBlue)
		{
	        drawing.setPaint(Color.BLUE);
		}
		else if(e.getSource() == btnGreen)
		{
	        drawing.setPaint(Color.GREEN);
		}
		else if(e.getSource() == btnEraser)
		{
			drawing.setPaint(Color.WHITE);
		}
		else if(e.getSource() == btnClear)
		{
			clear();
		}
		else if(e.getSource() == btnExport)
		{
			save();
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
}
