package es.eucm.eadventure.editor.gui.displaydialogs;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;

import es.eucm.eadventure.editor.control.Controller;

/**
 * This holds the common operations for a class that displays images.
 * 
 * @author Bruno Torijano Bueno
 */
public abstract class GraphicDialog extends JDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new graphic dialog.
	 */
	public GraphicDialog( ) {

		// Call to the JDialog constructor
		super( Controller.getInstance( ).peekWindow( ), Dialog.ModalityType.TOOLKIT_MODAL );

		// Set the panel to paint the animation
		setLayout( new GridBagLayout( ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 20, 20, 20, 20 );
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		add( new ImagePanel( ), c );

		// Set the dialog and show it
		addWindowListener( new WindowAdapter( ) {
			public void windowClosing( WindowEvent e ) {
				setVisible( false );
				deleteImages( );
				dispose( );
			}
		} );
		setMinimumSize( new Dimension( 400, 300 ) );
		setSize( 500, 380 );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
	}

	/**
	 * Returns the image that must be showed in the dialog.
	 * 
	 * @return Image to show
	 */
	protected abstract Image getCurrentImage( );

	/**
	 * Returns the ratio of the current image.
	 * 
	 * @return Ratio of the image being displayed
	 */
	protected double getCurrentImageRatio( ) {
		if (getCurrentImage()!=null)
			return (double) getCurrentImage( ).getWidth( null ) / (double) getCurrentImage( ).getHeight( null );
		else 
			return Double.MIN_VALUE;
	}

	/**
	 * Deletes the images when the dialog has been closed.
	 */
	protected abstract void deleteImages( );

	/**
	 * Panel which paints the image.
	 */
	protected class ImagePanel extends JPanel {

		/**
		 * Required.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void paint( Graphics g ) {
			super.paint( g );

			// To paint, we compare the ratios of the dialog and the image
			double dialogRatio = (double) getWidth( ) / (double) getHeight( );
			double imageRatio = getCurrentImageRatio( );
			if (imageRatio!=Double.MIN_VALUE){
			int x, y, width, height;
			if( dialogRatio <= imageRatio ) {
				width = getWidth( );
				height = (int) ( getWidth( ) / imageRatio );
				x = 0;
				y = (int) ( ( getHeight( ) - height ) / 2 );
			}

			else {
				width = (int) ( getHeight( ) * imageRatio );
				height = getHeight( );
				x = (int) ( ( getWidth( ) - width ) / 2 );
				y = 0;
			}

			g.drawImage( getCurrentImage( ), x, y, width, height, null, null );
		}
		}
	}
}
