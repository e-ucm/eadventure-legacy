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

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
