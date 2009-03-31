package es.eucm.eadventure.editor.control.controllers.general;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.tools.general.assets.DeleteAssetReferencesInResources;
import es.eucm.eadventure.editor.control.tools.general.assets.DeleteResourceTool;
import es.eucm.eadventure.editor.control.tools.general.assets.EditResourceTool;
import es.eucm.eadventure.editor.control.tools.general.assets.SelectResourceTool;
import es.eucm.eadventure.editor.data.AssetInformation;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

/**
 * Microcontroller for the resources data.
 * 
 * @author Bruno Torijano Bueno
 */
public class ResourcesDataControl extends DataControl {

	/**
	 * Contained resources.
	 */
	private Resources resources;

	/**
	 * The assets information of the resources.
	 */
	private AssetInformation[] assetsInformation;

	private int[][] assetsGroups = null;
	
	private String[] groupsInfo = null;
	
	/**
	 * Conditions controller.
	 */
	private ConditionsController conditionsController;
	
	private int resourcesType;

	private Map<String, String> imageIconMap;
	
	/**
	 * Contructor.
	 * 
	 * @param resources
	 *            Resources of the data control structure
	 * @param resourcesType
	 *            Type of the resources
	 */
	public ResourcesDataControl( Resources resources, int resourcesType ) {
		this.resources = resources;
		this.resourcesType = resourcesType;
		
		// Initialize the assetsInformation, depending on the assets type
		switch( resourcesType ) {
			case Controller.SCENE:
				assetsInformation = new AssetInformation[] { new AssetInformation( TextConstants.getText( "Resources.DescriptionSceneBackground" ), "background", true, AssetsController.CATEGORY_BACKGROUND, AssetsController.FILTER_JPG ), new AssetInformation( TextConstants.getText( "Resources.DescriptionSceneForeground" ), "foreground", false, AssetsController.CATEGORY_BACKGROUND, AssetsController.FILTER_PNG ), /*new AssetInformation( TextConstants.getText( "Resources.DescriptionSceneHardMap" ), "hardmap", false, AssetsController.CATEGORY_BACKGROUND, AssetsController.FILTER_PNG ), */new AssetInformation( TextConstants.getText( "Resources.DescriptionSceneMusic" ), "bgmusic", false, AssetsController.CATEGORY_AUDIO, AssetsController.FILTER_NONE ) };
				break;
			case Controller.CUTSCENE_SLIDES:
				assetsInformation = new AssetInformation[] { new AssetInformation( TextConstants.getText( "Resources.DescriptionSlidesceneSlides" ), "slides", true, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_JPG ), new AssetInformation( TextConstants.getText( "Resources.DescriptionSceneMusic" ), "bgmusic", false, AssetsController.CATEGORY_AUDIO, AssetsController.FILTER_NONE ) };
				break;
			case Controller.ACTION_CUSTOM:
			case Controller.ACTION_CUSTOM_INTERACT:
				assetsInformation = new AssetInformation[] {
						new AssetInformation( TextConstants.getText( "Resources.DescriptionButtonNormal"), "buttonNormal", true, AssetsController.CATEGORY_BUTTON, AssetsController.FILTER_PNG),
						new AssetInformation(TextConstants.getText("Resources.DescriptionButtonOver"), "buttonOver", true, AssetsController.CATEGORY_BUTTON, AssetsController.FILTER_PNG),
						new AssetInformation(TextConstants.getText("Resources.DescriptionButtonPressed"), "buttonPressed", true, AssetsController.CATEGORY_BUTTON, AssetsController.FILTER_PNG),
						new AssetInformation(TextConstants.getText("Resources.DescriptionActionAnimation"), "actionAnimation", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_NONE),
						new AssetInformation(TextConstants.getText("Resources.DescriptionActionAnimationLeft"), "actionAnimationLeft", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_NONE) };
				assetsGroups = new int[][]{{0,1,2},{3,4}};
				groupsInfo = new String[]{TextConstants.getText("Resources.Button"), TextConstants.getText("Resources.Animations")};
				break;
			case Controller.CUTSCENE_VIDEO:
				assetsInformation = new AssetInformation[] { new AssetInformation( TextConstants.getText( "Resources.DescriptionVideoscenes" ), "video", true, AssetsController.CATEGORY_VIDEO, AssetsController.FILTER_NONE ) };
				break;
			case Controller.BOOK:
				assetsInformation = new AssetInformation[] { new AssetInformation( TextConstants.getText( "Resources.DescriptionBookBackground" ), "background", true, AssetsController.CATEGORY_BACKGROUND, AssetsController.FILTER_JPG ) };
				break;
			case Controller.ITEM:
				assetsInformation = new AssetInformation[] { new AssetInformation( TextConstants.getText( "Resources.DescriptionItemImage" ), "image", false, AssetsController.CATEGORY_IMAGE, AssetsController.FILTER_NONE ), new AssetInformation( TextConstants.getText( "Resources.DescriptionItemIcon" ), "icon", false, AssetsController.CATEGORY_ICON, AssetsController.FILTER_NONE ) };
				imageIconMap = new HashMap<String, String>();
				imageIconMap.put("icon", "image");
				break;
			case Controller.PLAYER:
			case Controller.NPC:
				assetsInformation = new AssetInformation[] { 
						new AssetInformation( TextConstants.getText( "Resources.DescriptionCharacterAnimationStandUp" ), "standup", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_PNG ), 
						new AssetInformation( TextConstants.getText( "Resources.DescriptionCharacterAnimationStandDown" ), "standdown", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_PNG ), 
						new AssetInformation( TextConstants.getText( "Resources.DescriptionCharacterAnimationStandRight" ), "standright", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_PNG ), 
						new AssetInformation( TextConstants.getText( "Resources.DescriptionCharacterAnimationStandLeft" ), "standleft", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_PNG ), 
						new AssetInformation( TextConstants.getText( "Resources.DescriptionCharacterAnimationSpeakUp" ), "speakup", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_PNG ), 
						new AssetInformation( TextConstants.getText( "Resources.DescriptionCharacterAnimationSpeakDown" ), "speakdown", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_PNG ), 
						new AssetInformation( TextConstants.getText( "Resources.DescriptionCharacterAnimationSpeakRight" ), "speakright", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_PNG ), 
						new AssetInformation( TextConstants.getText( "Resources.DescriptionCharacterAnimationSpeakLeft" ), "speakleft", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_PNG ), 
						new AssetInformation( TextConstants.getText( "Resources.DescriptionCharacterAnimationUseRight" ), "useright", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_PNG ), 
						new AssetInformation( TextConstants.getText( "Resources.DescriptionCharacterAnimationUseLeft" ), "useleft", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_PNG ), 
						new AssetInformation( TextConstants.getText( "Resources.DescriptionCharacterAnimationWalkUp" ), "walkup", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_PNG ), 
						new AssetInformation( TextConstants.getText( "Resources.DescriptionCharacterAnimationWalkDown" ), "walkdown", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_PNG ), 
						new AssetInformation( TextConstants.getText( "Resources.DescriptionCharacterAnimationWalkRight" ), "walkright", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_PNG ),
						new AssetInformation( TextConstants.getText( "Resources.DescriptionCharacterAnimationWalkLeft" ), "walkleft", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_PNG ) };
				assetsGroups = new int[][]{{0,1,2,3},{4,5,6,7},{8,9},{10,11,12,13}};
				groupsInfo = new String[] { TextConstants.getText("Resources.StandingAnimations"), TextConstants.getText("Resources.SpeakingAnimations"), TextConstants.getText("Resources.UsingAnimations"), TextConstants.getText("Resources.WalkingAnimations") };
				break;
			case Controller.ATREZZO:
				assetsInformation = new AssetInformation[] { new AssetInformation( TextConstants.getText( "Resources.DescriptionItemImage" ), "image", false, AssetsController.CATEGORY_IMAGE, AssetsController.FILTER_NONE )};
				break;
		}

		// Create subcontrollers
		conditionsController = new ConditionsController( resources.getConditions( ) );
	}

