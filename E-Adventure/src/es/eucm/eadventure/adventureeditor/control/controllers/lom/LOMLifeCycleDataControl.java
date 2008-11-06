package es.eucm.eadventure.adventureeditor.control.controllers.lom;

import es.eucm.eadventure.adventureeditor.data.lomdata.LOMGeneral;
import es.eucm.eadventure.adventureeditor.data.lomdata.LOMLifeCycle;
import es.eucm.eadventure.adventureeditor.data.lomdata.LangString;
import es.eucm.eadventure.adventureeditor.gui.TextConstants;

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
