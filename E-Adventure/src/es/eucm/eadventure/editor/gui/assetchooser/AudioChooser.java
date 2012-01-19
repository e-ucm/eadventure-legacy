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

import java.awt.Container;

import javax.swing.BorderFactory;

import es.eucm.eadventure.common.auxiliar.AssetsConstants;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.gui.otherpanels.AudioPanel;

public class AudioChooser extends AssetChooser {

    private AudioPanel audioPanel;

    public AudioChooser( int filter ) {

        super( AssetsConstants.CATEGORY_AUDIO, filter, AssetChooser.PREVIEW_LOCATION_SOUTH, TC.get( "AssetsChooser.Audio" ) );
    }

    @Override
    protected void createPreviewPanel( Container parent ) {

        // Create a panel for the element preview
        audioPanel = new AudioPanel( );
        audioPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "AudioAssets.Preview" ) ) );
        parent.add( audioPanel );
    }

    @Override
    protected void updatePreview( ) {

        // If there is an asset selected, show it
        if( getSelectedAsset( ) != null ) {
            String[] assetFilenames = AssetsController.getAssetFilenames( AssetsConstants.CATEGORY_AUDIO );
            String[] assetPaths = AssetsController.getAssetsList( AssetsConstants.CATEGORY_AUDIO );
            int assetIndex = -1;
            for( int i = 0; i < assetFilenames.length; i++ )
                if( assetFilenames[i].equals( getSelectedAsset( ) ) )
                    assetIndex = i;

            audioPanel.loadAudio( assetPaths[assetIndex] );
        }
        else if( getSelectedFile( ) != null ) {
            audioPanel.loadAudio( getSelectedFile( ).getAbsolutePath( ) );

            // Else, delete the preview image
        }
        else {
            audioPanel.removeAudio( );
        }

    }

}
