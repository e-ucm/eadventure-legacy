/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, availabe at http://e-adventure.e-ucm.es.
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
 */
package es.eucm.eadventure.common.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JEditorPane;

import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.engine.core.gui.GUI;

public class BookEditorPane extends JEditorPane {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final int REAL_WIDTH = 800;

    private final int REAL_HEIGHT = 600;

    private BookPage currentBookPage;

    public BookEditorPane( BookPage currentBookP ) {

        this.currentBookPage = currentBookP;
        updateBounds( );
        setOpaque( false );
        //editorPane.setCaret( null );
        setEditable( false );
    }

    public void updateBounds( ) {

        setBounds( currentBookPage.getMargin( ), currentBookPage.getMarginTop( ), GUI.WINDOW_WIDTH - currentBookPage.getMargin( ) - currentBookPage.getMarginEnd( ), GUI.WINDOW_HEIGHT - currentBookPage.getMarginTop( ) - currentBookPage.getMarginBottom( ) );
    }

    /**
     * 
     * @param g
     *            Graphics
     * @param x
     *            x-left-corner-position to draw in graphics
     * @param y
     *            x-left-corner-position to draw in graphics
     * @param width
     *            real width of the component
     * @param height
     *            real height of the component
     */
    public void paint( Graphics g, int x, int y, int width, int height ) {

        if( width != 0 && height != 0 ) {

            BufferedImage b = new BufferedImage( getWidth( ), getHeight( ), BufferedImage.TYPE_INT_ARGB );
            Graphics2D g2 = (Graphics2D) b.getGraphics( );
            super.paint( g2 );
            int widthScale = Math.round( ( width * getWidth( ) ) / REAL_WIDTH );
            int heightScale = ( height * getHeight( ) ) / REAL_HEIGHT;

            g.drawImage( b.getScaledInstance( widthScale, heightScale, Image.SCALE_SMOOTH ), x, y, null );
        }
    }
}
