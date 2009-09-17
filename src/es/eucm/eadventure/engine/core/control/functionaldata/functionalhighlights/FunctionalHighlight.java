/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fern‡ndez-Manj—n, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
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
