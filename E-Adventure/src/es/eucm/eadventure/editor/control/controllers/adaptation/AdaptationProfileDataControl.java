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
		this (new AdaptationProfile (adpRules, initialState, path));
	}
	
	public AdaptationProfileDataControl(AdaptationProfile profile) {
		number = 0;
		dataControls = new ArrayList<AdaptationRuleDataControl>();
		this.profile = profile;
		
		for (AdaptationRule rule: profile.getRules()){
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
			controller.getIdentifierSummary( ).addAssessmentRuleId( adpRuleId );
			controller.dataModified( );
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
			controller.dataModified( );
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
			controller.dataModified( );
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
						controller.dataModified( );
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
		for (String flag: profile.getInitialState().getFlagsVars( )){
			varFlagSummary.addFlagReference( flag );	
		}
		
	}

	/**
	 * @return the profile.getInitialState()
	 */
	public AdaptedState getInitialState( ) {
		return profile.getInitialState();
	}

	/**
	 * @param profile.getInitialState() the profile.getInitialState() to set
	 */
	public void setInitialState( AdaptedState initialState ) {
		this.profile.setInitialState( profile.getInitialState() );
	}

	public void setInitialScene( String initScene ) {
		if (profile.getInitialState()==null)
			profile.setInitialState( new AdaptedState() );
		profile.getInitialState().setInitialScene( initScene );
	}
	
	public String getInitialScene(  ) {
		return profile.getInitialState().getInitialScene( );
	}

	public boolean addFlagAction( int selectedRow ) {
		boolean added=false;
		//Check there is at least one flag

		String[] flags = Controller.getInstance( ).getVarFlagSummary( ).getFlags( );
		if (flags!=null && flags.length>0){

			//	By default, the flag is activated. Default flag will be the first one
			profile.getInitialState().addActivatedFlag( flags[0] );
			added=true;
		}
		
		//Otherwise, prompt error message
		// If the list had no elements, show an error message
		else
			Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Adaptation.ErrorNoFlags.Title" ), TextConstants.getText( "Adaptation.ErrorNoFlags" ) );
		
		return added;
		
	}

	public void deleteFlagAction( int selectedRow ) {
		if (selectedRow >=0 && selectedRow <profile.getInitialState().getFlagsVars( ).size( )){
			profile.getInitialState().removeFlagVar( selectedRow );
			controller.updateFlagSummary( );
		}
	}

	public int getFlagActionCount( ) {
		return profile.getInitialState().getFlagsVars( ).size( );
	}

	public void changeAction( int rowIndex ) {
		if (rowIndex >=0 && rowIndex <profile.getInitialState().getFlagsVars( ).size( )){
			profile.getInitialState().changeAction( rowIndex );
		}

		
	}

	public void setFlag( int rowIndex, String flag ) {
		if (rowIndex >=0 && rowIndex <profile.getInitialState().getFlagsVars(  ).size( )){
			profile.getInitialState().changeFlag( rowIndex, flag );
			controller.updateFlagSummary( );
		}
		
	}

	public String getFlag( int rowIndex ) {
		return profile.getInitialState().getFlagVar( rowIndex );
	}

	public String getAction( int rowIndex ) {
		return profile.getInitialState().getAction( rowIndex );
	}

	public String[][] getAdaptationRulesInfo( ) {
		String[][] info = new String[profile.getRules().size( )][4];
		
		for (int i=0; i<profile.getRules().size( ); i++){
			info[i][0]=profile.getRules().get( i ).getId( );
			info[i][1]=String.valueOf( profile.getRules().get( i ).getUOLProperties( ).size( ));
			if (profile.getRules().get( i ).getAdaptedState( ).getInitialScene( )==null)
				info[i][2]="<Not selected>";
			else
				info[i][2]=profile.getRules().get( i ).getAdaptedState( ).getInitialScene( );
			info[i][3]=String.valueOf( profile.getRules().get( i ).getAdaptedState( ).getFlagsVars( ).size( ));
		}
		return info;
	}

	public void setAction( int rowIndex, String string ) {
		if (!profile.getInitialState().getAction( rowIndex ).equals( string ))
			profile.getInitialState().changeAction( rowIndex );
		
		
	}

	/**
	 * @return the profile.getPath()
	 */
	public String getPath( ) {
		return profile.getPath();
	}

	/**
	 * @param profile.getPath() the profile.getPath() to set
	 */
	public void setPath( String path) {
		this.profile.setPath( path );
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}

}