	/**
	 * Returns the conditions microcontroller.
	 * 
	 * @return Conditions microcontroller
	 */
	public ConditionsController getConditions( ) {
		return conditionsController;
	}

	/**
	 * Returns the number of assets that the resources block has.
	 * 
	 * @return Number of assets
	 */
	public int getAssetCount( ) {
		return assetsInformation.length;
	}

	/**
	 * Returns the description of the asset in the given position.
	 * 
	 * @param index
	 *            Index of the asset
	 * @return Description of the asset
	 */
	public String getAssetDescription( int index ) {
		return assetsInformation[index].description;
	}

	/**
	 * Returns the category of the asset in the given position.
	 * 
	 * @param index
	 *            Index of the asset
	 * @return Category of the asset
	 */
	public int getAssetCategory( int index ) {
		return assetsInformation[index].category;
	}

	/**
	 * Returns the filter of the asset in the given position.
	 * 
	 * @param index
	 *            Index of the asset
	 * @return Filter of the asset
	 */
	public int getAssetFilter( int index ) {
		return assetsInformation[index].filter;
	}

	/**
	 * Returns the relative path of the given asset.
	 * 
	 * @param asset
	 *            Name of the asset
	 * @return The path to the resource if present, null otherwise
	 */
	public String getAssetPath( String asset ) {
		return resources.getAssetPath( asset );
	}

