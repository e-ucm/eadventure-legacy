package es.eucm.eadventure.editor.gui.structurepanel.structureelements.Effects;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import es.eucm.eadventure.common.gui.TextConstants;



public class GameStateStructureListElement extends EffectsStructureListElement{

    private static final String[] COMPONENTS = {TextConstants.getText( "Effect.Activate" ),TextConstants.getText( "Effect.Deactivate" ),
	TextConstants.getText( "Effect.SetValue" ), TextConstants.getText( "Effect.IncrementVar" ),TextConstants.getText( "Effect.DecrementVar" )};
    
    private static final String LIST_URL = "Effects_GameState.html";
    
    public GameStateStructureListElement() {
	super(TextConstants.getText("EffectsGroup.GameState"));
	icon = new ImageIcon( "img/icons/adaptationProfiles.png" );
	groupEffects = COMPONENTS;	
	path = LIST_URL;
    }

   

}
