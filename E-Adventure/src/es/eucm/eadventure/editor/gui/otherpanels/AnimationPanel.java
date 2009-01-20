package es.eucm.eadventure.editor.gui.otherpanels;

import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.gui.auxiliar.clock.Clock;
import es.eucm.eadventure.editor.gui.auxiliar.clock.ClockListener;

/**
 * This panel holds an image inside, painted with its own aspect ratio.
 * 
 * @author Bruno Torijano Bueno
 */
public class AnimationPanel extends JPanel implements ClockListener {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Margin to display the image.
	 */
	private static final int MARGIN = 20;

	/**
	 * Time per animation frame.
	 */
	private static final int TIME_PER_FRAME = 100;

	/**
	 * Time per slide image.
	 */
	private static final int TIME_PER_SLIDE = 1200;

	/**
	 * Clock for the animation.
	 */
	private Clock clock;

	/**
	 * Stores the time that an image stands on the panel.
	 */
	private int imageTime;

	/**
	 * List of frames.
	 */
	private Image[] frames;

	/**
	 * Accumulated played time of the animation.
	 */
	private long accumulatedAnimationTime;

	/**
	 * Index of the current frame.
	 */
	private int currentFrameIndex;

	
	private Animation animation;
	
	
	/**
	 * Constructor.
	 */
	public AnimationPanel( ) {
		super( );

		// Add the closing listener
		addAncestorListener( new ClosingListener( ) );
		this.addMouseListener(new AnimationPanelMouseListener());
		// Set the image to null and start the clock
		frames = null;
		clock = new Clock( this );
		clock.start( );

		// Add a label
		setLayout( new GridBagLayout( ) );
		add( new JLabel( TextConstants.getText( "AnimationPanel.AnimationNotAvalaible" ) ) );
	}

	/**
	 * Constructor. This one is used for the animation dialogs. The animation path must NOT include the suffix, as it
	 * will be added in this constructor.
	 * 
	 * @param animationPath
	 *            Path to the animation , including the suffix
	 */
	public AnimationPanel( String animationPath ) {
		this( );
		loadAnimation( animationPath );
	}
	
	public AnimationPanel( Animation animation) {
		this();
		this.animation=animation;
		animation.restart();
		// Remove all components, and add a label if the animation is not loaded
		removeAll( );
		if( !isAnimationLoaded( ) ) {
			add( new JLabel( TextConstants.getText( "AnimationPanel.AnimationNotAvalaible" ) ) );
			revalidate( );
		}

		// Repaint the panel
		repaint( );

	}

	/**
	 * Loads the given animation in the panel.
	 * 
	 * @param animationPath
	 *            Path of the animation, including the suffix
	 */
	public void loadAnimation( String animationPath ) {
		// Clear the animation (if there was one)
		if( frames != null )
			for( Image frame : frames )
				frame.flush( );

		this.animation = null;
		
		if (animationPath.endsWith(".eaa")) {
			this.animation = Animation.loadAnimation(animationPath);
		} else {
	
			// Load the image and calculate the sizes
			if( animationPath != null ) {
				// Load the animation
				frames = AssetsController.getAnimation( animationPath );
				currentFrameIndex = 0;
	
				// Set the new time for the frame change
				if( animationPath.endsWith( "png" ) )
					imageTime = TIME_PER_FRAME;
				else if( animationPath.endsWith( "jpg" ) )
					imageTime = TIME_PER_SLIDE;
			}
	
			// If no path is given, delete the frames
			else
				frames = null;
		}
		// Remove all components, and add a label if the animation is not loaded
		removeAll( );
		if( !isAnimationLoaded( ) ) {
			add( new JLabel( TextConstants.getText( "AnimationPanel.AnimationNotAvalaible" ) ) );
			revalidate( );
		}

		// Repaint the panel
		repaint( );
	}

