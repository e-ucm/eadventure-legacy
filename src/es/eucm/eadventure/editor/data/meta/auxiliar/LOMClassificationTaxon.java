package es.eucm.eadventure.editor.data.meta.auxiliar;

import es.eucm.eadventure.editor.data.meta.LangString;

public class LOMClassificationTaxon implements LOMESComposeType{

    
  
	//9.2.2 TAXON
	//9.2.2.1 
	private String identifier;
	
	//9.2.2.2
	private LangString entry;
	
	public LOMClassificationTaxon(){
	    identifier = new String("");
	    entry = new LangString("");
	}
	
	public LOMClassificationTaxon(String identifier,LangString entry){
	    this.identifier=identifier;
	    this.entry=entry;
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
	    return identifier;
	}

	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(String identifier) {
	    this.identifier = identifier;
	}

	/**
	 * @return the entry
	 */
	public LangString getEntry() {
	    return entry;
	}

	/**
	 * @param entry the entry to set
	 */
	public void setEntry(LangString entry) {
	    this.entry = entry;
	}

	public String getTitle() {
	    // TODO Auto-generated method stub
	    return null;
	}
    
}
