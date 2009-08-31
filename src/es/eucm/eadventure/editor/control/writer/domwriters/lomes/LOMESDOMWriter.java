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
import es.eucm.eadventure.editor.control.controllers.metadata.lomes.LOMESDataControl;

public class LOMESDOMWriter {

    public static Node buildLOMESDOM( LOMESDataControl dataControl ) {

        Element lomElement = null;

        try {
            // Create the necessary elements to create the DOM
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
            DocumentBuilder db = dbf.newDocumentBuilder( );
            Document doc = db.newDocument( );

            // Create the root node
            lomElement = doc.createElement( "lomes:lom" );
            //lomElement.setAttribute("xmlns", "http://www.imsglobal.org/xsd/imsmd_rootv1p2p1");
            // Create general node
            Node generalNode = LOMESGeneralDOMWriter.buildDOM( dataControl.getGeneral( ).getData( ) );
            doc.adoptNode( generalNode );
            lomElement.appendChild( generalNode );

            // Create life cycle node
            Node lifeCycleNode = LOMESLifeCycleDOMWriter.buildDOM( dataControl.getLifeCycle( ).getData( ) );
            doc.adoptNode( lifeCycleNode );
            lomElement.appendChild( lifeCycleNode );

            // Create meta meta data node
            Node metametadataNode = LOMESMetaMetaDataDOMWriter.buildDOM( dataControl.getMetametadata( ).getData( ) );
            doc.adoptNode( metametadataNode );
            lomElement.appendChild( metametadataNode );

            // Create technical node
            Node technicalNode = LOMESTechnicalDOMWriter.buildDOM( dataControl.getTechnical( ).getData( ) );
            doc.adoptNode( technicalNode );
            lomElement.appendChild( technicalNode );

            // Create educational node
            Node educationalNode = LOMESEducationalDOMWriter.buildDOM( dataControl.getEducational( ).getData( ) );
            doc.adoptNode( educationalNode );
            lomElement.appendChild( educationalNode );

            // Create rights node 
            Node rightsNode = LOMESRightsDOMWriter.buildDOM( dataControl.getRights( ).getData( ) );
            doc.adoptNode( rightsNode );
            lomElement.appendChild( rightsNode );

            // Create classification node 
            Node classificationNode = LOMESClassificationDOMWriter.buildDOM( dataControl.getClassification( ).getData( ) );
            doc.adoptNode( classificationNode );
            lomElement.appendChild( classificationNode );

        }
        catch( ParserConfigurationException e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }

        return lomElement;

    }

    public static Element createOneIdentifier( Document doc, String cat, String ent ) {

        Element identifier = doc.createElement( "lomes:identifier" );
        Element catalog = doc.createElement( "lomes:catalog" );
        catalog.setAttribute( "uniqueElementName", "catalog" );
        catalog.setTextContent( cat );
        identifier.appendChild( catalog );
        Element entry = doc.createElement( "lomes:entry" );
        entry.setAttribute( "uniqueElementName", "entry" );
        entry.setTextContent( ent );
        identifier.appendChild( entry );
        return identifier;
    }

}
