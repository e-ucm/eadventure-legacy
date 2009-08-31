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
package es.eucm.eadventure.editor.data.meta.ims;

import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.Vocabulary;

public class IMSRights {

	/*
	 * cost and copyrightandotherrestrictions only can be "yes" or "no"
	 */

	// 6.1
	private Vocabulary cost;
	
	// 6.2
	private Vocabulary copyrightandotherrestrictions;
	
	
	public IMSRights(){
		cost = new Vocabulary(Vocabulary.IMS_YES_NO);
		copyrightandotherrestrictions = new Vocabulary(Vocabulary.IMS_YES_NO);;
	}


	public Vocabulary getCost() {
		return cost;
	}


	public void setCost(int index) {
		this.cost.setValueIndex(index);
	}


	public Vocabulary getCopyrightandotherrestrictions() {
		return copyrightandotherrestrictions;
	}


	public void setCopyrightandotherrestrictions(int index) {
		this.copyrightandotherrestrictions.setValueIndex(index);
	}


	
	
	
}
