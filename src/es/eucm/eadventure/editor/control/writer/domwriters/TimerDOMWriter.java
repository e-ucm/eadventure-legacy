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
	private TimerDOMWriter( ) {}

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
			timerElement.setAttribute( "usesEndCondition", timer.isUsesEndCondition() ? "yes" : "no");
			timerElement.setAttribute( "multipleStarts", timer.isMultipleStarts() ? "yes" : "no");
			timerElement.setAttribute( "runsInLoop", timer.isRunsInLoop() ? "yes" : "no");
			timerElement.setAttribute( "showTime" , timer.isShowTime() ? "yes" : "no");
			timerElement.setAttribute( "displayName", timer.getDisplayName());
			timerElement.setAttribute( "countDown", timer.isCountDown() ? "yes" : "no");
			timerElement.setAttribute( "showWhenStopped", timer.isShowWhenStopped() ? "yes" : "no");
			
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

		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return timerElement;
	}
}
