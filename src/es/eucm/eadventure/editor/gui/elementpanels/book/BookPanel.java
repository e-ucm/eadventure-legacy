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
package es.eucm.eadventure.editor.gui.elementpanels.book;

import javax.swing.JComponent;

import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;

public class BookPanel extends ElementPanel {
	
	private static final long serialVersionUID = 1L;
	
	public BookPanel (BookDataControl dataControl){
		addTab(new BookContentPanel(dataControl));
		addTab(new BookAppPanelTab(dataControl));
		addTab(new BookDocPanelTab(dataControl));
	}
	
	private class BookDocPanelTab extends PanelTab {
		private BookDataControl dataControl;
		
		public BookDocPanelTab(BookDataControl dataControl) {
			super(TextConstants.getText("Book.Doc"), dataControl);
			setToolTipText(TextConstants.getText("Book.Doc.Tip"));
			this.dataControl = dataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new BookDocPanel(dataControl);
		}
	}

	private class BookAppPanelTab extends PanelTab {
		private BookDataControl dataControl;
		
		public BookAppPanelTab(BookDataControl dataControl) {
			super(TextConstants.getText("Book.App"), dataControl);
			setToolTipText(TextConstants.getText("Book.App.Tip"));
			this.dataControl = dataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new BookAppPanel(dataControl);
		}
	}

	private class BookContentPanel extends PanelTab {
		private BookDataControl dataControl;
		
		public BookContentPanel(BookDataControl dataControl) {
			super(TextConstants.getText("Book.Contents"), dataControl);
			setToolTipText(TextConstants.getText("Book.Contents.Tip"));
			this.dataControl = dataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			if (dataControl.getType( ) == Book.TYPE_PARAGRAPHS) {
				return new BookParagraphsPanel(dataControl);
			} else {
				return new BookPagesPanel(dataControl);
			}
		}
		
		@Override
		public DataControl getDataControl() {
			if (dataControl.getType( ) == Book.TYPE_PARAGRAPHS) {
				return dataControl.getBookParagraphsList();
			}
			return super.getDataControl();
		}
	}	
}
