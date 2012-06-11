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
package es.eucm.eadventure.editor.control.tools.general.assets;

import java.io.File;

import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.Controller;
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
    
    // Added v.15
    protected String destinyAssetName;

    public EditResourceTool( Resources resources, AssetInformation[] assetsInformation, int index, String filename, String destinyAssetName ) throws CloneNotSupportedException {

        super( resources, assetsInformation, -1, index );
        this.filename = filename;
        this.destinyAssetName = destinyAssetName;
    }

    @Override
    public boolean doTool( ) {

        boolean done = false;

        AssetsController.addSingleAsset( assetsInformation[index].category, filename, destinyAssetName, true );
        // Dirty fix?
        String selectedAsset = destinyAssetName==null?( new File( filename ) ).getName( ):destinyAssetName;
        // If a file was selected
        if( selectedAsset != null ) {
            
            // Take the index of the selected asset
            String[] assetFilenames = AssetsController.getAssetFilenames( assetsInformation[index].category, assetsInformation[index].filter );
            String[] assetPaths = AssetsController.getAssetsList( assetsInformation[index].category, assetsInformation[index].filter );
            int assetIndex = -1;
            for( int i = 0; i < assetFilenames.length; i++ )
                if( assetFilenames[i].equals( selectedAsset ) )
                    assetIndex = i;
            
            // check if the asset is "standright" or "standleft" in order to modify the attr assetNecessary
            // for the assetInformation
            if (assetsInformation[index].name.equals( "standright" ) ){
               // if "standright" asset is necessary, set the "standleft" as not necessary
               if (assetsInformation[index].assetNecessary){ 
                for (int i=0;  i < assetsInformation.length; i++ ){
                    if (assetsInformation[i].name.equals( "standleft" ))
                        assetsInformation[i].assetNecessary = false;
                }
               } 
               //if is not art necessary and is 3rd person game, look for "standleft", if this asset is 
               // not necessary, set "standright as necessary"
               else if (!Controller.getInstance( ).isPlayTransparent( )){
                   for (int i=0;  i < assetsInformation.length; i++ ){
                       if (assetsInformation[i].name.equals( "standleft" )){
                           assetsInformation[index].assetNecessary = true;
                           assetsInformation[i].assetNecessary = false;
                       }
                   }
               }
            } else if (assetsInformation[index].name.equals( "standleft" )){
             // if "standleft" asset is necessary, set the "standright" as not necessary
                if (assetsInformation[index].assetNecessary){ 
                 for (int i=0;  i < assetsInformation.length; i++ ){
                         assetsInformation[i].assetNecessary = false;
                 }
                } //if is not art necessary and is 3rd person game, look for "standright", if this asset is 
                // not necessary, set "standright as necessary"
                else if (!Controller.getInstance( ).isPlayTransparent( )){
                    for (int i=0;  i < assetsInformation.length; i++ ){
                        if (assetsInformation[i].name.equals( "standright" )){
                            assetsInformation[index].assetNecessary = true;
                            assetsInformation[i].assetNecessary = false;
                        }
                    }
                }
            }
            //The empty animation is, in fact, a special asset. When this asset is in an animation, it is considered as animation asset.
            // For this reason,at this point, assetIndex is = -1. So, if animation is emptyAnimation, change the path in addAsset method
            boolean changeFilter = false;
            String specialPath = AssetsController.CATEGORY_SPECIAL_ASSETS + "/" + "EmptyAnimation.eaa";
            if ( filename.contains( "EmptyAnimation" ))
                changeFilter = true;
            
            resources.addAsset( assetsInformation[index].name,changeFilter?specialPath:assetPaths[assetIndex] );
            done = true;
        

        }
        return done;
    
    }

}
