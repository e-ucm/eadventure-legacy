package es.eucm.eadventure.editor.gui.treepanel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

/**
 * This class extends the java JTree. It repretents a set of nodes which holds the data and the operations that can be
 * performed onto the script's elements.
 * 
 * @author Bruno Torijano Bueno
 */
public class TreePanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The container in which the edition panel will be placed.
	 */
	private Container editorContainer;

	/**
	 * "Add" button.
	 */
	private JButton addButton;

	/**
	 * "Delete" button.
	 */
	private JButton deleteButton;

	/**
	 * "Move up" button.
	 */
	private JButton moveUpButton;

	/**
	 * "Move down" button.
	 */
	private JButton moveDownButton;

	/**
	 * "Collapse all" button.
	 */
	private JButton collapseAllButton;

	/**
	 * "Expand all" button.
	 */
	private JButton expandAllButton;

	/**
	 * The tree inside the panel.
	 */
	private JTree dataTree;

	/**
	 * Current root node of the tree.
	 */
	private TreeNode rootNode;

	/**
	 * Stores the selected row of the tree.
	 */
	private int rowSelected;

	/**
	 * Icon for the "Add" button.
	 */
	private static Icon addIcon;

	/**
	 * Icon for the "Delete" button.
	 */
	private static Icon deleteIcon;

	/**
	 * Icon for the "Move up" button.
	 */
	private static Icon moveUpIcon;

	/**
	 * Icon for the "Move down" button.
	 */
	private static Icon moveDownIcon;

	/**
	 * Icon for the "Collapse all" button.
	 */
	private static Icon collapseAllIcon;

	/**
	 * Icon for the "Expand all" button.
	 */
	private static Icon expandAllIcon;

	/**
	 * Loads the icons for the buttons of the panel.
	 */
	public static void loadIcons( ) {
		addIcon = new ImageIcon( "img/icons/addNode.png" );
		deleteIcon = new ImageIcon( "img/icons/deleteNode.png" );
		moveUpIcon = new ImageIcon( "img/icons/moveNodeUp.png" );
		moveDownIcon = new ImageIcon( "img/icons/moveNodeDown.png" );
		collapseAllIcon = new ImageIcon( "img/icons/collapseAll.png" );
		expandAllIcon = new ImageIcon( "img/icons/expandAll.png" );
	}

	/**
	 * Constructor.
	 * 
	 * @param root
	 *            The root node of the tree
	 * @param editorContainer
	 *            The container that will hold the edition panels
	 */
	public TreePanel( TreeNode root, Container editorContainer ) {
		// Set the editor container
		this.editorContainer = editorContainer;
		this.rootNode = root;

		// Link the tree panel with the tree nodes
		root.setOwnerPanel( this );

		// Create the buttons
		addButton = new JButton( addIcon );
		deleteButton = new JButton( deleteIcon );
		moveUpButton = new JButton( moveUpIcon );
		moveDownButton = new JButton( moveDownIcon );
		collapseAllButton = new JButton( collapseAllIcon );
		expandAllButton = new JButton( expandAllIcon );

		// Set the size of the buttons
		addButton.setPreferredSize( new Dimension( 24, 22 ) );
		deleteButton.setPreferredSize( new Dimension( 24, 22 ) );
		moveUpButton.setPreferredSize( new Dimension( 24, 22 ) );
		moveDownButton.setPreferredSize( new Dimension( 24, 22 ) );
		collapseAllButton.setPreferredSize( new Dimension( 24, 22 ) );
		expandAllButton.setPreferredSize( new Dimension( 24, 22 ) );

		// Options for the buttons: don't paint when focused,
		// don't paint the filling of the button, and set the rollover effect enabled
		addButton.setFocusPainted( false );
		deleteButton.setFocusPainted( false );
		moveUpButton.setFocusPainted( false );
		moveDownButton.setFocusPainted( false );
		collapseAllButton.setFocusPainted( false );
		expandAllButton.setFocusPainted( false );

		addButton.setContentAreaFilled( false );
		deleteButton.setContentAreaFilled( false );
		moveUpButton.setContentAreaFilled( false );
		moveDownButton.setContentAreaFilled( false );
		collapseAllButton.setContentAreaFilled( false );
		expandAllButton.setContentAreaFilled( false );

		addButton.setRolloverEnabled( true );
		deleteButton.setRolloverEnabled( true );
		moveUpButton.setRolloverEnabled( true );
		moveDownButton.setRolloverEnabled( true );
		collapseAllButton.setRolloverEnabled( true );
		expandAllButton.setRolloverEnabled( true );

		// Add the listeners to the buttons
		ActionListener buttonsActionListener = new ButtonsActionListener( );
		addButton.addActionListener( buttonsActionListener );
		deleteButton.addActionListener( buttonsActionListener );
		moveUpButton.addActionListener( buttonsActionListener );
		moveDownButton.addActionListener( buttonsActionListener );
		collapseAllButton.addActionListener( buttonsActionListener );
		expandAllButton.addActionListener( buttonsActionListener );

		// Create the tree
		dataTree = new JTree( new DataTreeModel( root ) );

		// Sets the cell renderer and enables the tool tips of the tree
		ToolTipManager.sharedInstance( ).registerComponent( dataTree );
		dataTree.setCellRenderer( new DataTreeCellRenderer( ) );

		// Set the selection mode and listener for the tree
		dataTree.getSelectionModel( ).setSelectionMode( TreeSelectionModel.SINGLE_TREE_SELECTION );
		dataTree.addTreeSelectionListener( new DataTreeSelectionListener( ) );
		dataTree.addTreeExpansionListener( new DataTreeExpansionListener( ) );

		// Add the mouse listener to the tree (for the popup menus)
		dataTree.addMouseListener( new DataTreeMouseListener( ) );

		// Select the first row of the tree
		dataTree.setSelectionRow( 0 );

		// Create the north panel (the one holding the buttons)
		JPanel northPanel = new JPanel( );
		northPanel.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		// Add and delete buttons
		northPanel.add( addButton );
		northPanel.add( deleteButton );

		// Separator
		JSeparator separator = new JSeparator( JSeparator.VERTICAL );
		separator.setPreferredSize( new Dimension( 2, 24 ) );
		northPanel.add( separator );

		// Move buttons
		northPanel.add( moveUpButton );
		northPanel.add( moveDownButton );

		// Separator
		separator = new JSeparator( JSeparator.VERTICAL );
		separator.setPreferredSize( new Dimension( 2, 24 ) );
		northPanel.add( separator );

		// Expand and collapse buttons
		northPanel.add( collapseAllButton );
		northPanel.add( expandAllButton );

		// Create the center panel
		JScrollPane treePanel = new JScrollPane( dataTree );

		// Add the components to the panel
		setLayout( new BorderLayout( ) );
		add( northPanel, BorderLayout.NORTH );
		add( treePanel, BorderLayout.CENTER );

		// Add a border to the panel
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), "Script tree model" ) );
	}

	/**
	 * Reloads the entire data tree with the new root given.
	 * 
	 * @param newRoot
	 *            New root for the tree
	 */
	public void reloadTree( TreeNode newRoot ) {
		// Set the new root, select the first row and update the panel
		this.rootNode = newRoot;
		newRoot.setOwnerPanel( this );
		dataTree.setModel( new DataTreeModel( newRoot ) );
		dataTree.setSelectionRow( 0 );
		updateTreePanel( );
	}

	/**
	 * Loads the panel of the currently selected tree node.
	 */
	public void loadPanel( ) {
		// Take the selected node
		TreeNode node = (TreeNode) dataTree.getSelectionPath( ).getLastPathComponent( );

		// Load the panel with the node
		editorContainer.removeAll( );
		editorContainer.add( node.getEditPanel( ) );
		editorContainer.validate( );
		editorContainer.repaint( );
	}

	/**
	 * Updates the identifier references from the tree model, deleting the references to elements that have been
	 * deleted.
	 */
	public void checkForDeletedReferences( ) {
		rootNode.checkForDeletedReferences( );
	}

	/**
	 * Updates the information of the tree.
	 */
	public void updateTreePanel( ) {
		dataTree.updateUI( );
	}

	/**
	 * Stores the new value of the row when some element has been moved.
	 */
	public void updateSelectedRow( ) {
		rowSelected = dataTree.getRowForPath( dataTree.getSelectionPath( ) );
	}

	/**
	 * Selects the previous row in the tree.
	 */
	public void reselectSelectedRow( ) {
		if( rowSelected < dataTree.getRowCount( ) )
			dataTree.setSelectionRow( rowSelected );
		else
			dataTree.setSelectionRow( rowSelected - 1 );
	}

	/**
	 * Selects a child of the selected node.
	 * 
	 * @param child
	 *            Child node to select
	 */
	public void selectChildOfSelectedElement( Object child ) {
		TreePath newPath = dataTree.getSelectionPath( ).pathByAddingChild( child );
		dataTree.setSelectionPath( newPath );
		dataTree.scrollPathToVisible( newPath );
	}

	/**
	 * Listener for the upper buttons of the panel.
	 */
	private class ButtonsActionListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			// Get the selected node
			TreeNode node = (TreeNode) dataTree.getSelectionPath( ).getLastPathComponent( );

			// If the add button is pressed, show the popup dialog
			if( e.getSource( ) == addButton ) {
				JPopupMenu menu = node.getAddChildPopupMenu( );
				menu.show( addButton, 10, 10 );
			}

			// If the delete button is pressed, delete the node
			else if( e.getSource( ) == deleteButton )
				node.delete( );

			// If the move up button is pressed, move the node
			else if( e.getSource( ) == moveUpButton )
				node.moveUp( );

			// If the move down button is pressed, move the node
			else if( e.getSource( ) == moveDownButton )
				node.moveDown( );

			// If the collapse all button is pressed, collapse all the nodes
			else if( e.getSource( ) == collapseAllButton ) {
				// Pick the last row
				int actualRow = dataTree.getRowCount( );

				// Collapse every row, from last to first (except the first node)
				while( actualRow >= 1 ) {
					dataTree.collapseRow( actualRow );
					actualRow--;
				}
			}

			// If the expand all button is pressed, expand all the nodes
			else if( e.getSource( ) == expandAllButton ) {
				// Pick the first row
				int actualRow = 0;

				// Expand every row, from first to last
				while( actualRow <= dataTree.getRowCount( ) ) {
					dataTree.expandRow( actualRow );
					actualRow++;
				}
			}
		}
	}

	/**
	 * Listener for the selection of the tree nodes.
	 */
	private class DataTreeSelectionListener implements TreeSelectionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
		 */
		public void valueChanged( TreeSelectionEvent e ) {
			rowSelected = dataTree.getRowForPath( e.getPath( ) );
			TreeNode node = (TreeNode) e.getPath( ).getLastPathComponent( );

			// Enable or disable the buttons
			addButton.setEnabled( node.canAddChildren( ) );
			deleteButton.setEnabled( node.canBeDeleted( ) );
			moveUpButton.setEnabled( node.canBeMoved( ) );
			moveDownButton.setEnabled( node.canBeMoved( ) );

			// Load the editor panel
			editorContainer.removeAll( );
			editorContainer.add( node.getEditPanel( ) );
			editorContainer.validate( );
			editorContainer.repaint( );
		}
	}

	/**
	 * The expansion listener of the tree. It must update the row selected everytime an element is expanded or
	 * collapsed.
	 */
	private class DataTreeExpansionListener implements TreeExpansionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.TreeExpansionListener#treeCollapsed(javax.swing.event.TreeExpansionEvent)
		 */
		public void treeCollapsed( TreeExpansionEvent event ) {
			updateSelectedRow( );
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.TreeExpansionListener#treeExpanded(javax.swing.event.TreeExpansionEvent)
		 */
		public void treeExpanded( TreeExpansionEvent event ) {
			updateSelectedRow( );
		}
	}

	/**
	 * The mouse listener for the tree. It shows the popup menu when neccessary.
	 */
	private class DataTreeMouseListener extends MouseAdapter {

		@Override
		public void mousePressed( MouseEvent e ) {
			// By default the JTree only selects the nodes with the left click of the mouse
			// With this code, we spread a new call everytime the right mouse button is pressed
			if( e.getButton( ) == MouseEvent.BUTTON3 ) {
				// Create new event (with the left mouse button)
				MouseEvent newEvent = new MouseEvent( e.getComponent( ), e.getID( ), e.getWhen( ), MouseEvent.BUTTON1_MASK, e.getX( ), e.getY( ), e.getClickCount( ), e.isPopupTrigger( ) );

				// Take the listeners and make the calls
				for( MouseListener mouseListener : e.getComponent( ).getMouseListeners( ) )
					mouseListener.mousePressed( newEvent );
			}
		}

		@Override
		public void mouseReleased( MouseEvent e ) {
			if( e.isPopupTrigger( ) ) {
				TreeNode node = (TreeNode) dataTree.getSelectionPath( ).getLastPathComponent( );
				JPopupMenu menu = node.getCompletePopupMenu( );
				menu.show( dataTree, e.getX( ), e.getY( ) );
			}
		}
	}
}