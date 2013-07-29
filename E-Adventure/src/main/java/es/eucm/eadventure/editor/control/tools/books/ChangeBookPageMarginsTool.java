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

import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeBookPageMarginsTool extends Tool {

    private BookPage bookPage;

    private int newMargin;

    private int newMarginTop;

    private int newMarginBottom;

    private int newMarginEnd;

    private int oldMargin;

    private int oldMarginTop;

    private int oldMarginBottom;

    private int oldMarginEnd;

    public ChangeBookPageMarginsTool( BookPage bookPage, int newMargin, int newMarginTop, int newMarginBottom, int newMarginEnd ) {

        this.bookPage = bookPage;
        this.newMargin = newMargin;
        this.newMarginTop = newMarginTop;
        this.newMarginBottom = newMarginBottom;
        this.newMarginEnd = newMarginEnd;
        this.oldMargin = bookPage.getMargin( );
        this.oldMarginTop = bookPage.getMarginTop( );
        this.oldMarginBottom = bookPage.getMarginBottom( );
        this.oldMarginEnd = bookPage.getMarginEnd( );
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

        if( other instanceof ChangeBookPageMarginsTool ) {
            ChangeBookPageMarginsTool cbpmt = (ChangeBookPageMarginsTool) other;
            if( cbpmt.bookPage == bookPage ) {
                if( oldMargin != newMargin && cbpmt.oldMargin != cbpmt.newMargin ) {
                    newMargin = cbpmt.newMargin;
                    timeStamp = cbpmt.timeStamp;
                    return true;
                }
                if( oldMarginBottom != newMarginBottom && cbpmt.oldMarginBottom != cbpmt.newMarginBottom ) {
                    oldMarginBottom = cbpmt.oldMarginBottom;
                    timeStamp = cbpmt.timeStamp;
                    return true;
                }
                if( oldMarginTop != newMarginTop && cbpmt.oldMarginTop != cbpmt.newMarginTop ) {
                    newMarginTop = cbpmt.newMarginTop;
                    timeStamp = cbpmt.timeStamp;
                    return true;
                }
                if( oldMarginEnd != newMarginEnd && cbpmt.oldMarginEnd != cbpmt.newMarginEnd ) {
                    newMarginEnd = cbpmt.newMarginEnd;
                    timeStamp = cbpmt.timeStamp;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean doTool( ) {

        if( oldMargin != newMargin ) {
            bookPage.setMargin( newMargin );
            return true;
        }
        if( oldMarginTop != newMarginTop ) {
            bookPage.setMarginTop( newMarginTop );
            return true;
        }
        if( oldMarginBottom != newMarginBottom ) {
            bookPage.setMarginBottom( newMarginBottom );
            return true;
        }
        if( oldMarginEnd != newMarginEnd ) {
            bookPage.setMarginEnd( newMarginEnd );
            return true;
        }
        return false;
    }

    @Override
    public boolean redoTool( ) {

        bookPage.setMargin( newMargin );
        bookPage.setMarginBottom( newMarginBottom );
        bookPage.setMarginEnd( newMarginEnd );
        bookPage.setMarginTop( newMarginTop );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        bookPage.setMargin( oldMargin );
        bookPage.setMarginBottom( oldMarginBottom );
        bookPage.setMarginEnd( oldMarginEnd );
        bookPage.setMarginTop( oldMarginTop );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

}
