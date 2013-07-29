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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import es.eucm.eadventure.comm.AdventureApplet;
import es.eucm.eadventure.common.data.assessment.AssessmentProperty;
import es.eucm.eadventure.engine.adaptation.AdaptationEngine;

public class CommManagerScorm extends AdventureApplet {

    private static final long serialVersionUID = 3444828969384528659L;

    private boolean connected;

    private boolean lock;

    private HashMap<String, String> adaptedStates;

    private int index;

    private AdaptationEngine adaptationEngine;
    
   //
    
   // private 

    /**
     * The relations between the operation and where is store in "valuesFromLms"
     */
    private HashMap<String, String> valuesFromLMS;

    public CommManagerScorm( ) {

        valuesFromLMS = new HashMap<String, String>( );
        connected = false;
        adaptedStates = new HashMap<String, String>( );
        index = 0;
        lock = false;
    }

    /**
     * 
     */
    public boolean connect( HashMap<String, String> info ) {

        String command = "javascript:eucm.eadventure.connect(\" \");";

        this.sendJavaScript( command );

        return false;
    }

    /**
     * 
     */
    public boolean disconnect( HashMap<String, String> info ) {

        String command = "javascript:eucm.eadventure.disconnect(\" \");";

        this.sendJavaScript( command );

        this.connected = false;

        return false;
    }

    public void disconnectOK( ) {

        this.connected = false;
    }

    private void waitResponse( ) {

        int milis = 0;
        while( lock ) {
            // If its was waiting 10 seconds, abort
            if( milis >= 10000 )
                break;
            try {
                Thread.sleep( 500 );
                milis += 500;
            }
            catch( InterruptedException e ) {
                e.printStackTrace( );
            }
        }

    }

    public int getCommType( ) {

        return CommManagerApi.SCORMV12_TYPE;
    }

    public void connectionEstablished( String serverComment ) {

        connected = true;
        System.out.println( serverComment );

    }

    public void connectionFailed( String serverComment ) {

        System.out.println( serverComment );
        connected = false;
    }

    public void dataFromLMS( String key, String value ) {

        if( value == null )
            value = new String( "" );
        valuesFromLMS.put( key, value );

    }

    public void dataSendingFailed( String serverComment ) {

        System.out.println( serverComment );

    }

    public void dataSendingOK( String serverComment ) {

        System.out.println( serverComment );
    }

    public void getFromLMS( String attribute ) {

        String command = "javascript:eucm.eadventure.getLMSData(\"" + attribute + "\");";

        this.sendJavaScript( command );
    }

    public void notifyRelevantState( List<AssessmentProperty> list ) {

        Iterator<AssessmentProperty> it = list.iterator( );
        while( it.hasNext( ) ) {
            AssessmentProperty assessProp = it.next( );
            String attribute = assessProp.getId( );
            String value = String.valueOf( assessProp.getValue( ) );
            String command = "javascript:eucm.eadventure.setLMSData(\"" + attribute + "\", \"" + removeQuotationMarks(value) + "\");";
            System.out.println(command);
            this.sendJavaScript( command );
            String command2 = "javascript:eucm.eadventure.commit(\"\");";
            this.sendJavaScript( command2 );
        }

    }
    
    private StringBuffer removeQuotationMarks(String ini){
        
        StringBuffer fin = new StringBuffer();
        for (int i=0; i < ini.length( ); i++){
            if (ini.charAt( i ) == '"'){
                fin.append('\134');
                fin.append('\042');
            }
            else
                fin.append(ini.charAt(i));
            
        }
        return fin;
        
    }

    public boolean isConnected( ) {

        return connected;
    }

    public HashMap<String, String> getInitialStates( ) {

        waitResponse( );

        return valuesFromLMS;
    }

    public void getAdaptedState( Set<String> properties ) {

        lock = true;
        for( String rule : properties ) {
            getFromLMS( rule );
        }
        lock = false;
    }

    public void setAdaptationEngine( AdaptationEngine adaptationEngine ) {

        this.adaptationEngine = adaptationEngine;
    }

    public void sendHTMLReport( String report ) {

        // TODO Auto-generated method stub
        
    }

}
