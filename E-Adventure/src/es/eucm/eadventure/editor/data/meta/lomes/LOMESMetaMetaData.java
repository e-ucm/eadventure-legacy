package es.eucm.eadventure.editor.data.meta.lomes;

import java.util.ArrayList;

import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.Vocabulary;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMContribute;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESGeneralId;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMIdentifier;

public class LOMESMetaMetaData {

	
	//3.1 Identifier
	private LOMIdentifier identifier;
	
	//3.2 Contribute
	private LOMContribute contribute;
	
	private String description;
	
	//3.3
	private ArrayList<String> metadatascheme;
	
	//3.4
	private String language;
	
	public LOMESMetaMetaData (){
		metadatascheme = new ArrayList<String>();
		identifier = new LOMIdentifier(true);
		language = new String();
}
	
	
	/***********************************ADD METHODS **************************/
	public void addMetadatascheme(String metadatascheme){
		this.metadatascheme.add(metadatascheme);
	}
	
	public void addIdentifier(String catalog, String entry){
		identifier.addIdentifier(catalog, entry);
	}
	
	/*********************************** SETTERS **************************/
	

	/**
	 * @param contribute the contribute to set
	 */
	public void setContribute(LOMContribute contribute) {
		this.contribute = contribute;
	}
	
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setLanguage(String language){
		this.language = language;
	}
	
	public void setMetadatascheme(String metadatascheme){
		this.metadatascheme = new ArrayList();
		this.metadatascheme.add(metadatascheme);
	}
	
	/*********************************** GETTERS **************************/
	//IDENTIFIER
	public LOMESGeneralId getIdentifier(int index){
		return (LOMESGeneralId)identifier.get(index);
	}
		
	public int getNIdentifier(){
		return identifier.getSize();
	}
	
	/**
	 * @return the identifier
	 */
	public LOMIdentifier getIdentifier() {
		return identifier;
	}
	
	//CONTRIBUTION
	
	/**
	 * @return the contribute
	 */
	public LOMContribute getContribute() {
		return contribute;
	}

	public String getDescription() {
		return description;
	}
	
	//LANGUAGE
	public String getLanguage(){
		return this.language;
	}
	
	public String getMetadatascheme(int i){
		return metadatascheme.get(i);
	}
	public String getMetadatascheme(){
		return metadatascheme.get(0);
	}
	
	
}
