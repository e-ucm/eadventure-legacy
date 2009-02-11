package es.eucm.eadventure.editor.gui.treepanel;

import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

public class TreeNodeControl {
	
	private static TreeNodeControl instance;
	
	private TreeNode root;
	
	public static TreeNodeControl getInstance() {
		if (instance == null)
			instance = new TreeNodeControl();
		return instance;
	}
	
	public void changeTreeNode(Object object) {
		root.changeTreeNodeForObject(object);
	}
	
	public void setRoot(TreeNode root) {
		this.root = root;
	}
	
}
