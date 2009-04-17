package es.eucm.eadventure.editor.gui.treepanel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreePath;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

/**
 * Singleton class used to move the users view along
 * the tree.
 * 
 * @author Eugenio Marchiori
 */
public class TreeNodeControl {
	
	/**
	 * The instance of this class
	 */
	private static TreeNodeControl instance;
	
	/**
	 * The root tree node
	 */
	private TreeNode root;
	
	/**
	 * Back list of paths of visited tree nodes
	 */
	private List<TreePath> backList;
	
	/**
	 * Forward list of paths of visited tree nodes
	 */
	private List<TreePath> forwardList;
	
	/**
	 * Default constructor
	 */
	private TreeNodeControl() {
		backList = new ArrayList<TreePath>();
		forwardList = new ArrayList<TreePath>();
	}
	
	/**
	 * Get the instance of the tree node control
	 * @return the instance of the class
	 */
	public static TreeNodeControl getInstance() {
		if (instance == null)
			instance = new TreeNodeControl();
		return instance;
	}
	
	/**
	 * Change tree node to the one that has the
	 * object given in the parameters as its data control
	 * 
	 * @param object The data control of the tree node
	 */
	public void changeTreeNode(Object object) {
		root.changeTreeNodeForObject(object);
	}
	
	/**
	 * Change tree node to the one that has the given object
	 * as the content of it's data control
	 * 
	 * @param object The content of the data control of the tree node
	 */
	public void changeTreeNodeDataControlContent(Object object) {
		root.changeTreeNodeForObjectContent(object);
	}
	
	/**
	 * Set the root of the tree.
	 * 
	 * @param root The new root of the tree
	 */
	public void setRoot(TreeNode root) {
		this.root = root;
		backList.clear();
		forwardList.clear();
	}
	
	/**
	 * Go back in the list of visited tree nodes (if possible)
	 */
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
	
	/**
	 * Go forward in the list of visited tree nodes (if possible) 
	 */
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
	
	/**
	 * Add a tree path to the back list
	 * 
	 * @param path The new tree path
	 */
	public void visitPath(TreePath path) {
		if (backList.size() > 0) {
			if (!path.equals(backList.get(backList.size() - 1)))
				backList.add(path);
		} else 
			backList.add(path);
		forwardList.clear();
	}

	/**
	 * Remove the path of the back and forward lists when
	 * the data control it belonged to is deleted
	 * 
	 * @param dataControl The data control
	 */
	public void dataControlRemoved(DataControl dataControl) {
		TreePath path = root.getTreePathForObject(dataControl);
		
		if (path != null) {
			while(backList.remove(path)){};
			while(forwardList.remove(path)){};
		}
	}
}

