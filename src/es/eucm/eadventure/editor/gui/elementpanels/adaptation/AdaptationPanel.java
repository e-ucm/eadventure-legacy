package es.eucm.eadventure.editor.gui.elementpanels.adaptation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationRuleDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DescriptionChangeListener;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;

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
    
    private JButton delete;
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
		c.fill = GridBagConstraints.VERTICAL;
		c.weighty = 1;
		c.gridx=0;
		c.gridy=0;
		this.add(profileTypePanel,c);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 2.0;
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
		profileTypePanel = new JPanel();
		profileTypePanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), TextConstants
						.getText("AssessmentRule.ProfileType")));

		String[] options = new String[]{TextConstants.getText("AdaptationRulesList.Scorm2004"),
									  TextConstants.getText("AdaptationRulesList.Scorm12"),
									  TextConstants.getText("AdaptationRulesList.Normal")};
		JComboBox comboProfile = new JComboBox(options);
		if (dataControl.isScorm12())
			comboProfile.setSelectedIndex(1);
		else if (dataControl.isScorm2004())
			comboProfile.setSelectedIndex(0);
		else
			comboProfile.setSelectedIndex(2);
		
		comboProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox combo = ((JComboBox) e.getSource());
				informationTable.clearSelection();
				if (combo.getSelectedIndex() == 0) {
					dataControl.changeToScorm2004Profile();
				} else if (combo.getSelectedIndex() == 1) {
					dataControl.changeToScorm12Profile();
				} else if (combo.getSelectedIndex() == 2) {
					dataControl.changeToNormalProfile();
				}
			}
		});

		profileTypePanel.add(comboProfile);
    }
    
    public void createInitialState(){
    	initialStatePanel = new InitialStatePanel(dataControl,true);
    }
    
    private void createRuleListPanel(){
    	informationTable = new AdaptationRulesTable(dataControl);
		informationTable.getSelectionModel( ).addListSelectionListener( new ListSelectionListener(){
			public void valueChanged( ListSelectionEvent e ) {
			    createRulesInfoPanel();
			    if (informationTable.getSelectedRow() > -1)
			    	delete.setEnabled(true);
			    else
			    	delete.setEnabled(false);
			}
		});
		informationTable.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		//informationTable.removeEditor( );
		ruleListPanel = new JPanel( );
		ruleListPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AdaptationRulesList.ListTitle" ) ) );
		ruleListPanel.setLayout( new BorderLayout( ) );
		ruleListPanel.add( new JScrollPane( informationTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ), BorderLayout.CENTER );
		JButton add = new JButton(new ImageIcon("img/icons/addNode.png"));
		add.setContentAreaFilled( false );
		add.setMargin( new Insets(0,0,0,0) );
		add.setToolTipText( TextConstants.getText( "AdaptationProfile.AddRule" ) );
		add.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
				if (dataControl.canAddElement(Controller.ADAPTATION_RULE)&&dataControl.addElement(Controller.ADAPTATION_RULE, null)){
				   ((AdaptationRulesTable) informationTable).fireTableDataChanged();
				   informationTable.changeSelection(dataControl.getAdaptationRules().size() - 1, 0, false, false);
				}
		    }
		});
		delete = new JButton(new ImageIcon("img/icons/deleteNode.png"));
		delete.setContentAreaFilled( false );
		delete.setMargin( new Insets(0,0,0,0) );
		delete.setToolTipText( TextConstants.getText( "AdaptationProfile.DeleteRule") );
		delete.setEnabled(false);
		delete.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
				if (dataControl.canBeDeleted()&&dataControl.deleteElement(dataControl.getAdaptationRules().get(informationTable.getSelectedRow()), true)){
				   ((AdaptationRulesTable) informationTable).fireTableDataChanged();
				   informationTable.clearSelection( );
				}
		    }
		});
		
		JPanel buttonsPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		buttonsPanel.add(add, c);
		c.gridy = 2;
		buttonsPanel.add(delete, c);
		c.gridy = 1;
		c.weighty = 2.0;
		c.fill = GridBagConstraints.VERTICAL;
		buttonsPanel.add(new JFiller(), c);
		ruleListPanel.add(buttonsPanel,BorderLayout.EAST);
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
    

}
