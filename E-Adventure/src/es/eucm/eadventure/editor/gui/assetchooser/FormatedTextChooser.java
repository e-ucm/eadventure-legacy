/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.assetchooser;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import es.eucm.eadventure.common.auxiliar.AssetsConstants;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.gui.otherpanels.FormattedTextPanel;

public class FormatedTextChooser extends AssetChooser {

    /**
     * Required
     */
    private static final long serialVersionUID = 1L;

    private FormattedTextPanel previewPanel;

    private JScrollPane scrollPane;

    public FormatedTextChooser( ) {

        super( AssetsConstants.CATEGORY_STYLED_TEXT, AssetsController.FILTER_NONE, AssetChooser.PREVIEW_LOCATION_SOUTH, TC.get( "AssetsChooser.FormattedText" ) );
    }

    @Override
    protected void createPreviewPanel( Container parent ) {

        previewPanel = new FormattedTextPanel( );
        JPanel previewPanelContainer = new JPanel( );
        previewPanelContainer.setLayout( new BorderLayout( ) );
        scrollPane = new JScrollPane( previewPanel );
        previewPanelContainer.add( scrollPane, BorderLayout.CENTER );
        //previewPanelContainer.setMaximumSize(new Dimension(200,200));
        previewPanelContainer.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "FormattedTextAssets.Preview" ) ) );
        parent.add( previewPanelContainer );
    }

    @Override
    protected void updatePreview( ) {

        // If there is an asset selected, show it
        if( getSelectedAsset( ) != null ) {
            String[] assetFilenames = AssetsController.getAssetFilenames( AssetsConstants.CATEGORY_STYLED_TEXT );
            String[] assetPaths = AssetsController.getAssetsList( AssetsConstants.CATEGORY_STYLED_TEXT );
            int assetIndex = -1;
            for( int i = 0; i < assetFilenames.length; i++ )
                if( assetFilenames[i].equals( getSelectedAsset( ) ) )
                    assetIndex = i;

            previewPanel.loadFile( assetPaths[assetIndex] );

            //previewPanel.setPage( getSelectedAsset )
        }
        else if( getSelectedFile( ) != null ) {
            previewPanel.loadFile( getSelectedFile( ).getAbsolutePath( ) );

            // Else, delete the preview image
        }
        else {
            previewPanel.removeFile( );
        }
    }

}
