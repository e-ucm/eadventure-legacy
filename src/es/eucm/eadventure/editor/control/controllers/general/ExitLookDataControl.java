package es.eucm.eadventure.editor.control.controllers.general;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.Exit;
import es.eucm.eadventure.common.data.chapter.ExitLook;
import es.eucm.eadventure.common.data.chapter.NextScene;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.gui.assetchooser.AssetChooser;

public class ExitLookDataControl {

	private ExitLook exitLook;
	
	private boolean isTextCustomized;
	
	private boolean isCursorCustomized;
	
	public ExitLookDataControl (NextScene nextScene){
		if (nextScene.getExitLook( )==null)
			nextScene.setExitLook( new ExitLook() );
		this.exitLook=nextScene.getExitLook( );
		
		isTextCustomized = (exitLook.getExitText( )!=null);
		isCursorCustomized = (exitLook.getCursorPath( )!=null);

	}
	
	public ExitLookDataControl (Exit exit){
		if (exit.getDefaultExitLook( )==null)
			exit.setDefaultExitLook( new ExitLook() );
		this.exitLook=exit.getDefaultExitLook( );
		
		isTextCustomized = (exitLook.getExitText( )!=null);
		isCursorCustomized = (exitLook.getCursorPath( )!=null);

	}

	
	/**
	 * @return the isTextCustomized
	 */
	public boolean isTextCustomized( ) {
		return isTextCustomized;
	}
	
	public String getCustomizedText( ) {
		String text = null;
		if (exitLook!=null && exitLook.getExitText( )!=null)
			text = exitLook.getExitText( );
		return text;
	}

	/**
	 * @return the isCursorCustomized
	 */
	public boolean isCursorCustomized( ) {
		return isCursorCustomized;
	}
	
	public String getCustomizedCursor( ) {
		String text = null;
		if (exitLook!=null && exitLook.getCursorPath( )!=null)
			text = exitLook.getCursorPath( );
		return text;
	}

	
	public void setExitText(String text){
		if (text!=null){
			exitLook.setExitText( text );
			this.isTextCustomized = true;
		}
		else{
			isTextCustomized = false;
			exitLook.setExitText( null );
		}
		Controller.getInstance( ).dataModified( );
	}
	
	public void editCursorPath( ){
		String selectedAsset = null;
		String cursorPath = null;
		AssetChooser chooser = AssetsController.getAssetChooser( AssetsController.CATEGORY_CURSOR, AssetsController.FILTER_NONE );
		int option = chooser.showAssetChooser( Controller.getInstance( ).peekWindow( ) );
		//In case the asset was selected from the zip file
		if( option == AssetChooser.ASSET_FROM_ZIP ) {
			selectedAsset = chooser.getSelectedAsset( );
		}

		//In case the asset was not in the zip file: first add it
		else if( option == AssetChooser.ASSET_FROM_OUTSIDE ) {
			boolean added = AssetsController.addSingleAsset( AssetsController.CATEGORY_CURSOR, chooser.getSelectedFile( ).getAbsolutePath( ) );
			if( added ) {
				selectedAsset = chooser.getSelectedFile( ).getName( );
			}
		}

		// If a file was selected
		if( selectedAsset != null ) {
			// Take the index of the selected asset
			String[] assetFilenames = AssetsController.getAssetFilenames( AssetsController.CATEGORY_CURSOR );
			String[] assetPaths = AssetsController.getAssetsList( AssetsController.CATEGORY_CURSOR );
			int assetIndex = -1;
			for( int i = 0; i < assetFilenames.length; i++ )
				if( assetFilenames[i].equals( selectedAsset ) )
					assetIndex = i;

			// Store the data in the resources block (removing the suffix if necessary)
			cursorPath = assetPaths[assetIndex];
			
			
		}

		
		if (cursorPath!=null){
			exitLook.setCursorPath( cursorPath );
			this.isCursorCustomized = true;
			Controller.getInstance().dataModified( );
		}

		
	}
	
	public void invalidCursor(){
		exitLook.setCursorPath( null );
		isCursorCustomized=false;
		Controller.getInstance().dataModified( );
	}
	
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		if (this.isCursorCustomized( )){
			boolean add = true;
			for (String asset: assetPaths){
				if (asset.equals( exitLook.getCursorPath( ) )){
					add = false;
					break;
				}
			}
			if (add){
				int last = assetPaths.size( );
				assetPaths.add( last, exitLook.getCursorPath( ) );
				assetTypes.add( last, AssetsController.CATEGORY_CURSOR );
			}
		}

	}

}
