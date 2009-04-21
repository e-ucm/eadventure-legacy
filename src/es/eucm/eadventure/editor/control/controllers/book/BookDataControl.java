package es.eucm.eadventure.editor.control.controllers.book;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDocumentationTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

/**
 * Controller for the book elements.
 * 
 * @author Bruno Torijano Bueno
 */
public class BookDataControl extends DataControlWithResources {

	/**
	 * Contained book.
	 */
	private Book book;

	/**
	 * List of resources.
	 */
	private List<Resources> resourcesList;

	/**
	 * List of resources controllers.
	 */
	private List<ResourcesDataControl> resourcesDataControlList;

	/**
	 * Book paragraphs list controller.
	 */
	private BookParagraphsListDataControl bookParagraphsListDataControl;
	
	/**
	 * Book pages list controller.
	 */
	private BookPagesListDataControl bookPagesListDataControl;


	/**
	 * The resources that must be used in the previews.
	 */
	private int selectedResources;

	/**
	 * Constructor.
	 * 
	 * @param book
	 *            Contained book
	 */
	public BookDataControl( Book book ) {
		this.book = book;
		this.resourcesList = book.getResources( );

		selectedResources = 0;

		// Add a new resource if the list is empty
		if( resourcesList.size( ) == 0 )
			resourcesList.add( new Resources( ) );

		// Create the subcontrollers
		resourcesDataControlList = new ArrayList<ResourcesDataControl>( );
		for( Resources resources : resourcesList )
			resourcesDataControlList.add( new ResourcesDataControl( resources, Controller.BOOK ) );

		if (book.getType( ) == Book.TYPE_PARAGRAPHS)
			bookParagraphsListDataControl = new BookParagraphsListDataControl( book.getParagraphs( ) );
		else if (book.getType( ) == Book.TYPE_PAGES)
			bookPagesListDataControl = new BookPagesListDataControl( book.getPageURLs( ) );
	}

	/**
	 * Returns the list of resources controllers.
	 * 
	 * @return Resources controllers
	 */
	public List<ResourcesDataControl> getResources( ) {
		return resourcesDataControlList;
	}

	/**
	 * Returns the number of resources blocks contained.
	 * 
	 * @return Number of resources blocks
	 */
	public int getResourcesCount( ) {
		return resourcesDataControlList.size( );
	}

	/**
	 * Returns the last resources controller of the list.
	 * 
	 * @return Last resources controller
	 */
	public ResourcesDataControl getLastResources( ) {
		return resourcesDataControlList.get( resourcesDataControlList.size( ) - 1 );
	}

	/**
	 * Returns the book paragraphs list controller.
	 * 
	 * @return Book paragraphs list controller
	 */
	public BookParagraphsListDataControl getBookParagraphsList( ) {
		return bookParagraphsListDataControl;
	}
	
	/**
	 * Returns the book paragraphs list controller.
	 * 
	 * @return Book paragraphs list controller
	 */
	public BookPagesListDataControl getBookPagesList( ) {
		return bookPagesListDataControl;
	}
	

	/**
	 * Returns the selected resources block of the list.
	 * 
	 * @return Selected block of resources
	 */
	public int getSelectedResources( ) {
		return selectedResources;
	}

	/**
	 * Returns the id of the book.
	 * 
	 * @return Book's id
	 */
	public String getId( ) {
		return book.getId( );
	}

	/**
	 * Returns the documentation of the book.
	 * 
	 * @return Book's documentation
	 */
	public String getDocumentation( ) {
		return book.getDocumentation( );
	}

	/**
	 * Sets the new selected resources block of the list.
	 * 
	 * @param selectedResources
	 *            New selected block of resources
	 */
	public void setSelectedResources( int selectedResources ) {
		this.selectedResources = selectedResources;
	}

	/**
	 * Returns the path to the selected preview image.
	 * 
	 * @return Path to the image, null if not present
	 */
	public String getPreviewImage( ) {
		return resourcesDataControlList.get( selectedResources ).getAssetPath( "background" );
	}

	/**
	 * Sets the new documentation of the book.
	 * 
	 * @param documentation
	 *            Documentation of the book
	 */
	public void setDocumentation( String documentation ) {
		controller.addTool(new ChangeDocumentationTool(book, documentation));
	}

	@Override
	public Object getContent( ) {
		return book;
	}

	@Override
	public int[] getAddableElements( ) {
		//return new int[] { Controller.RESOURCES };
		return new int[] {};
	}

	@Override
	public boolean canAddElement( int type ) {
		// It can always add new resources
		//return type == Controller.RESOURCES;
		return false;
	}

	@Override
	public boolean canBeDeleted( ) {
		return true;
	}

	@Override
	public boolean canBeMoved( ) {
		return true;
	}

	@Override
	public boolean canBeRenamed( ) {
		return true;
	}

	@Override
	public boolean addElement( int type , String id) {
		boolean elementAdded = false;

		if( type == Controller.RESOURCES ) {
			Resources newResources = new Resources( );
			resourcesList.add( newResources );
			resourcesDataControlList.add( new ResourcesDataControl( newResources, Controller.BOOK ) );
			//controller.dataModified( );
			elementAdded = true;
		}

		return elementAdded;
	}

