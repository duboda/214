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

    // constants defining board
    static final int BOARD_WIDTH = 10;
    static final int BOARD_HEIGHT = 20;
    
    // Timer
    Timer timer;
    
    // for gameflow
    boolean isFallingFinished = false;
    boolean isStarted = false;
    boolean isPaused = false;
    boolean isSwapped = false;
    
    // JLabels for middle panel
    JLabel statusbar;
    JLabel nextPieceLabel;
    JLabel heldPieceLabel;
    
    // variables for game logic
    Shape curPiece;
    Shape nextPiece;
    Tetrominoes heldPiece;
    Tetrominoes[] board;
    int player = 0;
    int numLinesRemoved = 0;
    int curX = 0;
    int curY = 0;

    /**
     * Class constructor.
     * @param parent parent Tetris canvas
     * @param m player number
     */
    public Board(Tetris parent, int m) {
       player = m;
       setBorder(BorderFactory.createLoweredBevelBorder());
       setFocusable(true);
       curPiece = new Shape();
       nextPiece = new Shape();
       heldPiece = Tetrominoes.NoShape;
       timer = new Timer(400, this);
       if (m==1){
            statusbar =  parent.player1();
            nextPieceLabel = parent.nextPiece1();
            heldPieceLabel = parent.heldPiece1();
       }
       if (m==2){
            statusbar = parent.player2();
            nextPieceLabel = parent.nextPiece2();
            heldPieceLabel = parent.heldPiece2();
       }
       board = new Tetrominoes[BOARD_WIDTH * BOARD_HEIGHT];
//       if (player == 1){
//            addKeyListener(new TAdapter1());
//            System.out.println("111111111111111");
//       }
//       if (player == 2){
//            addKeyListener(new TAdapter2());
//            System.out.println("22222222222222222");
//       }
//        addKeyListener(new TAdapter());
    }
    
    /**
     * Moves game one step ahead
     * @param e event triggered by timer timeout
     */
    public void actionPerformed(ActionEvent e) {
        if (isFallingFinished) {
            isFallingFinished = false;
            newPiece();
        } else {
            oneLineDown();
        }
    }

    int squareWidth() { return (int) getWidth() / BOARD_WIDTH; }
    int squareHeight() {return (int) getHeight() / BOARD_HEIGHT; }
    Tetrominoes shapeAt(int x, int y) { return board[(y * BOARD_WIDTH) + x]; }

    /**
     * Starts game.
     */
    public void start()
    {
        if (isPaused)
            return;

        isStarted = true;
        isFallingFinished = false;
        numLinesRemoved = 0;
        clearBoard();
        
        nextPiece.setRandomShape();
        newPiece();
        timer.start();
    }

    /**
     * Pauses game.
     */
    void pause()
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

    /**
     * 
     * @param g 
     */
    public void paint(Graphics g)
    { 
        super.paint(g);

        Dimension size = getSize();
//        int kkk = (int)size.getHeight();
//        System.out.println("getsize"+kkk);
        int boardTop = (int) size.getHeight() - BOARD_HEIGHT * squareHeight();
//        System.out.println(boardTop);

        for (int i = 0; i < BOARD_HEIGHT; ++i) {
            for (int j = 0; j < BOARD_WIDTH; ++j) {
                Tetrominoes shape = shapeAt(j, BOARD_HEIGHT - i - 1);
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
                           boardTop + (BOARD_HEIGHT - y - 1) * squareHeight(),
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

    /**
     * Moves current piece down one line.
     */
    void oneLineDown()
    {
        // if piece can no longer be moved down a line, then it is dropped
        if (!tryMove(curPiece, curX, curY - 1))
            pieceDropped();
    }

    /**
     * Clears board.
     */
    private void clearBoard()
    {
        for (int i = 0; i < BOARD_HEIGHT * BOARD_WIDTH; ++i)
            board[i] = Tetrominoes.NoShape;
    }

    /**
     * Updates board and creates new piece when old piece hits bottom.
     */
    private void pieceDropped()
    {
        // fill in board with appropriate piece type
        for (int i = 0; i < 4; ++i) {
            int x = curX + curPiece.x(i);
            int y = curY - curPiece.y(i);
            board[(y * BOARD_WIDTH) + x] = curPiece.getShape();
        }
        
        // remove full lines
        removeFullLines();

        
        if (!isFallingFinished)
            newPiece();
    }
    
    /**
     * Creates a new piece at the top of the board if possible, otherwise game
     * over.
     */
    private void newPiece()
    {
        curPiece.setShape(nextPiece.getShape());
        nextPiece.setRandomShape();
        nextPieceLabel.setText("Next:\n" + nextPiece.getShape().name());
        
        curX = BOARD_WIDTH / 2;
        curY = BOARD_HEIGHT - 1 + curPiece.minY();

        isSwapped = false;
        
        if (!tryMove(curPiece, curX, curY)) {
            curPiece.setShape(Tetrominoes.NoShape);
            timer.stop();
            isStarted = false;
            statusbar.setText("game over");
        }
    }
    
    /**
     * Moves a piece and return true if possible, or return false otherwise.
     * @param newPiece piece to try move
     * @param newX new x-location
     * @param newY new y-location
     * @return true if move is possible, false otherwise
     */
    boolean tryMove(Shape newPiece, int newX, int newY)
    {
        // check if all coordinates within the shape can be moved
        for (int i = 0; i < 4; ++i) {
            int x = newX + newPiece.x(i);
            int y = newY - newPiece.y(i);
            if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT)
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
    
    /**
     * Checks for and removes full lines on the board, then update board.
     */
    private void removeFullLines()
    {
        int numFullLines = 0;

        for (int i = BOARD_HEIGHT - 1; i >= 0; --i) {
            boolean lineIsFull = true;
            
            // check if line has a gap
            for (int j = 0; j < BOARD_WIDTH; ++j) {
                if (shapeAt(j, i) == Tetrominoes.NoShape) {
                    lineIsFull = false;
                    break;
                }
            }
            
            // copy everything above full line down one line
            if (lineIsFull) {
                ++numFullLines;
                for (int k = i; k < BOARD_HEIGHT - 1; ++k) {
                    for (int j = 0; j < BOARD_WIDTH; ++j)
                         board[(k * BOARD_WIDTH) + j] = shapeAt(j, k + 1);
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
    
    /**
     * Swaps current piece and held piece
     */
    public void hold() {
        // if already swapped then return
        if (isSwapped)
            return;
        
        // swap current piece and held and reset to top of board
        Tetrominoes temp = heldPiece;
        heldPiece = curPiece.getShape();
        curPiece.setShape(temp);
        curX = BOARD_WIDTH / 2;
        curY = BOARD_HEIGHT - 1 + curPiece.minY();
        isSwapped = true;
                   
        // update piece name in held slot
        heldPieceLabel.setText("Held:\n" + heldPiece.name());  
                    
        // for first held piece
        if (curPiece.getShape() == Tetrominoes.NoShape)
            newPiece();
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


}