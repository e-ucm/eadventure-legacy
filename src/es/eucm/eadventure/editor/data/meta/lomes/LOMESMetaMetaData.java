package es.eucm.eadventure.editor.data.meta.lomes;

import java.util.ArrayList;

import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.Vocabulary;

public class LOMESMetaMetaData {

	
	//3.1 Identifier
	//3.1.1
	private String catalog;
	
	//3.1.2
	private String entry;
	
	//3.2 Contribute
	//3.2.1
	private Vocabulary role; 
	
	//3.2.2 
	private String entity;
	
	//3.2.3 Date; It has 2 values, dateTime and description
	private String dateTime;
	
	private String description;
	
	//3.3
	private ArrayList<String> metadatascheme;
	
	//3.4
	private String language;
	
	public LOMESMetaMetaData (){
		metadatascheme = new ArrayList<String>();
}
	
	
	/***********************************ADD METHODS **************************/
	public void addMetadatascheme(String metadatascheme){
		this.metadatascheme.add(metadatascheme);
	}
	
	/*********************************** SETTERS **************************/
	public void setCatalog(String catalog){
		this.catalog = catalog;
	}
	
	public void setEntry(String entry){
		this.entry = entry;
	}
	

	public void setRole(int index){
		this.role = new Vocabulary(Vocabulary.MD_CONTRIBUTION_TYPE_2_3_1,index);
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
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
	public String getCatalog(){
		return this.catalog;
	}
	
	public String getEntry(){
		return this.entry;
	}
	
	//CONTRIBUTION
	
	public Vocabulary getRole(){
		return role;
	}


	public String getEntity() {
		return entity;
	}
	
	public String getDateTime() {
		return dateTime;
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
