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

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ElementContainer;
import es.eucm.eadventure.editor.control.controllers.scene.ReferencesListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.elementpanels.scene.ElementReferencesTable;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class AddReferenceTool extends Tool {

	private ReferencesListDataControl referencesListDataControl;
	
	private int type;
	
	private ScenePreviewEditionPanel spep;
	
	private ElementReferencesTable table;

	private ElementContainer newElement;
	
	public AddReferenceTool(
			ReferencesListDataControl referencesListDataControl, int type,
			ScenePreviewEditionPanel spep, ElementReferencesTable table) {
		this.referencesListDataControl = referencesListDataControl;
		this.type = type;
		this.spep = spep;
		this.table = table;
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
		int category;
		if (referencesListDataControl.addElement( type, null )){
			category = ReferencesListDataControl.transformType(type);
			if (category != 0 && referencesListDataControl.getLastElementContainer()!=null) {
				// it is not necessary to check if it is an player element container because never a player will be added
				newElement = referencesListDataControl.getLastElementContainer();
				spep.addElement(category, newElement.getErdc());
				spep.setSelectedElement(newElement.getErdc());
				spep.repaint();
				int layer = newElement.getErdc().getElementReference().getLayer();
				table.getSelectionModel().setSelectionInterval(layer, layer);
				table.updateUI( );
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean redoTool() {
		referencesListDataControl.addElement(newElement);
		int category = ReferencesListDataControl.transformType(type);
		spep.addElement(category, newElement.getErdc());
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		referencesListDataControl.deleteElement(newElement.getErdc(), false);
		int category = ReferencesListDataControl.transformType(type);
		spep.removeElement(category, newElement.getErdc());
		Controller.getInstance().updatePanel();
		return true;
	}
}
