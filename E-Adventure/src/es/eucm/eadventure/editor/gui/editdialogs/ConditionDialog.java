package es.eucm.eadventure.editor.gui.editdialogs;

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

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;

import es.eucm.eadventure.common.data.chapter.conditions.Condition;
import es.eucm.eadventure.common.data.chapter.conditions.VarCondition;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.controllers.VarFlagsController;
import es.eucm.eadventure.editor.data.support.IdentifierSummary;

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
	private JComboBox idsComboBox;
	
	/**
	 * Spinner for values (vars)
	 */
	private JSpinner valueSpinner;
	
	/**
	 * Options panel: flag/var/state(group of conditions)
	 */
	private JPanel optionsPanel;

	/**
	 * This panel is updated with diverse features according to the selected one
	 */
	private JPanel featuresPanel;
	
	/**
	 * Selected id 
	 */
	private String selectedId;
	
	/**
	 * Current mode selected for the condition.
	 * Valid values: #Condition.VAR_CONDITION, #Condition.FLAG_CONDITION, #Condition.GROUP_CONDITION
	 */
	private int selectedMode;
	
	/**
	 * Default flag
	 */
	private String defaultFlag;
	
	/**
	 * Default id
	 */
	private String defaultId;
	
	/**
	 * Default var
	 */
	private String defaultVar;
	
	/**
	 * Default state (vars)
	 */
	private String defaultState;
	
	/**
	 * Default value (vars)
	 */
	private int defaultValue;


	/**
	 * Constructor with no default selection data.
	 * 
	 * @param title
	 *            Title of the dialog
	 */
	public ConditionDialog( String title ) {
		this( new Integer ( Condition.FLAG_CONDITION), title, null, null, null, null, null );
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
	public ConditionDialog( Integer defaultMode, String title, String defaultState, String defaultFlag, String defaultVar, String defaultId, String defaultValue ) {
		super( Controller.getInstance( ).peekWindow( ), title, Dialog.ModalityType.APPLICATION_MODAL );

		this.defaultFlag = defaultFlag;
		this.defaultVar = defaultVar;
		this.defaultId = defaultId;
		this.defaultState = defaultState;
		if (defaultValue!=null)
			this.defaultValue = Integer.parseInt( defaultValue );
		else
			this.defaultValue = 1;
		
		// If it is not empty
		//if( flagsArray.length > 0 ) {

			pressedOKButton = false;

			JPanel mainPanel = new JPanel( );
			mainPanel.setLayout( new GridBagLayout( ) );
			GridBagConstraints c = new GridBagConstraints( );
			c.insets = new Insets( 14, 14, 14, 4 );
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridwidth = 3;
			c.weightx = 1;
			mainPanel.add( new JLabel( TextConstants.getText( "Conditions.EditConditionMessage" ) ), c );
			
			c.gridy = 1;
			optionsPanel = createOptionsPanel();
			mainPanel.add( optionsPanel, c );

			featuresPanel = new JPanel();
			c.gridy = 2;
			mainPanel.add( featuresPanel, c );
			
			add( mainPanel, BorderLayout.CENTER );

			JPanel buttonsPanel = new JPanel( );
			buttonsPanel.setLayout( new FlowLayout( FlowLayout.RIGHT, 4, 4 ) );

			JButton okButton = new JButton( TextConstants.getText( "GeneralText.OK" ) );
			okButton.addActionListener( new OKButtonListener( ) );
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
			this.selectedMode = defaultMode.intValue();
			updateDialog( );
			Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
			setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
			setVisible( true );
		//}

		// If the list had no elements, show an error message
		//else
			//Controller.getInstance( ).showErrorDialog( getTitle( ), TextConstants.getText( "Conditions.ErrorNoFlags" ) );
	}
	
	private JPanel createOptionsPanel ( ){
		JPanel panel = new JPanel();
		panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				TextConstants.getText("Conditions.Type")));
		ButtonGroup group = new ButtonGroup( );
		JToggleButton button1 =  new JToggleButton ( TextConstants.getText( "Conditions.Flags" ), new ImageIcon ( "img/flags.png" ));
		button1.setToolTipText( TextConstants.getText( "Conditions.Flag.Description" ) );
		JToggleButton button2 =  new JToggleButton ( TextConstants.getText( "Conditions.Var" ), new ImageIcon ( "img/vars.png" ));
		button2.setToolTipText( TextConstants.getText( "Conditions.Var.Description" ) );
		JToggleButton button3 =  new JToggleButton ( TextConstants.getText( "Conditions.ConditionGroup" ), new ImageIcon ( "img/group.png" ));
		button3.setToolTipText( TextConstants.getText( "Conditions.Group.Description" ) );
		button1.addActionListener( new ConditionModeButtonListener( Condition.FLAG_CONDITION ) );
		button2.addActionListener( new ConditionModeButtonListener( Condition.VAR_CONDITION ) );
		button3.addActionListener( new ConditionModeButtonListener( Condition.GLOBAL_STATE_CONDITION ) );
		panel.add( button1 );
		panel.add( button2 );
		panel.add( button3 );
		group.add( button1 );
		group.add( button2 );
		group.add( button3 );

		return panel;
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
	public String getSelectedState( ) {
		if ( stateComboBox!=null && stateComboBox.getSelectedItem()!= null)
			return stateComboBox.getSelectedItem( ).toString( );
		else 
			return null;
	}
	
	/**
	 * Returns the value (vars).
	 * 
	 * @return The value
	 */
	public String getSelectedValue( ) {
		if ( valueSpinner!=null && valueSpinner.getValue()!= null)
			return valueSpinner.getValue().toString();
		else
			return null;
	}

	/**
	 * Returns the flag value.
	 * 
	 * @return The flag value
	 */
	public String getSelectedId( ) {
		/*if (flagsComboBox.getSelectedItem( )!=null)
			return flagsComboBox.getSelectedItem( ).toString( );
		else return null;*/
		return selectedId;
	}
	
	public int getSelectedType( ) {
		return selectedMode;
	}
	
	private void updateDialog (  ){
		
		this.featuresPanel.removeAll();
		featuresPanel.updateUI();
		
		if ( selectedMode == Condition.FLAG_CONDITION ){
			featuresPanel.setLayout( new GridBagLayout() );
			String[] flagsArray = Controller.getInstance( ).getVarFlagSummary( ).getFlags( );
			
			GridBagConstraints c = new GridBagConstraints( );
			c.insets = new Insets( 4, 4, 2, 4 );
			c.gridy = 0;
			c.gridwidth = 1;
			c.weightx = 0.8;
			featuresPanel.add( new JLabel( TextConstants.getText( "Conditions.Flag.Id" ) ), c );

			c.gridx = 1;
			c.weightx = 0.2;
			featuresPanel.add( new JLabel( TextConstants.getText( "Conditions.Flag.State"  ) ), c );
			
			c.insets = new Insets( 2, 4, 4, 4 );
			c.gridx = 0;
			c.gridy = 1;
			c.weightx = 0.8;
			idsComboBox = new JComboBox( flagsArray );
			idsComboBox.setEditable( true );
			if( defaultFlag != null )
				idsComboBox.setSelectedItem( defaultFlag );
			featuresPanel.add( idsComboBox, c );

			c.gridx = 1;
			c.weightx = 0.2;
			stateComboBox = new JComboBox( ConditionsController.STATE_VALUES_FLAGS );
			if ( defaultState!=null ){
				stateComboBox.setSelectedItem( defaultState );
			}
			featuresPanel.add( stateComboBox, c );
			
			featuresPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), TextConstants.getText("Conditions.Flag.Title")));

		}
		
		else if ( selectedMode == Condition.VAR_CONDITION ){
			featuresPanel.setLayout( new GridBagLayout() );
			String[] varsArray = Controller.getInstance( ).getVarFlagSummary( ).getVars( );
			
			GridBagConstraints c = new GridBagConstraints( );
			c.insets = new Insets( 4, 4, 2, 4 );
			c.gridy = 0;
			c.gridwidth = 1;
			c.weightx = 0.6;
			featuresPanel.add( new JLabel( TextConstants.getText( "Conditions.Var.Id" ) ), c );

			c.gridx = 1;
			c.weightx = 0.2;
			featuresPanel.add( new JLabel( TextConstants.getText( "Conditions.Var.State"  ) ), c );
			
			c.gridx = 2;
			c.weightx = 0.2;
			featuresPanel.add( new JLabel( TextConstants.getText( "Conditions.Var.Value"  ) ), c );
			
			c.insets = new Insets( 2, 4, 4, 4 );
			c.gridx = 0;
			c.gridy = 1;
			c.weightx = 0.6;
			idsComboBox = new JComboBox( varsArray );
			idsComboBox.setEditable( true );
			if( defaultVar != null )
				idsComboBox.setSelectedItem( defaultVar );
			featuresPanel.add( idsComboBox, c );

			c.gridx = 1;
			c.weightx = 0.2;
			stateComboBox = new JComboBox( ConditionsController.STATE_VALUES_VARS );
			if ( defaultState!=null ){
				stateComboBox.setSelectedItem( defaultState );
			}
			featuresPanel.add( stateComboBox, c );
			
			c.gridx = 2;
			c.weightx = 0.2;
			valueSpinner = new JSpinner( new SpinnerNumberModel( defaultValue, VarCondition.MIN_VALUE, VarCondition.MAX_VALUE, 1 ) );
			featuresPanel.add( valueSpinner, c );
			
			featuresPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), TextConstants.getText("Conditions.Var.Title")));
			
		} 
		
		else if ( selectedMode == Condition.GLOBAL_STATE_CONDITION ){
			featuresPanel.setLayout( new GridBagLayout() );
			String[] globalStatesArray = Controller.getInstance( ).getIdentifierSummary().getGlobalStatesIds();
			
			GridBagConstraints c = new GridBagConstraints( );
			c.insets = new Insets( 4, 4, 2, 4 );
			c.gridy = 0;
			c.gridwidth = 1;
			c.weightx = 1;
			featuresPanel.add( new JLabel( TextConstants.getText( "Conditions.Group.Id" ) ), c );

			c.gridx = 0;
			c.gridy = 1;
			c.weightx = 1;
			idsComboBox = new JComboBox( globalStatesArray );
			idsComboBox.setEditable( false );
			if( defaultId != null )
				idsComboBox.setSelectedItem( defaultId );
			featuresPanel.add( idsComboBox, c );

			featuresPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), TextConstants.getText("Conditions.Group.Title")));
		}
		featuresPanel.doLayout();
		pack();

	}
	
	/**
	 * Listener for mode buttons
	 * @author Javier
	 *
	 */
	private class ConditionModeButtonListener implements ActionListener {

		/**
		 * Mode represented by the button linked to this listener
		 */
		private int mode;
		
		public ConditionModeButtonListener ( int mode ){
			this.mode = mode;
		}
		
		public void actionPerformed(ActionEvent e) {
			// Change current mode
			selectedMode = mode;
			updateDialog ( );
		}
		
	}
	
	private class OKButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (idsComboBox !=null ){
				VarFlagsController varFlagsController = new VarFlagsController(Controller.getInstance().getVarFlagSummary( ));
				IdentifierSummary idSummary = Controller.getInstance().getIdentifierSummary();
				String id = null;
				if (idsComboBox.getSelectedItem( )!=null)
					id = idsComboBox.getSelectedItem( ).toString( );
				
				if (varFlagsController.existsId( id ) || idSummary.isGlobalStateId(id)){
					idsComboBox.setSelectedItem( id  );
					selectedId = id;
				} 
				else if (id!=null){
					String idAdded = varFlagsController.addShortCutFlagVar( selectedMode == Condition.FLAG_CONDITION, id );
					
					if ( selectedMode == Condition.FLAG_CONDITION)
						idsComboBox.setModel( new DefaultComboBoxModel(Controller.getInstance( ).getVarFlagSummary( ).getFlags( )) );
					else if ( selectedMode == Condition.VAR_CONDITION)
						idsComboBox.setModel( new DefaultComboBoxModel(Controller.getInstance( ).getVarFlagSummary( ).getVars( )) );
					
					if (idAdded!=null){
						idsComboBox.setSelectedItem( idAdded );
						selectedId = idAdded;
					} 
					idsComboBox.updateUI( );
					//updateDialog ( );
				} else if (id == null){
					selectedId = null;
				}
				
			} else {
				selectedId = null;
			}
			pressedOKButton = true;
			setVisible( false );
			
		}

		
	}
}
