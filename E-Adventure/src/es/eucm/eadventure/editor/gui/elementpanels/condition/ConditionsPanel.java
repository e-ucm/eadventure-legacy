package es.eucm.eadventure.editor.gui.elementpanels.condition;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager2;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.SpinnerNumberModel;

import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.conditions.FlagCondition;
import es.eucm.eadventure.common.data.chapter.conditions.GlobalStateCondition;
import es.eucm.eadventure.common.data.chapter.conditions.VarCondition;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.controllers.ConditionsController.ConditionContextProperty;
import es.eucm.eadventure.editor.control.controllers.ConditionsController.ConditionCustomMessage;
import es.eucm.eadventure.editor.control.controllers.ConditionsController.ConditionOwner;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.editdialogs.ConditionDialog;

public class ConditionsPanel extends JPanel implements Updateable, ConditionsPanelController{

	/*
	 * Colors
	 */
	private static Color topPanelLineColor = Color.black;
	private static Color centralPanelLineColor = Color.black;
	private static Color buttonsPanelLineColor = Color.black;
	
	private ConditionsController conditionsController;
	
	private List<EditablePanel> panels;
	
	private EditablePanel selectedPanel=null;
	
	private JPanel topPanel;
	private JEditorPane textPane;
	
	private JPanel centralPanel;
	
	private JPanel buttonsPanel;
	
	private JButton addConditionButton;
	
	private JButton okButton;
	/**
	 * Constructor.
	 * 
	 * @param conditionController
	 *            Controller for the conditions
	 * @param keyListener 
	 */
	public ConditionsPanel( ConditionsController conditionsController ) {
		this.setLayout(new BorderLayout());
		this.conditionsController = conditionsController;
		
		topPanel = createTopPanel();
		this.add(topPanel, BorderLayout.NORTH);
		
		createButtonsPanel();
		this.add(buttonsPanel, BorderLayout.SOUTH);
		
		createCentralPanel();
		
	}

	public void update(){
		textPane.setContentType("text/html");
		textPane.setText(getHTMLTopText());
		updateCentralPanel();
	}
	
	private JPanel createTopPanel(){
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.setBorder(BorderFactory.createLineBorder(topPanelLineColor));
		
		textPane = new JEditorPane("text/html", getHTMLTopText());
		textPane.setEditable(false);
		textPane.setOpaque(false);
		
		topPanel.add(textPane, BorderLayout.CENTER);
		return topPanel;
	}
	
	private String getHTMLTopText(){
		String html =
			"<html>\n"+
			"\t<head>\n"+
			"\t</head>\n"+
			"\t<body>\n"+
			"\t\t<p align=center>";
		HashMap<String,ConditionContextProperty> context = conditionsController.getContext();
		if ( context.containsKey(ConditionsController.CONDITION_OWNER) ){
			ConditionOwner owner= (ConditionOwner)context.get(ConditionsController.CONDITION_OWNER);
			
			if ( !context.containsKey(ConditionsController.CONDITION_CUSTOM_MESSAGE) ){
				
				String ownerTypeString = TextConstants.getElementName(owner.getOwnerType());
				html +=TextConstants.getText("Conditions.Context.Sentence1")+"<i>"+ownerTypeString+"</i>"
				+" <b>\""+owner.getOwnerName()+"\" </b>";
	
				ConditionOwner parent = owner.getParent();
				if (parent!=null)
					html+=" (";
				while (parent!=null){
					html+= TextConstants.getElementName(parent.getOwnerType())+" "+parent.getOwnerName();
					parent = parent.getParent();
					if (parent!=null)
						html+=", ";
					else
						html+=")";
				}
				
	 			
				if (!conditionsController.isEmpty()){
					html+=TextConstants.getText("Conditions.Context.Sentence2a");
				}
				else {
					html+=TextConstants.getText("Conditions.Context.Sentence2b");
				}
			} else {
				ConditionCustomMessage cMessage = (ConditionCustomMessage)context.get(ConditionsController.CONDITION_CUSTOM_MESSAGE);
				if (!conditionsController.isEmpty()){
					html+=cMessage.getFormattedSentence(owner);
				}
				else {
					html+=cMessage.getNoConditionFormattedSentence(owner);
				}
			}
			
		} else {
			html+= TextConstants.getText("Conditions.Context.NoOwner");
		}
		html+="\n\t\t</p>\n";
		html+="\t</body>\n";
		html+="</html>";
		return html;
	}
	
