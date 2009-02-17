package es.eucm.eadventure.editor.control.controllers.assessment;

import java.util.List;

import es.eucm.eadventure.common.data.assessment.AssessmentProperty;
import es.eucm.eadventure.common.data.assessment.AssessmentRule;
import es.eucm.eadventure.common.data.assessment.TimedAssessmentEffect;
import es.eucm.eadventure.common.data.assessment.TimedAssessmentRule;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
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

	public String getEffectText( ) {
		return assessmentRule.getText( );
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
		assessmentRule.setConcept( text );
		
	}

	public void setEffectText( String text ) {
		assessmentRule.setText( text );
		controller.dataModified( );
	}
	
	public void setEffectText( int effect, String text ) {
		if (assessmentRule instanceof TimedAssessmentRule){
			TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
			tRule.setText( text, effect );
			controller.dataModified( );
		} else {
			assessmentRule.setText( text );		
			controller.dataModified( );
		}
	}

	public void setImportance( int importance ) {
		assessmentRule.setImportance( importance );
		
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
		boolean elementMoved = false;

		if( selectedRow > 0 ) {
			assessmentRule.getAssessmentProperties( ).add( selectedRow - 1, assessmentRule.getAssessmentProperties( ).remove( selectedRow ) );
			controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;

	}
	
	public boolean movePropertyUp( int selectedRow, int effect ) {
		boolean elementMoved = false;
		if (assessmentRule instanceof TimedAssessmentRule){
			TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
			if(  effect>=0 && effect<tRule.getEffectsCount( ) && selectedRow >0 ) {
				tRule.getEffects( ).get( effect ).getAssessmentProperties( ).add( selectedRow - 1, assessmentRule.getAssessmentProperties( ).remove( selectedRow ) );
				controller.dataModified( );
				elementMoved = true;
			}	
		} else {
			if( selectedRow > 0 ) {
				assessmentRule.getAssessmentProperties( ).add( selectedRow - 1, assessmentRule.getAssessmentProperties( ).remove( selectedRow ) );
				controller.dataModified( );
				elementMoved = true;
			}
		}

		return elementMoved;

	}


	public boolean movePropertyDown( int selectedRow ) {
		boolean elementMoved = false;
		
		if( selectedRow < assessmentRule.getAssessmentProperties( ).size( ) - 1 ) {
			assessmentRule.getAssessmentProperties( ).add( selectedRow + 1, assessmentRule.getAssessmentProperties( ).remove( selectedRow ) );
			controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;	
	}
	
	public boolean movePropertyDown( int selectedRow, int effect ) {
		boolean elementMoved = false;

		if (assessmentRule instanceof TimedAssessmentRule){
			TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
			if( effect>=0 && effect<tRule.getEffectsCount( ) && selectedRow < tRule.getEffects( ).get( effect ).getAssessmentProperties( ).size( ) - 1 ) {
				tRule.getEffects( ).get( effect ).getAssessmentProperties( ).add( selectedRow + 1, assessmentRule.getAssessmentProperties( ).remove( selectedRow ) );
				controller.dataModified( );
				elementMoved = true;
			}	
		} else {
			if( selectedRow < assessmentRule.getAssessmentProperties( ).size( ) - 1 ) {
				assessmentRule.getAssessmentProperties( ).add( selectedRow + 1, assessmentRule.getAssessmentProperties( ).remove( selectedRow ) );
				controller.dataModified( );
				elementMoved = true;
			}			
		}

		return elementMoved;	
	}


	public void addBlankProperty( int selectedRow ) {
		assessmentRule.getAssessmentProperties( ).add( selectedRow, new AssessmentProperty("PropertyId", 0) );
		controller.dataModified( );
	}
	
	public boolean addBlankProperty( int selectedRow, int effect ) {
		boolean added = false;
		if (assessmentRule instanceof TimedAssessmentRule){
			TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
			if (effect>=0 && effect<tRule.getEffectsCount( )){
				TimedAssessmentEffect currentEffect = tRule.getEffects( ).get( effect ); 
				currentEffect.getAssessmentProperties( ).add( selectedRow, new AssessmentProperty("PropertyId", 0) );
				controller.dataModified( );
				added = true;
			}
		} else {
			assessmentRule.getAssessmentProperties( ).add( selectedRow, new AssessmentProperty("PropertyId", 0) );
			controller.dataModified( );
			added = true;
		}

		return added;
	}


	public void deleteProperty( int selectedRow ) {
		if (selectedRow >=0 && selectedRow <assessmentRule.getAssessmentProperties( ).size( )){
			assessmentRule.getAssessmentProperties( ).remove( selectedRow );
			controller.dataModified( );
		}

		
	}
	
	public void deleteProperty( int selectedRow, int effect ) {
			if (assessmentRule instanceof TimedAssessmentRule){
				TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
				if (effect>=0 && effect<tRule.getEffectsCount( )){
					tRule.getEffects( ).get( effect ).getAssessmentProperties( ).remove( selectedRow );
					controller.dataModified( );
				}
			} else {

		if (selectedRow >=0 && selectedRow <assessmentRule.getAssessmentProperties( ).size( )){
			assessmentRule.getAssessmentProperties( ).remove( selectedRow );
			controller.dataModified( );
		}
			}

		
	}


	public int getPropertyCount( ) {
		return assessmentRule.getAssessmentProperties( ).size( );
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
				int value = Integer.parseInt( string );
				assessmentRule.getAssessmentProperties( ).get( rowIndex ).setValue( value );
				controller.dataModified( );
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
					int value = Integer.parseInt( string );
					property.setValue( value );
					controller.dataModified( );
				} catch (Exception e){
					//Display error message
					controller.showErrorDialog( TextConstants.getText("AssessmentRule.InvalidPropertyID"), TextConstants.getText("AssessmentRule.InvalidPropertyID.Message") );
				}
			}
		}else {
			if (rowIndex >=0 && rowIndex <assessmentRule.getAssessmentProperties( ).size( )){
				//Check only integers are set
				try{
					int value = Integer.parseInt( string );
					assessmentRule.getAssessmentProperties( ).get( rowIndex ).setValue( value );
				} catch (Exception e){
					//Display error message
					controller.showErrorDialog( TextConstants.getText("AssessmentRule.InvalidPropertyID"), TextConstants.getText("AssessmentRule.InvalidPropertyID.Message") );
					controller.dataModified( );
				}
				
			}
		}

		
		

		
	}


	public void setPropertyId( int rowIndex, String string ) {
		if (rowIndex >=0 && rowIndex <assessmentRule.getAssessmentProperties( ).size( )){
			if (controller.isElementIdValid( string )){
				assessmentRule.getAssessmentProperties( ).get( rowIndex ).setId( string );
				controller.dataModified( );
			}
		}
		
	}
	
	public void setPropertyId( int rowIndex, int effect, String string ) {
		if (assessmentRule instanceof TimedAssessmentRule){
			TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
			AssessmentProperty property = tRule.getProperty( rowIndex, effect );
			if (property!=null){
				property.setId( string );
				controller.dataModified( );
			}
		}else {
			if (rowIndex >=0 && rowIndex <assessmentRule.getAssessmentProperties( ).size( )){
				if (controller.isElementIdValid( string )){
					assessmentRule.getAssessmentProperties( ).get( rowIndex ).setId( string );
					controller.dataModified( );
				}
			}

		}

		
		
	}

	public String getPropertyId( int rowIndex ) {
		return this.assessmentRule.getAssessmentProperties( ).get( rowIndex ).getId( );
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

	public int getPropertyValue( int rowIndex ) {
		return this.assessmentRule.getAssessmentProperties( ).get( rowIndex ).getValue( );
	}
	
	public int getPropertyValue( int rowIndex, int effect ) {
		if (assessmentRule instanceof TimedAssessmentRule){
			TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
			AssessmentProperty property = tRule.getProperty( rowIndex, effect );
			if (property!=null){
				return property.getValue( );
			}else
				return Integer.MIN_VALUE;
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
			tRule.setMinTime( time, effect );
			controller.dataModified( );
		}
	}
	
	public void setMaxTime (int time, int effect){
		TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
		if (effect>=0 && effect<tRule.getEffectsCount( )){
			tRule.setMaxTime( time, effect );
			controller.dataModified( );
		}
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
		tRule.addEffect( );
	}
	
	public void removeEffectBlock(int currentIndex ){
		TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
		if (currentIndex>=0 && currentIndex<tRule.getEffectsCount( ))
			tRule.getEffects( ).remove( currentIndex );
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}

}
