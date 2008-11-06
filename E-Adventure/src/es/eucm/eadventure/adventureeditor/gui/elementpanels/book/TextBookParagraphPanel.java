package es.eucm.eadventure.adventureeditor.gui.elementpanels.book;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import es.eucm.eadventure.adventureeditor.control.Controller;
import es.eucm.eadventure.adventureeditor.control.controllers.book.BookParagraphDataControl;
import es.eucm.eadventure.adventureeditor.gui.TextConstants;
import es.eucm.eadventure.adventureeditor.gui.auxiliar.components.JFiller;

public class TextBookParagraphPanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Controller of the book paragraph.
	 */
	private BookParagraphDataControl bookParagraphDataControl;

	/**
	 * Text area for the content of the paragraph.
	 */
	private JTextComponent contentText;

	/**
	 * Constructor.
	 * 
	 * @param bookParagraphDataControl
	 *            Book paragraph controller
	 */
	public TextBookParagraphPanel( BookParagraphDataControl bookParagraphDataControl, int position ) {

		// Set the controller
		this.bookParagraphDataControl = bookParagraphDataControl;

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

		// If it is a text or bullet paragrah, set a text area
		if( bookParagraphDataControl.getType( ) == Controller.BOOK_TEXT_PARAGRAPH || bookParagraphDataControl.getType( ) == Controller.BOOK_BULLET_PARAGRAPH ) {
			// Create the label for the content
			c.fill = GridBagConstraints.BOTH;
			c.gridy=1;
			c.weightx = 1;
			c.weighty = 1;
			JPanel contentPanel = new JPanel( );
			contentPanel.setLayout( new GridLayout( ) );
			JTextArea textArea = new JTextArea( bookParagraphDataControl.getParagraphContent( ) );
			textArea.setLineWrap( true );
			textArea.setWrapStyleWord( true );
			textArea.getDocument( ).addDocumentListener( new TextAreaChangesListener( ) );
			contentPanel.add( new JScrollPane( textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );

			// Add the title
			if( bookParagraphDataControl.getType( ) == Controller.BOOK_TEXT_PARAGRAPH )
				contentPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "BookParagraph.Text" ) ) );
			else if( bookParagraphDataControl.getType( ) == Controller.BOOK_BULLET_PARAGRAPH )
				contentPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "BookParagraph.Bullet" ) ) );
			add( contentPanel, c );

			// Set the content text component
			contentText = textArea;
		}

		// If it is a text or bullet paragrah, set a text area
		else if( bookParagraphDataControl.getType( ) == Controller.BOOK_TITLE_PARAGRAPH ) {
			// Create the label for the content
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			c.gridy=1;
			JPanel contentPanel = new JPanel( );
			contentPanel.setLayout( new GridLayout( ) );
			JTextField textField = new JTextField( bookParagraphDataControl.getParagraphContent( ) );
			textField.getDocument( ).addDocumentListener( new TextAreaChangesListener( ) );
			contentPanel.add( textField );
			contentPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "BookParagraph.Title" ) ) );
			add( contentPanel, c );

			// Add a filler
			c.fill = GridBagConstraints.BOTH;
			c.weighty = 1;
			c.gridy = 1;
			add( new JFiller( ), c );

			// Set the content text component
			contentText = textField;
		}
	}

	/**
	 * Listener for the text fields. It checks the values from the fields and updates the data.
	 */
	private class TextAreaChangesListener implements DocumentListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
		 */
		public void changedUpdate( DocumentEvent arg0 ) {
		// Do nothing
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
		 */
		public void insertUpdate( DocumentEvent arg0 ) {
			// Set the new content
			bookParagraphDataControl.setParagraphTextContent( contentText.getText( ) );
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
		 */
		public void removeUpdate( DocumentEvent arg0 ) {
			// Set the new content
			bookParagraphDataControl.setParagraphTextContent( contentText.getText( ) );
		}
	}
}
