package es.eucm.eadventure.common.data.chapter;

import java.util.ArrayList;
import java.util.List;

public class Trajectory {
	
	List<Node> nodes;
	
	List<Side> sides;
	
	Node initial;
	
	public Trajectory() {
		nodes = new ArrayList<Node>();
		sides = new ArrayList<Side>();
		initial = null;
	}
	
	public Node addNode(int x, int y, float scale) {
		return addNode(generateID(), x, y, scale);
	}
	
	public Node addNode(String id, int x, int y, float scale) {
		Node node = new Node(id, x, y, scale);
		if (nodes.contains(node)) {
			node = nodes.get(nodes.indexOf(node));
		} else {
			nodes.add(node);
		}
		if (nodes.size() == 1) {
			initial = nodes.get(0);
		}
		return node;		 	
	}
	
	public Side addSide(String idStart, String idEnd) {
		if  (idStart.equals(idEnd))
			return null;
		Side side = new Side(idStart, idEnd);
		if (sides.contains(side)) {
			return null;
		} else {
			sides.add(side);
		}
		return side;
	}
	
	public void removeNode(int x, int y) {
		Node node = new Node("id", x, y, 1.0f);
		if (nodes.contains(node)) {
			node = nodes.get(nodes.indexOf(node));
			for (int i = 0; i < sides.size();) {
				Side side = sides.get(i);
				if (side.getIDEnd().equals(node.getID()) || side.getIDStart().equals(node.getID()))
					sides.remove(i);
				else
					i++;
			}
		}
	}
	
	public List<Node> getNodes() {
		return nodes;
	}
	
	public List<Side> getSides() {
		return sides;
	}
		
	private String generateID() {
		return "";
	}
	
	public class Node {
		private String id;
		
		private int x;
		
		private int y;
		
		private float scale;
		
		public Node(String id, int x, int y, float scale) {
			this.id = id;
			this.x = x;
			this.y = y;
			this.scale = scale;
		}
		
		public String getID() {
			return id;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
		
		public float getScale() {
			return scale;
		}
		
		public void setScale(float scale) {
			this.scale = scale;
		}
		
		public boolean equals(Object o) {
			if (o == null)
				return false;
			if (o instanceof Node) {
				Node node = (Node) o;
				if (node.id.equals(this.id))
					return true;
				if (node.x == this.x && node.y == this.y)
					return true;
			}
			return false;
		}

		public void setValues(int x, int y, float scale) {
			this.x = x;
			this.y = y;
			this.scale = scale;
		}
	}
	
	public class Side {
		private String idStart;
		
		private String idEnd;
		
		public Side(String idStart, String idEnd) {
			this.idStart = idStart;
			this.idEnd = idEnd;
		}
		
		public String getIDStart() {
			return idStart;
		}
		
		public String getIDEnd() {
			return idEnd;
		}
		
		public boolean equals(Object o) {
			if (o == null)
				return false;
			if (o instanceof Side) {
				Side side = (Side) o;
				if (side.idEnd.equals(this.idEnd) && side.idStart.equals(this.idStart))
					return true;
			}
			return false;
		}
	}

	public Node getNodeForId(String id) {
		for (Node node : nodes) {
			if (id.equals(node.id))
				return node;
		}
		return null;
	}
	
	public void setInitial(String id) {
		initial = getNodeForId(id);
	}
	
	public Node getInitial() {
		return initial;
	}
}
