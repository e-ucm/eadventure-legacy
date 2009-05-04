package es.eucm.eadventure.editor.control.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.conditions.Condition;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.conditions.FlagCondition;
import es.eucm.eadventure.common.data.chapter.conditions.GlobalStateCondition;
import es.eucm.eadventure.common.data.chapter.conditions.VarCondition;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.general.conditions.AddConditionTool;
import es.eucm.eadventure.editor.control.tools.general.conditions.AddEitherBlockTool;
import es.eucm.eadventure.editor.control.tools.general.conditions.DeleteConditionTool;
import es.eucm.eadventure.editor.control.tools.general.conditions.DeleteEitherBlockTool;
import es.eucm.eadventure.editor.control.tools.general.conditions.ReplaceConditionTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

/**
 * Controller for the conditions. This class operates on the conditions blocks to add new single conditions, and either
 * conditions blocks.
 * 
 * @author Bruno Torijano Bueno
 */
public class ConditionsController {

	public static final int VAR_CONDITION = Condition.VAR_CONDITION;
	public static final int FLAG_CONDITION = Condition.FLAG_CONDITION;
	public static final int GLOBAL_STATE_CONDITION = Condition.GLOBAL_STATE_CONDITION;
	
	/**
	 * Constant for the index of the main conditions block.
	 */
	public static final int MAIN_CONDITIONS_BLOCK = -1;

	/**
	 * String values for the states of a condition
	 */
	public static final String[] STATE_VALUES = { "Active", "Inactive", 
		">", ">=","<", "<=", "="
	};
	
	/**
	 * String values for the states of a flag condition
	 */
	public static final String[] STATE_VALUES_FLAGS = { "Active", "Inactive" };
	
	/**
	 * String values for the states of a varcondition
	 */
	public static final String[] STATE_VALUES_VARS = { ">", ">=","<", "<=", "=" };

	/**
	 * Link to the main controller.
	 */
	private Controller controller;


	/**
	 * Conditions data.
	 */
	private Conditions conditions;
	
	

	/**
	 * Constructor.
	 * 
	 * @param conditions
	 *            Conditions block of data
	 */
	/*public ConditionsController( Conditions conditions ) {
		this.conditions = conditions;
		controller = Controller.getInstance( );
	}*/

	/**
	 * Returns if the controller can add conditions to the blocks.
	 * 
	 * @return True if the controller can add conditions, false otherwise
	 */
	public boolean canAddConditions( ) {
		return controller.getVarFlagSummary( ).getFlagCount( ) > 0 || 
		controller.getVarFlagSummary( ).getVarCount( ) > 0; 
	}

	/**
	 * Returns the number of either conditions blocks present in the conditions data.
	 * 
	 * @return Number of either conditions blocks
	 */
	public int getEitherConditionsBlockCount( ) {
		return conditions.getEitherConditionsBlockCount( );
	}

	/**
	 * Returns the id of the condition in the given position of the given block.
	 * 
	 * @param blockIndex
	 *            The index of the conditions block. Use MAIN_CONDITIONS_BLOCK (-1) to select the main block of
	 *            conditions, and values from 0 to getEitherConditionsBlockCount( ) to access the either blocks of
	 *            conditions
	 * @param conditionIndex
	 *            Index of the condition
	 * @return Id of the condition
	 */
	public String getConditionId( int blockIndex, int conditionIndex ) {
		String conditionId;

		if( blockIndex == MAIN_CONDITIONS_BLOCK )
			conditionId = conditions.getMainConditions( ).get( conditionIndex ).getId( );

		else
			conditionId = conditions.getEitherConditions( blockIndex ).get( conditionIndex ).getId( );

		return conditionId;
	}
	
	/**
	 * Returns the id of the condition in the given position of the given block.
	 * 
	 * @param blockIndex
	 *            The index of the conditions block. Use MAIN_CONDITIONS_BLOCK (-1) to select the main block of
	 *            conditions, and values from 0 to getEitherConditionsBlockCount( ) to access the either blocks of
	 *            conditions
	 * @param conditionIndex
	 *            Index of the condition
	 * @return Id of the condition
	 */
	public int getConditionType( int blockIndex, int conditionIndex ) {
		int conditionType;

		if( blockIndex == MAIN_CONDITIONS_BLOCK )
			conditionType = conditions.getMainConditions( ).get( conditionIndex ).getType();

		else
			conditionType = conditions.getEitherConditions( blockIndex ).get( conditionIndex ).getType();

		return conditionType;
	}
	
	
	/**
	 * Checks if the selected condition is a var or a flag condition
	 * 
	 * @param blockIndex
	 *            The index of the conditions block. Use MAIN_CONDITIONS_BLOCK (-1) to select the main block of
	 *            conditions, and values from 0 to getEitherConditionsBlockCount( ) to access the either blocks of
	 *            conditions
	 * @param conditionIndex
	 *            Index of the condition
	 * @return Id of the condition
	 */
	public boolean isFlagCondtion( int blockIndex, int conditionIndex ) {
		boolean isFlag = false;

		if( blockIndex == MAIN_CONDITIONS_BLOCK )
			isFlag = conditions.getMainConditions( ).get( conditionIndex ).getType() == Condition.FLAG_CONDITION;
		else
			isFlag = conditions.getEitherConditions( blockIndex ).get( conditionIndex ).getType() == Condition.FLAG_CONDITION;

		return isFlag;
	}
	
