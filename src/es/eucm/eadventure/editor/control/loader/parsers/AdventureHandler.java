package es.eucm.eadventure.editor.control.loader.parsers;

import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.FileInputStream;

import es.eucm.eadventure.common.data.adaptation.AdaptationRule;
import es.eucm.eadventure.common.data.adaptation.AdaptedState;
import es.eucm.eadventure.common.data.assessment.AssessmentRule;
import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.auxiliar.File;
import es.eucm.eadventure.editor.control.controllers.AdventureDataControl;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfilesDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfilesDataControl;
import es.eucm.eadventure.editor.control.loader.incidences.Incidence;
import es.eucm.eadventure.editor.gui.TextConstants;

/**
 * This class is the handler to parse the e-Adventure descriptor file.
 * 
 * @author Bruno Torijano Bueno
 */
public class AdventureHandler extends DefaultHandler {

	/**
	 * Constant for reading nothing.
	 */
	private static final int READING_NONE = 0;

	/**
	 * Constant for reading a chapter.
	 */
	private static final int READING_CHAPTER = 1;

	/**
	 * Stores the current element being read.
	 */
	private int reading = READING_NONE;

	/**
	 * Stores the zip path in which the chapters must be searched.
	 */
	private String zipFile;

	/**
	 * Adventure data being read.
	 */
	private AdventureDataControl adventureData;
	
	/**
	 * List of incidences
	 */
	private List<Incidence> incidences;

	/**
	 * List of chapters of the adventure.
	 */
	private List<Chapter> chapters;
	
	/**
	 * Assessment controller: to be filled with the assessment data
	 */ 
	private AssessmentProfilesDataControl assessmentController;
	
	/**
	 * Adaptation controller: to be filled with the adaptation data
	 */
	private AdaptationProfilesDataControl adaptationController;

	/**
	 * Chapter being currently read.
	 */
	private Chapter currentChapter;

	/**
	 * String to store the current string in the XML file
	 */
	protected StringBuffer currentString;

	private static void getXMLFilePaths (String zipFile, List<String> assessmentPaths, List<String> adaptationPaths){
		File assessmentFolder = new File(zipFile, AssetsController.getCategoryFolder(AssetsController.CATEGORY_ASSESSMENT));
		File adaptationFolder = new File(zipFile, AssetsController.getCategoryFolder(AssetsController.CATEGORY_ADAPTATION));

		String completeZipPath = zipFile;
		if (!(zipFile.endsWith( "/" ) || zipFile.endsWith( "\\" ))){
			completeZipPath +=File.separator;
		}
		
		if (assessmentFolder.isDirectory( ) && assessmentFolder.exists( )){
		for (java.io.File file: assessmentFolder.listFiles( new FileFilter(){
			public boolean accept( java.io.File pathname ) {
				return pathname.getAbsolutePath( ).toLowerCase( ).endsWith( "xml" );
			}
		})){
			// Check the file is not empty
			if (file.length( )!=0){
				assessmentPaths.add( file.getAbsolutePath( ).substring( file.getAbsolutePath( ).indexOf( completeZipPath)+completeZipPath.length( )) );
			}
			else {
				file.delete( );
			}
		}}
		
		if (adaptationFolder.isDirectory( ) && assessmentFolder.exists( )){
		for (java.io.File file: adaptationFolder.listFiles( new FileFilter(){
			public boolean accept( java.io.File pathname ) {
				return pathname.getAbsolutePath( ).toLowerCase( ).endsWith( "xml" );
			}
		})){
			// Check the file is not empty
			if (file.length( )!=0){
				adaptationPaths.add( file.getAbsolutePath( ).substring( file.getAbsolutePath( ).indexOf( completeZipPath)+completeZipPath.length( )) );
			}
			else {
				file.delete( );
			}
		}}

	}

