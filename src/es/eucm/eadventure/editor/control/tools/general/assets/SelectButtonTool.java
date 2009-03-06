package es.eucm.eadventure.editor.control.tools.general.assets;


import es.eucm.eadventure.common.data.adventure.AdventureData;
import es.eucm.eadventure.common.data.adventure.CustomButton;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.data.AssetInformation;

public class SelectButtonTool extends SelectResourceTool{

	protected AdventureData adventureData;
	
	protected String type;
	
	protected String action;
	
	protected boolean removed;
	
	protected static AssetInformation[] createAssetInfoArray (String action, String type){
		AssetInformation[] array = new AssetInformation[1];
		array[0] = new AssetInformation( "", action+"#"+type, true, AssetsController.CATEGORY_BUTTON, AssetsController.FILTER_NONE );
		return array;
	}
	
	protected static Resources createResources( AdventureData adventureData, String action, String type ){
		Resources resources = new Resources();
		boolean introduced = false;
		for (int i=0; i<adventureData.getButtons().size(); i++){
			CustomButton customButton = adventureData.getButtons( ).get( i );
			if (customButton.getType( ).equals( type ) && customButton.getAction().equals(action)){
				resources.addAsset(action+"#"+type, customButton.getPath());
				introduced = true;break;
			}
		}
		
		if (!introduced){
			resources.addAsset(action+"#"+type, "NULL");
		}
		
		return resources;
	}
	
	public SelectButtonTool( AdventureData adventureData, String action, String type )
			throws CloneNotSupportedException {
		super(createResources(adventureData, action, type), createAssetInfoArray( action, type ), Controller.ACTION_CUSTOM, 0);
		this.adventureData = adventureData;
		this.type = type;
		this.action = action;
		this.setGlobal(true);
	}

	@Override
	public boolean undoTool(){
		boolean done = super.undoTool();
		if (!done)
			return false;
		else {
			for (int i =0; i<adventureData.getButtons().size(); i++){
				if (adventureData.getButtons().get(i).getType().equals(type)
						&&adventureData.getButtons().get(i).getAction().equals(action)){
					if (removed){
						adventureData.getButtons().remove(i);
						setButton(action, type, resources.getAssetPath(action+"#"+type));
					}else
						adventureData.getButtons().get(i).setPath(resources.getAssetPath(action+"#"+type));
					break;
					
				}
			}
			controller.updatePanel();
			return true;
		}
		
	}
	
	@Override
	public boolean redoTool(){
		if (removed)
			adventureData.addButton(action,type, "");
		boolean done = super.redoTool();
		if (!done)
			return false;
		else {
			for (int i =0; i<adventureData.getButtons().size(); i++){
				if (adventureData.getButtons().get(i).getType().equals(type)
						&&adventureData.getButtons().get(i).getAction().equals(action)){
					adventureData.getButtons().get(i).setPath(resources.getAssetPath(action+"#"+type));
				}
			}
			controller.updatePanel();
			return true;
		}		
	}
	
	@Override
	public boolean doTool(){
		if (resources.getAssetPath(action+"#"+type).equals("NULL")){
			removed = false;
		} else {
			for (int i =0; i<adventureData.getButtons().size(); i++){
				CustomButton button =adventureData.getButtons().get(i); 
				if ( button.getAction().equals(action) && 
						button.getType().equals(type)){
					adventureData.getButtons().remove(button);
					break;
				}
			}
			removed = true;
		}
		boolean done = super.doTool();
		if (!done)
			return false;
		else {
			setButton (action, type, resources.getAssetPath(action+"#"+type));
			return true;
		}		
	}


	public void setButton(String action, String type, String path) {
		CustomButton button = new CustomButton(action, type, path);
		CustomButton temp = null;
		for (CustomButton cb : adventureData.getButtons()) {
			if (cb.equals(button))
				temp = cb;
		}
		if (temp != null)
			adventureData.getButtons().remove(temp);
		System.out.println("Adding button: "+button.getAction()+ ","+button.getType()+","+button.getPath());
		adventureData.addButton(button);
	}
}
