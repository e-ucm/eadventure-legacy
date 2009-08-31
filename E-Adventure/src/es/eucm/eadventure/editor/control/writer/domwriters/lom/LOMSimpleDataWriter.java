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
package es.eucm.eadventure.editor.control.writer.domwriters.lom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.Vocabulary;

public class LOMSimpleDataWriter {

	public static Node buildVocabularyNode (Document doc, String name,Vocabulary vocabulary,boolean scorm){
		Element vocElement = null;

			vocElement = doc.createElement( "imsmd:" +name );
			
			//Create the source element
			Node source = doc.createElement( "imsmd:source" );
			source.appendChild( buildStringNode(doc, vocabulary.getSource( ),scorm) );
			vocElement.appendChild( source );
			
			//Create the value element
			Node value = doc.createElement("imsmd:value" );
			value.appendChild( buildStringNode(doc, vocabulary.getValue( ),scorm) );
			vocElement.appendChild( value );
			

		return vocElement;
		
	}
	
	public static Node buildLangStringNode (Document doc, LangString lang,boolean scorm){
		Element langElement = null;

			langElement = doc.createElement(scorm?"imsmd:string": "imsmd:langstring" );
			langElement.setAttribute( scorm?"language":"xml:lang", lang.getLanguage( 0 ) );
			langElement.setTextContent( lang.getValue( 0 ) );

		return langElement;

	}
	
	public static Node buildStringNode (Document doc, String lang, boolean scorm){
		return buildLangStringNode (doc, new LangString("x-none", lang),scorm);
	}
	
	 
	public static boolean isStringSet(String string){
		return string != null && !string.equals( "" );
	}
	
	public static boolean isStringSet (LangString string){
		return string!= null && string.getLanguage( 0 )!= null && !string.getLanguage( 0 ).equals( "" ) &&
		string.getValue( 0 )!= null && !string.getValue( 0 ).equals( "" );
	}
}
