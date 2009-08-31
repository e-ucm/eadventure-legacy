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
package es.eucm.eadventure.editor.control.controllers.metadata.lomes;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.ims.IMSRights;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESRights;


public class LOMESRightsDataControl {

	public static final String[] AVAILABLE_OPTIONS= new String[]{"yes", "no"};
	
	private LOMESRights data;
	
	public LOMESRightsDataControl(LOMESRights data){
		this.data = data;
	}
	
	public LOMESOptionsDataControl getCost() {
		return new LOMESOptionsDataControl (){

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
					if (AVAILABLE_OPTIONS[i].equals( data.getCost().getValue() ))
						return i;
				}
				return -1;
			}
			
		};
	}
	
	public LOMESOptionsDataControl getCopyrightandotherrestrictions() {
		return new LOMESOptionsDataControl (){

			public String[] getOptions( ) {
			    String[] options = new String[data.getCopyrightandotherrestrictions().getValues( ).length];
				for (int i=0; i<options.length; i++){
					options[i]=TextConstants.getText( "LOMES.Rights.CopyAndOthers"+i );
				}
				return options;
			}

			public void setOption( int option ) {
				if (option!=getSelectedOption()){
					data.setCopyrightandotherrestrictions(option);
				}
				
			}

			public int getSelectedOption( ) {
				for (int i=0; i<AVAILABLE_OPTIONS.length; i++){
					if (AVAILABLE_OPTIONS[i].equals( data.getCopyrightandotherrestrictions().getValue() ))
						return i;
				}
				return -1;
			}
			
		};
	}
	
	public LOMESTextDataControl getDescriptionController (){
		return new LOMESTextDataControl (){

			public String getText( ) {
				return data.getDescription().getValue(0);
			}

			public void setText( String text ) {
				data.setDescription( new LangString(text) );
			}
			
		};
	}
	
	public LOMESTextDataControl getAccessDescriptionController (){
		return new LOMESTextDataControl (){

			public String getText( ) {
				return data.getAccessDescription().getValue(0);
			}

			public void setText( String text ) {
				data.setAccessDescription(new LangString(text) );
			}
			
		};
	}
	
	public LOMESOptionsDataControl getAccesType() {
		return new LOMESOptionsDataControl (){

			public String[] getOptions( ) {
				String[] options = new String[data.getAccessType().getValues( ).length];
				for (int i=0; i<options.length; i++){
					options[i]=TextConstants.getText( "LOMES.Rights.AccesType"+i );
				}
				return options;
			}

			public void setOption( int option ) {
				if (option!=getSelectedOption()){
					data.setCopyrightandotherrestrictions(option);
				}
				
			}

			public int getSelectedOption( ) {
				for (int i=0; i<AVAILABLE_OPTIONS.length; i++){
					if (AVAILABLE_OPTIONS[i].equals( data.getAccessType().getValue() ))
						return i;
				}
				return -1;
			}
			
		};
	}

	public LOMESRights getData() {
		return data;
	}

	public void setData(LOMESRights data) {
		this.data = data;
	}
	
	
}
