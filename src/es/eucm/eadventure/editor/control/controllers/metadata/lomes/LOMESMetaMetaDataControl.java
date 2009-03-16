package es.eucm.eadventure.editor.control.controllers.metadata.lomes;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESContainer;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMIdentifier;
import es.eucm.eadventure.editor.data.meta.ims.IMSMetaMetaData;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESMetaMetaData;
import es.eucm.eadventure.editor.data.meta.LangString;

public class LOMESMetaMetaDataControl {

	private LOMESMetaMetaData data;
	
	public static final String[] AVAILABLE_LANGS= new String[]{"en", "es"};
	
	public LOMESMetaMetaDataControl(LOMESMetaMetaData data){
		this.data = data;
	}
	
	public LOMIdentifier getIdentifier(){
		return data.getIdentifier();
	}
	
	public LOMESContainer getContribute(){
		return data.getContribute();
	}
	
	
	
	public LOMESTextDataControl getDescription(){
		return new LOMESTextDataControl (){

			public String getText( ) {
				return data.getDescription();
			}

			public void setText( String text ) {
				data.setDescription(text);
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
	
	public LOMESTextDataControl getMetadataschemeController (){
		return new LOMESTextDataControl (){

			public String getText( ) {
				return data.getMetadatascheme();
			}

			public void setText( String text ) {
				data.setMetadatascheme(text);
			}
			
		};
	}

	public LOMESMetaMetaData getData() {
		return data;
	}

	public void setData(LOMESMetaMetaData data) {
		this.data = data;
	}
	
}
