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
