package es.eucm.eadventure.editor.control.controllers.ims;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.data.ims.IMSLifeCycle;
import es.eucm.eadventure.editor.data.lom.LOMLifeCycle;
import es.eucm.eadventure.editor.data.lom.LangString;

public class IMSLifeCycleDataControl {

	private IMSLifeCycle data;
	
	public IMSLifeCycleDataControl (IMSLifeCycle data){
		this.data = data;
	}
	
	public IMSTextDataControl getVersionController (){
		return new IMSTextDataControl (){

			public String getText( ) {
				return data.getVersion( ).getValue( 0 );
			}

			public void setText( String text ) {
				data.setVersion( new LangString(text) );
			}
			
		};
	}
	
	public IMSOptionsDataControl getStatusController() {
		return new IMSOptionsDataControl (){

			public String[] getOptions( ) {
				String[] options = new String[data.getStatus().getValues( ).length];
				for (int i=0; i<options.length; i++){
					options[i]=TextConstants.getText( "IMS.LifeCycle.Status"+i );
				}
				return options;
			}

			public void setOption( int option ) {
				data.getStatus().setValueIndex( option );
			}

			public int getSelectedOption( ) {
				return data.getStatus().getValueIndex( );
			}
			
		};
	}
	

	/**
	 * @return the data
	 */
	public IMSLifeCycle getData( ) {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData( IMSLifeCycle data ) {
		this.data = data;
	}

}
