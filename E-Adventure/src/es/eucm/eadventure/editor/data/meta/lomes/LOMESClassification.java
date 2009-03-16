package es.eucm.eadventure.editor.data.meta.lomes;

import java.util.ArrayList;

import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.Vocabulary;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMTaxonPath;

public class LOMESClassification {

	
	// 9.1
	private Vocabulary purpose;
	
	//9.2 Taxon  Path
	private LOMTaxonPath taxonPath;
	
	// 9.3
	private LangString description;
	
	// 9.4
	private ArrayList<LangString> keyword;
	
	public LOMESClassification(){
		purpose = new Vocabulary(Vocabulary.LOMES_CL_PURPOSE_9_1);
		description = null;
		keyword = new ArrayList<LangString>();
		taxonPath = new LOMTaxonPath();
		
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
	
	public ArrayList<LangString> getKeywords(){
	    return keyword;
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


	public void setPurpose(Vocabulary purpose) {
		this.purpose = purpose;
	}

	/**
	 * @return the taxonPath
	 */
	public LOMTaxonPath getTaxonPath() {
	    return taxonPath;
	}

	/**
	 * @param taxonPath the taxonPath to set
	 */
	public void setTaxonPath(LOMTaxonPath taxonPath) {
	    this.taxonPath = taxonPath;
	}

	

	
}
