/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.editor.gui.elementpanels.item;

import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.item.ItemDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;
import es.eucm.eadventure.editor.gui.elementpanels.general.ActionsListPanel;

public class ItemPanel extends ElementPanel {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * 
     * @param itemDataControl
     *            Item controller
     */
    public ItemPanel( ItemDataControl itemDataControl ) {

        this.addTab( new ItemLooksPanelTab( itemDataControl ) );
        this.addTab( new ItemDocPanelTab( itemDataControl ) );
        this.addTab( new ActionsPanelTab( itemDataControl ) );
    }

    private class ItemDocPanelTab extends PanelTab {

        private ItemDataControl itemDataControl;

        public ItemDocPanelTab( ItemDataControl itemDataControl ) {

            super( TextConstants.getText( "Item.DocPanelTitle" ), itemDataControl );
            this.itemDataControl = itemDataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new ItemDocPanel( itemDataControl );
        }
    }

    private class ItemLooksPanelTab extends PanelTab {

        private ItemDataControl itemDataControl;

        public ItemLooksPanelTab( ItemDataControl itemDataControl ) {

            super( TextConstants.getText( "Item.LookPanelTitle" ), itemDataControl );
            this.itemDataControl = itemDataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new ItemLooksPanel( itemDataControl );
        }
    }

    private class ActionsPanelTab extends PanelTab {

        private ItemDataControl itemDataControl;

        public ActionsPanelTab( ItemDataControl itemDataControl ) {

            super( TextConstants.getText( "Item.ActionsPanelTitle" ), itemDataControl.getActionsList( ) );
            this.itemDataControl = itemDataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new ActionsListPanel( itemDataControl.getActionsList( ) );
        }
    }

}
