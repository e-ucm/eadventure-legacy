package es.eucm.eadventure.editor.control.controllers.general;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapterdata.Action;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.data.supportdata.FlagSummary;
import es.eucm.eadventure.editor.gui.TextConstants;

public class ActionsListDataControl extends DataControl {

	/**
	 * List of actions.
	 */
	private List<Action> actionsList;

	/**
	 * List of action controllers.
	 */
	private List<ActionDataControl> actionsDataControlList;

	/**
	 * Constructor.
	 * 
	 * @param actionsList
	 *            List of actions
	 */
	public ActionsListDataControl( List<Action> actionsList ) {
		this.actionsList = actionsList;

		// Create subcontrollers
		actionsDataControlList = new ArrayList<ActionDataControl>( );
		for( Action action : actionsList )
			actionsDataControlList.add( new ActionDataControl( action ) );
	}

	/**
	 * Returns the list of action controllers.
	 * 
	 * @return Action controllers
	 */
	public List<ActionDataControl> getActions( ) {
		return actionsDataControlList;
	}

	/**
	 * Returns the last action controller of the list.
	 * 
	 * @return Last action controller
	 */
	public ActionDataControl getLastAction( ) {
		return actionsDataControlList.get( actionsDataControlList.size( ) - 1 );
	}

	/**
	 * Returns the info of the actions contained in the list.
	 * 
	 * @return Array with the information of the actions. It contains the type of the action, and information about
	 *         whether they have conditions and effects
	 */
	public String[][] getActionsInfo( ) {
		String[][] actionsInfo = null;

		// Create the list for the actions
		actionsInfo = new String[actionsList.size( )][3];

		// Fill the array with the info
		for( int i = 0; i < actionsList.size( ); i++ ) {
			Action action = actionsList.get( i );

			if( action.getType( ) == Action.EXAMINE )
				actionsInfo[i][0] = TextConstants.getText( "ActionsList.ExamineAction" );
			else if( action.getType( ) == Action.GRAB )
				actionsInfo[i][0] = TextConstants.getText( "ActionsList.GrabAction" );
			else if( action.getType( ) == Action.GIVE_TO )
				actionsInfo[i][0] = TextConstants.getText( "ActionsList.GiveToAction", action.getIdTarget( ) );
			else if( action.getType( ) == Action.USE_WITH )
				actionsInfo[i][0] = TextConstants.getText( "ActionsList.UseWithAction", action.getIdTarget( ) );
			else if( action.getType( ) == Action.USE )
				actionsInfo[i][0] = TextConstants.getText( "ActionsList.UseAction" );

			if( action.getConditions( ).isEmpty( ) )
				actionsInfo[i][1] = TextConstants.getText( "GeneralText.No" );
			else
				actionsInfo[i][1] = TextConstants.getText( "GeneralText.Yes" );

			if( action.getEffects( ).isEmpty( ) )
				actionsInfo[i][2] = TextConstants.getText( "GeneralText.No" );
			else
				actionsInfo[i][2] = TextConstants.getText( "GeneralText.Yes" );
		}

		return actionsInfo;
	}

