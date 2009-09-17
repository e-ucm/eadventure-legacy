/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fern�ndez-Manj�n, B. (directors)
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
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import es.eucm.eadventure.engine.core.gui.GUI;


public class FunctionalHighlightRed extends FunctionalHighlight {
    
    public FunctionalHighlightRed(boolean animated) {
       this.animated = animated;
       this.time = System.currentTimeMillis( );
    }
    
    @Override
    public Image getHighlightedImage( Image image ) {
        BufferedImage temp = GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage(image.getWidth( null ), image.getHeight( null ), Transparency.BITMASK );

        if (animated)
            calculateDisplacements(image.getWidth( null ), image.getHeight( null ));
        temp.getGraphics( ).drawImage( image, 0, 0, null );

        if (oldImage == null || oldImage != image) {
            for (int i = 0 ; i < image.getWidth( null ); i++) {
                for (int j = 0; j < image.getHeight( null ); j++) {
                    temp.setRGB( i, j, temp.getRGB( i, j ) | 0x00ff0000 );
                }
            }
            oldImage = image;
            newImage = temp;
        }
        return newImage.getScaledInstance( (int)(image.getWidth(null) * scale), (int)(image.getHeight( null ) * scale), Image.SCALE_SMOOTH );
    }

}
