package es.eucm.eadventure.editor.gui.editdialogs.effectdialogs;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import es.eucm.eadventure.common.data.chapter.effects.Effect;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.control.controllers.SingleEffectController;

public abstract class EffectDialog extends JDialog {

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
	protected HashMap<Integer, String> properties;

	/**
	 * Constructor.
	 * 
	 * @param title
	 *            Title of the dialog
	 */
	public EffectDialog( String title ) {

		// Call the super method
		super( Controller.getInstance( ).peekWindow( ), title, Dialog.ModalityType.APPLICATION_MODAL );

		// Set the controller and the properties
		controller = Controller.getInstance( );
		properties = null;

		// Push the dialog into the stack, and add the window listener to pop in when closing
		controller.pushWindow( this );
		addWindowListener( new WindowAdapter( ) {
			public void windowClosing( WindowEvent e ) {
				controller.popWindow( );
			}
		} );

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
	public HashMap<Integer, String> getEffectProperties( ) {
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
	 * Factory method that creates a dialog to add an effect. It returns a set of values in a Hashmap with the
	 * properties of the effect.
	 * 
	 * @param effectsController
	 *            Controller of the effects, used in some dialogs
	 * @param effectType
	 *            Type of the effect to add
	 * @return Hashmap with the properties of the effect, null if the action was cancelled
	 */
	public static HashMap<Integer, String> showAddEffectDialog( EffectsController effectsController, int effectType ) {
		return showEditEffectDialog( effectsController, effectType, null );
	}

	/**
	 * Factory method that creates a dialog to edit an effect. It returns a set of values in a Hashmap with the
	 * properties of the effect.
	 * 
	 * @param effectsController
	 *            Controller of the effects, used in some dialogs
	 * @param effectType
	 *            Type of the effect to edit
	 * @param currentProperties
	 *            Current set of properties of the effect
	 * @return Hashmap with the properties of the effect, null if the action was cancelled
	 */
	public static HashMap<Integer, String> showEditEffectDialog( EffectsController effectsController, int effectType, HashMap<Integer, String> currentProperties ) {
		HashMap<Integer, String> effectProperties = null;

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
				effectProperties = new HashMap<Integer, String>( );
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
		}

		return effectProperties;
	}
	
	public static HashMap<Integer, String> showEditRandomEffectDialog( int probability, SingleEffectController posEffectController, SingleEffectController negEffectController ) {
		return new RandomEffectDialog ( probability, posEffectController, negEffectController).getEffectProperties( );
	}
}
