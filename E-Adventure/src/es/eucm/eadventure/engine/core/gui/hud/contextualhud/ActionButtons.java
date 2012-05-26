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
package es.eucm.eadventure.engine.core.gui.hud.contextualhud;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.CustomAction;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalItem;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalNPC;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.core.gui.GUIAudioDescriptionsHandler;

/**
 * This class contains all the graphical information about the action buttons
 */
public class ActionButtons {

    public static final int MAX_BUTTON_WIDTH = 50;

    public static final int MAX_BUTTON_HEIGHT = 50;

    public static final int MIN_BUTTON_WIDTH = 35;

    public static final int MIN_BUTTON_HEIGHT = 35;

    private double radius = 50;

    /**
     * X coordinate of the center of the action buttons
     */
    private int centerX;

    /**
     * Y coordinate of the center of the action buttons
     */
    private int centerY;

    /**
     * Index of the overed action button
     */
    private ActionButton buttonOver;

    /**
     * Index of the pressed action button
     */
    private ActionButton buttonPressed;

    private List<ActionButton> buttons;

    /*
     * Default action buttons, so they don't have to be generated each time
     */
    private ActionButton handButton;

    private ActionButton mouthButton;

    private ActionButton eyeButton;

    private long appearedTime = Long.MAX_VALUE;

    private static long appearingTime = 600L;

    private GUIAudioDescriptionsHandler audioDescHandler;
    /**
     * Constructor of the class. Requires that the MultimediaManager class is
     * loaded.
     * 
     * @param customized
     *            True if the graphics of the HUD are customized, false
     *            otherwise
     */
    public ActionButtons( boolean customized, GUIAudioDescriptionsHandler audioDescHandler ) {

        buttons = new ArrayList<ActionButton>( );

        //No action button is overed or pressed
        buttonOver = null;
        buttonPressed = null;

        handButton = new ActionButton( ActionButton.USE_BUTTON, audioDescHandler );
        mouthButton = new ActionButton( ActionButton.TALK_BUTTON, audioDescHandler );
        eyeButton = new ActionButton( ActionButton.EXAMINE_BUTTON, audioDescHandler );
        
        this.audioDescHandler = audioDescHandler;
    }

    /**
     * Recreates the list of action buttons, depending of the type of the
     * element.
     * 
     * @param functionalElement
     */
    public void recreate( int posX, int posY, FunctionalElement functionalElement ) {

        centerX = posX;
        centerY = posY;
        buttons.clear( );
        if( functionalElement instanceof FunctionalItem ) {
            FunctionalItem item = (FunctionalItem) functionalElement;
            addDefaultObjectButtons( item );
            addCustomActionButtons( ( (FunctionalItem) functionalElement ).getItem( ).getActions( ), functionalElement );
        }
        if( functionalElement instanceof FunctionalNPC ) {
            addDefaultCharacterButtons( ( (FunctionalNPC) functionalElement ) );
            addCustomActionButtons( ( (FunctionalNPC) functionalElement ).getNPC( ).getActions( ), functionalElement );
        }
        recalculateButtonsPositions( );
        clearButtons( );
        appearedTime = System.currentTimeMillis( );
    }

    /**
     * Method that adds the necessary custom action buttons to the list of
     * buttons. The number of buttons is limited to 8 (including the default
     * ones)
     * 
     * @param actions
     *            the actions of the element
     * @param functionalElement
     *            the functional element with the actions
     */
    private void addCustomActionButtons( List<Action> actions, FunctionalElement functionalElement ) {

        List<CustomAction> added = new ArrayList<CustomAction>( );
        boolean drag_to = false;
        for( Action action : actions ) {
            if( buttons.size( ) >= 8 )
                return;
            if( action.getType( ) == Action.DRAG_TO && !drag_to&&!functionalElement.isInInventory( )) {
                boolean add = functionalElement.getFirstValidAction( Action.DRAG_TO ) != null;
                if( add ) {
                buttons.add( new ActionButton(ActionButton.DRAG_BUTTON, audioDescHandler) );
                drag_to = true;
                }
            }
            if( action.getType( ) == Action.CUSTOM ) {
                boolean add = functionalElement.getFirstValidCustomAction( ( (CustomAction) action ).getName( ) ) != null;
                for( CustomAction customAction : added ) {
                    if( customAction.getName( ).equals( ( (CustomAction) action ).getName( ) ) )
                        add = false;
                }
                if( add ) {
                    buttons.add( new ActionButton( (CustomAction) action, audioDescHandler ) );
                    added.add( (CustomAction) action );
                }
            }
            else if( action.getType( ) == Action.CUSTOM_INTERACT /*&& functionalElement.isInInventory( )*/ ) {
                boolean add = functionalElement.getFirstValidCustomInteraction( ( (CustomAction) action ).getName( ) ) != null;
                for( CustomAction customAction : added ) {
                    if( customAction.getName( ).equals( ( (CustomAction) action ).getName( ) ) )
                        add = false;
                }
                if( add ) {
                    buttons.add( new ActionButton( (CustomAction) action, audioDescHandler ) );
                    added.add( (CustomAction) action );
                }
            }
        }
    }

