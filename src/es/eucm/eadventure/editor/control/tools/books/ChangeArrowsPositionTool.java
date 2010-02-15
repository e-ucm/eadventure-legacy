/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.editor.control.tools.books;

import java.awt.Point;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;


/**
 * Tool for redo and undo of changing arrows position
 * @author Ángel S.
 *
 */
public class ChangeArrowsPositionTool extends Tool{
    
    private BookDataControl dataControl;
    private Point newNextPoint;
    private Point newPreviousPoint;
    private Point oldPreviousPoint;
    private Point oldNextPoint;

    public ChangeArrowsPositionTool( BookDataControl dataControl, Point oldNextPoint, Point newNextPoint, Point oldPreviousPoint, Point newPreviousPoint ){
        this.dataControl = dataControl;
        this.oldNextPoint = oldNextPoint;
        this.newNextPoint = newNextPoint;
        this.oldPreviousPoint = oldPreviousPoint;
        this.newPreviousPoint = newPreviousPoint;
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
        dataControl.setPreviousPagePosition( newPreviousPoint );
        dataControl.setNextPagePosition( newNextPoint );
        return true;
    }

    @Override
    public boolean redoTool( ) {
        dataControl.setPreviousPagePosition( newPreviousPoint );
        dataControl.setNextPagePosition( newNextPoint );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {
        dataControl.setPreviousPagePosition( oldPreviousPoint );
        dataControl.setNextPagePosition( oldNextPoint );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

}
