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

import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.loader.InputStreamCreator;
import es.eucm.eadventure.common.loader.subparsers.FrameSubParser;
import es.eucm.eadventure.common.loader.subparsers.TransitionSubParser;

public class AnimationHandler extends DefaultHandler {

    /**
     * String to store the current string in the XML file
     */
    StringBuffer currentString;

    /**
     * Resources to store the current resources being read
     */
    Resources currentResources;

    /**
     * Constant for reading nothing
     */
    private static final int READING_NONE = 0;

    /**
     * Constant for reading transition
     */
    private static final int READING_TRANSITION = 1;

    /**
     * Constant for reading frame
     */
    private static final int READING_FRAME = 2;

    /**
     * Stores the current element being read.
     */
    private int reading = READING_NONE;

    /**
     * Current subparser being used
     */
    private DefaultHandler subParser;

    /**
     * Animation being read.
     */
    private Animation animation;

    /**
     * InputStreamCreator used in resolveEntity to find dtds (only required in
     * Applet mode)
     */
    private InputStreamCreator isCreator;

    public AnimationHandler( InputStreamCreator isCreator ) {

        this.isCreator = isCreator;
    }

    @Override
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) throws SAXException {

        if( this.reading == READING_NONE ) {

            if( qName.equals( "animation" ) ) {
                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getQName( i ).equals( "id" ) ) {
                        animation = new Animation( attrs.getValue( i ) );
                        animation.getFrames( ).clear( );
                        animation.getTransitions( ).clear( );
                    }

                    if( attrs.getQName( i ).equals( "slides" ) ) {
                        if( attrs.getValue( i ).equals( "yes" ) )
                            animation.setSlides( true );
                        else
                            animation.setSlides( false );
                    }

                    if( attrs.getQName( i ).equals( "usetransitions" ) ) {
                        if( attrs.getValue( i ).equals( "yes" ) )
                            animation.setUseTransitions( true );
                        else
                            animation.setUseTransitions( false );
                    }
                }
            }

            if( qName.equals( "documentation" ) ) {
                currentString = new StringBuffer( );
            }

            if( qName.equals( "resources" ) ) {
                currentResources = new Resources( );
            }

            else if( qName.equals( "asset" ) ) {
                String type = "";
                String path = "";

                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getQName( i ).equals( "type" ) )
                        type = attrs.getValue( i );
                    if( attrs.getQName( i ).equals( "uri" ) )
                        path = attrs.getValue( i );
                }

                currentResources.addAsset( type, path );
            }

            if( qName.equals( "frame" ) ) {
                subParser = new FrameSubParser( animation );
                reading = READING_FRAME;
            }

            if( qName.equals( "transition" ) ) {
                subParser = new TransitionSubParser( animation );
                reading = READING_TRANSITION;
            }
        }
        if( reading != READING_NONE ) {
            subParser.startElement( namespaceURI, sName, qName, attrs );
        }

    }

    @Override
    public void endElement( String namespaceURI, String sName, String qName ) {

        if( qName.equals( "documentation" ) ) {
            if( reading == READING_NONE )
                animation.setDocumentation( currentString.toString( ).trim( ) );
        }
        else if( qName.equals( "resources" ) ) {
            animation.addResources( currentResources );
        }

        if( reading != READING_NONE ) {
            try {
                subParser.endElement( namespaceURI, sName, qName );
            }
            catch( SAXException e ) {
                e.printStackTrace( );
            }
            reading = READING_NONE;
        }

    }

    @Override
    public void error( SAXParseException exception ) throws SAXParseException {

        // On validation, propagate exception
        exception.printStackTrace( );
        throw exception;
    }

    @Override
    public void characters( char[] buf, int offset, int len ) throws SAXException {

        // Append the new characters
        currentString.append( new String( buf, offset, len ) );
    }

    public Animation getAnimation( ) {

        return animation;
    }

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
        InputStream inputStream = AnimationHandler.class.getResourceAsStream( filename );
        if( inputStream == null ) {
            try {
                inputStream = new FileInputStream( filename );
                //inputStream = isCreator.buildInputStream( filename );
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
}
