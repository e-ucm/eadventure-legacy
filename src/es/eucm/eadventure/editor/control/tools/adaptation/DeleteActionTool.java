package es.eucm.eadventure.editor.control.tools.adaptation;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.adaptation.AdaptedState;
import es.eucm.eadventure.common.data.adaptation.ContainsAdaptedState;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Edition Tool for adding an action in an adaptation rule (adapted state)
 * @author Javier
 *
 */
public class DeleteActionTool extends Tool{

	protected ContainsAdaptedState containsAS;
	
	protected AdaptedState state;
	
	protected AdaptedState oldState;
	
	protected int index;
	
	protected int mode;
	
	public DeleteActionTool (ContainsAdaptedState rule, int index){
		this.containsAS = rule;
		this.state = rule.getAdaptedState();
		this.index = index;
	}
	
	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return oldState!=null;
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
		boolean deleted = false;
		if ( index >=0 && index <state.getFlagsVars( ).size( )){
			try {
				oldState = (AdaptedState) state.clone();
				state.removeFlagVar( index );
				Controller.getInstance().updateVarFlagSummary( );
				deleted = true;
				//Controller.getInstance().updatePanel();
			} catch (CloneNotSupportedException e) {
				ReportDialog.GenerateErrorReport(e, false, "Could not clone adaptedState "+((state==null)?"null":state.getClass().toString()));
			}

		}
		
		return deleted;
	}

	@Override
	public boolean redoTool() {
		return undoTool();
	}

	@Override
	public boolean undoTool() {
		
		try {
			AdaptedState temp = (AdaptedState)state.clone();
			// Restore initial scene id
			state.setTargetId( oldState.getTargetId() );
			// Restore all FlagVars
			state.getFlagsVars().clear();
			for ( String flagVar: oldState.getFlagsVars()){
				state.getFlagsVars().add(flagVar);
			}
			// Restore all actions
			state.getActionsValues().clear();
			for ( String flagVar: oldState.getActionsValues()){
				state.getActionsValues().add(flagVar);
			}
			oldState = temp;
			Controller.getInstance( ).updateVarFlagSummary( );
			Controller.getInstance().updatePanel();
			return true;
		} catch (CloneNotSupportedException e) {
			ReportDialog.GenerateErrorReport(e, false, "Could not clone adaptedState "+((state==null)?"null":state.getClass().toString()));
			return false;
		}		
	}

}
