package es.eucm.eadventure.editor.control.controllers.book;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import es.eucm.eadventure.common.auxiliar.File;
import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.item.ItemDataControl;
import es.eucm.eadventure.editor.gui.assetchooser.AssetChooser;
import es.eucm.eadventure.editor.gui.otherpanels.FormattedTextPanel;

public class BookPagesListDataControl{
	/**
	 * List of book paragraphs.
	 */
	private List<BookPage> bookPagesList;

	private int selectedPage;
	
	private boolean defaultScrollable;
	
	private int defaultMargin;
	
	private int defaultType;
	
	/**
	 * Constructor.
	 * 
	 * @param bookParagraphsList
	 *            List of book paragraphs
	 */
	public BookPagesListDataControl( List<BookPage> bookPagesList ) {
		this.bookPagesList = bookPagesList;
		selectedPage = -1;
		defaultType = BookPage.TYPE_RESOURCE;
		defaultMargin = 0;
		defaultScrollable = false;
	}

	/**
	 * Return the list of book paragraph controllers.
	 * 
	 * @return Book paragraph controllers
	 */
	public List<BookPage> getBookPages( ) {
		return bookPagesList;
	}

	/**
	 * Returns the info of the book paragraphs contained in the list.
	 * 
	 * @return Array with the information of the book paragraphs. It contains the number of each paragraph, the type of
	 *         the paragraph, and the word count if applicable
	 */
	public String[][] getBookPagesInfo( ) {
		String[][] bookPagesInfo = null;

		// Create the list for the book paragraphs
		bookPagesInfo = new String[bookPagesList.size( )][3];

		// Fill the array with the info
		for( int i = 0; i < bookPagesList.size( ); i++ ) {
			BookPage bookPage = bookPagesList.get( i );

			if( bookPage.getType( ) == BookPage.TYPE_RESOURCE )
				bookPagesInfo[i][0] = TextConstants.getText( "BookPagesList.TypeResource" );
			else if( bookPage.getType( ) == BookPage.TYPE_URL )
				bookPagesInfo[i][0] = TextConstants.getText( "BookPagesList.TypeURL" );

			bookPagesInfo[i][1] = Integer.toString( bookPage.getMargin( ) );
			bookPagesInfo[i][2] = bookPage.getScrollable( )?TextConstants.getText( "BookPage.Scrollable" ):TextConstants.getText( "BookPage.NotScrollable" );
		}

		return bookPagesInfo;
	}

	public BookPage addPage( ) {
		BookPage newBookPage = new BookPage("", defaultType,defaultMargin, defaultScrollable);

		bookPagesList.add( newBookPage );
		
		//If there is a selected page, move the new one until it is just below
		if (selectedPage>=0 && selectedPage<bookPagesList.size( )){
			//We need size-2-selectedRow movements
			for (int i=0; i<bookPagesList.size( )-2-selectedPage; i++){
				movePageUp (newBookPage);
			}
			this.selectedPage++;
		}

		Controller.getInstance( ).dataModified( );
		return newBookPage;
	}

	public boolean deletePage( BookPage page ) {
		boolean elementDeleted = false;

		if( bookPagesList.remove( page ) ) {
			Controller.getInstance( ).dataModified( );
			elementDeleted = true;
		}

		return elementDeleted;
	}

