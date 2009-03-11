package es.eucm.eadventure.editor.control.tools.general.assets;

import java.util.ArrayList;

import es.eucm.eadventure.common.data.adventure.AdventureData;
import es.eucm.eadventure.common.data.adventure.CustomCursor;
import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.common.data.chapter.ExitLook;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.data.AssetInformation;

public class SelectCursorPathTool extends SelectResourceTool{

	protected AdventureData adventureData;
	
	protected int t;
	
	protected String type;
	
	protected boolean added;
	
	protected static AssetInformation[] createAssetInfoArray (int t){
		String type = DescriptorData.getCursorTypeString(t);
		AssetInformation[] array = new AssetInformation[1];
		array[0] = new AssetInformation( "", type, true, AssetsController.CATEGORY_CURSOR, AssetsController.FILTER_NONE );
		return array;
	}
	
	protected static Resources createResources( AdventureData adventureData, int t ){
		String type = DescriptorData.getCursorTypeString(t);
		Resources resources = new Resources();
		boolean introduced = false;
		for (int i=0; i<adventureData.getCursors().size(); i++){
			if (adventureData.getCursors( ).get( i ).getType( ).equals( type ) && 
					adventureData.getCursors( ).get( i ).getPath( )!=null){
				resources.addAsset(type, adventureData.getCursors().get( i ).getPath());
				introduced = true;break;
			}
		}
		
		if (!introduced){
			resources.addAsset(type, "NULL");
		}
		
		return resources;
	}
	
	public SelectCursorPathTool( AdventureData adventureData, int t )
			throws CloneNotSupportedException {
		super(createResources(adventureData, t), createAssetInfoArray(t), Controller.ACTION_CUSTOM, 0);
		this.adventureData = adventureData;
		this.t = t;
		this.type = DescriptorData.getCursorTypeString(t);
	}

	@Override
	public boolean undoTool(){
		boolean done = super.undoTool();
		if (!done)
			return false;
		else {
			for (int i =0; i<adventureData.getCursors().size(); i++){
				if (adventureData.getCursors().get(i).getType().equals(type)){
					if (added)
						adventureData.getCursors().remove(i);
					else
						adventureData.getCursors().get(i).setPath(resources.getAssetPath(type));
					break;
					
				}
			}
			controller.updatePanel();
			return true;
		}
		
	}
	
	@Override
	public boolean redoTool(){
		if (added)
			adventureData.addCursor(type, "");
		boolean done = super.redoTool();
		if (!done)
			return false;
		else {
			for (int i =0; i<adventureData.getCursors().size(); i++){
				if (adventureData.getCursors().get(i).getType().equals(type)){
					adventureData.getCursors().get(i).setPath(resources.getAssetPath(type));
				}
			}
			controller.updatePanel();
			return true;
		}		
	}
	
	@Override
	public boolean doTool(){
		if (resources.getAssetPath(type).equals("NULL")){
			adventureData.addCursor(type, "");
			added = true;
		} else {
			added = false;
		}
		boolean done = super.doTool();
		if (!done)
			return false;
		else {
			for (int i =0; i<adventureData.getCursors().size(); i++){
				if (adventureData.getCursors().get(i).getType().equals(type)){
					adventureData.getCursors().get(i).setPath(resources.getAssetPath(type));
				}
			}
			return true;
		}		
	}


}
