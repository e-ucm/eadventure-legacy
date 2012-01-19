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

        if (animated)
            calculateDisplacements(image.getWidth( null ), image.getHeight( null ));

        if (oldImage == null || oldImage != image) {
            BufferedImage temp = GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage(image.getWidth( null ), image.getHeight( null ), Transparency.BITMASK );
            temp.getGraphics( ).drawImage( image, 0, 0, null );
           for (int i = 0 ; i < image.getWidth( null ); i++) {
                for (int j = 0; j < image.getHeight( null ); j++) {
                    temp.setRGB( i, j, temp.getRGB( i, j ) | 0x00ff0000 );
                }
            }
            oldImage = image;
            newImage = temp;
        }
//        BufferedImage temp = GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage( Math.round( image.getWidth( null ) * scale ),  Math.round( image.getHeight( null ) * scale ), Transparency.BITMASK );
//        ((Graphics2D) temp.getGraphics( )).drawImage( image, AffineTransform.getScaleInstance( scale, scale ), null );
//        return temp;

        return newImage.getScaledInstance( (int)(image.getWidth(null) * scale), (int)(image.getHeight( null ) * scale), Image.SCALE_SMOOTH );
    }

}
