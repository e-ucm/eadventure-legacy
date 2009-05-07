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
		if( profileNames.length == 0 )
			controller.showErrorDialog( TextConstants.getText( "Resources.EditAsset" ), TextConstants.getText( "Resources.ErrorNoAssets" ) );

		// If not empty, select one of them
		else {
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
