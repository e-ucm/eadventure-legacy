/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 * research group.
 * 
 * Copyright 2005-2010 <e-UCM> research group.
 * 
 * You can access a list of all the contributors to <e-Adventure> at:
 * http://e-adventure.e-ucm.es/contributors
 * 
 * <e-UCM> is a research group of the Department of Software Engineering and
 * Artificial Intelligence at the Complutense University of Madrid (School of
 * Computer Science).
 * 
 * C Profesor Jose Garcia Santesmases sn, 28040 Madrid (Madrid), Spain.
 * 
 * For more info please visit: <http://e-adventure.e-ucm.es> or
 * <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 * <e-Adventure> is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with <e-Adventure>. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package es.eucm.eadventure.editor.gui.otherpanels.imageelements;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;

public class ImageElementSelectImage extends ImageElement {

    private static final int LIGHT_BORDER = 1;

    private static final int HARD_BORDER = 2;

    private static final int RESCALE_BORDER = 3;

    private static final int RESCALE_BORDER_ACTIVE = 4;

    private static final int RESIZE_BORDER = 5;

    private static final int RESIZE_BORDER_ACTIVE = 6;

    private final int DEFAULTWIDTH = 800;

    private final int DEFAULTHEIGHT = 600;

    private int width;

    private int height;

    private int originalWidth;

    private int originalHeight;

    private double ratioWidth;//TODO revisar

    private double ratioHeight;

    private int marginX;

    private int marginY;

    private int x;

    private int y;

    private BufferedImage image;

    private boolean movable;

    private boolean resize;

    private boolean resizeWidth;

    private String path;

    public ImageElementSelectImage( BufferedImage image2, String path ) {

        this.path = path;
        this.originalHeight = image2.getHeight( );
        this.originalWidth = image2.getWidth( );

        this.image = image2;

        ratioWidth = originalWidth / DEFAULTWIDTH;
        ratioHeight = originalHeight / DEFAULTHEIGHT;

        this.width = DEFAULTWIDTH;
        this.height = DEFAULTHEIGHT;

        image2 = new BufferedImage( getWidth( ), getHeight( ), BufferedImage.TYPE_4BYTE_ABGR );

        x = ( originalWidth - width ) / 2;
        y = ( originalHeight - height ) / 2;

        this.resize = false;
        this.resizeWidth = false;
        this.movable = false;

        System.out.println( "imageancho: " + this.originalWidth + " imagealto: " + this.originalHeight + "x: " + x + "y: " + y + "width: " + width + "height: " + height );
        fillImage( );
    }

