package es.eucm.eadventure.editor.gui.assetchooser;

import java.awt.Container;

import javax.swing.BorderFactory;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.gui.otherpanels.AnimationPanel;

public class AnimationChooser extends AssetChooser {

	/**
	 * Default generated serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private AnimationPanel animationPanel;

	public AnimationChooser( int filter ) {
		super( AssetsController.CATEGORY_ANIMATION, filter, AssetChooser.PREVIEW_LOCATION_WEST, TextConstants.getText( "AssetsChooser.Animation" ) );
	}

	@Override
	protected void createPreviewPanel( Container parent ) {
		animationPanel = new AnimationPanel( true );
		animationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AnimationAssets.Preview" ) ) );
		parent.add( animationPanel );
	}

	@Override
	protected void updatePreview( ) {
		// If there is an asset selected, show it
		if( getSelectedAsset( ) != null ) {
			String[] assetFilenames = AssetsController.getAssetFilenames( AssetsController.CATEGORY_ANIMATION );
			String[] assetPaths = AssetsController.getAssetsList( AssetsController.CATEGORY_ANIMATION );
			int assetIndex = -1;
			for( int i = 0; i < assetFilenames.length; i++ )
				if( assetFilenames[i].equals( getSelectedAsset( ) ) )
					assetIndex = i;

			animationPanel.loadAnimation( assetPaths[assetIndex] );
		} else if( getSelectedFile( ) != null ) {
			animationPanel.loadAnimation( getSelectedFile( ).getAbsolutePath( ) );
			// Else, delete the preview image
		} else {
			animationPanel.removeAnimation( );
		}
	}

}
