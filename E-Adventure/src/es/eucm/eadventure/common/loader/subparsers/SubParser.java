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
package es.eucm.eadventure.common.loader.subparsers;

import org.xml.sax.Attributes;

import es.eucm.eadventure.common.data.chapter.Chapter;

/**
 * Abstract class for subparsing elements of the script
 */
public abstract class SubParser {

    /* Attributes */

    /**
     * String to store the current string in the XML file.
     */
    protected StringBuffer currentString;

    /**
     * Chapter in which the data will be stored.
     */
    protected Chapter chapter;

    /* Methods */

    /**
     * Constructor.
     * 
     * @param chapter
     *            Chapter data to store the read data
     */
    public SubParser( Chapter chapter ) {

        this.chapter = chapter;
        currentString = new StringBuffer( );
    }

    /**
     * Receive notification of the start of an element.
     * 
     * @param namespaceURI
     *            The Namespace URI, or the empty string if the element has no
     *            Namespace URI or if Namespace processing is not being
     *            performed
     * @param sName
     *            The local name (without prefix), or the empty string if
     *            Namespace processing is not being performed
     * @param qName
     *            The qualified name (with prefix), or the empty string if
     *            qualified names are not available
     * @param attrs
     *            The attributes attached to the element. If there are no
     *            attributes, it shall be an empty Attributes object
     */
    public abstract void startElement( String namespaceURI, String sName, String qName, Attributes attrs );

    /**
     * Receive notification of the end of an element.
     * 
     * @param namespaceURI
     *            The Namespace URI, or the empty string if the element has no
     *            Namespace URI or if Namespace processing is not being
     *            performed
     * @param sName
     *            The local name (without prefix), or the empty string if
     *            Namespace processing is not being performed
     * @param qName
     *            The qualified name (with prefix), or the empty string if
     *            qualified names are not available
     */
    public abstract void endElement( String namespaceURI, String sName, String qName );

    /**
     * Receive notification of character data inside an element.
     * 
     * @param buf
     *            The characters
     * @param offset
     *            The start position in the character array
     * @param len
     *            The number of characters to use from the character array
     */
    public void characters( char[] buf, int offset, int len ) {

        // Append the new characters
        currentString.append( new String( buf, offset, len ) );
    }
}
