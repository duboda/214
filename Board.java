/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkgtry;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import pkgtry.Shape.Tetrominoes;


public class Board extends JPanel implements ActionListener {

    // constants defining board
    static final int BOARD_WIDTH = 10;
    static final int BOARD_HEIGHT = 20;
    static enum MODE {QKST, COMP, COOP};
    
    // Timer
    Timer timer;
    
    // for gameflow
    boolean isFallingFinished;
    boolean isStarted;
    boolean isPaused;
    boolean isSwapped;
    boolean heldSwapped;
    
    // Middle JPanel for drawing next and hold
    JPanel midPanel;
    
    // JLabels for middle panel
    JLabel statusBar;
    JLabel nextPieceLabel;
    JLabel heldPieceLabel;
    
    // variables for game logic
    MODE mode;
    Shape curPiece;
    Shape nextPiece;
    Shape heldPiece;
    createpiece newPiece;
    Tetrominoes[] board;
    Board otherBoard;
    int player = 0;
    int numLinesRemoved = 0;
    int curX = 0;
    int curY = 0;
    
    int leftNumber = 20;
    boolean isSpedUp = false;
    
    JPanel restart;
    JLabel leftPiece;
    JLabel otherPiece;
    JButton newgame;
    JLabel roundLabel;
    int round = 1;
    int speed = 400;
    
    /**
     * Class constructor.
     * @param parent parent Tetris canvas
     * @param m player number
     * @param midPanel
     * @param mode 
     */
    public Board(Tetris parent, int m, JPanel midPanel, MODE mode) {
       player = m;
       setBorder(BorderFactory.createLoweredBevelBorder());
       setFocusable(true);
       curPiece = new Shape();
       nextPiece = new Shape();
       heldPiece = new Shape();
       timer = new Timer(speed, this);
       this.midPanel = midPanel;
       this.mode = mode;
       
       isFallingFinished = false;
       isStarted = false;
       isPaused = false;
       isSwapped = false;
       heldSwapped = false;
       
       if (mode == MODE.COMP){
            newPiece = new createpiece();
            newPiece = parent.list();
       }
       
       roundLabel = parent.round();
       if (m==1){
            statusBar =  parent.player1();
            nextPieceLabel = parent.nextPiece1();
            heldPieceLabel = parent.heldPiece1();
            
            leftPiece = parent.leftpiece1();
            otherPiece = parent.leftpiece2();
       }
       if (m==2){
            statusBar = parent.player2();
            nextPieceLabel = parent.nextPiece2();
            heldPieceLabel = parent.heldPiece2();

            leftPiece = parent.leftpiece2();
            otherPiece = parent.leftpiece1();
       }
       
       board = new Tetrominoes[BOARD_WIDTH * BOARD_HEIGHT];

    }
    
