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

package es.eucm.eadventure.tracking.prv.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

import org.apache.http.HttpHost;

import es.eucm.eadventure.tracking.pub.config.ProxyConfig;

public class ProxySetup {

    private static ProxyConfig proxyConfig;
    
    private static Logger log;
    private static Handler handler;
    private static boolean init=false;
    
    public static void log (String message){
        if (log!=null)
            log.info( message );
    }
    
    public static HttpHost buildHttpProxy(ProxyConfig proxyConfig){
        
        if(!init) init();
        boolean proxyConfigValid = false;
        HttpHost httpProxy = null;
        if (proxyConfig!=null && proxyConfig.getHostName( )!=null && !proxyConfig.getHostName( ).equals( "" ) &&
                proxyConfig.getPort( )!=null && !proxyConfig.getPort( ).equals( "" ) && 
                proxyConfig.getProtocol( )!=null && !proxyConfig.getProtocol( ).equals( "" )){
            try {
                int port = Integer.parseInt( proxyConfig.getPort() );
                String hostName = proxyConfig.getHostName( );
                String protocol = proxyConfig.getProtocol( );
                httpProxy = new HttpHost(hostName, port, protocol);
                proxyConfigValid = true;
            }catch (NumberFormatException ne){
                // Just consider the config proxy not valid
                proxyConfigValid = false;
            }
        }
        // If the proxy config was not set up, try default proxy
        if (!proxyConfigValid){
            ProxyConfig systemProxy = ProxySetup.getProxyConfig( );
            if (systemProxy!=null){
                try {
                    int port = Integer.parseInt( systemProxy.getPort() );
                    String hostName = systemProxy.getHostName( );
                    String protocol = systemProxy.getProtocol( );
                    httpProxy = new HttpHost(hostName, port, protocol);
                    proxyConfigValid = true;
                }catch (NumberFormatException ne){
                    // Just consider the config proxy not valid
                    proxyConfigValid = false;
                }
            }
        }
        
        // If neither option worked out, just disable the proxy using null
        if (!proxyConfigValid){
            httpProxy = null;
        }
        return httpProxy;
    }


    private static void init() {
        proxyConfig=null;
        log = Logger.getLogger( "es.eucm.eadventure.tracking.prv.service.ProxyConfig" );
        handler = null;
        try {
            handler = new FileHandler("proxy.log");
            log.addHandler( handler );
        }
        catch( SecurityException e ) {
            handler=null;
            e.printStackTrace();
        }
        catch( IOException e ) {
            handler=null;
            e.printStackTrace();
        }
        //Log log = LogFactory.getLog( "es.eucm.eadventure.tracking.prv.service.ProxyConfig" );
        log( "Setting prop java.net.useSystemProxies=true" );
        System.setProperty("java.net.useSystemProxies", "true");
      Proxy proxy = getProxy();
      if (proxy != null) {
        InetSocketAddress addr = (InetSocketAddress) proxy.address();
        if (addr==null){
            log ("No proxy detected");
            System.out.println( "NO PROXY" );
        } else {
            String host = addr.getHostName();
            int port = addr.getPort();
            proxyConfig = new ProxyConfig();
            proxyConfig.setHostName( host );
            proxyConfig.setPort( ""+port );
            proxyConfig.setProtocol( "http" );
            log ("Proxy detected: host="+host+" port="+port);
            System.setProperty("java.net.useSystemProxies", "false");
            System.setProperty("http.proxyHost", host);
            System.setProperty("http.proxyPort", ""+port);
            log ("Setting up system proxy host & port");
        }

      }
      System.setProperty("java.net.useSystemProxies", "false");
      log( "Setting prop java.net.useSystemProxies=false" );
      init = true;
    }

    /*public static String getHost() {
      return host;
    }

    public static int getPort() {
      return port;
    }*/
    
    private static ProxyConfig getProxyConfig(){
        if (!init){
            init();
        }
        
        return proxyConfig;
    }

    private static Proxy getProxy() {
      List<Proxy> l = null;
      try {
        ProxySelector def = ProxySelector.getDefault();

        l = def.select(new URI("http://foo/bar"));
        ProxySelector.setDefault(null);
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (l != null) {
        for (Iterator<Proxy> iter = l.iterator(); iter.hasNext();) {
          java.net.Proxy proxy = iter.next();
          return proxy;
        }
      }
      return null;
    }
    
    /*public static void main ( String[]args ){
        ProxyConfig.init( );
        System.out.println( ProxyConfig.host);
        System.out.println( ProxyConfig.port);
    }*/

}
