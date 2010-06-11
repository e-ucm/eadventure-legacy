/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.tools.general.assets;

import java.io.File;

import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.data.AssetInformation;

/**
 * Tool for editing Resources Blocks ("Edit" button). It cannot be combined as
 * well.
 * 
 * @author Javier
 * 
 */
public class EditResourceTool extends ResourcesTool {

    /**
     * The filename to edit
     */
    protected String filename;

    public EditResourceTool( Resources resources, AssetInformation[] assetsInformation, int index, String filename ) throws CloneNotSupportedException {

        super( resources, assetsInformation, -1, index );
        this.filename = filename;
    }

    @Override
    public boolean doTool( ) {

        boolean done = false;

        AssetsController.addSingleAsset( assetsInformation[index].category, filename );
        // Dirty fix?
        String selectedAsset = ( new File( filename ) ).getName( );
        // If a file was selected
        if( selectedAsset != null ) {
            // Take the index of the selected asset
            String[] assetFilenames = AssetsController.getAssetFilenames( assetsInformation[index].category, assetsInformation[index].filter );
            String[] assetPaths = AssetsController.getAssetsList( assetsInformation[index].category, assetsInformation[index].filter );
            int assetIndex = -1;
            for( int i = 0; i < assetFilenames.length; i++ )
                if( assetFilenames[i].equals( selectedAsset ) )
                    assetIndex = i;

            resources.addAsset( assetsInformation[index].name, assetPaths[assetIndex] );
            done = true;
        }

        return done;
    }

}
