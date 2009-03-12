package es.eucm.eadventure.editor.control.controllers.metadata.lomes;

import es.eucm.eadventure.editor.control.controllers.metadata.lomes.time.LOMESTimeDataControl;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESLifeCycleDate;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESEducational;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESLifeCycle;

public class LOMESDateDataControl extends LOMESTimeDataControl{

	private LOMESLifeCycleDate data;
	
	public LOMESDateDataControl (LOMESLifeCycleDate data){
		this.data = data;
		this.parseDuration( data.getDateTime() );
	}
	
	
	protected boolean setParameter(int param, String value){
		boolean set = super.setParameter( param, value );
		if (set)
			data.setDateTime( toString() );
		
		return set;
	}
	
	
}
