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
import java.io.InputStream;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.data.chapter.conditions.GlobalState;
import es.eucm.eadventure.common.data.chapter.effects.Macro;
import es.eucm.eadventure.common.loader.InputStreamCreator;
import es.eucm.eadventure.common.loader.subparsers.AdaptationSubParser;
import es.eucm.eadventure.common.loader.subparsers.AssessmentSubParser;
import es.eucm.eadventure.common.loader.subparsers.AtrezzoSubParser;
import es.eucm.eadventure.common.loader.subparsers.BookSubParser;
import es.eucm.eadventure.common.loader.subparsers.CharacterSubParser;
import es.eucm.eadventure.common.loader.subparsers.ConditionSubParser;
import es.eucm.eadventure.common.loader.subparsers.CutsceneSubParser;
import es.eucm.eadventure.common.loader.subparsers.EffectSubParser;
import es.eucm.eadventure.common.loader.subparsers.GraphConversationSubParser;
import es.eucm.eadventure.common.loader.subparsers.ItemSubParser;
import es.eucm.eadventure.common.loader.subparsers.PlayerSubParser;
import es.eucm.eadventure.common.loader.subparsers.SceneSubParser;
import es.eucm.eadventure.common.loader.subparsers.SubParser;
import es.eucm.eadventure.common.loader.subparsers.TimerSubParser;
import es.eucm.eadventure.common.loader.subparsers.TreeConversationSubParser;

/**
 * This class is the handler to parse the e-Adventure XML file
 */
public class ChapterHandler extends DefaultHandler {

    /* Attributes */

    /**
     * Constant for subparsing nothing
     */
    private static final int NONE = 0;

    /**
     * Constant for subparsing scene tag
     */
    private static final int SCENE = 1;

    /**
     * Constant for subparsing slidescene tag
     */
    private static final int CUTSCENE = 2;

    /**
     * Constant for subparsing book tag
     */
    private static final int BOOK = 3;

    /**
     * Constant for subparsing object tag
     */
    private static final int OBJECT = 4;

    /**
     * Constant for subparsing player tag
     */
    private static final int PLAYER = 5;

    /**
     * Constant for subparsing character tag
     */
    private static final int CHARACTER = 6;

    /**
     * Constant for subparsing conversation tag
     */
    private static final int CONVERSATION = 7;

    /**
     * Constant for subparsing timer tag
     */
    private static final int TIMER = 8;

    /**
     * Constant for subparsing global-state tag
     */
    private static final int GLOBAL_STATE = 9;

    /**
     * Constant for subparsing macro tag
     */
    private static final int MACRO = 10;

    /**
     * Constant for subparsing atrezzo object tag
     */
    private static final int ATREZZO = 11;

    /**
     * Constant for subparsing assessment tag
     */
    private static final int ASSESSMENT = 12;

    /**
     * Constant for subparsing adaptation tag
     */
    private static final int ADAPTATION = 13;

    /**
     * Stores the current element being parsed
     */
    private int subParsing = NONE;

    /**
     * Current subparser being used
     */
    private SubParser subParser;

    /**
     * Chapter data
     */
    private Chapter chapter;

    /**
     * InputStreamCreator used in resolveEntity to find dtds (only required in
     * Applet mode)
     */
    private InputStreamCreator isCreator;

    /**
     * Current global state being subparsed
     */
    private GlobalState currentGlobalState;

    /**
     * Current macro being subparsed
     */
    private Macro currentMacro;

    /**
     * Buffer for globalstate docs
     */
    private StringBuffer currentString;

    /* Methods */

    /**
     * Default constructor.
     * 
     * @param chapter
     *            Chapter in which the data will be stored
     */
    public ChapterHandler( InputStreamCreator isCreator, Chapter chapter ) {

        this.chapter = chapter;
        this.isCreator = isCreator;
        currentString = new StringBuffer( );
    }

