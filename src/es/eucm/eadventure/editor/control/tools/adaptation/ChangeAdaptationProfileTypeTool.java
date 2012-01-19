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