    /**
     * Clear buttons of any pressed or over information
     */
    private void clearButtons( ) {

        for( ActionButton ab : buttons ) {
            //ab.setPressed( false );
            ab.setOver( false );
        }
        buttonOver = null;
        buttonPressed = null;
    }

    /**
     * Recalculate the buttons positions, acording to their size and place on
     * the screen
     */
    private void recalculateButtonsPositions( ) {

        double degreeIncrement = Math.PI / ( buttons.size( ) - 1 );
        double angle = Math.PI;
        int newCenterY = centerY;
        int newCenterX = centerX;
        // TODO check the radius recalculation to get an appropiate distribution
        radius = 20 * buttons.size( );

        if( centerY > GUI.WINDOW_HEIGHT / 2 ) {
            if( centerY > GUI.WINDOW_HEIGHT - MAX_BUTTON_HEIGHT / 2 )
                newCenterY = GUI.WINDOW_HEIGHT - MAX_BUTTON_HEIGHT / 2;
            degreeIncrement = -degreeIncrement;
        }
        else {
            if( centerY < MAX_BUTTON_HEIGHT / 2 )
                newCenterY = MAX_BUTTON_HEIGHT / 2;
        }

        if( centerX > GUI.WINDOW_WIDTH - MAX_BUTTON_WIDTH / 2 )
            newCenterX = GUI.WINDOW_WIDTH - MAX_BUTTON_WIDTH / 2;
        if( centerX < MAX_BUTTON_WIDTH )
            newCenterX = MAX_BUTTON_WIDTH;

        if( newCenterX < ( radius + MAX_BUTTON_WIDTH / 2 ) ) {
            if( newCenterY - radius < MAX_BUTTON_HEIGHT ) {
                angle = angle / 2;
                degreeIncrement = degreeIncrement / 2;
                radius = radius * 1.5;
            }
            else if( newCenterY + radius > GUI.WINDOW_HEIGHT - MAX_BUTTON_HEIGHT / 2 ) {
                angle = angle + angle / 2;
                degreeIncrement = degreeIncrement / 2;
                radius = radius * 1.5;
            }
            else {
                if( centerY > GUI.WINDOW_HEIGHT / 2 )
                    degreeIncrement = -degreeIncrement;
                angle = angle / 2;
            }
        }
        else if( newCenterX > ( GUI.WINDOW_WIDTH - radius - MAX_BUTTON_WIDTH / 2 ) ) {
            if( newCenterY - radius < MAX_BUTTON_HEIGHT ) {
                angle = angle / 2;
                degreeIncrement = -degreeIncrement / 2;
                radius = radius * 1.5;
            }
            else if( newCenterY + radius > GUI.WINDOW_HEIGHT - MAX_BUTTON_HEIGHT / 2 ) {
                angle = angle + angle / 2;
                degreeIncrement = -degreeIncrement / 2;
                radius = radius * 1.5;
            }
            else {
                if( centerY > GUI.WINDOW_HEIGHT / 2 )
                    degreeIncrement = -degreeIncrement;
                angle = -angle / 2;
            }
        }

        for( ActionButton ab : buttons ) {
            ab.setPosX( (int) ( Math.cos( angle ) * radius + newCenterX ) );
            ab.setPosY( (int) ( Math.sin( angle ) * radius + newCenterY ) );
            angle -= degreeIncrement;
        }

    }

