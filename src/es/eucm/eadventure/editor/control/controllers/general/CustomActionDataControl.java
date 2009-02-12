package es.eucm.eadventure.editor.control.controllers.general;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.CustomAction;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.Controller;

public class CustomActionDataControl extends ActionDataControl {

	/**
	 * Contained customAction structure
	 */
	private CustomAction customAction;
	
	/**
	 * List of resources for the customAction
	 */
	private List<Resources> resourcesList;
	
	/**
	 * List of resources controllers.
	 */
	private List<ResourcesDataControl> resourcesDataControlList;
	
	/**
	 * The resources that must be used in the previews.
	 */
	private int selectedResources;

	/**
	 * Default constructor
	 * 
	 * @param action the custom Action
	 */
	public CustomActionDataControl(CustomAction action) {
		super(action);
		//customAction = new CustomAction(action);
		customAction = action;
		this.resourcesList = customAction.getResources( );
		if (this.resourcesList.size() == 0)
			this.resourcesList.add(new Resources());
		selectedResources = 0;

		resourcesDataControlList = new ArrayList<ResourcesDataControl>();
		for (Resources resources: resourcesList) {
			resourcesDataControlList.add(new ResourcesDataControl( resources, Controller.ACTION_CUSTOM ));
		}
	}
		
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		if (!name.equals(customAction.getName())) {
			customAction.setName(name);
			controller.updateTree();
			controller.dataModified();
		}
	}

	@Override
	public ResourcesDataControl getLastResources() {
		return resourcesDataControlList.get( resourcesDataControlList.size( ) - 1 );
	}

	@Override
	public List<ResourcesDataControl> getResources() {
		return resourcesDataControlList;
	}

	@Override
	public int getResourcesCount() {
		return resourcesDataControlList.size();
	}

	@Override
	public int getSelectedResources() {
		return selectedResources;
	}

	@Override
	public void setSelectedResources(int selectedResources) {
		this.selectedResources = selectedResources;
	}

	/**
	 * @return the value of name
	 */
	public String getName() {
		return customAction.getName();
	}



}
