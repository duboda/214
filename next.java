/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkgtry;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import pkgtry.Shape.Tetrominoes;
/**
 *
 * @author bodadu
 */
public class next extends JPanel{
    static final int BOARD_WIDTH = 4;
    static final int BOARD_HEIGHT = 4;
    Shape curPiece;
    int squareWidth() { return (int) getWidth() / BOARD_WIDTH; }
    int squareHeight() {return (int) getHeight() / BOARD_HEIGHT; } 
    public void next(){
        Tetrominoes[] values = Tetrominoes.values();        
        curPiece.setShape(values[3]);    
    }
    public void paint(Graphics g)
    { 
        super.paint(g);
        Dimension size = getSize();
//        int kkk = (int)size.getHeight();
//        System.out.println("getsize"+kkk);
        int boardTop = (int) size.getHeight() - BOARD_HEIGHT * squareHeight();
//        System.out.println(boardTop);

       

        if (curPiece.getShape() != Tetrominoes.NoShape) {
            for (int i = 0; i < 4; ++i) {
                int x = curPiece.x(i);
                int y = 4-curPiece.y(i);
                drawSquare(g, 0 + x * squareWidth(),
                           boardTop + (BOARD_HEIGHT - y - 1) * squareHeight(),
                           curPiece.getShape());
            }
        }
    }
     private void drawSquare(Graphics g, int x, int y, Tetrominoes shape)
    {
        Color colors[] = { new Color(0, 0, 0), new Color(204, 102, 102), 
            new Color(102, 204, 102), new Color(102, 102, 204), 
            new Color(204, 204, 102), new Color(204, 102, 204), 
            new Color(102, 204, 204), new Color(218, 170, 0)
        };


        Color color = colors[shape.ordinal()];

        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth()-1, squareHeight()-1);
    }
}
