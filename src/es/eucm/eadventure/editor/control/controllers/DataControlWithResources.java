package es.eucm.eadventure.editor.control.controllers;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.control.tools.general.assets.DeleteResourcesBlockTool;
import es.eucm.eadventure.editor.control.tools.general.assets.DuplicateResourcesBlockTool;

public abstract class DataControlWithResources extends DataControl {

	/**
	 * List of resources.
	 */
	protected List<Resources> resourcesList;

	/**
	 * List of resources controllers.
	 */
	protected List<ResourcesDataControl> resourcesDataControlList;

	/**
	 * The resources that must be used in the previews.
	 */
	protected int selectedResources;

	public List<ResourcesDataControl> getResources( ) {
		return resourcesDataControlList;
	}

	public int getResourcesCount( ) {
		return resourcesDataControlList.size();
	}

	/**
	 * Returns the last resources controller of the list.
	 * 
	 * @return Last resources controller
	 */
	public ResourcesDataControl getLastResources( ) {
		return resourcesDataControlList.get( resourcesDataControlList.size( ) - 1 );
	}

	/**
	 * Returns the selected resources block of the list.
	 * 
	 * @return Selected block of resources
	 */
	public int getSelectedResources( ) {
		return selectedResources;
	}

	/**
	 * Sets the new selected resources block of the list.
	 * 
	 * @param selectedResources
	 *            New selected block of resources
	 */
	public void setSelectedResources( int selectedResources ) {
		this.selectedResources = selectedResources;
	}
	
	@Override
	// This method only caters for deleting RESOURCES. Subclasses should override this method
	// to implement removal of other element types
	public boolean deleteElement( DataControl dataControl , boolean askConfirmation) {
		return controller.addTool( new DeleteResourcesBlockTool(resourcesList, resourcesDataControlList, dataControl, this) );
	}
	
	public boolean duplicateResources(DataControl dataControl) {
		return controller.addTool( new DuplicateResourcesBlockTool(dataControl, resourcesList, resourcesDataControlList, this) );
	}
	
}