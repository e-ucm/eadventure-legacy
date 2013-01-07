/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 * research group.
 * 
 * Copyright 2005-2012 <e-UCM> research group.
 * 
 * <e-UCM> is a research group of the Department of Software Engineering and
 * Artificial Intelligence at the Complutense University of Madrid (School of
 * Computer Science).
 * 
 * C Profesor Jose Garcia Santesmases sn, 28040 Madrid (Madrid), Spain.
 * 
 * For more info please visit: <http://e-adventure.e-ucm.es> or
 * <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * This file is part of <e-Adventure>, version 1.4.
 * 
 * You can access a list of all the contributors to <e-Adventure> at:
 * http://e-adventure.e-ucm.es/contributors
 * 
 * ****************************************************************************
 * <e-Adventure> is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with <e-Adventure>. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package es.eucm.eadventure.tracking.prv.gleaner;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

import es.eucm.eadventure.tracking.prv.GameLogEntry;
import es.eucm.eadventure.tracking.prv.service.GameLogConsumer;
import es.eucm.eadventure.tracking.prv.service.ServiceConstArgs;

/**
 * 
 * Gleaner tracker
 * 
 */
public class GleanerLogConsumer extends GameLogConsumer {

    private static final String START = "start/";

    private static final String TRACK = "track";

    private Gson gson;

    private HttpClient client;

    private HttpPost trackPost;

    private HttpGet startGet;

    private String url;

    private String gamekey;

    private String userId;

    private int retry = 5;

    private boolean connected;

    private List<Map<String, Object>> traces;

    private String currentPhase;

    private Map<String, Integer> vars;

    public GleanerLogConsumer( ServiceConstArgs args ) {

        super( args );
        userId = args.config.getStudentId( );
        gamekey = args.config.getGameId( );
        url =  args.serviceConfig.getUrl( );
    }

    @Override
    protected void consumerInit( ) {

        gson = new Gson( );
        traces = new ArrayList<Map<String, Object>>( );
        client = new DefaultHttpClient( );
        vars = new HashMap<String, Integer>( );

        startGet = new HttpGet( url + START + gamekey );
        startGet.setHeader( "Authorization", userId );

        connect( );
    }

    @Override
    protected boolean consumerCode( List<GameLogEntry> newQ ) {

        if( !connected && retry > 0 ) {
            connect( );
        }

        if( connected ) {
            traces.clear( );
            for( GameLogEntry entry : newQ ) {
                traces.add( convert( entry ) );
            }
            return sendTraces( );
        }
        else {
            return false;
        }
    }

    private boolean sendTraces( ) {

        String jsonTraces = gson.toJson( traces );
        traces.clear( );
        try {
            trackPost.setEntity( new StringEntity( jsonTraces ) );
            HttpResponse response = client.execute( trackPost );
            return response.getStatusLine( ).getStatusCode( ) == 204;
        }
        catch( UnsupportedEncodingException e ) {
            return false;
        }
        catch( ClientProtocolException e ) {
            return false;
        }
        catch( IOException e ) {
            return false;
        }
    }

    @Override
    protected boolean consumerClose( List<GameLogEntry> newQ ) {

        consumerCode( newQ );
        return false;
    }

    private void connect( ) {

        InputStreamReader is = null;
        try {

            HttpResponse response = client.execute( startGet );

            if( response.getStatusLine( ).getStatusCode( ) == 200 ) {
                is = new InputStreamReader( response.getEntity( ).getContent( ) );
                SessionToken token = gson.fromJson( is, SessionToken.class );
                if( token != null && token.sessionToken != null ) {
                    trackPost = new HttpPost( url + TRACK );
                    trackPost.setHeader( "Content-Type", "application/json" );
                    trackPost.setHeader( "Authorization", token.sessionToken );
                    connected = true;
                }
                else {
                    retry--;
                }
            }
            else {
                retry--;
            }
        }
        catch( ClientProtocolException e ) {
            retry--;
        }
        catch( IOException e ) {
            retry--;
        }
        finally {
            if( is != null ) {
                try {
                    is.close( );
                }
                catch( IOException e ) {

                }
            }
        }
    }

