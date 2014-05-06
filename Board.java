/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkgtry;

/**
 *
 * @author bodadu
 */
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


public class Board extends JPanel implements ActionListener {


    final int BoardWidth = 10;
    final int BoardHeight = 20;

    Timer timer;
    boolean isFallingFinished = false;
    boolean isStarted = false;
    boolean isPaused = false;
    int numLinesRemoved = 0;
    int curX = 0;
    int curY = 0;
    JLabel statusbar;
    Shape curPiece;
    Tetrominoes[] board;
    int player = 0;



    public Board(Tetris parent, int m) {
       player = m;
       setBorder(BorderFactory.createLoweredBevelBorder());
       setFocusable(true);
       curPiece = new Shape();
       timer = new Timer(400, this);
       timer.start(); 
       if (m==1){
            statusbar =  parent.player1();
       }
       if (m==2){
            statusbar = parent.player2();
       }
       board = new Tetrominoes[BoardWidth * BoardHeight];
//       if (player == 1){
//            addKeyListener(new TAdapter1());
//            System.out.println("111111111111111");
//       }
//       if (player == 2){
//            addKeyListener(new TAdapter2());
//            System.out.println("22222222222222222");
//       }
       addKeyListener(new TAdapter());
       clearBoard();  
    }

    public void actionPerformed(ActionEvent e) {
        if (isFallingFinished) {
            isFallingFinished = false;
            newPiece();
        } else {
            oneLineDown();
//            oneLineDown();
        }
    }


    int squareWidth() { return (int) getWidth() / BoardWidth; }
    int squareHeight() {return (int) getHeight() / BoardHeight; }
    Tetrominoes shapeAt(int x, int y) { return board[(y * BoardWidth) + x]; }


    public void start()
    {
        if (isPaused)
            return;

        isStarted = true;
        isFallingFinished = false;
        numLinesRemoved = 0;
        clearBoard();

        newPiece();
        timer.start();
    }

    private void pause()
    {
        if (!isStarted)
            return;

        isPaused = !isPaused;
        if (isPaused) {
            timer.stop();
            statusbar.setText("paused");
        } else {
            timer.start();
            statusbar.setText(String.valueOf(numLinesRemoved));
        }
        repaint();
    }

    public void paint(Graphics g)
    { 
        super.paint(g);

        Dimension size = getSize();
//        int kkk = (int)size.getHeight();
//        System.out.println("getsize"+kkk);
        int boardTop = (int) size.getHeight() - BoardHeight * squareHeight();
//        System.out.println(boardTop);

        for (int i = 0; i < BoardHeight; ++i) {
            for (int j = 0; j < BoardWidth; ++j) {
                Tetrominoes shape = shapeAt(j, BoardHeight - i - 1);
                if (shape != Tetrominoes.NoShape)
                    drawSquare(g, 0 + j * squareWidth(),
                               boardTop + i * squareHeight(), shape);
            }
        }

        if (curPiece.getShape() != Tetrominoes.NoShape) {
            for (int i = 0; i < 4; ++i) {
                int x = curX + curPiece.x(i);
                int y = curY - curPiece.y(i);
                drawSquare(g, 0 + x * squareWidth(),
                           boardTop + (BoardHeight - y - 1) * squareHeight(),
                           curPiece.getShape());
            }
        }
    }

//    private void dropDown()
//    {
//        int newY = curY;
//        while (newY > 0) {
//            if (!tryMove(curPiece, curX, newY - 1))
//                break;
//            --newY;
//        }
//        pieceDropped();
//    }

    private void oneLineDown()
    {
        if (!tryMove(curPiece, curX, curY - 1))
            pieceDropped();
    }


    private void clearBoard()
    {
        for (int i = 0; i < BoardHeight * BoardWidth; ++i)
            board[i] = Tetrominoes.NoShape;
    }

    private void pieceDropped()
    {
        for (int i = 0; i < 4; ++i) {
            int x = curX + curPiece.x(i);
            int y = curY - curPiece.y(i);
            board[(y * BoardWidth) + x] = curPiece.getShape();
        }

        removeFullLines();

        if (!isFallingFinished)
            newPiece();
    }

    private void newPiece()
    {
        curPiece.setRandomShape();
        curX = BoardWidth / 2 + 1;
        curY = BoardHeight - 1 + curPiece.minY();

        if (!tryMove(curPiece, curX, curY)) {
            curPiece.setShape(Tetrominoes.NoShape);
            timer.stop();
            isStarted = false;
            statusbar.setText("game over");
        }
    }

    private boolean tryMove(Shape newPiece, int newX, int newY)
    {
        for (int i = 0; i < 4; ++i) {
            int x = newX + newPiece.x(i);
            int y = newY - newPiece.y(i);
            if (x < 0 || x >= BoardWidth || y < 0 || y >= BoardHeight)
                return false;
            if (shapeAt(x, y) != Tetrominoes.NoShape)
                return false;
        }

        curPiece = newPiece;
        curX = newX;
        curY = newY;
        repaint();
        return true;
    }

