package es.eucm.eadventure.editor.gui.elementpanels.condition;

import java.util.HashMap;

import es.eucm.eadventure.editor.control.controllers.ConditionsController;

public interface ConditionsPanelController {

	public static final String CONDITION_TYPE_FLAG = ConditionsController.CONDITION_TYPE_FLAG;
	public static final String CONDITION_TYPE_VAR = ConditionsController.CONDITION_TYPE_VAR;
	public static final String CONDITION_TYPE_GS = ConditionsController.CONDITION_TYPE_GS;
	
	public static final String CONDITION_ID =ConditionsController.CONDITION_ID;
	
	
	public static final String CONDITION_VALUE=ConditionsController.CONDITION_VALUE;
	
	//active|inactive|<,<=,>,>=,=
	public static final String CONDITION_STATE=ConditionsController.CONDITION_STATE;
	public static final String CONDITION_TYPE=ConditionsController.CONDITION_TYPE;
	
	public int getConditionCount (int index1);
	
	public HashMap<String, String> getCondition(int index1, int index2);
	
	public void addCondition( int index1, int index2 );
	
	public void deleteCondition( int index1, int index2 );
	
	public void editCondition( int index1, int index2 );
	
	public void duplicateCondition( int index1, int index2 );
	
	public void addCondition( );
	
	public void evalEditablePanelSelectionEvent(EditablePanel source, int oldState, int newState);
	
	public void evalFunctionChanged(EvalFunctionPanel source, int index1, int index2, int oldValue, int newValue);
}
