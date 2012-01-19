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
package es.eucm.eadventure.editor.control.tools.books;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.book.BookParagraph;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookParagraphDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeleteParagraphElementTool extends Tool {

    private List<BookParagraph> bookParagraphsList;

    private List<BookParagraphDataControl> bookParagraphsDataControlList;

    private DataControl elementDataControl;

    private int listIndex;

    private int dataControlListIndex;

    public DeleteParagraphElementTool( BookDataControl dataControl, BookParagraphDataControl paragraph ) {

        this.bookParagraphsList = dataControl.getBookParagraphsList( ).getBookParagraphsList( );
        this.bookParagraphsDataControlList = dataControl.getBookParagraphsList( ).getBookParagraphs( );
        this.elementDataControl = paragraph;
        this.listIndex = bookParagraphsList.indexOf( elementDataControl.getContent( ) );
        this.dataControlListIndex = bookParagraphsDataControlList.indexOf( elementDataControl );
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

        boolean temp = bookParagraphsList.remove( elementDataControl.getContent( ) );
        if( temp ) {
            bookParagraphsDataControlList.remove( elementDataControl );
            return true;
        }
        return false;
    }

    @Override
    public boolean redoTool( ) {

        boolean temp = bookParagraphsList.remove( elementDataControl.getContent( ) );
        if( temp ) {
            bookParagraphsDataControlList.remove( elementDataControl );
            Controller.getInstance( ).updatePanel( );
            return true;
        }
        return false;
    }

    @Override
    public boolean undoTool( ) {

        bookParagraphsList.add( listIndex, (BookParagraph) elementDataControl.getContent( ) );
        bookParagraphsDataControlList.add( dataControlListIndex, (BookParagraphDataControl) elementDataControl );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

}
