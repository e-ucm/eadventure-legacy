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
package es.eucm.eadventure.editor.control.tools.general;

import es.eucm.eadventure.common.data.chapter.ElementReference;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeElementReferenceTool extends Tool {

	private ElementReference elementReference;
	
	private int x, y;
	
	private int oldX, oldY;
	
	private boolean changePosition;
	
	private boolean changeScale;
	
	private float scale, oldScale;
	
	public ChangeElementReferenceTool(ElementReference elementReference, int x, int y) {
		this.elementReference = elementReference;
		this.x = x;
		this.y = y;
		this.oldX = elementReference.getX();
		this.oldY = elementReference.getY();
		changePosition = true;
		changeScale = false;
	}
	
	public ChangeElementReferenceTool(ElementReference elementReference2,
			float scale2) {
		this.elementReference = elementReference2;
		this.scale = scale2;
		this.oldScale = elementReference.getScale();
		changePosition = false;
		changeScale = true;
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
		if (other instanceof ChangeElementReferenceTool) {
			ChangeElementReferenceTool crvt = (ChangeElementReferenceTool) other;
			if (crvt.elementReference != elementReference)
				return false;
			if (crvt.changePosition && changePosition) {
				x = crvt.x;
				y = crvt.y;
				timeStamp = crvt.timeStamp;
				return true;
			}
			if (crvt.changeScale && changeScale) {
				scale = crvt.scale;
				timeStamp = crvt.timeStamp;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean doTool() {
		if (changeScale) {
			elementReference.setScale(scale);
		} else if (changePosition){
			elementReference.setPosition(x, y);
		}
		return true;
	}

	@Override
	public String getToolName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean redoTool() {
		if (changeScale) {
			elementReference.setScale(scale);
		} else if (changePosition){
			elementReference.setPosition(x, y);
		}
		Controller.getInstance().reloadPanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		if (changeScale) {
			elementReference.setScale(oldScale);
		} else if (changePosition){
			elementReference.setPosition(oldX, oldY);
		}
		Controller.getInstance().reloadPanel();
		return true;
	}

}
