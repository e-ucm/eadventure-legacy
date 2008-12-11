package es.eucm.eadventure.engine.core.gui.hud.contextualhud;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;

import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.engine.core.control.ActionManager;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalItem;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.core.gui.hud.HUD;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

public class ContextualHUD extends HUD {
    
    /**
     * Width of the playable area of the screen
     */
    private final int GAME_AREA_WIDTH = 800;
    
    /**
     * Height of the playable area of the screen
     */
    private final int GAME_AREA_HEIGHT = 600;
    
    /**
     * Left most point of the response text block
     */
    private static final int RESPONSE_TEXT_X = 10;
    
    /**
     * Upper most point of the upper response text block
     */
    private static final int UPPER_RESPONSE_TEXT_Y = 10;
    
    /**
     * Upper most point of the bottom response text block
     */
    private static final int BOTTOM_RESPONSE_TEXT_Y = 480;
    
    /**
     * Number of response lines to display
     */
    private static final int RESPONSE_TEXT_NUMBER_LINES = 5;
    /**
     * Offset to check if the mouse is in it when the inventory is not shown to show it
     */
    private static final int INVENTORY_OFF_OFFSET = Inventory.INVENTORY_PANEL_HEIGHT*3/4;
    /**
     * Offset to check if the mouse is not in it nor in the inventory to hide it
     */
    private static final int INVENTORY_ON_OFFSET = Inventory.INVENTORY_PANEL_HEIGHT*1/4;
    
    /**
     * Action buttons
     */
    private ActionButtons actionButtons;
    
    /**
     * Inventory
     */
    private Inventory inventory;
    /**
     * Whether the inventory is shown
     */
    private boolean showInventory;
    /**
     * Whether the action buttons are shown
     */
    private boolean showActionButtons;
    
    /**
     * Element to do the actions to
     */
    private FunctionalElement elementAction;
    /**
     * Element selected and current cursor
     */
    private FunctionalElement elementInCursor;
    
    /**
     * Cursor for use when it's over a element
     */
    private Cursor cursorOver;
    /**
     * Cursor for use when it's over an exit
     */
    private Cursor cursorExit;
    /**
     * Cursor for use when it's over am action button
     */
    private Cursor cursorAction;
    
    /**
     * Last mouse event in the hud
     */
    private MouseEvent lastMouseMoved;

