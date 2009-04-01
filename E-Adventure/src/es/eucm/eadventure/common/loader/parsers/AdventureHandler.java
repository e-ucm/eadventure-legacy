package es.eucm.eadventure.common.loader.parsers;

import java.io.FileInputStream;
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

import es.eucm.eadventure.common.data.adaptation.AdaptationProfile;
import es.eucm.eadventure.common.data.adventure.AdventureData;
import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.common.data.assessment.AssessmentProfile;
import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.common.loader.InputStreamCreator;
import es.eucm.eadventure.common.loader.Loader;
import es.eucm.eadventure.common.loader.incidences.Incidence;

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
	 * Adventure data being read.
	 */
	private AdventureData adventureData;
	
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
	//private List<AssessmentProfile> assessmentController;
	
	/**
	 * Adaptation controller: to be filled with the adaptation data
	 */
	//private List<AdaptationProfile> adaptationController;

	/**
	 * Chapter being currently read.
	 */
	private Chapter currentChapter;

	/**
	 * String to store the current string in the XML file
	 */
	protected StringBuffer currentString;
	
	private InputStreamCreator isCreator;
	
	/**
	 * The paths of assessments files
	 */
	private List<String> assessmentPaths;
	
	/**
	 * The paths of adaptation files
	 */
	private List<String> adaptationPaths;
	
	private static void getXMLFilePaths (InputStreamCreator isCreator, String assessmentFolderPath, String adaptationFolderPath, List<String> assessmentPaths, List<String> adaptationPaths){

		// Assessment
			for ( String child: isCreator.listNames(assessmentFolderPath)){
				if (child.toLowerCase().endsWith(".xml")){
					assessmentPaths.add( assessmentFolderPath+"/"+child );					
				}
			}
		
		// Adaptation
			
			for ( String child: isCreator.listNames(adaptationFolderPath)){
				if (child.toLowerCase().endsWith(".xml")){
					adaptationPaths.add( adaptationFolderPath+"/"+child );					
				}
			}
	}

	/**
	 * Constructor.
	 * 
	 * @param zipFile
	 *            Path to the zip file which helds the chapter files
	 */
	public AdventureHandler(  InputStreamCreator isCreator, String assessmentFolder, String adaptationFolder, List<Incidence> incidences ) {
		this.isCreator = isCreator;
		assessmentPaths = new ArrayList<String>();
		adaptationPaths = new ArrayList<String>();
		getXMLFilePaths(isCreator, assessmentFolder, adaptationFolder, assessmentPaths, adaptationPaths );
		
		adventureData = new AdventureData( );
		this.incidences = incidences;
		chapters = new ArrayList<Chapter>( );
		//this.assessmentController = adventureData.getAssessmentProfiles();
		//this.adaptationController = adventureData.getAdaptationProfiles();
		
		
	}
	
	/**
	 * Load the assessment and adaptation profiles from xml.
	 * 
	 */
	//This method must be called after all chapter data is parse, because is a past funtionality, and must be preserved in order
	// to bringh the posibility to load game of past versions. Now the adaptation and assessment profiles are into chapter.xml, and not 
	// in separate files.
	public void loadProfiles(){
	 // Load all the assessment files in each chapter
		for (String assessmentPath : assessmentPaths){
		    
		    AssessmentProfile assessProfile = Loader.loadAssessmentProfile ( isCreator, assessmentPath, incidences) ;
			for (Chapter chapter : adventureData.getChapters()){
			    chapter.addAssessmentProfile(assessProfile);
			}
		}
		
		// Load all the adaptation files in each chapter
		for (String adaptationPath: adaptationPaths){
		    AdaptationProfile adaptProfile= Loader.loadAdaptationProfile( isCreator, adaptationPath, incidences) ;
		    for (Chapter chapter : adventureData.getChapters()){
			    chapter.addAdaptationProfile(adaptProfile);
			}
		}
	}

	/**
	 * Returns the adventure data read
	 * 
	 * @return The adventure data from the XML descriptor
	 */
	public AdventureData getAdventureData( ) {
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
			for( int i = 0; i < attrs.getLength( ); i++ ) {
				if( attrs.getQName( i ).equals( "type" ) ) {
					if( attrs.getValue( i ).equals( "traditional" ) )
						adventureData.setGUIType( DescriptorData.GUI_TRADITIONAL );
					else if( attrs.getValue( "type" ).equals( "contextual" ) )
						adventureData.setGUIType( DescriptorData.GUI_CONTEXTUAL );
				}
				if (attrs.getQName( i ).equals( "customized" )) {
					if (attrs.getValue(i).equals("yes"))
						adventureData.setGUI(adventureData.getGUIType(), true);
					else
						adventureData.setGUI(adventureData.getGUIType(), false);
				}
				if (attrs.getQName( i ).equals( "inventoryPosition" )) {
					if (attrs.getValue(i).equals("none"))
						adventureData.setInventoryPosition(DescriptorData.INVENTORY_NONE);
					else if (attrs.getValue(i).equals("top_bottom"))
						adventureData.setInventoryPosition(DescriptorData.INVENTORY_TOP_BOTTOM);
					else if (attrs.getValue(i).equals("top"))
						adventureData.setInventoryPosition(DescriptorData.INVENTORY_TOP);
					else if (attrs.getValue(i).equals("bottom"))
						adventureData.setInventoryPosition(DescriptorData.INVENTORY_BOTTOM);
				}
			}
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

        //Button
        if (qName.equals( "button" )){
            String type="";String uri=""; String action ="";
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if (attrs.getQName( i ).equals( "type" )){
                    type=attrs.getValue( i );
                }else if (attrs.getQName( i ).equals( "uri" )){
                    uri=attrs.getValue( i );
                }else if (attrs.getQName( i ).equals( "action" )){
                    action=attrs.getValue( i );
                }
            }
            adventureData.addButton( action, type, uri );
        }
        
        if (qName.equals( "arrow" )) {
        	String type="";String uri="";
        	for (int i = 0; i < attrs.getLength(); i++) {
               if (attrs.getQName( i ).equals( "type" )){
                  type=attrs.getValue( i );
               }else if (attrs.getQName( i ).equals( "uri" )){
                  uri=attrs.getValue( i );
               }
        	}
        	adventureData.addArrow( type, uri);
        }
        
		// If reading the mode tag:
		if( qName.equals( "mode" ) ) {
			for( int i = 0; i < attrs.getLength( ); i++ )
				if( attrs.getQName( i ).equals( "playerTransparent" ) )
					if( attrs.getValue( i ).equals( "yes" ) )
						adventureData.setPlayerMode( DescriptorData.MODE_PLAYER_1STPERSON );
					else if( attrs.getValue( i ).equals( "no" ) )
						adventureData.setPlayerMode( DescriptorData.MODE_PLAYER_3RDPERSON );
		}
		
        if (qName.equals("graphics")){
        	for( int i = 0; i < attrs.getLength(); i++) {
        		if (attrs.getQName( i ).equals("mode")) {
        			if (attrs.getValue( i ).equals( "windowed")) {
        				adventureData.setGraphicConfig(DescriptorData.GRAPHICS_WINDOWED);
        			}
        			else if (attrs.getValue( i ).equals( "fullscreen" )) {
        				adventureData.setGraphicConfig(DescriptorData.GRAPHICS_FULLSCREEN);
        			}
        			else if (attrs.getValue( i ).equals( "blackbkg") ) {
        				adventureData.setGraphicConfig(DescriptorData.GRAPHICS_BLACKBKG);
        			}
        		}
        	}
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
				currentChapter.setChapterPath( chapterPath );
			} else 
				currentChapter.setChapterPath( "" );

			// Open the file and load the data
			try {
				// Set the chapter handler
				ChapterHandler chapterParser = new ChapterHandler( isCreator, currentChapter );

				// Create a new factory
				SAXParserFactory factory = SAXParserFactory.newInstance( );
				factory.setValidating( true );
				SAXParser saxParser = factory.newSAXParser( );

				// Set the input stream with the file
				InputStream chapterIS = isCreator.buildInputStream( chapterPath );

				// Parse the data and close the data
				saxParser.parse( chapterIS, chapterParser );
				chapterIS.close( );

			} catch( ParserConfigurationException e ) {
				incidences.add( Incidence.createChapterIncidence( TextConstants.getText( "Error.LoadData.SAX" ), chapterPath ) );
			} catch( SAXException e ) {
			    	incidences.add( Incidence.createChapterIncidence( TextConstants.getText( "Error.LoadData.SAX" ), chapterPath ) );
			} catch( IOException e ) {
				incidences.add( Incidence.createChapterIncidence( TextConstants.getText( "Error.LoadData.IO" ), chapterPath ) );
			}

		}
        	//TODO que pasa con tu wasa!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!77
		// If reading the adaptation configuration, store it
		else if( qName.equals( "adaptation-configuration" ) ) {
			for( int i = 0; i < attrs.getLength( ); i++ )
				if( attrs.getQName( i ).equals( "path" ) ){
					String adaptationName = attrs.getValue( i );
					currentChapter.setAdaptationName( adaptationName );
					// Search in incidences. If an adaptation incidence was related to this profile, the error is more relevant
					for (int j=0; j<incidences.size( ); j++){
						Incidence current = incidences.get( j );
						if (current.getAffectedArea( ) == Incidence.ADAPTATION_INCIDENCE && current.getAffectedResource( ).equals( adaptationName )){
							String message = current.getMessage( );
							incidences.remove( j );
							incidences.add( j, Incidence.createAdaptationIncidence( true, message+TextConstants.getText( "Error.LoadAdaptation.Referenced" ), adaptationName ) );
						}
					}
				}
		}

        	//TODO que pasa con tu wasa!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!77
		// If reading the assessment configuration, store it
		else if( qName.equals( "assessment-configuration" ) ) {
			for( int i = 0; i < attrs.getLength( ); i++ )
				if( attrs.getQName( i ).equals( "path" ) ){
					String assessmentName = attrs.getValue( i );
					currentChapter.setAssessmentName( assessmentName );
					// Search in incidences. If an adaptation incidence was related to this profile, the error is more relevant
					for (int j=0; j<incidences.size( ); j++){
						Incidence current = incidences.get( j );
						if (current.getAffectedArea( ) == Incidence.ASSESSMENT_INCIDENCE && current.getAffectedResource( ).equals( assessmentName )){
							String message = current.getMessage( );
							incidences.remove( j );
							incidences.add( j, Incidence.createAssessmentIncidence( true, message+TextConstants.getText( "Error.LoadAssessment.Referenced" ), assessmentName ) );
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
		throw exception;
	}

/*	@Override
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
	}*/

	/**
	 * @return the incidences
	 */
	public List<Incidence> getIncidences( ) {
		return incidences;
	}
	
    /*
     *  (non-Javadoc)
     * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
     */
    public InputSource resolveEntity( String publicId, String systemId ) {
        // Take the name of the file SAX is looking for
        int startFilename = systemId.lastIndexOf( "/" ) + 1;
        String filename = systemId.substring( startFilename, systemId.length( ) );
        
        // Build and return a input stream with the file (usually the DTD): 
        // 1) First try looking at main folder
        InputStream inputStream = AdventureHandler.class.getResourceAsStream( filename );
        if ( inputStream==null ){
        	try {
				inputStream = new FileInputStream ( filename );
			} catch (FileNotFoundException e) {
				inputStream = null;
			}
        }
        
        // 2) Secondly use the inputStreamCreator
        if ( inputStream == null)
        	inputStream = isCreator.buildInputStream( filename );
        
        return new InputSource( inputStream );
    }

}
