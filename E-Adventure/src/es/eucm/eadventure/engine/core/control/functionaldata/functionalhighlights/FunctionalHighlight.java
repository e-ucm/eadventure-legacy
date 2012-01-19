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
package es.eucm.eadventure.engine.core.control.functionaldata.functionalhighlights;

import java.awt.Image;


public abstract class FunctionalHighlight {
    
    protected boolean animated;
    
    protected long time = System.currentTimeMillis( );
    
    protected int displacementX = 0;
    
    protected int displacementY = 0;
    
    protected float scale = 1.0f;
    
    protected static final int TIME_CONST = 600;
    
    public abstract Image getHighlightedImage(Image image);
    
    protected Image oldImage;
    
    protected Image newImage;
    
    public int getDisplacementX() {
        return displacementX;
    }
    
    public int getDisplacementY() {
        return displacementY;
    }
    
    public boolean isAnimated() {
        return animated;
    }
    
    protected void calculateDisplacements(int width, int height) {
        long elapsedTime = System.currentTimeMillis( ) - time;
        float temp = ( elapsedTime % (TIME_CONST * 2) );
        if (temp < TIME_CONST / 2)
            temp = -temp; 
        else if (temp < TIME_CONST)
            temp = temp - TIME_CONST;
        else if (temp < TIME_CONST * 1.5f)
            temp = temp - TIME_CONST;
        else
            temp = TIME_CONST*2 - temp;
        scale = 1f + (temp / TIME_CONST) * 0.2f; 
        displacementY = (int) ((height - (height * scale)) / 2);
        displacementX = (int) ((width - (width * scale)) / 2);
    }
}