	/**
	 * Checks if the selected condition is a var or a flag condition
	 * 
	 * @param blockIndex
	 *            The index of the conditions block. Use MAIN_CONDITIONS_BLOCK (-1) to select the main block of
	 *            conditions, and values from 0 to getEitherConditionsBlockCount( ) to access the either blocks of
	 *            conditions
	 * @param conditionIndex
	 *            Index of the condition
	 * @return Id of the condition
	 */
	public boolean isVarCondtion( int blockIndex, int conditionIndex ) {
		boolean isFlag = false;

		if( blockIndex == MAIN_CONDITIONS_BLOCK )
			isFlag = conditions.getMainConditions( ).get( conditionIndex ).getType() == Condition.VAR_CONDITION;
		else
			isFlag = conditions.getEitherConditions( blockIndex ).get( conditionIndex ).getType() == Condition.VAR_CONDITION;

		return isFlag;
	}
	
	/**
	 * Checks if the selected condition is a var or a flag condition
	 * 
	 * @param blockIndex
	 *            The index of the conditions block. Use MAIN_CONDITIONS_BLOCK (-1) to select the main block of
	 *            conditions, and values from 0 to getEitherConditionsBlockCount( ) to access the either blocks of
	 *            conditions
	 * @param conditionIndex
	 *            Index of the condition
	 * @return Id of the condition
	 */
	public boolean isGlobalStateCondtion( int blockIndex, int conditionIndex ) {
		boolean isFlag = false;

		if( blockIndex == MAIN_CONDITIONS_BLOCK )
			isFlag = conditions.getMainConditions( ).get( conditionIndex ).getType() == Condition.GLOBAL_STATE_CONDITION;
		else
			isFlag = conditions.getEitherConditions( blockIndex ).get( conditionIndex ).getType() == Condition.GLOBAL_STATE_CONDITION;

		return isFlag;
	}


	/**
	 * Returns the state of the condition in the index position of the given block.
	 * 
	 * @param blockIndex
	 *            The index of the conditions block. Use MAIN_CONDITIONS_BLOCK (-1) to select the main block of
	 *            conditions, and values from 0 to getEitherConditionsBlockCount( ) to access the either blocks of
	 *            conditions
	 * @param conditionIndex
	 *            Index of the condition
	 * @return State of the condition
	 */
	public String getConditionState( int blockIndex, int conditionIndex ) {
		String conditionState;

		if( blockIndex == MAIN_CONDITIONS_BLOCK )
			conditionState = getStringFromState( conditions.getMainConditions( ).get( conditionIndex ).getState( ) );

		else
			conditionState = getStringFromState( conditions.getEitherConditions( blockIndex ).get( conditionIndex ).getState( ) );

		return conditionState;
	}

	/**
	 * Adds a new either conditions block.
	 */
	public void addEitherConditionsBlock( ) {
		controller.addTool(new AddEitherBlockTool(conditions));
	}

	/**
	 * Deletes the given either conditions block.
	 * 
	 * @param index
	 *            Index of the either conditions block
	 */
	public void deleteEitherConditionsBlock( int index ) {
		controller.addTool(new DeleteEitherBlockTool(conditions,controller.getVarFlagSummary(),index));
	}

