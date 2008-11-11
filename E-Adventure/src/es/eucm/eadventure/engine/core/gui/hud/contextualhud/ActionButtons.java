package es.eucm.eadventure.engine.core.gui.hud.contextualhud;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * This class contains all the graphical information about the action buttons
 */
public class ActionButtons {

    /**
     * Width of an action button
     */
    public static final int ACTIONBUTTON_WIDTH = 40;

    /**
     * Height of an action button
     */
    public static final int ACTIONBUTTON_HEIGHT = 40;

    /**
     * Hand index action button
     */
    public static final int ACTIONBUTTON_HAND = 0;

    /**
     * Eye index action button
     */
    public static final int ACTIONBUTTON_EYE = 1;

    /**
     * Mouth index action button
     */
    public static final int ACTIONBUTTON_MOUTH = 2;

    /**
     * Number of action buttons
     */
    private static final int ACTIONBUTTON_COUNT = 3;

    /**
     * X coordinates of the action buttons
     */
    private static final int[] ACTIONBUTTON_X = { 50, -50, 0 };

    /**
     * Y coordinate of the action buttons
     */
    private static final int[] ACTIONBUTTON_Y = { 0, 0, 0 };

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
    private int index_button_over;

    /**
     * Index of the pressed action button
     */
    private int index_button_pressed;

    /**
     * Normal images of the action buttons
     */
    private Image[] button_normal;

    /**
     * Overed images of the action buttons
     */
    private Image[] button_over;

    /**
     * Pressed images of the action buttons
     */
    private Image[] button_pressed;

    /**
     * Constructor of the class.
     * Requires that the MultimediaManager class is loaded.
     * @param customized True if the graphics of the HUD are customized, false otherwise
     */
    public ActionButtons( boolean customized ) {

        //No action button is overed or pressed
        index_button_over = -1;
        index_button_pressed = -1;

        button_normal = new Image[ ACTIONBUTTON_COUNT ];
        //Load the customized normal images of the action buttons
        if( customized ) {
            button_normal[ACTIONBUTTON_HAND] = MultimediaManager.getInstance( ).loadImageFromZip( "gui/hud/contextual/btnHand.png", MultimediaManager.IMAGE_MENU );
            button_normal[ACTIONBUTTON_EYE] = MultimediaManager.getInstance( ).loadImageFromZip( "gui/hud/contextual/btnEye.png", MultimediaManager.IMAGE_MENU );
            button_normal[ACTIONBUTTON_MOUTH] = MultimediaManager.getInstance( ).loadImageFromZip( "gui/hud/contextual/btnMouth.png", MultimediaManager.IMAGE_MENU );
            // Load the default normal images of the action buttons
        } else {
            button_normal[ACTIONBUTTON_HAND] = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/btnHand.png", MultimediaManager.IMAGE_MENU );
            button_normal[ACTIONBUTTON_EYE] = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/btnEye.png", MultimediaManager.IMAGE_MENU );
            button_normal[ACTIONBUTTON_MOUTH] = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/btnMouth.png", MultimediaManager.IMAGE_MENU );
        }

        button_over = new Image[ ACTIONBUTTON_COUNT ];
        //Load the customized overed images of the action buttons
        if( customized ) {
            button_over[ACTIONBUTTON_HAND] = MultimediaManager.getInstance( ).loadImageFromZip( "gui/hud/contextual/btnHandHighlighted.png", MultimediaManager.IMAGE_MENU );
            button_over[ACTIONBUTTON_EYE] = MultimediaManager.getInstance( ).loadImageFromZip( "gui/hud/contextual/btnEyeHighlighted.png", MultimediaManager.IMAGE_MENU );
            button_over[ACTIONBUTTON_MOUTH] = MultimediaManager.getInstance( ).loadImageFromZip( "gui/hud/contextual/btnMouthHighlighted.png", MultimediaManager.IMAGE_MENU );
            //Load the default overed images of the action buttons
        } else {
            button_over[ACTIONBUTTON_HAND] = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/btnHandHighlighted.png", MultimediaManager.IMAGE_MENU );
            button_over[ACTIONBUTTON_EYE] = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/btnEyeHighlighted.png", MultimediaManager.IMAGE_MENU );
            button_over[ACTIONBUTTON_MOUTH] = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/btnMouthHighlighted.png", MultimediaManager.IMAGE_MENU );
        }

        button_pressed = new Image[ ACTIONBUTTON_COUNT ];
        //Load the customized pressed images of the action buttons
        if( customized ) {
            button_pressed[ACTIONBUTTON_HAND] = MultimediaManager.getInstance( ).loadImageFromZip( "gui/hud/contextual/btnHandPressed.png", MultimediaManager.IMAGE_MENU );
            button_pressed[ACTIONBUTTON_EYE] = MultimediaManager.getInstance( ).loadImageFromZip( "gui/hud/contextual/btnEyePressed.png", MultimediaManager.IMAGE_MENU );
            button_pressed[ACTIONBUTTON_MOUTH] = MultimediaManager.getInstance( ).loadImageFromZip( "gui/hud/contextual/btnMouthPressed.png", MultimediaManager.IMAGE_MENU );
            // Load the default pressed images of the action buttons
        } else {
            button_pressed[ACTIONBUTTON_HAND] = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/btnHandPressed.png", MultimediaManager.IMAGE_MENU );
            button_pressed[ACTIONBUTTON_EYE] = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/btnEyePressed.png", MultimediaManager.IMAGE_MENU );
            button_pressed[ACTIONBUTTON_MOUTH] = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/btnMouthPressed.png", MultimediaManager.IMAGE_MENU );
        }
    }

