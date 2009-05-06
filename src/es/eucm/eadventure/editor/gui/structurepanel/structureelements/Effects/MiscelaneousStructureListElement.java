package es.eucm.eadventure.editor.gui.structurepanel.structureelements.Effects;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import es.eucm.eadventure.common.gui.TextConstants;

public class MiscelaneousStructureListElement extends EffectsStructureListElement{

    
    private static final String LIST_URL = "effects/Effects_Miscellaneous.html";
    
    public MiscelaneousStructureListElement() {
	super(TextConstants.getText("EffectsGroup.Miscellaneous"));
	//icon = new ImageIcon( "img/icons/adaptationProfiles.png" );
	groupEffects = new String[] {TextConstants.getText( "Effect.MacroReference" ),TextConstants.getText( "Effect.CancelAction" ),
		TextConstants.getText( "Effect.RandomEffect" ),TextConstants.getText( "Effect.WaitTime" )};
;
	path = LIST_URL;
    }
    
}
