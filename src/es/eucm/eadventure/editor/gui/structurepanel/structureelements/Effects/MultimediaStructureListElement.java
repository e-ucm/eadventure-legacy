package es.eucm.eadventure.editor.gui.structurepanel.structureelements.Effects;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import es.eucm.eadventure.common.gui.TextConstants;

public class MultimediaStructureListElement extends EffectsStructureListElement{

    
    
    private static final String[] COMPONENTS = {TextConstants.getText("Effect.PlaySound" ),TextConstants.getText( "Effect.PlayAnimation" ),
	TextConstants.getText( "Effect.TriggerCutscene"  )};
    
    private static final String LIST_URL = "Effects_Multimedia.html";
    
    public MultimediaStructureListElement() {
	super(TextConstants.getText("EffectsGroup.Multimedia"));
	icon = new ImageIcon( "img/icons/adaptationProfiles.png" );
	groupEffects = COMPONENTS;
	path = LIST_URL;
    }

}
