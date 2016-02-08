/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collaborativedrawing;
import  GUI.*;
import java.awt.BorderLayout;
import java.awt.Container;

/**
 * Controller of the application. Will Create/Initialize a Frame 
 * for the whole application
 * @author CPSC 441 Group 34
 * Date Last Modified: 02/07/2016
 */
public class CollaborativeDrawing 
{

    public static void main(String[] args) 
    {
        // TODO code application logic here
        DrawingScreenFrame newFrame = new DrawingScreenFrame();
      

        Container content = newFrame.getContentPane();
        content.setLayout(new BorderLayout());
        DrawingCanvas newPiece = new DrawingCanvas();
        content.add(newPiece,BorderLayout.SOUTH);
        content.setVisible(true);
        newFrame.setVisible(true);
        
        
    }
    
}
