/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
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
