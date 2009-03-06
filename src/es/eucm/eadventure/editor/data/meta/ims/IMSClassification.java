package es.eucm.eadventure.editor.data.meta.ims;

import java.util.ArrayList;

import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.Vocabulary;

public class IMSClassification {

	
	//private Vocabulary vocPurpose;
	// 9.1
	private Vocabulary purpose;
	
	// 9.2
	private LangString description;
	
	// 9.4
	private ArrayList<LangString> keyword;
	
	public IMSClassification(){
		purpose = new Vocabulary(Vocabulary.IMS_CL_PURPOSE_9_1);
		description = null;
		keyword = new ArrayList<LangString>();
		
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

	

	
}
