package es.eucm.eadventure.engine.core.control;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import es.eucm.eadventure.common.data.chapter.Exit;
import es.eucm.eadventure.common.data.chapter.NextScene;
import es.eucm.eadventure.common.data.chapter.scenes.GeneralScene;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * Updated feb 2008: cursors
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
    
    public static final int ACTION_CUSTOM = 9;
    
    public static final int ACTION_CUSTOM_INTERACT = 10;

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
     * Cursor of the current exit being selected
     */
    private Cursor exitCursor;
    
    /**
     * List of the already created cursors. Useful to avoid creating the same cursors more than once
     */
    private HashMap<Exit, Cursor> cursors;
    
    /**
     * Constructor.
     */
    public ActionManager( ) {
        elementOver = null;
        actionSelected = 6;
        exit = "";
        exitCursor=null;
        cursors = new HashMap<Exit, Cursor>();
    }
    
    /**
     * Returns the selected element.
     * @return Selected element
     */
    public FunctionalElement getElementOver( ) {
        return elementOver;
    }
    
    /**
     * Sets the new selected element.
     * @param elementOver New selected element
     */
    public void setElementOver( FunctionalElement elementOver ) {
        this.elementOver = elementOver;
    }

    /**
     * Returns the action selected.
     * @return Action selected
     */
    public int getActionSelected( ) {
        return actionSelected;
    }

    /**
     * Sets the new action selected.
     * @param actionSelected New action selected
     */
    public void setActionSelected( int actionSelected ) {
        this.actionSelected = actionSelected;
        GUI.getInstance( ).newActionSelected( );
    }

    /**
     * Returns the current exit.
     * @return Current exit
     */
    public String getExit( ) {
        return exit;
    }
    
    /**
     * Returns the current exit cursor.
     * @return Current cursor
     */
    public Cursor getExitCursor( ) {
        return exitCursor;
    }


    /**
     * Sets the current exit.
     * @param exit Current exit
     */
    public void setExit( String exit ) {
        if( exit == null ) 
            this.exit = "";
        else
            this.exit = exit;
    }
    
    /**
     * Sets the current exit cursor.
     * @param exit Current exit cursro
     */
    public void setExitCursor( Cursor cursor ) {
        this.exitCursor=cursor;
    }
    
    public void setExitCustomized ( String exit, Cursor cursor){
        setExit(exit);
        setExitCursor(cursor);
    }

    /**
     * Called when a mouse click event has been triggered
     * @param e Mouse event
     */
    public void mouseClicked( MouseEvent e ) {
        Game game = Game.getInstance();
        if( e.getButton( ) == MouseEvent.BUTTON1 ) {
        	DebugLog.user("Mouse clicked in scene: " + e.getX() + " , " + e.getY());
            game.getFunctionalScene( ).mouseClicked( e.getX( ), e.getY( ) );
        }
    }
    
    /**
     * Called when a mouse move event has been triggered
     * @param e Mouse event
     */
    public void mouseMoved( MouseEvent e ) {
        Game game = Game.getInstance();
        FunctionalElement elementInside = game.getFunctionalScene( ).getElementInside( e.getX( ), e.getY( ) );
        Exit exit = game.getFunctionalScene( ).getExitInside( e.getX( ), e.getY( ) );
        if( elementInside != null ) {
            setElementOver( elementInside );
            
        } else if( exit != null && actionSelected == ACTION_GOTO ) {
            //Check if the exit has a customized cursor. If it has already been created, retrieve it. Otherwise, create it
            boolean isCursorSet = getCursorPath( exit )!=null && !getCursorPath( exit ).equals( "" );
            //Customized. It has already been created
            if (isCursorSet && cursors.containsKey( exit )){
                setExitCursor(cursors.get( exit ));
            }
 
            //Customized. Not created yet.
            else if(isCursorSet && !cursors.containsKey( exit )){
                Cursor newCursor = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImageFromZip( getCursorPath( exit ), MultimediaManager.IMAGE_MENU ), new Point( 0, 0 ), "exitCursor("+exit+")" );
                this.cursors.put( exit, newCursor );
                setExitCursor(newCursor);
            }
            
            //Not customized. Use default
            else{
               setExitCursor(null);
            }
            
            
            GeneralScene nextScene = null;
            
            // Pick the FIRST valid next-scene structure
            for( int i = 0; i < exit.getNextScenes( ).size( ) && nextScene == null; i++ )
                if( new FunctionalConditions( exit.getNextScenes( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                    nextScene = game.getCurrentChapterData( ).getGeneralScene( exit.getNextScenes( ).get( i ).getNextSceneId( ) );

            //Check the text (customized or not)
            if (getExitText( exit )!=null ){
                setExit (getExitText( exit  ));
            } else if( nextScene != null )
                setExit( nextScene.getName( ) );
        }
    }
    
    public String getExitText( Exit exit ) {
        String exitText=null;
        for (NextScene nextScene:exit.getNextScenes()){
            if (new FunctionalConditions(nextScene.getConditions( )).allConditionsOk( )){
                exitText=nextScene.getExitText( );
            }
        }
        
        if (exitText==null && exit.getDefaultExitLook()!=null)
            exitText=exit.getDefaultExitLook().getExitText( );
        
        return exitText;
    }

    
    /**
     * Returns the cursor of the first resources block which all conditions are met
     * @return the cursor
     */
    public String getCursorPath(Exit exit){
        String cursorPath=null;
        for (NextScene nextScene:exit.getNextScenes()){
            if (new FunctionalConditions(nextScene.getConditions( )).allConditionsOk( )){
                cursorPath=nextScene.getCursorPath( );
            }
        }
        if (cursorPath==null && exit.getDefaultExitLook()!=null)
            cursorPath=exit.getDefaultExitLook().getCursorPath( );
        return cursorPath;
   }


	public void setCustomActionName(String name) {
		customActionName = name;
	}
	
	public String getCustomActionName() {
		return customActionName;
	}

}
