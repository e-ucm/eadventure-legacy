package es.eucm.eadventure.editor.control.tools.adaptation;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.adaptation.AdaptationRule;
import es.eucm.eadventure.common.data.adaptation.AdaptedState;
import es.eucm.eadventure.common.data.adaptation.ContainsAdaptedState;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Edition Tool for adding an action in an adaptation rule (adapted state)
 * @author Javier
 *
 */
public class AddActionTool extends Tool{

	protected ContainsAdaptedState containsAS;
	
	protected AdaptedState state;
	
	protected AdaptedState oldState;
	
	protected int index;
	
	protected int mode;
	
	public AddActionTool (ContainsAdaptedState rule, int index){
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
		boolean added=false;
		//Check there is at least one flag

		String[] flags = Controller.getInstance( ).getVarFlagSummary( ).getFlags( );
		if (flags!=null && flags.length>0){
			try {
				this.oldState = (AdaptedState) state.clone();
				//	By default, the flag is activated. Default flag will be the first one
				state.addActivatedFlag( flags[0] );
				Controller.getInstance( ).updateFlagSummary( );
				added=true;
			} catch (CloneNotSupportedException e) {
				ReportDialog.GenerateErrorReport(e, false, "Could not clone adaptedState "+((state==null)?"null":state.getClass().toString()));
			}
		}
		
		//Otherwise, prompt error message
		// If the list had no elements, show an error message
		else
			Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Adaptation.ErrorNoFlags.Title" ), TextConstants.getText( "Adaptation.ErrorNoFlags" ) );
		
		return added;
	}

	@Override
	public boolean redoTool() {
		containsAS.setAdaptedState( state );
		Controller.getInstance( ).updateFlagSummary( );
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		containsAS.setAdaptedState( oldState );
		Controller.getInstance( ).updateFlagSummary( );
		Controller.getInstance().updatePanel();
		return true;
	}

}