    private void fillImage( ) {

        //reload the image
        BufferedImage tempImage = (BufferedImage) AssetsController.getImage( path );
        // image = new BufferedImage(getWidth( ), getHeight( ), BufferedImage.TYPE_4BYTE_ABGR);
        image.getGraphics( ).drawImage( tempImage, 0, 0, null );

        Graphics2D g = (Graphics2D) image.getGraphics( );
        AlphaComposite alphaComposite = AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.3f );
        g.setComposite( alphaComposite );
        g.setColor( Color.YELLOW );
        g.fillRect( x, y, width, height );
        g.setColor( Color.BLACK );
        g.drawRect( x, y, width - 1, height - 1 );
    }

    private void paintBorders( int border_type ) {

        Graphics2D g = (Graphics2D) image.getGraphics( );
        if( border_type == LIGHT_BORDER ) {
            Color color = g.getColor( );
            g.setColor( Color.RED );
            g.drawRect( x, y, width, height );
            g.setColor( color );
            //drawPanel.paintRelativeImage( element.getImage( ), element.getX( ), element.getY( ), element.getScale( ), 0.5f );
        }
        else if( border_type == HARD_BORDER ) {
            Color color = g.getColor( );
            g.setColor( Color.RED );
            g.fillRect( x - 4, y - 4, width + 8, 4 );
            g.fillRect( x - 4, y - 4, 4, height + 4 );
            g.fillRect( x + width, y, 4, height + 4 );
            g.fillRect( x - 4, y + height, width + 4, 4 );
            g.setColor( color );
            //    drawPanel.paintRelativeImage( element.getImage( ), element.getX( ), element.getY( ), element.getScale( ), 0.5f );
        }
        else if( border_type == RESCALE_BORDER ) {
            Color color = g.getColor( );
            g.setColor( Color.GREEN );
            g.drawRect( x + width - 8, y - 8, 16, 16 );
            g.setColor( color );
        }
        else if( border_type == RESCALE_BORDER_ACTIVE ) {// a la izquierda también
            Color color = g.getColor( );
            g.setColor( Color.BLUE );
            g.drawRect( x + width - 8, y - 8, 16, 16 );
            g.drawRect( x + width - 7, y - 7, 14, 14 );
            g.setColor( Color.RED );
            g.fillRect( x + width, y, 6, height );
            g.setColor( color );
        }
        else if( border_type == RESIZE_BORDER ) {
            Color color = g.getColor( );
            g.setColor( Color.GREEN );
            g.drawRect( x + width - 8, y + height - 8, 16, 16 );
            g.setColor( color );
        }
        else if( border_type == RESIZE_BORDER_ACTIVE ) {
            System.out.println( "yes" );
            Color color = g.getColor( );
            g.setColor( Color.BLUE );
            g.drawRect( x + width - 8, y + height - 8, 16, 16 );
            g.drawRect( x + width - 7, y + height - 7, 14, 14 );
            g.setColor( color );
        }

    }

    @Override
    public void changeSize( int width2, int height2 ) {

        /*   if (width2 < height2) {
               height2 = (height2 * 800) / width2;
               width2 = 800;
             }
             else {
               width2 = (width2 * 600) / height2;
               height2 = 600;
             }*///falta reescalar
        // TODO
        this.width = width2;
        this.height = height2;
        if( width < 1 )
            width = 1;
        if( height < 1 )
            height = 1;
        if( height < 600 ) //TODO ajustar proporciones
            height = 600;
        if( width < 800 ) //TODO ajustar proporciones
            width = 800;
        recreateImage( );
    }

    @Override
    public int getHeight( ) {

        return height;
    }

    @Override
    public int getWidth( ) {

        return width;
    }

    public int getRealX( int x ) {

        return ( ( this.originalWidth * x ) / DEFAULTWIDTH ) + marginX;
    }

    public int getRealY( int y ) {

        return ( ( this.originalHeight * y ) / DEFAULTHEIGHT ) + marginY;
    }

    @Override
    public float getScale( ) {

        return image.getWidth( ) / this.DEFAULTWIDTH;
    }

    @Override
    public DataControl getDataControl( ) {

        return null;
    }

    @Override
    public DataControl getReferencedDataControl( ) {

        return null;
    }

    @Override
    public int getX( ) {

        return x;
    }

    @Override
    public int getY( ) {

        return y;
    }

    @Override
    public void recreateImage( ) {

        fillImage( );

        if( !movable && !resize && !resizeWidth ) {
            paintBorders( LIGHT_BORDER );
        }
        if( movable ) {
            paintBorders( HARD_BORDER );
            if( resizeWidth )
                paintBorders( RESCALE_BORDER_ACTIVE );
            else
                paintBorders( RESCALE_BORDER );

            if( resize )
                paintBorders( RESIZE_BORDER_ACTIVE );
            else
                paintBorders( RESIZE_BORDER );

        }
    }

    @Override
    public int getLayer( ) {

        return 0;
    }

    @Override
    public void changePosition( int x2, int y2 ) {

        this.x = x2;
        this.y = y2;
        if( x < 0 )
            x = 0;
        if( y < 0 )
            y = 0;
        if( x > this.originalWidth - this.width )
            x = originalWidth - width;
        if( y > this.originalHeight - this.height )
            y = originalHeight - height;
        
        this.recreateImage( );

    }

    @Override
    public boolean transparentPoint( int x, int y ) {

        return false;
    }

    public void getMovableElement( int x2, int y2 ) {

        movable = false;
        if( ( x2 >= x && x2 <= x + width ) && ( y2 >= y && y2 <= y + height ) ) {
            movable = true;
        }
    }

    @Override
    public boolean canResize( ) {

        return true;
    }

    @Override
    public boolean canRescale( ) {

        return true;
    }

    public boolean isResize( ) {

        return resize;
    }

    public boolean isResizeWidth( ) {

        return resizeWidth;
    }

    public boolean isMovable( ) {

        return movable;
    }

    public void getResizeElement( int x, int y ) {

        int x_image = ( this.x + width );
        int y_image = ( this.y );

        this.resize = false;
        this.resizeWidth = false;

        int margin = getRealX( 8 );

        if( ( x > ( x_image - margin ) ) && ( x < ( x_image + margin ) ) && y > ( y_image + height - margin ) && y < ( y_image + height + margin ) ) {
            this.resize = true;
        }
        else if( ( x > ( x_image - margin ) ) && ( x < ( x_image + margin ) ) && y > ( y_image ) && y < ( height ) ) {
            this.resizeWidth = true;
        }

    }

    public void getRescaleElement( int x2, int y2 ) {

        // TODO Auto-generated method stub

    }

    @Override
    public void setScale( float scale ) {

        // TODO Auto-generated method stub

    }

    public void updateMargin( int marginX2, int marginY2 ) {

        this.marginX = marginX2;
        this.marginY = marginY2;
    }

}
