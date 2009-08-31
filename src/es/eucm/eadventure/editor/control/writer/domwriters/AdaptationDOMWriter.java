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

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.adaptation.AdaptationRule;
import es.eucm.eadventure.common.data.adaptation.AdaptedState;
import es.eucm.eadventure.common.data.adaptation.UOLProperty;

public class AdaptationDOMWriter {

	/**
	 * Private constructor.
	 */
	private AdaptationDOMWriter( ) {}

	/**
	 * Returns the DOM element for the chapter
	 * 
	 * @param chapter
	 *            Chapter data to be written
	 * @return DOM element with the chapter data
	 */
	public static Element buildDOM( List<AdaptationRule> rules, AdaptedState initialState,boolean scorm12, boolean scorm2004,String name, Document doc ) {
		Element adaptationNode = null;

	
			// Create the root node
			adaptationNode = doc.createElement( "adaptation" );
			
			if (scorm12){
				adaptationNode.setAttribute("scorm12","yes");
			}else {
				adaptationNode.setAttribute("scorm12","no");
			}
			if (scorm2004){
				adaptationNode.setAttribute("scorm2004","yes");
			}else {
				adaptationNode.setAttribute("scorm2004","no");
			}
			adaptationNode.setAttribute("name", name);
			
			// Append the initial state, when available
			if (initialState != null && !initialState.isEmpty( )){
				Node initialStateNode = doc.createElement( "initial-state" );
				
				// Append initial scene if available
				if (initialState.getTargetId( )!= null && !initialState.getTargetId( ).equals( "" )){
					Element initialScene = doc.createElement( "initial-scene" );
					initialScene.setAttribute( "idTarget", initialState.getTargetId( ) );
					initialStateNode.appendChild( initialScene );
				}
				
				// Append activate / deactivate flags or set value var
				Element actionFlag;
				for (int i=0; i<initialState.getFlagsVars( ).size( ); i++){
				    if (initialState.isFlag(i)){
					actionFlag = doc.createElement( initialState.getAction( i ) );
					actionFlag.setAttribute( "flag", initialState.getFlagVar( i ) );
					
				    } else {
					// check the operation's type
					actionFlag=null;
					if (AdaptedState.isSetValueOp(initialState.getAction( i )))
					   // get only the title of the operation
					    actionFlag = doc.createElement( AdaptedState.VALUE );
					else 
					    if (AdaptedState.isIncrementOp(initialState.getAction( i )))
						actionFlag = doc.createElement( AdaptedState.INCREMENT );
					    else 
						if (AdaptedState.isDecrementOp(initialState.getAction( i )))
						    actionFlag = doc.createElement( AdaptedState.DECREMENT);
								
					//set the name of the current var
					actionFlag.setAttribute( "var", initialState.getFlagVar( i ) );
					// set the value
					actionFlag.setAttribute( "value", initialState.getValueForVar(i));
				    }
				    initialStateNode.appendChild( actionFlag );
				}
				
				//Append the node
				adaptationNode.appendChild( initialStateNode );
			}

			// Append the adaptation rules
			for( AdaptationRule rule : rules ) {
				
				//Create the rule node and set attributes
				Node ruleNode = doc.createElement( "adaptation-rule" );
				
				//Append rule description
				Node descriptionNode = doc.createElement( "ruleDescription" );
				if (rule.getDescription( )!=null)
					descriptionNode.appendChild( doc.createTextNode( rule.getDescription( ) ) );
				else
					descriptionNode.appendChild( doc.createTextNode( "" ) );
				ruleNode.appendChild( descriptionNode );
				
				//Append uol-state
				Node uolStateNode = doc.createElement( "uol-state" );
				for (UOLProperty property: rule.getUOLProperties( )){
					Element propertyElement = doc.createElement( "property" );
					propertyElement.setAttribute( "id", property.getId( ) );
					propertyElement.setAttribute( "value", property.getValue( ) );
					uolStateNode.appendChild( propertyElement );
					propertyElement.setAttribute( "operation",property.getOperation());
				}
				ruleNode.appendChild( uolStateNode );
				
				//Append game-state
				// Append the initial state, when available
				Node gameStateNode = doc.createElement( "game-state" );
				if (rule.getAdaptedState( ) != null && !rule.getAdaptedState( ).isEmpty( )){
					
					
					// Append initial scene if available
					if (rule.getAdaptedState( ).getTargetId( )!= null && !rule.getAdaptedState( ).getTargetId( ).equals( "" )){
						Element initialScene = doc.createElement( "initial-scene" );
						initialScene.setAttribute( "idTarget", rule.getAdaptedState( ).getTargetId( ) );
						gameStateNode.appendChild( initialScene );
					}
									
					// Append activate / deactivate flags or set value var
					Element actionFlag=null;
					for (int i=0; i<rule.getAdaptedState( ).getFlagsVars( ).size( ); i++){
					    if (rule.getAdaptedState( ).isFlag(i)){
						actionFlag = doc.createElement( rule.getAdaptedState( ).getAction( i ) );
						actionFlag.setAttribute( "flag", rule.getAdaptedState( ).getFlagVar( i ) );
						
					    } else {
						// check if this operation is "set-value"
						if (AdaptedState.isSetValueOp(rule.getAdaptedState( ).getAction( i )))
						   // get only the title of the operation
						    actionFlag = doc.createElement( AdaptedState.VALUE );
						// check if this operation is "increment"
						else if (AdaptedState.isIncrementOp(rule.getAdaptedState( ).getAction( i )))
						 // get only the title of the operation
						    actionFlag = doc.createElement( AdaptedState.INCREMENT );
						// check if this operation is "decrement"
						else if (AdaptedState.isDecrementOp(rule.getAdaptedState( ).getAction( i )))
							 // get only the title of the operation
							    actionFlag = doc.createElement( AdaptedState.DECREMENT );
							
						
						//set the name of the current var
						actionFlag.setAttribute( "var", rule.getAdaptedState( ).getFlagVar( i ) );
						// store the future value
						actionFlag.setAttribute( "value", rule.getAdaptedState( ).getValueForVar(i) );
					    
					    }
					    gameStateNode.appendChild( actionFlag );
					}
					
				}
				//Append the node
				ruleNode.appendChild( gameStateNode );
					
				//Append the rule
				adaptationNode.appendChild( ruleNode );
				
			}


		return adaptationNode;
	}
}
