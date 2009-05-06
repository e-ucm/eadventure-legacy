package es.eucm.eadventure.editor.gui.elementpanels.condition;

import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import es.eucm.eadventure.common.data.chapter.conditions.FlagCondition;
import es.eucm.eadventure.common.data.chapter.conditions.GlobalStateCondition;
import es.eucm.eadventure.common.data.chapter.conditions.VarCondition;
import es.eucm.eadventure.common.gui.TextConstants;

public class AtomicConditionPanel extends EditablePanel{

		/*
		 * Fields for displaying. The structure is the following: ICON Subject(id) Verb(Action) directComplement (Value)?
		 */
		private JLabel iconLabel;
		private List<Component> renderComponents;
		
		/*
		 * Buttons panel
		 */
		private ButtonsPanel buttonsPanel;
		private JButton deleteButton;
		private JButton duplicateButton;
		private JButton editButton;
		
		private int index2;
		
		/*
		 * Elements for alpha effect
		 */
		private AlphaEffectTimer timer;
		private AlphaComposite alphaComposite;//=AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
		private float alpha = 0;
		
		
		public AtomicConditionPanel ( ConditionsPanelController controller, int i, int index2){
			super (controller, i);
			this.index2 = index2;
			
			// Create timer
			timer = new AlphaEffectTimer();
			
			// Create buttons Panel
			buttonsPanel = new ButtonsPanel();
			
			// Condition properties
			HashMap<String,String> properties = controller.getCondition(i, index2);
			String type = properties.get(ConditionsPanelController.CONDITION_TYPE);
			String id = properties.get(ConditionsPanelController.CONDITION_ID);
			String value = properties.get(ConditionsPanelController.CONDITION_VALUE);
			String state = properties.get(ConditionsPanelController.CONDITION_STATE);
			
			// Create rendering elements
			iconLabel = new JLabel(getIcon (type));
			
			renderComponents = new ArrayList<Component>();
			Component subjectRender = getSubjectRender(id);
			if (subjectRender!=null)
				renderComponents.add(subjectRender);
			Component verbRender = getVerbRender(type, state);
			if (verbRender!=null)
				renderComponents.add(verbRender);
			Component directComplementRender = getDirectComplementRender(type, value);
			if (directComplementRender!=null)
				renderComponents.add(directComplementRender);
			
			setState(NO_SELECTED);
		}

		@Override
		protected void addComponents(int newState) {
			
			// Check timer update
			if (newState!=NO_SELECTED){
				timer.start();
			} else {
				if (timer!=null&&timer.isRunning())
					timer.stop();
			}
			
			// Add items
			add (iconLabel);
			for (Component component: renderComponents)
				add (component);

			// Add buttonsPanel
			add (buttonsPanel);
			buttonsPanel.setVisible(state!=NO_SELECTED);
			if (state!=NO_SELECTED)
				buttonsPanel.repaint();	
		}
		

		
		
	private Icon[] getIcons (){
		return new Icon[]{new ImageIcon ( "img/icons/flag16.png"),
				new ImageIcon ( "img/icons/var16.png"),
				new ImageIcon ( "img/icons/group16.png")};
	}

	private Icon getIcon(String type){
		Icon icon = null;
		if ( type.equals(ConditionsPanelController.CONDITION_TYPE_FLAG) ){
			icon = new ImageIcon ( "img/icons/flag16.png");
		} else if ( type.equals(ConditionsPanelController.CONDITION_TYPE_VAR) ){
			icon = new ImageIcon ( "img/icons/var16.png");
		} else if ( type.equals(ConditionsPanelController.CONDITION_TYPE_GS) ){
			icon = new ImageIcon ( "img/icons/group16.png");
		}
		return icon;
	}
	
	private Component getSubjectRender(String id){
		JLabel label1 = new JLabel(id);
		return label1;
	}
	
	/*private Component getSubjectEditor(Condition condition){
		if (condition.getType() == Condition.FLAG_CONDITION){
			String[] flags = new String[]{"Flag1", "Flag2", "Flag3", "Flag4"};
			JComboBox ids = new JComboBox(Controller.getInstance( ).getVarFlagSummary( ).getFlags()flags);
			ids.setSelectedItem(condition.getId());
			return ids;
		}
		else if (condition.getType() == Condition.VAR_CONDITION){
			String[] vars = new String[]{"Var1", "Var2", "Var3", "Var4"};
			JComboBox ids = new JComboBox(Controller.getInstance( ).getVarFlagSummary( ).getVars()vars);
			ids.setSelectedItem(condition.getId());
			return ids;
		}
		else if (condition.getType() == Condition.GLOBAL_STATE_CONDITION){
			String[] gs = new String[]{"GS1", "GS2", "GS3", "GS4"};
			JComboBox ids = new JComboBox(gsController.getInstance( ).getIdentifierSummary().getGlobalStatesIds());
			ids.setSelectedItem(condition.getId());
		}
		return null;
	}*/


