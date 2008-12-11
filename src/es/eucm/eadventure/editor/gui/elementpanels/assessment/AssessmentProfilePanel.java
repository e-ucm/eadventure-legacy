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
import javax.swing.JCheckBox;
import javax.swing.JPanel;
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
	
	private AssessmentProfileDataControl dataControl;

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
		JPanel feedbackPanel = new JPanel();
		feedbackPanel.setLayout(new GridLayout(3,1));
		feedbackPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AssessmentProfile.Feedback.Title" ) ) );

		JTextPane feedbackTextPane = new JTextPane( );
		feedbackTextPane.setEditable( false );
		feedbackTextPane.setBackground( getBackground( ) );
		feedbackTextPane.setText( TextConstants.getText( "AssessmentProfile.Feedback.Description" ) );
		feedbackPanel.add( feedbackTextPane );
		
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
						if (dataControl.isSendByEmail())
							sendByEmailText.setText(dataControl.getEmail());
					} else {
						sendByEmailCheck.setEnabled(false);
						sendByEmailCheck.setSelected(false);
						sendByEmailText.setEnabled(false);
						sendByEmailText.setText("");
					}
				}	
				dataControl.setShowReportAtEnd(showReportAtEnd.isSelected());
			}
		});
		feedbackPanel.add( showReportAtEnd );
		
		JPanel sendByEmailPanel = new JPanel();
		sendByEmailCheck = new JCheckBox(TextConstants.getText("AssessmentProfile.Feedback.SendByEmail"));
		sendByEmailCheck.setEnabled(dataControl.isShowReportAtEnd());
		sendByEmailCheck.setSelected(dataControl.isShowReportAtEnd() && dataControl.isSendByEmail());
		sendByEmailCheck.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (sendByEmailCheck.isSelected() != dataControl.isSendByEmail()) {
					dataControl.setSendByEmail(sendByEmailCheck.isSelected());
					sendByEmailText.setEnabled(sendByEmailCheck.isSelected());
					if (sendByEmailCheck.isSelected())
						sendByEmailText.setText(dataControl.getEmail());
					else
						sendByEmailText.setText("");
					dataControl.setSendByEmail(sendByEmailCheck.isSelected());
				}	
			}
		});
		sendByEmailPanel.add(sendByEmailCheck);
		
		sendByEmailText = new JTextField(30);
		sendByEmailText.setEnabled(dataControl.isShowReportAtEnd() && dataControl.isSendByEmail());
		if (dataControl.isSendByEmail() && dataControl.isShowReportAtEnd())
			sendByEmailText.setText(dataControl.getEmail());
		sendByEmailText.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent arg0) {
			}
			public void keyReleased(KeyEvent arg0) {
			}

			public void keyTyped(KeyEvent arg0) {
				dataControl.setEmail(sendByEmailText.getText());
			}
		});
		sendByEmailPanel.add(sendByEmailText);
		
		feedbackPanel.add( sendByEmailPanel);
		add (feedbackPanel, c);
		
		
		
		JTextPane informationTextPane = new JTextPane( );
		informationTextPane.setEditable( false );
		informationTextPane.setBackground( getBackground( ) );
		informationTextPane.setText( TextConstants.getText( "AssessmentRulesList.Information" ) );
		JPanel informationPanel = new JPanel( );
		informationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "GeneralText.Information" ) ) );
		informationPanel.setLayout( new BorderLayout( ) );
		informationPanel.add( informationTextPane, BorderLayout.CENTER );
		c.gridy = 1;
		add( informationPanel, c );

		// Create the table with the data
		c.gridy = 2;
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
