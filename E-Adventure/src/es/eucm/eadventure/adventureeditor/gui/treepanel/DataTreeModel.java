package es.eucm.eadventure.adventureeditor.gui.treepanel;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.TreeNode;

/**
 * The model for the tree, it holds a TreeNode root.
 * 
 * @author Bruno Torijano Bueno
 */
class DataTreeModel implements TreeModel {

	/**
	 * Root of the tree.
	 */
	TreeNode root;

	/**
	 * Constructor.
	 * 
	 * @param root
	 *            Root node
	 */
	public DataTreeModel( TreeNode root ) {
		this.root = root;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#addTreeModelListener(javax.swing.event.TreeModelListener)
	 */
	public void addTreeModelListener( TreeModelListener l ) {}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
	 */
	public Object getChild( Object parent, int index ) {
		TreeNode node = (TreeNode) parent;
		return node.getChildAt( index );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
	 */
	public int getChildCount( Object parent ) {
		TreeNode node = (TreeNode) parent;
		return node.getChildCount( );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#getIndexOfChild(java.lang.Object, java.lang.Object)
	 */
	public int getIndexOfChild( Object parent, Object child ) {
		TreeNode parentTreeNode = (TreeNode) parent;
		TreeNode childTreeNode = (TreeNode) child;
		return parentTreeNode.getIndexOfChild( childTreeNode );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#getRoot()
	 */
	public Object getRoot( ) {
		return root;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#isLeaf(java.lang.Object)
	 */
	public boolean isLeaf( Object node ) {
		TreeNode treeNode = (TreeNode) node;
		return treeNode.getChildCount( ) == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#removeTreeModelListener(javax.swing.event.TreeModelListener)
	 */
	public void removeTreeModelListener( TreeModelListener l ) {}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#valueForPathChanged(javax.swing.tree.TreePath, java.lang.Object)
	 */
	public void valueForPathChanged( TreePath path, Object newValue ) {}
}
