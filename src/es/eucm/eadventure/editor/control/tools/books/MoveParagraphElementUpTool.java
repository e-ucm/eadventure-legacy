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
package es.eucm.eadventure.editor.control.tools.books;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.book.BookParagraph;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookParagraphDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class MoveParagraphElementUpTool extends Tool {

    private List<BookParagraph> bookParagraphsList;

    private List<BookParagraphDataControl> bookParagraphsDataControlList;

    private DataControl elementDataControl;

    private int oldElementIndex;

    private int newElementIndex;

    public MoveParagraphElementUpTool( BookDataControl dataControl, BookParagraphDataControl paragraph ) {

        this.bookParagraphsList = dataControl.getBookParagraphsList( ).getBookParagraphsList( );
        this.bookParagraphsDataControlList = dataControl.getBookParagraphsList( ).getBookParagraphs( );
        this.elementDataControl = paragraph;
        this.oldElementIndex = bookParagraphsList.indexOf( elementDataControl.getContent( ) );
        this.newElementIndex = oldElementIndex - 1;
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

        if( other instanceof MoveParagraphElementUpTool ) {
            MoveParagraphElementUpTool tool = (MoveParagraphElementUpTool) other;
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

        if( oldElementIndex > 0 ) {
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
