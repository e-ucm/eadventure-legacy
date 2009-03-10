package es.eucm.eadventure.engine.core.gui;

import java.awt.AWTException;
import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import es.eucm.eadventure.common.data.chapter.NextScene;

/**
 * This class creates and manages the graphic transitions
 * between scenes of the game
 *
 * @author Eugenio Marchiori
 */
public class Transition {

	/**
     * Total time of the current transition
     */
    private int totalTime;
    
    /**
     * Type of the current transition
     */
    private int type;
    
    /**
     * Elapsed time for the current transition
     */
    private long elapsedTime;
        
    /**
     * True if the current transition has already started
     */
    private boolean started;

    /**
     * The bufferd image of the transition
     */
    private BufferedImage transitionImage;
    
	/**
	 * Temporary image for the transition
	 */
	private static BufferedImage tempImage = new BufferedImage(GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);

    public Transition(int transitionTime, int transitionType) {
    	this.totalTime = transitionTime;
    	this.type = transitionType;
    	this.elapsedTime = 0;
   		this.started = false;
	}
    
    public boolean hasFinished(long elapsedTime) {
    	this.elapsedTime += elapsedTime;
    	if (started && this.elapsedTime > totalTime) {
    		return true;
    	}
    	return false;
    }
    
    public void update(Graphics2D g) {
		if (!started) {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Dimension screenSize = toolkit.getScreenSize();
			Rectangle screenRect = new Rectangle(screenSize);
			Robot robot;
			int tempX = 0, tempY = 0;
			Frame frame = GUI.getInstance().getJFrame();
			if (frame != null) {
				tempX = frame.getX();
				tempY = frame.getY();
			}
			try {
				robot = new Robot();
    			transitionImage = robot.createScreenCapture(screenRect);
    			Canvas gameFrame = GUI.getInstance().getFrame();
    			transitionImage = transitionImage.getSubimage(gameFrame.getX() + tempX, gameFrame.getY() + tempY, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT);
    			this.elapsedTime = 0;
			} catch (AWTException e) {
			}
	        started = true;
	        g.drawImage(transitionImage, 0, 0, null);
		} else {
			Graphics2D g2 = tempImage.createGraphics();
			GUI.getInstance().drawToGraphics(g2);
			g2.dispose();

			float temp = (float) this.elapsedTime / (float) totalTime;
			if (type == NextScene.RIGHT_TO_LEFT) {
    			float temp2 = (float) GUI.WINDOW_WIDTH * temp;
    			g.drawImage(transitionImage, (int) (- temp2), 0, null);
    			g.drawImage(tempImage, (int) (GUI.WINDOW_WIDTH - temp2), 0,  null);
			} else if (type == NextScene.LEFT_TO_RIGHT) {
    			float temp2 = (float) GUI.WINDOW_WIDTH * temp;
    			g.drawImage(transitionImage, (int) (temp2), 0, null);
    			g.drawImage(tempImage, (int) (temp2 - GUI.WINDOW_WIDTH), 0,  null);
			} else if (type == NextScene.TOP_TO_BOTTOM) {
    			float temp3 = (float) GUI.WINDOW_HEIGHT * temp;
    			g.drawImage(transitionImage, 0, (int) temp3, null);
    			g.drawImage(tempImage, 0, (int) (temp3 - GUI.WINDOW_HEIGHT), null);
			} else if (type == NextScene.BOTTOM_TO_TOP) {
    			float temp3 = (float) GUI.WINDOW_HEIGHT * temp;
    			g.drawImage(transitionImage, 0, (int) -temp3, null);
    			g.drawImage(tempImage, 0, (int) (GUI.WINDOW_HEIGHT - temp3), null);
			} else if (type == NextScene.FADE_IN) {
				g.drawImage(tempImage, 0, 0, null);
				AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1 - temp);
				g.setComposite(alphaComposite);
				g.drawImage(transitionImage, 0, 0, null);
			}
		}
    }

}