	/**
	 * Returns the relative path of the given asset (used for display).
	 * 
	 * @param index
	 *            Index of the asset
	 * @return The path to the resource if present, null otherwise
	 */
	public String getAssetPath( int index ) {
		return resources.getAssetPath( assetsInformation[index].name );
	}

	/**
	 * Shows a dialog to choose a new path for the given asset.
	 * 
	 * @param index
	 *            Index of the asset
	 * @throws CloneNotSupportedException 
	 */
	public void editAssetPath( int index )  {
		try {
			controller.addTool(new SelectResourceTool(resources, assetsInformation, resourcesType,index));
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Deletes the path of the given asset.
	 * 
	 * @param index
	 *            Index of the asset
	 */
	public void deleteAssetPath( int index ) {
		try {
			controller.addTool(new DeleteResourceTool(resources, assetsInformation, index));
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object getContent( ) {
		return resources;
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] {};
	}

	@Override
	public boolean canAddElement( int type ) {
		return false;
	}

	@Override
	public boolean canBeDeleted( ) {
		return true;
	}

	@Override
	public boolean canBeMoved( ) {
		return true;
	}

	@Override
	public boolean canBeRenamed( ) {
		return false;
	}

	@Override
	public boolean addElement( int type, String id ) {
		return false;
	}

	@Override
	public boolean deleteElement( DataControl dataControl , boolean askConfirmation) {
		return false;
	}
	
	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		return false;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		return false;
	}

	@Override
	public String renameElement( String name ) {
		return null;
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
		// Update the flag summary with the conditions
		ConditionsController.updateVarFlagSummary( varFlagSummary, resources.getConditions( ) );
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean valid = true;

		// Check the assets that are necessary
		for( AssetInformation assetInformation : assetsInformation ) {
			// If the asset is necessary and the value is null, set to invalid
			if( assetInformation.assetNecessary && resources.getAssetPath( assetInformation.name ) == null ) {
				valid = false;

				// Store the incidence
				if( incidences != null )
					incidences.add( currentPath + " >> " + TextConstants.getText( "Operation.AdventureConsistencyErrorResources", assetInformation.name ) );
			}
		}

		return valid;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		// Search in the types of the resources
		for( String type : resources.getAssetTypes( ) )
			if( resources.getAssetPath( type ).equals( assetPath ) )
				count++;

		return count;
	}
	
	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		// Search in the assetsInformation
		for( int index=0; index<assetsInformation.length; index++ ){
			if ( resources.getAssetPath( assetsInformation[index].name )!=null &&
					!resources.getAssetPath( assetsInformation[index].name ).equals( "" )){
				String assetPath = resources.getAssetPath( assetsInformation[index].name );
				int assetType = assetsInformation[index].category;
				
				// Search that the assetPath has not been previously added
				boolean add = true;
				for (String asset: assetPaths){
					if (asset.equals( assetPath )){
						add = false;
						break;
					}
				}
				if (add){
					int last = assetPaths.size( );
					assetPaths.add( last, assetPath );
					assetTypes.add( last, assetType );
				}
			}
		}
	}

	@Override
	public void deleteAssetReferences( String assetPath ) {
		try {
			controller.addTool(new DeleteAssetReferencesInResources(resources, assetPath));
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int countIdentifierReferences( String id ) {
		return 0;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
	// Do nothing
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
	// Do nothing
	}


	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}

	/**
	 * Method that is invoked only by the "Edit" button
	 * @param filename
	 * @param index
	 */
	public void setAssetPath(String filename, int index) {
		try {
			controller.addTool(new EditResourceTool(resources, assetsInformation, index, filename));
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void recursiveSearch() {
		check(this.getConditions(), TextConstants.getText("Search.Conditions"));
		for (int i = 0; i < this.getAssetCount(); i++) {
			check(this.getAssetDescription(i), TextConstants.getText("Search.AssetDescription"));
			check(this.getAssetPath(i), TextConstants.getText("Search.AssetPath"));
		}
	}

	public int getAssetGroupCount() {
		if (assetsGroups == null)
			return 1;
		else
			return assetsGroups.length;
	}

	public String getGroupInfo(int i) {
		return groupsInfo[i];
	}

	public int getGroupAssetCount(int selectedIndex) {
		return assetsGroups[selectedIndex].length;
	}
	
	public int getAssetIndex(int group, int asset) {
		if (assetsGroups == null)
			return asset;
		return assetsGroups[group][asset];
	}
	
	public boolean isIconFromImage(int i) {
		if (imageIconMap == null)
			return false;
		return imageIconMap.get(assetsInformation[i].name) != null;
	}
	
	public int getOriginalImage(int i) {
		String name = imageIconMap.get(assetsInformation[i].name);
		for (int j = 0; j < assetsInformation.length; j++) {
			if (assetsInformation[j].name.equals(name))
				return j;
		}
		return -1;
	}
	
}
