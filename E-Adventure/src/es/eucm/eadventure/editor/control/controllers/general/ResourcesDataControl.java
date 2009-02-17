package es.eucm.eadventure.editor.control.controllers.general;

import java.io.File;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;
import es.eucm.eadventure.editor.gui.assetchooser.AssetChooser;

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

	/**
	 * Conditions controller.
	 */
	private ConditionsController conditionsController;
	
	private int resourcesType;

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
				assetsInformation = new AssetInformation[] {new AssetInformation( TextConstants.getText( "Resources.DescriptionButtonNormal"), "buttonNormal", true, AssetsController.CATEGORY_IMAGE, AssetsController.FILTER_PNG),
						new AssetInformation(TextConstants.getText("Resources.DescriptionButtonOver"), "buttonOver", true, AssetsController.CATEGORY_IMAGE, AssetsController.FILTER_PNG),
						new AssetInformation(TextConstants.getText("Resources.DescriptionButtonPressed"), "buttonPressed", true, AssetsController.CATEGORY_IMAGE, AssetsController.FILTER_PNG),
						new AssetInformation(TextConstants.getText("Resources.DescriptionActionAnimation"), "actionAnimation", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_NONE)};
				 break;
			case Controller.CUTSCENE_VIDEO:
				assetsInformation = new AssetInformation[] { new AssetInformation( TextConstants.getText( "Resources.DescriptionVideoscenes" ), "video", true, AssetsController.CATEGORY_VIDEO, AssetsController.FILTER_NONE ) };
				break;
			case Controller.BOOK:
				assetsInformation = new AssetInformation[] { new AssetInformation( TextConstants.getText( "Resources.DescriptionBookBackground" ), "background", true, AssetsController.CATEGORY_BACKGROUND, AssetsController.FILTER_JPG ) };
				break;
			case Controller.ITEM:
				assetsInformation = new AssetInformation[] { new AssetInformation( TextConstants.getText( "Resources.DescriptionItemImage" ), "image", false, AssetsController.CATEGORY_IMAGE, AssetsController.FILTER_NONE ), new AssetInformation( TextConstants.getText( "Resources.DescriptionItemIcon" ), "icon", false, AssetsController.CATEGORY_ICON, AssetsController.FILTER_NONE ) };
				break;
			case Controller.PLAYER:
			case Controller.NPC:
				assetsInformation = new AssetInformation[] { new AssetInformation( TextConstants.getText( "Resources.DescriptionCharacterAnimationStandUp" ), "standup", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_PNG ), new AssetInformation( TextConstants.getText( "Resources.DescriptionCharacterAnimationStandDown" ), "standdown", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_PNG ), new AssetInformation( TextConstants.getText( "Resources.DescriptionCharacterAnimationStandRight" ), "standright", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_PNG ), new AssetInformation( TextConstants.getText( "Resources.DescriptionCharacterAnimationSpeakUp" ), "speakup", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_PNG ), new AssetInformation( TextConstants.getText( "Resources.DescriptionCharacterAnimationSpeakDown" ), "speakdown", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_PNG ), new AssetInformation( TextConstants.getText( "Resources.DescriptionCharacterAnimationSpeakRight" ), "speakright", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_PNG ), new AssetInformation( TextConstants.getText( "Resources.DescriptionCharacterAnimationUseRight" ), "useright", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_PNG ), new AssetInformation( TextConstants.getText( "Resources.DescriptionCharacterAnimationWalkUp" ), "walkup", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_PNG ), new AssetInformation( TextConstants.getText( "Resources.DescriptionCharacterAnimationWalkDown" ), "walkdown", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_PNG ), new AssetInformation( TextConstants.getText( "Resources.DescriptionCharacterAnimationWalkRight" ), "walkright", false, AssetsController.CATEGORY_ANIMATION, AssetsController.FILTER_PNG ) };
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
	 */
	public void editAssetPath( int index ) {
		// Get the list of assets from the ZIP file
		//String[] assetFilenames = AssetsController.getAssetFilenames( assetsInformation[index].category, assetsInformation[index].filter );
		//String[] assetPaths = AssetsController.getAssetsList( assetsInformation[index].category, assetsInformation[index].filter );

		// If the list of assets is empty, show an error message
		//if( assetFilenames.length == 0 )
		//	controller.showErrorDialog( TextConstants.getText( "Resources.EditAsset" ), TextConstants.getText( "Resources.ErrorNoAssets" ) );

		// If not empty, select one of them
		//else {
		//TODO MODIFIED
		//if (assetsInformation[index].category==AssetsController.CATEGORY_ANIMATION){
		//	AnimationChooser chooser = new AnimationChooser();
		//	chooser.showOpenDialog( controller.peekWindow( ) );

		//}
		//else{
		// Let the user choose between the assets
		//String selectedAsset = controller.showInputDialog( TextConstants.getText( "Resources.EditAsset" ), TextConstants.getText( "Resources.EditAssetMessage" ), assetFilenames );
		String selectedAsset = null;
		AssetChooser chooser = AssetsController.getAssetChooser( assetsInformation[index].category, assetsInformation[index].filter );
		int option = chooser.showAssetChooser( controller.peekWindow( ) );
		//In case the asset was selected from the zip file
		if( option == AssetChooser.ASSET_FROM_ZIP ) {
			selectedAsset = chooser.getSelectedAsset( );
		}

		//In case the asset was not in the zip file: first add it
		else if( option == AssetChooser.ASSET_FROM_OUTSIDE ) {
			boolean added = AssetsController.addSingleAsset( assetsInformation[index].category, chooser.getSelectedFile( ).getAbsolutePath( ) );
			if( added ) {
				selectedAsset = chooser.getSelectedFile( ).getName( );
			}
		}

		// If a file was selected
		if( selectedAsset != null ) {
			// Take the index of the selected asset
			String[] assetFilenames = AssetsController.getAssetFilenames( assetsInformation[index].category, assetsInformation[index].filter );
			String[] assetPaths = AssetsController.getAssetsList( assetsInformation[index].category, assetsInformation[index].filter );
			int assetIndex = -1;
			for( int i = 0; i < assetFilenames.length; i++ )
				if( assetFilenames[i].equals( selectedAsset ) )
					assetIndex = i;

			// Store the data in the resources block (removing the suffix if necessary)
			if( assetsInformation[index].category == AssetsController.CATEGORY_ANIMATION ){
				resources.addAsset( assetsInformation[index].name, AssetsController.removeSuffix( assetPaths[assetIndex] ) );
				
				// For player and character resources block, check if the other animations are set. When any are set, ask the user to set them automatically
				if (resourcesType == Controller.PLAYER || resourcesType == Controller.NPC){
					boolean someAnimationSet = false;
					for (int i=0; i<assetsInformation.length; i++){
						if (i!=index && resources.getAssetPath( assetsInformation[i].name )!= null &&
								!resources.getAssetPath( assetsInformation[i].name ).equals( "" )){
							someAnimationSet = true;
							break;
						}
					}
					
					if (!someAnimationSet &&
							controller.showStrictConfirmDialog( TextConstants.getText( "Operation.SetAllAnimations.Title" ), 
									TextConstants.getText( "Operation.SetAllAnimations.Message" ) )){
						for (int i=0; i<assetsInformation.length; i++){
							if (i!=index){
								resources.addAsset( assetsInformation[i].name, AssetsController.removeSuffix( assetPaths[assetIndex] ) );
							}
						}
						
					}
				}
			}
			else
				resources.addAsset( assetsInformation[index].name, assetPaths[assetIndex] );
			controller.dataModified( );
		}
	}
	
	
	//}
	//}

	/**
	 * Deletes the path of the given asset.
	 * 
	 * @param index
	 *            Index of the asset
	 */
	public void deleteAssetPath( int index ) {
		// If the given asset is not empty, delete it
		if( resources.getAssetPath( assetsInformation[index].name ) != null ) {
			resources.deleteAsset( assetsInformation[index].name );
			controller.dataModified( );
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
	public boolean addElement( int type ) {
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
		// Search in the types of the resources
		for( String type : resources.getAssetTypes( ) ) {
			if( resources.getAssetPath( type ).equals( assetPath ) ) {
				resources.deleteAsset( type );
				controller.dataModified( );
			}
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

	/**
	 * This class holds the information about an asset. It stores the description of the asset, the identifier (name) of
	 * the asset, and its type.
	 */
	private class AssetInformation {

		/**
		 * Textual description of the asset.
		 */
		public String description;

		/**
		 * Name of the asset.
		 */
		public String name;

		/**
		 * True if the asset is necessary for the resources block to be valid.
		 */
		public boolean assetNecessary;

		/**
		 * Category of the asset.
		 */
		public int category;

		/**
		 * Specific filter for the category
		 */
		public int filter;

		/**
		 * Constructor.
		 * 
		 * @param description
		 *            Description of the asset
		 * @param name
		 *            Name of the asset
		 * @param assetNecessary
		 *            True if the asset is necessary for the resources to be valid
		 * @param category
		 *            Category of the asset
		 * @param filter
		 *            Specific filter for the category
		 */
		public AssetInformation( String description, String name, boolean assetNecessary, int category, int filter ) {
			this.description = description;
			this.name = name;
			this.assetNecessary = assetNecessary;
			this.category = category;
			this.filter = filter;
		}
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}

	public void setAssetPath(String filename, int index) {
		AssetsController.addSingleAsset( assetsInformation[index].category, filename );
		String selectedAsset = (new File(filename)).getName();
		// If a file was selected
		if( selectedAsset != null ) {
			// Take the index of the selected asset
			String[] assetFilenames = AssetsController.getAssetFilenames( assetsInformation[index].category, assetsInformation[index].filter );
			String[] assetPaths = AssetsController.getAssetsList( assetsInformation[index].category, assetsInformation[index].filter );
			int assetIndex = -1;
			for( int i = 0; i < assetFilenames.length; i++ )
				if( assetFilenames[i].equals( selectedAsset ) )
					assetIndex = i;
			
			resources.addAsset( assetsInformation[index].name, assetPaths[assetIndex] );
			controller.dataModified( );
		}
	}
}
