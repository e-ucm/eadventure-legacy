package es.eucm.eadventure.editor.gui;

import java.util.List;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.Searchable;

public interface DataControlsPanel {
	public void setSelectedItem(List<Searchable> path);
}
