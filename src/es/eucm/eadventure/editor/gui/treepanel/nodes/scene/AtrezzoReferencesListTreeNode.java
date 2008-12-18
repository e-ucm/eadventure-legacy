package es.eucm.eadventure.editor.gui.treepanel.nodes.scene;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.scene.AtrezzoReferencesListDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.scene.AtrezzoReferencesListPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;


public class AtrezzoReferencesListTreeNode extends TreeNode {
	/**
	 * Contained micro-controller.
	 */
	private AtrezzoReferencesListDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/itemReferences.png" );
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            List of atrezzo item references
	 */
	public AtrezzoReferencesListTreeNode( TreeNode parent, AtrezzoReferencesListDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

		for( ElementReferenceDataControl atrezzoReferenceDataControl : dataControl.getAtrezzoReferences( ) )
			children.add( new AtrezzoReferenceTreeNode( this, atrezzoReferenceDataControl ) );
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

		// If an atrezzo item reference was added
		if( type == Controller.ATREZZO_REFERENCE ) {
			// Add the last atrezzo item reference of the list
			addedTreeNode = new AtrezzoReferenceTreeNode( this, dataControl.getLastAtrezzoReference( ) );
			children.add( addedTreeNode );

			// Spread the owner panel to the children
			spreadOwnerPanel( );
		}

		// Return the node created
		return addedTreeNode;
	}

	@Override
	public void checkForDeletedReferences( ) {
		// If some atrezzo item reference is missing, delete it
		int i = 0;
		while( i < children.size( ) )
			if( i == dataControl.getAtrezzoReferences( ).size( ) || !children.get( i ).getDataControl( ).equals( dataControl.getAtrezzoReferences( ).get( i ) ) )
				children.remove( i );
			else
				i++;
	}

	@Override
	protected int getNodeType( ) {
		return Controller.ATREZZO_REFERENCES_LIST;
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
		
		return new AtrezzoReferencesListPanel( dataControl );
	}
}
