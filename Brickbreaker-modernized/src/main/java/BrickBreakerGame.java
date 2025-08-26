import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import sun.audio.*;


public class BrickBreakerGame extends JPanel implements KeyListener, ActionListener, MouseListener, MouseMotionListener

{
   
    //instance variables
    private Ball ball;
    private Timer timer;
    private boolean play;
    private Paddle paddle;
    private Brick[][] bricks;
    
    AudioStream Click;
    AudioStream Drop;// click sound effect
    Image p2;
    Image p1;
    Image p3;
    Image p;
    Image menu;
    Image menu1;
    Image over;
    Image win;
    
    int screen = 1;
  
   
   
    
    //constructor
    public BrickBreakerGame()
    {


        setDoubleBuffered(true);
        setFocusable(true);
          new javax.swing.Timer(16, e -> repaint()).start();
        ball = new Ball(250, 450, 11, 3, 3, new Color(191,0,255)); // ball specs and size
       
        paddle = new Paddle(225, 470, 120, 15, Color.black, 50);
        addKeyListener(this);
        timer = new Timer(10, this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addMouseListener(this);
        addMouseMotionListener(this);
        timer.start();
        play = false;
         bricks = new Brick[5][3]; //create 2d array of bricks
        for(int i=0;i< bricks.length; i++)
        {
            for(int j = 0; j<bricks[0].length;j++)
            {
                if((int)(10*Math.random()+1)== 1) //10% chance of hard bricks
                    bricks[i][j] = new hardBrick(80*(i+1), 40*(j+1), 55, 10);
                else
                bricks[i][j] = new Brick (80*(i+1), 40*(j+1), 55, 10, Color.white);
            }
        }
                
        
        try
        {
           p1 = ImageIO.read(new File("assets/play1.png"));
           p2 = ImageIO.read(new File("assets/play2.png"));
           
           p = ImageIO.read(new File("assets/play.png"));
           menu1 = ImageIO.read(new File("assets/menu1.png"));
           menu = ImageIO.read(new File("assets/menu.png"));
            over = ImageIO.read(new File("assets/over.png"));
           win = ImageIO.read(new File("assets/win.png"));
        }
        catch( IOException e)
        {
        }
    }
    
   
    public void paintComponent(java.awt.Graphics g)
    {
        super.paintComponent(g);
        if(screen == 1) // menu screen
        {
            menu(g);
           
        }
        if(screen ==2) // start the game screen
        {
            p(g);
        }
        if(screen ==3)
        {
            p1(g);
        }
        if(screen ==4)
        {
            p2(g);
        }
        if(screen ==5)
        {
            menu1(g);
        }
        if(screen ==6)
        {
            over(g);
        }
        if(screen == 7)
        {
            win(g);
        }
        
        drawBricks(g, bricks);
        
        if(paddle.getVisible() ==true)
        {
            paddle.draw(g);
        }
        
        
        ball.collides(paddle); // check collision w ball and paddle
        if(ball.getScore()>0 &&ball.getHits()== 15) // check for win/loss screens
        {
            win(g);
            
            }
            else if(ball.getDrop())
            {
                over(g);
               
            }
   }
    
    public void p(Graphics g) // play screen, reset ball position
    {
        g.setColor(Color.white);
        g.fillRect(0, 0, 500, 500);
        g.drawImage(p, 5,5, null); // background
     
        ball.move(g); // draw the ball
        g.setColor(Color.white);
        g.setFont(new Font("LucidaBright", Font.PLAIN, 15));
        g.drawString("Score: "+String.valueOf(ball.getScore()), 400, 400); // score display
        visible(bricks, true);
        paddle.setVisible(true);
        
    }
    public void p1(Graphics g) // play screen without instructions
    {
        g.setColor(Color.white);
        
        g.fillRect(0, 0, 500, 500);
        g.drawImage(p1, 5,5, null);
         g.setColor(Color.white);
        g.setFont(new Font("LucidaBright", Font.PLAIN, 15));
         g.drawString("Score: "+String.valueOf(ball.getScore()), 400, 400);
        ball.move(g);
        play= true;
        
    }
    public void p2(Graphics g) // play screen without instructions
    {
        g.setColor(Color.white);
        g.fillRect(0, 0, 500, 500);
        g.drawImage(p2, 5,5, null); // background
        g.setColor(Color.white);
        g.setFont(new Font("LucidaBright", Font.PLAIN, 15));
        g.drawString("Score: "+String.valueOf(ball.getScore()), 400, 400);
       
        ball.move(g);
    }
   
    
    public void menu(Graphics g) // menu screen
    {
        g.drawImage(menu, 0,0, null);
       reset();
       
    }
    public void menu1(Graphics g) // menu screen
    {
        g.drawImage(menu1, 0,0, null);
        reset();
       
    }
    
    public void over(Graphics g) // help screen
    {
        g.drawImage(over, 0,0, null);
        g.setColor(Color.white);
        g.setFont(new Font("LucidaBright", Font.PLAIN, 15));
        g.drawString("Score: "+String.valueOf(ball.getScore()), 261, 115);
       
    }
    public void win(Graphics g) // help screen
    {
        g.drawImage(win, 0,0, null);
        g.setColor(Color.white);
        g.setFont(new Font("LucidaBright", Font.PLAIN, 15));
        g.drawString("Score: "+String.valueOf(ball.getScore()), 266, 123);
    }
    public void reset() //resets play screen to default
    {
        for(int i=0;i< bricks.length; i++)
        {
            for(int j = 0; j<bricks[0].length;j++)
            {
                if((int)(10*Math.random()+1)== 3)
                    bricks[i][j] = new hardBrick(80*(i+1), 40*(j+1), 55, 10);
                else
                bricks[i][j] = new Brick (80*(i+1), 40*(j+1), 55, 10, Color.white);
            }
        }
            play =false;
        ball.setX(250);
        ball.setY(450);
        ball.setdx1(5);
        ball.setdy1(2);
        visible(bricks, false);
        paddle.setVisible(false);
        ball.setHits(0);
        ball.setScore(0);
        ball.setDrop(false);
        repaint();
    }
    public void visible(Brick[][] a, boolean b) // turns the 2d array of bricks on
    {
        
        for(int x = 0;x<a.length; x++)
        {
                    for(int y = 0;y<a[x].length; y++)
                {
                    
                    
                    a[x][y].setVisible(b);
                    
                }
                
        }
        
    }
   
    public void drawBricks(Graphics g, Brick[][]a) // draws the 2d array of Bricks
    {
        
        for(int x = 0;x<a.length; x++)
        {
                    for(int y = 0;y<a[x].length; y++)
                {
                    
                     if(a[x][y].getVisible() == true)
                    {
                        a[x][y].draw(g);
                        
                         ball.collides(a[x][y]); 
                        
                    }
                    
                }
                
        }
        
    }
    public void keyPressed(KeyEvent e)
    {
        if(screen ==2||screen ==3|| screen ==4)
        {play = true;
        screen = 3;}
       
        if ( e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            paddle.moveRight();
            if( paddle.getX() + paddle.getW() >=495) // stopping paddle going out of bounds
                paddle.setX(495 - paddle.getW());
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            paddle.moveLeft();
             if( paddle.getX()  <= 5) // stopping paddle going out of bounds
                paddle.setX(5);
        }
        
        repaint();
        }
    
    public void keyTyped(KeyEvent e)
    {
    }
    public void keyReleased(KeyEvent e)
    {
    }
    
    public void actionPerformed(ActionEvent e)//when timer runs
    {
        if(play)
        
           {
               repaint();
               
            }
    }
    public void mousePressed(MouseEvent e)
    {
       
        
         // load sounds
        try
        {
            Click = new AudioStream(new FileInputStream("assets/Click2.wav"));
            Drop = new AudioStream(new FileInputStream("assets/Drop2.wav"));
        }
        catch (IOException ea)
        {
        }
        
        int x = e.getX();
        int y = e.getY(); //get x and y coordinates 
        if(screen ==4||screen ==3) //home button
        {
            if(x>17.5&&x<88&&y>18.4&&y<55.2)
            {
             screen = 1;
             reset();
             AudioPlayer.player.start(Drop);
           }
          
          }
         else if(screen ==2) //home button
        {
            if(x>17.5&&x<88&&y>18.4&&y<55.2)
            {
              screen = 1;
             reset();
             AudioPlayer.player.start(Drop);
           }
          
          }
        else if(screen ==5||screen ==1) // play button
        {
            if(x>122&&x<175&&y>300&&y<321)
            {
                screen =2;
             AudioPlayer.player.start(Drop);
             }
            
        }
        else if(screen ==6||screen ==7) //home button
        {
           
            if(x>17.5&&x<88&&y>18.4&&y<55.2)
            {
              screen = 1;
              reset();
              AudioPlayer.player.start(Drop);
        
           }
           else if(x>17.5&&x<88&&y>73&&y<125)//restart
            { 
               
             screen = 2;
              reset();
             AudioPlayer.player.start(Drop);
            }
           
           
          
          }
          
          
            
        repaint();
       }

     public void mouseClicked(MouseEvent e)
    {

    }

    public void mouseReleased(MouseEvent e)
    {

    }

    public void mouseEntered(MouseEvent e)
    {

    }

    public void mouseMoved(MouseEvent e) // hover stuff
    {
        int x = e.getX();
        int y = e.getY(); //get x and y coordinates 
        if(screen ==4 || screen ==3) //home button
        {
            if(x>17.5&&x<88&&y>18.4&&y<55.2)
            {
                screen = 4;
            }
            else
                screen =3;
                repaint();
        }
        
        else if(screen ==1 || screen ==5) // play button
        {
            if(x>122&&x<175&&y>300&&y<321)
            {
                screen =5;
            }
            else
            screen =1;
            repaint();
        }
        
            
    }
    public void mouseExited(MouseEvent e)
    {

    }

    public void mouseDragged(MouseEvent e)
    {
    }
}