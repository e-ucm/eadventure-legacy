package es.eucm.eadventure.editor.gui.displaydialogs;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.gui.otherpanels.AudioPanel;

/**
 * This dialog plays an audio file.
 * 
 * @author Bruno Torijano Bueno
 */
public class AudioDialog extends JDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param soundPath
	 *            Path to the file to be played
	 */
	public AudioDialog( String soundPath ) {
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "AudioDialog.Title", AssetsController.getFilename( soundPath ) ), Dialog.ModalityType.APPLICATION_MODAL );

		// Add a audio panel
		add( new AudioPanel( soundPath ) );

		// Set the dialog properties
		setResizable( false );
		pack( );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );

		// Show the dialog
		setVisible( true );
	}
}
