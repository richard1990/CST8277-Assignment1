/**
 * bouncingsprites is the package for class placement
 */
package bouncingsprites;
// import statements
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
/**
 * This class sets up a Sprite that will be displayed on screen and moves
 * around in multiple directions, bouncing off boundaries (JFrame edges).
 * @author		Todd Kelley, Richard Barney
 * @version		1.0.0 January 2016
 */
public class Sprite implements Runnable {
	/** Random object. */
	public final static Random random = new Random();
	/** Static integer to determine sprite size. */
	final static int SIZE = 10;
	/** Static integer to determine sprite speed. */
	final static int MAX_SPEED = 5;
	/** SpritePanel object. */
	private SpritePanel panel;
	/** Integer used to determine the sprite's x-axis position. */
	private int x;
	/** Integer used to determine the sprite's y-axis position. */
	private int y;
	/** Integer for directional x-axis. */
	private int dx;
	/** Integer for directional y-axis. */
	private int dy;
	/** Color object for sprite (blue). */
	private Color color = Color.BLUE;	
	
	/**
	 * Parameterized constructor.
	 * @param	panel	SpritePanel object.
	 */
    public Sprite (SpritePanel panel) {
    	this.panel = panel;
        x = random.nextInt(panel.getWidth());
        y = random.nextInt(panel.getHeight());
        dx = random.nextInt(2*MAX_SPEED) - MAX_SPEED;
        dy = random.nextInt(2*MAX_SPEED) - MAX_SPEED;
    }
    
    /**
     * Overridden void method "run" necessary for Runnable interface.
     */
    @Override
    public void run() {
    	boolean inSquare = false; // boolean to determine if ball is in square
    	int nLastXPos, nLastYPos;
    	while (true) {
    		// get the last x- and y-axis position of the ball to
    		// compare its last location to its current location
        	nLastXPos = x;
        	nLastYPos = y;
			move(); 
			// determine if ball is entering square by checking left, bottom,
			// top, and right boundaries of square and if ball is generated inside
			// the square
			if ((((nLastXPos < x && x >= 40 && nLastXPos < 40) ||
				(nLastXPos > x && x <= 345 && nLastXPos > 345) ||
				(nLastYPos < y && y >= 40 && nLastYPos < 40)  ||
				(nLastYPos > y && y <= 320 && nLastYPos > 320)) && inSquare == false) ||
				(inSquare == false && x >= 40 && x <= 345 && y >= 40 && y <= 320)) {
				inSquare = true;
				try {
					panel.consume();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// determine if ball is exiting square by checking left, bottom,
			// top, and middle boundaries of square
			if (((nLastXPos > x && x <= 40 && nLastYPos > 40) ||
				(nLastXPos < x && x >= 345 && nLastXPos < 345) ||
				(nLastYPos > y && y <= 40 && nLastYPos > 40) ||
				(nLastYPos < y && y >= 320 && nLastYPos < 320)) && inSquare == true) {
				inSquare = false;
				try {
					panel.produce();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//sleep while waiting to display the next frame of the animation
			try {
	            Thread.sleep(40); // wake up roughly 25 frames per second
	        } catch (InterruptedException ex) {
	            ex.printStackTrace();
	        }
    	}
    } // end method run
    
    /**
     * Void method that draws the sprite.
     * @param	g	Graphics object.
     */
    public void draw(Graphics g) {
        g.setColor(color);
	    g.fillOval(x, y, SIZE, SIZE);
    }
    
    /**
     * Void method that determines how the sprite moves and how it
     * will bounce off the edges of the window.
     */
    public void move(){       
    	// if the sprite is less than 0 on x-axis and directionally is 
    	// moving to the left, then make it look like its bouncing 
    	// off the left wall by inverting the direction on x-axis
        if (x < 0 && dx < 0) {
            x = 0;
            dx = -dx;
        }
        // if the sprite is less than 0 on y-axis and directionally is 
    	// moving to the top, then make it look like its bouncing 
    	// off the top wall by inverting the direction on y-axis
        if (y < 0 && dy < 0) {
            y = 0;
            dy = -dy;
        }
        // if the sprite on the x-axis is greater than the panel width
        // and directionally is moving to the right, then make it look like
        // its bouncing off the right wall by inverting the direction
        // on x-axis
        if (x > panel.getWidth() - SIZE && dx > 0) {
        	x = panel.getWidth() - SIZE;
        	dx = - dx;
        }
        // if the sprite on the x-axis is greater than the panel height 
        // and directionally is moving to the bottom, then make it look like
        // its bouncing off the bottom wall by inverting the direction
        // on y-axis
        if (y > panel.getHeight() - SIZE && dy > 0) {
        	y = panel.getHeight() - SIZE;
        	dy = -dy;
        }
        // make the sprite move by incrementing the sprite's position on the
        // x- and y-axis with the sprite's direction
        x += dx;
        y += dy;
    } // end method move
} // end class Sprite