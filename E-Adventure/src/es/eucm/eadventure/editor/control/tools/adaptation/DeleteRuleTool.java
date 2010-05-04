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

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationRuleDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentRuleDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeleteRuleTool extends Tool {

    protected DataControl dataControl;

    protected List<AdaptationRuleDataControl> oldAdapRules;

    protected List<AssessmentRuleDataControl> oldAssessRules;

    protected int type;

    protected int index;

    public DeleteRuleTool( DataControl dataControl, int type, int index ) {

        this.dataControl = dataControl;
        this.type = type;
        this.index = index;
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

        //Check there is at least one flag
        if( dataControl.canBeDeleted( ) ) {

            if( type == Controller.ADAPTATION_RULE ) {
                oldAdapRules = new ArrayList<AdaptationRuleDataControl>( ( (AdaptationProfileDataControl) dataControl ).getDataControls( ) );
                return dataControl.deleteElement( ( (AdaptationProfileDataControl) dataControl ).getAdaptationRules( ).get( index ), true );
            }else
            if( type == Controller.ASSESSMENT_RULE || type == Controller.TIMED_ASSESSMENT_RULE ) {
                oldAssessRules = new ArrayList<AssessmentRuleDataControl>( ( (AssessmentProfileDataControl) dataControl ).getDataControls( ) );
                return dataControl.deleteElement( ( (AssessmentProfileDataControl) dataControl ).getAssessmentRules( ).get( index ), true );
            }

        }
        return false;
    }

    @Override
    public boolean redoTool( ) {

        //Check there is at least one flag
        if( dataControl.canBeDeleted( ) ) {
            if( type == Controller.ADAPTATION_RULE ) {
                if( dataControl.deleteElement( ( (AdaptationProfileDataControl) dataControl ).getAdaptationRules( ).get( index ), true ) ) {
                    Controller.getInstance( ).updatePanel( );
                    return true;
                }
                else {
                    return false;
                }
            } else
            if( type == Controller.ASSESSMENT_RULE ) {
                if( dataControl.deleteElement( ( (AssessmentProfileDataControl) dataControl ).getAssessmentRules( ).get( index ), true ) ) {
                    Controller.getInstance( ).updatePanel( );
                    return true;
                }
                else {
                    return false;
                }
            }

        }
        return false;
    }

    @Override
    public boolean undoTool( ) {

        if( type == Controller.ADAPTATION_RULE ) {
            ( (AdaptationProfileDataControl) dataControl ).setDataControlsAndData( new ArrayList( oldAdapRules ) );
            Controller.getInstance( ).getIdentifierSummary( ).addAdaptationRuleId( oldAdapRules.get( index ).getId( ), ( (AdaptationProfileDataControl) dataControl ).getName() );
            Controller.getInstance( ).updatePanel( );
            return true;
        }
        else if( type == Controller.ASSESSMENT_RULE || type == Controller.TIMED_ASSESSMENT_RULE ) {
            ( (AssessmentProfileDataControl) dataControl ).setDataControlsAndData( new ArrayList( oldAssessRules ) );
            Controller.getInstance( ).getIdentifierSummary( ).addAssessmentRuleId( oldAssessRules.get( index ).getId( ) , ( (AssessmentProfileDataControl) dataControl ).getName() );
            Controller.getInstance( ).updatePanel( );
            return true;
        }
        else
            return false;

    }

}
