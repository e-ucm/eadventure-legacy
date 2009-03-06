package es.eucm.eadventure.editor.data.meta.lomes;

import java.util.ArrayList;

import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.Vocabulary;

public class LOMESClassification {

	
	// 9.1
	private Vocabulary purpose;
	
	//9.2 Taxon  Path
	
	//9.2.1
	private LangString source;
	
	//9.2.2 TAXON
	//9.2.2.1 
	private String identifier;
	
	//9.2.2.2
	private LangString entry;
	
	// 9.3
	private LangString description;
	
	// 9.4
	private ArrayList<LangString> keyword;
	
	public LOMESClassification(){
		purpose = new Vocabulary(Vocabulary.LOMES_CL_PURPOSE_9_1);
		description = null;
		keyword = new ArrayList<LangString>();
		source =null;
		identifier=null;
		entry=null;
		
	}
	
	public void addKeyword(LangString keyword){
		this.keyword.add( keyword );
	}
	
	

	public void setKeyword(LangString keyword){
		this.keyword = new ArrayList<LangString>();
		this.keyword.add( keyword );
	}
	
	public LangString getKeyword(){
		return keyword.get( 0 );
	}
	
	public LangString getKeyword(int i){
		return keyword.get( i );
	}
	
	public int getNKeyword(){
		return keyword.size( );
	}

	

	public Vocabulary getPurpose() {
		return purpose;
	}

	public void setPurpose(int index) {
		this.purpose.setValueIndex(index);
		 
	}

	public LangString getDescription() {
		return description;
	}

	public void setDescription(LangString description) {
		this.description = description;
	}

	public void setKeyword(ArrayList<LangString> keyword) {
		this.keyword = keyword;
	}

	public LangString getSource() {
		return source;
	}

	public void setSource(LangString source) {
		this.source = source;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public LangString getEntry() {
		return entry;
	}

	public void setEntry(LangString entry) {
		this.entry = entry;
	}

	public void setPurpose(Vocabulary purpose) {
		this.purpose = purpose;
	}

	

	
}
