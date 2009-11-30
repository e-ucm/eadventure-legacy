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
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.gui.editdialogs.ChangeArrowsPositionDialog;

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
    private boolean selectedPrevious = false, selectedNext = false;
    
    private MouseArrowsListener mouseListener = new MouseArrowsListener( this );
    
    private ChangeArrowsPositionDialog parent;
    
    public BookArrowPositionPreview( BookDataControl dControl, ChangeArrowsPositionDialog parent ){
        super( dControl );
        this.parent = parent;
        if ( image != null ){
            Dimension d = new Dimension( image.getWidth( null ), image.getHeight( null ) );
            this.setPreferredSize( d );
        }
        this.addMouseListener( mouseListener );
        this.addMouseMotionListener( mouseListener );
    }
    
    @Override
    public void paint( Graphics g ) {
        // Background
        this.paintBackground( g );
        
        // Arrows
        paintArrows( g );
        
        // Square for arrows
        g.setColor( new Color( 1.0f, 0.0f, 0.0f, 0.5f ) );
        if ( arrowLeftNormal != null )
            g.drawRect( getAbsoluteX( previousPagePoint.x ), getAbsoluteY( previousPagePoint.y ), getAbsoluteWidth( arrowLeftNormal.getWidth( null ) ), getAbsoluteHeight( arrowLeftNormal.getHeight( null )) );
        
        if ( arrowRightNormal != null ){            
            g.drawRect( getAbsoluteX( nextPagePoint.x ), getAbsoluteY( nextPagePoint.y ), getAbsoluteWidth( arrowRightNormal.getWidth( null ) ), getAbsoluteHeight( arrowRightNormal.getHeight( null )) );
        }
        
        // Rectangles if an arrow is selected
        g.setColor( new Color( 0.0f, 1.0f, 0.0f, 0.5f ) );
        if ( selectedPrevious ){
            g.fillRect( getAbsoluteX( previousPagePoint.x ), getAbsoluteY( previousPagePoint.y ), getAbsoluteWidth( arrowLeftNormal.getWidth( null ) ), getAbsoluteHeight( arrowLeftNormal.getHeight( null )) );
        }
        else if ( selectedNext ){
            g.fillRect( getAbsoluteX( nextPagePoint.x ), getAbsoluteY( nextPagePoint.y ), getAbsoluteWidth( arrowRightNormal.getWidth( null ) ), getAbsoluteHeight( arrowRightNormal.getHeight( null )) );
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
        public void mousePressed( MouseEvent e ) {
                int x = e.getX( );
                int y = e.getY( );
                if ( isInPreviousPage( x, y ) ){
                    selectedPrevious = true;
                    // With this, we avoid selecting both arrows
                    selectedNext = false;
                    // Calculate margins
                    marginX = x - previousPagePoint.x;
                    marginY = y - previousPagePoint.y;
                }
                else if ( isInNextPage( x, y ) ){
                    selectedNext = true;
                    selectedPrevious = false;
                    // Calculate margins
                    marginX = x - nextPagePoint.x;
                    marginY = y - nextPagePoint.y;
                }
                else {
                    selectedPrevious = false;
                    selectedNext = false;
                }
                if ( selectedPrevious || selectedNext ){
                    bookPreview.repaint( );
                }
        }

        @Override
        public void mouseDragged( MouseEvent e ) {            
            if ( selectedPrevious ){
                bookPreview.setPreviousPagePosition( e.getX( ) - marginX, e.getY( ) - marginY );
            }
            else if ( selectedNext ){
                bookPreview.setNextPagePosition( e.getX( ) - marginX, e.getY( ) - marginY );
            }
            bookPreview.repaint( );
            parent.updateSpinners( );
        }
        
        @Override
        public void mouseReleased( MouseEvent e){
            // If we have one arrow selected, we release it.
            if ( selectedPrevious || selectedNext ){
                selectedPrevious = false;
                selectedNext = false;
                bookPreview.repaint( );
                bookPreview.dispatchEvent( new ActionEvent( bookPreview, ActionEvent.ACTION_PERFORMED, "mouseReleased" ) );
                parent.updateSpinners( );
            }
        }
        
    }

}
