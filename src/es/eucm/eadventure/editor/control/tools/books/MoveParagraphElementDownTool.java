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
package es.eucm.eadventure.editor.control.tools.books;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.book.BookParagraph;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookParagraphDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class MoveParagraphElementDownTool extends Tool {

    private List<BookParagraph> bookParagraphsList;

    private List<BookParagraphDataControl> bookParagraphsDataControlList;

    private DataControl elementDataControl;

    private int oldElementIndex;

    private int newElementIndex;

    public MoveParagraphElementDownTool( BookDataControl dataControl, BookParagraphDataControl paragraph ) {

        this.bookParagraphsList = dataControl.getBookParagraphsList( ).getBookParagraphsList( );
        this.bookParagraphsDataControlList = dataControl.getBookParagraphsList( ).getBookParagraphs( );
        this.elementDataControl = paragraph;
        this.oldElementIndex = bookParagraphsDataControlList.indexOf( elementDataControl );
        this.newElementIndex = oldElementIndex + 1;
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

        if( other instanceof MoveParagraphElementDownTool ) {
            MoveParagraphElementDownTool tool = (MoveParagraphElementDownTool) other;
            if( tool.elementDataControl == elementDataControl ) {
                newElementIndex = tool.newElementIndex;
                timeStamp = tool.timeStamp;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean doTool( ) {

        if( oldElementIndex >= 0 && oldElementIndex < bookParagraphsList.size( ) - 1 ) {
            bookParagraphsList.add( newElementIndex, bookParagraphsList.remove( oldElementIndex ) );
            bookParagraphsDataControlList.add( newElementIndex, bookParagraphsDataControlList.remove( oldElementIndex ) );
            return true;
        }
        return false;
    }

    @Override
    public boolean redoTool( ) {

        bookParagraphsList.add( newElementIndex, bookParagraphsList.remove( oldElementIndex ) );
        bookParagraphsDataControlList.add( newElementIndex, bookParagraphsDataControlList.remove( oldElementIndex ) );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        bookParagraphsList.add( oldElementIndex, bookParagraphsList.remove( newElementIndex ) );
        bookParagraphsDataControlList.add( oldElementIndex, bookParagraphsDataControlList.remove( newElementIndex ) );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

}
