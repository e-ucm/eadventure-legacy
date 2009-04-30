package es.eucm.eadventure.engine.core.control.functionaldata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.InfluenceArea;
import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.data.chapter.Trajectory.Node;
import es.eucm.eadventure.common.data.chapter.Trajectory.Side;

/**
 * Functional Trajectory
 *
 * @author Eugenio Marchiori
 */
public class FunctionalTrajectory {

	/**
	 * The trajectory
	 */
	private Trajectory trajectory;
	
	/**
	 * The node the player is currently at
	 */
	private Node currentNode;
	
	/**
	 * The side the player is currently at
	 */
	private Side currentSide;
	
	/**
	 * The path the player is currently following
	 */
	private List<FunctionalSide> currentPath;
	
	/**
	 * The nearest point to the desired one along the x-axis the player can reach
	 */
	private int nearestX;
	
	/**
	 * The nearest point to the desired one along the y-axis the player con reach
	 */
	private int nearestY;
	
	/**
	 * The speed along the x-axis
	 */
	private float speedX;
	
	/**
	 * The speed along the y-axis
	 */
	private float speedY;
	
	/**
	 * The scale the player must take in the current position
	 */
	private float scale = 1.0f;
	
	/**
	 * Indicates if there is a trajectory that gets to the influece area of the element
	 */
	private boolean getsTo;
	
	/**
	 * List of the barriers in the scene
	 */
	private List<FunctionalBarrier> barriers;
	
	/**
	 * List of the functional sides of the trajectory
	 */
	private List<FunctionalSide> sides;
	
	/**
	 * The element the player wants to get to (can be null if a point is given)
	 */
	private FunctionalElement destinationElement;
	
	/**
	 * Create a new FunctionalTrajectory form a Trajectory
	 * 
	 * @param trajectory the trajectory
	 * @param barriers 
	 */
	public FunctionalTrajectory(Trajectory trajectory, ArrayList<FunctionalBarrier> barriers) {
		this.trajectory = trajectory;
		sides = new ArrayList<FunctionalSide>();
		this.barriers = barriers;
		if (trajectory != null) {
			for (Side side : trajectory.getSides()) {
				sides.add(new FunctionalSide(side, trajectory, false));
				sides.add(new FunctionalSide(side, trajectory, true));
			}
			currentSide = null;
			currentNode = trajectory.getInitial();
		}
	}
	
	/**
	 * Indicates if it has a trajectory
	 * 
	 * @return boolean indicating if it has a trajectory
	 */
	public boolean hasTrajectory() {
		return trajectory != null;
	}
		
	/**
	 * Change the current path the trajectory must follow to the one that gets
	 * to the point nearest to the given one
	 *  
	 * @param fromX The present position along the x-axis of the player
	 * @param fromY The present position along the y-axis of the player
	 * @param toX The desired position along the x-axis for the player
	 * @param toY The desired position along the y-axis for the player
	 */
	public void updatePathToNearestPoint(float fromX, float fromY, int toX, int toY) {
		this.currentPath = pathToNearestPoint(fromX, fromY, toX, toY);
	}
	
	/**
	 * Returns a path (list of FunctionalSides) from the a point to another. If there is
	 * a destinationElement the destination point is ignored.
	 * 
	 * @param fromX The current position along the x-axis
	 * @param fromY The current position along the y-axis
	 * @param toX The current position along the x-axis
	 * @param toY The current position along the y-axis
	 * @return The path to the destination
	 */
	private List<FunctionalSide> pathToNearestPoint(float fromX, float fromY, int toX, int toY) {		
		List<FunctionalSide> currentSides = getCurrentValidSides();
		
		List<FunctionalPath> tempPaths = new ArrayList<FunctionalPath>();
		
		for (FunctionalSide currentSide : currentSides) {
			List<FunctionalSide> tempSides = new ArrayList<FunctionalSide>();
			tempSides.add(currentSide);
			float dist = getDistance(fromX, fromY, currentSide.getEndNode().getX(), currentSide.getEndNode().getY());
			FunctionalPath newPath = new FunctionalPath(dist, Float.MAX_VALUE, tempSides);
			tempPaths.add(newPath);
		}
		
		List<FunctionalPath> fullPathList = getFullPathList(tempPaths);
		
		List<FunctionalPath> validPaths = getValidPaths(fullPathList, fromX, fromY, toX, toY);
			
		Collections.sort(validPaths);
		
		if (validPaths.size() > 0) {
			FunctionalPath bestPath = validPaths.get(validPaths.size() - 1);
			this.nearestX = (int) bestPath.getDestX();
			this.nearestY = (int) bestPath.getDestY();
			this.currentNode = null;
			this.currentSide = bestPath.getSides().get(0).getSide();
			this.getsTo = bestPath.isGetsTo();
			return bestPath.getSides();
		} else {
			this.currentNode = this.trajectory.getInitial();
			this.currentSide = null;
			this.nearestX = currentNode.getX();
			this.nearestY = currentNode.getY();
			this.getsTo = false;
			return new ArrayList<FunctionalSide>();
		}
	}

