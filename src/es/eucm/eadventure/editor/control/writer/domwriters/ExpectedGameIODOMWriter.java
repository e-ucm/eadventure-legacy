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
import es.eucm.eadventure.editor.control.Controller;


public class ExpectedGameIODOMWriter {

    
    private static final String STRING = "string";
    
    private static final String BOOLEAN = "boolean";
    
    private static final String INTEGER = "integer";
    
    
    public static String getType(String value, boolean isVarFlag){
        
        
        if (isVarFlag){
            if (Controller.getInstance( ).getVarFlagSummary( ).existsVar( value ))
                return INTEGER;
            else
                return BOOLEAN;
        } else {
        
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
      
        
        }
    
    
    private static boolean defaultVar(String name){
        if (name.equals( "game-completed" ))
            return true;
        else if (name.equals( "score" ))
            return true;
        else if (name.equals( "total-time" ))
            return true;
        else if (name.equals( "real-time" ))
            return true;
        else return false;
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
                        iParameter.setAttribute( "type", getType(property.getValue( ), false) );
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
                    if (!outputsID.contains( property.getId( ) ) &&!defaultVar(property.getId( ))){
                        outputsID.add( property.getId( ) );
                        Element oParameter = doc.createElement( "o-parameter" );
                        oParameter.setAttribute( "name", property.getId( ) );
                        oParameter.setAttribute( "type", getType(property.getVarName( )!=null?property.getVarName( ):property.getValue( ), property.getVarName( )!=null));
                        parameterList.appendChild( oParameter );
                    }
                }
            } 
            return parameterList;
            
        }
    
    
}
