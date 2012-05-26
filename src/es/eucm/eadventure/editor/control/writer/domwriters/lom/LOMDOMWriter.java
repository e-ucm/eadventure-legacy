/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.writer.domwriters.lom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMDataControl;

public class LOMDOMWriter {

    public static Node buildManifestDOM( LOMDataControl dataControl ) {

        Element manifest = null;
        try {
            // Create the necessary elements to create the DOM
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
            DocumentBuilder db = dbf.newDocumentBuilder( );
            Document doc = db.newDocument( );

            manifest = doc.createElement( "manifest" );
            manifest.setAttribute( "identifier", "imsaccmdv1p0_manifest" );
            manifest.setAttribute( "xmlns", "http://www.imsglobal.org/xsd/imscp_v1p1" );
            manifest.setAttribute( "xmlns:imsmd", "http://www.imsglobal.org/xsd/imsmd_v1p2" );
            manifest.setAttribute( "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance" );
            manifest.setAttribute( "xsi:schemaLocation", "http://www.imsglobal.org/xsd/imscp_v1p1 imscp_v1p1.xsd http://www.imsglobal.org/xsd/imsmd_v1p2 imsmd_v1p2p4.xsd" );
            manifest.setAttribute( "version", "IMS CP 1.1.3" );
            //manifest.appendChild( buildLOMDOM(dataControl) );

        }
        catch( ParserConfigurationException e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }

        return manifest;
    }

    public static Node buildLOMDOM( LOMDataControl dataControl, boolean scorm ) {

        Element lomElement = null;

        try {
            // Create the necessary elements to create the DOM
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
            DocumentBuilder db = dbf.newDocumentBuilder( );
            Document doc = db.newDocument( );

            // Create the root node
            lomElement = doc.createElement( "imsmd:lom" );
            Node generalNode = LOMGeneralDOMWriter.buildDOM( dataControl.getGeneral( ).getData( ), scorm );
            doc.adoptNode( generalNode );
            lomElement.appendChild( generalNode );

            Node lifeCycleNode = LOMLifeCycleDOMWriter.buildDOM( dataControl.getLifeCycle( ).getData( ), scorm );
            doc.adoptNode( lifeCycleNode );
            lomElement.appendChild( lifeCycleNode );

            Node technicalNode = LOMTechnicalDOMWriter.buildDOM( dataControl.getTechnical( ).getData( ), scorm );
            doc.adoptNode( technicalNode );
            lomElement.appendChild( technicalNode );

            Node educationalNode = LOMEducationalDOMWriter.buildDOM( dataControl.getEducational( ).getData( ), scorm );
            doc.adoptNode( educationalNode );
            lomElement.appendChild( educationalNode );

        }
        catch( ParserConfigurationException e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }

        return lomElement;

    }

}
