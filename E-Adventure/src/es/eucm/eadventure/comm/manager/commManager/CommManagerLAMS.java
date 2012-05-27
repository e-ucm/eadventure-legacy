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
package es.eucm.eadventure.comm.manager.commManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import es.eucm.eadventure.comm.AdventureApplet;
import es.eucm.eadventure.common.data.assessment.AssessmentProperty;
import es.eucm.eadventure.engine.adaptation.AdaptationEngine;


public class CommManagerLAMS extends AdventureApplet{


    public final static String BASE_URL = "eadventure/";
    
    public final static String REPORT = "/report";
    
    public final static String VAR = "/var";
    
    public final static String SPLIT = "eueadv10";
    
    /**
     * userID and runID are the id of the LAMS user and the toolSessionID of LAMS tool
     */

    private String appletURL;
    
    private String userFName;
    
    private String userLName;
    
    private ArrayList<AssessmentProperty> dataToSend;
    
    private boolean connect;


    /**
     * Read the default parameters from LAMS via JavaScript (due to synchronize with the begining of the applet)
     */
    public void setParams(String aplURL, String usrFN, String usrLN, String lamsUserID, String toolContentID){
       
        appletURL= aplURL;
        
        userFName = usrFN;
        
        userLName = usrLN;
        
       userId = lamsUserID;
       
       runId = toolContentID;
       
    }
 
    public void connectionEstablished( String serverComment ) {

        // TODO Auto-generated method stub
        
    }

    
    public void connectionFailed( String serverComment ) {

        // TODO Auto-generated method stub
        
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

    
    public boolean connect( HashMap<String, String> info ) {
        if (userId!=null&&runId!=null)
            connect=true;
        else 
            connect=false;
        
        return false;
    }
    

    
    public boolean disconnect( HashMap<String, String> info ) {

       
        return false;
    }

    
    public void getAdaptedState( Set<String> properties ) {

        // TODO get REST
        
    }

    
    public int getCommType( ) {
        return CommManagerApi.LAMS_TYPE;
    }

    
    public HashMap<String, String> getInitialStates( ) {

        // TODO Auto-generated method stub
        return null;
    }

    
    public boolean isConnected( ) {

        
        return connect;
    }
    
    public void sendHTMLReport(String report ) {
        sendData(REPORT,report);
    }

    private void sendData(String data, String varName){
        // Create the URL and URL Connection
        URL programUrl;
        try {
            programUrl = new URL( appletURL+BASE_URL+userId+"/"+runId+"/"+varName );
            System.out.println(  appletURL+BASE_URL+userId+"/" + runId+"/"+varName );
        HttpURLConnection connection = (HttpURLConnection)programUrl.openConnection();
                
        // send data
            (connection).setRequestMethod("POST");
            connection.setDoOutput(true);
            //connection.setDoInput(true); //Only if you expect to read a response...
            connection.setUseCaches(false); //Highly recommended...
            connection.setRequestProperty("Content-Type", "application/x-java-serialized-object");
          
            OutputStream outstream = connection.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outstream);
            oos.writeObject(data);
            oos.flush();
             oos.close();
             
            connection.connect();
            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine())!=null)
            {
                if (line.equals( "showButton" )){
                    System.out.println( "Server answers" );
                    String command = "javascript:showButton(\" \");";

                    this.sendJavaScript( command );
                }
            }
            
            br.close( );
     
        }
        catch( MalformedURLException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch( IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    

    
    public void notifyRelevantState( List<AssessmentProperty> list ) {

        if (dataToSend==null){
            dataToSend=new ArrayList<AssessmentProperty>();
        } 
        // store all rules to send when the game finish
        else{
            dataToSend.addAll( list );
        }
        Iterator<AssessmentProperty> it = list.iterator( );
        while( it.hasNext( ) ) {
            AssessmentProperty assessProp = it.next( );
            String attribute = assessProp.getId( );
            String value = String.valueOf( assessProp.getValue( ) );
            sendData(value,attribute);
            
        }
    }

    
    public void setAdaptationEngine( AdaptationEngine engine ) {

        // TODO Auto-generated method stub
        
    }

    
    /**
     * @return the appletURL
     */
    public String getAppletURL( ) {
    
        return appletURL;
    }

    /**
     * @param appletURL the appletURL to set
     */
    public void setAppletURL( String appletURL ) {
    
       // this.appletURL = appletURL;
        // take the part of the applet URL where the outputs will be sent (+ to obtain tool signature into result char)
        this.appletURL=appletURL.substring(0, appletURL.indexOf(SPLIT, 0)+SPLIT.length( )+1);
    }

    /**
     * @return the userFName
     */
    public String getUserFName( ) {
    
        return userFName;
    }
    
    /**
     * @param userFName the userFName to set
     */
    public void setUserFName( String userFName ) {
    
        this.userFName = userFName;
    }
    
    /**
     * @return the userLName
     */
    public String getUserLName( ) {
    
        return userLName;
    }
    
    /**
     * @param userLName the userLName to set
     */
    public void setUserLName( String userLName ) {
    
        this.userLName = userLName;
    }

}
