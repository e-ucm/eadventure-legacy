package es.eucm.eadventure.comm.manager.commManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import es.eucm.eadventure.comm.AdventureApplet;

import es.eucm.eadventure.common.data.assessment.AssessmentProperty;
import es.eucm.eadventure.engine.adaptation.AdaptationEngine;


public class CommManagerScorm extends AdventureApplet{
	
	private static final long serialVersionUID = 3444828969384528659L;

	private boolean connected;
	
	private boolean lock;
	
	private HashMap<String,String> adaptedStates;

	private int index;
	
	private AdaptationEngine adaptationEngine;

	/**
	 * The relations between the operation and where is store in "valuesFromLms"
	 */
	private HashMap<String,String> valuesFromLMS;

	
	public CommManagerScorm(){
		valuesFromLMS = new HashMap<String,String>();
		connected = false;
		adaptedStates = new HashMap<String,String>();
		index=0;
		lock = false;
	}
	
	 
	

	/**
	 * 
	 */
	public boolean connect(HashMap<String, String> info) {
		
	        String command = "javascript:connect(\" \");";
	        
	        this.sendJavaScript(command);
	        
		return false;
	}

	/**
	 * 
	 */
	public boolean disconnect(HashMap<String, String> info){
		
        String command = "javascript:disconnect(\" \");";
        
        this.sendJavaScript(command);
        
        this.connected = false;
     
		return false;
	}
	
	public void disconnectOK(){
		this.connected = false;
	}
	
	
	private void waitResponse(){
	    int milis = 0;
        while (lock){
        	// If its was waiting 10 seconds, abort
        	if (milis >= 10000)
        		break;
        	try {
				Thread.sleep(500);
				milis+=500;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        
	}

	public int getCommType() {
		
		return CommManagerApi.SCORMV12_TYPE;
	}

	public void connectionEstablished(String serverComment) {
		
		connected = true;
		System.out.println(serverComment);
		
	}

	public void connectionFailed(String serverComment) {
		System.out.println(serverComment);	
		connected = false;
	}

	public void dataFromLMS(String key, String value) {
		
		//System.out.println("Esto es lo que nos ha devuelto el LMS: "+ value);

		if (value==null)
			value = new String("");
		valuesFromLMS.put(key, value);
	
		
	}

	public void dataSendingFailed(String serverComment) {
		
		System.out.println(serverComment);
		
	}

	public void dataSendingOK(String serverComment) {
		
		System.out.println(serverComment);
	}
	
	
	public void getFromLMS(String attribute){
		
		String command = "javascript:getLMSData(\""+ attribute + "\");";
        
		this.sendJavaScript(command);
	}

	public void notifyRelevantState( List<AssessmentProperty> list) {
		
		//System.out.println("Entramos en notify relevant state");
		Iterator<AssessmentProperty> it = list.iterator();
		while (it.hasNext()){
			AssessmentProperty assessProp = it.next();
			String attribute = assessProp.getId();
			String value = String.valueOf(assessProp.getValue());
			String command = "javascript:setLMSData(\""+ attribute + "\", \"" + value + "\");";
			this.sendJavaScript(command);
			String command2 = "javascript:commit(\"\");";
			this.sendJavaScript(command2);
		}
		
		
	}

	public boolean isConnected() {
		return connected;
	}


	public HashMap<String,String> getInitialStates() {
		waitResponse();
		
		return valuesFromLMS;
	}
	

	public void getAdaptedState(Set<String> properties) {
		lock = true;
		for (String rule:properties){
			getFromLMS(rule);
		}
		lock = false;
	}

	public void setAdaptationEngine(AdaptationEngine adaptationEngine) {
		this.adaptationEngine = adaptationEngine;
	}
	
}
