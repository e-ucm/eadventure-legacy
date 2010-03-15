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

import es.eucm.eadventure.common.data.adaptation.AdaptationRule;
import es.eucm.eadventure.common.data.assessment.AssessmentRule;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationRuleDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentRuleDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class AddRuleTool extends Tool {

    protected DataControl dataControl;

    protected List<AdaptationRuleDataControl> oldAdapRules;

    protected List<AssessmentRuleDataControl> oldAssessRules;

    protected String ruleId;
    
    protected DataControl newDataControl;

    protected int type;

    public AddRuleTool( DataControl dataControl, int type ) {

        this.dataControl = dataControl;
        this.type = type;
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
        if( dataControl.canAddElement( type ) ) {
            String defaultId = dataControl.getDefaultId( type );
            int count = 0;
            if( type == Controller.ADAPTATION_RULE ){
                oldAdapRules = new ArrayList<AdaptationRuleDataControl>( ( (AdaptationProfileDataControl) dataControl ).getDataControls( ) );
                ruleId = ( (AdaptationProfileDataControl) dataControl ).getName( ) + "." + defaultId;
                while( !Controller.getInstance( ).isElementIdValid( ruleId, false ) ) {
                    count++;
                    ruleId = ( (AdaptationProfileDataControl) dataControl ).getName( ) + "." + defaultId + count;
                }
                ruleId = ruleId.substring( ((AdaptationProfileDataControl) dataControl ).getName( ).length( ) + 1);
            }else
            if( type == Controller.ASSESSMENT_RULE || type == Controller.TIMED_ASSESSMENT_RULE ){
                oldAssessRules = new ArrayList<AssessmentRuleDataControl>( ( (AssessmentProfileDataControl) dataControl ).getDataControls( ) );
                ruleId = ( (AssessmentProfileDataControl) dataControl ).getName( ) + "." + defaultId;
                while( !Controller.getInstance( ).isElementIdValid( ruleId, false ) ) {
                    count++;
                    ruleId = ( (AssessmentProfileDataControl) dataControl ).getName( ) + "." + defaultId + count;
                }
                ruleId = ruleId.substring( ((AssessmentProfileDataControl) dataControl ).getName( ).length( ) + 1);
            }
            
            boolean ret=dataControl.addElement( type, ruleId );
            
            if( type == Controller.ADAPTATION_RULE ){
                newDataControl= ((AdaptationProfileDataControl)dataControl).getLastDatacontrol();
            }else if( type == Controller.ASSESSMENT_RULE || type == Controller.TIMED_ASSESSMENT_RULE ){
                newDataControl= ((AssessmentProfileDataControl)dataControl).getLastDatacontrol();
            }
            
            return ret;

        }
        return false;
    }

    @Override
    public boolean redoTool( ) {

        //Check there is at least one flag
        if( dataControl.canAddElement( type ) ) {
            String defaultId = dataControl.getDefaultId( type );
            String id = defaultId;
            int count = 0;
            while( !Controller.getInstance( ).isElementIdValid( id, false ) ) {
                count++;
                id = defaultId + count;
            }
            boolean ret=false;
            if( type == Controller.ADAPTATION_RULE )
                ret =((AdaptationProfileDataControl)dataControl).addElement( type, id ,(AdaptationRule)newDataControl.getContent( )) ;
            if( type == Controller.ASSESSMENT_RULE || type == Controller.TIMED_ASSESSMENT_RULE )
                ret =((AssessmentProfileDataControl)dataControl).addElement( type, id ,(AssessmentRule)newDataControl.getContent( )) ;    

            if( ret ) {
                Controller.getInstance( ).updatePanel( );
                return true;
            }
            else {
                return false;
            }

        }
        return false;
    }

    @Override
    public boolean undoTool( ) {

        if( type == Controller.ADAPTATION_RULE ) {
            ( (AdaptationProfileDataControl) dataControl ).setDataControlsAndData( new ArrayList<AdaptationRuleDataControl>( oldAdapRules ) );
            Controller.getInstance( ).getIdentifierSummary( ).deleteAdaptationRuleId( ruleId, ( (AdaptationProfileDataControl) dataControl ).getName() );
            Controller.getInstance( ).updatePanel( );
            return true;
        }
        else if( type == Controller.ASSESSMENT_RULE || type == Controller.TIMED_ASSESSMENT_RULE ) {
            ( (AssessmentProfileDataControl) dataControl ).setDataControlsAndData( new ArrayList<AssessmentRuleDataControl>( oldAssessRules ) );
            Controller.getInstance( ).getIdentifierSummary( ).deleteAssessmentRuleId( ruleId, ( (AssessmentProfileDataControl) dataControl ).getName() );
            Controller.getInstance( ).updatePanel( );
            return true;
        }
        else
            return false;

    }

}
