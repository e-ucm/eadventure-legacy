package es.eucm.eadventure.editor.gui.elementpanels.book;

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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DocumentationChangeListener;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.displaydialogs.StyledBookDialog;
import es.eucm.eadventure.editor.gui.elementpanels.general.LooksPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.BookImagePanel;
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.ImagePanel;

public class BookDocAppPanel extends JPanel implements Updateable{

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
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		JPanel documentationPanel = new JPanel( );
		documentationPanel.setLayout( new GridLayout( ) );
		documentationTextArea = new JTextArea( bookDataControl.getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationChangeListener( documentationTextArea, (Documented) bookDataControl.getContent() ) );
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Book.Documentation" ) ) );
		add( documentationPanel, c );

		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 3;

		// Create the looksPanel
		if (bookDataControl.getType( ) == Book.TYPE_PARAGRAPHS){
			this.bookLooks = new BookLooksPanel( this.bookDataControl );
			this.add( bookLooks, c );
		} else {
			this.bookLooks = new StyledBookLooksPanel( this.bookDataControl );
			this.add( bookLooks, c );
		}
	}

	private class BookLooksPanel extends LooksPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
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

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private StyledBookDialog dialog;
		
		private JButton previewButton;
		
		public StyledBookLooksPanel( DataControlWithResources control ) {
			super( control );
		}

		@Override
		protected void createPreview( ) {
			
			JPanel previewPanel = new JPanel();
			previewPanel.setLayout( new BorderLayout() );
			previewPanel.add( new JLabel(TextConstants.getText("BookPages.PreviewDescription") ), BorderLayout.NORTH );
			
			previewButton = new JButton ("Preview");
			previewButton.addActionListener( new ActionListener(){
				public void actionPerformed( ActionEvent e ) {
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

	public boolean updateFields() {
		bookLooks.updateResources();
		bookLooks.updatePreview();
		bookLooks.repaint();
		
		documentationTextArea.setText(this.bookDataControl.getDocumentation());
		return true;
	}

}
