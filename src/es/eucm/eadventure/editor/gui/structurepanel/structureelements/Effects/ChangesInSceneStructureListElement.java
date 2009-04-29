package es.eucm.eadventure.editor.gui.structurepanel.structureelements.Effects;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import es.eucm.eadventure.common.gui.TextConstants;

public class ChangesInSceneStructureListElement extends EffectsStructureListElement{

    private static final String[] COMPONENTS = {TextConstants.getText( "Effect.ConsumeObject" ),TextConstants.getText( "Effect.GenerateObject" ),
	TextConstants.getText( "Effect.MovePlayer" ),TextConstants.getText( "Effect.MoveCharacter" )};

    private static final String LIST_URL = "Effects_SceneChanges.html";
    
    public ChangesInSceneStructureListElement() {
	super(TextConstants.getText("EffectsGroup.ChangeInScene"));
	icon = new ImageIcon( "img/icons/adaptationProfiles.png" );
	groupEffects = COMPONENTS;
	path = LIST_URL;
	
    }

}
