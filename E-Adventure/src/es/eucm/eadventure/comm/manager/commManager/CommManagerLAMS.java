/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
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
    //TODO delete!
    public void setParams(String aplURL, String usrFN, String usrLN, String lamsUserID, String toolContentID){
        System.out.println( "se ejecuta" );
        
        appletURL= aplURL;
        
        userFName = usrFN;
        
        userLName = usrLN;
        
       userId = lamsUserID;
       
       runId = toolContentID;
       
       System.out.println( appletURL);
       
       System.out.println( userFName );
       
       System.out.println( userLName );
       
       System.out.println( userId  );
       
       System.out.println( runId);
        
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


    
    private void teCallas(){
        // Create the URL and URL Connection
        URL programUrl;
        try {
            
            //programUrl = new URL( appletURL+BASE_URL+userId+"/"+runId+ "/"+ varName);
            programUrl = new URL( appletURL+BASE_URL);
            System.out.println(appletURL+BASE_URL);
            //System.out.println(appletURL+BASE_URL+userId+"/"+runId+ "/"+ varName);
        HttpURLConnection connection = (HttpURLConnection)programUrl.openConnection();
                
        (connection).setRequestMethod("POST");
        connection.setDoOutput(true);
        //connection.setDoInput(true); //Only if you expect to read a response...
        connection.setUseCaches(false); //Highly recommended...
        connection.setRequestProperty("Content-Type", "application/x-java-serialized-object");
        
/*        PrintWriter output = new PrintWriter(new OutputStreamWriter(connection.getOutputStream()));
        output.println(data);
        output.flush();
        output.close(); */
     
       OutputStream outstream = connection.getOutputStream();
       ObjectOutputStream oos = new ObjectOutputStream(outstream);
       oos.writeObject(/*data*/"descomentgar");
       oos.flush();
        oos.close();
        
        
        //connection.connect();

        //InputStream is = connection.getInputStream();
        //InputStreamReader isr = new InputStreamReader(is);
        //BufferedReader br = new BufferedReader(isr);
     
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
    
    
    
    private void sendData(String data, String varName){
        // Create the URL and URL Connection
        URL programUrl;
        try {
            programUrl = new URL( appletURL+BASE_URL+userId+"/"+runId+"/"+varName );
            System.out.println(  appletURL+BASE_URL+userId+"/"+runId+REPORT + runId+"/"+varName );
        HttpURLConnection connection = (HttpURLConnection)programUrl.openConnection();
                
        // send report
        
           
            (connection).setRequestMethod("POST");
            connection.setDoOutput(true);
            //connection.setDoInput(true); //Only if you expect to read a response...
            connection.setUseCaches(false); //Highly recommended...
            connection.setRequestProperty("Content-Type", "application/x-java-serialized-object");
            
            //PrintWriter output = new PrintWriter(new OutputStreamWriter(connection.getOutputStream()));
            //output.println(data);
            //output.flush();
            //output.close(); 
            
            OutputStream outstream = connection.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outstream);
            oos.writeObject(data);
            oos.flush();
             oos.close();
          
            
            
         
            connection.connect();
           //TODO ver que pasa si se quita
            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine())!=null)
            {
                if (line.equals( "showButton" )){
                    System.out.println( "Rebibido OK respuesta del servlet!!" );
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


    
    public void sendHTMLReport(String report ) {
        // Create the URL and URL Connection
        URL programUrl;
        try {
            programUrl = new URL( appletURL+BASE_URL+userId+"/"+runId+REPORT );
            System.out.println(  appletURL+BASE_URL+userId+"/"+runId+REPORT );
        HttpURLConnection connection = (HttpURLConnection)programUrl.openConnection();
        // send report
        
        
        (connection).setRequestMethod("POST");
        connection.setDoOutput(true);
        //connection.setDoInput(true); //Only if you expect to read a response...
        connection.setUseCaches(false); //Highly recommended...
        connection.setRequestProperty("Content-Type", "application/x-java-serialized-object");
        
        //PrintWriter output = new PrintWriter(new OutputStreamWriter(connection.getOutputStream()));
        //output.println(data);
        //output.flush();
        //output.close(); 
        
        OutputStream outstream = connection.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(outstream);
        oos.writeObject(report);
        oos.flush();
         oos.close();

        connection.connect();
       //TODO ver que pasa si se quita
        InputStream is = connection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
 
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
        System.out.println( appletURL );
        System.out.println( this.appletURL );
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
        System.out.println( userFName );
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
        System.out.println( userLName );
    }
    
   

}
