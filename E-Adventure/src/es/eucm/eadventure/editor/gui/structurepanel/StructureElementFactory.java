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
