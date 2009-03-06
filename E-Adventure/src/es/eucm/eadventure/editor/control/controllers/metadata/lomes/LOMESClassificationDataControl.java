package es.eucm.eadventure.editor.control.controllers.metadata.lomes;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.data.meta.ims.IMSClassification;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESClassification;
import es.eucm.eadventure.editor.data.meta.LangString;

public class LOMESClassificationDataControl {

	private LOMESClassification data;
	
	public LOMESClassificationDataControl(LOMESClassification data){
		this.data = data;
	}
	
	public LOMESOptionsDataControl getPurpose() {
		return new LOMESOptionsDataControl (){

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
	

	public LOMESTextDataControl getDescription(){
		return new LOMESTextDataControl (){

			public String getText( ) {
				return data.getDescription().getValue( 0 );
			}

			public void setText( String text ) {
				data.setDescription( new LangString(text) );
			}
			
		};
	}
	
	public LOMESTextDataControl getSource(){
		return new LOMESTextDataControl (){

			public String getText( ) {
				return data.getSource().getValue( 0 );
			}

			public void setText( String text ) {
				data.setSource( new LangString(text) );
			}
			
		};
	}
	
	public LOMESTextDataControl getIdentifier(){
		return new LOMESTextDataControl (){

			public String getText( ) {
				return data.getIdentifier();
			}

			public void setText( String text ) {
				data.setIdentifier(text);
			}
			
		};
	}
	
	public LOMESTextDataControl getEntry(){
		return new LOMESTextDataControl (){

			public String getText( ) {
				return data.getEntry().getValue(0);
			}

			public void setText( String text ) {
				data.setEntry(new LangString(text));
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

	public LOMESClassification getData() {
		return data;
	}

	public void setData(LOMESClassification data) {
		this.data = data;
	}

	
	
	
}
