package es.eucm.eadventure.editor.gui.displaydialogs;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;

import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.common.loader.Loader;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.gui.otherpanels.AnimationPanel;

/**
 * This class plays an animation showing it inside a dialog.
 * 
 * @author Bruno Torijano Bueno
 */
public class AnimationDialog extends JDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new animation dialog with the given path.
	 * 
	 * @param animationPath
	 *            Path of the animation
	 */
	public AnimationDialog( String animationPath ) {
		// Call to the JDialog constructor
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "AnimationDialog.Title", AssetsController.getFilename( animationPath ) ), Dialog.ModalityType.APPLICATION_MODAL );

		
		if (animationPath.endsWith(".eaa")) {
			add(new AnimationPanel(true, Loader.loadAnimation(AssetsController.getInputStreamCreator(), animationPath)));
		} else {
			// Create and add the animation panel (a PNG suffix is attached to the path)
			add( new AnimationPanel(true, animationPath + "_01.png" ) );
		}
		// Set the dialog properties
		setMinimumSize( new Dimension( 400, 300 ) );
		setSize( 500, 380 );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );

		// Show the dialog
		setVisible( true );
	}

	/**
	 * Creates a new animation dialog with the given path.
	 * 
	 * @param animationPath
	 *            Path of the animation
	 */
	public AnimationDialog( Animation animation ) {
		// Call to the JDialog constructor
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "AnimationDialog.Title", animation.getId()), Dialog.ModalityType.APPLICATION_MODAL );

		// Create and add the animation panel (a PNG suffix is attached to the path)
		add( new AnimationPanel(true, animation ) );

		// Set the dialog properties
		setMinimumSize( new Dimension( 400, 300 ) );
		setSize( 500, 380 );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );

		// Show the dialog
		setVisible( true );
	}

}