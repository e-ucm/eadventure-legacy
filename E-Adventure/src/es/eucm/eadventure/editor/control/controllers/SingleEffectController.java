/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.editor.control.controllers;

import java.util.HashMap;

import es.eucm.eadventure.common.data.chapter.effects.AbstractEffect;
import es.eucm.eadventure.common.data.chapter.effects.ActivateEffect;
import es.eucm.eadventure.common.data.chapter.effects.ConsumeObjectEffect;
import es.eucm.eadventure.common.data.chapter.effects.DeactivateEffect;
import es.eucm.eadventure.common.data.chapter.effects.DecrementVarEffect;
import es.eucm.eadventure.common.data.chapter.effects.Effect;
import es.eucm.eadventure.common.data.chapter.effects.Effects;
import es.eucm.eadventure.common.data.chapter.effects.GenerateObjectEffect;
import es.eucm.eadventure.common.data.chapter.effects.IncrementVarEffect;
import es.eucm.eadventure.common.data.chapter.effects.MacroReferenceEffect;
import es.eucm.eadventure.common.data.chapter.effects.MoveNPCEffect;
import es.eucm.eadventure.common.data.chapter.effects.MovePlayerEffect;
import es.eucm.eadventure.common.data.chapter.effects.PlayAnimationEffect;
import es.eucm.eadventure.common.data.chapter.effects.PlaySoundEffect;
import es.eucm.eadventure.common.data.chapter.effects.SetValueEffect;
import es.eucm.eadventure.common.data.chapter.effects.ShowTextEffect;
import es.eucm.eadventure.common.data.chapter.effects.SpeakCharEffect;
import es.eucm.eadventure.common.data.chapter.effects.SpeakPlayerEffect;
import es.eucm.eadventure.common.data.chapter.effects.TriggerBookEffect;
import es.eucm.eadventure.common.data.chapter.effects.TriggerConversationEffect;
import es.eucm.eadventure.common.data.chapter.effects.TriggerCutsceneEffect;
import es.eucm.eadventure.common.data.chapter.effects.TriggerLastSceneEffect;
import es.eucm.eadventure.common.data.chapter.effects.TriggerSceneEffect;
import es.eucm.eadventure.common.data.chapter.effects.WaitTimeEffect;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.general.effects.AddEffectTool;
import es.eucm.eadventure.editor.gui.editdialogs.effectdialogs.EffectDialog;

/**
 * This class is the controller of the effects blocks. It manages the insertion and modification of the effects lists.
 * 
 * @author Bruno Torijano Bueno
 */
public class SingleEffectController extends EffectsController{

