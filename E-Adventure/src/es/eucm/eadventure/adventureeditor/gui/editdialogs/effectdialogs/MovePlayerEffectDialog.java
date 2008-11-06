package es.eucm.eadventure.adventureeditor.gui.editdialogs.effectdialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import es.eucm.eadventure.adventureeditor.control.controllers.EffectsController;
import es.eucm.eadventure.adventureeditor.gui.TextConstants;
import es.eucm.eadventure.adventureeditor.gui.otherpanels.positionimagepanels.ElementImagePanel;
import es.eucm.eadventure.adventureeditor.gui.otherpanels.positionpanel.PositionPanel;

/**
 * This class represents a dialog used to add and edit move player effects. It allows the user to type the position
 * directly, or to select it from a position of a scene.
 * 
 * @author Bruno Torijano Bueno
 */
public class MovePlayerEffectDialog extends EffectDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Combo box with the scenes.
	 */
	private JComboBox scenesComboBox;

	/**
	 * Panel to select and show the position.
	 */
	private PositionPanel playerPositionPanel;

	/**
	 * Constructor.
	 * 
	 * @param currentProperties
	 *            Set of initial values
	 */
	public MovePlayerEffectDialog( HashMap<Integer, String> currentProperties ) {

		// Call the super method
		super( TextConstants.getText( "MovePlayerEffect.Title" ) );

		// Load the path to the image of the player
		String playerPath = controller.getPlayerImagePath( );

		// Create the set of values for the scenes
		List<String> scenesList = new ArrayList<String>( );
		scenesList.add( TextConstants.getText( "SceneLocation.NoSceneSelected" ) );
		String[] scenesArray = controller.getIdentifierSummary( ).getSceneIds( );
		for( String scene : scenesArray )
			scenesList.add( scene );
		scenesArray = scenesList.toArray( new String[] {} );

		// Create the main panel
		JPanel mainPanel = new JPanel( );
		mainPanel.setLayout( new GridBagLayout( ) );
		GridBagConstraints c = new GridBagConstraints( );

		// Set the border of the panel with the description
		mainPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "MovePlayerEffect.Description" ) ) ) );

		// Create and add the list of scenes
		c.insets = new Insets( 2, 4, 4, 4 );
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		scenesComboBox = new JComboBox( scenesArray );
		scenesComboBox.addActionListener( new ScenesComboBoxActionListener( ) );
		mainPanel.add( scenesComboBox, c );

		// Create and add the panel
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		playerPositionPanel = new PositionPanel( new ElementImagePanel( null, playerPath ) );
		mainPanel.add( playerPositionPanel, c );

		// Add the panel to the center
		add( mainPanel, BorderLayout.CENTER );

		// Set the defualt values (if present)
		if( currentProperties != null ) {
			int x = 0;
			int y = 0;

			if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_X ) )
				x = Integer.parseInt( currentProperties.get( EffectsController.EFFECT_PROPERTY_X ) );

			if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_Y ) )
				y = Integer.parseInt( currentProperties.get( EffectsController.EFFECT_PROPERTY_Y ) );

			playerPositionPanel.setPosition( x, y );
		}

		// Set the dialog
		setResizable( false );
		setSize( 640, 480 );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setVisible( true );
	}

	@Override
	protected void pressedOKButton( ) {
		// Create a set of properties, and put the selected value
		properties = new HashMap<Integer, String>( );
		properties.put( EffectsController.EFFECT_PROPERTY_X, String.valueOf( playerPositionPanel.getPositionX( ) ) );
		properties.put( EffectsController.EFFECT_PROPERTY_Y, String.valueOf( playerPositionPanel.getPositionY( ) ) );
	}

	/**
	 * Listener for the scenes combo box.
	 */
	private class ScenesComboBoxActionListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent arg0 ) {
			int selectedScene = scenesComboBox.getSelectedIndex( );

			// If the first option were selected, remove the image
			if( selectedScene == 0 )
				playerPositionPanel.removeImage( );

			// If other option were selected, load the image
			else
				playerPositionPanel.loadImage( controller.getSceneImagePath( scenesComboBox.getSelectedItem( ).toString( ) ) );
		}
	}
}
