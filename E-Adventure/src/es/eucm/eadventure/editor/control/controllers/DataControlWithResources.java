package es.eucm.eadventure.editor.control.controllers;

import java.util.List;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;

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
	
	public boolean duplicateResources(DataControl dataControl) {
		if (!(dataControl instanceof ResourcesDataControl))
			return false;
		
		try {
			Resources newElement = (Resources) (((Resources) (dataControl.getContent())).clone());
			resourcesList.add(newElement);
			resourcesDataControlList.add( new ResourcesDataControl(newElement, ((ResourcesDataControl) dataControl).getResourcesType()));
			return true;
		} catch (CloneNotSupportedException e) {
			ReportDialog.GenerateErrorReport(e, true, "Could not clone resources");	
			return false;
		} 		
	}
	
}