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
package es.eucm.eadventure.editor.control.controllers.general;

import java.util.List;

import es.eucm.eadventure.common.auxiliar.AssetsConstants;
import es.eucm.eadventure.common.data.chapter.Exit;
import es.eucm.eadventure.common.data.chapter.ExitLook;
import es.eucm.eadventure.common.data.chapter.NextScene;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.tools.general.InvalidExitCursorTool;
import es.eucm.eadventure.editor.control.tools.general.assets.SelectExitCursorPathTool;

public class ExitLookDataControl {

    private ExitLook exitLook;

    public ExitLookDataControl( NextScene nextScene ) {

        if( nextScene.getExitLook( ) == null )
            nextScene.setExitLook( new ExitLook( ) );
        this.exitLook = nextScene.getExitLook( );
    }

    public ExitLookDataControl( Exit exit ) {

        if( exit.getDefaultExitLook( ) == null )
            exit.setDefaultExitLook( new ExitLook( ) );
        this.exitLook = exit.getDefaultExitLook( );
    }

    /**
     * @return the isTextCustomized
     */
    public boolean isTextCustomized( ) {

        return exitLook.getExitText( ) != null;
    }

    public String getCustomizedText( ) {

        String text = null;
        if( exitLook != null && exitLook.getExitText( ) != null )
            text = exitLook.getExitText( );
        return text;
    }

    /**
     * @return the isCursorCustomized
     */
    public boolean isCursorCustomized( ) {

        return exitLook.getCursorPath( ) != null;
    }

    public String getCustomizedCursor( ) {

        String text = null;
        if( exitLook != null && exitLook.getCursorPath( ) != null )
            text = exitLook.getCursorPath( );
        return text;
    }

    public void setExitText( String text ) {

        this.exitLook.setExitText( text );
    }

    public void editCursorPath( ) {

        try {
            Controller.getInstance( ).addTool( new SelectExitCursorPathTool( exitLook ) );
        }
        catch( CloneNotSupportedException e ) {
            e.printStackTrace( );
        }
    }

    public void invalidCursor( ) {

        Controller.getInstance( ).addTool( new InvalidExitCursorTool( exitLook ) );
    }

    public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {

        if( this.isCursorCustomized( ) ) {
            boolean add = true;
            for( String asset : assetPaths ) {
                if( asset.equals( exitLook.getCursorPath( ) ) ) {
                    add = false;
                    break;
                }
            }
            if( add ) {
                int last = assetPaths.size( );
                assetPaths.add( last, exitLook.getCursorPath( ) );
                assetTypes.add( last, AssetsConstants.CATEGORY_CURSOR );
            }
        }

    }

    public int countAssetReferences( String assetPath ) {

        if( exitLook.getCursorPath( ) != null && exitLook.getCursorPath( ).equals( assetPath ) )
            return 1;
        else
            return 0;

    }

    public void deleteAssetReferences( String assetPath ) {

        if( exitLook.getCursorPath( ) != null && exitLook.getCursorPath( ).equals( assetPath ) )
            exitLook.setCursorPath( "" );

    }

    public void setCursorPath( String value ) {

        exitLook.setCursorPath( value );
    }

}