	@Override
	public Object getContent( ) {
		return actionsList;
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] { Controller.ACTION_EXAMINE, Controller.ACTION_GRAB, Controller.ACTION_USE, Controller.ACTION_USE_WITH, Controller.ACTION_GIVE_TO };
	}

	@Override
	public boolean canAddElement( int type ) {
		// It can always add new scenes
		return type == Controller.ACTION_EXAMINE || type == Controller.ACTION_GRAB || type == Controller.ACTION_USE || type == Controller.ACTION_USE_WITH || type == Controller.ACTION_GIVE_TO;
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
		Action newAction = null;

		if( type == Controller.ACTION_EXAMINE )
			newAction = new Action( Action.EXAMINE );

		else if( type == Controller.ACTION_GRAB )
			newAction = new Action( Action.GRAB );

		else if( type == Controller.ACTION_USE )
			newAction = new Action( Action.USE );

		// If the type of action is use-with, we must ask for a second item
		else if( type == Controller.ACTION_USE_WITH ) {
			// Take the list of the items
			String[] items = controller.getIdentifierSummary( ).getItemIds( );

			// If the list has elements, show the dialog with the options
			if( items.length > 0 ) {
				String selectedItem = controller.showInputDialog( TextConstants.getText( "Action.OperationAddAction" ), TextConstants.getText( "Action.MessageSelectItem" ), items );

				// If some value was selected
				if( selectedItem != null )
					newAction = new Action( Action.USE_WITH, selectedItem );
			}

			// If the list had no elements, show an error dialog
			else
				controller.showErrorDialog( TextConstants.getText( "Action.OperationAddAction" ), TextConstants.getText( "Action.ErrorNoItems" ) );
		}

		// If the type of action is give-to, we must ask for a character
		else if( type == Controller.ACTION_GIVE_TO ) {
			// Take the list of the characters
			String[] npcs = controller.getIdentifierSummary( ).getNPCIds( );

			// If the list has elements, show the dialog with the options
			if( npcs.length > 0 ) {
				String selectedNPC = controller.showInputDialog( TextConstants.getText( "Action.OperationAddAction" ), TextConstants.getText( "Action.MessageSelectNPC" ), npcs );

				// If some value was selected
				if( selectedNPC != null )
					newAction = new Action( Action.GIVE_TO, selectedNPC );
			}

			// If the list had no elements, show an error dialog
			else
				controller.showErrorDialog( TextConstants.getText( "Action.OperationAddAction" ), TextConstants.getText( "Action.ErrorNoNPCs" ) );
		}

		// If an action was added, create a controller and store it
		if( newAction != null ) {
			actionsList.add( newAction );
			actionsDataControlList.add( new ActionDataControl( newAction ) );
			controller.dataModified( );
		}

		return newAction != null;
	}

	@Override
	public boolean deleteElement( DataControl dataControl ) {
		boolean elementDeleted = false;

		if( actionsList.remove( dataControl.getContent( ) ) ) {
			actionsDataControlList.remove( dataControl );
			controller.dataModified( );
			elementDeleted = true;
		}

		return elementDeleted;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = actionsList.indexOf( dataControl.getContent( ) );

		if( elementIndex > 0 ) {
			actionsList.add( elementIndex - 1, actionsList.remove( elementIndex ) );
			actionsDataControlList.add( elementIndex - 1, actionsDataControlList.remove( elementIndex ) );
			controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = actionsList.indexOf( dataControl.getContent( ) );

		if( elementIndex < actionsList.size( ) - 1 ) {
			actionsList.add( elementIndex + 1, actionsList.remove( elementIndex ) );
			actionsDataControlList.add( elementIndex + 1, actionsDataControlList.remove( elementIndex ) );
			controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean renameElement( ) {
		return false;
	}

	@Override
	public void updateFlagSummary( FlagSummary flagSummary ) {
		// Iterate through each action
		for( ActionDataControl actionDataControl : actionsDataControlList )
			actionDataControl.updateFlagSummary( flagSummary );
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean valid = true;

		// Iterate through the actions
		for( int i = 0; i < actionsDataControlList.size( ); i++ ) {
			String actionPath = currentPath + " >> " + TextConstants.getText( "Element.Action" ) + " #" + ( i + 1 ) + " (" + TextConstants.getElementName( actionsDataControlList.get( i ).getType( ) ) + ")";
			valid &= actionsDataControlList.get( i ).isValid( actionPath, incidences );
		}

		return valid;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		// Iterate through each action
		for( ActionDataControl actionDataControl : actionsDataControlList )
			count += actionDataControl.countAssetReferences( assetPath );

		return count;
	}

	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		// Iterate through each action
		for( ActionDataControl actionDataControl : actionsDataControlList )
			actionDataControl.getAssetReferences( assetPaths, assetTypes );
	}
	
	@Override
	public void deleteAssetReferences( String assetPath ) {
		// Iterate through each action
		for( ActionDataControl actionDataControl : actionsDataControlList )
			actionDataControl.deleteAssetReferences( assetPath );
	}

	@Override
	public int countIdentifierReferences( String id ) {
		int count = 0;

		// Iterate through each action
		for( ActionDataControl actionDataControl : actionsDataControlList )
			count += actionDataControl.countIdentifierReferences( id );

		return count;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		// Iterate through each action
		for( ActionDataControl actionDataControl : actionsDataControlList )
			actionDataControl.replaceIdentifierReferences( oldId, newId );
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		int i = 0;

		// Check every action
		while( i < actionsList.size( ) ) {
			Action action = actionsList.get( i );

			// If the action has a reference to the identifier, delete it
			if( ( action.getType( ) == Action.GIVE_TO || action.getType( ) == Action.USE_WITH ) && action.getIdTarget( ).equals( id ) ) {
				actionsList.remove( i );
				actionsDataControlList.remove( i );
			}

			// If not, spread the call and increase the counter
			else {
				actionsDataControlList.get( i ).deleteIdentifierReferences( id );
				i++;
			}
		}
	}

	@Override
	public boolean canBeDuplicated( ) {
		return false;
	}
}
