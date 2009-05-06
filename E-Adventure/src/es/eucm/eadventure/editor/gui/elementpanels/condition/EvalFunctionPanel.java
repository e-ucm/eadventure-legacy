package es.eucm.eadventure.editor.gui.elementpanels.condition;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.plaf.basic.BasicComboBoxUI;

import sun.swing.DefaultLookup;

import com.sun.media.ui.ComboBox;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;

public class EvalFunctionPanel extends EditablePanel{
	
	public static final int AND=ConditionsController.EVAL_FUNCTION_AND;
	public static final int OR=ConditionsController.EVAL_FUNCTION_OR;
	
	private JComboBox comboBox;
	
	private JLabel label;
	
	private int value;
	private int index2;
	
	public EvalFunctionPanel ( ConditionsPanelController controller, int index1, int index2, int value){
		super (controller, index1);
		this.value = value;
		this.index2 = index2;
		createLabel();
		createComboBox();
		setState(NO_SELECTED);
	}
		
	private void createLabel(){
		if (value == AND){
			label = new JLabel(TextConstants.getText("Conditions.And"));
		}
		else if (value == OR){
			label = new JLabel(TextConstants.getText("Conditions.Or"));
		}
		label.setFont(new Font("Default", Font.BOLD|Font.ITALIC, 13));
	}
	
	private void createComboBox(){
		comboBox = new JComboBox(new String[]{TextConstants.getText("Conditions.And"), TextConstants.getText("Conditions.Or")});
		comboBox.setEditable(false);
		comboBox.setUI(new BasicComboBoxUI());
	
		if ( value == AND ){
			comboBox.setSelectedIndex(0);	
		} else if ( value == OR ){
			comboBox.setSelectedIndex(1);	
		}
		
		comboBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange()==ItemEvent.SELECTED){
					String newValue = (String)e.getItem();
					if (newValue.equals(TextConstants.getText("Conditions.Or"))){
						if (EvalFunctionPanel.this.value!=OR){
							EvalFunctionPanel.this.value = OR;
							EvalFunctionPanel.this.controller.evalFunctionChanged(EvalFunctionPanel.this, index1, index2, EvalFunctionPanel.AND, EvalFunctionPanel.OR);
						}
					}
					else if (newValue.equals(TextConstants.getText("Conditions.And"))){
						if (EvalFunctionPanel.this.value!=AND){
							EvalFunctionPanel.this.value = AND;
							EvalFunctionPanel.this.controller.evalFunctionChanged(EvalFunctionPanel.this, index1, index2, EvalFunctionPanel.OR, EvalFunctionPanel.AND);
						}
					}
					
				}
			}
			
			
		});

	}
	
	@Override
	protected void addComponents(int newState) {
		if (newState == NO_SELECTED || newState==OVER){
			createLabel();
			this.add(label);
		} 
		else if (newState==SELECTED){
			createComboBox();
			this.add(comboBox);
		}
		
	}
	
	/*public void paint (Graphics g){
		if(state !=SELECTED){
			super.paint(g);
		}else {
			g.drawRoundRect(x, y, w-1, h-1, sinkLevel, sinkLevel);
		}
	}*/
	
}