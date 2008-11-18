package es.eucm.eadventure.editor.control.controllers.lom;

import es.eucm.eadventure.editor.data.lom.LOMEducational;

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
