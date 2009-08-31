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
import es.eucm.eadventure.common.data.chapter.conditions.Condition;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.conditions.FlagCondition;
import es.eucm.eadventure.common.data.chapter.conditions.GlobalState;
import es.eucm.eadventure.common.data.chapter.conditions.GlobalStateCondition;
import es.eucm.eadventure.common.data.chapter.conditions.VarCondition;

public class ConditionsDOMWriter {

	/**
	 * Constant for "condition" tag (general case)
	 */
	public static final int CONDITIONS = 0;
	
	/**
	 * Constant for "init-condition" tag (timers)
	 */
	public static final int INIT_CONDITIONS = 1;
	
	/**
	 * Constant for "end-condition" tag (timers)
	 */
	public static final int END_CONDITIONS = 2;
	
	/**
	 * Constant for "global-state" tag
	 */
	public static final int GLOBAL_STATE = 3;

	
	/**
	 * Private constructor.
	 */
	private ConditionsDOMWriter( ) {}

	public static Node buildDOM( Conditions conditions ) {
		return buildDOM(CONDITIONS, conditions);
	}
	
	/**
	 * Builds the DOM element for a global state
	 * @param globalState
	 * @return
	 */
	public static Element buildDOM( GlobalState globalState ) {
		Element conditionsNode = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );
			conditionsNode = doc.createElement("global-state");
			conditionsNode.setAttribute("id", globalState.getId());
			Node documentationNode = doc.createElement("documentation");
			documentationNode.appendChild( doc.createTextNode(globalState.getDocumentation()));
			conditionsNode.appendChild(documentationNode);

			// Iterate all the condition'blocks
			for (int i=0; i<globalState.size(); i++){
				List<Condition> block = globalState.get(i);
				// Single condition
				if (block.size() == 1){
					Element conditionElement =createConditionElement(doc, block.get(0));
					doc.adoptNode(conditionElement);
					conditionsNode.appendChild(conditionElement);
					
				} else if (block.size()>1){
					Node eitherNode = createElementWithList( "either", block );
					doc.adoptNode( eitherNode );
					conditionsNode.appendChild( eitherNode );
				}
			}	

			
			/*createElementWithList(doc, conditionsNode, globalState.getMainConditions() );
			// Create and write the either condition blocks
			for( int i = 0; i < globalState.getEitherConditionsBlockCount( ); i++ ) {
				List<Condition> eitherBlock = globalState.getEitherConditions( i );
				// Write it if the block is not empty.
				if ( eitherBlock.size( )>0 ){
					Node eitherNode = createElementWithList( "either", eitherBlock );
					doc.adoptNode( eitherNode );
					conditionsNode.appendChild( eitherNode );
				}
			}*/

		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return conditionsNode;
	}
	
	public static Node buildDOM( int type, Conditions conditions ) {
		Node conditionsNode = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node (with its children)
			if (type == CONDITIONS)
				conditionsNode = doc.createElement("condition");
			else if (type == INIT_CONDITIONS)
				conditionsNode = doc.createElement("init-condition");
			else if (type == END_CONDITIONS)
				conditionsNode = doc.createElement("end-condition");
			doc.adoptNode( conditionsNode );
			
			// Iterate all the condition'blocks
			for (int i=0; i<conditions.size(); i++){
				List<Condition> block = conditions.get(i);
				// Single condition
				if (block.size() == 1){
					Element conditionElement =createConditionElement(doc, block.get(0));
					doc.adoptNode(conditionElement);
					conditionsNode.appendChild(conditionElement);
					
				} else if (block.size()>1){
					Node eitherNode = createElementWithList( "either", block );
					doc.adoptNode( eitherNode );
					conditionsNode.appendChild( eitherNode );
				}
			}	
			// Create the root node (with its children)
			/*if (type == CONDITIONS)
				conditionsNode = createElementWithList( "condition", conditions.getMainConditions( ) );
			else if (type == INIT_CONDITIONS)
				conditionsNode = createElementWithList( "init-condition", conditions.getMainConditions( ) );
			else if (type == END_CONDITIONS)
				conditionsNode = createElementWithList( "end-condition", conditions.getMainConditions( ) );
			doc.adoptNode( conditionsNode );

			// Create and write the either condition blocks
			for( int i = 0; i < conditions.getEitherConditionsBlockCount( ); i++ ) {
				List<Condition> eitherBlock = conditions.getEitherConditions( i );
				// Write it if the block is not empty.
				if ( eitherBlock.size( )>0 ){
					Node eitherNode = createElementWithList( "either", eitherBlock );
					doc.adoptNode( eitherNode );
					conditionsNode.appendChild( eitherNode );
				}
			}*/

		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return conditionsNode;
	}

	private static Node createElementWithList( String tagname, List<Condition> conditions ) {
		Element conditionsListNode = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			conditionsListNode = doc.createElement( tagname );
			
			createElementWithList( doc, conditionsListNode, conditions );
			return conditionsListNode;
		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
			return null;
		}
		
		

	}

	private static Element createConditionElement(Document doc, Condition condition){
		Element conditionElement = null;

		if ( condition.getType() == Condition.FLAG_CONDITION ){
			// Create the tag
			if( condition.getState( ) == FlagCondition.FLAG_ACTIVE )
				conditionElement = doc.createElement( "active" );
			else if( condition.getState( ) == FlagCondition.FLAG_INACTIVE )
				conditionElement = doc.createElement( "inactive" );

			// Set the target flag and append it
			conditionElement.setAttribute( "flag", condition.getId( ) );
		} else if ( condition.getType() == Condition.VAR_CONDITION ){
			VarCondition varCondition = (VarCondition) condition;
			// Create the tag
			if( varCondition.getState( ) == VarCondition.VAR_EQUALS )
				conditionElement = doc.createElement( "equals" );
			else if( condition.getState( ) == VarCondition.VAR_GREATER_EQUALS_THAN )
				conditionElement = doc.createElement( "greater-equals-than" );
			else if( condition.getState( ) == VarCondition.VAR_GREATER_THAN )
				conditionElement = doc.createElement( "greater-than" );
			else if( condition.getState( ) == VarCondition.VAR_LESS_EQUALS_THAN )
				conditionElement = doc.createElement( "less-equals-than" );
			else if( condition.getState( ) == VarCondition.VAR_LESS_THAN )
				conditionElement = doc.createElement( "less-than" );

			// Set the target flag and append it
			conditionElement.setAttribute( "var", varCondition.getId( ) );
			conditionElement.setAttribute( "value", Integer.toString( varCondition.getValue() ) );
		} else if ( condition.getType() == Condition.GLOBAL_STATE_CONDITION ){
			GlobalStateCondition globalStateCondition = (GlobalStateCondition) condition;
			// Create the tag
			conditionElement = doc.createElement( "global-state-ref" );

			// Set the target flag and append it
			conditionElement.setAttribute( "id", globalStateCondition.getId( ) );
		}
		
		return conditionElement;
	}
	
	private static void createElementWithList(Document doc, Element conditionsListNode, List<Condition> conditions ) {
			// Write every condition
			for( Condition condition : conditions ) {

				conditionsListNode.appendChild( createConditionElement(doc,condition) );
			}
	}
}
