package es.eucm.eadventure.adventureeditor.gui.editdialogs.customizeguidialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import es.eucm.eadventure.adventureeditor.control.Controller;
import es.eucm.eadventure.adventureeditor.control.controllers.AssetsController;
import es.eucm.eadventure.adventureeditor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.adventureeditor.data.adventuredata.AdventureDataControl;
import es.eucm.eadventure.adventureeditor.data.adventuredata.CustomCursor;
import es.eucm.eadventure.adventureeditor.gui.TextConstants;
import es.eucm.eadventure.adventureeditor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.adventureeditor.gui.displaydialogs.AnimationDialog;
import es.eucm.eadventure.adventureeditor.gui.displaydialogs.AudioDialog;
import es.eucm.eadventure.adventureeditor.gui.displaydialogs.ImageDialog;
import es.eucm.eadventure.adventureeditor.gui.displaydialogs.SlidesDialog;
import es.eucm.eadventure.adventureeditor.gui.displaydialogs.VideoDialog;
import es.eucm.eadventure.adventureeditor.gui.editdialogs.ConditionsDialog;
import es.eucm.eadventure.adventureeditor.gui.elementpanels.PreviewUpdater;

/**
 * 
 * @author Javier Torrente
 */
public class CursorsPanel extends JScrollPane {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	private static final int MAX_SPACE = 0;

	/**
	 * The list of cursors
	 */
	private String[] cursorTypes;
	
	private AdventureDataControl adventureData;

	/**
	 * The text fields with the cursor paths.
	 */
	private JTextField[] cursorFields;

	/**
	 * The buttons with the "View" option.
	 */
	private JButton[] viewButtons;


