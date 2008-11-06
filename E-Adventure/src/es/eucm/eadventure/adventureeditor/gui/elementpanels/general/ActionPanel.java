package es.eucm.eadventure.adventureeditor.gui.elementpanels.general;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.adventureeditor.control.controllers.general.ActionDataControl;
import es.eucm.eadventure.adventureeditor.gui.TextConstants;
import es.eucm.eadventure.adventureeditor.gui.editdialogs.ConditionsDialog;

public class ActionPanel extends JPanel {

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
	 * Constructor.
	 * 
	 * @param actionDataControl
	 *            Action controller
	 */
	public ActionPanel( ActionDataControl actionDataControl ) {

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
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationTextAreaChangesListener( ) );
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		// TODO Revisar problemas con el layout sin esta linea
		documentationPanel.setMinimumSize( new Dimension( 0, 108 ) );
		documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Action.Documentation" ) ) );
		add( documentationPanel, c );

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
	 * Listener for the text area. It checks the value of the area and updates the documentation.
	 */
	private class DocumentationTextAreaChangesListener implements DocumentListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
		 */
		public void changedUpdate( DocumentEvent arg0 ) {
		// Do nothing
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
		 */
		public void insertUpdate( DocumentEvent arg0 ) {
			// Set the new content
			actionDataControl.setDocumentation( documentationTextArea.getText( ) );
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
		 */
		public void removeUpdate( DocumentEvent arg0 ) {
			// Set the new content
			actionDataControl.setDocumentation( documentationTextArea.getText( ) );
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
}
