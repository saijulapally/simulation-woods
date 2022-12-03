/* e-LEMON-ators */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.io.File;
import javax.imageio.ImageIO;
import java.applet.*;
import javax.swing.JOptionPane;

public class HomePanel extends JPanel 
{
   private BufferedImage myImage;
   private Graphics myBuffer;
   private final int FRAME = 600;
   private Image img;
   
   private JFrame frame;
   private JButton startButton, instructions;
   private AudioClip music;
 
   public HomePanel(JFrame frame) 
   {
      this.frame = frame;
      setLayout(new GridLayout(5, 1));
      
      myImage = new BufferedImage(FRAME, FRAME, BufferedImage.TYPE_INT_RGB);
      myBuffer = myImage.getGraphics();

      try {
         img = ImageIO.read(new File("sprites/home.png"));
      }
      catch (Exception e) {
         System.out.println("Error-Home");
      }
      myBuffer.drawImage(img, 0, 0, null);
      myBuffer.setColor(Color.WHITE);
      myBuffer.setFont(new Font("Chiller", Font.BOLD, 100));
      myBuffer.drawString("The Woods",100,300);
      
      /********FORMATTING*********/
      JPanel trash1 = new JPanel();
      trash1.setOpaque(false);
      add(trash1);
      JPanel trash2 = new JPanel();
      trash2.setOpaque(false);
      add(trash2);
      JPanel trash3 = new JPanel();
      trash3.setOpaque(false);
      add(trash3); 
      /**************************/
      
      JPanel buttonPanel = new JPanel(new FlowLayout());
      buttonPanel.setOpaque(false);
      add(buttonPanel);
  
      startButton = new JButton ("Start");
      startButton.setFont(new Font("Chiller", Font.BOLD, 50));
      startButton.setBackground(Color.WHITE);
      startButton.setForeground(Color.RED);
      startButton.setOpaque(true);
      startButton.setHorizontalAlignment(SwingConstants.CENTER);
      startButton.setFocusPainted(false);
      startButton.addActionListener(new Start());
      buttonPanel.add(startButton);  
      
      instructions = new JButton ("How2");
      instructions.setFont(new Font("Chiller", Font.BOLD, 50));
      instructions.setBackground(Color.WHITE);
      instructions.setForeground(Color.RED);
      instructions.setOpaque(true);
      instructions.setHorizontalAlignment(SwingConstants.CENTER);
      instructions.setFocusPainted(false);
      instructions.addActionListener(new Instruction());
      buttonPanel.add(instructions); 
      
      music = Sound.getClip("sound/home.wav");
      Sound.loop(music);
      
      try {
         Thread.currentThread().sleep(5);
      }
      catch (Exception e) {
         e.printStackTrace();
      } 
   }
   public void paintComponent(Graphics g)
   {
      g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
   }
   private class Start implements ActionListener
   {
      public void actionPerformed(ActionEvent a)
      {
         Sound.stop(music);
         frame.setContentPane(new GamePanel(frame));
         frame.revalidate();
         frame.setVisible(true);
      }
   }
   private class Instruction implements ActionListener
   {
      public void actionPerformed(ActionEvent a)
      {
         JOptionPane.showMessageDialog(null, "WASD to move.\nHold Shift to sprint. (uses Energy)\nPress Space to barrage through tree. (uses 30% of Energy)");
      }
   }
}
