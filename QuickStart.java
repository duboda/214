/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkgtry;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;

/**
 *
 * @author Kanut Harichanwong
 */
public class QuickStart extends Tetris {
    
    /**
     * Class constructor.
     */
    public QuickStart() {
        super();
        newFrame.setTitle("Quick Start");
        board.addKeyListener(new TAdapter());
        board2.addKeyListener(new TAdapter());
    }
    
    
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (button.getText().equals("try again")){
            newFrame.dispose();
            
            QuickStart game = new QuickStart();
            
            game.newFrame.setLocationRelativeTo(null);
            game.newFrame.setVisible(true);
        }
        else if (button.getText().equals("homepage")){
              newFrame.dispose();
              SwingControlDemo  swingControlDemo = new SwingControlDemo("please choose the model");      
              swingControlDemo.showLabelDemo();
        }
    }
    
    
    /**
     * Event listener with competitive keys
     */
    class TAdapter extends KeyAdapter {
         
        public void keyPressed(KeyEvent e) {
             
            if (!board.isStarted || board.curPiece.getShape() == Shape.Tetrominoes.NoShape)
                return;
            if (!board2.isStarted || board2.curPiece.getShape() == Shape.Tetrominoes.NoShape)  
                return;
             
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
                for (int i = 1 ; i < 4; i++){
                    board2.oneLineDown();
                }
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
                for (int i = 1 ; i < 4; i++){
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
