package es.eucm.eadventure.editor.control.loader;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.util.List;

import es.eucm.eadventure.common.data.adventure.AdventureData;
import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.loader.incidences.Incidence;
import es.eucm.eadventure.editor.control.controllers.AdventureDataControl;
import es.eucm.eadventure.editor.control.loader.parsers.AdventureHandler;
import es.eucm.eadventure.editor.control.loader.parsers.ChapterHandler;
import es.eucm.eadventure.editor.control.loader.parsers.DescriptorHandler;
import es.eucm.eadventure.editor.gui.TextConstants;

/**
 * This class loads the e-Adventure data from a XML file
 */
public class Loader {

	/**
	 * Private constructor
	 */
	private Loader( ) {}

	/**
	 * Loads the adventure data from the given ZIP file.
	 * 
	 * @param zipFile
	 *            Path to the zip file which holds the adventure
	 * @return The adventure data, null if there was an error
	 */
	public static AdventureDataControl loadData( String zipFile, List<Incidence> incidences ) {
		AdventureDataControl adventureData = null;
		try {
			// Set the adventure handler
			AdventureHandler adventureParser = new AdventureHandler( zipFile, incidences );

			// Create a new factory
			SAXParserFactory factory = SAXParserFactory.newInstance( );
			factory.setValidating( true );
			SAXParser saxParser = factory.newSAXParser( );

			// Load the input stream with the descriptor
			InputStream descriptorIS = new FileInputStream( zipFile + "/descriptor.xml" );

			// Read and close the input stream
			saxParser.parse( descriptorIS, adventureParser );
			descriptorIS.close( );

			// Store the adventure data
			adventureData = adventureParser.getAdventureData( );

		} catch( ParserConfigurationException e ) {
			//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadData" ) );
			//e.printStackTrace( );
			incidences.add( Incidence.createDescriptorIncidence( TextConstants.getText( "Error.LoadDescriptor.SAX" ) ) );
		} catch( SAXException e ) {
			//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadData" ) );
			//e.printStackTrace( );
			incidences.add( Incidence.createDescriptorIncidence( TextConstants.getText( "Error.LoadDescriptor.SAX" ) ) );
		} catch( IOException e ) {
			//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadData" ) );
			//e.printStackTrace( );
			incidences.add( Incidence.createDescriptorIncidence( TextConstants.getText( "Error.LoadDescriptor.IO" ) ) );
		}

		return adventureData;
	}

	public static AdventureData loadDescriptorData( String zipFile ) {
		AdventureData adventureData = null;

		try {
			// Set the adventure handler
			DescriptorHandler adventureParser = new DescriptorHandler( zipFile );

			// Create a new factory
			SAXParserFactory factory = SAXParserFactory.newInstance( );
			factory.setValidating( true );
			SAXParser saxParser = factory.newSAXParser( );

			// Load the input stream with the descriptor
			InputStream descriptorIS = new FileInputStream( zipFile + "/descriptor.xml" );

			// Read and close the inputstream
			saxParser.parse( descriptorIS, adventureParser );
			descriptorIS.close( );

			// Store the adventure data
			adventureData = adventureParser.getAdventureData( );

		} catch( ParserConfigurationException e ) {
			//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadData" ) );
			//e.printStackTrace( );
		} catch( SAXException e ) {
			//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadData" ) );
			//e.printStackTrace( );
		} catch( IOException e ) {
			//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadData" ) );
			//e.printStackTrace( );
		}

		return adventureData;

	}
	
	
	public static Chapter loadChapterFromFile ( String absolutePath, List<Incidence> incidences ){
		// Create the chapter
		Chapter currentChapter = new Chapter( );
		
		if (absolutePath!=null){
			String chapterPath = absolutePath.substring( Math.max (absolutePath.lastIndexOf( '\\' ), absolutePath.lastIndexOf( '/' ) ), absolutePath.length( ));
			currentChapter.setName( chapterPath );
		} else 
			currentChapter.setName( "" );


		// Open the file and load the data
		try {
			// Set the chapter handler
			ChapterHandler chapterParser = new ChapterHandler( currentChapter );

			// Create a new factory
			SAXParserFactory factory = SAXParserFactory.newInstance( );
			factory.setValidating( true );
			SAXParser saxParser = factory.newSAXParser( );

			// Set the input stream with the file
			InputStream chapterIS = new FileInputStream( absolutePath );

			// Parse the data and close the data
			saxParser.parse( chapterIS, chapterParser );
			chapterIS.close( );

		} catch( ParserConfigurationException e ) {
			//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadData" ) );
			//e.printStackTrace( );
			incidences.add( Incidence.createChapterIncidence( TextConstants.getText( "Error.LoadData.SAX" ), absolutePath ) );
		} catch( SAXException e ) {
			//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadData" ) );
			//e.printStackTrace( );
			incidences.add( Incidence.createChapterIncidence( TextConstants.getText( "Error.LoadData.SAX" ), absolutePath ) );
		} catch( IOException e ) {
			//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadData" ) );
			//e.printStackTrace( );
			incidences.add( Incidence.createChapterIncidence( TextConstants.getText( "Error.LoadData.IO" ), absolutePath ) );
		}

		return currentChapter;
	}
	
    /**
     * Loads assessment rules from a file
     * @param filename File name
     * @return List of assessment rules
     */
    /*public static List<AssessmentRule> loadAssessmentRules( String filename ) {
        List<AssessmentRule> assessmentRules = null;

        try {
            AssessmentHandler assessmentHandler = new AssessmentHandler( );
            
            // If a filename was specified
            if( filename != null ) {
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
    }*/
    
    /**
     * Loads adaptation data from a file
     * @param filename File name
     * 
     * @return 
     */
    /*public static Map<String,Object> loadAdaptationData( String filename ) {

        Map<String,Object> output = new HashMap<String,Object>();
        
        try {
            AdaptationHandler adaptationHandler = new AdaptationHandler( );
            
            // If a filename was specified
            if( filename != null ) {
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
    }*/
	
	
	
}
