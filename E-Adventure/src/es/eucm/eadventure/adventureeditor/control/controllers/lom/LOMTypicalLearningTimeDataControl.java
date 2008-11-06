package es.eucm.eadventure.adventureeditor.control.controllers.lom;

import es.eucm.eadventure.adventureeditor.data.lomdata.LOMEducational;

public class LOMTypicalLearningTimeDataControl extends LOMDurationDataControl{

	private LOMEducational data;
	
	public LOMTypicalLearningTimeDataControl (LOMEducational data){
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
