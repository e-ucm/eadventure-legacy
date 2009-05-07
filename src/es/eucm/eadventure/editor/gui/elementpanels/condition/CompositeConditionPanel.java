package es.eucm.eadventure.editor.gui.elementpanels.condition;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;


public class CompositeConditionPanel extends EditablePanel{

	/**
	 * Required
	 */
	private static final long serialVersionUID = -4472061217945559110L;

	public CompositeConditionPanel(ConditionsPanelController controller, int i) {
		super(controller,i,false, false);
		
		borderSelected = borderOver = null;
		state = SELECTED;
		setState (NO_SELECTED);
	}
	
	@Override
	protected void addComponents(int newState) {

		//Left bracket
		if (controller.getConditionCount(super.index1)>1){
			JPanel leftBracket = new BracketPanel(true);
			add (leftBracket);
		}
		
		for (int i=0; i<controller.getConditionCount(super.index1); i++){
			AtomicConditionPanel currentConditionPanel = new AtomicConditionPanel(controller, super.index1, i);
			add(currentConditionPanel);
			if (i<controller.getConditionCount(super.index1)-1){
				add (new EvalFunctionPanel(controller, super.index1, i, EvalFunctionPanel.OR));
			}
		}
		
		//Right bracket
		if (controller.getConditionCount(super.index1)>1){
			JPanel rightBracket = new BracketPanel(false);
			add (rightBracket);
		}

		
	}

	@Override
	protected ButtonsPanel createButtonsPanel() {
		return null;
	}

	private class BracketPanel extends JPanel{
		/**
		 * Required
		 */
		private static final long serialVersionUID = -5089559898788716264L;
		private boolean isLeft;
		
		
		
		public BracketPanel (boolean isLeft){
			this.isLeft = isLeft;
			this.setMinimumSize(new Dimension(32,32));
			this.setPreferredSize(new Dimension(32,32));
		}
		public void paint (Graphics g){
			g.setColor(Color.black);
			int width = getWidth();
			int height = getHeight();
			if (isLeft)
				g.drawArc(0, 0, 2*width/3, height, 90, 180);
			else
				g.drawArc(0, 0, 2*width/3, height, 90, -180);
			
		}
	}
	
}
