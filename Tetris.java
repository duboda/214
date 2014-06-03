package pkgtry;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import pkgtry.Shape.Tetrominoes;


public class Tetris implements ActionListener{

    JLabel statusBar;
    JLabel statusBar2;
    
    //JLabel nextLabel;
    JLabel nextPiece;
    JLabel nextPiece2;
    
    JLabel heldPiece;
    JLabel heldPiece2;
    
    JFrame newFrame;
    JPanel newPanel;
    Board board;
    Board board2;    
    JButton newGame;
    JButton homePage;
    JPanel buttonPanel;
    JPanel roundPanel;
    JLabel roundLabel;
    JLabel leftLabel;
    JLabel leftLabel2;
    JPanel leftPanel;
    createpiece listpiece;
    
    
    public Tetris() {
//        newframeL = new JFrame("L");
//        newframeL.setSize(400,800);
        listpiece = new createpiece();
        listpiece.createpiece();        
        newFrame = new JFrame();
//        setSize(200,400);
//        newframeL.setVisible(true);
        newFrame.setSize(1200,800);
        newFrame.setLayout(new GridLayout(1,3));
        newFrame.setVisible(true);
        newPanel = new JPanel();
        newPanel.setLayout(new GridLayout(4,1));
//        newPanel.setSize(200, 600);
//        newPanel.setLayout(new FlowLayout());        
//        newPanel.setVisible(true);
//        newframeL.add(newPanel);
//        JButton button1 = new JButton();
//        button1.setText("try");
//        newPanel.add(button1);
        
       
        // create all labels
        statusBar = new JLabel("0"); statusBar2 = new JLabel("0");
        nextPiece = new JLabel("Next:"); nextPiece2 = new JLabel("Next:");
        heldPiece = new JLabel("Held:"); heldPiece2 = new JLabel("Held:");
        
        // add buttons
        newGame = new JButton();
        homePage = new JButton();
        newGame.addActionListener(this);
        homePage.addActionListener(this);
        homePage.setText("homepage");
        newGame.setText("restart");
        
        // add all labels to panel
        newPanel.add(statusBar2); newPanel.add(statusBar);
        newPanel.add(nextPiece2); newPanel.add(nextPiece);
        newPanel.add(heldPiece2); newPanel.add(heldPiece);
        buttonPanel = new JPanel();
        newPanel.add(buttonPanel);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(newGame);
        buttonPanel.add(homePage);
        roundPanel = new JPanel();
        newPanel.add(roundPanel);
        roundPanel.setLayout(new BoxLayout(roundPanel, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Round");
        roundPanel.add(title);
        roundLabel = new JLabel("1");
        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(1,2));
        leftLabel = new JLabel("20");
        leftLabel2 = new JLabel("20");
        roundPanel.add(roundLabel);
        roundPanel.add(leftPanel);
        leftPanel.add(leftLabel);
        leftPanel.add(leftLabel2);
        
//        newFrame.add(newPanel);
//        newFrame.add(statusBar, BorderLayout.SOUTH);
        board = new Board(this,1, newPanel);
        board2 = new Board(this,2, newPanel);
        

        // add components to frame
        newFrame.add(board2);
        newFrame.add(newPanel);
        newFrame.add(board);
//        board.addKeyListener(new TAdapter());
//        board2.addKeyListener(new TAdapter());        
        // start boards
        board.start();
        board2.start();
        newFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public void actionPerformed(ActionEvent e) {
//        JButton button = (JButton) e.getSource();
//        if (button.getText().equals("try again")){
//            newFrame.dispose();
//            
//            Tetris game = new Tetris();
//            if (mode == 1)
//                game = new Competitive();
//            else if (mode == 2)
//                game = new Cooperative();
//            
//            game.newFrame.setLocationRelativeTo(null);
//            game.newFrame.setVisible(true);
//        }
//        else if (button.getText().equals("homepage")){
//              newFrame.dispose();
//              SwingControlDemo  swingControlDemo = new SwingControlDemo("please choose the model");      
//              swingControlDemo.showLabelDemo();
//        }
   //     button.setText("Successfull");        
    }        


    public JLabel player1() {return statusBar;}
    public JLabel player2() {return statusBar2;}
    
    public JLabel nextPiece1() {return nextPiece;}
    public JLabel nextPiece2() {return nextPiece2;}
    
    public JLabel heldPiece1() {return heldPiece;}
    public JLabel heldPiece2() {return heldPiece2;}
    
    public JPanel getpanel(){ return newPanel;}
    
    public JLabel leftpiece1(){return leftLabel2;}
    public JLabel leftpiece2(){return leftLabel;}
    public createpiece list(){return listpiece;}
    public JLabel round(){return roundLabel;}

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
            case KeyEvent.VK_ENTER:
                // do check mode here
                board.swapHeld(board2);
                break;
            case KeyEvent.VK_1:
                if (board2.numLinesRemoved>=2){
                    board.up();
                    board2.numLinesRemoved = board2.numLinesRemoved-2;
                    board2.statusbar.setText(String.valueOf(board2.numLinesRemoved));                    
                }
                break;
            case KeyEvent.VK_2:
                if (board2.numLinesRemoved>=4){
                    board.fast();
                    board2.numLinesRemoved = board2.numLinesRemoved-4;
                    board2.statusbar.setText(String.valueOf(board2.numLinesRemoved));                    
                }
                break;                
            case KeyEvent.VK_COMMA:
                if (board.numLinesRemoved>=2){
                    board2.up();
                    board.numLinesRemoved = board.numLinesRemoved-2;
                    board.statusbar.setText(String.valueOf(board.numLinesRemoved));                    
                }
                break;
            case KeyEvent.VK_PERIOD:
                if (board.numLinesRemoved>=4){
                    board2.fast();
                    board.numLinesRemoved = board.numLinesRemoved-4;
                    board.statusbar.setText(String.valueOf(board.numLinesRemoved));                    
                }
                break;                  
            }
        }
    }


}
