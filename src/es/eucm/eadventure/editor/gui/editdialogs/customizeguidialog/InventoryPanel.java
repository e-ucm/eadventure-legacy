package es.eucm.eadventure.editor.gui.editdialogs.customizeguidialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.AdventureDataControl;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.displaydialogs.ImageDialog;

/**
 * 
 * @author Javier Torrente
 */
public class InventoryPanel extends JScrollPane implements Updateable{

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	private static final int MAX_SPACE = 0;

	/**
	 * The list of images
	 */
	private String[] arrowTypes;

	private AdventureDataControl adventureData;

	/**
	 * The text fields with the cursor paths.
	 */
	private JTextField[] arrowFields;

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
	public InventoryPanel( AdventureDataControl adventureData ) {
		super( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		JPanel mainPanel = new JPanel();

		this.adventureData = adventureData;

		this.arrowTypes = DescriptorData.getArrowTypes();

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
		informationTextPane.setText( TextConstants.getText( "Inventory.Information" ) );
		JPanel informationPanel = new JPanel( );
		informationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "GeneralText.Information" ) ) );
		informationPanel.setLayout( new BorderLayout( ) );
		informationPanel.add( informationTextPane, BorderLayout.CENTER );
		mainPanel.add( informationPanel, c );


		// Create the fields
		int assetCount = arrowTypes.length;
		arrowFields = new JTextField[assetCount];
		viewButtons = new JButton[assetCount];


		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints c3 = new GridBagConstraints();
		c3.insets = new Insets( 2, 4, 2, 4 );
		c3.fill = GridBagConstraints.HORIZONTAL;
		c3.weightx = 1;
		c3.gridy = 0;
		c3.weighty = 0;

		for( int i = 0; i < arrowTypes.length; i++ ) {
			// Create the panel and set the border
			JPanel assetPanel = new JPanel( );
			assetPanel.setLayout( new GridBagLayout( ) );
			assetPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Inventory."+arrowTypes[i]+".Description" ) ) );
			GridBagConstraints c2 = new GridBagConstraints( );
			c2.insets = new Insets( 2, 2, 2, 2 );
			c2.fill = GridBagConstraints.NONE;
			c2.weightx = 0;
			c2.weighty = 0;

			// Create the delete content button
			JButton deleteContentButton = new JButton( deleteContentIcon );
			deleteContentButton.addActionListener( new DeleteContentButtonListener( i ) );
			deleteContentButton.setPreferredSize( new Dimension( 20, 20 ) );
			deleteContentButton.setToolTipText( TextConstants.getText( "Buttons.DeleteButton" ) );
			assetPanel.add( deleteContentButton, c2 );


			// Create the text field and insert it
			arrowFields[i] = new JTextField( MAX_SPACE );
			if (adventureData.getArrowPath(arrowTypes[i])!=null)
				arrowFields[i].setText( adventureData.getArrowPath( arrowTypes[i] ) );	

			arrowFields[i].setEditable( false );
			c2.gridx = 1;
			c2.fill = GridBagConstraints.HORIZONTAL;
			c2.weightx = 1;
			assetPanel.add( arrowFields[i], c2 );

			// Create the "Select" button and insert it
			JButton selectButton = new JButton( TextConstants.getText( "Inventory.Select" ) );
			selectButton.addActionListener( new ExamineButtonListener( i ) );
			c2.gridx = 2;
			c2.fill = GridBagConstraints.NONE;
			c2.weightx = 0;
			assetPanel.add( selectButton, c2 );

			// Create the "View" button and insert it
			viewButtons[i] = new JButton( TextConstants.getText( "Inventory.Preview" ) );
			viewButtons[i].setEnabled( adventureData.getArrowPath( arrowTypes[i])!=null );
			viewButtons[i].addActionListener( new ViewButtonListener( i ) );
			c2.gridx = 3;
			assetPanel.add( viewButtons[i], c2 );


			// Add the panel
			//resourcesPanel.add( assetPanel, c );
			assetPanel.setToolTipText( TextConstants.getText( "Inventory."+ arrowTypes[i] +".Tip" ) );
			buttonPanel.add(assetPanel, c3);
			c3.gridy++;

		}
		c.gridy++;
		mainPanel.add( buttonPanel, c );

		// Add a filler at the end
		c.gridy++;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;

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
			adventureData.deleteArrow( arrowTypes[assetIndex] );
			arrowFields[assetIndex].setText( null );
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
			adventureData.editArrowPath(arrowTypes[assetIndex]);
			if (adventureData.getArrowPath( arrowTypes[assetIndex] ) != null){
				arrowFields[assetIndex].setText( adventureData.getArrowPath( arrowTypes[assetIndex] ) );
				viewButtons[assetIndex].setEnabled( adventureData.getArrowPath( arrowTypes[assetIndex] ) != null );
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
			String assetPath = adventureData.getArrowPath( arrowTypes[assetIndex] );
			new ImageDialog( assetPath );
		}
	}

	public boolean updateFields() {
		// For every cursor, update the cursorPath field
		int assetCount = arrowTypes.length;
		for( int i = 0; i < assetCount; i++ ) {
			if (adventureData.getArrowPath( arrowTypes[i] )!=null){
				arrowFields[i].setText( adventureData.getArrowPath( arrowTypes[i] ) );
				viewButtons[i].setEnabled( true );
			} else {
				arrowFields[i].setText( null );
				viewButtons[i].setEnabled( false );
			}
				
		}
		return true;
	}
	
}
