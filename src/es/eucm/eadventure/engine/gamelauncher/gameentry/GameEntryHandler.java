/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.engine.gamelauncher.gameentry;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * SAX handler for the game entries 
 */
public class GameEntryHandler extends DefaultHandler {

    /* Attributes */

    /**
     * String to store the current string in the XML file
     */
    private StringBuffer currentString;
    
    /**
     * Stores if the title has been read
     */
    private boolean titleRead;
    
    /**
     * Stores if the description has been read
     */
    private boolean descriptionRead;
    
    /**
     * Information of the game
     */
    private GameEntry gameEntry;
    
    /* Methods */

    /**
     * Returns the game entry readed
     * @return The game entry
     */
    public GameEntry getGameEntry( ) {
        return gameEntry;
    }
    
    @Override
    public void startDocument( ) {
        gameEntry = new GameEntry( );
        currentString = new StringBuffer( );
        titleRead = false;
        descriptionRead = false;
    }
    
    @Override
    public void startElement( String namespaceURI, String sName, String qName, Attributes attributes ) throws SAXException {
        // If it is an invalid tag, set the adventure as invalid
        if( qName.equals( "invalid" ) )
            gameEntry.setValid( false );
    }

    @Override
    public void endElement( String namespaceURI, String sName, String qName ) throws SAXException {
        // Store the title
        if( qName.equals( "title" ) && !titleRead ) {
            gameEntry.setTitle( new String( currentString ).trim( ) );
            titleRead = true;
        }
        
        else if( qName.equals( "description" ) && !descriptionRead ) {
            gameEntry.setDescription( new String( currentString ).trim( ).replace( "\t", "" ) );
            descriptionRead = true;
        }
        
        // Reset the current string
        currentString = new StringBuffer( );
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
    public InputSource resolveEntity( String publicId, String systemId ) {
        // Take the name of the file SAX is looking for
        int startFilename = systemId.lastIndexOf( "/" ) + 1;
        String filename = systemId.substring( startFilename, systemId.length( ) );

        // Build and return a input stream with the file (usually the DTD)
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream( filename );
        } catch( FileNotFoundException e ) {
            e.printStackTrace( );
        }
        return new InputSource( inputStream );
    }
}
