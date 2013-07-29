/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *         research group.
 *  
 *   Copyright 2005-2010 e-UCM research group.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   e-UCM is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     eAdventure is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     eAdventure is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package es.eucm.eadventure.engine.core.control.interaction.auxiliar;

import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import es.eucm.eadventure.common.data.chapter.Exit;
import es.eucm.eadventure.common.data.chapter.elements.ActiveArea;
import es.eucm.eadventure.engine.core.control.DebugLog;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalActiveArea;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalItem;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalNPC;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalScene;
import es.eucm.eadventure.engine.core.gui.GUI;


public class GridFactory {

    public static final int MAX_TRIES = 200;
    
    /*public static List<GridPosition> buildSceneGrid( String idScene){
        // Use old method
        //return getGrillScene(idScene);
        
        // Use new method
        return buildSceneGrid ( );
    }*/
    
    public static List<GridPosition> buildSceneGrid ( ){
        // Grid object
        List<GridPosition> grid = new ArrayList<GridPosition>();
        
        // Get FunctionalScene
        FunctionalScene functionalScene = Game.getInstance( ).getFunctionalScene( );
        
        // Consider if there are elements in the scene outside the view port that need to be reached. In that case add points to go left or right
        boolean scroll=false;
        
        // View port
        Polygon viewPort = new Polygon();
        viewPort.addPoint( functionalScene.getOffsetX( ), 0 );
        viewPort.addPoint( functionalScene.getOffsetX( ) + GUI.WINDOW_WIDTH, 0 );
        viewPort.addPoint( functionalScene.getOffsetX( ) + GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT );
        viewPort.addPoint( functionalScene.getOffsetX( ), GUI.WINDOW_HEIGHT );

        /*viewPort.addPoint( 0, 0 );
        viewPort.addPoint( GUI.WINDOW_WIDTH, 0 );
        viewPort.addPoint( GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT );
        viewPort.addPoint( 0, GUI.WINDOW_HEIGHT );*/
        
        // Check functionalScene is not null
        if (functionalScene!=null){
            // Process exits
            List<Exit> exits = functionalScene.getScene( ).getExits( );
            for ( Exit exit: exits ){
                // If exit is placed outside scene's bounds, discard it 
                if ( isRectangleOutsideScene(exit) ){
                    continue;
                }
                
                else if (new FunctionalConditions(exit.getConditions( )).allConditionsOk( ) ||
                        exit.isHasNotEffects( )){

                    // Check exit intersects viewport. 
                    if (!isRectangleCompletelyInsideViewPort(exit)){
                        scroll = true;
                    } 
                    if (isRectangleIntersectsViewPort(exit)){
                        GridPosition pos = getRectangleGridPosition ( exit, "Exit");
                        if (pos!=null){
                            grid.add( pos );
                        }else if (Game.getInstance( ).isDebug( )){
                            DebugLog.general( "[KeyboardInterface] ERROR: Exit to "+exit.getNextSceneId( )+
                                    " in scene "+functionalScene.getScene( ).getId( )+" is not reachable");
                        }
                    }
                }
            }
            // Process active areas
            List<ActiveArea> activeAreas = functionalScene.getScene( ).getActiveAreas( );
            for ( ActiveArea activeArea: activeAreas ){
                // If exit is placed outside scene's bounds, discard it 
                if ( isRectangleOutsideScene(activeArea) ){
                    continue;
                }

                else if (new FunctionalConditions(activeArea.getConditions( )).allConditionsOk( )){
                    
                    // Check active area intersects viewport. 
                    if (!isRectangleCompletelyInsideViewPort(activeArea)){
                        scroll = true;
                    } 
                    
                    if (isRectangleIntersectsViewPort(activeArea)){
                        GridPosition pos =getRectangleGridPosition( activeArea, "ActiveArea" );
                        if (pos!=null){
                            grid.add( pos );
                        }else if (Game.getInstance( ).isDebug( )){
                            DebugLog.general( "[KeyboardInterface] ERROR: Active Area "+activeArea.getId( )+
                                    " in scene "+functionalScene.getScene( ).getId( )+" is not reachable");
                        }
                    }
                }
            }
            
            // Items
            List<? extends FunctionalElement> items = functionalScene.getItems( );
            for (FunctionalElement element: items){
                // If element is placed outside scene's bounds, discard it 
                //if ( isElementOutsideScene(element) ){
                //    continue;
                //} 
                // Check element intersects viewport. 
                if (!isElementCompletelyInsideViewPort(element)){
                    scroll = true;
                }
                
                if (isElementIntersectsViewPort(element)){
                    GridPosition pos =getElementGridPosition( element, "ItemReferences");
                    if (pos!=null)
                        grid.add( pos );
                    else if (Game.getInstance( ).isDebug( )){
                        DebugLog.general( "[KeyboardInterface] ERROR: Item "+((FunctionalItem)element).getItem( ).getId( )+
                                " in scene "+functionalScene.getScene( ).getId( )+" is not reachable");
                    }
                }
            }
            
            // Characters
            List<? extends FunctionalElement> npcs = functionalScene.getNPCs( );
            for (FunctionalElement element: npcs){
                // If element is placed outside scene's bounds, discard it 
                //if ( isElementOutsideScene(element) ){
                //    continue;
                //} 
                // Check element intersects viewport. 
                if (!isElementCompletelyInsideViewPort(element)){
                    scroll = true;
                }
                if (isElementIntersectsViewPort(element)){
                    GridPosition pos =getElementGridPosition( element, "CharacterReferences");
                    if (pos!=null)
                        grid.add( pos );
                    else if (Game.getInstance( ).isDebug( )){
                        DebugLog.general( "[KeyboardInterface] ERROR: Character "+((FunctionalNPC)element).getNPC( ).getId( )+
                                " in scene "+functionalScene.getScene( ).getId( )+" is not reachable");
                    }
                }
            }
            
        }
        
        //If scroll, add a point in the grid to make scroll
        // arrows accessible through keyboard
        if (scroll|| functionalScene.isShowsOffsetArrows( )){
            grid.add( new GridPosition(functionalScene.getScene( ).getId( ), 
                    GUI.getInstance( ).getLeftOffsetArrowCenter( ).x, 
                    GUI.getInstance( ).getLeftOffsetArrowCenter( ).y, "Exit", "") );
            grid.add( new GridPosition(functionalScene.getScene( ).getId( ), 
                    GUI.getInstance( ).getRightOffsetArrowCenter( ).x, 
                    GUI.getInstance( ).getRightOffsetArrowCenter( ).y, "Exit", "") );
        }
        
        
        // Finally, order the grid
        /*for ( int i=1; i<grid.size( ); i++){
            for (int j=0; j<i; j++){
                if (compareGridPositions(grid.get( j ), grid.get( i ))==1 ||
                        (compareGridPositions(grid.get( j ), grid.get( i ))==0 && 
                        grid.get( j ).getY( ) > grid.get( i ).getY( ))){
                    // Insert grid[j] in position i
                    grid.add( i, grid.remove( j ) );
                    break;
                } 
            }
        }*/
        if (grid.size( )>0){
            orderGridX(grid, 0, grid.size( )-1);
            
            GridPosition init=null;
            GridPosition end=null;
            int initPos=-1;
            int endPos=-1;
            for (int i=0; i<grid.size( ); i++){
                if (init!=null){
                    if ( GridFactory.compareGridPositions( grid.get( i ), init)==0){
                        endPos=i;
                        end = grid.get( i );
                    }else if (end!=null){
                        orderGridY(grid,initPos,endPos);
                        init=end=null;
                    } else {
                        init=grid.get( i );
                        initPos=i;
                    }
                } else {
                    init = grid.get( i );
                    initPos=i;
                }
            }
        }
        
        return grid;
    }
    
