package es.eucm.eadventure.editor.gui.treepanel.nodes.book;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.control.controllers.book.BooksListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.book.BooksListPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

/**
 * This class holds a list of game scenes.
 * 
 * @author Bruno Torijano Bueno
 */
public class BooksListTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private BooksListDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/books.png" );;
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            List of books
	 */
	public BooksListTreeNode( TreeNode parent, BooksListDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

		for( BookDataControl bookDataControl : dataControl.getBooks( ) )
			children.add( new BookTreeNode( this, bookDataControl ) );
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

		// If a book was added
		if( type == Controller.BOOK ) {
			// Add the last book of the list
			addedTreeNode = new BookTreeNode( this, dataControl.getLastBook( ) );
			children.add( addedTreeNode );

			// Spread the owner panel to the children
			spreadOwnerPanel( );
		}

		// Return the node created
		return addedTreeNode;
	}

	@Override
	public void checkForDeletedReferences( ) {
	// Do nothing
	}

	@Override
	protected int getNodeType( ) {
		return Controller.BOOKS_LIST;
	}

	@Override
	public DataControl getDataControl( ) {
		return dataControl;
	}

	@Override
	public Icon getIcon( ) {
		return icon;
	}

	@Override
	public String getToolTipText( ) {
		return null;
	}

	@Override
	public JComponent getEditPanel( ) {
		return new BooksListPanel( dataControl );
	}
	
	@Override
	public TreeNode isObjectTreeNode(Object object) {
		if (dataControl == object)
			return this;
		return null;
	}
	
	@Override
	public TreeNode isObjectContentTreeNode(Object object) {
		if (dataControl.getContent() == object)
			return this;
		return null;
	}

}
