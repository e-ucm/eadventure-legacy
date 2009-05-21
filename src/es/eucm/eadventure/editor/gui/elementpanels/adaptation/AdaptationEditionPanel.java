package es.eucm.eadventure.editor.gui.elementpanels.adaptation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationRuleDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.control.tools.adaptation.AddRuleTool;
import es.eucm.eadventure.editor.control.tools.adaptation.DeleteRuleTool;
import es.eucm.eadventure.editor.control.tools.adaptation.DuplicateRuleTool;
import es.eucm.eadventure.editor.gui.DataControlsPanel;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.editor.gui.elementpanels.AuxTabPanel;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;
import es.eucm.eadventure.editor.gui.elementpanels.general.TableScrollPane;
import es.eucm.eadventure.editor.gui.elementpanels.scene.BarriersListPanel;

public class AdaptationEditionPanel extends JPanel implements Updateable,DataControlsPanel{

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
    private InitialStatePanel initialStatePanel; 
    
    /**
     * Panel which contains all the rules associated with current profile
     */
    private JPanel ruleListPanel;
    
    /**
     * Panel which contains the initial state and LMS state of selected rule
     */
   // private AuxTabPanel rulesInfoPanel;
    private JTabbedPane rulesInfoPanel;
    
    /**
     * Button to duplicate selected rule
     */
    private JButton duplicate;
    
    /**
     * Button to delete selected rule
     */
    private JButton delete;
    
    /**
     * Data control
     */
    private AdaptationProfileDataControl dataControl;
    
    /**
     * Last used rule
     */
    private AdaptationRuleDataControl lastRule;
    
    /**
     * Table with all profile's rules
     */
    private JTable informationTable;
    
    /**
     * Combo box for adaptation profile type
     */
    private JComboBox comboProfile;
    
    
    /**
     * 
     */
    JLabel typeLabel;
    
    public AdaptationEditionPanel(AdaptationProfileDataControl dataControl){
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
		 comboProfile = new JComboBox(options);
		if (dataControl.isScorm12())
			comboProfile.setSelectedIndex(1);
		else if (dataControl.isScorm2004())
			comboProfile.setSelectedIndex(0);
		else
			comboProfile.setSelectedIndex(2);
		
		comboProfile.addActionListener(new ComboListener(comboProfile.getSelectedIndex()));

		profileTypePanel.add(comboProfile);
    }
    
    public void createInitialState(){
    	initialStatePanel = new InitialStatePanel(dataControl,true);
    }
    
    private class ComboListener implements ActionListener{

	private int pastSelection;
	
	public ComboListener(int pastSelection){
	    this.pastSelection = pastSelection;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	    JComboBox combo = ((JComboBox) e.getSource());
	    if (pastSelection!=combo.getSelectedIndex() ){
	    	informationTable.clearSelection();
		if (combo.getSelectedIndex() == 0) {
			dataControl.changeToScorm2004Profile();
		} else if (combo.getSelectedIndex() == 1) {
			dataControl.changeToScorm12Profile();
		} else if (combo.getSelectedIndex() == 2) {
			dataControl.changeToNormalProfile();
		}
		pastSelection = combo.getSelectedIndex();
	    }
	    
	}
	
    }
    