  //Recibe un vector de enteros y el �ndice del primer y �ltimo elemento v�lido del mismo
    
    private static void orderGridX(List<GridPosition> grid, int first, int last){
            int i=first, j=last;
            GridPosition pivot= grid.get( (first + last) / 2 );
            GridPosition aux;
     
            do{
                    while( GridFactory.compareGridPositions( grid.get( i ), pivot )==-1 ) i++;                  
                    while( GridFactory.compareGridPositions( grid.get( j ), pivot )==1 ) j--;
     
                    if (i<=j){
                        
                        aux=grid.get( j );
                        //auxiliar=vector[j];
                        grid.set( j, grid.get( i ) );
                        //vector[j]=vector[i];
                        grid.set( i, aux );
                        //vector[i]=auxiliar;
                            i++;
                            j--;
                    }
     
            } while (i<=j);
     
            if(first<j) orderGridX(grid,first, j);
            if(last>i) orderGridX(grid,i, last);
    }
    
    private static void orderGridY(List<GridPosition> grid, int first, int last){
        int i=first, j=last;
        GridPosition pivot= grid.get( (first + last) / 2 );
        GridPosition aux;
 
        do{
                while( grid.get( i ).getY( )< pivot.getY( ) ) i++;                  
                while( grid.get( j ).getY( )> pivot.getY( ) ) j--;
 
                if (i<=j){
                    
                    aux=grid.get( j );
                    //auxiliar=vector[j];
                    grid.set( j, grid.get( i ) );
                    //vector[j]=vector[i];
                    grid.set( i, aux );
                    //vector[i]=auxiliar;
                        i++;
                        j--;
                }
 
        } while (i<=j);
 
        if(first<j) orderGridY(grid,first, j);
        if(last>i) orderGridY(grid,i, last);
}

    
    /**
     * Returns 0 if g1.x is equivalent to g2.x, 1 if g1.x is greater than g2.x, -1 if is lower         
     * @param p1
     * @param p2
     * @return
     */
    private static int compareGridPositions ( GridPosition p1, GridPosition p2 ){
        int dif=p1.getX( )-p2.getX( );
        if (Math.abs(dif)<=20){
            return 0;
        } else if (dif>0)
            return 1;
        else return -1;
    }
    /**
     * Does not take into account scroll, just scene dimensions
     * @param rectangle
     * @return
     */
    private static boolean isRectangleOutsideScene ( es.eucm.eadventure.common.data.chapter.Rectangle rectangle ){
     // Get FunctionalScene
        FunctionalScene functionalScene = Game.getInstance( ).getFunctionalScene( );

        return rectangle.getX( )+rectangle.getWidth( ) < 0 && 
        rectangle.getX( ) > functionalScene.getBackgroundWidth() &&
        rectangle.getY( )+rectangle.getHeight( ) <0  &&
        rectangle.getY( ) > GUI.WINDOW_HEIGHT;
        
                
    }
    
    
    /**
     * Does not take into account scroll, just scene dimensions
     * @param rectangle
     * @return
     */
    private static boolean isRectangleCompletelyInsideViewPort ( es.eucm.eadventure.common.data.chapter.Rectangle rectangle ){
     // Get FunctionalScene
        FunctionalScene functionalScene = Game.getInstance( ).getFunctionalScene( );
        int offsetX = functionalScene.getOffsetX( );

        return rectangle.getX( )+rectangle.getWidth( ) <= offsetX+GUI.WINDOW_WIDTH && 
        rectangle.getX( ) >= offsetX &&
        rectangle.getY( )+rectangle.getHeight( ) <= GUI.WINDOW_HEIGHT &&
        rectangle.getY( ) >= 0;

        
    }

