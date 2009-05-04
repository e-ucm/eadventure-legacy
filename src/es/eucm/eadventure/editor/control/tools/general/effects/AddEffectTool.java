package es.eucm.eadventure.editor.control.tools.general.effects;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.effects.AbstractEffect;
import es.eucm.eadventure.common.data.chapter.effects.Effects;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Edition tool for adding an effect
 * @author Javier
 *
 */
public class AddEffectTool extends Tool{

	protected Effects effects;
	protected AbstractEffect effectToAdd;
	protected List<ConditionsController> conditions;
	protected ConditionsController condition;
	
	public AddEffectTool (Effects effects, AbstractEffect effectToAdd,List<ConditionsController> conditions){
		this.effects = effects;
		this.effectToAdd = effectToAdd;
		this.conditions = conditions;
	}
	
	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
		effects.add(effectToAdd);
		if (conditions!=null){
		    condition = new ConditionsController(effectToAdd.getConditions() );
		    conditions.add(condition);
		}
		
		return true;
	}

	@Override
	public boolean redoTool() {
		boolean done = doTool();
		if (done)
			Controller.getInstance().updatePanel();
		return done;
	}

	@Override
	public boolean undoTool() {
		effects.getEffects().remove(effectToAdd);
		if (conditions!=null){
		    conditions.remove(condition);
		}
		Controller.getInstance().updatePanel();
		return true;
	}

}
