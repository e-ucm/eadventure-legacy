package es.eucm.eadventure.adventureeditor.control.controllers.assessment;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.adventureeditor.control.auxiliar.File;

import es.eucm.eadventure.adventureeditor.control.Controller;
import es.eucm.eadventure.adventureeditor.control.controllers.AssetsController;
import es.eucm.eadventure.adventureeditor.control.controllers.DataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.EffectsController;
import es.eucm.eadventure.adventureeditor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.adventureeditor.data.assessment.AssessmentRule;
import es.eucm.eadventure.adventureeditor.data.assessment.TimedAssessmentRule;
import es.eucm.eadventure.adventureeditor.data.chapterdata.scenes.Scene;
import es.eucm.eadventure.adventureeditor.data.supportdata.FlagSummary;
import es.eucm.eadventure.adventureeditor.gui.TextConstants;

public class AssessmentProfileDataControl extends DataControl{

	private List<AssessmentRuleDataControl> dataControls;
	private List<AssessmentRule> assessmentRules;
	
	private String path;
	
	public AssessmentProfileDataControl( List<AssessmentRule> assessmentRules, String path){
		dataControls = new ArrayList<AssessmentRuleDataControl>();
		this.assessmentRules = assessmentRules;
		
		for (AssessmentRule rule: assessmentRules){
			dataControls.add( new AssessmentRuleDataControl(rule) );
		}
		
		this.path = path;
	}
	
	public String getFileName(){
		return path.substring( Math.max( path.lastIndexOf( "/" ), path.lastIndexOf( "\\" ) )+1);
	}

	
	@Override
	public boolean addElement( int type ) {
		boolean added = false;
		
		if (type == Controller.ASSESSMENT_RULE || type == Controller.TIMED_ASSESSMENT_RULE){
			// Show a dialog asking for the ass rule id
			String assRuleId = controller.showInputDialog( TextConstants.getText( "Operation.AddAssessmentRuleTitle" ), TextConstants.getText( "Operation.AddAssessmentRuleMessage" ), TextConstants.getText( "Operation.AddAssessmentRuleDefaultValue" ) );

			// If some value was typed and the identifier is valid
			if( assRuleId != null && controller.isElementIdValid( assRuleId ) ) {
				// Add thew new ass rule
				AssessmentRule assRule = null;
				if (type  == Controller.TIMED_ASSESSMENT_RULE){
					assRule = new TimedAssessmentRule (assRuleId, AssessmentRule.IMPORTANCE_NORMAL);
				} else {
					assRule = new AssessmentRule (assRuleId, AssessmentRule.IMPORTANCE_NORMAL);
				}
				this.assessmentRules.add( assRule );
				dataControls.add( new AssessmentRuleDataControl( assRule ) );
				controller.getIdentifierSummary( ).addAssessmentRuleId( assRuleId );
				controller.dataModified( );
				added = true;
			}

		}
		return added;
	}
	
	public AssessmentRuleDataControl getLastAssessmentRule(){
		return dataControls.get( dataControls.size( )-1 );
	}

	@Override
	public boolean canAddElement( int type ) {
		return type == Controller.ASSESSMENT_RULE || type == Controller.TIMED_ASSESSMENT_RULE;
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
	public int countIdentifierReferences( String id ) {
		return 0;
	}
	
	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		// Do nothing
	}

	@Override
	public void deleteAssetReferences( String assetPath ) {
		
	}

	@Override
	public boolean deleteElement( DataControl dataControl ) {
		boolean deleted = false;
		
		String assRuleId = ( (AssessmentRuleDataControl) dataControl ).getId( );
		String references = String.valueOf( controller.countIdentifierReferences( assRuleId ) );

		// Ask for confirmation
		if( controller.showStrictConfirmDialog( TextConstants.getText( "Operation.DeleteElementTitle" ), TextConstants.getText( "Operation.DeleteElementWarning", new String[] { assRuleId, references } ) ) ) {
			if( this.assessmentRules.remove( dataControl.getContent( ) ) ) {
				dataControls.remove( dataControl );
				controller.deleteIdentifierReferences( assRuleId );
				controller.getIdentifierSummary( ).deleteAssessmentRuleId( assRuleId );
				controller.dataModified( );
				deleted = true;
			}
		}

		return deleted;
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[]{Controller.ASSESSMENT_RULE, Controller.TIMED_ASSESSMENT_RULE};
	}