    /**
     * Does not take into account scroll, just scene dimensions
     * @param rectangle
     * @return
     */
    private static boolean isRectangleIntersectsViewPort ( es.eucm.eadventure.common.data.chapter.Rectangle rectangle ){
     // Get FunctionalScene
        FunctionalScene functionalScene = Game.getInstance( ).getFunctionalScene( );
        int offsetX = functionalScene.getOffsetX( );

        int rightLimit = offsetX+GUI.WINDOW_WIDTH;
        int leftLimit = offsetX;
        int topLimit = 0;
        int bottomLimit = GUI.WINDOW_HEIGHT;
        
        return isRectangleCompletelyInsideViewPort(rectangle) ||
                ( rightLimit>=rectangle.getX( ) && rightLimit<=rectangle.getX( )+rectangle.getWidth( )) ||
                ( leftLimit>=rectangle.getX( ) && leftLimit<=rectangle.getX( )+rectangle.getWidth( ) ) ||
                ( topLimit>=rectangle.getY( ) && topLimit<=rectangle.getY( )+rectangle.getHeight( )) ||
                ( bottomLimit>=rectangle.getY( ) && bottomLimit<=rectangle.getY( )+rectangle.getHeight( ) );

        
    }

    
    /**
     * Does not take into account scroll, just scene dimensions
     * @param rectangle
     * @return
     */
    private static boolean isElementOutsideScene ( FunctionalElement element ){
     // Get FunctionalScene
        FunctionalScene functionalScene = Game.getInstance( ).getFunctionalScene( );
        
        return element.getXImage( )+element.getWImage( ) < 0 || 
                element.getXImage( ) > functionalScene.getBackgroundWidth() ||
                element.getYImage( )+element.getHImage( ) < 0 &&
        element.getYImage( ) > GUI.WINDOW_HEIGHT;
        
    }
    
    
    /**
     * Does not take into account scroll, just scene dimensions
     * @param rectangle
     * @return
     */
    private static boolean isElementCompletelyInsideViewPort ( FunctionalElement element ){
     // Get FunctionalScene
        FunctionalScene functionalScene = Game.getInstance( ).getFunctionalScene( );
        int offsetX = functionalScene.getOffsetX( );
        
        return element.getXImage( )+element.getWImage( ) <= offsetX+GUI.WINDOW_WIDTH && 
                element.getXImage( ) >= offsetX &&
                element.getYImage( )+element.getHImage( ) <= GUI.WINDOW_HEIGHT &&
                element.getYImage( ) >= 0;
                
    }