    /**
     * Function that initializa the HUD class
     */
    public void init( ) {
        super.init( );
        
        actionButtons = new ActionButtons( false );
        inventory = new Inventory( );
        
        showInventory = false;
        showActionButtons = false;
        elementAction = null;
        
        lastMouseMoved = null;
        
        DescriptorData descriptor =Game.getInstance().getGameDescriptor( ); 
        
        if (descriptor.getCursorPath( DescriptorData.CURSOR_OVER )==null){
            cursorOver = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImage( "gui/cursors/over.png", MultimediaManager.IMAGE_MENU ), new Point( 5, 5 ), "cursorOver" );            
        }
        else{
            cursorOver = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImageFromZip( descriptor.getCursorPath( DescriptorData.CURSOR_OVER ), MultimediaManager.IMAGE_MENU ), new Point( 5, 5 ), "cursorOver" );            
        }
        
        if (descriptor.getCursorPath( DescriptorData.EXIT_CURSOR )==null){
            cursorExit = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImage( "gui/cursors/exit.png", MultimediaManager.IMAGE_MENU ), new Point( 5, 5 ), "cursorExit" );            
        }
        else{
            cursorExit = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImageFromZip( descriptor.getCursorPath( DescriptorData.EXIT_CURSOR ), MultimediaManager.IMAGE_MENU ), new Point( 5, 5 ), "cursorExit" );
        }
        
        if (descriptor.getCursorPath( DescriptorData.EXIT_CURSOR )==null){
            cursorAction = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImage( "gui/cursors/action.png", MultimediaManager.IMAGE_MENU ), new Point( 5, 5 ), "cursorAction" );            
        }
        else{
            cursorAction = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImageFromZip( descriptor.getCursorPath( DescriptorData.CURSOR_ACTION ), MultimediaManager.IMAGE_MENU ), new Point( 5, 5 ), "cursorAction" );
        }
        
    }
    
    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.gui.hud.HUD#getGameAreaWidth()
     */
    public int getGameAreaWidth( ) {
        return GAME_AREA_WIDTH;
    }
    
    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.gui.hud.HUD#getGameAreaHeight()
     */
    public int getGameAreaHeight( ) {
        return GAME_AREA_HEIGHT;
    }
    
    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.gui.hud.HUD#getResponseTextX()
     */
    public int getResponseTextX( ) {
        return RESPONSE_TEXT_X;
    }
    
    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.gui.hud.HUD#getResponseTextY()
     */
    public int getResponseTextY( ) {
        int responseTextY;
        FunctionalPlayer player = Game.getInstance( ).getFunctionalPlayer( );
        
        // Show the response block in the upper or bottom of the screen, depending on the player's position
        if( player.getY( ) - player.getHeight( )  > GUI.WINDOW_HEIGHT / 2 )
            responseTextY = UPPER_RESPONSE_TEXT_Y;
        else
            responseTextY = BOTTOM_RESPONSE_TEXT_Y;
        
        return responseTextY;
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.gui.hud.HUD#getResponseTextNumberLines()
     */
    public int getResponseTextNumberLines( ) {
        return RESPONSE_TEXT_NUMBER_LINES;
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.gui.hud.HUD#newActionSelected()
     */
    public void newActionSelected( ) {  }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.gui.hud.HUD#mouseMoved(java.awt.event.MouseEvent)
     */
    public boolean mouseMoved( MouseEvent e ) {
        boolean inHud = false;
               
        //Reset the action overed button
        actionButtons.mouseMoved( null );
        
        //Propagate the event to the action buttons only if are shown
        if( showActionButtons ){
            actionButtons.mouseMoved( e );
            inHud = actionButtons.getButtonOver( )!=-1;
        //else if the inventory is shown
        }else if( showInventory ){
            //If the inventory is the upper window one
            if( inventory.isUpperInventory( ) ){
                //The mouse event is in the inventory
                if( e.getY( ) < Inventory.UPPER_INVENTORY_PANEL_Y+Inventory.INVENTORY_PANEL_HEIGHT ) {
                    //propagate the event
                    inventory.mouseMoved( e );
                    inHud = true;
                //else if the mouse is not in the inventory or its offset, hide it
                }else if( e.getY( ) > Inventory.UPPER_INVENTORY_PANEL_Y+Inventory.INVENTORY_PANEL_HEIGHT + INVENTORY_ON_OFFSET ){
                    showInventory = false;
                }       
            //else the inventory is the bottom window one
            }else{
                //The mouse event is in the inventory
                if( e.getY( ) > Inventory.BOTTOM_INVENTORY_PANEL_Y ) {
                    //propagate the event
                    inventory.mouseMoved( e );
                    inHud = true;
                //else if the mouse is not in the inventory or its offset, hide it
                }else if( e.getY( ) < Inventory.BOTTOM_INVENTORY_PANEL_Y-INVENTORY_ON_OFFSET ){
                    showInventory = false;
                }
            }
        //else the inventory is not shown
        }else{
            //If the mouse is in the upper inventory window offset, show it 
            if( e.getY( ) > GUI.WINDOW_HEIGHT-INVENTORY_OFF_OFFSET ){
                inventory.setUpperInventory( false );
                showInventory = true; 
            //else if the mouse is in the lower inventory window offset, show it 
            }else if( e.getY( ) < INVENTORY_OFF_OFFSET ){
                inventory.setUpperInventory( true );
                showInventory = true;
            }
        }
        
        //update the last mouse event with this one
        lastMouseMoved = e;

        return inHud;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.gui.hud.HUD#mouseClicked(java.awt.event.MouseEvent)
     */
    public boolean mouseClicked( MouseEvent e ) {
        boolean inHud = false;
        ActionManager actionManager = game.getActionManager( );
        
        if (e.getButton() != MouseEvent.BUTTON1 && e.getButton() != MouseEvent.BUTTON3)
        	return false;
        
        boolean button = false;
        if (showActionButtons) {
        	actionButtons.mouseClicked(e);
        	if (actionButtons.getButtonPressed() != -1)
        		button = true;
        }
        
        //Right mouse button click
        if(!button && e.getButton( ) == MouseEvent.BUTTON3) {
            //Reset current element as cursor
            elementInCursor = null;
            //put the default cursor
            gui.setDefaultCursor( );
            //If the mouse is over an element
            if( actionManager.getElementOver( ) != null ){
                //Select that element to do the action on it
                elementAction = actionManager.getElementOver( );
                //Set the click coordinate as the center for the action buttons
                actionButtons.setCenterX( e.getX( ) );
                actionButtons.setCenterY( e.getY( ) );
                //Show the action  buttons
                showActionButtons = true;
                //Reset the pressed action button
                actionButtons.setButtonPressed(-1);
                inHud = true;
            //else the mouse is not over an element
            }else{
                //Reset the element to do the action on it
                elementAction = null;
                //Hide the action buttons
                showActionButtons = false;
                inHud = false;
            }
        //other mouse button click
        }else{
            //If the action buttons are shown
            if( showActionButtons ) {
                //Propagate the click
                actionButtons.mouseClicked( e );
                //If there is a action button pressed
                if( actionButtons.getButtonPressed()!=-1 ){
                    inHud = true;
                    switch(actionButtons.getButtonPressed()){
                        //Hand action button pressed
                        case ActionButtons.ACTIONBUTTON_HAND:
                            elementInCursor = null;
                            gui.setDefaultCursor( );
                            
                            // If the element can be used alone, perform the action
                            if( elementAction.canBeUsedAlone( ) ) {
                                actionManager.setActionSelected( ActionManager.ACTION_USE );
                                game.getFunctionalScene( ).performActionInElement( elementAction );
                            }
                            
                            // If not, grab the item or pick it up to use or give it
                            else {
                                if( !elementAction.isInInventory( ) ){
                                    actionManager.setActionSelected( ActionManager.ACTION_GRAB );
                                    game.getFunctionalScene( ).performActionInElement( elementAction );
                                }else{
                                    elementInCursor = elementAction;
                                    gui.setCursor( Toolkit.getDefaultToolkit( ).createCustomCursor( ((FunctionalItem)elementInCursor).getIconImage(), new Point( 5, 5 ), "elementInCursor" ) );
                                }
                            }
                            break;
                        //Eye action button pressed
                        case ActionButtons.ACTIONBUTTON_EYE:
                            actionManager.setActionSelected( ActionManager.ACTION_EXAMINE );
                            game.getFunctionalScene().performActionInElement( elementAction );
                            break;
                        //Mouth action button pressed
                        case ActionButtons.ACTIONBUTTON_MOUTH:
                            actionManager.setActionSelected( ActionManager.ACTION_TALK );
                            game.getFunctionalScene().performActionInElement( elementAction );
                            break;
                    }
                    actionManager.setActionSelected( ActionManager.ACTION_GOTO );
                }
            //else if the inventory is shown and the click is in it
            }else if( showInventory && ( e.getY( ) > Inventory.BOTTOM_INVENTORY_PANEL_Y || e.getY( ) < Inventory.UPPER_INVENTORY_PANEL_Y + Inventory.INVENTORY_PANEL_HEIGHT ) ) {
                inHud = true;
                //Get the element of the inventory where there has been the click
                FunctionalElement element = inventory.mouseClicked( e );
                //If we have an element in the cursor to be used to
                if( elementInCursor!= null ){
                    //and there is an element in the inventory where there has been the click
                    if( element != null ){
                       //put the element in the cursor as the optional element for the player
                       game.getFunctionalPlayer( ).setOptionalElement( elementInCursor );
                       //select the action use_with
                       actionManager.setActionSelected( ActionManager.ACTION_USE_WITH );
                       //perform the action in the element of the inventory (will use the optional element
                       //of the player as second element)
                       game.getFunctionalScene().performActionInElement( element );
                       //deselect the element of the cursor
                       elementInCursor = null;
                       //put the default cursor
                       gui.setDefaultCursor( );
                    }
                } 
                //else there is no element in the cursor and there is an element in the inventory where the click
                else if( element != null ) {
                    //select the look element
                    actionManager.setActionSelected( ActionManager.ACTION_LOOK );
                    //perform the action in the inventory element
                    game.getFunctionalScene( ).performActionInElement( element );
                }
            //else if the click is in an element
            }else if( actionManager.getElementOver( ) != null ){
                inHud = true;
                //If there is an element selected
                if( elementInCursor!= null ){
                    //if the cursor is over an element that can perfrom the action give_to
                    if( actionManager.getElementOver( ).canPerform( ActionManager.ACTION_GIVE_TO ) )
                        //select that action
                        actionManager.setActionSelected( ActionManager.ACTION_GIVE_TO );
                    else
                        //select the action use with
                        actionManager.setActionSelected( ActionManager.ACTION_USE_WITH );
                    //Set the optional element the element selected
                    game.getFunctionalPlayer( ).setOptionalElement( elementInCursor );
                    //perform the action on the element the mouse is over
                    game.getFunctionalScene().performActionInElement( actionManager.getElementOver( ) );
                    //deselect the element of the cursor
                    elementInCursor = null;
                    //put the default cursor
                    gui.setDefaultCursor( );
                }
                //else there is no element selected
                else{
                    //select the action look
                    actionManager.setActionSelected( ActionManager.ACTION_LOOK );
                    //perform it on the element the mouse is over
                    game.getFunctionalScene().performActionInElement( actionManager.getElementOver( ) );
                }
            }
            // Hide the action buttons
            showActionButtons = false;
            elementAction = null;  
        }
        
        return inHud;
    }

    /**
     * Draw the HUD with the action button, action and element selected
     * @param g Graphics2D where will be drawn
     */
    public void draw( Graphics2D g ) {
        
        // Get the action manager
        ActionManager actionManager = game.getActionManager( );
        
        //draw the inventory
        inventory.draw( g );
        
        //If the action buttons are shown
        if( showActionButtons )
            //draw them
            actionButtons.draw( g );
        
        //set the font and color for the text (tooltip)
        g.setFont( new Font( null, Font.BOLD, 16 ) );
        g.setColor( Color.WHITE );
        
        //If there is no element selected
        if( elementInCursor == null ) {
            //If there mouse is over an exit
            if( !actionManager.getExit( ).equals( "" ) ){
                //change the cursor for the exit one
                if (actionManager.getExitCursor( )!=null)
                    gui.setCursor( actionManager.getExitCursor( ) );
                else
                    gui.setCursor( cursorExit );
                //If there is a known mouse position to be used for the position of the text
                if( lastMouseMoved != null )
                    //draw the name of the exit into the mouse in the last mouse position
                    GUI.drawStringOnto( g, new String[]{actionManager.getExit( )}, lastMouseMoved.getX( )+16, lastMouseMoved.getY( ), Color.WHITE, Color.BLACK );
            }
            //esle if the mouse is over an element
            else if( actionManager.getElementOver( ) != null ){
                //change the cursor for the over an element one
                gui.setCursor( cursorOver );
                //If there is a known mouse position to be used for the position of the text
                if( lastMouseMoved != null )
                    //draw the name of the element into the mouse in the last mouse position
                    GUI.drawStringOnto( g, new String[]{actionManager.getElementOver( ).getElement( ).getName( )}, lastMouseMoved.getX( )+16, lastMouseMoved.getY( ), Color.WHITE, Color.BLACK );
            }
            //else (the mouse is over and action button or nothing
            else{
                //If the action buttons are shown and the mouse is over one of them
                if( showActionButtons && actionButtons.getButtonOver( ) != -1)
                    //change the cursor for the action button cursor one
                    gui.setCursor( cursorAction );
                //else, the mouse is over nothing 
                else
                    //set the default cursor
                    gui.setDefaultCursor( );
            }
        }
        //else if there is element selected and the mouse is over an element
        else if( actionManager.getElementOver( ) != null ){
            //If there is a known mouse position to be used for the position of the text
            if( lastMouseMoved != null )
                //draw the name of the element into the mouse in the last mouse position
                GUI.drawStringOnto( g, new String[]{actionManager.getElementOver( ).getElement( ).getName( )}, lastMouseMoved.getX( )+16, lastMouseMoved.getY( ), Color.WHITE, Color.BLACK );
        }
       
    }
    
    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.gui.hud.HUD#update(long)
     */
    public void update( long elapsedTime ) {
        //update the inventory
        inventory.update( elapsedTime );
        //If the hud is shown and the inventory must show
        if( showHud && showInventory ){
            //show the inventory
            inventory.setDY( inventory.getDY( ) - (double)elapsedTime / 10.0 );
        }else{
            //else hide it
            inventory.setDY( inventory.getDY( ) + (double)elapsedTime / 10.0 );
        }
        NUPDATES++;
    }
    
    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.gui.hud.HUD#toggleHud(boolean)
     */
    public void toggleHud(boolean show){
        showHud = show;
        elementInCursor = null;
    }
    
    private static int NUPDATES = 0;
}
