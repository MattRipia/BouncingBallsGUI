package bouncingballs;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dqz7130
 */
public class Ball implements Runnable{

    int xPosition, yPosition;
    int xSpeed, ySpeed;
    int ballSize;

    static int _frameWidth, _frameHeight;
    boolean stopRequest;
    Color colour;
    Random rand = new Random();

    public Ball(int frameWidth, int frameHeight){

        // change these values to get random values
        _frameWidth = frameWidth;
        _frameHeight = frameHeight;
        colour = PickColour();
        ballSize = rand.nextInt(40);
        xPosition = rand.nextInt(frameWidth - this.ballSize * 3);
        yPosition = rand.nextInt(frameHeight- this.ballSize * 3);
        xSpeed = rand.nextInt(10);
        ySpeed = rand.nextInt(10);

        while(xSpeed == 0 || ySpeed == 0)
        {
            xSpeed = rand.nextInt(10);
            ySpeed = rand.nextInt(10);
        }
    }

    private Color PickColour() {

        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        return new Color(r, g, b);
    }

    private void moveBall(){

        // reverses the direction of the ball when it bounces
        if(xPosition > _frameWidth - ballSize)
             xSpeed *= -1;
        if(xPosition < 0)
            xSpeed *= -1;
        if(yPosition > _frameHeight - ballSize)
            ySpeed *= -1;
        if(yPosition < 0)
            ySpeed *= -1;

        //this.colour = PickColour();
        xPosition = xPosition + xSpeed;
        yPosition = yPosition + ySpeed;
    }

    public void drawBall(Graphics g){

        g.setColor(colour);
        g.fillOval(xPosition, yPosition, ballSize, ballSize);
    }

    @Override
    public void run() {

        stopRequest = false;

        while(!stopRequest)
        {
            moveBall();

            try {
                Thread.sleep(10);

            } catch (InterruptedException ex) {
                Logger.getLogger(Ball.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