	/**
	 * Constructor.
	 * 
	 * @param zipFile
	 *            Path to the zip file which helds the chapter files
	 */
	public AdventureHandler( String zipFile, List<Incidence> incidences ) {
		this.zipFile = zipFile;
		
		List<String> assessmentPaths = new ArrayList<String>();
		List<String> adaptationPaths = new ArrayList<String>();
		getXMLFilePaths(zipFile, assessmentPaths, adaptationPaths);
		
		adventureData = new AdventureDataControl( );
		this.incidences = incidences;
		chapters = new ArrayList<Chapter>( );
		this.assessmentController = adventureData.getAssessmentRulesListDataControl( );
		this.adaptationController = adventureData.getAdaptationRulesListDataControl( );
		
		// Load all the assessment files
		for (String assessmentPath : assessmentPaths){
			// Open the file and load the data
			try {
				// Set the chapter handler
				List<AssessmentRule> rules = new ArrayList<AssessmentRule>();
				AssessmentHandler assParser = new AssessmentHandler( rules );

				// Create a new factory
				SAXParserFactory factory = SAXParserFactory.newInstance( );
				factory.setValidating( true );
				SAXParser saxParser = factory.newSAXParser( );

				// Set the input stream with the file
				InputStream chapterIS = new FileInputStream( zipFile + "/" + assessmentPath );

				// Parse the data and close the data
				saxParser.parse( chapterIS, assParser );
				chapterIS.close( );
				
				// Finally add the new controller to the list
				// Create the new profile
				
				AssessmentProfileDataControl newProfile = new AssessmentProfileDataControl(assParser.getAssessmentRules( ), assessmentPath);
				assessmentController.getProfiles( ).add( newProfile );

			} catch( ParserConfigurationException e ) {
				//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadAssessmentData" ) );
				//e.printStackTrace( );
				incidences.add( Incidence.createAssessmentIncidence( false, TextConstants.getText( "Error.LoadAssessmentData.SAX" ), assessmentPath ) );
			} catch( SAXException e ) {
				//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadAssessmentData" ) );
				//e.printStackTrace( );
				incidences.add( Incidence.createAssessmentIncidence( false, TextConstants.getText( "Error.LoadAssessmentData.SAX" ), assessmentPath ) );
			} catch( IOException e ) {
				//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadAssessmentData" ) );
				//e.printStackTrace( );
				incidences.add( Incidence.createAssessmentIncidence( false, TextConstants.getText( "Error.LoadAssessmentData.IO" ), assessmentPath ) );
			}

		}
		
		// Load all the adaptation files
		for (String adaptationPath: adaptationPaths){
			// Open the file and load the data
			try {
				// Create the new profile
				List<AdaptationRule> rules = new ArrayList<AdaptationRule>();
				AdaptedState initialState = new AdaptedState();
				
				// Set the chapter handler
				AdaptationHandler adpParser = new AdaptationHandler( rules, initialState );

				// Create a new factory
				SAXParserFactory factory = SAXParserFactory.newInstance( );
				factory.setValidating( true );
				SAXParser saxParser = factory.newSAXParser( );

				// Set the input stream with the file
				InputStream chapterIS = new FileInputStream( zipFile + "/" + adaptationPath );

				// Parse the data and close the data
				saxParser.parse( chapterIS, adpParser );
				chapterIS.close( );
				
				// Finally add the new controller to the list
				AdaptationProfileDataControl newProfile = new AdaptationProfileDataControl(adpParser.getAdaptationRules( ), adpParser.getInitialState( ), adaptationPath);
				adaptationController.getProfiles( ).add( newProfile );

			} catch( ParserConfigurationException e ) {
				//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadAdaptationData" ) );
				//e.printStackTrace( );
				incidences.add( Incidence.createAdaptationIncidence( false, TextConstants.getText( "Error.LoadAdaptationData.SAX" ), adaptationPath ) );
			} catch( SAXException e ) {
				//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadAdaptationData" ) );
				//e.printStackTrace( );
				incidences.add( Incidence.createAdaptationIncidence( false, TextConstants.getText( "Error.LoadAdaptationData.SAX" ), adaptationPath ) );
			} catch( IOException e ) {
				//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadAdaptationData" ) );
				//e.printStackTrace( );
				incidences.add( Incidence.createAdaptationIncidence( false, TextConstants.getText( "Error.LoadAdaptationData.IO" ), adaptationPath ) );
			}
		}
	}

