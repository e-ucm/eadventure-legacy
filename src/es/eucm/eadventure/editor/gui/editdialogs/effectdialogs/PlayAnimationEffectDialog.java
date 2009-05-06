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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.gui.displaydialogs.AnimationDialog;
import es.eucm.eadventure.editor.gui.otherpanels.positionimagepanels.PointImagePanel;
import es.eucm.eadventure.editor.gui.otherpanels.positionpanel.PositionPanel;

public class PlayAnimationEffectDialog extends EffectDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Controller of the effects.
	 */
	private EffectsController effectsController;

	/**
	 * Text field containing the path.
	 */
	private JTextField pathTextField;

	/**
	 * Button to display the actual asset.
	 */
	private JButton viewButton;

	/**
	 * Combo box with the scenes.
	 */
	private JComboBox scenesComboBox;

	/**
	 * Panel to select and show the position.
	 */
	private PositionPanel pointPositionPanel;

	/**
	 * Constructor.
	 * 
	 * @param effectsController
	 *            Controller of the effects
	 * @param currentProperties
	 *            Set of initial values
	 */
	public PlayAnimationEffectDialog( EffectsController effectsController, HashMap<Integer, Object> currentProperties ) {

		// Call the super method
		super( TextConstants.getText( "PlayAnimationEffect.Title" ) , true);
		this.effectsController = effectsController;

		// Create the set of values for the scenes
		List<String> scenesList = new ArrayList<String>( );
		scenesList.add( TextConstants.getText( "SceneLocation.NoSceneSelected" ) );
		String[] scenesArray = controller.getIdentifierSummary( ).getSceneIds( );
		for( String scene : scenesArray )
			scenesList.add( scene );
		scenesArray = scenesList.toArray( new String[] {} );

		// Load the image for the delete content button
		Icon deleteContentIcon = new ImageIcon( "img/icons/deleteContent.png" );

		// Create the asset panel and set the border
		JPanel assetPanel = new JPanel( );
		assetPanel.setLayout( new GridBagLayout( ) );
		assetPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "PlayAnimationEffect.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 4, 4, 4, 4 );
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		c.weighty = 0;

		// Create the delete content button
		JButton deleteContentButton = new JButton( deleteContentIcon );
		deleteContentButton.addActionListener( new DeleteContentButtonActionListener( ) );
		deleteContentButton.setPreferredSize( new Dimension( 20, 20 ) );
		deleteContentButton.setToolTipText( TextConstants.getText( "Resources.DeleteAsset" ) );
		assetPanel.add( deleteContentButton, c );

		// Create the text field and insert it
		pathTextField = new JTextField( );
		pathTextField.setEditable( false );
		c.gridx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		assetPanel.add( pathTextField, c );

		// Create the "Select" button and insert it
		JButton selectButton = new JButton( TextConstants.getText( "Resources.Select" ) );
		selectButton.addActionListener( new ExamineButtonActionListener( ) );
		c.gridx = 2;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		assetPanel.add( selectButton, c );

		// Create the "View" button and insert it
		viewButton = new JButton( TextConstants.getText( "Resources.ViewAsset" ) );
		viewButton.setEnabled( false );
		viewButton.addActionListener( new ViewButtonActionListener( ) );
		c.gridx = 3;
		assetPanel.add( viewButton, c );

		// Create the main panel
		JPanel mainPanel = new JPanel( );
		mainPanel.setLayout( new GridBagLayout( ) );
		c = new GridBagConstraints( );

		// Set the border of the panel with the description
		mainPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "PlayAnimationEffect.Description" ) ) ) );

		// Add the asset panel to the main panel
		c.insets = new Insets( 2, 4, 4, 4 );
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		mainPanel.add( assetPanel, c );

		// Create and add the list of scenes
		c.gridy = 1;
		scenesComboBox = new JComboBox( scenesArray );
		scenesComboBox.addActionListener( new ScenesComboBoxActionListener( ) );
		mainPanel.add( scenesComboBox, c );

		// Create and add the panel
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = 1;
		// Set the defualt values (if present)
		if( currentProperties != null ) {
			int x = 0;
			int y = 0;

			if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_PATH ) ) {
				pathTextField.setText( (String)currentProperties.get( EffectsController.EFFECT_PROPERTY_PATH ) );
				viewButton.setEnabled( pathTextField.getText( ) != null );
			}

			if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_X ) )
				x = Integer.parseInt( (String) currentProperties.get( EffectsController.EFFECT_PROPERTY_X ) );

			if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_Y ) )
				y = Integer.parseInt( (String)currentProperties.get( EffectsController.EFFECT_PROPERTY_Y ) );

			pointPositionPanel = new PositionPanel( new PointImagePanel( ),x,y );
			//pointPositionPanel.setPosition( x, y );
		} else {
			pointPositionPanel = new PositionPanel( new PointImagePanel( ),400,500 );
		}
		//pointPositionPanel = new PositionPanel( new PointImagePanel( ) );
		mainPanel.add( pointPositionPanel, c );

		// Add the panel to the center
		add( mainPanel, BorderLayout.CENTER );



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
		properties = new HashMap<Integer, Object>( );
		properties.put( EffectsController.EFFECT_PROPERTY_PATH, pathTextField.getText( ) );
		properties.put( EffectsController.EFFECT_PROPERTY_X, String.valueOf( pointPositionPanel.getPositionX( ) ) );
		properties.put( EffectsController.EFFECT_PROPERTY_Y, String.valueOf( pointPositionPanel.getPositionY( ) ) );
	}

	/**
	 * Listener for the delete content button.
	 */
	private class DeleteContentButtonActionListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			// Delete the current path and disable the view button
			pathTextField.setText( null );
			viewButton.setEnabled( false );
		}
	}

	/**
	 * Listener for the examine button.
	 */
	private class ExamineButtonActionListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			// Ask the user for an animation
			String newPath = effectsController.selectAsset( EffectsController.ASSET_ANIMATION );

			// If a new value was selected, set it and enable the view button
			if( newPath != null ) {
				pathTextField.setText( newPath );
				viewButton.setEnabled( true );
			}
		}
	}

	/**
	 * Listener for the view button.
	 */
	private class ViewButtonActionListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			new AnimationDialog( pathTextField.getText( ) );
		}
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
				pointPositionPanel.removeImage( );

			// If other option were selected, load the image
			else
				pointPositionPanel.loadImage( controller.getSceneImagePath( scenesComboBox.getSelectedItem( ).toString( ) ) );
		}
	}
}