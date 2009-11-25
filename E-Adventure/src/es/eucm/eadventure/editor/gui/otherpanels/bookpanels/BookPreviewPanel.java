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

import java.awt.Image;
import java.awt.Point;

import javax.swing.JPanel;

import es.eucm.eadventure.common.auxiliar.SpecialAssetPaths;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.gui.auxiliar.ImageTransformer;

/**
 * This class contains all the basic information about the preview
 * of a book and the methods to load required images.
 * 
 * BookPreviewPanel extends this class. 
 * @author Ángel S.
 *
 */
public abstract class BookPreviewPanel extends JPanel {

    protected static final long serialVersionUID = 1L;

    protected Image background, arrowLeftNormal, arrowRightNormal, arrowLeftOver, arrowRightOver;
    
    /**
     * Current state for arrows
     */
    protected Image currentArrowLeft, currentArrowRight;
    
    /**
     * Coordinates for arrows
     */
    protected int xLeft, xRight, yLeft, yRight;
    
    /**
     * Setter for the left arrow position
     * @param x Coordinate x
     * @param y Coordiante y
     */
    public void setLeftArrowPosition( int x, int y ){
        if ( x < 0 )
            xLeft = 0;
        else if ( x > background.getWidth( null ) - arrowLeftNormal.getWidth( null ) ){
            xLeft = background.getWidth( null ) - arrowLeftNormal.getWidth( null );
        }
        else{
            xLeft = x;
        }
        
        if ( y < 0 ){
            yLeft = 0; 
        }
        else if ( y > background.getHeight( null ) - arrowLeftNormal.getHeight( null ) ){
            yLeft = background.getHeight( null ) - arrowLeftNormal.getHeight( null );
        }
        else
            yLeft = y;
    }
    
    /**
     * Setter for the right arrow position
     * @param x Coordinate x
     * @param y Coordiante y
     */
    public void setRightArrowPosition( int x, int y ){
        if ( x < 0 )
            xRight = 0;
        else if ( x > background.getWidth( null ) - arrowRightNormal.getWidth( null ) ){
            xRight = background.getWidth( null ) - arrowRightNormal.getWidth( null );
        }
        else{
            xRight = x;
        }
        
        if ( y < 0 ){
            yRight = 0; 
        }
        else if ( y > background.getHeight( null ) - arrowRightNormal.getHeight( null ) ){
            yRight = background.getHeight( null ) - arrowRightNormal.getHeight( null );
        }
        else
            yRight = y;
    }
    
    /**
     * 
     * @return Left arrow position.
     */
    public Point getLeftArrowPosition( ){ return new Point( xLeft, yLeft ); }
    
    /**
     * 
     * @return Right arrow position.
     */
    public Point getRightArrowPosition( ){ return new Point( xRight, yRight ); }
    
    /**
     * Load the required images for the book
     * @param dControl Controller with the required information
     */
    protected void loadImages( BookDataControl dControl ) {
        background = AssetsController.getImage( dControl.getPreviewImage( ) );
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
            
            arrowRightOver = ImageTransformer.getInstance( ).getScaledImage( arrowLeftOver, -1.0f, 1.0f );
        }

        if ( arrowLeftNormal != null && arrowRightNormal != null ){
            setDefaultArrowsPosition( );
            
            currentArrowLeft = arrowLeftNormal;
            currentArrowRight = arrowRightNormal;
        }
  
    }
    
    protected boolean isInPreviousPage( int x, int y ){
        int xLeftEnd = xLeft + arrowLeftNormal.getWidth( null );
        int yLeftEnd = yLeft + arrowLeftNormal.getHeight( null );
        return ( xLeft < x ) && ( x < xLeftEnd ) && ( yLeft < y ) && ( y < yLeftEnd );
    }
    
    protected boolean isInNextPage( int x, int y ){
        int xRightEnd = xRight + arrowRightNormal.getWidth( null );
        int yRightEnd = yRight + arrowRightNormal.getHeight( null );
        return ( xRight < x ) && ( x < xRightEnd ) && ( yRight < y ) && ( y < yRightEnd );
    }
    
    public void setDefaultArrowsPosition( ){
        int margin = 20;
        xLeft = margin;
        yLeft = background.getHeight( null ) - arrowLeftNormal.getHeight( null ) - margin;
        xRight = background.getWidth( null ) - arrowRightNormal.getWidth( null ) - margin;
        yRight = background.getHeight( null ) - arrowRightNormal.getHeight( null ) - margin;
    }

}
