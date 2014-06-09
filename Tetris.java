package pkgtry;


import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class Tetris implements ActionListener{

    JLabel statusBar;
    JLabel statusBar2;
    
    JLabel nextPiece;
    JLabel nextPiece2;
    
    JLabel heldPiece;
    JLabel heldPiece2;
    
    JFrame newFrame;
    JPanel midPanel;
    Board board;
    Board board2;    
    JButton newGame;
    JButton homePage;
    JPanel buttonPanel;
    JPanel buttonArea;
    JPanel roundPanel;
    JLabel roundTitle;
    JLabel roundLabel;
    JLabel leftLabel;
    JLabel leftLabel2;
    JPanel leftPanel;
    createpiece listPiece;
    
    
    public Tetris(Board.MODE mode) {
        listPiece = new createpiece();
        listPiece.createpiece();  
        
        newFrame = new JFrame();
        newFrame.setSize(1200,800);
        newFrame.setLayout(new GridLayout(1,3));
        newFrame.setVisible(true);
        midPanel = new JPanel();
        midPanel.setLayout(new GridLayout(4,2));
        
       
        // create labels
        statusBar = new JLabel("0", SwingConstants.CENTER); statusBar2 = new JLabel("0", SwingConstants.CENTER);
        nextPiece = new JLabel("Next:", SwingConstants.CENTER); nextPiece2 = new JLabel("Next:", SwingConstants.CENTER);
        heldPiece = new JLabel("Held:", SwingConstants.CENTER); heldPiece2 = new JLabel("Held:", SwingConstants.CENTER);
        
        // style labels
        nextPiece.setVerticalAlignment(JLabel.TOP); nextPiece2.setVerticalAlignment(JLabel.TOP);
        heldPiece.setVerticalAlignment(JLabel.TOP); heldPiece2.setVerticalAlignment(JLabel.TOP);
        
        // add buttons
        newGame = new JButton();
        homePage = new JButton();
        newGame.addActionListener(this);
        homePage.addActionListener(this);

        homePage.setText("Homepage");
        newGame.setText("Restart");
        
        // add all labels to panel
        midPanel.add(statusBar2); midPanel.add(statusBar);
        midPanel.add(nextPiece2); midPanel.add(nextPiece);
        midPanel.add(heldPiece2); midPanel.add(heldPiece);
        
        
        // button panel
        buttonPanel = new JPanel();
        buttonArea = new JPanel();
        buttonArea.setLayout(new FlowLayout());
        
        buttonPanel.add(buttonArea);
        midPanel.add(buttonPanel);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonArea.add(newGame);
        buttonArea.add(homePage);
        
        // round panel
        roundPanel = new JPanel();
        midPanel.add(roundPanel);
        roundPanel.setLayout(new BoxLayout(roundPanel, BoxLayout.Y_AXIS));
        roundTitle = new JLabel("");
        roundPanel.add(roundTitle);
        roundLabel = new JLabel("");
        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(1,2));
        leftLabel = new JLabel("");
        leftLabel2 = new JLabel("");
        roundPanel.add(roundLabel);
        roundPanel.add(leftPanel);
        leftPanel.add(leftLabel);
        leftPanel.add(leftLabel2);
        

        board = new Board(this,1, midPanel, mode);
        board2 = new Board(this,2, midPanel, mode);
        
        board.setOtherBoard(board2);
        board2.setOtherBoard(board);
        

        // add components to frame
        newFrame.add(board2);
        newFrame.add(midPanel);
        newFrame.add(board);
        
        // start boards
        board.start();
        board2.start();
        newFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public void actionPerformed(ActionEvent e) {
        // to be overridden
    }        


    public JLabel player1() {return statusBar;}
    public JLabel player2() {return statusBar2;}
    
    public JLabel nextPiece1() {return nextPiece;}
    public JLabel nextPiece2() {return nextPiece2;}
    
    public JLabel heldPiece1() {return heldPiece;}
    public JLabel heldPiece2() {return heldPiece2;}
    
    public JPanel getpanel(){ return midPanel;}
    
    public JLabel leftpiece1(){return leftLabel2;}
    public JLabel leftpiece2(){return leftLabel;}
    public createpiece list(){return listPiece;}
    public JLabel round(){return roundLabel;}
    public JLabel roundTitle(){return roundTitle;}


    class TAdapter extends KeyAdapter {
        // to be overridden
    }
}