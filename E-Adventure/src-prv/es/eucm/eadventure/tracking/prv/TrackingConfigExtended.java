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

package es.eucm.eadventure.tracking.prv;

import es.eucm.eadventure.tracking.pub.config.Property;
import es.eucm.eadventure.tracking.pub.config.TrackingConfig;


public class TrackingConfigExtended {

    private static final String STUDENT_ID_PROP ="student-id";
    private static final String GAME_ID_PROP ="game-id";
    private static final String EFFECTS_VERBOSITY_PROP ="log-effects";
    private static final String LOW_LEVEL_FREQ_PROP ="low-level-freq";
    
    private TrackingConfig trackingConfig;

    /**
     * This field identifies the current implementation of the tracking system.
     * It should be updated every time the system is modified, especially if the
     * model changes.
     */
    public static final String VERSION = "20130109";
    
    public TrackingConfigExtended(TrackingConfig trackingConfig){
        this.trackingConfig = trackingConfig;
    }
    
    public String getStudentId(){
        return findProp(STUDENT_ID_PROP);
    }
    
    public String getGameId(){
        return findProp(GAME_ID_PROP);
    }
    
    public boolean effectVerbosityEnabled(){
        try {
            return Boolean.parseBoolean( findProp(EFFECTS_VERBOSITY_PROP) );
        } catch (Exception e){
            return false;
        }
    }
    
    public long logLowLevelEventsSampleFreq(){
        try {
            return Long.parseLong( findProp(LOW_LEVEL_FREQ_PROP) );
        } catch (Exception e){
            return 2000;
        }
    }
    
    private String findProp( String propName ){
        String prop=null;
        for (Property p:trackingConfig.getProperty( )){
            if (p.getName( ).equals( propName )){
                prop = p.getValue( );
                break;
            }
        }
        return prop;
    }

    
    public TrackingConfig getTrackingConfig( ) {
    
        return trackingConfig;
    }
    
    public void setStudentId(String sID){
        for (Property p:trackingConfig.getProperty( )){
            if (p.getName( ).equals( STUDENT_ID_PROP )){
                p.setValue( sID );
                break;
            }
        }
    }
}
