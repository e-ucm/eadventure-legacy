package es.eucm.eadventure.editor.gui.elementpanels.condition;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import es.eucm.eadventure.common.data.chapter.conditions.Condition;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;

public class CompositeConditionPanel extends EditablePanel{

	public CompositeConditionPanel(ConditionsPanelController controller, int i) {
		super(controller,i);
		borderSelected = borderOver = null;
		state = SELECTED;
		setState (NO_SELECTED);
	}
	
	@Override
	protected void addComponents(int newState) {

		for (int i=0; i<controller.getConditionCount(super.index1); i++){
			AtomicConditionPanel currentConditionPanel = new AtomicConditionPanel(controller, super.index1, i);
			add(currentConditionPanel);
			if (i<controller.getConditionCount(super.index1)-1){
				add (new EvalFunctionPanel(controller, super.index1, i, EvalFunctionPanel.OR));
			}
		}
		
	}

}
