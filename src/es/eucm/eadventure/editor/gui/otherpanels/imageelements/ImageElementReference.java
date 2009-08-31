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

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;

public class ImageElementReference extends ImageElement {

    private ElementReferenceDataControl elementReferenceDataControl;

    public ImageElementReference( ElementReferenceDataControl elementReferenceDataControl ) {

        this.elementReferenceDataControl = elementReferenceDataControl;
        String imagePath = Controller.getInstance( ).getElementImagePath( elementReferenceDataControl.getElementId( ) );
        if( imagePath != null )
            image = AssetsController.getImage( imagePath );
        else
            image = ( new ImageIcon( "img/assets/EmptyImage.png" ) ).getImage( );
    }

    @Override
    public int getX( ) {

        return elementReferenceDataControl.getElementX( );
    }

    @Override
    public int getY( ) {

        return elementReferenceDataControl.getElementY( );
    }

    @Override
    public float getScale( ) {

        return elementReferenceDataControl.getElementScale( );
    }

    @Override
    public DataControl getDataControl( ) {

        return elementReferenceDataControl;
    }

    @Override
    public void recreateImage( ) {

        String imagePath = Controller.getInstance( ).getElementImagePath( elementReferenceDataControl.getElementId( ) );
        if( imagePath != null )
            image = AssetsController.getImage( imagePath );
        else
            image = ( new ImageIcon( "img/assets/EmptyImage.png" ) ).getImage( );
    }

    @Override
    public int getLayer( ) {

        return elementReferenceDataControl.getElementReference( ).getLayer( );
    }

    @Override
    public boolean equals( Object o ) {

        if( o == null )
            return false;
        if( !( o instanceof ImageElementReference ) )
            return false;
        ImageElementReference temp = (ImageElementReference) o;
        if( temp.elementReferenceDataControl.getElementId( ).equals( elementReferenceDataControl.getElementId( ) ) )
            return true;
        return false;
    }

    @Override
    public void changePosition( int x, int y ) {

        elementReferenceDataControl.setElementPosition( x, y );
    }

    @Override
    public void setScale( float scale ) {

        if( elementReferenceDataControl.getInfluenceArea( ) != null ) {
            int incrementX = (int) ( image.getWidth( null ) * scale - image.getWidth( null ) * this.getScale( ) );
            int incrementY = (int) ( image.getHeight( null ) * scale - image.getHeight( null ) * this.getScale( ) );
            elementReferenceDataControl.getInfluenceArea( ).referenceScaleChanged( incrementX, incrementY );
        }
        elementReferenceDataControl.setElementScale( scale );
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

        if( image == null || !elementReferenceDataControl.isVisible( ) )
            return false;
        else {
            int alpha = ( (BufferedImage) this.image ).getRGB( (int) ( x / this.getScale( ) ), (int) ( y / this.getScale( ) ) ) >>> 24;
            return !( alpha > 128 );
        }
    }

    @Override
    public DataControl getReferencedDataControl( ) {

        return elementReferenceDataControl.getReferencedElementDataControl( );
    }

    @Override
    public boolean isVisible( ) {

        return elementReferenceDataControl.isVisible( );
    }
}
