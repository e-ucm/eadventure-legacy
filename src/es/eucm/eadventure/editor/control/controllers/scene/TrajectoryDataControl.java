package es.eucm.eadventure.editor.control.controllers.scene;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.data.chapter.Trajectory.Node;
import es.eucm.eadventure.common.data.chapter.Trajectory.Side;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.tools.scene.AddTrajectoryNodeTool;
import es.eucm.eadventure.editor.control.tools.scene.AddTrajectorySideTool;
import es.eucm.eadventure.editor.control.tools.scene.DeleteTrajectoryNodeTool;
import es.eucm.eadventure.editor.control.tools.scene.DeleteTrajectorySideTool;
import es.eucm.eadventure.editor.control.tools.scene.SetTrajectoryInitialNodeTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

/**
 * Data Control for the trajectory
 * 
 * @author Eugenio Marchiori
 */
public class TrajectoryDataControl extends DataControl {

	/**
	 * Scene controller that contains this element reference.
	 */
	private SceneDataControl sceneDataControl;

	/**
	 * The trajectory
	 */
	private Trajectory trajectory;

	/**
	 * List of node controllers.
	 */
	private List<NodeDataControl> nodeDataControlList;
	
	/**
	 * List of side controllers.
	 */
	private List<SideDataControl> sideDataControlList;
	
	/**
	 * Initial node controller
	 */
	public NodeDataControl initialNode;
	
	/**
	 * Constructor.
	 * 
	 * @param sceneDataControl
	 *            Link to the parent scene controller
	 * @param barriersList
	 *            List of activeAreas
	 */
	public TrajectoryDataControl( SceneDataControl sceneDataControl, Trajectory trajectory ) {
		this.sceneDataControl = sceneDataControl;
		this.trajectory = trajectory;

		sideDataControlList = new ArrayList<SideDataControl>();
		nodeDataControlList = new ArrayList<NodeDataControl>();
		if (trajectory != null) {
			for (Node node : trajectory.getNodes()) {
				nodeDataControlList.add(new NodeDataControl(sceneDataControl, node));
				if (node == trajectory.getInitial()) {
					initialNode = nodeDataControlList.get(nodeDataControlList.size() - 1);
					initialNode.setInitial(true);
				}
			}
			for (Side side : trajectory.getSides())
				sideDataControlList.add(new SideDataControl(sceneDataControl, this, side));
		}
	}

	/**
	 * Returns the list of node data controllers
	 * 
	 * @return the list of node data controllers
	 */
	public List<NodeDataControl> getNodes() {
		return nodeDataControlList;
	}
	
	/**
	 * Returns the list of side data controllers
	 * 
	 * @return the list of side data controllers
	 */
	public List<SideDataControl> getSides() {
		return sideDataControlList;
	}
	
	
	/**
	 * Returns the last node data control in the list
	 * 
	 * @return the last node data control
	 */
	public NodeDataControl getLastNode() {
		return nodeDataControlList.get(nodeDataControlList.size() - 1);
	}
	
	
	/**
	 * Returns the last side data control in the list
	 * 
	 * @return the last side data control in the list
	 */
	public SideDataControl getLastSide() {
		return sideDataControlList.get(sideDataControlList.size() - 1);
	}

	/**
	 * Returns the id of the scene that contains this activeAreas list.
	 * 
	 * @return Parent scene id
	 */
	public String getParentSceneId( ) {
		return sceneDataControl.getId( );
	}

