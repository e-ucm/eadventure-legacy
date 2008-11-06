package es.eucm.eadventure.engine.core.data.gamedata;

import java.util.ArrayList;

import es.eucm.eadventure.engine.core.data.gamedata.conditions.Conditions;
import es.eucm.eadventure.engine.core.data.gamedata.resources.Resources;

/**
 * This class holds the data of an exit in eAdventure
 */
public class Exit {

    public static final String CURSOR = "cursor";
    
    /**
     * X position of the upper left corner of the exit
     */
    private int x0;

    /**
     * Y position of the upper left corner of the exit
     */
    private int y0;

    /**
     * X position of the bottom right corner of the exit
     */
    private int x1;

    /**
     * Y position of the bottom right corner of the exit
     */
    private int y1;

    /**
     * List of nextscenes of the exit
     */
    private ArrayList<NextScene> nextScenes;
    
    
    /**
     * Default exit look (it can exists or not)
     */
    private ExitLook defaultExitLook;

    /**
     * Creates a new Exit
     * @param x0 The horizontal coordinate of the upper left corner of the exit
     * @param y0 The vertical coordinate of the upper left corner of the exit
     * @param x1 The horizontal coordinate of the bottom right corner of the exit
     * @param y1 The vertical coordinate of the bottom right corner of the exit
     */
    public Exit( int x0, int y0, int x1, int y1 ) {
        this.x0 = x0;
        this.x1 = x1;
        this.y0 = y0;
        this.y1 = y1;
        
        nextScenes = new ArrayList<NextScene>( );
    }

    /**
     * Returns the horizontal coordinate of the upper left corner of the exit
     * @return the horizontal coordinate of the upper left corner of the exit
     */
    public int getX0( ) {
        return x0;
    }

    /**
     * Returns the vertical coordinate of the upper left corner of the exit
     * @return the vertical coordinate of the upper left corner of the exit
     */
    public int getX1( ) {
        return x1;
    }

    /**
     * Returns the horizontal coordinate of the bottom right of the exit
     * @return the horizontal coordinate of the bottom right of the exit
     */
    public int getY0( ) {
        return y0;
    }

    /**
     * Returns the vertical coordinate of the bottom right of the exit
     * @return the vertical coordinate of the bottom right of the exit
     */
    public int getY1( ) {
        return y1;
    }

    /**
     * Returns the list of the next scenes from this scene
     * @return the list of the next scenes from this scene
     */
    public ArrayList<NextScene> getNextScenes( ) {
        return nextScenes;
    }

    /**
     * Adds a next scene to the list of next scenes
     * @param nextScene the next scene to add
     */
    public void addNextScene( NextScene nextScene ) {
        nextScenes.add( nextScene );
    }
    

    /**
     * Returns whether a point is inside the exit
     * @param x the horizontal positon
     * @param y the vertical position
     * @return true if the point (x, y) is inside the exit, false otherwise
     */
    public boolean isPointInside( int x, int y ) {
        return x > x0 && x < x1 && y > y0 && y < y1;
    }

    public String getExitText( ) {
        String exitText=null;
        for (NextScene nextScene:nextScenes){
            if (nextScene.getConditions( ).allConditionsOk( )){
                exitText=nextScene.getExitText( );
            }
        }
        
        if (exitText==null && defaultExitLook!=null)
            exitText=defaultExitLook.getExitText( );
        
        return exitText;
    }

    
    /**
     * Returns the cursor of the first resources block which all conditions are met
     * @return the cursor
     */
    public String getCursorPath(){
        String cursorPath=null;
        for (NextScene nextScene:nextScenes){
            if (nextScene.getConditions( ).allConditionsOk( )){
                cursorPath=nextScene.getCursorPath( );
            }
        }
        
        if (cursorPath==null && defaultExitLook!=null)
            cursorPath=defaultExitLook.getCursorPath( );
        
        return cursorPath;
   }

    /**
     * @return the defaultExitLook
     */
    public ExitLook getDefaultExitLook( ) {
        return defaultExitLook;
    }

    /**
     * @param defaultExitLook the defaultExitLook to set
     */
    public void setDefaultExitLook( ExitLook defaultExitLook ) {
        this.defaultExitLook = defaultExitLook;
    }

 
}