/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
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
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.common.loader.subparsers;

import org.xml.sax.Attributes;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.data.chapter.CustomAction;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.effects.Effects;
import es.eucm.eadventure.common.data.chapter.elements.Element;
import es.eucm.eadventure.common.data.chapter.resources.Resources;

/**
 * Class to subparse actions
 * 
 * @author Eugenio Marchiori
 * 
 */
public class ActionsSubParser extends SubParser {

    /**
     * Indicates the current element being subparsed
     */
    private int subParsing;

    /**
     * Indicates the current element being read
     */
    private int reading;

    /**
     * Stores the current conditions being read
     */
    private Conditions currentConditions;

    /**
     * Stores the current effects being read
     */
    private Effects currentEffects;

    /**
     * Stores the current not-effects being read
     */
    private Effects currentNotEffects;
    
    /**
     * Stores the current click-effects being read
     * 
     */
    private Effects currentClickEffects;

    /**
     * Stores the current documentation being read
     */
    private String currentDocumentation;

    /**
     * Stores the current IdTarget being read
     */
    private String currentIdTarget;

    /**
     * Stores the current Name being read
     */
    private String currentName;

    /**
     * Stores the current needsGoTo being read
     */
    private boolean currentNeedsGoTo;

    /**
     * Stores the current keepDinstance being read
     */
    private int currentKeepDistance;

    /**
     * Stores the current customAction being read
     */
    private CustomAction currentCustomAction;

    /**
     * Stores the current Resources being read
     */
    private Resources currentResources;

    /**
     * The current subparser being used
     */
    private SubParser subParser;

    /**
     * Constant for no subparsing
     */
    private static final int SUBPARSING_NONE = 0;

    /**
     * Constant for reading nothing
     */
    private static final int READING_NONE = 0;

    /**
     * Constant for reading an action
     */
    private static final int READING_ACTION = 1;

    /**
     * Constant for reading resources
     */
    private static final int READING_RESOURCES = 2;

    /**
     * Constant for subparsing conditions
     */
    private static final int SUBPARSING_CONDITION = 1;

    /**
     * Constant for subparsing effects
     */
    private static final int SUBPARSING_EFFECT = 2;

    /**
     * The element into which the actions are written
     */
    private Element element;

    /**
     * Activate not effects
     */
    boolean activateNotEffects;
    
    /**
     * Activate click effects
     */
    boolean activateClickEffects;

