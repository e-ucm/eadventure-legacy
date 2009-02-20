package es.eucm.eadventure.editor.control.controllers.macro;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.effects.Macro;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.control.tools.general.ChangeDocumentationTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class MacroDataControl extends DataControl{

	private EffectsController controller;
	
	private Macro macro;
	
	public MacroDataControl(Macro conditions) {
		macro = conditions;
		controller = new EffectsController ( macro );
	}
	
	public void setDocumentation ( String doc ){
		Controller.getInstance().addTool(new ChangeDocumentationTool(macro, doc));
	}
	
	public String getDocumentation ( ){
		return macro.getDocumentation();
	}
	
	public String getId(){
		return macro.getId();
	}
	
	private void setId ( String newId ){
		macro.setId( newId );
	}

	/**
	 * @return the controller
	 */
	public EffectsController getController() {
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
		return EffectsController.countAssetReferences(assetPath, macro);
	}

	@Override
	public int countIdentifierReferences(String id) {
		return EffectsController.countIdentifierReferences(id, macro);
	}

	@Override
	public void deleteAssetReferences(String assetPath) {
		EffectsController.deleteAssetReferences(assetPath, macro);
	}

	@Override
	public boolean deleteElement(DataControl dataControl, boolean askConfirmation) {
		return false;
	}

	@Override
	public void deleteIdentifierReferences(String id) {
		EffectsController.deleteIdentifierReferences(id, macro);
	}

	@Override
	public int[] getAddableElements() {
		return new int[]{};
	}

	@Override
	public void getAssetReferences(List<String> assetPaths,
			List<Integer> assetTypes) {
		EffectsController.getAssetReferences(assetPaths, assetTypes, macro);
	}

	@Override
	public Object getContent() {
		return macro;
	}

	@Override
	public boolean isValid(String currentPath, List<String> incidences) {
		return EffectsController.isValid(currentPath, incidences, macro);
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
	public String renameElement( String name ) {
		boolean elementRenamed = false;
		String oldItemId = getId( );
		String references = String.valueOf( Controller.getInstance().countIdentifierReferences( oldItemId ) );

		// Ask for confirmation
		if(name != null || Controller.getInstance().showStrictConfirmDialog( TextConstants.getText( "Operation.RenameMacroTitle" ), TextConstants.getText( "Operation.RenameElementWarning", new String[] { oldItemId, references } ) ) ) {

			// Show a dialog asking for the new item id
			String newItemId = name;
			if (name == null)
				newItemId = Controller.getInstance().showInputDialog( TextConstants.getText( "Operation.RenameMacroTitle" ), TextConstants.getText( "Operation.RenameMacroMessage" ), oldItemId );

			// If some value was typed and the identifiers are different
			if( newItemId != null && !newItemId.equals( oldItemId ) && Controller.getInstance().isElementIdValid( newItemId ) ) {
				setId( newItemId );
				Controller.getInstance().replaceIdentifierReferences( oldItemId, newItemId );
				Controller.getInstance().getIdentifierSummary( ).deleteMacroId( oldItemId );
				Controller.getInstance().getIdentifierSummary( ).addMacroId( newItemId );
				//Controller.getInstance().dataModified( );
				elementRenamed = true;
			}
		}

		if (elementRenamed)
			return oldItemId;
		return null;
	}

	@Override
	public void replaceIdentifierReferences(String oldId, String newId) {
		EffectsController.replaceIdentifierReferences(oldId, newId, macro);
	}

	@Override
	public void updateVarFlagSummary(VarFlagSummary varFlagSummary) {
		EffectsController.updateVarFlagSummary(varFlagSummary, macro);
	}

}
