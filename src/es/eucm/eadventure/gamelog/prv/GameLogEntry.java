/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.gamelog.prv;

import java.util.ArrayList;
import java.util.List;

public class GameLogEntry {

	private String elementName;
	
	private List<GameLogEntryAttribute> attributes;
	
	private long timeStamp;
	
	public GameLogEntry ( long startTime, String elementName ){
		this (startTime, elementName, new String[]{});
	}
	
	public GameLogEntry ( long startTime, String elementName, String[] attributes ){
		this.timeStamp = System.currentTimeMillis()-startTime;
		this.elementName = elementName;
		this.attributes = new ArrayList<GameLogEntryAttribute>();
		for (String attribute: attributes){
			if (attribute.contains("=")){
				String name = attribute.substring(0, attribute.indexOf("="));
				String value = attribute.substring(attribute.indexOf("=")+1, attribute.length());
				this.attributes.add(new GameLogEntryAttribute(name, value));
			}
		}
		this.attributes.add( new GameLogEntryAttribute ("ms", Long.toString(timeStamp)) );
	}
	
	public int getAttributeCount () {
		return attributes.size();
	}
	
	public String getAttributeName ( int i ){
		if (i>=0 && i<attributes.size())
			return attributes.get(i).getAttributeName();
		else
			return null;
	}
	
	public String getAttributeValue ( int i ){
		if (i>=0 && i<attributes.size())
			return attributes.get(i).getAttributeValue();
		else
			return null;
	}
	
	public String getElementName() {
		return elementName;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	@Override
    public String toString(){
	    String str="<"+this.elementName+" ";
	    for (GameLogEntryAttribute at:attributes){
	        str+=at.attributeName+"=\""+at.attributeValue+"\"";
	    }
	    str+="/>";
	    return str;
	}
	
	private class GameLogEntryAttribute {
		private String attributeName;
		private String attributeValue;
		
		public GameLogEntryAttribute(String attributeName, String attributeValue) {
			super();
			this.attributeName = attributeName;
			this.attributeValue = attributeValue;
		}
		public String getAttributeName() {
			return attributeName;
		}
		public String getAttributeValue() {
			return attributeValue;
		}
		
	}
	
}
