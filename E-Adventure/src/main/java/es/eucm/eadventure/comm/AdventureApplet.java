/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.comm;

import javax.swing.JApplet;

import netscape.javascript.JSObject;
import es.eucm.eadventure.comm.manager.commManager.CommManagerApi;
import es.eucm.eadventure.comm.manager.commManager.LMStoComInterface;

public abstract class AdventureApplet extends JApplet implements LMStoComInterface, CommManagerApi {

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
    public void readParameters( ) {

        userId = getParameter( "USER_ID" );
        if( userId == null ) {
            userId = "";
            System.out.println( "No UserId param available" );
        }

        runId = getParameter( "RUN_ID" );
        if( runId == null ) {
            runId = "";
            System.out.println( "No RunId param available" );
        }

        String temp = getParameter( "WINDOWED" );
        if( temp != null && temp.equalsIgnoreCase( "yes" ) )
            windowed = true;
    }

    /**
     * Send a command through java script
     * 
     * @param command
     *            The command
     */
    public void sendJavaScript( String command ) {

        JSObject win = null;
        try {
            System.out.println( "Establishing communications..." );
            System.out.println( "Issuing command: " + command );
            win = JSObject.getWindow( this );
            win.eval( command );
        }
        catch( Exception e ) {
            System.out.println( "Error sending command " + command );
        }
    }

    
    /**
     * @param userId the userId to set
     */
    public void setUserId( String userId ) {
    
        this.userId = userId;
        System.out.println( userId );
    }

    
    /**
     * @param runId the runId to set
     */
    public void setRunId( String runId ) {
    
        this.runId = runId;
        System.out.println( runId );
    }
}