	/**
	 * Adds a new condition to the given block.
	 * 
	 * @param blockIndex
	 *            The index of the conditions block. Use MAIN_CONDITIONS_BLOCK (-1) to select the main block of
	 *            conditions, and values from 0 to getEitherConditionsBlockCount( ) to access the either blocks of
	 *            conditions
	 * @param conditionId
	 *            Id of the condition
	 * @param conditionState
	 *            State of the condition
	 */
	public void addCondition( int blockIndex, int type, String conditionId, String conditionState, String value ) {

		
		if (Controller.getInstance( ).getVarFlagSummary( ).existsId( conditionId ) || 
				Controller.getInstance().getIdentifierSummary().isGlobalStateId(conditionId)){
			if( blockIndex == MAIN_CONDITIONS_BLOCK ){
				if ( type == FLAG_CONDITION ){
					Controller.getInstance().addTool(new AddConditionTool(conditions.getMainConditions( ),new FlagCondition( conditionId, getStateFromString( conditionState ) ),controller.getVarFlagSummary() ));
				}else if ( type == VAR_CONDITION ){
					Controller.getInstance().addTool(new AddConditionTool(conditions.getMainConditions( ),new VarCondition( conditionId, getStateFromString( conditionState ), Integer.parseInt(value) ),controller.getVarFlagSummary() ));
				} else if (type == GLOBAL_STATE_CONDITION ){
					Controller.getInstance().addTool(new AddConditionTool(conditions.getMainConditions( ),new GlobalStateCondition( conditionId ),controller.getVarFlagSummary() ));
				}
	
			}else{
				if ( type == FLAG_CONDITION ){
					Controller.getInstance().addTool(new AddConditionTool(conditions.getEitherConditions( blockIndex ),new FlagCondition( conditionId, getStateFromString( conditionState ) ),controller.getVarFlagSummary() ));
				}else if ( type == VAR_CONDITION ){
					Controller.getInstance().addTool(new AddConditionTool(conditions.getEitherConditions( blockIndex ),new VarCondition( conditionId, getStateFromString( conditionState ), Integer.parseInt(value) ),controller.getVarFlagSummary() ));					
				}else if (type == GLOBAL_STATE_CONDITION ){
					Controller.getInstance().addTool(new AddConditionTool(conditions.getEitherConditions( blockIndex ),new GlobalStateCondition( conditionId ), controller.getVarFlagSummary()));
				}
			}
		}
	}

	
	
	/**
	 * Deletes the given condition from the given block.
	 * 
	 * @param blockIndex
	 *            The index of the conditions block. Use MAIN_CONDITIONS_BLOCK (-1) to select the main block of
	 *            conditions, and values from 0 to getEitherConditionsBlockCount( ) to access the either blocks of
	 *            conditions
	 * @param conditionIndex
	 *            Index of the condition
	 */
	//public void deleteCondition( int blockIndex, int conditionIndex ) {
	//	if( blockIndex == MAIN_CONDITIONS_BLOCK ) {
	//		controller.addTool(new DeleteConditionTool(conditions.getMainConditions( ), conditionIndex, controller.getVarFlagSummary()));
	//	}
	////	else {
	//		controller.addTool(new DeleteConditionTool(conditions.getEitherConditions( blockIndex ), conditionIndex, controller.getVarFlagSummary()));
	//	}
	//}

	/**
	 * Replaces the condition in block blockIndex in position conditionIndex with a new condition created
	 * with the given arguments
	 * @param blockIndex
	 * @param conditionIndex
	 * @param type
	 * @param id
	 * @param state
	 * @param value
	 */
	public void setCondition ( int blockIndex, int conditionIndex, int type, String id, String state, String value){
		Condition newCondition = null;
		
		if( blockIndex == MAIN_CONDITIONS_BLOCK ){
			Condition oldCondition = conditions.getMainConditions( ).get( conditionIndex );
			// Check if there are any changes: (type, id, state or value must be different)
			if (oldCondition.getType()!=type || !oldCondition.getId().equals(id) || 
					oldCondition.getState()!=getStateFromString(state) ||
					(type == VAR_CONDITION && ((VarCondition)oldCondition).getValue()!=Integer.parseInt(value))){
				
				// Create the new condition according to the type
				if ( type == ConditionsController.FLAG_CONDITION ){
					newCondition = new FlagCondition ( id, getStateFromString(state) ) ;
					controller.addTool(new ReplaceConditionTool(conditions.getMainConditions(), newCondition, conditionIndex,controller.getVarFlagSummary()));
				} else if ( type == ConditionsController.VAR_CONDITION ){
					newCondition = new VarCondition ( id, getStateFromString(state), Integer.parseInt(value) ) ;
					controller.addTool(new ReplaceConditionTool(conditions.getMainConditions(), newCondition, conditionIndex,controller.getVarFlagSummary()));
				} else if ( type == ConditionsController.GLOBAL_STATE_CONDITION ){
					newCondition = new GlobalStateCondition ( id ) ;
					controller.addTool(new ReplaceConditionTool(conditions.getMainConditions(), newCondition, conditionIndex,controller.getVarFlagSummary()));
				}
			}
		}
		
		else {
			Condition oldCondition = conditions.getEitherConditions( blockIndex ).get( conditionIndex );
			if (oldCondition.getType()!=type || !oldCondition.getId().equals(id) || 
					oldCondition.getState()!=getStateFromString(state) ||
					(type == VAR_CONDITION && ((VarCondition)oldCondition).getValue()!=Integer.parseInt(value))){
				if ( type == ConditionsController.FLAG_CONDITION ){
					newCondition = new FlagCondition ( id, getStateFromString(state) ) ;
					controller.addTool(new ReplaceConditionTool(conditions.getEitherConditions( blockIndex ), newCondition, conditionIndex,controller.getVarFlagSummary()));
				} else if ( type == ConditionsController.VAR_CONDITION ){
					newCondition = new VarCondition ( id, getStateFromString(state), Integer.parseInt(value) ) ;
					controller.addTool(new ReplaceConditionTool(conditions.getEitherConditions( blockIndex ), newCondition, conditionIndex,controller.getVarFlagSummary()));
				} else if ( type == ConditionsController.GLOBAL_STATE_CONDITION ){
					newCondition = new GlobalStateCondition ( id ) ;
					controller.addTool(new ReplaceConditionTool(conditions.getEitherConditions( blockIndex ), newCondition, conditionIndex,controller.getVarFlagSummary()));
				}
				
			}
		}
	}
	
