package es.eucm.eadventure.editor.gui.editdialogs.effectdialogs;

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
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.gui.otherpanels.positionimagepanels.ElementImagePanel;
import es.eucm.eadventure.editor.gui.otherpanels.positionpanel.PositionPanel;

/**
 * This class represents a dialog used to add and edit character player effects. It allows the user to type the position
 * directly, or to select it from a position of a scene.
 * 
 * @author Bruno Torijano Bueno
 */
public class MoveNPCEffectDialog extends EffectDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Combo box with the characters of the game
	 */
	private JComboBox charactersComboBox;

	/**
	 * Combo box with the scenes.
	 */
	private JComboBox scenesComboBox;

	/**
	 * Panel to select and show the position.
	 */
	private PositionPanel npcPositionPanel;

	/**
	 * Panel to display the element (it changes along with the selected NPC).
	 */
	private ElementImagePanel elementPositionImagePanel;

	/**
	 * Constructor.
	 * 
	 * @param currentProperties
	 *            Set of initial values
	 */
	public MoveNPCEffectDialog( HashMap<Integer, Object> currentProperties ) {

		// Call the super method
		super( TextConstants.getText( "MoveNPCEffect.Title" ) , true);

		// Take the list of characters
		String[] charactersArray = controller.getIdentifierSummary( ).getNPCIds( );

		// If there is one character
		if( charactersArray.length > 0 ) {

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
			mainPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "MoveNPCEffect.Description" ) ) ) );

			// Create and add the list of characters
			c.insets = new Insets( 2, 4, 4, 4 );
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			charactersComboBox = new JComboBox( charactersArray );
			charactersComboBox.addActionListener( new CharactersComboBoxListener( ) );
			mainPanel.add( charactersComboBox, c );

			// Create and add a description for the scenes
			c.gridy = 1;
			mainPanel.add( new JLabel( TextConstants.getText( "SceneLocation.SceneListDescription" ) ), c );

			// Create and add the list of scenes
			c.gridy = 2;
			scenesComboBox = new JComboBox( scenesArray );
			scenesComboBox.addActionListener( new ScenesComboBoxListener( ) );
			mainPanel.add( scenesComboBox, c );

			// Create the panel which will display the background and the position
			elementPositionImagePanel = new ElementImagePanel( null, null );

			// Create and add the panel to edit the position
			c.fill = GridBagConstraints.BOTH;
			c.gridx = 0;
			c.gridy = 3;
			c.weightx = 1;
			c.weighty = 1;
			
			// Set the defualt values (if present)
			if( currentProperties != null ) {
				int x = 0;
				int y = 0;

				if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_TARGET ) )
					charactersComboBox.setSelectedItem( currentProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );

				if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_X ) )
					x = Integer.parseInt( (String)currentProperties.get( EffectsController.EFFECT_PROPERTY_X ) );

				if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_Y ) )
					y = Integer.parseInt( (String) currentProperties.get( EffectsController.EFFECT_PROPERTY_Y ) );

				npcPositionPanel= new PositionPanel( elementPositionImagePanel,x,y );
				//npcPositionPanel.setPosition( x, y );
			} else{
				npcPositionPanel= new PositionPanel( elementPositionImagePanel,400,500 );
			}
			
			
			//npcPositionPanel = new PositionPanel( elementPositionImagePanel );
			mainPanel.add( npcPositionPanel, c );

			// Add the panel to the center
			add( mainPanel, BorderLayout.CENTER );



			// Set the character image in the element position image panel
			String npcPath = controller.getElementImagePath( charactersComboBox.getSelectedItem( ).toString( ) );
			elementPositionImagePanel.loadElement( npcPath );
			elementPositionImagePanel.repaint( );

			// Set the dialog
			setResizable( false );
			setSize( 640, 480 );
			Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
			setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
			setVisible( true );
		}

		// If the list had no elements, show an error message
		else
			controller.showErrorDialog( TextConstants.getText( "MoveNPCEffect.Title" ), TextConstants.getText( "MoveNPCEffect.ErrorNoCharacters" ) );
	}

	@Override
	protected void pressedOKButton( ) {
		// Create a set of properties, and put the selected value
		properties = new HashMap<Integer, Object>( );
		properties.put( EffectsController.EFFECT_PROPERTY_TARGET, charactersComboBox.getSelectedItem( ).toString( ) );
		properties.put( EffectsController.EFFECT_PROPERTY_X, String.valueOf( npcPositionPanel.getPositionX( ) ) );
		properties.put( EffectsController.EFFECT_PROPERTY_Y, String.valueOf( npcPositionPanel.getPositionY( ) ) );
	}

	/**
	 * Listener for the characters combo box.
	 */
	private class CharactersComboBoxListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent arg0 ) {
			// Set the new character image in the element position image panel
			String npcPath = controller.getElementImagePath( charactersComboBox.getSelectedItem( ).toString( ) );
			elementPositionImagePanel.loadElement( npcPath );
			elementPositionImagePanel.repaint( );
		}
	}

	/**
	 * Listener for the scenes combo box.
	 */
	private class ScenesComboBoxListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent arg0 ) {
			int selectedScene = scenesComboBox.getSelectedIndex( );

			// If the first option were selected, remove the image
			if( selectedScene == 0 )
				npcPositionPanel.removeImage( );

			// If other option were selected, load the image
			else
				npcPositionPanel.loadImage( controller.getSceneImagePath( scenesComboBox.getSelectedItem( ).toString( ) ) );
		}
	}
}