	/**
	 * 
	 * @param state
	 * @return
	 */
	private Component getVerbRender( String type, String state ){
	if ( type.equals(ConditionsPanelController.CONDITION_TYPE_FLAG) ){
		return new JLabel(TextConstants.getText("GeneralText.Is.Singular"));
	}
	
	else if ( type.equals(ConditionsPanelController.CONDITION_TYPE_VAR) ){
		if ( state.equals(Integer.toString(VarCondition.VAR_EQUALS)) ){
			return new JLabel("=");
		}
		else if ( state.equals(Integer.toString(VarCondition.VAR_GREATER_EQUALS_THAN)) ){
			return new JLabel(">=");
		}
		else if ( state.equals(Integer.toString(VarCondition.VAR_GREATER_THAN)) ){
			return new JLabel(">");
		}
		else if ( state.equals(Integer.toString(VarCondition.VAR_LESS_EQUALS_THAN)) ){
			return new JLabel("<=");
		}
		else if ( state.equals(Integer.toString(VarCondition.VAR_LESS_THAN)) ){
			return new JLabel("<");
		}
	}
	
	else if ( type.equals(ConditionsPanelController.CONDITION_TYPE_GS) ){
		if (state.equals(Integer.toString(GlobalStateCondition.GS_SATISFIED))){
			return new JLabel(TextConstants.getText("Conditions.ConditionGroup.Satisfied"));
		}
		
		else if (state.equals(Integer.toString(GlobalStateCondition.GS_NOT_SATISFIED))){
			return new JLabel(TextConstants.getText("Conditions.ConditionGroup.NotSatisfied"));
		}
	}

	return null;
}

	/**
	 * 
	 * @param state
	 * @return
	 */
	/*private Component getVerbEditor( Condition condition ){
		if ( condition.getType() == Condition.FLAG_CONDITION ){
			return new JLabel(TextConstants.getText("GeneralText.Is.Singular"));
		}
		
		else if ( condition.getType() == Condition.VAR_CONDITION ){
			VarCondition varCondition = (VarCondition)condition;
			JComboBox varBox = new JComboBox (new String[]{"=",">",">=","<","<="});
			if ( varCondition.getState() == VarCondition.VAR_EQUALS ){
				varBox.setSelectedIndex(0);
			}
			else if ( varCondition.getState() == VarCondition.VAR_GREATER_EQUALS_THAN ){
				varBox.setSelectedIndex(2);
			}
			else if ( varCondition.getState() == VarCondition.VAR_GREATER_THAN ){
				varBox.setSelectedIndex(1);
			}
			else if ( varCondition.getState() == VarCondition.VAR_LESS_EQUALS_THAN ){
				varBox.setSelectedIndex(4);
			}
			else if ( varCondition.getState() == VarCondition.VAR_LESS_THAN ){
				varBox.setSelectedIndex(3);
			}
			return varBox;
		}
		
		else if ( condition.getType() == Condition.GLOBAL_STATE_CONDITION ){
			GlobalStateCondition gsCondition = (GlobalStateCondition)condition;
			JComboBox gsCombo = new JComboBox(new String[]{TextConstants.getText("Conditions.ConditionGroup.Satisfied"),
					TextConstants.getText("Conditions.ConditionGroup.NotSatisfied")});
			if (gsCondition.getState() == GlobalStateCondition.GS_SATISFIED){
				gsCombo.setSelectedIndex(0);
			}
			
			else if (gsCondition.getState() == GlobalStateCondition.GS_NOT_SATISFIED){
				gsCombo.setSelectedIndex(1);
			}
			return gsCombo;
		}
	
		return null;
	}*/
	
	/**
	 * 
	 * @param state
	 * @return
	 */
	private Component getDirectComplementRender( String type, String value ){
		if ( type.equals(ConditionsPanelController.CONDITION_TYPE_FLAG) ){
			if ( value.equals(Integer.toString(FlagCondition.FLAG_ACTIVE)) ){
				return new JLabel(TextConstants.getText("Conditions.Flag.Active"));
			}
			else if ( value.equals(Integer.toString(FlagCondition.FLAG_INACTIVE)) ){
				return new JLabel(TextConstants.getText("Conditions.Flag.Inactive"));
			}
		}
		
		else if ( type.equals(ConditionsPanelController.CONDITION_TYPE_VAR) ){
			return new JLabel(value);
		}
		
		else if ( type.equals(ConditionsPanelController.CONDITION_TYPE_GS)){
			return null;
		}
	
		return null;
	}
	
