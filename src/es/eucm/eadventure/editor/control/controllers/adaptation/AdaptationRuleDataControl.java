package es.eucm.eadventure.editor.control.controllers.adaptation;

import java.util.List;

import es.eucm.eadventure.common.data.adaptation.AdaptationRule;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.tools.adaptation.AddActionTool;
import es.eucm.eadventure.editor.control.tools.adaptation.AddUOLPropertyTool;
import es.eucm.eadventure.editor.control.tools.adaptation.ChangeActionTool;
import es.eucm.eadventure.editor.control.tools.adaptation.ChangeUOLPropertyTool;
import es.eucm.eadventure.editor.control.tools.adaptation.DeleteActionTool;
import es.eucm.eadventure.editor.control.tools.adaptation.DeleteUOLPropertyTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeTargetIdTool;
import es.eucm.eadventure.editor.control.tools.generic.MoveObjectTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class AdaptationRuleDataControl extends DataControl{

	private AdaptationRule adaptationRule;
	//TODO PANEL
	
	public AdaptationRuleDataControl (AdaptationRule adpRule){
		this.adaptationRule = adpRule;
	}
	
	@Override
	public boolean addElement( int type ) {
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
		return 0;
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
		
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
		for (String flag: adaptationRule.getAdaptedState( ).getFlagsVars( )){
			varFlagSummary.addReference( flag );	
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

	public String getFlag( int rowIndex ) {
		return this.adaptationRule.getAdaptedState( ).getFlagVar( rowIndex );
	}

	public String getAction( int rowIndex ) {
		return this.adaptationRule.getAdaptedState( ).getAction( rowIndex );
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

	public String getUOLPropertyId( int rowIndex ) {
		return this.adaptationRule.getUOLProperties( ).get( rowIndex ).getId( );
	}

	public String getUOLPropertyValue( int rowIndex ) {
		return adaptationRule.getUOLProperties( ).get( rowIndex ).getValue( );
	}

	public void setAction( int rowIndex, String string ) {
		controller.addTool(new ChangeActionTool(adaptationRule, rowIndex, string, ChangeActionTool.SET_VALUE));
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
		for (int i = 0; i < this.getFlagActionCount(); i++)
			check(getFlag(i), TextConstants.getText("Search.Flag"));
		for (int i = 0; i < this.getUOLPropertyCount(); i++) {
			check(this.getUOLPropertyId(i), TextConstants.getText("Search.UOLPropertyID"));
			check(this.getUOLPropertyValue(i), TextConstants.getText("Search.UOLPropertyValue"));
		}
	}


}
