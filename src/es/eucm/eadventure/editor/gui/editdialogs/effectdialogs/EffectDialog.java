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
package es.eucm.eadventure.editor.gui.editdialogs.effectdialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import es.eucm.eadventure.common.data.chapter.effects.Effect;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.control.controllers.SingleEffectController;
import es.eucm.eadventure.editor.gui.editdialogs.ToolManagableDialog;

public abstract class EffectDialog extends ToolManagableDialog {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Link with the main controller.
     */
    protected Controller controller;

    /**
     * Properties of the effect.
     */
    protected HashMap<Integer, Object> properties;

    /**
     * Constructor.
     * 
     * @param title
     *            Title of the dialog
     */
    public EffectDialog( String title, boolean worksInLocal ) {

        // Call the super method
        super( Controller.getInstance( ).peekWindow( ), title, worksInLocal );//, Dialog.ModalityType.APPLICATION_MODAL );

        // Set the controller and the properties
        controller = Controller.getInstance( );
        properties = null;

        // Create the panel for the buttons
        JPanel buttonsPanel = new JPanel( );
        buttonsPanel.setLayout( new FlowLayout( FlowLayout.RIGHT, 4, 4 ) );

        // Create and add the OK button
        JButton okButton = new JButton( TextConstants.getText( "GeneralText.OK" ) );
        okButton.addActionListener( new OKButtonActionListener( ) );
        buttonsPanel.add( okButton );

        // Create and add the Cancel button
        JButton cancelButton = new JButton( TextConstants.getText( "GeneralText.Cancel" ) );
        cancelButton.addActionListener( new CancelButtonActionListener( ) );
        buttonsPanel.add( cancelButton );

        // Add and empty border
        buttonsPanel.setBorder( BorderFactory.createEmptyBorder( 8, 4, 4, 4 ) );

        // Add the panel to the south
        add( buttonsPanel, BorderLayout.SOUTH );
    }

    /**
     * Returns the set of properties of the effect dialog.
     * 
     * @return Hash map with the effect properties and their values
     */
    public HashMap<Integer, Object> getEffectProperties( ) {

        return properties;
    }

    /**
     * Actions to perform when the OK button has been pressed.
     */
    protected abstract void pressedOKButton( );

    /**
     * Listener for the OK button.
     */
    private class OKButtonActionListener implements ActionListener {

        public void actionPerformed( ActionEvent arg0 ) {

            pressedOKButton( );
            setVisible( false );

        }
    }

    /**
     * Listener for the Cancel button.
     */
    private class CancelButtonActionListener implements ActionListener {

        public void actionPerformed( ActionEvent arg0 ) {

            setVisible( false );

        }
    }

    /**
     * Factory method that creates a dialog to add an effect. It returns a set
     * of values in a Hashmap with the properties of the effect.
     * 
     * @param effectsController
     *            Controller of the effects, used in some dialogs
     * @param effectType
     *            Type of the effect to add
     * @return Hashmap with the properties of the effect, null if the action was
     *         cancelled
     */
    public static HashMap<Integer, Object> showAddEffectDialog( EffectsController effectsController, int effectType ) {

        return showEditEffectDialog( effectsController, effectType, null );
    }

