package es.eucm.eadventure.comm.manager.commManager;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import es.eucm.eadventure.common.data.assessment.AssessmentProperty;

public interface LMStoComInterface {
	
	public void connectionEstablished(String serverComment);
	
	public void connectionFailed(String serverComment);
	
	public void dataReceived(String key, String value);
	
	public void dataSendingOK(String serverComment);
	
	public void dataSendingFailed(String serverComment);
	
	public void notifyRelevantState(List<AssessmentProperty> list);
	
	public void getAdaptedState( Set<String> properties );
	
	public HashMap<String,String> getInitialStates();

}
