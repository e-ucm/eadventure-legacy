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
	
	public AddParagraphElementTool(BookDataControl dataControl, int type,
			int selectedRow) {
		this.dataControl = dataControl;
		this.type = type;
		this.selectedRow = selectedRow;
	}

	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
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
			if (selectedRow != -1 && selectedRow < dataControl.getBookParagraphsList().getBookParagraphs().size()) {
				dataControl.getBookParagraphsList().getBookParagraphsList().add(selectedRow + 1,  newBookParagraph );
				dataControl.getBookParagraphsList().getBookParagraphs().add(selectedRow + 1, newBookParagraphDataControl );
			} else {
				dataControl.getBookParagraphsList().getBookParagraphsList().add( newBookParagraph );
				dataControl.getBookParagraphsList().getBookParagraphs().add( newBookParagraphDataControl );
			}
		}

		return newBookParagraph != null;
	}

	@Override
	public boolean redoTool() {
		if( newBookParagraph != null ) {
			if (selectedRow != -1 && selectedRow < dataControl.getBookParagraphsList().getBookParagraphs().size()) {
				dataControl.getBookParagraphsList().getBookParagraphsList().add(selectedRow + 1,  newBookParagraph );
				dataControl.getBookParagraphsList().getBookParagraphs().add(selectedRow + 1, newBookParagraphDataControl );
			} else {
				dataControl.getBookParagraphsList().getBookParagraphsList().add( newBookParagraph );
				dataControl.getBookParagraphsList().getBookParagraphs().add( newBookParagraphDataControl );
			}
		}
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		dataControl.getBookParagraphsList().getBookParagraphsList().remove( newBookParagraph );
		dataControl.getBookParagraphsList().getBookParagraphs().remove( newBookParagraphDataControl );
		Controller.getInstance().updatePanel();
		return true;
	}

}
