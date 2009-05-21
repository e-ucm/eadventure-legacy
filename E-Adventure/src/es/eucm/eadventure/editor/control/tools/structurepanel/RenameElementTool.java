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
