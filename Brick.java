import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Brick
{
    //intance vars
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;
    private boolean visible = true; // when hit, it is false
    private int num; //the amount it has been hit
   
    
    //constructor
    public Brick(int x1, int y1, int w, int h, Color c)
    {
        //initialize instance variables
        x = x1;
        y = y1;
        width = w;
        height = h;
        color = c;
    }
    
    
    //accessors
    public int getW() {return width;}
    public int getH() {return height;}
    public int getX() {return x;}
    public int getY() {return y;}
      public boolean getHard(){ return false;}
    public boolean getVisible(){return visible;}
     public int getNum(){ return num;}
      public boolean isPaddle(){ return false;}
    
   
    //mutators
    public void setW(int w) { width = w;}
    public void setH(int h) { height = h;}
    public void setX(int x1) {x = x1;}
    public void setY(int y1) { y= y1;}
    public void setVisible(boolean v){ visible = v;}
     public void setNum(int s){num =s; }
     public void setColor(Color c1){ color =c1;}
    
    //draw method
    public void draw(Graphics g)
    {
        g.setColor(color);
        g.fillRect(x, y, width, height); //top corners + size
    }
    
    //hit method
    public void hit() // always checking if it intersects w ball
    {
        visible = false;
    }

    
}