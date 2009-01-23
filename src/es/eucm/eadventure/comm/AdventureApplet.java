package es.eucm.eadventure.comm;

import javax.swing.JApplet;

import es.eucm.eadventure.comm.manager.commManager.CommManagerApi;
import es.eucm.eadventure.comm.manager.commManager.LMStoComInterface;

import netscape.javascript.JSObject;

public abstract class AdventureApplet extends JApplet implements LMStoComInterface, CommManagerApi{
	
	
	protected String userId;
	protected String runId;
	protected boolean windowed;
	
	
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


	
	
	
	
	public void sendJavaScript(String command){
		JSObject win = null;
        try {
            System.out.println("Establishing communications...");
            System.out.println( "Issuing command: " + command );
            win = (JSObject) JSObject.getWindow( this );
            win.eval( command );
        } catch( Exception e ) {
        }
	}
}
