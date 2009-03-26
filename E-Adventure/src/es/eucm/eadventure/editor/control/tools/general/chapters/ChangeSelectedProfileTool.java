package es.eucm.eadventure.editor.control.tools.general.chapters;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeSelectedProfileTool extends Tool{

	public static final int MODE_ADAPTATION = AssetsController.CATEGORY_ADAPTATION;
	public static final int MODE_ASSESSMENT = AssetsController.CATEGORY_ASSESSMENT;
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
		
		// Get the list of assets from the ZIP file
		String[] assetFilenames = AssetsController.getAssetFilenames( mode );
		String[] assetPaths = AssetsController.getAssetsList( mode );

		// If the list of assets is empty, show an error message
		if( assetFilenames.length == 0 )
			controller.showErrorDialog( TextConstants.getText( "Resources.EditAsset" ), TextConstants.getText( "Resources.ErrorNoAssets" ) );

		// If not empty, select one of them
		else {
			// Let the user choose between the assets
			String selectedAsset = controller.showInputDialog( TextConstants.getText( "Resources.EditAsset" ), TextConstants.getText( "Resources.EditAssetMessage" ), assetFilenames );

			// Get old Value
			if (mode==MODE_ASSESSMENT){
				oldValue = chapter.getAssessmentPath();
			} else if (mode==MODE_ADAPTATION){
				oldValue = chapter.getAdaptationPath();
			}
			
			// If a file was selected
			if( selectedAsset != null ) {
				// Take the index of the selected asset
				int assetIndex = -1;
				for( int i = 0; i < assetFilenames.length; i++ )
					if( assetFilenames[i].equals( selectedAsset ) )
						assetIndex = i;

				// Store the data (if modified)
				if (oldValue==null && assetPaths[assetIndex]!=null || 
						oldValue!=null && assetPaths[assetIndex]==null ||
						(oldValue!=null && assetPaths[assetIndex]!=null &&
								!oldValue.equals(assetPaths[assetIndex]))){
				
					if (mode == MODE_ASSESSMENT){
						chapter.setAssessmentPath( assetPaths[assetIndex]);
					} else if (mode == MODE_ADAPTATION){
						chapter.setAdaptationPath( assetPaths[assetIndex]);
					}
					newValue = assetPaths[assetIndex];
					done = true;
				}
				
				//controller.dataModified( );
			}
		}
		// update var/flags summary, because in adaptation and/or assessement profiles may have new var/flag
		controller.updateFlagSummary();
		return done;
	}

	@Override
	public boolean redoTool() {
		if (mode == MODE_ASSESSMENT){
			chapter.setAssessmentPath(newValue);
		} else if (mode == MODE_ADAPTATION){
			chapter.setAdaptationPath(newValue);
		}
		controller.reloadPanel();
		controller.updateFlagSummary();
		return true;
	}

	@Override
	public boolean undoTool() {
		if (mode == MODE_ASSESSMENT){
			chapter.setAssessmentPath(oldValue);
		} else if (mode == MODE_ADAPTATION){
			chapter.setAdaptationPath(oldValue);
		}
		controller.updateFlagSummary();
		controller.reloadPanel();
		return true;
	}
	
	
	
}
