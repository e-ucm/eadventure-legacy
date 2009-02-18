package es.eucm.eadventure.editor.control.tools.general.resources;

import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.data.AssetInformation;
import es.eucm.eadventure.editor.gui.treepanel.TreeNodeControl;

/**
 * Abstract class for Resources modification. It contains the common data that tools EditResourceTool,
 * SetResourceTool and DeleteResourceTool will use.
 * @author Javier
 *
 */
public abstract class ResourcesTool implements Tool{
	/**
	 * Controller
	 */
	protected Controller controller;
	
	/**
	 * Contained resources. This field is kept updated all the time.
	 */
	protected Resources resources;
	
	/**
	 * Old resources. This is a backup copy that is done when the tool is built (for undo)
	 */
	protected Resources oldResources;

	/**
	 * The assets information of the resources.
	 */
	protected AssetInformation[] assetsInformation;

	/**
	 * Conditions controller.
	 */
	protected ConditionsController conditionsController;
	
	/**
	 * indicates if the resource block belongs to a NPC, the player or other element
	 */
	protected int resourcesType;
	
	/**
	 * The index of the resource to be modified
	 */
	protected int index;
	
	/**
	 * Default constructor
	 * @throws CloneNotSupportedException 
	 */
	public ResourcesTool (Resources resources, AssetInformation[] assetsInformation, ConditionsController conditionsController, int resourcesType, int index) throws CloneNotSupportedException{
		this.resources = resources;
		this.assetsInformation = assetsInformation;
		this.conditionsController = conditionsController;
		this.resourcesType = resourcesType;
		this.controller = Controller.getInstance();
		this.index = index;
		this.oldResources = (Resources)(resources.clone());
	}
	
	@Override
	public boolean undoTool() {
		// Restores the resources object with the information stored in oldResoures
		try {
			Resources temp = (Resources)(resources.clone());
			resources.clearAssets();
			String[] oldResourceTypes = oldResources.getAssetTypes();
			for (String type: oldResourceTypes){
				resources.addAsset(type, oldResources.getAssetPath(type));
			}
			
			// Update older data
			oldResources.clearAssets();
			oldResourceTypes = temp.getAssetTypes();
			for (String type: oldResourceTypes){
				oldResources.addAsset(type, temp.getAssetPath(type));
			}
			controller.reloadPanel();
			return true;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean redoTool() {
		return undoTool();
	}
	
	@Override
	public String getToolName() {
		return TextConstants.getEditionToolName(getClass());
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
	
}