    /**
     * Factory method that creates a dialog to edit an effect. It returns a set
     * of values in a Hashmap with the properties of the effect.
     * 
     * @param effectsController
     *            Controller of the effects, used in some dialogs
     * @param effectType
     *            Type of the effect to edit
     * @param currentProperties
     *            Current set of properties of the effect
     * @return Hashmap with the properties of the effect, null if the action was
     *         cancelled
     */
    public static HashMap<Integer, Object> showEditEffectDialog( EffectsController effectsController, int effectType, HashMap<Integer, Object> currentProperties ) {

        HashMap<Integer, Object> effectProperties = null;

        switch( effectType ) {
            case Effect.ACTIVATE:
                effectProperties = new FlagEffectDialog( FlagEffectDialog.ACTIVATE, currentProperties ).getEffectProperties( );
                break;
            case Effect.DEACTIVATE:
                effectProperties = new FlagEffectDialog( FlagEffectDialog.DEACTIVATE, currentProperties ).getEffectProperties( );
                break;
            case Effect.SET_VALUE:
                effectProperties = new VarEffectDialog( VarEffectDialog.SET_VALUE, currentProperties ).getEffectProperties( );
                break;
            case Effect.INCREMENT_VAR:
                effectProperties = new VarEffectDialog( VarEffectDialog.INCREMENT_VAR, currentProperties ).getEffectProperties( );
                break;
            case Effect.DECREMENT_VAR:
                effectProperties = new VarEffectDialog( VarEffectDialog.DECREMENT_VAR, currentProperties ).getEffectProperties( );
                break;
            case Effect.MACRO_REF:
                effectProperties = new MacroReferenceEffectDialog( currentProperties ).getEffectProperties( );
                break;
            case Effect.CONSUME_OBJECT:
                effectProperties = new ObjectEffectDialog( ObjectEffectDialog.CONSUME, currentProperties ).getEffectProperties( );
                break;
            case Effect.GENERATE_OBJECT:
                effectProperties = new ObjectEffectDialog( ObjectEffectDialog.GENERATE, currentProperties ).getEffectProperties( );
                break;
            case Effect.CANCEL_ACTION:
            case Effect.TRIGGER_LAST_SCENE:
                effectProperties = new HashMap<Integer, Object>( );
                break;
            case Effect.SPEAK_PLAYER:
                effectProperties = new SpeakPlayerEffectDialog( currentProperties ).getEffectProperties( );
                break;
            case Effect.SPEAK_CHAR:
                effectProperties = new SpeakCharacterEffectDialog( currentProperties ).getEffectProperties( );
                break;
            case Effect.TRIGGER_BOOK:
                effectProperties = new TriggerBookEffectDialog( currentProperties ).getEffectProperties( );
                break;
            case Effect.PLAY_SOUND:
                effectProperties = new PlaySoundEffectDialog( effectsController, currentProperties ).getEffectProperties( );
                break;
            case Effect.PLAY_ANIMATION:
                effectProperties = new PlayAnimationEffectDialog( effectsController, currentProperties ).getEffectProperties( );
                break;
            case Effect.MOVE_PLAYER:
                effectProperties = new MovePlayerEffectDialog( currentProperties ).getEffectProperties( );
                break;
            case Effect.MOVE_NPC:
                effectProperties = new MoveNPCEffectDialog( currentProperties ).getEffectProperties( );
                break;
            case Effect.TRIGGER_CONVERSATION:
                effectProperties = new TriggerConversationEffectDialog( currentProperties ).getEffectProperties( );
                break;
            case Effect.TRIGGER_CUTSCENE:
                effectProperties = new TriggerCutsceneEffectDialog( currentProperties ).getEffectProperties( );
                break;
            case Effect.TRIGGER_SCENE:
                effectProperties = new TriggerSceneEffectDialog( currentProperties ).getEffectProperties( );
                break;
            case Effect.WAIT_TIME:
                effectProperties = new WaitTimeEffectDialog( currentProperties ).getEffectProperties( );
                break;
            case Effect.SHOW_TEXT:
                effectProperties = new ShowTextEffectDialog( currentProperties ).getEffectProperties( );
                break;
            case Effect.HIGHLIGHT_ITEM:
                effectProperties = new HighlightItemEffectDialog( HighlightItemEffectDialog.HIGHLIGHT, currentProperties ).getEffectProperties( );
                break;
        }

        return effectProperties;
    }

    public static HashMap<Integer, Object> showEditRandomEffectDialog( int probability, SingleEffectController posEffectController, SingleEffectController negEffectController ) {

        return new RandomEffectDialog( probability, posEffectController, negEffectController ).getEffectProperties( );
    }
}
