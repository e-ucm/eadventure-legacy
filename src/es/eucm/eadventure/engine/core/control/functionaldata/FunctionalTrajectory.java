package es.eucm.eadventure.engine.core.control.functionaldata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	private List<Side> currentPath;
	
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
	 * Create a new FunctionalTrajectory form a Trajectory
	 * 
	 * @param trajectory the trajectory
	 */
	public FunctionalTrajectory(Trajectory trajectory) {
		this.trajectory = trajectory;
		if (trajectory != null) {
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
	 * Returns the node where the player is currently at
	 * 
	 * @return the currentNode
	 */
	public Node currentNode() {
		return currentNode;
	}
	
	/**
	 * Returns the side where the player is currently at
	 * 
	 * @return the currentSide
	 */
	public Side currentSide() {
		return currentSide;
	}
	
	/**
	 * Returns the path to the point nearest to the one given
	 *  
	 * @param fromX The present position along the x-axis of the player
	 * @param fromY The present position along the y-axis of the player
	 * @param toX The desired position along the x-axis for the player
	 * @param toY The desired position along the y-axis for the player
	 * @return A path as a list of sides
	 */
	public List<Side> getPathToNearestPoint(float fromX, float fromY, int toX, int toY) {
		Side side = getSideWithShortestDistance(toX, toY);
		return getPathToSide(fromX, fromY, side);
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
			if (nextNode == null) {
				moveAlongCurrentSide(elapsedTime, x, y, speed);
			} else {
				moveInDirectionToTheNode(elapsedTime, x, y, speed, nextNode);
			}
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
		float distX = nextNode.getX() - x;
		float distY = nextNode.getY() - y;
		float dist = Math.abs(distX) + Math.abs(distY);
		double dist2 = Math.sqrt(distX * distX + distY * distY);
		if (dist2 > speed * elapsedTime / 1000) {
			speedX = (float) (distX / dist * speed);
			speedY = (float) (distY / dist * speed);
		} else {
			currentSide = currentPath.get(currentPath.indexOf(currentSide) + 1);
			updateSpeeds(elapsedTime, x, y, speed);
		}
		
		Node endNode = nextNode;
		Node startNode;
		if (currentSide.getIDEnd().equals(nextNode.getID())) {
			startNode = trajectory.getNodeForId(currentSide.getIDStart());
		} else {
			startNode = trajectory.getNodeForId(currentSide.getIDEnd());
		}
		updateScale(dist2, startNode, endNode);
	}

	/**
	 * Recalculate speedX, speedY and scale when the player must move along the
	 * current side.
	 * 
	 * @param elapsedTime The time elapsed since the last update
	 * @param x The position of the player along the x-axis
	 * @param y The position of the player along the y-axis
 	 * @param speed The linear speed of the player
	 */
	private void moveAlongCurrentSide(long elapsedTime, float x, float y, double speed) {
		float distX = nearestX - x;
		float distY = nearestY - y;
		double dist2 = Math.sqrt(distX * distX + distY * distY);
		if (dist2 < elapsedTime * speed / 1000) {
			speedX = 0;
			speedY = 0;
			currentPath = null;
		} else {
			float dist = Math.abs(distX) + Math.abs(distY);
			speedX = (float) (distX / dist * speed);
			speedY = (float) (distY / dist * speed);
		}
		
		Node startNode = trajectory.getNodeForId(currentSide.getIDStart());
		Node endNode = trajectory.getNodeForId(currentSide.getIDEnd());
		
		distX = startNode.getX() - nearestX;
		distY = startNode.getY() - nearestY;
		float distStart = distX * distX + distY * distY;
		distX = endNode.getX() - nearestX;
		distY = endNode.getY() - nearestY;
		float distEnd = distX * distX + distY * distY;
		
		if (distEnd > distStart) {
			Node temp = startNode;
			startNode = endNode;
			endNode = temp;
		}
		updateScale(dist2, startNode, endNode);
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
		int i = currentPath.indexOf(currentSide);
		if (i == currentPath.size() - 1) 
			return null;
		Side nextSide = currentPath.get(i + 1);
		String temp = nextSide.getIDEnd();		
		if (currentSide.getIDEnd().equals(temp) || currentSide.getIDStart().equals(temp))
			return trajectory.getNodeForId(temp);
		temp = nextSide.getIDStart();		
		if (currentSide.getIDEnd().equals(temp) || currentSide.getIDStart().equals(temp))
			return trajectory.getNodeForId(temp);
		return null;
	}
	
	/**
	 * Get a path to a destiny position from a given side
	 * 
	 * @param f Destiny position along the x-axis
	 * @param g Destiny position along the y-axis
	 * @param side The initial side
	 * @return A path as a list of sides
	 */
	private List<Side> getPathToSide(float f, float g, Side side) {
		List<Side> tempList = null;
		String obj0 = side.getIDStart();
		String obj1 = side.getIDEnd();

		if (currentNode != null && (currentNode.getID().equals(obj0) || currentNode.getID().equals(obj1))) {
			tempList = new ArrayList<Side>();
			tempList.add(side);
			currentSide = side;
			return tempList;
		} else {
			if (currentNode != null) {
				List<Side> list = getAllSidesForNode(currentNode);
				for (Side tempSide : list) {
					tempList = getPath(tempSide, side);
					if (tempList != null) {
						currentNode = null;
						currentSide = tempList.get(0);
						return tempList;
					}
				}
			} else {
				tempList = getPath(currentSide, side);
			}
		}
		
		return tempList;
	}
	
	/**
	 * Get the path from a side to another. It uses something like a dijkstra
	 * algorithm
	 * 
	 * @param current The current or initial side
	 * @param dest The destination side
	 * @return A path as a list of sides
	 */
	private List<Side> getPath(Side current, Side dest) {
		List<Side> temp = new ArrayList<Side>();
		temp.add(current);
		if (current == dest)
			return temp;
		
		List<Path> pathList = new ArrayList<Path>();
		pathList.add(new Path(0, temp));

		// TODO the length still to be traveled of the current side is not taken into account...

		while (pathList.size() > 0) {
			Path currentPath = pathList.get(pathList.size() - 1);
			current = currentPath.getSides().get(currentPath.getSides().size() - 1);
			pathList.remove(pathList.size() - 1);
			if (current == dest)
				return currentPath.getSides();
			
			for (Side side : getAllSidesForNode(trajectory.getNodeForId(current.getIDEnd()))) {
				if (side != current) {
					Path newPath = new Path(currentPath.getLength() + side.getLength(), currentPath.getSides());
					newPath.getSides().add(side);
					pathList.add(newPath);
				}
			}

			for (Side side : getAllSidesForNode(trajectory.getNodeForId(current.getIDStart()))) {
				if (side != current) {
					Path newPath = new Path(currentPath.getLength() + side.getLength(), currentPath.getSides());
					newPath.getSides().add(side);
					pathList.add(newPath);
				}
			}
			
			Collections.sort(pathList);
		}
		

		return null;
	}
	
	/**
	 * Private class used to order the paths by their length, used
	 * in the path searching algorithm
	 */
	private class Path implements Comparable<Path> {

		private int length;
		
		private List<Side> sides;
		
		public Path(int length, List<Side> sides) {
			this.length = length;
			this.sides = new ArrayList<Side>(sides);
		}

		public List<Side> getSides() {
			return sides;
		}
		
		public int getLength() {
			return length;
		}

		@Override
		public int compareTo(Path arg0) {
			return arg0.length - length;
		}		
	}

	/**
	 * Returns a list of all the sides that start or end in a node
	 * 
	 * @param node The given node
	 * @return a list of all the sides that start or end in a node
	 */
	private List<Side> getAllSidesForNode(Node node) {
		List<Side> temp = new ArrayList<Side>();
		for (Side side : trajectory.getSides()) {
			if (side.getIDEnd().equals(node.getID()) || side.getIDStart().equals(node.getID()))
				temp.add(side);
		}
		return temp;
	}
	
	/**
	 * Returns the side that is nearest to a given point. It also sets
	 * the value of nearestX and nearestY.
	 * 
	 * @param toX the value of the point along the x-axis
	 * @param toY the value of the point along the y-axis
	 * @return the nearest side to the point
	 */
	private Side getSideWithShortestDistance(int toX, int toY) {
		float shortestSquare = 1000000;
		Side tempSide = null;
		
		for (Side side : trajectory.getSides()) {
			Node start = trajectory.getNodeForId(side.getIDStart());
			Node end = trajectory.getNodeForId(side.getIDEnd());
			int x1 = start.getX();
			int y1 = start.getY();
			int x2 = end.getX();
			int y2 = end.getY();
			if (x1 > x2) {
				x2 = start.getX();
				y2 = start.getY();
				x1 = end.getX();
				y1 = end.getY();
			}
			int dist = x2 - x1;
			for (int j = 0; j < dist; j++) {
				float tempX = (x1 + (x2 - x1) * ((float) j / dist));
				float tempY = (y1 + (y2 - y1) * ((float) j / dist));
				float temp = (float) (Math.pow(tempX - toX, 2) + Math.pow(tempY - toY, 2)); 
				if (temp < shortestSquare) {
					tempSide = side;
					shortestSquare = temp;
					nearestX = (int) tempX;
					nearestY = (int) tempY;
				}
			}
		}
		
		return tempSide;
	}

	/**
	 * Change the current side
	 * 
	 * @param side the new current side
	 */
	public void changeCurrent(Side side) {
		currentNode = null;
		currentSide = side;
	}
	
	/**
	 * Change the current node
	 * 
	 * @param node the new current node
	 */
	public void changeCurrent(Node node) {
		currentNode = node;
		currentSide = null;
	}
	
	/**
	 * Set the value of the current path
	 * 
	 * @param path the new path as a list of sides
	 */
	public void setCurrentPath(List<Side> path) {
		this.currentPath = path;
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
}
