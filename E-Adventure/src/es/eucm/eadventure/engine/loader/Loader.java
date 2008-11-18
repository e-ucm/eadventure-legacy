package es.eucm.eadventure.engine.loader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import es.eucm.eadventure.common.data.adventure.AdventureData;
import es.eucm.eadventure.engine.adaptation.AdaptationEngine;
import es.eucm.eadventure.engine.assessment.AssessmentRule;
import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

/**
 * This class loads the e-Adventure data from a XML file
 */
public class Loader {
    
    /**
     * Loads the descriptor of the current ZIP adventure loaded
     * @return The descriptor data of the game
     */
    public static AdventureData loadDescriptor( ) {
        AdventureData gameDescriptor = null;
        
        try {
            // Load the file with the script handler
            InputStream is = ResourceHandler.getInstance( ).getResourceAsStreamFromZip( "descriptor.xml" );
            DescriptorHandler descriptorHandler = new DescriptorHandler( );

            // Create a new factory
            SAXParserFactory factory = SAXParserFactory.newInstance( );
            factory.setValidating( true );

            // Read the information of the selected file
            SAXParser saxParser;

            // Parse the file
            saxParser = factory.newSAXParser( );
            saxParser.parse( is, descriptorHandler );
            is.close( );

            // Store the script data
            gameDescriptor = descriptorHandler.getGameDescriptor( );

        } catch( ParserConfigurationException e ) {
            e.printStackTrace( );
        } catch( SAXException e ) {
            e.printStackTrace( );
        } catch( IOException e ) {
            e.printStackTrace( );
        }

        return gameDescriptor;
    }

    /**
     * Loads the script data from the given XML file
     * @param filename Name of the XML file containing the script
     * @return The script stored as game data
     */
    public static Chapter loadData( String filename ) {
        Chapter gameData = null;

        try {
            // Load the file with the script handler
            InputStream is = ResourceHandler.getInstance( ).getResourceAsStreamFromZip( filename );
            ScriptHandler scriptParser = new ScriptHandler( );

            // Create a new factory
            SAXParserFactory factory = SAXParserFactory.newInstance( );
            factory.setValidating( true );

            // Read the information of the selected file
            SAXParser saxParser;

            // Parse the file
            saxParser = factory.newSAXParser( );
            saxParser.parse( is, scriptParser );
            is.close( );

            // Store the script data
            gameData = scriptParser.getGameData( );

        } catch( ParserConfigurationException e ) {
            e.printStackTrace( );
        } catch( SAXException e ) {
            e.printStackTrace( );
        } catch( IOException e ) {
            e.printStackTrace( );
        }

        return gameData;
    }
    
    /**
     * Loads assessment rules from a file
     * @param filename File name
     * @return List of assessment rules
     */
    public static List<AssessmentRule> loadAssessmentRules( String filename ) {
        List<AssessmentRule> assessmentRules = null;

        try {
            AssessmentHandler assessmentHandler = new AssessmentHandler( );
            
            // If a filename was specified
            if( filename != null && !filename.equals("")) {
                // Load the assessment rules
                InputStream is = ResourceHandler.getInstance( ).getResourceAsStreamFromZip( filename );
                
                // Create a new factory
                SAXParserFactory factory = SAXParserFactory.newInstance( );
                factory.setValidating( true );
    
                // Read the information of the selected file
                SAXParser saxParser;
    
                // Parse the file
                saxParser = factory.newSAXParser( );
                saxParser.parse( is, assessmentHandler );
                is.close( );
            }

            // Store the script data
            assessmentRules = assessmentHandler.getAssessmentRules( );

        } catch( ParserConfigurationException e ) {
            e.printStackTrace( );
        } catch( SAXException e ) {
            e.printStackTrace( );
        } catch( IOException e ) {
            e.printStackTrace( );
        }
        
        return assessmentRules;
    }
    
    /**
     * Loads adaptation data from a file
     * @param filename File name
     * 
     * @return 
     */
    public static Map<String,Object> loadAdaptationData( String filename ) {

        Map<String,Object> output = new HashMap<String,Object>();
        
        try {
            AdaptationHandler adaptationHandler = new AdaptationHandler( );
            
            // If a filename was specified
            if( filename != null && !filename.equals("")) {
                // Load the adaptation file
                InputStream is = ResourceHandler.getInstance( ).getResourceAsStreamFromZip( filename );
                
                // Create a new factory
                SAXParserFactory factory = SAXParserFactory.newInstance( );
                factory.setValidating( true );
    
                // Read the information of the selected file
                SAXParser saxParser;
    
                // Parse the file
                saxParser = factory.newSAXParser( );
                saxParser.parse( is, adaptationHandler );
                is.close( );
            }

            // Store the data
            output.put(AdaptationEngine.INITIAL_STATE,adaptationHandler.getInitialState( ));
            output.put( AdaptationEngine.ADAPTATION_RULES, adaptationHandler.getAdaptationRules());
            
        } catch( ParserConfigurationException e ) {
            e.printStackTrace( );
        } catch( SAXException e ) {
            e.printStackTrace( );
        } catch( IOException e ) {
            e.printStackTrace( );
        }
        
        return output;
    }
}
