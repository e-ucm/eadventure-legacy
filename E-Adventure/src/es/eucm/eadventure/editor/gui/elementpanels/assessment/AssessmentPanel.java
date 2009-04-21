package es.eucm.eadventure.editor.gui.elementpanels.assessment;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationRuleDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentRuleDataControl;
import es.eucm.eadventure.editor.gui.editdialogs.ConditionsDialog;
import es.eucm.eadventure.editor.gui.elementpanels.adaptation.AdaptationPanel.AdaptationRulesInfoTableModel;
import es.eucm.eadventure.editor.gui.elementpanels.adaptation.AdaptationPanel.ProfileTypeDialog;



public class AssessmentPanel extends JPanel{

    
    /**
     * Panel which contains the profiles's type
     */
    private JPanel profileTypePanel;
    
    /**
     * Panel which contains the initial state
     */
    private JPanel feedbackPanel; 
    
    /**
     * Panel which contains all the rules associated with current profile
     */
    private JPanel ruleListPanel;
    
    /**
     * Panel which contains the initial state and LMS state of selected rule
     */
    private JPanel rulesInfoPanel;
    
    /**
     * 
     */
    private JLabel typeLabel;
    
    
    private JCheckBox showReportAtEnd;
	
	private JTextField sendByEmailText;
	
	private JCheckBox sendByEmailCheck;
	
	private JCheckBox smtpSSL;
	
	private JTextField smtpServer;
	
	private JTextField smtpPort;
	
	private JTextField smtpUser;
	
	private JTextField smtpPwd;
	
	private JPanel smtpConfig;
	
	private JButton saveSmtpConfig;
	
	private JTable informationTable;
	
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
     * Data control
     */
    private AssessmentProfileDataControl dataControl;
    
    private AssessmentRuleDataControl currentRuleDataControl;
    
    public AssessmentPanel(AssessmentProfileDataControl dataControl){
	this.dataControl = dataControl;
	createProfileTypePanel();
	createFeedbackPanel();
	createRuleListPanel();
	rulesInfoPanel = new JPanel(new GridBagLayout());
	createRulesInfoPanel();
	this.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints( );
	c.insets = new Insets( 5, 5, 5, 5 );
	c.fill = GridBagConstraints.BOTH;
	c.weightx = 0;
	c.weighty = 0;
	c.gridx=0;
	c.gridy=0;
	this.add(profileTypePanel,c);
	c.gridx = 1;
	this.add(feedbackPanel,c);
	c.weightx = 1;
	c.weighty = 1;
	c.gridx=0;
	c.gridwidth=2;
	//c.ipady = 80;
	c.gridy++;
	this.add(ruleListPanel,c);
	c.ipady=50;
	c.gridy++;
	this.add(rulesInfoPanel,c);
    }
    
