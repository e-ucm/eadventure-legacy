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
package es.eucm.eadventure.editor.gui.otherpanels.imageelements;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.scene.NodeDataControl;

public class ImageElementNode extends ImageElement {

    private NodeDataControl nodeDataControl;

    private Image playerImage;

    public ImageElementNode( NodeDataControl nodeDataControl ) {

        this.nodeDataControl = nodeDataControl;
        String imagePath = nodeDataControl.getPlayerImagePath( );
        if( imagePath != null && imagePath.length( ) > 0 )
            playerImage = AssetsController.getImage( imagePath );
        if( playerImage != null ) {
            float scale = nodeDataControl.getScale( );
            image = new BufferedImage( Math.max( playerImage.getWidth( null ), (int) ( 10 * ( 1 / scale ) ) ), playerImage.getHeight( null ) + (int) ( 10 * ( 1 / scale ) ), BufferedImage.TYPE_4BYTE_ABGR );
        }
        else {
            image = new BufferedImage( 20, 20, BufferedImage.TYPE_4BYTE_ABGR );
        }
        fillImage( );
    }

    private void fillImage( ) {

        Graphics2D g = (Graphics2D) image.getGraphics( );
        AlphaComposite alphaComposite = AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.6f );
        g.setComposite( alphaComposite );
        if( playerImage != null ) {
            int width = playerImage.getWidth( null );
            int height = playerImage.getHeight( null );
            g.drawImage( playerImage, 0, 0, width, height, null );
            alphaComposite = AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.3f );
            g.setComposite( alphaComposite );
            if( nodeDataControl.isInitial( ) )
                g.setColor( Color.RED );
            else
                g.setColor( Color.BLUE );
            float scale = nodeDataControl.getScale( );
            g.fillOval( width / 2 - (int) ( 10 / scale ), height - (int) ( 10 / scale ), (int) ( 19 / scale ), (int) ( 19 / scale ) );
            g.setColor( Color.BLACK );
            g.drawOval( width / 2 - (int) ( 10 / scale ), height - (int) ( 10 / scale ), (int) ( 19 / scale ), (int) ( 19 / scale ) );
        }
        else {
            if( nodeDataControl.isInitial( ) )
                g.setColor( Color.RED );
            else
                g.setColor( Color.BLUE );
            g.fillOval( 0, 0, image.getWidth( null ), image.getHeight( null ) );
            g.setColor( Color.BLACK );
            g.drawOval( 0, 0, image.getWidth( null ) - 1, image.getHeight( null ) - 1 );
        }
    }

    @Override
    public void changePosition( int x, int y ) {

        nodeDataControl.setNode( x, y, nodeDataControl.getScale( ) );
    }

    @Override
    public DataControl getDataControl( ) {

        return null;
    }

    @Override
    public int getLayer( ) {

        return nodeDataControl.getPlayerLayer( );
    }

    @Override
    public float getScale( ) {

        return nodeDataControl.getScale( );
    }

    @Override
    public int getX( ) {

        return nodeDataControl.getX( );
    }

    @Override
    public int getY( ) {

        return nodeDataControl.getY( ) + 10;
    }

    @Override
    public void recreateImage( ) {

        if( playerImage != null ) {
            float scale = nodeDataControl.getScale( );
            image = new BufferedImage( Math.max( playerImage.getWidth( null ), (int) ( 10 * ( 1 / scale ) ) ), playerImage.getHeight( null ) + (int) ( 10 * ( 1 / scale ) ), BufferedImage.TYPE_4BYTE_ABGR );
        }
        else {
            image = new BufferedImage( 20, 20, BufferedImage.TYPE_4BYTE_ABGR );
        }
        fillImage( );
    }

    public NodeDataControl getNodeDataControl( ) {

        return nodeDataControl;
    }

    @Override
    public void setScale( float scale ) {

        nodeDataControl.setNode( nodeDataControl.getX( ), nodeDataControl.getY( ), scale );
    }

    @Override
    public boolean canRescale( ) {

        return true;
    }

    @Override
    public boolean canResize( ) {

        return false;
    }

    @Override
    public void changeSize( int width, int height ) {

    }

    @Override
    public int getHeight( ) {

        return image.getHeight( null );
    }

    @Override
    public int getWidth( ) {

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

}
