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
package es.eucm.eadventure.engine.core.control;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.data.chapter.Exit;
import es.eucm.eadventure.common.data.chapter.elements.ActiveArea;
import es.eucm.eadventure.common.data.chapter.elements.Item;
import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.common.data.chapter.scenes.GeneralScene;
import es.eucm.eadventure.common.data.chapter.scenes.Scene;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalScene;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * Updated feb 2008: cursors
 * 
 * @author Javier Torrente
 * 
 */
public class ActionManager {

    /**
     * Constant for looking action
     */
    public static final int ACTION_LOOK = 0;

    /**
     * Constant for grabbing action
     */
    public static final int ACTION_GRAB = 1;

    /**
     * Constant for talking action
     */
    public static final int ACTION_TALK = 2;

    /**
     * Constant for examining action
     */
    public static final int ACTION_EXAMINE = 3;

    /**
     * Constant for using action
     */
    public static final int ACTION_USE = 4;

    /**
     * Constant for giving action
     */
    public static final int ACTION_GIVE = 5;

    /**
     * Constant for going to action
     */
    public static final int ACTION_GOTO = 6;

    /**
     * Constant for using with action
     */
    public static final int ACTION_USE_WITH = 7;

    /**
     * Constant for giving to action
     */
    public static final int ACTION_GIVE_TO = 8;

    /**
     * Constant for custom action
     */
    public static final int ACTION_CUSTOM = 9;

    /**
     * Constant for custom interact action
     */
    public static final int ACTION_CUSTOM_INTERACT = 10;
    
    /**
     * Constant for drag to action
     */
    public static final int ACTION_DRAG_TO = 11;

    /**
     * Functional element in which the cursor is placed.
     */
    private FunctionalElement elementOver;

    /**
     * Current action selected.
     */
    private int actionSelected;

    /**
     * The original action.
     */
    private String customActionName;

    /**
     * Name of the current string being selected.
     */
    private String exit;
    /**
     * Path of the current audio path for exit's name. Added for v1.4
     */
    private String exitSoundPath;

    /**
     * Cursor of the current exit being selected
     */
    private Cursor exitCursor;

    /**
     * List of the already created cursors. Useful to avoid creating the same
     * cursors more than once
     */
    private HashMap<Exit, Cursor> cursors;

    private FunctionalElement dragElement = null;

    /**
     * Constructor.
     */
    public ActionManager( ) {

        elementOver = null;
        actionSelected = ACTION_GOTO;
        exit = "";
        exitSoundPath = "";
        exitCursor = null;
        cursors = new HashMap<Exit, Cursor>( );
    }

    /**
     * Returns the selected element.
     * 
     * @return Selected element
     */
    public FunctionalElement getElementOver( ) {

        return elementOver;
    }

    /**
     * Sets the new selected element.
     * 
     * @param elementOver
     *            New selected element
     */
    public void setElementOver( FunctionalElement elementOver ) {

        this.elementOver = elementOver;
    }

    /**
     * Returns the action selected.
     * 
     * @return Action selected
     */
    public int getActionSelected( ) {

        return actionSelected;
    }

    /**
     * Sets the new action selected.
     * 
     * @param actionSelected
     *            New action selected
     */
    public void setActionSelected( int actionSelected ) {

        this.actionSelected = actionSelected;
        GUI.getInstance( ).newActionSelected( );
    }

    /**
     * Returns the current exit.
     * 
     * @return Current exit
     */
    public String getExit( ) {

        return exit;
    }
    
    /**
     * Returns the current exit sound path.
     * 
     * @return Current exit sound path
     * 
     * Added 1.4
     */
    public String getExitSoundPath( ) {

        return exitSoundPath;
    }

    /**
     * Returns the current exit cursor.
     * 
     * @return Current cursor
     */
    public Cursor getExitCursor( ) {

        return exitCursor;
    }

    /**
     * Sets the current exit.
     * 
     * @param exitText
     *            Current exit
     *            
     *            Modified 1.4
     */
    public void setExit( String exitText, String soundPath ) {

        if( exitText == null ){
            this.exit = "";
        }else{
            this.exit = exitText;
        }
        
        if( soundPath == null ){
            this.exitSoundPath = "";
        }else{
            this.exitSoundPath = soundPath;
        }

    }

    /**
     * Sets the current exit cursor.
     * 
     * @param exit
     *            Current exit cursro
     */
    public void setExitCursor( Cursor cursor ) {

        this.exitCursor = cursor;
    }

    public void deleteCustomExit(  ) {

        setExit( null, null );
        setExitCursor( null );
    }

    /**
     * Called when a mouse click event has been triggered
     * 
     * @param e
     *            Mouse event
     */
    public void mouseClicked( MouseEvent e ) {

        Game game = Game.getInstance( );

        //if( //this.elementOver!=null && isBinaryAction ( actionSelected ) || 
        //		e.getButton( ) == MouseEvent.BUTTON1 ) {
        DebugLog.user( "Mouse clicked in scene: " + e.getX( ) + " , " + e.getY( ) );
        game.getFunctionalScene( ).mouseClicked( e.getX( ), e.getY( ) );
        //}
    }

    /**
     * Added v1.4 (See method below)
     * @param element
     * @param actions
     * @return
     */
    private boolean isElementTargetInActions ( FunctionalElement element, List<Action> actions ){
        boolean isTarget=false;
        if (actions!=null){
            for (Action a: actions){
                if (element!=null && element.getElement( ) !=null && element.getElement( ).getId()!=null &&
                        a!=null && a.getTargetId( )!=null && a.getTargetId( ).equals( element.getElement( ).getId() )){
                    isTarget=true;break;
                }
            }
        }
        return isTarget;
    }
    
