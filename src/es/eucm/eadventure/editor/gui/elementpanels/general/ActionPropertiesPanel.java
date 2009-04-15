package es.eucm.eadventure.editor.gui.elementpanels.general;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.general.ActionDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DocumentationChangeListener;
import es.eucm.eadventure.editor.gui.editdialogs.ConditionsDialog;
import es.eucm.eadventure.editor.gui.editdialogs.effectdialogs.MacroReferenceEffectDialog;

public class ActionPropertiesPanel extends JPanel implements ActionTypePanel{

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Controller of the action.
	 */
	private ActionDataControl actionDataControl;

	/**
	 * Text area for the documentation.
	 */
	private JTextArea documentationTextArea;

	/**
	 * Combo box for the elements (items or characters) in the script.
	 */
	private JComboBox elementsComboBox;

	/**
	 * The checkbox with the value of needsGoTo
	 */
	private JCheckBox needsGoToCheck;
	
	/**
	 * The spinner to set the value of keepDistance
	 */
	private JSpinner keepDistanceSpinner;

	/**
	 * Constructor.
	 * 
	 * @param actionDataControl
	 *            Action controller
	 */
	public ActionPropertiesPanel( ActionDataControl actionDataControl ) {

		// Set the controller
		this.actionDataControl = actionDataControl;

		// Set the layout
		setLayout( new GridBagLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Action.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;

		// Add the list of items for id target if the action accepts them
		if( actionDataControl.hasIdTarget( ) ) {
			JPanel destinyElementPanel = new JPanel( );
			destinyElementPanel.setLayout( new GridLayout( ) );
			elementsComboBox = new JComboBox( actionDataControl.getElementsList( ) );
			elementsComboBox.setSelectedItem( actionDataControl.getIdTarget( ) );
			elementsComboBox.addActionListener( new ElementComboBoxListener( ) );
			destinyElementPanel.add( elementsComboBox );
			destinyElementPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Action.IdTarget" ) ) );
			add( destinyElementPanel, c );
			c.gridy++;
		}
		// Create the text area for the documentation
		JPanel documentationPanel = new JPanel( );
		documentationPanel.setLayout( new GridLayout( ) );
		documentationTextArea = new JTextArea( actionDataControl.getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationChangeListener( documentationTextArea, (Documented) actionDataControl.getContent() ));
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		// TODO Revisar problemas con el layout sin esta linea
		documentationPanel.setMinimumSize( new Dimension( 0, 108 ) );
		documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Action.Documentation" ) ) );
		add( documentationPanel, c );

		c.gridy++;
		JPanel otherPanel = new JPanel();
		otherPanel.setLayout( new GridLayout(3,1));
		otherPanel.add(new JLabel(TextConstants.getText("CustomAction.OtherConfigurationDetails")));
		
		needsGoToCheck = new JCheckBox(TextConstants.getText("CustomAction.NeedsGoTo"));
		needsGoToCheck.setSelected(actionDataControl.getNeedsGoTo());
		needsGoToCheck.addChangeListener( new NeedsGoToCheckListener());
		otherPanel.add( needsGoToCheck);
		
	    JPanel temp = new JPanel();
		SpinnerModel sm = new SpinnerNumberModel(actionDataControl.getKeepDistance(), 0, 100, 5);
		keepDistanceSpinner = new JSpinner(sm);
		keepDistanceSpinner.setEnabled(actionDataControl.getNeedsGoTo());
		keepDistanceSpinner.addChangeListener(new KeepDistanceSpinnerListener());
		temp.setLayout(new BorderLayout());
		temp.add(new JLabel(TextConstants.getText("CustomAction.DistanceToObjective")), BorderLayout.CENTER);
		temp.add(keepDistanceSpinner, BorderLayout.WEST);
		otherPanel.add( temp);
		
		otherPanel.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), TextConstants.getText("CustomAction.OtherConfiguration")));
		add(otherPanel, c);

		// Create the button for the conditions
		c.gridy++;
		JPanel conditionsPanel = new JPanel( );
		conditionsPanel.setLayout( new GridLayout( ) );
		JButton conditionsButton = new JButton( TextConstants.getText( "GeneralText.EditConditions" ) );
		conditionsButton.addActionListener( new ConditionsButtonListener( ) );
		conditionsPanel.add( conditionsButton );
		conditionsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Action.Conditions" ) ) );
		add( conditionsPanel, c );

		// Create a effects panel and attach it
		c.gridy++;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		MacroReferenceEffectDialog.ID = null;
		add( new EffectsPanel( actionDataControl.getEffects( ) ), c );
		
		
	}

	/**
	 * Listener for the elements (items or characters) combo box.
	 */
	private class ElementComboBoxListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			actionDataControl.setIdTarget( elementsComboBox.getSelectedItem( ).toString( ) );
		}
	}


	/**
	 * Listener for the edit conditions button.
	 */
	private class ConditionsButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			new ConditionsDialog( actionDataControl.getConditions( ) );
		}
	}
	
	/**
	 * Listener for the changes in the needsGoTo checkbox
	 */
	private class NeedsGoToCheckListener implements ChangeListener {
		public void stateChanged(ChangeEvent arg0) {
			actionDataControl.setNeedsGoTo(needsGoToCheck.isSelected());
			keepDistanceSpinner.setEnabled(needsGoToCheck.isSelected());
		}
	}
	
	/**
	 * Listener for the changes in the keepDistances spinner
	 */
	private class KeepDistanceSpinnerListener implements ChangeListener {
		public void stateChanged(ChangeEvent arg0) {
			actionDataControl.setKeepDistance(((Integer) keepDistanceSpinner.getModel().getValue()).intValue());
		}
	}

	@Override
	public int getType() {
	    return ActionTypePanel.ACTION_TYPE;
	}


}