    private Map<String, Object> convert( GameLogEntry entry ) {

        LinkedHashMap<String, Object> trace = new LinkedHashMap<String, Object>( );
        LinkedHashMap<String, Object> data = new LinkedHashMap<String, Object>( );

        trace.put( "timeStamp", entry.getAttributeValue( "ms" ) );
        // High level
        if( entry.getElementName( ).equals( "h" ) ) {

            trace.put( "type", "logic" );

            if( entry.getAttributeValue( "o" ) != null ) {
                trace.put( "target", entry.getAttributeValue( "o" ) );
            }

            if( entry.getAttributeValue( "t" ) != null ) {
                if( trace.containsKey( "t" ) ) {
                    data.put( "target", entry.getAttributeValue( "t" ) );
                }
                else {
                    trace.put( "target", entry.getAttributeValue( "t" ) );
                }
            }

            if( "scn".equals( entry.getAttributeValue( "a" ) ) ) {
                if( currentPhase != null ) {
                    // Send phase_end trace
                    LinkedHashMap<String, Object> endPhaseTrace = new LinkedHashMap<String, Object>( );
                    endPhaseTrace.put( "type", "logic" );
                    endPhaseTrace.put( "timeStamp", entry.getAttributeValue( "ms" ) );
                    endPhaseTrace.put( "event", "phase_end" );
                    endPhaseTrace.put( "target", currentPhase );
                    traces.add( endPhaseTrace );
                    // Set phase_start trace
                    trace.put( "event", "phase_start" );
                    this.currentPhase = trace.get( "target" ).toString( );

                }
            }
            else if( entry.getAttributeValue( "a" ) != null ) {
                trace.put( "event", entry.getAttributeValue( "a" ) );
            }
            else if( entry.getAttributeValue( "e" ) != null ) {
                // Activate / Deactive flag
                String attValue = entry.getAttributeValue( "e" );
                trace.put( "event", attValue );
                boolean varUpdate = false;
                boolean value = false;
                if( attValue.equals( "act" ) ) {
                    trace.put( "event", "var_update" );
                    varUpdate = true;
                    value = true;
                }
                else if( attValue.equals( "dct" ) ) {
                    trace.put( "event", "var_update" );
                    varUpdate = true;
                    value = false;
                }

                if( varUpdate ) {
                    data.put( "value", value );
                    data.put( "operator", attValue );
                }
            }

            // Change var value
            if( entry.getAttributeValue( "v" ) != null ) {
                Integer value = Integer.parseInt( entry.getAttributeValue( "v" ) );
                data.put( "operand", value );
                String operator = entry.getAttributeValue( "e" );
                data.put( "operator", operator );
                trace.put( "event", "var_update" );
                String varName = trace.get( "target" ).toString( );

                if( !vars.containsKey( trace.get( "target" ) ) ) {
                    vars.put( varName, 0 );
                }

                if( operator.equals( "set" ) ) {
                    vars.put( varName, value );
                }
                else if( operator.equals( "inc" ) ) {
                    vars.put( varName, value + vars.get( varName ) );
                }
                else if( operator.equals( "dec" ) ) {
                    vars.put( varName, value - vars.get( varName ) );
                }

                data.put( "value", vars.get( varName ) );
            }

            // Other data
            if( entry.getAttributeValue( "ix" ) != null ) {
                data.put( "ix", Integer.parseInt( entry.getAttributeValue( "ix" ) ) );
            }
            if( entry.getAttributeValue( "iy" ) != null ) {
                data.put( "iy", Integer.parseInt( entry.getAttributeValue( "iy" ) ) );
            }
            if( entry.getAttributeValue( "x" ) != null ) {
                data.put( "x", Integer.parseInt( entry.getAttributeValue( "x" ) ) );
            }
            if( entry.getAttributeValue( "y" ) != null ) {
                data.put( "y", Integer.parseInt( entry.getAttributeValue( "y" ) ) );
            }
            if( entry.getAttributeValue( "dx" ) != null ) {
                data.put( "dx", Integer.parseInt( entry.getAttributeValue( "dx" ) ) );
            }
            if( entry.getAttributeValue( "dy" ) != null ) {
                data.put( "dy", Integer.parseInt( entry.getAttributeValue( "dy" ) ) );
            }
            if( entry.getAttributeValue( "l" ) != null ) {
                data.put( "l", entry.getAttributeValue( "l" ) );
            }
        }
        // Low level
        else if( entry.getElementName( ).equals( "l" ) ) {
            trace.put( "type", "input" );
            String type = entry.getAttributeValue( "m" );
            String action = entry.getAttributeValue( "i" );
            if ( "m".equals( type )){
                trace.put( "device", "mouse" );
                data.put( "count", Integer.parseInt( entry.getAttributeValue( "c" ) ) );
                data.put( "button", Integer.parseInt( entry.getAttributeValue( "b" ) ) );
                String offset = entry.getAttributeValue( "off" );
                if ( offset != null ){
                    data.put( "offset", Integer.parseInt( offset ) );
                }
            }
            else if ( "k".equals( type )){
                trace.put( "device", "keyboard" );                
                if ( "t".equals( action )){
                    data.put("ch", entry.getAttributeValue( "k" ) );
                }
                else {
                    data.put( "keyCode", Integer.parseInt( entry.getAttributeValue( "c" )  ));
                }
            }
            
            // Action
            if ( "m".equals( action )){
                trace.put( "action", "move" );
            }
            else if ( "p".equals( action )){
                trace.put( "action", "press" );
            }
            else if ( "r".equals( action )){
                trace.put( "action", "release" );
            }
            else if ( "c".equals( action )){
                trace.put( "action", "click" );
            }
            else if ( "en".equals( action )){
                trace.put( "action", "enter" );            }
            else if ( "d".equals( action )){
                trace.put( "action", "drag" );
            }
            else if ( "ex".equals( action )){
                trace.put( "action", "exit" );
            }
            else if ( "t".equals( action )){
                trace.put( "action", "type" );
            }
            
            if ( entry.getAttributeValue( "m" ) != null ){
                data.put( "modifiers", entry.getAttributeValue( "m" ) );
            }
            
            if ( entry.getAttributeValue( "x" ) != null ){
                data.put( "x", Integer.parseInt( entry.getAttributeValue( "x" ) ) );
            }
            
            if ( entry.getAttributeValue( "y" ) != null ){
                data.put( "y", Integer.parseInt( entry.getAttributeValue( "y" ) ) );
            }
        }
        
        
        if( !data.isEmpty( ) ) {
            trace.put( "data", data );
        }
        return trace;

    }

    private class SessionToken {

        public String sessionToken;
    }
}
