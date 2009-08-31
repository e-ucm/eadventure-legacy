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

import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;

public class AssessmentPanel extends ElementPanel {

	private static final long serialVersionUID = 1L;

	public AssessmentPanel(AssessmentProfileDataControl dataControl) {
		addTab(new AssessmentEditionPanelTab(dataControl));
	}
	
	private class AssessmentEditionPanelTab extends PanelTab {
		private AssessmentProfileDataControl sDataControl;
		
		public AssessmentEditionPanelTab(AssessmentProfileDataControl sDataControl) {
			super(TextConstants.getText( "AssessmentProfile.Title" ), sDataControl);
			this.setHelpPath("assessment/assessmentProfile.html");
			this.sDataControl = sDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new AssessmentEditionPanel(sDataControl);
		}
	}
}
