package es.eucm.eadventure.editor.control.controllers.character;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.common.loader.Loader;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.general.ActionsListDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.control.tools.generic.ChangeBooleanValueTool;
import es.eucm.eadventure.editor.control.tools.generic.ChangeStringValueTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class NPCDataControl extends DataControlWithResources {

	/**
	 * Contained NPC data.
	 */
	private NPC npc;

	/**
	 * Actions list controller.
	 */
	private ActionsListDataControl actionsListDataControl;

	/**
	 * Constructor
	 * 
	 * @param npc
	 *            Contained NPC data
	 */
	public NPCDataControl( NPC npc ) {
		this.npc = npc;
		this.resourcesList = npc.getResources( );

		selectedResources = 0;

		// Add a new resource if the list is empty
		if( resourcesList.size( ) == 0 )
			resourcesList.add( new Resources( ) );

		// Create the subcontrollers
		resourcesDataControlList = new ArrayList<ResourcesDataControl>( );
		for( Resources resources : resourcesList )
			resourcesDataControlList.add( new ResourcesDataControl( resources, Controller.NPC ) );

		actionsListDataControl = new ActionsListDataControl( npc.getActions( ) , this);
	}

	/**
	 * Returns the actions list controller.
	 * 
	 * @return Actions list controller
	 */
	public ActionsListDataControl getActionsList( ) {
		return actionsListDataControl;
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
	 * Returns the id of the NPC.
	 * 
	 * @return NPC's id
	 */
	public String getId( ) {
		return npc.getId( );
	}

	/**
	 * Returns the documentation of the NPC.
	 * 
	 * @return NPC's documentation
	 */
	public String getDocumentation( ) {
		return npc.getDocumentation( );
	}

	/**
	 * Returns the text front color for the player strings.
	 * 
	 * @return Text front color
	 */
	public Color getTextFrontColor( ) {
		return new Color( Integer.valueOf( npc.getTextFrontColor( ).substring( 1 ), 16 ).intValue( ) );
	}

	/**
	 * Returns the text border color for the player strings.
	 * 
	 * @return Text front color
	 */
	public Color getTextBorderColor( ) {
		return new Color( Integer.valueOf( npc.getTextBorderColor( ).substring( 1 ), 16 ).intValue( ) );
	}

	/**
	 * Returns the name of the item.
	 * 
	 * @return Character's name
	 */
	public String getName( ) {
		return npc.getName( );
	}

	/**
	 * Returns the brief description of the character.
	 * 
	 * @return Character's description
	 */
	public String getBriefDescription( ) {
		return npc.getDescription( );
	}

	/**
	 * Returns the detailed description of the character.
	 * 
	 * @return Character's detailed description
	 */
	public String getDetailedDescription( ) {
		return npc.getDetailedDescription( );
	}

	/**
	 * Check if the engine must synthesizer all current npc conversation lines
	 * 
	 * @return
	 * 		if npc must synthesizer all his lines
	 */
	public boolean isAlwaysSynthesizer(){
		return npc.isAlwaysSynthesizer();
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

		controller.addTool( new ChangeStringValueTool(npc,"#" + red + green + blue,"getTextFrontColor", "setTextFrontColor") );
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

		controller.addTool( new ChangeStringValueTool(npc,"#" + red + green + blue,"getTextBorderColor", "setTextBorderColor") );
	}

	public void setBubbleBkgColor( Color bubbleBkgColor ) {
		String red = Integer.toHexString( bubbleBkgColor.getRed() );
		String green = Integer.toHexString( bubbleBkgColor.getGreen() );
		String blue = Integer.toHexString( bubbleBkgColor.getBlue() );
		
		if (red.length() == 1)
			red = "0" + red;
		if (green.length() == 1)
			green = "0" + green;
		if (blue.length() == 1)
			blue = "0" + blue;
		
		controller.addTool( new ChangeStringValueTool(npc,"#" + red + green + blue,"getBubbleBkgColor", "setBubbleBkgColor"));
 	}

	public void setBubbleBorderColor( Color bubbleBorderColor ) {
		String red = Integer.toHexString( bubbleBorderColor.getRed() );
		String green = Integer.toHexString( bubbleBorderColor.getGreen() );
		String blue = Integer.toHexString( bubbleBorderColor.getBlue() );
		
		if (red.length() == 1)
			red = "0" + red;
		if (green.length() == 1)
			green = "0" + green;
		if (blue.length() == 1)
			blue = "0" + blue;
		
		controller.addTool( new ChangeStringValueTool(npc, "#" + red + green + blue,"getBubbleBorderColor", "setBubbleBorderColor"));
 	}

	/**
	 * Set the possibility to all conversation lines to be read by synthesizer
	 * 
	 * @param always
	 * 			Boolean value
	 */
	public void setAlwaysSynthesizer(boolean always){
		controller.addTool( new ChangeBooleanValueTool(npc, always, "isAlwaysSynthesizer", "setAlwaysSynthesizer"));
	}
	
	/**
	 * Sets the new voice for the character
	 * 
	 * @param voice
	 * 			a string with the valid voice
	 */
	public void setVoice(String voice){
		controller.addTool( new ChangeStringValueTool(npc,voice,"getVoice", "setVoice") );
	}
	
	/**
	 * Gets the voice associated to character
	 * 
	 * @return
	 * 		string representing character voice
	 */
	public String getVoice(){
		return npc.getVoice();
	}
	
	@Override
	public Object getContent( ) {
		return npc;
	}

	@Override
	public int[] getAddableElements( ) {
		//return new int[] { Controller.RESOURCES };
		return new int[] {};
	}

	@Override
	public boolean canAddElement( int type ) {
		// It can always add new resources
		//return type == Controller.RESOURCES;
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
		return true;
	}

	@Override
	public boolean addElement( int type, String id ) {
		boolean elementAdded = false;

		if( type == Controller.RESOURCES ) {
			Resources newResources = new Resources( );
			resourcesList.add( newResources );
			resourcesDataControlList.add( new ResourcesDataControl( newResources, Controller.NPC ) );
			//controller.dataModified( );
			elementAdded = true;
		}

		return elementAdded;
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
	public String renameElement(String name ) {
		boolean elementRenamed = false;
		String oldNPCId = npc.getId( );
		String references = String.valueOf( controller.countIdentifierReferences( oldNPCId ) );

		// Ask for confirmation
		if(name != null || controller.showStrictConfirmDialog( TextConstants.getText( "Operation.RenameNPCTitle" ), TextConstants.getText( "Operation.RenameElementWarning", new String[] { oldNPCId, references } ) ) ) {

			// Show a dialog asking for the new npc id
			String newNPCId = name;
			if (name == null)
				newNPCId = controller.showInputDialog( TextConstants.getText( "Operation.RenameNPCTitle" ), TextConstants.getText( "Operation.RenameNPCMessage" ), oldNPCId );

			// If some value was typed and the identifiers are different
			if( newNPCId != null && !newNPCId.equals( oldNPCId ) && controller.isElementIdValid( newNPCId ) ) {
				npc.setId( newNPCId );
				controller.replaceIdentifierReferences( oldNPCId, newNPCId );
				controller.getIdentifierSummary( ).deleteNPCId( oldNPCId );
				controller.getIdentifierSummary( ).addNPCId( newNPCId );
				//controller.dataModified( );
				elementRenamed = true;
			}
		}

		if (elementRenamed)
			return oldNPCId;
		else
			return null;
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
		actionsListDataControl.updateVarFlagSummary( varFlagSummary );
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean valid = true;

		// Iterate through the resources
		for( int i = 0; i < resourcesDataControlList.size( ); i++ ) {
			String resourcesPath = currentPath + " >> " + TextConstants.getElementName( Controller.RESOURCES ) + " #" + ( i + 1 );
			valid &= resourcesDataControlList.get( i ).isValid( resourcesPath, incidences );
		}

		// Spread the call to the actions
		valid &= actionsListDataControl.isValid( currentPath, incidences );

		return valid;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		// Iterate through the resources
		for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
			count += resourcesDataControl.countAssetReferences( assetPath );

		// Add the references in the actions
		count += actionsListDataControl.countAssetReferences( assetPath );

		return count;
	}
	
	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		// Iterate through the resources
		for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
			resourcesDataControl.getAssetReferences( assetPaths, assetTypes );

		// Add the references in the actions
		actionsListDataControl.getAssetReferences( assetPaths, assetTypes );
	}

	@Override
	public void deleteAssetReferences( String assetPath ) {
		// Iterate through the resources
		for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
			resourcesDataControl.deleteAssetReferences( assetPath );

		// Delete the references from the actions
		actionsListDataControl.deleteAssetReferences( assetPath );
	}

	@Override
	public int countIdentifierReferences( String id ) {
		return actionsListDataControl.countIdentifierReferences( id );
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		actionsListDataControl.replaceIdentifierReferences( oldId, newId );
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		actionsListDataControl.deleteIdentifierReferences( id );
	}

	public boolean buildResourcesTab( ) {
		return true;
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}

	@Override
	public void recursiveSearch() {
		check(this.getBriefDescription(), TextConstants.getText("Search.BriefDescription"));
		check(this.getDetailedDescription(), TextConstants.getText("Search.DetailedDescription"));
		check(this.getDocumentation(), TextConstants.getText("Search.Documentation"));
		check(this.getId(), "ID");
		check(this.getName(), TextConstants.getText("Search.Name"));
		check(this.getVoice(), TextConstants.getText("Search.NPCVoice"));
		check(this.getPreviewImage(), TextConstants.getText("Search.PreviewImage"));
		getActionsList().recursiveSearch();
	}

	public String getAnimationPath(String animation) {
		return resourcesDataControlList.get( selectedResources ).getAssetPath( animation );
	}
	
	public Color getBubbleBorderColor() {
		return new Color( Integer.valueOf( npc.getBubbleBorderColor( ).substring( 1 ), 16 ).intValue( ) );
	}
	
	public Color getBubbleBkgColor() {
		return new Color( Integer.valueOf( npc.getBubbleBkgColor( ).substring( 1 ), 16 ).intValue( ) );
	}
	
	public Boolean getShowsSpeechBubbles() {
		return npc.getShowsSpeechBubbles();
	}
	
	public void setShowsSpeechBubbles(Boolean showsSpeechBubbles) {
		controller.addTool( new ChangeBooleanValueTool(npc, showsSpeechBubbles, "getShowsSpeechBubbles", "setShowsSpeechBubbles"));
	}

	@Override
	public List<DataControl> getPathToDataControl(DataControl dataControl) {
		List<DataControl> path = getPathFromChild(dataControl, resourcesDataControlList);
		if (path != null) return path;
		return getPathFromChild(dataControl, actionsListDataControl);
	}


}
