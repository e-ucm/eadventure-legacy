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

import es.eucm.eadventure.common.data.chapter.Trajectory.Node;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class SetNodeValuesTool extends Tool{

	private int oldX;
	private int oldY;
	private float oldScale;
	
	private int newX;
	private int newY;
	private float newScale;
	
	private Node node;
	
	public SetNodeValuesTool (Node node, int newX, int newY, float newScale ){
		this.newX = newX;
		this.newY = newY;
		this.newScale = newScale;
		this.oldX = node.getX();
		this.oldY = node.getY();
		this.oldScale = node.getScale();
		this.node = node;
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
		if (other instanceof SetNodeValuesTool) {
			SetNodeValuesTool crvt = (SetNodeValuesTool) other;
			if (crvt.node != node)
				return false;
			newX = crvt.newX;
			newY = crvt.newY;
			newScale = crvt.newScale;
			timeStamp = crvt.timeStamp;
			return true;
		}
		return false;
	}

	@Override
	public boolean doTool() {
		node.setValues(newX, newY, newScale);
		return true;
	}

	@Override
	public boolean redoTool() {
		node.setValues(newX, newY, newScale);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		node.setValues(oldX, oldY, oldScale);
		Controller.getInstance().updatePanel();
		return true;
	}

}
