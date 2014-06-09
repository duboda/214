package pkgtry;
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
 
public class SwingControlDemo implements ActionListener {
    
   private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel statusLabel;
   private JPanel controlPanel;
   private JPanel buttonPanel;
   private Desktop desktop;
   private String path;
   
   String text;
   public SwingControlDemo(String m){
       text = m;
      prepareGUI();
   }

   private void prepareGUI(){
      mainFrame = new JFrame("This is battle tetris game!");
      mainFrame.setLocation(100,100);
      mainFrame.setSize(400,600);
      mainFrame.setLayout(new GridLayout(2, 1));
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });    
      headerLabel = new JLabel("", JLabel.CENTER);        
//      statusLabel = new JLabel("",JLabel.CENTER);    

//      statusLabel.setSize(350,100);
      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());
      
      buttonPanel = new JPanel();
      buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
      controlPanel.add(buttonPanel);

      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
//      mainFrame.add(statusLabel);
      mainFrame.setVisible(true);  
   }

   void showLabelDemo(){
      headerLabel.setText(text);      

//      JLabel label  = new JLabel("", JLabel.CENTER); 
      JButton button1 = new JButton();
      JButton button2 = new JButton();
      JButton button3 = new JButton();
      JButton button4 = new JButton();
      
      button1.setText("Quick Start");
      button2.setText("Competitive");
      button3.setText("Cooperative");
      button4.setText("Instructions");
      
      button1.addActionListener(this);
      button2.addActionListener(this);
      button3.addActionListener(this);
      button4.addActionListener(this);
      
      buttonPanel.add(button1);
      buttonPanel.add(button2);
      buttonPanel.add(button3);
      buttonPanel.add(button4);
      
      mainFrame.setVisible(true);  
   }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
//        button.setVisible(false);
        headerLabel.setVisible(false);
        controlPanel.setVisible(false);
//        mainFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
        mainFrame.dispose();
        
        Tetris game;
        
        if (button.getText().equals("Competitive"))
            game = new Competitive();
        else if (button.getText().equals("Cooperative"))
            game = new Cooperative();
        else if (button.getText().equals("Instructions")) {
            game = null;
            SwingControlDemo  swingControlDemo = new SwingControlDemo("please choose the model");      
            swingControlDemo.showLabelDemo();
            
            desktop = Desktop.getDesktop();
            path="src/pkgtry/Instructions.txt";
            
            try {
                desktop.open(new File(path));
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        else
            game = new QuickStart();
        if (!button.getText().equals("Instructions"))
        {
            game.newFrame.setLocationRelativeTo(null);
            game.newFrame.setVisible(true);
        }
   //     button.setText("Successfull");        
    }
}