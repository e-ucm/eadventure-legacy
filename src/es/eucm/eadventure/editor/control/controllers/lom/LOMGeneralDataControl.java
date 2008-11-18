package es.eucm.eadventure.editor.control.controllers.lom;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.lom.LOMGeneral;
import es.eucm.eadventure.editor.data.lom.LangString;
import es.eucm.eadventure.editor.gui.TextConstants;

public class LOMGeneralDataControl {

	public static final String[] AVAILABLE_LANGS= new String[]{"en", "es"};
	
	private LOMGeneral data;
	
	public LOMGeneralDataControl (LOMGeneral data){
		this.data = data;
	}
	
	public LOMTextDataControl getTitleController (){
		return new LOMTextDataControl (){

			public String getText( ) {
				return data.getTitle( ).getValue( 0 );
			}

			public void setText( String text ) {
				data.setTitle( new LangString(text) );
			}
			
		};
	}
	
	public LOMOptionsDataControl getLanguageController() {
		return new LOMOptionsDataControl (){

			public String[] getOptions( ) {
				return new String[]{TextConstants.getText("LOM.General.Language.English"), TextConstants.getText("LOM.General.Language.Spanish")};
			}

			public void setOption( int option ) {
				if (option!=getSelectedOption()){
					data.setLanguage( AVAILABLE_LANGS[option] );
					Controller.getInstance().updateLOMLanguage();
				}
				
			}

			public int getSelectedOption( ) {
				for (int i=0; i<AVAILABLE_LANGS.length; i++){
					if (AVAILABLE_LANGS[i].equals( data.getLanguage( ) ))
						return i;
				}
				return -1;
			}
			
		};
	}
	
	
	public LOMTextDataControl getDescriptionController (){
		return new LOMTextDataControl (){

			public String getText( ) {
				return data.getDescription( ).getValue( 0 );
			}

			public void setText( String text ) {
				data.setDescription( new LangString(text) );
			}
			
		};
	}
	
	public LOMTextDataControl getKeywordController (){
		return new LOMTextDataControl (){

			public String getText( ) {
				return data.getKeyword( ).getValue( 0 );
			}

			public void setText( String text ) {
				data.setKeyword( new LangString(text) );
			}
			
		};
	}

	/**
	 * @return the data
	 */
	public LOMGeneral getData( ) {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData( LOMGeneral data ) {
		this.data = data;
	}

}
