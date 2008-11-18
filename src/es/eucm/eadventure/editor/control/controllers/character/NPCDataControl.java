package es.eucm.eadventure.editor.control.controllers.character;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.data.supportdata.FlagSummary;
import es.eucm.eadventure.editor.gui.TextConstants;

public class NPCDataControl extends DataControlWithResources {

	/**
	 * Contained NPC data.
	 */
	private NPC npc;

	/**
	 * List of resources.
	 */
	private List<Resources> resourcesList;

	/**
	 * List of resources controllers.
	 */
	private List<ResourcesDataControl> resourcesDataControlList;

	/**
	 * Conversation references list controller.
	 */
	private ConversationReferencesListDataControl conversationReferencesListDataControl;

	/**
	 * The resources that must be used in the previews.
	 */
	private int selectedResources;

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

		conversationReferencesListDataControl = new ConversationReferencesListDataControl( npc.getConversationReferences( ) );
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
	 * Returns the conversation reference list controller.
	 * 
	 * @return Conversation reference list controller
	 */
	public ConversationReferencesListDataControl getConversationReferencesList( ) {
		return conversationReferencesListDataControl;
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
		if( previewImagePath != null )
			previewImagePath += "_01.png";

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
	 * Sets the new selected resources block of the list.
	 * 
	 * @param selectedResources
	 *            New selected block of resources
	 */
	public void setSelectedResources( int selectedResources ) {
		this.selectedResources = selectedResources;
	}

	/**
	 * Sets the new documentation of the NPC.
	 * 
	 * @param documentation
	 *            Documentation of the NPC
	 */
	public void setDocumentation( String documentation ) {
		// If the value is different
		if( !documentation.equals( npc.getDocumentation( ) ) ) {
			// Set the new documentation and modify the data
			npc.setDocumentation( documentation );
			controller.dataModified( );
		}
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

		npc.setTextFrontColor( "#" + red + green + blue );
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

		npc.setTextBorderColor( "#" + red + green + blue );
	}

	/**
	 * Sets the new name of the character.
	 * 
	 * @param name
	 *            Name of the character
	 */
	public void setName( String name ) {
		// If the value is different
		if( !name.equals( npc.getName( ) ) ) {
			// Set the new name and modify the data
			npc.setName( name );
			controller.dataModified( );
		}
	}

	/**
	 * Sets the new brief description of the character.
	 * 
	 * @param description
	 *            Description of the character
	 */
	public void setBriefDescription( String description ) {
		// If the value is different
		if( !description.equals( npc.getDescription( ) ) ) {
			// Set the new description and modify the data
			npc.setDescription( description );
			controller.dataModified( );
		}
	}

	/**
	 * Sets the new detailed description of the character.
	 * 
	 * @param detailedDescription
	 *            Detailed description of the character
	 */
	public void setDetailedDescription( String detailedDescription ) {
		// If the value is different
		if( !detailedDescription.equals( npc.getDetailedDescription( ) ) ) {
			// Set the new detailed description and modify the data
			npc.setDetailedDescription( detailedDescription );
			controller.dataModified( );
		}
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
	public boolean addElement( int type ) {
		boolean elementAdded = false;

		if( type == Controller.RESOURCES ) {
			Resources newResources = new Resources( );
			resourcesList.add( newResources );
			resourcesDataControlList.add( new ResourcesDataControl( newResources, Controller.NPC ) );
			controller.dataModified( );
			elementAdded = true;
		}

		return elementAdded;
	}

	@Override
	public boolean deleteElement( DataControl dataControl ) {
		boolean elementDeleted = false;

		// Delete the block only if it is not the last one
		if( resourcesList.size( ) > 1 ) {
			if( resourcesList.remove( dataControl.getContent( ) ) ) {
				int resourcesIndex = resourcesDataControlList.indexOf( dataControl );
				resourcesDataControlList.remove( dataControl );

				// Decrease the selected index if necessary
				if( selectedResources > 0 && selectedResources >= resourcesIndex )
					selectedResources--;

				controller.dataModified( );
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
			controller.dataModified( );
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
			controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean renameElement( ) {
		boolean elementRenamed = false;
		String oldNPCId = npc.getId( );
		String references = String.valueOf( controller.countIdentifierReferences( oldNPCId ) );

		// Ask for confirmation
		if( controller.showStrictConfirmDialog( TextConstants.getText( "Operation.RenameNPCTitle" ), TextConstants.getText( "Operation.RenameElementWarning", new String[] { oldNPCId, references } ) ) ) {

			// Show a dialog asking for the new npc id
			String newNPCId = controller.showInputDialog( TextConstants.getText( "Operation.RenameNPCTitle" ), TextConstants.getText( "Operation.RenameNPCMessage" ), oldNPCId );

			// If some value was typed and the identifiers are different
			if( newNPCId != null && !newNPCId.equals( oldNPCId ) && controller.isElementIdValid( newNPCId ) ) {
				npc.setId( newNPCId );
				controller.replaceIdentifierReferences( oldNPCId, newNPCId );
				controller.getIdentifierSummary( ).deleteNPCId( oldNPCId );
				controller.getIdentifierSummary( ).addNPCId( newNPCId );
				controller.dataModified( );
				elementRenamed = true;
			}
		}

		return elementRenamed;
	}

	@Override
	public void updateFlagSummary( FlagSummary flagSummary ) {
		conversationReferencesListDataControl.updateFlagSummary( flagSummary );
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
		return conversationReferencesListDataControl.countIdentifierReferences( id );
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		conversationReferencesListDataControl.replaceIdentifierReferences( oldId, newId );
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		conversationReferencesListDataControl.deleteIdentifierReferences( id );
	}

	public boolean buildResourcesTab( ) {
		return true;
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}
}
