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

package es.eucm.eadventure.common.auxiliar;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.data.chapter.Trajectory.Node;
import es.eucm.eadventure.common.data.chapter.Trajectory.Side;
import es.eucm.eadventure.common.data.chapter.scenes.Scene;

/**
 * Utility class that is used to fix consistency problems in trajectories
 * @author Javier Torrente
 *
 */
public class TrajectoryFixer {

    /** If a trajectory has only one node, the player is not displayed. To fix that situation, the node is duplicated with a slightly different location.
     * Both old and new nodes are linked through a side. 
     * 
     * @param scene
     */
    public static void fixSingleNode ( Scene scene ){
        Trajectory trajectory =scene.getTrajectory( );
        if (trajectory!=null){
            if (trajectory.getNodes( ).size( )==1){
                Node node1= trajectory.getNodes( ).get( 0 );
                trajectory.addNode( node1.getID()+"Dupl", node1.getX( )+1, node1.getY(), node1.getScale( ) );
                trajectory.addSide( node1.getID(), node1.getID()+"Dupl", 1 );
            }
        }
    }

    
    /**
     * Checks consistency between nodes and references in sides. If a node is referenced to but does not exist, it is created on position 400,300.
     * @param scene
     */
    private static void fixMissingNodes ( Scene scene ) {
        Trajectory trajectory =scene.getTrajectory( );
        if (trajectory!=null){
            List<String> nodeIds = new ArrayList<String>();
            // Put all node ids referenced in sides in a single array list
            for (Side side:trajectory.getSides( )){
                if (!nodeIds.contains( side.getIDStart( ) )){
                    nodeIds.add( side.getIDStart( ) );
                }
                if (!nodeIds.contains( side.getIDEnd( ) )){
                    nodeIds.add( side.getIDEnd( ) );
                }
            }
            
            // Remove all ids belonging to an existing nodes. The result is a list containing the strings of
            // all missing nodes
            for (Node node:trajectory.getNodes( )){
                if (nodeIds.contains( node.getID( ))){
                    nodeIds.remove( node.getID( ) );
                }
            }
            
            // Add all missing nodes to the trajectory
            for (String missingNodeId : nodeIds){
                if (trajectory.getNodes( ).size( )>0){
                    trajectory.addNode( missingNodeId, 400, 300, 1 );
                }
            }
        }
        
    }
    
   /**
    * Ensures that all nodes in a trajectory have a different location. If two nodes are found in the same position, first's x coordinate
    * is incremented by 1 px. This is important as nodes with equal position makes the trajectory algorithm to crash.
    * @param scene
    */
    private static void fixNodesWithSameLocation ( Scene scene ) {
        Trajectory trajectory =scene.getTrajectory( );
        if (trajectory!=null){
            // Iterate through nodes. 
            for (int i=0; i<trajectory.getNodes( ).size( ); i++){
                Node node1 = trajectory.getNodes( ).get( i );
                for (int j=0; j<trajectory.getNodes( ).size( ); j++){
                    Node node2 = trajectory.getNodes( ).get( j );
                    if (i!=j && node1.getX( )==node2.getX( ) && node1.getY( ) == node2.getY( )){
                        node1.setValues( node1.getX( )+1, node1.getY(), node1.getScale() );
                        j=0;
                    }
                }

            }
            
        }
    }
    
    /**
     * Checks pairs of nodes with duplicate ids. One of the nodes is removed.
     * @param nodes
     */
    private static void fixDuplicateNodes ( Scene scene ){
        Trajectory trajectory =scene.getTrajectory( );
        if (trajectory!=null){
            // Iterate through nodes. 
            for (int i=0; i<trajectory.getNodes( ).size( ); i++){
                Node node1 = trajectory.getNodes( ).get( i );
                for (int j=0; j<trajectory.getNodes( ).size( ); j++){
                    Node node2 = trajectory.getNodes( ).get( j );
                    if (i!=j && node1.getID( ).equals( node2.getID( ) )){
                        trajectory.getNodes( ).remove( j );
                        i--;j--;
                    }
                }

            }
        }
        
    }

    /**
     * Invokes all possible checks for the trajectory
     * @param scene
     */
    public static void fixTrajectory( Scene scene ){
        fixSingleNode ( scene );
        fixDuplicateNodes( scene );
        fixMissingNodes( scene );
        fixNodesWithSameLocation ( scene );
    }
    
}
