package es.eucm.eadventure.comm.manager.commManager;

import java.util.HashMap;

import javax.swing.JApplet;

import netscape.javascript.JSObject;

import es.eucm.eadventure.comm.CommException;
import es.eucm.eadventure.comm.CommListenerApi;
import es.eucm.eadventure.engine.adaptation.AdaptationEngine;



public interface CommManagerApi {

	
	public static final int SCORMV12_TYPE = 0;
	
	public static final int SCORMV2004_TYPE = 1;
	
	public static final int LD_ENVIROMENT_TYPE = 2;
	
	public boolean connect(HashMap<String,String> info); //throws CommException;
	
	public boolean disconnect(HashMap<String,String> info); //throws CommException;

	//public boolean sendMessage(String attribute, String value) throws CommException;
	
	//public abstract void addListener(CommListenerApi commListener);
	
	//public String[] getAllObjetives();
	
	public boolean isConnected();
	
	public void setAdaptationEngine(AdaptationEngine engine);
	
	public int getCommType();
	
	
}