    @Override
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) throws SAXException {

        // If no element is being subparsed, check if we must subparse something
        if( subParsing == NONE ) {

            //Parse eAdventure attributes
            if( qName.equals( "eAdventure" ) ) {
                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getQName( i ).equals( "adaptProfile" ) ) {
                        chapter.setAdaptationName( attrs.getValue( i ) );
                    }
                    if( attrs.getQName( i ).equals( "assessProfile" ) ) {
                        chapter.setAssessmentName( attrs.getValue( i ) );
                    }
                }
            }
            // Subparse scene
            else if( qName.equals( "scene" ) ) {
                subParser = new SceneSubParser( chapter );
                subParsing = SCENE;
            }

            // Subparse slidescene
            else if( qName.equals( "slidescene" ) || qName.equals( "videoscene" ) ) {
                subParser = new CutsceneSubParser( chapter );
                subParsing = CUTSCENE;
            }

            // Subparse book
            else if( qName.equals( "book" ) ) {
                subParser = new BookSubParser( chapter );
                subParsing = BOOK;
            }

            // Subparse object
            else if( qName.equals( "object" ) ) {
                subParser = new ItemSubParser( chapter );
                subParsing = OBJECT;
            }

            // Subparse player
            else if( qName.equals( "player" ) ) {
                subParser = new PlayerSubParser( chapter );
                subParsing = PLAYER;
            }

            // Subparse character
            else if( qName.equals( "character" ) ) {
                subParser = new CharacterSubParser( chapter );
                subParsing = CHARACTER;
            }

            // Subparse conversacion (tree conversation)
            else if( qName.equals( "tree-conversation" ) ) {
                subParser = new TreeConversationSubParser( chapter );
                subParsing = CONVERSATION;
            }

            // Subparse conversation (graph conversation)
            else if( qName.equals( "graph-conversation" ) ) {
                subParser = new GraphConversationSubParser( chapter );
                subParsing = CONVERSATION;
            }

            // Subparse timer
            else if( qName.equals( "timer" ) ) {
                subParser = new TimerSubParser( chapter );
                subParsing = TIMER;
            }

            // Subparse global-state
            else if( qName.equals( "global-state" ) ) {
                String id = null;
                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getQName( i ).equals( "id" ) )
                        id = attrs.getValue( i );
                }
                currentGlobalState = new GlobalState( id );
                currentString = new StringBuffer( );
                chapter.addGlobalState( currentGlobalState );
                subParser = new ConditionSubParser( currentGlobalState, chapter );
                subParsing = GLOBAL_STATE;
            }

            // Subparse macro
            else if( qName.equals( "macro" ) ) {
                String id = null;
                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getQName( i ).equals( "id" ) )
                        id = attrs.getValue( i );
                }
                currentMacro = new Macro( id );
                currentString = new StringBuffer( );
                chapter.addMacro( currentMacro );
                subParser = new EffectSubParser( currentMacro, chapter );
                subParsing = MACRO;
            }
            // Subparse atrezzo object
            else if( qName.equals( "atrezzoobject" ) ) {
                subParser = new AtrezzoSubParser( chapter );
                subParsing = ATREZZO;
            }// Subparse assessment profile
            else if( qName.equals( "assessment" ) ) {
                subParser = new AssessmentSubParser( chapter );
                subParsing = ASSESSMENT;
            }// Subparse adaptation profile
            else if( qName.equals( "adaptation" ) ) {
                subParser = new AdaptationSubParser( chapter );
                subParsing = ADAPTATION;
            }

        }

        // If an element is being subparsed, spread the call
        if( subParsing != NONE ) {
            //try {
            subParser.startElement( namespaceURI, sName, qName, attrs );
            //} catch (Exception e){
            //	System.out.println("Marihuanhell es muy malo pero hemos capturado la excepción");
            //e.printStackTrace();
            //}

        }
    }

    @Override
    public void endElement( String namespaceURI, String sName, String qName ) throws SAXException {

        if( qName.equals( "documentation" ) && subParsing == GLOBAL_STATE ) {
            currentGlobalState.setDocumentation( currentString.toString( ).trim( ) );
        }
        else if( qName.equals( "documentation" ) && subParsing == MACRO ) {
            currentMacro.setDocumentation( currentString.toString( ).trim( ) );
        }

        currentString = new StringBuffer( );

        // If an element is being subparsed
        if( subParsing != NONE ) {

            // Spread the end element call
            subParser.endElement( namespaceURI, sName, qName );

            // If the element is not being subparsed anymore, return to normal state
            if( qName.equals( "scene" ) && subParsing == SCENE || ( qName.equals( "slidescene" ) || qName.equals( "videoscene" ) ) && subParsing == CUTSCENE || qName.equals( "book" ) && subParsing == BOOK || qName.equals( "object" ) && subParsing == OBJECT || qName.equals( "player" ) && subParsing == PLAYER || qName.equals( "character" ) && subParsing == CHARACTER || qName.equals( "tree-conversation" ) && subParsing == CONVERSATION || qName.equals( "graph-conversation" ) && subParsing == CONVERSATION || qName.equals( "timer" ) && subParsing == TIMER || qName.equals( "global-state" ) && subParsing == GLOBAL_STATE || qName.equals( "macro" ) && subParsing == MACRO || qName.equals( "atrezzoobject" ) && subParsing == ATREZZO || qName.equals( "assessment" ) && subParsing == ASSESSMENT || qName.equals( "adaptation" ) && subParsing == ADAPTATION ) {
                subParsing = NONE;
            }

        }
    }

    @Override
    public void endDocument( ) {

        // In the end of the document, if the chapter has no initial scene
        if( chapter.getTargetId( ) == null ) {
            // Set it to the first scene
            if( chapter.getScenes( ).size( ) > 0 )
                chapter.setTargetId( chapter.getScenes( ).get( 0 ).getId( ) );

            // Or to the first cutscene
            else if( chapter.getCutscenes( ).size( ) > 0 )
                chapter.setTargetId( chapter.getCutscenes( ).get( 0 ).getId( ) );
        }
    }

    @Override
    public void characters( char[] buf, int offset, int len ) throws SAXException {

        // If the SAX handler is reading an element, just spread the call to the parser
        currentString.append( new String( buf, offset, len ) );
        if( subParsing != NONE ) {
            subParser.characters( buf, offset, len );
        }
    }

    @Override
    public void error( SAXParseException exception ) throws SAXParseException {

        // On validation, propagate exception
        exception.printStackTrace( );
        throw exception;
    }

    /*	@Override
    	public InputSource resolveEntity( String publicId, String systemId ) {
    		// Take the name of the file SAX is looking for
    		int startFilename = systemId.lastIndexOf( "/" ) + 1;
    		String filename = systemId.substring( startFilename, systemId.length( ) );

    		// Create the input source to return
    		InputSource inputSource = null;

    		try {
    			// If the file is eadventure.dtd, use the one in the editor's folder
    			if( filename.toLowerCase( ).equals( "eadventure.dtd" ) )
    				inputSource = new InputSource( new FileInputStream( filename ) );

    			// If it is any other file, use the super's method
    			else
    				inputSource = super.resolveEntity( publicId, systemId );
    		} catch( FileNotFoundException e ) {
    			e.printStackTrace( );
    		} catch( IOException e ) {
    			e.printStackTrace( );
    		} catch( SAXException e ) {
    			e.printStackTrace( );
    		}

    		return inputSource;
    	}*/

    /*
     *  (non-Javadoc)
     * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
     */
    @Override
    public InputSource resolveEntity( String publicId, String systemId ) {

        // Take the name of the file SAX is looking for
        int startFilename = systemId.lastIndexOf( "/" ) + 1;
        String filename = systemId.substring( startFilename, systemId.length( ) );

        // Build and return a input stream with the file (usually the DTD): 
        // 1) First try looking at main folder
        InputStream inputStream = AdaptationHandler.class.getResourceAsStream( filename );
        if( inputStream == null ) {
            try {
                inputStream = new FileInputStream( filename );
            }
            catch( FileNotFoundException e ) {
                inputStream = null;
            }
        }

        // 2) Secondly use the inputStreamCreator
        if( inputStream == null )
            inputStream = isCreator.buildInputStream( filename );

        return new InputSource( inputStream );
    }

    @Override
    public void fatalError( SAXParseException e ) throws SAXException {

        //throw e;
    }
}
