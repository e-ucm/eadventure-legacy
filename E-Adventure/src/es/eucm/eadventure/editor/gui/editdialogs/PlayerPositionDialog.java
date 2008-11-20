package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JDialog;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.gui.otherpanels.positionimagepanels.ElementImagePanel;
import es.eucm.eadventure.editor.gui.otherpanels.positionpanel.PositionPanel;

/**
 * This class is the dialog to edit the insertion position for the scenes and the next scene structures. It displays the
 * scene graphically, so the user can select a insertion point.
 * 
 * @author Bruno Torijano Bueno
 */
public class PlayerPositionDialog extends JDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Panel to select and show the position.
	 */
	private PositionPanel playerPositionPanel;

	/**
	 * Constructor.
	 * 
	 * @param sceneId
	 *            Identifier of the scene to display
	 * @param positionX
	 *            Initial X coordinate
	 * @param positionY
	 *            Initial Y coordinate
	 */
	public PlayerPositionDialog( String sceneId, int positionX, int positionY ) {

		// Call the super method
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "PlayerPosition.Title" ), Dialog.ModalityType.APPLICATION_MODAL );

		// Get the path to the scene image and the player
		String scenePath = Controller.getInstance( ).getSceneImagePath( sceneId );
		String playerPath = Controller.getInstance( ).getPlayerImagePath( );

		// Set the layout
		setLayout( new GridBagLayout( ) );
		GridBagConstraints c = new GridBagConstraints( );

		// Create and add the panel
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		playerPositionPanel = new PositionPanel( new ElementImagePanel( scenePath, playerPath ) );
		playerPositionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "PlayerPosition.PositionPanel" ) ) );
		playerPositionPanel.setPosition( positionX, positionY );
		add( playerPositionPanel, c );

		// Set the dialog
		setResizable( false );
		setSize( 640, 480 );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setVisible( true );
	}

	/**
	 * Returns X coordinate of the selected position
	 * 
	 * @return X coordinate of the selected position
	 */
	public int getPositionX( ) {
		return playerPositionPanel.getPositionX( );
	}

	/**
	 * Returns Y coordinate of the selected position
	 * 
	 * @return Y coordinate of the selected position
	 */
	public int getPositionY( ) {
		return playerPositionPanel.getPositionY( );
	}
}
