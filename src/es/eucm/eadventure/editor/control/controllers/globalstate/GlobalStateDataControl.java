/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.editor.control.controllers.globalstate;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.conditions.GlobalState;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDocumentationTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class GlobalStateDataControl extends DataControl{

	private ConditionsController controller;
	
	private GlobalState globalState;
	
	public GlobalStateDataControl(GlobalState conditions) {
		globalState = conditions;
		controller = new ConditionsController ( globalState, Controller.GLOBAL_STATE, globalState.getId() );
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
	
	/**
	 * @return the controller
	 */
	public ConditionsController getController() {
		return controller;
	}

	@Override
	public boolean addElement(int type, String id) {
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
		return true;
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
		int count=0;
	    	count += controller.countIdentifierReferences(id);
	    	return count;
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
	    controller.deleteIdentifierReferences(id);
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
				globalState.setId( newItemId );
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
		if (globalState.getId().equals(oldId)){
		    globalState.setId(newId);
		    Controller.getInstance().getIdentifierSummary().deleteGlobalStateId(oldId);
		    Controller.getInstance().getIdentifierSummary().addGlobalStateId(newId);
		}
		controller.replaceIdentifierReferences(oldId, newId);
	}

	@Override
	public void updateVarFlagSummary(VarFlagSummary varFlagSummary) {
		ConditionsController.updateVarFlagSummary(varFlagSummary, globalState);
	}

	@Override
	public void recursiveSearch() {
		check(this.controller, TextConstants.getText("Search.Conditions"));
		check(this.getDocumentation(), TextConstants.getText("Search.Documentation"));
		check(this.getId(), "ID");
	}
	
	@Override
	public List<Searchable> getPathToDataControl(Searchable dataControl) {
		return null;
	}

}