    private void createRulesInfoPanel(){
	if (informationTable.getSelectedRow( )<0 || informationTable.getSelectedRow( )>=dataControl.getAssessmentRules().size()){
	    rulesInfoPanel.removeAll();
		JPanel empty = new JPanel();
		JLabel label = new JLabel(TextConstants.getText("AdaptationProfile.Empty"));
		empty.add(label);
		rulesInfoPanel.add(empty);
		rulesInfoPanel.setMinimumSize(new Dimension(0,190));
	}else {
	    rulesInfoPanel.removeAll();
	    // take the current rule data control
	    currentRuleDataControl = dataControl.getAssessmentRules().get(informationTable.getSelectedRow());
	    if (currentRuleDataControl.isTimedRule() ){
		GridBagConstraints c = new GridBagConstraints( );
		c.fill = GridBagConstraints.BOTH;
		c.weightx=1;
		c.weighty=1;
		rulesInfoPanel.setLayout(new GridBagLayout());
		rulesInfoPanel.add(new TimedAssessmentRulePanel(currentRuleDataControl,dataControl.isScorm12(),dataControl.isScorm2004()),c);
		rulesInfoPanel.updateUI();
		
		
	    }else {
		
		// Create first side
		JPanel firstSide = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the combo box of importance
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.gridy=0;
		JPanel importancePanel = new JPanel( );
		importancePanel.setLayout( new GridLayout( ) );
		
		importanceComboBox = new JComboBox( new String[]{TextConstants.getText( "AssessmentRule.Importance.VeryLow" ), TextConstants.getText( "AssessmentRule.Importance.Low" ), TextConstants.getText( "AssessmentRule.Importance.Normal" ), TextConstants.getText( "AssessmentRule.Importance.High" ), TextConstants.getText( "AssessmentRule.Importance.VeryHigh" )} );
		importanceComboBox.setSelectedIndex( currentRuleDataControl.getImportance( ) );
		importanceComboBox.addActionListener( new ItemComboBoxListener( ) );
		importancePanel.add( importanceComboBox );
		importancePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentRule.Importance.Title" ) ) );
		firstSide.add( importancePanel, c );

		// Create the text area for the concept
		c.gridy++;
		c.weighty=1;
		c.ipady=20;
		JPanel conceptPanel = new JPanel( );
		conceptPanel.setLayout( new GridLayout( ) );
		conceptTextArea = new JTextArea( currentRuleDataControl.getConcept( ), 4, 0 );
		conceptTextArea.setLineWrap( true );
		conceptTextArea.setWrapStyleWord( true );
		conceptTextArea.getDocument( ).addDocumentListener( new DocumentationTextAreaChangesListener( ) );
		conceptPanel.add( new JScrollPane( conceptTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		conceptPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentRule.Concept" ) ) );
		firstSide.add( conceptPanel, c );

		// Create the button for the conditions
		c.gridy++;
		c.ipady=0;
		JPanel conditionsPanel = new JPanel( );
		conditionsPanel.setLayout( new GridBagLayout( ) );
		JButton conditionsButton = new JButton( TextConstants.getText( "GeneralText.EditConditions" ) );
		conditionsButton.addActionListener( new ConditionsButtonListener( ) );
		GridBagConstraints c2 = new GridBagConstraints( );
		c2.anchor=GridBagConstraints.CENTER;
		conditionsPanel.add( conditionsButton,c2 );
		conditionsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentRule.Conditions" ) ) );
		firstSide.add( conditionsPanel, c );
		
		c.gridx=0;
		c.gridy = 0;
		 rulesInfoPanel.add(firstSide,c);
		
		// Create the effect panel (second tab)
		JPanel effectPanel = new JPanel();
		effectPanel.setLayout( new GridBagLayout( ) );
		effectPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentRule.Effect.Title" ) ) );
		c2 = new GridBagConstraints( );
		
		c2.gridx=0;c2.gridy=0;
		c2.insets = new Insets( 5, 5, 5, 5 );
		c2.fill=GridBagConstraints.BOTH;
		c2.weightx=1; c2.weighty=0.2;
		JPanel textPanel = new JPanel( );
		textPanel.setLayout( new GridLayout( ) );
		textTextArea = new JTextArea( currentRuleDataControl.getEffectText( 0), 4, 0 );
		textTextArea.setLineWrap( true );
		textTextArea.setWrapStyleWord( true );
		textTextArea.getDocument( ).addDocumentListener( new DocumentationTextAreaChangesListener( ) );
		textPanel.add( new JScrollPane( textTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		textPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentRule.Effect.Text" ) , TitledBorder.CENTER, TitledBorder.TOP ) );
		
		effectPanel.add( textPanel, c2 );
		//effectPanel.add( new JFiller() );

		// Create and add the set-property table
		c2.weighty=0.8; c2.fill=GridBagConstraints.BOTH; c2.gridy=1;
		AssessmentPropertiesPanel propPanel = new AssessmentPropertiesPanel( this.currentRuleDataControl,this.dataControl.isScorm12(), this.dataControl.isScorm2004()); 
		effectPanel.add( propPanel, c2 );
		
		c.gridx=1;
		rulesInfoPanel.add(effectPanel,c);
		rulesInfoPanel.updateUI();
		
	    }
	}
    }
    
    
	private void createRuleListPanel(){
		informationTable = new JTable( new AssessmentRulesInfoTableModel( dataControl.getAssessmentRulesInfo() ) );
		informationTable.getSelectionModel( ).addListSelectionListener( new ListSelectionListener(){
			public void valueChanged( ListSelectionEvent e ) {
			    createRulesInfoPanel();
			}
		});
		informationTable.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		//informationTable.removeEditor( );
		ruleListPanel = new JPanel( );
		ruleListPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AdaptationRulesList.ListTitle" ) ) );
		ruleListPanel.setLayout( new BorderLayout( ) );
		ruleListPanel.add( new JScrollPane( informationTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ), BorderLayout.CENTER );
		JButton add = new JButton(TextConstants.getText("AdaptationProfile.AddRule"));
		add.addActionListener(new ActionListener(){

		    @Override
		    public void actionPerformed(ActionEvent e) {
			
			if (dataControl.canAddElement(Controller.ASSESSMENT_RULE)&&dataControl.addElement(Controller.ASSESSMENT_RULE, null)){
			   ((AssessmentRulesInfoTableModel) informationTable.getModel()).setAssRules(dataControl.getAssessmentRulesInfo());
			    informationTable.getSelectionModel( ).setSelectionInterval(informationTable.getRowCount()-1 ,informationTable.getRowCount()-1  );
			    informationTable.updateUI();
			    
			}
		    }
		    
		});
		
		JButton addTimed = new JButton(TextConstants.getText("AdaptationProfile.AddTimedRule"));
		addTimed.addActionListener(new ActionListener(){

		    @Override
		    public void actionPerformed(ActionEvent e) {
			
			if (dataControl.canAddElement(Controller.TIMED_ASSESSMENT_RULE)&&dataControl.addElement(Controller.TIMED_ASSESSMENT_RULE, null)){
			   ((AssessmentRulesInfoTableModel) informationTable.getModel()).setAssRules(dataControl.getAssessmentRulesInfo());
			    informationTable.updateUI();
			    
			}
		    }
		    
		});
		JButton delete = new JButton(TextConstants.getText("AdaptationProfile.DeleteRule"));
		delete.addActionListener(new ActionListener(){

		    @Override
		    public void actionPerformed(ActionEvent e) {
			
			if (dataControl.canBeDeleted()&&dataControl.deleteElement(dataControl.getAssessmentRules().get(informationTable.getSelectedRow()), true)){
			    informationTable.clearSelection( );
			    informationTable.changeSelection(0, 1, false, false);
			    ((AssessmentRulesInfoTableModel) informationTable.getModel()).setAssRules(dataControl.getAssessmentRulesInfo());
			    informationTable.updateUI( );
			    
			    createRulesInfoPanel();
			}
		    }
		    
		});
		JPanel buttonsPanel = new JPanel(new GridLayout(0,3));
		buttonsPanel.add(add);
		buttonsPanel.add(addTimed);
		buttonsPanel.add(delete);
		ruleListPanel.add(buttonsPanel,BorderLayout.SOUTH);
	    }
    
    public void createProfileTypePanel(){
	profileTypePanel = new JPanel(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints( );
	c.insets = new Insets( 5, 5, 5, 5 );
	c.fill = GridBagConstraints.BOTH;
	//c.gridx=1;
	profileTypePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentRule.ProfileType" ) ) );
	// Create the label with the profile's type
	typeLabel = new JLabel();
	if (dataControl.isScorm12())
	    typeLabel.setText(TextConstants.getText("AdaptationProfile.Scorm12Profile"));
	else if (dataControl.isScorm2004())
	    typeLabel.setText(TextConstants.getText("AdaptationProfile.Scorm2004Profile"));
	else 
	    typeLabel.setText(TextConstants.getText("AdaptationProfile.NormalProfile"));
	
	// create the button which allow to edit the type of profile
	JPanel buttonPanel = new JPanel();
	JButton edit = new JButton(TextConstants.getText("AdaptationProfile.Edit"));
	edit.addActionListener(new ActionListener(){

	    @Override
	    public void actionPerformed(ActionEvent e) {
		
		new ProfileTypeDialog();
	    }
	    
	});
	buttonPanel.add(edit);
	profileTypePanel.add(typeLabel,c);
	c.gridx=0;
	c.gridy=1;
	profileTypePanel.add(buttonPanel,c);
    }
    
    public void createFeedbackPanel(){
	feedbackPanel = createFeedbackPanel(dataControl);
	/*JPanel buttonPanel = new JPanel();
	JButton expand = new JButton(TextConstants.getText("AdaptationProfile.Expand"));
	expand.addActionListener(new ActionListener(){

	    @Override
	    public void actionPerformed(ActionEvent e) {
		
		new InitialStateDialog();
	    }
	    
	});
	buttonPanel.add(expand);
	GridBagConstraints c = new GridBagConstraints( );
	c.insets = new Insets( 5, 5, 5, 5 );
	c.fill = GridBagConstraints.BOTH;
	c.weightx = 1;
	c.weighty = 1;*/
	//initialStatePanel.add(buttonPanel,c);
    }
    
    private JPanel createFeedbackPanel(AssessmentProfileDataControl assRulesListDataControl) {
	JPanel feedbackPanel = new JPanel();
	feedbackPanel.setLayout(new GridBagLayout());
	feedbackPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentProfile.Feedback.Title" ) ) );

	GridBagConstraints c2 = new GridBagConstraints();

	c2.fill = GridBagConstraints.HORIZONTAL;
	c2.weightx = 1.0;
	c2.gridy = 0;
	
	/*JTextPane feedbackTextPane = new JTextPane( );
	feedbackTextPane.setEditable( false );
	feedbackTextPane.setBackground( getBackground( ) );
	feedbackTextPane.setText( TextConstants.getText( "AssessmentProfile.Feedback.Description" ) );
	feedbackPanel.add( feedbackTextPane, c2 );
	*/
	showReportAtEnd = new JCheckBox ( TextConstants.getText("AssessmentProfile.Feedback.ShowReportAtEnd") );
	if ( assRulesListDataControl.isShowReportAtEnd() ){
		showReportAtEnd.setSelected( true );
	}
	showReportAtEnd.addActionListener( new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (dataControl.isShowReportAtEnd() != showReportAtEnd.isSelected()) {
				if (showReportAtEnd.isSelected()) {
					sendByEmailCheck.setEnabled(true);
					sendByEmailCheck.setSelected(dataControl.isSendByEmail());
					sendByEmailText.setEnabled(dataControl.isSendByEmail());
					smtpSSL.setEnabled(dataControl.isSendByEmail());
					smtpServer.setEnabled(dataControl.isSendByEmail());
					smtpPort.setEnabled(dataControl.isSendByEmail());
					smtpUser.setEnabled(dataControl.isSendByEmail());
					smtpPwd.setEnabled(dataControl.isSendByEmail());
					if (dataControl.isSendByEmail()) {
						sendByEmailText.setText(dataControl.getEmail());
						smtpSSL.setSelected(dataControl.isSmtpSSL());
						smtpServer.setText(dataControl.getSmtpServer());
						smtpPort.setText(dataControl.getSmtpPort());
						smtpUser.setText(dataControl.getSmtpUser());
						smtpPwd.setText(dataControl.getSmtpPwd());
					}
				} else {
					sendByEmailCheck.setEnabled(false);
					sendByEmailCheck.setSelected(false);
					sendByEmailText.setEnabled(false);
					sendByEmailText.setText("");
					smtpSSL.setSelected(false);
					smtpServer.setText("");
					smtpPort.setText("");
					smtpUser.setText("");
					smtpPwd.setText("");

				}
			}	

			dataControl.setShowReportAtEnd(showReportAtEnd.isSelected());
		}
	});
	c2.gridy++;
	feedbackPanel.add( showReportAtEnd , c2);
	
	JPanel sendByEmailPanel = new JPanel();
	sendByEmailCheck = new JCheckBox(TextConstants.getText("AssessmentProfile.Feedback.SendByEmail"));
	sendByEmailCheck.setEnabled(dataControl.isShowReportAtEnd());
	sendByEmailCheck.setSelected(dataControl.isShowReportAtEnd() && dataControl.isSendByEmail());
	sendByEmailCheck.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent arg0) {
			if (sendByEmailCheck.isSelected() != dataControl.isSendByEmail()) {
				dataControl.setSendByEmail(sendByEmailCheck.isSelected());
				sendByEmailText.setEnabled(sendByEmailCheck.isSelected());
				smtpSSL.setEnabled(sendByEmailCheck.isSelected());
				smtpServer.setEnabled(sendByEmailCheck.isSelected());
				smtpPort.setEnabled(sendByEmailCheck.isSelected());
				smtpUser.setEnabled(sendByEmailCheck.isSelected());
				smtpPwd.setEnabled(sendByEmailCheck.isSelected());

				if (sendByEmailCheck.isSelected()) {
					sendByEmailText.setText(dataControl.getEmail());
					smtpSSL.setSelected(dataControl.isSmtpSSL());
					smtpServer.setText(dataControl.getSmtpServer());
					smtpPort.setText(dataControl.getSmtpPort());
					smtpUser.setText(dataControl.getSmtpUser());
					smtpPwd.setText(dataControl.getSmtpPwd());
				} else {
					sendByEmailText.setText("");
					smtpSSL.setSelected(false);
					smtpServer.setText("");
					smtpPort.setText("");
					smtpUser.setText("");
					smtpPwd.setText("");
				}
				dataControl.setSendByEmail(sendByEmailCheck.isSelected());
			}	
		}
	});
	
	sendByEmailText = new JTextField(40);
	sendByEmailText.setEnabled(dataControl.isShowReportAtEnd() && dataControl.isSendByEmail());
	if (dataControl.isSendByEmail() && dataControl.isShowReportAtEnd())
		sendByEmailText.setText(dataControl.getEmail());
	sendByEmailText.addKeyListener(new KeyListener() {
		public void keyPressed(KeyEvent arg0) {
		}
		public void keyReleased(KeyEvent arg0) {
			dataControl.setEmail(sendByEmailText.getText());
		}
		public void keyTyped(KeyEvent arg0) {
		}
	});
	
	JPanel tempPanelemail = new JPanel();
	tempPanelemail.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = 0;
	c.weightx=1;
	tempPanelemail.add(sendByEmailCheck, c);
	c.gridx++;
	c.fill = GridBagConstraints.HORIZONTAL;
	tempPanelemail.add(sendByEmailText, c);
	sendByEmailPanel.add(tempPanelemail);
	
	smtpConfig = createSmtpConfigPanel();
	if (!dataControl.isSendByEmail()) {
		smtpSSL.setEnabled(false);
		smtpServer.setEnabled(false);
		smtpPort.setEnabled(false);
		smtpUser.setEnabled(false);
		smtpPwd.setEnabled(false);
	}
	c2.gridy++;
	feedbackPanel.add(sendByEmailPanel,c2);
	
	JPanel buttonPanel = new JPanel();
	
	JButton expand = new JButton("Edit SMTP");
	expand.addActionListener(new ActionListener(){

	    @Override
	    public void actionPerformed(ActionEvent e) {
		new SMTPDialog();
		
	    }
	    
	});
	
	buttonPanel.add(expand);
	c2.gridy++;
	feedbackPanel.add(buttonPanel,c2);
	
	
	//c2.gridy++;
	//feedbackPanel.add( tempPanel, c2);

	return feedbackPanel;
}
    
