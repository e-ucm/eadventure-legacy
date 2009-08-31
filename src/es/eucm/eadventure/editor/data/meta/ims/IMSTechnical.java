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


import java.util.ArrayList;



public class IMSTechnical {

	//4.1
	private ArrayList<String> format;
	
	//4.3 Each location begins with a number which identifies the type attribute:
	//    0- URI
	//	  1- TEXT
	private ArrayList<String> location;
	
	
	//4.4.1.3
	private String minimumVersion;
	
	//4.4.1.4
	private String maximumVersion;

	
	public IMSTechnical (){
		format = new ArrayList<String>();
		location = new ArrayList<String>();
		minimumVersion = null;
		maximumVersion = null;
	}

	public void setFormat(String format){
		this.format.add(format);
		
	}
	
	public String getFormat(int i){
		return format.get(i);
	}
	
	/**
	 * 
	 * Add new location
	 * 
	 * @param location
	 * 				The String with the location
	 * @param URI
	 * 			Indicate if is URI or TEXT
	 */
	public void setLocation(String location, boolean URI){
		this.location.add(URI?"0 ":"1 "+location);
	}
	
	/**
	 * Returns the specified location
	 * 
	 * @param i
	 * @return
	 */
	public String getLocation(int i){
		return this.location.get(i);
	}
	
	/**
	 * Returns the first location
	 * @return
	 */
	public String getLocation(){
		return location.get(0);
	}
	
	/**
	 * Returns the first format
	 * 
	 * @return
	 */
	public String getFormat(){
		return format.get(0);
	}
	/**
	 * @return the minimumVersion
	 */
	public String getMinimumVersion( ) {
		return minimumVersion;
	}

	/**
	 * @param minimumVersion the minimumVersion to set
	 */
	public void setMinimumVersion( String minimumVersion ) {
		this.minimumVersion = minimumVersion;
	}

	/**
	 * @return the maximumVersion
	 */
	public String getMaximumVersion( ) {
		return maximumVersion;
	}

	/**
	 * @param maximumVersion the maximumVersion to set
	 */
	public void setMaximumVersion( String maximumVersion ) {
		this.maximumVersion = maximumVersion;
	}
	
	
	
}
