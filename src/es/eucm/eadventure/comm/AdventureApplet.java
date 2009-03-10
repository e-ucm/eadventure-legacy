package es.eucm.eadventure.comm;

import javax.swing.JApplet;

import es.eucm.eadventure.comm.manager.commManager.CommManagerApi;
import es.eucm.eadventure.comm.manager.commManager.LMStoComInterface;

import netscape.javascript.JSObject;

public abstract class AdventureApplet extends JApplet implements LMStoComInterface, CommManagerApi{
	
	private static final long serialVersionUID = 3561527305650676509L;
	
	/**
	 * User ID for the applet
	 */
	protected String userId;
	
	/**
	 * Run ID for the applet
	 */
	protected String runId;
	
	/**
	 * True if the applet runs in a new window
	 */
	protected boolean windowed;
	
	/**
	 * Read the default parameters for the applet
	 */
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

	/**
	 * Send a command through java script
	 * 
	 * @param command The command
	 */
	public void sendJavaScript(String command){
		JSObject win = null;
        try {
            System.out.println("Establishing communications...");
            System.out.println( "Issuing command: " + command );
            win = (JSObject) JSObject.getWindow( this );
            win.eval( command );
        } catch( Exception e ) {
        	System.out.println("Error sending command " + command);
        }
	}
}
