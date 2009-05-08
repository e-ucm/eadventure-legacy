package es.eucm.eadventure.editor.control.tools.scene;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ElementContainer;
import es.eucm.eadventure.editor.control.controllers.scene.ReferencesListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.elementpanels.scene.ElementReferencesTable;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class AddReferenceTool extends Tool {

	private ReferencesListDataControl referencesListDataControl;
	
	private int type;
	
	private ScenePreviewEditionPanel spep;
	
	private ElementReferencesTable table;

	private ElementContainer newElement;
	
	public AddReferenceTool(
			ReferencesListDataControl referencesListDataControl, int type,
			ScenePreviewEditionPanel spep, ElementReferencesTable table) {
		this.referencesListDataControl = referencesListDataControl;
		this.type = type;
		this.spep = spep;
		this.table = table;
	}

	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
		int category;
		if (referencesListDataControl.addElement( type, null )){
			category = ReferencesListDataControl.transformType(type);
			if (category != 0 && referencesListDataControl.getLastElementContainer()!=null) {
				// it is not necessary to check if it is an player element container because never a player will be added
				newElement = referencesListDataControl.getLastElementContainer();
				spep.addElement(category, newElement.getErdc());
				spep.setSelectedElement(newElement.getErdc());
				spep.repaint();
				int layer = newElement.getErdc().getElementReference().getLayer();
				table.getSelectionModel().setSelectionInterval(layer, layer);
				table.updateUI( );
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean redoTool() {
		referencesListDataControl.addElement(newElement);
		int category = ReferencesListDataControl.transformType(type);
		spep.addElement(category, newElement.getErdc());
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		referencesListDataControl.deleteElement(newElement.getErdc(), false);
		int category = ReferencesListDataControl.transformType(type);
		spep.removeElement(category, newElement.getErdc());
		Controller.getInstance().updatePanel();
		return true;
	}
}
