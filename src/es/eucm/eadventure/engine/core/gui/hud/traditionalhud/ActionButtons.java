/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 * research group.
 * 
 * Copyright 2005-2010 <e-UCM> research group.
 * 
 * You can access a list of all the contributors to <e-Adventure> at:
 * http://e-adventure.e-ucm.es/contributors
 * 
 * <e-UCM> is a research group of the Department of Software Engineering and
 * Artificial Intelligence at the Complutense University of Madrid (School of
 * Computer Science).
 * 
 * C Profesor Jose Garcia Santesmases sn, 28040 Madrid (Madrid), Spain.
 * 
 * For more info please visit: <http://e-adventure.e-ucm.es> or
 * <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 * <e-Adventure> is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with <e-Adventure>. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.engine.core.gui.hud.traditionalhud;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.engine.core.control.ActionManager;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.config.ConfigData;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * This class contains all the graphical information about the action buttons
 */
public class ActionButtons {

    /**
     * Button the mouse is over
     */
    private int index_button_over;

    /**
     * Button being pressed
     */
    private int index_button_pressed;

    /**
     * Array of the button images
     */
    private Image[] button_normal;

    /**
     * Array of the button images when the mouse is over
     */
    private Image[] button_over;

    /**
     * Array of the button images when they are pressed
     */
    private Image[] button_pressed;

    /**
     * Array of the action of each button
     */
    private int[] button_acction;

    /**
     * Default action
     */
    public static final int ACTION_NOACTION = -1;

    /**
     * Number of action/buttons
     */
    private static final int ACTION_COUNT = 6;

    /**
     * Action look
     */
    private static final int ACTION_LOOK = 0;

    /**
     * Action grab
     */
    private static final int ACTION_GRAB = 1;

    /**
     * Action talk
     */
    private static final int ACTION_TALK = 2;

    /**
     * Action examine
     */
    private static final int ACTION_EXAMINE = 3;

    /**
     * Action use
     */
    private static final int ACTION_USE = 4;

    /**
     * Action give
     */
    private static final int ACTION_GIVE = 5;

