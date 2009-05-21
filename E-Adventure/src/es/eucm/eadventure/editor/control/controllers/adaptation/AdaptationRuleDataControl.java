package es.eucm.eadventure.editor.control.controllers.adaptation;

import java.util.List;

import es.eucm.eadventure.common.data.adaptation.AdaptationRule;
import es.eucm.eadventure.common.data.adaptation.AdaptedState;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.tools.adaptation.AddActionTool;
import es.eucm.eadventure.editor.control.tools.adaptation.AddUOLPropertyTool;
import es.eucm.eadventure.editor.control.tools.adaptation.ChangeActionTool;
import es.eucm.eadventure.editor.control.tools.adaptation.ChangeUOLPropertyTool;
import es.eucm.eadventure.editor.control.tools.adaptation.ChangeVarFlagTool;
import es.eucm.eadventure.editor.control.tools.adaptation.DeleteActionTool;
import es.eucm.eadventure.editor.control.tools.adaptation.DeleteUOLPropertyTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeTargetIdTool;
import es.eucm.eadventure.editor.control.tools.generic.MoveObjectTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class AdaptationRuleDataControl extends DataControl{

	private AdaptationRule adaptationRule;
	
	public AdaptationRuleDataControl (AdaptationRule adpRule){
		this.adaptationRule = adpRule;
	}
	
	@Override
	public boolean addElement( int type , String id) {
		return false;
	}

	@Override
	public boolean canAddElement( int type ) {
		return false;
	}

	@Override
	public boolean canBeDeleted( ) {
		return true;
	}

	@Override
	public boolean canBeMoved( ) {
		return true;
	}

	@Override
	public boolean canBeRenamed( ) {
		return false;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		return 0;
	}

	@Override
	public int countIdentifierReferences( String id ) {
		if (adaptationRule.getId().equals(id)){
		    return 1;
		}
		else {
		    return 0;
		}
	}

	@Override
	public void deleteAssetReferences( String assetPath ) {
		
	}

	@Override
	public boolean deleteElement( DataControl dataControl , boolean askConfirmation) {
		return false;
	}
	
	@Override
	public void deleteIdentifierReferences( String id ) {
		// this action is done in adaptationProfileDataControl
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[0];
	}

	@Override
	public Object getContent( ) {
		return adaptationRule;
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		return true;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		return false;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		return false;
	}

	@Override
	public String renameElement( String name ) {
		return null;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		// this action is done in adaptationProfileDataControl
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
		for (String flag: adaptationRule.getAdaptedState( ).getFlagsVars( )){
			if (isFlag(flag))
			    varFlagSummary.addFlagReference( flag );
			else 
			    varFlagSummary.addVarReference(flag);
		}
		
	}

	public String getDescription( ) {
		return adaptationRule.getDescription( );
	}


	public void setInitialScene( String initScene ) {
		controller.addTool(new ChangeTargetIdTool(adaptationRule.getAdaptedState( ), initScene));
		//adaptationRule.getAdaptedState( ).setTargetId( initScene );
	}
	
	public String getInitialScene(  ) {
		return adaptationRule.getAdaptedState( ).getTargetId( );
	}

	private AdaptedState getGameState(){
	    return adaptationRule.getAdaptedState();
	}
	
	public boolean moveUOLPropertyUp( int selectedRow ) {
		return	controller.addTool(new MoveObjectTool(adaptationRule.getUOLProperties( ),selectedRow,MoveObjectTool.MODE_UP));
	}

	public boolean moveUOLPropertyDown( int selectedRow ) {
		return	controller.addTool(new MoveObjectTool(adaptationRule.getUOLProperties( ),selectedRow,MoveObjectTool.MODE_DOWN));
	}
	public boolean addFlagAction( int selectedRow ) {
		return controller.addTool(new AddActionTool(adaptationRule.getAdaptedState(), selectedRow));
	}

	public void deleteFlagAction( int selectedRow ) {
		controller.addTool(new DeleteActionTool(adaptationRule, selectedRow));
	}

	public int getFlagActionCount( ) {
		return adaptationRule.getAdaptedState( ).getFlagsVars( ).size( );
	}

	public void setFlag( int rowIndex, String flag ) {
		controller.addTool(new ChangeActionTool(adaptationRule, rowIndex, flag, ChangeActionTool.SET_ID));
	}

	public void change(int rowIndex,String name){
	    //profile.getAdaptedState().change(rowIndex, name);
	    controller.addTool(new ChangeVarFlagTool(adaptationRule.getAdaptedState(),rowIndex,name));
	}
	public String getFlag( int rowIndex ) {
		return this.adaptationRule.getAdaptedState( ).getFlagVar( rowIndex );
	}

	public String getAction( int rowIndex ) {
		return this.adaptationRule.getAdaptedState( ).getAction( rowIndex );
	}

	public boolean isFlag(int rowIndex){
	    return this.adaptationRule.getAdaptedState().isFlag(rowIndex);
	}
	
	public boolean isFlag(String name){
	    return this.adaptationRule.getAdaptedState().isFlag(name);
	}
	
	public String getId( ) {
		return adaptationRule.getId( );
	}

	public void addBlankUOLProperty( int selectedRow ) {
		controller.addTool(new AddUOLPropertyTool(adaptationRule, selectedRow));
	}

	public void deleteUOLProperty( int selectedRow ) {
		controller.addTool(new DeleteUOLPropertyTool(adaptationRule, selectedRow));
	}

	public int getUOLPropertyCount( ) {
		return adaptationRule.getUOLProperties( ).size( );
	}

	public void setUOLPropertyValue( int rowIndex, String string ) {
		controller.addTool(new ChangeUOLPropertyTool(adaptationRule, string, rowIndex, ChangeUOLPropertyTool.SET_VALUE));
	}

	public void setUOLPropertyId( int rowIndex, String string ) {
		controller.addTool(new ChangeUOLPropertyTool(adaptationRule, string, rowIndex, ChangeUOLPropertyTool.SET_ID));
	}
	
	public void setUOLPropertyOp( int rowIndex, String string ) {
		controller.addTool(new ChangeUOLPropertyTool(adaptationRule, string, rowIndex, ChangeUOLPropertyTool.SET_OP));
	}

	public String getUOLPropertyId( int rowIndex ) {
		return this.adaptationRule.getUOLProperties( ).get( rowIndex ).getId( );
	}

	public String getUOLPropertyValue( int rowIndex ) {
		return adaptationRule.getUOLProperties( ).get( rowIndex ).getValue( );
	}
	
	public String getUOLPropertyOp( int rowIndex ) {
		return adaptationRule.getUOLProperties( ).get( rowIndex ).getOperation();
	}

	public void setAction( int rowIndex, String string ) {
		controller.addTool(new ChangeActionTool(adaptationRule, rowIndex, string, ChangeActionTool.SET_VALUE));
	}
	
	public int getValueToSet(int rowIndex){
	    if (adaptationRule.getAdaptedState().getValueToSet(rowIndex)==Integer.MIN_VALUE)
		return 0;
	    else
		return adaptationRule.getAdaptedState().getValueToSet(rowIndex);

	}

	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		// Do nothing
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}

	@Override
	public void recursiveSearch( ) {
		check(getDescription(), TextConstants.getText("Search.Description"));
		check(getId(), "ID");
		check(getInitialScene(), TextConstants.getText("Search.InitialScene"));
		
		for (int i = 0; i < this.getFlagActionCount(); i++){
		    if (isFlag(i))
			check(getFlag(i), TextConstants.getText("Search.Flag"));
		    else
			check(getFlag(i), TextConstants.getText("Search.Var"));
		    
		    check(getAction(i), TextConstants.getText("Search.ActionOverGameState"));
		}
		for (int i = 0; i < this.getUOLPropertyCount(); i++) {
			check(this.getUOLPropertyId(i), TextConstants.getText("Search.LMSPropertyID"));
			check(this.getUOLPropertyValue(i), TextConstants.getText("Search.LMSPropertyValue"));
		}
	}

	@Override
	public List<Searchable> getPathToDataControl(Searchable dataControl) {
		return null;
	}
}
