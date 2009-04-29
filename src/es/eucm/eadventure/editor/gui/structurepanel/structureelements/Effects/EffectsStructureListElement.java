package es.eucm.eadventure.editor.gui.structurepanel.structureelements.Effects;

import java.net.URL;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.gui.structurepanel.EffectsStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.StructureListElement;

public abstract class EffectsStructureListElement extends StructureListElement{

    protected String[] groupEffects;
    
    /**
     * The path with the .html file with related help
     */
    protected String path;
    
    public EffectsStructureListElement(String name) {
	super(name, null);
	
    }

    @Override
    public StructureElement getChild(int i) {
	return new EffectsStructureElement(this,groupEffects[i] );
    }

    @Override
    public int getChildCount() {
	return groupEffects.length;
    }
    
    public String getPath(){
	return path;
    }

}
