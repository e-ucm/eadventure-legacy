package es.eucm.eadventure.editor.control.controllers.scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.data.chapter.Trajectory.Node;
import es.eucm.eadventure.common.data.chapter.Trajectory.Side;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class TrajectoryDataControl extends DataControl {

	/**
	 * Scene controller that contains this element reference.
	 */
	private SceneDataControl sceneDataControl;

	private Trajectory trajectory;

	/**
	 * List of node controllers.
	 */
	private List<NodeDataControl> nodeDataControlList;
	
	private List<SideDataControl> sideDataControlList;
	
	private NodeDataControl initialNode;
	
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

	public List<NodeDataControl> getNodes() {
		return nodeDataControlList;
	}
	
	public List<SideDataControl> getSides() {
		return sideDataControlList;
	}
	
	
	public NodeDataControl getLastNode() {
		return nodeDataControlList.get(nodeDataControlList.size() - 1);
	}
	
	
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

	public boolean addNode(int x, int y) {
		if (trajectory == null) {
			return false;
		}
		String id = "node" + (new Random()).nextInt(10000);
		Node newNode = trajectory.addNode(id, x, y, 1.0f);
		NodeDataControl nodeDataControl = new NodeDataControl(sceneDataControl, newNode);
		nodeDataControlList.add(nodeDataControl);
		if (trajectory.getInitial() == newNode) {
			setInitialNode(nodeDataControl);
		}
		controller.dataModified();
		
		return true;
	}
	
	public boolean deleteNode(int x, int y) {
		NodeDataControl dataControl = null;
		for (NodeDataControl nodeDC : nodeDataControlList) {
			if (nodeDC.getX() == x && nodeDC.getY() == y)
				dataControl = nodeDC;
		}
		if (dataControl != null) {
			trajectory.removeNode(x, y);
			nodeDataControlList.remove(dataControl);
			controller.dataModified();
			return true;
		}
		return false;
	}
	
	public boolean addSide(NodeDataControl startNode, NodeDataControl endNode) {
		if (startNode == endNode)
			return false;
		Side side = trajectory.addSide(startNode.getID(), endNode.getID());
		if (side != null) {
			sideDataControlList.add(new SideDataControl(sceneDataControl, this, side));
			controller.dataModified();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean addElement( int type ) {
		boolean elementAdded = false;

		return elementAdded;
	}

	@Override
	public boolean deleteElement( DataControl dataControl ) {
		boolean elementDeleted = false;

		if (nodeDataControlList.contains(dataControl)) {
			trajectory.getNodes().remove((Node) dataControl.getContent());
			nodeDataControlList.remove(dataControl);
			controller.dataModified();
			return true;
		}
		if (sideDataControlList.contains(dataControl)) {
			trajectory.getSides().remove((Side) dataControl.getContent());
			sideDataControlList.remove(dataControl);
			controller.dataModified();
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
	public boolean renameElement( ) {
		return false;
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
	
	public boolean hasTrajectory() {
		return trajectory != null;
	}

	public void setHasTrajectory(boolean hasTrajectory) {
		if (hasTrajectory) {
			if (trajectory == null) {
				trajectory = new Trajectory();
				sceneDataControl.setTrajectory(trajectory);
			}
		} else {
			if (trajectory != null) {
				trajectory = null;
				sceneDataControl.setTrajectory(null);
			}
		}
	}

	public void setInitialNode(NodeDataControl nodeDataControl) {
		trajectory.setInitial(nodeDataControl.getID());
		if (initialNode != null) {
			initialNode.setInitial(false);
		}
		initialNode = nodeDataControl;
		initialNode.setInitial(true);
		controller.dataModified();
	}

	public NodeDataControl getInitialNode() {
		return initialNode;
	}
	
	
}
