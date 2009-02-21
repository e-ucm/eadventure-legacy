package es.eucm.eadventure.editor.control.tools.general;

import es.eucm.eadventure.common.data.chapter.ExitLook;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.tools.general.resources.SelectResourceTool;
import es.eucm.eadventure.editor.data.AssetInformation;

public class SelectAssetForEffectTool extends SelectResourceTool{

	protected static final String ANIMATION_EFF = "anim";
	protected static final String AUDIO_EFF = "audio";
	
	protected ExitLook exitLook;
	
	protected static AssetInformation[] createAssetInfoArray (){
		AssetInformation[] array = new AssetInformation[1];
		array[0] = new AssetInformation( "", ANIMATION_EFF, true, AssetsController.CATEGORY_CURSOR, AssetsController.FILTER_NONE );
		return array;
	}
	
	protected static Resources createResources(ExitLook exitLook){
		Resources resources = new Resources();
		resources.addAsset(ANIMATION_EFF, exitLook.getCursorPath());
		return resources;
	}
	
	public SelectAssetForEffectTool(ExitLook exitLook )
			throws CloneNotSupportedException {
		super(createResources(exitLook), createAssetInfoArray(), Controller.EXIT, 0);
		this.exitLook = exitLook;
	}

	@Override
	public boolean undoTool(){
		boolean done = super.undoTool();
		if (!done)
			return false;
		else {
			exitLook.setCursorPath( resources.getAssetPath(ANIMATION_EFF) );
			//System.out.println("NEW CURSOR PATH = "+resources.getAssetPath(CURSOR_STR));
			controller.reloadPanel();
			return true;
		}
		
	}
	
	@Override
	public boolean redoTool(){
		boolean done = super.redoTool();
		if (!done)
			return false;
		else {
			//System.out.println("NEW CURSOR PATH = "+resources.getAssetPath(CURSOR_STR));
			exitLook.setCursorPath( resources.getAssetPath(ANIMATION_EFF) );
			controller.reloadPanel();
			return true;
		}		
	}
	
	@Override
	public boolean doTool(){
		boolean done = super.doTool();
		if (!done)
			return false;
		else {
			exitLook.setCursorPath( resources.getAssetPath(ANIMATION_EFF) );
			//System.out.println("NEW CURSOR PATH = "+resources.getAssetPath(CURSOR_STR));
			return true;
		}		
	}


}
