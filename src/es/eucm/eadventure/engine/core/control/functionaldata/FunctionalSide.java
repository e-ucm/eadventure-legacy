package es.eucm.eadventure.engine.core.control.functionaldata;

import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.data.chapter.Trajectory.Node;
import es.eucm.eadventure.common.data.chapter.Trajectory.Side;

public class FunctionalSide {

	private Side side;
	
	private float length;
	
	private Node startNode;
	
	private Node endNode;
	
	public FunctionalSide(Side side, Trajectory trajectory, boolean inverted) {
		length = side.getLength();
		if (!inverted) {
			startNode = trajectory.getNodeForId(side.getIDStart());
			endNode = trajectory.getNodeForId(side.getIDEnd());
		} else {
			startNode = trajectory.getNodeForId(side.getIDEnd());
			endNode = trajectory.getNodeForId(side.getIDStart());
		}
	}
	
	public float getLenght() {
		return length;
	}
	
	public Node getStartNode() {
		return startNode;
	}
	
	public Node getEndNode() {
		return endNode;
	}

	public Side getSide() {
		return side;
	}
	
}
