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
package es.eucm.eadventure.editor.data.meta.auxiliar;

import es.eucm.eadventure.editor.data.meta.LangString;

public class LOMClassificationTaxonPath implements LOMESComposeType{

    	//9.2.1
	private LangString source;
	//9.2.2
	private LOMTaxon taxon;
	
	public LOMClassificationTaxonPath(){
	    source = new LangString("");
	    taxon = new LOMTaxon();
	}
	
	public LOMClassificationTaxonPath(LangString source,LOMTaxon taxon){
	    this.source = source ;
	    this.taxon=taxon;
	}

	/**
	 * @return the source
	 */
	public LangString getSource() {
	    return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(LangString source) {
	    this.source = source;
	}

	/**
	 * @return the taxon
	 */
	public LOMTaxon getTaxon() {
	    return taxon;
	}

	/**
	 * @param taxon the taxon to set
	 */
	public void setTaxon(LOMTaxon taxon) {
	    this.taxon = taxon;
	}

	public String getTitle() {
	    // TODO Auto-generated method stub
	    return null;
	}
	
	
    
}
