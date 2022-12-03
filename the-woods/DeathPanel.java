/* e-LEMON-ators */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.io.*;
import javax.imageio.ImageIO;
import java.applet.*;

public class DeathPanel extends JPanel 
{
   private BufferedImage myImage;
   private Graphics myBuffer;
   private final int FRAME = 600;
   private Image img;
   
   private JFrame frame;
   private JButton reButton, homeButton;
   private AudioClip music;
   
   private Monster ghost; 
   private String[] gArray = {"sprites/ghost_one.png", "sprites/ghost_two.png"};
   private Timer gTimer; 
   private int gIndex = 0;
 
   public DeathPanel(JFrame frame) 
   {
      this.frame = frame;
      setLayout(new GridLayout(5, 1));
      
      myImage = new BufferedImage(FRAME, FRAME, BufferedImage.TYPE_INT_RGB);
      myBuffer = myImage.getGraphics();
      
      myBuffer.setColor(new Color(0, 0, 0));
      myBuffer.fillRect(0, 0, FRAME, FRAME);
      myBuffer.setColor(Color.WHITE);
      myBuffer.setFont(new Font("Chiller", Font.BOLD, 100));
      myBuffer.drawString("lol u noob",130,225);
      
      ghost = new Monster(250, 250, "sprites/ghost_one.png");
      ghost.draw(myBuffer);
      
      gTimer = new Timer(300, new Ghost());
      gTimer.start();
      
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
      
      reButton = new JButton ("Replay");
      reButton.setFont(new Font("Chiller", Font.BOLD, 50));
      reButton.setBackground(Color.WHITE);
      reButton.setForeground(Color.RED);
      reButton.setOpaque(true);
      reButton.setHorizontalAlignment(SwingConstants.CENTER);
      reButton.setFocusPainted(false);
      reButton.addActionListener(new Replay());
      buttonPanel.add(reButton);  
      
      homeButton = new JButton ("Home");
      homeButton.setFont(new Font("Chiller", Font.BOLD, 50));
      homeButton.setBackground(Color.WHITE);
      homeButton.setForeground(Color.RED);
      homeButton.setOpaque(true);
      homeButton.setHorizontalAlignment(SwingConstants.CENTER);
      homeButton.setFocusPainted(false);
      homeButton.addActionListener(new Home());
      buttonPanel.add(homeButton); 
      
      music = Sound.getClip("sound/death.wav");
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
   private class Ghost implements ActionListener 
   {
      public void actionPerformed (ActionEvent a)
      {
         myBuffer.setColor(new Color(0, 0, 0));
         myBuffer.fillRect(0, 0, FRAME, FRAME);
         myBuffer.setColor(Color.WHITE);
         myBuffer.setFont(new Font("Chiller", Font.BOLD, 100));
         myBuffer.drawString("lol u noob",130,225);
         
         ghost.setSprite(gArray[gIndex]);
         gIndex++;
         gIndex %= gArray.length;
         ghost.draw(myBuffer);
         repaint();
      }
   }
   private class Replay implements ActionListener
   {
      public void actionPerformed(ActionEvent a)
      {
         Sound.stop(music);
         gTimer.stop();
         frame.setContentPane(new GamePanel(frame));
         frame.revalidate();
         frame.setVisible(true);
      }
   }
   private class Home implements ActionListener
   {
      public void actionPerformed(ActionEvent a)
      {
         Sound.stop(music);
         gTimer.stop();
         frame.setContentPane(new HomePanel(frame));
         frame.revalidate();
         frame.setVisible(true);
      }
   }
}