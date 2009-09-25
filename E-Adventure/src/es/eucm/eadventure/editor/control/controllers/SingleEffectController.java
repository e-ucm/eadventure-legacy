/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.editor.control.controllers;

import java.util.HashMap;

import es.eucm.eadventure.common.data.chapter.effects.AbstractEffect;
import es.eucm.eadventure.common.data.chapter.effects.Effect;
import es.eucm.eadventure.common.data.chapter.effects.Effects;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.general.effects.AddEffectTool;
import es.eucm.eadventure.editor.gui.editdialogs.effectdialogs.EffectDialog;

/**
 * This class is the controller of the effects blocks. It manages the insertion
 * and modification of the effects lists.
 * 
 * @author Bruno Torijano Bueno
 */
public class SingleEffectController extends EffectsController {

    private static Effects createEffectsStructure( AbstractEffect effect ) {

        Effects effects = new Effects( );
        if( effect != null )
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

        super( createEffectsStructure( effect ) );
    }

    public SingleEffectController( ) {

        super( new Effects( ) );
    }

    /**
     * Returns the info of the effect in the given position.
     * 
     * @param index
     *            Position of the effect
     * @return Information about the effect
     */
    public String getEffectInfo( ) {

        if( getEffectCount( ) > 0 )
            return getEffectInfo( 0 );
        return null;
    }

    /**
     * Adds a new condition to the block.
     * 
     * @return True if an effect was added, false otherwise
     */
    @Override
    public boolean addEffect( ) {

        effects.clear( );

        boolean effectAdded = false;

        // Create a list with the names of the effects (in the same order as the next)
        final String[] effectNames = { TC.get( "Effect.Activate" ), TC.get( "Effect.Deactivate" ), TC.get( "Effect.ConsumeObject" ), TC.get( "Effect.GenerateObject" ), TC.get( "Effect.SpeakPlayer" ), TC.get( "Effect.SpeakCharacter" ), TC.get( "Effect.TriggerBook" ), TC.get( "Effect.PlaySound" ), TC.get( "Effect.PlayAnimation" ), TC.get( "Effect.MovePlayer" ), TC.get( "Effect.MoveCharacter" ), TC.get( "Effect.TriggerConversation" ), TC.get( "Effect.TriggerCutscene" ), TC.get( "Effect.TriggerScene" ), TC.get( "Effect.TriggerLastScene" ), TC.get( "Effect.ShowText" ), TC.get( "Effect.WaitTime" ), TC.get( "Effect.MoveObject" ) };

        // Create a list with the types of the effects (in the same order as the previous)
        final int[] effectTypes = { Effect.ACTIVATE, Effect.DEACTIVATE, Effect.CONSUME_OBJECT, Effect.GENERATE_OBJECT, Effect.SPEAK_PLAYER, Effect.SPEAK_CHAR, Effect.TRIGGER_BOOK, Effect.PLAY_SOUND, Effect.PLAY_ANIMATION, Effect.MOVE_PLAYER, Effect.MOVE_NPC, Effect.TRIGGER_CONVERSATION, Effect.TRIGGER_CUTSCENE, Effect.TRIGGER_SCENE, Effect.TRIGGER_LAST_SCENE, Effect.SHOW_TEXT, Effect.WAIT_TIME, Effect.MOVE_OBJECT };

        // Show a dialog to select the type of the effect
        String selectedValue = controller.showInputDialog( TC.get( "Effects.OperationAddEffect" ), TC.get( "Effects.SelectEffectType" ), effectNames );

        // If some effect was selected
        if( selectedValue != null ) {
            // Store the type of the effect selected
            int selectedType = 0;
            for( int i = 0; i < effectNames.length; i++ )
                if( effectNames[i].equals( selectedValue ) )
                    selectedType = effectTypes[i];

            HashMap<Integer, Object> effectProperties = null;
            if( selectedType == Effect.MOVE_PLAYER && Controller.getInstance( ).isPlayTransparent( ) ) {
                Controller.getInstance( ).showErrorDialog( TC.get( "Error.EffectMovePlayerNotAllowed.Title" ), TC.get( "Error.EffectMovePlayerNotAllowed.Message" ) );
            }
            else {
                effectProperties = EffectDialog.showAddEffectDialog( this, selectedType );
            }

            if( effectProperties != null ) {
                AbstractEffect newEffect = null;

                newEffect = createNewEffect(effectProperties);
                

                effectAdded = controller.addTool( new AddEffectTool( effects, newEffect, null ) );
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
        if( getEffectCount( ) > 0 )
            deleteEffect( 0 );
    }

    /**
     * Edits the effect in the given position.
     * 
     * @param index
     *            Index of the effect
     * @return True if the effect was moved, false otherwise
     */
    public boolean editEffect( ) {

        if( getEffectCount( ) > 0 )
            return editEffect( 0 );
        else
            return addEffect( );
    }

    public AbstractEffect getEffect( ) {

        if( getEffectCount( ) > 0 )
            return effects.getEffects( ).get( 0 );
        else
            return null;
    }

}