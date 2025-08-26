import java.awt.*;

public class hardBrick extends Brick
{
    private int num = 0;
    
    public hardBrick (int x1, int y1, int w, int h)
    {
       super(x1,y1,w,h,   new Color(100, 149, 237));
       
       
    }
     public boolean getHard(){ return true;}
   
}