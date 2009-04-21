package es.eucm.eadventure.editor.gui.elementpanels.adaptation;

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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.data.Described;
import es.eucm.eadventure.common.gui.TextConstants;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationRuleDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DescriptionChangeListener;
import es.eucm.eadventure.editor.gui.elementpanels.assessment.AssessmentPanel.ProfileTypeDialog;



public class AdaptationPanel extends JPanel{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Panel which contains the profiles's type
     */
    private JPanel profileTypePanel;
    
    /**
     * Panel which contains the initial state
     */
    private JPanel initialStatePanel; 
    
    /**
     * Panel which contains all the rules associated with current profile
     */
    private JPanel ruleListPanel;
    
    /**
     * Panel which contains the initial state and LMS state of selected rule
     */
    private JTabbedPane rulesInfoPanel;
    
    /**
     * Data control
     */
    private AdaptationProfileDataControl dataControl;
    
    /**
     * Table with all profile's rules
     */
    private JTable informationTable;
    
    /**
     * 
     */
    JLabel typeLabel;
    
    public AdaptationPanel(AdaptationProfileDataControl dataControl){
	this.dataControl = dataControl;
	createProfileTypePanel();
	createInitialState();
	createRuleListPanel();
	rulesInfoPanel = new JTabbedPane();
	createRulesInfoPanel();
	this.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints( );
	c.insets = new Insets( 5, 5, 5, 5 );
	c.fill = GridBagConstraints.BOTH;
	c.weightx = 1;
	c.weighty = 1;
	c.gridx=0;
	c.gridy=0;
	this.add(profileTypePanel,c);
	c.gridx = 1;
	this.add(initialStatePanel,c);
	c.gridx=0;
	c.gridwidth=2;
	c.ipady = 100;
	c.gridy++;
	this.add(ruleListPanel,c);
	c.gridy++;
	this.add(rulesInfoPanel,c);
	
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
    
    public void createInitialState(){
	initialStatePanel = new InitialStatePanel(dataControl,true);
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
    
    private void createRuleListPanel(){
	informationTable = new JTable( new AdaptationRulesInfoTableModel( dataControl.getAdaptationRulesInfo() ) );
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
		
		if (dataControl.canAddElement(Controller.ADAPTATION_RULE)&&dataControl.addElement(Controller.ADAPTATION_RULE, null)){
		   ((AdaptationRulesInfoTableModel) informationTable.getModel()).setAdaptRules(dataControl.getAdaptationRulesInfo());
		   informationTable.getSelectionModel( ).setSelectionInterval(informationTable.getRowCount()-1 ,informationTable.getRowCount()-1  );
		    informationTable.updateUI();
		    
		}
	    }
	    
	});
	JButton delete = new JButton(TextConstants.getText("AdaptationProfile.DeleteRule"));
	delete.addActionListener(new ActionListener(){

	    @Override
	    public void actionPerformed(ActionEvent e) {
		
		if (dataControl.canBeDeleted()&&dataControl.deleteElement(dataControl.getAdaptationRules().get(informationTable.getSelectedRow()), true)){
		    informationTable.clearSelection( );
		    informationTable.changeSelection(0, 1, false, false);
		    ((AdaptationRulesInfoTableModel) informationTable.getModel()).setAdaptRules(dataControl.getAdaptationRulesInfo());
		    informationTable.updateUI( );
		    
		    createRulesInfoPanel();
		}
	    }
	    
	});
	JPanel buttonsPanel = new JPanel(new GridLayout(0,2));
	buttonsPanel.add(add);
	buttonsPanel.add(delete);
	ruleListPanel.add(buttonsPanel,BorderLayout.SOUTH);
	ruleListPanel.setMinimumSize(new Dimension(0,30));
	ruleListPanel.setMaximumSize(new Dimension(0,30));
    }
    
    private void createRulesInfoPanel(){
	if (informationTable.getSelectedRow( )<0 || informationTable.getSelectedRow( )>=dataControl.getAdaptationRules().size()){
	    rulesInfoPanel.removeAll();
		JPanel empty = new JPanel();
		JLabel label = new JLabel(TextConstants.getText("AdaptationProfile.Empty"));
		empty.add(label);
		rulesInfoPanel.addTab(TextConstants.getText("AdaptationProfile.TabbedLMSState"), empty);
		rulesInfoPanel.addTab(TextConstants.getText("AdaptationProfile.TabbedInitialState"),empty);
		rulesInfoPanel.setMinimumSize(new Dimension(0,100));
	}else {
	    rulesInfoPanel.removeAll();
	    // take the current rule data control
	    AdaptationRuleDataControl adpRuleDataControl = dataControl.getAdaptationRules().get(informationTable.getSelectedRow());
	    //create the infoPanel
	    JPanel conceptPanel = new JPanel( );
	    conceptPanel.setLayout( new GridLayout( ) );
	    JTextArea descriptionTextArea = new JTextArea( adpRuleDataControl.getDescription( ), 4, 0 );
	    descriptionTextArea.setLineWrap( true );
	    descriptionTextArea.setWrapStyleWord( true );
	    descriptionTextArea.getDocument( ).addDocumentListener( new DescriptionChangeListener( descriptionTextArea, (Described) adpRuleDataControl.getContent() ) );
	    conceptPanel.add( new JScrollPane( descriptionTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
	    conceptPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AdaptationRule.Description" ) ) );
	    // Create the LMS-state panel
	    JPanel lmsPanel = new UOLPropertiesPanel( adpRuleDataControl,dataControl.isScorm12(),dataControl.isScorm2004()); 
	    
	    JPanel container = new JPanel(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints( );
	    c.fill = GridBagConstraints.BOTH;
	    c.weightx=1;
	    c.weighty=1;
	    c.ipady=-30;
	    container.add(conceptPanel,c);
	    c.ipady=0;
	    c.gridx++;
	    container.add(lmsPanel,c);
	    rulesInfoPanel.addTab(TextConstants.getText("AdaptationProfile.TabbedLMSState"), container);
	    
	    // Create the game-state panel
	    JPanel gsPanel = new GameStatePanel( adpRuleDataControl ); 
	    rulesInfoPanel.addTab(TextConstants.getText("AdaptationProfile.TabbedInitialState"),gsPanel);
	    rulesInfoPanel.setPreferredSize(new Dimension(0,250));
	    rulesInfoPanel.updateUI();

	}
	
    }
    
    	/**
	 * Table model to display the scenes information.
	 */
	public class AdaptationRulesInfoTableModel extends AbstractTableModel {

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
		public AdaptationRulesInfoTableModel( String[][] assRulesInfo ) {
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
		
		public void setAdaptRules(String[][] assRulesInfo){
		    this.assRulesInfo = assRulesInfo;
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
			   // createProfileTypePanel();
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

}
