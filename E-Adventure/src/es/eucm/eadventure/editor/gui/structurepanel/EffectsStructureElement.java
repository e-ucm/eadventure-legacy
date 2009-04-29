package es.eucm.eadventure.editor.gui.structurepanel;

import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.controllers.DataControl;

public class EffectsStructureElement extends StructureElement{

    
    public EffectsStructureElement(StructureListElement parent,String effectName) {
	super(effectName,null, parent);	
    }
    
    public String getName() {
	return this.name;
    }
    
    public JComponent getEditPanel() {
	//StructureControl.getInstance().changeEffectEditPanel(name);
	return null;
    }
    
    public boolean isCanRename() {
	return false;
    }

    public boolean canBeRemoved() {
	return false;
	}

    public DataControl getDataControl() {
	return null;
    }

    public boolean delete( boolean askConfirmation ) {
	return false;
    }

    public void setJustCreated(boolean justCreated) {
	
    }

    public boolean isJustCreated() {
	return false;
    }

    public boolean canBeDuplicated() {
	return false;
    }	

}
