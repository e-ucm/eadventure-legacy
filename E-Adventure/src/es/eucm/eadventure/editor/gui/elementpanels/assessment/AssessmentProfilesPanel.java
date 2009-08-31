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
package es.eucm.eadventure.editor.gui.elementpanels.assessment;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfilesDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;
import es.eucm.eadventure.editor.gui.elementpanels.general.ResizeableListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ResizeableCellRenderer;

public class AssessmentProfilesPanel extends ElementPanel {
	/**
	 * Required
	 */
	private static final long serialVersionUID = 6602692300239491332L;

	public AssessmentProfilesPanel (AssessmentProfilesDataControl dataControl){
		addTab(new AssessmentProfilesPanelTab(dataControl));
	}

	private class AssessmentProfilesPanelTab extends PanelTab {
		private AssessmentProfilesDataControl sDataControl;
		
		public AssessmentProfilesPanelTab(AssessmentProfilesDataControl sDataControl) {
			super(TextConstants.getText( "AssessmentProfiles.Title" ), sDataControl);
			this.sDataControl = sDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			JPanel booksListPanel = new JPanel();
			booksListPanel.setLayout( new BorderLayout( ) );
			List<DataControl> dataControlList = new ArrayList<DataControl>();
			for (AssessmentProfileDataControl item : sDataControl.getProfiles()) {
				dataControlList.add(item);
			}
			ResizeableCellRenderer renderer = new AssessmentProfileCellRenderer();
			booksListPanel.add(new ResizeableListPanel(dataControlList, renderer, "AssessmentProfileListPanel"), BorderLayout.CENTER);
			return booksListPanel;
		}
	}

}
