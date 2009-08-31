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
package es.eucm.eadventure.common.data.chapter.effects;

/**
 * An effect that show text in an specific zone in scene
 */
public class ShowTextEffect extends AbstractEffect {

    /**
     * The text which will be showed
     */
    private String text;

    /**
     * The x position in scene
     */
    private int x;

    /**
     * The y position in scene
     */
    private int y;

    /**
     * The text front color in RGB format
     */
    private int rgbFrontColor;

    /**
     * The text border color in RGB fotrmat
     */
    private int rgbBorderColor;

    /**
     * Constructor
     * 
     * @param text
     * @param x
     * @param y
     * @param front
     * @param border
     */
    public ShowTextEffect( String text, int x, int y, int front, int border ) {

        super( );
        this.text = text;
        this.x = x;
        this.y = y;
        this.rgbFrontColor = front;
        this.rgbBorderColor = border;
    }

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
     * @return the x
     */
    public int getX( ) {

        return x;
    }

    /**
     * @return the y
     */
    public int getY( ) {

        return y;
    }

    /**
     * Sets the new text position
     * 
     * @param x
     *            New text position X coordinate
     * @param y
     *            New text position Y coordinate
     */
    public void setTextPosition( int x, int y ) {

        this.x = x;
        this.y = y;
    }

    /**
     * Return the effect type
     */
    @Override
    public int getType( ) {

        return SHOW_TEXT;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        ShowTextEffect ste = (ShowTextEffect) super.clone( );
        ste.text = ( text != null ? new String( text ) : null );
        ste.x = x;
        ste.y = y;
        ste.rgbBorderColor = rgbBorderColor;
        ste.rgbFrontColor = rgbFrontColor;
        return ste;
    }

    /**
     * @return the rgbFrontColor
     */
    public int getRgbFrontColor( ) {

        return rgbFrontColor;
    }

    /**
     * @param rgbFrontColor
     *            the rgbFrontColor to set
     */
    public void setRgbFrontColor( int rgbFrontColor ) {

        this.rgbFrontColor = rgbFrontColor;
    }

    /**
     * @return the rgbBorderColor
     */
    public int getRgbBorderColor( ) {

        return rgbBorderColor;
    }

    /**
     * @param rgbBorderColor
     *            the rgbBorderColor to set
     */
    public void setRgbBorderColor( int rgbBorderColor ) {

        this.rgbBorderColor = rgbBorderColor;
    }
}
