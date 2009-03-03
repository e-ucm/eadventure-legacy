package es.eucm.eadventure.editor.gui.elementpanels.adaptation;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ScenesListDataControl;

public class AdaptationRulesListPanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	private JCheckBox scorm12;
	
	private JCheckBox scorm2004;	
	
	private AdaptationProfileDataControl rulesListDataControl;
	/**
	 * Constructor.
	 * 
	 * @param adpRulesListDataControl
	 *            Scenes list controller
	 */
	public AdaptationRulesListPanel( AdaptationProfileDataControl adpRulesListDataControl ) {
		
		this.rulesListDataControl = adpRulesListDataControl;
		// Set the layout and the border
		setLayout( new GridBagLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AdaptationRulesList.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the text area for the documentation
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		JTextPane informationTextPane = new JTextPane( );
		informationTextPane.setEditable( false );
		informationTextPane.setBackground( getBackground( ) );
		informationTextPane.setText( TextConstants.getText( "AdaptationRulesList.Information" ) );
		JPanel informationPanel = new JPanel( );
		informationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "GeneralText.Information" ) ) );
		informationPanel.setLayout( new BorderLayout( ) );
		informationPanel.add( informationTextPane, BorderLayout.CENTER );
		add( informationPanel, c );
		
		// Create two check box for scorm 2004 and 1.2 options
		
		JPanel scormPanel = new JPanel();
		scormPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AdaptationRulesList.ScormBorder" ) ) );
		scorm2004 = new JCheckBox(TextConstants.getText( "AdaptationRulesList.Scorm2004" ) );
		scorm12 = new JCheckBox(TextConstants.getText( "AdaptationRulesList.Scorm12" ) );
		scorm2004.addActionListener(new Scorm2004CheckBoxListener());
		scorm12.addActionListener(new Scorm12CheckBoxListener());
		scorm2004.setSelected(rulesListDataControl.isScorm2004());
		scorm12.setSelected(rulesListDataControl.isScorm12());
		scormPanel.setLayout(new GridLayout( 0, 2 ));
		scormPanel.add(scorm12);
		scormPanel.add(scorm2004);
		//set the constraints
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.weighty = 0;
		add(scormPanel,c);
		
		//Add the initial state data
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		add (new InitialStatePanel(adpRulesListDataControl), c);
		
		// Create the table with the data
		c.gridy = 3;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		JTable informationTable = new JTable( new AssessmentRulesInfoTableModel( adpRulesListDataControl.getAdaptationRulesInfo( ) ) );
		informationTable.removeEditor( );
		JPanel listPanel = new JPanel( );
		listPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AdaptationRulesList.ListTitle" ) ) );
		listPanel.setLayout( new BorderLayout( ) );
		listPanel.add( new JScrollPane( informationTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ), BorderLayout.CENTER );
		add( listPanel, c );
	}

	

	/**
	 * Listener for the "Scorm2004" check box.
	 */
	private class Scorm2004CheckBoxListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			rulesListDataControl.setScorm2004(scorm2004.isSelected());
			if (rulesListDataControl.isScorm12()){
				rulesListDataControl.setScorm12(false);
				scorm12.setSelected(false);
			}
		}

	}
	
	/**
	 * Listener for the "Scorm12" check box.
	 */
	private class Scorm12CheckBoxListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			rulesListDataControl.setScorm12(scorm12.isSelected());
			if (rulesListDataControl.isScorm2004()){
				rulesListDataControl.setScorm2004(false);
				scorm2004.setSelected(false);
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
			return 4;
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
				columnName = TextConstants.getText( "AdaptationRulesList.ColumnHeader0" );

			// The second one is the number of exits
			else if( columnIndex == 1 )
				columnName = TextConstants.getText( "AdaptationRulesList.ColumnHeader1" );

			// The third one is the number of item references
			else if( columnIndex == 2 )
				columnName = TextConstants.getText( "AdaptationRulesList.ColumnHeader2" );
			
			// The third one is the number of item references
			else if( columnIndex == 3 )
				columnName = TextConstants.getText( "AdaptationRulesList.ColumnHeader3" );



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
