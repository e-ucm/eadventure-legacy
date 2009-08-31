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
package es.eucm.eadventure.editor.control.tools.general.conditions;

import es.eucm.eadventure.common.data.chapter.conditions.Condition;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Edition tool for duplicating a condition
 * @author Javier Torrente
 *
 */
public class DuplicateConditionTool extends Tool{

	private Conditions conditions;
	private int index1;
	private int index2;
	
	private Condition duplicate;
	
	public DuplicateConditionTool (Conditions conditions, int index1, int index2){
		this.conditions = conditions;
		this.index1 = index1;
		this.index2 = index2;
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
		try {
			if (duplicate==null)
				duplicate = (Condition)(conditions.get(index1).get(index2).clone());
			conditions.get(index1).add(index2+1, duplicate);
			Controller.getInstance().updateVarFlagSummary();
			Controller.getInstance().updatePanel();
			return true;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean redoTool() {
		return doTool();
	}

	@Override
	public boolean undoTool() {
		conditions.get(index1).remove(index2+1);
		Controller.getInstance().updateVarFlagSummary();
		Controller.getInstance().updatePanel();
		return true;
	}
}
