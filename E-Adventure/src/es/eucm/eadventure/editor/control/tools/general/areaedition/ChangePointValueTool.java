/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.tools.general.areaedition;

import java.awt.Point;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangePointValueTool extends Tool {

    private Point point;

    private int x;

    private int y;

    private int originalX;

    private int originalY;

    public ChangePointValueTool( Point point, int x, int y ) {

        this.point = point;
        this.x = x;
        this.y = y;
        this.originalX = (int) point.getX( );
        this.originalY = (int) point.getY( );
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

        if( other instanceof ChangePointValueTool ) {
            ChangePointValueTool cpvt = (ChangePointValueTool) other;
            if( cpvt.point == point ) {
                cpvt.x = x;
                cpvt.y = y;
                cpvt.timeStamp = timeStamp;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean doTool( ) {

        point.setLocation( x, y );
        return true;
    }

    @Override
    public boolean redoTool( ) {

        point.setLocation( x, y );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        point.setLocation( originalX, originalY );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

}
