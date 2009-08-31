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
package es.eucm.eadventure.engine.comm;

import java.util.List;
import java.util.Set;

import es.eucm.eadventure.common.data.assessment.AssessmentProperty;
import es.eucm.eadventure.engine.adaptation.AdaptationEngine;

/**
 * General interface for interoperating with a webserver
 * This API is designed for asynchronous communication. This means that
 * when the methods are invoked they return inmediately. It is up to the
 * implementation of the API to invoke the callback method from this 
 * interface when appropriate.
 * 
 * The documentation of each method describes its possible expected
 * callbacks.
 * 
 * @author Pablo Moreno Ger
 *
 */
public interface AsynchronousCommunicationApi {

    /**
     * This method should be called when the session is started.
     * If the communication is possible, the API should be notified
     * by calling the method communicationEstablished. If the 
     * communication fails, the API may optionally call the
     * the communicationFailed method.
     * 
     * @param initializationText An implementation-dependent String
     * containing information to be sent to the server.
     *
     */
    public void startCommunication(String initializationText );

    /**
     * This is the callback method for the startCommunication method.
     * If this notification is never received, the engine assumes that
     * it is working in stand-alone mode and won't attempt any further
     * communications.
     * 
     * @param serverText The response of the server.
     */
    public void communicationEstablished( String serverText );

    /**
     * Callback method for a communication failure. It can be invoked
     * after failing to establish an initial communication or any time
     * that the communication fails during execution.
     * 
     * @param reason A message with the reason for the failure if available
     *
     */
    public void communicationFailed( String reason );

    /**
     * Returns whether there is a communication with the server. This
     * method is executed locally and returns inmediately.
     * @return A boolean stating the state of the comms link
     */
    public boolean isConnected( );

    /**
     * Request an updated state from the server. The external API should
     * call back the methods newState or communicationFailed.
     * 
     * @param properties A set containing the names of the properties that
     * should be requested from the LMS.
     *
     */
    public void getAdaptedState( Set<String> properties );
    
    /**
     * Callback method for the getAdaptedState request. This method parses
     * the string with the response and transforms it into an e-Adventure
     * state.
     * 
     * @param newState Enconded String with the new state.
     * 
     */
    public void newState( String newState );

    public void setAdaptationEngine( AdaptationEngine engine );
    
    public void notifyRelevantState( List<AssessmentProperty> properties);
    //public void reportRuleActivation();
}
