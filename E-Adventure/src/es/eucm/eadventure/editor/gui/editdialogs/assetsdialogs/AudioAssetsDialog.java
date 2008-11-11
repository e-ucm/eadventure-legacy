package es.eucm.eadventure.editor.gui.editdialogs.assetsdialogs;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.gui.TextConstants;
import es.eucm.eadventure.editor.gui.otherpanels.AudioPanel;

/**
 * This class manages the sets of audio assets, including MIDI and MP3 files.
 * 
 * @author Bruno Torijano Bueno
 */
public class AudioAssetsDialog extends JDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Paths to the assets in the list.
	 */
	private String[] assetPaths;

	/**
	 * List holding the resources.
	 */
	private JList resourcesList;

	/**
	 * Audio panel for the preview.
	 */
	private AudioPanel audioPanel;

	/**
	 * Constructor.
	 */
	public AudioAssetsDialog( ) {
		// Call to the JDialog constructor
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "AudioAssets.Title" ), Dialog.ModalityType.APPLICATION_MODAL );

		// Push the dialog into the stack, and add the window listener to pop in when closing
		Controller.getInstance( ).pushWindow( this );
		addWindowListener( new WindowAdapter( ) {
			public void windowClosing( WindowEvent e ) {
				Controller.getInstance( ).popWindow( );
			}
		} );

		// Initialize variables
		assetPaths = AssetsController.getAssetsList( AssetsController.CATEGORY_AUDIO );

		// Create a container panel, and set the properties
		JPanel mainPanel = new JPanel( );
		mainPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AudioAssets.Title" ) ) );
		mainPanel.setLayout( new GridBagLayout( ) );
		mainPanel.setPreferredSize( new Dimension( 400, 0 ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 10, 5, 10 );

		// Create the table and add it
		resourcesList = new JList( AssetsController.getAssetFilenames( AssetsController.CATEGORY_AUDIO ) );
		resourcesList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		resourcesList.addListSelectionListener( new ResourcesListListener( ) );
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		mainPanel.add( new JScrollPane( resourcesList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ), c );

		// Add an "Add asset" button
		JButton addAsset = new JButton( TextConstants.getText( "Assets.AddAsset" ) );
		addAsset.addActionListener( new AddAssetListener( ) );
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0;
		c.gridy = 1;
		mainPanel.add( addAsset, c );

		// Add an "Delete asset" button
		JButton deleteAsset = new JButton( TextConstants.getText( "Assets.DeleteAsset" ) );
		deleteAsset.addActionListener( new DeleteAssetListener( ) );
		c.gridy = 2;
		mainPanel.add( deleteAsset, c );

		// Create a panel for the element preview
		audioPanel = new AudioPanel( );
		audioPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AudioAssets.Preview" ) ) );

		// Add the panel
		setLayout( new GridBagLayout( ) );
		c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1;
		add( mainPanel, c );

		c.weightx = 1;
		c.gridx = 1;
		add( audioPanel, c );

		// Set the size, position and properties of the dialog
		setResizable( false );
		setSize( 600, 300 );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setVisible( true );
	}

	/**
	 * Updates the list of assets, and the data associated with it.
	 */
	private void updateAssets( ) {
		resourcesList.setListData( AssetsController.getAssetFilenames( AssetsController.CATEGORY_AUDIO ) );
		assetPaths = AssetsController.getAssetsList( AssetsController.CATEGORY_AUDIO );
	}

	/**
	 * Listener for the list, everytime the selection changes, the image is displayed in the preview panel.
	 */
	private class ResourcesListListener implements ListSelectionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
		 */
		public void valueChanged( ListSelectionEvent e ) {
			// If there is an asset selected, show it
			if( resourcesList.getSelectedIndex( ) >= 0 )
				audioPanel.loadAudio( assetPaths[resourcesList.getSelectedIndex( )] );

			// Else, delete the preview image
			else
				audioPanel.removeAudio( );
		}
	}

	/**
	 * Listener for the "Add asset" button.
	 */
	private class AddAssetListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			// Stop the audio first
			audioPanel.stopSound( );

			// Show the add elements dialog, and update the data if necessary
			if( AssetsController.addAssets( AssetsController.CATEGORY_AUDIO ) )
				updateAssets( );
		}
	}

	/**
	 * Listener for the "Delete asset" button.
	 */
	private class DeleteAssetListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			// If there is an asset selected, delete it
			if( resourcesList.getSelectedIndex( ) >= 0 && AssetsController.deleteAsset( assetPaths[resourcesList.getSelectedIndex( )] ) )
				updateAssets( );
		}
	}
}
