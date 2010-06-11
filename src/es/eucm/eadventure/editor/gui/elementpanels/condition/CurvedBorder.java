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
package es.eucm.eadventure.editor.gui.elementpanels.condition;

//CurvedBorder.java
//A custom border that draws round rectangle borders.
//
import java.awt.*;

import javax.swing.border.*;

public class CurvedBorder extends AbstractBorder {

    private Color wallColor = Color.gray;

    private Color bgColor = new Color( 255, 255, 20, 100 );

    private int sinkLevel = 10;

    private AlphaComposite alphaComposite;

    /**
     * @param alphaComposite
     *            the alphaComposite to set
     */
    public void setAlphaComposite( AlphaComposite alphaComposite ) {

        this.alphaComposite = alphaComposite;
    }

    public CurvedBorder( ) {

    }

    public CurvedBorder( int sinkLevel ) {

        this.sinkLevel = sinkLevel;
    }

    public CurvedBorder( Color wall ) {

        this.wallColor = wall;
    }

    public CurvedBorder( int sinkLevel, Color wall ) {

        this.sinkLevel = sinkLevel;
        this.wallColor = wall;
    }

    public CurvedBorder( int sinkLevel, Color wall, Color bg ) {

        this.sinkLevel = sinkLevel;
        this.wallColor = wall;
        this.bgColor = bg;
    }

    @Override
    public void paintBorder( Component c, Graphics g, int x, int y, int w, int h ) {

        if( alphaComposite != null ) {
            ( (Graphics2D) g ).setComposite( alphaComposite );
        }

        //  Paint a tall wall around the component
        //for (int i = 0; i < sinkLevel; i++) {
        /*g.drawRoundRect(x+i, y+i, w-i-1, h-i-1, sinkLevel-i, sinkLevel);
        g.drawRoundRect(x+i, y+i, w-i-1, h-i-1, sinkLevel, sinkLevel-i);
        g.drawRoundRect(x+i, y, w-i-1, h-1, sinkLevel-i, sinkLevel);
        g.drawRoundRect(x, y+i, w-1, h-i-1, sinkLevel, sinkLevel-i);*/
        if( bgColor != null ) {
            g.setColor( bgColor );
            g.fillRoundRect( x, y, w - 1, h - 1, sinkLevel, sinkLevel );
        }
        g.setColor( getWallColor( ) );
        g.drawRoundRect( x, y, w - 1, h - 1, sinkLevel, sinkLevel );
        ( (Graphics2D) g ).setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 1.0f ) );
        //}
    }

    @Override
    public Insets getBorderInsets( Component c ) {

        return new Insets( 0, 0, 0, 0 );
    }

    @Override
    public Insets getBorderInsets( Component c, Insets i ) {

        i.left = i.right = i.bottom = i.top = 10;
        return i;
    }

    @Override
    public boolean isBorderOpaque( ) {

        return true;
    }

    public int getSinkLevel( ) {

        return sinkLevel;
    }

    public Color getWallColor( ) {

        return wallColor;
    }
}