	public boolean movePageUp( BookPage page ) {
		boolean elementMoved = false;
		int elementIndex = bookPagesList.indexOf( page );

		if( elementIndex > 0 ) {
			bookPagesList.add( elementIndex - 1, bookPagesList.remove( elementIndex ) );
			Controller.getInstance( ).dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	public boolean movePageDown( BookPage page ) {
		boolean elementMoved = false;
		int elementIndex = bookPagesList.indexOf( page );

		if( elementIndex < bookPagesList.size( )-1 ) {
			bookPagesList.add( elementIndex + 1, bookPagesList.remove( elementIndex ) );
			Controller.getInstance( ).dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	public int countAssetReferences( String assetPath ) {
		int count = 0;

		// Spread the call to the pages
		for( BookPage bookPage : bookPagesList )
			if (bookPage.getUri( ).equals( assetPath ))
				count++;

		return count;
	}

	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		// Spread the call to the pages
		for( BookPage bookPage : bookPagesList ){
			
			String uri = bookPage.getUri( );
			if (uri!=null && !uri.equals( "" )){
				// Search assetPaths
				boolean add = true;
				for (String asset: assetPaths){
					if (asset.equals( uri )){
						add = false; break;
					}
				}
				if (add){
					int last = assetPaths.size( );
					assetPaths.add( last, uri );
					assetTypes.add( last, AssetsController.CATEGORY_STYLED_TEXT );
				}
			}
		}
	}

	
	public void deleteAssetReferences( String assetPath ) {
		ArrayList<BookPage> toRemove = new ArrayList<BookPage>();
		
		//Spread the call to the paragraphs
		for( BookPage bookPage : bookPagesList )
			if (bookPage.getUri( ).equals( assetPath ))
				if (bookPage.getType( ) == BookPage.TYPE_URL)
					bookPage.setUri( "" );
				else
					toRemove.add( bookPage );
		
		for (BookPage bookPage: toRemove)
			bookPagesList.remove( bookPage );
					
	}
	
	public BookPage changeCurrentPage ( int page ){
		BookPage currentPage = null;
		if (page>=0 && page< bookPagesList.size( )){
			currentPage = bookPagesList.get( page );
			this.selectedPage = page;
		}
		return currentPage;
	}
	
	public boolean editAssetPath(){
		String selectedAsset = null;
		if (selectedPage<0 || selectedPage>=bookPagesList.size( ))
			return false;
		AssetChooser chooser = AssetsController.getAssetChooser( AssetsController.CATEGORY_STYLED_TEXT, AssetsController.FILTER_NONE );
		int option = chooser.showAssetChooser( Controller.getInstance( ).peekWindow( ) );
		//In case the asset was selected from the zip file
		if( option == AssetChooser.ASSET_FROM_ZIP ) {
			selectedAsset = chooser.getSelectedAsset( );
		}

		//In case the asset was not in the zip file: first add it
		else if( option == AssetChooser.ASSET_FROM_OUTSIDE ) {
			boolean added = AssetsController.addSingleAsset( AssetsController.CATEGORY_STYLED_TEXT, chooser.getSelectedFile( ).getAbsolutePath( ) );
			
			//Check if there are referenced files. Those files must be in a folder where the asset is contained, and that folder must be called
			//assetname_files
			String filePath = chooser.getSelectedFile( ).getAbsolutePath( );
			String filesFolderPath = filePath.substring( 0, filePath.lastIndexOf( "." ))+"_files";
			File filesFolder = new File(filesFolderPath); 
			if (filesFolder.exists( ) && filesFolder.isDirectory( )){
				added &=AssetsController.addSingleAsset( AssetsController.CATEGORY_STYLED_TEXT, filesFolderPath );
			}
			
			if( added ) {
				selectedAsset = chooser.getSelectedFile( ).getName( );
			}
		}

		// If a file was selected
		if( selectedAsset != null ) {
			// Take the index of the selected asset
			String[] assetFilenames = AssetsController.getAssetFilenames( AssetsController.CATEGORY_STYLED_TEXT );
			String[] assetPaths = AssetsController.getAssetsList( AssetsController.CATEGORY_STYLED_TEXT);
			int assetIndex = -1;
			for( int i = 0; i < assetFilenames.length; i++ )
				if( assetFilenames[i].equals( selectedAsset ) )
					assetIndex = i;

			// Store the data in the resources block (removing the suffix if necessary)
			bookPagesList.get( selectedPage ).setUri( assetPaths[assetIndex] );
			Controller.getInstance().dataModified( );
			return true;
		}else
			return false;

	}
	
	public boolean editURL(String newURL){
		if (selectedPage>=0 && selectedPage<bookPagesList.size( ) && bookPagesList.get( selectedPage ).getType( ) == BookPage.TYPE_URL){
			bookPagesList.get( selectedPage ).setUri( newURL );
			Controller.getInstance( ).dataModified( );
			return true;
		}
		return false;
	}
	
	public boolean setType ( int newType ){
		boolean typeSet = false;
		if ( newType!=bookPagesList.get( selectedPage ).getType( ) && selectedPage >=0 && selectedPage<bookPagesList.size( )){
			bookPagesList.get( selectedPage ).setType( newType );
			
			if (newType == BookPage.TYPE_RESOURCE)
				bookPagesList.get( selectedPage ).setUri( "" );
			else
				bookPagesList.get( selectedPage ).setUri( "http://www." );

			typeSet = true;
			Controller.getInstance( ).dataModified( );
		}
		return typeSet;
	}
	
	public void setMargin ( int newMargin ){
		if ( selectedPage >=0 && selectedPage<bookPagesList.size( )){
			bookPagesList.get( selectedPage ).setMargin( newMargin );
			Controller.getInstance( ).dataModified( );
		}
	}
	
	public void setScrollable ( boolean scrollable ){
		if ( selectedPage >=0 && selectedPage<bookPagesList.size( )){
			bookPagesList.get( selectedPage ).setScrollable(scrollable);
			Controller.getInstance( ).dataModified( );
		}
	}

	public boolean isValidPage( BookPage page ){
		boolean isValid = false;
		try{
			if (page.getType( ) == BookPage.TYPE_RESOURCE){
				FormattedTextPanel panel = new FormattedTextPanel();
				panel.loadFile( page.getUri( ) );
				isValid = ! page.getUri( ).equals( "" ) && panel.isValid( );
			}else {
				//Check the URL exists and is accessible
				URL url = new URL (page.getUri( ));
				url.openStream( ).close( );
				isValid = true;
			}
		} catch (Exception e){	
			isValid = false;
		}
		return isValid;
	}
	
	public boolean isValidPage( int page ){
		return isValidPage(bookPagesList.get( page ));
	}

	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean valid = true;

		// Iterate through the paragraphs
		for( int i = 0; i < bookPagesList.size( ); i++ ) {
			String bookParagraphPath = currentPath + " >> " + TextConstants.getText( "Element.BookPage" ) + " #" + ( i + 1 );
			boolean isPageValid = true;
			if (bookPagesList.get( i ).getType( ) == BookPage.TYPE_RESOURCE && bookPagesList.get( i ).getUri( ).length( ) == 0){
				isPageValid = false;
				incidences.add(bookParagraphPath + " >> " + TextConstants.getText( "Operation.AdventureConsistencyErrorBookPage" ) );
			}
			valid &= isPageValid;
			
		}

		return valid;

	}
	
	public BookPage getSelectedPage(){
		if (selectedPage>=0 && selectedPage<bookPagesList.size( ))
			return bookPagesList.get( selectedPage );
		else
			return null;
	}
	
	public BookPage getLastPage(){
		return bookPagesList.get( bookPagesList.size( )-1 );
	}
}
