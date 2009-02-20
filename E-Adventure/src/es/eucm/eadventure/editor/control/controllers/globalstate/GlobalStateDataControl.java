package es.eucm.eadventure.editor.control.controllers.globalstate;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.conditions.GlobalState;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.tools.general.ChangeDocumentationTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class GlobalStateDataControl extends DataControl{

	private ConditionsController controller;
	
	private GlobalState globalState;
	
	public GlobalStateDataControl(GlobalState conditions) {
		globalState = conditions;
		controller = new ConditionsController ( globalState );
	}
	
	public void setDocumentation ( String doc ){
		Controller.getInstance().addTool(new ChangeDocumentationTool(globalState, doc));
	}
	
	public String getDocumentation ( ){
		return globalState.getDocumentation();
	}
	
	public String getId(){
		return globalState.getId();
	}
	
	private void setId ( String newId ){
		globalState.setId( newId );
	}

	/**
	 * @return the controller
	 */
	public ConditionsController getController() {
		return controller;
	}

	@Override
	public boolean addElement(int type) {
		return false;
	}

	@Override
	public boolean canAddElement(int type) {
		return false;
	}

	@Override
	public boolean canBeDeleted() {
		// Check if no references are made to this global state
		int references = Controller.getInstance().countIdentifierReferences( getId() );
		return ( references == 0);
	}

	@Override
	public boolean canBeDuplicated() {
		return false;
	}

	@Override
	public boolean canBeMoved() {
		return true;
	}

	@Override
	public boolean canBeRenamed() {
		return true;
	}

	@Override
	public int countAssetReferences(String assetPath) {
		return 0;
	}

	@Override
	public int countIdentifierReferences(String id) {
		return 0;
	}

	@Override
	public void deleteAssetReferences(String assetPath) {
	}

	@Override
	public boolean deleteElement(DataControl dataControl, boolean askConfirmation) {
		return false;
	}

	@Override
	public void deleteIdentifierReferences(String id) {
	}

	@Override
	public int[] getAddableElements() {
		return new int[]{};
	}

	@Override
	public void getAssetReferences(List<String> assetPaths,
			List<Integer> assetTypes) {
	}

	@Override
	public Object getContent() {
		return globalState;
	}

	@Override
	public boolean isValid(String currentPath, List<String> incidences) {
		return true;
	}

	@Override
	public boolean moveElementDown(DataControl dataControl) {
		return false;
	}

	@Override
	public boolean moveElementUp(DataControl dataControl) {
		return false;
	}

	@Override
	public String renameElement(String name) {
		boolean elementRenamed = false;
		String oldItemId = getId( );
		String references = String.valueOf( Controller.getInstance().countIdentifierReferences( oldItemId ) );

		// Ask for confirmation
		if(name != null || Controller.getInstance().showStrictConfirmDialog( TextConstants.getText( "Operation.RenameGlobalStateTitle" ), TextConstants.getText( "Operation.RenameElementWarning", new String[] { oldItemId, references } ) ) ) {

			// Show a dialog asking for the new item id
			String newItemId = name;
			if (name == null)
				newItemId = Controller.getInstance().showInputDialog( TextConstants.getText( "Operation.RenameGlobalStateTitle" ), TextConstants.getText( "Operation.RenameGlobalStateMessage" ), oldItemId );

			// If some value was typed and the identifiers are different
			if( newItemId != null && !newItemId.equals( oldItemId ) && Controller.getInstance().isElementIdValid( newItemId ) ) {
				setId( newItemId );
				Controller.getInstance().replaceIdentifierReferences( oldItemId, newItemId );
				Controller.getInstance().getIdentifierSummary( ).deleteGlobalStateId( oldItemId );
				Controller.getInstance().getIdentifierSummary( ).addGlobalStateId( newItemId );
				//Controller.getInstance().dataModified( );
				elementRenamed = true;
			}
		}

		if (elementRenamed)
			return oldItemId;
		else
			return null;
	}

	@Override
	public void replaceIdentifierReferences(String oldId, String newId) {
		
	}

	@Override
	public void updateVarFlagSummary(VarFlagSummary varFlagSummary) {
		ConditionsController.updateVarFlagSummary(varFlagSummary, globalState);
	}

}
