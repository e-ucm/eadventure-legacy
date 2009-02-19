package es.eucm.eadventure.editor.control.tools.general.resources;

import es.eucm.eadventure.common.data.chapter.resources.Resources;

/**
 * Edition tool for deleting the given asset path
 * @author Javier
 *
 */
public class DeleteAssetReferencesInResources extends ResourcesTool{

	/**
	 * The assetPath to be deleted
	 */
	protected String assetPath;
	
	public DeleteAssetReferencesInResources(Resources resources, String assetPath) throws CloneNotSupportedException {
		super(resources, null,  -1, -1);
		this.assetPath = assetPath;
	}

	@Override
	public boolean doTool() {
		boolean done = false;
		// Search in the types of the resources
		for( String type : resources.getAssetTypes( ) ) {
			if( resources.getAssetPath( type ).equals( assetPath ) ) {
				resources.deleteAsset( type );
				done = true;
			}
		}
		return done;
	}

}
