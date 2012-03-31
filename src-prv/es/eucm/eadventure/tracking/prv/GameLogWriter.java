/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
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
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.tracking.prv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class GameLogWriter {

	public static void writeToFile ( long startTimeStamp, List<GameLogEntry> logEntries, File file ){
	    try {	
	    	// Create the necessary elements for building the DOM
	    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
	    	TransformerFactory tFactory = TransformerFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument();
			Transformer transformer = null;
			OutputStream fout = null;
			OutputStreamWriter writeFile = null;
	
			Element main = doc.createElement( "log" );
            for (GameLogEntry entry:logEntries){
            	Element entryElement = doc.createElement(entry.getElementName());
            	for (int i=0; i<entry.getAttributeCount(); i++){
            		entryElement.setAttribute(entry.getAttributeName(i), entry.getAttributeValue(i));
            	}
            	main.appendChild(entryElement);
            }
            
            
			transformer = tFactory.newTransformer( );
	
		    // Create the output buffer, write the DOM and close it
		    //fout = new FileOutputStream( zipFilename + "/imsmanifest.xml" );
			indentDOM(main, 0);
			doc.appendChild( main );
		    fout = new FileOutputStream( file );
		    writeFile = new OutputStreamWriter( fout, "UTF-8" );
		    transformer.transform( new DOMSource( doc ), new StreamResult( writeFile ) );
		    writeFile.close( );
		    fout.close( );
	    
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
    /**
     * Returns a set of tabulations, equivalent to the given number.
     * 
     * @param tabulations
     *            Number of tabulations
     */
    private static String getTab( int tabulations ) {

        String tab = "";
        for( int i = 0; i < tabulations; i++ )
            tab += "\t";
        return tab;
    }
	
    /**
     * Indent the given DOM node recursively with the given depth.
     * 
     * @param nodeDOM
     *            DOM node to be indented
     * @param depth
     *            Depth of the current node
     */
    private static void indentDOM( Node nodeDOM, int depth ) {

        // First of all, extract the document of the node, and the list of children
        Document document = nodeDOM.getOwnerDocument( );
        NodeList children = nodeDOM.getChildNodes( );

        // Flag for knowing if the current node is empty of element nodes
        boolean isEmptyOfElements = true;

        int i = 0;
        // For each children node
        while( i < children.getLength( ) ) {
            Node currentChild = children.item( i );

            // If the current child is an element node
            if( currentChild.getNodeType( ) == Node.ELEMENT_NODE ) {
                // Insert a indention before it, and call the recursive function with the child (and a higher depth)
                nodeDOM.insertBefore( document.createTextNode( "\n" + getTab( depth + 1 ) ), currentChild );
                indentDOM( currentChild, depth + 1 );

                // Set empty of elements to false, and increase i (the new child moves all children)
                isEmptyOfElements = false;
                i++;
            }

            // Go to next child
            i++;
        }

        // If this node has some element, add the indention for the closing tag
        if( !isEmptyOfElements )
            nodeDOM.appendChild( document.createTextNode( "\n" + getTab( depth ) ) );
    }


}