	@Override
	public Object getContent( ) {
		return trajectory;
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] { };
	}

	@Override
	public boolean canAddElement( int type ) {
		// It can always add new barrier
		return false;
	}

	@Override
	public boolean canBeDeleted( ) {
		return false;
	}

	@Override
	public boolean canBeMoved( ) {
		return false;
	}

	@Override
	public boolean canBeRenamed( ) {
		return false;
	}

	/**
	 * Add a new node to the trajectory
	 * 
	 * @param x The position along the x-axis of the node
	 * @param y The position along the y-axis of the node
	 * @return Boolean indicating if the node was added
	 */
	public boolean addNode(int x, int y) {
		if (trajectory == null) {
			return false;
		}
		
		Controller.getInstance().addTool(new AddTrajectoryNodeTool(trajectory, this, x, y, sceneDataControl));
		
		
		return true;
	}
	
	/**
	 * Add a new side to the trajectory
	 * 
	 * @param startNode the start node data control of the side
	 * @param endNode the end node data control of the side
	 * @return Boolean indicating if the side was added
	 */
	public boolean addSide(NodeDataControl startNode, NodeDataControl endNode) {
		if (startNode == endNode)
			return false;
		
		Controller.getInstance().addTool(new AddTrajectorySideTool(startNode, endNode, trajectory, this, sceneDataControl));

		return true;
	}
	
	@Override
	public boolean addElement( int type ) {
		boolean elementAdded = false;

		return elementAdded;
	}

	@Override
	public boolean deleteElement( DataControl dataControl , boolean askConfirmation) {
		boolean elementDeleted = false;

		if (nodeDataControlList.contains(dataControl)) {
			Controller.getInstance().addTool(new DeleteTrajectoryNodeTool(dataControl, trajectory, this));
			
			return true;
		}
		if (sideDataControlList.contains(dataControl)) {
			Controller.getInstance().addTool(new DeleteTrajectorySideTool((SideDataControl) dataControl, trajectory, this));
			
			trajectory.getSides().remove((Side) dataControl.getContent());
			sideDataControlList.remove(dataControl);
			//controller.dataModified();
			return true;
		}
		
		return elementDeleted;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;

		return elementMoved;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		boolean elementMoved = false;

		return elementMoved;
	}

	@Override
	public String renameElement( String name ) {
		return null;
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
		for (NodeDataControl nodeDataControl: nodeDataControlList)
			nodeDataControl.updateVarFlagSummary(varFlagSummary);
		for (SideDataControl sideDataControl: sideDataControlList)
			sideDataControl.updateVarFlagSummary(varFlagSummary);
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean valid = true;

		for( int i = 0; i < nodeDataControlList.size( ); i++ ) {
			String activeAreaPath = currentPath + " >> " + TextConstants.getElementName( Controller.NODE ) + " #" + ( i + 1 );
			valid &= nodeDataControlList.get( i ).isValid( activeAreaPath, incidences );
		}
		for( int i = 0; i < sideDataControlList.size( ); i++ ) {
			String activeAreaPath = currentPath + " >> " + TextConstants.getElementName( Controller.SIDE ) + " #" + ( i + 1 );
			valid &= sideDataControlList.get( i ).isValid( activeAreaPath, incidences );
		}

		return valid;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		for (NodeDataControl nodeDataControl : nodeDataControlList)
			count += nodeDataControl.countAssetReferences(assetPath);
		for (SideDataControl sideDataControl : sideDataControlList)
			count += sideDataControl.countAssetReferences(assetPath);

		return count;
	}

	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		
		for (NodeDataControl nodeDataControl : nodeDataControlList)
			nodeDataControl.getAssetReferences( assetPaths, assetTypes );
		for (SideDataControl sideDataControl : sideDataControlList)
			sideDataControl.getAssetReferences( assetPaths, assetTypes );
				
	}
	
	@Override
	public void deleteAssetReferences( String assetPath ) {
		for (NodeDataControl nodeDataControl : nodeDataControlList)
			nodeDataControl.deleteAssetReferences( assetPath );
		for (SideDataControl sideDataControl : sideDataControlList)
			sideDataControl.deleteAssetReferences( assetPath );
	}

	@Override
	public int countIdentifierReferences( String id ) {
		int count = 0;

		for (NodeDataControl nodeDataControl : nodeDataControlList)
			count += nodeDataControl.countIdentifierReferences( id );
		for (SideDataControl sideDataControl : sideDataControlList)
			count += sideDataControl.countIdentifierReferences( id );

		return count;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		for (NodeDataControl nodeDataControl : nodeDataControlList)
			nodeDataControl.replaceIdentifierReferences( oldId, newId );
		for (SideDataControl sideDataControl : sideDataControlList)
			sideDataControl.replaceIdentifierReferences( oldId, newId );
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		for (NodeDataControl nodeDataControl : nodeDataControlList)
			nodeDataControl.deleteIdentifierReferences( id );
		for (SideDataControl sideDataControl : sideDataControlList)
			sideDataControl.deleteIdentifierReferences( id );
	}

	@Override
	public boolean canBeDuplicated( ) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Boolean indicating if there is a trajectory
	 * 
	 * @return True if it has a trajectory
	 */
	public boolean hasTrajectory() {
		return trajectory != null;
	}

	/**
	 * Set the initial node of the trajectory to the given one
	 * 
	 * @param nodeDataControl The new initial node data control
	 */
	public void setInitialNode(NodeDataControl nodeDataControl) {
		Controller.getInstance().addTool(new SetTrajectoryInitialNodeTool(trajectory, this, nodeDataControl));
	}

	/**
	 * Returns the initial node data control
	 * 
	 * @return the initial node data control
	 */
	public NodeDataControl getInitialNode() {
		return initialNode;
	}

	@Override
	public void recursiveSearch() {
		// TODO Auto-generated method stub
		
	}
	
	
}
