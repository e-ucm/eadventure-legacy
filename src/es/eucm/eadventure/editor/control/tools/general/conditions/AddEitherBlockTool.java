package es.eucm.eadventure.editor.control.tools.general.conditions;

import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Edition tool for adding an either conditions block 
 * @author Javier
 *
 */
public class AddEitherBlockTool extends Tool{

	protected Conditions conditions;
	
	protected Conditions addedBlock;
	
	public AddEitherBlockTool(Conditions conditions){
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
		addedBlock = new Conditions( );
		conditions.add( addedBlock );
		return true;
	}

	@Override
	public boolean redoTool() {
		conditions.add( addedBlock );
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		boolean done = false;
		for (int i=0; i<conditions.getEitherConditionsBlockCount(); i++){
			if (conditions.getEitherBlock(i) == addedBlock){
				conditions.delete(i);
				Controller.getInstance().updatePanel();
				done = true;
				break;
			}
		}
		return done;
	}
}
