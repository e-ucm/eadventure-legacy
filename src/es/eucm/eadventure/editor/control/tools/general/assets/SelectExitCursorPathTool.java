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
package es.eucm.eadventure.editor.control.tools.general.assets;

import es.eucm.eadventure.common.auxiliar.AssetsConstants;
import es.eucm.eadventure.common.data.chapter.ExitLook;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.data.AssetInformation;

public class SelectExitCursorPathTool extends SelectResourceTool {

    protected static final String CURSOR_STR = "cursor";

    protected ExitLook exitLook;

    protected static AssetInformation[] createAssetInfoArray( ) {

        AssetInformation[] array = new AssetInformation[ 1 ];
        array[0] = new AssetInformation( "", CURSOR_STR, true, AssetsConstants.CATEGORY_CURSOR, AssetsController.FILTER_NONE );
        return array;
    }

    protected static Resources createResources( ExitLook exitLook ) {

        Resources resources = new Resources( );
        resources.addAsset( CURSOR_STR, exitLook.getCursorPath( ) );
        return resources;
    }

    public SelectExitCursorPathTool( ExitLook exitLook ) throws CloneNotSupportedException {

        super( createResources( exitLook ), createAssetInfoArray( ), Controller.EXIT, 0 );
        this.exitLook = exitLook;
    }

    @Override
    public boolean undoTool( ) {

        boolean done = super.undoTool( );
        if( !done )
            return false;
        else {
            exitLook.setCursorPath( resources.getAssetPath( CURSOR_STR ) );
            controller.updatePanel( );
            return true;
        }

    }

    @Override
    public boolean redoTool( ) {

        boolean done = super.redoTool( );
        if( !done )
            return false;
        else {
            exitLook.setCursorPath( resources.getAssetPath( CURSOR_STR ) );
            controller.updatePanel( );
            return true;
        }
    }

    @Override
    public boolean doTool( ) {

        boolean done = super.doTool( );
        if( !done )
            return false;
        else {
            exitLook.setCursorPath( resources.getAssetPath( CURSOR_STR ) );
            //System.out.println("NEW CURSOR PATH = "+resources.getAssetPath(CURSOR_STR));
            return true;
        }
    }

}
