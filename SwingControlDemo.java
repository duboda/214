package pkgtry;
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
 
public class SwingControlDemo implements ActionListener {
    
   private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel statusLabel;
   private JPanel controlPanel;
   String text;
   public SwingControlDemo(String m){
       text = m;
      prepareGUI();
   }

   private void prepareGUI(){
      mainFrame = new JFrame("This is battle tetris game!");
      mainFrame.setLocationRelativeTo(null);
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
      button1.addActionListener(this);
      button1.setText("Quick Start");
      button2.setText("Time Rush");
      controlPanel.add(button1);
      controlPanel.add(button2);
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

        Tetris game = new Tetris();
        game.newframe.setLocationRelativeTo(null);
        game.newframe.setVisible(true);
   //     button.setText("Successfull");        
    }
}