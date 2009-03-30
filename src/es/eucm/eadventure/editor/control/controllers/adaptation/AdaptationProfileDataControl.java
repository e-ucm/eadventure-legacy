package es.eucm.eadventure.editor.control.controllers.adaptation;

import java.util.ArrayList;
import java.util.List;


import es.eucm.eadventure.common.auxiliar.File;
import es.eucm.eadventure.common.data.adaptation.AdaptationProfile;
import es.eucm.eadventure.common.data.adaptation.AdaptationRule;
import es.eucm.eadventure.common.data.adaptation.AdaptedState;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.tools.adaptation.AddActionTool;
import es.eucm.eadventure.editor.control.tools.adaptation.ChangeActionTool;
import es.eucm.eadventure.editor.control.tools.adaptation.ChangeAdaptationProfileTypeTool;
import es.eucm.eadventure.editor.control.tools.adaptation.ChangeVarFlagTool;
import es.eucm.eadventure.editor.control.tools.adaptation.DeleteActionTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeTargetIdTool;
import es.eucm.eadventure.editor.control.tools.generic.ChangeBooleanValueTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class AdaptationProfileDataControl extends DataControl{

	/**
	 * Data control for each rule
	 */
	private List<AdaptationRuleDataControl> dataControls;

	/**
	 * The profile
	 */
	private AdaptationProfile profile;
	
	//TODO PANEL
	
	private int number;
	
	public AdaptationProfileDataControl( List<AdaptationRule> adpRules, AdaptedState initialState, String path){
		this (new AdaptationProfile (adpRules, initialState, path,false,false));
	}
	
	public AdaptationProfileDataControl(AdaptationProfile profile) {
		number = 0;
		dataControls = new ArrayList<AdaptationRuleDataControl>();
		this.profile = profile;
		
		if (profile != null && profile.getRules() != null)
		for (AdaptationRule rule : profile.getRules()){
			rule.setId(generateId());
			dataControls.add( new AdaptationRuleDataControl(rule) );
		}
	}

	private String generateId(){
		number++;
		return "#"+number;
	}
	
	@Override
	public boolean addElement( int type ) {
		boolean added = false;
		
		if (type == Controller.ADAPTATION_RULE){
			// Auto generate the rule id
			String adpRuleId = generateId();

			// Add thew new adp rule
			AdaptationRule adpRule = new AdaptationRule ();
			adpRule.setId( adpRuleId );
			profile.addRule( adpRule );
			dataControls.add( new AdaptationRuleDataControl( adpRule ) );
			controller.getIdentifierSummary( ).addAdaptationRuleId( adpRuleId );
			//controller.dataModified( );
			added = true;

		}
		return added;
	}
	
	public AdaptationRuleDataControl getLastAdaptationRule(){
		return dataControls.get( dataControls.size( )-1 );
	}

	@Override
	public boolean canAddElement( int type ) {
		return type == Controller.ADAPTATION_RULE;
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
		boolean deleted = false;
		
		String adpRuleId = ( (AdaptationRuleDataControl) dataControl ).getId( );
		String references = String.valueOf( controller.countIdentifierReferences( adpRuleId ) );

		// Ask for confirmation
		if(!askConfirmation || controller.showStrictConfirmDialog( TextConstants.getText( "Operation.DeleteElementTitle" ), TextConstants.getText( "Operation.DeleteElementWarning", new String[] { adpRuleId, references } ) ) ) {
			if( profile.getRules().remove( dataControl.getContent( ) ) ) {
				dataControls.remove( dataControl );
				controller.deleteIdentifierReferences( adpRuleId );
				controller.getIdentifierSummary( ).deleteAdaptationRuleId( adpRuleId );
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
		return new int[]{Controller.ADAPTATION_RULE};
	}

	@Override
	public Object getContent( ) {
		return profile;
	}
	
	public List<AdaptationRuleDataControl> getAdaptationRules(){
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

	public String getFileName(){
		return profile.getPath().substring( Math.max( profile.getPath().lastIndexOf( "/" ), profile.getPath().lastIndexOf( "\\" ) )+1);
	}
	
	@Override
	public String renameElement( String name ) {
		String oldName = null;
		boolean renamed = false;
		if (this.profile.getPath() != null) {
			String[] temp = this.profile.getPath().split("/");
			oldName = temp[temp.length - 1];
		}
		
		// Show confirmation dialog.
		if (name != null || controller.showStrictConfirmDialog( TextConstants.getText( "Operation.RenameAdaptationFile" ), TextConstants.getText( "Operation.RenameAdaptationFile.Message" ) )){
			
			String fileName = name;
			if (name == null)
				//Prompt for file name:
				fileName = controller.showInputDialog( TextConstants.getText( "Operation.RenameAdaptationFile.FileName" ), TextConstants.getText( "Operation.RenameAdaptationFile.FileName.Message" ), getFileName() );
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
						renamed = assessmentFile.renameTo( new File(Controller.getInstance( ).getProjectFolder( ), AssetsController.getCategoryFolder( AssetsController.CATEGORY_ADAPTATION ) +"/"+fileName) );
						//controller.dataModified( );
						this.profile.setPath( AssetsController.getCategoryFolder( AssetsController.CATEGORY_ADAPTATION ) +"/"+fileName );
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
		for (AdaptationRuleDataControl dataControl : dataControls)
			dataControl.updateVarFlagSummary( varFlagSummary );
		
		//Update the initial state
		for (String flag: profile.getAdaptedState().getFlagsVars( )){
		    if (profile.getAdaptedState().isFlag(flag))
			varFlagSummary.addFlagReference( flag );
		    else 
			varFlagSummary.addVarReference(flag);
		}
		
	}

	/**
	 * @return the profile.getInitialState()
	 */
	public AdaptedState getInitialState( ) {
		return profile.getAdaptedState();
	}

	public void setInitialScene( String initScene ) {
		if (profile.getAdaptedState()==null)
			profile.setAdaptedState( new AdaptedState() );
		controller.addTool(new ChangeTargetIdTool(profile.getAdaptedState(), initScene));
	}
	
	public String getInitialScene(  ) {
		return profile.getAdaptedState().getTargetId( );
	}

	public boolean addFlagAction( int selectedRow ) {
		return controller.addTool(new AddActionTool(profile.getAdaptedState(), selectedRow));
	}

	public void deleteFlagAction( int selectedRow ) {
		controller.addTool(new DeleteActionTool(profile, selectedRow));
	}

	public int getFlagActionCount( ) {
		return profile.getAdaptedState().getFlagsVars( ).size( );
	}

	public void setFlag( int rowIndex, String flag ) {
		controller.addTool(new ChangeActionTool(profile, rowIndex, flag, ChangeActionTool.SET_ID));
	}
	
	public void setAction( int rowIndex, String string ) {
		controller.addTool(new ChangeActionTool(profile, rowIndex, string, ChangeActionTool.SET_VALUE));
	}

	public String getFlag( int rowIndex ) {
		return profile.getAdaptedState().getFlagVar( rowIndex );
	}

	public String getAction( int rowIndex ) {
		return profile.getAdaptedState().getAction( rowIndex );
	}
	
	public int getValueToSet(int rowIndex){
	    if (profile.getAdaptedState().getValueToSet(rowIndex)==Integer.MIN_VALUE)
		return 0;
	    else
		return profile.getAdaptedState().getValueToSet(rowIndex);

	}
	public String[][] getAdaptationRulesInfo( ) {
		String[][] info = new String[profile.getRules().size( )][4];
		
		for (int i=0; i<profile.getRules().size( ); i++){
			info[i][0]=profile.getRules().get( i ).getId( );
			info[i][1]=String.valueOf( profile.getRules().get( i ).getUOLProperties( ).size( ));
			if (profile.getRules().get( i ).getAdaptedState( ).getTargetId( )==null)
				info[i][2]="<Not selected>";
			else
				info[i][2]=profile.getRules().get( i ).getAdaptedState( ).getTargetId( );
			info[i][3]=String.valueOf( profile.getRules().get( i ).getAdaptedState( ).getFlagsVars( ).size( ));
		}
		return info;
	}

	/**
	 * @return the profile.getPath()
	 */
	public String getPath( ) {
		return profile.getPath();
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}
	
	@Override
	public void recursiveSearch( ) {
		for (DataControl dc : this.dataControls) {
			dc.recursiveSearch( );
		}
		check("" + number, TextConstants.getText("Search.Number"));
		check(getFileName(), TextConstants.getText("Search.FileName"));
		check(getInitialScene(), TextConstants.getText("Search.InitialScene"));
		check(getPath(), TextConstants.getText("Search.Path"));
	}
	
	
	public void change(int rowIndex,String name){
	    //profile.getAdaptedState().change(rowIndex, name);
	    controller.addTool(new ChangeVarFlagTool(profile.getAdaptedState(),rowIndex,name));
	}
	
	public boolean isFlag(int rowIndex){
	    return this.profile.getAdaptedState().isFlag(rowIndex);
	}
	
	public boolean isScorm2004(){
		return profile.isScorm2004();
	}
	
	public boolean isScorm12(){
		return profile.isScorm12();
	}
	
	
	public void changeToScorm2004Profile(){
	    controller.addTool(new ChangeAdaptationProfileTypeTool(profile,ChangeAdaptationProfileTypeTool.SCORM2004 ,profile.isScorm12(),profile.isScorm2004()));
	}
	
	public void changeToScorm12Profile(){
	    controller.addTool(new ChangeAdaptationProfileTypeTool(profile,ChangeAdaptationProfileTypeTool.SCORM12 ,profile.isScorm12(),profile.isScorm2004()));
	}
	
	public void changeToNormalProfile(){
	    controller.addTool(new ChangeAdaptationProfileTypeTool(profile,ChangeAdaptationProfileTypeTool.NORMAL ,profile.isScorm12(),profile.isScorm2004()));
	}
	
}
