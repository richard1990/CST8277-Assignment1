/**
 * bouncingsprites is the package for class placement.
 */
package bouncingsprites;
// import statements
import javax.swing.JFrame;
/**
 * This class sets up the program's UI and launches the whole program.
 * @author		Todd Kelley, Richard Barney
 * @version		1.0.0 January 2016
 */
public class BouncingSprites {
	/** JFrame object. */
    private JFrame frame;
    /** SpritePanel object. */
    private SpritePanel panel = new SpritePanel();
    
    /**
     * Default constructor.
     */
    public BouncingSprites() {
        frame = new JFrame("Bouncing Sprites");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setVisible(true);
        frame.setResizable(false);
    }
    
    /**
	 * Entry point "main()" as required by the JVM.
	 * @param	args	standard command line parameters (arguments) as a string array.
	 */
    public static void main(String[] args) {
        new BouncingSprites().panel.animate();
    } // end method main
} // end class BouncingSprites