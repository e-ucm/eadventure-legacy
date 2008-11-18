package es.eucm.eadventure.editor.data.lom;

import java.util.ArrayList;

import es.eucm.eadventure.editor.control.Controller;

public class LangString {

	private static final ArrayList<LangString> summary = new ArrayList<LangString> ();
	
	public static void updateLanguage (String lang){
		for (LangString ls:summary){
			ls.languages = new ArrayList<String>();
			for (int i=0; i<ls.getNValues( ); i++){
				ls.languages.add( lang );
			}
		}
	}
	
	public static final String XXLAN = "XXlan";
	
	private ArrayList<String> languages;
	
	private ArrayList<String> values;
	
	public LangString(String lang, String value){
		languages = new ArrayList<String>();
		values = new ArrayList<String>();
		
		languages.add( lang );
		values.add( value );
	}
	
	public LangString(String value){
		languages = new ArrayList<String>();
		values = new ArrayList<String>();

		languages.add( XXLAN );
		values.add( value );
		summary.add( this );
	}
	
	public LangString(){
		languages = new ArrayList<String>();
		values = new ArrayList<String>();
		summary.add( this );
	}
	
	public void addValue(String language, String value){
		languages.add( language );
		values.add( value );
	}
	
	public void addValue(String value){
		addValue(XXLAN, value);
	}
	
	public int getNValues(){
		return values.size( );
	}
	
	public String getValue(int i){
		return values.get( i );
	}
	
	public String getLanguage(int i){
		return languages.get( i );
	}
	
	public String getValue (String lang){
		for (int i=0; i<languages.size( ); i++){
			if (languages.get( i ).equals( lang ))
				return values.get( i );
		}
		return null;
	}
	
}
