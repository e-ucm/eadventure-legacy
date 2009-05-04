package es.eucm.eadventure.editor.gui.structurepanel.structureelements.Effects;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import es.eucm.eadventure.common.gui.TextConstants;

public class MainStructureListElement extends EffectsStructureListElement{

    private static final String LIST_URL = "effects/Effects_General.html";
    
    public MainStructureListElement() {
	super(TextConstants.getText("EffectsGroup.Main"));
	icon = new ImageIcon( "img/icons/adaptationProfiles.png" );
	groupEffects = new String[0];
	path = LIST_URL;
    }

}
