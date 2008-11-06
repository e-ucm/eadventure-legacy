package es.eucm.eadventure.adventureeditor.gui.elementpanels.book;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.adventureeditor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.adventureeditor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.adventureeditor.data.chapterdata.book.Book;
import es.eucm.eadventure.adventureeditor.gui.TextConstants;
import es.eucm.eadventure.adventureeditor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.adventureeditor.gui.displaydialogs.StyledBookDialog;
import es.eucm.eadventure.adventureeditor.gui.elementpanels.general.LooksPanel;
import es.eucm.eadventure.adventureeditor.gui.otherpanels.imagepanels.BookImagePanel;
import es.eucm.eadventure.adventureeditor.gui.otherpanels.imagepanels.ImagePanel;

public class BookDocAppPanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Controller of the book.
	 */
	private BookDataControl bookDataControl;

	/**
	 * Text area for the documentation.
	 */
	private JTextArea documentationTextArea;

	private LooksPanel bookLooks;

	/**
	 * Constructor.
	 * 
	 * @param bookDataControl
	 *            Book controller
	 */
	public BookDocAppPanel( BookDataControl bookDataControl ) {

		// Set the controller
		this.bookDataControl = bookDataControl;

		// Create the list of resources
		String[] resourcesArray = new String[bookDataControl.getResourcesCount( )];
		for( int i = 0; i < bookDataControl.getResourcesCount( ); i++ )
			resourcesArray[i] = TextConstants.getText( "ResourcesList.ResourcesBlockNumber" ) + ( i + 1 );

		// Set the layout
		setLayout( new GridBagLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Book.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the text area for the documentation
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		JPanel documentationPanel = new JPanel( );
		documentationPanel.setLayout( new GridLayout( ) );
		documentationTextArea = new JTextArea( bookDataControl.getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationTextAreaChangesListener( ) );
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Book.Documentation" ) ) );
		add( documentationPanel, c );

		// Create the looksPanel
		if (bookDataControl.getType( ) == Book.TYPE_PARAGRAPHS){
			this.bookLooks = new BookLooksPanel( this.bookDataControl );
				
			// Add the preview of the player
			c.gridy = 1;
			c.fill = GridBagConstraints.BOTH;
			c.weightx = 1;
			c.weighty = 1;
			this.add( bookLooks, c );
		} else {
			this.bookLooks = new StyledBookLooksPanel( this.bookDataControl );
			
			// Add the preview of the player
			c.gridy = 1;
			c.fill = GridBagConstraints.BOTH;
			c.weightx = 1;
			c.weighty = 1;
			this.add( bookLooks, c );
			
		}

	}

	/**
	 * Listener for the text area. It checks the value of the area and updates the documentation.
	 */
	private class DocumentationTextAreaChangesListener implements DocumentListener {

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
			bookDataControl.setDocumentation( documentationTextArea.getText( ) );
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
		 */
		public void removeUpdate( DocumentEvent arg0 ) {
			// Set the new content
			bookDataControl.setDocumentation( documentationTextArea.getText( ) );
		}
	}

	private class BookLooksPanel extends LooksPanel {

		/**
		 * Preview image panel.
		 */
		private ImagePanel imagePanel;

		public BookLooksPanel( DataControlWithResources control ) {
			super( control );
		}

		@Override
		protected void createPreview( ) {

			// Take the path to the current image of the book
			String bookImagePath = bookDataControl.getPreviewImage( );

			JPanel previewPanel = new JPanel();
			previewPanel.setLayout( new BorderLayout() );
			previewPanel.add( new JLabel(TextConstants.getText("BookParagraphs.PreviewDescription") ), BorderLayout.NORTH );
			imagePanel = new BookImagePanel( bookImagePath, bookDataControl.getBookParagraphsList( ) );
			previewPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Book.Preview" ) ) );
			previewPanel.add( imagePanel, BorderLayout.CENTER );
			lookPanel.add( previewPanel, cLook );
			// TODO Parche, arreglar
			lookPanel.setPreferredSize( new Dimension( 0, 90 ) );

		}

		@Override
		public void updatePreview( ) {
			imagePanel.loadImage( bookDataControl.getPreviewImage( ) );
			imagePanel.repaint( );
			getParent( ).getParent( ).repaint( );

		}

		public void updateResources( ) {
			super.updateResources( );
			getParent( ).getParent( ).repaint( );
		}

	}
	
	private class StyledBookLooksPanel extends LooksPanel {

		private StyledBookDialog dialog;
		
		private JButton previewButton;
		
		public StyledBookLooksPanel( DataControlWithResources control ) {
			super( control );
		}

		@Override
		protected void createPreview( ) {

			// Take the path to the current image of the book
			String bookImagePath = bookDataControl.getPreviewImage( );
			
			JPanel previewPanel = new JPanel();
			previewPanel.setLayout( new BorderLayout() );
			previewPanel.add( new JLabel(TextConstants.getText("BookPages.PreviewDescription") ), BorderLayout.NORTH );
			
			previewButton = new JButton ("Preview");
			previewButton.addActionListener( new ActionListener(){
				public void actionPerformed( ActionEvent e ) {
					if (dialog==null)
						dialog = new StyledBookDialog(bookDataControl);
					dialog.setVisible( true );
					dialog.updatePreview( );
				}
			});
			JPanel buttonPanel = new JPanel();
			buttonPanel.add( previewButton );
			previewPanel.add( buttonPanel, BorderLayout.CENTER );
			//imagePanel = new BookImagePanel( bookImagePath, bookDataControl.getBookParagraphsList( ) );
			//previewPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Book.Preview" ) ) );
			//previewPanel.add( imagePanel, BorderLayout.CENTER );
			//lookPanel.add( previewPanel, cLook );
			lookPanel.add( previewPanel, cLook );
			// TODO Parche, arreglar
			lookPanel.setPreferredSize( new Dimension( 0, 90 ) );

		}

		@Override
		public void updatePreview( ) {
			//imagePanel.loadImage( bookDataControl.getPreviewImage( ) );
			//imagePanel.repaint( );
			if (dialog!=null)
				dialog.updatePreview( );
			getParent( ).getParent( ).repaint( );

		}

		public void updateResources( ) {
			super.updateResources( );
			if (dialog!=null)
				dialog.updatePreview( );
			getParent( ).getParent( ).repaint( );
		}

	}

}
