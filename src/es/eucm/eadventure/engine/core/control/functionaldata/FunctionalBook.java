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
package es.eucm.eadventure.engine.core.control.functionaldata;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import es.eucm.eadventure.common.auxiliar.ImageTransformer;
import es.eucm.eadventure.common.auxiliar.SpecialAssetPaths;
import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.data.chapter.resources.Asset;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

/**
 * This class manages the eGame "bookscenes".
 */

public abstract class FunctionalBook {

    /**
     * Position of the upper left corner of the next page button
     */
    protected Point nextPage;

    /**
     * Position of the upper left corner of the previous page button
     */
    protected Point previousPage;

    /**
     * Dimensions for next page arrow
     */
    protected Dimension nextPageDimension;

    /**
     * Dimensions of the previous page arrow
     */
    protected Dimension previousPageDimension;

    /**
     * Book with the information
     */
    protected Book book;

    /**
     * Current page.
     */
    protected int currentPage;

    /**
     * Image for background
     */
    protected Image background;

    /**
     * Current images for the arrows
     */
    protected BufferedImage currentArrowLeft,
            currentArrowRight;

    /**
     * All images for the arrows
     */
    protected BufferedImage arrowLeftNormal, arrowLeftOver,
            arrowRightNormal, arrowRightOver;

    /**
     * Number of pages.
     */
    protected int numPages;

    protected FunctionalBook( Book b ) {

        this.book = b;
        // Create necessaries resources to display the book
        Resources r = createResourcesBlock( book );
        // Load images and positions
        loadImages( r );

        // Load arrows position
        this.previousPage = book.getPreviousPagePoint( );
        this.nextPage = book.getNextPagePoint( );

        if( previousPage == null || nextPage == null ) {
            this.setDefaultArrowsPosition( );
        }

    }

    /**
     * Returns whether the mouse pointer is in the "next page" button
     * 
     * @param x
     *            the horizontal position of the mouse pointer
     * @param y
     *            the vertical position of the mouse pointer
     * @return true if the mouse is in the "next page" button, false otherwise
     */
    public boolean isInNextPage( int x, int y ) {

        if( ( nextPage.getX( ) < x ) && ( x < nextPage.getX( ) + nextPageDimension.getWidth( ) ) && ( nextPage.getY( ) < y ) && ( y < nextPage.getY( ) + nextPageDimension.getHeight( ) ) ) {
            boolean isInside = false;

            int mousex = x - nextPage.x;
            int mousey = y - nextPage.y;

            try {

                int alpha = currentArrowRight.getRGB( mousex, mousey ) >>> 24;
                isInside = alpha > 128;
            }
            catch( Exception e ) {
                isInside = false;
            }

            return isInside;
        }
        else
            return false;
    }

    /**
     * Returns wheter the mouse pointer is in the "previous page" button
     * 
     * @param x
     *            the horizontal position of the mouse pointer
     * @param y
     *            the vertical position of the mouse pointer
     * @return true if the mouse is in the "previous page" button, false
     *         otherwise
     */
    public boolean isInPreviousPage( int x, int y ) {

        if( ( previousPage.x < x ) && ( x < previousPage.x + previousPageDimension.getWidth( ) ) && ( previousPage.y < y ) && ( y < previousPage.y + previousPageDimension.height ) ) {
            boolean isInside = false;

            int mousex = x - previousPage.x;
            int mousey = y - previousPage.y;

            try {

                int alpha = currentArrowLeft.getRGB( mousex, mousey ) >>> 24;
                isInside = alpha > 128;
            }
            catch( Exception e ) {
                isInside = false;
            }

            return isInside;
        }
        else
            return false;
    }

    /**
     * Returns the book's data (text and images)
     * 
     * @return the book's data
     */
    public Book getBook( ) {

        return book;
    }

    /**
     * Returns whether the book is in its last page
     * 
     * @return true if the book is in its last page, false otherwise
     */
    public abstract boolean isInLastPage( );

    /**
     * Returns whether the book is in its first page
     * 
     * @return true if the book is in its first page, false otherwise
     */
    public abstract boolean isInFirstPage( );

    /**
     * Changes the current page to the next one
     */
    public abstract void nextPage( );

    /**
     * Changes the current page to the previous one
     */
    public abstract void previousPage( );

