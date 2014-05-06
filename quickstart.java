/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkgtry;

import java.awt.GridLayout;
import javax.swing.JFrame;

/**
 *
 * @author bodadu
 */
public class quickstart {
    JFrame newframe = new JFrame("Quick Start");    
    public void tetris(){
        newframe.setSize(600,400);
        newframe.setLayout(new GridLayout(2, 1)); 
        newframe.setVisible(true);        
    }    
    
}
