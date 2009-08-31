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

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.data.chapter.elements.Barrier;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.BarrierDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.BarriersListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class DeleteBarrierTool extends Tool {

	private BarriersListDataControl dataControl;
	
	private ScenePreviewEditionPanel spep;
	
	private JTable table;

	private BarrierDataControl element;
	
	private int position;
	
	public DeleteBarrierTool(BarriersListDataControl dataControl,
			JTable table2, ScenePreviewEditionPanel spep2) {
		this.dataControl = dataControl;
		this.table = table2;
		this.spep = spep2;
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
		int position = table.getSelectedRow();
		element = dataControl.getBarriers().get(position);
		spep.removeElement(element);
		dataControl.deleteElement(element, true);
		((AbstractTableModel) table.getModel()).fireTableDataChanged();
		return true;
	}

	@Override
	public boolean redoTool() {
		spep.removeElement(element);
		dataControl.deleteElement(element, true);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		dataControl.getBarriersList().add(position, (Barrier) element.getContent());
		dataControl.getBarriers().add(position, element);
		spep.addBarrier(element);
		Controller.getInstance().updatePanel();
		return true;
	}
}
