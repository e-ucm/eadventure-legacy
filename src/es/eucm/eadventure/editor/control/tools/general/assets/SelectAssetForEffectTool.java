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
import es.eucm.eadventure.common.data.chapter.ExitLook;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.data.AssetInformation;

public class SelectAssetForEffectTool extends SelectResourceTool {

    protected static final String ANIMATION_EFF = "anim";

    protected static final String AUDIO_EFF = "audio";

    protected ExitLook exitLook;

    protected static AssetInformation[] createAssetInfoArray( ) {

        AssetInformation[] array = new AssetInformation[ 1 ];
        array[0] = new AssetInformation( "", ANIMATION_EFF, true, AssetsConstants.CATEGORY_CURSOR, AssetsController.FILTER_NONE );
        return array;
    }

    protected static Resources createResources( ExitLook exitLook ) {

        Resources resources = new Resources( );
        resources.addAsset( ANIMATION_EFF, exitLook.getCursorPath( ) );
        return resources;
    }

    public SelectAssetForEffectTool( ExitLook exitLook ) throws CloneNotSupportedException {

        super( createResources( exitLook ), createAssetInfoArray( ), Controller.EXIT, 0 );
        this.exitLook = exitLook;
    }

    @Override
    public boolean undoTool( ) {

        boolean done = super.undoTool( );
        if( !done )
            return false;
        else {
            exitLook.setCursorPath( resources.getAssetPath( ANIMATION_EFF ) );
            //System.out.println("NEW CURSOR PATH = "+resources.getAssetPath(CURSOR_STR));
            controller.reloadPanel( );
            return true;
        }

    }

    @Override
    public boolean redoTool( ) {

        boolean done = super.redoTool( );
        if( !done )
            return false;
        else {
            //System.out.println("NEW CURSOR PATH = "+resources.getAssetPath(CURSOR_STR));
            exitLook.setCursorPath( resources.getAssetPath( ANIMATION_EFF ) );
            controller.reloadPanel( );
            return true;
        }
    }

    @Override
    public boolean doTool( ) {

        boolean done = super.doTool( );
        if( !done )
            return false;
        else {
            exitLook.setCursorPath( resources.getAssetPath( ANIMATION_EFF ) );
            //System.out.println("NEW CURSOR PATH = "+resources.getAssetPath(CURSOR_STR));
            return true;
        }
    }

}
