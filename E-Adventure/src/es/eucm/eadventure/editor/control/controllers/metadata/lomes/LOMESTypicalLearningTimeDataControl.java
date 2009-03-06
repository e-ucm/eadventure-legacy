package es.eucm.eadventure.editor.control.controllers.metadata.lomes;

import es.eucm.eadventure.editor.data.meta.ims.IMSEducational;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESEducational;

public class LOMESTypicalLearningTimeDataControl extends LOMESDurationDataControl{

	private LOMESEducational data;
	
	public LOMESTypicalLearningTimeDataControl (LOMESEducational data){
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
