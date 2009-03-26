package es.eucm.eadventure.editor.gui.elementpanels.assessment;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;


public class AssessmentProfilePanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;
	
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
	
	private AssessmentProfileDataControl dataControl;
	
	private JRadioButton scorm12;
	
	private JRadioButton scorm2004;
	
	private JRadioButton normal;
	

	/**
	 * Constructor.
	 * 
	 * @param assRulesListDataControl
	 *            Scenes list controller
	 */
	public AssessmentProfilePanel( AssessmentProfileDataControl assRulesListDataControl ) {
		dataControl = assRulesListDataControl;
		// Set the layout and the border
		setLayout( new GridBagLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentRulesList.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the text area for the documentation
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;

		JPanel feedbackPanel = createFeedbackPanel(assRulesListDataControl);
		add (feedbackPanel, c);
		
		// Create two check box for scorm 2004 and 1.2 options
		
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
		scormPanel.setLayout(new GridLayout( 0, 3 ));
		scormPanel.add(scorm12);
		scormPanel.add(scorm2004);
		scormPanel.add(normal);
		//set the constraints
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.weighty = 0;
		add(scormPanel,c);
		
		JTextPane informationTextPane = new JTextPane( );
		informationTextPane.setEditable( false );
		informationTextPane.setBackground( getBackground( ) );
		informationTextPane.setText( TextConstants.getText( "AssessmentRulesList.Information" ) );
		JPanel informationPanel = new JPanel( );
		informationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "GeneralText.Information" ) ) );
		informationPanel.setLayout( new BorderLayout( ) );
		informationPanel.add( informationTextPane, BorderLayout.CENTER );
		c.gridy = 2;
		add( informationPanel, c );

		// Create the table with the data
		c.gridy = 3;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		JTable informationTable = new JTable( new AssessmentRulesInfoTableModel( assRulesListDataControl.getAssessmentRulesInfo( ) ) );
		informationTable.removeEditor( );
		JPanel listPanel = new JPanel( );
		listPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentRulesList.ListTitle" ) ) );
		listPanel.setLayout( new BorderLayout( ) );
		listPanel.add( new JScrollPane( informationTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ), BorderLayout.CENTER );
		add( listPanel, c );
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
			
		}

	}
	
	
	private JPanel createFeedbackPanel(AssessmentProfileDataControl assRulesListDataControl) {
		JPanel feedbackPanel = new JPanel();
		feedbackPanel.setLayout(new GridBagLayout());
		feedbackPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentProfile.Feedback.Title" ) ) );

		GridBagConstraints c2 = new GridBagConstraints();

		c2.fill = GridBagConstraints.HORIZONTAL;
		c2.weightx = 1.0;
		c2.gridy = 0;
		
		JTextPane feedbackTextPane = new JTextPane( );
		feedbackTextPane.setEditable( false );
		feedbackTextPane.setBackground( getBackground( ) );
		feedbackTextPane.setText( TextConstants.getText( "AssessmentProfile.Feedback.Description" ) );
		feedbackPanel.add( feedbackTextPane, c2 );
		
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
		
		JPanel tempPanel = new JPanel();
		tempPanel.setLayout(new BorderLayout());
		tempPanel.add(sendByEmailPanel, BorderLayout.CENTER);
		tempPanel.add(smtpConfig, BorderLayout.SOUTH);
		c2.gridy++;
		feedbackPanel.add( tempPanel, c2);

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
	}

}
