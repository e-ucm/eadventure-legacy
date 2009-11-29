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

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import es.eucm.eadventure.engine.core.gui.GUI;


public class FunctionalHighlightBlue extends FunctionalHighlight {
    
    public FunctionalHighlightBlue(boolean animated) {
       this.animated = animated;
    }
    
    @Override
    public Image getHighlightedImage( Image image ) {
        
        if (animated)
            calculateDisplacements(image.getWidth( null ), image.getHeight( null ));
        
        if (oldImage == null || oldImage != image) {
            BufferedImage temp = GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage(image.getWidth( null ), image.getHeight( null ), Transparency.BITMASK );
            temp.getGraphics( ).drawImage( image, 0, 0, null );
            
            for (int i = 0 ; i < image.getWidth( null ); i++) {
                for (int j = 0; j < image.getHeight( null ); j++) {
                    temp.setRGB( i, j, temp.getRGB( i, j ) | 0x000000ff );
                }
            }
            oldImage = image;
            newImage = temp;
        } 
        BufferedImage temp = GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage( Math.round( image.getWidth( null ) * scale ),  Math.round( image.getHeight( null ) * scale ), Transparency.BITMASK );
        ((Graphics2D) temp.getGraphics( )).drawImage( image, AffineTransform.getScaleInstance( scale, scale ), null );
        return temp;
//        return newImage.getScaledInstance( (int)(image.getWidth(null) * scale), (int)(image.getHeight( null ) * scale), Image.SCALE_SMOOTH );
    }
    
}
