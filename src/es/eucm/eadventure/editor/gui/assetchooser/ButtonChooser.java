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

import java.awt.Container;

import javax.swing.BorderFactory;

import es.eucm.eadventure.common.auxiliar.AssetsConstants;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.ImagePanel;

public class ButtonChooser extends AssetChooser {

    private ImagePanel imagePanel;

    public ButtonChooser( ) {

        super( AssetsConstants.CATEGORY_BUTTON, AssetsController.FILTER_NONE, AssetChooser.PREVIEW_LOCATION_WEST, TextConstants.getText( "AssetsChooser.Button" ) );
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
            String[] assetFilenames = AssetsController.getAssetFilenames( AssetsConstants.CATEGORY_BUTTON );
            String[] assetPaths = AssetsController.getAssetsList( AssetsConstants.CATEGORY_BUTTON );
            int assetIndex = -1;
            for( int i = 0; i < assetFilenames.length; i++ )
                if( assetFilenames[i].equals( getSelectedAsset( ) ) )
                    assetIndex = i;

            imagePanel.loadImage( assetPaths[assetIndex] );
        }
        else if( getSelectedFile( ) != null ) {
            imagePanel.loadImage( getSelectedFile( ).getAbsolutePath( ) );

            // Else, delete the preview image
        }
        else {
            imagePanel.removeImage( );
        }

    }

}