	private void createButtonsPanel(){
		buttonsPanel = new JPanel();
		addConditionButton = new JButton(new ImageIcon("img/icons/addNode.png"));
		addConditionButton.setText(TextConstants.getText("Conditions.AddCondition"));
		addConditionButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				ConditionsPanel.this.addCondition();
			}
			
		});
		okButton = new JButton(TextConstants.getText("GeneralText.OK"));
		buttonsPanel.add(addConditionButton);
		buttonsPanel.add(okButton);
		buttonsPanel.setBorder(BorderFactory.createLineBorder(buttonsPanelLineColor));
	}
	
	private JPanel createCentralPanel(){
		centralPanel = new JPanel();
		centralPanel.setBorder(BorderFactory.createLineBorder(centralPanelLineColor));
		centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.PAGE_AXIS));
		JScrollPane scroll = new JScrollPane (centralPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(scroll, BorderLayout.CENTER);
		
		updateCentralPanel();
		
		return centralPanel;
	}
	
	private void updateCentralPanel(){
		centralPanel.removeAll();
		panels = new ArrayList<EditablePanel>();
		for ( int i=0; i<conditionsController.getConditionsCount(); i++ ){

			if (i>0){
				EvalFunctionPanel labelPanel = new EvalFunctionPanel(this, i-1, ConditionsController.INDEX_NOT_USED, EvalFunctionPanel.AND);
				JPanel evalFunctionPanel = new JPanel();
				evalFunctionPanel.add(labelPanel);
				panels.add(labelPanel);
				centralPanel.add (evalFunctionPanel);
			}
			
			EditablePanel subPanel = null;
			subPanel = new CompositeConditionPanel(this, i);//subPanel = new ConditionPanel(1,null);//getConditionPanel ( wrapper.getEitherBlock(), false);
			//subPanel.setBorder(new LineBorder( Color.DARK_GRAY, 1, true ));
			//subPanel.setBorder( new CurvedBorder(50, Color.DARK_GRAY) );
			panels.add(subPanel);
			centralPanel.add ( subPanel );
		}
		
		centralPanel.repaint();
	}
	
	@Override
	public boolean updateFields() {
		return false;
	}

		private class SpecialLayout implements LayoutManager2{

		public static final int GAP = 3;
		
		private Component comp;
		
		public SpecialLayout(Component comp){
			this.comp = comp;
		}
		
		@Override
		public void addLayoutComponent(Component comp, Object constraints) {
			this.comp = comp;
		}

		@Override
		public float getLayoutAlignmentX(Container target) {
			return 0;
		}

		@Override
		public float getLayoutAlignmentY(Container target) {
			return 0;
		}

		@Override
		public void invalidateLayout(Container target) {
		}

		@Override
		public Dimension maximumLayoutSize(Container target) {
			return target.getSize();
		}

		@Override
		public void addLayoutComponent(String name, Component comp) {
			
		}

		@Override
		public void layoutContainer(Container parent) {
			if (comp!=null){
				int maxWidth = parent.getSize().width;
				int maxHeight = parent.getSize().height;
				
				int compWidth = comp.getPreferredSize().width;
				int compHeight = comp.getPreferredSize().height;
				
				int x = maxWidth-GAP-compWidth;
				int y = maxHeight-GAP-compHeight;
				comp.setBounds(x, y, compWidth, compHeight);
			}
		}

		@Override
		public Dimension minimumLayoutSize(Container parent) {
			return comp.getMinimumSize();
		}

		@Override
		public Dimension preferredLayoutSize(Container parent) {
			return parent.getPreferredSize();
		}

		@Override
		public void removeLayoutComponent(Component comp) {
		}
		
	}

		@Override
		public void evalEditablePanelSelectionEvent(EditablePanel source,
				int oldState, int newState) {
			if (newState != EditablePanel.NO_SELECTED && selectedPanel!=null && selectedPanel!=source)
				selectedPanel.deselect();
			
			if (newState != EditablePanel.NO_SELECTED)
				selectedPanel = source;
		}

		@Override
		public void evalFunctionChanged(EvalFunctionPanel source, int index1,
				int index2, int oldValue, int newValue) {
			if (this.conditionsController.setEvalFunction(index1, index2, newValue)){
				update();
			}
		}

		@Override
		public HashMap<String, String> getCondition(int index1, int index2) {
			return conditionsController.getCondition(index1, index2);
		}

		@Override
		public int getConditionCount(int index1) {
			return conditionsController.getConditionCount(index1);
		}

		@Override
		public void addCondition(int index1, int index2 ) {
			// Display the dialog to add a new condition
			ConditionDialog conditionDialog = new ConditionDialog( TextConstants.getText( "Conditions.AddCondition" ) );

			// If the data was approved
			if( conditionDialog.wasPressedOKButton( ) ) {
				// Set the new values and update the table
				if (conditionsController.addCondition( index1, index2, conditionDialog.getSelectedType(), conditionDialog.getSelectedId( ), conditionDialog.getSelectedState( ), conditionDialog.getSelectedValue( ) )){
					update();
				}
			}
		}

		@Override
		public void addCondition() {
			addCondition (conditionsController.getConditionsCount(), ConditionsController.INDEX_NOT_USED);
		}

		@Override
		public void deleteCondition(int index1, int index2) {
			if (conditionsController.deleteCondition(index1, index2)){
				update();
			}
		}

		@Override
		public void duplicateCondition(int index1, int index2) {
			if (conditionsController.duplicateCondition(index1, index2)){
				update();
			}
			
		}

		@Override
		public void editCondition(int index1, int index2) {
			// Take the actual values of the condition, and display the editing dialog
			//String stateValue = conditionsTable.getValueAt( selectedCondition, 0 ).toString( );
			//String flagValue = conditionsTable.getValueAt( selectedCondition, 1 ).toString( );
			HashMap<String, String> properties = getCondition(index1, index2);
			String defaultId = properties.get(ConditionsPanelController.CONDITION_ID);
			String defaultFlag = properties.get(ConditionsPanelController.CONDITION_ID);
			String defaultVar = properties.get(ConditionsPanelController.CONDITION_ID);
			String defaultState = properties.get(ConditionsPanelController.CONDITION_STATE);
			String defaultValue = properties.get(ConditionsPanelController.CONDITION_VALUE);
			String defaultType = properties.get(ConditionsPanelController.CONDITION_TYPE);

			if (defaultType.equals(ConditionsPanelController.CONDITION_TYPE_FLAG)){
				defaultVar = defaultId = null;
			}
			else if (defaultType.equals(ConditionsPanelController.CONDITION_TYPE_VAR)){
				defaultFlag = defaultId = null;
			}
			else if (defaultType.equals(ConditionsPanelController.CONDITION_TYPE_GS)){
				defaultFlag = defaultVar = null;
			}
			ConditionDialog conditionDialog = new ConditionDialog( defaultType, TextConstants.getText( "Conditions.EditCondition" ), defaultState, defaultFlag, defaultVar, defaultId, defaultValue, conditionsController.getContext() );

			// If the data was approved
			if( conditionDialog.wasPressedOKButton( ) ) {
				
				// Set the new values
				/*conditionsController.setConditionType( tableIndex, selectedCondition, conditionDialog.getSelectedType( ) );
				conditionsController.setConditionState( tableIndex, selectedCondition, conditionDialog.getSelectedState( ) );
				conditionsController.setConditionId( tableIndex, selectedCondition, conditionDialog.getSelectedId( ) );
				conditionsController.setConditionValue( tableIndex, selectedCondition, conditionDialog.getSelectedValue( ) );*/
				properties.clear();
				properties.put(ConditionsPanelController.CONDITION_ID, conditionDialog.getSelectedId());
				properties.put(ConditionsPanelController.CONDITION_STATE, conditionDialog.getSelectedState());
				properties.put(ConditionsPanelController.CONDITION_VALUE, conditionDialog.getSelectedValue());
				properties.put(ConditionsPanelController.CONDITION_TYPE, conditionDialog.getSelectedType());
				if (conditionsController.setCondition(index1, index2, properties))
					// Update the table
					update();
			}
	}
}