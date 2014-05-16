/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkgtry;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
    
    //JLabel nextLabel;
    JLabel nextPiece;
    JLabel nextPiece2;
    
    JLabel heldPiece;
    JLabel heldPiece2;
    
    JFrame newframe;
    JPanel newpanel;
    Board board;
    Board board2;    
//    JFrame newframeL;
    public Tetris() {
//        newframeL = new JFrame("L");
//        newframeL.setSize(400,800);
        newframe = new JFrame("Quick Start");
//        setSize(200,400);
//        newframeL.setVisible(true);
        newframe.setSize(900,600);
        newframe.setLayout(new GridLayout(1,3));
        newframe.setVisible(true);
        newpanel = new JPanel();
        newpanel.setLayout(new GridLayout(4,2));
//        newpanel.setSize(200, 600);
//        newpanel.setLayout(new FlowLayout());        
//        newpanel.setVisible(true);
//        newframeL.add(newpanel);
//        JButton button1 = new JButton();
//        button1.setText("try");
//        newpanel.add(button1);
        
        
        // create all labels
        statusbar = new JLabel("0");statusbar2 = new JLabel("0");
        nextPiece = new JLabel("Next:"); nextPiece2 = new JLabel("Next:");
        heldPiece = new JLabel("Held:"); heldPiece2 = new JLabel("Held:");
        
        // add all labels to panel
        newpanel.add(statusbar2); newpanel.add(statusbar);
        newpanel.add(nextPiece2); newpanel.add(nextPiece);
        newpanel.add(heldPiece2); newpanel.add(heldPiece);
        
        
//        newframe.add(newpanel);
//        newframe.add(statusbar, BorderLayout.SOUTH);
        board = new Board(this,1, newpanel);
        board2 = new Board(this,2, newpanel);
        
        // add components to frame
        newframe.add(board2);
        newframe.add(newpanel);
        newframe.add(board);
       board.addKeyListener(new TAdapter());
       board2.addKeyListener(new TAdapter());        
        // start boards
        board.start();
        board2.start();
        
        
        newframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
   }

    public JLabel player1() {return statusbar;}
    public JLabel player2() {return statusbar2;}
   
    public JLabel nextPiece1() {return nextPiece;}
    public JLabel nextPiece2() {return nextPiece2;}
    
    public JLabel heldPiece1() {return heldPiece;}
    public JLabel heldPiece2() {return heldPiece2;}
       class TAdapter extends KeyAdapter {
         public void keyPressed(KeyEvent e) {

             if (!board.isStarted || board.curPiece.getShape() == Shape.Tetrominoes.NoShape) {  
                 return;
             }
             if (!board2.isStarted || board2.curPiece.getShape() == Shape.Tetrominoes.NoShape) {  
                 return;
             }             

             int keycode = e.getKeyCode();

             if (keycode == KeyEvent.VK_SPACE) {
                 board.pause();
                 board2.pause();
                 return;
             }

             if (board.isPaused)
                 return;

             switch (keycode) {
             case 'a':
             case 'A':
                 board2.tryMove(board2.curPiece, board2.curX - 1, board2.curY);
                 break; 
             case 's':
             case 'S':
                 board2.oneLineDown();
                 break;
             case 'd':
             case 'D':
                 board2.tryMove(board2.curPiece, board2.curX + 1, board2.curY);
                 break;                 
             case 'w': 
             case 'W':
                 board2.tryMove(board2.curPiece.rotateRight(), board2.curX, board2.curY);
                 break;                  
             case KeyEvent.VK_LEFT:
                 board.tryMove(board.curPiece, board.curX - 1, board.curY);
                 break;
             case KeyEvent.VK_RIGHT:
                 board.tryMove(board.curPiece, board.curX + 1, board.curY);
                 break;
             case KeyEvent.VK_DOWN:
                 for (int i = 1 ; i<4;i++){
                    board.oneLineDown();
                 }
                 break;
             case KeyEvent.VK_UP:
                 board.tryMove(board.curPiece.rotateRight(), board.curX, board.curY);
                 break;
             case KeyEvent.VK_SHIFT:
                    int keyLoc = e.getKeyLocation();
                    if (keyLoc == KeyEvent.KEY_LOCATION_RIGHT)
                        board.hold();
                    else 
                        board2.hold();
                    
                    break;
             }

         }
     }

}