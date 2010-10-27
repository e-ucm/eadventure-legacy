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
package es.eucm.eadventure.engine.assessment;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import es.eucm.eadventure.common.data.assessment.AssessmentRule;
import es.eucm.eadventure.common.data.assessment.TimedAssessmentRule;

/**
 * Stores the information of a processed rule, including the time in which the
 * rule was executed
 */
public class ProcessedRule {

    /**
     * Executed rule
     */
    private AssessmentRule rule;

    /**
     * Time in which the rule was executed (stored in seconds)
     */
    private int time;

    /**
     * Constructor
     * 
     * @param rule
     *            Rule which was executed
     * @param time
     *            Time in which the rule was executed
     */
    public ProcessedRule( AssessmentRule rule, int time ) {

        this.rule = rule;
        this.time = time;
    }

    /**
     * Returns the importance of the rule
     * 
     * @return Importance of the rule
     */
    public int getImportance( ) {

        return rule.getImportance( );
    }

    /**
     * Builds and returns a DOM element containing the information of the
     * processed rule stores in XML format
     * 
     * @return Element containing the DOM information
     */
    public Element getDOMStructure( ) {

        Element processedRule = null;

        try {
            // Create the necessary elements to create the DOM
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
            DocumentBuilder db = dbf.newDocumentBuilder( );
            Document doc = db.newDocument( );

            // Create the root node
            processedRule = doc.createElement( "processed-rule" );
            processedRule.setAttribute( "id", rule.getId( ) );
            if( rule instanceof TimedAssessmentRule ) {
                processedRule.setAttribute( "type", "timed-rule" );
            }
            else {
                processedRule.setAttribute( "type", "normal-rule" );
            }
            processedRule.setAttribute( "importance", AssessmentRule.IMPORTANCE_VALUES[rule.getImportance( )] );
            processedRule.setAttribute( "time", Integer.toString( time ) );

            // Add the concept
            Element concept = doc.createElement( "concept" );
            concept.setTextContent( rule.getConcept( ) );
            processedRule.appendChild( concept );

            // If there was a text, add it
            if( rule.getText( ) != null ) {
                Element text = doc.createElement( "text" );
                text.setTextContent( rule.getText( ) );
                processedRule.appendChild( text );
            }

        }
        catch( ParserConfigurationException e ) {
            e.printStackTrace( );
        }

        return processedRule;
    }

    /**
     * Generates the HTML code depicting the content of the processed rule
     * 
     * @return String with HTML code
     */
    public String getHTMLCode( ) {

        StringBuffer code = new StringBuffer( );

        // Write execution time
        code.append( "<h3 id=\""+ rule.getId( ) +"\">" );
        code.append( rule.getConcept( ) );
        code.append( " (" );
        if (time / 3600 < 10)
            code.append("0"+Integer.toString(time / 3600 ));
        else 
            code.append(Integer.toString(time / 3600 ));
        code.append( ":" );
        if (( time / 60 ) % 60 < 10)      
            code.append("0"+( time / 60 ) % 60  );
        else 
            code.append(( time / 60 ) % 60  );
        code.append( ":" );
        if (time % 60 <10)
            code.append("0"+ time % 60 );
        else
            code.append(time % 60 );
        code.append( ")" );
        code.append( "</h3>" );

        // Write text (if present)
        if( rule.getText( ) != null ) {
            code.append( rule.getText( ) );
            code.append( "<br/>" );
        }

        return code.toString( );
    }

    /*
     *  (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString( ) {

        return "Time: " + time + " - " + rule.toString( );
    }
}
