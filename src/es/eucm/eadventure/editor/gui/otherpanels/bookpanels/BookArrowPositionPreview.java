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
package es.eucm.eadventure.editor.gui.otherpanels.bookpanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;

/**
 * This class represents the panel in which we can define
 * the position for the arrows of the book.
 * @author Ángel S.
 *
 */
public class BookArrowPositionPreview extends BookPreviewPanel {

    private static final long serialVersionUID = 1L;
    
    /**
     * To know if we have any arrows selected
     */
    private boolean selectedLeft = false, selectedRight = false;
    
    private MouseArrowsListener mouseListener = new MouseArrowsListener( this );
    
    public BookArrowPositionPreview( BookDataControl dControl ){
        loadImages( dControl );
        if ( background != null ){
            Dimension d = new Dimension( background.getWidth( null ), background.getHeight( null ) );
            this.setPreferredSize( d );
        }
        this.addMouseListener( mouseListener );
    }
    
    @Override
    public void paint( Graphics g ) {
        super.paint( g );
        if( background != null )
            g.drawImage( background, 0, 0, background.getWidth( null ), background.getHeight( null ), null );
        
        if ( currentArrowLeft != null && currentArrowRight != null )
            g.drawImage( currentArrowLeft, xLeft, yLeft, currentArrowLeft.getWidth( null ), currentArrowLeft.getHeight( null ), null );                
        
        if ( currentArrowRight != null )            
            g.drawImage( currentArrowRight, xRight, yRight, currentArrowLeft.getWidth( null ), currentArrowLeft.getHeight( null ), null );
        
        g.setColor( new Color( 1.0f, 0.0f, 0.0f, 0.5f ) );
        if ( selectedLeft ){
            g.drawRect( xLeft, yLeft, currentArrowLeft.getWidth( null ), currentArrowLeft.getHeight( null ) );
        }
        else if ( selectedRight ){
            g.drawRect( xRight, yRight, currentArrowRight.getWidth( null ), currentArrowRight.getHeight( null ) );
        }
    }
    
    private class MouseArrowsListener extends MouseAdapter {
        
        BookArrowPositionPreview bookPreview;
        
        // Margin of the mouse relative to the image
        int marginX, marginY;
        
        public MouseArrowsListener( BookArrowPositionPreview bPreview ){
            bookPreview = bPreview;
        }
        @Override
        public void mouseClicked( MouseEvent e ) {
            // If we have one arrow selected, we release it.
            if ( selectedLeft || selectedRight ){
                selectedLeft = false;
                selectedRight = false;
                bookPreview.removeMouseMotionListener( this );
                bookPreview.repaint( );
            }
            // Else, we check if we just selected one
            else {
                int x = e.getX( );
                int y = e.getY( );
                if ( isInPreviousPage( x, y ) ){
                    selectedLeft = true;
                    // With this, we avoid selecting both arrows
                    selectedRight = false;
                    // Calculate margins
                    marginX = x - xLeft;
                    marginY = y - yLeft;
                }
                else if ( isInNextPage( x, y ) ){
                    selectedRight = true;
                    selectedLeft = false;
                    // Calculate margins
                    marginX = x - xRight;
                    marginY = y - yRight;
                }
                else {
                    selectedLeft = false;
                    selectedRight = false;
                }
                if ( selectedLeft || selectedRight ){
                    bookPreview.addMouseMotionListener( this );
                    bookPreview.repaint( );
                }
            }
        }

        @Override
        public void mouseMoved( MouseEvent e ) {
            if ( selectedLeft ){
                bookPreview.setLeftArrowPosition( e.getX( ) - marginX, e.getY( ) - marginY );
            }
            else if ( selectedRight ){
                bookPreview.setRightArrowPosition( e.getX( ) - marginX, e.getY( ) - marginY );
            }
            bookPreview.repaint( );
        }
        
    }

}
