/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
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
