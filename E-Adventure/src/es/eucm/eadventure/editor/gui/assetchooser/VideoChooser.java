package es.eucm.eadventure.editor.gui.assetchooser;

import java.awt.Container;

import es.eucm.eadventure.editor.control.auxiliar.FileFilter;
import es.eucm.eadventure.editor.control.auxiliar.categoryfilters.VideoFileFilter;
import es.eucm.eadventure.editor.control.controllers.AssetsController;

public class VideoChooser extends AssetChooser {

	public VideoChooser( ) {
		super( AssetsController.CATEGORY_VIDEO, AssetsController.FILTER_NONE, AssetChooser.PREVIEW_LOCATION_SOUTH, "Video chooser" );
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void createPreviewPanel( Container parent ) {

	}

	@Override
	protected void updatePreview( ) {

	}

}
