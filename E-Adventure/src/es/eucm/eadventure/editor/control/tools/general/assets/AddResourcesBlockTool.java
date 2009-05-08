package es.eucm.eadventure.editor.control.tools.general.assets;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class AddResourcesBlockTool extends Tool{

	/**
	 * Arguments
	 */
	private List<Resources> resourcesList;
	private List<ResourcesDataControl> resourcesDataControlList;
	private int resourcesType;
	private DataControlWithResources parent;
	
	/*
	 * Temporal data for undo/redo
	 */
	private Resources newResources;
	private ResourcesDataControl newResourcesDataControl;
	
	public AddResourcesBlockTool ( List<Resources> resourcesList, List<ResourcesDataControl> resourcesDataControlList, int resourcesType, DataControlWithResources parent){
		this.resourcesList = resourcesList;
		this.resourcesDataControlList = resourcesDataControlList;
		this.resourcesType = resourcesType;
		this.parent = parent;
	}
	
	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
		newResources = new Resources( );
		newResourcesDataControl = new ResourcesDataControl( newResources, resourcesType );
		resourcesList.add( newResources );
		resourcesDataControlList.add( newResourcesDataControl );
		return true;
	}

	@Override
	public boolean redoTool() {
		resourcesList.add( newResources );
		resourcesDataControlList.add( newResourcesDataControl );
		parent.setSelectedResources(resourcesList.size()-1);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		boolean undone= resourcesList.remove( newResources ) && resourcesDataControlList.remove( newResourcesDataControl );
		
		if (undone){
			parent.setSelectedResources(resourcesList.size()-1);
			Controller.getInstance().updatePanel();
		}
		return undone;
	}

}
