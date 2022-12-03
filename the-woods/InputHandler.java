

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener 
{
   public InputHandler(GamePanel game) {
      game.addKeyListener(this);
   }
   
   public class Key {
      private boolean pressed = false;
      public boolean isPressed() { 
         return pressed;
      }
      public void toggle(boolean isPressed) {
         pressed = isPressed;
      }
   }
   
   public Key up = new Key();
   public Key down = new Key();
   public Key left = new Key();
   public Key right = new Key();
   public Key sprint = new Key();
   public Key barrage = new Key();
   
   public void keyPressed(KeyEvent key) {
      toggleKey(key.getKeyCode(), true);
   }
   
   public void keyReleased(KeyEvent key) {
      toggleKey(key.getKeyCode(), false);
   }
   
   public void keyTyped(KeyEvent key) {} 
   
   public void toggleKey(int keyCode, boolean isPressed) 
   {
      if (keyCode == KeyEvent.VK_W) { up.toggle(isPressed); }
      if (keyCode == KeyEvent.VK_S) { down.toggle(isPressed); }
      if (keyCode == KeyEvent.VK_A) { left.toggle(isPressed); }
      if (keyCode == KeyEvent.VK_D) { right.toggle(isPressed); }
      if (keyCode == KeyEvent.VK_SHIFT) { sprint.toggle(isPressed); }
      if (keyCode == KeyEvent.VK_SPACE) { barrage.toggle(isPressed); }
   }  
}
