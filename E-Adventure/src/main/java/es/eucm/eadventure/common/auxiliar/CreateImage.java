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
package es.eucm.eadventure.common.auxiliar;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Utility for creating virtual images. That is, images that are not stored
 * physically on the hard disk.
 * 
 * @author Javier
 * 
 */
public class CreateImage {

    public static final int CENTER = 0;

    public static final int LEFT = 1;

    public static final int RIGHT = 2;

    public static final int TOP = 1;

    public static final int BOTTOM = 2;

    public static Image createImage( int width, int height, String text ) {

        return createImage( width, height, text, new Font( "Arial", Font.PLAIN, 12 ) );
    }

    public static Image createImage( int width, int height, String text, Font font ) {

        return createImage( width, height, Color.BLACK, 5, Color.LIGHT_GRAY, text, Color.WHITE, CENTER, CENTER, font );
    }

    public static Image createImage( int width, int height, Color backgroundColor, int borderThickness, Color borderColor, String text, Color textColor, int alignX, int alignY, Font font ) {

        // Create basic image & get graphics object
        BufferedImage im = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );
        Graphics2D gr = (Graphics2D) im.getGraphics( );

        // Fill background
        gr.setColor( backgroundColor );
        gr.fillRect( 0, 0, width, height );

        // Fill border
        gr.setColor( borderColor );
        for( int i = 0; i < borderThickness; i++ )
            gr.drawRect( i, i, width - 2 * i, height - 2 * i );

        // Write text
        gr.setColor( textColor );
        gr.setFont( font );

        FontMetrics metrics = gr.getFontMetrics( );
        Rectangle2D rect = metrics.getStringBounds( text, gr );
        double textWidth = rect.getWidth( );
        double textHeight = rect.getHeight( );

        // Calculate x & y according to alignment
        int x = 0;
        int y = 0;
        if( alignX == CENTER ) {
            x = (int) ( ( width - textWidth ) / 2.0 );
        }
        else if( alignX == LEFT ) {
            if( width > 5 )
                x = 5;
            else
                x = 0;
        }
        else if( alignX == RIGHT ) {
            if( width > textWidth + 5 )
                x = (int) ( width - textWidth - 5.0 );
            else
                x = 0;
        }

        if( alignY == CENTER ) {
            y = (int) ( ( height - textHeight ) / 2.0 );
        }
        else if( alignY == TOP ) {
            if( height > 5 )
                y = 5;
            else
                y = 0;
        }
        else if( alignY == BOTTOM ) {
            if( height > textHeight + 5 )
                y = (int) ( height - textHeight - 5.0 );
            else
                y = 0;
        }

        gr.drawString( text, x, y );
        gr.dispose( );

        return im;
    }

   

}
