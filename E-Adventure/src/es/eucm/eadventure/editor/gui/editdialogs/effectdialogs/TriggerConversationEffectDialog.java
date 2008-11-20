package es.eucm.eadventure.editor.gui.editdialogs.effectdialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.EffectsController;

public class TriggerConversationEffectDialog extends EffectDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Combo box with the conversations.
	 */
	private JComboBox conversationsComboBox;

	/**
	 * Constructor.
	 * 
	 * @param currentProperties
	 *            Set of initial values
	 */
	public TriggerConversationEffectDialog( HashMap<Integer, String> currentProperties ) {

		// Call the super method
		super( TextConstants.getText( "TriggerConversationEffect.Title" ) );

		// Take the array of conversations
		String[] conversationsArray = controller.getIdentifierSummary( ).getConversationsIds( );

		// If there is at least one
		if( conversationsArray.length > 0 ) {
			// Create the main panel
			JPanel mainPanel = new JPanel( );
			mainPanel.setLayout( new GridBagLayout( ) );
			GridBagConstraints c = new GridBagConstraints( );

			// Set the border of the panel with the description
			mainPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "TriggerConversationEffect.Description" ) ) ) );

			// Create and add the list of flags
			c.insets = new Insets( 2, 4, 4, 4 );
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			conversationsComboBox = new JComboBox( conversationsArray );
			mainPanel.add( conversationsComboBox, c );

			// Add the panel to the center
			add( mainPanel, BorderLayout.CENTER );

			// Set the defualt values (if present)
			if( currentProperties != null ) {
				if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_TARGET ) )
					conversationsComboBox.setSelectedItem( currentProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
			}

			// Set the dialog
			setResizable( false );
			setSize( 280, 140 );
			Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
			setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
			setVisible( true );
		}

		// If there are no values, show an error
		else
			controller.showErrorDialog( getTitle( ), TextConstants.getText( "TriggerConversationEffect.ErrorNoConversations" ) );
	}

	@Override
	protected void pressedOKButton( ) {
		// Create a set of properties, and put the selected value
		properties = new HashMap<Integer, String>( );
		properties.put( EffectsController.EFFECT_PROPERTY_TARGET, conversationsComboBox.getSelectedItem( ).toString( ) );
	}

}
