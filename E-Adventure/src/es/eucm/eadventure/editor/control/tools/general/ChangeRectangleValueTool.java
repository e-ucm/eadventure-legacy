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
package es.eucm.eadventure.editor.control.tools.general;

import es.eucm.eadventure.common.data.chapter.Rectangle;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeRectangleValueTool extends Tool {

    private Rectangle rectangle;

    private int x, y, width, height;

    private int oldX, oldY, oldWidth, oldHeight;

    public ChangeRectangleValueTool( Rectangle rectangle, int x, int y, int width, int height ) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rectangle = rectangle;

        this.oldX = rectangle.getX( );
        this.oldY = rectangle.getY( );
        this.oldWidth = rectangle.getWidth( );
        this.oldHeight = rectangle.getHeight( );
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

        if( other instanceof ChangeRectangleValueTool ) {
            ChangeRectangleValueTool crvt = (ChangeRectangleValueTool) other;
            if( crvt.rectangle != rectangle )
                return false;
            if( crvt.isChangePos( ) && isChangePos( ) ) {
                x = crvt.x;
                y = crvt.y;
                timeStamp = crvt.timeStamp;
                return true;
            }
            if( crvt.isChangeSize( ) && isChangeSize( ) ) {
                width = crvt.width;
                height = crvt.height;
                timeStamp = crvt.timeStamp;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean doTool( ) {

        rectangle.setValues( x, y, width, height );
        return true;
    }

    @Override
    public boolean redoTool( ) {

        rectangle.setValues( x, y, width, height );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        rectangle.setValues( oldX, oldY, oldWidth, oldHeight );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    private boolean isChangeSize( ) {

        if( x == oldX && y == oldY )
            return true;
        return false;
    }

    private boolean isChangePos( ) {

        if( width == oldWidth && height == oldHeight )
            return true;
        return false;
    }

}
