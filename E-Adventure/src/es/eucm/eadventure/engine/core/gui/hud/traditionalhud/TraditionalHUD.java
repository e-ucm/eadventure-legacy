package es.eucm.eadventure.engine.core.gui.hud.traditionalhud;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;

import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.engine.core.control.ActionManager;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.data.GameText;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.core.gui.hud.HUD;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * Updated feb 2008. UPDATES: Customized cursors
 * @author Javier Torrente
 *
 */
public class TraditionalHUD extends HUD {
    
    /**
     * Width of the playable area of the screen
     */
    private final int GAME_AREA_WIDTH = 800;
    
    /**
     * Height of the playable area of the screen
     */
    private final int GAME_AREA_HEIGHT = 400;
    
    /**
     * Width of the HUD
     */
    private static final int HUD_WIDTH = 800;

    /**
     * Height of the HUD
     */
    private static final int HUD_HEIGHT = 200;

    /**
     * Left most point of the HUD
     */
    private static final int HUD_X = 0;

    /**
     * Upper most point of the HUD
     */
    private static final int HUD_Y = 400;
    
    /**
     * Left most point of the response text block
     */
    private static final int RESPONSE_TEXT_X = HUD_X + 10;
    
    /**
     * Upper most point of the response text block
     */
    private static final int RESPONSE_TEXT_Y = HUD_Y + 5;
    
    /**
     * Number of response lines to display
     */
    private static final int RESPONSE_TEXT_NUMBER_LINES = 9;
    
    /**
     * Left most point of the panel that show the current action and element selected
     */
    public static final int ACTIONTEXT_PANEL_X = HUD_X;

    /**
     * Upper most point of the panel that show the current action and element selected
     */
    public static final int ACTIONTEXT_PANEL_Y = HUD_Y;

    /**
     * Width of the panel that show the current action and element selected
     */
    private static final int ACTIONTEXT_PANEL_WIDTH = 800;

    /**
     * Height of the panel that show the current action and element selected
     */
    private static final int ACTIONTEXT_PANEL_HEIGHT = 35;

    /**
     * Left most point of the panel that contains the action buttons
     */
    private static final int ACTIONBUTTONS_PANEL_X = HUD_X;

    /**
     * Upper most point of the panel that contains the action buttons
     */
    private static final int ACTIONBUTTONS_PANEL_Y = HUD_Y + ACTIONTEXT_PANEL_HEIGHT;

    /**
     * Width of the panel that contains the action buttons
     */
    private static final int ACTIONBUTTONS_PANEL_WIDTH = 339;

    /**
     * Height of the panel that contains the action buttons
     */
    private static final int ACTIONBUTTONS_PANEL_HEIGHT = 165;

    /**
     * Left most point of the first action button
     */
    public static final int FIRST_ACTIONBUTTON_X = ACTIONBUTTONS_PANEL_X + 6;

    /**
     * Upper most point of the first action button
     */
    public static final int FIRST_ACTIONBUTTON_Y = ACTIONBUTTONS_PANEL_Y + 7;

    /**
     * Width of an action button
     */
    public static final int ACTIONBUTTON_WIDTH = 109;

    /**
     * Height of an action button
     */
    public static final int ACTIONBUTTON_HEIGHT = 76;

    /**
     * Width of the spacing between action buttons
     */
    public static final int ACTIONBUTTON_SPACING_X = ACTIONBUTTON_WIDTH;

    /**
     * Height of the spacing between action buttons
     */
    public static final int ACTIONBUTTON_SPACING_Y = ACTIONBUTTON_HEIGHT;

    /**
     * Left most point of the panel that contains the inventory
     */
    private static final int INVENTORY_PANEL_X = HUD_X + ACTIONBUTTONS_PANEL_WIDTH;

    /**
     * Upper most point of the panel that contains the inventory
     */
    private static final int INVENTORY_PANEL_Y = HUD_Y + ACTIONTEXT_PANEL_HEIGHT;

    /**
     * Width of the panel that contains the inventory
     */
    private static final int INVENTORY_PANEL_WIDTH = 461;

    /**
     * Height of the panel that contains the inventory
     */
    private static final int INVENTORY_PANEL_HEIGHT = 165;

    /**
     * Number of inventory lines in the inventory panel
     */
    public static final int INVENTORY_LINES = 3;

    /**
     * Number of inventory items in each line of the inventory panel
     */
    public static final int INVENTORY_ITEMS_PER_LINE = 5;
    
