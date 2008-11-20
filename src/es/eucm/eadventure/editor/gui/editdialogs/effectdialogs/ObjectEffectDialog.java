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

public class ObjectEffectDialog extends EffectDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constant for the consume dialog.
	 */
	public static final int CONSUME = 0;

	/**
	 * Constant for the generate dialog.
	 */
	public static final int GENERATE = 1;

	/**
	 * Texts for the different titles of the dialogs.
	 */
	private static final String[] dialogTitles = { "ConsumeObject.Title", "GenerateObject.Title" };

	/**
	 * Texts for the different descriptions of the dialogs.
	 */
	private static final String[] dialogDescriptions = { "ConsumeObject.Description", "GenerateObject.Description" };

	/**
	 * Combo box with the items.
	 */
	private JComboBox itemsComboBox;

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            Type of the dialog (ConsumeObjectEffectDialog.CONSUME or ConsumeObjectEffectDialog.GENERATE)
	 * @param currentProperties
	 *            Set of initial values
	 */
	public ObjectEffectDialog( int type, HashMap<Integer, String> currentProperties ) {

		// Call the super method
		super( TextConstants.getText( dialogTitles[type] ) );

		// Take the list of items
		String[] itemsArray = controller.getIdentifierSummary( ).getItemIds( );

		// If there is at least one element
		if( itemsArray.length > 0 ) {
			// Create the main panel
			JPanel mainPanel = new JPanel( );
			mainPanel.setLayout( new GridBagLayout( ) );
			GridBagConstraints c = new GridBagConstraints( );

			// Set the border of the panel with the description
			mainPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( dialogDescriptions[type] ) ) ) );

			// Create and add the list of flags
			c.insets = new Insets( 2, 4, 4, 4 );
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			itemsComboBox = new JComboBox( controller.getIdentifierSummary( ).getItemIds( ) );
			mainPanel.add( itemsComboBox, c );

			// Add the panel to the center
			add( mainPanel, BorderLayout.CENTER );

			// Set the defualt values (if present)
			if( currentProperties != null ) {
				if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_TARGET ) )
					itemsComboBox.setSelectedItem( currentProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
			}

			// Set the dialog
			setResizable( false );
			pack( );
			Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
			setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
			setVisible( true );
		}

		// If the list had no elements, show an error message
		else
			controller.showErrorDialog( getTitle( ), TextConstants.getText( "ConsumeGenerateObjectEffect.ErrorNoItems" ) );
	}

	@Override
	protected void pressedOKButton( ) {
		// Create a set of properties, and put the selected value
		properties = new HashMap<Integer, String>( );
		properties.put( EffectsController.EFFECT_PROPERTY_TARGET, itemsComboBox.getSelectedItem( ).toString( ) );
	}

}
