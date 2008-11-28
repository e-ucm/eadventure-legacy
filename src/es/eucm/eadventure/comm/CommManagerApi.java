package es.eucm.eadventure.comm;

import java.util.HashMap;



public interface CommManagerApi {

	
	public boolean connect(HashMap<String,String> info) throws CommException;
	
	public boolean disconnect(HashMap<String,String> info) throws CommException;

	public boolean sendMessage(String attribute, String value) throws CommException;
	
	public void addListener(CommListenerApi commListener);
}
