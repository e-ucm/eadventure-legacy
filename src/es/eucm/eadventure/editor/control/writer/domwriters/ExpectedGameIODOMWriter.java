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

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import es.eucm.eadventure.common.data.adaptation.AdaptationRule;
import es.eucm.eadventure.common.data.adaptation.UOLProperty;
import es.eucm.eadventure.common.data.assessment.AssessmentProperty;
import es.eucm.eadventure.common.data.assessment.AssessmentRule;


public class ExpectedGameIODOMWriter {

    
    private static final String STRING = "string";
    
    private static final String BOOLEAN = "boolean";
    
    private static final String INTEGER = "integer";
    
    
    public static String getType(String value){
        
        
        try{
        Integer.parseInt( value );
        return INTEGER;
        
        } catch (NumberFormatException e){
        
            if ((value != null) && (value.equalsIgnoreCase("true")||value.equalsIgnoreCase("false")))
                    return BOOLEAN;
            else 
                return STRING;
        }
      
        
        }
    
    
    
    /**
     * Write the xml which describes the eAdventure expected outputs (nowadays used in LAMS)
     * 
     * 
     */    
        public static Element buildExpectedInputs(Document doc, List<AdaptationRule> inputs, List<AssessmentRule>  outputs) {
            
        
            
            Element parameterList = null;

            parameterList = doc.createElement( "io-parameter-list" );
            
           
            
            
      
            HashSet<String> inputsID = new HashSet<String>();
            HashSet<String> outputsID = new HashSet<String>();
            
            Iterator it = inputs.iterator( );
            //Obtain the input id 
            while (it.hasNext( )){
                AdaptationRule rule = (AdaptationRule) it.next( );
                Iterator it2 =rule.getUOLProperties( ).iterator( );
                while (it2.hasNext( )){
                    UOLProperty property = (UOLProperty) it2.next( );
                    if (!inputsID.contains( property.getId( ) )){
                        inputsID.add( property.getId( ) );
                        Element iParameter = doc.createElement( "i-parameter" );
                        iParameter.setAttribute( "name", property.getId( ) );
                        iParameter.setAttribute( "type", getType(property.getValue( )) );
                        parameterList.appendChild( iParameter );
                    }
                }
            }
            it = outputs.iterator( );
         
            //Obtain the output id 
            while (it.hasNext( )){
                AssessmentRule rule = (AssessmentRule) it.next( );
                Iterator it2 =rule.getAssessmentProperties( ).iterator( );
                while (it2.hasNext( )){
                    AssessmentProperty property = (AssessmentProperty) it2.next( );
                    if (!outputsID.contains( property.getId( ) )){
                        outputsID.add( property.getId( ) );
                        Element oParameter = doc.createElement( "o-parameter" );
                        oParameter.setAttribute( "name", property.getId( ) );
                        oParameter.setAttribute( "type", getType(property.getValue( )));
                        parameterList.appendChild( oParameter );
                    }
                }
            } 
            
           
            
         
            
            return parameterList;
            
        }
    
    
}
