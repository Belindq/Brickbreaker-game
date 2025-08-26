import javax.swing.JFrame;

public class Main
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        
        frame.setBounds(0, 0, 507, 535);
        frame.setTitle("Title ");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        BrickBreakerGame game = new BrickBreakerGame();
        frame.add(game);
        
        frame.setVisible(true);
    }
}
       
   