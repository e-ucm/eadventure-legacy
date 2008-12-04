package es.eucm.eadventure.editor.control.writer.domwriters;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.data.chapter.conditions.Condition;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
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
	 * Private constructor.
	 */
	private ConditionsDOMWriter( ) {}

	public static Node buildDOM( Conditions conditions ) {
		return buildDOM(CONDITIONS, conditions);
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
			}

		} catch( ParserConfigurationException e ) {
			e.printStackTrace( );
		}

		return conditionsNode;
	}

	private static Node createElementWithList( String tagname, List<Condition> conditions ) {
		Node conditionsListNode = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			conditionsListNode = doc.createElement( tagname );

			// Write every condition
			for( Condition condition : conditions ) {
				Element conditionElement = null;

				if ( condition.getType() == Condition.FLAG_CONDITION ){
					// Create the tag
					if( condition.getState( ) == Condition.FLAG_ACTIVE )
						conditionElement = doc.createElement( "active" );
					else if( condition.getState( ) == Condition.FLAG_INACTIVE )
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
				}
				conditionsListNode.appendChild( conditionElement );
			}

		} catch( ParserConfigurationException e ) {
			e.printStackTrace( );
		}

		return conditionsListNode;
	}
}
