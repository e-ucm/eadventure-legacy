/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
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
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.otherpanels.imageelements;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.scene.BarrierDataControl;

public class ImageElementBarrier extends ImageElement {

    private BarrierDataControl barrierDataControl;

    public ImageElementBarrier( BarrierDataControl barrierDataControl ) {

        this.barrierDataControl = barrierDataControl;
        int x = barrierDataControl.getX( );
        int y = barrierDataControl.getY( );
        int width = barrierDataControl.getWidth( );
        int height = barrierDataControl.getHeight( );

        if( width <= 0 ) {
            width = 1;
            barrierDataControl.setBarrier( x, y, width, height );
        }
        if( height <= 0 ) {
            height = 1;
            barrierDataControl.setBarrier( x, y, width, height );
        }

        image = new BufferedImage( barrierDataControl.getWidth( ), barrierDataControl.getHeight( ), BufferedImage.TYPE_4BYTE_ABGR );
        fillImage( );
    }

    private void fillImage( ) {

        Graphics2D g = (Graphics2D) image.getGraphics( );
        AlphaComposite alphaComposite = AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.3f );
        g.setComposite( alphaComposite );
        g.setColor( Color.YELLOW );
        g.fillRect( 0, 0, image.getWidth( null ), image.getHeight( null ) );
        g.setColor( Color.BLACK );
        g.drawRect( 0, 0, image.getWidth( null ) - 1, image.getHeight( null ) - 1 );
    }

    @Override
    public void changePosition( int x, int y ) {

        int width = barrierDataControl.getWidth( );
        int height = barrierDataControl.getHeight( );
        barrierDataControl.setBarrier( x - width / 2, y - height, width, height );
    }

    @Override
    public DataControl getDataControl( ) {

        return barrierDataControl;
    }

    @Override
    public int getLayer( ) {

        return -1;
    }

    @Override
    public float getScale( ) {

        return 1.0f;
    }

    @Override
    public int getX( ) {

        return barrierDataControl.getX( ) + barrierDataControl.getWidth( ) / 2;
    }

    @Override
    public int getY( ) {

        return barrierDataControl.getY( ) + barrierDataControl.getHeight( );
    }

    @Override
    public void recreateImage( ) {

        image = new BufferedImage( barrierDataControl.getWidth( ), barrierDataControl.getHeight( ), BufferedImage.TYPE_4BYTE_ABGR );
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

        int x = barrierDataControl.getX( );
        int y = barrierDataControl.getY( );
        if( width < 1 )
            width = 1;
        if( height < 1 )
            height = 1;
        barrierDataControl.setBarrier( x, y, width, height );
    }

    @Override
    public int getHeight( ) {

        return barrierDataControl.getHeight( );
    }

    @Override
    public int getWidth( ) {

        return barrierDataControl.getWidth( );
    }

    @Override
    public boolean transparentPoint( int x, int y ) {

        return false;
    }

    @Override
    public DataControl getReferencedDataControl( ) {

        return null;
    }

}
