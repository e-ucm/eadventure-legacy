/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.elementpanels.item;

import javax.swing.BorderFactory;

import es.eucm.eadventure.common.gui.TC;
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
        imagePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Item.Preview" ) ) );

        lookPanel.add( imagePanel, cLook );

    }

    @Override
    public void updatePreview( ) {

        imagePanel.loadImage( itemDataControl.getPreviewImage( ) );
        imagePanel.repaint( );

    }

    @Override
    public void updateResources( ) {

        super.updateResources( );
        if( getParent( ) != null )
            getParent( ).repaint( );
    }

}
