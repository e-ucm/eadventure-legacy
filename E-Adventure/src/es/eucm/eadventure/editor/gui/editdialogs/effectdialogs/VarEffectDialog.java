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
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import es.eucm.eadventure.common.data.chapter.conditions.VarCondition;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.control.controllers.VarFlagsController;

public class VarEffectDialog extends EffectDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constant for the set-value dialog.
	 */
	public static final int SET_VALUE = 0;

	/**
	 * Constant for the increment-var dialog.
	 */
	public static final int INCREMENT_VAR = 1;
	
	/**
	 * Constant for the decrement-var dialog.
	 */
	public static final int DECREMENT_VAR = 2;

	/**
	 * Texts for the different titles of the dialogs.
	 */
	private static final String[] dialogTitles = { "SetValueEffect.Title", "IncrementVarEffect.Title",  "DecrementVarEffect.Title"};

	/**
	 * Texts for the different descriptions of the dialogs.
	 */
	private static final String[] dialogDescriptions = { "SetValueEffect.Description", "IncrementVarEffect.Description",  "DecrementVarEffect.Description" };

	/**
	 * Combo box with the vars.
	 */
	private JComboBox varsComboBox;
	
	/**
	 * Spinner for values/increments/decrements
	 */
	private JSpinner valuesSpinner;

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            Type of the dialog (VarEffectDialog.SET_VALUE or VarEffectDialog.INCREMENT_VAR or VarEffectDialog.DECREMENT_VAR)
	 * @param currentProperties
	 *            Set of initial values
	 */
	public VarEffectDialog( int type, HashMap<Integer, Object> currentProperties ) {

		// Call the super method
		super( TextConstants.getText( dialogTitles[type] ), false );

		// Take the array of flags
		String[] varsArray = controller.getVarFlagSummary( ).getVars( );

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
			c.weightx = 0.5;
			varsComboBox = new JComboBox( varsArray );
			varsComboBox.setEditable( true );
			// Set the defualt values (if present)
			int defaultValue = 1;
			if( currentProperties != null ) {
				String defaultValueString = (String)currentProperties.get( EffectsController.EFFECT_PROPERTY_VALUE );
				if (defaultValueString!=null)
					defaultValue = Integer.parseInt(defaultValueString);
					if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_TARGET ) )
						varsComboBox.setSelectedItem( currentProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
			}

			
			// Create the spinner for the value/increment
			valuesSpinner = new JSpinner ( new SpinnerNumberModel(defaultValue, VarCondition.MIN_VALUE, VarCondition.MAX_VALUE, 1 ) );
			mainPanel.add( varsComboBox, c );
			c.gridy++;
			mainPanel.add( valuesSpinner, c );
			
			// Add the panel to the center
			add( mainPanel, BorderLayout.CENTER );

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
		String var = null;
		String value = null;
		if (varsComboBox.getSelectedItem( )!=null){
			var = varsComboBox.getSelectedItem( ).toString( );
			value = valuesSpinner.getValue().toString();
		}
		
		if (varFlagsController.existsVar(var)){
			properties.put( EffectsController.EFFECT_PROPERTY_TARGET, var );
			properties.put( EffectsController.EFFECT_PROPERTY_VALUE, value );
		}
		else{
			String varAdded = varFlagsController.addShortCutFlagVar( false, var );
			if (varAdded!=null){
				properties.put( EffectsController.EFFECT_PROPERTY_TARGET, varAdded );
				properties.put( EffectsController.EFFECT_PROPERTY_VALUE, value );
			} else
				properties = null;
		}
		
			
		
	}

}
