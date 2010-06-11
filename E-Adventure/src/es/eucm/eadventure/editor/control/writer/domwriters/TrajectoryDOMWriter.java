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
package es.eucm.eadventure.editor.control.writer.domwriters;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.Trajectory;

public class TrajectoryDOMWriter {

    /**
     * Private constructor.
     */
    private TrajectoryDOMWriter( ) {

    }

    public static Node buildDOM( Trajectory trajectory ) {

        Element itemElement = null;

        try {
            // Create the necessary elements to create the DOM
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
            DocumentBuilder db = dbf.newDocumentBuilder( );
            Document doc = db.newDocument( );

            // Create the root node
            itemElement = doc.createElement( "trajectory" );

            for( es.eucm.eadventure.common.data.chapter.Trajectory.Node node : trajectory.getNodes( ) ) {
                Element nodeElement = doc.createElement( "node" );
                nodeElement.setAttribute( "id", node.getID( ) );
                nodeElement.setAttribute( "x", String.valueOf( node.getX( ) ) );
                nodeElement.setAttribute( "y", String.valueOf( node.getY( ) ) );
                nodeElement.setAttribute( "scale", String.valueOf( node.getScale( ) ) );
                itemElement.appendChild( nodeElement );
            }

            if( trajectory.getInitial( ) != null ) {
                Element initialNodeElement = doc.createElement( "initialnode" );
                initialNodeElement.setAttribute( "id", trajectory.getInitial( ).getID( ) );
                itemElement.appendChild( initialNodeElement );
            }

            for( es.eucm.eadventure.common.data.chapter.Trajectory.Side side : trajectory.getSides( ) ) {
                Element sideElement = doc.createElement( "side" );
                sideElement.setAttribute( "idStart", side.getIDStart( ) );
                sideElement.setAttribute( "idEnd", side.getIDEnd( ) );
                sideElement.setAttribute( "length", String.valueOf( (int) side.getLength( )) );
                itemElement.appendChild( sideElement );
            }

        }
        catch( ParserConfigurationException e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }

        return itemElement;
    }
}
