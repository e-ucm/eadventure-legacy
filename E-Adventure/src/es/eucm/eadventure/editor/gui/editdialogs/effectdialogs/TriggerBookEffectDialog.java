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

public class TriggerBookEffectDialog extends EffectDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Combo box with the books.
	 */
	private JComboBox booksComboBox;

	/**
	 * Constructor.
	 * 
	 * @param currentProperties
	 *            Set of initial values
	 */
	public TriggerBookEffectDialog( HashMap<Integer, String> currentProperties ) {

		// Call the super method
		super( TextConstants.getText( "TriggerBookEffect.Title" ) );

		// Take the list of books
		String[] booksArray = controller.getIdentifierSummary( ).getBookIds( );

		// If there is at least one element
		if( booksArray.length > 0 ) {
			// Create the main panel
			JPanel mainPanel = new JPanel( );
			mainPanel.setLayout( new GridBagLayout( ) );
			GridBagConstraints c = new GridBagConstraints( );

			// Set the border of the panel with the description
			mainPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "TriggerBookEffect.Description" ) ) ) );

			// Create and add the list of flags
			c.insets = new Insets( 2, 4, 4, 4 );
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			booksComboBox = new JComboBox( booksArray );
			mainPanel.add( booksComboBox, c );

			// Add the panel to the center
			add( mainPanel, BorderLayout.CENTER );

			// Set the defualt values (if present)
			if( currentProperties != null ) {
				if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_TARGET ) )
					booksComboBox.setSelectedItem( currentProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
			}

			// Set the dialog
			setResizable( false );
			setSize( 240, 140 );
			Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
			setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
			setVisible( true );
		}

		// If the list had no elements, show an error message
		else
			controller.showErrorDialog( getTitle( ), TextConstants.getText( "TriggerBookEffect.ErrorNoBooks" ) );
	}

	@Override
	protected void pressedOKButton( ) {
		// Create a set of properties, and put the selected value
		properties = new HashMap<Integer, String>( );
		properties.put( EffectsController.EFFECT_PROPERTY_TARGET, booksComboBox.getSelectedItem( ).toString( ) );
	}

}
