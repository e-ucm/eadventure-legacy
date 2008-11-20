package es.eucm.eadventure.editor.gui.assetchooser;

import java.awt.Container;

import javax.swing.BorderFactory;

import es.eucm.eadventure.common.auxiliar.FileFilter;
import es.eucm.eadventure.common.auxiliar.categoryfilters.AudioFileFilter;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.gui.otherpanels.AudioPanel;

public class AudioChooser extends AssetChooser {

	private AudioPanel audioPanel;

	public AudioChooser( int filter ) {
		super( AssetsController.CATEGORY_AUDIO, filter, AssetChooser.PREVIEW_LOCATION_SOUTH, TextConstants.getText( "AssetsChooser.Audio" ) );
	}

	@Override
	protected void createPreviewPanel( Container parent ) {
		// Create a panel for the element preview
		audioPanel = new AudioPanel( );
		audioPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AudioAssets.Preview" ) ) );
		parent.add( audioPanel );
	}

	@Override
	protected void updatePreview( ) {
		// If there is an asset selected, show it
		if( getSelectedAsset( ) != null ) {
			String[] assetFilenames = AssetsController.getAssetFilenames( AssetsController.CATEGORY_AUDIO );
			String[] assetPaths = AssetsController.getAssetsList( AssetsController.CATEGORY_AUDIO );
			int assetIndex = -1;
			for( int i = 0; i < assetFilenames.length; i++ )
				if( assetFilenames[i].equals( getSelectedAsset( ) ) )
					assetIndex = i;

			audioPanel.loadAudio( assetPaths[assetIndex] );
		} else if( getSelectedFile( ) != null ) {
			audioPanel.loadAudio( getSelectedFile( ).getAbsolutePath( ) );

			// Else, delete the preview image
		} else {
			audioPanel.removeAudio( );
		}

	}

}