    /**
     * Does not take into account scroll, just scene dimensions
     * @param rectangle
     * @return
     */
    private static boolean isElementIntersectsViewPort ( FunctionalElement element ){
     // Get FunctionalScene
        FunctionalScene functionalScene = Game.getInstance( ).getFunctionalScene( );
        int offsetX = functionalScene.getOffsetX( );
        
        int rightLimit = offsetX+GUI.WINDOW_WIDTH;
        int leftLimit = offsetX;
        int topLimit = 0;
        int bottomLimit = GUI.WINDOW_HEIGHT;
        
        return isElementCompletelyInsideViewPort ( element ) ||
            ( rightLimit>=element.getXImage( ) && rightLimit<=element.getXImage( )+element.getWImage( )) ||
                ( leftLimit>=element.getXImage( ) && leftLimit<=element.getXImage( )+element.getWImage( ) ) ||
                ( topLimit>=element.getYImage( ) && topLimit<=element.getYImage( )+element.getHImage( )) ||
                ( bottomLimit>=element.getYImage( ) && bottomLimit<=element.getYImage( )+element.getHImage( ) );
                
    }

    
    private static boolean isValidRectangleGridPosition ( GridPosition candidate, es.eucm.eadventure.common.data.chapter.Rectangle rectangle ){
        // Fetch fs
        FunctionalScene fScene = Game.getInstance( ).getFunctionalScene( );

        // If it's an active area, search for the FunctionalActiveArea in FScene 
        if ( rectangle instanceof ActiveArea ){
            ActiveArea activeArea = (ActiveArea)rectangle;
            
            FunctionalActiveArea fActiveArea = null;
            for (FunctionalActiveArea fA: fScene.getActiveAreas( )){
                if (fA.getActiveArea( ).getId( ).equals( activeArea.getId( ) )){
                    fActiveArea = fA;
                    break;
                }
            }
            
            // Check as an element, not a rectangle
            return isValidElementGridPosition ( candidate, fActiveArea );
        }
        
        // It it's an exit, just check this object is the exit returned by fScene in that position
        // viewport check should be done as well
        else if ( rectangle instanceof Exit ){
            // View port
            Polygon viewPort = new Polygon();
            viewPort.addPoint( fScene.getOffsetX( ), 0 );
            viewPort.addPoint( fScene.getOffsetX( ) + GUI.WINDOW_WIDTH, 0 );
            viewPort.addPoint( fScene.getOffsetX( ) + GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT );
            viewPort.addPoint( fScene.getOffsetX( ), GUI.WINDOW_HEIGHT );

            
            return fScene.getExitInside( candidate.getX( ), candidate.getY( ) ) == rectangle;/* &&
            viewPort.contains( candidate.getX( ), candidate.getY( ) );*/
        }
        return false;
    }
    
    
    private static GridPosition getRectangleGridPosition ( es.eucm.eadventure.common.data.chapter.Rectangle rectangle,
            String type){
        // Start w/ elementCenter
        List<GridPosition> candidates = new ArrayList<GridPosition>();
        candidates.add( getRectangleCenter( rectangle, type ) );
        
        // FunctionalScene
        FunctionalScene fs = Game.getInstance( ).getFunctionalScene( );
        int offsetX = fs.getOffsetX( );
        int h4=rectangle.getHeight( )/4;
        
        //Add intersections with viewPort 
        candidates.add( new GridPosition(fs.getScene( ).getId( ), 5, rectangle.getY( )+5+h4, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), 5, rectangle.getY( )+5+2*h4, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), 5, rectangle.getY( )+5+3*h4, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), 5, rectangle.getY( )+5, type,"" ) );
        
        candidates.add( new GridPosition(fs.getScene( ).getId( ), GUI.WINDOW_WIDTH-5, rectangle.getY( )+5+h4, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), GUI.WINDOW_WIDTH-5, rectangle.getY( )+5+2*h4, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), GUI.WINDOW_WIDTH-5, rectangle.getY( )+5+3*h4, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), GUI.WINDOW_WIDTH-5, rectangle.getY( )+5, type,"" ) );

        candidates.add( new GridPosition(fs.getScene( ).getId( ), rectangle.getX()+5-offsetX, GUI.WINDOW_HEIGHT-5, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), rectangle.getX()+rectangle.getWidth( )-5-offsetX, 5, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), rectangle.getX()+rectangle.getWidth( )-5-offsetX, GUI.WINDOW_HEIGHT-5, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), rectangle.getX()+5-offsetX, 5, type,"" ) );
        
        // Add also right and left points
        candidates.add( new GridPosition(fs.getScene( ).getId( ), rectangle.getX( )+5-offsetX, rectangle.getY( )+5+h4, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), rectangle.getX( )+5-offsetX, rectangle.getY( )+5+2*h4, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), rectangle.getX( )+5-offsetX, rectangle.getY( )+5+3*h4, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), rectangle.getX( )+5-offsetX, rectangle.getY( )+5, type,"" ) );

        candidates.add( new GridPosition(fs.getScene( ).getId( ), rectangle.getX( )+rectangle.getWidth( )-5-offsetX, rectangle.getY( )+5+h4, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), rectangle.getX( )+rectangle.getWidth( )-5-offsetX, rectangle.getY( )+5+2*h4, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), rectangle.getX( )+rectangle.getWidth( )-5-offsetX, rectangle.getY( )+5+3*h4, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), rectangle.getX( )+rectangle.getWidth( )-5-offsetX, rectangle.getY( )+5, type,"" ) );
        
        // Add also vertex if it's a polygon
        float factor = 0.2F;
        if (!rectangle.isRectangular( )){
            GridPosition center = candidates.get( 0 );
            for (Point p: rectangle.getPoints( )){
                // Move vertex a bit towards polygon's center. 
                int x = Math.round( p.x + (center.getX( )-p.x)*factor );
                int y = Math.round( p.y + (center.getY( )-p.y)*factor );
                candidates.add( new GridPosition(fs.getScene( ).getId( ), x, y, type, "") );
            }
        }
        int tries = 0;
        Random r = new Random();
        
        // Now iterate until gridPosition is valid
        // a position is valid only if it can be assured that it's the last element in the rendering
        // queue. Besides, the candidate position should be within the view port (scene offset must
        // be considered)
        
        while ( !isValidRectangleGridPosition(candidates.get( 0 ), rectangle ) ){
            // pop discarded candidate
            candidates.remove( 0 );
            tries++;
            
            // If no more candidates, then:
            // 1) push more candidates if no tries limit is reached
            // 2) return null to indicate that this element cannot be reached by keyboard
            if ( candidates.size( ) == 0 ){
                if ( tries<MAX_TRIES ){
                    // Generate random position within element
                    int x = r.nextInt( rectangle.getWidth( ) )+rectangle.getX( );
                    int y = r.nextInt( rectangle.getHeight( ) )+rectangle.getY( );
                    candidates.add( new GridPosition(fs.getScene( ).getId( ), x, y, type, "") );
                } else {
                    return null;
                }
            }
        }
        
        return candidates.get( 0 );
    }
    
    private static boolean isValidElementGridPosition ( GridPosition candidate, FunctionalElement element ){
     // FunctionalScene
        FunctionalScene fs = Game.getInstance( ).getFunctionalScene( );
        
        // View port
        Polygon viewPort = new Polygon();
        viewPort.addPoint( fs.getOffsetX( ), 0 );
        viewPort.addPoint( fs.getOffsetX( ) + GUI.WINDOW_WIDTH, 0 );
        viewPort.addPoint( fs.getOffsetX( ) + GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT );
        viewPort.addPoint( fs.getOffsetX( ), GUI.WINDOW_HEIGHT );

        // a position is valid only if it can be assured that it's the last element in the rendering
        // queue. Besides, the candidate position should be within the view port (scene offset must
        // be considered)
        return fs.getElementInside( candidate.getX( ), candidate.getY( ), null )==element &&
                fs.getExitInside( candidate.getX( ), candidate.getY( ) )==null;/* &&
                viewPort.contains( candidate.getX( ), candidate.getY( ));*/
    }
    
    private static GridPosition getElementGridPosition (  FunctionalElement element, String type  ){
        // Start w/ elementCenter
        List<GridPosition> candidates = new ArrayList<GridPosition>();
        candidates.add( getElementCenter( element, type ) );
        int tries = 0;
        Random r = new Random();
        // FunctionalScene
        FunctionalScene fs = Game.getInstance( ).getFunctionalScene( );
        int offsetX = fs.getOffsetX( );
        
        // Add also right and left points
        int h4=element.getHImage( )/4;
        
        candidates.add( new GridPosition(fs.getScene( ).getId( ), 5, element.getYImage( )+5+h4, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), 5, element.getYImage( )+5+2*h4, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), 5, element.getYImage( )+5+3*h4, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), 5, element.getYImage( )+5, type,"" ) );
        
        candidates.add( new GridPosition(fs.getScene( ).getId( ), GUI.WINDOW_WIDTH-5, element.getYImage( )+5+h4, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), GUI.WINDOW_WIDTH-5, element.getYImage( )+5+2*h4, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), GUI.WINDOW_WIDTH-5, element.getYImage( )+5+3*h4, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), GUI.WINDOW_WIDTH-5, element.getYImage( )+5, type,"" ) );
        
        candidates.add( new GridPosition(fs.getScene( ).getId( ), element.getXImage( )+5-offsetX, element.getYImage( )+5+h4, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), element.getXImage( )+5-offsetX, element.getYImage( )+5+2*h4, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), element.getXImage( )+5-offsetX, element.getYImage( )+5+3*h4, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), element.getXImage( )+5-offsetX, element.getYImage( )+5, type,"" ) );
        
        candidates.add( new GridPosition(fs.getScene( ).getId( ), element.getXImage( )+element.getWImage( )-offsetX-5, element.getYImage( )+5+h4, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), element.getXImage( )+element.getWImage( )-offsetX-5, element.getYImage( )+5+2*h4, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), element.getXImage( )+element.getWImage( )-offsetX-5, element.getYImage( )+5+3*h4, type,"" ) );
        candidates.add( new GridPosition(fs.getScene( ).getId( ), element.getXImage( )+element.getWImage( )-offsetX-5, element.getYImage( )+5, type,"" ) );
       
        
        // Now iterate until gridPosition is valid
        // a position is valid only if it can be assured that it's the last element in the rendering
        // queue. Besides, the candidate position should be within the view port (scene offset must
        // be considered)
        
        while ( !isValidElementGridPosition ( candidates.get( 0 ), element )){
            // pop discarded candidate
            candidates.remove( 0 );
            tries++;
            
            // If no more candidates, then:
            // 1) push more candidates if no tries limit is reached
            // 2) return null to indicate that this element cannot be reached by keyboard
            if ( candidates.size( ) == 0 ){
                if ( tries<MAX_TRIES ){
                    // Generate random position within element
                    int x = r.nextInt( element.getWImage( ) )+element.getXImage( )-offsetX;
                    int y = r.nextInt( element.getHImage( ) )+element.getYImage( )-offsetX;
                    candidates.add( new GridPosition(fs.getScene( ).getId( ), x, y, type, "") );
                } else {
                    return null;
                }
            }
        }
        
        return candidates.get( 0 );
    }
    
    private static GridPosition getElementCenter( FunctionalElement element, String type ){
        String sceneId = Game.getInstance( ).getFunctionalScene( ).getScene( ).getId( );
        
        int eX = element.getXImage( )-Game.getInstance( ).getFunctionalScene( ).getOffsetX( );
        int eY = element.getYImage( );
        int eW = element.getWImage( );
        int eH = element.getHImage( );
        
        return new GridPosition(sceneId,eX+eW/2,eY+eH/2,type,"");
    }
    
    /**
     * Determines the center of a "rectangle" (exit/active area).
     * @param rectangle
     * @return
     */
    private static GridPosition getRectangleCenter( es.eucm.eadventure.common.data.chapter.Rectangle rectangle,
            String type){
        GridPosition center=null;
        
        int offsetX=Game.getInstance( ).getFunctionalScene( ).getOffsetX( );
        String sceneId = Game.getInstance( ).getFunctionalScene( ).getScene( ).getId( );
        if ( rectangle.isRectangular( ) ){
            int rX = rectangle.getX( )-offsetX;//-offsetX+GUI.getInstance( ).getFrame( ).getLocationOnScreen( ).x;
            int rY = rectangle.getY( );//+GUI.getInstance( ).getFrame( ).getLocationOnScreen( ).y;
            int rW = rectangle.getWidth( );
            int rH = rectangle.getHeight( );

            center = new GridPosition(sceneId, rX+rW/2, rY+rH/2, type, "");
        } else {
            int mX = 0, mY=0;
            for (Point p:rectangle.getPoints( )){
                mX+= p.x-offsetX;
                mY+= p.y ;
            }
            mX/=rectangle.getPoints( ).size( );
            mY/=rectangle.getPoints( ).size( );
            
            center = new GridPosition(sceneId, mX, mY, type, "");
        }
        
        return center;
            
    }
    
    /*******************************************************************
     *  PEDRO!!!
     * @param idScene
     * @return
     * Funci�n que devuelve las posiciones de los objetos dentro de la escena.
     *******************************************************************/
    private static List<GridPosition> oldGrid; //Pedro - Gradiant
    
    // OLD METHOD
    public static List<GridPosition> getGrillScene( String idScene){ //Como parametro le vamos a pasar el idScene
        
        List<GridPosition> GrillScene = new ArrayList<GridPosition>( );
        
        int index=0;
        
        for( int j = 0; j < oldGrid.size( ); j++ ) {    
            if (oldGrid.get( j).getIdScene( ).equals(idScene)){
                GrillScene.add( index, oldGrid.get( j ) );
                index++;
            }
        }
        
        FunctionalScene functionalScene = Game.getInstance( ).getFunctionalScene( );
        
        if (functionalScene!=null) {
            for (int k=0; k < functionalScene.getItems( ).size( );k++){
                for (int j=0; j<GrillScene.size( );j++){
                    if (GrillScene.get( j ).getIdElement( ).equals( functionalScene.getItems( ).get( k ).getItem( ).getId( ) )){
                        GrillScene.get( j ).setY((int)functionalScene.getItems( ).get( k ).getY( ),-(int)(functionalScene.getItems( ).get( k ).getHeight( )*functionalScene.getItems( ).get( k ).getScale( )));
                    }
                }
            }
            for (int k=0; k < functionalScene.getNPCs( ).size( );k++){
                for (int j=0; j<GrillScene.size( );j++){
                    if (GrillScene.get( j ).getIdElement( ).equals( functionalScene.getNPCs( ).get( k ).getElement( ).getId( ))){
                        GrillScene.get( j ).setY((int)functionalScene.getNPCs( ).get( k ).getX( ),-(int)(functionalScene.getNPCs( ).get( k ).getHeight( )*functionalScene.getNPCs( ).get( k ).getScale( )));
                    }
                }
            }
        }
        return GrillScene;
        
    }
    
}
