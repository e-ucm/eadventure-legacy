package es.eucm.eadventure.editor.control.controllers.assessment;

import java.util.ArrayList;
import java.util.List;


import es.eucm.eadventure.common.auxiliar.File;
import es.eucm.eadventure.common.data.assessment.AssessmentProfile;
import es.eucm.eadventure.common.data.assessment.AssessmentRule;
import es.eucm.eadventure.common.data.assessment.TimedAssessmentRule;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.tools.adaptation.ChangeAdaptationProfileTypeTool;
import es.eucm.eadventure.editor.control.tools.animation.ChangeAssessmentProfileTypeTool;
import es.eucm.eadventure.editor.control.tools.assessment.ChangeReportSettingsTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class AssessmentProfileDataControl extends DataControl{

	/**
	 * Controllers for each assessment rule
	 */
	private List<AssessmentRuleDataControl> dataControls;

	/**
	 * The profile
	 */
	private AssessmentProfile profile;
	
	public AssessmentProfileDataControl ( AssessmentProfile profile ){
		dataControls = new ArrayList<AssessmentRuleDataControl>();
		this.profile = profile;
		for (AssessmentRule rule: profile.getRules()){
			dataControls.add( new AssessmentRuleDataControl(rule) );
		}
	}
	
	public AssessmentProfileDataControl( List<AssessmentRule> assessmentRules, String path){
		this( new AssessmentProfile (assessmentRules, path) );
	}
	
	public String getFileName(){
		return profile.getPath().substring( Math.max( profile.getPath().lastIndexOf( "/" ), profile.getPath().lastIndexOf( "\\" ) )+1);
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
				this.profile.getRules().add( assRule );
				dataControls.add( new AssessmentRuleDataControl( assRule ) );
				controller.getIdentifierSummary( ).addAssessmentRuleId( assRuleId );
				//controller.dataModified( );
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
	public boolean deleteElement( DataControl dataControl , boolean askConfirmation) {
		boolean deleted = false;
		
		String assRuleId = ( (AssessmentRuleDataControl) dataControl ).getId( );
		String references = String.valueOf( controller.countIdentifierReferences( assRuleId ) );

		// Ask for confirmation
		if(!askConfirmation || controller.showStrictConfirmDialog( TextConstants.getText( "Operation.DeleteElementTitle" ), TextConstants.getText( "Operation.DeleteElementWarning", new String[] { assRuleId, references } ) ) ) {
			if( this.profile.getRules().remove( dataControl.getContent( ) ) ) {
				dataControls.remove( dataControl );
				controller.deleteIdentifierReferences( assRuleId );
				controller.getIdentifierSummary( ).deleteAssessmentRuleId( assRuleId );
				//controller.dataModified( );
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
		return profile;
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
		int elementIndex = profile.getRules().indexOf( dataControl.getContent( ) );

		if( elementIndex < profile.getRules().size( ) - 1 ) {
			profile.getRules().add( elementIndex + 1, profile.getRules().remove( elementIndex ) );
			dataControls.add( elementIndex + 1, dataControls.remove( elementIndex ) );
			//controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = profile.getRules().indexOf( dataControl.getContent( ) );

		if( elementIndex > 0 ) {
			profile.getRules().add( elementIndex - 1, profile.getRules().remove( elementIndex ) );
			dataControls.add( elementIndex - 1, dataControls.remove( elementIndex ) );
			//controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public String renameElement( String name ) {
		boolean renamed = false;
		String oldName = null;
		if (this.profile.getPath() != null) {
			String[] temp = this.profile.getPath().split("/");
			oldName = temp[temp.length - 1]; 
		}

		
		// Show confirmation dialog.
		if (name != null || controller.showStrictConfirmDialog( TextConstants.getText( "Operation.RenameAssessmentFile" ), TextConstants.getText( "Operation.RenameAssessmentFile.Message" ) )){
			
			//Prompt for file name:
			String fileName = name;
			if (name == null)
				fileName = controller.showInputDialog( TextConstants.getText( "Operation.RenameAssessmentFile.FileName" ), TextConstants.getText( "Operation.RenameAssessmentFile.FileName.Message" ), getFileName() );
			if (fileName!=null && !fileName.equals( profile.getPath().substring( profile.getPath().lastIndexOf( "/" ) + 1 ) )){
				if (fileName.contains( "/") || fileName.contains( "\\" )){
					controller.showErrorDialog( TextConstants.getText( "Operation.RenameXMLFile.ErrorSlash" ), TextConstants.getText( "Operation.RenameXMLFile.ErrorSlash.Message" ) );
					return null;
				}

				if (!fileName.toLowerCase().endsWith( ".xml" )){
					if (fileName.endsWith( "." )){
						fileName=fileName+"xml";
					}else{
						fileName=fileName+".xml";
					}
				}
				
				//Checks if the file exists. In that case, ask to overwrite it
						File assessmentFile = new File (Controller.getInstance( ).getProjectFolder( ), profile.getPath() );
						renamed = assessmentFile.renameTo( new File(Controller.getInstance( ).getProjectFolder( ), AssetsController.getCategoryFolder( AssetsController.CATEGORY_ASSESSMENT ) +"/"+fileName) );
						//controller.dataModified( );
						profile.setPath( AssetsController.getCategoryFolder( AssetsController.CATEGORY_ASSESSMENT ) +"/"+fileName );
			}
			
		}
		
		if (renamed)
			return oldName;
		else
			return null;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
		for (AssessmentRuleDataControl dataControl : dataControls)
			dataControl.updateVarFlagSummary( varFlagSummary );
		
	}

    /**
     * String values for the different importance values (for printing)
     */
    public static final String[] IMPORTANCE_VALUES_PRINT = {
        "Very low",
        "Low",
        "Normal",
        "High",
        "Very high"
    };
	
	public String[][] getAssessmentRulesInfo( ) {
		String[][] info = new String[this.profile.getRules().size()][3];
		
		for (int i=0; i<profile.getRules().size( ); i++){
			info[i][0] = profile.getRules().get( i ).getId( );
			info[i][1] = IMPORTANCE_VALUES_PRINT[profile.getRules().get( i ).getImportance( )];
			info[i][2] = (profile.getRules().get( i ).getConditions( ).isEmpty( ))?TextConstants.getText("GeneralText.No"):TextConstants.getText("GeneralText.Yes");
		}
		return info;
	}

	/**
	 * @return the path
	 */
	public String getPath( ) {
		return profile.getPath();
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}
	
	/**
	 * @return the showReportAtEnd
	 */
	public boolean isShowReportAtEnd() {
		return profile.isShowReportAtEnd();
	}

	/**
	 * @param showReportAtEnd the showReportAtEnd to set
	 */
	public void setShowReportAtEnd(boolean showReportAtEnd) {
		controller.addTool(new ChangeReportSettingsTool(profile, showReportAtEnd, ChangeReportSettingsTool.SHOW_REPORT));
	}
	
	public boolean isSendByEmail() {
		return profile.isSendByEmail();
	}
	
	public void setSendByEmail(boolean sendByEmail) {
		controller.addTool(new ChangeReportSettingsTool(profile, sendByEmail, ChangeReportSettingsTool.SEND));
	}
	
	public String getEmail() {
		return profile.getEmail();
	}
	
	public void setEmail(String email) {
		controller.addTool(new ChangeReportSettingsTool(profile, email, ChangeReportSettingsTool.EMAIL));
	}

	public String getSmtpServer() {
		return profile.getSmtpServer();
	}

	public boolean isSmtpSSL() {
		return profile.isSmtpSSL();
	}

	public String getSmtpPort() {
		return profile.getSmtpPort();
	}

	public String getSmtpUser() {
		return profile.getSmtpUser();
	}

	public String getSmtpPwd() {
		return profile.getSmtpPwd();
	}

	public void setSmtpServer(String smtpServer) {
		controller.addTool(new ChangeReportSettingsTool(profile, smtpServer, ChangeReportSettingsTool.SMTP_SERVER));
	}
	
	public void setSmtpSSL(boolean smtpSSL) {
		controller.addTool(new ChangeReportSettingsTool(profile, smtpSSL, ChangeReportSettingsTool.SMTP_SSL));
	}
	
	public void setSmtpPort(String smtpPort) {
		controller.addTool(new ChangeReportSettingsTool(profile, smtpPort, ChangeReportSettingsTool.SMTP_PORT));
	}
	
	public void setSmtpUser(String smtpUser) {
		controller.addTool(new ChangeReportSettingsTool(profile, smtpUser, ChangeReportSettingsTool.SMTP_USER));
	}
	
	public void setSmtpPwd(String smtpPwd) {
		controller.addTool(new ChangeReportSettingsTool(profile, smtpPwd, ChangeReportSettingsTool.SMTP_PWD));
	}
	
	public boolean isScorm12(){
		return profile.isScorm12();
	}
	
	public boolean isScorm2004(){
		return profile.isScorm2004();
	}
	
	public void changeToScorm2004Profile(){
	    controller.addTool(new ChangeAssessmentProfileTypeTool(profile,ChangeAssessmentProfileTypeTool.SCORM2004 ,profile.isScorm12(),profile.isScorm2004()));
	}
	
	public void changeToScorm12Profile(){
	    controller.addTool(new ChangeAssessmentProfileTypeTool(profile,ChangeAssessmentProfileTypeTool.SCORM12 ,profile.isScorm12(),profile.isScorm2004()));
	}
	
	public void changeToNormalProfile(){
	    controller.addTool(new ChangeAssessmentProfileTypeTool(profile,ChangeAssessmentProfileTypeTool.NORMAL ,profile.isScorm12(),profile.isScorm2004()));
	}
	@Override
	public void recursiveSearch( ) {
		for (DataControl dc : dataControls)
			dc.recursiveSearch( );
		check(getEmail(), TextConstants.getText("Search.EMail"));
		check(this.getPath(), TextConstants.getText("Search.Path"));
		check(this.getSmtpPort(), TextConstants.getText("Search.SMTPPort"));
		check(this.getSmtpServer(), TextConstants.getText("Search.SMPTServer"));
		check(this.getSmtpUser(), TextConstants.getText("Search.SMPTUser"));
	}
}
