package es.eucm.eadventure.editor.control.controllers.general;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.data.supportdata.FlagSummary;
import es.eucm.eadventure.editor.gui.TextConstants;

public class ActionDataControl extends DataControl {

	/**
	 * Contained action structure.
	 */
	private Action action;

	/**
	 * Type of the action.
	 */
	private int actionType;

	/**
	 * Conditions controller.
	 */
	private ConditionsController conditionsController;

	/**
	 * FunctionalEffects controller
	 */
	private EffectsController effectsController;

	/**
	 * Contructor.
	 * 
	 * @param action
	 *            Next scenes of the data control structure
	 */
	public ActionDataControl( Action action ) {
		this.action = action;

		// Store the type of the action
		switch( action.getType( ) ) {
			case Action.EXAMINE:
				actionType = Controller.ACTION_EXAMINE;
				break;
			case Action.GRAB:
				actionType = Controller.ACTION_GRAB;
				break;
			case Action.USE:
				actionType = Controller.ACTION_USE;
				break;
			case Action.USE_WITH:
				actionType = Controller.ACTION_USE_WITH;
				break;
			case Action.GIVE_TO:
				actionType = Controller.ACTION_GIVE_TO;
				break;
		}

		// Create subcontrollers
		conditionsController = new ConditionsController( action.getConditions( ) );
		effectsController = new EffectsController( action.getEffects( ) );
	}

	/**
	 * Returns the conditions of the action.
	 * 
	 * @return Conditions of the action
	 */
	public ConditionsController getConditions( ) {
		return conditionsController;
	}

	/**
	 * Returns the effects of the action.
	 * 
	 * @return FunctionalEffects of the action
	 */
	public EffectsController getEffects( ) {
		return effectsController;
	}

	/**
	 * Returns the type of the contained effect.
	 * 
	 * @return Type of the contained effect
	 */
	public int getType( ) {
		return actionType;
	}

	/**
	 * Returns whether the action accepts id target or not.
	 * 
	 * @return True if the action accepts id target, false otherwise
	 */
	public boolean hasIdTarget( ) {
		// The use-with and give-to actions accept id target
		return action.getType( ) == Action.USE_WITH || action.getType( ) == Action.GIVE_TO;
	}

	/**
	 * Returns the list of elements to select for the actions with targets.
	 * 
	 * @return List of elements, null if the action doesn't accept targets
	 */
	public String[] getElementsList( ) {
		String[] elements = null;

		if( action.getType( ) == Action.USE_WITH )
			elements = controller.getIdentifierSummary( ).getItemIds( );
		else if( action.getType( ) == Action.GIVE_TO )
			elements = controller.getIdentifierSummary( ).getNPCIds( );

		return elements;
	}

	/**
	 * Returns the target id of the contained effect.
	 * 
	 * @return Target id of the contained effect
	 */
	public String getIdTarget( ) {
		return action.getIdTarget( );
	}

	/**
	 * Returns the documentation of the action.
	 * 
	 * @return Action's documentation
	 */
	public String getDocumentation( ) {
		return action.getDocumentation( );
	}

	/**
	 * Sets the new documentation of the action.
	 * 
	 * @param documentation
	 *            Documentation of the action
	 */
	public void setDocumentation( String documentation ) {
		// If the value is different
		if( !documentation.equals( action.getDocumentation( ) ) ) {
			// Set the new documentation and modify the data
			action.setDocumentation( documentation );
			controller.dataModified( );
		}
	}

	/**
	 * Sets the new id target of the action.
	 * 
	 * @param idTarget
	 *            Id target of the action
	 */
	public void setIdTarget( String idTarget ) {
		// If the value is different
		if( !idTarget.equals( action.getIdTarget( ) ) ) {
			// Set the new documentation and modify the data
			action.setIdTarget( idTarget );
			controller.updateTree( );
			controller.dataModified( );
		}
	}

	@Override
	public Object getContent( ) {
		return action;
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
	public boolean deleteElement( DataControl dataControl ) {
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
	public boolean renameElement( ) {
		return false;
	}

	@Override
	public void updateFlagSummary( FlagSummary flagSummary ) {
		// Update the flag summary with the effects of the action
		EffectsController.updateFlagSummary( flagSummary, action.getEffects( ) );
		ConditionsController.updateFlagSummary( flagSummary, action.getConditions( ) );
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		// Check the effects of the action
		return EffectsController.isValid( currentPath + " >> " + TextConstants.getText( "Element.Effects" ), incidences, action.getEffects( ) );
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		// Return the asset references from the effects
		return EffectsController.countAssetReferences( assetPath, action.getEffects( ) );
	}

	@Override
	public void deleteAssetReferences( String assetPath ) {
		EffectsController.deleteAssetReferences( assetPath, action.getEffects( ) );
	}

	@Override
	public int countIdentifierReferences( String id ) {
		int count = 0;

		// If the action references to the given identifier, increase the counter
		if( ( action.getType( ) == Action.GIVE_TO || action.getType( ) == Action.USE_WITH ) && action.getIdTarget( ).equals( id ) )
			count++;

		// Add to the counter the references in the effects block
		count += EffectsController.countIdentifierReferences( id, action.getEffects( ) );

		return count;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		// Only the "Give to" and "Use with" have item references
		if( ( action.getType( ) == Action.GIVE_TO || action.getType( ) == Action.USE_WITH ) && action.getIdTarget( ).equals( oldId ) )
			action.setIdTarget( newId );

		EffectsController.replaceIdentifierReferences( oldId, newId, action.getEffects( ) );
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		EffectsController.deleteIdentifierReferences( id, action.getEffects( ) );
	}

	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		EffectsController.getAssetReferences( assetPaths, assetTypes, action.getEffects( ) );
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}
}
