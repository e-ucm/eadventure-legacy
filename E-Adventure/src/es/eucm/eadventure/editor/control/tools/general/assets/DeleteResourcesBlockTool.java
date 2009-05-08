package es.eucm.eadventure.editor.control.tools.general.assets;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeleteResourcesBlockTool extends Tool{

	/**
	 * Arguments
	 */
	private List<Resources> resourcesList;
	private List<ResourcesDataControl> resourcesDataControlList;
	// The data control (Resources) to be removed
	private DataControl dataControl;
	// The parent of the resources block. This is required for updating selectedResources
	private DataControlWithResources parentDataControl;
	
	/*
	 * Elements for UNDO REDO
	 */
	private int lastSelectedResources;
	private int resourcesIndex;
	
	public DeleteResourcesBlockTool(List<Resources> resourcesList,List<ResourcesDataControl> resourcesDataControlList, DataControl dataControl,
			DataControlWithResources parentDataControl){
		this.resourcesDataControlList = resourcesDataControlList;
		this.resourcesList = resourcesList;
		this.dataControl = dataControl;
		this.parentDataControl = parentDataControl;
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
		boolean elementDeleted = false;

		// Delete the block only if it is not the last one
		lastSelectedResources = parentDataControl.getSelectedResources();
		if( resourcesList.size( ) > 1 ) {
			if( resourcesList.remove( dataControl.getContent( ) ) ) {
				resourcesIndex = resourcesDataControlList.indexOf( dataControl );
				resourcesDataControlList.remove( dataControl );

				int selectedResources = parentDataControl.getSelectedResources();
				// Decrease the selected index if necessary
				if( selectedResources > 0 && selectedResources >= resourcesIndex ){
					parentDataControl.setSelectedResources(selectedResources-1);
				}

				//controller.dataModified( );
				elementDeleted = true;
			}
		}

		// If it was the last one, show an error message
		else
			Controller.getInstance().showErrorDialog( TextConstants.getText( "Operation.DeleteResourcesTitle" ), TextConstants.getText( "Operation.DeleteResourcesErrorLastResources" ) );

		return elementDeleted;
	}

	@Override
	public boolean redoTool() {
		boolean redone = doTool();
		if (redone)
			Controller.getInstance().updatePanel();
		return redone;
	}

	@Override
	public boolean undoTool() {
		// Add deleted elements
		resourcesList.add( resourcesIndex, (Resources)dataControl.getContent() );
		resourcesDataControlList.add( resourcesIndex, (ResourcesDataControl)dataControl );
		parentDataControl.setSelectedResources(lastSelectedResources);
		Controller.getInstance().updatePanel();
		return true;
	}

	
}
