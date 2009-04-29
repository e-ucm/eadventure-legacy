package es.eucm.eadventure.editor.gui.structurepanel.structureelements.Effects;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import es.eucm.eadventure.common.gui.TextConstants;

public class FeedbackStructureListElement extends EffectsStructureListElement{

    private static final String[] COMPONENTS = {TextConstants.getText( "Effect.SpeakPlayer" ),TextConstants.getText( "Effect.SpeakCharacter" ),
	TextConstants.getText( "Effect.TriggerBook" ),TextConstants.getText( "Effect.ShowText" ),TextConstants.getText( "Effect.TriggerCutscene" ),TextConstants.getText( "Effect.TriggerConversation" )};

    private static final String LIST_URL = "Effects_Feedback.html";
    
    public FeedbackStructureListElement() {
	super(TextConstants.getText("EffectsGroup.Feedback"));
	icon = new ImageIcon( "img/icons/adaptationProfiles.png" );
	groupEffects = COMPONENTS;
	path = LIST_URL;
    }
    

}
