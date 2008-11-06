package es.eucm.eadventure.adventureeditor.gui.elementpanels.book;

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
import javax.swing.JTextField;
import javax.swing.JTextPane;

import es.eucm.eadventure.adventureeditor.control.controllers.book.BookParagraphDataControl;
import es.eucm.eadventure.adventureeditor.gui.TextConstants;
import es.eucm.eadventure.adventureeditor.gui.otherpanels.imagepanels.ImagePanel;

public class ImageBookParagraphPanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Controller of the book paragraph.
	 */
	private BookParagraphDataControl bookParagraphDataControl;

	/**
	 * Text field containing the path.
	 */
	private JTextField pathTextField;

	/**
	 * Panel showing the selected image.
	 */
	private ImagePanel imagePanel;

	/**
	 * Constructor.
	 * 
	 * @param bookParagraphDataControl
	 *            Book paragraph controller
	 */
	public ImageBookParagraphPanel( BookParagraphDataControl bookParagraphDataControl, int position ) {

		// Set the controller
		this.bookParagraphDataControl = bookParagraphDataControl;

		// Load the image for the delete content button
		Icon deleteContentIcon = new ImageIcon( "img/icons/deleteContent.png" );

		// Set the layout
		setLayout( new GridBagLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "BookParagraph.TitlePanel", new String[]{"#"+(position+1), TextConstants.getElementName( bookParagraphDataControl.getType( ) )} ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );
		
		// Create the info panel
		JTextPane informationTextPane = new JTextPane( );
		informationTextPane.setEditable( false );
		informationTextPane.setBackground( getBackground( ) );
		informationTextPane.setText( TextConstants.getText( "BookParagraph.DescriptionPanel" ) );
		c.fill = GridBagConstraints.HORIZONTAL;
		add( informationTextPane, c );

		// Create the panel and set the border
		JPanel imagePathPanel = new JPanel( );
		imagePathPanel.setLayout( new GridBagLayout( ) );
		imagePathPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "BookParagraph.ImagePath" ) ) );
		GridBagConstraints c2 = new GridBagConstraints( );
		c2.insets = new Insets( 4, 4, 4, 4 );
		c2.fill = GridBagConstraints.NONE;
		c2.weightx = 0;
		c2.weighty = 0;

		// Create the delete content button
		JButton deleteContentButton = new JButton( deleteContentIcon );
		deleteContentButton.addActionListener( new DeleteContentButtonListener( ) );
		deleteContentButton.setPreferredSize( new Dimension( 20, 20 ) );
		deleteContentButton.setToolTipText( TextConstants.getText( "Resources.DeleteAsset" ) );
		imagePathPanel.add( deleteContentButton, c2 );

		// Create the text field and insert it
		c2.gridx = 1;
		c2.fill = GridBagConstraints.HORIZONTAL;
		c2.weightx = 1;
		pathTextField = new JTextField( bookParagraphDataControl.getParagraphContent( ) );
		pathTextField.setEditable( false );
		imagePathPanel.add( pathTextField, c2 );

		// Create the "Select" button and insert it
		c2.gridx = 2;
		c2.fill = GridBagConstraints.NONE;
		c2.weightx = 0;
		JButton selectButton = new JButton( TextConstants.getText( "Resources.Select" ) );
		selectButton.addActionListener( new ExamineButtonListener( ) );
		imagePathPanel.add( selectButton, c2 );

		// Add the panel to the principal panel
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy=1;
		c.weightx = 1;
		add( imagePathPanel, c );

		// Create and add the image panel
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1;
		imagePanel = new ImagePanel( bookParagraphDataControl.getParagraphContent( ) );
		imagePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "BookParagraph.Preview" ) ) );
		add( imagePanel, c );
	}

	/**
	 * Listener for the delete content button.
	 */
	private class DeleteContentButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			// Delete the current path and disable the view button
			pathTextField.setText( null );
			bookParagraphDataControl.deleteImageParagraphContent( );
			imagePanel.removeImage( );
		}
	}

	/**
	 * Listener for the examine button.
	 */
	private class ExamineButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			// Ask the user for an image
			//bookParagraphDataControl.setImageParagraphContent( );
			bookParagraphDataControl.editImagePath( );
			pathTextField.setText( bookParagraphDataControl.getParagraphContent( ) );
			if (bookParagraphDataControl.getParagraphContent( )!=null)
				imagePanel.loadImage( bookParagraphDataControl.getParagraphContent( ) );
			else
				imagePanel.removeImage( );
		}
	}
}
