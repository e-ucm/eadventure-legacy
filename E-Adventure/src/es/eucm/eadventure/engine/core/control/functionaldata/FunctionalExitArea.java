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
package es.eucm.eadventure.engine.core.control.functionaldata;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Transparency;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import es.eucm.eadventure.common.data.chapter.Exit;
import es.eucm.eadventure.common.data.chapter.InfluenceArea;
import es.eucm.eadventure.common.data.chapter.elements.Item;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.core.gui.GUI;

public class FunctionalExitArea extends FunctionalItem {

    private Exit exit;

    private Polygon polygon;

    private static Item buildItem( Exit exit ) {

        Item item = new Item( "" );

        item.addResources( new Resources( ) );
        return item;
    }

    private static int calculateX( Exit exit ) {

        return exit.getX( ) + exit.getWidth( ) / 2;
    }

    private static int calculateY( Exit exit ) {

        return exit.getY( ) + exit.getHeight( );
    }

    public FunctionalExitArea( Exit exit, InfluenceArea influenceArea ) {

        super( buildItem( exit ), null, calculateX( exit ), calculateY( exit ) );

        this.exit = exit;
        this.influenceArea = influenceArea;

        // Create transparent image
        BufferedImage bImagenTransparente = GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage( exit.getWidth( ), exit.getHeight( ), Transparency.TRANSLUCENT );

        Graphics2D g2d = bImagenTransparente.createGraphics( );

        // Make all pixels transparent
        Color transparent = new Color( 0, 0, 0, 0 );
        g2d.setColor( transparent );
        g2d.setComposite( AlphaComposite.Src );
        g2d.fill( new Rectangle2D.Float( 0, 0, exit.getWidth( ), exit.getHeight( ) ) );
        g2d.dispose( );

        image = bImagenTransparente;

        if( !exit.isRectangular( ) ) {
            polygon = new Polygon( );
            for( Point point : exit.getPoints( ) ) {
                polygon.addPoint( point.x, point.y );
            }
        }
    }

    @Override
    public int getWidth( ) {

        return exit.getWidth( );
    }

    @Override
    public int getHeight( ) {

        return exit.getHeight( );
    }

    public Exit getExit( ) {

        return exit;
    }

    @Override
    public boolean isPointInside( float x, float y ) {

        boolean isInside = false;
        if( exit.isRectangular( ) ) {

            int mousex = (int) ( x - ( this.x - getWidth( ) / 2 ) );
            int mousey = (int) ( y - ( this.y - getHeight( ) ) );

            isInside = ( ( mousex >= 0 ) && ( mousex < getWidth( ) ) && ( mousey >= 0 ) && ( mousey < getHeight( ) ) );

            //System.out.println( "IS ACTIVE AREA INSIDE("+this.activeArea.getId( )+")="+isInside+" "+x+" , "+y );
            //System.out.println( "X="+this.x+" Y="+ this.y+" WIDTH="+this.getWidth( )+" HEIGHT="+this.getHeight( ));
        }
        else {
            return polygon.contains( x, y );
        }
        return isInside;
    }

    /**
     * Triggers the giving action associated with the item
     * 
     * @param npc
     *            The character receiver of the item
     * @return True if the item was given, false otherwise
     */
    @Override
    public boolean giveTo( FunctionalNPC npc ) {

        return false;
    }

    /**
     * Triggers the grabbing action associated with the item
     * 
     * @return True if the item was grabbed, false otherwise
     */
    @Override
    public boolean grab( ) {

        return false;
    }

}