	@Override
	public Object getContent( ) {
		return this.assessmentRules;
	}
	
	public List<AssessmentRuleDataControl> getAssessmentRules(){
		return this.dataControls;
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		return true;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = assessmentRules.indexOf( dataControl.getContent( ) );

		if( elementIndex < assessmentRules.size( ) - 1 ) {
			assessmentRules.add( elementIndex + 1, assessmentRules.remove( elementIndex ) );
			dataControls.add( elementIndex + 1, dataControls.remove( elementIndex ) );
			controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = assessmentRules.indexOf( dataControl.getContent( ) );

		if( elementIndex > 0 ) {
			assessmentRules.add( elementIndex - 1, assessmentRules.remove( elementIndex ) );
			dataControls.add( elementIndex - 1, dataControls.remove( elementIndex ) );
			controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean renameElement( ) {
		boolean renamed = false;
		
		// Show confirmation dialog.
		if (controller.showStrictConfirmDialog( TextConstants.getText( "Operation.RenameAssessmentFile" ), TextConstants.getText( "Operation.RenameAssessmentFile.Message" ) )){
			
			//Prompt for file name:
			String fileName = controller.showInputDialog( TextConstants.getText( "Operation.RenameAssessmentFile.FileName" ), TextConstants.getText( "Operation.RenameAssessmentFile.FileName.Message" ), getFileName() );
			if (fileName!=null && !fileName.equals( path.substring( path.lastIndexOf( "/" ) + 1 ) )){
				if (fileName.contains( "/") || fileName.contains( "\\" )){
					controller.showErrorDialog( TextConstants.getText( "Operation.RenameXMLFile.ErrorSlash" ), TextConstants.getText( "Operation.RenameXMLFile.ErrorSlash.Message" ) );
					return false;
				}

				if (!fileName.toLowerCase().endsWith( ".xml" )){
					if (fileName.endsWith( "." )){
						fileName=fileName+"xml";
					}else{
						fileName=fileName+".xml";
					}
				}
				
				//Checks if the file exists. In that case, ask to overwrite it
						File assessmentFile = new File (Controller.getInstance( ).getProjectFolder( ), path );
						renamed = assessmentFile.renameTo( new File(Controller.getInstance( ).getProjectFolder( ), AssetsController.getCategoryFolder( AssetsController.CATEGORY_ASSESSMENT ) +"/"+fileName) );
						controller.dataModified( );
						this.path = AssetsController.getCategoryFolder( AssetsController.CATEGORY_ASSESSMENT ) +"/"+fileName;
			}
			
		}
		
		return renamed;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
	}

	@Override
	public void updateFlagSummary( FlagSummary flagSummary ) {
		for (AssessmentRuleDataControl dataControl : dataControls)
			dataControl.updateFlagSummary( flagSummary );
		
	}

	public String[][] getAssessmentRulesInfo( ) {
		String[][] info = new String[this.assessmentRules.size()][3];
		
		for (int i=0; i<assessmentRules.size( ); i++){
			info[i][0] = assessmentRules.get( i ).getId( );
			info[i][1] = AssessmentRule.IMPORTANCE_VALUES_PRINT[assessmentRules.get( i ).getImportance( )];
			info[i][2] = (assessmentRules.get( i ).getConditions( ).isEmpty( ))?"No":"Yes";
		}
		return info;
	}

	/**
	 * @return the path
	 */
	public String getPath( ) {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath( String path ) {
		this.path = path;
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}

}
