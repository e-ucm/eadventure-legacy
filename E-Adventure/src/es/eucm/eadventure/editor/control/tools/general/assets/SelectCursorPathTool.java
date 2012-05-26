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

import es.eucm.eadventure.common.auxiliar.AssetsConstants;
import es.eucm.eadventure.common.data.adventure.AdventureData;
import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.data.AssetInformation;

public class SelectCursorPathTool extends SelectResourceTool {

    protected AdventureData adventureData;

    protected int t;

    protected String type;

    protected boolean added;

    protected static AssetInformation[] createAssetInfoArray( int t ) {

        String type = DescriptorData.getCursorTypeString( t );
        AssetInformation[] array = new AssetInformation[ 1 ];
        array[0] = new AssetInformation( "", type, true, AssetsConstants.CATEGORY_CURSOR, AssetsController.FILTER_NONE );
        return array;
    }

    protected static Resources createResources( AdventureData adventureData, int t ) {

        String type = DescriptorData.getCursorTypeString( t );
        Resources resources = new Resources( );
        boolean introduced = false;
        for( int i = 0; i < adventureData.getCursors( ).size( ); i++ ) {
            if( adventureData.getCursors( ).get( i ).getType( ).equals( type ) && adventureData.getCursors( ).get( i ).getPath( ) != null ) {
                resources.addAsset( type, adventureData.getCursors( ).get( i ).getPath( ) );
                introduced = true;
                break;
            }
        }

        if( !introduced ) {
            resources.addAsset( type, null );
        }

        return resources;
    }

    public SelectCursorPathTool( AdventureData adventureData, int t ) throws CloneNotSupportedException {

        super( createResources( adventureData, t ), createAssetInfoArray( t ), Controller.ACTION_CUSTOM, 0 );
        this.adventureData = adventureData;
        this.t = t;
        this.type = DescriptorData.getCursorTypeString( t );
    }

    @Override
    public boolean undoTool( ) {

        boolean done = super.undoTool( );
        if( !done )
            return false;
        else {
            for( int i = 0; i < adventureData.getCursors( ).size( ); i++ ) {
                if( adventureData.getCursors( ).get( i ).getType( ).equals( type ) ) {
                    if( added ){
                        adventureData.getCursors( ).remove( i );
                        //adventureData.addCursor( type, "" );
                    }
                    else
                        adventureData.getCursors( ).get( i ).setPath( resources.getAssetPath( type ) );
                    break;

                }
            }
            controller.updatePanel( );
            controller.dataModified( );
            return true;
        }

    }

    @Override
    public boolean redoTool( ) {

        
        boolean done = super.redoTool( );
        if( added )
          adventureData.addCursor( type, "" );
        if( !done )
            return false;
        else {
            for( int i = 0; i < adventureData.getCursors( ).size( ); i++ ) {
                if( adventureData.getCursors( ).get( i ).getType( ).equals( type ) ) {
                    adventureData.getCursors( ).get( i ).setPath( resources.getAssetPath( type ) );
                }
            }
            controller.updatePanel( );
            return true;
        }
    }

    @Override
    public boolean doTool( ) {

       // if( resources.getAssetPath( type ).equals( "NULL" ) ) {
        if( resources.getAssetPath( type )== null ) {
            adventureData.addCursor( type, "" );
            added = true;
        }
        else {
            added = false;
        }
        boolean done = super.doTool( );
        if( !done )
            return false;
        else {
            for( int i = 0; i < adventureData.getCursors( ).size( ); i++ ) {
                if( adventureData.getCursors( ).get( i ).getType( ).equals( type ) ) {
                    adventureData.getCursors( ).get( i ).setPath( resources.getAssetPath( type ) );
                }
            }
            return true;
        }
    }

}
