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
package es.eucm.eadventure.editor.gui.structurepanel;

import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfilesDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfilesDataControl;
import es.eucm.eadventure.editor.control.controllers.atrezzo.AtrezzoDataControl;
import es.eucm.eadventure.editor.control.controllers.atrezzo.AtrezzoListDataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.control.controllers.book.BooksListDataControl;
import es.eucm.eadventure.editor.control.controllers.character.NPCDataControl;
import es.eucm.eadventure.editor.control.controllers.character.NPCsListDataControl;
import es.eucm.eadventure.editor.control.controllers.character.PlayerDataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationsListDataControl;
import es.eucm.eadventure.editor.control.controllers.cutscene.CutsceneDataControl;
import es.eucm.eadventure.editor.control.controllers.cutscene.CutscenesListDataControl;
import es.eucm.eadventure.editor.control.controllers.general.AdvancedFeaturesDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ChapterDataControl;
import es.eucm.eadventure.editor.control.controllers.item.ItemDataControl;
import es.eucm.eadventure.editor.control.controllers.item.ItemsListDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ScenesListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.adaptation.AdaptationPanel;
import es.eucm.eadventure.editor.gui.elementpanels.adaptation.AdaptationProfilesPanel;
import es.eucm.eadventure.editor.gui.elementpanels.assessment.AssessmentPanel;
import es.eucm.eadventure.editor.gui.elementpanels.assessment.AssessmentProfilesPanel;
import es.eucm.eadventure.editor.gui.elementpanels.atrezzo.AtrezzoListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.atrezzo.AtrezzoPanel;
import es.eucm.eadventure.editor.gui.elementpanels.book.BookPanel;
import es.eucm.eadventure.editor.gui.elementpanels.book.BooksListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.character.NPCPanel;
import es.eucm.eadventure.editor.gui.elementpanels.character.NPCsListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.character.PlayerPanel;
import es.eucm.eadventure.editor.gui.elementpanels.conversation.ConversationPanel;
import es.eucm.eadventure.editor.gui.elementpanels.conversation.ConversationsListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.cutscene.CutscenePanel;
import es.eucm.eadventure.editor.gui.elementpanels.cutscene.CutscenesListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.AdvancedFeaturesPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.ChapterPanel;
import es.eucm.eadventure.editor.gui.elementpanels.item.ItemPanel;
import es.eucm.eadventure.editor.gui.elementpanels.item.ItemsListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.scene.ScenePanel;
import es.eucm.eadventure.editor.gui.elementpanels.scene.ScenesListPanel;

public class EditPanelFactory {

	public static JComponent getEditPanel(DataControl dataControl) {
		if (dataControl instanceof ScenesListDataControl)
			return new ScenesListPanel((ScenesListDataControl) dataControl);
		if (dataControl instanceof CutscenesListDataControl)
			return new CutscenesListPanel((CutscenesListDataControl) dataControl);
		if (dataControl instanceof BooksListDataControl)
			return new BooksListPanel( (BooksListDataControl) dataControl );
		if (dataControl instanceof ItemsListDataControl)
			return new ItemsListPanel((ItemsListDataControl) dataControl);
		if (dataControl instanceof AtrezzoListDataControl)
			return new AtrezzoListPanel((AtrezzoListDataControl) dataControl);
		if (dataControl instanceof AdvancedFeaturesDataControl)
			return new AdvancedFeaturesPanel((AdvancedFeaturesDataControl) dataControl);
		if (dataControl instanceof NPCsListDataControl)
			return new NPCsListPanel((NPCsListDataControl) dataControl);
		if (dataControl instanceof ConversationsListDataControl)
			return new ConversationsListPanel((ConversationsListDataControl) dataControl);

		if (dataControl instanceof SceneDataControl)
			return new ScenePanel((SceneDataControl) dataControl);
		if (dataControl instanceof BookDataControl)
			return new BookPanel((BookDataControl) dataControl);
		if (dataControl instanceof CutsceneDataControl)
			return new CutscenePanel((CutsceneDataControl) dataControl);
		if (dataControl instanceof ItemDataControl)
			return new ItemPanel((ItemDataControl) dataControl);
		if (dataControl instanceof AtrezzoDataControl)
			return new AtrezzoPanel((AtrezzoDataControl) dataControl);
		if (dataControl instanceof AssessmentProfilesDataControl)
			return new AssessmentProfilesPanel((AssessmentProfilesDataControl) dataControl);
		if (dataControl instanceof AdaptationProfilesDataControl)
			return new AdaptationProfilesPanel((AdaptationProfilesDataControl) dataControl);
		if (dataControl instanceof PlayerDataControl)
			return new PlayerPanel((PlayerDataControl) dataControl);
		if (dataControl instanceof NPCDataControl)
			return new NPCPanel((NPCDataControl) dataControl);
		if (dataControl instanceof ConversationDataControl)
			return new ConversationPanel((ConversationDataControl) dataControl);
		if (dataControl instanceof ChapterDataControl)
			return new ChapterPanel((ChapterDataControl) dataControl);
		if (dataControl instanceof AssessmentProfileDataControl)
			return new AssessmentPanel((AssessmentProfileDataControl) dataControl);
		if (dataControl instanceof AdaptationProfileDataControl)
			return new AdaptationPanel((AdaptationProfileDataControl) dataControl);
		
		
		return null;
	}
	
	
}
