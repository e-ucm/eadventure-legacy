package es.eucm.eadventure.editor.gui.structurepanel;

import javax.swing.Icon;
import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.common.data.HasId;

public class StructureElement {

	protected Icon icon;
	
	protected String name;
	
	protected DataControl dataControl;

	public StructureElement(DataControl dataControl) {
		this.dataControl = dataControl;
		name = null;
	}
	
	public StructureElement(String name, DataControl dataControl) {
		this(dataControl);
		this.name = name;
	}

	public String getName() {
		if (name != null)
			return name;
		return ((HasId) dataControl.getContent()).getId();
	}
	
	public Icon getIcon() {
		return icon;
	}
	
	public JComponent getEditPanel() {
		return EditPanelFactory.getEditPanel(dataControl);
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public boolean isCanRename() {
		return dataControl.canBeRenamed();
	}

	public boolean canBeRemoved() {
		return dataControl.canBeDeleted();
	}
	
	
}
