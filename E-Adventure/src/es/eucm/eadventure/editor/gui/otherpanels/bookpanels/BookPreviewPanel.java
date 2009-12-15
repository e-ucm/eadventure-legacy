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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import es.eucm.eadventure.common.auxiliar.SpecialAssetPaths;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.gui.auxiliar.ImageTransformer;
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.ImagePanel;

/**
 * This class contains all the basic information about the preview
 * of a book and the methods to load required images. It extends ImagePanel.
 * The background is stored in the image atrribute of ImagePanel
 * 
 * @author Ángel S.
 *
 */
public class BookPreviewPanel extends ImagePanel {

    protected static final long serialVersionUID = 1L;

    /**
     * Images for the arrows
     */
    protected Image arrowLeftNormal, arrowRightNormal, arrowLeftOver, arrowRightOver;
    
    /**
     * Coordinates for arrows
     */
    protected Point nextPagePoint, previousPagePoint;
    
    /**
     * Book data control
     */
    protected BookDataControl dataControl;
    
    /**
     * Constructor.
     * @param backgroundPath Image for background.
     */
    public BookPreviewPanel( String backgroundPath ){
        super( backgroundPath );
    }
    
    /**
     * Constructor.
     * @param dControl Data control of the book.
     */
    public BookPreviewPanel( BookDataControl dControl ){
        this.dataControl = dControl;
        // Initialize points to avoid NullPointer exception
        previousPagePoint = new Point( 0, 0 );
        nextPagePoint = new Point( 0, 0 );
        this.loadImages( dataControl );
        if ( image != null ){
            this.setMinimumSize( new Dimension( image.getWidth( null ), image.getHeight( null ) ) );
        }
    }
    
    /**
     * Setter for the left arrow position
     * @param x Coordinate x
     * @param y Coordiante y
     */
    public void setPreviousPagePosition( int x, int y ){
        if ( x < 0 )
            previousPagePoint.x = 0;
        else if ( x > image.getWidth( null ) - arrowLeftNormal.getWidth( null ) ){
            previousPagePoint.x = image.getWidth( null ) - arrowLeftNormal.getWidth( null );
        }
        else{
            previousPagePoint.x = x;
        }
        
        if ( y < 0 ){
            previousPagePoint.y = 0; 
        }
        else if ( y > image.getHeight( null ) - arrowLeftNormal.getHeight( null ) ){
            previousPagePoint.y = image.getHeight( null ) - arrowLeftNormal.getHeight( null );
        }
        else
            previousPagePoint.y = y;
        
        dataControl.setPreviousPagePosition( previousPagePoint );
    }
    
    /**
     * Setter for the right arrow position
     * @param x Coordinate x
     * @param y Coordiante y
     */
    public void setNextPagePosition( int x, int y ){
        if ( x < 0 )
            nextPagePoint.x = 0;
        else if ( x > image.getWidth( null ) - arrowRightNormal.getWidth( null ) ){
            nextPagePoint.x = image.getWidth( null ) - arrowRightNormal.getWidth( null );
        }
        else{
            nextPagePoint.x = x;
        }
        
        if ( y < 0 ){
            nextPagePoint.y = 0; 
        }
        else if ( y > image.getHeight( null ) - arrowRightNormal.getHeight( null ) ){
            nextPagePoint.y = image.getHeight( null ) - arrowRightNormal.getHeight( null );
        }
        else
            nextPagePoint.y = y;
        
        dataControl.setNextPagePosition( nextPagePoint );
    }
    
    /**
     * 
     * @return Left arrow position.
     */
    public Point getPreviousPagePosition( ){ return previousPagePoint;  }
    
    /**
     * 
     * @return Right arrow position.
     */
    public Point getNextPagePosition( ){ return nextPagePoint; }
    
