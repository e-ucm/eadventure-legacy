package es.eucm.eadventure.editor.control.controllers.metadata.lom;

import es.eucm.eadventure.editor.data.meta.lom.LOMEducational;
import es.eucm.eadventure.editor.control.config.LOMConfigData;

public class LOMTypicalLearningTimeDataControl extends LOMDurationDataControl{

	private LOMEducational data;
	
	public LOMTypicalLearningTimeDataControl (LOMEducational data){
		this.data = data;
		this.parseDuration( data.getTypicalLearningTime( ) );
	}
	
	
	protected boolean setParameter(int param, String value){
		boolean set = super.setParameter( param, value );
		if (set){
			data.setTypicalLearningTime( toString() );
			LOMConfigData.storeData(LOMEducationalDataControl.GROUP, "typicalLearningTime", toString());
		}
		return set;
	}
	
}
