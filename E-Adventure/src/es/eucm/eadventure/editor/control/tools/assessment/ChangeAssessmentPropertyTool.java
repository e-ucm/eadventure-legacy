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
            }
   
    }

   

    @Override
    public boolean canRedo( ) {

        return true;
    }

    @Override
    public boolean canUndo( ) {

        return mode == SET_ID || mode == SET_VALUE ;
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
        }
        return false;
    }

    /**
     * Constructors. Will change the
     * 
     * @param oldRule
     */
}