	/**
	 * Sets the new value (only vars) of the given condition of the given block.
	 * 
	 * @param blockIndex
	 *            The index of the conditions block. Use MAIN_CONDITIONS_BLOCK (-1) to select the main block of
	 *            conditions, and values from 0 to getEitherConditionsBlockCount( ) to access the either blocks of
	 *            conditions
	 * @param conditionIndex
	 *            Index of the condition
	 * @param value
	 *            New value of the var condition
	 */

	public String getConditionValue(int blockIndex, int conditionIndex) {
		// Stores the old flag
		String value = null;

		if( blockIndex == MAIN_CONDITIONS_BLOCK ) {
			if ( conditions.getMainConditions( ).get( conditionIndex ) instanceof VarCondition ){
				value = Integer.toString( ((VarCondition)(conditions.getMainConditions( ).get( conditionIndex ))).getValue( ) );	
			}
			
		}

		else {
			if ( conditions.getEitherConditions( blockIndex ).get( conditionIndex ) instanceof VarCondition ){
				value = Integer.toString( ((VarCondition)(conditions.getEitherConditions( blockIndex ).get( conditionIndex ))).getValue( ) );
			}
		}

		// Updates the flag references
		//controller.dataModified( );

		return value;
	}
	
	/**
	 * Returns the int value of the string state given.
	 * 
	 * @param stringState
	 *            Condition state in String form
	 * @return Int value of state
	 */
	private int getStateFromString( String stringState ) {
		int state = Condition.NO_STATE;

		if( stringState.equals( STATE_VALUES[0] ) )
			state = FlagCondition.FLAG_ACTIVE;

		else if( stringState.equals( STATE_VALUES[1] ) )
			state = FlagCondition.FLAG_INACTIVE;
		
		else if( stringState.equals( STATE_VALUES[2] ) )
			state = VarCondition.VAR_GREATER_THAN;

		else if( stringState.equals( STATE_VALUES[3] ) )
			state = VarCondition.VAR_GREATER_EQUALS_THAN;
		
		else if( stringState.equals( STATE_VALUES[4] ) )
			state = VarCondition.VAR_LESS_THAN;
		
		else if( stringState.equals( STATE_VALUES[5] ) )
			state = VarCondition.VAR_LESS_EQUALS_THAN;
		
		else if( stringState.equals( STATE_VALUES[6] ) )
			state = VarCondition.VAR_EQUALS;
		return state;
	}

	/**
	 * Returns the string value of the boolean state given.
	 * 
	 * @param state
	 *            Condition state in int form
	 * @return String value of state
	 */
	private String getStringFromState( int state ) {
		String stringState = null;

		if( state == FlagCondition.FLAG_ACTIVE )
			stringState = STATE_VALUES[0];

		else if( state == FlagCondition.FLAG_INACTIVE )
			stringState = STATE_VALUES[1];

		else if( state == VarCondition.VAR_GREATER_THAN )
			stringState = STATE_VALUES[2];
		
		else if( state == VarCondition.VAR_GREATER_EQUALS_THAN )
			stringState = STATE_VALUES[3];

		else if( state == VarCondition.VAR_LESS_THAN )
			stringState = STATE_VALUES[4];
		
		else if( state == VarCondition.VAR_LESS_EQUALS_THAN )
			stringState = STATE_VALUES[5];

		else if( state == VarCondition.VAR_EQUALS )
			stringState = STATE_VALUES[6];

		return stringState;
	}

	/**
	 * Updates the given flag summary, adding the flag references contained in the given conditions.
	 * 
	 * @param varFlagSummary
	 *            Flag summary to update
	 * @param conditions
	 *            Set of conditions to search in
	 */
	public static void updateVarFlagSummary( VarFlagSummary varFlagSummary, Conditions conditions ) {
		// First check the main block of conditions
		for( Condition condition : conditions.getMainConditions( ) ){
			if ( condition.getType() == Condition.FLAG_CONDITION )
				varFlagSummary.addFlagReference( condition.getId( ) );
			else if ( condition.getType() == Condition.VAR_CONDITION )
				varFlagSummary.addVarReference( condition.getId( ) );
		}

		// Then add the references from the either blocks
		for( int i = 0; i < conditions.getEitherConditionsBlockCount( ); i++ )
			for( Condition condition : conditions.getEitherConditions( i ) ){
				if ( condition.getType() == Condition.FLAG_CONDITION )
					varFlagSummary.addFlagReference( condition.getId( ) );
				else if ( condition.getType() == Condition.VAR_CONDITION )
					varFlagSummary.addVarReference( condition.getId( ) );
			}
	}

