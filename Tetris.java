/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkgtry;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Tetris  {

    JLabel statusbar;
    JLabel statusbar2;
    JFrame newframe;
    JPanel newpanel;
//    JFrame newframeL;
    public Tetris() {
//        newframeL = new JFrame("L");
//        newframeL.setSize(400,800);
        newframe = new JFrame("Qick Start");
//        setSize(200,400);
//        newframeL.setVisible(true);
        newframe.setSize(900,600);
        newframe.setLayout(new GridLayout(1, 1));
        newframe.setVisible(true);
        newpanel = new JPanel();
//        newpanel.setSize(200, 600);
//        newpanel.setLayout(new FlowLayout());        
//        newpanel.setVisible(true);
//        newframeL.add(newpanel);
//        JButton button1 = new JButton();
//        button1.setText("try");
//        newpanel.add(button1);
        statusbar = new JLabel("0");
        statusbar2 = new JLabel("0");
        newpanel.add(statusbar2);
        newpanel.add(statusbar);
//        newframe.add(newpanel);
//        newframe.add(statusbar, BorderLayout.SOUTH);
        Board board = new Board(this,1);
        Board board2 = new Board(this,2);
        newframe.add(board2);
        newframe.add(newpanel);
        newframe.add(board);
        board.start();
        board2.start();
        newframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
   }

   public JLabel player1() {
       return statusbar;
   }
   public JLabel player2(){
       return statusbar2;
   }

 
}
