package es.eucm.eadventure.editor.control.tools.general.assets;

import java.util.List;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DuplicateResourcesBlockTool extends Tool{

	private DataControl dataControl;
	/**
	 * List of resources.
	 */
	protected List<Resources> resourcesList;

	/**
	 * List of resources controllers.
	 */
	protected List<ResourcesDataControl> resourcesDataControlList;
	
	// Temp data
	private Resources newElement;
	private ResourcesDataControl newDataControl; 
	private DataControlWithResources parent;
	
	public DuplicateResourcesBlockTool ( DataControl dataControl, List<Resources> resourcesList, List<ResourcesDataControl> resourcesDataControlList, DataControlWithResources parent){
		this.dataControl = dataControl;
		this.resourcesList = resourcesList;
		this.resourcesDataControlList = resourcesDataControlList;
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
		if (!(dataControl instanceof ResourcesDataControl))
			return false;
		
		try {
			newElement = (Resources) (((Resources) (dataControl.getContent())).clone());
			newDataControl = new ResourcesDataControl(newElement, ((ResourcesDataControl) dataControl).getResourcesType() );
			resourcesList.add(newElement);
			resourcesDataControlList.add( newDataControl );
			parent.setSelectedResources(resourcesList.size()-1);
			return true;
		} catch (CloneNotSupportedException e) {
			ReportDialog.GenerateErrorReport(e, true, "Could not clone resources");	
			return false;
		} 		
	}

	@Override
	public boolean redoTool() {
		resourcesList.add(newElement);
		resourcesDataControlList.add( newDataControl );
		parent.setSelectedResources(resourcesList.size()-1);
		Controller.getInstance().reloadPanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		boolean undone= resourcesList.remove(newElement) && resourcesDataControlList.remove(newDataControl);
		if (undone){
			parent.setSelectedResources(resourcesList.size()-1);
			Controller.getInstance().reloadPanel();
		}
		return undone;
	}

}
