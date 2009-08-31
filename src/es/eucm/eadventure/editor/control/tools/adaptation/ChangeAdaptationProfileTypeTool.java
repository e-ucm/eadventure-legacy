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
package es.eucm.eadventure.editor.control.tools.adaptation;

import es.eucm.eadventure.common.data.adaptation.AdaptationProfile;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Changes the type of the profile between scorm12, scorm2004 and normal
 * 
 */
public class ChangeAdaptationProfileTypeTool extends Tool {

    public static final int NORMAL = 0;

    public static final int SCORM12 = 1;

    public static final int SCORM2004 = 2;

    private int type;

    private AdaptationProfile adapProfile;

    private int oldType;

    public ChangeAdaptationProfileTypeTool( AdaptationProfile profile, int type, boolean scorm12, boolean scorm2004 ) {

        this.type = type;
        this.adapProfile = profile;

        // get the old type, as result of previous values
        // the combination scorm12 and scorm2004 is not possible
        if( !scorm12 && !scorm2004 )
            oldType = NORMAL;
        else if( scorm12 && !scorm2004 )
            oldType = SCORM12;
        else if( !scorm12 && scorm2004 )
            oldType = SCORM2004;

    }

    @Override
    public boolean canRedo( ) {

        return true;
    }

    @Override
    public boolean canUndo( ) {

        return true;
    }

    @Override
    public boolean combine( Tool other ) {

        return false;
    }

    @Override
    public boolean doTool( ) {

        if( type == NORMAL )
            adapProfile.changeToNormalProfile( );
        else if( type == SCORM12 )
            adapProfile.changeToScorm12Profile( );
        else if( type == SCORM2004 )
            adapProfile.changeToScorm2004Profile( );
        return true;
    }

    @Override
    public boolean redoTool( ) {

        doTool( );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        if( oldType == NORMAL )
            adapProfile.changeToNormalProfile( );
        else if( oldType == SCORM12 )
            adapProfile.changeToScorm12Profile( );
        else if( oldType == SCORM2004 )
            adapProfile.changeToScorm2004Profile( );
        Controller.getInstance( ).updatePanel( );
        return true;
    }
}
