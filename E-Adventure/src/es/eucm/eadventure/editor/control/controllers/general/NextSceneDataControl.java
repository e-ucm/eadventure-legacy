	package es.eucm.eadventure.editor.control.controllers.general;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.NextScene;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.control.tools.general.ChangeNSDestinyPositionTool;
import es.eucm.eadventure.editor.control.tools.general.ChangeTargetIdTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class NextSceneDataControl extends DataControl {

	/**
	 * Contained next scene structure.
	 */
	private NextScene nextScene;

	/**
	 * Conditions controller.
	 */
	private ConditionsController conditionsController;

	/**
	 * Effects controller
	 */
	private EffectsController effectsController;

	/**
	 * Post effects controller.
	 */
	private EffectsController postEffectsController;
	
	private ExitLookDataControl exitLookDataController;
	
	/**
	 * Contructor.
	 * 
	 * @param nextScene
	 *            Next scene structure
	 */
	public NextSceneDataControl( NextScene nextScene ) {
		this.nextScene = nextScene;

		// Create subcontrollers
		conditionsController = new ConditionsController( nextScene.getConditions( ) );
		effectsController = new EffectsController( nextScene.getEffects( ) );
		postEffectsController = new EffectsController( nextScene.getPostEffects( ) );

		exitLookDataController = new ExitLookDataControl(nextScene);
	}

	/**
	 * Returns the conditions of the next scene.
	 * 
	 * @return Conditions of the next scene
	 */
	public ConditionsController getConditions( ) {
		return conditionsController;
	}

	/**
	 * Returns the effects of the next scene.
	 * 
	 * @return Effects of the next scene
	 */
	public EffectsController getEffects( ) {
		return effectsController;
	}

	/**
	 * Returns the post-effects of the next scene.
	 * 
	 * @return Post-effects of the next scene
	 */
	public EffectsController getPostEffects( ) {
		return postEffectsController;
	}

	/**
	 * Returns the target scene id of the next scene.
	 * 
	 * @return Target scene id
	 */
	public String getNextSceneId( ) {
		return nextScene.getTargetId( );
	}

	/**
	 * Returns whether the next scene has a destiny position or not.
	 * 
	 * @return True if the next scene has a destiny position, false otherwise
	 */
	public boolean hasDestinyPosition( ) {
		return nextScene.hasPlayerPosition( );
	}

	/**
	 * Returns the X coordinate of the destiny position
	 * 
	 * @return X coordinate of the destiny position
	 */
	public int getDestinyPositionX( ) {
		return nextScene.getPositionX( );
	}

	/**
	 * Returns the Y coordinate of the destiny position
	 * 
	 * @return Y coordinate of the destiny position
	 */
	public int getDestinyPositionY( ) {
		return nextScene.getPositionY( );
	}

	/**
	 * Sets a new next scene id.
	 * 
	 * @param nextSceneId
	 *            New next scene id
	 */
	public void setNextSceneId( String nextSceneId ) {
		controller.addTool(new ChangeTargetIdTool(nextScene, nextSceneId, true, true));
	}

	/**
	 * Toggles the destiny position. If the next scene has a destiny position deletes it, if it doesn't have one, set
	 * initial values for it.
	 */
	public void toggleDestinyPosition( ) {
		if( nextScene.hasPlayerPosition( ) )
			controller.addTool(new ChangeNSDestinyPositionTool(nextScene, Integer.MIN_VALUE, Integer.MIN_VALUE));
		else
			controller.addTool(new ChangeNSDestinyPositionTool(nextScene, 0,0));

	}

	/**
	 * Sets the new destiny position of the next scene.
	 * 
	 * @param positionX
	 *            X coordinate of the destiny position
	 * @param positionY
	 *            Y coordinate of the destiny position
	 */
	public void setDestinyPosition( int positionX, int positionY ) {
		controller.addTool(new ChangeNSDestinyPositionTool(nextScene, positionX, positionY));
	}

	@Override
	public Object getContent( ) {
		return nextScene;
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
	public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {
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
	public String renameElement(String name ) {
		return null;
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
		// Update the summary with conditions and both blocks of effects
		ConditionsController.updateVarFlagSummary( varFlagSummary, nextScene.getConditions( ) );
		EffectsController.updateVarFlagSummary( varFlagSummary, nextScene.getEffects( ) );
		EffectsController.updateVarFlagSummary( varFlagSummary, nextScene.getPostEffects( ) );
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean valid = true;

		// Valid if the effects and the post effects are valid
		valid &= EffectsController.isValid( currentPath + " >> " + TextConstants.getText( "Element.Effects" ), incidences, nextScene.getEffects( ) );
		valid &= EffectsController.isValid( currentPath + " >> " + TextConstants.getText( "Element.PostEffects" ), incidences, nextScene.getPostEffects( ) );

		return valid;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		// Add to the counter the values of the effects and posteffects
		count += EffectsController.countAssetReferences( assetPath, nextScene.getEffects( ) );
		count += EffectsController.countAssetReferences( assetPath, nextScene.getPostEffects( ) );

		return count;
	}

	@Override
	public void deleteAssetReferences( String assetPath ) {
		EffectsController.deleteAssetReferences( assetPath, nextScene.getEffects( ) );
		EffectsController.deleteAssetReferences( assetPath, nextScene.getPostEffects( ) );
	}

	@Override
	public int countIdentifierReferences( String id ) {
		int count = 0;

		// If the next scene references to the identifier, increase the counter
		if( nextScene.getTargetId( ).equals( id ) )
			count++;

		// Add to the counter the values of the effects and posteffects
		count += EffectsController.countIdentifierReferences( id, nextScene.getEffects( ) );
		count += EffectsController.countIdentifierReferences( id, nextScene.getPostEffects( ) );

		return count;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		if( nextScene.getTargetId( ).equals( oldId ) )
			nextScene.setTargetId( newId );

		EffectsController.replaceIdentifierReferences( oldId, newId, nextScene.getEffects( ) );
		EffectsController.replaceIdentifierReferences( oldId, newId, nextScene.getPostEffects( ) );
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		EffectsController.deleteIdentifierReferences( id, nextScene.getEffects( ) );
		EffectsController.deleteIdentifierReferences( id, nextScene.getPostEffects( ) );
	}

	/**
	 * @return the exitLookDataController
	 */
	public ExitLookDataControl getExitLookDataController( ) {
		return exitLookDataController;
	}

	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		if (nextScene.getEffects( )!=null)
			EffectsController.getAssetReferences( assetPaths, assetTypes, nextScene.getEffects( ) );
		if (nextScene.getPostEffects( )!=null)
			EffectsController.getAssetReferences( assetPaths, assetTypes, nextScene.getPostEffects( ) );
		if (exitLookDataController!=null)
			exitLookDataController.getAssetReferences( assetPaths, assetTypes );
		
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}


}
