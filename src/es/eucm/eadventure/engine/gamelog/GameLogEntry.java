package es.eucm.eadventure.engine.gamelog;

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