	public int getConditionsCount() {
		return conditions.size();
	}

	public List<Condition> getCondition(int i) {
		if ( i>=0 && i<conditions.size())
			return conditions.get(i);
		return null;
	}
	
	//***********************************************************/
	// MËTODOS NUEVOS
	//***********************************************************/

	private static HashMap<String, ConditionContextProperty> createContextFromOwner(int ownerType, String ownerName){
		HashMap<String, ConditionContextProperty> context1 = new HashMap<String, ConditionContextProperty>();
		ConditionOwner owner = new ConditionOwner(ownerType, ownerName);
		context1.put(ConditionsController.CONDITION_OWNER, owner);
		
		if (TextConstants.containsConditionsContextText(ownerType, TextConstants.NORMAL_SENTENCE)
				&& TextConstants.containsConditionsContextText(ownerType, TextConstants.NO_CONDITION_SENTENCE)){
			ConditionCustomMessage cMessage = new ConditionCustomMessage(TextConstants.getConditionsContextText(ownerType, TextConstants.NORMAL_SENTENCE)
					, TextConstants.getConditionsContextText(ownerType, TextConstants.NO_CONDITION_SENTENCE));
			context1.put(CONDITION_CUSTOM_MESSAGE, cMessage);
		}
		
		return context1;
	}
	
	private static HashMap<String, ConditionContextProperty> createContextFromOwnerMessage(int ownerType, String ownerName, String message1, String message2){
		HashMap<String, ConditionContextProperty> context1 = new HashMap<String, ConditionContextProperty>();
		ConditionOwner owner = new ConditionOwner(ownerType, ownerName);
		
		ConditionCustomMessage cMessage = new ConditionCustomMessage(message1, message2);
		context1.put(CONDITION_CUSTOM_MESSAGE, cMessage);
		context1.put(ConditionsController.CONDITION_OWNER, owner);
		return context1;
	}
	
	public ConditionsController( Conditions conditions ) {
		this(conditions, new HashMap<String, ConditionContextProperty>());
	}
	
	public ConditionsController( Conditions conditions, int ownerType, String ownerName) {
		this(conditions, createContextFromOwner(ownerType, ownerName));
	}
	
	public ConditionsController( Conditions conditions, int ownerType, String ownerName, String message1, String message2) {
		this(conditions, createContextFromOwnerMessage(ownerType, ownerName, message1, message2));
	}

	
	/**
	 * Constructor.
	 * 
	 * @param conditions
	 *            Conditions block of data
	 */
	public ConditionsController( Conditions conditions, HashMap<String,ConditionContextProperty> context) {
		this.conditions = conditions;
		this.context = context;
		if (context.containsKey(CONDITION_OWNER)){
			ConditionOwner owner = (ConditionOwner)context.get(CONDITION_OWNER);
			if (owner.getOwnerType() == Controller.GLOBAL_STATE){
				ConditionRestrictions restrictions = new ConditionRestrictions(new String[]{owner.getOwnerName()});
				this.context.put(CONDITION_RESTRICTIONS, restrictions);
			}
		}
		controller = Controller.getInstance( );
	}

	private HashMap<String,ConditionContextProperty> context;
	
	public boolean isEmpty(){
		return conditions.isEmpty();
	}
	
	// FLAG; VAR; GLOBAL_STATE
	public static final String CONDITION_TYPE="condition-type";
	public static final String CONDITION_TYPE_VAR="var";
	public static final String CONDITION_TYPE_FLAG="flag";
	public static final String CONDITION_TYPE_GS="global-state";
	
	public static final String CONDITION_ID ="condition-id";
	
	
	public static final String CONDITION_VALUE="condition-value";
	
	//active|inactive|<,<=,>,>=,=
	public static final String CONDITION_STATE="condition-state";
	
	public int getConditionCount (int index1){
		// Check index
		if (index1<0 || index1>=conditions.size())
			return -1;
		
		return conditions.get(index1).size();
	}
	
	public boolean deleteCondition( int index1, int index2 ){
		// Check index
		if (index1<0 || index1>=conditions.size())
			return false;
		
		if (index2<0 || index2>=conditions.get(index1).size())
			return false;
		
		conditions.get(index1).remove(index2);
		if (conditions.get(index1).size()==0)
			conditions.delete(index1);
		return true;
	}
	
