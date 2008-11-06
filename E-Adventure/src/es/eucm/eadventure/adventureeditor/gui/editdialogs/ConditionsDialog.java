package es.eucm.eadventure.adventureeditor.gui.editdialogs;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;

import es.eucm.eadventure.adventureeditor.control.Controller;
import es.eucm.eadventure.adventureeditor.control.controllers.ConditionsController;
import es.eucm.eadventure.adventureeditor.gui.TextConstants;
import es.eucm.eadventure.adventureeditor.gui.elementpanels.general.ConditionsPanel;

/**
 * This class is the editing dialog for the conditions. Here the user can add conditions to the events of the script,
 * using the flags defined in the Flags dialog.
 * 
 * @author Bruno Torijano Bueno
 */
public class ConditionsDialog extends JDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param conditionsController
	 *            Controller for the conditions
	 */
	public ConditionsDialog( ConditionsController conditionsController ) {

		// Call to the JDialog constructor
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "Conditions.Title" ), Dialog.ModalityType.APPLICATION_MODAL );

		// Push the dialog into the stack, and add the window listener to pop in when closing
		Controller.getInstance( ).pushWindow( this );
		addWindowListener( new WindowAdapter( ) {
			public void windowClosing( WindowEvent e ) {
				Controller.getInstance( ).popWindow( );
			}
		} );

		// Create the main panel and add it
		setLayout( new GridBagLayout( ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		add( new ConditionsPanel( conditionsController ), c );

		// Set the size, position and properties of the dialog
		setResizable( false );
		setSize( 600, 400 );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setVisible( true );
	}
}
