package es.eucm.eadventure.editor.gui.treepanel.nodes.book;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookParagraphDataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookParagraphsListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.book.BookParagraphsListPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

/**
 * This class holds a list of game scenes.
 * 
 * @author Bruno Torijano Bueno
 */
public class BookParagraphsListTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private BookParagraphsListDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/bookParagraphs.png" );;
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            List of paragraphs
	 */
	public BookParagraphsListTreeNode( TreeNode parent, BookParagraphsListDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

		for( BookParagraphDataControl bookParagraphDataControl : dataControl.getBookParagraphs( ) ) {
			if( bookParagraphDataControl.getType( ) == Controller.BOOK_TEXT_PARAGRAPH )
				children.add( new TextBookParagraphTreeNode( this, bookParagraphDataControl ) );
			else if( bookParagraphDataControl.getType( ) == Controller.BOOK_TITLE_PARAGRAPH )
				children.add( new TitleBookParagraphTreeNode( this, bookParagraphDataControl ) );
			else if( bookParagraphDataControl.getType( ) == Controller.BOOK_IMAGE_PARAGRAPH )
				children.add( new ImageBookParagraphTreeNode( this, bookParagraphDataControl ) );
			else if( bookParagraphDataControl.getType( ) == Controller.BOOK_BULLET_PARAGRAPH )
				children.add( new BulletBookParagraphTreeNode( this, bookParagraphDataControl ) );
		}
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

		// Add the last book of the list
		BookParagraphDataControl lastBookParagraphDataControl = dataControl.getLastBookParagraph( );

		if( lastBookParagraphDataControl.getType( ) == Controller.BOOK_TEXT_PARAGRAPH ) {
			addedTreeNode = new TextBookParagraphTreeNode( this, lastBookParagraphDataControl );
			children.add( addedTreeNode );
		} else if( lastBookParagraphDataControl.getType( ) == Controller.BOOK_TITLE_PARAGRAPH ) {
			addedTreeNode = new TitleBookParagraphTreeNode( this, lastBookParagraphDataControl );
			children.add( addedTreeNode );
		} else if( lastBookParagraphDataControl.getType( ) == Controller.BOOK_IMAGE_PARAGRAPH ) {
			addedTreeNode = new ImageBookParagraphTreeNode( this, lastBookParagraphDataControl );
			children.add( addedTreeNode );
		} else if( lastBookParagraphDataControl.getType( ) == Controller.BOOK_BULLET_PARAGRAPH ) {
			addedTreeNode = new BulletBookParagraphTreeNode( this, lastBookParagraphDataControl );
			children.add( addedTreeNode );
		}

		// Spread the owner panel to the children
		spreadOwnerPanel( );

		// Return the node created
		return addedTreeNode;
	}

	@Override
	public void checkForDeletedReferences( ) {
	// Do nothing
	}

	@Override
	protected int getNodeType( ) {
		return Controller.BOOK_PARAGRAPHS_LIST;
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
		return new BookParagraphsListPanel( dataControl );
	}
}
