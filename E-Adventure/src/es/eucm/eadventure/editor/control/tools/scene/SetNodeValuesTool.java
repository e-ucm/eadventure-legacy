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
package es.eucm.eadventure.editor.control.tools.scene;

import java.util.HashMap;

import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.data.chapter.Trajectory.Node;
import es.eucm.eadventure.common.data.chapter.Trajectory.Side;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class SetNodeValuesTool extends Tool {

    private int oldX;

    private int oldY;

    private float oldScale;

    private int newX;

    private int newY;

    private float newScale;

    private Node node;

    private Trajectory trajectory;
    
    private HashMap<String, Float> oldLengths;
    
    public SetNodeValuesTool( Node node, Trajectory trajectory, int newX, int newY, float newScale ) {
        this.newX = newX;
        this.newY = newY;
        this.newScale = newScale;
        this.oldX = node.getX( );
        this.oldY = node.getY( );
        this.oldScale = node.getScale( );
        this.node = node;
        this.trajectory = trajectory;
        this.oldLengths = new HashMap<String, Float>();
    }

    @Override
    public boolean canRedo( ) {

        return true;
    }

    @Override
    public boolean canUndo( ) {

        return true;
    }

    @Override
    public boolean combine( Tool other ) {

        if( other instanceof SetNodeValuesTool ) {
            SetNodeValuesTool crvt = (SetNodeValuesTool) other;
            if( crvt.node != node )
                return false;
            newX = crvt.newX;
            newY = crvt.newY;
            newScale = crvt.newScale;
            timeStamp = crvt.timeStamp;
            return true;
        }
        return false;
    }

    @Override
    public boolean doTool( ) {
        node.setValues( newX, newY, newScale );
        if (newX != oldX || newY != oldY)
            for (Side side : trajectory.getSides( )) {
                if (side.getIDEnd( ).equals(node.getID( )) || side.getIDStart( ).equals( node.getID( ) ) ) {
                    oldLengths.put( side.getIDStart( ) + ";" + side.getIDEnd( ) , side.getLength( ) );
                    Node start = trajectory.getNodeForId( side.getIDStart( ) );
                    Node end = trajectory.getNodeForId( side.getIDEnd( ) );
                    double x = start.getX( ) - end.getX( );
                    double y = start.getY( ) - end.getY( );
                    side.setLenght( (float) Math.sqrt( Math.pow(x,2) + Math.pow( y,2 ) ) );
                    side.setRealLength( (float) Math.sqrt( Math.pow(x,2) + Math.pow( y,2 ) ) );
                }
            }
        return true;
    }

    @Override
    public boolean redoTool( ) {

        node.setValues( newX, newY, newScale );
        if (newX != oldX || newY != oldY)
            for (Side side : trajectory.getSides( )) {
                if (side.getIDEnd( ).equals(node.getID( )) || side.getIDStart( ).equals( node.getID( ) ) ) {
                    Node start = trajectory.getNodeForId( side.getIDStart( ) );
                    Node end = trajectory.getNodeForId( side.getIDEnd( ) );
                    double x = start.getX( ) - end.getX( );
                    double y = start.getY( ) - end.getY( );
                    side.setLenght( (float) Math.sqrt( Math.pow(x,2) + Math.pow( y,2 ) ) );
                    side.setRealLength( (float) Math.sqrt( Math.pow(x,2) + Math.pow( y,2 ) ) );
                }
            }
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        node.setValues( oldX, oldY, oldScale );
        if (newX != oldX || newY != oldY)
            for (Side side : trajectory.getSides( )) {
                if (side.getIDEnd( ).equals(node.getID( )) || side.getIDStart( ).equals( node.getID( ) ) ) {
                    Node start = trajectory.getNodeForId( side.getIDStart( ) );
                    Node end = trajectory.getNodeForId( side.getIDEnd( ) );
                    double x = start.getX( ) - end.getX( );
                    double y = start.getY( ) - end.getY( );
                    side.setRealLength( (float) Math.sqrt( Math.pow(x,2) + Math.pow( y,2 ) ) );
                }

                Float temp = oldLengths.get( side.getIDStart( ) + ";" + side.getIDEnd( ) );
                if (temp != null)
                    side.setLenght( temp );
            }
        
        Controller.getInstance( ).updatePanel( );
        return true;
    }

}
