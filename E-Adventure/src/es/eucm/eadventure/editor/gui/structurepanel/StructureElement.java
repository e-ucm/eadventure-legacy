package es.eucm.eadventure.editor.gui.structurepanel;

import javax.swing.Icon;
import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.common.data.HasId;

public class StructureElement {

	protected Icon icon;
	
	protected String name;
	
	private DataControl dataControl;

	private StructureListElement parent;
		
	public StructureElement(DataControl dataControl, StructureListElement parent) {
		this.dataControl = dataControl;
		this.parent = parent;
		name = null;
	}
	
	public StructureElement(String name, DataControl dataControl, StructureListElement parent) {
		this(dataControl, parent);
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
	
	public DataControl getDataControl() {
		return dataControl;
	}
	
	public boolean delete( boolean askConfirmation ) {
		if( getDataControl( ).canBeDeleted( ) && parent.getDataControl( ).deleteElement( getDataControl( ), askConfirmation ) ) {
			Controller.getInstance( ).updateVarFlagSummary( );
			return true;
		}
		return false;
	}

	public void setJustCreated(boolean justCreated) {
		dataControl.setJustCreated(justCreated);
	}
	
	public boolean isJustCreated() {
		return dataControl.isJustCreated();
	}

	public boolean canBeDuplicated() {
		return dataControl.canBeDuplicated();
	}
}
