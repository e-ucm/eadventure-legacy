package es.eucm.eadventure.editor.control.controllers.book;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AdventureDataControl;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.item.ItemDataControl;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;
import es.eucm.eadventure.editor.gui.editdialogs.BookTypesDialog;
import es.eucm.eadventure.editor.gui.editdialogs.GUIStylesDialog;

public class BooksListDataControl extends DataControl {

	/**
	 * List of books.
	 */
	private List<Book> booksList;

	/**
	 * Book controllers.
	 */
	private List<BookDataControl> booksDataControlList;

	/**
	 * Constructor.
	 * 
	 * @param booksList
	 *            List of books
	 */
	public BooksListDataControl( List<Book> booksList ) {
		this.booksList = booksList;

		// Create the subcontrollers
		booksDataControlList = new ArrayList<BookDataControl>( );
		for( Book book : booksList )
			booksDataControlList.add( new BookDataControl( book ) );
	}

	/**
	 * Returns the list of book controllers.
	 * 
	 * @return Book controllers
	 */
	public List<BookDataControl> getBooks( ) {
		return booksDataControlList;
	}

	/**
	 * Returns the last book controller of the list.
	 * 
	 * @return Last book controller
	 */
	public BookDataControl getLastBook( ) {
		return booksDataControlList.get( booksDataControlList.size( ) - 1 );
	}

	/**
	 * Returns the info of the books contained in the list.
	 * 
	 * @return Array with the information of the books. It contains the identifier of each book, and the number of
	 *         paragraphs
	 */
	public String[][] getBooksInfo( ) {
		String[][] booksInfo = null;

		// Create the list for the books
		booksInfo = new String[booksList.size( )][2];

		// Fill the array with the info
		for( int i = 0; i < booksList.size( ); i++ ) {
			Book book = booksList.get( i );
			booksInfo[i][0] = book.getId( );
			if (book.getType( ) == Book.TYPE_PARAGRAPHS)
				booksInfo[i][1] = TextConstants.getText( "BooksList.ParagraphsNumber", String.valueOf( book.getParagraphs( ).size( ) ) );
			else if (book.getType( ) == Book.TYPE_PAGES)
				booksInfo[i][1] = TextConstants.getText( "BooksList.PagesNumber", String.valueOf( book.getPageURLs( ).size( ) ) );
		}

		return booksInfo;
	}

	@Override
	public Object getContent( ) {
		return booksList;
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] { Controller.BOOK };
	}

	@Override
	public boolean canAddElement( int type ) {
		// It can always add new scenes
		return type == Controller.BOOK;
	}

	@Override
	public boolean canBeDeleted( ) {
		return false;
	}

	@Override
	public boolean canBeMoved( ) {
		return false;
	}

	@Override
	public boolean canBeRenamed( ) {
		return false;
	}

	@Override
	public boolean addElement( int type ) {
		boolean elementAdded = false;

		if( type == Controller.BOOK ) {
			// Show a dialog asking for the type (PAGES/PARAGRAPHS)
			int bookType = -1;
			BookTypesDialog bookTypesDialog = new BookTypesDialog( Book.TYPE_PARAGRAPHS );

			// If the new GUI style is different from the current, and valid, change the value
			int optionSelected = bookTypesDialog.getOptionSelected( );
			if( optionSelected != -1 ) {
				bookType = optionSelected;

				// Show a dialog asking for the book id
				String bookId = controller.showInputDialog( TextConstants.getText( "Operation.AddBookTitle" ), TextConstants.getText( "Operation.AddBookMessage" ), TextConstants.getText( "Operation.AddBookDefaultValue" ) );

				// If some value was typed and the identifier is valid
				if( bookId != null && controller.isElementIdValid( bookId ) ) {
					// Add thew new book
					Book newBook = new Book( bookId );
					newBook.setType( bookType );
					
					// Set default background
					Resources resources = new Resources();
					resources.addAsset( "background", AssetsController.ASSET_DEFAULT_BOOK_IMAGE );
					newBook.addResources( resources );
					booksList.add( newBook );
					
					BookDataControl newDataControl = new BookDataControl( newBook );
					booksDataControlList.add( newDataControl );
					controller.getIdentifierSummary( ).addBookId( bookId );
					controller.dataModified( );
					elementAdded = true;
				}

			}
			
		}

		return elementAdded;
	}

	@Override
	public boolean deleteElement( DataControl dataControl ) {
		boolean elementDeleted = false;
		String bookId = ( (BookDataControl) dataControl ).getId( );
		String references = String.valueOf( controller.countIdentifierReferences( bookId ) );

		// Ask for confirmation
		if( controller.showStrictConfirmDialog( TextConstants.getText( "Operation.DeleteElementTitle" ), TextConstants.getText( "Operation.DeleteElementWarning", new String[] { bookId, references } ) ) ) {
			if( booksList.remove( dataControl.getContent( ) ) ) {
				booksDataControlList.remove( dataControl );
				controller.deleteIdentifierReferences( bookId );
				controller.getIdentifierSummary( ).deleteBookId( bookId );
				controller.dataModified( );
				elementDeleted = true;
			}
		}

		return elementDeleted;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = booksList.indexOf( dataControl.getContent( ) );

		if( elementIndex > 0 ) {
			booksList.add( elementIndex - 1, booksList.remove( elementIndex ) );
			booksDataControlList.add( elementIndex - 1, booksDataControlList.remove( elementIndex ) );
			controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = booksList.indexOf( dataControl.getContent( ) );

		if( elementIndex < booksList.size( ) - 1 ) {
			booksList.add( elementIndex + 1, booksList.remove( elementIndex ) );
			booksDataControlList.add( elementIndex + 1, booksDataControlList.remove( elementIndex ) );
			controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean renameElement( ) {
		return false;
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
	// Do nothing
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean valid = true;

		// Update the current path
		currentPath += " >> " + TextConstants.getElementName( Controller.BOOKS_LIST );

		// Iterate through the books
		for( BookDataControl bookDataControl : booksDataControlList ) {
			String bookPath = currentPath + " >> " + bookDataControl.getId( );
			valid &= bookDataControl.isValid( bookPath, incidences );
		}

		return valid;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		// Iterate through the books
		for( BookDataControl bookDataControl : booksDataControlList )
			count += bookDataControl.countAssetReferences( assetPath );

		return count;
	}
	
	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		// Iterate through the books
		for( BookDataControl bookDataControl : booksDataControlList )
			bookDataControl.getAssetReferences( assetPaths, assetTypes );
	}


	@Override
	public void deleteAssetReferences( String assetPath ) {
		// Iterate through the books
		for( BookDataControl bookDataControl : booksDataControlList )
			bookDataControl.deleteAssetReferences( assetPath );
	}

	@Override
	public int countIdentifierReferences( String id ) {
		return 0;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
	// Do nothing
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
	// Do nothing
	}

	@Override
	public boolean canBeDuplicated( ) {
		return false;
	}
}
