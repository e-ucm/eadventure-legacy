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

package es.eucm.eadventure.editor.control.controllers.imageedition;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import es.eucm.eadventure.editor.gui.displaydialogs.SelectImageDialog;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElement;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElementSelectImage;

public class SelectImageController extends MouseInputAdapter {

    private ImageElementSelectImage underMouse;

    /**
     * The x axis value of the point where the drag started
     */
    protected int startDragX;

    /**
     * The y axis value of the point where the drag started
     */
    protected int startDragY;

    /**
     * The original x value of the dragged object
     */
    protected int originalX;

    /**
     * The original y value of the dragged object
     */
    protected int originalY;

    /**
     * The original width of the object being resized
     */
    protected int originalWidth;

    /**
     * The original height of the object being resized
     */
    protected int originalHeight;

    /**
     * The original scale of the object being rescaled
     */
    protected float originalScale;


    private SelectImageDialog selectImageDialog;

    public SelectImageController( ImageElementSelectImage selectImage, SelectImageDialog selectImageDialog ) {

        this.selectImageDialog = selectImageDialog;
        underMouse = selectImage;
    }

    @Override
    public void mouseClicked( MouseEvent e ) {

        setMouseUnder( e.getX( ), e.getY( ) );
        /*
                setMouseUnder( e.getX( ), e.getY( ) );
                if( spep.getSelectedElement( ) != null && e.getClickCount( ) == 2 && spep.getSelectedElement( ) instanceof ImageElementReference ) {
                    StructureControl.getInstance( ).changeDataControl( spep.getSelectedElement( ).getReferencedDataControl( ) );
                }
                else if( underMouse != null && !spep.getFixedSelectedElement( ) && !( spep.getSelectedElement( ) instanceof ImageElementInfluenceArea ) ) {
                    spep.setSelectedElement( underMouse );
                    spep.notifySelectionListener( );
                    spep.repaint( );
                }
                else if( underMouse == null && !spep.getFixedSelectedElement( ) ) {
                    spep.setSelectedElement( (ImageElement) null );
                    spep.notifySelectionListener( );
                    spep.repaint( );
                }
                else if( spep.getFixedSelectedElement( ) ) {
                    int x = spep.getRealX( e.getX( ) );
                    int y = spep.getRealY( e.getY( ) );
                    spep.getSelectedElement( ).changePosition( x, y );
                    spep.updateTextEditionPanel( );
                    spep.repaint( );
                }*/
    }

    @Override
    public void mousePressed( MouseEvent e ) {

        Point p = e.getPoint( );
        System.out.println( "pulsado " + e.getPoint( ).x + " " + e.getPoint( ).y );
        System.out.println( "traducido " + underMouse.getRealX( e.getPoint( ).x ) + " " + underMouse.getRealY( e.getPoint( ).y ) );

        this.setMouseUnder( p.x, p.y );

        if( underMouse.isMovable( ) || underMouse.isResize( ) || underMouse.isResizeWidth( ) ) {
            startDragX = e.getX( );
            startDragY = e.getY( );
            originalX = underMouse.getX( );
            originalY = underMouse.getY( );
            originalWidth = underMouse.getWidth( );
            originalHeight = underMouse.getHeight( );
            originalScale = underMouse.getScale( );
        }
        underMouse.recreateImage( );

    }

    @Override
    public void mouseEntered( MouseEvent e ) {

        //TODO resaltar  area.
    }

    @Override
    public void mouseMoved( MouseEvent e ) {

        setMouseUnder( e.getX( ), e.getY( ) );
        underMouse.recreateImage( );
        selectImageDialog.repaint( );
    }

    @Override
    public void mouseReleased( MouseEvent e ) {

        //   dragging = false;
    }

    @Override
    public void mouseDragged( MouseEvent e ) {

        if( underMouse.isMovable( ) && !underMouse.isResizeWidth( ) && !underMouse.isResize( ) ) {
            int changeX = underMouse.getRealX( e.getX( ) - startDragX );
            int changeY = underMouse.getRealY( e.getY( ) - startDragY );
            int x = originalX + changeX;
            int y = originalY + changeY;
            underMouse.changePosition( x, y );
            this.selectImageDialog.repaint( );
        } 
        else if (!underMouse.isResize( ) && underMouse.isResizeWidth( )){
            int changeX = underMouse.getRealX( e.getX( ) - startDragX );
            underMouse.changeSize( originalWidth + changeX, originalHeight );
            this.selectImageDialog.repaint( );
        }
        else if (underMouse.isResize( ) && !underMouse.isResizeWidth( )){
            int changeX = underMouse.getRealX( e.getX( ) - startDragX );
            int changeY = underMouse.getRealY( e.getY( ) - startDragY );
            underMouse.changeSize( originalWidth + changeX, originalHeight + changeY );
            this.selectImageDialog.repaint( );
        }
            
    }

    protected void setMouseUnder( int mouseX, int mouseY ) {

        int x = underMouse.getRealX( mouseX );
        int y = underMouse.getRealY( mouseY );
        underMouse.getMovableElement( x, y );
        underMouse.getResizeElement( x, y );

    }

    public ImageElement getUnderMouse( ) {

        return underMouse;
    }

}
