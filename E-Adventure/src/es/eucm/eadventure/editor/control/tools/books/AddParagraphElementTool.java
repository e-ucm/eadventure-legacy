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
package es.eucm.eadventure.editor.control.tools.books;

import es.eucm.eadventure.common.data.chapter.book.BookParagraph;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookParagraphDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class AddParagraphElementTool extends Tool {

    private BookDataControl dataControl;

    private int type;

    private int selectedRow;

    private BookParagraph newBookParagraph;

    private BookParagraphDataControl newBookParagraphDataControl;

    public AddParagraphElementTool( BookDataControl dataControl, int type, int selectedRow ) {

        this.dataControl = dataControl;
        this.type = type;
        this.selectedRow = selectedRow;
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

        newBookParagraph = null;
        if( type == Controller.BOOK_TITLE_PARAGRAPH )
            newBookParagraph = new BookParagraph( BookParagraph.TITLE );

        else if( type == Controller.BOOK_TEXT_PARAGRAPH )
            newBookParagraph = new BookParagraph( BookParagraph.TEXT );

        else if( type == Controller.BOOK_BULLET_PARAGRAPH )
            newBookParagraph = new BookParagraph( BookParagraph.BULLET );

        else if( type == Controller.BOOK_IMAGE_PARAGRAPH )
            newBookParagraph = new BookParagraph( BookParagraph.IMAGE );

        newBookParagraphDataControl = new BookParagraphDataControl( newBookParagraph );
        if( newBookParagraph != null ) {
            if( selectedRow != -1 && selectedRow < dataControl.getBookParagraphsList( ).getBookParagraphs( ).size( ) ) {
                dataControl.getBookParagraphsList( ).getBookParagraphsList( ).add( selectedRow + 1, newBookParagraph );
                dataControl.getBookParagraphsList( ).getBookParagraphs( ).add( selectedRow + 1, newBookParagraphDataControl );
            }
            else {
                dataControl.getBookParagraphsList( ).getBookParagraphsList( ).add( newBookParagraph );
                dataControl.getBookParagraphsList( ).getBookParagraphs( ).add( newBookParagraphDataControl );
            }
        }

        return newBookParagraph != null;
    }

    @Override
    public boolean redoTool( ) {

        if( newBookParagraph != null ) {
            if( selectedRow != -1 && selectedRow < dataControl.getBookParagraphsList( ).getBookParagraphs( ).size( ) ) {
                dataControl.getBookParagraphsList( ).getBookParagraphsList( ).add( selectedRow + 1, newBookParagraph );
                dataControl.getBookParagraphsList( ).getBookParagraphs( ).add( selectedRow + 1, newBookParagraphDataControl );
            }
            else {
                dataControl.getBookParagraphsList( ).getBookParagraphsList( ).add( newBookParagraph );
                dataControl.getBookParagraphsList( ).getBookParagraphs( ).add( newBookParagraphDataControl );
            }
        }
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        dataControl.getBookParagraphsList( ).getBookParagraphsList( ).remove( newBookParagraph );
        dataControl.getBookParagraphsList( ).getBookParagraphs( ).remove( newBookParagraphDataControl );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

}
