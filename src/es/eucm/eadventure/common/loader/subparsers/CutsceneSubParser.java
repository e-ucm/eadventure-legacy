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
package es.eucm.eadventure.common.loader.subparsers;

import org.xml.sax.Attributes;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.data.chapter.NextScene;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.effects.AbstractEffect;
import es.eucm.eadventure.common.data.chapter.effects.Effects;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.data.chapter.scenes.Cutscene;
import es.eucm.eadventure.common.data.chapter.scenes.Slidescene;
import es.eucm.eadventure.common.data.chapter.scenes.Videoscene;

/**
 * Class to subparse slidescenes
 */
public class CutsceneSubParser extends SubParser {

    /* Attributes */

    /**
     * Constant for reading nothing
     */
    private static final int READING_NONE = 0;

    /**
     * Constant for reading resources tag
     */
    private static final int READING_RESOURCES = 1;

    /**
     * Constant for reading next-scene tag
     */
    private static final int READING_NEXT_SCENE = 2;

    /**
     * Constant for subparsing nothing
     */
    private static final int SUBPARSING_NONE = 0;

    /**
     * Constant for subparsing condition tag
     */
    private static final int SUBPARSING_CONDITION = 1;

    /**
     * Constant for subparsing effect tag
     */
    private static final int SUBPARSING_EFFECT = 2;

    /**
     * Stores the current element being parsed
     */
    private int reading = READING_NONE;

    /**
     * Stores the current element being subparsed
     */
    private int subParsing = SUBPARSING_NONE;

    /**
     * Stores the current slidescene being parsed
     */
    private Cutscene cutscene;

    /**
     * Stores the current resources being parsed
     */
    private Resources currentResources;

    /**
     * Stores the current next-scene being used
     */
    private NextScene currentNextScene;

    /**
     * Stores the current conditions being parsed
     */
    private Conditions currentConditions;

    /**
     * Stores the current effects being parsed
     */
    private Effects currentEffects;

    /**
     * The subparser for the condition or effect tags
     */
    private SubParser subParser;

    /* Methods */

