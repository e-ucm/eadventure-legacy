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
package es.eucm.eadventure.editor.control.tools.structurepanel;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.structurepanel.StructureControl;

public class RenameElementTool extends Tool {

    private DataControl dataControl;

    private String oldName;

    private String newName;

    public RenameElementTool( DataControl dataControl, String string ) {

        this.dataControl = dataControl;
        this.newName = string;
    }

    @Override
    public boolean canRedo( ) {

        return true;
    }

    @Override
    public boolean canUndo( ) {

        return ( oldName != null );
    }

    @Override
    public boolean doTool( ) {

        if( newName.length( ) == 0 )
            return false;
        if( dataControl.canBeRenamed( ) ) {
            oldName = dataControl.renameElement( newName );

            renameSelectedProfile( oldName, newName );
            if( oldName != null && !oldName.equals( newName ) ) {
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean redoTool( ) {

        if( dataControl.canBeRenamed( ) ) {
            oldName = dataControl.renameElement( newName );
            renameSelectedProfile( oldName, newName );
            if( oldName != null ) {
                Controller.getInstance( ).updateStructure( );
                StructureControl.getInstance( ).changeDataControl( dataControl );
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean undoTool( ) {

        if( dataControl.canBeRenamed( ) ) {
            newName = dataControl.renameElement( oldName );
            renameSelectedProfile( newName, oldName );
            if( newName != null ) {
                Controller.getInstance( ).updateStructure( );
                StructureControl.getInstance( ).changeDataControl( dataControl );
                return true;
            }
        }
        return false;
    }

    private void renameSelectedProfile( String name1, String name2 ) {

        // if has been renamed a selected adaptation or assessment profile, change that name
        if( dataControl instanceof AdaptationProfileDataControl ) {
            if( Controller.getInstance( ).getSelectedChapterDataControl( ).getAdaptationName( ).equals( name1 ) )
                ( (Chapter) Controller.getInstance( ).getSelectedChapterDataControl( ).getContent( ) ).setAdaptationName( name2 );
        }

        if( dataControl instanceof AssessmentProfileDataControl ) {
            if( Controller.getInstance( ).getSelectedChapterDataControl( ).getAssessmentName( ).equals( name1 ) )
                ( (Chapter) Controller.getInstance( ).getSelectedChapterDataControl( ).getContent( ) ).setAssessmentName( name2 );
        }
    }

    @Override
    public boolean combine( Tool other ) {

        return false;
    }

}
