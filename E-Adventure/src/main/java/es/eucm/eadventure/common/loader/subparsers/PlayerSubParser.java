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

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.elements.Description;
import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.common.data.chapter.resources.Resources;

/**
 * Class to subparse objetcs
 */
public class PlayerSubParser extends SubParser {

    /* Attributes */

    /**
     * Constant for subparsing nothing
     */
    private static final int SUBPARSING_NONE = 0;

    /**
     * Constant for subparsing condition tag
     */
    private static final int SUBPARSING_CONDITION = 1;
    
    /**
     * Constant for subparsing description tag.
     */
    private static final int SUBPARSING_DESCRIPTION = 2;

    /**
     * Stores the current element being subparsed
     */
    private int subParsing = SUBPARSING_NONE;

    /**
     * Player being parsed
     */
    private Player player;

    /**
     * Current resources being parsed
     */
    private Resources currentResources;

    /**
     * Current conditions being parsed
     */
    private Conditions currentConditions;

    /**
     * Subparser for conditions
     */
    private SubParser subParser;
    
    
    private List<Description> descriptions;
    
    private Description description;

    /* Methods */

    /**
     * Constructor
     * 
     * @param chapter
     *            Chapter data to store the read data
     */
    public PlayerSubParser( Chapter chapter ) {

        super( chapter );
        descriptions = new ArrayList<Description>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#startElement(java.lang.String, java.lang.String,
     *      java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) {

        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE ) {

            // If it is a player tag, create the player
            if( qName.equals( "player" ) ) {
                player = new Player( );
                descriptions = new ArrayList<Description>();
                player.setDescriptions( descriptions );
            }

            // If it is a resources tag, create new resources
            else if( qName.equals( "resources" ) ) {
                currentResources = new Resources( );
                
                for (int i = 0; i < attrs.getLength( ); i++) {
                    if (attrs.getQName( i ).equals( "name" ))
                        currentResources.setName( attrs.getValue( i ) );
                }

            }

            // If it is a condition tag, create new conditions, new subparser and switch the state
            else if( qName.equals( "condition" ) ) {
                currentConditions = new Conditions( );
                subParser = new ConditionSubParser( currentConditions, chapter );
                subParsing = SUBPARSING_CONDITION;
            }

            // If it is an asset tag, read it and add it to the current resources
            else if( qName.equals( "asset" ) ) {
                String type = "";
                String path = "";

                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getQName( i ).equals( "type" ) )
                        type = attrs.getValue( i );
                    if( attrs.getQName( i ).equals( "uri" ) )
                        path = attrs.getValue( i );
                }

                // If the asset is not an special one
                //if( !AssetsController.isAssetSpecial( path ) )
                currentResources.addAsset( type, path );
            }

            // If it is a frontcolor or bordercolor tag, pick the color
            else if( qName.equals( "frontcolor" ) || qName.equals( "bordercolor" ) ) {
                String color = "";

                // Pick the color
                for( int i = 0; i < attrs.getLength( ); i++ )
                    if( attrs.getQName( i ).equals( "color" ) )
                        color = attrs.getValue( i );

                // Set the color in the player
                if( qName.equals( "frontcolor" ) )
                    player.setTextFrontColor( color );
                if( qName.equals( "bordercolor" ) )
                    player.setTextBorderColor( color );
            }

            else if( qName.equals( "textcolor" ) ) {
                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getQName( i ).equals( "showsSpeechBubble" ) )
                        player.setShowsSpeechBubbles( attrs.getValue( i ).equals( "yes" ) );
                    if( attrs.getQName( i ).equals( "bubbleBkgColor" ) )
                        player.setBubbleBkgColor( attrs.getValue( i ) );
                    if( attrs.getQName( i ).equals( "bubbleBorderColor" ) )
                        player.setBubbleBorderColor( attrs.getValue( i ) );
                }
            }

            // If it is a voice tag, take the voice and the always synthesizer option
            else if( qName.equals( "voice" ) ) {
                String voice = new String( "" );
                String response;
                boolean alwaysSynthesizer = false;

                // Pick the voice and synthesizer option
                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getQName( i ).equals( "name" ) )
                        voice = attrs.getValue( i );
                    if( attrs.getQName( i ).equals( "synthesizeAlways" ) ) {
                        response = attrs.getValue( i );
                        if( response.equals( "yes" ) )
                            alwaysSynthesizer = true;
                    }

                }
                player.setAlwaysSynthesizer( alwaysSynthesizer );
                player.setVoice( voice );

            }
            
         // If it is a description tag, create the new description (with its id)
            else if( qName.equals( "description" ) ) {
                description = new Description();
                subParser = new DescriptionsSubParser(description, chapter);
                subParsing = SUBPARSING_DESCRIPTION; 
            }

        }

        // If a condition is being subparsed, spread the call
        if( subParsing != SUBPARSING_NONE) {
            subParser.startElement( namespaceURI, sName, qName, attrs );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#endElement(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void endElement( String namespaceURI, String sName, String qName ) {

        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE ) {

            // If it is a player tag, store the player in the game data
            if( qName.equals( "player" ) ) {
                chapter.setPlayer( player );
            }

            // If it is a documentation tag, hold the documentation in the player
            else if( qName.equals( "documentation" ) ) {
                player.setDocumentation( currentString.toString( ).trim( ) );
            }

            // If it is a resources tag, add the resources to the player
            else if( qName.equals( "resources" ) ) {
                player.addResources( currentResources );
            }
            // Reset the current string
            currentString = new StringBuffer( );
        }

        // If a condition is being subparsed
        else if( subParsing == SUBPARSING_CONDITION ) {
            // Spread the call
            subParser.endElement( namespaceURI, sName, qName );

            // If the condition tag is being closed, add the condition to the resources, and switch the state
            if( qName.equals( "condition" ) ) {
                currentResources.setConditions( currentConditions );
                subParsing = SUBPARSING_NONE;
            }
        }
        
     // If it is a description tag, create the new description (with its id)
        else if( subParsing == SUBPARSING_DESCRIPTION ) {
            // Spread the call
            subParser.endElement( namespaceURI, sName, qName );
            if( qName.equals( "description" ) ) {
                this.descriptions.add( description );
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

        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE )
            super.characters( buf, offset, len );

        /// If there are some kind of subparsing, spread the call
        else 
            subParser.characters( buf, offset, len );
    }
}
