package es.eucm.eadventure.editor.control.controllers.book;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.book.BookParagraph;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.data.support.FlagSummary;
import es.eucm.eadventure.editor.gui.assetchooser.AssetChooser;

/**
 * Controller for the book paragraph element.
 * 
 * @author Bruno Torijano Bueno
 */
public class BookParagraphDataControl extends DataControl {

	/**
	 * Contained book paragraph.
	 */
	private BookParagraph bookParagraph;

	/**
	 * Type of the book paragraph.
	 */
	private int bookParagraphType;

	/**
	 * Constructor.
	 * 
	 * @param bookParagraph
	 *            Contained book paragraph
	 */
	public BookParagraphDataControl( BookParagraph bookParagraph ) {
		this.bookParagraph = bookParagraph;

		// Store the type of the paragraph
		switch( bookParagraph.getType( ) ) {
			case BookParagraph.TITLE:
				bookParagraphType = Controller.BOOK_TITLE_PARAGRAPH;
				break;
			case BookParagraph.TEXT:
				bookParagraphType = Controller.BOOK_TEXT_PARAGRAPH;
				break;
			case BookParagraph.IMAGE:
				bookParagraphType = Controller.BOOK_IMAGE_PARAGRAPH;
				break;
			case BookParagraph.BULLET:
				bookParagraphType = Controller.BOOK_BULLET_PARAGRAPH;
				break;
		}
	}

	/**
	 * Returns the type of the contained paragraph.
	 * 
	 * @return Type of contained book paragraph
	 */
	public int getType( ) {
		return bookParagraphType;
	}

	/**
	 * Returns the content of the paragraph.
	 * 
	 * @return Paragraph's content
	 */
	public String getParagraphContent( ) {
		return bookParagraph.getContent( );
	}

	/**
	 * Sets the new content for the paragraph. This method must be used only with text and bullet paragraphs.
	 * 
	 * @param content
	 *            New content for the paragtaph
	 */
	public void setParagraphTextContent( String content ) {
		// If the value is different
		if( !content.equals( bookParagraph.getContent( ) ) ) {
			// Set the new text and modify the data
			bookParagraph.setContent( content );
			controller.dataModified( );
		}
	}

	/**
	 * Shows a dialog to choose a new path for the given asset.
	 * 
	 * @param index
	 *            Index of the asset
	 */
	public void editImagePath( ) {
		// Get the list of assets from the ZIP file
		String selectedAsset = null;
		AssetChooser chooser = AssetsController.getAssetChooser( AssetsController.CATEGORY_IMAGE, AssetsController.FILTER_NONE );
		int option = chooser.showAssetChooser( controller.peekWindow( ) );
		//In case the asset was selected from the zip file
		if( option == AssetChooser.ASSET_FROM_ZIP ) {
			selectedAsset = chooser.getSelectedAsset( );
		}

		//In case the asset was not in the zip file: first add it
		else if( option == AssetChooser.ASSET_FROM_OUTSIDE ) {
			boolean added = AssetsController.addSingleAsset(  AssetsController.CATEGORY_IMAGE, chooser.getSelectedFile( ).getAbsolutePath( ) );
			if( added ) {
				selectedAsset = chooser.getSelectedFile( ).getName( );
			}
		}

		// If a file was selected
		if( selectedAsset != null ) {
			// Take the index of the selected asset
			String[] assetFilenames = AssetsController.getAssetFilenames(  AssetsController.CATEGORY_IMAGE );
			String[] assetPaths = AssetsController.getAssetsList(  AssetsController.CATEGORY_IMAGE );
			int assetIndex = -1;
			for( int i = 0; i < assetFilenames.length; i++ )
				if( assetFilenames[i].equals( selectedAsset ) )
					assetIndex = i;

			// Store the data 
			bookParagraph.setContent( assetPaths[assetIndex] );
			controller.dataModified( );
		}
	}
	
	/**
	 * Sets the new path for the paragraph. This method must be used only with image paragraphs.
	 */
	public void setImageParagraphContent( ) {
		// Get the list of assets from the ZIP file
		String[] assetFilenames = AssetsController.getAssetFilenames( AssetsController.CATEGORY_IMAGE );
		String[] assetPaths = AssetsController.getAssetsList( AssetsController.CATEGORY_IMAGE );

		// If the list of assets is empty, show an error message
		if( assetFilenames.length == 0 )
			controller.showErrorDialog( TextConstants.getText( "Resources.EditAsset" ), TextConstants.getText( "Resources.ErrorNoAssets" ) );

		// If not empty, select one of them
		else {
			// Let the user choose between the assets
			String selectedAsset = controller.showInputDialog( TextConstants.getText( "Resources.EditAsset" ), TextConstants.getText( "Resources.EditAssetMessage" ), assetFilenames );

			// If a file was selected
			if( selectedAsset != null ) {
				// Take the index of the selected asset
				int assetIndex = -1;
				for( int i = 0; i < assetFilenames.length; i++ )
					if( assetFilenames[i].equals( selectedAsset ) )
						assetIndex = i;

				// Store the data
				bookParagraph.setContent( assetPaths[assetIndex] );
				controller.dataModified( );
			}
		}
	}

	/**
	 * Deletes the content of the image paragraph. This method must be used only with image paragraphs.
	 */
	public void deleteImageParagraphContent( ) {
		// Set the content to an empty string
		bookParagraph.setContent( "" );
	}

	@Override
	public Object getContent( ) {
		return bookParagraph;
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] {};
	}

	@Override
	public boolean canAddElement( int type ) {
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
		return false;
	}

	@Override
	public boolean addElement( int type ) {
		return false;
	}

	@Override
	public boolean deleteElement( DataControl dataControl ) {
		return false;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		return false;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		return false;
	}

	@Override
	public boolean renameElement( ) {
		return false;
	}

	@Override
	public void updateFlagSummary( FlagSummary flagSummary ) {
	// Do nothing
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean valid = true;

		// If it is an image paragraph and it's not linked to an asset, is invalid
		if( bookParagraph.getType( ) == BookParagraph.IMAGE && bookParagraph.getContent( ).length( ) == 0 ) {
			valid = false;

			// Store the incidence
			if( incidences != null )
				incidences.add( currentPath + " >> " + TextConstants.getText( "Operation.AdventureConsistencyErrorBookParagraph" ) );
		}

		return valid;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		// Return 1 if it is an image paragraph and the asset matches
		return bookParagraph.getType( ) == BookParagraph.IMAGE && bookParagraph.getContent( ).equals( assetPath ) ? 1 : 0;
	}

	@Override
	public void deleteAssetReferences( String assetPath ) {
		// If it is an image paragraph and contains the asset, delete it
		if( bookParagraph.getType( ) == BookParagraph.IMAGE && bookParagraph.getContent( ).equals( assetPath ) ) {
			bookParagraph.setContent( "" );
			controller.dataModified( );
		}
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
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		// Only if book paragraph is image
		if (bookParagraph.getType( ) == BookParagraph.IMAGE){
			String imagePath = bookParagraph.getContent( );
			// Search in assetPaths
			boolean add = true;
			for (String asset: assetPaths){
				if (asset.equals( imagePath )){
					add = false; break;
				}
			}
			if (add){
				int last = assetPaths.size( );
				assetPaths.add( last, imagePath );
				assetTypes.add( last, AssetsController.CATEGORY_IMAGE );
			}
		}
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}
}