    /**
     * Constructor of the class. Requires that the MultimediaManager class is
     * loaded.
     * 
     * @param customized
     *            True if the graphics of the HUD are customized, false
     *            otherwise
     */
    public ActionButtons( boolean customized ) {

        index_button_over = -1;
        index_button_pressed = -1;

        button_acction = new int[ ACTION_COUNT ];

        button_acction[ACTION_LOOK] = ActionManager.ACTION_LOOK;
        button_acction[ACTION_GRAB] = ActionManager.ACTION_GRAB;
        button_acction[ACTION_TALK] = ActionManager.ACTION_TALK;
        button_acction[ACTION_EXAMINE] = ActionManager.ACTION_EXAMINE;
        button_acction[ACTION_USE] = ActionManager.ACTION_USE;
        button_acction[ACTION_GIVE] = ActionManager.ACTION_GIVE;

        button_normal = new Image[ ACTION_COUNT ];
        if( customized ) {
            button_normal[ACTION_USE] = MultimediaManager.getInstance( ).loadImageFromZip( LoadButton( "btnUse" ), MultimediaManager.IMAGE_MENU );
            button_normal[ACTION_LOOK] = MultimediaManager.getInstance( ).loadImageFromZip( LoadButton( "btnLook.png" ), MultimediaManager.IMAGE_MENU );
            button_normal[ACTION_EXAMINE] = MultimediaManager.getInstance( ).loadImageFromZip( LoadButton( "btnExamine.png" ), MultimediaManager.IMAGE_MENU );
            button_normal[ACTION_TALK] = MultimediaManager.getInstance( ).loadImageFromZip( LoadButton( "btnTalk.png" ), MultimediaManager.IMAGE_MENU );
            button_normal[ACTION_GRAB] = MultimediaManager.getInstance( ).loadImageFromZip( LoadButton( "btnGrab.png" ), MultimediaManager.IMAGE_MENU );
            button_normal[ACTION_GIVE] = MultimediaManager.getInstance( ).loadImageFromZip( LoadButton( "btnGive.png" ), MultimediaManager.IMAGE_MENU );
        }
        else {
            button_normal[ACTION_USE] = MultimediaManager.getInstance( ).loadImage( LoadButton( "btnUse.png" ), MultimediaManager.IMAGE_MENU );
            button_normal[ACTION_LOOK] = MultimediaManager.getInstance( ).loadImage( LoadButton( "btnLook.png" ), MultimediaManager.IMAGE_MENU );
            button_normal[ACTION_EXAMINE] = MultimediaManager.getInstance( ).loadImage( LoadButton( "btnExamine.png" ), MultimediaManager.IMAGE_MENU );
            button_normal[ACTION_TALK] = MultimediaManager.getInstance( ).loadImage( LoadButton( "btnTalk.png" ), MultimediaManager.IMAGE_MENU );
            button_normal[ACTION_GRAB] = MultimediaManager.getInstance( ).loadImage( LoadButton( "btnGrab.png" ), MultimediaManager.IMAGE_MENU );
            button_normal[ACTION_GIVE] = MultimediaManager.getInstance( ).loadImage( LoadButton( "btnGive.png" ), MultimediaManager.IMAGE_MENU );
        }

        button_over = new Image[ ACTION_COUNT ];
        if( customized ) {
            button_over[ACTION_USE] = MultimediaManager.getInstance( ).loadImageFromZip( LoadButton( "btnUseFocus.png" ), MultimediaManager.IMAGE_MENU );
            button_over[ACTION_LOOK] = MultimediaManager.getInstance( ).loadImageFromZip( LoadButton( "btnLookFocus.png" ), MultimediaManager.IMAGE_MENU );
            button_over[ACTION_EXAMINE] = MultimediaManager.getInstance( ).loadImageFromZip( LoadButton( "btnExamineFocus.png" ), MultimediaManager.IMAGE_MENU );
            button_over[ACTION_TALK] = MultimediaManager.getInstance( ).loadImageFromZip( LoadButton( "btnTalkFocus.png" ), MultimediaManager.IMAGE_MENU );
            button_over[ACTION_GRAB] = MultimediaManager.getInstance( ).loadImageFromZip( LoadButton( "btnGrabFocus.png" ), MultimediaManager.IMAGE_MENU );
            button_over[ACTION_GIVE] = MultimediaManager.getInstance( ).loadImageFromZip( LoadButton( "btnGiveFocus.png" ), MultimediaManager.IMAGE_MENU );
        }
        else {
            button_over[ACTION_USE] = MultimediaManager.getInstance( ).loadImage( LoadButton( "btnUseFocus.png" ), MultimediaManager.IMAGE_MENU );
            button_over[ACTION_LOOK] = MultimediaManager.getInstance( ).loadImage( LoadButton( "btnLookFocus.png" ), MultimediaManager.IMAGE_MENU );
            button_over[ACTION_EXAMINE] = MultimediaManager.getInstance( ).loadImage( LoadButton( "btnExamineFocus.png" ), MultimediaManager.IMAGE_MENU );
            button_over[ACTION_TALK] = MultimediaManager.getInstance( ).loadImage( LoadButton( "btnTalkFocus.png" ), MultimediaManager.IMAGE_MENU );
            button_over[ACTION_GRAB] = MultimediaManager.getInstance( ).loadImage( LoadButton( "btnGrabFocus.png" ), MultimediaManager.IMAGE_MENU );
            button_over[ACTION_GIVE] = MultimediaManager.getInstance( ).loadImage( LoadButton( "btnGiveFocus.png" ), MultimediaManager.IMAGE_MENU );
        }

        button_pressed = new Image[ ACTION_COUNT ];
        if( customized ) {
            button_pressed[ACTION_USE] = MultimediaManager.getInstance( ).loadImageFromZip( LoadButton( "btnUsePressed.png" ), MultimediaManager.IMAGE_MENU );
            button_pressed[ACTION_LOOK] = MultimediaManager.getInstance( ).loadImageFromZip( LoadButton( "btnLookPressed.png" ), MultimediaManager.IMAGE_MENU );
            button_pressed[ACTION_EXAMINE] = MultimediaManager.getInstance( ).loadImageFromZip( LoadButton( "btnExaminePressed.png" ), MultimediaManager.IMAGE_MENU );
            button_pressed[ACTION_TALK] = MultimediaManager.getInstance( ).loadImageFromZip( LoadButton( "btnTalkPressed.png" ), MultimediaManager.IMAGE_MENU );
            button_pressed[ACTION_GRAB] = MultimediaManager.getInstance( ).loadImageFromZip( LoadButton( "btnGrabPressed.png" ), MultimediaManager.IMAGE_MENU );
            button_pressed[ACTION_GIVE] = MultimediaManager.getInstance( ).loadImageFromZip( LoadButton( "btnGivePressed.png" ), MultimediaManager.IMAGE_MENU );
        }
        else {
            button_pressed[ACTION_USE] = MultimediaManager.getInstance( ).loadImage( LoadButton( "btnUsePressed.png" ), MultimediaManager.IMAGE_MENU );
            button_pressed[ACTION_LOOK] = MultimediaManager.getInstance( ).loadImage( LoadButton( "btnLookPressed.png" ), MultimediaManager.IMAGE_MENU );
            button_pressed[ACTION_EXAMINE] = MultimediaManager.getInstance( ).loadImage( LoadButton( "btnExaminePressed.png" ), MultimediaManager.IMAGE_MENU );
            button_pressed[ACTION_TALK] = MultimediaManager.getInstance( ).loadImage( LoadButton( "btnTalkPressed.png" ), MultimediaManager.IMAGE_MENU );
            button_pressed[ACTION_GRAB] = MultimediaManager.getInstance( ).loadImage( LoadButton( "btnGrabPressed.png" ), MultimediaManager.IMAGE_MENU );
            button_pressed[ACTION_GIVE] = MultimediaManager.getInstance( ).loadImage( LoadButton( "btnGivePressed.png" ), MultimediaManager.IMAGE_MENU );
        }

    }

