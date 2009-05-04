package es.eucm.eadventure.editor.control.tools.general.conditions;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.conditions.Condition;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

/**
 * Edition tool for deleting an either conditions block 
 * @author Javier
 *
 */
public class DeleteEitherBlockTool extends Tool{

	protected Conditions conditions;
	
	protected Conditions deletedBlock;
	
	protected VarFlagSummary varFlagSummary;
	
	protected int index;
	
	protected List<String> deletedReferences;
	
	public DeleteEitherBlockTool(Conditions conditions, VarFlagSummary summary, int index){
		this.conditions = conditions;
		this.varFlagSummary = summary;
		this.index = index;
		this.deletedReferences = new ArrayList<String>();
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
		// Delete the flag/var references
		for( Condition condition : conditions.getEitherConditions( index ) ){
			deletedReferences.add(condition.getId( ));
			varFlagSummary.deleteReference( condition.getId( ) );
		}
		// Get the block to be removed
		deletedBlock = conditions.getEitherBlock(index);
		
		// Delete the block
		conditions.delete( index );
		return true;
	}

	@Override
	public boolean redoTool() {
		boolean done= doTool();
		if (done)
			Controller.getInstance().updatePanel();
		return done;
	}

	@Override
	public boolean undoTool() {
		boolean done = true;
		// Add the deleted references first
		for ( String ref: deletedReferences){
			varFlagSummary.addReference(ref);
		}
		
		// Then, add the deleted block
		conditions.add( index, deletedBlock );
		Controller.getInstance().updatePanel();
		return done;
	}
}
