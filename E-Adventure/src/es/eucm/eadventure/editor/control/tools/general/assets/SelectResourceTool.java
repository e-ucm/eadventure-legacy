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

import es.eucm.eadventure.common.auxiliar.AssetsConstants;
import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.data.AssetInformation;
import es.eucm.eadventure.editor.gui.assetchooser.AssetChooser;

/**
 * Edition tool for selecting a resource. It supports undo, redo but not
 * combine.
 * 
 * @author Javier
 * 
 */
public class SelectResourceTool extends ResourcesTool {

    public SelectResourceTool( Resources resources, AssetInformation[] assetsInformation, int resourcesType, int index ) throws CloneNotSupportedException {

        super( resources, assetsInformation, resourcesType, index );
    }

    @Override
    public boolean doTool( ) {

        boolean done = false;

        String selectedAsset = null;
        AssetChooser chooser = AssetsController.getAssetChooser( assetsInformation[index].category, assetsInformation[index].filter );
        int option = chooser.showAssetChooser( controller.peekWindow( ) );
        //In case the asset was selected from the zip file
        if( option == AssetChooser.ASSET_FROM_ZIP ) {
            selectedAsset = chooser.getSelectedAsset( );
        }

        //In case the asset was not in the zip file: first add it
        else if( option == AssetChooser.ASSET_FROM_OUTSIDE ) {
            boolean added = AssetsController.addSingleAsset( assetsInformation[index].category, chooser.getSelectedFile( ).getAbsolutePath( ) );
            if( added ) {
                selectedAsset = chooser.getSelectedFile( ).getName( );
            }
        }

        // If a file was selected
        if( selectedAsset != null ) {
            // Take the index of the selected asset
            String[] assetFilenames = AssetsController.getAssetFilenames( assetsInformation[index].category, assetsInformation[index].filter );
            String[] assetPaths = AssetsController.getAssetsList( assetsInformation[index].category, assetsInformation[index].filter );
            int assetIndex = -1;
            for( int i = 0; i < assetFilenames.length; i++ )
                if( assetFilenames[i].equals( selectedAsset ) )
                    assetIndex = i;

            // Store the data in the resources block (removing the suffix if necessary)
            if( assetsInformation[index].category == AssetsConstants.CATEGORY_ANIMATION ) {
                done = resources.addAsset( assetsInformation[index].name, AssetsController.removeSuffix( assetPaths[assetIndex] ) );

                // For player and character resources block, check if the other animations are set. When any are set, ask the user to set them automatically
                if( resourcesType == Controller.PLAYER || resourcesType == Controller.NPC ) {
                    boolean someAnimationSet = false;
                    for( int i = 0; i < assetsInformation.length; i++ ) {
                        if( i != index && resources.getAssetPath( assetsInformation[i].name ) != null && !resources.getAssetPath( assetsInformation[i].name ).equals( "" ) ) {
                            someAnimationSet = true;
                            break;
                        }
                    }

                    if( !someAnimationSet && controller.showStrictConfirmDialog( TC.get( "Operation.SetAllAnimations.Title" ), TC.get( "Operation.SetAllAnimations.Message" ) ) ) {
                        for( int i = 0; i < assetsInformation.length; i++ ) {
                            if( i != index ) {
                                done |= resources.addAsset( assetsInformation[i].name, AssetsController.removeSuffix( assetPaths[assetIndex] ) );
                            }
                        }
                    }
                }
            }
            else {
                done = resources.addAsset( assetsInformation[index].name, assetPaths[assetIndex] );
            }
        }
        return done;
    }

    @Override
    public boolean undoTool( ) {

        boolean done = super.undoTool( );
        if( done ) {
            Controller.getInstance( ).updatePanel( );
        }
        return done;
    }

    @Override
    public boolean redoTool( ) {

        boolean done = super.redoTool( );
        if( done ) {
            Controller.getInstance( ).updatePanel( );
        }
        return done;
    }

    /**
     * Uses a SelectResourceTool to get the assetPath of a resource belonging to
     * the given category using an asset chooser with the given filter
     * 
     * @param category
     * @param filter
     * @return
     */
    public static String selectAssetPathUsingChooser( int category, int filter ) {

        String assetPath = null;

        Resources resources = new Resources( );
        AssetInformation[] assetsInformation = new AssetInformation[ 1 ];
        assetsInformation[0] = new AssetInformation( "", "marihuanhell", false, category, filter );
        int resourcesType = -10;
        int index = 0;
        try {
            SelectResourceTool tool = new SelectResourceTool( resources, assetsInformation, resourcesType, index );
            tool.doTool( );
            if( tool.resources.existAsset( "marihuanhell" ) )
                assetPath = tool.resources.getAssetPath( "marihuanhell" );
        }
        catch( CloneNotSupportedException e ) {
            ReportDialog.GenerateErrorReport( e, true, "Error selecting asset path" );
        }
        return assetPath;
    }

}
