package es.eucm.eadventure.editor.control.controllers.lom;

import es.eucm.eadventure.editor.data.lom.LOMTechnical;

public class LOMTechnicalDataControl {

	private LOMTechnical data;
	
	public LOMTechnicalDataControl (LOMTechnical data){
		this.data = data;
	}
	
	public LOMTextDataControl getMaximumVersionController (){
		return new LOMTextDataControl (){

			public String getText( ) {
				return data.getMaximumVersion( );
			}

			public void setText( String text ) {
				data.setMaximumVersion( text );
			}
			
		};
	}
	
	public LOMTextDataControl getMinimumVersionController (){
		return new LOMTextDataControl (){

			public String getText( ) {
				return data.getMinimumVersion( );
			}

			public void setText( String text ) {
				data.setMinimumVersion( text );
			}
			
		};
	}
	
	
	/**
	 * @return the data
	 */
	public LOMTechnical getData( ) {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData( LOMTechnical data ) {
		this.data = data;
	}

}