	/**
	 * 
	 * @param state
	 * @return
	 */
	/*private Component getDirectComplementEditor( Condition condition ){
		if ( condition.getType() == Condition.FLAG_CONDITION ){
			FlagCondition flagCondition = (FlagCondition)condition;
			JComboBox component = new JComboBox(new String[]{TextConstants.getText("Conditions.Flag.Active"),
								TextConstants.getText("Conditions.Flag.Inactive")});
			if ( flagCondition.isActiveState() )
				component.setSelectedIndex(0);
			else if ( flagCondition.isInactiveState() )
				component.setSelectedIndex(1);
			return component;
		}
		
		else if ( condition.getType() == Condition.VAR_CONDITION ){
			VarCondition varCondition = (VarCondition)condition;
			JSpinner component = new JSpinner( new SpinnerNumberModel( varCondition.getValue().intValue(), VarCondition.MIN_VALUE, VarCondition.MAX_VALUE, 1 ) );
			return component;
		}
		
		else if ( condition.getType() == Condition.GLOBAL_STATE_CONDITION ){
			return null;
		}
	
		return null;
	}*/
	
	private class CustomLayout extends FlowLayout{
		
		private int x;
		
		private int y;
		
		public CustomLayout(int x, int y){
			super();
			this.x = x;
			this.y = y;
		}
		public void layoutContainer(Container target) {
			//target.remove(buttonsPanel);
			super.layoutContainer(target);
			//target.add(buttonsPanel);
			buttonsPanel.setLocation(target.getWidth()-buttonsPanel.getWidth()+x, y);
		}
	}
	
	/**
	 * Panel with nice alpha effect for buttons
	 * @author Javier
	 *
	 */
	private class ButtonsPanel extends JPanel {
		
		public ButtonsPanel(){
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			setBorder(new LeftLineBorder());
			//this.setLayout(new CustomFlowLayout(buttonsPanel,0,0));
			
			deleteButton = new JButton(new ImageIcon("img/icons/deleteNode.png"));
			deleteButton.setMargin(new Insets(0,0,0,0));
			deleteButton.setContentAreaFilled(false);
			deleteButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					controller.deleteCondition(index1, index2);
				}
			});
			
			duplicateButton = new JButton(new ImageIcon("img/icons/duplicateNode.png"));
			duplicateButton.setMargin(new Insets(0,0,0,0));
			duplicateButton.setContentAreaFilled(false);
			duplicateButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					controller.duplicateCondition(index1, index2);
				}
			});
			
			editButton = new JButton(new ImageIcon("img/icons/edit.png"));
			editButton.setMargin(new Insets(0,0,0,0));
			editButton.setContentAreaFilled(false);
			editButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					controller.editCondition(index1, index2);
				}
			});
			
			//FlowLayout flow= new FlowLayout(FlowLayout.CENTER, 0,0);
			//buttonsPanel.setLayout(flow);
			setOpaque(false);
			
			add(deleteButton);
			add(duplicateButton);
			add(editButton);
			setVisible(false);
		}
		
		public void paint(Graphics g){
			if (alphaComposite!=null)
				((Graphics2D) g).setComposite(alphaComposite);
			super.paint(g);
		}
	}
	

	/**
	 * Timer that controlls the alpha effect when mouse is over the panel
	 * @author Javier
	 *
	 */
	public class AlphaEffectTimer extends Timer {
		
		private static final int INC_PERIOD=10; //MILIS
		private static final float INCREMENT=0.015f; // Range:0-1

		public void start(){
			alpha = 0;
			alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
			((CurvedBorder)borderOver).setAlphaComposite(alphaComposite);
			repaint();
			super.start();
		}
		
		public AlphaEffectTimer(){
			super(INC_PERIOD, new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					// Calculate new alpha
					if (alpha<=1.0f-INCREMENT){
						alpha+=INCREMENT;
					} else{
						alpha = 1.0f;
					}

					// Create new alphaComposite object
					alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
					
					// Update border
					((CurvedBorder)borderOver).setAlphaComposite(alphaComposite);
					
					// Repaint all panel
					repaint();

					//Adjust alpha if necessary
					if (alpha>=1.0f){
						alpha = 1.0f;
						alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
						((CurvedBorder)borderOver).setAlphaComposite(alphaComposite);
						repaint();
						((AlphaEffectTimer)e.getSource()).stop();
					}
					
					// Repaint the last part (buttons)
					buttonsPanel.repaint();	
					
				}
				
			});
		}
	}		
	
}
