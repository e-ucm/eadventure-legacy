package es.eucm.eadventure.adventureeditor.gui.assetchooser;

import java.awt.Container;

import javax.swing.BorderFactory;

import es.eucm.eadventure.adventureeditor.control.auxiliar.FileFilter;
import es.eucm.eadventure.adventureeditor.control.auxiliar.categoryfilters.AudioFileFilter;
import es.eucm.eadventure.adventureeditor.control.controllers.AssetsController;
import es.eucm.eadventure.adventureeditor.gui.TextConstants;
import es.eucm.eadventure.adventureeditor.gui.otherpanels.AudioPanel;

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
