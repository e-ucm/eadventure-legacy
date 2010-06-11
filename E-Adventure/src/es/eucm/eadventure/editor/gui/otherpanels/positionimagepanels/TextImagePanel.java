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
package es.eucm.eadventure.editor.gui.otherpanels.positionimagepanels;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;

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
            int desY = fontMetrics.getAscent( );
            int desX = fontMetrics.getHeight( );
            // Calculate the position of the image
            int posX=0;
            int posY=0;
            if (image!=null){
                posX = getAbsoluteX( (int) ( selectedX - ( image.getWidth( null ) * scale / 2 ) ) );
                posY = getAbsoluteY( (int) ( selectedY - image.getHeight( null ) * scale ) );
                posY += desY/2;
                posX -= (text.length( ) * desX / 2) / 2;
                // Draw the border text
                g.setColor( textBorderColor );
                g.drawString( text, posX - 1, posY - 1 );
                g.drawString( text, posX - 1, posY + 1 );
                g.drawString( text, posX + 1, posY - 1 );
                g.drawString( text, posX + 1, posY + 1 );
                // Draw the text
                g.setColor( textFrontColor );
                g.drawString( text, posX, posY);
            }
            
            paintRelativeImage( g, image, selectedX, selectedY, scale, false );
            
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
