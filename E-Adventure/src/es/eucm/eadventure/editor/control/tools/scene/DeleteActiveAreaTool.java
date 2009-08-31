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
package es.eucm.eadventure.editor.control.tools.scene;

import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.general.ChapterDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreaDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreasListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.elementpanels.scene.ActiveAreasTable;
import es.eucm.eadventure.editor.gui.otherpanels.IrregularAreaEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElement;

public class DeleteActiveAreaTool extends Tool {

	private ActiveAreasListDataControl dataControl;
	
	private IrregularAreaEditionPanel iaep;
	
	private ActiveAreasTable table;

	private ActiveAreaDataControl element;
	
	private int position;

	private ChapterDataControl chapterDataControl;
	
	private Chapter chapter;

	
	public DeleteActiveAreaTool(ActiveAreasListDataControl dataControl2,
			IrregularAreaEditionPanel iaep, ActiveAreasTable table2) {
		this.dataControl = dataControl2;
		this.table = table2;
		this.iaep = iaep;
		
		chapterDataControl = Controller.getInstance().getSelectedChapterDataControl();
		try {
			chapter = (Chapter) (((Chapter) chapterDataControl.getContent()).clone());
		} catch (Exception e) {
			ReportDialog.GenerateErrorReport(e, true, "Could not clone chapter");	
		}

	}

	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return (chapter != null);
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
		position = table.getSelectedRow();
		element = dataControl.getActiveAreas().get(position);
		if (dataControl.deleteElement(element, true)) {
			iaep.getScenePreviewEditionPanel().removeElement(element);
			iaep.getScenePreviewEditionPanel().setSelectedElement((ImageElement) null);
			((AbstractTableModel) table.getModel()).fireTableDataChanged();
			return true;
		}
		return false;
		
		
	}

	@Override
	public boolean redoTool() {
		Controller.getInstance().replaceSelectedChapter((Chapter)chapterDataControl.getContent());
		Controller.getInstance().reloadData();
		return true;
	}

	@Override
	public boolean undoTool() {
		Controller.getInstance().replaceSelectedChapter(chapter);
		Controller.getInstance().reloadData();
		return true;
	}
}
