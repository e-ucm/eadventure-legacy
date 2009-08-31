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
package es.eucm.eadventure.editor.data.meta.lomes;

import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.Vocabulary;

public class LOMESRights {

    
    	public static final String DEFAULT_DESCRIPTION="La utilización de estos contenidos es universal, gratuita y abierta, siempre y cuando se trate de un uso educativo no comercial. Las acciones, productos y utilidades derivadas de su utilización no podrán, en consecuencia, generar ningún tipo de lucro. Asimismo, es obligada la referencia a la fuente.";
    
    	public static final String DEFAULT_ACCESS_DSCRIPTION="No existen restricciones ó MEC";
    	
	/*
	 * cost and copyrightandotherrestrictions only can be "yes" or "no"
	 */

	// 6.1
	private Vocabulary cost;
	
	// 6.2
	private Vocabulary copyrightandotherrestrictions;
	
	//6.3 
	private LangString description;
	
	//6.4 ACCESS
	//6.4.1
	private Vocabulary accessType;
	
	//6.4.2
	private LangString accessDescription;
	
	
	public LOMESRights(){
		cost = new Vocabulary(Vocabulary.IMS_YES_NO,Vocabulary.LOM_ES_SOURCE,0);
		copyrightandotherrestrictions = new Vocabulary(Vocabulary.LOMES_RG_COP_AND_OTHER_6_2,Vocabulary.LOM_ES_SOURCE,0);
		description = new LangString(DEFAULT_DESCRIPTION);
		accessType = new Vocabulary(Vocabulary.LOMES_RG_ACCESS_TYPE_6_4_1,Vocabulary.LOM_ES_SOURCE,0);
		accessDescription = null;
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
		this.copyrightandotherrestrictions = new Vocabulary(Vocabulary.LOMES_RG_COP_AND_OTHER_6_2,Vocabulary.LOM_ES_SOURCE,index);
	}


	public LangString getDescription() {
		return description;
	}


	public void setDescription(LangString description) {
		this.description = description;
	}


	public Vocabulary getAccessType() {
		return accessType;
	}


	public void setAccessType(int index) {
		this.accessType = new Vocabulary(Vocabulary.LOMES_RG_ACCESS_TYPE_6_4_1,Vocabulary.LOM_ES_SOURCE,index);
	}


	public LangString getAccessDescription() {
		return accessDescription;
	}


	public void setAccessDescription(LangString accessDescription) {
		this.accessDescription = accessDescription;
	}


	public void setCost(Vocabulary cost) {
		this.cost = cost;
	}


	public void setCopyrightandotherrestrictions(
			Vocabulary copyrightandotherrestrictions) {
		this.copyrightandotherrestrictions = copyrightandotherrestrictions;
	}


	
	
	
}
