package es.eucm.eadventure.editor.control.controllers.ims;

import es.eucm.eadventure.editor.data.ims.IMSEducational;
import es.eucm.eadventure.editor.data.lom.LOMEducational;

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
