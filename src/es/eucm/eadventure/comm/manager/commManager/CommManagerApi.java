package es.eucm.eadventure.comm.manager.commManager;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import es.eucm.eadventure.common.data.assessment.AssessmentProperty;
import es.eucm.eadventure.engine.adaptation.AdaptationEngine;

public interface CommManagerApi {

	
	public static final int SCORMV12_TYPE = 0;
	
	public static final int SCORMV2004_TYPE = 1;
	
	public static final int LD_ENVIROMENT_TYPE = 2;
	
	public boolean connect(HashMap<String,String> info); //throws CommException;
	
	public boolean disconnect(HashMap<String,String> info); //throws CommException;
	
	public void notifyRelevantState(List<AssessmentProperty> list);
	
	public boolean isConnected();
	
	public void setAdaptationEngine(AdaptationEngine engine);
	
	public int getCommType();
	
	public void getAdaptedState( Set<String> properties );
	
	
	public HashMap<String,String> getInitialStates();
	
	
}
