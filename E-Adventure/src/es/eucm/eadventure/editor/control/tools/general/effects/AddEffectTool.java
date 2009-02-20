package es.eucm.eadventure.editor.control.tools.general.effects;

import es.eucm.eadventure.common.data.chapter.effects.Effect;
import es.eucm.eadventure.common.data.chapter.effects.Effects;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Edition tool for adding an effect
 * @author Javier
 *
 */
public class AddEffectTool extends Tool{

	protected Effects effects;
	protected Effect effectToAdd;
	
	public AddEffectTool (Effects effects, Effect effectToAdd){
		this.effects = effects;
		this.effectToAdd = effectToAdd;
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
		Controller.getInstance().updatePanel();
		return true;
	}

}
