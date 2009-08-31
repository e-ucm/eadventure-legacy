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
package es.eucm.eadventure.editor.control.tools.general.chapters;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeSelectedProfileTool extends Tool{

	public static final int MODE_ADAPTATION = Controller.ADAPTATION_PROFILE;
	public static final int MODE_ASSESSMENT = Controller.ASSESSMENT_PROFILE;
	public static final int MODE_DELETE_ASSESS = 0;
	public static final int MODE_DELETE_ADAPT = 1;
	public static final int MODE_UNKNOWN = -1;
	
	protected Chapter chapter;
	
	protected int mode;
	
	protected Controller controller;
	
	protected String oldValue;
	
	protected String newValue;
	
	public ChangeSelectedProfileTool ( Chapter chapter, int mode ){
		this.chapter = chapter;
		this.mode = mode;
		controller = Controller.getInstance();
	}

	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
		boolean done = false;
		String[] profileNames = new String[1];
		// Get the list of profile names
		if (mode==MODE_ASSESSMENT)
		    profileNames = chapter.getAssessmentProfilesNames();
		else if (mode==MODE_ADAPTATION)
		    profileNames = chapter.getAdaptationProfilesNames();
		
		if (mode==MODE_DELETE_ASSESS){
		    oldValue = chapter.getAssessmentName();
		    chapter.setAssessmentName("");
		}else if (mode==MODE_DELETE_ADAPT){
		    oldValue = chapter.getAdaptationName();
		    chapter.setAdaptationName("");
		    
		}else{
		// If the list of profiles is empty, show an error message
		if( profileNames.length == 0 ){
		    if (mode==MODE_ASSESSMENT)
			controller.showErrorDialog( TextConstants.getText( "Resources.EditAsset" ), TextConstants.getText( "Operation.AssignAssessmentProfile" ) );
		    else if (mode==MODE_ADAPTATION)
			controller.showErrorDialog( TextConstants.getText( "Resources.EditAsset" ), TextConstants.getText( "Operation.AssignAdaptationProfile" ) );
		// If not empty, select one of them
		}else {
			// Let the user choose between the profiles
			String selectedProfile = controller.showInputDialog( TextConstants.getText( "Resources.EditAsset" ), TextConstants.getText( "Resources.EditAssetMessage" ), profileNames );

			// Get old Value
			if (mode==MODE_ASSESSMENT){
				oldValue = chapter.getAssessmentName();
			} else if (mode==MODE_ADAPTATION){
				oldValue = chapter.getAdaptationName();
			}
			
			// If a profile was selected
			if( selectedProfile != null ) {
				// Take the index of the selected profile
				int profileIndex = -1;
				for( int i = 0; i < profileNames.length; i++ )
					if( profileNames[i].equals( selectedProfile ) )
						profileIndex = i;

				// Store the data (if modified)
				//if (oldValue==null && profileNames[profileIndex]!=null || 
				//		oldValue!=null && assetPaths[assetIndex]==null ||
				//		(oldValue!=null && assetPaths[assetIndex]!=null &&
				//				!oldValue.equals(assetPaths[assetIndex]))){
				
					if (mode == MODE_ASSESSMENT){
						chapter.setAssessmentName( profileNames[profileIndex]);
					} else if (mode == MODE_ADAPTATION){
						chapter.setAdaptationName( profileNames[profileIndex]);
					}
					newValue = profileNames[profileIndex];
					done = true;
				//}
				
				//controller.dataModified( );
			}
		}
		}
		// update var/flags summary, because in adaptation and/or assessement profiles may have new var/flag
		controller.updateVarFlagSummary();
		return done;
	}

	@Override
	public boolean redoTool() {
		if (mode == MODE_ASSESSMENT){
			chapter.setAssessmentName(newValue);
		} else if (mode == MODE_ADAPTATION){
			chapter.setAdaptationName(newValue);
		}else if (mode==MODE_DELETE_ASSESS){
		    	chapter.setAssessmentName("");
		}else if (mode==MODE_DELETE_ADAPT){
		    	chapter.setAdaptationName("");
		}
		controller.reloadPanel();
		controller.updateVarFlagSummary();
		return true;
	}

	@Override
	public boolean undoTool() {
		if (mode == MODE_ASSESSMENT || mode==MODE_DELETE_ASSESS){
			chapter.setAssessmentName(oldValue);
		} else if (mode == MODE_ADAPTATION || mode==MODE_DELETE_ADAPT){
			chapter.setAdaptationName(oldValue);
		}
		controller.updateVarFlagSummary();
		controller.reloadPanel();
		return true;
	}
	
	
	
}
