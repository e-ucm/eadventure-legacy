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
package es.eucm.eadventure.editor.gui.auxiliar.components;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.util.Hashtable;

import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TC;

/**
 * Panel to display preview colored text.
 * 
 * @author Bruno Torijano Bueno
 */
public class TextPreviewPanel extends JPanel {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Color of front of the text.
     */
    private Color textFrontColor;

    /**
     * Color of the border of the text.
     */
    private Color textBorderColor;

    private boolean speechBubble;

    private Color bubbleBkgColor;

    private Color bubbleBorderColor;

    /**
     * Constructor.
     * 
     * @param textFrontColor
     *            Front text color
     * @param textBorderColor
     *            Border text color
     */
    public TextPreviewPanel( Color textFrontColor, Color textBorderColor, boolean speechBubble, Color bubbleBkgColor, Color bubbleBorderColor ) {

        this.textFrontColor = textFrontColor;
        this.textBorderColor = textBorderColor;
        this.speechBubble = speechBubble;
        this.bubbleBkgColor = bubbleBkgColor;
        this.bubbleBorderColor = bubbleBorderColor;
    }

    /**
     * Updates the front color of the text and repaints the panel.
     * 
     * @param textFrontColor
     *            Front text color
     */
    public void setTextFrontColor( Color textFrontColor ) {

        this.textFrontColor = textFrontColor;
        repaint( );
    }

    /**
     * Updates the border color of the text and repaints the panel.
     * 
     * @param textBorderColor
     *            Border text color
     */
    public void setTextBorderColor( Color textBorderColor ) {

        this.textBorderColor = textBorderColor;
        repaint( );
    }

    public void setBubbleBkgColor( Color bubbleBkgColor ) {

        this.bubbleBkgColor = bubbleBkgColor;
        repaint( );
    }

    public void setBubbleBorderColor( Color bubbleBorderColor ) {

        this.bubbleBorderColor = bubbleBorderColor;
        repaint( );
    }

    @Override
    public void paint( Graphics g ) {

        super.paint( g );

        // Get the text to display
        String displayText = TC.get( "GeneralText.PreviewText" );

        // Calculate the position to paint
        g.setFont( g.getFont( ).deriveFont( 18.0f ) );
        Hashtable attributes = new Hashtable();
        attributes.put(TextAttribute.WIDTH, TextAttribute.WIDTH_SEMI_EXTENDED);
        g.setFont( g.getFont( ).deriveFont( attributes ) );

        FontMetrics fontMetrics = g.getFontMetrics( );

        int x = ( getWidth( ) / 2 ) - ( fontMetrics.stringWidth( displayText ) / 2 );
        int y = ( getHeight( ) / 2 ) + ( fontMetrics.getAscent( ) / 2 );

        int textBlockHeight = fontMetrics.getHeight( ) - fontMetrics.getLeading( );

        if( speechBubble ) {
            int maxWidth = fontMetrics.stringWidth( displayText );
            g.setColor( bubbleBkgColor );
            g.fillRoundRect( getWidth( ) / 2 - maxWidth / 2 - 5, y - textBlockHeight - 5, maxWidth + 10, textBlockHeight + 10, 20, 20 );
            g.setColor( bubbleBorderColor );
            g.drawRoundRect( getWidth( ) / 2 - maxWidth / 2 - 5, y - textBlockHeight - 5, maxWidth + 10, textBlockHeight + 10, 20, 20 );
            g.setColor( bubbleBkgColor );
        }

        // Draw the border of the text
        g.setColor( textBorderColor );

        g.drawString( displayText, x - 1, y - 1 );
        g.drawString( displayText, x - 1, y + 1 );
        g.drawString( displayText, x + 1, y - 1 );
        g.drawString( displayText, x + 1, y + 1 );

        // Draw the text
        g.setColor( textFrontColor );
        g.drawString( displayText, x, y );
    }

    public void setShowsSpeechBubbles( boolean selected ) {

        this.speechBubble = selected;
        repaint( );
    }
}
