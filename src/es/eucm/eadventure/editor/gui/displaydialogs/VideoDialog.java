package es.eucm.eadventure.editor.gui.displaydialogs;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.gui.otherpanels.AudioPanel;
import es.eucm.eadventure.editor.gui.otherpanels.VideoPanel;

/**
 * This dialog plays a video file.
 * 
 * @author Javier Torrente
 */
public class VideoDialog extends JDialog {

	private VideoPanel videoPanel;
	
	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param videoPath
	 *            Path to the file to be played
	 */
	public VideoDialog( String videoPath ) {
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "VideoDialog.Title", AssetsController.getFilename( videoPath ) ), Dialog.ModalityType.APPLICATION_MODAL );

		// Add a video panel
		 videoPanel = new VideoPanel( videoPath );
		add( videoPanel );

		// Set the dialog properties
		setResizable( false );
		pack( );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		this.addWindowListener( new WindowAdapter(){

			public void windowClosed( WindowEvent e ) {
				videoPanel.removeVideo( );
				
			}

			public void windowClosing( WindowEvent e ) {
				videoPanel.removeVideo( );
				
			}
			
		});
		
		// Show the dialog
		setVisible( true );
	}
}