	/**
	 * Get the distance from one point to another.
	 * 
	 * @param x1 The position along the x-axis of the first point
	 * @param y1 The position along the y-axis of the first point
	 * @param x2 The position along the x-axis of the second point
	 * @param y2 The position along the y-axis of the second point
	 * @return The distance between to given points
	 */
	private float getDistance(float x1, float y1, float x2, float y2) {
		double xsq = Math.pow(x1 - x2, 2);
		double ysq = Math.pow(y1 - y2, 2);
		return (float) Math.sqrt(xsq + ysq);	
	}
	
	/**
	 * Returns a list of the valid paths (paths that get to the desired destination) from a list of all
	 * the possible paths form the starting position. If an element is set as the destination, the valid
	 * paths will be those that get to the influence area of said element.
	 * 
	 * @param fullPathList A list of the paths from the current position
	 * @param fromX The current position along the x-axis
	 * @param fromY The current position along the y-axis
	 * @param toX The destination position along the x-axis
	 * @param toY The destination position along the y-axis
	 * @return A list with all the paths that get to the destination.
	 */
	private List<FunctionalPath> getValidPaths(List<FunctionalPath> fullPathList, float fromX, float fromY, int toX, int toY) {
		List<FunctionalPath> validPaths = new ArrayList<FunctionalPath>();
		
		for (FunctionalPath tempPath : fullPathList) {
			FunctionalPath newPath = new FunctionalPath(0, Float.MAX_VALUE, new ArrayList<FunctionalSide>());
			float length = getDistance(fromX, fromY, tempPath.getSides().get(0).getEndNode().getX(), tempPath.getSides().get(0).getEndNode().getY());
			newPath.addSide(length, Float.MAX_VALUE, tempPath.getSides().get(0));
			
			float posX = fromX;
			float posY = fromY;			
			
			boolean end = false;
			int sideNr = 1;
			while (!end && sideNr <= tempPath.getSides().size()) {
				Node endNode = newPath.getSides().get(sideNr - 1).getEndNode();
				float deltaX = endNode.getX() - posX;
				float deltaY = endNode.getY() - posY;
				
				int delta = (int) (Math.abs(deltaX) > Math.abs(deltaY) ? Math.abs(deltaX) : Math.abs(deltaY));
				
				for (int i = 0; i < delta && !end; i++) {
					posY = posY + deltaY / delta;
					posX = posX + deltaX / delta;
					if (inBarrier(posX, posY))
						end = true;
					else if (destinationElement != null && inInfluenceArea(posX, posY)) {
						float dist = getDistance(posX, posY, destinationElement.getX(), destinationElement.getY());
						newPath.updateUpTo(dist, posX, posY);
						newPath.setGetsTo(true);
					} else if (destinationElement == null){
						float dist = getDistance(posX, posY, toX, toY);
						newPath.updateUpTo(dist, posX, posY);
					}
				}
				
				validPaths.add(newPath);
				if (sideNr < tempPath.getSides().size()) {
					newPath = newPath.newFunctionalPath(tempPath.getSides().get(sideNr).getLenght(), Float.MAX_VALUE, tempPath.getSides().get(sideNr));
					posX = tempPath.getSides().get(sideNr).getStartNode().getX();
					posY = tempPath.getSides().get(sideNr).getStartNode().getY();
				}
				sideNr++;
			}
		}
		
		return validPaths;
	}
	
