package es.eucm.eadventure.comm.manager.commManager;

import java.applet.Applet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JApplet;

import es.eucm.eadventure.comm.AdventureApplet;
import es.eucm.eadventure.comm.CommException;
import es.eucm.eadventure.comm.CommListenerApi;

import es.eucm.eadventure.common.data.assessment.AssessmentProperty;
import es.eucm.eadventure.engine.adaptation.AdaptationEngine;

import netscape.javascript.JSObject;


public class CommManagerScormV12 extends AdventureApplet{
	
	private final String SEND_DIR = "cmi.interactions";

	private boolean connected;
	
	private boolean lock;

	
	private HashMap<String,String> adaptedStates;
	private int index;
	
	private AdaptationEngine adaptationEngine;

	/**
	 * The relations between the operation and where is store in "valuesFromLms"
	 */
	private HashMap<String,String> valuesFromLMS;

	
	public CommManagerScormV12(){
		valuesFromLMS = new HashMap<String,String>();
		connected = false;
		adaptedStates = new HashMap<String,String>();
		index=0;
		lock = false;
	}
	
	 
	/*public void addListener(CommListenerApi commListener) {
		// TODO Auto-generated method stub
		
	}*/

	/**
	 * 
	 */
	public boolean connect(HashMap<String, String> info) {
		
	        String command = "javascript:connect(\" \");";
	        
	        this.sendJavaScript(command);
	        
	      //TODO no se xk hay que devolver algo aki, ver si se cambia!!!
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

	public void getAllObjetives() {
		
		String command = "javascript:getLMSData(\"cmi.objetives._count\");";
		lock = true;
        this.sendJavaScript(command);
		
        adaptedStates = new HashMap<String,String>();
        
        waitResponse();
        
        String value = valuesFromLMS.get("cmi.objetives._count");
    
        if (!lock){
        int count = Integer.valueOf(value);
        if (count !=0){
		for (int i = 0; i < count; i++ ){
			command = "javascript:getLMSData(\"cmi.objetives."+ String.valueOf(i) + ".id\");";
			this.sendJavaScript(command);
		}
		int milis =0;
		while (valuesFromLMS.get("cmi.objetives"+String.valueOf(count)+".id") == null){
			try {
				Thread.sleep(500);
				milis+=500;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (milis >= 30000){
				break;
			}
		}
		
       // ArrayList<String> temp = 
        for (int i=0; i<count;i++){
        	String aux = valuesFromLMS.get("cmi.objetives"+String.valueOf(i)+".id");
        	if (aux!=null)
        	adaptedStates.put(aux,"1");
        }
        
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
	}

	public void dataReceived(String key, String value) {
		
		System.out.println("Esto es lo que nos ha devuelto el LMS: "+ value);
		//return data;
		valuesFromLMS.put(key, value);
		//lock=false;
		
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
		
		/*getFromLMS(SEND_DIR+"._count");
		
		String value = valuesFromLMS.get(SEND_DIR+"._count");
		int count = Integer.valueOf(value);
		
		for (int i=0; i< list.size(); i++){
			
			if (i<count){
				
				value = "id:"+list.get(i).getId() + " value:" + list.get(i).getValue();
				String command = "javascript:setLMSData(\""+ SEND_DIR + String.valueOf(i) +".student_response \", \"" + value + "\");";
				this.sendJavaScript(command);
				
			} else {
				//TODO situacion de error
			}
		}*/
		System.out.println("Entramos en notify relevant state");
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
		//getAllObjetives();
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