    /**
     * Load the necessaries images for displaying the book. This method is
     * pretty much the same as "loadImages" from BookPagePreviewPanel.
     */
    protected void loadImages( Resources r ) {

        background = MultimediaManager.getInstance( ).loadImageFromZip( r.getAssetPath( Book.RESOURCE_TYPE_BACKGROUND ), MultimediaManager.IMAGE_SCENE );

        try {
            arrowLeftNormal = this.toBufferedImage( MultimediaManager.getInstance( ).loadImageFromZip( r.getAssetPath( Book.RESOURCE_TYPE_ARROW_LEFT_NORMAL ), MultimediaManager.IMAGE_SCENE ) );
        }
        catch( Exception e ) {
            arrowLeftNormal = null;
        }

        try {
            arrowRightNormal = this.toBufferedImage( MultimediaManager.getInstance( ).loadImageFromZip( r.getAssetPath( Book.RESOURCE_TYPE_ARROW_RIGHT_NORMAL ), MultimediaManager.IMAGE_SCENE ) );
        }
        catch( Exception e ) {
            arrowRightNormal = null;
        }

        try {
            arrowLeftOver = this.toBufferedImage( MultimediaManager.getInstance( ).loadImageFromZip( r.getAssetPath( Book.RESOURCE_TYPE_ARROW_LEFT_OVER ), MultimediaManager.IMAGE_SCENE ) );
        }
        catch( Exception e ) {
            arrowLeftOver = null;
        }

        try {
            arrowRightOver = this.toBufferedImage( MultimediaManager.getInstance( ).loadImageFromZip( r.getAssetPath( Book.RESOURCE_TYPE_ARROW_RIGHT_OVER ), MultimediaManager.IMAGE_SCENE ) );
        }
        catch( Exception e ) {
            arrowRightOver = null;
        }

        // We check the arrowLeftNormal
        if( arrowLeftNormal == null ) {
            // We look for first in the over arrow
            if( arrowLeftOver != null ) {

                arrowLeftNormal = arrowLeftOver;
            }
            else if( arrowRightNormal != null ) {

                arrowLeftNormal = (BufferedImage) ImageTransformer.getInstance( ).getScaledImage( arrowRightNormal, -1.0f, 1.0f );
            }
            else if( arrowRightOver != null ) {

                arrowLeftNormal = (BufferedImage) ImageTransformer.getInstance( ).getScaledImage( arrowRightOver, -1.0f, 1.0f );
            }
            //  Else, we load defaults left arrows
            else {
                loadDefaultArrows( );
            }
        }

        // We check the arrowRightNormal
        if( arrowRightNormal == null ) {
            //We look for first in the over arrow
            if( arrowRightOver != null ) {

                arrowRightNormal = arrowRightOver;
            }
            // Else, we use the mirrored left arrow
            else {

                arrowRightNormal = (BufferedImage) ImageTransformer.getInstance( ).getScaledImage( arrowLeftNormal, -1.0f, 1.0f );
            }
        }

        // We check the arrowLeftNormal
        if( arrowLeftOver == null ) {

            arrowLeftOver = arrowLeftNormal;
        }

        // We check the arrowRightOver
        if( arrowRightOver == null ) {

            arrowRightOver = (BufferedImage) ImageTransformer.getInstance( ).getScaledImage( arrowLeftOver, -1.0f, 1.0f );
        }

        previousPageDimension = new Dimension( arrowLeftNormal.getWidth( null ), arrowLeftNormal.getHeight( null ) );
        nextPageDimension = new Dimension( arrowRightNormal.getWidth( null ), arrowRightNormal.getHeight( null ) );

        currentArrowLeft = arrowLeftNormal;
        currentArrowRight = arrowRightNormal;
    }

    private void loadDefaultArrows( ) {

        try {
            arrowLeftNormal = (BufferedImage) MultimediaManager.getInstance( ).loadImageFromZip( SpecialAssetPaths.ASSET_DEFAULT_ARROW_NORMAL, MultimediaManager.IMAGE_SCENE );
            if( arrowLeftNormal == null ) {
                arrowLeftNormal = ImageIO.read( getClass( ).getResourceAsStream( "/es/eucm/eadventure/engine/core/gui/images/defaultleftnormalarrow.png" ) );
            }

            arrowRightNormal = (BufferedImage) ImageTransformer.getInstance( ).getScaledImage( arrowLeftNormal, -1.0f, 1.0f );
            
            arrowLeftOver = (BufferedImage) MultimediaManager.getInstance( ).loadImageFromZip( SpecialAssetPaths.ASSET_DEFAULT_ARROW_OVER, MultimediaManager.IMAGE_SCENE );
            if ( arrowLeftOver == null ){
                arrowLeftOver = ImageIO.read( getClass( ).getResourceAsStream( "/es/eucm/eadventure/engine/core/gui/images/defaultleftoverarrow.png" ) );
            }
            
            arrowRightOver = (BufferedImage) ImageTransformer.getInstance( ).getScaledImage( arrowLeftOver, -1.0f, 1.0f );
        }
        catch( IOException e ) {
            e.printStackTrace( );
        }
    }