    private String LoadButton( String name ) {

        //route to the gui elements
        String route = "gui/hud/traditional/";
        String dirButton = null;

        //#JAVA6#
        if( Game.getInstance( ).isFromEditor( ) ) {
            dirButton = route + Controller.getInstance( ).getLanguage( ) + "/" + name + ".png";
            File fichero = new File( dirButton );
            if( !fichero.exists( ) ) {
                // if there isn't file, load the default file 
                dirButton = route + Controller.getInstance( ).getDefaultLanguage( ) + "/" + name + ".png";
            }
        }
        else {
        //@JAVA6@
            try{
                dirButton = route + ConfigData.getLanguage( ) + "/" + name + ".png";
                File fichero = new File( dirButton );
                if( !fichero.exists( ) ) {
                    // if there isn't file, load the default file
                    dirButton = route + ConfigData.getDefaultLanguage( ) + "/" + name + ".png";
                }
            }catch(NullPointerException e){
                dirButton = route + TC.get( "Language.Internationalize" ) + "/" + name + ".png";
            }
        //#JAVA6#            
        }
        //@JAVA6@
        
        return dirButton;
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

        index_button_over = ACTION_NOACTION;
        if( e != null ) {
            int i = 0;
            int pointX = TraditionalHUD.FIRST_ACTIONBUTTON_X;
            int pointY = TraditionalHUD.FIRST_ACTIONBUTTON_Y;
            while( index_button_over == ACTION_NOACTION && i < ACTION_COUNT ) {
                if( e.getX( ) > pointX && e.getX( ) < pointX + TraditionalHUD.ACTIONBUTTON_WIDTH && e.getY( ) > pointY && e.getY( ) < pointY + TraditionalHUD.ACTIONBUTTON_HEIGHT ) {
                    index_button_over = i;
                }
                pointX += TraditionalHUD.ACTIONBUTTON_SPACING_X;
                if( pointX == TraditionalHUD.FIRST_ACTIONBUTTON_X + 3 * TraditionalHUD.ACTIONBUTTON_SPACING_X ) {
                    pointX = TraditionalHUD.FIRST_ACTIONBUTTON_X;
                    pointY += TraditionalHUD.ACTIONBUTTON_SPACING_Y;
                }
                i++;
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

        index_button_pressed = ACTION_NOACTION;
        if( e != null ) {
            int pointX = TraditionalHUD.FIRST_ACTIONBUTTON_X;
            int pointY = TraditionalHUD.FIRST_ACTIONBUTTON_Y;
            int i = 0;

            while( index_button_pressed == ACTION_NOACTION && i < ACTION_COUNT ) {
                if( e.getX( ) > pointX && e.getX( ) < pointX + TraditionalHUD.ACTIONBUTTON_WIDTH && e.getY( ) > pointY && e.getY( ) < pointY + TraditionalHUD.ACTIONBUTTON_HEIGHT ) {
                    index_button_pressed = i;
                }
                pointX += TraditionalHUD.ACTIONBUTTON_SPACING_X;
                if( pointX == TraditionalHUD.FIRST_ACTIONBUTTON_X + 3 * TraditionalHUD.ACTIONBUTTON_SPACING_X ) {
                    pointX = TraditionalHUD.FIRST_ACTIONBUTTON_X;
                    pointY += TraditionalHUD.ACTIONBUTTON_SPACING_Y;
                }
                i++;
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

        int pointX = TraditionalHUD.FIRST_ACTIONBUTTON_X;
        int pointY = TraditionalHUD.FIRST_ACTIONBUTTON_Y;
        for( int i = 0; i < ACTION_COUNT; i++ ) {
            if( index_button_pressed == i ) {
                g.drawImage( button_pressed[i], pointX, pointY, null );
            }
            else if( index_button_over == i ) {
                g.drawImage( button_over[i], pointX, pointY, null );
            }
            else {
                g.drawImage( button_normal[i], pointX, pointY, null );
            }

            pointX += TraditionalHUD.ACTIONBUTTON_SPACING_X;
            if( pointX == TraditionalHUD.FIRST_ACTIONBUTTON_X + 3 * TraditionalHUD.ACTIONBUTTON_SPACING_X ) {
                pointX = TraditionalHUD.FIRST_ACTIONBUTTON_X;
                pointY += TraditionalHUD.ACTIONBUTTON_SPACING_Y;
            }
        }
    }

    /**
     * Forces a button to be pressed
     * 
     * @param button
     *            int corresponding the button to be pressed
     */
    public void setButtonPressed( int button ) {

        index_button_pressed = button;

    }

    /**
     * Get the action of the current button pressed
     * 
     * @return Action of the button
     */
    public int getActionPressed( ) {

        return button_acction[index_button_pressed];
    }

}
