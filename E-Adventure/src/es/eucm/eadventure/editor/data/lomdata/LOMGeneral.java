package es.eucm.eadventure.editor.data.lomdata;

import java.util.ArrayList;

import es.eucm.eadventure.editor.control.controllers.lom.LOMTextDataControl;

public class LOMGeneral {

	//1.1
	private LangString title;
	
	//1.2
	private ArrayList<String> language;
	
	//1.4
	private ArrayList<LangString> description;
	
	//1.5 
	private ArrayList<LangString> keyword;
	
	public LOMGeneral (){
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
		this.description.add( keyword );
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

	/*********************************** GETTERS **************************/
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
