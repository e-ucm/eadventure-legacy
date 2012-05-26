/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.common.loader.subparsers;

import org.xml.sax.Attributes;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.elements.Description;


/**
 * Class for subparsing set of descriptions, it means, parse all the set of name, description and detailed descriptions with their associated
 * soundPaths and the conditions for each description.
 * 
 */
public class DescriptionsSubParser extends SubParser {

   
    private Description description;
    
    private Conditions currentConditions;
    
    /**
     * Constant for subparsing nothing.
     */
    private static final int SUBPARSING_NONE = 0;

    /**
     * Constant for subparsing condition tag.
     */
    private static final int SUBPARSING_CONDITION = 1;
    
    /**
     * Subparser for  conditions.
     */
    private SubParser subParser;
    
    /**
     * Stores the current element being subparsed.
     */
    private int subParsing = SUBPARSING_NONE;
    
    /**
     * Constructor 
     * 
     * @param description
     *          the description to be parsed
     * @param chapter
     *          Chapter data to store the read data
     */
    public DescriptionsSubParser(Description description, Chapter chapter){
        super( chapter );
        this.description = description;
    }
    
    
    @Override
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) {
        
     // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE ) {
         
            
         // If it is a name tag, store the name 
            if( qName.equals( "name" ) ) {
                String soundPath = "";
                
                // if name tag has soundPath attribute, add it to the active area data model
                for( int i = 0; i < attrs.getLength( ); i++ ) { 
                    if( attrs.getQName( i ).equals( "soundPath" ) )
                        soundPath = attrs.getValue( i );
                }
                
                
                description.setNameSoundPath( soundPath );
                
            }
            
         // If it is a brief tag, store the brief description 
            else if( qName.equals( "brief" ) ) {
                
                String soundPath = "";
                
                // if brief tag has soundPath attribute, add it to the data model
                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getQName( i ).equals( "soundPath" ) )
                        soundPath = attrs.getValue( i );
                }
                
                description.setDescriptionSoundPath( soundPath );
            }

            // If it is a detailed tag, store the detailed description 
            else if( qName.equals( "detailed" ) ) {
                
                String soundPath = "";
                
                // if detailed tag has soundPath attribute, add it to the data model
                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getQName( i ).equals( "soundPath" ) )
                        soundPath = attrs.getValue( i );
                }
                
                description.setDetailedDescriptionSoundPath( soundPath );
            }
            
            // If it is a condition tag, create new conditions and switch the state
            else if( qName.equals( "condition" ) ) {
                currentConditions = new Conditions( );
                subParser = new ConditionSubParser( currentConditions, chapter );
                subParsing = SUBPARSING_CONDITION;
            }
            
        }// end if subparsing none
            
     // If it is reading an effect or a condition, spread the call
        if( subParsing != SUBPARSING_NONE ) {
            subParser.startElement( namespaceURI, sName, qName, attrs );
        }

    }
    
    
    @Override
    public void endElement( String namespaceURI, String sName, String qName ) {

        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE ) {
            
                    
             // If it is a name tag, store the name in the active area
            if( qName.equals( "name" ) ) {
                description.setName( currentString.toString( ).trim( ) );
            }
            // If it is a brief tag, store the brief description in the active area
            else if( qName.equals( "brief" ) ) {
                description.setDescription( currentString.toString( ).trim( ) );
            }
         // If it is a detailed tag, store the detailed description in the active area
            else if( qName.equals( "detailed" ) ) {
                description.setDetailedDescription( currentString.toString( ).trim( ) );
            }
    
            // Reset the current string
            currentString = new StringBuffer( );
        
        }// end if subparsing none
        
        // If a condition is being subparsed
        else if( subParsing == SUBPARSING_CONDITION ) {
            // Spread the call
            subParser.endElement( namespaceURI, sName, qName );

            // If the condition tag is being closed
            if( qName.equals( "condition" ) ) {
                this.description.setConditions( currentConditions );
              
                // Switch state
                subParsing = SUBPARSING_NONE;
            }
        }
    }

    
    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#characters(char[], int, int)
     */
    @Override
    public void characters( char[] buf, int offset, int len ) {
        
     // If no element is being subparsed, read the characters
        if( subParsing == SUBPARSING_NONE )
            super.characters( buf, offset, len );

        // If a condition is being subparsed, spread the call
        else if( subParsing == SUBPARSING_CONDITION )
            subParser.characters( buf, offset, len );
        
    }
    

}
