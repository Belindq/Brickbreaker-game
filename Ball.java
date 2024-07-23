import java.awt.*;

import java.io.*;
import sun.audio.*;
import java.net.MalformedURLException;
import java.net.URL;
public class Ball
{
    //instance variables
    private int x; //top left x-value
    private int y; // top right y-value
    //represent where the ball is located: its coordinates
    private int size;
    //represents the diameter of the ball
    private int dx;
    private int dy;
    private Color color;
    private int score;
    private boolean drop;
     private int hits; // how many bricks have been broken
    AudioStream Click;
    AudioStream Drop;

    //constructor
    public Ball(int x1, int y1, int s, int dx1, int dy1, Color c)
    {
        x = x1;
        y = y1;
        size = s;
        dx = dx1;
        dy = dy1;
        color =c;
    }

    //default constructor
    public Ball()
    {
        x=0;
        y=0;
        size = 5;
        dx = 0;
        dy = 0;
        color = Color.black;
    }

    //accessors and mutators

    public int getX(){ return x;}

    public int getY(){ return y;}
    
    public int getScore(){ return score;}

    public int getSize(){ return size;}

    public Color getColour(){ return color;}
    public boolean getDrop(){ return drop;}

    public void setX(int x1){x = x1;}
    public int getHits(){ return hits;}

    public void setY(int y1){y = y1;}
    public void setdx1(int x1){dx = x1;}
    public void setdy1(int y1){dy = y1;}
    public void setDrop(boolean a){drop = a;} //if the ball dropped 
  
    public void setScore(int y1){score = y1;}
    public void setSize(int s){size = s;}
    public void setHits(int s){hits = s;}
    public void setColor(Color c){color = c;}
    

    public void move(Graphics g)
    {
        x -=dx;

        //check collision with left and right walls
        if(x<5) // left wall
        {
            x=5;
            dx = -dx; // x travels opposite
        }
        else if (x+size>495) // right wall
        {
            x = 495 - size;
            dx = -dx;
        }

        if(y<5) // top wall
        {
            y=5;
            dy = -dy; // x travels opposite
        }
        else if (y+size>495) //bottom wall
        {

            dy = 0;
            dx = 0;
            drop = true;
            
        }

        y-= dy;
        
        //check if game is over
        draw(g); 
    }
    public void draw(Graphics g)
    {
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }
    
    public void collides(Brick b)
    {
        
         // load sounds
        try
        {
            Click = new AudioStream(new FileInputStream("Click2.wav"));
            Drop = new AudioStream(new FileInputStream("Drop2.wav"));
        }
        catch (IOException ea)
        {
        }
        Rectangle ball = new Rectangle(x, y, size, size);
        Rectangle brick = new Rectangle(b.getX(), b.getY(), b.getW(), b.getH());
          
        if(ball.intersects(brick)) //checks for harder bricks
        {
            
             if(  b.isPaddle()==false)
             {
                 if(b.getHard()==false)
                        {  b.hit();
                            score+=100;
                            AudioPlayer.player.start(Click);
                            hits++;
                        }
                        
                 else 
                        {  
                           if(b.getNum() ==1)
                            {
                             b.setNum(2);
                             
                              score+=200;
                               b.hit();
                               hits++;
                               AudioPlayer.player.start(Click);
                            }
                            
                           else
                            {
                            b.setNum(1);
                             b.setColor(Color.white);
                             AudioPlayer.player.start(Click);
                            }
                       }
               }
             if(x < b.getX() || x + size > b.getX() + b.getW())
                dx= -dx;
            else 
                dy = -dy;
        }
     }
   
}

    
