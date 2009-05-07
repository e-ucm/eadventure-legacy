package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.gui.elementpanels.condition.ConditionsPanel;

/**
 * This class is the editing dialog for the conditions. Here the user can add conditions to the events of the script,
 * using the flags defined in the Flags dialog.
 * 
 * @author Javier Torrente
 */
public class ConditionsDialog extends ToolManagableDialog{

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;
	
	private ConditionsPanel conditionsPanel;


	/**
	 * Constructor.
	 * 
	 * @param conditionsController
	 *            Controller for the conditions
	 */
	public ConditionsDialog( ConditionsController conditionsController ) {

		// Call to the JDialog constructor
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "Conditions.Title" ), false );

		// Create the main panel and add it
		setLayout( new GridBagLayout( ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		conditionsPanel = new ConditionsPanel( conditionsController);
		add( conditionsPanel, c );

		// Set the size, position and properties of the dialog
		//setResizable( false );
		setSize( 600, 400 );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setVisible( true );
	}
	
	//@Override
	public boolean updateFields() {
		//this.removeAll();
		return conditionsPanel.updateFields();
	}
	
}
