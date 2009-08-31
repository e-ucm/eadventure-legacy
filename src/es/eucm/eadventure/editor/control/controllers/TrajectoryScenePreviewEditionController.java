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
package es.eucm.eadventure.editor.control.controllers;

import java.awt.event.MouseEvent;

import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.editor.control.controllers.scene.NodeDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.SideDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.TrajectoryDataControl;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElement;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElementNode;

/**
 * Controller for the mouse clicks in the ScenePreviewEditionPanel when editing
 * a trajectory.
 * 
 * @author Eugenio Marchiori
 */
public class TrajectoryScenePreviewEditionController extends NormalScenePreviewEditionController {

    /**
     * Id for the node edit tool
     */
    public static final int NODE_EDIT = 0;

    /**
     * Id for the side edit tool
     */
    public static final int SIDE_EDIT = 1;

    /**
     * Id for the delete tool
     */
    public static final int DELETE_TOOL = 2;

    /**
     * Id for the barries edit tool
     */
    public static final int EDIT_BARRIERS = 3;

    /**
     * Id for the tool to select the initial node
     */
    public static final int SELECT_INITIAL = 4;

    private TrajectoryDataControl tdc;

    private int selectedTool = NODE_EDIT;

    public TrajectoryScenePreviewEditionController( ScenePreviewEditionPanel spep, TrajectoryDataControl trajectoryDataControl ) {

        super( spep );
        this.spep = spep;
        this.tdc = trajectoryDataControl;
    }

    @Override
    public void mouseClicked( MouseEvent e ) {

        int x = spep.getRealX( e.getX( ) );
        int y = spep.getRealY( e.getY( ) );
        setMouseUnder( e.getX( ), e.getY( ) );

        if( selectedTool == NODE_EDIT ) {
            if( this.underMouse == null ) {
                tdc.addNode( x, y );
                spep.addNode( tdc.getLastNode( ) );
                spep.setTrajectory( (Trajectory) tdc.getContent( ) );
                spep.repaint( );
            }
            else {
                spep.setSelectedElement( underMouse );
                spep.repaint( );
            }
        }
        else if( selectedTool == DELETE_TOOL ) {
            if( underMouse != null ) {
                NodeDataControl nodeDataControl = ( (ImageElementNode) underMouse ).getNodeDataControl( );
                if( tdc.deleteElement( nodeDataControl, true ) ) {
                    spep.removeElement( ScenePreviewEditionPanel.CATEGORY_NODE, underMouse );
                    underMouse = null;
                    spep.setSelectedElement( (ImageElement) null );
                    spep.setTrajectory( (Trajectory) tdc.getContent( ) );
                    spep.repaint( );
                }
            }
            else {
                findAndDeleteSide( x, y );
            }
        }
        else if( selectedTool == SIDE_EDIT ) {
            if( underMouse != null ) {
                if( spep.getFirstElement( ) == null ) {
                    spep.setFirstElement( underMouse );
                }
                else {
                    tdc.addSide( ( (ImageElementNode) spep.getFirstElement( ) ).getNodeDataControl( ), ( (ImageElementNode) underMouse ).getNodeDataControl( ) );
                    spep.setFirstElement( null );
                    spep.repaint( );
                }
            }
        }
        else if( selectedTool == EDIT_BARRIERS ) {
            super.mouseClicked( e );
        }
        else if( selectedTool == SELECT_INITIAL ) {
            if( underMouse != null ) {
                tdc.setInitialNode( ( (ImageElementNode) underMouse ).getNodeDataControl( ) );
                spep.recreateElements( ScenePreviewEditionPanel.CATEGORY_NODE );
                spep.repaint( );
            }
        }
    }

