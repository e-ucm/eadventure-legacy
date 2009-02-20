package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.gui.editdialogs.effectdialogs.MacroReferenceEffectDialog;
import es.eucm.eadventure.editor.gui.elementpanels.general.EffectsPanel;

/**
 * This class is the editing dialog for the effects. Here the user can add effects to the events of the script.
 * 
 * @author Bruno Torijano Bueno
 */
public class EffectsDialog extends ToolManagableDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;
	
	private EffectsPanel effectsPanel;

	/**
	 * Constructor.
	 * 
	 * @param effectsController
	 *            Controller for the conditions
	 */
	public EffectsDialog( EffectsController effectsController ) {

		// Call to the JDialog constructor
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "Effects.Title" ), false );//, Dialog.ModalityType.APPLICATION_MODAL );

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
		MacroReferenceEffectDialog.ID = null;
		effectsPanel = new EffectsPanel( effectsController );
		add(effectsPanel, c );

		// Set the size, position and properties of the dialog
		setResizable( false );
		setSize( 600, 400 );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setVisible( true );
	}
	
	public boolean updateFields(){
		return effectsPanel.updateFields();
	}
}
