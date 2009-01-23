package es.eucm.eadventure.editor.control.writer.domwriters;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.assessment.AssessmentProfile;
import es.eucm.eadventure.common.data.assessment.AssessmentProperty;
import es.eucm.eadventure.common.data.assessment.AssessmentRule;
import es.eucm.eadventure.common.data.assessment.TimedAssessmentEffect;
import es.eucm.eadventure.common.data.assessment.TimedAssessmentRule;

public class AssessmentDOMWriter {

	/**
	 * Private constructor.
	 */
	private AssessmentDOMWriter( ) {}

	/**
	 * Returns the DOM element for the chapter
	 * 
	 * @param chapter
	 *            Chapter data to be written
	 * @return DOM element with the chapter data
	 */
	public static Node buildDOM( AssessmentProfile profile ) {
		List<AssessmentRule> rules = profile.getRules();
		
		Element assessmentNode = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			assessmentNode = doc.createElement( "assessment-rules" );
			if ( profile.isShowReportAtEnd() ){
				assessmentNode.setAttribute("show-report-at-end", "yes");
			} else {
				assessmentNode.setAttribute("show-report-at-end", "no");
			}
			if ( !profile.isShowReportAtEnd() || !profile.isSendByEmail() )
				assessmentNode.setAttribute("send-to-email", "");
			else {
				if (profile.getEmail() == null || !profile.getEmail().contains("@"))
					assessmentNode.setAttribute("send-to-email", "");
				else {
					assessmentNode.setAttribute("send-to-email", profile.getEmail());
				}
			}
			
			Element smtpConfigNode = doc.createElement("smtp-config");
			smtpConfigNode.setAttribute("smtp-ssl", (profile.isSmtpSSL() ? "yes" : "no"));
			smtpConfigNode.setAttribute("smtp-server", profile.getSmtpServer());
			smtpConfigNode.setAttribute("smtp-port", profile.getSmtpPort());
			smtpConfigNode.setAttribute("smtp-user", profile.getSmtpUser());
			smtpConfigNode.setAttribute("smtp-pwd", profile.getSmtpPwd());

			assessmentNode.appendChild(smtpConfigNode);
			
			// Append the assessment rules
			for( AssessmentRule rule : rules ) {
				
				if ( rule instanceof TimedAssessmentRule){
					TimedAssessmentRule tRule = (TimedAssessmentRule)rule;
					//Create the rule node and set attributes
					Element ruleNode = doc.createElement( "timed-assessment-rule" );
					ruleNode.setAttribute( "id", tRule.getId( ) );
					ruleNode.setAttribute( "importance", AssessmentRule.IMPORTANCE_VALUES[tRule.getImportance( )] );
					
					//Append concept
					if (tRule.getConcept( )!=null && !tRule.getConcept( ).equals( "" )){
						Node conceptNode = doc.createElement( "concept" );
						conceptNode.appendChild( doc.createTextNode( tRule.getConcept( ) ) );
						ruleNode.appendChild( conceptNode );
					}
					
					//Append conditions (always required at least one)
					if (!tRule.getInitConditions( ).isEmpty( )){
						Node conditionsNode = ConditionsDOMWriter.buildDOM( ConditionsDOMWriter.INIT_CONDITIONS,tRule.getInitConditions( ) );
						doc.adoptNode( conditionsNode );
						ruleNode.appendChild( conditionsNode );
					}
					
					//Append conditions (always required at least one)
					if (!tRule.getEndConditions( ).isEmpty( )){
						Node conditionsNode = ConditionsDOMWriter.buildDOM( ConditionsDOMWriter.END_CONDITIONS,tRule.getEndConditions( ) );
						doc.adoptNode( conditionsNode );
						ruleNode.appendChild( conditionsNode );
					}
	
					// Create effects
					for (int i=0; i<tRule.getEffectsCount( ); i++){
						//Create effect element and append it
						Element effectNode = doc.createElement( "effect" );
						
						// Append time attributes
						effectNode.setAttribute( "time-min", Integer.toString( tRule.getMinTime( i ) ) );
						effectNode.setAttribute( "time-max", Integer.toString( tRule.getMaxTime( i ) ) );
						
						//Append set-text when appropriate
						TimedAssessmentEffect currentEffect = tRule.getEffects( ).get( i );
						if (currentEffect.getText( )!= null && !currentEffect.getText( ).equals( "" )){
							Node textNode = doc.createElement( "set-text" );
							textNode.appendChild( doc.createTextNode( currentEffect.getText( ) ) );
							effectNode.appendChild( textNode );
						}
						//Append properties
						for (AssessmentProperty property: currentEffect.getAssessmentProperties( )){
							Element propertyElement = doc.createElement( "set-property" );
							propertyElement.setAttribute( "id", property.getId( ) );
							propertyElement.setAttribute( "value", String.valueOf( property.getValue( ) ) );
							effectNode.appendChild( propertyElement );
						}
						//Append the effect
						ruleNode.appendChild( effectNode );
					}
						
					//Append the rule
					assessmentNode.appendChild( ruleNode );					
				} 
				else { 
					//Create the rule node and set attributes
					Element ruleNode = doc.createElement( "assessment-rule" );
					ruleNode.setAttribute( "id", rule.getId( ) );
					ruleNode.setAttribute( "importance", AssessmentRule.IMPORTANCE_VALUES[rule.getImportance( )] );
					
					//Append concept
					if (rule.getConcept( )!=null && !rule.getConcept( ).equals( "" )){
						Node conceptNode = doc.createElement( "concept" );
						conceptNode.appendChild( doc.createTextNode( rule.getConcept( ) ) );
						ruleNode.appendChild( conceptNode );
					}
					
					//Append conditions (always required at least one)
					if (!rule.getConditions( ).isEmpty( )){
						Node conditionsNode = ConditionsDOMWriter.buildDOM( rule.getConditions( ) );
						doc.adoptNode( conditionsNode );
						ruleNode.appendChild( conditionsNode );
					}
	
					//Create effect element and append it
					Node effectNode = doc.createElement( "effect" );
					//Append set-text when appropriate
					if (rule.getText( )!= null && !rule.getText( ).equals( "" )){
						Node textNode = doc.createElement( "set-text" );
						textNode.appendChild( doc.createTextNode( rule.getText( ) ) );
						effectNode.appendChild( textNode );
					}
					//Append properties
					for (AssessmentProperty property: rule.getAssessmentProperties( )){
						Element propertyElement = doc.createElement( "set-property" );
						propertyElement.setAttribute( "id", property.getId( ) );
						propertyElement.setAttribute( "value", String.valueOf( property.getValue( ) ) );
						effectNode.appendChild( propertyElement );
					}
					//Append the effect
					ruleNode.appendChild( effectNode );
						
					//Append the rule
					assessmentNode.appendChild( ruleNode );
				}
				
			}


		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return assessmentNode;
	}
}
