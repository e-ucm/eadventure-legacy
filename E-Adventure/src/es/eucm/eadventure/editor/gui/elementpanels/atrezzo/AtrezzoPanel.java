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
package es.eucm.eadventure.editor.gui.elementpanels.atrezzo;

import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.atrezzo.AtrezzoDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;

public class AtrezzoPanel extends ElementPanel {

	/**
	 * Requiered
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Constructor.
	 * 
	 * @param atrezzoDataControl
	 *            Atrezzo controller
	 */
	public AtrezzoPanel( AtrezzoDataControl atrezzoDataControl ) {
		this.addTab(new AtrezzoLooksPanelTab(atrezzoDataControl));
		this.addTab(new AtrezzoDocPanelTab(atrezzoDataControl));
	}
	
	private class AtrezzoDocPanelTab extends PanelTab {
		private AtrezzoDataControl atrezzoDataControl;

		public AtrezzoDocPanelTab(AtrezzoDataControl atrezzoDataControl) {
			super(TextConstants.getText( "Atrezzo.DocPanelTitle" ), atrezzoDataControl);
			this.atrezzoDataControl = atrezzoDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new AtrezzoDocPanel( atrezzoDataControl);
		}
	}
	
	private class AtrezzoLooksPanelTab extends PanelTab {
		private AtrezzoDataControl atrezzoDataControl;

		public AtrezzoLooksPanelTab(AtrezzoDataControl atrezzoDataControl) {
			super(TextConstants.getText( "Atrezzo.LookPanelTitle" ), atrezzoDataControl);
			this.atrezzoDataControl = atrezzoDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new AtrezzoLooksPanel( atrezzoDataControl );
		}
	}
}
