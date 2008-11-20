package es.eucm.eadventure.editor.gui.elementpanels.assessment;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentRuleDataControl;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.editor.gui.editdialogs.ConditionsDialog;

public class TimedAssessmentRulePanel extends JPanel {

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

	private int currentEffect;
	
	private JButton addEffectBlock;
	
	private JButton deleteEffectBlock;
	
	/**
	 * Combo box for the items in the script.
	 */
	private JComboBox effectComboBox;
	
	private JSpinner minTime;
	
	private JSpinner maxTime;
	
	private AssessmentPropertiesPanel propPanel;
	
	/**
	 * Constructor.
	 * 
	 * @param assRuleDataControl
	 *            Controller of the element reference
	 */
	public TimedAssessmentRulePanel( AssessmentRuleDataControl assRuleDataControl ) {

		// Set the controller
		Controller controller = Controller.getInstance( );
		this.assessmentRuleDataControl = assRuleDataControl;
		
		// Calculate the currentEffect index:
		if (assessmentRuleDataControl.getEffectsCount( )>0){
			currentEffect = 0;
		} else 
			currentEffect = -1;

		// Set the layout
		setLayout( new GridBagLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentRule.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 1, 5, 1, 5 );

		// Info panel
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		JTextPane informationTextPane = new JTextPane( );
		informationTextPane.setEditable( false );
		informationTextPane.setBackground( getBackground( ) );
		informationTextPane.setText( TextConstants.getText( "TimedAssessmentRule.Information" ) );
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
		importanceComboBox.addActionListener( new ImportanceComboBoxListener( ) );
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
		conditionsPanel.setLayout( new GridLayout(1,2) );
		JButton initConditionsButton = new JButton( TextConstants.getText( "GeneralText.EditInitConditions" ) );
		initConditionsButton.addActionListener( new InitConditionsButtonListener( ) );
		
		JButton endConditionsButton = new JButton( TextConstants.getText( "GeneralText.EditEndConditions" ) );
		endConditionsButton.addActionListener( new EndConditionsButtonListener( ) );
		
		conditionsPanel.add( initConditionsButton );
		conditionsPanel.add( endConditionsButton );
		conditionsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentRule.Conditions" ) ) );
		add( conditionsPanel, c );

		// Create the effect panel
		JPanel effectPanel = new JPanel();
		effectPanel.setLayout( new GridBagLayout( ) );
		effectPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentRule.Effect.Title" ) ) );
		GridBagConstraints c2 = new GridBagConstraints( );
		
		c2.gridx=0;c2.gridy=0;
		c2.insets = new Insets( 1, 5, 1, 5 );
		c2.fill=GridBagConstraints.HORIZONTAL;
		c2.weightx=0.5; c2.weighty=0; c2.gridwidth = 1;
		// Create the effect-selector panel
		JPanel selectorPanel = new JPanel();
		selectorPanel.setLayout( new GridLayout(1,3) );
		addEffectBlock = new JButton(new ImageIcon("img/icons/addNode.png"));
		addEffectBlock.setContentAreaFilled( false );
		addEffectBlock.addActionListener( new AddEffectListener() );
		deleteEffectBlock = new JButton(new ImageIcon("img/icons/deleteNode.png"));
		deleteEffectBlock.setContentAreaFilled( false );
		deleteEffectBlock.addActionListener( new DeleteEffectListener() );
		this.effectComboBox = new JComboBox(this.assessmentRuleDataControl.getEffectNames( ));
		effectComboBox.setEditable( false );
		effectComboBox.addActionListener( new EffectsComboBoxListener() );
		if (assessmentRuleDataControl.getEffectsCount( ) == 0){
			effectComboBox.setEnabled( false );
		}
		selectorPanel.add( addEffectBlock );
		selectorPanel.add( deleteEffectBlock );
		selectorPanel.add( effectComboBox );
		selectorPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "TimedAssessmentRule.Effects" ) ) );
		effectPanel.add( selectorPanel, c2 );
		
		// Create the time panel
		c2.gridx = 1;
		JPanel timePanel = new JPanel();
		timePanel.setLayout( new GridLayout(1,4) );
		JLabel minTimeLabel = new JLabel(TextConstants.getText( "TimedAssessmentRule.MinTime" ));
		JLabel maxTimeLabel = new JLabel(TextConstants.getText( "TimedAssessmentRule.MaxTime" ));
		if (this.currentEffect>=0){
			int current = this.assessmentRuleDataControl.getMinTime( currentEffect );
			int min = 0;
			int max = Integer.MAX_VALUE;
			int increment = 1;
			this.minTime = new JSpinner(new SpinnerNumberModel(current, min, max, increment));
			min  = current+1;
			current = this.assessmentRuleDataControl.getMaxTime( currentEffect );
			this.maxTime = new JSpinner(new SpinnerNumberModel(current, min, max, increment));
		} else {
			this.minTime = new JSpinner();
			minTime.setEnabled( false );
			this.maxTime = new JSpinner();
			maxTime.setEnabled( false );
		}
		minTime.addChangeListener( new TimeMinListener() );
		maxTime.addChangeListener( new TimeMaxListener() );
		timePanel.add( minTimeLabel );
		timePanel.add( minTime );
		timePanel.add( maxTimeLabel );
		timePanel.add( maxTime );
		timePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "TimedAssessmentRule.Time" ) ) );
		effectPanel.add( timePanel, c2 );
		
		c2.gridx = 0; c2.gridy = 1;
		c2.fill=GridBagConstraints.BOTH;
		c2.weightx = 1; c2.gridwidth = 2; c2.weighty=0.2;
		JPanel textPanel = new JPanel( );
		textPanel.setLayout( new GridLayout( ) );
		if (this.currentEffect>=0)
			textTextArea = new JTextArea( assRuleDataControl.getEffectText( currentEffect ), 4, 0 );
		else{
			textTextArea = new JTextArea( "", 4, 0 );
			textTextArea.setEditable( false );
		}
		textTextArea.setLineWrap( true );
		textTextArea.setWrapStyleWord( true );
		textTextArea.getDocument( ).addDocumentListener( new DocumentationTextAreaChangesListener( ) );
		textPanel.add( new JScrollPane( textTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		textPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentRule.Effect.Text" ) , TitledBorder.CENTER, TitledBorder.TOP ) );
		
		effectPanel.add( textPanel, c2 );
		//effectPanel.add( new JFiller() );

		// Create and add the set-property table
		c2.weighty=0.8; c2.fill=GridBagConstraints.BOTH; c2.gridy=2;
		propPanel = new AssessmentPropertiesPanel( this.assessmentRuleDataControl ); 
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
				if (currentEffect >=0)
					assessmentRuleDataControl.setEffectText( currentEffect, textTextArea.getText( ) );
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
				if (currentEffect >=0)
					assessmentRuleDataControl.setEffectText( currentEffect, textTextArea.getText( ) );
			}


		}
	}

	/**
	 * Listener for the items combo box.
	 */
	private class ImportanceComboBoxListener implements ActionListener {

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
	 * Listener for the effects combo box.
	 */
	private class EffectsComboBoxListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			currentEffect = effectComboBox.getSelectedIndex( );
			if (currentEffect>=0){
				// Activate timer spinners
				minTime.setEnabled( true );
				minTime.setModel( new SpinnerNumberModel(assessmentRuleDataControl.getMinTime( currentEffect ), 0, Integer.MAX_VALUE, 1) );
				maxTime.setModel( new SpinnerNumberModel(assessmentRuleDataControl.getMaxTime( currentEffect ), assessmentRuleDataControl.getMinTime( currentEffect )+1, Integer.MAX_VALUE, 1) );
				minTime.updateUI( );
				maxTime.updateUI( );
				maxTime.setEnabled( true );
				
				// Activate delete button
				deleteEffectBlock.setEnabled( true );
				
				// Change the effect text
				textTextArea.setText( assessmentRuleDataControl.getEffectText( currentEffect ) );
				
				// Change the properties
				propPanel.setCurrentIndex( currentEffect );
			}
		}
	}
	
	private class AddEffectListener implements ActionListener {

		public void actionPerformed( ActionEvent e ) {
			assessmentRuleDataControl.addEffectBlock( currentEffect );
			
			if ( currentEffect == -1){
				currentEffect = 0;
				textTextArea.setText( assessmentRuleDataControl.getEffectText( currentEffect ) );
				// Activate timer spinners
				minTime.setEnabled( true );
				minTime.setModel( new SpinnerNumberModel(assessmentRuleDataControl.getMinTime( currentEffect ), 0, Integer.MAX_VALUE, 1) );
				maxTime.setModel( new SpinnerNumberModel(assessmentRuleDataControl.getMaxTime( currentEffect ), assessmentRuleDataControl.getMinTime( currentEffect )+1, Integer.MAX_VALUE, 1) );
				minTime.updateUI( );
				maxTime.updateUI( );
				maxTime.setEnabled( true );
			}
			
			// Update the combo box
			effectComboBox.setModel( new DefaultComboBoxModel(assessmentRuleDataControl.getEffectNames( )) );
			
			propPanel.setCurrentIndex( currentEffect );
			
			effectComboBox.setSelectedIndex( currentEffect );
			effectComboBox.updateUI( );
			effectComboBox.setEnabled( true );
			textTextArea.setEditable( true );
		}
		
	}
	
	private class DeleteEffectListener implements ActionListener {

		public void actionPerformed( ActionEvent e ) {
			if (currentEffect>=0){
				assessmentRuleDataControl.removeEffectBlock( currentEffect );
				
				// Update the combo box
				effectComboBox.setModel( new DefaultComboBoxModel(assessmentRuleDataControl.getEffectNames( )) );
				if ( currentEffect > 0)
					currentEffect --;
				else if (assessmentRuleDataControl.getEffectsCount( )==0)
					currentEffect = -1;
				else
					currentEffect = 0;
				
				if (currentEffect!=-1){
					effectComboBox.setEnabled( true );
					textTextArea.setEditable( true );
					textTextArea.setText( assessmentRuleDataControl.getEffectText( currentEffect ) );
					effectComboBox.setSelectedIndex( currentEffect );
					// Activate timer spinners
					minTime.setEnabled( true );
					minTime.setModel( new SpinnerNumberModel(assessmentRuleDataControl.getMinTime( currentEffect ), 0, Integer.MAX_VALUE, 1) );
					maxTime.setModel( new SpinnerNumberModel(assessmentRuleDataControl.getMaxTime( currentEffect ), assessmentRuleDataControl.getMinTime( currentEffect )+1, Integer.MAX_VALUE, 1) );
					minTime.updateUI( );
					maxTime.updateUI( );
					maxTime.setEnabled( true );
				}else{
					effectComboBox.setEnabled( false );
					textTextArea.setText( "" );
					textTextArea.setEditable( false );
					// Activate timer spinners
					minTime.setEnabled( false );
					minTime.setModel( new SpinnerNumberModel() );
					maxTime.setModel( new SpinnerNumberModel() );
					minTime.updateUI( );
					maxTime.updateUI( );
					maxTime.setEnabled( false );
				}
				effectComboBox.updateUI( );
			}
		}
		
	}
	
	private class TimeMinListener implements ChangeListener {

		public void stateChanged( ChangeEvent e ) {
			SpinnerNumberModel model =  (SpinnerNumberModel)minTime.getModel( );
			int currentMin = model.getNumber( ).intValue( );
			assessmentRuleDataControl.setMinTime(currentMin, currentEffect );
			
			SpinnerNumberModel modelMax = (SpinnerNumberModel)maxTime.getModel( );
			int currentMax = modelMax.getNumber( ).intValue( );
			if (currentMin>= currentMax){
				currentMax = currentMin+1;
				assessmentRuleDataControl.setMaxTime(currentMax, currentEffect );
			}
			
			maxTime.setModel( new SpinnerNumberModel(currentMax, currentMin, Integer.MAX_VALUE, 1) );
			maxTime.updateUI( );
			minTime.updateUI( );
		}
		
	}

	private class TimeMaxListener implements ChangeListener {

		public void stateChanged( ChangeEvent e ) {
			SpinnerNumberModel model =  (SpinnerNumberModel)maxTime.getModel( );
			int currentMax = model.getNumber( ).intValue( );
			assessmentRuleDataControl.setMaxTime(currentMax, currentEffect );
			maxTime.updateUI( );
		}
		
	}

	

	/**
	 * Listener for the edit conditions button.
	 */
	private class InitConditionsButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			new ConditionsDialog( assessmentRuleDataControl.getInitConditions( ) );
		}
	}
	
	/**
	 * Listener for the edit conditions button.
	 */
	private class EndConditionsButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			new ConditionsDialog( assessmentRuleDataControl.getEndConditions( ) );
		}
	}

}
