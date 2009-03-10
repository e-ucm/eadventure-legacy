package es.eucm.eadventure.editor.gui.treepanel.nodes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.TreePath;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.tools.treepanel.AddChildTool;
import es.eucm.eadventure.editor.control.tools.treepanel.DeleteNodeTool;
import es.eucm.eadventure.editor.control.tools.treepanel.MoveTreeNodeDownTool;
import es.eucm.eadventure.editor.control.tools.treepanel.MoveTreeNodeUpTool;
import es.eucm.eadventure.editor.control.tools.treepanel.RenameElementTool;
import es.eucm.eadventure.editor.gui.treepanel.TreeNodeControl;
import es.eucm.eadventure.editor.gui.treepanel.TreePanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.adaptation.AdaptationProfileTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.adaptation.AdaptationProfilesTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.adaptation.AdaptationRuleTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.assessment.AssessmentProfileTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.assessment.AssessmentProfilesTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.assessment.AssessmentRuleTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.assessment.TimedAssessmentRuleTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.atrezzo.AtrezzoListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.atrezzo.AtrezzoTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.book.BookParagraphsListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.book.BookTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.book.BooksListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.book.BulletBookParagraphTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.book.ImageBookParagraphTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.book.TextBookParagraphTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.book.TitleBookParagraphTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.character.ConversationReferenceTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.character.ConversationReferencesListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.character.NPCTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.character.NPCsListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.character.PlayerTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.conversation.ConversationsListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.conversation.GraphConversationTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.conversation.TreeConversationTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.cutscene.CutscenesListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.cutscene.EndSceneTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.cutscene.SlidesceneTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.cutscene.VideosceneTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.general.ActionsListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.general.AdvancedFeaturesTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.general.ChapterTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.general.CustomActionTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.general.CustomInteractActionTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.general.ExamineActionTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.general.GiveToActionTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.general.GrabActionTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.general.NextSceneTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.general.UseActionTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.general.UseWithActionTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.globalstate.GlobalStateTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.globalstate.GlobalStatesListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.item.ItemTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.item.ItemsListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.macro.MacroTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.macro.MacrosListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.scene.ActiveAreaTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.scene.ActiveAreasListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.scene.AtrezzoReferenceTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.scene.BarrierTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.scene.BarriersListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.scene.ExitTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.scene.ExitsListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.scene.ItemReferenceTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.scene.ReferencesListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.scene.NPCReferenceTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.scene.SceneTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.scene.ScenesListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.timer.TimerTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.timer.TimersListTreeNode;

/**
 * This class contains the operations required to build a JTree panel with the data from the script.
 * 
 * @author Bruno Torijano Bueno
 * @author Eugenio Marchiori
 */
public abstract class TreeNode {

	/**
	 * Tree panel that holds this node and its children.
	 */
	private TreePanel ownerPanel;

	/**
	 * Parent node.
	 */
	protected TreeNode parent;

	/**
	 * List of node children.
	 */
	protected List<TreeNode> children;

