package es.eucm.eadventure.editor.control.controllers.metadata.ims;

import es.eucm.eadventure.editor.data.meta.ims.IMSEducational;

public class IMSTypicalLearningTimeDataControl extends IMSDurationDataControl{

	private IMSEducational data;
	
	public IMSTypicalLearningTimeDataControl (IMSEducational data){
		this.data = data;
		this.parseDuration( data.getTypicalLearningTime( ) );
	}
	
	
	protected boolean setParameter(int param, String value){
		boolean set = super.setParameter( param, value );
		if (set)
			data.setTypicalLearningTime( toString() );
		
		return set;
	}
	
}
