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

import java.util.ArrayList;

import es.eucm.eadventure.editor.data.meta.auxiliar.LOMContribute;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESGeneralId;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMIdentifier;

public class LOMESMetaMetaData {

	
	//3.1 Identifier
	private LOMIdentifier identifier;
	
	//3.2 Contribute
	private LOMContribute contribute;
	
	private String description;
	
	//3.3
	private ArrayList<String> metadatascheme;
	
	//3.4
	private String language;
	
	public LOMESMetaMetaData (){
		metadatascheme = new ArrayList<String>();
		identifier = new LOMIdentifier(true);
		language = new String();
}
	
	
	/***********************************ADD METHODS **************************/
	public void addMetadatascheme(String metadatascheme){
		this.metadatascheme.add(metadatascheme);
	}
	
	public void addIdentifier(String catalog, String entry){
		identifier.addIdentifier(catalog, entry);
	}
	
	/*********************************** SETTERS **************************/
	

	/**
	 * @param contribute the contribute to set
	 */
	public void setContribute(LOMContribute contribute) {
		this.contribute = contribute;
	}
	
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setLanguage(String language){
		this.language = language;
	}
	
	public void setMetadatascheme(String metadatascheme){
		this.metadatascheme = new ArrayList<String>();
		this.metadatascheme.add(metadatascheme);
	}
	
	/*********************************** GETTERS **************************/
	//IDENTIFIER
	public LOMESGeneralId getIdentifier(int index){
		return (LOMESGeneralId)identifier.get(index);
	}
		
	public int getNIdentifier(){
		return identifier.getSize();
	}
	
	/**
	 * @return the identifier
	 */
	public LOMIdentifier getIdentifier() {
		return identifier;
	}
	
	//CONTRIBUTION
	
	/**
	 * @return the contribute
	 */
	public LOMContribute getContribute() {
		return contribute;
	}

	public String getDescription() {
		return description;
	}
	
	//LANGUAGE
	public String getLanguage(){
		return this.language;
	}
	
	public String getMetadatascheme(int i){
		return metadatascheme.get(i);
	}
	public String getMetadatascheme(){
		return metadatascheme.get(0);
	}
	
	
}
