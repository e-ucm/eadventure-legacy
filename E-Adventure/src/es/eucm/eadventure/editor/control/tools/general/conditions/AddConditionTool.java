package es.eucm.eadventure.editor.control.tools.general.conditions;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.conditions.Condition;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.conditions.FlagCondition;
import es.eucm.eadventure.common.data.chapter.conditions.GlobalStateCondition;
import es.eucm.eadventure.common.data.chapter.conditions.VarCondition;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.tools.Tool;

public class AddConditionTool extends Tool{

	private Conditions conditions;
	private int index1;
	private int index2;
	private String conditionType;
	private String conditionId;
	private String conditionState;
	private String value;
	
	private List<Condition> blockAdded; //index1
	private Condition conditionAdded;
	private int indexAdded;
	
	public AddConditionTool (Conditions conditions, int index1, int index2, String conditionType, String conditionId, String conditionState, String value ){
		this.conditions = conditions;
		this.index1 = index1;
		this.index2 = index2;
		this.conditionType = conditionType;
		this.conditionId = conditionId;
		this.conditionState = conditionState;
		this.value = value;
		
		this.blockAdded = null;
		this.conditionAdded =null;
		this.indexAdded = -1;

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
		
		
		Condition newCondition = null;
		int type=ConditionsController.getTypeFromString(conditionType);
		if ( type == ConditionsController.FLAG_CONDITION ){
			newCondition = new FlagCondition(conditionId, ConditionsController.getStateFromString(conditionState));
		}else if ( type == ConditionsController.VAR_CONDITION ){
			newCondition = new VarCondition(conditionId, ConditionsController.getStateFromString(conditionState), Integer.parseInt(value));
		} else if (type == ConditionsController.GLOBAL_STATE_CONDITION ){
			newCondition = new GlobalStateCondition(conditionId, ConditionsController.getStateFromString(conditionState));
		}
		
		if (newCondition!=null){
			if (index1<conditions.size()){
				
				if (index2==ConditionsController.INDEX_NOT_USED){
					// Add new block
					List<Condition> newBlock = new ArrayList<Condition>();
					newBlock.add(newCondition);
					conditions.add(index1, newBlock);
					indexAdded = index1;
					blockAdded = newBlock;
				} else {
					List<Condition> block = conditions.get(index1);
					if (index2<0 || index2>block.size())
						return false;
					
					if (index2==conditions.size()){
						block.add(newCondition);
						indexAdded = block.indexOf(newCondition);
						conditionAdded = newCondition;
					}else{
						indexAdded = index2;
						conditionAdded = newCondition;
						block.add(index2, newCondition);
					}
				}
				
			} else {
				// Add new block
				List<Condition> newBlock = new ArrayList<Condition>();
				newBlock.add(newCondition);
				conditions.add(newBlock);
				indexAdded = conditions.size()-1;
				blockAdded = newBlock;
			}
			Controller.getInstance().updateVarFlagSummary();
			Controller.getInstance().updatePanel();
			return true;
		}
		return false;
	}

	@Override
	public boolean redoTool() {
		if (blockAdded!=null){
			conditions.add(indexAdded, blockAdded);
			Controller.getInstance().updateVarFlagSummary();
			Controller.getInstance().updatePanel();
			return true;
		}
		else if (conditionAdded!=null){
			conditions.get(index1).add(indexAdded, conditionAdded);
			Controller.getInstance().updateVarFlagSummary();
			Controller.getInstance().updatePanel();
			return true;
		}

		return false;
	}

	@Override
	public boolean undoTool() {
		if (blockAdded!=null){
			conditions.delete(indexAdded);
			Controller.getInstance().updateVarFlagSummary();
			Controller.getInstance().updatePanel();
			return true;
		}
		else if (conditionAdded!=null){
			conditions.get(index1).remove(indexAdded);
			Controller.getInstance().updateVarFlagSummary();
			Controller.getInstance().updatePanel();
			return true;
		}

		return false;
	}

}