	/**
	 * This static method loads the icons of all the TreeNodes.
	 */
	public static void loadIcons( ) {

		// General nodes
		ChapterTreeNode.loadIcon( );
		NextSceneTreeNode.loadIcon( );
		//ResourcesTreeNode.loadIcon( );

		// Scene nodes
		ExitsListTreeNode.loadIcon( );
		ExitTreeNode.loadIcon( );
		ReferencesListTreeNode.loadIcon( );
		ItemReferenceTreeNode.loadIcon( );
		NPCReferenceTreeNode.loadIcon( );
		AtrezzoReferenceTreeNode.loadIcon( );
		ScenesListTreeNode.loadIcon( );
		SceneTreeNode.loadIcon( );

		// Cutscene nodes
		CutscenesListTreeNode.loadIcon( );
		VideosceneTreeNode.loadIcon( );
		EndSceneTreeNode.loadIcon( );
		SlidesceneTreeNode.loadIcon( );

		// Book nodes
		BookParagraphsListTreeNode.loadIcon( );
		BooksListTreeNode.loadIcon( );
		BookTreeNode.loadIcon( );
		BulletBookParagraphTreeNode.loadIcon( );
		ImageBookParagraphTreeNode.loadIcon( );
		TextBookParagraphTreeNode.loadIcon( );
		TitleBookParagraphTreeNode.loadIcon( );

		// Item nodes
		ActionsListTreeNode.loadIcon( );
		ExamineActionTreeNode.loadIcon( );
		GiveToActionTreeNode.loadIcon( );
		GrabActionTreeNode.loadIcon( );
		CustomActionTreeNode.loadIcon();
		CustomInteractActionTreeNode.loadIcon();
		ItemsListTreeNode.loadIcon( );
		ItemTreeNode.loadIcon( );
		UseActionTreeNode.loadIcon( );
		UseWithActionTreeNode.loadIcon( );
		
		// Atrezzo items node
		AtrezzoTreeNode.loadIcon();
		AtrezzoListTreeNode.loadIcon();

		// Character nodes
		ConversationReferencesListTreeNode.loadIcon( );
		ConversationReferenceTreeNode.loadIcon( );
		NPCsListTreeNode.loadIcon( );
		NPCTreeNode.loadIcon( );
		PlayerTreeNode.loadIcon( );

		// Conversation nodes
		ConversationsListTreeNode.loadIcon( );
		GraphConversationTreeNode.loadIcon( );
		TreeConversationTreeNode.loadIcon( );
		
		// Assessment nodes
		AssessmentProfileTreeNode.loadIcon();
		AssessmentRuleTreeNode.loadIcon();
		TimedAssessmentRuleTreeNode.loadIcon();
		AssessmentProfilesTreeNode.loadIcon();
		
		// Adaptation nodes
		AdaptationProfileTreeNode.loadIcon();
		AdaptationRuleTreeNode.loadIcon();
		AdaptationProfilesTreeNode.loadIcon();
		
		// Advanced features nodes
		TimerTreeNode.loadIcon();
		TimersListTreeNode.loadIcon();
		AdvancedFeaturesTreeNode.loadIcon();
		ActiveAreaTreeNode.loadIcon();
		ActiveAreasListTreeNode.loadIcon();
		BarrierTreeNode.loadIcon();
		BarriersListTreeNode.loadIcon();
		EmptyTreeNode.loadIcon();
		GlobalStateTreeNode.loadIcon();
		GlobalStatesListTreeNode.loadIcon();
		MacroTreeNode.loadIcon();
		MacrosListTreeNode.loadIcon();

	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 */
	protected TreeNode( TreeNode parent ) {
		this.parent = parent;
		children = new ArrayList<TreeNode>( );
		ownerPanel = null;
	}

	/**
	 * Checks for new children in the data, and stores them in the children structure. It must be called only when an
	 * element is added.
	 * 
	 * @param type
	 *            Type of the child that was added
	 * @return The node that has been added to the tree, null if no node was created
	 */
	public abstract TreeNode checkForNewChild( int type );

	/**
	 * Checks the references to identifiers (scenes, items, characters...) to see which TreeNodes must be deleted.
	 */
	public abstract void checkForDeletedReferences( );

	/**
	 * Sets the tree panel of this tree node.
	 * 
	 * @param ownerPanel
	 *            The owner tree panel
	 */
	public void setOwnerPanel( TreePanel ownerPanel ) {
		this.ownerPanel = ownerPanel;
		spreadOwnerPanel( );
	}

	/**
	 * Spreads the owner panel to all the children of this node
	 */
	protected void spreadOwnerPanel( ) {
		for( TreeNode child : children )
			child.setOwnerPanel( ownerPanel );
	}

	/**
	 * Returns the type of node.
	 * 
	 * @return Type of node (values stored in Controller)
	 */
	protected abstract int getNodeType( );

	/**
	 * Returns the data controller of the node.
	 * 
	 * @return Content data control of the node
	 */
	public abstract DataControl getDataControl( );

	/**
	 * Returns the child count of the node.
	 * 
	 * @return Child count
	 */
	public int getChildCount( ) {
		return children.size( );
	}

	/**
	 * Returns the given child's index in the node.
	 * 
	 * @param child
	 *            Child which index we want to know
	 * @return The index of the given child, -1 if it was not found
	 */
	public int getIndexOfChild( TreeNode child ) {
		return children.indexOf( child );
	}

	/**
	 * Returns the child node at the given position.
	 * 
	 * @param index
	 *            Index of the child
	 * @return Child node
	 */
	public TreeNode getChildAt( int index ) {
		return children.get( index );
	}

	/**
	 * Returns whether the node accepts new child nodes.
	 * 
	 * @return True if the node accepts new children, false otherwise
	 */
	public boolean canAddChildren( ) {
		if (getDataControl()!=null)
		return getDataControl( ).canAddElements( );
		else
			return false;
	}

	/**
	 * Returns whether the node accepts new child nodes of the given type.
	 * 
	 * @param type
	 *            Type of the child node
	 * @return True if the node accepts new children of the given type, false otherwise
	 */
	public boolean canAddChild( int type ) {
		return getDataControl( ).canAddElement( type );
	}

	/**
	 * Returns whether the node can be deleted.
	 * 
	 * @return True if the node can be deleted, false otherwise
	 */
	public boolean canBeDeleted( ) {
		if (getDataControl()!=null)
			return getDataControl( ).canBeDeleted( );
			else
				return false;
		
	}

	/**
	 * Returns whether the node can be moved.
	 * 
	 * @return True if the node can be moved, false otherwise
	 */
	public boolean canBeMoved( ) {
		if (getDataControl()!=null)
			return getDataControl( ).canBeMoved( );
			else
				return false;
		
	}

	/**
	 * Returns whether the element can be renamed.
	 * 
	 * @return True if the node can be renamed, false otherwise
	 */
	public boolean canBeRenamed( ) {
		if (getDataControl()!=null)
			return getDataControl( ).canBeRenamed( );
			else
				return false;
		
	}

	/**
	 * Adds a new child of the given type.
	 * 
	 * @param type
	 *            Type of the child to be added
	 */
	public void addChild( int type ) {
		// If the element can be added, and the addition was successful
		AddChildTool addChildTool = new AddChildTool(this, type, ownerPanel);
		Controller.getInstance().addTool(addChildTool);
	}
		 
	/**
	 * Selects one tree�s node
	 * 
	 * @param node
	 * 		the node to select
	 */
	protected void setSelectedChild(TreeNode node){
		ownerPanel.selectChildOfSelectedElement( node );
	}
	
	/**
	 * Adds new child of the given type. This method is called when we add element not from tree,
	 * to only reflects the changes in the tree.
	 * 
	 * @param type
	 */
	public TreeNode addChildOnlyInTree(int type){
		TreeNode node = null;
		if( getDataControl( ).canAddElement( type )){
			// Add the new child and update the tree
			node = checkForNewChild( type );
			ownerPanel.updateTreePanel( );
		}
		return node;
	}
	
	/**
	 * Delete a child. This method is called when we delete element not from tree,
	 * to only reflects the changes in the tree.
	 */
	public void deleteChildOnlyInTree(){
		// Update the tree panel and select new row
			ownerPanel.checkForDeletedReferences( );
			ownerPanel.updateTreePanel( );
			Controller.getInstance( ).updateFlagSummary( );
		
	}

	public void delete() {
		DeleteNodeTool deleteNodeTool = new DeleteNodeTool(this);
		Controller.getInstance().addTool(deleteNodeTool);
	}
	
	/**
	 * Deletes the node (invoked by DeleteNodeTool).
	 */
	public boolean delete( boolean askConfirmation ) {
		if( getDataControl( ).canBeDeleted( ) && parent.getDataControl( ).deleteElement( getDataControl( ), askConfirmation ) ) {
			// Delete the node from the parents structure
			TreeNodeControl.getInstance().dataControlRemoved(getDataControl());
			parent.children.remove( this );

			// Update the tree panel and select new row
			ownerPanel.checkForDeletedReferences( );
			ownerPanel.updateTreePanel( );
			ownerPanel.reselectSelectedRow( );
			Controller.getInstance( ).updateFlagSummary( );
			return true;
		}
		return false;
	}
	
	public boolean addExistingChildInPosition ( int index, TreeNode child ){
		parent.children.add(index, child);
		ownerPanel.updateTreePanel( );
		ownerPanel.selectChildOfSelectedElement( child );
		return true;
	}
	
	public int getIndexInParent( ){
		return parent.getIndexOfChild(this);
	}

	/**
	 * Moves the node to the previous position in the list.
	 */
	public void moveUp( ) {
		MoveTreeNodeUpTool mtnup = new MoveTreeNodeUpTool(this, getDataControl(), parent, ownerPanel);
		Controller.getInstance().addTool(mtnup);
	}

	/**
	 * Moves the node to the next position.
	 */
	public void moveDown( ) {
		MoveTreeNodeDownTool tool = new MoveTreeNodeDownTool(this, getDataControl(), parent, ownerPanel);
		Controller.getInstance().addTool(tool);
	}

	/**
	 * Asks the user for a new ID for the element.
	 */
	public void rename( ) {
		RenameElementTool tool = new RenameElementTool(getDataControl(), ownerPanel);
		Controller.getInstance().addTool(tool);
	}

	/**
	 * Returns the icon associated with this element.
	 * 
	 * @return Icon of the node
	 */
	public abstract Icon getIcon( );

	/**
	 * Returns the tool tip text of the node.
	 * 
	 * @return Tool tip text
	 */
	public abstract String getToolTipText( );

	/**
	 * Returns a popup menu with the add operations.
	 * 
	 * @return Popup menu with child adding operations
	 */
	public JPopupMenu getAddChildPopupMenu( ) {
		JPopupMenu addChildPopupMenu = new JPopupMenu( );

		// If the element accepts children
		if( getDataControl()!=null && getDataControl( ).getAddableElements( ).length > 0 ) {
			// Add an entry in the popup menu for each type of possible child
			for( int type : getDataControl( ).getAddableElements( ) ) {
				JMenuItem addChildMenuItem = new JMenuItem( TextConstants.getText( "TreeNode.AddElement" + type ) );
				addChildMenuItem.setEnabled( canAddChild( type ) );
				addChildMenuItem.addActionListener( new AddElementActionListener( type ) );
				addChildPopupMenu.add( addChildMenuItem );
			}
		}

		// If no element can be added, insert a disabled general option
		else {
			JMenuItem addChildMenuItem = new JMenuItem( TextConstants.getText( "TreeNode.AddElement" ) );
			addChildMenuItem.setEnabled( false );
			addChildPopupMenu.add( addChildMenuItem );
		}

		return addChildPopupMenu;
	}

	/**
	 * Returns a popup menu with all the operations.
	 * 
	 * @return Popup menu with all operations
	 */
	public JPopupMenu getCompletePopupMenu( ) {
		JPopupMenu completePopupMenu = getAddChildPopupMenu( );

		// Separator
		completePopupMenu.addSeparator( );

		// Add the "Rename element..." option
		JMenuItem renameMenuItem = new JMenuItem( TextConstants.getText( "TreeNode.RenameElement" ) );
		renameMenuItem.setEnabled( canBeRenamed( ) );
		renameMenuItem.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent arg0 ) {
				rename( );
			}
		} );
		completePopupMenu.add( renameMenuItem );

		// Separator
		completePopupMenu.addSeparator( );

		// Create and add the delete item
		JMenuItem deleteMenuItem = new JMenuItem( TextConstants.getText( "TreeNode.DeleteElement" ) );
		deleteMenuItem.setEnabled( canBeDeleted( ) );
		deleteMenuItem.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent arg0 ) {
				delete( );
			}
		} );
		completePopupMenu.add( deleteMenuItem );

		// Separator
		completePopupMenu.addSeparator( );

		// Create and add the move up and down item
		JMenuItem moveUpMenuItem = new JMenuItem( TextConstants.getText( "TreeNode.MoveElementUp" ) );
		JMenuItem moveDownMenuItem = new JMenuItem( TextConstants.getText( "TreeNode.MoveElementDown" ) );
		moveUpMenuItem.setEnabled( canBeMoved( ) );
		moveDownMenuItem.setEnabled( canBeMoved( ) );
		moveUpMenuItem.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent arg0 ) {
				moveUp( );
			}
		} );
		moveDownMenuItem.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent arg0 ) {
				moveDown( );
			}
		} );
		completePopupMenu.add( moveUpMenuItem );
		completePopupMenu.add( moveDownMenuItem );

		return completePopupMenu;
	}

	/**
	 * Returns the panel which edits the content of the node.
	 * 
	 * @return Component with components to edit the content of the node
	 */
	public abstract JComponent getEditPanel( );

	/**
	 * This class is the action listener for the add buttons of the popup menus.
	 */
	private class AddElementActionListener implements ActionListener {

		/**
		 * Type of element to be created.
		 */
		int type;

		/**
		 * Constructor
		 * 
		 * @param type
		 *            Type of element the listener must call
		 */
		public AddElementActionListener( int type ) {
			this.type = type;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			addChild( type );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString( ) {
		return TextConstants.getElementName( getNodeType( ) );
	}
	
	/**
	 * Change the current tree node to the one that has
	 * the given object as its data control.
	 * 
	 * @param object The object of the tree node
	 * @return true if the tree node was changed
	 */
	public boolean changeTreeNodeForObject(Object object) {
		TreeNode tempParent = parent;
		TreeNode tempNode = this;
		while (parent != null && parent != tempNode) {
			tempNode = tempParent;
			tempParent = tempParent.parent;
		}
		TreePath temp = getTreeNodeForObject(null, object);
		if (temp != null) {
			ownerPanel.changePath(temp);
			TreeNodeControl.getInstance().visitPath(temp);
			ownerPanel.updateSelectedRow();
			ownerPanel.reselectSelectedRow();
			ownerPanel.updateUI();
			return true;
		}
		return false;
	}
	
	/**
	 * Change the tree node for the one that has the given
	 * object as the content of its data control.
	 * 
	 * @param object The content of the data control of the desired tree node
	 * @return true if the tree node was changed
	 */
	public boolean changeTreeNodeForObjectContent(Object object) {
		TreeNode tempParent = parent;
		TreeNode tempNode = this;
		while (parent != null && parent != tempNode) {
			tempNode = tempParent;
			tempParent = tempParent.parent;
		}
		TreePath temp = getTreeNodeForObjectContent(null, object);
		if (temp != null) {
			ownerPanel.changePath(temp);
			TreeNodeControl.getInstance().visitPath(temp);
			ownerPanel.updateSelectedRow();
			ownerPanel.reselectSelectedRow();
			ownerPanel.updateUI();
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the relative path from the current tree
	 * node to the one that has the given object as its data
	 * control. Returns null if said data control is not in the
	 * current tree node or its descendants.
	 * 
	 * @param object The data control
	 * @return the tree path if it exists
	 */
	public TreePath getTreePathForObject(Object object) {
		TreeNode tempParent = parent;
		TreeNode tempNode = this;
		while (parent != null && parent != tempNode) {
			tempNode = tempParent;
			tempParent = tempParent.parent;
		}
		TreePath temp = getTreeNodeForObject(null, object);
		if (temp != null) {
			return temp;
		}
		return null;
	}

	/**
	 * Returns the relative path from the current tree
	 * node to the one that has the given object as its data
	 * control content. Returns null if said data control is not in the
	 * current tree node or its descendants.
	 * 
	 * @param object The data controls content
	 * @return the tree path if it exists
	 */
	public TreePath getTreePathForObjectContent(Object object) {
		TreeNode tempParent = parent;
		TreeNode tempNode = this;
		while (parent != null && parent != tempNode) {
			tempNode = tempParent;
			tempParent = tempParent.parent;
		}
		TreePath temp = getTreeNodeForObjectContent(null, object);
		if (temp != null) {
			return temp;
		}
		return null;
	}

	/**
	 * Change the tree node to the one in the given path
	 * 
	 * @param path the path of the new tree node
	 * @return true if successfully changed
	 */
	public boolean changeTreeNodeForPath(TreePath path) {
		try {
			if (path != null) {
				ownerPanel.changePath(path);
				ownerPanel.updateSelectedRow();
				ownerPanel.reselectSelectedRow();
				ownerPanel.updateUI();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Recursive method to find the tree path of a object.
	 * 
	 * @param treePath the original tree path
	 * @param object the object
	 * @return the new tree path
	 */
	private TreePath getTreeNodeForObject(TreePath treePath, Object object) {
		TreeNode temp = this.isObjectTreeNode(object);
		if (treePath == null && temp != null) {
			return new TreePath(this);
		} 
		if (temp != null && treePath != null) {
			return treePath.pathByAddingChild(this);
		}
		TreePath tempPath;
		if (children != null) {
			for (TreeNode child : children) {
				if (treePath != null) {
					tempPath = child.getTreeNodeForObject(treePath.pathByAddingChild(this), object);
				} else {
					tempPath = child.getTreeNodeForObject(new TreePath(this), object);
				}
				if (tempPath != null)
					return tempPath;
			}
		}
		return null;
	}

	/**
	 * Recursive method to find the tree path that has the given object as its data control content
	 * 
	 * @param treePath the original tree path
	 * @param object the object
	 * @return the new tree path
	 */
	private TreePath getTreeNodeForObjectContent(TreePath treePath, Object object) {
		TreeNode temp = this.isObjectContentTreeNode(object);
		if (treePath == null && temp != null) {
			return new TreePath(this);
		} 
		if (temp != null && treePath != null) {
			return treePath.pathByAddingChild(this);
		}
		
		TreePath tempPath;
		if (children != null) {
			for (TreeNode child : children) {
				if (treePath != null) {
					tempPath = child.getTreeNodeForObjectContent(treePath.pathByAddingChild(this), object);
				} else {
					tempPath = child.getTreeNodeForObjectContent(new TreePath(this), object);
				}
				if (tempPath != null)
					return tempPath;
		
			}
		}
		return null;
	}

	/**
	 * Returns the tree node if the content of its that control is the
	 * given object, null in another case
	 * 
	 * @param object The data control content
	 * @return The treenode or null
	 */
	public abstract TreeNode isObjectContentTreeNode(Object object);

	/**
	 * Returns the tree node if the object is its data control
	 * @param object The data control
	 * @return The tree node of null
	 */
	public abstract TreeNode isObjectTreeNode(Object object);

	/**
	 * Get the list of children of the tree node
	 * 
	 * @return The list of children
	 */
	public List<TreeNode> getChildren() {
		return children;
	}
}