    private void setDefaultArrowsPosition( ) {

        int margin = 20;
        int xLeft = margin;
        int yLeft = background.getHeight( null ) - (int) previousPageDimension.getHeight( ) - margin;
        int xRight = background.getWidth( null ) - (int) nextPageDimension.getWidth( ) - margin;
        int yRight = background.getHeight( null ) - (int) nextPageDimension.getHeight( ) - margin;

        previousPage = new Point( xLeft, yLeft );
        nextPage = new Point( xRight, yRight );
    }

    /**
     * Creates the current resource block to be used
     */
    protected Resources createResourcesBlock( Book b ) {

        // Get the active resources block
        Resources newResources = null;
        for( int i = 0; i < b.getResources( ).size( ) && newResources == null; i++ )
            if( new FunctionalConditions( b.getResources( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                newResources = b.getResources( ).get( i );

        // If no resource block is available, create a default one 
        if( newResources == null ) {
            newResources = new Resources( );
            newResources.addAsset( new Asset( Book.RESOURCE_TYPE_BACKGROUND, ResourceHandler.DEFAULT_BACKGROUND ) );
        }
        return newResources;
    }

    public void draw( Graphics g ) {

        g.drawImage( background, 0, 0, background.getWidth( null ), background.getHeight( null ), null );

        if( !isInFirstPage( ) )
            g.drawImage( currentArrowLeft, previousPage.x, previousPage.y, previousPageDimension.width, previousPageDimension.height, null );

        if( !isInLastPage( ) )
            g.drawImage( currentArrowRight, nextPage.x, nextPage.y, nextPageDimension.width, nextPageDimension.height, null );
    }

    public void mouseOverPreviousPage( boolean mouseOverPreviousPage ) {

        if( !mouseOverPreviousPage )
            currentArrowLeft = arrowLeftNormal;
        else
            currentArrowLeft = arrowLeftOver;

    }

    public void mouseOverNextPage( boolean mouseOverNextPage ) {

        if( !mouseOverNextPage )
            currentArrowRight = arrowRightNormal;
        else
            currentArrowRight = arrowRightOver;

    }

    // MÉTODOS PARA DETERMINAR LAS TRANSPARENCIAS DE LAS FLECHAS
    public BufferedImage toBufferedImage( Image image ) {

        if( image instanceof BufferedImage ) {
            return (BufferedImage) image;
        }

        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon( image ).getImage( );

        // Determine if the image has transparent pixels; for this method's
        // implementation, see e661 Determining If an Image Has Transparent Pixels
        boolean hasAlpha = hasAlpha( image );

        // Create a buffered image with a format that's compatible with the screen
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment( );
        try {
            // Determine the type of transparency of the new buffered image
            int transparency = Transparency.OPAQUE;
            if( hasAlpha ) {
                transparency = Transparency.BITMASK;
            }

            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice( );
            GraphicsConfiguration gc = gs.getDefaultConfiguration( );
            bimage = gc.createCompatibleImage( image.getWidth( null ), image.getHeight( null ), transparency );
        }
        catch( HeadlessException e ) {
            // The system does not have a screen
        }

        if( bimage == null ) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            if( hasAlpha ) {
                type = BufferedImage.TYPE_INT_ARGB;
            }
            bimage = new BufferedImage( image.getWidth( null ), image.getHeight( null ), type );
        }

        // Copy image to buffered image
        Graphics g = bimage.createGraphics( );

        // Paint the image onto the buffered image
        g.drawImage( image, 0, 0, null );
        g.dispose( );

        return bimage;
    }

    // This method returns true if the specified image has transparent pixels
    public static boolean hasAlpha( Image image ) {

        // If buffered image, the color model is readily available
        if( image instanceof BufferedImage ) {
            BufferedImage bimage = (BufferedImage) image;
            return bimage.getColorModel( ).hasAlpha( );
        }

        // Use a pixel grabber to retrieve the image's color model;
        // grabbing a single pixel is usually sufficient
        PixelGrabber pg = new PixelGrabber( image, 0, 0, 1, 1, false );
        try {
            pg.grabPixels( );
        }
        catch( InterruptedException e ) {
        }

        // Get the image's color model
        ColorModel cm = pg.getColorModel( );
        return cm.hasAlpha( );
    }

}
