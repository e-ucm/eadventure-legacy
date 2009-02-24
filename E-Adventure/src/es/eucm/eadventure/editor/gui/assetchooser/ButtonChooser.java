package es.eucm.eadventure.editor.gui.assetchooser;

import java.awt.Container;

import javax.swing.BorderFactory;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.ImagePanel;

public class ButtonChooser extends AssetChooser{

	private ImagePanel imagePanel;

	public ButtonChooser( ) {
		super( AssetsController.CATEGORY_BUTTON, AssetsController.FILTER_NONE, AssetChooser.PREVIEW_LOCATION_WEST, TextConstants.getText( "AssetsChooser.Button" ) );
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void createPreviewPanel( Container parent ) {
		imagePanel = new ImagePanel( );
		imagePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ButtonAssets.Preview" ) ) );
		parent.add( imagePanel );
	}

	@Override
	protected void updatePreview( ) {
		// If there is an asset selected, show it
		if( getSelectedAsset( ) != null ) {
			String[] assetFilenames = AssetsController.getAssetFilenames( AssetsController.CATEGORY_BUTTON );
			String[] assetPaths = AssetsController.getAssetsList( AssetsController.CATEGORY_BUTTON );
			int assetIndex = -1;
			for( int i = 0; i < assetFilenames.length; i++ )
				if( assetFilenames[i].equals( getSelectedAsset( ) ) )
					assetIndex = i;

			imagePanel.loadImage( assetPaths[assetIndex] );
		} else if( getSelectedFile( ) != null ) {
			imagePanel.loadImage( getSelectedFile( ).getAbsolutePath( ) );

			// Else, delete the preview image
		} else {
			imagePanel.removeImage( );
		}

	}

}
