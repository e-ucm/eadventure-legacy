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
package es.eucm.eadventure.editor.control.tools.structurepanel;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.structurepanel.StructureControl;


public class RenameElementTool extends Tool {

	private DataControl dataControl;
	
	private String oldName;
	
	private String newName;
		
	public RenameElementTool(DataControl dataControl, String string) {
		this.dataControl = dataControl;
		this.newName = string;
	}

	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return (oldName != null);
	}

	@Override
	public boolean doTool() {
	    if (newName.length() == 0)
	    	return false;
		if( dataControl.canBeRenamed( )) {
			oldName = dataControl.renameElement( newName );
			
			renameSelectedProfile(oldName,newName);
			if (oldName != null && !oldName.equals(newName)) {
				return true;
			}
		
		}
		return false;
	}

	@Override
	public boolean redoTool() {
		if( dataControl.canBeRenamed( )) {
			oldName = dataControl.renameElement( newName );
			renameSelectedProfile(oldName,newName);
			if (oldName != null) {
				Controller.getInstance().updateStructure();
				StructureControl.getInstance().changeDataControl(dataControl);
				return true;
			}
		}		
		return false;
	}

	@Override
	public boolean undoTool() {
		if( dataControl.canBeRenamed( )) {
			newName = dataControl.renameElement( oldName );
			renameSelectedProfile(newName,oldName);
			if (newName != null) {
				Controller.getInstance().updateStructure();
				StructureControl.getInstance().changeDataControl(dataControl);
				return true;
			}
		}		
		return false;
	}
	
	private void renameSelectedProfile(String name1, String name2){
	 // if has been renamed a selected adaptation or assessment profile, change that name
		if (dataControl instanceof AdaptationProfileDataControl){
		    if (Controller.getInstance().getSelectedChapterDataControl().getAdaptationName().equals(name1))
			((Chapter)Controller.getInstance().getSelectedChapterDataControl().getContent()).setAdaptationName(name2);
		}
		
		if (dataControl instanceof AssessmentProfileDataControl){
		    if (Controller.getInstance().getSelectedChapterDataControl().getAssessmentName().equals(name1))
			((Chapter)Controller.getInstance().getSelectedChapterDataControl().getContent()).setAssessmentName(name2);
		}
	}
	
	
	@Override
	public boolean combine(Tool other) {
		return false;
	}


}
