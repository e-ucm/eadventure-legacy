package es.eucm.eadventure.editor.control.controllers.assessment;

import java.util.List;

import es.eucm.eadventure.common.data.assessment.AssessmentProperty;
import es.eucm.eadventure.common.data.assessment.AssessmentRule;
import es.eucm.eadventure.common.data.assessment.TimedAssessmentEffect;
import es.eucm.eadventure.common.data.assessment.TimedAssessmentRule;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.tools.assessment.AddAssessmentPropertyTool;
import es.eucm.eadventure.editor.control.tools.assessment.AddEffectTool;
import es.eucm.eadventure.editor.control.tools.assessment.ChangeMinTimeValueTool;
import es.eucm.eadventure.editor.control.tools.assessment.ChangeUsesEndCondition;
import es.eucm.eadventure.editor.control.tools.assessment.DeleteAssessmentPropertyTool;
import es.eucm.eadventure.editor.control.tools.assessment.DeleteEffectTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeIdTool;
import es.eucm.eadventure.editor.control.tools.generic.ChangeIntegerValueTool;
import es.eucm.eadventure.editor.control.tools.generic.ChangeStringValueTool;
import es.eucm.eadventure.editor.control.tools.generic.MoveObjectTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class AssessmentRuleDataControl extends DataControl{

	private AssessmentRule assessmentRule;
	
	private ConditionsController conditionsController;
	
	private ConditionsController initConditionsController;
	
	private ConditionsController endConditionsController;
		
	public AssessmentRuleDataControl (AssessmentRule assessmentRule){
		this.assessmentRule = assessmentRule;
		
		// Create subcontrollers
		if (this.isTimedRule( )){
			TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
			initConditionsController = new ConditionsController( tRule.getInitConditions( ) );
			endConditionsController = new ConditionsController( tRule.getEndConditions( ) );
		}else {
			conditionsController = new ConditionsController( assessmentRule.getConditions( ) );	
		}
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
		return true;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		return 0;
	}
	
	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		// Do nothing
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
		return assessmentRule;
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
		String oldName = assessmentRule.getId();
		
		// Show a dialog asking for the ass rule id
		String assRuleId = name;
		if (name == null)
			assRuleId = controller.showInputDialog( TextConstants.getText( "Operation.RenameAssessmentRuleTitle" ), TextConstants.getText( "Operation.RenameAssessmentRuleMessage" ), TextConstants.getText( "Operation.AddAssessmentRuleDefaultValue" ) );

		// If some value was typed and the identifier is valid
		if( assRuleId != null && controller.isElementIdValid( assRuleId ) ) {
			controller.deleteIdentifierReferences( assessmentRule.getId( ) );
			assessmentRule.setId( assRuleId );
			return oldName;
		}
		return null;
	}
	


	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
		if (this.isTimedRule( )){
			TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
			ConditionsController.updateVarFlagSummary( varFlagSummary, tRule.getInitConditions( ) );
			ConditionsController.updateVarFlagSummary( varFlagSummary, tRule.getEndConditions( ) );
		}else
		ConditionsController.updateVarFlagSummary( varFlagSummary, assessmentRule.getConditions( ) );
		
	}

	public String getConcept( ) {
		return assessmentRule.getConcept( );
	}
	
	public String getEffectText( int effect ) {
		if (assessmentRule instanceof TimedAssessmentRule){
			TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
			return tRule.getEffects( ).get( effect ).getText( );
		} else {
			return assessmentRule.getText( );
		}
	}

	public void setConcept( String text ) {
		controller.addTool(new ChangeStringValueTool(assessmentRule, text, "getConcept", "setConcept"));
		
	}


	public void setEffectText( String text ) {
		controller.addTool(new ChangeStringValueTool(assessmentRule, text, "getText", "setText"));
	}


	public void setEffectText( int effect, String text ) {
		if (assessmentRule instanceof TimedAssessmentRule){
			TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
			if (effect >=0 && effect<tRule.getEffectsCount() ){
				controller.addTool(new ChangeStringValueTool(tRule.getEffects().get(effect), text, "getText", "setText"));
			}
		} else {
			setEffectText ( text );
		}
	}

	public void setImportance( int importance ) {
		controller.addTool(new ChangeIntegerValueTool(assessmentRule, importance, "getImportance", "setImportance"));
	}
	
	public int getImportance( ) {
		return assessmentRule.getImportance(  );
		
	}


	public ConditionsController getConditions( ) {
		return this.conditionsController;
	}
	
	public ConditionsController getInitConditions( ) {
		if (assessmentRule instanceof TimedAssessmentRule){
			return this.initConditionsController;
		}
		return this.conditionsController;
	}
	
	public ConditionsController getEndConditions( ) {
		if (assessmentRule instanceof TimedAssessmentRule){
			return this.endConditionsController;
		}
		return this.conditionsController;
	}




	public boolean movePropertyUp( int selectedRow ) {
		return	controller.addTool(new MoveObjectTool(assessmentRule.getAssessmentProperties( ),selectedRow,MoveObjectTool.MODE_UP));
	}

	
	public boolean movePropertyUp( int selectedRow, int effect ) {
		if (assessmentRule instanceof TimedAssessmentRule){
			TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
			if(  effect>=0 && effect<tRule.getEffectsCount( ) && selectedRow >0 ) {
				return	controller.addTool(new MoveObjectTool(
						tRule.getEffects( ).get( effect ).getAssessmentProperties( ),selectedRow,MoveObjectTool.MODE_UP));
			}	
		} else {
			return movePropertyUp( selectedRow );
		}

		return false;

	}



	public boolean movePropertyDown( int selectedRow ) {
		return	controller.addTool(new MoveObjectTool(assessmentRule.getAssessmentProperties( ),selectedRow,MoveObjectTool.MODE_DOWN));
	}
	

	public boolean movePropertyDown( int selectedRow, int effect ) {
		if (assessmentRule instanceof TimedAssessmentRule){
			TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
			if( effect>=0 && effect<tRule.getEffectsCount( ) && selectedRow < tRule.getEffects( ).get( effect ).getAssessmentProperties( ).size( ) - 1 ) {
				return	controller.addTool(new MoveObjectTool(tRule.getEffects( ).get( effect ).getAssessmentProperties( ),selectedRow,MoveObjectTool.MODE_UP));
			}	
		} else {
			return movePropertyDown(selectedRow);
		}

		return false;	
	}





	
	public boolean addBlankProperty( int selectedRow, int effect ) {
		boolean added = false;
		if (assessmentRule instanceof TimedAssessmentRule){
			TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
			if (effect>=0 && effect<tRule.getEffectsCount( )){
				TimedAssessmentEffect currentEffect = tRule.getEffects( ).get( effect );
				added = controller.addTool(new AddAssessmentPropertyTool(currentEffect.getAssessmentProperties(),selectedRow));

			}
		} else {

			added = controller.addTool(new AddAssessmentPropertyTool(assessmentRule.getAssessmentProperties(),selectedRow));;
		}

		return added;
	}


	public boolean deleteProperty( int selectedRow ) {
		return controller.addTool(new DeleteAssessmentPropertyTool(assessmentRule.getAssessmentProperties(),selectedRow));
	}
	

	public boolean deleteProperty( int selectedRow, int effect ) {
		if (assessmentRule instanceof TimedAssessmentRule){
			TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
			if (effect>=0 && effect<tRule.getEffectsCount( )){
				return controller.addTool(new DeleteAssessmentPropertyTool(tRule.getEffects( ).get( effect ).getAssessmentProperties( )
						,selectedRow));
			}
		} 
		else {
			return deleteProperty ( selectedRow );
		}
		return false;
	}

	public int getPropertyCount( int effect ) {
		if (assessmentRule instanceof TimedAssessmentRule){
			TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
			if (effect>=0 && effect<tRule.getEffectsCount( ))
				return tRule.getEffects( ).get( effect ).getAssessmentProperties( ).size( );
			else
				return 0;
		} else
		return assessmentRule.getAssessmentProperties( ).size( );
	}


	

	public void setPropertyValue( int rowIndex, String string ) {
		if (rowIndex >=0 && rowIndex <assessmentRule.getAssessmentProperties( ).size( )){
			//Check only integers are set
			
			try{
				controller.addTool(new ChangeStringValueTool(assessmentRule.getAssessmentProperties( ).get( rowIndex ),
						string, "getValue", "setValue"));
			} catch (Exception e){
				//Display error message
				controller.showErrorDialog( TextConstants.getText("AssessmentRule.InvalidPropertyID"), TextConstants.getText("AssessmentRule.InvalidPropertyID.Message") );
			}
			
		}

		
		
	}
	
	public void setPropertyValue( int rowIndex, int effect, String string ) {

		if (assessmentRule instanceof TimedAssessmentRule){
			TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
			AssessmentProperty property = tRule.getProperty( rowIndex, effect );
			if (property!=null){
				try{
					controller.addTool(new ChangeStringValueTool(property,	string, "getValue", "setValue"));

				} catch (Exception e){
					//Display error message
					controller.showErrorDialog( TextConstants.getText("AssessmentRule.InvalidPropertyID"), TextConstants.getText("AssessmentRule.InvalidPropertyID.Message") );
				}
			}
		}else {
			setPropertyValue ( rowIndex, string);

		}
	}


	public void setPropertyId( int rowIndex, String string ) {
		if (rowIndex >=0 && rowIndex <assessmentRule.getAssessmentProperties( ).size( )){
			if (controller.isElementIdValid( string )){
				controller.addTool(new ChangeIdTool(assessmentRule.getAssessmentProperties( ).get( rowIndex ), string ));
			}
		}

	}


	public void setPropertyId( int rowIndex, int effect, String string ) {
		if (assessmentRule instanceof TimedAssessmentRule){
			TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
			AssessmentProperty property = tRule.getProperty( rowIndex, effect );
			if (property!=null){
				controller.addTool(new ChangeIdTool(property, string ));
			}
		}else {
			if (rowIndex >=0 && rowIndex <assessmentRule.getAssessmentProperties( ).size( )){
				if (controller.isElementIdValid( string )){
					controller.addTool(new ChangeIdTool(assessmentRule.getAssessmentProperties( ).get( rowIndex ), string ));
				}
			}

		}

		
		
	}

	public String getPropertyId( int rowIndex, int effect ) {
		if (assessmentRule instanceof TimedAssessmentRule){
			TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
			AssessmentProperty property = tRule.getProperty( rowIndex, effect );
			if (property!=null){
				return property.getId( );
			}else
				return null;
		}else {
			return this.assessmentRule.getAssessmentProperties( ).get( rowIndex ).getId( );
		}
		
	}

	public String getPropertyValue( int rowIndex, int effect ) {
		if (assessmentRule instanceof TimedAssessmentRule){
			TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
			AssessmentProperty property = tRule.getProperty( rowIndex, effect );
			if (property!=null){
				return property.getValue( );
			}else
				return "";
		}else {
			return this.assessmentRule.getAssessmentProperties( ).get( rowIndex ).getValue( );
		}
	}

	public String getId( ) {
		return assessmentRule.getId( );
	}
	
	public boolean isTimedRule (){
		return assessmentRule!=null && assessmentRule instanceof TimedAssessmentRule;
	}

	public int getMinTime (int effect){
			TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
			return tRule.getMinTime( effect );
	}
	
	public int getMaxTime (int effect){
		TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
		return tRule.getMaxTime( effect );
		
	}

	public void setMinTime (int time, int effect){
		TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
		if (effect>=0 && effect<tRule.getEffectsCount( )){
			controller.addTool(new ChangeMinTimeValueTool(tRule.getEffects().get(effect), time));
		}
	}
	
	public void setMaxTime (int time, int effect){
		TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
		if (effect>=0 && effect<tRule.getEffectsCount( )){
			controller.addTool(new ChangeIntegerValueTool(tRule.getEffects().get(effect), time, "getMaxTime", "setMaxTime"));		}
	}
	
	public int getEffectsCount(){
		TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
		return tRule.getEffectsCount( );
	}

	public String[] getEffectNames(){
		TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
		String [] names = new String[tRule.getEffectsCount( )];
		for (int i=1; i<=names.length; i++){
			names[i-1] = Integer.toString( i );
		}
		return names;
	}
	
	public void addEffectBlock( int index ){
		TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
		controller.addTool(new AddEffectTool(tRule, index));
	}

	public void removeEffectBlock(int currentIndex ){
		TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
		controller.addTool(new DeleteEffectTool(tRule, currentIndex));
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}

	@Override
	public void recursiveSearch() {
		
		check(this.getConcept(), TextConstants.getText("Search.Concept"));
		
		if (assessmentRule instanceof AssessmentRule){
			for (int j = 0; j < this.getPropertyCount(-1); j++)
				check(this.getPropertyId(j,-1), TextConstants.getText("Search.PropertyID"));
		
			check(this.getEffectText(-1), TextConstants.getText("Search.EffectText"));
		
		}
		check(this.getId(), "ID");
		check(this.getConditions(), TextConstants.getText("Search.Conditions"));
		
		if (assessmentRule instanceof TimedAssessmentRule){
		for (int i = 0; i < this.getEffectsCount(); i++) {
			check(this.getEffectNames()[i], TextConstants.getText("Search.EffectName"));
			check(this.getEffectText(i), TextConstants.getText("Search.EffectText"));
			for (int j = 0; j < this.getPropertyCount(i); j++)
				check(this.getPropertyId(j,i), TextConstants.getText("Search.PropertyID"));
		}
		}
		check(this.getEndConditions(), TextConstants.getText("Search.EndConditions"));
	}

	public boolean isUsesEndConditions() {
		if (assessmentRule instanceof TimedAssessmentRule) {
			return ((TimedAssessmentRule) assessmentRule).isUsesEndConditions();
		}
		return false;
	}

	public void setUsesEndConditions(boolean selected) {
		Controller.getInstance().addTool(new ChangeUsesEndCondition((TimedAssessmentRule) assessmentRule, selected));
	}

}
