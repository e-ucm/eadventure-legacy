/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
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
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.comm.manager.commManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import netscape.javascript.JSObject;
import es.eucm.eadventure.comm.AdventureApplet;
import es.eucm.eadventure.common.data.assessment.AssessmentProperty;
import es.eucm.eadventure.engine.adaptation.AdaptationEngine;

public class CommManagerLD extends AdventureApplet {

    private static final long serialVersionUID = -2750949905060993695L;

    private boolean commWorking = false;

    private AdaptationEngine adaptationEngine;

    private String propertyNames = null;

    public void connectionEstablished( String serverComment ) {

        System.out.println( "OK Notification received: " + serverComment );
        this.commWorking = true;

    }

    public void connectionFailed( String serverComment ) {

        System.out.println( "No communication link available: " + serverComment );
        this.commWorking = false;

    }

    public void dataFromLMS( String key, String value ) {

        // TODO Auto-generated method stub

    }

    public void dataSendingFailed( String serverComment ) {

        // TODO Auto-generated method stub

    }

    public void dataSendingOK( String serverComment ) {

        // TODO Auto-generated method stub

    }

    public void getAdaptedState( Set<String> properties ) {

        //Generate the encoded string and maintain it cached
        if( propertyNames == null ) {
            StringBuffer sb = new StringBuffer( "" );
            for( String property : properties ) {
                sb.append( property );
                sb.append( "," );
            }
            propertyNames = sb.toString( );
            if( propertyNames.endsWith( "," ) ) {
                propertyNames = propertyNames.substring( 0, propertyNames.length( ) - 1 );
            }
        }

        //Don't bother the server if there are no external properties to import
        if( !propertyNames.equals( "" ) ) {
            JSObject win = null;
            String command = "javascript:getState(\"" + userId + "," + runId + "#" + propertyNames + "\");";
            try {
                System.out.println( "Retrieving properties..." );
                System.out.println( "Issuing command: " + command );
                win = JSObject.getWindow( this );
                win.eval( command );
            }
            catch( Exception e ) {
            }
        }

    }

    public HashMap<String, String> getInitialStates( ) {

        // TODO Auto-generated method stub
        return null;
    }

    public void notifyRelevantState( List<AssessmentProperty> list ) {

        //Generate the encoded string
        StringBuffer sb = new StringBuffer( "" );
        for( AssessmentProperty ap : list ) {
            sb.append( ap.getId( ) );
            sb.append( "=" );
            sb.append( ap.getValue( ) );
            sb.append( "," );
        }
        String encodedProperties = sb.toString( );
        if( encodedProperties.endsWith( "," ) ) {
            encodedProperties = encodedProperties.substring( 0, encodedProperties.length( ) - 1 );
        }

        //Don't bother the server if there are no properties to report
        if( !encodedProperties.equals( "" ) ) {
            JSObject win = null;
            String command = "javascript:notifyRelevantState(\"" + userId + "," + runId + "#" + encodedProperties + "\");";
            try {
                System.out.println( "Notifying new state..." );
                System.out.println( "Issuing command: " + command );
                win = JSObject.getWindow( this );
                win.eval( command );
            }
            catch( Exception e ) {
            }
        }

    }

    public boolean connect( HashMap<String, String> info ) {

        String command = "javascript:startCommunication(\"user_id:" + userId + ";run_id" + runId + "\");";
        this.sendJavaScript( command );
        return false;
    }

    public boolean disconnect( HashMap<String, String> info ) {

        // TODO Auto-generated method stub
        return false;
    }

    public int getCommType( ) {

        return LD_ENVIROMENT_TYPE;
    }

    public boolean isConnected( ) {

        return commWorking;
    }

    public void newState( String newState ) {

        System.out.println( "The server answers with a new State: " + newState );
        Map<String, String> decodedState = new HashMap<String, String>( );

        String[] properties = newState.split( "," );

        for( String property : properties ) {
            String[] aux = property.split( "=" );
            decodedState.put( aux[0], aux[1] );
        }

        adaptationEngine.processExternalState( decodedState );

    }

    public void setAdaptationEngine( AdaptationEngine engine ) {

        this.adaptationEngine = engine;
    }

    public void sendHTMLReport( String report ) {

        // TODO Auto-generated method stub
        
    }
}
