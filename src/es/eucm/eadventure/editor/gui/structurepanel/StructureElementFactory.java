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
package es.eucm.eadventure.editor.gui.structurepanel;

import javax.swing.ImageIcon;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfilesDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfilesDataControl;
import es.eucm.eadventure.editor.control.controllers.cutscene.CutsceneDataControl;
import es.eucm.eadventure.editor.control.controllers.globalstate.GlobalStateListDataControl;
import es.eucm.eadventure.editor.control.controllers.macro.MacroListDataControl;
import es.eucm.eadventure.editor.control.controllers.timer.TimersListDataControl;

public class StructureElementFactory {

    public static StructureElement getStructureElement( DataControl dataControl, StructureListElement parent ) {

        if( dataControl instanceof TimersListDataControl ) {
            StructureElement temp = new StructureElement( dataControl, parent );
            temp.setName( TC.get( "TimersList.Title" ) );
            temp.setIcon( new ImageIcon( "img/icons/advanced.png" ) );
            return temp;
        }
        if( dataControl instanceof AdaptationProfilesDataControl ) {
            StructureElement temp = new StructureElement( dataControl, parent );
            temp.setName( TC.get( "AdaptationProfiles.Title" ) );
            temp.setIcon( new ImageIcon( "img/icons/adaptationProfiles.png" ) );
            return temp;
        }
        if( dataControl instanceof AssessmentProfilesDataControl ) {
            StructureElement temp = new StructureElement( dataControl, parent );
            temp.setName( TC.get( "AssessmentProfiles.Title" ) );
            temp.setIcon( new ImageIcon( "img/icons/assessmentProfiles.png" ) );
            return temp;
        }
        if( dataControl instanceof GlobalStateListDataControl ) {
            StructureElement temp = new StructureElement( dataControl, parent );
            temp.setName( TC.get( "GlobalStatesList.Title" ) );
            temp.setIcon( new ImageIcon( "img/icons/groups16.png" ) );
            return temp;
        }
        if( dataControl instanceof MacroListDataControl ) {
            StructureElement temp = new StructureElement( dataControl, parent );
            temp.setName( TC.get( "MacrosList.Title" ) );
            temp.setIcon( new ImageIcon( "img/icons/macros.png" ) );
            return temp;
        }
        if( dataControl instanceof CutsceneDataControl ) {
            StructureElement temp = new StructureElement( dataControl, parent );
            if( ( (CutsceneDataControl) dataControl ).getType( ) == Controller.CUTSCENE_SLIDES )
                temp.setIcon( new ImageIcon( "img/icons/slidescene.png" ) );
            if( ( (CutsceneDataControl) dataControl ).getType( ) == Controller.CUTSCENE_VIDEO )
                temp.setIcon( new ImageIcon( "img/icons/videoscene.png" ) );
            return temp;
        }
        return new StructureElement( dataControl, parent );
    }

}
