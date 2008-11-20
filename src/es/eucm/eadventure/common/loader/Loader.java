package es.eucm.eadventure.common.loader;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.adaptation.AdaptationProfile;
import es.eucm.eadventure.common.data.adaptation.AdaptationRule;
import es.eucm.eadventure.common.data.adaptation.AdaptedState;
import es.eucm.eadventure.common.data.adventure.AdventureData;
import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.common.data.assessment.AssessmentProfile;
import es.eucm.eadventure.common.data.assessment.AssessmentRule;
import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.common.loader.incidences.Incidence;
import es.eucm.eadventure.common.loader.parsers.AdaptationHandler;
import es.eucm.eadventure.common.loader.parsers.AdventureHandler;
import es.eucm.eadventure.common.loader.parsers.AssessmentHandler;
import es.eucm.eadventure.common.loader.parsers.ChapterHandler;
import es.eucm.eadventure.common.loader.parsers.DescriptorHandler;

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
	public static AdventureData loadAdventureData(InputStreamCreator isCreator, String assessmentFolder, String adaptationFolder, List<Incidence> incidences ) {
		AdventureData adventureData = null;
		try {
			// Set the adventure handler
			AdventureHandler adventureParser = new AdventureHandler( isCreator, assessmentFolder, adaptationFolder, incidences );

			// Create a new factory
			SAXParserFactory factory = SAXParserFactory.newInstance( );
			factory.setValidating( true );
			SAXParser saxParser = factory.newSAXParser( );

			// Read and close the input stream
			InputStream descriptorIS = isCreator.buildInputStream("descriptor.xml");
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

    /**
     * Loads the descriptor of the current ZIP adventure loaded
     * @return The descriptor data of the game
     */	
	public static DescriptorData loadDescriptorData( InputStreamCreator isCreator ) {
		DescriptorData adventureData = null;

		try {
			// Set the adventure handler
			DescriptorHandler descriptorParser = new DescriptorHandler(  );

			// Create a new factory
			SAXParserFactory factory = SAXParserFactory.newInstance( );
			factory.setValidating( true );
			SAXParser saxParser = factory.newSAXParser( );

			// Read and close the inputstrea
			InputStream descriptorIS = isCreator.buildInputStream("descriptor.xml"); 
			saxParser.parse( descriptorIS, descriptorParser );
			descriptorIS.close( );

			// Store the adventure data
			adventureData = descriptorParser.getGameDescriptor();

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
	
    /**
     * Loads the script data from the given XML file
     * @param filename Name of the XML file containing the script
     * @return The script stored as game data
     */
	public static Chapter loadChapterData (InputStreamCreator isCreator, String fileName,  List<Incidence> incidences ){
		// Create the chapter
		Chapter currentChapter = new Chapter( );
		InputStream chapterIS = null;
		
		//if (zipFile!=null){
			chapterIS = isCreator.buildInputStream(fileName);
			currentChapter.setName( fileName );
			
		//} else{
			// Then fileName is an absolutePath
			//String chapterPath = fileName.substring( Math.max (fileName.lastIndexOf( '\\' ), fileName.lastIndexOf( '/' ) ), fileName.length( ));
			//currentChapter.setName( chapterPath );
			//try {
			//	chapterIS = new FileInputStream( fileName );
			//} catch (FileNotFoundException e) {
				//e.printStackTrace();
			//	incidences.add( Incidence.createChapterIncidence( TextConstants.getText( "Error.LoadData.IO" ), fileName ) );
			//}
		//}

		// Open the file and load the data
		try {
			if (chapterIS!=null){
				// Set the chapter handler
				ChapterHandler chapterParser = new ChapterHandler( currentChapter );
	
				// Create a new factory
				SAXParserFactory factory = SAXParserFactory.newInstance( );
				factory.setValidating( true );
				SAXParser saxParser = factory.newSAXParser( );
	
				// Parse the data and close the data
				saxParser.parse( chapterIS, chapterParser );
				chapterIS.close( );
			}

		} catch( ParserConfigurationException e ) {
			//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadData" ) );
			//e.printStackTrace( );
			incidences.add( Incidence.createChapterIncidence( TextConstants.getText( "Error.LoadData.SAX" ), fileName ) );
		} catch( SAXException e ) {
			//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadData" ) );
			//e.printStackTrace( );
			incidences.add( Incidence.createChapterIncidence( TextConstants.getText( "Error.LoadData.SAX" ), fileName ) );
		} catch( IOException e ) {
			//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadData" ) );
			//e.printStackTrace( );
			incidences.add( Incidence.createChapterIncidence( TextConstants.getText( "Error.LoadData.IO" ), fileName ) );
		}

		return currentChapter;
	}
	
	/**
	 * Loads the assessment profile (set of assessment rules) stored in file with path xmlFile in zipFile
	 * @param zipFile
	 * @param xmlFile
	 * @param incidences
	 * @return
	 */
	public static AssessmentProfile loadAssessmentProfile ( InputStreamCreator isCreator, String xmlFile, List<Incidence> incidences){
		
		AssessmentProfile newProfile = null;
		
		// Open the file and load the data
		try {
			// Set the chapter handler
			List<AssessmentRule> rules = new ArrayList<AssessmentRule>();
			AssessmentHandler assParser = new AssessmentHandler( rules );

			// Create a new factory
			SAXParserFactory factory = SAXParserFactory.newInstance( );
			factory.setValidating( true );
			SAXParser saxParser = factory.newSAXParser( );

			// Parse the data and close the data
			InputStream assessmentIS = isCreator.buildInputStream(xmlFile);
			saxParser.parse( assessmentIS, assParser );
			assessmentIS.close( );
			
			// Finally add the new controller to the list
			// Create the new profile
			
			newProfile = new AssessmentProfile(assParser.getAssessmentRules( ), xmlFile );

		} catch( ParserConfigurationException e ) {
			//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadAssessmentData" ) );
			//e.printStackTrace( );
			incidences.add( Incidence.createAssessmentIncidence( false, TextConstants.getText( "Error.LoadAssessmentData.SAX" ), xmlFile ) );
		} catch( SAXException e ) {
			//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadAssessmentData" ) );
			//e.printStackTrace( );
			incidences.add( Incidence.createAssessmentIncidence( false, TextConstants.getText( "Error.LoadAssessmentData.SAX" ), xmlFile ) );
		} catch( IOException e ) {
			//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadAssessmentData" ) );
			//e.printStackTrace( );
			incidences.add( Incidence.createAssessmentIncidence( false, TextConstants.getText( "Error.LoadAssessmentData.IO" ), xmlFile ) );
		}

		return newProfile;
	}

	/**
	 * Loads the adaptation profile (set of adaptation rules + initial state) stored in file with path xmlFile in zipFile
	 * @param zipFile
	 * @param xmlFile
	 * @param incidences
	 * @return
	 */
	public static AdaptationProfile loadAdaptationProfile (InputStreamCreator isCreator,  String xmlFile, List<Incidence> incidences){
		
		AdaptationProfile newProfile = null;
		
		// Open the file and load the data
		try {
			// Set the chapter handler
			List<AdaptationRule> rules = new ArrayList<AdaptationRule>();
			AdaptedState initialState = new AdaptedState();
			AdaptationHandler adpParser = new AdaptationHandler( rules, initialState );

			// Create a new factory
			SAXParserFactory factory = SAXParserFactory.newInstance( );
			factory.setValidating( true );
			SAXParser saxParser = factory.newSAXParser( );

			// Parse the data and close the data
			InputStream adaptationIS = isCreator.buildInputStream(xmlFile);
			saxParser.parse( adaptationIS, adpParser );
			adaptationIS.close( );
			
			// Finally add the new controller to the list
			// Create the new profile
			
			newProfile = new AdaptationProfile(adpParser.getAdaptationRules(), adpParser.getInitialState(), xmlFile );

		} catch( ParserConfigurationException e ) {
			//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadAdaptationData" ) );
			//e.printStackTrace( );
			incidences.add( Incidence.createAdaptationIncidence( false, TextConstants.getText( "Error.LoadAdaptationData.SAX" ), xmlFile ) );
		} catch( SAXException e ) {
			//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadAdaptationData" ) );
			//e.printStackTrace( );
			incidences.add( Incidence.createAdaptationIncidence( false, TextConstants.getText( "Error.LoadAdaptationData.SAX" ), xmlFile ) );
		} catch( IOException e ) {
			//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadAdaptationData" ) );
			//e.printStackTrace( );
			incidences.add( Incidence.createAdaptationIncidence( false, TextConstants.getText( "Error.LoadAdaptationData.IO" ), xmlFile ) );
		}

		return newProfile;
	}

	
}
