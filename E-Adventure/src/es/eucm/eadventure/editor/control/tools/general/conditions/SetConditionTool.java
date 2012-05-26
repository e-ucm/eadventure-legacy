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
package es.eucm.eadventure.editor.control.tools.general.conditions;

import java.util.HashMap;

import es.eucm.eadventure.common.data.chapter.conditions.Condition;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.conditions.FlagCondition;
import es.eucm.eadventure.common.data.chapter.conditions.GlobalStateCondition;
import es.eucm.eadventure.common.data.chapter.conditions.VarCondition;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Edition tool for duplicating a condition
 * 
 * @author Javier Torrente
 * 
 */
public class SetConditionTool extends Tool {

    private Conditions conditions;

    private int index1;

    private int index2;

    private HashMap<String, String> properties;

    private Condition oldCondition;

    private Condition newCondition;

    public SetConditionTool( Conditions conditions, int index1, int index2, HashMap<String, String> properties ) {

        this.conditions = conditions;
        this.index1 = index1;
        this.index2 = index2;
        this.properties = properties;
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

        try {
            Condition condition = conditions.get( index1 ).get( index2 );
            oldCondition = (Condition) condition.clone( );
            newCondition = null;
            String newId = properties.get( ConditionsController.CONDITION_ID );
            String newType = properties.get( ConditionsController.CONDITION_TYPE );
            String newState = properties.get( ConditionsController.CONDITION_STATE );
            String newValue = properties.get( ConditionsController.CONDITION_VALUE );
            int newTypeInt = ConditionsController.getTypeFromString( newType );

            if( newTypeInt != condition.getType( ) ) {
                if( newTypeInt == Condition.FLAG_CONDITION ) {
                    newCondition = new FlagCondition( newId, ConditionsController.getStateFromString( newState ) );
                }
                else if( newTypeInt == Condition.VAR_CONDITION ) {
                    newCondition = new VarCondition( newId, ConditionsController.getStateFromString( newState ), Integer.parseInt( newValue ) );
                }
                else if( newTypeInt == Condition.GLOBAL_STATE_CONDITION ) {
                    newCondition = new GlobalStateCondition( newId, ConditionsController.getStateFromString( newState ) );
                }
            }
            else {
                newCondition = condition;
                if( !newId.equals( newCondition.getId( ) ) ) {
                    newCondition.setId( newId );
                }
                if( !new Integer( ConditionsController.getStateFromString( newState ) ).equals( newCondition.getState( ) ) ) {
                    newCondition.setState( new Integer( ConditionsController.getStateFromString( newState ) ) );
                }
                if( newCondition.getType( ) == Condition.VAR_CONDITION ) {
                    VarCondition varCondition = (VarCondition) condition;
                    if( !varCondition.getValue( ).equals( Integer.parseInt( newValue ) ) ) {
                        varCondition.setValue( Integer.parseInt( newValue ) );
                    }
                }
            }

            conditions.get( index1 ).remove( index2 );
            conditions.get( index1 ).add( index2, newCondition );
            Controller.getInstance( ).updateVarFlagSummary( );
            Controller.getInstance( ).updatePanel( );
            return true;

        }
        catch( CloneNotSupportedException e ) {
            e.printStackTrace( );
            return false;
        }
    }

    @Override
    public boolean redoTool( ) {

        conditions.get( index1 ).remove( index2 );
        conditions.get( index1 ).add( index2, newCondition );
        Controller.getInstance( ).updateVarFlagSummary( );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        conditions.get( index1 ).remove( index2 );
        conditions.get( index1 ).add( index2, oldCondition );
        Controller.getInstance( ).updateVarFlagSummary( );
        Controller.getInstance( ).updatePanel( );
        return true;
    }
}
