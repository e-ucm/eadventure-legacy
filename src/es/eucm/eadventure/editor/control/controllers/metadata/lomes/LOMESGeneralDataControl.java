package es.eucm.eadventure.editor.control.controllers.metadata.lomes;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.meta.ims.IMSGeneral;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESGeneral;
import es.eucm.eadventure.editor.data.meta.LangString;

public class LOMESGeneralDataControl {

	public static final String[] AVAILABLE_LANGS= new String[]{"en", "es"};
	
	private LOMESGeneral data;
	
	public LOMESGeneralDataControl (LOMESGeneral data){
		this.data = data;
	}
	
	public LOMESTextDataControl getTitleController (){
		return new LOMESTextDataControl (){

			public String getText( ) {
				return data.getTitle( ).getValue( 0 );
			}

			public void setText( String text ) {
				data.setTitle( new LangString(text) );
			}
			
		};
	}
	
	public LOMESOptionsDataControl getLanguageController() {
		return new LOMESOptionsDataControl (){

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
	
	public LOMESTextDataControl getCatalogController() {
		return new LOMESTextDataControl (){

			public String getText( ) {
				return data.getCatalog();
			}

			public void setText( String text ) {
				data.setCatalog(text );
			}
			
		};
	}
	
	public LOMESTextDataControl getEntryController() {
		return new LOMESTextDataControl (){

			public String getText( ) {
				return data.getEntry();
			}

			public void setText( String text ) {
				data.setEntry(text );
			}
			
		};
	}
	
	
	public LOMESTextDataControl getDescriptionController (){
		return new LOMESTextDataControl (){

			public String getText( ) {
				return data.getDescription( ).getValue( 0 );
			}

			public void setText( String text ) {
				data.setDescription( new LangString(text) );
			}
			
		};
	}
	
	public LOMESTextDataControl getKeywordController (){
		return new LOMESTextDataControl (){

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
	public LOMESGeneral getData( ) {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData( LOMESGeneral data ) {
		this.data = data;
	}

}