    /**
     * Witdh of the scroll buttons
     */
    public static final int SCROLL_WIDTH = 40;
    
    /**
     * Height of the scroll buttons
     */
    public static final int SCROLL_HEIGHT = 72;
    
    /**
     * Top left position of the scroll up button
     */
    public static final int SCROLL_UP_X = INVENTORY_PANEL_X + 11;
    
    /**
     * Top left position of the scroll up button 
     */
    public static final int SCROLL_UP_Y = INVENTORY_PANEL_Y + 11;
    
    /**
     * Top left position of the scroll down button
     */
    public static final int SCROLL_DOWN_X = INVENTORY_PANEL_X + 11;
    
    /**
     * Top left position of the scroll down button
     */
    public static final int SCROLL_DOWN_Y = SCROLL_UP_Y + SCROLL_HEIGHT;

    /**
     * Left most point of the panel that contains the inventory items
     */
    public static final int ITEMS_PANEL_X = INVENTORY_PANEL_X + 51;

    /**
     * Upper most point of the panel that contains the inventory items
     */
    public static final int ITEMS_PANEL_Y = INVENTORY_PANEL_Y + 11;

    /**
     * Width of the panel that contains the inventory items
     */
    public static final int ITEMS_PANEL_WIDTH = 400;

    /**
     * Height of the panel that contains the inventory items
     */
    public static final int ITEMS_PANEL_HEIGHT = 144;

    /**
     * Left most point of the first inventory item
     */
    public static final int FIRST_ITEM_X = ITEMS_PANEL_X;

    /**
     * Upper most point of the first inventory item
     */
    public static final int FIRST_ITEM_Y = ITEMS_PANEL_Y;

    /**
     * Width of an inventory item
     */
    public static final int ITEM_WIDTH = 80;

    /**
     * Height of an inventory item
     */
    public static final int ITEM_HEIGHT = 48;

    /**
     * Width of the spacing between inventory items
     */
    public static final int ITEM_SPACING_X = ITEM_WIDTH;

    /**
     * Height of the spacing between inventory items
     */
    public static final int ITEM_SPACING_Y = ITEM_HEIGHT;
    
    /**
     * The ActionButtons element
     */
    private ActionButtons actionButtons;
    
    /**
     * The Inventory element
     */
    private Inventory inventory;

    /**
     * The background for the HUD
     */
    private Image background;
    
    /**
     * Boolean to control if the mouse is on the inventory panel
     */
    private boolean mouseInInventory;
    
    /**
     * The different cursor for each action
     */
    private Cursor[] actionCursors;

