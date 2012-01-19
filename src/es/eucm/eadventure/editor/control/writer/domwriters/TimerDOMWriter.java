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
package es.eucm.eadventure.editor.control.writer.domwriters;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.Timer;

public class TimerDOMWriter {

    /**
     * Private constructor.
     */
    private TimerDOMWriter( ) {

    }

    public static Node buildDOM( Timer timer ) {

        Element timerElement = null;

        try {
            // Create the necessary elements to create the DOM
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
            DocumentBuilder db = dbf.newDocumentBuilder( );
            Document doc = db.newDocument( );

            // Create the root node
            timerElement = doc.createElement( "timer" );

            // Set the time attribute
            timerElement.setAttribute( "time", Long.toString( timer.getTime( ) ) );
            timerElement.setAttribute( "usesEndCondition", timer.isUsesEndCondition( ) ? "yes" : "no" );
            timerElement.setAttribute( "multipleStarts", timer.isMultipleStarts( ) ? "yes" : "no" );
            timerElement.setAttribute( "runsInLoop", timer.isRunsInLoop( ) ? "yes" : "no" );
            timerElement.setAttribute( "showTime", timer.isShowTime( ) ? "yes" : "no" );
            timerElement.setAttribute( "displayName", timer.getDisplayName( ) );
            timerElement.setAttribute( "countDown", timer.isCountDown( ) ? "yes" : "no" );
            timerElement.setAttribute( "showWhenStopped", timer.isShowWhenStopped( ) ? "yes" : "no" );

            // Append the documentation (if avalaible)
            if( timer.getDocumentation( ) != null ) {
                Node timerDocumentationNode = doc.createElement( "documentation" );
                timerDocumentationNode.appendChild( doc.createTextNode( timer.getDocumentation( ) ) );
                timerElement.appendChild( timerDocumentationNode );
            }

            // Append the init conditions (if avalaible)
            if( !timer.getInitCond( ).isEmpty( ) ) {
                Node conditionsNode = ConditionsDOMWriter.buildDOM( ConditionsDOMWriter.INIT_CONDITIONS, timer.getInitCond( ) );
                doc.adoptNode( conditionsNode );
                timerElement.appendChild( conditionsNode );
            }

            // Append the end-conditions (if avalaible)
            if( !timer.getEndCond( ).isEmpty( ) ) {
                Node conditionsNode = ConditionsDOMWriter.buildDOM( ConditionsDOMWriter.END_CONDITIONS, timer.getEndCond( ) );
                doc.adoptNode( conditionsNode );
                timerElement.appendChild( conditionsNode );
            }

            // Append the effects (if avalaible)
            if( !timer.getEffects( ).isEmpty( ) ) {
                Node effectsNode = EffectsDOMWriter.buildDOM( EffectsDOMWriter.EFFECTS, timer.getEffects( ) );
                doc.adoptNode( effectsNode );
                timerElement.appendChild( effectsNode );
            }

            // Append the post-effects (if avalaible)
            if( !timer.getPostEffects( ).isEmpty( ) ) {
                Node postEffectsNode = EffectsDOMWriter.buildDOM( EffectsDOMWriter.POST_EFFECTS, timer.getPostEffects( ) );
                doc.adoptNode( postEffectsNode );
                timerElement.appendChild( postEffectsNode );
            }

        }
        catch( ParserConfigurationException e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }

        return timerElement;
    }
}
