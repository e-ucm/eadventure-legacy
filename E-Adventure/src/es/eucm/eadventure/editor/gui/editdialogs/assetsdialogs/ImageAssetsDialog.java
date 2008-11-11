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
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.ImagePanel;

/**
 * This class manages the sets of image assets, including backgrounds, general images and icons.
 * 
 * @author Bruno Torijano Bueno
 */
public class ImageAssetsDialog extends JDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constant for background asset dialog.
	 */
	public static final int BACKGROUND = 0;

	/**
	 * Constant for image asset dialog.
	 */
	public static final int IMAGE = 1;

	/**
	 * Cosntant for icon asset dialog.
	 */
	public static final int ICON = 2;

	/**
	 * Category of the assets managed with this dialog.
	 */
	private int assetsCategory;

	/**
	 * Paths to the assets in the list.
	 */
	private String[] assetPaths;

	/**
	 * List holding the resources.
	 */
	private JList resourcesList;

	/**
	 * Image panel for the preview.
	 */
	private ImagePanel imagePanel;

	/**
	 * Constructor.
	 * 
	 * @param dialogType
	 *            Type of the dialog, must be AssetsController.BACKGROUND, AssetsController.IMAGE or
	 *            AssetsController.ICON
	 */
	public ImageAssetsDialog( int dialogType ) {
		// Call to the JDialog constructor
		super( Controller.getInstance( ).peekWindow( ), Dialog.ModalityType.APPLICATION_MODAL );

		// Push the dialog into the stack, and add the window listener to pop in when closing
		Controller.getInstance( ).pushWindow( this );
		addWindowListener( new WindowAdapter( ) {
			public void windowClosing( WindowEvent e ) {
				Controller.getInstance( ).popWindow( );
			}
		} );

		// Take the title and preview text for the panels
		String titleText = null;
		String previewText = null;
		switch( dialogType ) {
			case BACKGROUND:
				titleText = TextConstants.getText( "BackgroundAssets.Title" );
				previewText = TextConstants.getText( "BackgroundAssets.Preview" );
				assetsCategory = AssetsController.CATEGORY_BACKGROUND;
				break;
			case IMAGE:
				titleText = TextConstants.getText( "ImageAssets.Title" );
				previewText = TextConstants.getText( "ImageAssets.Preview" );
				assetsCategory = AssetsController.CATEGORY_IMAGE;
				break;
			case ICON:
				titleText = TextConstants.getText( "IconAssets.Title" );
				previewText = TextConstants.getText( "IconAssets.Preview" );
				assetsCategory = AssetsController.CATEGORY_ICON;
				break;
		}

		// Initialize variables
		assetPaths = AssetsController.getAssetsList( assetsCategory );

		// Create a container panel, and set the properties
		JPanel mainPanel = new JPanel( );
		mainPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), titleText ) );
		mainPanel.setLayout( new GridBagLayout( ) );
		mainPanel.setPreferredSize( new Dimension( 220, 0 ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 10, 5, 10 );

		// Create the table and add it
		resourcesList = new JList( AssetsController.getAssetFilenames( assetsCategory ) );
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
		imagePanel = new ImagePanel( );
		imagePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), previewText ) );

		// Add the panel
		setLayout( new GridBagLayout( ) );
		c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1;
		add( mainPanel, c );

		c.weightx = 1;
		c.gridx = 1;
		add( imagePanel, c );

		// Set the size, position and properties of the dialog
		setTitle( titleText );
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
		resourcesList.setListData( AssetsController.getAssetFilenames( assetsCategory ) );
		assetPaths = AssetsController.getAssetsList( assetsCategory );
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
				imagePanel.loadImage( assetPaths[resourcesList.getSelectedIndex( )] );

			// Else, delete the preview image
			else
				imagePanel.removeImage( );
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
			// Show the add elements dialog, and update the data if necessary
			if( AssetsController.addAssets( assetsCategory ) )
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