    /**
     * Adds the default buttons for a character element
     * 
     * @param functionalNPC
     */
    private void addDefaultCharacterButtons( FunctionalNPC functionalNPC ) {
        if (functionalNPC.getFirstValidAction( Action.EXAMINE ) != null
                || functionalNPC.getElement( ).getDescriptions( ).size() > 1
                || (functionalNPC.getElement( ).getDescription( 0 ).getDetailedDescription( ) != null
                    && !functionalNPC.getElement( ).getDescription( 0 ).getDetailedDescription( ).equals( "" )))
             buttons.add( eyeButton );
        if (functionalNPC.getFirstValidAction( Action.TALK_TO ) != null)
            buttons.add( mouthButton );
        boolean use = functionalNPC.getFirstValidAction( Action.USE ) != null;
        if( use ) {
            handButton.reload( ActionButton.USE_BUTTON );
            buttons.add( handButton );
        } 
    }

    /**
     * Adds the default buttons for non-character elements
     */
    private void addDefaultObjectButtons( FunctionalItem item ) {
        if (item.getFirstValidAction( Action.EXAMINE ) != null
                || item.getElement( ).getDescriptions( ).size() > 1
                || (item.getElement( ).getDescription( 0 ).getDetailedDescription( ) != null
                    && !item.getElement( ).getDescription( 0 ).getDetailedDescription( ).equals( "" )))
            buttons.add( eyeButton );

        boolean addHandButton = false;
        
        if( !item.isInInventory( ) ) {
            handButton.reload( ActionButton.GRAB_BUTTON );
            Action grabAction = item.getFirstValidAction( Action.GRAB );
            Action useAction = item.getFirstValidAction( Action.USE );
            if ( grabAction != null)
                addHandButton = true;
            if( useAction != null ) {
                //if the conditions of useAction are not met and the conditions of grab action are met, the handButton
                //name must be "grabAction"
                if ( (useAction.isConditionsAreMeet( ) || (grabAction != null && !grabAction.isConditionsAreMeet( )) ) )
                    handButton.reload( ActionButton.USE_BUTTON );
                addHandButton = true;
            }
        }
        else {
            /*
             * Change the next lines of code to distinguish between give to/ use with
             * 
             * 
             * 
            // check if can be use alone and the action "use" don't meet the conditions
            // this way, check if useWith, giveTo or both meet the conditions to show this actions in the hud
            Action giveToAction = item.getFirstValidAction( Action.GIVE_TO );
            Action useWithAction =  item.getFirstValidAction( Action.USE_WITH );
            Action useAction = item.getFirstValidAction( Action.USE );
            boolean useAlone = item.canBeUsedAlone( );
            boolean giveTo = giveToAction != null;
            boolean useWith = useWithAction != null;
            addHandButton = useAlone || giveTo || useWith; 
            boolean useActionNotMeetConditions = useAction!=null && !useAction.isConditionsAreMeet( );
            boolean giveToConditionsAreMet = giveToAction.isConditionsAreMeet( );
            boolean useWithConditionsAreMet = useWithAction.isConditionsAreMeet( );
            if( useAlone && !giveTo && !useWith ) {
                handButton.setName( TC.get( "ActionButton.Use" ) );
            }
            else if( (!useAlone || (useActionNotMeetConditions && giveToConditionsAreMet)) && giveTo && (!useWith || !useWithConditionsAreMet )) {
                handButton.setName( TC.get( "ActionButton.GiveTo" ) );
            }
            else if( (!useAlone || (useActionNotMeetConditions && useWithConditionsAreMet )) && (!giveTo && !giveToConditionsAreMet) && useWith ) {
                handButton.setName( TC.get( "ActionButton.UseWith" ) );
            }
            else if( (!useAlone || (useActionNotMeetConditions && (useWithConditionsAreMet && giveToConditionsAreMet)  )) && giveTo && useWith ) {
                handButton.setName( TC.get( "ActionButton.UseGive" ) );
            }
            else {
                handButton.setName( TC.get( "ActionButton.Use" ) );
            }
             */
            boolean useAlone = item.canBeUsedAlone( );
            boolean giveTo = item.getFirstValidAction( Action.GIVE_TO ) != null;
            boolean useWith = item.getFirstValidAction( Action.USE_WITH ) != null;
            addHandButton = useAlone || giveTo || useWith; 
            if( useAlone && !giveTo && !useWith ) {
                handButton.reload( ActionButton.USE_BUTTON );
            }
            else if( !useAlone && giveTo && !useWith ) {
                handButton.reload( ActionButton.GIVETO_BUTTON );
            }
            else if( !useAlone && !giveTo && useWith ) {
                handButton.reload( ActionButton.USE_WITH_BUTTON );
            }
            else if( !useAlone && giveTo && useWith ) {
                handButton.reload( ActionButton.USEWITHGIVETO_BUTTON );
            }
            else {
                handButton.reload( ActionButton.USE_BUTTON );
            }
        }
        if (addHandButton)
            buttons.add( handButton );
    }