    /**
     * Added in v1.4. Checks if the given element is the target of any interaction (use-with, give-to, etc.) in the chapter.
     * This method is used to determine if an element with no actions should be clickable (it name appears on screen)
     * @param element
     * @return
     */
    private boolean isElementTarget( FunctionalElement element ){
        Chapter chapter = Game.getInstance( ).getCurrentChapterData( );
        boolean isTarget=false;
        if (chapter!=null){
            //Check if element is target in items
            List<Item> items=chapter.getItems( );
            if (items!=null){
                for (Item it:items){
                    isTarget |= isElementTargetInActions(element, it.getActions( ));
                    if (isTarget) break;
                }
            }
            
            //Check if element is target in npcs
            List<NPC> npcs=chapter.getCharacters( );
            if (!isTarget && npcs!=null){
                for (NPC npc:npcs){
                    isTarget |= isElementTargetInActions(element, npc.getActions( ));
                    if (isTarget) break;
                }
            }

            //Check if element is target in active areas
            List<Scene> scenes=chapter.getScenes( );
            if (!isTarget && scenes!=null){
                for (Scene scene: scenes){
                    List<ActiveArea> activeAreas=scene.getActiveAreas( );
                    if (activeAreas!=null){
                        for (ActiveArea aa :activeAreas){
                            isTarget |= isElementTargetInActions(element, aa.getActions( ));
                            if (isTarget) break;
                        }
                    }
                }
            }
        }
        return isTarget;
    }
    
    /**
     * Called when a mouse move event has been triggered
     * 
     * @param e
     *            Mouse event
     */
    public void mouseMoved( MouseEvent e ) {
        Game game = Game.getInstance( );
        FunctionalScene functionalScene = game.getFunctionalScene( );
        if( functionalScene == null )
            return;
        
        Exit exit = functionalScene.getExitInside( e.getX( ), e.getY( ) );
        FunctionalElement elementInside = functionalScene.getElementInside( e.getX( ), e.getY( ), dragElement );

        if (dragElement != null) {
            dragElement.setX( e.getX( ) );
            dragElement.setY( e.getY( ) );
        }
        
        if( exit == null && elementInside != null ) {
            if (elementInside.getElement( ).getActionsCount( ) != 0
                    || isElementTarget(elementInside)
                    || elementInside.getElement( ).getDescriptions( ).size() > 1
                    || (elementInside.getElement( ).getDescription( 0 ).getDetailedDescription( ) != null
                        && !elementInside.getElement( ).getDescription( 0 ).getDetailedDescription( ).equals( "" )))
                setElementOver( elementInside ); 
        }
        else if( exit != null && actionSelected == ACTION_GOTO ) {
            boolean isCursorSet = getCursorPath( exit ) != null && !getCursorPath( exit ).equals( "" );

            if( isCursorSet && !cursors.containsKey( exit ) ) {
                Cursor newCursor;
                try {
                    newCursor = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImageFromZip( getCursorPath( exit ), MultimediaManager.IMAGE_MENU ), new Point( 0, 0 ), "exitCursor(" + exit + ")" );
                }
                catch( Exception exc ) {
                    newCursor = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImageFromZip( "gui/cursors/nocursor.png", MultimediaManager.IMAGE_MENU ), new Point( 0, 0 ), "exitCursor(" + exit + ")" );
                }
                this.cursors.put( exit, newCursor );
                setExitCursor( newCursor );
            }
            else if( isCursorSet && cursors.containsKey( exit ) )
                setExitCursor( cursors.get( exit ) );
            else
                setExitCursor( null );

            GeneralScene nextScene = null;

            // Pick the FIRST valid next-scene structure
            for( int i = 0; i < exit.getNextScenes( ).size( ) && nextScene == null; i++ )
                if( new FunctionalConditions( exit.getNextScenes( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                    nextScene = game.getCurrentChapterData( ).getGeneralScene( exit.getNextScenes( ).get( i ).getTargetId( ) );

            //Check the text (customized or not)
            if( getExitText( exit ) != null && !getExitText( exit ).equals( "" ) ) {
                setExit( getExitText( exit ), getExitAudioPath ( exit ) );
            }
            else if( getExitText( exit ) != null ) {
                setExit( " ", getExitAudioPath ( exit ) );
            }
            else if( nextScene != null )
                setExit( nextScene.getName( ), getExitAudioPath ( exit ) );
        }
    }

    private String getExitText( Exit exit ) {

        if( exit.getDefaultExitLook( ) != null )
            return exit.getDefaultExitLook( ).getExitText( );
        return null;
    }

    private String getExitAudioPath( Exit exit ) {

        if( exit.getDefaultExitLook( ) != null )
            return exit.getDefaultExitLook( ).getSoundPath( );
        return null;
    }
    
    /**
     * Returns the cursor of the first resources block which all conditions are
     * met
     * 
     * @return the cursor
     */
    public String getCursorPath( Exit exit ) {

        if( exit.getDefaultExitLook( ) != null ) {
            return exit.getDefaultExitLook( ).getCursorPath( );
        }
        return null;
    }

    public void setCustomActionName( String name ) {

        customActionName = name;
    }

    public String getCustomActionName( ) {

        return customActionName;
    }

}