    /**
     * Function that initializa the HUD class
     */
    public void init( ) {
        super.init( );
        actionCursors = new Cursor[7];

        background = MultimediaManager.getInstance( ).loadImage( "gui/hud/traditional/HUD.png", MultimediaManager.IMAGE_MENU );
        
        DescriptorData descriptor =Game.getInstance().getGameDescriptor( ); 
        
        if (descriptor.getCursorPath( DescriptorData.USE_CURSOR )==null){
            actionCursors[0] = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImage( "gui/cursors/use.png", MultimediaManager.IMAGE_MENU ), new Point( 0, 0 ), "useCursor" );            
        }
        else{
            actionCursors[0] = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImageFromZip( descriptor.getCursorPath( DescriptorData.USE_CURSOR ), MultimediaManager.IMAGE_MENU ), new Point( 0, 0 ), "useCursor" );            
        }
        
        if (descriptor.getCursorPath( DescriptorData.LOOK_CURSOR )==null){
            actionCursors[1] = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImage( "gui/cursors/look.png", MultimediaManager.IMAGE_MENU ), new Point( 0, 0 ), "lookCursor" );            
        }
        else{
            actionCursors[1] = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImageFromZip( descriptor.getCursorPath( DescriptorData.LOOK_CURSOR ), MultimediaManager.IMAGE_MENU ), new Point( 0, 0 ), "lookCursor" );            
        }
        
        if (descriptor.getCursorPath( DescriptorData.EXAMINE_CURSOR )==null){
            actionCursors[2] = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImage( "gui/cursors/examine.png", MultimediaManager.IMAGE_MENU ), new Point( 0, 0 ), "examineCursor" );            
        }
        else{
            actionCursors[2] = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImageFromZip( descriptor.getCursorPath( DescriptorData.EXAMINE_CURSOR ), MultimediaManager.IMAGE_MENU ), new Point( 0, 0 ), "examineCursor" );            
        }
        
        if (descriptor.getCursorPath( DescriptorData.TALK_CURSOR )==null){
            actionCursors[3] = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImage( "gui/cursors/talk.png", MultimediaManager.IMAGE_MENU ), new Point( 0, 0 ), "talkCursor" );                        
        }
        else{
            actionCursors[3] = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImageFromZip( descriptor.getCursorPath( DescriptorData.TALK_CURSOR ), MultimediaManager.IMAGE_MENU ), new Point( 0, 0 ), "talkCursor" );            
        }
        
        if (descriptor.getCursorPath( DescriptorData.GRAB_CURSOR )==null){
            actionCursors[4] = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImage( "gui/cursors/grab.png", MultimediaManager.IMAGE_MENU ), new Point( 0, 0 ), "grabCursor" );
        }
        else{
            actionCursors[4] = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImageFromZip( descriptor.getCursorPath( DescriptorData.GRAB_CURSOR ), MultimediaManager.IMAGE_MENU ), new Point( 0, 0 ), "grabCursor" );            
        }
        
        if (descriptor.getCursorPath( DescriptorData.GIVE_CURSOR )==null){
            actionCursors[5] = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImage( "gui/cursors/give.png", MultimediaManager.IMAGE_MENU ), new Point( 0, 0 ), "giveCursor" );
        }
        else{
            actionCursors[5] = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImageFromZip( descriptor.getCursorPath( DescriptorData.GIVE_CURSOR ), MultimediaManager.IMAGE_MENU ), new Point( 0, 0 ), "giveCursor" );            
        }
        
        if (descriptor.getCursorPath( DescriptorData.EXIT_CURSOR )==null){
            actionCursors[6] = null;
        }
        else{
            actionCursors[6] = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImageFromZip( descriptor.getCursorPath( DescriptorData.EXIT_CURSOR ), MultimediaManager.IMAGE_MENU ), new Point( 0, 0 ), "exitCursor" );            
        }
        
        actionButtons = new ActionButtons( false );
        inventory = new Inventory( );
        
        mouseInInventory = false;
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
        return RESPONSE_TEXT_Y; 
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
    public void newActionSelected( ) {
        switch( game.getActionManager( ).getActionSelected( ) ) {
            case ActionManager.ACTION_GOTO:
                gui.setDefaultCursor( );
                actionButtons.setButtonPressed( -1 );
                break;
            case ActionManager.ACTION_USE:
                gui.setCursor( actionCursors[0] );
                actionButtons.setButtonPressed( ActionManager.ACTION_USE );
                break;
            case ActionManager.ACTION_LOOK:
                gui.setCursor( actionCursors[1] );
                actionButtons.setButtonPressed( ActionManager.ACTION_LOOK );
                break;
            case ActionManager.ACTION_EXAMINE:
                gui.setCursor( actionCursors[2] );
                actionButtons.setButtonPressed( ActionManager.ACTION_EXAMINE );
                break;
            case ActionManager.ACTION_TALK:
                gui.setCursor( actionCursors[3] );
                actionButtons.setButtonPressed( ActionManager.ACTION_TALK );
                break;
            case ActionManager.ACTION_GRAB:
                gui.setCursor( actionCursors[4] );
                actionButtons.setButtonPressed( ActionManager.ACTION_GRAB );
                break;
            case ActionManager.ACTION_GIVE:
                gui.setCursor( actionCursors[5] );
                actionButtons.setButtonPressed( ActionManager.ACTION_GIVE );
                break;
        }
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.gui.hud.HUD#mouseMoved(java.awt.event.MouseEvent)
     */
    public boolean mouseMoved( MouseEvent e ) {
        boolean res = false;
        ActionManager actionManager = game.getActionManager( );
        
        actionButtons.mouseMoved( null );
        // The cursor is in the hud
        if( e.getX( ) > HUD_X && e.getX( )<HUD_X+HUD_WIDTH && e.getY( ) > HUD_Y && e.getY( )<HUD_Y+HUD_HEIGHT ) {
            res = true;
            
            // The cursor is in the actionButtons
            if( e.getX( ) > ACTIONBUTTONS_PANEL_X && e.getX( )<ACTIONBUTTONS_PANEL_X+ACTIONBUTTONS_PANEL_WIDTH && e.getY( ) > ACTIONBUTTONS_PANEL_Y && e.getY( )<ACTIONBUTTONS_PANEL_Y+ACTIONBUTTONS_PANEL_HEIGHT ) {
                if( mouseInInventory ) {
                    if( actionManager.getActionSelected( ) == ActionManager.ACTION_LOOK ) {
                        actionManager.setActionSelected( ActionManager.ACTION_GOTO );
                    }
                    mouseInInventory = false;
                }
                    actionButtons.mouseMoved( e );
            }
            // The cursor is in the  inventory
            else if( e.getX( ) > INVENTORY_PANEL_X && e.getX( )<INVENTORY_PANEL_X+INVENTORY_PANEL_WIDTH && e.getY( ) > INVENTORY_PANEL_Y && e.getY( )<INVENTORY_PANEL_Y+INVENTORY_PANEL_HEIGHT ) {
                if( e.getX( ) > ITEMS_PANEL_X && e.getX( ) < ITEMS_PANEL_X + ITEMS_PANEL_WIDTH && e.getY( ) > ITEMS_PANEL_Y && e.getY( ) < ITEMS_PANEL_Y + ITEMS_PANEL_HEIGHT ) {
                    if( actionManager.getActionSelected( ) == ActionManager.ACTION_GOTO ) {
                        actionManager.setActionSelected( ActionManager.ACTION_LOOK );
                    }
                }else {
                    if( actionManager.getActionSelected( ) == ActionManager.ACTION_LOOK ) {
                        actionManager.setActionSelected( ActionManager.ACTION_GOTO );
                    }
                }
                if( !mouseInInventory )
                    mouseInInventory = true;
                inventory.mouseMoved( e );
            }else{
                if( mouseInInventory ) {
                    if( actionManager.getActionSelected( ) == ActionManager.ACTION_LOOK ) {
                        actionManager.setActionSelected( ActionManager.ACTION_GOTO );
                    }
                    mouseInInventory = false;
                }
            }
        }else
            res = false;
        
        return res;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.gui.hud.HUD#mouseClicked(java.awt.event.MouseEvent)
     */
    public boolean mouseClicked( MouseEvent e ) {
        boolean res = false;
        ActionManager actionManager = game.getActionManager( );
        
        // The click is with the right mouse button
        if( e.getButton( ) == MouseEvent.BUTTON3 ) {
            // The click is in the hud
            if( e.getX( ) > HUD_X && e.getX( )<HUD_X+HUD_WIDTH && e.getY( ) > HUD_Y && e.getY( )<HUD_Y+HUD_HEIGHT ) {
                if( e.getX( ) > INVENTORY_PANEL_X && e.getX( )<INVENTORY_PANEL_X+INVENTORY_PANEL_WIDTH && e.getY( ) > INVENTORY_PANEL_Y && e.getY( )<INVENTORY_PANEL_Y+INVENTORY_PANEL_HEIGHT ){
                    if( e.getX( )> FIRST_ITEM_X && e.getY( ) > FIRST_ITEM_Y){
                        actionManager.setActionSelected( ActionManager.ACTION_LOOK );
                    }
                    mouseInInventory = true;
                }
                res = true;
            }
            //The click is in the scene
            else{
            	// TODO it might be needed to clear the actionPool of the player
                //game.getFunctionalPlayer( ).setState( FunctionalPlayer.IDLE );
                actionManager.setActionSelected( ActionManager.ACTION_GOTO );
                res = false;
            }
        }
        
        //The click is with other button
        else {
            //The click is in the hud
            if( e.getX( ) > HUD_X && e.getX( )<HUD_X+HUD_WIDTH && e.getY( ) > HUD_Y && e.getY( )<HUD_Y+HUD_HEIGHT ) {
                actionButtons.mouseClicked( null );
                //The click is in the actionButtons
                if( e.getX( ) > ACTIONBUTTONS_PANEL_X && e.getX( ) < ACTIONBUTTONS_PANEL_X + ACTIONBUTTONS_PANEL_WIDTH && e.getY( ) > ACTIONBUTTONS_PANEL_Y && e.getY( ) < ACTIONBUTTONS_PANEL_Y + ACTIONBUTTONS_PANEL_HEIGHT ){
                    actionButtons.mouseClicked( e );
                    if( actionButtons.getActionPressed( ) != ActionButtons.ACTION_NOACTION ) {
                        actionManager.setActionSelected( actionButtons.getActionPressed( ) );
                    }
                }
                //The click is in the inventory
                else  if( e.getX( ) > INVENTORY_PANEL_X && e.getX( ) < INVENTORY_PANEL_X + INVENTORY_PANEL_WIDTH && e.getY( ) > INVENTORY_PANEL_Y && e.getY( ) < INVENTORY_PANEL_Y + INVENTORY_PANEL_HEIGHT ) {
                    inventory.mouseClicked( e );
                    
                    // If the action is not "Use with" or "Give to", set the "Look" action
                    if( actionManager.getActionSelected( ) != ActionManager.ACTION_USE_WITH && actionManager.getActionSelected( ) != ActionManager.ACTION_GIVE_TO ) {
                        if( e.getX( ) > ITEMS_PANEL_X && e.getX( ) < ITEMS_PANEL_X + ITEMS_PANEL_WIDTH && e.getY( ) > ITEMS_PANEL_Y && e.getY( ) < ITEMS_PANEL_Y + ITEMS_PANEL_HEIGHT ) {
                            actionManager.setActionSelected( ActionManager.ACTION_LOOK );
                        }
                    }
                }
                
                res = true;
            }
            //The click is in the scene
            else
                res = false;
        }
        
        return res;
    }

    /**
     * Draw the HUD with the action button, action and element selected
     * 
     * NOTE: Important changes where made so that it works with the new "FunctionlAction" classes, it is possible for
     * some more errors to appear
     *  
     * @param g Graphics2D where will be drawn
     */
    public void draw( Graphics2D g ) {
        
        g.clearRect( HUD_X, HUD_Y, HUD_WIDTH, HUD_HEIGHT);
                
        // Get the action manager
        ActionManager actionManager = game.getActionManager( );
        
        g.setFont( new Font( null, Font.BOLD, 16 ) );

        String textAction = "";
        switch( actionManager.getActionSelected( ) ){
            case ActionManager.ACTION_GOTO:
                textAction = GameText.TEXT_GO;
                if( actionManager.getElementOver( ) != null )
                    textAction += " " + GameText.TEXT_TO + " " + actionManager.getElementOver( ).getElement( ).getName( );
                else
                    if( !actionManager.getExit( ).equals( "" ) ){
                        textAction += " " + GameText.TEXT_TO + " " + actionManager.getExit( );
                        if (actionManager.getExitCursor( )!=null)
                            GUI.getInstance( ).setCursor( actionManager.getExitCursor( ) );
                        else if (actionCursors[6]!=null)
                            GUI.getInstance( ).setCursor( this.actionCursors[6] );
                        else
                            GUI.getInstance( ).setDefaultCursor( );
                    }
                    else{
                        GUI.getInstance( ).setDefaultCursor( );
                    }
                break;
            case ActionManager.ACTION_EXAMINE:
                textAction = GameText.TEXT_EXAMINE;
                //if( game.getFunctionalPlayer( ).getFinalElement( ) != null )
                //   textAction += " " + game.getFunctionalPlayer( ).getFinalElement( ).getElement( ).getName( );
                //else
                    if( actionManager.getElementOver( ) != null )
                        textAction += " " + actionManager.getElementOver( ).getElement( ).getName( );
                break;
            case ActionManager.ACTION_GRAB:
                textAction = GameText.TEXT_GRAB;
                //if( game.getFunctionalPlayer( ).getFinalElement( ) != null )
                //   textAction += " " + game.getFunctionalPlayer( ).getFinalElement( ).getElement( ).getName( );
                //else
                    if( actionManager.getElementOver( ) != null )
                        textAction += " " + actionManager.getElementOver( ).getElement( ).getName( );
                break;
            case ActionManager.ACTION_TALK:
                textAction = GameText.TEXT_TALK;
                //if( game.getFunctionalPlayer( ).getFinalElement( ) != null )
                //   textAction += " " + GameText.TEXT_TO + " " + game.getFunctionalPlayer( ).getFinalElement( ).getElement( ).getName( );
                //else
                    if( actionManager.getElementOver( ) != null )
                        textAction += " " + GameText.TEXT_TO + " " + actionManager.getElementOver( ).getElement( ).getName( );
                break;
            case ActionManager.ACTION_GIVE:
                textAction = GameText.TEXT_GIVE;
                //if( game.getFunctionalPlayer( ).getOptionalElement( ) != null )
                //   textAction += " " + game.getFunctionalPlayer( ).getOptionalElement( ).getElement( ).getName( );
                //else
                    if( actionManager.getElementOver( ) != null )
                        textAction += " " + actionManager.getElementOver( ).getElement( ).getName( );
                break;
            case ActionManager.ACTION_GIVE_TO:
                textAction = GameText.TEXT_GIVE;
/*                if( game.getFunctionalPlayer( ).getOptionalElement( ) != null ){
                    textAction += " " + game.getFunctionalPlayer( ).getOptionalElement( ).getElement( ).getName( );
                    if( game.getFunctionalPlayer( ).getFinalElement( ) != null )
                       textAction += " " + GameText.TEXT_TO + " " + game.getFunctionalPlayer( ).getFinalElement( ).getElement( ).getName( );
                    else
                        if( actionManager.getElementOver( ) != null )
                            textAction += " " + GameText.TEXT_TO + " " + actionManager.getElementOver( ).getElement( ).getName( );
                }
                
*/ 
                if (game.getFunctionalPlayer().getCurrentAction().getAnotherElement() != null) {
				    textAction += " " + game.getFunctionalPlayer().getCurrentAction().getAnotherElement().getElement().getName( );
			        if( actionManager.getElementOver( ) != null )
			            textAction += " " + GameText.TEXT_TO + " " + actionManager.getElementOver( ).getElement( ).getName( );	
				}
                break;
            case ActionManager.ACTION_USE:
                textAction = GameText.TEXT_USE;
                //if( game.getFunctionalPlayer( ).getOptionalElement( ) != null )
                //   textAction += " " + game.getFunctionalPlayer( ).getOptionalElement( ).getElement( ).getName( );
                //else
                    if( actionManager.getElementOver( ) != null )
                        textAction += " " + actionManager.getElementOver( ).getElement( ).getName( );
                //break;
            case ActionManager.ACTION_USE_WITH:
                textAction = GameText.TEXT_USE;
/*                if( game.getFunctionalPlayer( ).getOptionalElement( ) != null ){
                    textAction += " " + game.getFunctionalPlayer( ).getOptionalElement( ).getElement( ).getName( );
                    if( game.getFunctionalPlayer( ).getFinalElement( ) != null )
                       textAction += " " + GameText.TEXT_WITH + " " + game.getFunctionalPlayer( ).getFinalElement( ).getElement( ).getName( );
                    else
                        if( actionManager.getElementOver( ) != null )
                            textAction += " " + GameText.TEXT_WITH + " " + actionManager.getElementOver( ).getElement( ).getName( );
                }
 */
                if (game.getFunctionalPlayer().getCurrentAction().getAnotherElement() != null) {
				    textAction += " " + game.getFunctionalPlayer().getCurrentAction().getAnotherElement().getElement().getName( );
			        if( actionManager.getElementOver( ) != null )
			            textAction += " " + GameText.TEXT_WITH + " " + actionManager.getElementOver( ).getElement( ).getName( );	
				}
                break;
            case ActionManager.ACTION_LOOK:
                textAction = GameText.TEXT_LOOK;
                //if( game.getFunctionalPlayer( ).getFinalElement( ) != null )
                //   textAction += " " + GameText.TEXT_AT + " " + game.getFunctionalPlayer( ).getFinalElement( ).getElement( ).getName( );
                //else
                    if( actionManager.getElementOver( ) != null )
                        textAction += " " + GameText.TEXT_AT + " " + actionManager.getElementOver( ).getElement( ).getName( );
                break;
        }
        
        g.setColor( Color.WHITE );
        GUI.drawString( g, textAction, ACTIONTEXT_PANEL_WIDTH / 2, ACTIONTEXT_PANEL_Y + ACTIONTEXT_PANEL_HEIGHT/2 );
        
        g.drawImage( background, ACTIONBUTTONS_PANEL_X, ACTIONBUTTONS_PANEL_Y, null );
        
        actionButtons.draw( g );
    
        inventory.draw( g );
    }
    
    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.gui.hud.HUD#update(long)
     */
    public void update( long elapsedTime ) {
        
    }

	@Override
	public boolean mousePressed(MouseEvent e) {
		return false;
	}

	@Override
	public boolean mouseReleased(MouseEvent e) {
		return false;
	}
}
