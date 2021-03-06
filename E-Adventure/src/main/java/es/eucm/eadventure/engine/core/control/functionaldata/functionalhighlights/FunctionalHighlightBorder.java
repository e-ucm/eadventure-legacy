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
package es.eucm.eadventure.engine.core.control.functionaldata.functionalhighlights;

import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import es.eucm.eadventure.engine.core.gui.GUI;

public class FunctionalHighlightBorder extends FunctionalHighlight {
    
    public FunctionalHighlightBorder(boolean animated) {
       this.animated = animated;
       this.time = System.currentTimeMillis( );
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
                    temp.setRGB( i, j, temp.getRGB( i, j ) & 0xff000000 );
                }
            }
            
            temp.getGraphics( ).drawImage( image, 5, 5, image.getWidth( null ) - 10, image.getHeight( null ) - 10,  null );
            oldImage = image;
            newImage = temp;
        }
//        BufferedImage temp = GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage( Math.round( image.getWidth( null ) * scale ),  Math.round( image.getHeight( null ) * scale ), Transparency.BITMASK );
//        ((Graphics2D) temp.getGraphics( )).drawImage( image, AffineTransform.getScaleInstance( scale, scale ), null );
//        return temp;

        return newImage.getScaledInstance( (int)(image.getWidth(null) * scale), (int)(image.getHeight( null ) * scale), Image.SCALE_SMOOTH );
    }

}
