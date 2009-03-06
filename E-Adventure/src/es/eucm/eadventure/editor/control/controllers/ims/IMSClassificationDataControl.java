package es.eucm.eadventure.editor.control.controllers.metadata.ims;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.data.meta.ims.IMSClassification;
import es.eucm.eadventure.editor.data.meta.LangString;

public class IMSClassificationDataControl {

	private IMSClassification data;
	
	public IMSClassificationDataControl(IMSClassification data){
		this.data = data;
	}
	
	public IMSOptionsDataControl getPurpose() {
		return new IMSOptionsDataControl (){

			public String[] getOptions( ) {
				String[] options = new String[data.getPurpose().getValues( ).length];
				for (int i=0; i<options.length; i++){
					options[i]=TextConstants.getText( "IMS.Classification.Purpose"+i );
				}
				return options;
			}

			public void setOption( int option ) {
				data.setPurpose(option);
			}

			public int getSelectedOption( ) {
				return data.getPurpose().getValueIndex( );
			}
			
		};
	}
	

	public IMSTextDataControl getDescription(){
		return new IMSTextDataControl (){

			public String getText( ) {
				return data.getDescription().getValue( 0 );
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

	public IMSClassification getData() {
		return data;
	}

	public void setData(IMSClassification data) {
		this.data = data;
	}

	
	
	
}
