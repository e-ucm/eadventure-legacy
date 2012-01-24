/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
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
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package es.eucm.eadventure.engine.core.control.interaction.auxiliar;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.MouseEvent;

import es.eucm.eadventure.engine.core.gui.GUI;


public class MouseUtils {

    public static void click( int buttons ){
        Robot r;
        try {
            r = new Robot();
            r.mousePress( buttons );
            r.mouseRelease( buttons );

        }
        catch( AWTException e ) {
            e.printStackTrace();
        }
    }
    
    public static void move( int x, int y){
        move(x,y,MouseEvent.BUTTON1,true, false);
    }
    
    public static void move( int x, int y, int buttons, boolean move, boolean click){
        try {
            Robot pulsa = new Robot( );
            if (move)
                pulsa.mouseMove( x+GUI.getInstance( ).getFrame( ).getLocationOnScreen( ).x
                    , y+GUI.getInstance( ).getFrame( ).getLocationOnScreen( ).y);  
            
            if (click) {
                pulsa.mousePress( buttons );
                pulsa.mouseRelease( buttons );
            }

       } catch (AWTException e){
            
        }
    }
    
    public static void move( GridPosition point, int buttons, boolean click){
        int x = point.getX( );
        int y = point.getY( );
        
       move (x, y, buttons, true, click);
        
    }

    
}
