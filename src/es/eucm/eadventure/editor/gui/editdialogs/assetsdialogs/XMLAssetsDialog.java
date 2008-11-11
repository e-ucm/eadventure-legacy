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

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.gui.TextConstants;

/**
 * This class manages the sets of assessment and adaptation files.
 * 
 * @author Bruno Torijano Bueno
 */
public class XMLAssetsDialog extends JDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constant for assessment files dialog.
	 */
	public static final int ASSESSMENT = 0;

	/**
	 * Constant for adaptation files dialog.
	 */
	public static final int ADAPTATION = 1;

	/**
	 * Category of the assets managed with this dialog.
	 */
	private int dialogCategory;

	/**
	 * Paths to the assets in the list.
	 */
	private String[] assetPaths;

	/**
	 * List holding the resources.
	 */
	private JList resourcesList;

	/**
	 * Constructor.
	 * 
	 * @param dialogType
	 *            Type of the asset dialog, can be XMLAssetDialog.ASSESSMENT or XMLAssetDialog.ADAPTATION
	 */
	public XMLAssetsDialog( int dialogType ) {
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
		String addFileText = null;
		String deleteFileText = null;
		switch( dialogType ) {
			case ASSESSMENT:
				titleText = TextConstants.getText( "AssessmentFiles.Title" );
				addFileText = TextConstants.getText( "AssessmentFiles.AddFile" );
				deleteFileText = TextConstants.getText( "AssessmentFiles.DeleteFile" );
				dialogCategory = AssetsController.CATEGORY_ASSESSMENT;
				break;
			case ADAPTATION:
				titleText = TextConstants.getText( "AdaptationFiles.Title" );
				addFileText = TextConstants.getText( "AdaptationFiles.AddFile" );
				deleteFileText = TextConstants.getText( "AdaptationFiles.DeleteFile" );
				dialogCategory = AssetsController.CATEGORY_ADAPTATION;
				break;
		}

		// Initialize variables
		assetPaths = AssetsController.getAssetsList( dialogCategory );

		// Create a container panel, and set the properties
		JPanel mainPanel = new JPanel( );
		mainPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), titleText ) );
		mainPanel.setLayout( new GridBagLayout( ) );
		mainPanel.setPreferredSize( new Dimension( 220, 0 ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 10, 5, 10 );

		// Create the table and add it
		resourcesList = new JList( AssetsController.getAssetFilenames( dialogCategory ) );
		resourcesList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		mainPanel.add( new JScrollPane( resourcesList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ), c );

		// Add an "Add file" button
		JButton addFile = new JButton( addFileText );
		addFile.addActionListener( new AddFileListener( ) );
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0;
		c.gridy = 1;
		mainPanel.add( addFile, c );

		// Add an "Delete file" button
		JButton deleteFile = new JButton( deleteFileText );
		deleteFile.addActionListener( new DeleteFileListener( ) );
		c.gridy = 2;
		mainPanel.add( deleteFile, c );

		// Add the panel
		setLayout( new GridBagLayout( ) );
		c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		add( mainPanel, c );

		// Set the size, position and properties of the dialog
		setTitle( titleText );
		setResizable( false );
		setSize( 350, 300 );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setVisible( true );
	}

	/**
	 * Updates the list of assets, and the data associated with it.
	 */
	private void updateAssets( ) {
		resourcesList.setListData( AssetsController.getAssetFilenames( dialogCategory ) );
		assetPaths = AssetsController.getAssetsList( dialogCategory );
	}

	/**
	 * Listener for the "Add file" button.
	 */
	private class AddFileListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			// Show the add elements dialog, and update the data if necessary
			if( AssetsController.addAssets( dialogCategory ) )
				updateAssets( );
		}
	}

	/**
	 * Listener for the "Delete file" button.
	 */
	private class DeleteFileListener implements ActionListener {

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
