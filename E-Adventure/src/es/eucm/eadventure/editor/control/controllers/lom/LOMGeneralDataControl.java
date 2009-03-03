package es.eucm.eadventure.editor.control.controllers.lom;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.lom.LOMGeneral;
import es.eucm.eadventure.editor.data.lom.LangString;
import es.eucm.eadventure.editor.control.config.LOMConfigData;

public class LOMGeneralDataControl {

	public static final String[] AVAILABLE_LANGS= new String[]{"en", "es"};
	
	public static final String GROUP = "general";
	
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
				LOMConfigData.storeData(GROUP, "title", text);
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
					LOMConfigData.storeData(GROUP, "language", Integer.toString(option));
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
				LOMConfigData.storeData(GROUP, "description", text);
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
				LOMConfigData.storeData(GROUP, "keyword", text);
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
