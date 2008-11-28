package es.eucm.eadventure.comm;

import java.util.HashMap;


public class CommManager implements CommManagerApi{

	@Override
	public void addListener(CommListenerApi commListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean connect(HashMap<String, String> info) throws CommException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean disconnect(HashMap<String, String> info)
			throws CommException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sendMessage(String attribute, String value)
			throws CommException {
		// TODO Auto-generated method stub
		return false;
	}

}
