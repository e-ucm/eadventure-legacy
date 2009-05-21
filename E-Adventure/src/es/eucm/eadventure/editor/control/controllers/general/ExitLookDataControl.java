package es.eucm.eadventure.editor.control.controllers.general;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.Exit;
import es.eucm.eadventure.common.data.chapter.ExitLook;
import es.eucm.eadventure.common.data.chapter.NextScene;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.tools.general.InvalidExitCursorTool;
import es.eucm.eadventure.editor.control.tools.general.assets.SelectExitCursorPathTool;

public class ExitLookDataControl {

	private ExitLook exitLook;
		
	public ExitLookDataControl (NextScene nextScene){
		if (nextScene.getExitLook( )==null)
			nextScene.setExitLook( new ExitLook() );
		this.exitLook=nextScene.getExitLook( );
	}
	
	public ExitLookDataControl (Exit exit){
		if (exit.getDefaultExitLook() == null)
			exit.setDefaultExitLook(new ExitLook());
		this.exitLook=exit.getDefaultExitLook( );
	}

	
	/**
	 * @return the isTextCustomized
	 */
	public boolean isTextCustomized( ) {
		return exitLook.getExitText( )!=null;
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
		return exitLook.getCursorPath( )!=null;
	}
	
	public String getCustomizedCursor( ) {
		String text = null;
		if (exitLook!=null && exitLook.getCursorPath( )!=null)
			text = exitLook.getCursorPath( );
		return text;
	}

	public void setExitText(String text){
		this.exitLook.setExitText(text);
	}

	public void editCursorPath( ){
		try {
			Controller.getInstance().addTool(new SelectExitCursorPathTool(exitLook));
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public void invalidCursor(){
		Controller.getInstance().addTool(new InvalidExitCursorTool(exitLook));
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
	
	public int countAssetReferences( String assetPath ) {
	    if (exitLook.getCursorPath( ).equals(assetPath))
		return 1;
	    else
		return 0;
	    
	}
	
	public void deleteAssetReferences( String assetPath ) {
	    if (exitLook.getCursorPath( ).equals(assetPath))
		exitLook.setCursorPath("");
	    
	    
	}

	public void setCursorPath(String value) {
		exitLook.setCursorPath(value);
	}

}