    /**
     * Constructor
     * 
     * @param chapter
     *            Chapter data to store the read data
     */
    public CutsceneSubParser( Chapter chapter ) {

        super( chapter );
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

            // If it is a slidescene tag, create a new slidescene with its id
            if( qName.equals( "slidescene" ) || qName.equals( "videoscene" ) ) {
                String slidesceneId = "";
                boolean initialScene = false;
                String idTarget = "";
                int x = Integer.MIN_VALUE, y = Integer.MIN_VALUE;
                int transitionType = 0, transitionTime = 0;
                String next = "go-back";

                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getQName( i ).equals( "id" ) )
                        slidesceneId = attrs.getValue( i );
                    if( attrs.getQName( i ).equals( "start" ) )
                        initialScene = attrs.getValue( i ).equals( "yes" );
                    if( attrs.getQName( i ).equals( "idTarget" ) )
                        idTarget = attrs.getValue( i );
                    if( attrs.getQName( i ).equals( "destinyX" ) )
                        x = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getQName( i ).equals( "destinyY" ) )
                        y = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getQName( i ).equals( "transitionType" ) )
                        transitionType = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getQName( i ).equals( "transitionTime" ) )
                        transitionTime = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getQName( i ).equals( "next" ) )
                        next = attrs.getValue( i );
                }

                if( qName.equals( "slidescene" ) )
                    cutscene = new Slidescene( slidesceneId );
                else
                    cutscene = new Videoscene( slidesceneId );
                if( initialScene )
                    chapter.setTargetId( slidesceneId );
                cutscene.setTargetId( idTarget );
                cutscene.setPositionX( x );
                cutscene.setPositionY( y );
                cutscene.setTransitionType( transitionType );
                cutscene.setTransitionTime( transitionTime );
                if( next.equals( "go-back" ) ) {
                    cutscene.setNext( Cutscene.GOBACK );
                }
                else if( next.equals( "new-scene" ) ) {
                    cutscene.setNext( Cutscene.NEWSCENE );
                }
                else if( next.equals( "end-chapter" ) ) {
                    cutscene.setNext( Cutscene.ENDCHAPTER );
                }
            }

            // If it is a resources tag, create new resources
            else if( qName.equals( "resources" ) ) {
                currentResources = new Resources( );
                
                for (int i = 0; i < attrs.getLength( ); i++) {
                    if (attrs.getQName( i ).equals( "name" ))
                        currentResources.setName( attrs.getValue( i ) );
                }

                reading = READING_RESOURCES;
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

                currentResources.addAsset( type, path );
            }

            // If it is an end-game tag, store it in the slidescene
            else if( qName.equals( "end-game" ) ) {
                cutscene.setNext( Cutscene.ENDCHAPTER );
            }

            // If it is a next-scene tag, create the new next scene
            else if( qName.equals( "next-scene" ) ) {
                String idTarget = "";
                int x = Integer.MIN_VALUE, y = Integer.MIN_VALUE;
                int transitionType = 0, transitionTime = 0;

                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getQName( i ).equals( "idTarget" ) )
                        idTarget = attrs.getValue( i );
                    if( attrs.getQName( i ).equals( "x" ) )
                        x = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getQName( i ).equals( "y" ) )
                        y = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getQName( i ).equals( "transitionType" ) )
                        transitionType = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getQName( i ).equals( "transitionTime" ) )
                        transitionTime = Integer.parseInt( attrs.getValue( i ) );
                }

                currentNextScene = new NextScene( idTarget, x, y );
                currentNextScene.setTransitionType( transitionType );
                currentNextScene.setTransitionTime( transitionTime );
                reading = READING_NEXT_SCENE;
            }

            // If it is a condition tag, create the new condition, the subparser and switch the state
            else if( qName.equals( "condition" ) ) {
                currentConditions = new Conditions( );
                subParser = new ConditionSubParser( currentConditions, chapter );
                subParsing = SUBPARSING_CONDITION;
            }

            // If it is a effect tag, create the new effect, the subparser and switch the state
            else if( qName.equals( "effect" ) ) {
                currentEffects = new Effects( );
                subParser = new EffectSubParser( currentEffects, chapter );
                subParsing = SUBPARSING_EFFECT;
            }

            // If it is a post-effect tag, create the new effect, the subparser and switch the state
            else if( qName.equals( "post-effect" ) ) {
                currentEffects = new Effects( );
                subParser = new EffectSubParser( currentEffects, chapter );
                subParsing = SUBPARSING_EFFECT;
            }
        }

        // If it is reading an effect or a condition, spread the call
        if( subParsing != SUBPARSING_NONE ) {
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

            // If it is a slidescene tag, add it to the game data
            if( qName.equals( "slidescene" ) || qName.equals( "videoscene" ) ) {
                chapter.addCutscene( cutscene );
            }

            // If it is a resources tag, add it to the slidescene
            else if( qName.equals( "resources" ) ) {
                cutscene.addResources( currentResources );
                reading = READING_NONE;
            }

            // If it is a name tag, add the name to the slidescene
            else if( qName.equals( "name" ) ) {
                cutscene.setName( currentString.toString( ).trim( ) );
            }

            // If it is a documentation tag, hold the documentation in the slidescene
            else if( qName.equals( "documentation" ) ) {
                cutscene.setDocumentation( currentString.toString( ).trim( ) );
            }

            // If it is a next-scene tag, add the next scene to the slidescene
            else if( qName.equals( "next-scene" ) ) {
                cutscene.addNextScene( currentNextScene );
                reading = READING_NONE;
            }

            // Reset the current string
            currentString = new StringBuffer( );
        }

        // If a condition is being subparsed
        else if( subParsing == SUBPARSING_CONDITION ) {
            // Spread the call
            subParser.endElement( namespaceURI, sName, qName );

            // If the condition tag is being closed
            if( qName.equals( "condition" ) ) {
                // If we are parsing a resources tag, add the conditions to the current resources
                if( reading == READING_RESOURCES )
                    currentResources.setConditions( currentConditions );

                // If we are parsing a next-scene tag, add the conditions to the current next scene
                if( reading == READING_NEXT_SCENE )
                    currentNextScene.setConditions( currentConditions );

                // Switch the state
                subParsing = SUBPARSING_NONE;
            }
        }

        // If an effect is being subparsed
        else if( subParsing == SUBPARSING_EFFECT ) {
            // Spread the call
            subParser.endElement( namespaceURI, sName, qName );

            // If the effect tag is being closed, store the effect in the next scene and switch the state
            if( qName.equals( "effect" ) ) {
                if( currentNextScene != null )
                    currentNextScene.setEffects( currentEffects );
                else {
                    Effects effects = cutscene.getEffects( );
                    for( AbstractEffect effect : currentEffects.getEffects( ) ) {
                        effects.add( effect );
                    }
                }
                subParsing = SUBPARSING_NONE;
            }

            // If the effect tag is being closed, add the post-effects to the current next scene and switch the state
            if( qName.equals( "post-effect" ) ) {
                if( currentNextScene != null )
                    currentNextScene.setPostEffects( currentEffects );
                else {
                    Effects effects = cutscene.getEffects( );
                    for( AbstractEffect effect : currentEffects.getEffects( ) ) {
                        effects.add( effect );
                    }
                }
                //currentNextScene.setPostEffects( currentEffects );

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

        // If it is reading an effect or a condition
        else
            subParser.characters( buf, offset, len );
    }
}
