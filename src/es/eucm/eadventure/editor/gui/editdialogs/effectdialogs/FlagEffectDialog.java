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

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.control.controllers.FlagsController;
import es.eucm.eadventure.editor.gui.TextConstants;

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
	private static final String[] dialogTitles = { "FunctionalActivateEffect.Title", "FunctionalDeactivateEffect.Title" };

	/**
	 * Texts for the different descriptions of the dialogs.
	 */
	private static final String[] dialogDescriptions = { "FunctionalActivateEffect.Description", "FunctionalDeactivateEffect.Description" };

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
	public FlagEffectDialog( int type, HashMap<Integer, String> currentProperties ) {

		// Call the super method
		super( TextConstants.getText( dialogTitles[type] ) );

		// Take the array of flags
		String[] flagsArray = controller.getFlagSummary( ).getFlags( );

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
		properties = new HashMap<Integer, String>( );
		
		FlagsController flagsController = new FlagsController(Controller.getInstance().getFlagSummary( ));
		String flag = null;
		if (flagsComboBox.getSelectedItem( )!=null)
			flag = flagsComboBox.getSelectedItem( ).toString( );
		
		if (flagsController.existsFlag(flag)){
			properties.put( EffectsController.EFFECT_PROPERTY_TARGET, flag );			
		}
		else{
			String flagAdded = flagsController.addShortCutFlag( flag );
			if (flagAdded!=null){
				properties.put( EffectsController.EFFECT_PROPERTY_TARGET, flagAdded );
			} else
				properties = null;
			
/*			if (flag.contains( " " )){
				controller.showErrorDialog( TextConstants.getText( "Flags.AddFlag" ), TextConstants.getText( "Flags.ErrorFlagWhitespaces" ) );
				properties=null;
			}
			
			else {
			String[] similarFlags = flagsController.getSimilarFlags( flag );
			if (similarFlags.length==0){
				//Controller.getInstance( ).getFlagSummary( ).addFlag( flag );
				Controller.getInstance( ).getFlagSummary( ).addReference( flag );
				properties.put( EffectsController.EFFECT_PROPERTY_TARGET, flag );
			}else {
				String[] options = new String[similarFlags.length+1];
				options[0]= flag+" (NEW)";
				for (int i=1; i<options.length; i++){
					options[i]=similarFlags[i-1];
				}
				
				//TODO TextConstants
				String option = Controller.getInstance( ).showInputDialog( "Confirm new flag", "You are about to create a new flag that is similar to others.\nIs this correct?\nPlease confirm you want to create that flag or select an existing one.", options );
				if (option != null){
					
					// If it contain white spaces, show an error
					if (option.equals( flag+" (NEW)" )){
						properties.put( EffectsController.EFFECT_PROPERTY_TARGET, flag );
					}else {
						properties.put( EffectsController.EFFECT_PROPERTY_TARGET, option );
					}
											
				} else {
					properties = null;
				}
			}}*/
		}
		
			
		
	}

}
