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
package es.eucm.eadventure.editor.control.writer.domwriters.lomes;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMOrComposite;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESTechnical;

public class LOMESTechnicalDOMWriter extends LOMESSimpleDataWriter {

    /**
     * Private constructor.
     */
    private LOMESTechnicalDOMWriter( ) {

    }

    public static Node buildDOM( LOMESTechnical technical ) {

        Element technicalElement = null;

        try {
            // Create the necessary elements to create the DOM
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
            DocumentBuilder db = dbf.newDocumentBuilder( );
            Document doc = db.newDocument( );

            // Create the root node
            technicalElement = doc.createElement( "lomes:technical" );
            technicalElement.setAttribute( "vocElement", "technical" );

            // Create the format node
            /*Element format = doc.createElement("format");
            format.setTextContent(technical.getFormat());
            technicalElement.appendChild(format);
            
            //Create the location node
            Element location = doc.createElement("location");
            String loc = technical.getLocation();
            if (loc.startsWith("0")){
            	location.setAttribute("type", "URI");
            } else if (loc.startsWith("1")){
            	location.setAttribute("type", "TEXT");
            }
            location.setTextContent(loc.substring(2));
            technicalElement.appendChild(location);
            */

            //Create the requirement node
            Element requirement = doc.createElement( "lomes:requirement" );

            for( int i = 0; i < technical.getRequirement( ).getSize( ); i++ ) {

                Element orComposite = doc.createElement( "lomes:orComposite" );

                orComposite.appendChild( buildVocabularyNode( doc, "lomes:type", ( (LOMOrComposite) technical.getRequirement( ).get( i ) ).getType( ) ) );
                orComposite.appendChild( buildVocabularyNode( doc, "lomes:name", ( (LOMOrComposite) technical.getRequirement( ).get( i ) ).getName( ) ) );

                //Create the minimum version node
                if( isStringSet( ( (LOMOrComposite) technical.getRequirement( ).get( i ) ).getMinimumVersion( ) ) ) {
                    Element minVer = doc.createElement( "lomes:minimumVersion" );
                    minVer.setAttribute( "vocElement", "minimumVersion" );
                    minVer.setTextContent( ( (LOMOrComposite) technical.getRequirement( ).get( i ) ).getMinimumVersion( ) );
                    orComposite.appendChild( minVer );
                }
                //Create the maximum version node
                if( isStringSet( ( (LOMOrComposite) technical.getRequirement( ).get( i ) ).getMaximumVersion( ) ) ) {
                    Element maxVer = doc.createElement( "lomes:maximumVersion" );
                    maxVer.setAttribute( "vocElement", "maximumVersion" );
                    maxVer.setTextContent( ( (LOMOrComposite) technical.getRequirement( ).get( i ) ).getMaximumVersion( ) );
                    orComposite.appendChild( maxVer );
                }

                requirement.appendChild( orComposite );
            }
            technicalElement.appendChild( requirement );

        }
        catch( ParserConfigurationException e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }

        return technicalElement;
    }
}
