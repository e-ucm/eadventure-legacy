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

package es.eucm.eadventure.tracking.prv;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;


public class TrackingPoster {
    
    private static TrackingPoster instance;
    
    public static void setInstance (String serviceURL, String serviceKey){
        instance = new TrackingPoster(serviceURL, serviceKey);
    }
    
    public static TrackingPoster getInstance(){
        return instance;
    }
    
    private String serviceURL;
    
    private String serviceKey="";
    
    private String baseURL=null;
    
    public TrackingPoster(String serviceURL, String serviceKey){
        this.serviceKey = serviceKey;
        this.serviceURL = serviceURL;
    }
    
    public String openSession(){
        if (baseURL!=null) return baseURL;
        
        try {
            HttpClient httpclient = new DefaultHttpClient();
            URI uri = URIUtils.createURI("http", serviceURL, -1, "", null, null);
            HttpPost httppost = new HttpPost(uri);
            HttpResponse response;
        
            response = httpclient.execute(httppost);
            if (Integer.toString( response.getStatusLine( ).getStatusCode( ) ).startsWith( "2" )){
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream instream = entity.getContent();
                    
                    StringWriter strWriter = new StringWriter();
                    
                    char[] buffer = new char[1024];
                 
                    try {
                        Reader reader = new BufferedReader(new InputStreamReader(instream, "UTF-8"));
                        int n;
                        while ((n = reader.read(buffer)) != -1) {
                            strWriter.write(buffer, 0, n);
                        }
                    } finally {
                        instream.close();
                    }
                    baseURL =  strWriter.toString();
                    
                }
            }
        }
        catch( ClientProtocolException e ) {
            e.printStackTrace();
        }
        catch( IOException e ) {
            e.printStackTrace();
        }
        catch( URISyntaxException e ) {
            e.printStackTrace();
        }
        return baseURL;
    }
    
    public boolean sendChunk(List<GameLogEntry> entries){
        if (baseURL==null) return false; 
        boolean sent=false;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            String url = "";
            
            String chunk="";
            for (GameLogEntry entry:entries){
                chunk+=entry.toString( );
            }
            
            HttpPost httpput = new HttpPost(url);
            StringEntity myEntity = new StringEntity(chunk,"UTF-8");
            httpput.setEntity( myEntity );
            HttpResponse response;
        
            response = httpclient.execute(httpput);
            if (response!=null &&
                    Integer.toString( response.getStatusLine( ).getStatusCode( ) ).startsWith( "2" )){
                    sent=true;
            }
        }
        catch( ClientProtocolException e ) {
            e.printStackTrace();
        }
        catch( IOException e ) {
            e.printStackTrace();
        }
        return sent;
    }

    public boolean sendSnapshot( File file ){
        if (baseURL==null) return false; 
        boolean sent=false;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            String url = "";
            
            HttpPost httpput = new HttpPost(url);
            FileEntity myEntity = new FileEntity(file, "image/jpeg");
            httpput.setEntity( myEntity );
            HttpResponse response;
        
            response = httpclient.execute(httpput);
            if (response!=null &&
                    Integer.toString( response.getStatusLine( ).getStatusCode( ) ).startsWith( "2" )){
                    sent=true;
            }
        }
        catch( ClientProtocolException e ) {
            e.printStackTrace();
        }
        catch( IOException e ) {
            e.printStackTrace();
        }
        return sent;

    }

    public static void main (String[]args){
        TrackingPoster poster = new TrackingPoster("", "");
        System.out.println( poster.openSession( ));
        List<GameLogEntry> entries = new ArrayList<GameLogEntry>();
        entries.add( new GameLogEntry(500, "test1") );
        entries.add( new GameLogEntry(700, "test2") );
        entries.add( new GameLogEntry(800, "test3") );
        System.out.println( poster.sendChunk( entries ) );
    }
    
    
}
