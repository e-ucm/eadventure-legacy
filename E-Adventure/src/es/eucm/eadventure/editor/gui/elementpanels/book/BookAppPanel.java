package es.eucm.eadventure.editor.gui.elementpanels.book;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.LooksPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.ImagePanel;

public class BookAppPanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Controller of the book.
	 */
	private BookDataControl bookDataControl;

	private LooksPanel bookLooks;

	/**
	 * Constructor.
	 * 
	 * @param bookDataControl
	 *            Book controller
	 */
	public BookAppPanel( BookDataControl bookDataControl ) {

		// Set the controller
		this.bookDataControl = bookDataControl;

		// Create the list of resources
		String[] resourcesArray = new String[bookDataControl.getResourcesCount( )];
		for( int i = 0; i < bookDataControl.getResourcesCount( ); i++ )
			resourcesArray[i] = TextConstants.getText( "ResourcesList.ResourcesBlockNumber" ) + ( i + 1 );

		// Set the layout
		setLayout( new GridBagLayout( ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 3;

		this.bookLooks = new BookLooksPanel( this.bookDataControl );
		this.add( bookLooks, c );
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
			String bookImagePath = bookDataControl.getPreviewImage( );
			JPanel previewPanel = new JPanel();
			previewPanel.setLayout( new BorderLayout() );
			imagePanel = new ImagePanel( bookImagePath );
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

}
