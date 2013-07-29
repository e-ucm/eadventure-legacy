/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
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
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.assetchooser;

import java.awt.Container;

import javax.swing.BorderFactory;

import es.eucm.eadventure.common.auxiliar.AssetsConstants;
import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.gui.otherpanels.AnimationPanel;

public class AnimationChooser extends AssetChooser {

    /**
     * Default generated serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private AnimationPanel animationPanel;

    public AnimationChooser( int filter ) {

        super( AssetsConstants.CATEGORY_ANIMATION, filter, AssetChooser.PREVIEW_LOCATION_WEST, TC.get( "AssetsChooser.Animation" ) );
    }

    @Override
    protected void createPreviewPanel( Container parent ) {

        animationPanel = new AnimationPanel( true, Animation.PREVIEW );
        animationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "AnimationAssets.Preview" ) ) );
        parent.add( animationPanel );
    }

    @Override
    protected void updatePreview( ) {

        // If there is an asset selected, show it
        if( getSelectedAsset( ) != null ) {
            String[] assetFilenames = AssetsController.getAssetFilenames( AssetsConstants.CATEGORY_ANIMATION );
            String[] assetPaths = AssetsController.getAssetsList( AssetsConstants.CATEGORY_ANIMATION );
            int assetIndex = -1;
            for( int i = 0; i < assetFilenames.length; i++ )
                if( assetFilenames[i].equals( getSelectedAsset( ) ) )
                    assetIndex = i;

            animationPanel.setWhere(Animation.EDITOR);
            animationPanel.loadAnimation( assetPaths[assetIndex] );
        }
        else if( getSelectedFile( ) != null ) {
            animationPanel.setWhere(Animation.PREVIEW);
            animationPanel.loadAnimation( getSelectedFile( ).getAbsolutePath( ) );
            // Else, delete the preview image
        }
        else {
            animationPanel.removeAnimation( );
        }
    }

}
