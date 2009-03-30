package es.eucm.eadventure.editor.gui.structurepanel;

import javax.swing.Icon;
import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.controllers.DataControl;

public abstract class StructureListElement {

	protected String name;
	
	protected Icon icon;
	
	protected DataControl dataControl;
	
	public StructureListElement(String name, DataControl dataControl) {
		this.name = name;
		this.dataControl = dataControl;
	}
	
	public String getName() {
		return name;
	}
	
	public Icon getIcon() {
		return icon;
	}
	
	public JComponent getEditPanel() {
		return EditPanelFactory.getEditPanel(dataControl);
	}

	public DataControl getDataControl() {
		return dataControl;
	}
	
	public abstract int getChildCount();
	
	public abstract StructureElement getChild(int i);
	
	
	
}