    private void removeFullLines()
    {
        int numFullLines = 0;

        for (int i = BoardHeight - 1; i >= 0; --i) {
            boolean lineIsFull = true;

            for (int j = 0; j < BoardWidth; ++j) {
                if (shapeAt(j, i) == Tetrominoes.NoShape) {
                    lineIsFull = false;
                    break;
                }
            }

            if (lineIsFull) {
                ++numFullLines;
                for (int k = i; k < BoardHeight - 1; ++k) {
                    for (int j = 0; j < BoardWidth; ++j)
                         board[(k * BoardWidth) + j] = shapeAt(j, k + 1);
                }
            }
        }

        if (numFullLines > 0) {
            numLinesRemoved += numFullLines;
            statusbar.setText(String.valueOf(numLinesRemoved));
            isFallingFinished = true;
            curPiece.setShape(Tetrominoes.NoShape);
            repaint();
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
//        System.out.println("width");
//        System.out.println("height:"+squareHeight());
//        g.setColor(color.brighter());
//        g.drawLine(x, y + squareHeight() - 1, x, y);
//        g.drawLine(x, y, x + squareWidth() - 1, y);
//
//        g.setColor(color.darker());
//        g.drawLine(x + 1, y + squareHeight() - 1,
//                         x + squareWidth() - 1, y + squareHeight() - 1);
//        g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1,
//                         x + squareWidth() - 1, y + 1);

    }

    class TAdapter1 extends KeyAdapter {
         public void keyPressed(KeyEvent e) {

             if (!isStarted || curPiece.getShape() == Tetrominoes.NoShape) {  
                 return;
             }

             int keycode = e.getKeyCode();

             if (keycode == KeyEvent.VK_SPACE) {
                 pause();
                 return;
             }

             if (isPaused)
                 return;

             switch (keycode) {
             case KeyEvent.VK_LEFT:
                 tryMove(curPiece, curX - 1, curY);
                 break;
             case KeyEvent.VK_RIGHT:
                 tryMove(curPiece, curX + 1, curY);
                 break;
             case KeyEvent.VK_DOWN:
                 for (int i = 1 ; i<4;i++){
                    oneLineDown();
                 }
                 break;
             case KeyEvent.VK_UP:
                 tryMove(curPiece.rotateRight(), curX, curY);
                 break;
             }

         }
     }
    class TAdapter2 extends KeyAdapter {
         public void keyPressed(KeyEvent e) {

             if (!isStarted || curPiece.getShape() == Tetrominoes.NoShape) {  
                 return;
             }

             int keycode = e.getKeyCode();

             if (keycode == KeyEvent.VK_SPACE) {
                 pause();
                 return;
             }

             if (isPaused)
                 return;

             switch (keycode) {
             case 'a':
                 tryMove(curPiece, curX - 1, curY);
                 break;
             case 'A':
                 tryMove(curPiece, curX - 1, curY);
                 break;
             case 'd':
                 tryMove(curPiece, curX + 1, curY);
                 break;
             case 'D':
                 tryMove(curPiece, curX + 1, curY);
                 break;                 
             case 's':
                 for (int i = 1 ; i<4;i++){
                    oneLineDown();
                 }
                 break;
             case 'S':
                 for (int i = 1 ; i<4;i++){
                    oneLineDown();
                 }
                 break;                 
             case 'w':
                 tryMove(curPiece.rotateRight(), curX, curY);
                 break;
             case 'W':
                 tryMove(curPiece.rotateRight(), curX, curY);
                 break;                 
             }

         }
     } 
    class TAdapter extends KeyAdapter {
         public void keyPressed(KeyEvent e) {

             if (!isStarted || curPiece.getShape() == Tetrominoes.NoShape) {  
                 return;
             }

             int keycode = e.getKeyCode();

             if (keycode == KeyEvent.VK_SPACE) {
                 pause();
                 return;
             }

             if (isPaused)
                 return;
             if (player == 1){
                switch (keycode) {
                case KeyEvent.VK_LEFT:
                    tryMove(curPiece, curX - 1, curY);
                    break;
                case KeyEvent.VK_RIGHT:
                    tryMove(curPiece, curX + 1, curY);
                    break;
                case KeyEvent.VK_DOWN:
                    for (int i = 1 ; i<4;i++){
                        oneLineDown();
                    }
                    break;
                case KeyEvent.VK_UP:
                    tryMove(curPiece.rotateRight(), curX, curY);
                    break;
                }
             }
             if (player == 2){
                switch (keycode) {
                case 'a':
                    tryMove(curPiece, curX - 1, curY);
                    break;
                case 'A':
                    tryMove(curPiece, curX - 1, curY);
                    break;
                case 'd':
                    tryMove(curPiece, curX + 1, curY);
                    break;
                case 'D':
                    tryMove(curPiece, curX + 1, curY);
                    break;                 
                case 's':
                    for (int i = 1 ; i<4;i++){
                        oneLineDown();
                    }
                    break;
                case 'S':
                    for (int i = 1 ; i<4;i++){
                        oneLineDown();
                    }
                    break;                 
                case 'w':
                    tryMove(curPiece.rotateRight(), curX, curY);
                    break;
                case 'W':
                    tryMove(curPiece.rotateRight(), curX, curY);
                    break;                 
                }              
             }

         }
     }    
}