    /**
     * Default constructor
     * 
     * @param chapter
     *            The chapter that is being parsed
     * @param element
     *            The element where to add the actions
     */
    public ActionsSubParser( Chapter chapter, Element element ) {

        super( chapter );
        this.element = element;
        subParsing = SUBPARSING_NONE;
        reading = READING_NONE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.cargador.subparsers.SubParser#startElement(java.lang.String, java.lang.String,
     *      java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) {

        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE ) {

            // If it is an examine, use or grab tag, create new conditions and effects
            if( qName.equals( "examine" ) || qName.equals( "grab" ) || qName.equals( "use" ) || qName.equals( "talk-to" ) ) {
                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getQName( i ).equals( "needsGoTo" ) )
                        currentNeedsGoTo = attrs.getValue( i ).equals( "yes" );
                    if( attrs.getQName( i ).equals( "keepDistance" ) )
                        currentKeepDistance = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getQName( i ).equals( "not-effects" ) )
                        activateNotEffects = attrs.getValue( i ).equals( "yes" );
                }
                currentConditions = new Conditions( );
                currentEffects = new Effects( );
                currentNotEffects = new Effects( );
                currentClickEffects = new Effects( );
                currentDocumentation = null;
                reading = READING_ACTION;
            }

            // If it is an use-with or give-to tag, create new conditions and effects, and store the idTarget
            else if( qName.equals( "use-with" ) || qName.equals( "give-to" ) || qName.equals( "drag-to" )) {
                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getQName( i ).equals( "idTarget" ) )
                        currentIdTarget = attrs.getValue( i );
                    if( attrs.getQName( i ).equals( "needsGoTo" ) )
                        currentNeedsGoTo = attrs.getValue( i ).equals( "yes" );
                    if( attrs.getQName( i ).equals( "keepDistance" ) )
                        currentKeepDistance = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getQName( i ).equals( "not-effects" ) )
                        activateNotEffects = attrs.getValue( i ).equals( "yes" );
                    if( attrs.getQName( i ).equals( "click-effects" ) )
                        activateClickEffects = attrs.getValue( i ).equals( "yes" );
                }
                currentConditions = new Conditions( );
                currentEffects = new Effects( );
                currentNotEffects = new Effects( );
                currentClickEffects = new Effects( );
                currentDocumentation = null;
                reading = READING_ACTION;
            }

            else if( qName.equals( "custom" ) || qName.equals( "custom-interact" ) ) {
                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getQName( i ).equals( "idTarget" ) )
                        currentIdTarget = attrs.getValue( i );
                    if( attrs.getQName( i ).equals( "name" ) )
                        currentName = attrs.getValue( i );
                    if( attrs.getQName( i ).equals( "needsGoTo" ) )
                        currentNeedsGoTo = attrs.getValue( i ).equals( "yes" );
                    if( attrs.getQName( i ).equals( "keepDistance" ) )
                        currentKeepDistance = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getQName( i ).equals( "not-effects" ) )
                        activateNotEffects = attrs.getValue( i ).equals( "yes" );
                    if( attrs.getQName( i ).equals( "click-effects" ) )
                        activateClickEffects = attrs.getValue( i ).equals( "yes" );
                }

                currentConditions = new Conditions( );
                currentEffects = new Effects( );
                currentNotEffects = new Effects( );
                currentClickEffects = new Effects( );
                currentDocumentation = null;
                if( qName.equals( "custom" ) )
                    currentCustomAction = new CustomAction( Action.CUSTOM );
                else
                    currentCustomAction = new CustomAction( Action.CUSTOM_INTERACT );
                reading = READING_ACTION;
            }

            // If it is a resources tag, create the new resources and switch the state
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

                // If the asset is not an special one
                //				if( !AssetsController.isAssetSpecial( path ) )
                currentResources.addAsset( type, path );
            }

            // If it is a condition tag, create new conditions and switch the state
            else if( qName.equals( "condition" ) ) {
                currentConditions = new Conditions( );
                subParser = new ConditionSubParser( currentConditions, chapter );
                subParsing = SUBPARSING_CONDITION;
            }

            // If it is a effect tag, create new effects and switch the state
            else if( qName.equals( "effect" ) ) {
                subParser = new EffectSubParser( currentEffects, chapter );
                subParsing = SUBPARSING_EFFECT;
            }
            // If it is a not-effect tag, create new effects and switch the state
            else if( qName.equals( "not-effect" ) ) {
                subParser = new EffectSubParser( currentNotEffects, chapter );
                subParsing = SUBPARSING_EFFECT;
            }
            
            // If it is a click-effect tag, create new effects and switch the state
            else if( qName.equals( "click-effect" ) ) {
                subParser = new EffectSubParser( currentClickEffects, chapter );
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
     * @see es.eucm.eadventure.engine.cargador.subparsers.SubParser#endElement(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void endElement( String namespaceURI, String sName, String qName ) {

        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE ) {

            // If it is a resources tag, add it to the object
            if( qName.equals( "resources" ) ) {
                if( reading == READING_RESOURCES ) {
                    currentCustomAction.addResources( currentResources );
                    reading = READING_NONE;
                }
            }

            // If it is a documentation tag, hold the documentation in the current element
            else if( qName.equals( "documentation" ) ) {
                currentDocumentation = currentString.toString( ).trim( );
            }

            // If it is a examine tag, store the new action in the object
            else if( qName.equals( "examine" ) ) {
                Action examineAction = new Action( Action.EXAMINE, currentConditions, currentEffects, currentNotEffects );
                examineAction.setDocumentation( currentDocumentation );
                examineAction.setKeepDistance( currentKeepDistance );
                examineAction.setNeedsGoTo( currentNeedsGoTo );
                examineAction.setActivatedNotEffects( activateNotEffects );
                examineAction.setActivatedClickEffects( activateClickEffects );
                element.addAction( examineAction );
                reading = READING_NONE;
            }

            // If it is a grab tag, store the new action in the object
            else if( qName.equals( "grab" ) ) {
                Action grabAction = new Action( Action.GRAB, currentConditions, currentEffects, currentNotEffects );
                grabAction.setDocumentation( currentDocumentation );
                grabAction.setKeepDistance( currentKeepDistance );
                grabAction.setNeedsGoTo( currentNeedsGoTo );
                grabAction.setActivatedNotEffects( activateNotEffects );
                grabAction.setActivatedClickEffects( activateClickEffects );
                element.addAction( grabAction );
                reading = READING_NONE;
            }

            // If it is an use tag, store the new action in the object
            else if( qName.equals( "use" ) ) {
                Action useAction = new Action( Action.USE, currentConditions, currentEffects, currentNotEffects );
                useAction.setDocumentation( currentDocumentation );
                useAction.setNeedsGoTo( currentNeedsGoTo );
                useAction.setKeepDistance( currentKeepDistance );
                useAction.setActivatedNotEffects( activateNotEffects );
                useAction.setActivatedClickEffects( activateClickEffects );
                element.addAction( useAction );
                reading = READING_NONE;
            }

            // If it is an use tag, store the new action in the object
            else if( qName.equals( "talk-to" ) ) {
                Action talkToAction = new Action( Action.TALK_TO, currentConditions, currentEffects, currentNotEffects );
                talkToAction.setDocumentation( currentDocumentation );
                talkToAction.setNeedsGoTo( currentNeedsGoTo );
                talkToAction.setKeepDistance( currentKeepDistance );
                talkToAction.setActivatedNotEffects( activateNotEffects );
                talkToAction.setActivatedClickEffects( activateClickEffects );
                element.addAction( talkToAction );
                reading = READING_NONE;
            }

            // If it is an use-with tag, store the new action in the object
            else if( qName.equals( "use-with" ) ) {
                Action useWithAction = new Action( Action.USE_WITH, currentIdTarget, currentConditions, currentEffects, currentNotEffects, currentClickEffects );
                useWithAction.setDocumentation( currentDocumentation );
                useWithAction.setKeepDistance( currentKeepDistance );
                useWithAction.setNeedsGoTo( currentNeedsGoTo );
                useWithAction.setActivatedNotEffects( activateNotEffects );
                useWithAction.setActivatedClickEffects( activateClickEffects );
                element.addAction( useWithAction );
                reading = READING_NONE;
            }

            // If it is an use-with tag, store the new action in the object
            else if( qName.equals( "drag-to" ) ) {
                Action useWithAction = new Action( Action.DRAG_TO, currentIdTarget, currentConditions, currentEffects, currentNotEffects, currentClickEffects );
                useWithAction.setDocumentation( currentDocumentation );
                useWithAction.setKeepDistance( currentKeepDistance );
                useWithAction.setNeedsGoTo( currentNeedsGoTo );
                useWithAction.setActivatedNotEffects( activateNotEffects );
                useWithAction.setActivatedClickEffects( activateClickEffects );
                element.addAction( useWithAction );
                reading = READING_NONE;
            }

            // If it is a give-to tag, store the new action in the object
            else if( qName.equals( "give-to" ) ) {
                Action giveToAction = new Action( Action.GIVE_TO, currentIdTarget, currentConditions, currentEffects, currentNotEffects, currentClickEffects );
                giveToAction.setDocumentation( currentDocumentation );
                giveToAction.setKeepDistance( currentKeepDistance );
                giveToAction.setNeedsGoTo( currentNeedsGoTo );
                giveToAction.setActivatedNotEffects( activateNotEffects );
                giveToAction.setActivatedClickEffects( activateClickEffects );
                element.addAction( giveToAction );
                reading = READING_NONE;
            }

            // If it is a custom tag, store the new custom action in the object
            else if( qName.equals( "custom" ) ) {
                currentCustomAction.setName( currentName );
                currentCustomAction.setConditions( currentConditions );
                currentCustomAction.setEffects( currentEffects );
                currentCustomAction.setNotEffects( currentNotEffects );
                currentCustomAction.setDocumentation( currentDocumentation );
                currentCustomAction.setKeepDistance( currentKeepDistance );
                currentCustomAction.setNeedsGoTo( currentNeedsGoTo );
                currentCustomAction.setActivatedNotEffects( activateNotEffects );
                currentCustomAction.setClickEffects( currentClickEffects );
                currentCustomAction.setActivatedClickEffects( activateClickEffects );
                //				customAction.addResources(currentResources);
                element.addAction( currentCustomAction );
                currentCustomAction = null;
                reading = READING_NONE;
            }

            // If it is a custom-interact tag, store the new custom interact action in the object
            else if( qName.equals( "custom-interact" ) ) {
                currentCustomAction.setConditions( currentConditions );
                currentCustomAction.setEffects( currentEffects );
                currentCustomAction.setNotEffects( currentNotEffects );
                currentCustomAction.setName( currentName );
                currentCustomAction.setTargetId( currentIdTarget );
                currentCustomAction.setDocumentation( currentDocumentation );
                currentCustomAction.setKeepDistance( currentKeepDistance );
                currentCustomAction.setNeedsGoTo( currentNeedsGoTo );
                currentCustomAction.setActivatedNotEffects( activateNotEffects );
                currentCustomAction.setClickEffects( currentClickEffects );
                currentCustomAction.setActivatedClickEffects( activateClickEffects );
                //				customAction.addResources(currentResources);
                element.addAction( currentCustomAction );
                currentCustomAction = null;
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
                // Store the conditions in the resources
                if( reading == READING_RESOURCES )
                    currentResources.setConditions( currentConditions );

                // Switch state
                subParsing = SUBPARSING_NONE;
            }
        }

        // If an effect is being subparsed
        else if( subParsing == SUBPARSING_EFFECT ) {
            // Spread the call
            subParser.endElement( namespaceURI, sName, qName );

            // If the effect tag is being closed, switch the state
            if( qName.equals( "effect" ) ) {
                subParsing = SUBPARSING_NONE;
            }
            // If the not-effect tag is being closed, switch the state
            else if( qName.equals( "not-effect" ) ) {
                subParsing = SUBPARSING_NONE;
            }
            // If the not-effect tag is being closed, switch the state
            else if( qName.equals( "click-effect" ) ) {
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

        // If it is reading an effect or a condition, spread the call
        else
            subParser.characters( buf, offset, len );
    }

}
