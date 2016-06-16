/**
 * bouncingsprites is the package for class placement.
 */
package bouncingsprites;
// import statements
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
/**
 * This class handles setting up the JPanel for the program and
 * assigns each Sprite object its own thread and handles how many
 * Sprites can be in a square drawn inside the whole JFrame.
 * @author		Todd Kelley, Richard Barney
 * @version		1.0.0 January 2016
 */
public class SpritePanel extends JPanel{
	/** Eclipse-generated serialVersionUID. */
	private static final long serialVersionUID = -8859698422264043702L;
	/** ArrayList of Sprite objects. */
	private List<Sprite> spritesArr = new ArrayList<Sprite>();
	/** Integer to determine how many sprites are in the square. */
	private int nNumSpritesInSquare;
	
	/** 
	 * Default constructor.
	 */
	public SpritePanel(){
		addMouseListener(new Mouse());
	}
	
	/**
	 * Void method for when sprite is entering the square (consumed).
	 * @throws InterruptedException 
	 */
	public synchronized void consume() throws InterruptedException {
		// if the box is full (2 Sprites inside), wait
		while (nNumSpritesInSquare == 2) {
			wait();
		}
		// increment number of Sprites in the square due to it entering
		// and notify threads
		++nNumSpritesInSquare;
		notifyAll();
	}
	
	/**
	 * Void method for when sprite is exiting the square (produced).
	 * @throws InterruptedException
	 */
	public synchronized void produce() throws InterruptedException {
		// if the box is empty or there is only one Sprite inside, wait
		while (nNumSpritesInSquare <= 1) {
			wait();
		}
		// decrement number of Sprites in the square due to it exiting
		// and notify threads
		--nNumSpritesInSquare;
		notifyAll();
	}	
	
	/**
	 * Void method that creates a new Sprite.
	 * @param	event	MouseEvent object.
	 */
	private void newSprite(MouseEvent event) {
		Sprite sprite = new Sprite(this);
		// create new thread for the new Sprite object and
		// add it to the ArrayList of Sprite objects
		new Thread(sprite).start();
		spritesArr.add(sprite);
	}
	
	/**
	 * Void method that animates the program. Makes use of an infinite
	 * loop to keep drawing the Sprite objects.
	 */
	public void animate(){ 
		while (true) {
			repaint();
		}
	}
	
	/**
	 * Inner class Mouse to handle MouseEvent.
	 */
	private class Mouse extends MouseAdapter {
		@Override
	    public void mousePressed(final MouseEvent event) {
	        newSprite(event);
	    }
	}

	/**
	 * Overridden paintComponent method.
	 * @param	g	Graphics object.
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		// set color to black for the box
		g.setColor(Color.BLACK);
		// four lines to draw box
		g.drawLine(40, 40, 345, 40); // top line
		g.drawLine(40, 320, 345, 320); // bottom line
		g.drawLine(40, 40, 40, 319); // left line
		g.drawLine(345, 40, 345, 319); // right line
		// paint every sprite in the ArrayList of Sprite objects
		for (Sprite sprite : spritesArr) {
			sprite.draw(g);
		}
	}
} // end class SpritePanel