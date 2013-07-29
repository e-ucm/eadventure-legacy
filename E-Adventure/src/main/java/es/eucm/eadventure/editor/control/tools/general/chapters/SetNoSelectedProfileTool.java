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
package es.eucm.eadventure.editor.control.tools.general.chapters;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class SetNoSelectedProfileTool extends Tool {

    public static final int MODE_ADAPTATION = Controller.ADAPTATION_PROFILE;

    public static final int MODE_ASSESSMENT = Controller.ASSESSMENT_PROFILE;

    public static final int MODE_UNKNOWN = -1;

    protected Chapter chapter;

    protected int mode;

    protected Controller controller;

    protected String oldValue;

    protected String newValue;

    public SetNoSelectedProfileTool( Chapter chapter, int mode ) {

        this.chapter = chapter;
        this.mode = mode;
        controller = Controller.getInstance( );
        newValue = "";
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

        boolean done = false;

        // Get old Value
        if( mode == MODE_ASSESSMENT ) {
            oldValue = chapter.getAssessmentName( );
        }
        else if( mode == MODE_ADAPTATION ) {
            oldValue = chapter.getAdaptationName( );
        }

        // If oldValue is null, it is a bug. FIX IT
        if( oldValue == null )
            setData( newValue );
        else if( !oldValue.equals( newValue ) ) {
            setData( newValue );
            done = true;
        }
        // update var/flags summary, because in adaptation and/or assessement profiles may have new var/flag
        controller.updateVarFlagSummary( );
        return done;
    }

    private void setData( String data ) {

        if( mode == MODE_ASSESSMENT ) {
            chapter.setAssessmentName( data );
        }
        else if( mode == MODE_ADAPTATION ) {
            chapter.setAdaptationName( data );
        }
    }

    @Override
    public boolean redoTool( ) {

        setData( newValue );
        // update var/flags summary, because in adaptation and/or assessement profiles may have new var/flag
        controller.updateVarFlagSummary( );
        controller.reloadPanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        setData( oldValue );
        // update var/flags summary, because in adaptation and/or assessement profiles may have new var/flag
        controller.updateVarFlagSummary( );
        controller.reloadPanel( );
        return true;
    }

}
