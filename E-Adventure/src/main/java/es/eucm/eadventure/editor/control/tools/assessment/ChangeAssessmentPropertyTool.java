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
package es.eucm.eadventure.editor.control.tools.assessment;

import java.util.List;

import es.eucm.eadventure.common.data.assessment.AssessmentProperty;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Tool for changing an adaptation rule (whatever the adaptation rule is)
 * 
 * @author Javier
 * 
 */
public class ChangeAssessmentPropertyTool extends Tool {

    public static final int SET_ID = 2;

    public static final int SET_VALUE = 3;
    
    public static final int SET_VARNAME = 4;

    protected String oldValue;

    protected String newValue;

    protected List<AssessmentProperty> list;

    protected int mode;

    protected int index;

    public ChangeAssessmentPropertyTool( List<AssessmentProperty> parent, String newData, int index, int mode ) {
            
            this.mode = mode;
            this.list = parent;
            this.index = index;
            newValue = newData;
            
            if (mode == SET_ID){
                oldValue = list.get( index ).getId( );
            }else if (mode == SET_VALUE){
                oldValue = list.get( index ).getValue( );
            } else if (mode == SET_VARNAME){
                oldValue = list.get( index ).getVarName( );
            }
   
    }

   

    @Override
    public boolean canRedo( ) {

        return true;
    }

    @Override
    public boolean canUndo( ) {

        return mode == SET_ID || mode == SET_VALUE || mode == SET_VARNAME ;
    }

    @Override
    public boolean combine( Tool other ) {

        return false;
    }

    @Override
    public boolean doTool( ) {

        if (mode == SET_ID){
            list.get( index ).setId( newValue );
            return true;
        }else if (mode == SET_VALUE){
            list.get( index ).setValue( newValue );
            return true;
        }else if (mode == SET_VARNAME){
            list.get( index ).setVarName( newValue );
            return true;
        }
        return false;
    }

    @Override
    public boolean redoTool( ) {
       boolean ret = doTool();
        Controller.getInstance( ).updatePanel( );
        return ret;
    }

    @Override
    public boolean undoTool( ) {

        if (mode == SET_ID){
            list.get( index ).setId( oldValue );
            Controller.getInstance( ).updatePanel( );
            return true;
        }else if (mode == SET_VALUE){
            list.get( index ).setValue( oldValue );
            Controller.getInstance( ).updatePanel( );
            return true;
        }else if (mode == SET_VARNAME){
            list.get( index ).setVarName( oldValue );
            Controller.getInstance( ).updatePanel( );
            return true;
        }
        return false;
    }

}
