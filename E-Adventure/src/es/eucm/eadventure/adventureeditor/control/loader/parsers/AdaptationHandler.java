package es.eucm.eadventure.adventureeditor.control.loader.parsers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import es.eucm.eadventure.adventureeditor.data.adaptation.AdaptationRule;
import es.eucm.eadventure.adventureeditor.data.adaptation.AdaptedState;
import es.eucm.eadventure.adventureeditor.data.adaptation.UOLProperty;

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
     * String to store the current string in the XML file
     */
    private StringBuffer currentString;
    
    /**
     * Default constructor
     */
    public AdaptationHandler( List<AdaptationRule> rules, AdaptedState iState ) {
        initialState = iState;
        externalRules=rules;
        currentString = new StringBuffer( );
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
            rule_temp.addUOLProperty( new UOLProperty(id,value) );
        }
            
    }
    
    public void endElement( String namespaceURI, String localName, String qName ) throws SAXException {
        //Finish parsing the initial state
        if (qName.equals( "initial-state" )) {
            parsing = NONE;
        }
        
        else if( qName.equals( "description" ) ) {
            this.rule_temp.setDescription( currentString.toString( ).trim( ) );
        }

        //Finish parsing an adaptation rule
        else if (qName.equals( "adaptation-rule" )){
            parsing = NONE;
            externalRules.add( rule_temp );
        }
        
     // Reset the current string
        currentString = new StringBuffer( );
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
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    public void characters( char[] buf, int offset, int len ) throws SAXException {
        // Append the new characters
        currentString.append( new String( buf, offset, len ) );
        
    }


}
