package es.eucm.eadventure.editor.gui.elementpanels.assessment;

import java.awt.BorderLayout;
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
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentRuleDataControl;
import es.eucm.eadventure.editor.gui.TextConstants;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.editor.gui.editdialogs.ConditionsDialog;

public class AssessmentRulePanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Link to the element reference controller.
	 */
	private AssessmentRuleDataControl assessmentRuleDataControl;

	/**
	 * Combo box for the items in the script.
	 */
	private JComboBox importanceComboBox;

	/**
	 * Text area for the documentation.
	 */
	private JTextArea conceptTextArea;

	private JTextArea textTextArea;

	
	
	/**
	 * Constructor.
	 * 
	 * @param assRuleDataControl
	 *            Controller of the element reference
	 */
	public AssessmentRulePanel( AssessmentRuleDataControl assRuleDataControl ) {

		// Set the controller
		Controller controller = Controller.getInstance( );
		this.assessmentRuleDataControl = assRuleDataControl;

		// Set the layout
		setLayout( new GridBagLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentRule.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Info panel
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		JTextPane informationTextPane = new JTextPane( );
		informationTextPane.setEditable( false );
		informationTextPane.setBackground( getBackground( ) );
		informationTextPane.setText( TextConstants.getText( "AssessmentRule.Information" ) );
		JPanel informationPanel = new JPanel( );
		informationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "GeneralText.Information" ) ) );
		informationPanel.setLayout( new BorderLayout( ) );
		informationPanel.add( informationTextPane, BorderLayout.CENTER );
		add( informationPanel, c );
		
		
		// Create the combo box of importance
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridy=1;
		JPanel importancePanel = new JPanel( );
		importancePanel.setLayout( new GridLayout( ) );
		
		importanceComboBox = new JComboBox( new String[]{TextConstants.getText( "AssessmentRule.Importance.VeryLow" ), TextConstants.getText( "AssessmentRule.Importance.Low" ), TextConstants.getText( "AssessmentRule.Importance.Normal" ), TextConstants.getText( "AssessmentRule.Importance.High" ), TextConstants.getText( "AssessmentRule.Importance.VeryHigh" )} );
		importanceComboBox.setSelectedIndex( assessmentRuleDataControl.getImportance( ) );
		importanceComboBox.addActionListener( new ItemComboBoxListener( ) );
		importancePanel.add( importanceComboBox );
		importancePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentRule.Importance.Title" ) ) );
		add( importancePanel, c );

		// Create the text area for the concept
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.2;

		JPanel conceptPanel = new JPanel( );
		conceptPanel.setLayout( new GridLayout( ) );
		conceptTextArea = new JTextArea( assRuleDataControl.getConcept( ), 4, 0 );
		conceptTextArea.setLineWrap( true );
		conceptTextArea.setWrapStyleWord( true );
		conceptTextArea.getDocument( ).addDocumentListener( new DocumentationTextAreaChangesListener( ) );
		conceptPanel.add( new JScrollPane( conceptTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		conceptPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentRule.Concept" ) ) );
		add( conceptPanel, c );

		// Create the button for the conditions
		c.gridy = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		JPanel conditionsPanel = new JPanel( );
		conditionsPanel.setLayout( new GridLayout( ) );
		JButton conditionsButton = new JButton( TextConstants.getText( "GeneralText.EditConditions" ) );
		conditionsButton.addActionListener( new ConditionsButtonListener( ) );
		conditionsPanel.add( conditionsButton );
		conditionsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentRule.Conditions" ) ) );
		add( conditionsPanel, c );

		// Create the effect panel
		JPanel effectPanel = new JPanel();
		effectPanel.setLayout( new GridBagLayout( ) );
		effectPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentRule.Effect.Title" ) ) );
		GridBagConstraints c2 = new GridBagConstraints( );
		
		c2.gridx=0;c2.gridy=0;
		c2.insets = new Insets( 5, 5, 5, 5 );
		c2.fill=GridBagConstraints.BOTH;
		c2.weightx=1; c2.weighty=0.2;
		JPanel textPanel = new JPanel( );
		textPanel.setLayout( new GridLayout( ) );
		textTextArea = new JTextArea( assRuleDataControl.getEffectText( ), 4, 0 );
		textTextArea.setLineWrap( true );
		textTextArea.setWrapStyleWord( true );
		textTextArea.getDocument( ).addDocumentListener( new DocumentationTextAreaChangesListener( ) );
		textPanel.add( new JScrollPane( textTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		textPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentRule.Effect.Text" ) , TitledBorder.CENTER, TitledBorder.TOP ) );
		
		effectPanel.add( textPanel, c2 );
		//effectPanel.add( new JFiller() );

		// Create and add the set-property table
		c2.weighty=0.8; c2.fill=GridBagConstraints.BOTH; c2.gridy=1;
		AssessmentPropertiesPanel propPanel = new AssessmentPropertiesPanel( this.assessmentRuleDataControl ); 
		effectPanel.add( propPanel, c2 );
		
		c.gridy = 4;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1;
		add( effectPanel, c );

		// Add the other elements of the scene if a background image was loaded
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
			if (arg0.getDocument( ) == conceptTextArea.getDocument( )){
				assessmentRuleDataControl.setConcept( conceptTextArea.getText( ) );	
			}
			
			else if (arg0.getDocument( ) == textTextArea.getDocument( )){
				assessmentRuleDataControl.setEffectText( textTextArea.getText( ) );
			}

			
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
		 */
		public void removeUpdate( DocumentEvent arg0 ) {
			// Set the new content
			
			if (arg0.getDocument( ) == conceptTextArea.getDocument( )){
				assessmentRuleDataControl.setConcept( conceptTextArea.getText( ) );
			}
			
			else if (arg0.getDocument( ) == textTextArea.getDocument( )){
				assessmentRuleDataControl.setEffectText( textTextArea.getText( ) );
			}


		}
	}

	/**
	 * Listener for the items combo box.
	 */
	private class ItemComboBoxListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			assessmentRuleDataControl.setImportance( importanceComboBox.getSelectedIndex( ) );

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
			new ConditionsDialog( assessmentRuleDataControl.getConditions( ) );
		}
	}
}