	/**
	 * Constructor.
	 * 
	 * @param dataControl
	 *            Resources data control
	 */
	public CursorsPanel( AdventureDataControl adventureData ) {
		
		super( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		JPanel mainPanel = new JPanel();
		
		//super( );
		this.adventureData = adventureData;
		this.cursorTypes = AdventureDataControl.getCursorTypes( ); 

		// Load the image for the delete content button
		Icon deleteContentIcon = new ImageIcon( "img/icons/deleteContent.png" );

		mainPanel.setLayout( new GridBagLayout( ) );
		//setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Cursors.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 2, 4, 2, 4 );

		// Create and insert a text with information about this panel
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridy = 0;
		c.weighty = 0;
		//resourcesPanel.add( informationTextPane, c );
		JTextPane informationTextPane = new JTextPane( );
		informationTextPane.setEditable( false );
		informationTextPane.setBackground( getBackground( ) );
		informationTextPane.setText( TextConstants.getText( "Cursors.Information" ) );
		JPanel informationPanel = new JPanel( );
		informationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "GeneralText.Information" ) ) );
		informationPanel.setLayout( new BorderLayout( ) );
		informationPanel.add( informationTextPane, BorderLayout.CENTER );
		mainPanel.add( informationPanel, c );


		// Create the fields
		int assetCount = cursorTypes.length;
		cursorFields = new JTextField[assetCount];
		viewButtons = new JButton[assetCount];

		// For every asset type of the resources, create and add a subpanel
		for( int i = 0; i < assetCount; i++ ) {
			
			// Create the panel and set the border
			JPanel assetPanel = new JPanel( );
			assetPanel.setLayout( new GridBagLayout( ) );
			assetPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Cursor."+cursorTypes[i]+".Description" ) ) );
			GridBagConstraints c2 = new GridBagConstraints( );
			c2.insets = new Insets( 2, 2, 2, 2 );
			c2.fill = GridBagConstraints.NONE;
			c2.weightx = 0;
			c2.weighty = 0;

			// Create the delete content button
			JButton deleteContentButton = new JButton( deleteContentIcon );
			deleteContentButton.addActionListener( new DeleteContentButtonListener( i ) );
			deleteContentButton.setPreferredSize( new Dimension( 20, 20 ) );
			deleteContentButton.setToolTipText( TextConstants.getText( "Cursors.DeleteCursor" ) );
			deleteContentButton.setEnabled( adventureData.isCursorTypeAllowed( i ) );
			assetPanel.add( deleteContentButton, c2 );
			

			// Create the text field and insert it
			cursorFields[i] = new JTextField( MAX_SPACE );
			if (adventureData.isCursorTypeAllowed( i )){
				if (adventureData.getCursorPath( i )!=null)
					cursorFields[i].setText( adventureData.getCursorPath( i ) );	
			}
			else
				cursorFields[i].setText( TextConstants.getText( "Cursors.TypeNotAllowed" ) );
 			
			cursorFields[i].setEditable( false );
			c2.gridx = 1;
			c2.fill = GridBagConstraints.HORIZONTAL;
			c2.weightx = 1;
			assetPanel.add( cursorFields[i], c2 );

			// Create the "Select" button and insert it
			JButton selectButton = new JButton( TextConstants.getText( "Cursors.Select" ) );
			selectButton.addActionListener( new ExamineButtonListener( i ) );
			selectButton.setEnabled( adventureData.isCursorTypeAllowed( i ) );
			c2.gridx = 2;
			c2.fill = GridBagConstraints.NONE;
			c2.weightx = 0;
			assetPanel.add( selectButton, c2 );

			// Create the "View" button and insert it
			viewButtons[i] = new JButton( TextConstants.getText( "Cursors.Preview" ) );
			viewButtons[i].setEnabled( adventureData.isCursorTypeAllowed( i )&&adventureData.getCursorPath( i )!=null );
			viewButtons[i].addActionListener( new ViewButtonListener( i ) );
			c2.gridx = 3;
			assetPanel.add( viewButtons[i], c2 );

			// Add the panel
			c.gridy++;
			//resourcesPanel.add( assetPanel, c );
			assetPanel.setToolTipText( TextConstants.getText( "Cursor."+cursorTypes[i]+".Tip" ) );
			mainPanel.add( assetPanel, c );
		}

		// Add a filler at the end
		c.gridy++;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		//add( new JFiller( ), c );
		//add( new JFiller( ), c );

		// TODO Parche, arreglar
		//resourcesPanel.setPreferredSize( new Dimension( 0, assetCount * 80 ) );
		//setPreferredSize( new Dimension( 0, assetCount * 80 ) );
		//setMaximumSize( new Dimension( 0, assetCount * 80 ) );

		// Insert the panel into the scroll
		setViewportView( mainPanel );
	}


	/**
	 * This class is the listener for the "Delete content" buttons on the panels.
	 */
	private class DeleteContentButtonListener implements ActionListener {

		/**
		 * Index of the asset.
		 */
		private int assetIndex;

		/**
		 * Constructor.
		 * 
		 * @param assetIndex
		 *            Index of the asset
		 */
		public DeleteContentButtonListener( int assetIndex ) {
			this.assetIndex = assetIndex;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			adventureData.deleteCursor( assetIndex );
			cursorFields[assetIndex].setText( null );
			viewButtons[assetIndex].setEnabled( false );
		}
	}

	/**
	 * This class is the listener for the "Examine" buttons on the panels.
	 */
	private class ExamineButtonListener implements ActionListener {

		/**
		 * Index of the asset.
		 */
		private int assetIndex;

		/**
		 * Constructor.
		 * 
		 * @param assetIndex
		 *            Index of the asset
		 */
		public ExamineButtonListener( int assetIndex ) {
			this.assetIndex = assetIndex;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			adventureData.editCursorPath( assetIndex );
			if (adventureData.isCursorTypeAllowed( assetIndex )){
				cursorFields[assetIndex].setText( adventureData.getCursorPath( assetIndex ) );
				viewButtons[assetIndex].setEnabled( adventureData.getCursorPath( assetIndex ) != null );
			}
		}
	}

	/**
	 * This class is the listener for the "View" buttons on the panels.
	 */
	private class ViewButtonListener implements ActionListener {

		/**
		 * Index of the asset.
		 */
		private int assetIndex;

		/**
		 * Constructor.
		 * 
		 * @param assetIndex
		 *            Index of the asset
		 */
		public ViewButtonListener( int assetIndex ) {
			this.assetIndex = assetIndex;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent arg0 ) {

			String assetPath = adventureData.getCursorPath( assetIndex );
			
			new ImageDialog( assetPath );
		}
	}

}
