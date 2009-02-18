package es.eucm.eadventure.editor.control.tools.general.resources;

import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.data.AssetInformation;

/**
 * Edition tool for Deleting a resource
 * @author Javier
 *
 */
public class DeleteResourceTool extends ResourcesTool{

	public DeleteResourceTool(Resources resources,
			AssetInformation[] assetsInformation,
			ConditionsController conditionsController, int resourcesType, int index) throws CloneNotSupportedException {
		super(resources, assetsInformation, conditionsController, resourcesType, index);
	}

	@Override
	public boolean doTool() {
		boolean done = false;
		// If the given asset is not empty, delete it
		if( resources.getAssetPath( assetsInformation[index].name ) != null ) {
			resources.deleteAsset( assetsInformation[index].name );
			done = true;
		}
		return done;
	}

}