    private void createRuleListPanel(){
    	informationTable = new AdaptationRulesTable(dataControl);
		informationTable.getSelectionModel( ).addListSelectionListener( new ListSelectionListener(){
			public void valueChanged( ListSelectionEvent e ) {
			   
			   
			    if (informationTable.getSelectedRow() > -1){
			    	delete.setEnabled(true);
			    	duplicate.setEnabled(true);
			    	lastRule = dataControl.getAdaptationRules().get(informationTable.getSelectedRow());
			    }else{
			    	delete.setEnabled(false);
			    	duplicate.setEnabled(false);
			    }
			    createRulesInfoPanel();
			}
		});
		//informationTable.removeEditor( );
		ruleListPanel = new JPanel( );
		ruleListPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AdaptationRulesList.ListTitle" ) ) );
		ruleListPanel.setLayout( new BorderLayout( ) );
		ruleListPanel.add( new TableScrollPane( informationTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ), BorderLayout.CENTER );
		
		
		JButton add = new JButton(new ImageIcon("img/icons/addNode.png"));
		add.setContentAreaFilled( false );
		add.setMargin( new Insets(0,0,0,0) );
		add.setBorder(BorderFactory.createEmptyBorder());
		add.setToolTipText( TextConstants.getText( "AdaptationProfile.AddRule" ) );
		add.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
				if (Controller.getInstance().addTool(new AddRuleTool(dataControl,Controller.ADAPTATION_RULE))){
				   ((AdaptationRulesTable) informationTable).fireTableDataChanged();
				   informationTable.changeSelection(dataControl.getAdaptationRules().size() - 1, 0, false, false);
				}
		    }
		});
		
		duplicate = new JButton(new ImageIcon("img/icons/duplicateNode.png"));
		duplicate.setContentAreaFilled( false );
		duplicate.setMargin( new Insets(0,0,0,0) );
		duplicate.setBorder(BorderFactory.createEmptyBorder());
		duplicate.setToolTipText( TextConstants.getText( "AdaptationProfile.Duplicate" ) );
		duplicate.addActionListener(new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
			    if (Controller.getInstance().addTool(new DuplicateRuleTool(dataControl,Controller.ADAPTATION_RULE,informationTable.getSelectedRow()))) {
				    ((AdaptationRulesTable) informationTable).fireTableDataChanged();
					   informationTable.changeSelection(dataControl.getAdaptationRules().size() - 1, 0, false, false);
				}
			}
		});	
		duplicate.setEnabled(false);
		
		
		delete = new JButton(new ImageIcon("img/icons/deleteNode.png"));
		delete.setContentAreaFilled( false );
		delete.setMargin( new Insets(0,0,0,0) );
		delete.setBorder(BorderFactory.createEmptyBorder());
		delete.setToolTipText( TextConstants.getText( "AdaptationProfile.DeleteRule") );
		delete.setEnabled(false);
		delete.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
				if (Controller.getInstance().addTool(new DeleteRuleTool(dataControl,Controller.ADAPTATION_RULE,informationTable.getSelectedRow()))){
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
		c.gridy=1;
		buttonsPanel.add(duplicate, c);
		c.gridy = 3;
		buttonsPanel.add(delete, c);
		c.gridy = 2;
		c.weighty = 2.0;
		c.fill = GridBagConstraints.VERTICAL;
		buttonsPanel.add(new JFiller(), c);
		ruleListPanel.add(buttonsPanel,BorderLayout.EAST);
		ruleListPanel.setMinimumSize(new Dimension(0,30));
		ruleListPanel.setMaximumSize(new Dimension(0,30));
    }
    
 
    
    private void createRulesInfoPanel(){
		if (informationTable.getSelectedRow( )<0 || informationTable.getSelectedRow( )>=dataControl.getAdaptationRules().size()){
		//int i = informationTable.getRowCount();   
		//if (informationTable.getRowCount()<=0){
			rulesInfoPanel.removeAll();
			JPanel empty = new JPanel();
			JLabel label = new JLabel(TextConstants.getText("AdaptationProfile.Empty"));
			empty.add(label);
			//rulesInfoPanel.addTab(/*TextConstants.getText("AdaptationProfile.TabbedLMSState"),*/ empty);
			//rulesInfoPanel.addTab(TextConstants.getText("AdaptationProfile.TabbedInitialState"),empty);
			rulesInfoPanel.add(empty);
			//rulesInfoPanel.setSelectedIndex(0);
			rulesInfoPanel.setMinimumSize(new Dimension(0,100));
		}else {
		    rulesInfoPanel.removeAll();
		    // take the current rule data control
		    //AdaptationRuleDataControl adpRuleDataControl = dataControl.getAdaptationRules().get(lastRule); 
		   // rulesInfoPanel.addTab(new UOLPropertiesPanelTab( lastRule,dataControl.isScorm12(),dataControl.isScorm2004()));
		    
		    JPanel Uol =new UOLPropertiesPanel( lastRule,dataControl.isScorm12(),dataControl.isScorm2004());
		    rulesInfoPanel.addTab(TextConstants.getText("AdaptationProfile.TabbedLMSState"),Uol);
		    
		    
		    JPanel gameStatePanel = new GameStatePanel( lastRule);
		    rulesInfoPanel.addTab(TextConstants.getText("AdaptationProfile.TabbedInitialState"),gameStatePanel);
		    
		    // Create the game-state panel
		    //rulesInfoPanel.addTab(new GameStatePanelTab(lastRule));
		    rulesInfoPanel.setPreferredSize(new Dimension(0,250));
		   // rulesInfoPanel.setSelectedIndex(0);
		    rulesInfoPanel.updateUI();
	
		}
    }
    
    
    
    
    private class GameStatePanelTab extends PanelTab {
	private AdaptationRuleDataControl dataControl;
	
	public GameStatePanelTab(AdaptationRuleDataControl adpRuleDataControl) {
		super(TextConstants.getText("AdaptationProfile.TabbedInitialState"), adpRuleDataControl);
		//this.setHelpPath("scenes/Scene_Barriers.html");
		this.dataControl = adpRuleDataControl;
	}

	@Override
	protected JComponent getTabComponent() {
		return new GameStatePanel( dataControl ); 
	}
    }
    
    private class UOLPropertiesPanelTab extends PanelTab {
	private AdaptationRuleDataControl dataControl;
	private  boolean isScorm12;
	private boolean isScorm2004;
	
	public UOLPropertiesPanelTab(AdaptationRuleDataControl dataControl, boolean isScorm12, boolean isScorm2004) {
		super(TextConstants.getText("AdaptationProfile.TabbedLMSState"), dataControl);
		//this.setHelpPath("scenes/Scene_Barriers.html");
		this.dataControl = dataControl;
		this.isScorm12 = isScorm12;
		this.isScorm2004 = isScorm2004;
		}

	@Override
	protected JComponent getTabComponent() {
		return new UOLPropertiesPanel( dataControl,isScorm12,isScorm2004);
	}
    }
    
    
    

    public boolean updateFields() {
	int selected = informationTable.getSelectedRow();
	int items = informationTable.getRowCount();
	int selectedTab = rulesInfoPanel.getSelectedIndex();
	if (rulesInfoPanel!=null && rulesInfoPanel instanceof Updateable)
	    ((Updateable) rulesInfoPanel).updateFields();
	((AbstractTableModel) informationTable.getModel()).fireTableDataChanged();
	// update combo box
	if (dataControl.isScorm12())
		comboProfile.setSelectedIndex(1);
	else if (dataControl.isScorm2004())
		comboProfile.setSelectedIndex(0);
	else
		comboProfile.setSelectedIndex(2);
	
	if (items == informationTable.getRowCount()) {
		if (selected != -1) {
		    if (selected>=items)
			selected=items-1;
		    informationTable.changeSelection(selected, 0, false, false);
			if (informationTable.getEditorComponent() != null)
			    informationTable.editCellAt(selected, informationTable.getEditingColumn());
			
		} if (selectedTab!=-1)
		    rulesInfoPanel.setSelectedIndex(selectedTab);
	}
	
	if (initialStatePanel instanceof Updateable)
	    ((Updateable)initialStatePanel).updateFields();
	
	return true;
    }

    public void setSelectedItem(List<Searchable> path) {
	if (path.size() > 0) {
	    for (int i = 0 ; i < dataControl.getDataControls().size(); i++) {
			if (dataControl.getDataControls().get(i) == path.get(path.size() -1))
			    informationTable.changeSelection(i, i, false, false);
		}
	    }
    }
    

}
