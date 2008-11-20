package es.eucm.eadventure.editor.control.controllers.lom;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.data.lom.LOMGeneral;
import es.eucm.eadventure.editor.data.lom.LOMLifeCycle;
import es.eucm.eadventure.editor.data.lom.LangString;

public class LOMLifeCycleDataControl {

	private LOMLifeCycle data;
	
	public LOMLifeCycleDataControl (LOMLifeCycle data){
		this.data = data;
	}
	
	public LOMTextDataControl getVersionController (){
		return new LOMTextDataControl (){

			public String getText( ) {
				return data.getVersion( ).getValue( 0 );
			}

			public void setText( String text ) {
				data.setVersion( new LangString(text) );
			}
			
		};
	}
	

	/**
	 * @return the data
	 */
	public LOMLifeCycle getData( ) {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData( LOMLifeCycle data ) {
		this.data = data;
	}

}
