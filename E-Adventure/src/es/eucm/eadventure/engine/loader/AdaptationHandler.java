package es.eucm.eadventure.engine.loader;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import es.eucm.eadventure.engine.adaptation.AdaptationRule;
import es.eucm.eadventure.engine.adaptation.AdaptedState;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

/**
 * This handler reads the initial values for the adaptation engine
 */
public class AdaptationHandler extends DefaultHandler {
    
    /* Constants */
    private static final int NONE = 0;
    private static final int INITIAL_STATE = 1;
    private static final int ADAPTATION_RULE = 2;
    
    private int parsing = NONE;
    
    /**
     * Adaptation data
     */
    private AdaptedState initialState;
    private List<AdaptationRule> externalRules;
    private AdaptationRule rule_temp;
    
    /**
     * Default constructor
     */
    public AdaptationHandler( ) {
        initialState = new AdaptedState( );
        externalRules = new LinkedList<AdaptationRule>();
    }
    
    /**
     * Returns the initial state
     * @return initial state
     */
    public AdaptedState getInitialState( ) {
        return initialState;
    }
    
    /**
     * Returns a list of adaptation rules
     * @return adaptation rules
     */
    public List<AdaptationRule> getAdaptationRules( ) {
        return externalRules;
    }
    
    /*
     *  (non-Javadoc)
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) throws SAXException {

        //Start parsing the initial state
        if (qName.equals( "initial-state" )) {
            parsing = INITIAL_STATE;
        }
        
        //Start parsing an adaptation rule
        else if (qName.equals( "adaptation-rule" )){
            parsing = ADAPTATION_RULE;
            rule_temp = new AdaptationRule();
        }
        
        //Initial scene
        else if( qName.equals( "initial-scene" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "idTarget" ) ) {
                    if(parsing==INITIAL_STATE) {
                        initialState.setInitialScene( attrs.getValue( i ) );
                    } else {
                        rule_temp.setInitialScene( attrs.getValue( i ));
                    }
                }
            }
        }
        
        // If the tag activates a flag
        else if( qName.equals( "activate" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "flag" ) ) {
                    if (parsing==INITIAL_STATE) {
                        initialState.addActivatedFlag( attrs.getValue( i ) );
                    } else {
                        rule_temp.addActivatedFlag( attrs.getValue( i ) );
                    }
                }
            }
        }
        
        // If the tag deactivates a flag
        else if( qName.equals( "deactivate" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "flag" ) ) {
                    if (parsing==INITIAL_STATE) {
                        initialState.addDeactivatedFlag( attrs.getValue( i ) );
                    } else {
                        rule_temp.addDeactivatedFlag( attrs.getValue( i ) );
                    }
   
                }
            }
        }
        
        //Property from the UoL
        else if (qName.equals( "property" )) {
            String id = null;
            String value =  null;
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "id" ) ) {
                    id = attrs.getValue( i );  
                } else if (attrs.getQName( i ).equals( "value" ) ) {
                    value = attrs.getValue( i ); 
                }
            }
            rule_temp.addProperty( id, value );
        }
            
    }
    
    public void endElement( String namespaceURI, String localName, String qName ) throws SAXException {
        //Finish parsing the initial state
        if (qName.equals( "initial-state" )) {
            parsing = NONE;
        }
        
        //Finish parsing an adaptation rule
        else if (qName.equals( "adaptation-rule" )){
            parsing = NONE;
            externalRules.add( rule_temp );
        }
    }

    /*
     *  (non-Javadoc)
     * @see org.xml.sax.ErrorHandler#error(org.xml.sax.SAXParseException)
     */
    public void error( SAXParseException exception ) throws SAXParseException {
        // On validation, propagate exception
        exception.printStackTrace( );
        throw exception;
    }
    
    /*
     *  (non-Javadoc)
     * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
     */
    public InputSource resolveEntity( String publicId, String systemId ) {
        // Take the name of the file SAX is looking for
        int startFilename = systemId.lastIndexOf( "/" ) + 1;
        String filename = systemId.substring( startFilename, systemId.length( ) );
        
        // Build and return a input stream with the file (usually the DTD)
        InputStream inputStream = ResourceHandler.getInstance( ).getResourceAsStream( filename );        
        return new InputSource( inputStream );
    }    

}