    private JPanel createSmtpConfigPanel() {
	JPanel tempPanel = new JPanel();
	tempPanel.setLayout(new BorderLayout());
	
	JPanel panel = new JPanel();
	panel.setLayout(new GridLayout(5,2));
	
	this.smtpSSL = new JCheckBox("Use SSL");
	panel.add(smtpSSL);
	panel.add(new JLabel(""));
	
	smtpServer = new JTextField();
	panel.add(new JLabel("SMTP SERVER"));
	panel.add(smtpServer);
	
	smtpPort = new JTextField();
	panel.add(new JLabel("SMTP PORT"));
	panel.add(smtpPort);
	
	smtpUser = new JTextField();
	panel.add(new JLabel("SMTP USER"));
	panel.add(smtpUser);
	
	smtpPwd = new JPasswordField();
	((JPasswordField) smtpPwd).setEchoChar('*');
	panel.add(new JLabel("SMTP PASSWORD"));
	panel.add(smtpPwd);

	if (dataControl.isSendByEmail()) {
		smtpSSL.setSelected(dataControl.isSmtpSSL());
		smtpServer.setText(dataControl.getSmtpServer());
		smtpPort.setText(dataControl.getSmtpPort());
		smtpUser.setText(dataControl.getSmtpUser());
		smtpPwd.setText(dataControl.getSmtpPwd());
	}
	
	
	tempPanel.add(panel, BorderLayout.CENTER);
	
	saveSmtpConfig = new JButton("Save configuration");
	saveSmtpConfig.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			if (/*validateIP(smtpIP.getText()) && */validatePort(smtpPort.getText())) {
				dataControl.setSmtpServer(smtpServer.getText());
				dataControl.setSmtpPort(smtpPort.getText());
				dataControl.setSmtpPwd(smtpPwd.getText());
				dataControl.setSmtpSSL(smtpSSL.isSelected());
				dataControl.setSmtpUser(smtpUser.getText());
			} else {
				JOptionPane.showMessageDialog(null, "Invalid data", "Invalid data", JOptionPane.ERROR_MESSAGE);
			}
		}

