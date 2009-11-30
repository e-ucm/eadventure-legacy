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
package es.eucm.eadventure.editor.gui.otherpanels.imagepanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.AssetsController;

/**
 * This panel holds an image inside, painted with its own aspect ratio.
 * 
 * @author Bruno Torijano Bueno
 */
public class ImagePanel extends JPanel {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Margin for the image.
     */
    protected static final int MARGIN = 20;

    /**
     * Image to show.
     */
    protected Image image;

    /**
     * X position of the image in the panel.
     */
    protected int x;

    /**
     * Y position of the image in the panel.
     */
    protected int y;

    /**
     * Width of the image.
     */
    protected int width;

    /**
     * Height of the image.
     */
    protected int height;

    /**
     * Size ratio of the image.
     */
    protected double sizeRatio;

    /**
     * Constructor.
     */
    public ImagePanel( ) {

        super( );

        // Set the image to null
        image = null;

        // Add the resize listener
        addComponentListener( new ComponentAdapter( ) {

            @Override
            public void componentResized( ComponentEvent e ) {

                calculateSize( );
            }
        } );

        // Add a label
        setLayout( new GridBagLayout( ) );
        add( new JLabel( TC.get( "ImagePanel.ImageNotAvalaible" ) ) );
    }

    /**
     * Constructor.
     * 
     * @param imagePath
     *            Path of the image
     */
    public ImagePanel( String imagePath ) {

        this( );

        // Load the image
        loadImage( imagePath );
    }

    /**
     * Removes the current image from the panel.
     */
    public void removeImage( ) {

        // Remove the image
        if( image != null )
            image.flush( );
        image = null;

        // Remove all components, and add a label
        removeAll( );
        add( new JLabel( TC.get( "ImagePanel.ImageNotAvalaible" ) ) );
        revalidate( );

        // Repaint the panel
        repaint( );
    }

    /**
     * Loads the given image in the panel.
     * 
     * @param imagePath
     *            Path of the image
     */
    public void loadImage( String imagePath ) {

        // Clear the image (if there was one)
        if( image != null )
            image.flush( );

        // Load the image and calculate the sizes
        if( imagePath != null && imagePath.length( ) > 0 )
            image = AssetsController.getImage( imagePath );
        else
            image = null;
        calculateSize( );

        // Remove all components, and add a label if the image is not loaded
        removeAll( );
        if( !isImageLoaded( ) ) {
            add( new JLabel( TC.get( "ImagePanel.ImageNotAvalaible" ) ) );
            revalidate( );
        }

        // Repaint the panel
        repaint( );
    }

    @Override
    public void paint( Graphics g ) {

        super.paint( g );

        // Paint the image
        if( isImageLoaded( ) ) {
            g.drawImage( image, x, y, width, height, this );
        }
    }

    /**
     * Returns whether the image is loaded or not.
     * 
     * @return True if the image was loaded, false otherwise
     */
    public boolean isImageLoaded( ) {

        return image != null;
    }

    /**
     * Returns the relative position in the image.
     * 
     * @param x
     *            X position of the panel
     * @return X position of the image
     */
    public int getRelativeX( int x ) {

        int relativeX = 0;

        // If the image has been loaded
        if( isImageLoaded( ) )
            relativeX = (int) ( ( x - this.x ) / sizeRatio );

        return relativeX;
    }

    /**
     * Returns the relative position in the image.
     * 
     * @param y
     *            Y position of the panel
     * @return Y position of the image
     */
    public int getRelativeY( int y ) {

        int relativeY = 0;

        // If the image has been loaded
        if( isImageLoaded( ) )
            relativeY = (int) ( ( y - this.y ) / sizeRatio );

        return relativeY;
    }

    /**
     * Returns the absolute position in the image of the given relative
     * position.
     * 
     * @param x
     *            X position of the image
     * @return X position of the panel
     */
    protected int getAbsoluteX( int x ) {

        int absoluteX = 0;

        if( isImageLoaded( ) )
            absoluteX = (int) ( ( x * sizeRatio ) + this.x );

        return absoluteX;
    }

