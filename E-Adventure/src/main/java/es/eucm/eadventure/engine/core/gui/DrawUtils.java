/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *         research group.
 *  
 *   Copyright 2005-2010 e-UCM research group.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   e-UCM is a research group of the Department of Software Engineering
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
 *     eAdventure is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     eAdventure is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package es.eucm.eadventure.engine.core.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.List;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;


public class DrawUtils {

    public static void drawActionButtonsBoundingVolumes (Color c, Graphics2D g){
        // Draw button bounds
        g.setColor( c );
        
        int buttonCount =GUI.getInstance( ).getButtonCount();
        
        for (int i=0; i<buttonCount; i++){
            int buttonX = GUI.getInstance( ).getButtonX(i);
            int buttonWidth = GUI.getInstance( ).getButtonWidth(i);
            int buttonY = GUI.getInstance( ).getButtonY(i);
            int buttonHeight= GUI.getInstance( ).getButtonHeight(i);
            g.drawRect( buttonX-buttonWidth/2, 
                    buttonY-buttonHeight/2, 
                    buttonWidth, buttonHeight );
            g.fillRect( buttonX-5, buttonY-5, 10, 10 );
        }

    }
    
    /**
     * Method for debugging. It draws the bounding volume of all the functionalElements provided, with the 
     * given color, in the given Graphics2D object. Takes into account transparent areas, scales and offsets.
     * @param element
     * @param c
     * @param g
     */
    public static void drawElementCollectionBoundingVolumes (List<? extends FunctionalElement> elements, Color c, Graphics2D g){
        for (FunctionalElement element: elements){
            drawElementBoundingVolume (element, c, g);
        }
    }
    
    /**
     * Method for debugging. It draws the bounding volume of the functionalElement provided, with the 
     * given color, in the given Graphics2D object. Takes into account transparent areas, scales and offsets.
     * @param element
     * @param c
     * @param g
     */
    public static void drawElementBoundingVolume (FunctionalElement element, Color c, Graphics2D g){
        g.setColor( c );
        int offsetX = Game.getInstance( ).getFunctionalScene( ).getOffsetX( );
        //g.fillOval( element.getXImage( )-5, element.getYImage( )-5, 10, 10 );
        g.drawLine( element.getXImage( )-offsetX, element.getYImage( ), 
                element.getXImage( )+element.getWImage( )-offsetX, element.getYImage( ));
        g.drawLine( element.getXImage( )-offsetX, element.getYImage( ), 
                element.getXImage( )-offsetX, element.getYImage( )+element.getHImage( ));
        g.drawLine( element.getXImage( )-offsetX+element.getWImage( ), element.getYImage( ), 
                element.getXImage( )-offsetX+element.getWImage( ), element.getYImage( )+element.getHImage( ));
        g.drawLine( element.getXImage( )-offsetX, element.getYImage( )+element.getHImage( ), 
                element.getXImage( )-offsetX+element.getWImage( ), element.getYImage( )+element.getHImage( ));
        
    }
    
    public static void drawRectangleCollection (List<? extends es.eucm.eadventure.common.data.chapter.Rectangle> rectangles, Color c, Graphics2D g){
        for (es.eucm.eadventure.common.data.chapter.Rectangle r: rectangles){
            drawRectangle(r,c,g);
        }
    }
    
    public static void drawRectangle ( es.eucm.eadventure.common.data.chapter.Rectangle rectangle,
            Color c, Graphics2D g){
        g.setColor( c );
        int offsetX=Game.getInstance( ).getFunctionalScene( ).getOffsetX( );
        if ( rectangle.isRectangular( ) ){
            
            int rX = rectangle.getX( )-offsetX;
            int rY = rectangle.getY( );
            int rW = rectangle.getWidth( );
            int rH = rectangle.getHeight( );
                        
            g.drawLine( rX, rY, rX+rW, rY);
            g.drawLine( rX, rY, rX, rY+rH);
            g.drawLine( rX+rW, rY, rX+rW, rY+rH);
            g.drawLine( rX, rY+rH, rX+rW, rY+rH);
            
        } else {
            Polygon pol = new Polygon();
            for (Point p:rectangle.getPoints( )){
                pol.addPoint( p.x-offsetX, p.y );
            }
            g.drawPolygon( pol );
        }
    }
    
}
