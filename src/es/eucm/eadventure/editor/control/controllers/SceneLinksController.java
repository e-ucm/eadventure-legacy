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
package es.eucm.eadventure.editor.control.controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.gui.otherpanels.SceneLinksPanel;
import es.eucm.eadventure.editor.gui.otherpanels.scenelistelements.SceneElement;
import es.eucm.eadventure.editor.gui.structurepanel.StructureControl;

public class SceneLinksController implements MouseListener, MouseMotionListener {

    private SceneLinksPanel slp;

    private SceneElement under;

    private int startDragX;

    private int startDragY;

    private int originalX;

    private int originalY;

    private SceneElement showName;

    public SceneLinksController( SceneLinksPanel sceneLinksPanel ) {

        this.slp = sceneLinksPanel;
    }

    public void mouseClicked( MouseEvent e ) {

        setUnderMouse( e.getX( ), e.getY( ) );
        if( under != null && e.getClickCount( ) == 2 ) {
            StructureControl.getInstance( ).changeDataControl( under.getDataControl( ) );
        }
    }

    public void mouseEntered( MouseEvent e ) {

    }

    public void mouseExited( MouseEvent e ) {

    }

    public void mousePressed( MouseEvent e ) {

        setUnderMouse( e.getX( ), e.getY( ) );
        if( under != null ) {
            startDragX = e.getX( );
            startDragY = e.getY( );
            originalX = under.getPosX( );
            originalY = under.getPosY( );
        }
    }

    public void mouseReleased( MouseEvent e ) {

        // TODO Auto-generated method stub

    }

    public void mouseDragged( MouseEvent e ) {

        if( under != null ) {
            int changeX = slp.getRealWidth( e.getX( ) - startDragX );
            int changeY = slp.getRealHeight( e.getY( ) - startDragY );
            int x = originalX + changeX;
            int y = originalY + changeY;
            under.changePosition( x, y );
            slp.repaint( );
            Controller.getInstance( ).dataModified( );
        }
    }

    public void mouseMoved( MouseEvent e ) {

        SceneElement temp = slp.getSceneElement( e.getX( ), e.getY( ) );
        if( temp != showName ) {
            if( showName != null )
                showName.setShowName( false );
            showName = temp;
            if( showName != null )
                showName.setShowName( true );
            slp.repaint( );
        }
    }

    private void setUnderMouse( int mouseX, int mouseY ) {

        under = slp.getSceneElement( mouseX, mouseY );
    }

}
