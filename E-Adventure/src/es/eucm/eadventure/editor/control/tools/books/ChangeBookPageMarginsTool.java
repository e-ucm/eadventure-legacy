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
	
	public ChangeBookPageMarginsTool(BookPage bookPage, int newMargin,
			int newMarginTop, int newMarginBottom, int newMarginEnd) {
		this.bookPage = bookPage;
		this.newMargin = newMargin;
		this.newMarginTop = newMarginTop;
		this.newMarginBottom = newMarginBottom;
		this.newMarginEnd = newMarginEnd;
		this.oldMargin = bookPage.getMargin();
		this.oldMarginTop = bookPage.getMarginTop();
		this.oldMarginBottom = bookPage.getMarginBottom();
		this.oldMarginEnd = bookPage.getMarginEnd();
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
		if (other instanceof ChangeBookPageMarginsTool) {
			ChangeBookPageMarginsTool cbpmt = (ChangeBookPageMarginsTool) other;
			if (cbpmt.bookPage == bookPage) {
				if (oldMargin != newMargin && cbpmt.oldMargin != cbpmt.newMargin) {
					newMargin = cbpmt.newMargin;
					timeStamp = cbpmt.timeStamp;
					return true;
				}
				if (oldMarginBottom != newMarginBottom && cbpmt.oldMarginBottom != cbpmt.newMarginBottom) {
					oldMarginBottom = cbpmt.oldMarginBottom;
					timeStamp = cbpmt.timeStamp;
					return true;
				}
				if (oldMarginTop != newMarginTop && cbpmt.oldMarginTop != cbpmt.newMarginTop) {
					newMarginTop = cbpmt.newMarginTop;
					timeStamp = cbpmt.timeStamp;
					return true;
				}
				if (oldMarginEnd != newMarginEnd && cbpmt.oldMarginEnd != cbpmt.newMarginEnd) {
					newMarginEnd = cbpmt.newMarginEnd;
					timeStamp = cbpmt.timeStamp;
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean doTool() {
		if (oldMargin != newMargin) {
			bookPage.setMargin(newMargin);
			return true;
		}
		if (oldMarginTop != newMarginTop) {
			bookPage.setMarginTop(newMarginTop);
			return true;
		}
		if (oldMarginBottom != newMarginBottom) {
			bookPage.setMarginBottom(newMarginBottom);
			return true;
		}
		if (oldMarginEnd != newMarginEnd) {
			bookPage.setMarginEnd(newMarginEnd);
			return true;
		}
		return false;
	}

	@Override
	public boolean redoTool() {
		bookPage.setMargin(newMargin);
		bookPage.setMarginBottom(newMarginBottom);
		bookPage.setMarginEnd(newMarginEnd);
		bookPage.setMarginTop(newMarginTop);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		bookPage.setMargin(oldMargin);
		bookPage.setMarginBottom(oldMarginBottom);
		bookPage.setMarginEnd(oldMarginEnd);
		bookPage.setMarginTop(oldMarginTop);
		Controller.getInstance().updatePanel();
		return true;
	}

}
