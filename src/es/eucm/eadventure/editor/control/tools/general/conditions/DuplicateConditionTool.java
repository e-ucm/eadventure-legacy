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
package es.eucm.eadventure.editor.control.tools.general.conditions;

import es.eucm.eadventure.common.data.chapter.conditions.Condition;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Edition tool for duplicating a condition
 * 
 * @author Javier Torrente
 * 
 */
public class DuplicateConditionTool extends Tool {

    private Conditions conditions;

    private int index1;

    private int index2;

    private Condition duplicate;

    public DuplicateConditionTool( Conditions conditions, int index1, int index2 ) {

        this.conditions = conditions;
        this.index1 = index1;
        this.index2 = index2;
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
            if( duplicate == null )
                duplicate = (Condition) ( conditions.get( index1 ).get( index2 ).clone( ) );
            conditions.get( index1 ).add( index2 + 1, duplicate );
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

        return doTool( );
    }

    @Override
    public boolean undoTool( ) {

        conditions.get( index1 ).remove( index2 + 1 );
        Controller.getInstance( ).updateVarFlagSummary( );
        Controller.getInstance( ).updatePanel( );
        return true;
    }
}
