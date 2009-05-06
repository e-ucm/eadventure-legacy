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
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.control.controllers.VarFlagsController;

public class FlagEffectDialog extends EffectDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constant for the activate dialog.
	 */
	public static final int ACTIVATE = 0;

	/**
	 * Constant for the deactivate dialog.
	 */
	public static final int DEACTIVATE = 1;

	/**
	 * Texts for the different titles of the dialogs.
	 */
	private static final String[] dialogTitles = { "ActivateEffect.Title", "DeactivateEffect.Title" };

	/**
	 * Texts for the different descriptions of the dialogs.
	 */
	private static final String[] dialogDescriptions = { "ActivateEffect.Description", "DeactivateEffect.Description" };

	/**
	 * Combo box with the flags.
	 */
	private JComboBox flagsComboBox;

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            Type of the dialog (FlagEffectDialog.ACTIVATE or FlagEffectDialog.DEACTIVATE)
	 * @param currentProperties
	 *            Set of initial values
	 */
	public FlagEffectDialog( int type, HashMap<Integer, Object> currentProperties ) {

		// Call the super method
		super( TextConstants.getText( dialogTitles[type] ), false );

		// Take the array of flags
		String[] flagsArray = controller.getVarFlagSummary( ).getFlags( );

		// If it is not empty
		//if( flagsArray.length > 0 ) {
			// Create the main panel
			JPanel mainPanel = new JPanel( );
			mainPanel.setLayout( new GridBagLayout( ) );
			GridBagConstraints c = new GridBagConstraints( );

			// Set the border of the panel with the description
			mainPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( dialogDescriptions[type] ) ) ) );

			// Create and add the list of flags
			c.insets = new Insets( 2, 4, 4, 4 );
			c.weightx = 1;
			flagsComboBox = new JComboBox( flagsArray );
			flagsComboBox.setEditable( true );
			mainPanel.add( flagsComboBox, c );

			// Add the panel to the center
			add( mainPanel, BorderLayout.CENTER );

			// Set the defualt values (if present)
			if( currentProperties != null ) {
				if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_TARGET ) )
					flagsComboBox.setSelectedItem( currentProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
			}

			// Set the dialog
			setResizable( false );
			pack( );
			Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
			setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
			setVisible( true );
		//}

		// If the list had no elements, show an error message
		//else
		//	controller.showErrorDialog( getTitle( ), TextConstants.getText( "ActivateDeactivateEffect.ErrorNoFlags" ) );
	}

	@Override
	protected void pressedOKButton( ) {
		// Create a set of properties, and put the selected value
		properties = new HashMap<Integer, Object>( );
		
		VarFlagsController varFlagsController = new VarFlagsController(Controller.getInstance().getVarFlagSummary( ));
		String flag = null;
		if (flagsComboBox.getSelectedItem( )!=null)
			flag = flagsComboBox.getSelectedItem( ).toString( );
		
		if (varFlagsController.existsFlag(flag)){
			properties.put( EffectsController.EFFECT_PROPERTY_TARGET, flag );			
		}
		else{
			String flagAdded = varFlagsController.addShortCutFlagVar( true, flag );
			if (flagAdded!=null){
				properties.put( EffectsController.EFFECT_PROPERTY_TARGET, flagAdded );
			} else
				properties = null;
		}
		
			
		
	}

}
