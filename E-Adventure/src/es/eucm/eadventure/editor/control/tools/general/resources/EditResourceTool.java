package es.eucm.eadventure.editor.control.tools.general.resources;

import java.io.File;

import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.data.AssetInformation;

/**
 * Tool for editing Resources Blocks ("Edit" button). It cannot be combined as well.
 * @author Javier
 *
 */
public class EditResourceTool extends ResourcesTool{

	/**
	 * The filename to edit
	 */
	protected String filename;
	
	public EditResourceTool(Resources resources,
			AssetInformation[] assetsInformation,
			ConditionsController conditionsController, int resourcesType, int index, String filename) throws CloneNotSupportedException {
		super(resources, assetsInformation, conditionsController, resourcesType, index);
		this.filename = filename;
	}

	@Override
	public boolean doTool() {
		boolean done = false;
		
		AssetsController.addSingleAsset( assetsInformation[index].category, filename );
		// Dirty fix?
		String selectedAsset = (new File(filename)).getName();
		// If a file was selected
		if( selectedAsset != null ) {
			// Take the index of the selected asset
			String[] assetFilenames = AssetsController.getAssetFilenames( assetsInformation[index].category, assetsInformation[index].filter );
			String[] assetPaths = AssetsController.getAssetsList( assetsInformation[index].category, assetsInformation[index].filter );
			int assetIndex = -1;
			for( int i = 0; i < assetFilenames.length; i++ )
				if( assetFilenames[i].equals( selectedAsset ) )
					assetIndex = i;
			
			resources.addAsset( assetsInformation[index].name, assetPaths[assetIndex] );
			done = true;
		}
		
		return done;
	}

}
