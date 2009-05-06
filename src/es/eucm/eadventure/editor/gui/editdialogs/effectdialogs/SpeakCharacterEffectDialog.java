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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.EffectsController;

public class SpeakCharacterEffectDialog extends EffectDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Combo box for the characters.
	 */
	private JComboBox charactersComboBox;

	/**
	 * Combo box with the books.
	 */
	private JTextArea textArea;

	/**
	 * Constructor.
	 * 
	 * @param currentProperties
	 *            Set of initial values
	 */
	public SpeakCharacterEffectDialog( HashMap<Integer, Object> currentProperties ) {

		// Call the super method
		super( TextConstants.getText( "SpeakCharacterEffect.Title" ) , false);

		// Take the list of characters
		String[] charactersArray = controller.getIdentifierSummary( ).getNPCIds( );

		// If there is at least one
		if( charactersArray.length > 0 ) {
			// Create the main panel
			JPanel mainPanel = new JPanel( );
			mainPanel.setLayout( new GridBagLayout( ) );
			GridBagConstraints c = new GridBagConstraints( );

			// Set the border of the panel with the description
			mainPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "SpeakCharacterEffect.Description" ) ) ) );

			// Create and add the list of characters
			c.insets = new Insets( 2, 4, 4, 4 );
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			charactersComboBox = new JComboBox( charactersArray );
			mainPanel.add( charactersComboBox, c );

			// Create and add the list of flags
			c.fill = GridBagConstraints.BOTH;
			c.gridy = 1;
			c.weighty = 1;
			textArea = new JTextArea( );
			textArea.setWrapStyleWord( true );
			textArea.setLineWrap( true );
			mainPanel.add( new JScrollPane( textArea ), c );

			// Add the panel to the center
			add( mainPanel, BorderLayout.CENTER );

			// Set the defualt values (if present)
			if( currentProperties != null ) {
				if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_TEXT ) )
					textArea.setText( (String)currentProperties.get( EffectsController.EFFECT_PROPERTY_TEXT ) );
			}

			// Set the dialog
			setResizable( false );
			setSize( 400, 300 );
			Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
			setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
			setVisible( true );
		}

		// If the list had no elements, show an error message
		else
			controller.showErrorDialog( getTitle( ), TextConstants.getText( "SpeakCharacterEffect.ErrorNoCharacters" ) );
	}

	@Override
	protected void pressedOKButton( ) {
		// Create a set of properties, and put the selected value
		properties = new HashMap<Integer, Object>( );
		properties.put( EffectsController.EFFECT_PROPERTY_TARGET, charactersComboBox.getSelectedItem( ).toString( ) );
		properties.put( EffectsController.EFFECT_PROPERTY_TEXT, textArea.getText( ) );
	}

}
