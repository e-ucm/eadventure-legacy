package es.eucm.eadventure.editor.gui.elementpanels.assessment;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicButtonUI;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentRuleDataControl;
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
	 * Text area for the documentation.
	 */
	private JTextArea conceptTextArea;

	private JTextArea textTextArea;

	private int currentEffect;
	
	private JButton addEffectBlock;
	
	private JButton deleteEffectBlock;
		
	private JSpinner minTime;
	
	private JSpinner maxTime;
	
	private AssessmentPropertiesPanel propPanel;
	
	private JButton endConditionsButton;
	
	public int numEffects;
	
	public int currentTab;
	
	private JTabbedPane container2 ;
	
	private boolean scorm12;
	
	private boolean scorm2004;
	

	/**
	 * Constructor.
	 * 
	 * @param assRuleDataControl
	 *            Controller of the element reference
	 * @param  scorm12 
	 * 				Show if it is a Scorm 1.2 profile  
	 * 
	 * @param  scorm2004
	 * 				Show if it is a Scorm 2004 profile                  
	 */
	public TimedAssessmentRulePanel( AssessmentRuleDataControl assRuleDataControl, boolean scorm12, boolean scorm2004 ) {
		this.assessmentRuleDataControl = assRuleDataControl;
		this.scorm12=scorm12;
		this.scorm2004=scorm2004;
		this.numEffects=0;
		this.currentTab=0;
		// Calculate the currentEffect index:
		if (assessmentRuleDataControl.getEffectsCount( )>0){
			currentEffect = 0;
			
		} else 
			currentEffect = -1;

		// Set the layout
		JPanel container1= new JPanel(new GridBagLayout());
		setLayout( new GridBagLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentRule.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 1, 5, 1, 5 );

		GridBagConstraints g = new GridBagConstraints();
		g.fill=GridBagConstraints.BOTH;
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.gridy=0;

		c.weighty=1;
		c.ipady=20;
		JPanel conceptPanel = new JPanel( );
		conceptPanel.setLayout( new GridLayout( ) );
		conceptTextArea = new JTextArea( assRuleDataControl.getConcept( ), 4, 0 );
		conceptTextArea.setLineWrap( true );
		conceptTextArea.setWrapStyleWord( true );
		conceptTextArea.getDocument( ).addDocumentListener( new DocumentationTextAreaChangesListener( null) );
		conceptPanel.add( new JScrollPane( conceptTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ),g );
		conceptPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentRule.Concept" ) ) );
		container1.add( conceptPanel, c );

		// Create the button for the conditions
		
		JPanel conditionsPanel = new JPanel( );
		conditionsPanel.setLayout( new GridBagLayout() );
		
		
		JButton initConditionsButton = new JButton( TextConstants.getText( "GeneralText.EditInitConditions" ) );
		initConditionsButton.addActionListener( new InitConditionsButtonListener( ) );
		
		JCheckBox useEndConditionsCheck = new JCheckBox(TextConstants.getText("Timer.UsesEndConditionShort"));
		useEndConditionsCheck.setSelected(assessmentRuleDataControl.isUsesEndConditions());
		useEndConditionsCheck.addChangeListener( new UseEndConditionsCheckListener( ));
		
		endConditionsButton = new JButton( TextConstants.getText( "GeneralText.EditEndConditions" ) );
		endConditionsButton.setEnabled(assessmentRuleDataControl.isUsesEndConditions());
		endConditionsButton.addActionListener( new EndConditionsButtonListener( ) );
		
		
		g = new GridBagConstraints();
		g.fill = GridBagConstraints.HORIZONTAL;
		g.gridwidth = 2;
		g.gridx = 0;
		g.gridy = 0;
		//g.weightx = 1;
		conditionsPanel.add( useEndConditionsCheck , g);
		
		g.gridx = 0;
		g.gridwidth = 1;
		//g.fill = GridBagConstraints.BOTH;
		//g.weightx = 0.5;
		g.gridy = 1;
		g.anchor = GridBagConstraints.CENTER;
		conditionsPanel.add( initConditionsButton , g);
		g.gridy = 2;
		conditionsPanel.add( endConditionsButton , g);
		conditionsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentRule.Conditions" ) ) );
		
		c.gridy++;
		c.ipady=-10;
		container1.add( conditionsPanel, c );
		
		container2 = new JTabbedPane();
				
		addEffectBlock = new AddTabButton(new ImageIcon("img/icons/addNode.png"));
		addEffectBlock.setContentAreaFilled( false );
		//addEffectBlock.addActionListener( new AddEffectListener() );
		
		
		JPanel empty = new JPanel() ;
		empty.add(new JLabel("No hay ningun efecto creado               "));
		empty.setMinimumSize(new Dimension(200,200));
		container2.add(empty);
		container2.setTabComponentAt(0, addEffectBlock);
		container2.addChangeListener(new EffectsTabPaneListener());
		
		if (currentEffect!=-1){
		for (int i=0;i<assessmentRuleDataControl.getEffectsCount();i++){
		    currentEffect=i;
		    JPanel effectPanel = createEffectPanel();
		    
		    container2.add(effectPanel);
		    deleteEffectBlock = new DeleteTabButton(new ImageIcon("img/icons/deleteNode.png"));
		    deleteEffectBlock.setContentAreaFilled( false );
		    JPanel buttonCont =  new JPanel(new GridLayout(0,2));
		    buttonCont.add(new JLabel("#"+i));
		    buttonCont.add(deleteEffectBlock);
		    container2.setTabComponentAt(i+1,buttonCont );
		}
		numEffects=currentEffect+1;
		currentEffect=0;
		currentTab=1;
		container2.setSelectedIndex(currentTab);
		
		}
		c =  new GridBagConstraints();
		c.gridy=0;
		c.gridx=0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx=0.75;
		c.weighty=1;
		c.insets = new Insets( 5, 5, 5, 5 );
		container1.setMinimumSize(new Dimension(200,250));
		container1.setMaximumSize(new Dimension(200,250));
		container1.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentRule.Conditions" ) ) );
		//container1.setMinimumSize(new Dimension(100,50));
		add(container1,c);
		c.gridx=1;
		c.weightx=1;
		container2.setMaximumSize(new Dimension(250,250));
		container2.setMinimumSize(new Dimension(250,250));
		add(container2,c);
		this.updateUI();
		// Add the other elements of the scene if a background image was loaded
	}
	
	private JPanel createEffectPanel(){
	 // Create the effect panel
		JPanel effectPanel = new JPanel();
		effectPanel.setLayout( new GridBagLayout( ) );
		effectPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentRule.Effect.Title" ) ) );
		GridBagConstraints c2 = new GridBagConstraints( );
		
		c2.gridx=0;c2.gridy=0;
		c2.insets = new Insets( 1, 5, 1, 5 );
		c2.fill=GridBagConstraints.HORIZONTAL;
		c2.weightx=0.5; c2.weighty=0; c2.gridwidth = 1;
		
		// Create the time panel
		c2.gridx = 0;
		JPanel timePanel = new JPanel();
		timePanel.setLayout( new GridBagLayout() );
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
			//minTime.setEnabled( false );
			this.maxTime = new JSpinner();
			//maxTime.setEnabled( false );
		}
		minTime.addChangeListener( new TimeMinListener(minTime,maxTime) );
		maxTime.addChangeListener( new TimeMaxListener(maxTime) );
		minTime.setMaximumSize(new Dimension(5,5));
		maxTime.setMaximumSize(new Dimension(5,5));
		
		GridBagConstraints c3= new GridBagConstraints( );
		
		c3.gridx=0;c3.gridy=0;
		c3.insets = new Insets( 1, 5, 1, 5 );
		c3.fill=GridBagConstraints.BOTH;
		c3.weightx=1; c3.weighty=0; c3.gridwidth = 1;

		timePanel.add( minTimeLabel,c3 );
		c3.gridx++;
		c3.ipadx=-60;
		timePanel.add( minTime,c3 );
		c3.gridx++;
		c3.ipadx=1;
		timePanel.add( maxTimeLabel,c3 );
		c3.gridx++;
		c3.ipadx=-60;
		timePanel.add( maxTime,c3 );
		timePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "TimedAssessmentRule.Time" ) ) );
		effectPanel.add( timePanel, c2 );
		
		c2.gridx = 0; c2.gridy = 1;
		c2.fill=GridBagConstraints.BOTH;
		c2.weightx = 1; c2.gridwidth = 2; c2.weighty=0.2;
		c2.ipady=10;
		JPanel textPanel = new JPanel( );
		textPanel.setLayout( new GridLayout( ) );
		if (this.currentEffect>=0)
			textTextArea = new JTextArea( assessmentRuleDataControl.getEffectText( currentEffect ), 4, 0 );
		else{
			textTextArea = new JTextArea( "", 4, 0 );
			//textTextArea.setEditable( false );
		}
		textTextArea.setLineWrap( true );
		textTextArea.setWrapStyleWord( true );
		textTextArea.getDocument( ).addDocumentListener( new DocumentationTextAreaChangesListener(textTextArea ) );
		textPanel.add( new JScrollPane( textTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		textPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentRule.Effect.Text" ) , TitledBorder.CENTER, TitledBorder.TOP ) );
		
		effectPanel.add( textPanel, c2 );
		//effectPanel.add( new JFiller() );

		// Create and add the set-property table
		c2.ipady=-9;
		c2.weighty=0.8; c2.fill=GridBagConstraints.BOTH; c2.gridy=2;
		propPanel = new AssessmentPropertiesPanel( this.assessmentRuleDataControl, scorm12, scorm2004 ); 
		if (this.currentEffect>=0)
		    propPanel.setCurrentIndex(currentEffect);
		effectPanel.add( propPanel, c2 );
		return effectPanel;
	}

	
	
	/**
	 * Listener for the text area. It checks the value of the area and updates the documentation.
	 */
	private class DocumentationTextAreaChangesListener implements DocumentListener {

	    
	    private JTextArea textArea;
	    
	    public DocumentationTextAreaChangesListener(JTextArea textArea){
		this.textArea =textArea ; 
	    }
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
			
			else if (arg0.getDocument( ) == textArea.getDocument( )){
				currentEffect=container2.getSelectedIndex()-1;
			    	if (currentEffect >=0)
					assessmentRuleDataControl.setEffectText( currentEffect, textArea.getText( ) );
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
			
			else if (arg0.getDocument( ) == textArea.getDocument( )){
			    currentEffect=container2.getSelectedIndex()-1;
				if (currentEffect >=0)
					assessmentRuleDataControl.setEffectText( currentEffect, textArea.getText( ) );
			}


		}
	}

	/**
	 * Listener for the effects combo box.
	 */
	private class EffectsTabPaneListener implements ChangeListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		/*public void actionPerformed( ActionEvent e ) {
			currentEffect = container2.getSelectedIndex( );
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
				
				propPanel.setEnabled(true);
				// Change the properties
				propPanel.setCurrentIndex( currentEffect );
			}
		}*/

		@Override
		public void stateChanged(ChangeEvent e) {
		    int pastTab =  currentTab;
		    //currentEffect = container2.getSelectedIndex( )-1;
		    currentTab=container2.getSelectedIndex( );
		  if (pastTab!=currentTab){
			if (currentTab>0){
				// Activate timer spinners
				//minTime.setEnabled( true );
				minTime.setModel( new SpinnerNumberModel(assessmentRuleDataControl.getMinTime( currentTab-1 ), 0, Integer.MAX_VALUE, 1) );
				maxTime.setModel( new SpinnerNumberModel(assessmentRuleDataControl.getMaxTime( currentTab-1 ), assessmentRuleDataControl.getMinTime( currentTab-1 )+1, Integer.MAX_VALUE, 1) );
				minTime.updateUI( );
				maxTime.updateUI( );
				//maxTime.setEnabled( true );
				
				// Activate delete button
				deleteEffectBlock.setEnabled( true );
				
				// Change the effect text
				textTextArea.setText( assessmentRuleDataControl.getEffectText( currentTab-1 ) );
				
				propPanel.setEnabled(true);
				// Change the properties
				propPanel.setCurrentIndex( currentTab-1 );
				propPanel.updateUI();
			    
				
			   /* currentEffect=currentTab-1;
			    JPanel effectPanel = createEffectPanel();
			    
			    //container2.getComponentAt(currentEffect);
			    container2.setComponentAt(currentEffect, effectPanel);
			    /*deleteEffectBlock = new DeleteTabButton(new ImageIcon("img/icons/deleteNode.png"));
			    deleteEffectBlock.setContentAreaFilled( false );
			    JPanel buttonCont =  new JPanel(new GridLayout(0,2));
			    buttonCont.add(new JLabel("#"+currentEffect));
			    buttonCont.add(deleteEffectBlock);
			    container2.setTabComponentAt(currentEffect+1,buttonCont );*/

			    
			}else{
			    if (pastTab==0&&container2.getTabCount()>1)
			    container2.setSelectedIndex(1);
			    else if (pastTab<container2.getTabCount())
				container2.setSelectedIndex(pastTab);
			    else 
				container2.setSelectedIndex(0);
			}
		  }
		}
		
	}
	
	private class TimeMinListener implements ChangeListener {
	    
	    private JSpinner minT;
	    
	    private JSpinner maxT;
	    
	    public TimeMinListener(JSpinner minT,JSpinner maxT){
		this.minT = minT;
		this.maxT = maxT;
	    }
	    
		public void stateChanged( ChangeEvent e ) {
			//SpinnerNumberModel model =  (SpinnerNumberModel)minTime.getModel( );
			
		    	int currentMin = ((Number)minT.getValue()).intValue();
			currentEffect = container2.getSelectedIndex()-1;
			assessmentRuleDataControl.setMinTime(currentMin, currentEffect );
			
			maxT.setModel( new SpinnerNumberModel(assessmentRuleDataControl.getMaxTime(currentEffect), currentMin + 1, Integer.MAX_VALUE, 1) );
			maxT.updateUI( );
			minT.updateUI( );
		}
		
	}

	private class TimeMaxListener implements ChangeListener {

	    private JSpinner maxT;
	    
	    public TimeMaxListener(JSpinner maxT){
		this.maxT = maxT;
	    }
	    
	    
		public void stateChanged( ChangeEvent e ) {
			//SpinnerNumberModel model =  (SpinnerNumberModel)maxTime.getModel( );
			int currentMax = ((Number)maxT.getValue()).intValue();
			currentEffect = container2.getSelectedIndex()-1;
			assessmentRuleDataControl.setMaxTime(currentMax, currentEffect );
			maxT.updateUI( );
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

	private class UseEndConditionsCheckListener implements ChangeListener {
		public void stateChanged(ChangeEvent arg0) {
			if (((JCheckBox) arg0.getSource()).isSelected() != assessmentRuleDataControl.isUsesEndConditions()) {
				assessmentRuleDataControl.setUsesEndConditions(((JCheckBox) arg0.getSource()).isSelected());
				endConditionsButton.setEnabled(((JCheckBox) arg0.getSource()).isSelected());
			}
		}
	}
	
	private class AddTabButton extends JButton implements ActionListener {
	        /**
	     * 
	     */
	    private static final long serialVersionUID = -657186905921501288L;

		public AddTabButton(ImageIcon icon) {
	            super(icon);
	            int size = 17;
	            setPreferredSize(new Dimension(size, size));
	            setToolTipText("Add effect");
	            //Make the button looks the same for all Laf's
	            setUI(new BasicButtonUI());
	            //Make it transparent
	            setContentAreaFilled(false);
	            //No need to be focusable
	            setFocusable(false);
	            setBorder(BorderFactory.createEtchedBorder());
	            setBorderPainted(false);
	            //Making nice rollover effect
	            //we use the same listener for all buttons
	            addMouseListener(buttonMouseListener);
	            setRolloverEnabled(true);
	            //Close the proper tab by clicking the button
	            addActionListener(this);
	        }

	        public void actionPerformed(ActionEvent e) {
	            //currentEffect = assessmentRuleDataControl.getEffectsCount()+1;
	            	
	            	assessmentRuleDataControl.addEffectBlock( numEffects );
	            	currentEffect=-1;
			JPanel cont = createEffectPanel();
			/*if ( currentEffect == -1){
				currentEffect = 0;
				textTextArea.setText( assessmentRuleDataControl.getEffectText( currentEffect ) );
				// Activate timer spinners
				minTime.setEnabled( true );
				minTime.setModel( new SpinnerNumberModel(assessmentRuleDataControl.getMinTime( currentEffect ), 0, Integer.MAX_VALUE, 1) );
				maxTime.setModel( new SpinnerNumberModel(assessmentRuleDataControl.getMaxTime( currentEffect ), assessmentRuleDataControl.getMinTime( currentEffect )+1, Integer.MAX_VALUE, 1) );
				minTime.updateUI( );
				maxTime.updateUI( );
				maxTime.setEnabled( true );
			}*/
			
			// Update the combo box
			//effectComboBox.setModel( new DefaultComboBoxModel(assessmentRuleDataControl.getEffectNames( )) );
			
			propPanel.setEnabled(true);
			propPanel.setCurrentIndex( numEffects );
			
			//effectComboBox.setSelectedIndex( currentEffect );
			//effectComboBox.updateUI( );
			//effectComboBox.setEnabled( true );
			textTextArea.setEditable( true );
			
			
			deleteEffectBlock = new DeleteTabButton(new ImageIcon("img/icons/deleteNode.png"));
			deleteEffectBlock.setContentAreaFilled( false );
			JPanel buttonCont =  new JPanel(new GridLayout(0,2));
			buttonCont.add(new JLabel("#"+Integer.toString(numEffects)));
			numEffects++;
			buttonCont.add(deleteEffectBlock);
			container2.add(cont,numEffects);
			container2.setTabComponentAt(numEffects,buttonCont );
			container2.setSelectedIndex(numEffects);
	        }
	}
	
	private class DeleteTabButton extends JButton implements ActionListener {
	    /**
	     * 
	     */
	    private static final long serialVersionUID = -1916833906840632247L;

		public DeleteTabButton(ImageIcon icon) {
	            super(icon);
	            int size = 17;
	            setPreferredSize(new Dimension(size, size));
	            setToolTipText("remove this effect");
	            //Make the button looks the same for all Laf's
	            setUI(new BasicButtonUI());
	            //Make it transparent
	            setContentAreaFilled(false);
	            //No need to be focusable
	            setFocusable(false);
	            setBorder(BorderFactory.createEtchedBorder());
	            setBorderPainted(false);
	            //Making nice rollover effect
	            //we use the same listener for all buttons
	            addMouseListener(buttonMouseListener);
	            setRolloverEnabled(true);
	            //Close the proper tab by clicking the button
	            addActionListener(this);
	        }

	        public void actionPerformed(ActionEvent e) {
	            currentEffect = container2.getSelectedIndex()-1;
			if (currentEffect>=0){
				assessmentRuleDataControl.removeEffectBlock( currentEffect );
				
				// Update the combo box
				//effectComboBox.setModel( new DefaultComboBoxModel(assessmentRuleDataControl.getEffectNames( )) );
				
				numEffects--;
				container2.remove(currentEffect+1);
				//container2.updateUI();
				//effectComboBox.updateUI( );
			}
	        }
	}
	
	private final static MouseListener buttonMouseListener = new MouseAdapter() {
	        public void mouseEntered(MouseEvent e) {
	            Component component = e.getComponent();
	            if (component instanceof AbstractButton) {
	                AbstractButton button = (AbstractButton) component;
	                button.setBorderPainted(true);
	            }
	        }

	        public void mouseExited(MouseEvent e) {
	            Component component = e.getComponent();
	            if (component instanceof AbstractButton) {
	                AbstractButton button = (AbstractButton) component;
	                button.setBorderPainted(false);
	            }
	        }
	    };
}
