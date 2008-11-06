package es.eucm.eadventure.adventureeditor.gui.editdialogs;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.eucm.eadventure.adventureeditor.control.Controller;
import es.eucm.eadventure.adventureeditor.control.controllers.ConditionsController;
import es.eucm.eadventure.adventureeditor.control.controllers.EffectsController;
import es.eucm.eadventure.adventureeditor.control.controllers.FlagsController;
import es.eucm.eadventure.adventureeditor.gui.TextConstants;

/**
 * This class is the dialog that allows adding and editing the single conditions.
 * 
 * @author Bruno Torijano Bueno
 */
public class ConditionDialog extends JDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Tells if the OK button was pressed
	 */
	private boolean pressedOKButton;

	/**
	 * Combo box for the condition states.
	 */
	private JComboBox stateComboBox;

	/**
	 * Combo box for the flags.
	 */
	private JComboBox flagsComboBox;
	
	private String selectedFlag;

	/**
	 * Constructor with no default selection data.
	 * 
	 * @param title
	 *            Title of the dialog
	 */
	public ConditionDialog( String title ) {
		this( title, null, null );
	}

	/**
	 * Constructor with default selection data.
	 * 
	 * @param title
	 *            Title of the dialog
	 * @param defaultState
	 *            The default state value, null if none
	 * @param defaultFlag
	 *            The default flag value, null if none
	 */
	public ConditionDialog( String title, String defaultState, String defaultFlag ) {
		super( Controller.getInstance( ).peekWindow( ), title, Dialog.ModalityType.APPLICATION_MODAL );

		// Take the array of flags
		String[] flagsArray = Controller.getInstance( ).getFlagSummary( ).getFlags( );

		// If it is not empty
		//if( flagsArray.length > 0 ) {

			pressedOKButton = false;

			JPanel mainPanel = new JPanel( );
			mainPanel.setLayout( new GridBagLayout( ) );
			GridBagConstraints c = new GridBagConstraints( );
			c.insets = new Insets( 14, 14, 14, 4 );
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridwidth = 2;
			c.weightx = 1;
			mainPanel.add( new JLabel( TextConstants.getText( "Conditions.EditConditionMessage" ) ), c );

			c.insets = new Insets( 4, 4, 2, 4 );
			c.gridy = 1;
			c.gridwidth = 1;
			c.weightx = 0.2;
			mainPanel.add( new JLabel( TextConstants.getText( "Conditions.State" ) ), c );

			c.gridx = 1;
			c.weightx = 0.8;
			mainPanel.add( new JLabel( TextConstants.getText( "Conditions.Flag" ) ), c );

			c.insets = new Insets( 2, 4, 4, 4 );
			c.gridx = 0;
			c.gridy = 2;
			c.weightx = 0.2;
			stateComboBox = new JComboBox( ConditionsController.STATE_VALUES );
			if( defaultState != null )
				stateComboBox.setSelectedItem( defaultState );
			mainPanel.add( stateComboBox, c );

			c.gridx = 1;
			c.weightx = 0.8;
			flagsComboBox = new JComboBox( flagsArray );
			flagsComboBox.setEditable( true );
			if( defaultFlag != null )
				flagsComboBox.setSelectedItem( defaultFlag );
			mainPanel.add( flagsComboBox, c );

			add( mainPanel, BorderLayout.CENTER );

			JPanel buttonsPanel = new JPanel( );
			buttonsPanel.setLayout( new FlowLayout( FlowLayout.RIGHT, 4, 4 ) );

			JButton okButton = new JButton( TextConstants.getText( "GeneralText.OK" ) );
			okButton.addActionListener( new ActionListener( ) {
				public void actionPerformed( ActionEvent e ) {
					
					FlagsController flagsController = new FlagsController(Controller.getInstance().getFlagSummary( ));
					String flag = null;
					if (flagsComboBox.getSelectedItem( )!=null)
						flag = flagsComboBox.getSelectedItem( ).toString( );
					
					if (flagsController.existsFlag(flag)){
						flagsComboBox.setSelectedItem( flag );
						selectedFlag = flag;
					}
					else if (flag!=null){
						String flagAdded = flagsController.addShortCutFlag( flag );
						flagsComboBox.setModel( new DefaultComboBoxModel(Controller.getInstance( ).getFlagSummary( ).getFlags( )) );
						if (flagAdded!=null){
							flagsComboBox.setSelectedItem( flagAdded );
							selectedFlag = flagAdded;
						} 
						flagsComboBox.updateUI( );
					} else if (flag == null){
						selectedFlag = null;
					}
					
					pressedOKButton = true;
					setVisible( false );
				}
			} );
			buttonsPanel.add( okButton );

			JButton cancelButton = new JButton( TextConstants.getText( "GeneralText.Cancel" ) );
			cancelButton.addActionListener( new ActionListener( ) {
				public void actionPerformed( ActionEvent e ) {
					setVisible( false );
				}
			} );
			buttonsPanel.add( cancelButton );

			add( buttonsPanel, BorderLayout.SOUTH );

			setResizable( false );
			pack( );
			Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
			setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
			setVisible( true );
		//}

		// If the list had no elements, show an error message
		//else
			//Controller.getInstance( ).showErrorDialog( getTitle( ), TextConstants.getText( "Conditions.ErrorNoFlags" ) );
	}

	/**
	 * Returns if the OK button was pressed.
	 * 
	 * @return True if the OK button was pressed, false otherwise
	 */
	public boolean wasPressedOKButton( ) {
		return pressedOKButton;
	}

	/**
	 * Returns the state value.
	 * 
	 * @return The state value
	 */
	public String getState( ) {
		return stateComboBox.getSelectedItem( ).toString( );
	}

	/**
	 * Returns the flag value.
	 * 
	 * @return The flag value
	 */
	public String getFlag( ) {
		/*if (flagsComboBox.getSelectedItem( )!=null)
			return flagsComboBox.getSelectedItem( ).toString( );
		else return null;*/
		return selectedFlag;
	}
}
