package es.eucm.eadventure.editor.gui.treepanel.nodes.book;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookParagraphDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.book.TextBookParagraphPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

public class TextBookParagraphTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private BookParagraphDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/textBookParagraph.png" );;
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            Text paragraph to be contained
	 */
	public TextBookParagraphTreeNode( TreeNode parent, BookParagraphDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		// Do nothing (this node can't add children)
		return null;
	}

	@Override
	public void checkForDeletedReferences( ) {
	// Do nothing
	}

	@Override
	protected int getNodeType( ) {
		return Controller.BOOK_TEXT_PARAGRAPH;
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
		//return new TextBookParagraphPanel( dataControl );
		return new JPanel();
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