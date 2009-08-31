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
package es.eucm.eadventure.editor.control.controllers.metadata.ims;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.meta.ims.IMSRights;


public class IMSRightsDataControl {

	public static final String[] AVAILABLE_OPTIONS= new String[]{"yes", "no"};
	
	private IMSRights data;
	
	public IMSRightsDataControl(IMSRights data){
		this.data = data;
	}
	
	public IMSOptionsDataControl getCost() {
		return new IMSOptionsDataControl (){

			public String[] getOptions( ) {
				return new String[]{"Yes","No"};
			}

			public void setOption( int option ) {
				if (option!=getSelectedOption()){
					data.setCost(option) ;
				}
				
			}

			public int getSelectedOption( ) {
				for (int i=0; i<AVAILABLE_OPTIONS.length; i++){
					if (AVAILABLE_OPTIONS[i].equals( data.getCost() ))
						return i;
				}
				return -1;
			}
			
		};
	}
	
	public IMSOptionsDataControl getCopyrightandotherrestrictions() {
		return new IMSOptionsDataControl (){

			public String[] getOptions( ) {
				return new String[]{"Yes","No"};
			}

			public void setOption( int option ) {
				if (option!=getSelectedOption()){
					data.setCopyrightandotherrestrictions(option);
				}
				
			}

			public int getSelectedOption( ) {
				for (int i=0; i<AVAILABLE_OPTIONS.length; i++){
					if (AVAILABLE_OPTIONS[i].equals( data.getCopyrightandotherrestrictions() ))
						return i;
				}
				return -1;
			}
			
		};
	}

	public IMSRights getData() {
		return data;
	}

	public void setData(IMSRights data) {
		this.data = data;
	}
	
	
}
