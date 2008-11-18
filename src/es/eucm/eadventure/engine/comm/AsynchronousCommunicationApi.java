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
