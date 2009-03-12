package es.eucm.eadventure.editor.data.meta.auxiliar;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.data.meta.LangString;

public class LOMESLifeCycleDate implements LOMESComposeType{

	//2.3.3 Date; It has 2 values, dateTime and description
	private String dateTime;
	
	private LangString description;
	
	public LOMESLifeCycleDate(){
		dateTime = new String("1970-12-2");
		description = new LangString("");
	}
	
	public LOMESLifeCycleDate(String dateTime,LangString description){
		this.dateTime = dateTime;
		this.description = description;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public LangString getDescription() {
		return description;
	}

	public void setDescription(LangString description) {
		this.description = description;
	}

	public static String[] attributes() {
		String[] attr = new String[LOMESGeneralId.NUMBER_ATTR];
		attr[0] = TextConstants.getText("LOMES.Date.DateTimeName")+" "+ATTR_STRING;
		attr[0] = TextConstants.getText("LOMES.Date.Description")+" "+ATTR_STRING;
		return attr;
	}

	@Override
	public String getTitle() {
		
		return TextConstants.getText("LOMES.LifeCycle.DateTitle");
	}

	
}
