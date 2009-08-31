/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
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
        code.append( "<h3>" );
        code.append( rule.getConcept( ) );
        code.append( " (" );
        code.append( time / 3600 );
        code.append( ":" );
        code.append( ( time / 60 ) % 60 );
        code.append( ":" );
        code.append( time % 60 );
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
