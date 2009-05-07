package es.eucm.eadventure.engine.core.control.functionaldata;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.InfluenceArea;
import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.data.chapter.Trajectory.Node;
import es.eucm.eadventure.common.data.chapter.Trajectory.Side;

public class FunctionalSide {

	private Side side;
	
	private float length;
	
	private Node startNode;
	
	private Node endNode;
	
	public FunctionalSide(Side side, Trajectory trajectory, boolean inverted) {
		this.side = side;
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
	
	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof FunctionalSide)) {
			return false;
		} else if (this == other) {
			return true;
		} else {
			FunctionalSide temp = (FunctionalSide) other;
			if (temp.getStartNode() == getStartNode() && temp.getEndNode() == getEndNode())
				return true;
			return false;
		}
	}

	public boolean end;
	public float dist;
	public float posX, posY;
	public boolean getsTo;
	
	public void updateMinimunDistance(int toX, int toY, FunctionalElement destinationElement, List<FunctionalBarrier> barriers) {
		end = false;
		dist = Float.MAX_VALUE;
		getsTo = false;
		posX = startNode.getX();
		posY = startNode.getY();
		
		float posX = startNode.getX();
		float posY = startNode.getY();
		
		float deltaX = endNode.getX() - startNode.getX();
		float deltaY = endNode.getY() - startNode.getY();
		
		int delta = (int) (Math.abs(deltaX) > Math.abs(deltaY) ? Math.abs(deltaX) : Math.abs(deltaY));
		
		for (int i = 0; i < delta && !end; i++) {
			posY = posY + deltaY / delta;
			posX = posX + deltaX / delta;
			if (inBarrier(posX, posY, barriers)){
				end = true;
			}
			else if (destinationElement != null && inInfluenceArea(posX, posY, destinationElement)) {
				dist = FunctionalTrajectory.getDistanceFast(posX, posY, destinationElement.getX(), destinationElement.getY());
				this.posX = posX;
				this.posY = posY;
				getsTo = true;
				end = true;
			} else if (destinationElement == null){
				float tempdist = FunctionalTrajectory.getDistanceFast(posX, posY, toX, toY);
				if (tempdist < dist) {
					dist = tempdist;
					this.posX = posX;
					this.posY = posY;
				}
			}
		}
		
	}
	
	/**
	 * Returns true if the point is inside a barrier.
	 * 
	 * @param posX the position along the x-axis
	 * @param posY the position along the y-axis
	 * @return True if the point is inside a barrier
	 */
	private boolean inBarrier(float posX, float posY, List<FunctionalBarrier> barriers) {
		boolean temp = false;
		for (FunctionalBarrier barrier : barriers) {
			temp = temp || barrier.isInside(posX, posY);
		}
		return temp;
	}

	/**
	 * Returns true if the given point is inside the influence area of the destination element,
	 * false in another case.
	 * 
	 * @param posX The position along the x-axis
	 * @param posY The position along the y-axis
	 * @return True if the point is inside the destination elements influence area.
	 */
	private boolean inInfluenceArea(float posX, float posY, FunctionalElement destinationElement) {
		if (destinationElement == null)
			return false;
		else {
				InfluenceArea area;
				if (destinationElement.getInfluenceArea() != null)
					area = destinationElement.getInfluenceArea();
				else
					area = new InfluenceArea(-20, -20, destinationElement.getWidth() + 40, destinationElement.getHeight() + 40);

				int x1 = (int) (destinationElement.getX() - destinationElement.getWidth() * destinationElement.getScale() / 2);
				int y1 = (int) (destinationElement.getY() - destinationElement.getHeight() * destinationElement.getScale());
				int x2 = 0;
				int y2 = 0;
				if (!area.isExists()) {
					x1 = x1 - 20;
					y1 = y1 - 20;
					x2 = (int) (x1 + destinationElement.getWidth() * destinationElement.getScale() + 40);
					y2 = (int) (y1 + destinationElement.getHeight() * destinationElement.getScale() + 40);
				} else {
					x1 = x1 + area.getX();
					y1 = y1 + area.getY();
					x2 = x1 + area.getWidth();
					y2 = y1 + area.getHeight();
				}
				
				if (posX > x1 && posX < x2 && posY > y1 && posY < y2)
					return true;
		}
		return false;
	}

}