	@Override
	public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {
		boolean elementDeleted = false;

		// Delete the block only if it is not the last one
		if( resourcesList.size( ) > 1 ) {
			if( resourcesList.remove( dataControl.getContent( ) ) ) {
				int resourcesIndex = resourcesDataControlList.indexOf( dataControl );
				resourcesDataControlList.remove( dataControl );

				// Decrease the selected index if necessary
				if( selectedResources > 0 && selectedResources >= resourcesIndex )
					selectedResources--;

				//controller.dataModified( );
				elementDeleted = true;
			}
		}

		// If it was the last one, show an error message
		else
			controller.showErrorDialog( TextConstants.getText( "Operation.DeleteResourcesTitle" ), TextConstants.getText( "Operation.DeleteResourcesErrorLastResources" ) );

		return elementDeleted;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = resourcesList.indexOf( dataControl.getContent( ) );

		if( elementIndex > 0 ) {
			resourcesList.add( elementIndex - 1, resourcesList.remove( elementIndex ) );
			resourcesDataControlList.add( elementIndex - 1, resourcesDataControlList.remove( elementIndex ) );
			//controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = resourcesList.indexOf( dataControl.getContent( ) );

		if( elementIndex < resourcesList.size( ) - 1 ) {
			resourcesList.add( elementIndex + 1, resourcesList.remove( elementIndex ) );
			resourcesDataControlList.add( elementIndex + 1, resourcesDataControlList.remove( elementIndex ) );
			//controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public String renameElement( String name ) {
		boolean elementRenamed = false;
		String oldBookId = book.getId( );
		String references = String.valueOf( controller.countIdentifierReferences( oldBookId ) );

		// Ask for confirmation
		if(name != null || controller.showStrictConfirmDialog( TextConstants.getText( "Operation.RenameBookTitle" ), TextConstants.getText( "Operation.RenameElementWarning", new String[] { oldBookId, references } ) ) ) {

			// Show a dialog asking for the new book id
			String newBookId = name;
			if (name == null)
				newBookId = controller.showInputDialog( TextConstants.getText( "Operation.RenameBookTitle" ), TextConstants.getText( "Operation.RenameBookMessage" ), oldBookId );

			// If some value was typed and the identifiers are different
			if( newBookId != null && !newBookId.equals( oldBookId ) && controller.isElementIdValid( newBookId ) ) {
				book.setId( newBookId );
				controller.replaceIdentifierReferences( oldBookId, newBookId );
				controller.getIdentifierSummary( ).deleteBookId( oldBookId );
				controller.getIdentifierSummary( ).addBookId( newBookId );
				//controller.dataModified( );
				elementRenamed = true;
			}
		}

		if (elementRenamed)
			return oldBookId;
		else
			return null;
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
	// Do nothing
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean valid = true;

		// Iterate through the resources
		for( int i = 0; i < resourcesDataControlList.size( ); i++ ) {
			String resourcesPath = currentPath + " >> " + TextConstants.getElementName( Controller.RESOURCES ) + " #" + ( i + 1 );
			valid &= resourcesDataControlList.get( i ).isValid( resourcesPath, incidences );
		}

		// Spread the call to the paragraphs
		if (book.getType( ) == Book.TYPE_PARAGRAPHS)
			valid &= bookParagraphsListDataControl.isValid( currentPath, incidences );
		else if (book.getType( ) == Book.TYPE_PAGES)
			valid &= bookPagesListDataControl.isValid( currentPath, incidences );

		return valid;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		// Iterate through the resources
		for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
			count += resourcesDataControl.countAssetReferences( assetPath );

		// Spread the call to the paragraphs/pages
		if (book.getType( ) == Book.TYPE_PARAGRAPHS)
			count += bookParagraphsListDataControl.countAssetReferences( assetPath );
		else if (book.getType( ) == Book.TYPE_PAGES)
			count += bookPagesListDataControl.countAssetReferences( assetPath );
		return count;
	}
	
	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		// Iterate through the resources
		for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
			resourcesDataControl.getAssetReferences( assetPaths, assetTypes );
		
		// Spread the call to the paragraphs/pages
		if (book.getType( ) == Book.TYPE_PARAGRAPHS)
			bookParagraphsListDataControl.getAssetReferences( assetPaths, assetTypes );
		else if (book.getType( ) == Book.TYPE_PAGES)
			bookPagesListDataControl.getAssetReferences( assetPaths, assetTypes );

	}


	@Override
	public void deleteAssetReferences( String assetPath ) {
		// Iterate through the resources
		for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
			resourcesDataControl.deleteAssetReferences( assetPath );

		// Spread the call to the paragraphs/pages
		if (book.getType( ) == Book.TYPE_PARAGRAPHS)
			bookParagraphsListDataControl.deleteAssetReferences( assetPath );
		else if (book.getType( ) == Book.TYPE_PAGES)
			bookPagesListDataControl.deleteAssetReferences( assetPath );
		
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
	
	public int getType (){
		return book.getType( );
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}
	

	@Override
	public void recursiveSearch() {
		check(this.getDocumentation(), TextConstants.getText("Search.Documentation"));
		check(this.getId(), "ID");
		if (this.getBookParagraphsList() != null)
			this.getBookParagraphsList().recursiveSearch();
		check(this.getPreviewImage(), TextConstants.getText("Search.PreviewImage"));
	}
	
	@Override
	public List<DataControl> getPathToDataControl(DataControl dataControl) {
		List<DataControl> path = getPathFromChild(dataControl, resourcesDataControlList);
		if (path != null) return path;
		return getPathFromChild(dataControl, bookParagraphsListDataControl);
	}

}
