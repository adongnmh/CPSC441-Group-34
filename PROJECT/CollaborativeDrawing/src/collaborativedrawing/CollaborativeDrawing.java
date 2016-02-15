package collaborativedrawing;

import GUI.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;

/**
 * Controller of the application. Will Create/Initialize a Frame for the whole
 * application
 *
 * @author CPSC 441 Group 34 Date Last Modified: 02/07/2016
 */
public class CollaborativeDrawing
{

    public static void main(String[] args)
    {
        // TODO code application logic here
        DrawingScreenFrame newFrame = new DrawingScreenFrame();

        Container content = newFrame.getContentPane();
        content.setLayout(new BorderLayout());
        DrawingCanvas newPiece = new DrawingCanvas(newFrame);
        content.add(newPiece, BorderLayout.SOUTH);
        content.setVisible(true);
        newFrame.setVisible(true);
    }

}
