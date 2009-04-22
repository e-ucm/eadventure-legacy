package es.eucm.eadventure.editor.control.tools.general.effects;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.effects.Effects;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Move an effect in list. Associated to this, move the corresponding associated conditions 
 *
 */
public class MoveEffectInTableTool extends Tool {

	public static final int MODE_UP = 0;
	public static final int MODE_DOWN = 1;
	
	private Effects effects;

	private int index;
	
	private int newIndex;
	
	private int mode;
	
	private List<ConditionsController> conditions;
	
	/**
	 * Constructor.
	 * @param list	The List which contains the object to be moved
	 * @param index	The index of the object in the list
	 * @param mode	MODE_UP if the object must be moved one position up
	 * 				MODE_DOWN if the object must be moved one position down
	 */
	public MoveEffectInTableTool (Effects effects, int index, int mode, List<ConditionsController> conditions){
		this.effects = effects;
		this.index = index;
		this.mode = mode;
		this.conditions = conditions;
	}

	/**
	 * Constructor.
	 * @param list		The List which contains the object to be moved
	 * @param object	The object in the list. It must be compulsorily in the list
	 * @param mode		MODE_UP if the object must be moved one position up
	 * 					MODE_DOWN if the object must be moved one position down
	 */
	/*public MoveEffectInTableTool( List list, Object object, int mode ) {
		this (list, list.indexOf(object), mode);
	}*/
	
	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public boolean doTool() {
		if (mode == MODE_UP)
			newIndex = moveUp();
		else if (mode == MODE_DOWN)
			newIndex = moveDown();
		return (newIndex!=-1);
	}


	@Override
	public boolean redoTool() {
		boolean done = false;
		if (mode == MODE_UP)
			done = moveUp()!=-1;
		else if (mode == MODE_DOWN)
			done = moveDown()!=-1;
		
		if (done)
			Controller.getInstance().updatePanel();
		return done;
	}

	@Override
	public boolean undoTool() {
		boolean done = false;
		if (mode == MODE_UP){
			int temp = index;
			index = newIndex;
			done = moveDown()!=-1;
			index = temp;
		}else if (mode == MODE_DOWN){
			int temp = index;
			index = newIndex;
			done = moveUp()!=-1;
			index = temp;

		}
		
		if (done)
			Controller.getInstance().updatePanel();
		return done;

	}
	
	@Override
	public boolean combine(Tool other) {
		return false;
	}

	private int moveUp(){
		int moved = -1;

		if( index > 0 ) {
			effects.getEffects().add( index - 1, effects.getEffects().remove( index ) );
			conditions.add( index - 1, conditions.remove( index ) );
			moved = index-1;
		}
		
		return moved;
	}
	
	private int moveDown(){
		int moved = -1;

		if( index < effects.getEffects().size()-1 ) {
		    effects.getEffects().add( index + 1, effects.getEffects().remove( index ) );
		    conditions.add( index + 1, conditions.remove( index ) );
			moved = index+1;
		}
		
		return moved;
	}


}
