/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
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
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package es.eucm.eadventure.common.auxiliar;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

/**
 * This class contains several utilities to help determining which graphics device should be used to locate windows.
 * It is used by MainWindow.sizeAndLocationSetup() and Controller.buildRunAndDebugSettings( boolean debug ), which is invoked before the 
 * game engine is run from the editor
 * 
 * All the utilities are implemented in the form of static methods
 * @author Javier Torrente
 *
 */
public class MultiscreenTools {

    /**
     * Checks if the given rectangle (bounds) is visible on the screen.
     * The visibility can be calculated using two different functions, depending on the value of param fully:
     *      If fully is "true", then this method will check if the whole rectangle is contained on a single screen.
     *      This ensures that the rectangle is fully visible
     *      
     *      If fully is "false", then this method checks that at least a part of the rectangle is visible. It does so
     *      by intersecting the rectangle with all the graphics devices.
     *   
     * This method is equivalent to getDeviceContainer(Rectangle bounds, boolean fully)!=null
     * @param bounds    The rectangle which must be checked
     * @param fully     True for full visibility check, false for partial check
     * @return          True if the rectangle is considered to be visible, false otherwise
     */
    public static boolean isRectangleVisible(Rectangle bounds, boolean fully){
        return getDeviceContainer(bounds, fully)!=null;
    }
    
    /**
     * Calculates the GraphicsDevice that contains the given rectangle (bounds).
     * Two calculation modes are supported:
     *      If param "fully" is "true", then the device which bounds fully contain the given rectangle is returned, if any.
     *      
     *      If param "fully" is "false", then the first device which bounds intersects (at least partially) the given rectangle is returned, if any
     * @param bounds    The rectangle which must be checked
     * @param fully     True for full check, false for partial check
     * @return          The device that contains or intersects the given rectangle, null if there is none.
     */
    public static GraphicsDevice getDeviceContainer(Rectangle bounds, boolean fully){
     // Try to determine which device these bounds belong to
        // If there are more than one device, pick one that is not the "default", as this one may be occupied with Editor's main window
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment( );
        GraphicsDevice device = null;
        for (GraphicsDevice d: environment.getScreenDevices( )){
            // If prefBounds are contained on screen bounds, pick this device
            if (fully && d.getDefaultConfiguration( ).getBounds( ).contains( bounds )){
                device = d; break;
            } else if (fully && d.getDefaultConfiguration( ).getBounds( ).intersects( bounds )){
                device = d; break;
            }
        }   
        return device;
    }
    
    /**
     * Returns the GraphicsDevice that contains the less amount of the given rectangle.
     * The intersection between the given rectangle and all the devices' bounds is performed. Then, the area of the intersection rectangles
     * is calculated (width*height). The device producing the minimum intersection area is returned.
     * @param windowBounds  The rectangle to be checked.
     * @return  The device that contains the minimum portion of the given rectangle
     */
    public static GraphicsDevice getDeviceWithMinimumIntersection(Rectangle windowBounds){
        int intersectionArea=Integer.MAX_VALUE;
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment( );
        GraphicsDevice device = null;
        for (GraphicsDevice d: environment.getScreenDevices( )){
            Rectangle intersection = d.getDefaultConfiguration( ).getBounds( ).intersection( windowBounds );
            int area = intersection.width*intersection.height;
            if (area<intersectionArea){
                intersectionArea = area;
                device = d;
            }
        }
        return device;
    }
    
    /**
     * Prints information about all the graphics devices using System.out.println
     */
    public static void printDevices(){
        for (GraphicsDevice gd:GraphicsEnvironment.getLocalGraphicsEnvironment( ).getScreenDevices( )){
            Rectangle deviceBounds = gd.getDefaultConfiguration( ).getBounds( );
            int deviceX = deviceBounds.x;
            int deviceY = deviceBounds.y;
            int deviceW = deviceBounds.width;
            int deviceH = deviceBounds.height;
            System.out.println("[DEVICE "+gd+"] X="+deviceX+" Y="+deviceY+" W="+deviceW+" H="+deviceH );
        }
    }
}
