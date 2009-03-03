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
	public static Node buildDOM( List<AdaptationRule> rules, AdaptedState initialState,boolean scorm12, boolean scorm2004 ) {
		Element adaptationNode = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

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
			
			// Append the initial state, when available
			if (initialState != null && !initialState.isEmpty( )){
				Node initialStateNode = doc.createElement( "initial-state" );
				
				// Append initial scene if available
				if (initialState.getTargetId( )!= null && !initialState.getTargetId( ).equals( "" )){
					Element initialScene = doc.createElement( "initial-scene" );
					initialScene.setAttribute( "idTarget", initialState.getTargetId( ) );
					initialStateNode.appendChild( initialScene );
				}
				
				// Append activate / deactivate flags
				for (int i=0; i<initialState.getFlagsVars( ).size( ); i++){
					Element actionFlag = doc.createElement( initialState.getAction( i ) );
					actionFlag.setAttribute( "flag", initialState.getFlagVar( i ) );
					initialStateNode.appendChild( actionFlag );
				}
				
				//Append the node
				adaptationNode.appendChild( initialStateNode );
			}

			// Append the adaptation rules
			for( AdaptationRule rule : rules ) {
				
				//Create the rule node and set attributes
				Node ruleNode = doc.createElement( "adaptation-rule" );
				
				//Append description
				Node descriptionNode = doc.createElement( "description" );
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
					
					// Append activate / deactivate flags
					for (int i=0; i<rule.getAdaptedState( ).getFlagsVars( ).size( ); i++){
						Element actionFlag = doc.createElement( rule.getAdaptedState( ).getAction( i ) );
						actionFlag.setAttribute( "flag", rule.getAdaptedState( ).getFlagVar( i ) );
						gameStateNode.appendChild( actionFlag );
					}
				}
				//Append the node
				ruleNode.appendChild( gameStateNode );
					
				//Append the rule
				adaptationNode.appendChild( ruleNode );
				
			}


		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return adaptationNode;
	}
}
