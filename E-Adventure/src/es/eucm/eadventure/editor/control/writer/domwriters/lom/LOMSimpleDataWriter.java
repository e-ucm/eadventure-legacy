/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.writer.domwriters.lom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.Vocabulary;

public class LOMSimpleDataWriter {

    public static Node buildVocabularyNode( Document doc, String name, Vocabulary vocabulary, boolean scorm ) {

        Element vocElement = null;

        vocElement = doc.createElement( "imsmd:" + name );

        //Create the source element
        Node source = doc.createElement( "imsmd:source" );
        source.appendChild( buildStringNode( doc, vocabulary.getSource( ), scorm ) );
        vocElement.appendChild( source );

        //Create the value element
        Node value = doc.createElement( "imsmd:value" );
        value.appendChild( buildStringNode( doc, vocabulary.getValue( ), scorm ) );
        vocElement.appendChild( value );

        return vocElement;

    }

    public static Node buildLangStringNode( Document doc, LangString lang, boolean scorm ) {

        Element langElement = null;

        langElement = doc.createElement( scorm ? "imsmd:string" : "imsmd:langstring" );
        langElement.setAttribute( scorm ? "language" : "xml:lang", lang.getLanguage( 0 ) );
        langElement.setTextContent( lang.getValue( 0 ) );

        return langElement;

    }

    public static Node buildStringNode( Document doc, String lang, boolean scorm ) {

        return buildLangStringNode( doc, new LangString( "x-none", lang ), scorm );
    }

    public static boolean isStringSet( String string ) {

        return string != null && !string.equals( "" );
    }

    public static boolean isStringSet( LangString string ) {

        return string != null && string.getLanguage( 0 ) != null && !string.getLanguage( 0 ).equals( "" ) && string.getValue( 0 ) != null && !string.getValue( 0 ).equals( "" );
    }
}
