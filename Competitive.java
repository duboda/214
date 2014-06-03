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
public class Competitive extends Tetris {
    
    /**
     * Class constructor.
     */
    public Competitive() {
        super();
        newFrame.setTitle("Competitive Mode");
        board.addKeyListener(new TAdapter());
        board2.addKeyListener(new TAdapter());
    }
    
    
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (button.getText().equals("restart")){
            newFrame.dispose();
            
            Competitive game = new Competitive();
            
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
            case KeyEvent.VK_1:
                if (board2.numLinesRemoved>=2){
                    board.up();
                    board2.numLinesRemoved -= 2;
                    board2.statusbar.setText(String.valueOf(board2.numLinesRemoved));                    
                }
                break;
            case KeyEvent.VK_2:
                if (board2.numLinesRemoved>=4){
                    board.fast();
                    board2.numLinesRemoved -= 4;
                    board2.statusbar.setText(String.valueOf(board2.numLinesRemoved));                    
                }
                break;                
            case KeyEvent.VK_COMMA:
                if (board.numLinesRemoved>=2){
                    board2.up();
                    board.numLinesRemoved -= 2;
                    board.statusbar.setText(String.valueOf(board.numLinesRemoved));                    
                }
                break;
            case KeyEvent.VK_PERIOD:
                if (board.numLinesRemoved>=4){
                    board2.fast();
                    board.numLinesRemoved -= 4;
                    board.statusbar.setText(String.valueOf(board.numLinesRemoved));                    
                }
                break;
            }
        }
    }
}
