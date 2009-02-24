package es.eucm.eadventure.editor.control.controllers.ims;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.ims.IMSGeneral;
import es.eucm.eadventure.editor.data.lom.LOMGeneral;
import es.eucm.eadventure.editor.data.lom.LangString;

public class IMSGeneralDataControl {

	public static final String[] AVAILABLE_LANGS= new String[]{"en", "es"};
	
	private IMSGeneral data;
	
	public IMSGeneralDataControl (IMSGeneral data){
		this.data = data;
	}
	
	public IMSTextDataControl getTitleController (){
		return new IMSTextDataControl (){

			public String getText( ) {
				return data.getTitle( ).getValue( 0 );
			}

			public void setText( String text ) {
				data.setTitle( new LangString(text) );
			}
			
		};
	}
	
	public IMSOptionsDataControl getLanguageController() {
		return new IMSOptionsDataControl (){

			public String[] getOptions( ) {
				return new String[]{TextConstants.getText("LOM.General.Language.English"), TextConstants.getText("LOM.General.Language.Spanish")};
			}

			public void setOption( int option ) {
				if (option!=getSelectedOption()){
					data.setLanguage( AVAILABLE_LANGS[option] );
					// TODO ver que pasa con esto!!
					//Controller.getInstance().updateLOMLanguage();
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
	
	public IMSTextDataControl getCatalogController() {
		return new IMSTextDataControl (){

			public String getText( ) {
				return data.getCatalog();
			}

			public void setText( String text ) {
				data.setCalaog( text );
			}
			
		};
	}
	
	public IMSTextDataControl getEntryController() {
		return new IMSTextDataControl (){

			public String getText( ) {
				return data.getEntry().getValue(0);
			}

			public void setText( String text ) {
				data.setEntry( new LangString(text) );
			}
			
		};
	}
	
	
	public IMSTextDataControl getDescriptionController (){
		return new IMSTextDataControl (){

			public String getText( ) {
				return data.getDescription( ).getValue( 0 );
			}

			public void setText( String text ) {
				data.setDescription( new LangString(text) );
			}
			
		};
	}
	
	public IMSTextDataControl getKeywordController (){
		return new IMSTextDataControl (){

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
	public IMSGeneral getData( ) {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData( IMSGeneral data ) {
		this.data = data;
	}

}
