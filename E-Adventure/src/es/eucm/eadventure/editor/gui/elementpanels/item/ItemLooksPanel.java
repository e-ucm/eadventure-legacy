package es.eucm.eadventure.editor.gui.elementpanels.item;

import java.awt.Dimension;

import javax.swing.BorderFactory;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.item.ItemDataControl;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.elementpanels.general.LooksPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.ImagePanel;

public class ItemLooksPanel extends LooksPanel implements Updateable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Preview image panel.
	 */
	private ImagePanel imagePanel;
	
	private ItemDataControl itemDataControl;

	public ItemLooksPanel( ItemDataControl control ) {
		super( control );
		this.itemDataControl = control;
	}

	@Override
	protected void createPreview( ) {
		itemDataControl = (ItemDataControl) this.dataControl;
		// Take the path to the current image of the item
		String itemImagePath = itemDataControl.getPreviewImage( );

		imagePanel = new ImagePanel( itemImagePath );
		imagePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Item.Preview" ) ) );
		
		lookPanel.add( imagePanel, cLook );

	}

	@Override
	public void updatePreview( ) {
		imagePanel.loadImage( itemDataControl.getPreviewImage( ) );
		imagePanel.repaint( );

	}

	public void updateResources( ) {
		super.updateResources( );
		getParent( ).repaint( );
	}

	@Override
	public boolean updateFields() {
		updatePreview();
		return true;
	}

}
