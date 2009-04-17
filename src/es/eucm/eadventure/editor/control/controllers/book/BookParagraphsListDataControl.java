package es.eucm.eadventure.editor.control.controllers.book;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.book.BookParagraph;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class BookParagraphsListDataControl extends DataControl {

	/**
	 * List of book paragraphs.
	 */
	private List<BookParagraph> bookParagraphsList;

	/**
	 * Book paragraph controllers.
	 */
	private List<BookParagraphDataControl> bookParagraphsDataControlList;

	/**
	 * Constructor.
	 * 
	 * @param bookParagraphsList
	 *            List of book paragraphs
	 */
	public BookParagraphsListDataControl( List<BookParagraph> bookParagraphsList ) {
		this.bookParagraphsList = bookParagraphsList;

		// Create the subcontrollers
		bookParagraphsDataControlList = new ArrayList<BookParagraphDataControl>( );
		for( BookParagraph bookParagraph : bookParagraphsList )
			bookParagraphsDataControlList.add( new BookParagraphDataControl( bookParagraph ) );
	}

	/**
	 * Return the list of book paragraph controllers.
	 * 
	 * @return Book paragraph controllers
	 */
	public List<BookParagraphDataControl> getBookParagraphs( ) {
		return bookParagraphsDataControlList;
	}

	/**
	 * Returns the last book paragraph controller of the list.
	 * 
	 * @return Last book paragraph
	 */
	public BookParagraphDataControl getLastBookParagraph( ) {
		return bookParagraphsDataControlList.get( bookParagraphsDataControlList.size( ) - 1 );
	}

	/**
	 * Returns the info of the book paragraphs contained in the list.
	 * 
	 * @return Array with the information of the book paragraphs. It contains the number of each paragraph, the type of
	 *         the paragraph, and the word count if applicable
	 */
	public String[][] getBookParagraphsInfo( ) {
		String[][] bookParagraphsInfo = null;

		// Create the list for the book paragraphs
		bookParagraphsInfo = new String[bookParagraphsList.size( )][3];

		// Fill the array with the info
		for( int i = 0; i < bookParagraphsList.size( ); i++ ) {
			BookParagraph bookParagraph = bookParagraphsList.get( i );

			if( bookParagraph.getType( ) == BookParagraph.TEXT )
				bookParagraphsInfo[i][0] = TextConstants.getText( "BookParagraphsList.TextParagraph", String.valueOf( i + 1 ) );
			else if( bookParagraph.getType( ) == BookParagraph.TITLE )
				bookParagraphsInfo[i][0] = TextConstants.getText( "BookParagraphsList.TitleParagraph", String.valueOf( i + 1 ) );
			else if( bookParagraph.getType( ) == BookParagraph.BULLET )
				bookParagraphsInfo[i][0] = TextConstants.getText( "BookParagraphsList.BulletParagraph", String.valueOf( i + 1 ) );
			else if( bookParagraph.getType( ) == BookParagraph.IMAGE )
				bookParagraphsInfo[i][0] = TextConstants.getText( "BookParagraphsList.ImageParagraph", String.valueOf( i + 1 ) );

			if( bookParagraph.getType( ) != BookParagraph.IMAGE )
				bookParagraphsInfo[i][1] = TextConstants.getText( "BookParagraphsList.WordCount", String.valueOf( bookParagraph.getContent( ).split( " " ).length ) );
			else
				bookParagraphsInfo[i][1] = TextConstants.getText( "BookParagraphsList.NotApplicable" );
		}

		return bookParagraphsInfo;
	}

	@Override
	public Object getContent( ) {
		return bookParagraphsList;
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] { Controller.BOOK_TITLE_PARAGRAPH, Controller.BOOK_TEXT_PARAGRAPH, Controller.BOOK_BULLET_PARAGRAPH, Controller.BOOK_IMAGE_PARAGRAPH };
	}

	@Override
	public boolean canAddElement( int type ) {
		// It can always add new paragraphs
		return type == Controller.BOOK_TITLE_PARAGRAPH || type == Controller.BOOK_TEXT_PARAGRAPH || type == Controller.BOOK_BULLET_PARAGRAPH || type == Controller.BOOK_IMAGE_PARAGRAPH;
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
	public boolean addElement( int type , String id) {
		BookParagraph newBookParagraph = null;

		if( type == Controller.BOOK_TITLE_PARAGRAPH )
			newBookParagraph = new BookParagraph( BookParagraph.TITLE );

		else if( type == Controller.BOOK_TEXT_PARAGRAPH )
			newBookParagraph = new BookParagraph( BookParagraph.TEXT );

		else if( type == Controller.BOOK_BULLET_PARAGRAPH )
			newBookParagraph = new BookParagraph( BookParagraph.BULLET );

		else if( type == Controller.BOOK_IMAGE_PARAGRAPH )
			newBookParagraph = new BookParagraph( BookParagraph.IMAGE );

		// If a paragraph was added, add the controller to the list
		if( newBookParagraph != null ) {
			bookParagraphsList.add( newBookParagraph );
			bookParagraphsDataControlList.add( new BookParagraphDataControl( newBookParagraph ) );
			//controller.dataModified( );
		}

		return newBookParagraph != null;
	}

	@Override
	public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {
		boolean elementDeleted = false;

		if( bookParagraphsList.remove( dataControl.getContent( ) ) ) {
			bookParagraphsDataControlList.remove( dataControl );
			elementDeleted = true;
		}

		return elementDeleted;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = bookParagraphsList.indexOf( dataControl.getContent( ) );

		if( elementIndex > 0 ) {
			bookParagraphsList.add( elementIndex - 1, bookParagraphsList.remove( elementIndex ) );
			bookParagraphsDataControlList.add( elementIndex - 1, bookParagraphsDataControlList.remove( elementIndex ) );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = bookParagraphsList.indexOf( dataControl.getContent( ) );

		if( elementIndex < bookParagraphsList.size( ) - 1 ) {
			bookParagraphsList.add( elementIndex + 1, bookParagraphsList.remove( elementIndex ) );
			bookParagraphsDataControlList.add( elementIndex + 1, bookParagraphsDataControlList.remove( elementIndex ) );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public String renameElement(String name ) {
		return null;
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
	// Do nothing
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean valid = true;

		// Iterate through the paragraphs
		for( int i = 0; i < bookParagraphsDataControlList.size( ); i++ ) {
			String bookParagraphPath = currentPath + " >> " + TextConstants.getText( "Element.BookParagraph" ) + " #" + ( i + 1 ) + " (" + TextConstants.getElementName( bookParagraphsDataControlList.get( i ).getType( ) ) + ")";
			valid &= bookParagraphsDataControlList.get( i ).isValid( bookParagraphPath, incidences );
		}

		return valid;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		// Spread the call to the paragraphs
		for( BookParagraphDataControl bookParagraphDataControl : bookParagraphsDataControlList )
			count += bookParagraphDataControl.countAssetReferences( assetPath );

		return count;
	}

	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		// Spread the call to the paragraphs
		for( BookParagraphDataControl bookParagraphDataControl : bookParagraphsDataControlList )
			bookParagraphDataControl.getAssetReferences( assetPaths, assetTypes );
	}

	
	@Override
	public void deleteAssetReferences( String assetPath ) {
		// Spread the call to the paragraphs
		for( BookParagraphDataControl bookParagraphDataControl : bookParagraphsDataControlList )
			bookParagraphDataControl.deleteAssetReferences( assetPath );
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

	@Override
	public void recursiveSearch() {
		for (DataControl dc : bookParagraphsDataControlList)
			dc.recursiveSearch();
	}

	public List<BookParagraph> getBookParagraphsList() {
		return bookParagraphsList;
	}
	
	@Override
	public List<DataControl> getPathToDataControl(DataControl dataControl) {
		return getPathFromChild(dataControl, bookParagraphsDataControlList);
	}

}
