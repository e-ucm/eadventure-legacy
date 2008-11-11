package es.eucm.eadventure.engine.loader;

import java.io.InputStream;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import es.eucm.eadventure.engine.assessment.AssessmentProperty;
import es.eucm.eadventure.engine.assessment.AssessmentRule;
import es.eucm.eadventure.engine.assessment.TimedAssessmentRule;
import es.eucm.eadventure.common.data.chapterdata.conditions.Condition;
import es.eucm.eadventure.common.data.chapterdata.conditions.Conditions;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

/**
 * This class is the handler to parse the assesment rules file of the adventure
 */
public class AssessmentHandler extends DefaultHandler {
    
    /* Attributes */
    
    /**
     * String to store the current string in the XML file
     */
    private StringBuffer currentString;
    
    /**
     * Constant for reading nothing
     */
    private static final int READING_NONE = 0;

    /**
     * Constant for reading either tag
     */
    private static final int READING_EITHER = 1;
    
    /**
     * Stores the current element being readed
     */
    private int reading = READING_NONE;
    
    /**
     * Array of assessment rules
     */
    private ArrayList<AssessmentRule> assessmentRules;
    
    /**
     * Assessment rule currently being read
     */
    private AssessmentRule currentAssessmentRule;
    
    /**
     * Set of conditions being read
     */
    private Conditions currentConditions;
    
    /**
     * Set of either conditions being read
     */
    private Conditions currentEitherCondition;
    
    /* Methods */
    
    /**
     * Default constructor
     */
    public AssessmentHandler( ) {
        assessmentRules = new ArrayList<AssessmentRule>( );
        currentAssessmentRule = null;
        currentString = new StringBuffer( );
    }
    
    /**
     * Return the list of the assessment rules
     * @return List of assessment rules, empty list if no parsing were performed
     */
    public ArrayList<AssessmentRule> getAssessmentRules( ) {
        return assessmentRules;
    }
    
    /*
     *  (non-Javadoc)
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) throws SAXException {
        
        if( qName.equals( "assessment-rule" ) ) {
            
            String id = null;
            int importance = 0;
            
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "id" ) )
                    id = attrs.getValue( i );
                if( attrs.getQName( i ).equals( "importance" ) ) {
                    for( int j = 0; j < AssessmentRule.N_IMPORTANCE_VALUES; j++ )
                        if( attrs.getValue( i ).equals( AssessmentRule.IMPORTANCE_VALUES[ j ] ) )
                            importance = j;
                }
            }
            
            currentAssessmentRule = new AssessmentRule( id, importance );
        }
        
        else if( qName.equals( "timed-assessment-rule" ) ) {
            
            String id = null;
            int importance = 0;
            
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "id" ) )
                    id = attrs.getValue( i );
                if( attrs.getQName( i ).equals( "importance" ) ) {
                    for( int j = 0; j < AssessmentRule.N_IMPORTANCE_VALUES; j++ )
                        if( attrs.getValue( i ).equals( AssessmentRule.IMPORTANCE_VALUES[ j ] ) )
                            importance = j;
                }
            }
            
            currentAssessmentRule = new TimedAssessmentRule( id, importance );
        }

        
        else if( qName.equals( "condition" ) || qName.equals( "init-condition" ) || qName.equals( "end-condition" ) ) {
            currentConditions = new Conditions( );
        }
        
        // If it is an either tag, create a new either conditions and switch the state
        else if( qName.equals( "either" ) ) {
            currentEitherCondition = new Conditions( );
            reading = READING_EITHER;
        }
        
        // If it is an active tag
        else if( qName.equals( "active" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "flag" ) ) {
                    
                    // Store the active flag in the conditions or either conditions
                    if( reading == READING_NONE )
                        currentConditions.addCondition( new Condition( attrs.getValue( i ), true ) );
                    if( reading == READING_EITHER )
                        currentEitherCondition.addCondition( new Condition( attrs.getValue( i ), true ) );
                }
            }
        }

        // If it is an inactive tag
        else if( qName.equals( "inactive" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "flag" ) ) {
                    
                    // Store the inactive flag in the conditions or either conditions
                    if( reading == READING_NONE )
                        currentConditions.addCondition( new Condition( attrs.getValue( i ), false ) );
                    if( reading == READING_EITHER )
                        currentEitherCondition.addCondition( new Condition( attrs.getValue( i ), false ) );
                }
            }
        }
        
        else if( qName.equals( "set-property" ) ) {
            String id = null;
            int value = 0;
            
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "id" ) )
                    id = attrs.getValue( i );
                if( attrs.getQName( i ).equals( "value" ) )
                    value = Integer.parseInt( attrs.getValue( i ) );
            }
            
            currentAssessmentRule.addProperty( new AssessmentProperty( id, value ) );
        }
        
        else if( qName.equals( "effect" ) ) {
            if ( currentAssessmentRule instanceof TimedAssessmentRule){
                int timeMin = Integer.MIN_VALUE;
                int timeMax = Integer.MIN_VALUE;
                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    
                    if( attrs.getQName( i ).equals( "time-min" ) )
                        timeMin = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getQName( i ).equals( "time-max" ) )
                        timeMax = Integer.parseInt( attrs.getValue( i ) );
                }

                TimedAssessmentRule tRule = (TimedAssessmentRule) currentAssessmentRule;
                if (timeMin!=Integer.MIN_VALUE && timeMax!=Integer.MAX_VALUE){
                    tRule.addEffect( timeMin, timeMax );
                } else {
                    tRule.addEffect( );
                }
            }
        }

    }
    
    /*  
     *  (non-Javadoc)
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    public void endElement( String namespaceURI, String sName, String qName ) throws SAXException {
        
        if( qName.equals( "assessment-rule" ) || qName.equals( "timed-assessment-rule" )  ) {
            assessmentRules.add( currentAssessmentRule );
        }
        
        else if( qName.equals( "concept" ) ) {
            currentAssessmentRule.setConcept( currentString.toString( ).trim( ) );
        }
        
        else if( qName.equals( "condition" ) ) {
            currentAssessmentRule.setConditions( currentConditions );
        }
        
        else if( qName.equals( "init-condition" ) ) {
            ((TimedAssessmentRule)currentAssessmentRule).setInitConditions( currentConditions );
        }
        
        else if( qName.equals( "end-condition" ) ) {
            ((TimedAssessmentRule)currentAssessmentRule).setEndConditions( currentConditions );
        }

        
        // If it is an either tag
        else if( qName.equals( "either" ) ) {
            // Store the either condition in the condition, and switch the state back to normal
            currentConditions.addEitherCondition( currentEitherCondition );
            reading = READING_NONE;
        }
        
        else if( qName.equals( "set-text" ) ) {
            currentAssessmentRule.setText( currentString.toString( ).trim( ) );
        }
        
        // Reset the current string
        currentString = new StringBuffer( );
    }

    /*
     *  (non-Javadoc)
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    public void characters( char[] buf, int offset, int len ) throws SAXException {
        // Append the new characters
        currentString.append( new String( buf, offset, len ) );
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
