/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
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
import es.eucm.eadventure.common.gui.TC;
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
    	 * Constant with the assessment folder path
    	 */
    	private static final String assessmentFolderPath = "assessment";
    
    	/**
    	 * Constant with the adaptation folder path
    	 */
    	private static final String adaptationFolderPath = "adaptation";
    
    	
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
	
	/**
	 * To validate or not the XML with DTD
	 * 
	 */
	private boolean validate;
	
	private static void getXMLFilePaths (InputStreamCreator isCreator,List<String> assessmentPaths, List<String> adaptationPaths){

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
	public AdventureHandler(  InputStreamCreator isCreator, List<Incidence> incidences, boolean validate ) {
		this.isCreator = isCreator;
		assessmentPaths = new ArrayList<String>();
		adaptationPaths = new ArrayList<String>();
		getXMLFilePaths(isCreator,assessmentPaths, adaptationPaths );
		
		adventureData = new AdventureData( );
		this.incidences = incidences;
		chapters = new ArrayList<Chapter>( );
		this.validate = validate;
		//this.assessmentController = adventureData.getAssessmentProfiles();
		//this.adaptationController = adventureData.getAdaptationProfiles();
		
		
	}
	
	/**
	 * Load the assessment and adaptation profiles from xml.
	 * 
	 */
	//This method must be called after all chapter data is parse, because is a past functionality, and must be preserved in order
	// to bring the possibility to load game of past versions. Now the adaptation and assessment profiles are into chapter.xml, and not 
	// in separate files.
	public void loadProfiles(){
	
	    //check if in chapter.xml there was any assessment or adaptation data
	    if (!adventureData.hasAdapOrAssesData()) {
	    
	    // Load all the assessment files in each chapter
		for (String assessmentPath : assessmentPaths){
		    boolean added = false;
		    AssessmentProfile assessProfile = Loader.loadAssessmentProfile ( isCreator, assessmentPath, incidences) ;
		    if (assessProfile!=null){	
		    for (Chapter chapter : adventureData.getChapters()){
			if (chapter.getAssessmentName().equals(assessProfile.getName())){
			    chapter.addAssessmentProfile(assessProfile);
			    added=true;
			}
			}
		    if (!added){
			for (Chapter chapter : adventureData.getChapters()){
				    chapter.addAssessmentProfile(assessProfile);
				}
		    }
		    
		    
		    }
		}
		
		// Load all the adaptation files in each chapter
		for (String adaptationPath: adaptationPaths){
		    boolean added=false;
		    AdaptationProfile adaptProfile= Loader.loadAdaptationProfile( isCreator, adaptationPath, incidences) ;
		    if (adaptProfile!=null){
			for (Chapter chapter : adventureData.getChapters()){
				if (chapter.getAdaptationName().equals(adaptProfile.getName())){
				    chapter.addAdaptationProfile(adaptProfile);
				    added=true;
				}
				}
			    if (!added){
				for (Chapter chapter : adventureData.getChapters()){
					    chapter.addAdaptationProfile(adaptProfile);
					}
			    }
		}
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

		if (qName.equals( "game-descriptor" )){
		    for( int i = 0; i < attrs.getLength( ); i++ )
			if( attrs.getQName( i ).equals( "versionNumber" ) ){
			    adventureData.setVersionNumber(attrs.getValue(i));
			}
		}
		    
	    
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
				ChapterHandler chapterParser = new ChapterHandler( isCreator, currentChapter);

				// Create a new factory
				SAXParserFactory factory = SAXParserFactory.newInstance( );
				factory.setValidating( validate );
				SAXParser saxParser = factory.newSAXParser( );

				// Set the input stream with the file
				InputStream chapterIS = isCreator.buildInputStream( chapterPath );

				// Parse the data and close the data
				saxParser.parse( chapterIS, chapterParser );
				chapterIS.close( );

			} catch( ParserConfigurationException e ) {
				incidences.add( Incidence.createChapterIncidence( TC.get( "Error.LoadData.SAX" ), chapterPath , e) );
			} catch( SAXException e ) {
			    	incidences.add( Incidence.createChapterIncidence( TC.get( "Error.LoadData.SAX" ), chapterPath , e) );
			} catch( IOException e ) {
				incidences.add( Incidence.createChapterIncidence( TC.get( "Error.LoadData.IO" ), chapterPath, e) );
			}

		}
		// If reading the adaptation configuration, store it
		// With last profile modifications, only old games includes that information in its descriptor file.
        	// For that reason, the next "path" info is the name of the profile, and it is necessary to eliminate the path's characteristic
        	// such as / and .xml
		else if( qName.equals( "adaptation-configuration" ) ) {
			for( int i = 0; i < attrs.getLength( ); i++ )
				if( attrs.getQName( i ).equals( "path" ) ){
					String adaptationName = attrs.getValue( i );
					// delete the path's characteristics
					adaptationName = adaptationName.substring(adaptationName.indexOf("/")+1);
					adaptationName = adaptationName.substring(0,adaptationName.indexOf("."));
					currentChapter.setAdaptationName( adaptationName );
					// Search in incidences. If an adaptation incidence was related to this profile, the error is more relevant
					for (int j=0; j<incidences.size( ); j++){
						Incidence current = incidences.get( j );
						if (current.getAffectedArea( ) == Incidence.ADAPTATION_INCIDENCE && current.getAffectedResource( ).equals( adaptationName )){
							String message = current.getMessage( );
							incidences.remove( j );
							incidences.add( j, Incidence.createAdaptationIncidence( true, message+TC.get( "Error.LoadAdaptation.Referenced" ), adaptationName , null) );
						}
					}
				}
		}
		// If reading the assessment configuration, store it
        	// With last profile modifications, only old games includes that information in its descriptor file.
		// For that reason, the next "path" info is the name of the profile, and it is necessary to eliminate the path's characteristic
		// such as / and .xml
		else if( qName.equals( "assessment-configuration" ) ) {
			for( int i = 0; i < attrs.getLength( ); i++ )
				if( attrs.getQName( i ).equals( "path" ) ){
					String assessmentName = attrs.getValue( i );
					// delete the path's characteristics
					assessmentName = assessmentName.substring(assessmentName.indexOf("/")+1);
					assessmentName = assessmentName.substring(0,assessmentName.indexOf("."));
					currentChapter.setAssessmentName( assessmentName );
					// Search in incidences. If an adaptation incidence was related to this profile, the error is more relevant
					for (int j=0; j<incidences.size( ); j++){
						Incidence current = incidences.get( j );
						if (current.getAffectedArea( ) == Incidence.ASSESSMENT_INCIDENCE && current.getAffectedResource( ).equals( assessmentName )){
							String message = current.getMessage( );
							incidences.remove( j );
							incidences.add( j, Incidence.createAssessmentIncidence( true, message+TC.get( "Error.LoadAssessment.Referenced" ), assessmentName ,null) );
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
