package es.eucm.eadventure.editor.gui.assetchooser;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import es.eucm.eadventure.common.auxiliar.FileFilter;
import es.eucm.eadventure.common.auxiliar.categoryfilters.FormattedTextFileFilter;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.gui.otherpanels.FormattedTextPanel;

public class FormatedTextChooser extends AssetChooser{

	/**
	 * Required
	 */
	private static final long serialVersionUID = 1L;
	
	private FormattedTextPanel previewPanel;
	
	private JScrollPane scrollPane;
	
	public FormatedTextChooser( ) {
		super( AssetsController.CATEGORY_STYLED_TEXT, AssetsController.FILTER_NONE, AssetChooser.PREVIEW_LOCATION_SOUTH, TextConstants.getText( "AssetsChooser.FormattedText" ) );
	}

	
	@Override
	protected void createPreviewPanel( Container parent ) {
		previewPanel = new FormattedTextPanel();
		JPanel previewPanelContainer = new JPanel();
		previewPanelContainer.setLayout(new BorderLayout());
		scrollPane = new JScrollPane(previewPanel);
		previewPanelContainer.add(scrollPane, BorderLayout.CENTER);
		//previewPanelContainer.setMaximumSize(new Dimension(200,200));
		previewPanelContainer.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "FormattedTextAssets.Preview" ) ) );
		parent.add( previewPanelContainer );
	}


	@Override
	protected void updatePreview( ) {
		// If there is an asset selected, show it
		if( getSelectedAsset( ) != null ) {
			String[] assetFilenames = AssetsController.getAssetFilenames( AssetsController.CATEGORY_STYLED_TEXT );
			String[] assetPaths = AssetsController.getAssetsList( AssetsController.CATEGORY_STYLED_TEXT );
			int assetIndex = -1;
			for( int i = 0; i < assetFilenames.length; i++ )
				if( assetFilenames[i].equals( getSelectedAsset( ) ) )
					assetIndex = i;

			previewPanel.loadFile( assetPaths[assetIndex] );

			//previewPanel.setPage( getSelectedAsset )
		} else if( getSelectedFile( ) != null ) {
			previewPanel.loadFile( getSelectedFile().getAbsolutePath( ) );

			// Else, delete the preview image
		} else {
			previewPanel.removeFile();
		}
	}

}