    private void findAndDeleteSide( int x, int y ) {

        SideDataControl sdc = null;
        SideDataControl temp = null;
        for( int i = 0; i < tdc.getSides( ).size( ) && sdc == null; i++ ) {
            temp = tdc.getSides( ).get( i );
            NodeDataControl start = temp.getStart( );
            NodeDataControl end = temp.getEnd( );
            int x1 = start.getX( );
            int y1 = start.getY( );
            int x2 = end.getX( );
            int y2 = end.getY( );
            if( x1 > x2 ) {
                x2 = start.getX( );
                y2 = start.getY( );
                x1 = end.getX( );
                y1 = end.getY( );
            }
            int dist = x2 - x1;
            for( int j = 0; j < dist && sdc == null; j++ ) {
                int tempX = (int) ( x1 + ( x2 - x1 ) * ( (float) j / dist ) );
                int tempY = (int) ( y1 + ( y2 - y1 ) * ( (float) j / dist ) );
                if( Math.abs( tempX - x ) < 8 && Math.abs( tempY - y ) < 8 )
                    sdc = temp;
            }
        }
        if( sdc != null ) {
            tdc.deleteElement( sdc, true );
            spep.setTrajectory( (Trajectory) tdc.getContent( ) );
            spep.repaint( );
        }
    }

    @Override
    public void mouseEntered( MouseEvent e ) {

    }

    @Override
    public void mouseExited( MouseEvent e ) {

    }

    @Override
    public void mousePressed( MouseEvent e ) {

        if( selectedTool == NODE_EDIT ) {
            setMouseUnder( e.getX( ), e.getY( ) );
            if( underMouse != null ) {
                startDragX = e.getX( );
                startDragY = e.getY( );
                originalX = underMouse.getX( );
                originalY = underMouse.getY( );
                originalWidth = underMouse.getImage( ).getWidth( null );
                originalHeight = underMouse.getImage( ).getHeight( null );
                originalScale = underMouse.getScale( );
            }
            else if( underMouse != null ) {
                spep.setSelectedElement( underMouse );
                spep.repaint( );
            }
        }
        else if( selectedTool == EDIT_BARRIERS ) {
            super.mousePressed( e );
        }
    }

    @Override
    public void mouseReleased( MouseEvent e ) {

    }

    @Override
    public void mouseDragged( MouseEvent e ) {

        if( selectedTool == NODE_EDIT ) {
            if( underMouse != null && !spep.isRescale( ) ) {
                int changeX = spep.getRealWidth( e.getX( ) - startDragX );
                int changeY = spep.getRealHeight( e.getY( ) - startDragY );
                int x = originalX + changeX;
                int y = originalY + changeY;
                underMouse.changePosition( x, y );
                spep.repaint( );
            }
            else if( underMouse != null && spep.isRescale( ) ) {
                double changeX = ( e.getX( ) - startDragX );
                double changeY = -( e.getY( ) - startDragY );
                double width = originalWidth / originalScale;
                double height = ( originalHeight - 10 ) / originalScale;

                double tempX = changeX / width;
                double tempY = changeY / height;

                float scale = originalScale;
                if( tempX * tempX > tempY * tempY )
                    scale = (float) ( ( ( width * originalScale ) + spep.getRealWidth( (int) changeX ) ) / width );
                else
                    scale = (float) ( ( ( height * originalScale ) + spep.getRealHeight( (int) changeY ) ) / height );

                if( scale <= 0 )
                    scale = 0.01f;

                underMouse.setScale( scale );
                underMouse.recreateImage( );
                spep.repaint( );
            }
        }
        else if( selectedTool == EDIT_BARRIERS ) {
            super.mouseDragged( e );
        }
    }

    @Override
    public void mouseMoved( MouseEvent e ) {

        setMouseUnder( e.getX( ), e.getY( ) );
        if( spep.getFirstElement( ) != null ) {
            spep.repaint( );
        }
    }

    @Override
    public ImageElement getUnderMouse( ) {

        return underMouse;
    }

    public void setSelectedTool( int tool ) {

        selectedTool = tool;
        spep.setFirstElement( null );
        if( selectedTool == EDIT_BARRIERS ) {
            spep.setMovableCategory( ScenePreviewEditionPanel.CATEGORY_BARRIER, true );
        }
        else {
            spep.setMovableCategory( ScenePreviewEditionPanel.CATEGORY_BARRIER, false );
        }
    }
}
