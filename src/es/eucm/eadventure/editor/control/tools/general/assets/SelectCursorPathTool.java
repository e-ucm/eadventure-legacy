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
            resources.addAsset( type, "NULL" );
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
                    if( added )
                        adventureData.getCursors( ).remove( i );
                    else
                        adventureData.getCursors( ).get( i ).setPath( resources.getAssetPath( type ) );
                    break;

                }
            }
            controller.updatePanel( );
            return true;
        }

    }

    @Override
    public boolean redoTool( ) {

        if( added )
            adventureData.addCursor( type, "" );
        boolean done = super.redoTool( );
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

        if( resources.getAssetPath( type ).equals( "NULL" ) ) {
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
