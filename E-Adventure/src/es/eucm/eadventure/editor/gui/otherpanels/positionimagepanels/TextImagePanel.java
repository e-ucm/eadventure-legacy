/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.editor.gui.otherpanels.positionimagepanels;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class TextImagePanel extends PositionImagePanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String text;

    /**
     * Color of front of the text.
     */
    private Color textFrontColor;

    /**
     * Color of the border of the text.
     */
    private Color textBorderColor;

    public TextImagePanel( String imagePath, String text ) {

        super( imagePath );
        this.text = text;
        this.textFrontColor = Color.white;
        this.textBorderColor = Color.yellow;
        selectedX = 0;
        selectedY = 0;

    }

    public TextImagePanel( String text, Color textFrontColor, Color textBorderColor ) {

        super( null );
        this.text = text;
        this.textFrontColor = textFrontColor;
        this.textBorderColor = textBorderColor;
        selectedX = 0;
        selectedY = 0;
    }

    //  public int getRelativeX( int x ) {
    //return (int) ( ( x - this.x ) / sizeRatio );
    //}

    // public int getRelativeY( int y ) {
    //return (int) ( ( y - this.y ) / sizeRatio );
    //}

    /**
     * @return the text
     */
    public String getText( ) {

        return text;
    }

    /**
     * @param text
     *            the text to set
     */
    public void setText( String text ) {

        this.text = text;
    }

    /**
     * @param textFrontColor
     *            the textFrontColor to set
     */
    public void setTextFrontColor( Color textFrontColor ) {

        this.textFrontColor = textFrontColor;
    }

    /**
     * @param textBorderColor
     *            the textBorderColor to set
     */
    public void setTextBorderColor( Color textBorderColor ) {

        this.textBorderColor = textBorderColor;
    }

    @Override
    public void paint( Graphics g ) {

        super.paint( g );

        // Calculate the position to paint
        g.setFont( g.getFont( ).deriveFont( 18.0f ) );
        FontMetrics fontMetrics = g.getFontMetrics( );

        if( text.length( ) > 0 ) {
            BufferedImage image = new BufferedImage( fontMetrics.stringWidth( text ), fontMetrics.getAscent( ), BufferedImage.TYPE_4BYTE_ABGR );
            //BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics g2 = image.createGraphics( );

            int x = 0;
            int y = fontMetrics.getAscent( );

            // Draw the border of the text
            g2.setColor( textBorderColor );
            g2.drawString( text, x - 1, y - 1 );
            g2.drawString( text, x - 1, y + 1 );
            g2.drawString( text, x + 1, y - 1 );
            g2.drawString( text, x + 1, y + 1 );

            // Draw the text
            g2.setColor( textFrontColor );
            g2.drawString( text, x, y );

            paintRelativeImage( g, image, selectedX, selectedY, false );
            // Draw the image
            //g.drawImage( image, posX, posY, width, height, null );
        }

    }

    protected void paintRelativeImage( Graphics g, Image image, int x, int y ) {

        // If the image was loaded
        if( isImageLoaded( ) ) {
            // Calculate the size of the image
            int width = (int) ( image.getWidth( null ) * sizeRatio );
            int height = (int) ( image.getHeight( null ) * sizeRatio );

            // Calculate the position of the image
            int posX = x - ( image.getWidth( null ) / 2 );
            int posY = y - image.getHeight( null );

            // Draw the image
            g.drawImage( image, posX, posY, width, height, null );

        }
    }

}
