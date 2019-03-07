package bouncingballs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author dqz7130
 */
public class MainAndPanel extends JPanel implements ActionListener
{
    private JButton addBallButton;
    private JButton removeBallButton;
    private JButton addLotsOfBallsButton;
    private Ball ball;
    private ArrayList<Ball> balls = new ArrayList();
    private DrawPanel drawPanel;

   public MainAndPanel()
   {
      super(new  BorderLayout());

      // create two seperate panels, add these to the frame in the center
      drawPanel = new DrawPanel();
      JPanel buttonPanel = new JPanel();

      // creating a button for adding balls
      addBallButton = new JButton("Add Ball");
      addBallButton.addActionListener(this);
      addBallButton.setSize(30, 50);
      buttonPanel.add(addBallButton);

      addLotsOfBallsButton = new JButton("Add 100 Balls");
      addLotsOfBallsButton.addActionListener(this);
      addLotsOfBallsButton.setSize(30, 50);
      buttonPanel.add(addLotsOfBallsButton);

      removeBallButton = new JButton("Remove Balls");
      removeBallButton.addActionListener(this);
      removeBallButton.setSize(30, 50);
      buttonPanel.add(removeBallButton);

      add(buttonPanel,BorderLayout.SOUTH);
      add(drawPanel,BorderLayout.CENTER);

      // call actionPerformed method every 10ms using Swing timer
       Timer timer = new Timer(1, this);
       timer.start();
   }

   public void actionPerformed(ActionEvent e)
   {
       Object source = e.getSource();

       if(source == addBallButton)
       {
           Ball aball = new Ball(this.getSize().width, this.getSize().height);
           Thread b = new Thread(aball);
           b.start();
           balls.add(aball);
       }

       if(source == addLotsOfBallsButton)
       {
           for(int i = 0; i < 100; i++)
           {
               Ball aball = new Ball(this.getSize().width, this.getSize().height);
               Thread b = new Thread(aball);
               b.start();
               balls.add(aball);
               System.out.println(balls.size());
           }
       }

       if(source == removeBallButton)
       {
           balls.clear();
           System.out.print("remove pressed");
       }

       // this gets called from our timer
       drawPanel.repaint();
   }

   private class DrawPanel extends JPanel
   {
       public DrawPanel()
       {
           setPreferredSize(new Dimension(800, 800));
           setBackground(Color.GRAY);
       }

       @Override
       public void paintComponent(Graphics g)
       {
           super.paintComponent(g);

           if(balls.size() > 0)
           {
               ball._frameHeight = (int)this.getSize().getHeight();
               ball._frameWidth = (int)this.getSize().getWidth();

               for(Ball ball : balls)
               {
                   ball.drawBall(g);
               }
           }
       }

   }
   public static void main(String[] args)
   {
      // creates a new frame for us to add components to
      JFrame frame = new JFrame("Bouncing Balls GUI");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // gets the main content pane -> balls and buttons
      frame.getContentPane().add(new MainAndPanel());
      frame.pack();

      // sets the size and location of the frame in which the panel contains
      Toolkit tk = Toolkit.getDefaultToolkit();
      Dimension screenDimension = tk.getScreenSize();
      Dimension frameDimension = frame.getSize();
      frame.setLocation((screenDimension.width-frameDimension.width)/2, (screenDimension.height-frameDimension.height)/2);
      frame.setVisible(true);
   }
}
