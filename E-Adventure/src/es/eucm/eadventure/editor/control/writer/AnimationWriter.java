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
package es.eucm.eadventure.editor.control.writer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.common.data.animation.Frame;
import es.eucm.eadventure.common.data.animation.Transition;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.writer.domwriters.ResourcesDOMWriter;

public class AnimationWriter {

    private AnimationWriter( ) {

    }

    public static boolean writeAnimation( String filename, Animation animation ) {

        boolean dataSaved = false;

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
            TransformerFactory tf = TransformerFactory.newInstance( );
            DocumentBuilder db = dbf.newDocumentBuilder( );
            Document doc = db.newDocument( );
            Transformer transformer = null;
            OutputStream fout = null;
            OutputStreamWriter writeFile = null;

            Element mainNode = doc.createElement( "animation" );
            //mainNode.appendChild(doc.createAttribute("id").setNodeValue(animation.getId()));
            mainNode.setAttribute( "id", animation.getId( ) );
            mainNode.setAttribute( "usetransitions", animation.isUseTransitions( ) ? "yes" : "no" );
            mainNode.setAttribute( "slides", animation.isSlides( ) ? "yes" : "no" );
            Element documentation = doc.createElement( "documentation" );
            if( animation.getDocumentation( ) != null && animation.getDocumentation( ).length( ) > 0 )
                documentation.setTextContent( animation.getDocumentation( ) );
            mainNode.appendChild( documentation );

            for( Resources resources : animation.getResources( ) ) {
                Node resourcesNode = ResourcesDOMWriter.buildDOM( resources, ResourcesDOMWriter.RESOURCES_ANIMATION );
                doc.adoptNode( resourcesNode );
                mainNode.appendChild( resourcesNode );
            }

            for( int i = 0; i < animation.getFrames( ).size( ); i++ ) {
                mainNode.appendChild( createTransitionElement( animation.getTransitions( ).get( i ), doc ) );
                mainNode.appendChild( createFrameElement( animation.getFrames( ).get( i ), doc ) );
            }
            mainNode.appendChild( createTransitionElement( animation.getEndTransition( ), doc ) );

            doc.adoptNode( mainNode );
            doc.appendChild( mainNode );

            transformer = tf.newTransformer( );
            transformer.setOutputProperty( OutputKeys.DOCTYPE_SYSTEM, "animation.dtd" );

            try {
                fout = new FileOutputStream( filename );
            }
            catch( FileNotFoundException e ) {
                fout = new FileOutputStream( Controller.getInstance( ).getProjectFolder( ) + "/" + filename );
            }

            writeFile = new OutputStreamWriter( fout, "UTF-8" );
            transformer.transform( new DOMSource( doc ), new StreamResult( writeFile ) );
            writeFile.close( );
            fout.close( );

            dataSaved = true;
        }
        catch( Exception e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }

        return dataSaved;
    }

    private static Element createTransitionElement( Transition t, Document doc ) {

        Element element = doc.createElement( "transition" );

        if( t.getType( ) == Transition.TYPE_NONE )
            element.setAttribute( "type", "none" );
        else if( t.getType( ) == Transition.TYPE_FADEIN )
            element.setAttribute( "type", "fadein" );
        else if( t.getType( ) == Transition.TYPE_HORIZONTAL )
            element.setAttribute( "type", "horizontal" );
        else if( t.getType( ) == Transition.TYPE_VERTICAL )
            element.setAttribute( "type", "vertical" );

        element.setAttribute( "time", "" + t.getTime( ) );

        return element;
    }

    public static Element createFrameElement( Frame f, Document doc ) {

        Element element = doc.createElement( "frame" );

        element.setAttribute( "uri", ( f.getUri( ) != null ? f.getUri( ) : "" ) );

        if( f.getType( ) == Frame.TYPE_IMAGE )
            element.setAttribute( "type", "image" );
        else if( f.getType( ) == Frame.TYPE_VIDEO )
            element.setAttribute( "type", "video" );

        element.setAttribute( "time", String.valueOf( f.getTime( ) ) );

        element.setAttribute( "waitforclick", ( f.isWaitforclick( ) ? "yes" : "no" ) );

        element.setAttribute( "soundUri", ( f.getSoundUri( ) != null ? f.getSoundUri( ) : "" ) );

        element.setAttribute( "maxSoundTime", String.valueOf( f.getMaxSoundTime( ) ) );

        return element;
    }

}