	/**
	 * Returns true if the point is inside a barrier.
	 * 
	 * @param posX the position along the x-axis
	 * @param posY the position along the y-axis
	 * @return True if the point is inside a barrier
	 */
	private boolean inBarrier(float posX, float posY) {
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
	private boolean inInfluenceArea(float posX, float posY) {
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
		
	/**
	 * Get all the possible paths from the list of full paths.
	 * 
	 * @param tempPaths A list of the full paths
	 * @return A list of all the possible paths for the givven full paths
	 */
	private List<FunctionalPath> getFullPathList(List<FunctionalPath> tempPaths) {
		List<FunctionalPath> fullPathList = new ArrayList<FunctionalPath>();
		
		while(!tempPaths.isEmpty()) {	
			FunctionalPath originalPath = tempPaths.get(0);
			tempPaths.remove(0);
			
			FunctionalSide lastSide = originalPath.getSides().get(originalPath.getSides().size() - 1);
	
			boolean continues = false;
			for (FunctionalSide side : sides) {
				if (side.getSide() != lastSide.getSide() && side.getStartNode().getID().equals(lastSide.getEndNode().getID())) {
					FunctionalPath temp = originalPath.newFunctionalPath(side.getLenght(), 0, side);
					if (temp != null) {
						tempPaths.add(temp);
						continues = true;
					}
				}
			}
			if (!continues)
				fullPathList.add(originalPath);
		}
		
		return fullPathList;
	}
	
	private List<FunctionalSide> getCurrentValidSides() {
		List<FunctionalSide> tempList = new ArrayList<FunctionalSide>();
		if (currentNode != null) {
			for (FunctionalSide side : sides) {
				if (side.getStartNode() == currentNode)
					tempList.add(side);
			}
		} else {
			for (FunctionalSide side : sides) {
				if (side.getSide() == currentSide)
					tempList.add(side);
			}
		}
		return tempList;
	}
	
	/**
	 * Updates the value of speedX, speedY and scale for the player to move
	 * 
	 * @param elapsedTime The time elapsed since the last update
	 * @param x The position of the player along the x-axis
	 * @param y The position of the player along the y-axis
	 * @param speed The linear speed of the player
	 */
	public void updateSpeeds(long elapsedTime, float x, float y, float speed) {
		if (currentPath != null) {
			Node nextNode = getNextNode();
			moveInDirectionToTheNode(elapsedTime, x, y, speed, nextNode);
		} else {
			speedX = 0;
			speedY = 0;
		}
	}
	
	/**
	 * Recalculate speedX, speedY and scale when the player must move in
	 * the direction of a given node
	 * 
	 * @param elapsedTime The time elapsed since the last update
	 * @param x The position of the player along the x-axis
	 * @param y The position of the player along the y-axis
	 * @param nextNode the node the player must reach
 	 * @param speed The linear speed of the player
	 */
	private void moveInDirectionToTheNode(long elapsedTime, float x, float y,
			float speed, Node nextNode) {
		double dist = getDistance(nextNode.getX(), nextNode.getY(), x, y);
		float distX = nextNode.getX() - x;
		float distY = nextNode.getY() - y;		
		double rectDist = Math.abs(distX) + Math.abs(distY);
		double dist2 = getDistance(nearestX, nearestY, x, y);
		if (dist2 <= 10) {
			speedX = 0;
			speedY = 0;
			currentPath = null;
			nearestX = (int) x;
			nearestY = (int) y;
		} else if (dist >= speed * elapsedTime / 1000) {
			speedX = (float) (distX / rectDist * speed);
			speedY = (float) (distY / rectDist * speed);
		} else {
			FunctionalSide currentFunctionalSide = null;
			for (FunctionalSide side : currentPath) {
				if (side.getSide() == currentSide)
					currentFunctionalSide = side;
			}
			if (currentPath.indexOf(currentFunctionalSide) < currentPath.size() - 1) {
				currentSide = currentPath.get(currentPath.indexOf(currentFunctionalSide) + 1).getSide();
			} else
				currentPath = null;
			updateSpeeds(elapsedTime, x, y, speed);
		}
		
		Node endNode = nextNode;
		Node startNode;
		if (currentSide.getIDEnd().equals(nextNode.getID())) {
			startNode = trajectory.getNodeForId(currentSide.getIDStart());
		} else {
			startNode = trajectory.getNodeForId(currentSide.getIDEnd());
		}
		updateScale(dist, startNode, endNode);
	}

	
	/**
	 * Update the scale of the player, when dist2 has been covered between
	 * the start and end node.
	 * 
	 * @param dist2 The distance already covered
	 * @param startNode the start node of the side
	 * @param endNode the end node of the side
	 */
	private void updateScale(double dist2, Node startNode, Node endNode) {
		scale = (float) (startNode.getScale() * dist2 / currentSide.getLength() + endNode.getScale() * (1 - (dist2 / currentSide.getLength())));
	}

	/**
	 * Get the next node along the path
	 * 
	 * @return the next node along the path
	 */
	private Node getNextNode() {
		FunctionalSide currentFunctionalSide = null;
		for (FunctionalSide side : currentPath) {
			if (side.getSide() == currentSide)
				currentFunctionalSide = side;
		}
		if (currentFunctionalSide == null) 
			return null;
		return currentFunctionalSide.getEndNode();
	}
	
	/**
	 * Returns the value of speedX
	 * @return the value of speedX
	 */
	public float getSpeedX() {
		return speedX;
	}

	/**
	 * Returns the value of speedY
	 * 
	 * @return the value of speedY
	 */
	public float getSpeedY() {
		return speedY;
	}

	/**
	 * Returns the x-axis value of the initial node
	 * 
	 * @return the x-axis value of the initial node
	 */
	public float getInitialX() {
		return (float) trajectory.getInitial().getX();
	}

	/**
	 * Returns the y-axis value of the initial node
	 * 
	 * @return the y-axis value of the initial node
	 */
	public float getInitialY() {
		return (float) trajectory.getInitial().getY();
	}

	/**
	 * Returns the value of scale
	 * 
	 * @return the value of scale
	 */
	public float getScale() {
		return scale;
	}

	public void setDestinationElement(FunctionalElement element) {
		this.destinationElement = element;
	}
	
	public boolean canGetTo() {
		return getsTo;
	}

	/**
	 * Gets the node nearest to the given point and sets it as the
	 * current node in the trajectory.
	 * 
	 * @param destinyX The position along the x-axis
	 * @param destinyY The postiion along the y-axis
	 * @return
	 */
	public Node changeInitialNode(int destinyX, int destinyY) {
		currentSide = null;
		float minDist = Float.MAX_VALUE;
		for (Node node : trajectory.getNodes()) {
			float dist = getDistance(node.getX(), node.getY(), destinyX, destinyY);
			if (dist < minDist) {
				currentNode = node;
				minDist = dist;
			}
		}
		return currentNode;
	}
}
