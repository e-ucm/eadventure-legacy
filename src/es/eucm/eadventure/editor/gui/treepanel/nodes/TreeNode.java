package es.eucm.eadventure.editor.gui.treepanel.nodes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.gui.treepanel.TreePanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.adaptation.AdaptationProfileTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.adaptation.AdaptationProfilesTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.adaptation.AdaptationRuleTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.assessment.AssessmentProfileTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.assessment.AssessmentProfilesTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.assessment.AssessmentRuleTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.assessment.TimedAssessmentRuleTreeNode;
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
import es.eucm.eadventure.editor.gui.treepanel.nodes.scene.BarrierTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.scene.BarriersListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.scene.ExitTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.scene.ExitsListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.scene.ItemReferenceTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.scene.ItemReferencesListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.scene.NPCReferenceTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.scene.NPCReferencesListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.scene.SceneTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.scene.ScenesListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.timer.TimerTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.timer.TimersListTreeNode;

/**
 * This class contains the operations required to build a JTree panel with the data from the script.
 * 
 * @author Bruno Torijano Bueno
 */
public abstract class TreeNode {

	/**
	 * Tree panel that holds this node and its children.
	 */
	private TreePanel ownerPanel;

	/**
	 * Parent node.
	 */
	private TreeNode parent;

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
		ItemReferencesListTreeNode.loadIcon( );
		ItemReferenceTreeNode.loadIcon( );
		NPCReferencesListTreeNode.loadIcon( );
		NPCReferenceTreeNode.loadIcon( );
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
		ItemsListTreeNode.loadIcon( );
		ItemTreeNode.loadIcon( );
		UseActionTreeNode.loadIcon( );
		UseWithActionTreeNode.loadIcon( );
		
		// Atrezzo items node
		
		//TODO !!!!!

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
		if( getDataControl( ).canAddElement( type ) && getDataControl( ).addElement( type ) ) {
			// Add the new child and update the tree
			TreeNode addedTreeNode = checkForNewChild( type );
			ownerPanel.updateTreePanel( );
			ownerPanel.selectChildOfSelectedElement( addedTreeNode );
		}
	}

	/**
	 * Deletes the node.
	 */
	public void delete( ) {
		if( getDataControl( ).canBeDeleted( ) && parent.getDataControl( ).deleteElement( getDataControl( ) ) ) {
			// Delete the node from the parents structure
			parent.children.remove( this );

			// Update the tree panel and select new row
			ownerPanel.checkForDeletedReferences( );
			ownerPanel.updateTreePanel( );
			ownerPanel.reselectSelectedRow( );
			Controller.getInstance( ).updateFlagSummary( );
		}
	}

	/**
	 * Moves the node to the previous position in the list.
	 */
	public void moveUp( ) {
		if( getDataControl( ).canBeMoved( ) && parent.getDataControl( ).moveElementUp( getDataControl( ) ) ) {
			// If the element was moved, move the child node as well
			int index = parent.children.indexOf( this );
			parent.children.add( index - 1, parent.children.remove( index ) );

			// Update the tree panel and the row selected
			ownerPanel.updateTreePanel( );
			ownerPanel.updateSelectedRow( );
		}
	}

	/**
	 * Moves the node to the next position.
	 */
	public void moveDown( ) {
		if( getDataControl( ).canBeMoved( ) && parent.getDataControl( ).moveElementDown( getDataControl( ) ) ) {
			// If the element was moved, move the child node as well
			int index = parent.children.indexOf( this );
			parent.children.add( index + 1, parent.children.remove( index ) );

			// Update the tree panel and the row selected
			ownerPanel.updateTreePanel( );
			ownerPanel.updateSelectedRow( );
		}
	}

	/**
	 * Asks the user for a new ID for the element.
	 */
	public void rename( ) {
		if( getDataControl( ).canBeRenamed( ) && getDataControl( ).renameElement( ) ) {
			// Update the tree panel
			ownerPanel.updateTreePanel( );
		}
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
}