	/**
	 * Removes the current animation from the panel.
	 */
	public void removeAnimation( ) {
		// Remove the animation frames
		if( frames != null )
			for( Image frame : frames )
				frame.flush( );
		frames = null;

		// Remove all components, and add a label
		removeAll( );
		add( new JLabel( TextConstants.getText( "AnimationPanel.AnimationNotAvalaible" ) ) );
		revalidate( );

		// Repaint the panel
		repaint( );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.editor.gui.auxiliar.clock.ClockListener#update(long)
	 */
	public void update( long elapsedTime ) {
		if (animation != null) {
			if (!animation.isSlides() || !animation.finishedFirstTime(accumulatedAnimationTime)) {
				accumulatedAnimationTime += elapsedTime;
				repaint();
			}
		}
		else {
		// If the animation is loaded
		if( isAnimationLoaded( ) ) {
			// If there is more than one frame
			if( frames.length > 1 ) {
				// Add the elapsed time to the accumulated
				accumulatedAnimationTime += elapsedTime;

				// Skip frame for every "imageTime" miliseconds
				while( accumulatedAnimationTime > imageTime ) {
					accumulatedAnimationTime -= imageTime;
					currentFrameIndex++;
				}

				currentFrameIndex %= frames.length;

				// Repaint the dialog
				repaint( );
			}
		}
		}
	}

	@Override
	public void paint( Graphics g ) {
		super.paint( g );		
		// Paint the animation
		if( isAnimationLoaded( ) ) {
			// To paint, we compare the ratios of the dialog and the image
			double dialogRatio = (double) ( getWidth( ) - ( MARGIN * 2 ) ) / (double) ( getHeight( ) - ( MARGIN * 2 ) );
			double imageRatio;
			if (animation != null) {
				Image temp = animation.getImage(accumulatedAnimationTime);
		
				imageRatio = (double) temp.getWidth( null ) / (double) temp.getHeight( null );
			} else {
				imageRatio = (double) frames[currentFrameIndex].getWidth( null ) / (double) frames[currentFrameIndex].getHeight( null );			
			}

			int x, y, width = -MARGIN * 2, height = -MARGIN * 2;
			if( dialogRatio <= imageRatio ) {
				width += getWidth( );
				height += (int) ( getWidth( ) / imageRatio );
				x = MARGIN;
				y = (int) ( ( getHeight( ) - height ) / 2 );
			}

			else {
				width += (int) ( getHeight( ) * imageRatio );
				height += getHeight( );
				x = (int) ( ( getWidth( ) - width ) / 2 );
				y = MARGIN;
			}
			if (animation != null) {
				g.drawImage( animation.getImage(accumulatedAnimationTime), x, y, width, height, null, null );			
			} else {
				g.drawImage( frames[currentFrameIndex], x, y, width, height, null, null );
			}
		}
	}

	/**
	 * Returns whether the animation is loaded or not.
	 * 
	 * @return True if the animation was loaded, false otherwise
	 */
	private boolean isAnimationLoaded( ) {		
		return ((frames != null && frames.length > 0) || animation != null);
	}

	/**
	 * Ancestor listener to stop the clock when the panel is disposed.
	 */
	private class ClosingListener implements AncestorListener {

		public void ancestorRemoved( AncestorEvent event ) {
			// Stop the clock
			try {
				clock.stopClock( );
				clock.join( );
				clock = null;
			} catch( InterruptedException e ) {
				e.printStackTrace( );
			}
		}

		public void ancestorAdded( AncestorEvent event ) {}

		public void ancestorMoved( AncestorEvent event ) {}
	}
	
	private class AnimationPanelMouseListener implements MouseListener {

		public void mouseClicked(MouseEvent arg0) {
			if (animation != null) {
				accumulatedAnimationTime = animation.skipFrame(accumulatedAnimationTime);
			}
		}

		public void mouseEntered(MouseEvent arg0) {
		}

		public void mouseExited(MouseEvent arg0) {
		}

		public void mousePressed(MouseEvent arg0) {
		}

		public void mouseReleased(MouseEvent arg0) {
		}
		
	}
}
