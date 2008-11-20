package es.eucm.eadventure.editor.gui.editdialogs.effectdialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.EffectsController;

public class SpeakPlayerEffectDialog extends EffectDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

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
	public SpeakPlayerEffectDialog( HashMap<Integer, String> currentProperties ) {

		// Call the super method
		super( TextConstants.getText( "SpeakPlayerEffect.Title" ) );

		// Create the main panel
		JPanel mainPanel = new JPanel( );
		mainPanel.setLayout( new GridBagLayout( ) );
		GridBagConstraints c = new GridBagConstraints( );

		// Set the border of the panel with the description
		mainPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "SpeakPlayerEffect.Description" ) ) ) );

		// Create and add the list of flags
		c.insets = new Insets( 2, 4, 4, 4 );
		c.fill = GridBagConstraints.BOTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
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
				textArea.setText( currentProperties.get( EffectsController.EFFECT_PROPERTY_TEXT ) );
		}

		// Set the dialog
		setResizable( false );
		setSize( 400, 140 );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setVisible( true );
	}

	@Override
	protected void pressedOKButton( ) {
		// Create a set of properties, and put the selected value
		properties = new HashMap<Integer, String>( );
		properties.put( EffectsController.EFFECT_PROPERTY_TEXT, textArea.getText( ) );
	}

}
