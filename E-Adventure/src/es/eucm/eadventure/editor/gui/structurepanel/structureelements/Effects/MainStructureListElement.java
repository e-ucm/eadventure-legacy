package es.eucm.eadventure.editor.gui.structurepanel.structureelements.Effects;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.gui.structurepanel.EffectsStructurePanel;

public class MainStructureListElement extends EffectsStructureListElement{

    private static final String LIST_URL = "effects/Effects_General.html";
    
    public MainStructureListElement() {
		super(TextConstants.getText("EffectsGroup.Main"));
		//icon = EffectsStructurePanel.getEffectIcon(name, EffectsStructurePanel.ICON_SIZE_MEDIUM);
			//new ImageIcon( "img/icons/adaptationProfiles.png" );
		groupEffects = new String[]{
				TextConstants.getText( "Effect.Activate" ),TextConstants.getText( "Effect.Deactivate" ),
				TextConstants.getText( "Effect.SetValue" ), TextConstants.getText( "Effect.IncrementVar" ),
				TextConstants.getText( "Effect.DecrementVar" ),	TextConstants.getText("Effect.PlaySound" ),
				TextConstants.getText( "Effect.PlayAnimation" ),
				TextConstants.getText( "Effect.SpeakPlayer" ),TextConstants.getText( "Effect.SpeakCharacter" ),
				TextConstants.getText( "Effect.ShowText" ),
				TextConstants.getText( "Effect.TriggerConversation" ),
				TextConstants.getText( "Effect.TriggerScene" ),
				TextConstants.getText( "Effect.TriggerLastScene" ),
				TextConstants.getText( "Effect.TriggerCutscene" ),
				TextConstants.getText( "Effect.TriggerBook" ),
				TextConstants.getText( "Effect.ConsumeObject" ),
				TextConstants.getText( "Effect.GenerateObject" ),TextConstants.getText( "Effect.MovePlayer" ),
				TextConstants.getText( "Effect.MoveCharacter" ),TextConstants.getText( "Effect.MacroReference" ),
				TextConstants.getText( "Effect.CancelAction" ),	TextConstants.getText( "Effect.RandomEffect" ),
				TextConstants.getText( "Effect.WaitTime" )};
		path = LIST_URL;
    }

}