    /**
     * There has been a mouse moved over the action buttons
     * @param x int coordinate
     * @param y int coordinate
     */
    public void mouseMoved( MouseEvent e ) {
        index_button_over = -1;
        //If it's not a NULL event check if it's in a button
        if( e != null )
            index_button_over = inWhatButton( e.getX( ), e.getY( ) );
    }

    /**
     * There has been a click in the action buttons
     * @param x int coordinate
     * @param y int coordinate
     */
    public void mouseClicked( MouseEvent e ) {
        index_button_pressed = -1;
        //If it's not a NULL event check if it's in a button
        if( e != null )
            index_button_pressed = inWhatButton( e.getX( ), e.getY( ) );
    }

    /**
     * Draw the action buttons given a Graphics2D
     * @param g Graphics2D to be used
     */
    public void draw( Graphics2D g ) {
        //For each action button
        for( int i = 0; i < ACTIONBUTTON_COUNT; i++ ) {
            //If this action button is the pressed one draw its pressed image
            if( index_button_pressed == i ) {
                g.drawImage( button_pressed[i], centerX + ACTIONBUTTON_X[i] - ACTIONBUTTON_WIDTH / 2 - Game.getInstance( ).getFunctionalScene( ).getOffsetX( ), centerY + ACTIONBUTTON_Y[i] - ACTIONBUTTON_HEIGHT / 2, null );
                //else if this action button is the overed one draw its overed image
            } else if( index_button_over == i ) {
                g.drawImage( button_over[i], centerX + ACTIONBUTTON_X[i] - ACTIONBUTTON_WIDTH / 2 - Game.getInstance( ).getFunctionalScene( ).getOffsetX( ), centerY + ACTIONBUTTON_Y[i] - ACTIONBUTTON_HEIGHT / 2, null );
                //else that the normal action button image 
            } else {
                g.drawImage( button_normal[i], centerX + ACTIONBUTTON_X[i] - ACTIONBUTTON_WIDTH / 2 - Game.getInstance( ).getFunctionalScene( ).getOffsetX( ), centerY + ACTIONBUTTON_Y[i] - ACTIONBUTTON_HEIGHT / 2, null );
            }
        }
    }

    /**
     * Forces a button to be pressed
     * @param button int corresponding the button to be pressed
     */
    public void setButtonPressed( int button ) {
        index_button_pressed = button;
    }

    /**
     * Get the current button pressed
     * @return button pressed
     */
    public int getButtonPressed( ) {
        return index_button_pressed;
    }

    /**
     * Get the current button pressed
     * @return button pressed
     */
    public int getButtonOver( ) {
        return index_button_over;
    }

    /**
     * Set the X center coordinate of the action buttons and modify it to get all 
     * the action buttons in the window
     * @param positionX Center X coordinate
     */
    public void setCenterX( int positionX ) {
        //If the buttons get out of the window from the right, correct the position
        if( positionX + 2 * ACTIONBUTTON_WIDTH > GUI.WINDOW_WIDTH )
            this.centerX = GUI.WINDOW_WIDTH - 2 * ACTIONBUTTON_WIDTH;
        //else if the buttons get out of the window from the left, correct the position
        else if( positionX - 2 * ACTIONBUTTON_WIDTH < 0 )
            this.centerX = 2 * ACTIONBUTTON_WIDTH;
        //else use that position
        else
            this.centerX = positionX;

        //modify the center given the current offset of the scene
        this.centerX += Game.getInstance( ).getFunctionalScene( ).getOffsetX( );
    }

    /**
     * Set the Y center coordinate of the action buttons and modify it to get all 
     * the action buttons in the window
     * @param positionX Center Y coordinate
     */
    public void setCenterY( int positionY ) {
        //If the buttons get out of the window from the bottom, correct the position
        if( positionY + 2 * ACTIONBUTTON_HEIGHT > GUI.WINDOW_HEIGHT )
            this.centerY = GUI.WINDOW_HEIGHT - 2 * ACTIONBUTTON_HEIGHT;
        //else if the buttons get out of the window from the top, correct the position
        else if( positionY - 2 * ACTIONBUTTON_HEIGHT < 0 )
            this.centerY = 2 * ACTIONBUTTON_HEIGHT;
        //else use that position
        else
            this.centerY = positionY;
    }

    /**
     * Given a coordinate get the action button that has it in it
     * @param x Coodinate X
     * @param y Coordinate Y
     * @return The button that has the coordinate in it or -1 if no button has it
     */
    private int inWhatButton( int x, int y ) {
        int button = -1;
        int i = 0;
        //Loop through each button while noone has the coordinate
        while( i < ACTIONBUTTON_COUNT && button == -1 ) {
            //Check if the coordinate is in the actual action button
            //In the X coordinate take into account the current scene offset
            if( x > ( centerX + ACTIONBUTTON_X[i] - ACTIONBUTTON_WIDTH / 2 - Game.getInstance( ).getFunctionalScene( ).getOffsetX( ) ) 
                    && x < ( centerX + ACTIONBUTTON_X[i] + ACTIONBUTTON_WIDTH / 2 - Game.getInstance( ).getFunctionalScene( ).getOffsetX( ) ) 
                    && y > ( centerY + ACTIONBUTTON_Y[i] - ACTIONBUTTON_HEIGHT / 2 ) 
                    && y < ( centerY + ACTIONBUTTON_Y[i] + ACTIONBUTTON_HEIGHT / 2 ) )
                button = i;
            i++;
        }
        return button;
    }

}
