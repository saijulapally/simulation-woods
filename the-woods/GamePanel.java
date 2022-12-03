/* e-LEMON-ators */

import javax.swing.*;
import java.io.*;      
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.io.File;
import javax.imageio.ImageIO;
import java.applet.*;
import java.util.Scanner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GamePanel extends JPanel
{
   private JFrame frame;
   private BufferedImage myImage;
   private Graphics myBuffer;
   private final int FRAME = 600;
   private Image img;
   
   private AudioClip bgMusic, dthMusic;
   
   private Background bOne, bTwo;
   private int alpha = 255, dark = 100;
   private boolean aState = true;
   
   private Player player;
   private int speed = 1, energy = 1000;
   private int eSub = 15, hSub = 7;
   private double health = 1000;
   private Timer lTimer, rTimer, udTimer;
   private boolean lOn = false, rOn = false, udOn = false;
   private String[] mLeft = {"sprites/left_still.png", "sprites/left_move.png"};
   private String[] mRight = {"sprites/right_still.png", "sprites/right_move.png"};
   private String[] mFront = {"sprites/front_left.png", "sprites/front_right.png"};
   private int pIndex = 0;
   private boolean pLeft = true, pRight = true, pUp = true, pDown = true, bAllow = true;
   private int bCount = 0;
   
   private Monster monster;
   private String[] mArray = {"sprites/monster_one.png", "sprites/monster_two.png"};
   private int mIndex = 0;
   private Timer mTimer;
  
   private ArrayList<Entity> trees = new ArrayList<Entity>();
   private String[] tArray = {"sprites/fire_one.png", "sprites/fire_two.png"};
   private int tIndex = 0;
   private Timer tTimer;
   
   private int level = 2, lLevel = 100;
   
   private InputHandler input;
   
   private Timer gTimer, spawner, leveler, logger;
   private int logSecond = 1;
   
   private PrintStream outfile = null;
   private String name;
     
   public GamePanel(JFrame frame)
   {
      this.frame = frame; 
      
      name = JOptionPane.showInputDialog("What is your name?");
      myImage = new BufferedImage(FRAME, FRAME, BufferedImage.TYPE_INT_RGB);
      myBuffer = myImage.getGraphics();
      
      bOne = new Background(0,0);
      bTwo = new Background(FRAME, 0);
      
      player = new Player(500, 300, "sprites/still.png");
      monster = new Monster(-400, 0, "sprites/monster_one.png");
      
      
      bgMusic = Sound.getClip("sound/game_mix.wav");
      Sound.loop(bgMusic);
      try {
         Thread.currentThread().sleep(5);
      }
      catch (Exception e) {
         e.printStackTrace();
      } 
      
      input = new InputHandler(this);
      setFocusable(true);
      
      gTimer = new Timer(12, new updateGame());
      gTimer.start();
      
      mTimer = new Timer(200, new MonsterWall());
      mTimer.start();
      
      tTimer = new Timer(150, new TreeArray());
      tTimer.start();
      
      spawner = new Timer(1000, new updateSpawn());
      new java.util.Timer().schedule(
         new java.util.TimerTask() {
            @Override
            public void run() {
               spawner.start();
            }
        }, 2000
      );
      
      leveler = new Timer(30000, new updateLevel());
      leveler.start(); 
      
      Scanner cheat = null;      
      try {
         cheat = new Scanner(new File("text/cheatcodes.txt"));
      }
      catch (FileNotFoundException e) {
         JOptionPane.showMessageDialog(null,"Error-Cheat");
      }
      if (cheat.nextLine().equals("kimcity")) {
      	 hSub = 0;
      }
      if (cheat.nextLine().equals("evanyeyeye")) {
         eSub = 0;   
      }
      
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
      Calendar cal = Calendar.getInstance();
      try{
         outfile = new PrintStream(new FileOutputStream("text/logs/" + name + " " + dateFormat.format(cal.getTime()) + ".txt"));
      }
      catch(FileNotFoundException e) {
         JOptionPane.showMessageDialog(null,"Error-Log");
      }

      logger = new Timer(1000, new updateLog());
      logger.start();
   }
   public void paintComponent(Graphics g)
   {
      g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
   }
   private class updateGame implements ActionListener
   {
      public void actionPerformed(ActionEvent a)
      { 
         /**********DIFFICULTY*********/
         if (level == 4)
         {
            lLevel = 300;
            monster.setX(-200);
            leveler.setDelay(60000);
         } 
         /*****************************/
         
         bOne.draw(myBuffer);
         bTwo.draw(myBuffer);
         
         /****************WARNING LINE*****************/
         myBuffer.setColor(new Color(255, 0, 0)); 
         myBuffer.fillRoundRect(lLevel, 25, 5, 545, 3, 3);
         /*********************************************/
         
         player.draw(myBuffer);  
         /***************TREE COLLISON***************/
         for (int i = 0; i < trees.size(); i++) 
         {
            if (trees.get(i).getX() < -75) {
               trees.remove(i);
               continue;
            }
            if (player.collidesWith(trees.get(i), 0, 65, 70, 20))
            {
               player.collidedWith(trees.get(i));
               trees.get(i).collidedWith(player);
               int x = trees.get(i).getX();
               int y = trees.get(i).getY() + 20;
               if (player.getY() >= y + 5)
                  pUp = false;
               if (player.getY() + 5 <= y)
                  pDown = false;
               if (player.getX() >= x)
                  pLeft = false;
               if (player.getX() <= x)
                  pRight = false;
            }
      		trees.get(i).draw(myBuffer);
            trees.get(i).setX(trees.get(i).getX() - 1);
   	   }  
         /********************************************/
         monster.draw(myBuffer);
         
         
         
         
    
         /****************HEALTH BAR********************/
         myBuffer.setColor(new Color(255, 0, 0));
         myBuffer.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
         myBuffer.drawString("H", 10, 590); 
         myBuffer.setColor(new Color(96, 97, 97));        
         myBuffer.fillRoundRect(30, 573, 200, 22, 8, 8);
         myBuffer.setColor(new Color(255, 0, 0));        
         myBuffer.fillRoundRect(30, 573, (int)(health / 5), 22, 8, 8);
         /**********************************************/
         
         /****************ENERGY BAR********************/
         myBuffer.setColor(new Color(0, 191, 255));
         myBuffer.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
         myBuffer.drawString("E", 360, 590); 
         myBuffer.setColor(new Color(96, 97, 97));        
         myBuffer.fillRoundRect(380, 573, 200, 22, 8, 8);
         myBuffer.setColor(new Color(0, 191, 255));        
         myBuffer.fillRoundRect(380, 573, (int)(energy / 5), 22, 8, 8);
         /**********************************************/




         
         /***********MONSTER COLLISON************/
         if (player.collidesWith(monster, 500))
         {
            health = health - hSub;
            player.collidedWith(monster);
            monster.collidedWith(player);
         }
         else if (health < 1000)
            health = health + 0.2;
         /***************************************/ 
            
            
            
            
           
            
         /***********************MOVEMENT***********************/
         if (player.getX() >= lLevel - 25)
            player.setX(player.getX() - 1);
         if (!bAllow && !(bCount == 20))
            bCount++;
         if (bCount == 20) 
         {
            bCount = 0;
            bAllow = true;
         }
         if (input.barrage.isPressed() && energy - 300 >= 0 && bAllow)
         {
            if (input.right.isPressed() && player.getX() + 75 <= 560)
               player.setX(player.getX() + 75);
            else if (input.up.isPressed() && player.getY() - 90 >= 20)
               player.setY(player.getY() - 90);
            else if (input.down.isPressed() && player.getY() + 90 <= 490)
               player.setY(player.getY() + 90);
            else if (input.left.isPressed() && player.getX() - 75 >= 75)
               player.setX(player.getX() - 75);
            else if (player.getX() + 75 <= 560)
               player.setX(player.getX() + 75);
            energy = energy - (eSub * 20);
            bAllow = false;
         }
         if (input.sprint.isPressed() && energy - 15 >= 0) 
         {
            speed = 5;
            energy = energy - eSub;
         }
         else
         {
            speed = 2;
            if (energy + 1 <= 1000)
               energy++;
         }
         if (input.up.isPressed() || input.down.isPressed()) {
            if (input.up.isPressed() && player.getY() >= 20 && pUp)
               player.setY(player.getY() - speed);
            else if (input.down.isPressed() && player.getY() <= 490 && pDown)
               player.setY(player.getY() + speed);
            if (!udOn && !(input.left.isPressed() || input.right.isPressed())) {
               udTimer = new Timer(200, new AnimationFront());
               udTimer.start();
               udOn = true;
            }
            
         }
         else {
            if (udOn) {
               udTimer.stop();
               player.setSprite("sprites/still.png");
               udOn = false;
            }
         }
         if (input.left.isPressed()) {
            if (player.getX() >= 75 && pLeft)
               player.setX(player.getX() - speed); 
            if (!lOn) {
               lTimer = new Timer(200, new AnimationLeft());
               lTimer.start();
               lOn = true;
            }
         }
         else {
            if (lOn) {
               lTimer.stop();
               player.setSprite("sprites/still.png");
               lOn = false;
            }
         }
         if (input.right.isPressed()) {
            if (player.getX() <= 560 && pRight)
               player.setX(player.getX() + speed);
            if (!rOn) {
               rTimer = new Timer(200, new AnimationRight());
               rTimer.start();
               rOn = true;
            }
         }
         else {
            if (rOn) {
               rTimer.stop();
               player.setSprite("sprites/still.png");
               rOn = false;
            }
         }
         pLeft = pRight = pUp = pDown = true;
         /*******************************************************/
         
         
         
         
         
         
         
         /***************LIGHTING*********************/
         myBuffer.setColor(new Color(0, 0, 0, alpha));
         /*
         if (alpha + 5 >= 255 && !(dark == 100)) {
            dark++;
         }
         else */
          if (alpha > 0 && aState)
            alpha--;
         /*&
         else
         {
            dark = 0;
            aState = false;
            alpha = alpha + 5;
            if (alpha + 5 >= 255)
               aState = true;
         } */
         myBuffer.fillRect(0, 0, FRAME,FRAME); 
         /*******************************************/
         
         /************************DEATH***********************/
         if (health <= 0)
         {
            frame.setContentPane(new DeathPanel(frame));
            frame.revalidate();
            frame.setVisible(true);
            Sound.stop(bgMusic);
            logger.stop();
            spawner.stop();
            gTimer.stop();
         }
         /***************************************************/
         
         repaint();
      }
   }
   private class updateLog implements ActionListener
   {
      public void actionPerformed(ActionEvent a)
      {
         outfile.println(logSecond + ":  ");
         outfile.println("Health:  " + health);
         outfile.println("Energy:  " + energy);
         if (player.collidesWith(monster, 500))
         {
            outfile.print(player.collidedWith(monster));
            outfile.println(monster.collidedWith(player));
         }
         for (int i = 0; i < trees.size(); i++) 
         {
            if (player.collidesWith(trees.get(i), 0, 65, 70, 20))
            {
               outfile.print(player.collidedWith(trees.get(i)));
               outfile.println(trees.get(i).collidedWith(player));
               break;
            }
   	   }  
         outfile.println();
         logSecond++;
      }
   } 
   private class updateLevel implements ActionListener
   {
      public void actionPerformed(ActionEvent a)
      {
         level++;
      }
   }  
   private class updateSpawn implements ActionListener
   {
      public void actionPerformed(ActionEvent a)
      {
         for (int i = 0; i < level; i++)
            trees.add(new Tree(610, (int)(Math.random() * 475 + 20), "sprites/pine_tree.png"));
      }
   }
   private class TreeArray implements ActionListener 
   {
      public void actionPerformed (ActionEvent a)
      {
         for (int i = 0; i < trees.size(); i++) 
         {
            if (trees.get(i).getX() <= lLevel - 20)
               trees.get(i).setSprite(tArray[tIndex]);
   	   }  
         tIndex++;
         tIndex %= tArray.length;
      }
   }
   private class MonsterWall implements ActionListener 
   {
      public void actionPerformed (ActionEvent a)
      {
         monster.setSprite(mArray[mIndex]);
         mIndex++;
         mIndex %= mArray.length;
      }
   }
   private class AnimationFront implements ActionListener 
   {
      public void actionPerformed (ActionEvent a)
      {
         player.setSprite(mFront[pIndex]);
         pIndex++;
         pIndex %= mFront.length;
      }
   }
   private class AnimationLeft implements ActionListener 
   {
      public void actionPerformed (ActionEvent a)
      {
         player.setSprite(mLeft[pIndex]);
         pIndex++;
         pIndex %= mLeft.length;
      }
   }
   private class AnimationRight implements ActionListener 
   {
      public void actionPerformed (ActionEvent a)
      {
         player.setSprite(mRight[pIndex]);
         pIndex++;
         pIndex %= mRight.length;
      }
   }
}
