/* e-LEMON-ators */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class Tree extends Entity
{
   public Tree (int x, int y, String ref)
   {
      super(x, y, ref);
   }
   public String collidedWith(Entity other)
   {
      return "a tree.";  
   }
}