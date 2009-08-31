/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.editor.gui.elementpanels.item;

import javax.swing.BorderFactory;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.item.ItemDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.LooksPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.ImagePanel;

public class ItemLooksPanel extends LooksPanel {

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
		if (getParent()!=null)
			getParent( ).repaint( );
	}

}
