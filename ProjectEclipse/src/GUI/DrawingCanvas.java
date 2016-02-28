package GUI;



import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

public class DrawingCanvas extends JPanel implements ActionListener {
	
	 // Image that we will draw using the paint.
    private Image image;
    // Graphics2D object this is what we will use to draw on
    private Graphics2D drawing;

    //Corrdinates of the mouse X/Y old and new coordinates used
    // for drawing the image of the user
    private int oldXCoord;
    private int oldYCoord;
    private int currentXCoord;
    private int currentYCoord;
    private String paintColor;
    private JButton btnRed;
    private JButton btnBlue;
    private JButton btnGreen;
    private JButton btnClear;
    private JButton btnEraser;

    
    
    /**
     * Creates new form DrawingCanvase
     *
     * @param currentFrame
     */
    public DrawingCanvas()
    {
    	


    	InitializeCanvas();


        System.out.println(paintColor);
        if ("blue".equals(paintColor))
        {
            drawing.setPaint(Color.blue);
        }

        // Listening for whenever the mouse is pressed. If the mouse is pressed,
        // when pressed we will know where on the canvas the line will start using
        // the get methods.
        //SOURCE: http://www.tutorialspoint.com/awt/awt_mouseadapter.htm
        addMouseListener(new MouseAdapter()
        {
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
            public void mouseDragged(MouseEvent e)
            {
                // coord x,y when drag mouse
                currentXCoord = e.getX();
                currentYCoord = e.getY();

                if (drawing != null)
                {
                    // draw line if drawing context not null
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

    protected void paintComponent(Graphics g)
    {
        if (image == null)
        {
            // image to draw null initializing the image
            image = createImage(getSize().width, getSize().height);
            drawing = (Graphics2D) image.getGraphics();
            // enable antialiasing - smoother Lines
            drawing.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // clear draw area
            clear();
        }

        g.drawImage(image, 0, 0, null);
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
	    		groupLayout.createParallelGroup(Alignment.LEADING)
	    			.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	    	);
	    	groupLayout.setVerticalGroup(
	    		groupLayout.createParallelGroup(Alignment.LEADING)
	    			.addGroup(groupLayout.createSequentialGroup()
	    				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
	    				.addContainerGap(424, Short.MAX_VALUE))
	    	);
	    	
	    	// Initializing Buttons and adding an actionListener to the corresponding button
			btnRed = new JButton("RED");
			btnRed.addActionListener(this);
			
			btnBlue = new JButton("BLUE");
			btnBlue.addActionListener(this);
			
			btnGreen = new JButton("GREEN");
			btnGreen.addActionListener(this);
			
			btnClear = new JButton("Clear");
			btnClear.addActionListener(this);
	    	
	    	btnEraser = new JButton("Eraser");
	    	btnEraser.addActionListener(this);
			
			
	    	GroupLayout gl_panel = new GroupLayout(panel);
	    	gl_panel.setHorizontalGroup(
	    		gl_panel.createParallelGroup(Alignment.LEADING)
	    			.addGroup(gl_panel.createSequentialGroup()
	    				.addGap(19)
	    				.addComponent(btnRed, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
	    				.addPreferredGap(ComponentPlacement.UNRELATED)
	    				.addComponent(btnBlue, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
	    				.addPreferredGap(ComponentPlacement.RELATED)
	    				.addComponent(btnGreen)
	    				.addPreferredGap(ComponentPlacement.RELATED, 218, Short.MAX_VALUE)
	    				.addComponent(btnEraser, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
	    				.addPreferredGap(ComponentPlacement.UNRELATED)
	    				.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
	    				.addContainerGap())
	    	);
	    	gl_panel.setVerticalGroup(
	    		gl_panel.createParallelGroup(Alignment.LEADING)
	    			.addGroup(gl_panel.createSequentialGroup()
	    				.addContainerGap()
	    				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
	    					.addComponent(btnRed)
	    					.addComponent(btnGreen)
	    					.addComponent(btnBlue)
	    					.addComponent(btnClear)
	    					.addComponent(btnEraser))
	    				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	    	);
	    	panel.setLayout(gl_panel);
	    	setLayout(groupLayout);
	 }

	@Override
	public void actionPerformed(ActionEvent e) {
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
		
		
	}
}