	/**
	 * Returns the adventure data read
	 * 
	 * @return The adventure data from the XML descriptor
	 */
	public AdventureDataControl getAdventureData( ) {
		return adventureData;
	}

	@Override
	public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) throws SAXException {

		// If reading a title, empty the current string
		if( qName.equals( "title" ) || qName.equals( "description" ) ) {
			currentString = new StringBuffer( );
		}

		if (qName.endsWith("automatic-commentaries")) {
			adventureData.setCommentaries(true);
		}
		
		// If reading the GUI tag, store the settings
		if( qName.equals( "gui" ) ) {
			for( int i = 0; i < attrs.getLength( ); i++ )
				if( attrs.getQName( i ).equals( "type" ) )
					if( attrs.getValue( i ).equals( "traditional" ) )
						adventureData.setGUIType( AdventureDataControl.GUI_TRADITIONAL );
					else if( attrs.getValue( "type" ).equals( "contextual" ) )
						adventureData.setGUIType( AdventureDataControl.GUI_CONTEXTUAL );
		}
		
	       //Cursor
        if (qName.equals( "cursor" )){
            String type="";String uri="";
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if (attrs.getQName( i ).equals( "type" )){
                    type=attrs.getValue( i );
                }else if (attrs.getQName( i ).equals( "uri" )){
                    uri=attrs.getValue( i );
                }
            }
            adventureData.addCursor( type, uri );
        }		

		// If reading the mode tag:
		if( qName.equals( "mode" ) ) {
			for( int i = 0; i < attrs.getLength( ); i++ )
				if( attrs.getQName( i ).equals( "playerTransparent" ) )
					if( attrs.getValue( i ).equals( "yes" ) )
						adventureData.setPlayerMode( AdventureDataControl.PLAYER_1STPERSON );
					else if( attrs.getValue( i ).equals( "no" ) )
						adventureData.setPlayerMode( AdventureDataControl.PLAYER_3RDPERSON );
		}

		// If reading the contents tag, switch to the chapters mode
		else if( qName.equals( "contents" ) ) {
			reading = READING_CHAPTER;
		}

		// If reading the contents of a chapter, create a new one to store the data
		else if( qName.equals( "chapter" ) ) {
			// Create the chapter
			currentChapter = new Chapter( );

			// Search and store the path of the file
			String chapterPath = null;
			for( int i = 0; i < attrs.getLength( ); i++ )
				if( attrs.getQName( i ).equals( "path" ) )
					chapterPath = attrs.getValue( i );
			
			if (chapterPath!=null){
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
				InputStream chapterIS = new FileInputStream( zipFile + "/" + chapterPath );

				// Parse the data and close the data
				saxParser.parse( chapterIS, chapterParser );
				chapterIS.close( );

			} catch( ParserConfigurationException e ) {
				//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadData" ) );
				//e.printStackTrace( );
				incidences.add( Incidence.createChapterIncidence( TextConstants.getText( "Error.LoadData.SAX" ), chapterPath ) );
			} catch( SAXException e ) {
				//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadData" ) );
				//e.printStackTrace( );
				incidences.add( Incidence.createChapterIncidence( TextConstants.getText( "Error.LoadData.SAX" ), chapterPath ) );
			} catch( IOException e ) {
				//Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.LoadData" ) );
				//e.printStackTrace( );
				incidences.add( Incidence.createChapterIncidence( TextConstants.getText( "Error.LoadData.IO" ), chapterPath ) );
			}

		}

		// If reading the adaptation configuration, store it
		else if( qName.equals( "adaptation-configuration" ) ) {
			for( int i = 0; i < attrs.getLength( ); i++ )
				if( attrs.getQName( i ).equals( "path" ) ){
					String adaptationPath = attrs.getValue( i );
					currentChapter.setAdaptationPath( adaptationPath );
					// Search in incidences. If an adaptation incidence was related to this profile, the error is more relevant
					for (int j=0; j<incidences.size( ); j++){
						Incidence current = incidences.get( j );
						if (current.getAffectedArea( ) == Incidence.ADAPTATION_INCIDENCE && current.getAffectedResource( ).equals( adaptationPath )){
							String message = current.getMessage( );
							incidences.remove( j );
							incidences.add( j, Incidence.createAdaptationIncidence( true, message+TextConstants.getText( "Error.LoadAdaptation.Referenced" ), adaptationPath ) );
						}
					}
				}
		}

		// If reading the assessment configuration, store it
		else if( qName.equals( "assessment-configuration" ) ) {
			for( int i = 0; i < attrs.getLength( ); i++ )
				if( attrs.getQName( i ).equals( "path" ) ){
					String assessmentPath = attrs.getValue( i );
					currentChapter.setAssessmentPath( assessmentPath );
					// Search in incidences. If an adaptation incidence was related to this profile, the error is more relevant
					for (int j=0; j<incidences.size( ); j++){
						Incidence current = incidences.get( j );
						if (current.getAffectedArea( ) == Incidence.ASSESSMENT_INCIDENCE && current.getAffectedResource( ).equals( assessmentPath )){
							String message = current.getMessage( );
							incidences.remove( j );
							incidences.add( j, Incidence.createAssessmentIncidence( true, message+TextConstants.getText( "Error.LoadAssessment.Referenced" ), assessmentPath ) );
						}
					}

				}
		}
	}

	@Override
	public void endElement( String namespaceURI, String sName, String qName ) throws SAXException {

		// If the title is complete, store it
		if( qName.equals( "title" ) ) {
			// Store it in the adventure data
			if( reading == READING_NONE )
				adventureData.setTitle( currentString.toString( ).trim( ) );

			// Or in the chapter
			else if( reading == READING_CHAPTER )
				currentChapter.setTitle( currentString.toString( ).trim( ) );
		}

		// If the description is complete, store it
		else if( qName.equals( "description" ) ) {
			// Store it in the adventure data
			if( reading == READING_NONE )
				adventureData.setDescription( currentString.toString( ).trim( ) );

			// Or in the chapter
			else if( reading == READING_CHAPTER )
				currentChapter.setDescription( currentString.toString( ).trim( ) );
		}

		// If the list of chapters is closing, store it
		else if( qName.equals( "contents" ) ) {
			adventureData.setChapters( chapters );
		}

		// If a chapter is closing, store it in the list
		else if( qName.equals( "chapter" ) ) {
			chapters.add( currentChapter );
		}
	}

	@Override
	public void characters( char[] buf, int offset, int len ) throws SAXException {
		// Append the new characters
		currentString.append( new String( buf, offset, len ) );
	}

	@Override
	public void error( SAXParseException exception ) throws SAXParseException {
		// On validation, propagate exception
		exception.printStackTrace( );
		throw exception;
	}

	@Override
	public InputSource resolveEntity( String publicId, String systemId ) throws FileNotFoundException {
		// Take the name of the file SAX is looking for
		int startFilename = systemId.lastIndexOf( "/" ) + 1;
		String filename = systemId.substring( startFilename, systemId.length( ) );

		// Create the input source to return
		InputSource inputSource = null;

		try {
			// If the file is descriptor.dtd, use the one in the editor's folder
			if( filename.toLowerCase( ).equals( "descriptor.dtd" ) )
				inputSource = new InputSource( new FileInputStream( filename ) );

			// If it is any other file, use the super's method
			else
				inputSource = super.resolveEntity( publicId, systemId );
		} catch( IOException e ) {
			e.printStackTrace( );
		} catch( SAXException e ) {
			e.printStackTrace( );
		}

		return inputSource;
	}

	/**
	 * @return the incidences
	 */
	public List<Incidence> getIncidences( ) {
		return incidences;
	}
}
