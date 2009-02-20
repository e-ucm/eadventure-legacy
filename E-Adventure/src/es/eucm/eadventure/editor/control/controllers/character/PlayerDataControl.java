package es.eucm.eadventure.editor.control.controllers.character;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.common.loader.Loader;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.control.tools.general.ChangeDescriptionTool;
import es.eucm.eadventure.editor.control.tools.general.ChangeDetailedDescriptionTool;
import es.eucm.eadventure.editor.control.tools.general.ChangeDocumentationTool;
import es.eucm.eadventure.editor.control.tools.general.ChangeNameTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class PlayerDataControl extends DataControlWithResources{

	/**
	 * Contained player data.
	 */
	private Player player;

	/**
	 * List of resources.
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
	 * Constructor.
	 * 
	 * @param player
	 *            Contained player data
	 */
	public PlayerDataControl( Player player ) {
		this.player = player;
		this.resourcesList = player.getResources( );

		selectedResources = 0;

		// Add a new resource if the list is empty
		if( resourcesList.size( ) == 0 )
			resourcesList.add( new Resources( ) );

		// Create the subcontrollers
		resourcesDataControlList = new ArrayList<ResourcesDataControl>( );
		for( Resources resources : resourcesList )
			resourcesDataControlList.add( new ResourcesDataControl( resources, Controller.PLAYER ) );
	}

	/**
	 * Returns the list of resources controllers.
	 * 
	 * @return Resources controllers
	 */
	public List<ResourcesDataControl> getResources( ) {
		return resourcesDataControlList;
	}

	/**
	 * Returns the number of resources blocks contained.
	 * 
	 * @return Number of resources blocks
	 */
	public int getResourcesCount( ) {
		return resourcesDataControlList.size( );
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
	 * Returns the path to the selected preview image.
	 * 
	 * @return Path to the image, null if not present
	 */
	public String getPreviewImage( ) {
		String previewImagePath = resourcesDataControlList.get( selectedResources ).getAssetPath( "standdown" );

		// Add the extension of the frame
		if( previewImagePath != null && !previewImagePath.toLowerCase().endsWith(".eaa"))
			previewImagePath += "_01.png";
		else if (previewImagePath != null) {
			return Loader.loadAnimation(AssetsController.getInputStreamCreator(), previewImagePath).getFrame(0).getUri();
		}

		return previewImagePath;
	}

	/**
	 * Returns the documentation of the player.
	 * 
	 * @return Player's documentation
	 */
	public String getDocumentation( ) {
		return player.getDocumentation( );
	}

	/**
	 * Returns the text front color for the player strings.
	 * 
	 * @return Text front color
	 */
	public Color getTextFrontColor( ) {
		return new Color( Integer.valueOf( player.getTextFrontColor( ).substring( 1 ), 16 ).intValue( ) );
	}

	/**
	 * Returns the text border color for the player strings.
	 * 
	 * @return Text front color
	 */
	public Color getTextBorderColor( ) {
		return new Color( Integer.valueOf( player.getTextBorderColor( ).substring( 1 ), 16 ).intValue( ) );
	}

	/**
	 * Returns the name of the player.
	 * 
	 * @return Player's name
	 */
	public String getName( ) {
		return player.getName( );
	}

	/**
	 * Returns the brief description of the player.
	 * 
	 * @return Player's description
	 */
	public String getBriefDescription( ) {
		return player.getDescription( );
	}

	/**
	 * Returns the detailed description of the player.
	 * 
	 * @return Player's detailed description
	 */
	public String getDetailedDescription( ) {
		return player.getDetailedDescription( );
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

	/**
	 * Sets the new documentation of the player.
	 * 
	 * @param documentation
	 *            Documentation of the player
	 */
	public void setDocumentation( String documentation ) {
		controller.addTool(new ChangeDocumentationTool(player, documentation));
	}

	/**
	 * Sets the text front color for the player strings.
	 * 
	 * @param textFrontColor
	 *            New text front color
	 */
	public void setTextFrontColor( Color textFrontColor ) {
		String red = Integer.toHexString( textFrontColor.getRed( ) );
		String green = Integer.toHexString( textFrontColor.getGreen( ) );
		String blue = Integer.toHexString( textFrontColor.getBlue( ) );

		if( red.length( ) == 1 )
			red = "0" + red;
		if( green.length( ) == 1 )
			green = "0" + green;
		if( blue.length( ) == 1 )
			blue = "0" + blue;

		player.setTextFrontColor( "#" + red + green + blue );
	}

	/**
	 * Sets the text border color for the player strings.
	 * 
	 * @param textBorderColor
	 *            New text border color
	 */
	public void setTextBorderColor( Color textBorderColor ) {
		String red = Integer.toHexString( textBorderColor.getRed( ) );
		String green = Integer.toHexString( textBorderColor.getGreen( ) );
		String blue = Integer.toHexString( textBorderColor.getBlue( ) );

		if( red.length( ) == 1 )
			red = "0" + red;
		if( green.length( ) == 1 )
			green = "0" + green;
		if( blue.length( ) == 1 )
			blue = "0" + blue;

		player.setTextBorderColor( "#" + red + green + blue );
	}

	/**
	 * Sets the new name of the player.
	 * 
	 * @param name
	 *            Name of the player
	 */
	public void setName( String name ) {
		controller.addTool(new ChangeNameTool(player, name));
	}

	/**
	 * Sets the new brief description of the player.
	 * 
	 * @param description
	 *            Description of the player
	 */
	public void setBriefDescription( String description ) {
		controller.addTool(new ChangeDescriptionTool(player, description));
	}

	/**
	 * Sets the new detailed description of the player.
	 * 
	 * @param detailedDescription
	 *            Detailed description of the player
	 */
	public void setDetailedDescription( String detailedDescription ) {
		controller.addTool(new ChangeDetailedDescriptionTool(player, detailedDescription));
	}
	
	/**
	 * Set the possibility to all conversation lines to be read by synthesizer
	 * 
	 * @param always
	 * 			Boolean value
	 */
	public void setAlwaysSynthesizer(boolean always){
		player.setAlwaysSynthesizer(always);
	}
	
	
	/**
	 * Sets the new voice for the player
	 * 
	 * @param voice
	 * 			a string with the valid voice
	 */
	public void setPlayerVoice(String voice){
		player.setVoice(voice);
	}
	
	/**
	 * Notify to all scenes that the player image has been changed
	 */
	public void playerImageChange(){
		String preview = getPreviewImage();
		if (preview!=null){
		for (SceneDataControl scene : Controller.getInstance().getSelectedChapterDataControl().getScenesList().getScenes()){
			scene.imageChangeNotify(preview);
		}
		}
		
	}
	
	/**
	 * Check if the engine must synthesizer all player conversation lines
	 * 
	 * @return
	 * 		if player must synthesizer all his lines
	 */
	public boolean isAlwaysSynthesizer(){
		return player.isAlwaysSynthesizer();
	}
	
	/**
	 * Gets the voice associated to player
	 * 
	 * @return
	 * 		string representing player voice
	 */
	public String getPlayerVoice(){
		return player.getVoice();
	}

	@Override
	public Object getContent( ) {
		return player;
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] {};
		//return new int[] { Controller.RESOURCES };
	}

	@Override
	public boolean canAddElement( int type ) {
		// No elements can be added
		//return type == Controller.RESOURCES;
		return false;
	}

	@Override
	public boolean canBeDeleted( ) {
		return false;
	}

	@Override
	public boolean canBeMoved( ) {
		return false;
	}

	@Override
	public boolean canBeRenamed( ) {
		return false;
	}

	@Override
	public boolean addElement( int type ) {
		boolean elementAdded = false;

		if( type == Controller.RESOURCES && !Controller.getInstance( ).isPlayTransparent( ) ) {
			Resources newResources = new Resources( );
			resourcesList.add( newResources );
			resourcesDataControlList.add( new ResourcesDataControl( newResources, Controller.PLAYER ) );
			//controller.dataModified( );
			elementAdded = true;
		}

		return elementAdded;
	}

	public boolean buildResourcesTab( ) {
		//System.out.println( "BUILD RESOURCS TAB=" + !Controller.getInstance( ).isPlayTransparent( ) );
		return !Controller.getInstance( ).isPlayTransparent( );
	}

	@Override
	public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {
		boolean elementDeleted = false;

		// Delete the block only if it is not the last one
		if( resourcesList.size( ) > 1 ) {
			if( resourcesList.remove( dataControl.getContent( ) ) ) {
				int resourcesIndex = resourcesDataControlList.indexOf( dataControl );
				resourcesDataControlList.remove( dataControl );

				// Decrease the selected index if necessary
				if( selectedResources > 0 && selectedResources >= resourcesIndex )
					selectedResources--;

				//controller.dataModified( );
				elementDeleted = true;
			}
		}

		// If it was the last one, show an error message
		else
			controller.showErrorDialog( TextConstants.getText( "Operation.DeleteResourcesTitle" ), TextConstants.getText( "Operation.DeleteResourcesErrorLastResources" ) );

		return elementDeleted;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = resourcesList.indexOf( dataControl.getContent( ) );

		if( elementIndex > 0 ) {
			resourcesList.add( elementIndex - 1, resourcesList.remove( elementIndex ) );
			resourcesDataControlList.add( elementIndex - 1, resourcesDataControlList.remove( elementIndex ) );
			//controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = resourcesList.indexOf( dataControl.getContent( ) );

		if( elementIndex < resourcesList.size( ) - 1 ) {
			resourcesList.add( elementIndex + 1, resourcesList.remove( elementIndex ) );
			resourcesDataControlList.add( elementIndex + 1, resourcesDataControlList.remove( elementIndex ) );
			//controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public String renameElement( String name ) {
		return null;
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
	// Do nothing
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean valid = true;

		// Iterate through the resources
		for( int i = 0; i < resourcesDataControlList.size( ); i++ ) {
			String resourcesPath = currentPath + " >> " + TextConstants.getElementName( Controller.RESOURCES ) + " #" + ( i + 1 );
			valid &= resourcesDataControlList.get( i ).isValid( resourcesPath, incidences );
		}

		return valid;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		// Iterate through the resources
		for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
			count += resourcesDataControl.countAssetReferences( assetPath );

		return count;
	}
	
	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		// Iterate through the resources
		for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
			resourcesDataControl.getAssetReferences( assetPaths, assetTypes );
	}


	@Override
	public void deleteAssetReferences( String assetPath ) {
		// Iterate through the resources
		for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
			resourcesDataControl.deleteAssetReferences( assetPath );
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
		// It is duplicated as a character
	}

	@Override
	public void recursiveSearch() {
		check(this.getDocumentation(), "Documentation");
		check(this.getBriefDescription(), "Brief Description");
		check(this.getDetailedDescription(), "Detailed Description");
		check(this.getName(), "Name");
		check(this.getPlayerVoice(), "Player Voice");
	}
}