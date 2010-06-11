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
import es.eucm.eadventure.common.data.adventure.AdventureData;
import es.eucm.eadventure.common.data.adventure.CustomButton;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.data.AssetInformation;

public class SelectButtonTool extends SelectResourceTool {

    protected AdventureData adventureData;

    protected String type;

    protected String action;

    protected boolean removed;

    protected static AssetInformation[] createAssetInfoArray( String action, String type ) {

        AssetInformation[] array = new AssetInformation[ 1 ];
        array[0] = new AssetInformation( "", action + "#" + type, true, AssetsConstants.CATEGORY_BUTTON, AssetsController.FILTER_NONE );
        return array;
    }

    protected static Resources createResources( AdventureData adventureData, String action, String type ) {

        Resources resources = new Resources( );
        boolean introduced = false;
        for( int i = 0; i < adventureData.getButtons( ).size( ); i++ ) {
            CustomButton customButton = adventureData.getButtons( ).get( i );
            if( customButton.getType( ).equals( type ) && customButton.getAction( ).equals( action ) ) {
                resources.addAsset( action + "#" + type, customButton.getPath( ) );
                introduced = true;
                break;
            }
        }

        if( !introduced ) {
            resources.addAsset( action + "#" + type, /*"NULL"*/ null );
        }

        return resources;
    }

    public SelectButtonTool( AdventureData adventureData, String action, String type ) throws CloneNotSupportedException {

        super( createResources( adventureData, action, type ), createAssetInfoArray( action, type ), Controller.ACTION_CUSTOM, 0 );
        this.adventureData = adventureData;
        this.type = type;
        this.action = action;
    }

    @Override
    public boolean undoTool( ) {

        boolean done = super.undoTool( );
        if( !done )
            return false;
        else {
            for( int i = 0; i < adventureData.getButtons( ).size( ); i++ ) {
                if( adventureData.getButtons( ).get( i ).getType( ).equals( type ) && adventureData.getButtons( ).get( i ).getAction( ).equals( action ) ) {
                    if( removed ) {
                        adventureData.getButtons( ).remove( i );
                        setButton( action, type, resources.getAssetPath( action + "#" + type ) );
                    }
                    else
                        adventureData.getButtons( ).get( i ).setPath( resources.getAssetPath( action + "#" + type ) );
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

        if( removed )
            adventureData.addButton( action, type, "" );
        boolean done = super.redoTool( );
        if( !done )
            return false;
        else {
            for( int i = 0; i < adventureData.getButtons( ).size( ); i++ ) {
                if( adventureData.getButtons( ).get( i ).getType( ).equals( type ) && adventureData.getButtons( ).get( i ).getAction( ).equals( action ) ) {
                    adventureData.getButtons( ).get( i ).setPath( resources.getAssetPath( action + "#" + type ) );
                }
            }
            controller.updatePanel( );
            return true;
        }
    }

    @Override
    public boolean doTool( ) {

     //   if( resources.getAssetPath( action + "#" + type ).equals( "NULL" ) ) {
        if( resources.getAssetPath( action + "#" + type ) == null ) {
            removed = false;
        }
        else {
            for( int i = 0; i < adventureData.getButtons( ).size( ); i++ ) {
                CustomButton button = adventureData.getButtons( ).get( i );
                if( button.getAction( ).equals( action ) && button.getType( ).equals( type ) ) {
                    adventureData.getButtons( ).remove( button );
                    break;
                }
            }
            removed = true;
        }
        boolean done = super.doTool( );
        if( !done )
            return false;
        else {
            setButton( action, type, resources.getAssetPath( action + "#" + type ) );
            return true;
        }
    }

    public void setButton( String action, String type, String path ) {

        CustomButton button = new CustomButton( action, type, path );
        CustomButton temp = null;
        for( CustomButton cb : adventureData.getButtons( ) ) {
            if( cb.equals( button ) )
                temp = cb;
        }
        if( temp != null )
            adventureData.getButtons( ).remove( temp );
        System.out.println( "Adding button: " + button.getAction( ) + "," + button.getType( ) + "," + button.getPath( ) );
        adventureData.addButton( button );
    }
}