	public boolean duplicateCondition( int index1, int index2 ){
		// Check index
		if (index1<0 || index1>=conditions.size())
			return false;
		
		if (index2<0 || index2>=conditions.get(index1).size())
			return false;
		
		try {
			Condition duplicate = (Condition)(conditions.get(index1).get(index2).clone());
			conditions.get(index1).add(index2+1, duplicate);
			return true;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean setCondition( int index1, int index2, HashMap<String, String> properties){
		// Check index
		if (index1<0 || index1>=conditions.size())
			return false;
		
		if (index2<0 || index2>=conditions.get(index1).size())
			return false;
		
		Condition condition = conditions.get(index1).get(index2);
		Condition newCondition = null;
		String newId = properties.get(CONDITION_ID);
		String newType = properties.get(CONDITION_TYPE);
		String newState = properties.get(CONDITION_STATE);
		String newValue = properties.get(CONDITION_VALUE);
		int newTypeInt = getTypeFromString(newType);
		
		if (newTypeInt!=condition.getType()){
			if ( newTypeInt == Condition.FLAG_CONDITION ){
				newCondition = new FlagCondition(newId, getStateFromString(newState));
			}
			else if ( newTypeInt == Condition.VAR_CONDITION ){
				newCondition = new VarCondition(newId, getStateFromString(newState), Integer.parseInt(newValue));
			}
			else if ( newTypeInt == Condition.GLOBAL_STATE_CONDITION ){
				newCondition = new GlobalStateCondition(newId, getStateFromString(newState));
			}
		} else {
			newCondition = condition;
			if (!newId.equals(newCondition.getId())){
				newCondition.setId(newId);
			}
			if (!new Integer(getStateFromString(newState)).equals(newCondition.getState())){
				newCondition.setState(new Integer(getStateFromString(newState)));
			}
			if (newCondition.getType() == Condition.VAR_CONDITION){
				VarCondition varCondition = (VarCondition)condition;
				if (!varCondition.getValue().equals(Integer.parseInt(newValue))){
					varCondition.setValue(Integer.parseInt(newValue));
				}
			}
		}

		conditions.get(index1).remove(index2);
		conditions.get(index1).add(index2, newCondition);
		
		return true;
	}

	private int getTypeFromString (String newType){
		int newTypeInt = -1;
		if (newType.equals(ConditionsController.CONDITION_TYPE_FLAG))
			newTypeInt = Condition.FLAG_CONDITION;
		if (newType.equals(ConditionsController.CONDITION_TYPE_VAR))
			newTypeInt = Condition.VAR_CONDITION;
		if (newType.equals(ConditionsController.CONDITION_TYPE_GS))
			newTypeInt = Condition.GLOBAL_STATE_CONDITION;
		return newTypeInt;
	}
	
	public HashMap<String, String> getCondition(int index1, int index2){
		HashMap<String, String> conditionProperties = new HashMap<String, String>();
		
		// Check index
		if (index1<0 || index1>=conditions.size())
			return null;
		
		List<Condition> conditionsList = conditions.get(index1);
		// Check index2
		if (index2<0 || index2>=conditionsList.size())
			return null;
		
		Condition condition = conditionsList.get(index2);
		// Put ID
		conditionProperties.put(CONDITION_ID, condition.getId());
		
		// Put State
		conditionProperties.put(CONDITION_STATE, Integer.toString(condition.getState()));
		// Put Type
		if (condition.getType() == Condition.FLAG_CONDITION){
			conditionProperties.put(CONDITION_TYPE, CONDITION_TYPE_FLAG);
			//Put value
			conditionProperties.put(CONDITION_VALUE, Integer.toString(condition.getState()));
		}
		else if (condition.getType() == Condition.VAR_CONDITION){
			conditionProperties.put(CONDITION_TYPE, CONDITION_TYPE_VAR);
			//Put value
			VarCondition varCondition = (VarCondition)condition;
			conditionProperties.put(CONDITION_VALUE, Integer.toString(varCondition.getValue()));
		}
		else if (condition.getType() == Condition.GLOBAL_STATE_CONDITION){
			conditionProperties.put(CONDITION_TYPE, CONDITION_TYPE_GS);
			//Put value
			conditionProperties.put(CONDITION_VALUE, Integer.toString(condition.getState()));
		}
		
		return conditionProperties;
	}
	
	/**
	 * Adds a new condition to the given block.
	 * 
	 * @param blockIndex
	 *            The index of the conditions block. Use MAIN_CONDITIONS_BLOCK (-1) to select the main block of
	 *            conditions, and values from 0 to getEitherConditionsBlockCount( ) to access the either blocks of
	 *            conditions
	 * @param conditionId
	 *            Id of the condition
	 * @param conditionState
	 *            State of the condition
	 */
	public boolean addCondition( int index1, int index2, String conditionType, String conditionId, String conditionState, String value ) {
		
		if (index1<0 || index1 >conditions.size())
			return false;
		
		Condition newCondition = null;
		int type=getTypeFromString(conditionType);
		if ( type == FLAG_CONDITION ){
			newCondition = new FlagCondition(conditionId, this.getStateFromString(conditionState));
			//Controller.getInstance().addTool(new AddConditionTool(conditions.getMainConditions( ),new FlagCondition( conditionId, getStateFromString( conditionState ) ),controller.getVarFlagSummary() ));
		}else if ( type == VAR_CONDITION ){
			newCondition = new VarCondition(conditionId, this.getStateFromString(conditionState), Integer.parseInt(value));
			//Controller.getInstance().addTool(new AddConditionTool(conditions.getMainConditions( ),new VarCondition( conditionId, getStateFromString( conditionState ), Integer.parseInt(value) ),controller.getVarFlagSummary() ));
		} else if (type == GLOBAL_STATE_CONDITION ){
			newCondition = new GlobalStateCondition(conditionId, this.getStateFromString(conditionState));
			//Controller.getInstance().addTool(new AddConditionTool(conditions.getMainConditions( ),new GlobalStateCondition( conditionId ),controller.getVarFlagSummary() ));
		}
		
		if (newCondition!=null){
			if (index1<conditions.size()){
				
				if (index2==INDEX_NOT_USED){
					// Add new block
					List<Condition> newBlock = new ArrayList<Condition>();
					newBlock.add(newCondition);
					conditions.add(index1, newBlock);
				} else {
					List<Condition> block = conditions.get(index1);
					if (index2<0 || index2>block.size())
						return false;
					
					if (index2==conditions.size())
						block.add(newCondition);
					else
						block.add(index2, newCondition);
				}
				
			} else {
				// Add new block
				List<Condition> newBlock = new ArrayList<Condition>();
				newBlock.add(newCondition);
				conditions.add(newBlock);
			}
		}
		return true;
	}
	
	public static final int EVAL_FUNCTION_AND = 0;
	public static final int EVAL_FUNCTION_OR = 1;
	public static final int INDEX_NOT_USED=-1;
	
	public boolean setEvalFunction(int index1, int index2, int value){
		// Check value
		if (value!=EVAL_FUNCTION_AND && value!=EVAL_FUNCTION_OR)
			return false;
		
		// Check index
		// Check if the algorithm must search deeper (index2==-1 means no search inside blocks must be carried out)
		if (index2==-1){
			if (index1 <0 || index1 >=conditions.size()-1)
				return false;
		} else if (index2>=0){
			if (index1 <0 || index1 >=conditions.size())
				return false;
		}

		// Get upper and lower block
		List<Condition> upper = conditions.get(index1);
		List<Condition> lower = conditions.get(index1+1);
		
		// Check if the algorithm must search deeper (index2==-1 means no search inside blocks must be carried out)
		if (index2>=0){

			// Check index2
			if (upper.size()==1 || index2 >=upper.size()-1 || value!= EVAL_FUNCTION_AND)
				return false;
			
			// Either block must be split
			List<List<Condition>> newBlocks = splitBlock(upper, index2);
			List<Condition> firstBlock = newBlocks.get(0);
			List<Condition> secondBlock = newBlocks.get(1);
			
			conditions.delete(index1);
			if (firstBlock.size() == 1)
				conditions.add(index1, firstBlock.get(0));
			else
				conditions.add(index1, firstBlock );
			
			if (secondBlock.size() == 1)
				conditions.add(index1+1, secondBlock.get(0));
			else
				conditions.add(index1+1, secondBlock );
			
			return true;
			
		} 
		// No deep search is needed. The "And" function must be changed to "OR"
		else {
			if (value!=EVAL_FUNCTION_OR)
				return false;

			// Merge both wrappers
			Conditions newBlock = mergeBlocks(upper, lower);
			// Insert in "upper" position
			conditions.delete(index1);
			conditions.delete(index1);
			conditions.add(index1, newBlock);
			
			return true;
		}
	}
	
	private List<List<Condition>> splitBlock ( List<Condition> conditions, int index ){
		List<List<Condition>> result = new ArrayList<List<Condition>>();
	
		if (index<0 || index>=conditions.size()-1)
			return null;
		
		List<Condition> block1 = new ArrayList<Condition>();
		List<Condition> block2 = new ArrayList<Condition>();
		
		for (int i=0; i<=index; i++){
			block1.add(conditions.get(i));
		}
		for (int i=index+1; i<conditions.size(); i++){
			block2.add(conditions.get(i));
		}

		result.add(block1);
		result.add(block2);
		
		return result;
	}
	
	private Conditions mergeBlocks ( List<Condition> wrapper1, List<Condition> wrapper2 ){
		Conditions newBlock = new Conditions();
		transferConditions(newBlock, wrapper1);
		transferConditions(newBlock, wrapper2);
		return newBlock;
	}
	
	private void transferConditions ( Conditions container, List<Condition> wrapper1){
		for (Condition condition: wrapper1 ){
			container.add(condition);
		}
	}
	
	//Condition type. Values: GLOBAL_STATE | CONDITION
	public static String CONDITION_GROUP_TYPE ="condition-type";

	public static String CONDITION_RESTRICTIONS ="condition-restrictions";
	
	public static String CONDITION_OWNER ="condition-owner";
	
	public static String CONDITION_CUSTOM_MESSAGE ="condition-custom-message";
	
	public HashMap<String,ConditionContextProperty> getContext(){
		/*HashMap<String,ConditionContextProperty> context = new HashMap<String, ConditionContextProperty>();
		ConditionOwner parent = new ConditionOwner(Controller.SCENE, "Salón");
		ConditionOwner owner = new ConditionOwner(Controller.NPC_REFERENCE, "Personaje", parent);
		context.put(CONDITION_OWNER, owner);*/
		return context;
	}
	
	public static abstract class ConditionContextProperty {
		
		public ConditionContextProperty ( String type ){
			this.type = type;
		}
		
		private String type;
		
		public String getType(){
			return type;
		}
	}
	
	public static class ConditionRestrictions extends ConditionContextProperty{
		private String[] forbiddenIds;
		
		public ConditionRestrictions ( String[] forbiddenIds ){
			super(CONDITION_RESTRICTIONS);
			this.forbiddenIds = forbiddenIds;
		}
		
		public ConditionRestrictions ( String forbiddenId ){
			this (new String[]{forbiddenId});
		}

		public String[] getForbiddenIds(){
			return forbiddenIds;
		}
	}
	
	
	public static class ConditionCustomMessage extends ConditionContextProperty{

		public static final String ELEMENT_TYPE = "{#ELEMENT_TYPE$}";
		
		public static final String ELEMENT_ID = "{#ELEMENT_ID$}";
		
		private String sentence;
		
		private String sentenceNoConditions;
		
		public ConditionCustomMessage( String[] sentenceStrings, String[] noConditionStrings ) {
			super(CONDITION_CUSTOM_MESSAGE);
			
			sentence = "";
			for (String string: sentenceStrings){
				sentence+= string+" ";
			}
			if (sentenceStrings.length>0){
				sentence = sentence.substring(0, sentence.length()-1);
			}
			
			sentenceNoConditions = "";
			for (String string: noConditionStrings){
				sentenceNoConditions+= string+" ";
			}
			if (noConditionStrings.length>0){
				sentenceNoConditions = sentenceNoConditions.substring(0, sentenceNoConditions.length()-1);
			}
		}
		
		public ConditionCustomMessage( List<String> sentenceStrings, List<String> noConditionStrings ) {
			this (sentenceStrings.toArray(new String[]{}), noConditionStrings.toArray(new String[]{}));
		}
		public ConditionCustomMessage( String sentence, String noConditionSentence ) {
			super(CONDITION_CUSTOM_MESSAGE);
			this.sentence = sentence;
			this.sentenceNoConditions = noConditionSentence;
		}

		private String formatSentence(ConditionOwner owner, String sentence){
			String formattedSentence = new String(sentence);
			if(sentence.contains(ELEMENT_TYPE)){
				formattedSentence = formattedSentence.replace(ELEMENT_TYPE, "<i>"+TextConstants.getElementName(owner.getOwnerType())+"</i>");
			}
			if(sentence.contains(ELEMENT_ID)){
				formattedSentence = formattedSentence.replace(ELEMENT_ID, "<b>\""+owner.getOwnerName()+"\"</b>");
			}
			return formattedSentence;
		}

		
		public String getFormattedSentence(ConditionOwner owner){
			return formatSentence(owner, sentence);
		}
		
		public String getNoConditionFormattedSentence(ConditionOwner owner){
			return formatSentence(owner, sentenceNoConditions);
		}

		
	}
	public static class ConditionOwner extends ConditionContextProperty{

		private int ownerType;
		
		private String ownerName;
		
		private ConditionOwner parent;
		
		public ConditionOwner( int ownerType, String ownerName, ConditionOwner parent ) {
			super( CONDITION_OWNER );
			this.ownerType = ownerType;
			this.ownerName = ownerName;
			this.parent = parent;
		}
		
		public ConditionOwner( int ownerType, String ownerName ){
			this (ownerType, ownerName, null);
		}

		/**
		 * @return the ownerType
		 */
		public int getOwnerType() {
			return ownerType;
		}
		
		/**
		 * @return the owner name
		 */
		public String getOwnerName() {
			return ownerName;
		}
		
		/**
		 * @return the owner name
		 */
		public ConditionOwner getParent() {
			return parent;
		}
		
	}
}