package es.eucm.eadventure.editor.gui.treepanel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreePath;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

public class TreeNodeControl {
	
	private static TreeNodeControl instance;
	
	private TreeNode root;
	
	private List<TreePath> backList;
	
	private List<TreePath> forwardList;
	
	private TreeNodeControl() {
		backList = new ArrayList<TreePath>();
		forwardList = new ArrayList<TreePath>();
	}
	
	public static TreeNodeControl getInstance() {
		if (instance == null)
			instance = new TreeNodeControl();
		return instance;
	}
	
	public void changeTreeNode(Object object) {
		root.changeTreeNodeForObject(object);
	}
	
	public void changeTreeNodeDataControlContent(Object object) {
		root.changeTreeNodeForObjectContent(object);
	}
	
	public void setRoot(TreeNode root) {
		this.root = root;
		backList.clear();
		forwardList.clear();
	}
	
	public void goBack() {
		if (backList.size() > 2) {
			TreePath temp = backList.remove(backList.size() - 1);
			TreePath temp2 = backList.get(backList.size() - 1);
			List<TreePath> tempFwrd = new ArrayList<TreePath>();
			tempFwrd.addAll(forwardList);
			root.changeTreeNodeForPath(temp2);
			forwardList = tempFwrd;
			forwardList.add(temp);
		}
	}
	
	public void goForward() {
		if (forwardList.size() > 0) {
			TreePath temp = forwardList.get(forwardList.size() - 1);
			forwardList.remove(temp);
			List<TreePath> tempFwrd = new ArrayList<TreePath>();
			tempFwrd.addAll(forwardList);
			root.changeTreeNodeForPath(temp);
			forwardList = tempFwrd;
		}
	}
	
	public void visitPath(TreePath path) {
		if (backList.size() > 0) {
			if (!path.equals(backList.get(backList.size() - 1)))
				backList.add(path);
		} else 
			backList.add(path);
		forwardList.clear();
	}

	public void dataControlRemoved(DataControl dataControl) {
		TreePath path = root.getTreePathForObject(dataControl);
		
		if (path != null) {
			while(backList.remove(path)){};
			while(forwardList.remove(path)){};
		}
	}

	public TreeNode getTreeNode(DataControl dataControl) {
		TreePath path = root.getTreePathForObject(dataControl);
		if (path != null)
			return (TreeNode) path.getLastPathComponent();
		else
			return null;
	}
}