		private boolean validatePort(String text) {
			try {
				int port = Integer.parseInt(text);
				if (port > 0 && port < 16000)
					return true;
			} catch (Exception e) {}
			return false;
		}

		private boolean validateIP(String text) {
			String[] parts = text.split(".");
			if (parts.length != 4)
				return false;
			for (int i = 0; i < 4; i++) {
				try{
					int value = Integer.parseInt(parts[i]);
					if (value < 0 || value > 254)
						return false;
				} catch (Exception e) {
					return false;
				}
			}
			return true;
		}
	});
	tempPanel.add(saveSmtpConfig, BorderLayout.SOUTH);
	
	return tempPanel;
}
    
    private class SMTPDialog extends JDialog{
	public SMTPDialog(){
	  super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "AdaptationProfile.TypeDialog.Title" ), Dialog.ModalityType.APPLICATION_MODAL);
	  Controller.getInstance().pushWindow(this);
	  
		JPanel tempPanel = new JPanel();
		tempPanel.setLayout(new BorderLayout());
		//tempPanel.add(sendByEmailPanel, BorderLayout.CENTER);
		tempPanel.add(smtpConfig);
		this.add(tempPanel);
		
		addWindowListener( new WindowAdapter (){
			@Override
			public void windowClosed(WindowEvent e) {
				Controller.getInstance().popWindow();
				
			}
			
		});
		
		 this.setSize( new Dimension(250,200) );
			Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
			setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
			setResizable( false );
			setVisible( true );
	}
    }
    
    public class ProfileTypeDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JRadioButton scorm12;
	
	private JRadioButton scorm2004;
	
	private JRadioButton normal;
		
		
	public ProfileTypeDialog(){
	    super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "AdaptationProfile.TypeDialog.Title" ), Dialog.ModalityType.APPLICATION_MODAL);
	    Controller.getInstance().pushWindow(this);
	    // create the radio button for each profile type
	    JPanel scormPanel = new JPanel();
	    scormPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AdaptationRulesList.ScormBorder" ) ) );
	    scorm2004 = new JRadioButton(TextConstants.getText( "AdaptationRulesList.Scorm2004" ) );
	    scorm12 = new JRadioButton(TextConstants.getText( "AdaptationRulesList.Scorm12" ) );
	    normal = new JRadioButton(TextConstants.getText( "AdaptationRulesList.Normal" ) );
	    scorm2004.addActionListener(new Scorm2004RadioButtonListener());
	    scorm12.addActionListener(new Scorm12RadioButtonListener());
	    normal.addActionListener(new NormalRadioButtonListener());
	    scorm2004.setSelected(dataControl.isScorm2004());
	    scorm12.setSelected(dataControl.isScorm12());
	    normal.setSelected(!dataControl.isScorm2004()&&!dataControl.isScorm12());
	    ButtonGroup group = new ButtonGroup();
	    group.add(scorm12);
	    group.add(scorm2004);
	    group.add(normal);
	    scormPanel.setLayout(new GridLayout( 3, 0 ));
	    scormPanel.add(scorm12);
	    scormPanel.add(scorm2004);
	    scormPanel.add(normal);
	    this.add(scormPanel);
	    // create button to close the dialog
	    JButton ok = new JButton("OK");
	    ok.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
		    dispose();
		    Controller.getInstance().popWindow();
		    
		}
		
	    });
	    
	    
	    addWindowListener( new WindowAdapter (){
		@Override
		public void windowClosed(WindowEvent e) {
			Controller.getInstance().popWindow();
			
		}
		
	});
	    
	    this.setSize( new Dimension(250,300) );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setResizable( false );
		setVisible( true );
		
	}
	/**
	 * Listener for the "Scorm2004" radio button.
	 */
	private class Scorm2004RadioButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
		    dataControl.changeToScorm2004Profile();
		    typeLabel.setText(TextConstants.getText("AdaptationProfile.Scorm2004Profile"));
		    profileTypePanel.updateUI();
		}

	}
	
	/**
	 * Listener for the "Scorm12" radio button.
	 */
	private class Scorm12RadioButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
		    dataControl.changeToScorm12Profile();
		    typeLabel.setText(TextConstants.getText("AdaptationProfile.Scorm12Profile"));
		    profileTypePanel.updateUI();
		}

	}
	
	/**
	 * Listener for the "normal" radio button.
	 */
	private class NormalRadioButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
		    dataControl.changeToNormalProfile();
		    typeLabel.setText(TextConstants.getText("AdaptationProfile.NormalProfile"));
		    profileTypePanel.updateUI();
		}

	}
}
    
    /**
	 * Table model to display the scenes information.
	 */
	private class AssessmentRulesInfoTableModel extends AbstractTableModel {

		/**
		 * Required.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Array of data to display.
		 */
		private String[][] assRulesInfo;

		/**
		 * Constructor.
		 * 
		 * @param assRulesInfo
		 *            Container array of the information of the scenes
		 */
		public AssessmentRulesInfoTableModel( String[][] assRulesInfo ) {
			this.assRulesInfo = assRulesInfo;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		public int getColumnCount( ) {
			// Four columns, always
			return 3;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		public int getRowCount( ) {
			return assRulesInfo.length;
		}

		@Override
		public String getColumnName( int columnIndex ) {
			String columnName = "";

			// The first column is the scene identifier
			if( columnIndex == 0 )
				columnName = TextConstants.getText( "AssessmentRulesList.ColumnHeader0" );

			// The second one is the number of exits
			else if( columnIndex == 1 )
				columnName = TextConstants.getText( "AssessmentRulesList.ColumnHeader1" );

			// The third one is the number of item references
			else if( columnIndex == 2 )
				columnName = TextConstants.getText( "AssessmentRulesList.ColumnHeader2" );


			return columnName;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		public Object getValueAt( int rowIndex, int columnIndex ) {
			return assRulesInfo[rowIndex][columnIndex];
		}
		
		public void setAssRules(String[][] assRulesInfo){
		    this.assRulesInfo = assRulesInfo;
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
		    currentRuleDataControl.setImportance( importanceComboBox.getSelectedIndex( ) );

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
			new ConditionsDialog( currentRuleDataControl.getConditions( ) );
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
			if (arg0.getDocument( ) == conceptTextArea.getDocument( )){
			    currentRuleDataControl.setConcept( conceptTextArea.getText( ) );	
			}
			
			else if (arg0.getDocument( ) == textTextArea.getDocument( )){
			    currentRuleDataControl.setEffectText( -1,textTextArea.getText( ) );
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
			    currentRuleDataControl.setConcept( conceptTextArea.getText( ) );
			}
			
			else if (arg0.getDocument( ) == textTextArea.getDocument( )){
			    currentRuleDataControl.setEffectText(-1, textTextArea.getText( ) );
			}


		}
	}
}
