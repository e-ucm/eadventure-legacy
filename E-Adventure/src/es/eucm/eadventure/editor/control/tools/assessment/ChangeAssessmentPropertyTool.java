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

import es.eucm.eadventure.common.data.assessment.AssessmentProperty;
import es.eucm.eadventure.common.data.assessment.AssessmentRule;
import es.eucm.eadventure.common.data.assessment.TimedAssessmentRule;
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

    protected AssessmentProperty oldProperty;

    protected AssessmentProperty newProperty;

    protected AssessmentRule parent;

    protected int mode;

    protected int index;

    public ChangeAssessmentPropertyTool( AssessmentRule parent, String newData, int index, int mode ) {

        // We need separate by Rules Timed Assessment and General Assessment 
        if (parent instanceof AssessmentRule ){
            this.mode = mode;
            
            this.oldProperty = parent.getAssessmentProperties( ).get( index ); //DA ERROR AL CAMBIAR EL NOMBRE DE UNA PROPIEDAD
            
            this.parent = parent;
            this.index = index;
    
            if( mode == SET_ID ) {
                newProperty = new AssessmentProperty( newData, oldProperty.getValue( ) );
            }
            else if( mode == SET_VALUE ) {
                newProperty = new AssessmentProperty( oldProperty.getId( ), newData );
            }
        }
    }

    public ChangeAssessmentPropertyTool(AssessmentRule parent, String newData, int effect, int index, int mode ) {
     // We need separate by Rules Timed Assessment and General Assessment 
        if (parent instanceof TimedAssessmentRule ){
                this.mode = mode;
                
                this.oldProperty = ((TimedAssessmentRule) parent).getAssessmentProperties( effect ).get( index -1 );
                
                this.parent = parent;
                this.index = index;
        
                if( mode == SET_ID ) {
                    newProperty = new AssessmentProperty( newData, oldProperty.getValue( ) );
                }
                else if( mode == SET_VALUE ) {
                    newProperty = new AssessmentProperty( oldProperty.getId( ), newData );
                }
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

        if( mode == SET_ID || mode == SET_VALUE ) {
            if( index >= 0 && index < parent.getAssessmentProperties( ).size( ) ) {
                parent.getAssessmentProperties( ).remove( index );
                parent.getAssessmentProperties( ).add( index, newProperty );
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean redoTool( ) {

        if( mode == SET_ID || mode == SET_VALUE ) {
            parent.getAssessmentProperties( ).remove( index );
            parent.getAssessmentProperties( ).add( index, newProperty );
            Controller.getInstance( ).updatePanel( );
            return true;
        }
        return false;
    }

    @Override
    public boolean undoTool( ) {

        if( mode == SET_ID || mode == SET_VALUE ) {
            parent.getAssessmentProperties( ).remove( index );
            parent.getAssessmentProperties( ).add( index, oldProperty );
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