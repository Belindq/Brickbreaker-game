import java.awt.*;

import java.io.*;
import sun.audio.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Paddle extends Brick
{
    private int speed;
     AudioStream Click;
      // load sounds
       
    public Paddle (int x1, int y1, int w, int h, Color c, int s) // if add image = make colour vairable img, must change in brick as well
    {
        super(x1,y1,w,h,c);
        speed = s;
    }
    
    public int getSpeed(){ return speed;}
    public boolean isPaddle(){ return true;}
    public void setSpeed(int s){speed =s; }
    
    public void hit()
    {
         try
        {
            Click = new AudioStream(new FileInputStream("Click2.wav"));
            
        }
        catch (IOException ea)
        {
        }setVisible(true); // paddle doesn't disappear, override paddle method
        AudioPlayer.player.start(Click);
    }
    
    public void moveRight()
    {
        setX(getX()+speed);
    }
    public void moveLeft()
    {
        setX(getX()-speed);
    }
    
}