	private static Effects createEffectsStructure ( AbstractEffect effect ){
		Effects effects = new Effects();
		if (effect!=null)
			effects.add( effect );
		return effects;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param effects
	 *            Contained block of effects
	 */
	public SingleEffectController( AbstractEffect effect ) {
		super( createEffectsStructure(effect) );
	}

	public SingleEffectController( ) {
		super ( new Effects() );
	}

	/**
	 * Returns the info of the effect in the given position.
	 * 
	 * @param index
	 *            Position of the effect
	 * @return Information about the effect
	 */
	public String getEffectInfo( ) {
		if ( getEffectCount()>0 )
			return getEffectInfo (0);
		return null;
	}

	/**
	 * Adds a new condition to the block.
	 * 
	 * @return True if an effect was added, false otherwise
	 */
	public boolean addEffect( ) {
		
		effects.clear( );
		
		boolean effectAdded = false;

		// Create a list with the names of the effects (in the same order as the next)
		final String[] effectNames = { TextConstants.getText( "Effect.Activate" ), TextConstants.getText( "Effect.Deactivate" ), TextConstants.getText( "Effect.ConsumeObject" ), TextConstants.getText( "Effect.GenerateObject" ), TextConstants.getText( "Effect.SpeakPlayer" ), TextConstants.getText( "Effect.SpeakCharacter" ), TextConstants.getText( "Effect.TriggerBook" ), TextConstants.getText( "Effect.PlaySound" ), TextConstants.getText( "Effect.PlayAnimation" ), TextConstants.getText( "Effect.MovePlayer" ), TextConstants.getText( "Effect.MoveCharacter" ), TextConstants.getText( "Effect.TriggerConversation" ), TextConstants.getText( "Effect.TriggerCutscene" ), TextConstants.getText( "Effect.TriggerScene" ), TextConstants.getText( "Effect.TriggerLastScene" ),TextConstants.getText( "Effect.ShowText" ),TextConstants.getText( "Effect.WaitTime" ) };

		// Create a list with the types of the effects (in the same order as the previous)
		final int[] effectTypes = { Effect.ACTIVATE, Effect.DEACTIVATE, Effect.CONSUME_OBJECT, Effect.GENERATE_OBJECT, Effect.SPEAK_PLAYER, Effect.SPEAK_CHAR, Effect.TRIGGER_BOOK, Effect.PLAY_SOUND, Effect.PLAY_ANIMATION, Effect.MOVE_PLAYER, Effect.MOVE_NPC, Effect.TRIGGER_CONVERSATION, Effect.TRIGGER_CUTSCENE, Effect.TRIGGER_SCENE, Effect.TRIGGER_LAST_SCENE,Effect.SHOW_TEXT,Effect.WAIT_TIME };

		// Show a dialog to select the type of the effect
		String selectedValue = controller.showInputDialog( TextConstants.getText( "Effects.OperationAddEffect" ), TextConstants.getText( "Effects.SelectEffectType" ), effectNames );

		// If some effect was selected
		if( selectedValue != null ) {
			// Store the type of the effect selected
			int selectedType = 0;
			for( int i = 0; i < effectNames.length; i++ )
				if( effectNames[i].equals( selectedValue ) )
					selectedType = effectTypes[i];

			HashMap<Integer, Object> effectProperties = null;
			if (selectedType==Effect.MOVE_PLAYER && Controller.getInstance( ).isPlayTransparent( )){
				Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.EffectMovePlayerNotAllowed.Title" ), TextConstants.getText( "Error.EffectMovePlayerNotAllowed.Message" ) );
			}else{
				effectProperties = EffectDialog.showAddEffectDialog( this, selectedType );	
			}
			

			if( effectProperties != null ) {
				AbstractEffect newEffect = null;

				// Take all the values from the set
				String target = (String)effectProperties.get( EFFECT_PROPERTY_TARGET );
				int value = 0;
				if (effectProperties.containsKey( EFFECT_PROPERTY_VALUE))
					value =	Integer.parseInt( (String)effectProperties.get( EFFECT_PROPERTY_VALUE) );
				
				String path = (String)effectProperties.get( EFFECT_PROPERTY_PATH );
				String text = (String)effectProperties.get( EFFECT_PROPERTY_TEXT );

				int x = 0;
				if( effectProperties.containsKey( EFFECT_PROPERTY_X ) )
					x = Integer.parseInt((String)effectProperties.get( EFFECT_PROPERTY_X ) );

				int y = 0;
				if( effectProperties.containsKey( EFFECT_PROPERTY_Y ) )
					y = Integer.parseInt( (String)effectProperties.get( EFFECT_PROPERTY_Y ) );

				boolean background = false;
				if( effectProperties.containsKey( EFFECT_PROPERTY_BACKGROUND ) )
					background = Boolean.parseBoolean( (String)effectProperties.get( EFFECT_PROPERTY_BACKGROUND ) );

				int time=0;
				if ( effectProperties.containsKey( EFFECT_PROPERTY_TIME  ) )
					time = Integer.parseInt( (String)effectProperties.get( EFFECT_PROPERTY_TIME ) );

				int frontColor=0;
				if ( effectProperties.containsKey( EFFECT_PROPERTY_FRONT_COLOR  ) )
				    frontColor = Integer.parseInt( (String)effectProperties.get( EFFECT_PROPERTY_FRONT_COLOR ) );

				int borderColor=0;
				if ( effectProperties.containsKey( EFFECT_PROPERTY_BORDER_COLOR  ) )
				    borderColor = Integer.parseInt( (String)effectProperties.get( EFFECT_PROPERTY_BORDER_COLOR ) );

				
				switch( selectedType ) {
					case Effect.ACTIVATE:
						newEffect = new ActivateEffect( target );
						controller.getVarFlagSummary( ).addFlagReference( target );
						break;
					case Effect.DEACTIVATE:
						newEffect = new DeactivateEffect( target );
						controller.getVarFlagSummary( ).addFlagReference( target );
						break;
					case Effect.SET_VALUE:
						newEffect = new SetValueEffect( target, value );
						controller.getVarFlagSummary( ).addVarReference( target );
						break;
					case Effect.INCREMENT_VAR:
						newEffect = new IncrementVarEffect( target, value );
						controller.getVarFlagSummary( ).addVarReference( target );
						break;
					case Effect.DECREMENT_VAR:
						newEffect = new DecrementVarEffect( target, value );
						controller.getVarFlagSummary( ).addVarReference( target );
						break;
					case Effect.MACRO_REF:
						newEffect = new MacroReferenceEffect( target );
						break;

					case Effect.CONSUME_OBJECT:
						newEffect = new ConsumeObjectEffect( target );
						break;
					case Effect.GENERATE_OBJECT:
						newEffect = new GenerateObjectEffect( target );
						break;
					case Effect.TRIGGER_LAST_SCENE:
						newEffect = new TriggerLastSceneEffect( );
						break;
					case Effect.SPEAK_PLAYER:
						newEffect = new SpeakPlayerEffect( text );
						break;
					case Effect.SPEAK_CHAR:
						newEffect = new SpeakCharEffect( target, text );
						break;
					case Effect.TRIGGER_BOOK:
						newEffect = new TriggerBookEffect( target );
						break;
					case Effect.PLAY_SOUND:
						newEffect = new PlaySoundEffect( background, path );
						break;
					case Effect.PLAY_ANIMATION:
						newEffect = new PlayAnimationEffect( path, x, y );
						break;
					case Effect.MOVE_PLAYER:
						newEffect = new MovePlayerEffect( x, y );
						break;
					case Effect.MOVE_NPC:
						newEffect = new MoveNPCEffect( target, x, y );
						break;
					case Effect.TRIGGER_CONVERSATION:
						newEffect = new TriggerConversationEffect( target );
						break;
					case Effect.TRIGGER_CUTSCENE:
						newEffect = new TriggerCutsceneEffect( target );
						break;
					case Effect.TRIGGER_SCENE:
						newEffect = new TriggerSceneEffect( target, x, y );
						break;
					case Effect.WAIT_TIME:
					    	newEffect = new WaitTimeEffect(time);
					    	break;
					case Effect.SHOW_TEXT:
					    	newEffect = new ShowTextEffect(text,x,y,frontColor,borderColor);
					    	break;
				}

				effectAdded = controller.addTool(new AddEffectTool(effects,newEffect,null));
			}
		}

		return effectAdded;
	}

	/**
	 * Deletes the effect in the given position.
	 * 
	 * @param index
	 *            Index of the effect
	 */
	public void deleteEffect( ) {
		if ( getEffectCount( )>0 )
			deleteEffect (0);
	}

	/**
	 * Edits the effect in the given position.
	 * 
	 * @param index
	 *            Index of the effect
	 * @return True if the effect was moved, false otherwise
	 */
	public boolean editEffect( ) {
		if ( getEffectCount( )>0 )
			return editEffect ( 0 );
		else
			return addEffect ( );
	}

	public AbstractEffect getEffect (){
		if ( getEffectCount( )>0 )
			return effects.getEffects( ).get( 0 );
		else
			return null;
	}

}