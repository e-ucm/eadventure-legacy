package es.eucm.eadventure.editor.control.controllers.metadata.ims;

import es.eucm.eadventure.editor.data.meta.ims.IMSMetaMetaData;
import es.eucm.eadventure.editor.data.meta.LangString;

public class IMSMetaMetaDataControl {

	private IMSMetaMetaData data;
	
	public IMSMetaMetaDataControl(IMSMetaMetaData data){
		this.data = data;
	}
	
	public IMSTextDataControl getMetadataschemeController (){
		return new IMSTextDataControl (){

			public String getText( ) {
				return data.getMetadatascheme();
			}

			public void setText( String text ) {
				data.setMetadatascheme(text);
			}
			
		};
	}

	public IMSMetaMetaData getData() {
		return data;
	}

	public void setData(IMSMetaMetaData data) {
		this.data = data;
	}
	
}
