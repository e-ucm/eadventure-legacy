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
package es.eucm.eadventure.editor.gui.otherpanels.imageelements;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.scene.InfluenceAreaDataControl;

public class ImageElementInfluenceArea extends ImageElement {

    private InfluenceAreaDataControl influenceAreaDataControl;

    private ImageElement imageElementReference;

    private boolean visible = true;

    public ImageElementInfluenceArea( InfluenceAreaDataControl influenceAreaDataControl, ImageElement reference ) {

        this.influenceAreaDataControl = influenceAreaDataControl;
        this.imageElementReference = reference;
        if( influenceAreaDataControl.hasInfluenceArea( ) ) {
            if( influenceAreaDataControl.getWidth( ) == 0 || influenceAreaDataControl.getHeight( ) == 0 )
                image = null;
            else
                image = new BufferedImage( influenceAreaDataControl.getWidth( ), influenceAreaDataControl.getHeight( ), BufferedImage.TYPE_4BYTE_ABGR );
        }
        else {
            int width = (int) ( reference.getImage( ).getWidth( null ) * reference.getScale( ) + 40 );
            int height = (int) ( reference.getImage( ).getHeight( null ) * reference.getScale( ) + 40 );
            image = new BufferedImage( width, height, BufferedImage.TYPE_4BYTE_ABGR );
        }
        fillImage( );
    }

    private void fillImage( ) {

        if( image != null ) {
            Graphics2D g = (Graphics2D) image.getGraphics( );
            AlphaComposite alphaComposite = AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.3f );
            g.setComposite( alphaComposite );
            g.setColor( Color.BLUE );
            g.fillRect( 0, 0, image.getWidth( null ), image.getHeight( null ) );
            g.setColor( Color.BLACK );
            g.drawRect( 0, 0, image.getWidth( null ) - 1, image.getHeight( null ) - 1 );
        }
    }

    @Override
    public void changePosition( int x, int y ) {

        if( !influenceAreaDataControl.hasInfluenceArea( ) ) {
            int width = (int) ( imageElementReference.getImage( ).getWidth( null ) * imageElementReference.getScale( ) + 40 );
            int height = (int) ( imageElementReference.getImage( ).getHeight( null ) * imageElementReference.getScale( ) + 40 );
            influenceAreaDataControl.setInfluenceArea( -20, -20, width, height );
        }

        int width = influenceAreaDataControl.getWidth( );
        int height = influenceAreaDataControl.getHeight( );
        int imageWidth = (int) ( imageElementReference.getWidth( ) * imageElementReference.getScale( ) );
        int imageHeight = (int) ( imageElementReference.getHeight( ) * imageElementReference.getScale( ) );
        int imageX = imageElementReference.getX( );
        int imageY = imageElementReference.getY( );

        int imageTempX = imageX - imageWidth / 2;
        int imageTempY = imageY - imageHeight;
        int tempX = x - width / 2;
        int tempY = y - height;

        tempX = tempX - imageTempX;
        tempY = tempY - imageTempY;

        if( -tempX > width )
            tempX = -width;
        if( -tempY > height )
            tempY = -height;
        if( tempX > imageElementReference.getWidth( ) * imageElementReference.getScale( ) )
            tempX = (int) ( imageElementReference.getWidth( ) * imageElementReference.getScale( ) );
        if( tempY > imageElementReference.getHeight( ) * imageElementReference.getScale( ) )
            tempY = (int) ( imageElementReference.getHeight( ) * imageElementReference.getScale( ) );

        influenceAreaDataControl.setInfluenceArea( tempX, tempY, width, height );

    }

    @Override
    public DataControl getDataControl( ) {

        return null;
    }

    @Override
    public int getLayer( ) {

        return Integer.MAX_VALUE;
    }

    @Override
    public float getScale( ) {

        return 1.0f;
    }

    @Override
    public int getX( ) {

        if( influenceAreaDataControl.hasInfluenceArea( ) ) {
            int left = (int) ( imageElementReference.getX( ) - imageElementReference.getWidth( ) * imageElementReference.getScale( ) / 2 );
            return left + influenceAreaDataControl.getX( ) + influenceAreaDataControl.getWidth( ) / 2;
        }
        else
            return imageElementReference.getX( );
    }

    @Override
    public int getY( ) {

        if( influenceAreaDataControl.hasInfluenceArea( ) ) {
            int upper = (int) ( imageElementReference.getY( ) - imageElementReference.getHeight( ) * imageElementReference.getScale( ) );
            return upper + influenceAreaDataControl.getY( ) + influenceAreaDataControl.getHeight( );
        }
        else
            return imageElementReference.getY( ) + 20;
    }

    @Override
    public void recreateImage( ) {

        imageElementReference.recreateImage( );
        if( influenceAreaDataControl.hasInfluenceArea( ) )
            image = new BufferedImage( influenceAreaDataControl.getWidth( ), influenceAreaDataControl.getHeight( ), BufferedImage.TYPE_4BYTE_ABGR );
        else {
            int width = (int) ( imageElementReference.getImage( ).getWidth( null ) * imageElementReference.getScale( ) + 40 );
            int height = (int) ( imageElementReference.getImage( ).getHeight( null ) * imageElementReference.getScale( ) + 40 );
            image = new BufferedImage( width, height, BufferedImage.TYPE_4BYTE_ABGR );
        }
        fillImage( );
    }

    @Override
    public void setScale( float scale ) {

    }

    @Override
    public boolean canRescale( ) {

        return false;
    }

    @Override
    public boolean canResize( ) {

        return true;
    }

    @Override
    public void changeSize( int width, int height ) {

        int x = influenceAreaDataControl.getX( );
        int y = influenceAreaDataControl.getY( );

        //int imageWidth = (int) (imageElementReference.getWidth() * imageElementReference.getScale());
        //int imageHeight = (int) (imageElementReference.getHeight() * imageElementReference.getScale());
        int imageWidth = x + 5;
        int imageHeight = y + 5;

        if( width + x < imageWidth )
            width = imageWidth - x;
        if( height + y < imageHeight )
            height = imageHeight - y;

        influenceAreaDataControl.setInfluenceArea( x, y, width, height );
    }

    @Override
    public int getHeight( ) {

        if( image == null )
            return 0;
        return image.getHeight( null );
    }

    @Override
    public int getWidth( ) {

        if( image == null )
            return 0;
        return image.getWidth( null );
    }

    @Override
    public boolean transparentPoint( int x, int y ) {

        return false;
    }

    @Override
    public DataControl getReferencedDataControl( ) {

        return null;
    }

    public void setVisible( boolean b ) {

        this.visible = b;
    }

    @Override
    public boolean isVisible( ) {

        return visible;
    }
}