    /**
     * Returns the absolute position in the image of the given relative
     * position.
     * 
     * @param y
     *            Y position of the image
     * @return Y position of the panel
     */
    protected int getAbsoluteY( int y ) {

        int absoluteY = 0;

        if( isImageLoaded( ) )
            absoluteY = (int) ( ( y * sizeRatio ) + this.y );

        return absoluteY;
    }

    /**
     * Returns the absolute width in the image of the given relative width.
     * 
     * @param width
     *            Width of the image
     * @return Width in the panel
     */
    protected int getAbsoluteWidth( int width ) {

        int absoluteWidth = 0;

        if( isImageLoaded( ) )
            absoluteWidth = (int) ( width * sizeRatio );

        return absoluteWidth;
    }

    /**
     * Returns the absolute height in the image of the given relative height.
     * 
     * @param height
     *            Height of the image
     * @return Height in the panel
     */
    protected int getAbsoluteHeight( int height ) {

        int absoluteHeight = 0;

        if( isImageLoaded( ) )
            absoluteHeight = (int) ( height * sizeRatio );

        return absoluteHeight;
    }

    /**
     * Paints an rescaled image in the given graphics.
     * 
     * @param g
     *            Graphics to paint
     * @param image
     *            Image to be painted
     * @param x
     *            Absolute X position of the center of the image
     * @param y
     *            Absolute Y position of the bottom of the image
     * @param highlighted
     *            True if the image must be painted with a border
     */
    protected void paintRelativeImage( Graphics g, Image image, int x, int y, float scale, boolean highlighted ) {

        // If the image was loaded
        if( isImageLoaded( ) ) {
            // Calculate the size of the image
            int width = (int) ( image.getWidth( null ) * sizeRatio * scale);
            int height = (int) ( image.getHeight( null ) * sizeRatio * scale);

            // Calculate the position of the image
            int posX = getAbsoluteX( (int) ( x - ( image.getWidth( null ) * scale / 2 ) ) );
            int posY = getAbsoluteY( (int) ( y - image.getHeight( null ) * scale ) );

            // Draw the image
            g.drawImage( image, posX, posY, width, height, null );

            // Highlight the image, if necessary
            if( highlighted ) {
                g.setColor( Color.BLACK );
                g.drawRect( posX - 1, posY - 1, width + 2, height + 2 );
                g.drawRect( posX - 3, posY - 3, width + 6, height + 6 );
                g.setColor( Color.RED );
                g.drawRect( posX - 2, posY - 2, width + 4, height + 4 );
            }
        }
    }

    /**
     * Calculates and stores the size of the current image.
     */
    private synchronized void calculateSize( ) {

        // If the image is loaded
        if( isImageLoaded( ) && getWidth( ) > 0 && getHeight( ) > 0 ) {
            // Compare the aspect ratios of the image and the panel
            double panelRatio = (double) getWidth( ) / (double) getHeight( );
            double imageRatio = (double) image.getWidth( null ) / (double) image.getHeight( null );

            if( panelRatio <= imageRatio ) {
                int panelWidth = getWidth( ) - MARGIN * 2;
                width = panelWidth;
                height = (int) ( panelWidth / imageRatio );
            }

            else {
                int panelHeight = getHeight( ) - MARGIN * 2;
                width = (int) ( panelHeight * imageRatio );
                height = panelHeight;
            }

            x = ( ( getWidth( ) - width ) / 2 );
            y = ( ( getHeight( ) - height ) / 2 );

            // Calculate the size ratio
            sizeRatio = (double) width / (double) image.getWidth( null );

            // Repaint the panel
            repaint( );
        }
    }

    public Dimension getImageSize( ) {

        return new Dimension( image.getWidth( null ), image.getHeight( null ) );
    }
}
