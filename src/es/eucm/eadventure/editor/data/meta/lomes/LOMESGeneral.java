package es.eucm.eadventure.editor.data.meta.lomes;

import java.util.ArrayList;

import es.eucm.eadventure.editor.data.meta.LangString;

public class LOMESGeneral {

	//1.1 Identifier
	//1.1.1
	private String catalog;
	
	//1.1.2
	private String entry;
	
	//1.2
	private LangString title;
	
	//1.3
	private ArrayList<String> language;
	
	//1.4
	private ArrayList<LangString> description;
	
	//1.5 
	private ArrayList<LangString> keyword;
	
	public LOMESGeneral (){
		catalog = null;
		entry = null;
		title = null;
		language = new ArrayList<String>();
		description = new ArrayList<LangString>();
		keyword = new ArrayList<LangString>();
	}
	
	
	/***********************************ADD METHODS **************************/
	public void addTitle(LangString title){
		this.title=title;
	}
	
	public void addLanguage(String language){
		this.language.add( language );
	}
	
	public void addDescription(LangString desc){
		this.description.add( desc );
	}
	
	public void addKeyword(LangString keyword){
		this.keyword.add( keyword );
	}
	
	public void addCatalog(String catalog){
		this.catalog = catalog;
	}
	
	public void addEntry(String entry){
		this.entry = entry;
	}
	



	/*********************************** SETTERS **************************/
	public void setTitle(LangString title){
		this.title=title;
	}
	
	public void setLanguage(String language){
		this.language = new ArrayList<String>();
		this.language.add( language );
	}
	
	public void setDescription(LangString desc){
		description = new ArrayList<LangString>();
		this.description.add( desc );
	}
	
	public void setKeyword(LangString keyword){
		this.keyword = new ArrayList<LangString>();
		this.keyword.add( keyword );
	}
	

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}


	public void setEntry(String entry) {
		this.entry = entry;
	}
	

	/*********************************** GETTERS **************************/
	//IDENTIFIER
	public String getCatalog(){
		return this.catalog;
	}
	
	public String getEntry(){
		return this.entry;
	}
	
	//TITLE
	public LangString getTitle(){
		return title;
	}
	
	//LANGUAGE
	public String getLanguage(){
		return language.get( 0 );
	}
	
	public String getLanguage( int i){
		return language.get( i );
	}
	
	public int getNLanguage(){
		return language.size( );
	}

	//DESCRIPTION
	public LangString getDescription(){
		return description.get( 0 );
	}
	
	public LangString getDescription(int i){
		return description.get( i );
	}
	
	public int getNDescription(){
		return description.size( );
	}
	
	//KEYWORD
	public LangString getKeyword(){
		return keyword.get( 0 );
	}
	
	public LangString getKeyword(int i){
		return keyword.get( i );
	}
	
	public int getNKeyword(){
		return keyword.size( );
	}
	
}
