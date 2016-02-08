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
 *
 * @author Andrew Dong
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