    public int getScore()
    {
        return numLinesRemoved;
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
    
    public void setOtherBoard(Board otherBoard){ this.otherBoard = otherBoard;};
    public Tetrominoes[] getBoard(){ return board;};
    public JLabel getStatusbar(){ return statusBar;};
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
        
        if (mode == MODE.COMP)
            nextPiece = newPiece.nextpiece[0];
        else
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
            statusBar.setText("paused");
        } else if(mode == MODE.COOP) {
            timer.start();
            statusBar.setText(String.valueOf(numLinesRemoved + otherBoard.getScore()));
            otherBoard.statusBar.setText(String.valueOf(numLinesRemoved + otherBoard.getScore()));
        }
        else {
            timer.start();
            statusBar.setText(String.valueOf(numLinesRemoved));
        }
        repaint();
    }
    
    /**
     * Stops board
     */
    public void stop(){
        timer.stop();
        clearBoard();
    }

    /**
     * 
     * @param g 
     */
    public void paint(Graphics g)
    { 
        super.paint(g);

        Dimension size = getSize();
        int boardTop = (int) size.getHeight() - BOARD_HEIGHT * squareHeight();

        if (mode == MODE.COMP){
            if ((otherPiece.getText().equals("1"))&&!isSpedUp){
                leftNumber = 20;
                leftPiece.setText(String.valueOf(leftNumber));
                up();
                timer.stop();
                speed *= 0.7;
                isSpedUp = true;
                timer = new Timer(speed,this);
                timer.start();

            }
        }
        
        if (statusBar.getText().equals("Game Over")){
            this.drawLose(g);
        
        }
        else if (statusBar.getText().equals("You Win")){
            this.drawWin(g);
        }
        else {
            for (int i = 0; i < BOARD_HEIGHT; ++i) {
                  for (int j = 0; j < BOARD_WIDTH; ++j) {
                    Tetrominoes shape = shapeAt(j, BOARD_HEIGHT - i - 1);
                    //if (shape != Tetrominoes.NoShape)
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
            
            if (player == 2){
                nextPiece.draw(midPanel.getGraphics(), 10, 26);
                heldPiece.draw(midPanel.getGraphics(), 10, 44);
            }
            else if(player == 1){
                nextPiece.draw(midPanel.getGraphics(), 30, 26);
                heldPiece.draw(midPanel.getGraphics(), 30, 44);
            }
        }
    }
    
    public void drawLose(Graphics g){
        Image buffer=new ImageIcon("src/pkgtry/lose.png").getImage();
        g.drawImage(buffer, 0, 0, this.getWidth(), this.getHeight(), this); 
    };
    
    public void drawWin(Graphics g){
        Image buffer=new ImageIcon("src/pkgtry/win.png").getImage();
        g.drawImage(buffer, 0, 0, this.getWidth(), this.getHeight(), this); 
    };


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

        // call new piece after last piece has settled
        if (!isFallingFinished)
            newPiece();
    }
    
    /**
     * Creates a new piece at the top of the board if possible, otherwise game
     * over.
     */

    private void newPiece()
    {
        // enable swapping
        if (curPiece.getShape() != Tetrominoes.NoShape){
            isSwapped = false;
            heldSwapped = false;
        }
        
        if (mode == MODE.COMP) {
            if (leftPiece.getText().equals("1")){
                newPiece.createpiece();           
                leftNumber=20;
                timer.stop();
                speed *= 0.7;
                timer = new Timer(speed,this);
                timer.start();
                round = Integer.parseInt(roundLabel.getText());
                round++;
                roundLabel.setText(String.valueOf(round));
                
                isSpedUp = false;
            }
            
            // shift next piece into current piece and randomly create next piece
            curPiece.setShape(nextPiece.getShape());

            leftPiece.setText(String.valueOf(leftNumber));
            leftNumber -= 1;
            nextPiece = newPiece.nextpiece[20-leftNumber]; 
        }
        else {
            // shift next piece into current piece and randomly create next piece
            curPiece.setShape(nextPiece.getShape());
            nextPiece.setRandomShape();
        }
            
            
               
        
        // set position of new piece
        curX = BOARD_WIDTH / 2 - 1;
        curY = BOARD_HEIGHT - 1 + curPiece.minY();
        
        // game over if new piece cannot move
        if (!tryMove(curPiece, curX, curY)) {
            curPiece.setShape(Tetrominoes.NoShape);
            
            // stop both boards
            this.stop();
            otherBoard.stop();
            
            statusBar.setText("Game Over");
            
            if (mode == MODE.COOP)
                otherBoard.getStatusbar().setText("Game Over");
            else
                otherBoard.getStatusbar().setText("You Win");
            
            repaint();
            otherBoard.repaint();
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
            
            if (mode == MODE.COOP) {
                // check if line has a gap
                for (int j = 0; j < BOARD_WIDTH; ++j) {
                    if ((shapeAt(j, i) == Tetrominoes.NoShape)
                            || (otherBoard.shapeAt(j, i) == Tetrominoes.NoShape)) {
                        lineIsFull = false;
                        break;
                    }
                }

                // copy everything above full line down one line
                if (lineIsFull) {
                    ++numFullLines;
                    for (int k = i; k < BOARD_HEIGHT - 1; ++k) {
                        for (int j = 0; j < BOARD_WIDTH; ++j) {
                             board[(k * BOARD_WIDTH) + j] = shapeAt(j, k + 1);
                             otherBoard.getBoard()[(k * BOARD_WIDTH) + j] = otherBoard.shapeAt(j, k + 1);
                        }
                    }
                }
            }
            else {
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
        }

        if (numFullLines > 0) {
            if (mode == MODE.COOP) {
                numLinesRemoved += numFullLines;
                statusBar.setText(String.valueOf(numLinesRemoved + otherBoard.getScore()));
                otherBoard.statusBar.setText(String.valueOf(numLinesRemoved + otherBoard.getScore()));
                isFallingFinished = true;
                curPiece.setShape(Tetrominoes.NoShape);
                repaint();
            }
            else {
                numLinesRemoved += numFullLines;
                statusBar.setText(String.valueOf(numLinesRemoved));
                isFallingFinished = true;
                curPiece.setShape(Tetrominoes.NoShape);
                repaint();
            }
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
        Tetrominoes temp = heldPiece.getShape();
        heldPiece.setShape(curPiece.getShape());
        curPiece.setShape(temp);
        curX = BOARD_WIDTH / 2;
        curY = BOARD_HEIGHT - 1 + curPiece.minY();
        isSwapped = true;
                    
        // for first held piece
        if (curPiece.getShape() == Tetrominoes.NoShape)
            newPiece();
    }
    
    
    /**
     * Takes two boards and swaps their held pieces
     * @param otherBoard
     */
    public void swapHeld(Board otherBoard) {
        
        // return if already swapped
        if (heldSwapped)
            return;
        
        // swap the held pieces between the two boards
        Tetrominoes temp = heldPiece.getShape();
        heldPiece.setShape(otherBoard.heldPiece.getShape());
        otherBoard.heldPiece.setShape(temp);
        
        // then disable further swapping
        heldSwapped = true;
    } 
    
    public void up(){
        
        for (int k = BOARD_HEIGHT-1; k > 0; k--) {
                for (int j = 0; j < BOARD_WIDTH; ++j)
                    board[(k * BOARD_WIDTH) + j] = shapeAt(j, k - 1);
                }
        int k = (int)(BOARD_WIDTH * Math.random());

        for (int j = 0; j<BOARD_WIDTH;++j){
            if (j!=k)
                board[j] = Tetrominoes.GrayShape;
            else
                board[j] = Tetrominoes.NoShape;
        }
    }
    public void drop(){
        int newY = curY;
        while (newY > 0) {
            if (!tryMove(curPiece, curX, newY - 1))
                break;
            --newY;
        }
        pieceDropped();   
    }
    

    private void drawSquare(Graphics g, int x, int y, Tetrominoes shape)
    {
        Color color = colors[shape.ordinal()];

        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth()-1, squareHeight()-1);
        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);

        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1,
                         x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1,
                         x + squareWidth() - 1, y + 1);

    }


    private static final Color colors[] = { 
        new Color( 50,  50,  50), new Color(204, 102, 102), 
        new Color(102, 204, 102), new Color(102, 102, 204), 
        new Color(204, 204, 102), new Color(204, 102, 204), 
        new Color(102, 204, 204), new Color(218, 170,   0),
        new Color(100, 100, 100)
    };
}