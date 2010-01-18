/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
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
package es.eucm.eadventure.common.auxiliar;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Class with all the common functions for transform
 * images.
 * 
 * Implemented as a Singleton 
 * @author Ángel S.
 *
 */
public class ImageTransformer {

    private static ImageTransformer imageTransformer;
    
    private ImageTransformer(){
        
    }
    
    public static ImageTransformer getInstance(){
        if ( imageTransformer == null ){
            imageTransformer = new ImageTransformer();
        }
        return imageTransformer;
    }
    
    /**
     * Return an image scaled with specified parameters
     * (Method copied from MultimediaManager)
     * @param image Image to scale
     * @param x Scale for axis x
     * @param y Scale for axis y
     * @return An image scaled.
     */
    public Image getScaledImage( Image image, float x, float y ) {

        Image newImage = null;

        if( image != null ) {

            // set up the transform
            AffineTransform transform = new AffineTransform( );
            transform.scale( x, y );
            transform.translate( ( x - 1 ) * image.getWidth( null ) / 2, ( y - 1 ) * image.getHeight( null ) / 2 );

            // create a transparent (not translucent) image
            newImage = new BufferedImage( image.getWidth( null ), image.getHeight( null ), Transparency.BITMASK );

            // draw the transformed image
            Graphics2D g = (Graphics2D) newImage.getGraphics( );

            g.drawImage( image, transform, null );
            g.dispose( );
        }

        return newImage;
    }
}
