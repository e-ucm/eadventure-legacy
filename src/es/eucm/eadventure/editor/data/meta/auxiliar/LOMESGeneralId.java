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

import java.util.ArrayList;

import es.eucm.eadventure.common.gui.TextConstants;

public class LOMESGeneralId implements LOMESComposeType{

	
	public static final int NUMBER_ATTR = 2;
	
	
	//1.1.1
	private String catalog;

	private String entry;
	
	public LOMESGeneralId(){
		this.catalog = null;
		this.entry = null;
	}
	
	public LOMESGeneralId(String catalog,String entry){
		this.catalog = catalog;
		this.entry = entry;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	
	public static String[] attributes() {
		String[] attr = new String[NUMBER_ATTR];
		attr[0] = TextConstants.getText("LOMES.GeneralId.CatalogName")+" "+ATTR_STRING;
		attr[0] = TextConstants.getText("LOMES.GeneralId.EntryName")+" "+ATTR_STRING;
		return attr;
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