    /**
     * There has been a mouse moved over the action buttons
     * 
     * @param x
     *            int coordinate
     * @param y
     *            int coordinate
     */
    public void mouseMoved( MouseEvent e ) {

        clearButtons( );
        if( e != null ) {
            ActionButton ab = getButton( e.getX( ), e.getY( ) );
            if( ab != null ) {
                ab.setOver( true );
                buttonOver = ab;
            }
        }
    }

    /**
     * There has been a click in the action buttons
     * 
     * @param x
     *            int coordinate
     * @param y
     *            int coordinate
     */
    public void mouseClicked( MouseEvent e ) {

        clearButtons( );
        if( e != null ) {
            ActionButton ab = getButton( e.getX( ), e.getY( ) );
            if( ab != null ) {
                //ab.setPressed( true );
                buttonPressed = ab;
            }
        }
    }

    /**
     * Draw the action buttons given a Graphics2D
     * 
     * @param g
     *            Graphics2D to be used
     */
    public void draw( Graphics2D g ) {

        //For each action button
        float percent = (float) ( System.currentTimeMillis( ) - appearedTime ) / (float) appearingTime;
        //System.out.println("" + System.currentTimeMillis() + " " + appearedTime + " " + percent);
        if( percent > 1 )
            percent = 1;

        Composite original = g.getComposite( );
        g.setColor( Color.RED );
        Composite alphaComposite = AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.4f );
        g.setComposite( alphaComposite );
        g.fillOval( centerX - 8, centerY - 8, 16, 16 );

        for( ActionButton ab : buttons ) {
            g.setComposite( alphaComposite );
            g.setColor( Color.RED );
            int posX = (int) ( ( ab.getPosX( ) - centerX ) * percent + centerX );
            int posY = (int) ( ( ab.getPosY( ) - centerY ) * percent + centerY );
            if( ab.isOver( ) ) {
                Stroke temp = g.getStroke( );
                g.setStroke( new BasicStroke( 3.0f ) );
                g.drawLine( centerX, centerY, ab.getPosX( ), ab.getPosY( ) );
                g.setStroke( temp );
            }
            else {
                g.drawLine( centerX, centerY, posX, posY );
            }
            g.setComposite( original );
            ab.draw( g, percent, posX, posY );
        }
        if( buttonOver != null ) {
            buttonOver.drawName( g );
        }

    }

    /**
     * Get the current button pressed
     * 
     * @return button pressed
     */
    public ActionButton getButtonPressed( ) {

        return buttonPressed;
    }

    /**
     * Get the current button pressed
     * 
     * @return button pressed
     */
    public ActionButton getButtonOver( ) {

        return buttonOver;
    }

    /**
     * Returns the button at the given coordinates
     * 
     * @param x
     *            the x-axis value
     * @param y
     *            the y-axis value
     * @return the button in that place
     */
    public ActionButton getButton( int x, int y ) {

        for( ActionButton ab : buttons ) {
            if( ab.isInside( x, y ) ) {
                return ab;
            }
        }
        return null;
    }

    // GAMETEL
    public int getButtonCount () {
        return buttons.size( );
    }
    
    public int getButtonX( int index ){
        if (index<0 || index>=buttons.size( ))
            return Integer.MIN_VALUE;
        return buttons.get( index ).getPosX( );
    }
    
    public int getButtonY( int index ){
        if (index<0 || index>=buttons.size( ))
            return Integer.MIN_VALUE;
        return buttons.get( index ).getPosY( );
    }
    
    public int getButtonWidth( int index ){
        if (index<0 || index>=buttons.size( ))
            return Integer.MIN_VALUE;
        return buttons.get( index ).getButtonWidth( );
    }

    public int getButtonHeight( int index ){
        if (index<0 || index>=buttons.size( ))
            return Integer.MIN_VALUE;
        return buttons.get( index ).getButtonHeight( );
    }

}
