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
package es.eucm.eadventure.editor.gui.otherpanels.bookpanels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.book.ArrowsPositionPanel;

/**
 * This class represents the panel in which we can define the position for the
 * arrows of the book.
 * 
 * @author Ángel S.
 * 
 */
public class BookArrowPositionPreviewPanel extends BookPreviewPanel {

    private static final long serialVersionUID = 1L;

    /**
     * To know if we have any arrows selected
     */
    private boolean selectedPrevious = false,
            selectedNext = false;

    private ArrowsPositionPanel aPanel;

    private MouseArrowsListener mouseListener = new MouseArrowsListener( this );

    public BookArrowPositionPreviewPanel( BookDataControl dControl ) {

        super( dControl );
        /*if ( image != null ){
            Dimension d = new Dimension( image.getWidth( null ), image.getHeight( null ) );
            this.setPreferredSize( d );
            this.setMaximumSize( d );
        }*/
        this.addMouseListener( mouseListener );
        this.addMouseMotionListener( mouseListener );
    }

    public void setArrowsPositionPanel( ArrowsPositionPanel aPanel ) {

        this.aPanel = aPanel;
    }

    @Override
    public void paint( Graphics g ) {

        // Background
        this.paintBackground( g );

        // Arrows
        paintArrows( g );

        // Square for arrows
        g.setColor( new Color( 1.0f, 0.0f, 0.0f, 0.5f ) );
        if( arrowLeftNormal != null )
            g.drawRect( getAbsoluteX( previousPagePoint.x ), getAbsoluteY( previousPagePoint.y ), getAbsoluteWidth( arrowLeftNormal.getWidth( null ) ), getAbsoluteHeight( arrowLeftNormal.getHeight( null ) ) );

        if( arrowRightNormal != null ) {
            g.drawRect( getAbsoluteX( nextPagePoint.x ), getAbsoluteY( nextPagePoint.y ), getAbsoluteWidth( arrowRightNormal.getWidth( null ) ), getAbsoluteHeight( arrowRightNormal.getHeight( null ) ) );
        }

        // Rectangles if an arrow is selected
        g.setColor( new Color( 0.0f, 1.0f, 0.0f, 0.5f ) );
        if( selectedPrevious ) {
            g.fillRect( getAbsoluteX( previousPagePoint.x ), getAbsoluteY( previousPagePoint.y ), getAbsoluteWidth( arrowLeftNormal.getWidth( null ) ), getAbsoluteHeight( arrowLeftNormal.getHeight( null ) ) );
        }
        else if( selectedNext ) {
            g.fillRect( getAbsoluteX( nextPagePoint.x ), getAbsoluteY( nextPagePoint.y ), getAbsoluteWidth( arrowRightNormal.getWidth( null ) ), getAbsoluteHeight( arrowRightNormal.getHeight( null ) ) );
        }
    }

    public void setPreviousPagePosition( int x, int y ) {

        if( x < 0 )
            previousPagePoint.x = 0;
        else if( x > image.getWidth( null ) - arrowLeftNormal.getWidth( null ) ) {
            previousPagePoint.x = image.getWidth( null ) - arrowLeftNormal.getWidth( null );
        }
        else {
            previousPagePoint.x = x;
        }

        if( y < 0 ) {
            previousPagePoint.y = 0;
        }
        else if( y > image.getHeight( null ) - arrowLeftNormal.getHeight( null ) ) {
            previousPagePoint.y = image.getHeight( null ) - arrowLeftNormal.getHeight( null );
        }
        else
            previousPagePoint.y = y;
    }

    public void setNextPagePosition( int x, int y ) {

        if( x < 0 )
            nextPagePoint.x = 0;
        else if( x > image.getWidth( null ) - arrowRightNormal.getWidth( null ) ) {
            nextPagePoint.x = image.getWidth( null ) - arrowRightNormal.getWidth( null );
        }
        else {
            nextPagePoint.x = x;
        }

        if( y < 0 ) {
            nextPagePoint.y = 0;
        }
        else if( y > image.getHeight( null ) - arrowRightNormal.getHeight( null ) ) {
            nextPagePoint.y = image.getHeight( null ) - arrowRightNormal.getHeight( null );
        }
        else
            nextPagePoint.y = y;
    }

    private class MouseArrowsListener extends MouseAdapter {

        private BookArrowPositionPreviewPanel bookPreview;

        private Point oldNextPoint, oldPreviousPoint;

        // Margin of the mouse relative to the image
        int marginX, marginY;

        public MouseArrowsListener( BookArrowPositionPreviewPanel bPreview ) {

            bookPreview = bPreview;
        }

        @Override
        public void mousePressed( MouseEvent e ) {

            int x = e.getX( );
            int y = e.getY( );
            if( isInPreviousPage( x, y ) ) {
                selectedPrevious = true;
                // With this, we avoid selecting both arrows
                selectedNext = false;
                // Calculate margins
                marginX = getRelativeX( x ) - previousPagePoint.x;
                marginY = getRelativeY( y ) - previousPagePoint.y;
            }
            else if( isInNextPage( x, y ) ) {
                selectedNext = true;
                selectedPrevious = false;
                // Calculate margins
                marginX = getRelativeX( x ) - nextPagePoint.x;
                marginY = getRelativeY( y ) - nextPagePoint.y;
            }
            else {
                selectedPrevious = false;
                selectedNext = false;
            }
            if( selectedPrevious || selectedNext ) {
                oldNextPoint = (Point) nextPagePoint.clone( );
                oldPreviousPoint = (Point) previousPagePoint.clone( );
                bookPreview.repaint( );
            }
        }

        @Override
        public void mouseDragged( MouseEvent e ) {

            if( selectedPrevious ) {
                bookPreview.setPreviousPagePosition( getRelativeX( e.getX( ) ) - marginX, getRelativeY( e.getY( ) ) - marginY );
            }
            else if( selectedNext ) {
                bookPreview.setNextPagePosition( getRelativeX( e.getX( ) ) - marginX, getRelativeY( e.getY( ) ) - marginY );
            }
            bookPreview.repaint( );
            aPanel.updateSpinners( false );
            aPanel.setAddTool( true );
        }

        @Override
        public void mouseReleased( MouseEvent e ) {

            // If we have one arrow selected, we release it.
            if( selectedPrevious || selectedNext ) {
                if( selectedPrevious )
                    bookPreview.setPreviousPagePosition( previousPagePoint, oldPreviousPoint );
                if( selectedNext )
                    bookPreview.setNextPagePosition( nextPagePoint, oldNextPoint );

                selectedPrevious = false;
                selectedNext = false;

                bookPreview.repaint( );
                bookPreview.dispatchEvent( new ActionEvent( bookPreview, ActionEvent.ACTION_PERFORMED, "mouseReleased" ) );
                aPanel.updateSpinners( false );
                aPanel.setAddTool( true );
            }
        }

    }

}