    /**
     * Load the required images for the book
     * @param dControl Controller with the required information
     */
    public void loadImages( BookDataControl dControl ) {
        image = AssetsController.getImage( dControl.getPreviewImage( ) );
        arrowLeftNormal = AssetsController.getImage( dControl.getArrowImagePath( BookDataControl.ARROW_LEFT, BookDataControl.ARROW_NORMAL ) );
        arrowRightNormal = AssetsController.getImage( dControl.getArrowImagePath( BookDataControl.ARROW_RIGHT, BookDataControl.ARROW_NORMAL ) );
        arrowLeftOver = AssetsController.getImage( dControl.getArrowImagePath( BookDataControl.ARROW_LEFT, BookDataControl.ARROW_OVER ) );
        arrowRightOver = AssetsController.getImage( dControl.getArrowImagePath( BookDataControl.ARROW_RIGHT, BookDataControl.ARROW_OVER ) );
        
        // We check the arrowLeftNormal
        if ( arrowLeftNormal == null ){
            // We look for first in the over arrow
            if ( arrowLeftOver != null ){
                
                arrowLeftNormal = arrowLeftOver;
            }
            else if ( arrowRightNormal != null ){
                
                arrowLeftNormal = ImageTransformer.getInstance( ).getScaledImage( arrowRightNormal, -1.0f, 1.0f );
            }
            else if ( arrowRightOver != null ){
                
                arrowLeftNormal = ImageTransformer.getInstance( ).getScaledImage( arrowRightOver, -1.0f, 1.0f );
            }
            //  Else, we load defaults left arrows
            else{
                
                arrowLeftNormal = AssetsController.getImage( SpecialAssetPaths.ASSET_DEFAULT_ARROW_NORMAL );
                arrowLeftOver = AssetsController.getImage( SpecialAssetPaths.ASSET_DEFAULT_ARROW_OVER );
            }
        }
        
        // We check the arrowRightNormal
        if ( arrowRightNormal == null ){
            //We look for first in the over arrow
            if ( arrowRightOver != null ){
                
                arrowRightNormal = arrowRightOver;
            }
            // Else, we use the mirrored left arrow
            else {
                
                arrowRightNormal = ImageTransformer.getInstance( ).getScaledImage( arrowLeftNormal, -1.0f, 1.0f );
            }
        }
        
        // We check the arrowLeftNormal
        if ( arrowLeftOver == null ){
            
            arrowLeftOver = arrowLeftNormal;
        }
        
        // We check the arrowRightNormal
        if ( arrowRightOver == null ){
            
            arrowRightOver = ImageTransformer.getInstance( ).getScaledImage( arrowLeftOver, -1.0f, 1.0f );;
        }

        if ( arrowLeftNormal != null && arrowRightNormal != null ){
            if (!setArrowsPosition( dControl ))
                setDefaultArrowsPosition( );
         
        }
  
    }
    
    private boolean setArrowsPosition( BookDataControl dControl ) {
        Point npPosition = dControl.getNextPagePosition( );
        if ( npPosition != null ){
            this.setNextPagePosition( npPosition.x, npPosition.y );
        }
        else
            return false;
        
        Point ppPosition = dControl.getPreviousPagePosition( );
        if ( ppPosition != null ){
            this.setPreviousPagePosition( ppPosition.x, ppPosition.y );
        }
        else
            return false;
        
        return true;
        
    }

    protected boolean isInPreviousPage( int x, int y ){
        int xLeftEnd = getAbsoluteX( previousPagePoint.x ) + getAbsoluteWidth( arrowLeftNormal.getWidth( null ) );
        int yLeftEnd = getAbsoluteY( previousPagePoint.y ) + getAbsoluteHeight( arrowLeftNormal.getHeight( null ) );
        return ( getAbsoluteX( previousPagePoint.x )< x ) && ( x < xLeftEnd ) && ( getAbsoluteY( previousPagePoint.y ) < y ) && ( y < yLeftEnd );
    }
    
    protected boolean isInNextPage( int x, int y ){
        int xRightEnd = getAbsoluteX( nextPagePoint.x ) + getAbsoluteWidth( arrowRightNormal.getWidth( null ) );
        int yRightEnd = getAbsoluteY( nextPagePoint.y ) + getAbsoluteHeight( arrowRightNormal.getHeight( null ) );
        return ( getAbsoluteX( nextPagePoint.x ) < x ) && ( x < xRightEnd ) && ( getAbsoluteY( nextPagePoint.y ) < y ) && ( y < yRightEnd );
    }
    
    public void setDefaultArrowsPosition( ){
        int margin = 20;
        this.setPreviousPagePosition( margin, image.getHeight( null ) - arrowLeftNormal.getHeight( null ) - margin );
        this.setNextPagePosition( image.getWidth( null ) - arrowRightNormal.getWidth( null ) - margin, image.getHeight( null ) - arrowRightNormal.getHeight( null ) - margin );
    }
    
    @Override
    public void paint( Graphics g ){
        if ( isImageLoaded( )) {
            // Paint the background
            paintBackground( g );
            // Paint the arrows
            paintArrows( g );
        }
        
    }
    
    protected void paintArrows( Graphics g ){
        paintPreviousPageArrow( g );
        paintNextPageArrow( g );

    }
    
    protected void paintPreviousPageArrow( Graphics g ){
        if ( arrowLeftNormal != null ){
            g.drawImage( arrowLeftNormal, getAbsoluteX( previousPagePoint.x ), getAbsoluteY( previousPagePoint.y ), getAbsoluteWidth( arrowLeftNormal.getWidth( null ) ), getAbsoluteHeight( arrowLeftNormal.getHeight( null ) ), null );
        }
    }
    
    protected void paintNextPageArrow( Graphics g ){
        if ( arrowRightNormal != null ){
            g.drawImage( arrowRightNormal, getAbsoluteX( nextPagePoint.x ), getAbsoluteY( nextPagePoint.y ), getAbsoluteWidth( arrowRightNormal.getWidth( null ) ), getAbsoluteHeight( arrowRightNormal.getHeight( null ) ), null );
        }
    }
    
    protected void paintBackground( Graphics g ){
        super.paint( g );
    }

}
