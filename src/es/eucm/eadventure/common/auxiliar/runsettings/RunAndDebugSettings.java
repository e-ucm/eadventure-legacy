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

package es.eucm.eadventure.common.auxiliar.runsettings;

import java.awt.GraphicsDevice;
import java.awt.Rectangle;

/**
 * This class is used to transmit information from editor to engine when games are run from the editor.
 * It stores settings that are only accessible in the editor context:
 * Options selected by the user on the "debug options menu", only for debugging. These options are stored
 * in DebugSettings objects
 * 
 *      PaintGrid: Enables/Disables the grid, which is useful to improve elements' location on screen
 *      
 *      PaintHotspots: Enables/disables rendering of the "hot spots", which are the points used by the
 *      keyboard navigation algorithm to navigate throughout interactive elements. Hot spots usually 
 *      correspond to the center of the interactive element, although it may vary to avoid overlapping.
 *      
 *      PaintBoundingAreas: Enables/disables rendering the bounding rectangles of the interactive elements.
 *      
 * (Settings calculated automatically by the editor. Apply in run and debug modes):
 *      GraphicsDevice: the graphics device (i.e. screen) where the game should be rendered. It is used to improve
 *      authoring experience on multiscreen environments, as editor and engine can be displayed on different screens.
 *      If null, the engine will use the default device.
 *      
 *      PreferredBounds: indicates preferred location and size of the game engine, if any. Used by the system to remember
 *      last position of the game window in case the user moves/it or resizes it. If null, the engine will calculate coordinates.
 *      
 *      ComponentListener: used to record changes in size and location of the game window, so the editor can track them
 * @author Javier Torrente
 * @see DebugSettings
 */
public class RunAndDebugSettings extends DebugSettings{

    private boolean debugMode;
    
    private GraphicsDevice graphicsDevice;
    
    private Rectangle preferredBounds;
    
    private GameWindowBoundsListener listener;
    
    /**
     * Constructor for "run" mode. Sets debugMode automatically to false.
     */
    public RunAndDebugSettings( GraphicsDevice graphicsDevice, Rectangle preferredBounds, GameWindowBoundsListener listener){
        this(false,false,false, graphicsDevice, preferredBounds, listener);
        this.debugMode = false;
    }
    
    /**
     * Constructor for "debug" mode. Sets debugMode automatically to true.
     */
    public RunAndDebugSettings( boolean paintGrid, boolean paintHotSpots, boolean paintBoundingAreas, GraphicsDevice graphicsDevice, Rectangle preferredBounds, 
            GameWindowBoundsListener listener) {

        super( paintGrid, paintHotSpots, paintBoundingAreas);
        this.debugMode = true;
        this.graphicsDevice = graphicsDevice;
        this.preferredBounds = preferredBounds;
        this.listener = listener;
    }

    
    
    public GraphicsDevice getGraphicsDevice( ) {
    
        return graphicsDevice;
    }

    
    public void setGraphicsDevice( GraphicsDevice graphicsDevice ) {
    
        this.graphicsDevice = graphicsDevice;
    }

    
    public Rectangle getPreferredBounds( ) {
    
        return preferredBounds;
    }

    
    public void setPreferredBounds( Rectangle preferredBounds ) {
    
        this.preferredBounds = preferredBounds;
    }

    
    public boolean isDebugMode( ) {
    
        return debugMode;
    }

    
    public GameWindowBoundsListener getListener( ) {
    
        return listener;
    }

    
    public void setListener( GameWindowBoundsListener listener ) {
    
        this.listener = listener;
    }
    
    
    
}
