package es.eucm.eadventure.comm;

import java.applet.Applet;
import java.util.HashMap;

import javax.swing.JApplet;

import netscape.javascript.JSObject;


public class CommManagerScormV12 extends JApplet implements CommManagerApi{

	protected String userId;
	protected String runId;
	protected boolean windowed;
	
	@Override

	public void addListener(CommListenerApi commListener) {
		// TODO Auto-generated method stub
		
	}

    public void readParameters() {

        userId = getParameter("USER_ID");
        if(userId == null) {
            userId = "";
            System.out.println("No UserId param available");
        }
        
        runId = getParameter("RUN_ID");
        if(runId == null) {
            runId = "";
            System.out.println("No RunId param available");
        }
        
        String temp = getParameter("WINDOWED");
        if(temp != null && temp.equalsIgnoreCase("yes"))
        		windowed = true;
        
    }

	public boolean connect(HashMap<String, String> info) throws CommException {
		  JSObject win = null;
	        String command = "javascript:startCommunication(\" \");";
	        try {
	            System.out.println("Establishing communications...");
	            System.out.println( "Issuing command: " + command );
	            win = (JSObject) JSObject.getWindow( this );
	            win.eval( command );
	        } catch( Exception e ) {
	            //e.printStackTrace();
	        }
	      //TODO no se xk hay que devolver algo aki, ver si se cambia!!!
		return false;
	}

	public boolean disconnect(HashMap<String, String> info)
			throws CommException {
		JSObject win = null;
        String command = "javascript:endCommunication(\" \");";
        try {
            System.out.println("Establishing communications...");
            System.out.println( "Issuing command: " + command );
            win = (JSObject) JSObject.getWindow( this );
            win.eval( command );
        } catch( Exception e ) {
            //e.printStackTrace();
        }
      //TODO no se xk hay que devolver algo aki, ver si se cambia!!!
		return false;
	}

	public boolean getMessage(String attribute){
		JSObject win = null;
        String command = "javascript:getMessage(\""+ attribute + "\");";
        try {
            System.out.println("Establishing communications...");
            System.out.println( "Issuing command: " + command );
            win = (JSObject) JSObject.getWindow( this );
            win.eval( command );
        } catch( Exception e ) {
            //e.printStackTrace();
        }
		return false;
	}
	
	
	public boolean sendMessage(String attribute, String value)
			throws CommException {
		
		JSObject win = null;
        String command = "javascript:setSomething(\""+ attribute + "\", \"" + value + "\");";
        try {
            System.out.println("Establishing communications...");
            System.out.println( "Issuing command: " + command );
            win = (JSObject) JSObject.getWindow( this );
            win.eval( command );
        } catch( Exception e ) {
            //e.printStackTrace();
        }
		return false;
	}

	public boolean getLMSData(String data){
	
		System.out.println("Esto es lo que nos ha devuelto el LMS: "+ data );
		return false;
	}
	
}
