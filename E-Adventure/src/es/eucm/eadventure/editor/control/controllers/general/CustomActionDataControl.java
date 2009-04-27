package es.eucm.eadventure.editor.control.controllers.general;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.CustomAction;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeNameTool;

public class CustomActionDataControl extends ActionDataControl {

	/**
	 * Contained customAction structure
	 */
	private CustomAction customAction;
	
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
		controller.addTool(new ChangeNameTool(customAction, name));
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = super.countAssetReferences(assetPath);
		
		for (ResourcesDataControl resources : resourcesDataControlList)
			count += resources.countAssetReferences(assetPath);
		
		return count;
	}

	/**
	 * @return the value of name
	 */
	public String getName() {
		return customAction.getName();
	}

	@Override
	public void recursiveSearch() {
		super.recursiveSearch();
		check(this.getName(), TextConstants.getText("Search.Name"));
	}

	@Override
	public List<DataControl> getPathToDataControl(DataControl dataControl) {
		return getPathFromChild(dataControl, resourcesDataControlList);
	}

}
