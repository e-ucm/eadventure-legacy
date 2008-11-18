package es.eucm.eadventure.editor.control.controllers.adaptation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import es.eucm.eadventure.common.data.adaptation.AdaptationRule;
import es.eucm.eadventure.common.data.adaptation.AdaptedState;
import es.eucm.eadventure.common.data.assessment.AssessmentRule;
import es.eucm.eadventure.common.data.chapter.scenes.Scene;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.auxiliar.File;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.data.support.FlagSummary;
import es.eucm.eadventure.editor.gui.TextConstants;

public class AdaptationProfileDataControl extends DataControl{

	private List<AdaptationRuleDataControl> dataControls;
	private List<AdaptationRule> adaptationRules;
	private AdaptedState initialState;
	private String path;
	
	//TODO PANEL
	
	private int number;
	public AdaptationProfileDataControl( List<AdaptationRule> adpRules, AdaptedState initialState, String path){
		number = 0;
		dataControls = new ArrayList<AdaptationRuleDataControl>();
		this.adaptationRules = adpRules;
		this.initialState = initialState;
		
		for (AdaptationRule rule: adpRules){
			rule.setId(generateId());
			dataControls.add( new AdaptationRuleDataControl(rule) );
		}
		
		this.path = path;
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
			this.adaptationRules.add( adpRule );
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
	public boolean deleteElement( DataControl dataControl ) {
		boolean deleted = false;
		
		String adpRuleId = ( (AdaptationRuleDataControl) dataControl ).getId( );
		String references = String.valueOf( controller.countIdentifierReferences( adpRuleId ) );

		// Ask for confirmation
		if( controller.showStrictConfirmDialog( TextConstants.getText( "Operation.DeleteElementTitle" ), TextConstants.getText( "Operation.DeleteElementWarning", new String[] { adpRuleId, references } ) ) ) {
			if( this.adaptationRules.remove( dataControl.getContent( ) ) ) {
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
		return this.adaptationRules;
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
		int elementIndex = adaptationRules.indexOf( dataControl.getContent( ) );

		if( elementIndex < adaptationRules.size( ) - 1 ) {
			adaptationRules.add( elementIndex + 1, adaptationRules.remove( elementIndex ) );
			dataControls.add( elementIndex + 1, dataControls.remove( elementIndex ) );
			controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = adaptationRules.indexOf( dataControl.getContent( ) );

		if( elementIndex > 0 ) {
			adaptationRules.add( elementIndex - 1, adaptationRules.remove( elementIndex ) );
			dataControls.add( elementIndex - 1, dataControls.remove( elementIndex ) );
			controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	public String getFileName(){
		return path.substring( Math.max( path.lastIndexOf( "/" ), path.lastIndexOf( "\\" ) )+1);
	}
	
	@Override
	public boolean renameElement( ) {
		boolean renamed = false;
		
		// Show confirmation dialog.
		if (controller.showStrictConfirmDialog( TextConstants.getText( "Operation.RenameAdaptationFile" ), TextConstants.getText( "Operation.RenameAdaptationFile.Message" ) )){
			
			//Prompt for file name:
			String fileName = controller.showInputDialog( TextConstants.getText( "Operation.RenameAdaptationFile.FileName" ), TextConstants.getText( "Operation.RenameAdaptationFile.FileName.Message" ), getFileName() );
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
						renamed = assessmentFile.renameTo( new File(Controller.getInstance( ).getProjectFolder( ), AssetsController.getCategoryFolder( AssetsController.CATEGORY_ADAPTATION ) +"/"+fileName) );
						controller.dataModified( );
						this.path = AssetsController.getCategoryFolder( AssetsController.CATEGORY_ADAPTATION ) +"/"+fileName;
			}
			
		}
		
		return renamed;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		
	}

	@Override
	public void updateFlagSummary( FlagSummary flagSummary ) {
		for (AdaptationRuleDataControl dataControl : dataControls)
			dataControl.updateFlagSummary( flagSummary );
		
		//Update the initial state
		for (String flag: initialState.getFlags( )){
			flagSummary.addReference( flag );	
		}
		
	}

	/**
	 * @return the initialState
	 */
	public AdaptedState getInitialState( ) {
		return initialState;
	}

	/**
	 * @param initialState the initialState to set
	 */
	public void setInitialState( AdaptedState initialState ) {
		this.initialState = initialState;
	}

	public void setInitialScene( String initScene ) {
		if (initialState==null)
			initialState = new AdaptedState();
		initialState.setInitialScene( initScene );
	}
	
	public String getInitialScene(  ) {
		return initialState.getInitialScene( );
	}

	public boolean addFlagAction( int selectedRow ) {
		boolean added=false;
		//Check there is at least one flag

		String[] flags = Controller.getInstance( ).getFlagSummary( ).getFlags( );
		if (flags!=null && flags.length>0){

			//	By default, the flag is activated. Default flag will be the first one
			initialState.addActivatedFlag( flags[0] );
			added=true;
		}
		
		//Otherwise, prompt error message
		// If the list had no elements, show an error message
		else
			Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Adaptation.ErrorNoFlags.Title" ), TextConstants.getText( "Adaptation.ErrorNoFlags" ) );
		
		return added;
		
	}

	public void deleteFlagAction( int selectedRow ) {
		if (selectedRow >=0 && selectedRow <initialState.getFlags( ).size( )){
			initialState.removeFlag( selectedRow );
			controller.updateFlagSummary( );
		}
	}

	public int getFlagActionCount( ) {
		return initialState.getFlags( ).size( );
	}

	public void changeAction( int rowIndex ) {
		if (rowIndex >=0 && rowIndex <initialState.getFlags( ).size( )){
			initialState.changeAction( rowIndex );
		}

		
	}

	public void setFlag( int rowIndex, String flag ) {
		if (rowIndex >=0 && rowIndex <initialState.getFlags(  ).size( )){
			initialState.changeFlag( rowIndex, flag );
			controller.updateFlagSummary( );
		}
		
	}

	public String getFlag( int rowIndex ) {
		return initialState.getFlag( rowIndex );
	}

	public String getAction( int rowIndex ) {
		return initialState.getAction( rowIndex );
	}

	public String[][] getAdaptationRulesInfo( ) {
		String[][] info = new String[adaptationRules.size( )][4];
		
		for (int i=0; i<adaptationRules.size( ); i++){
			info[i][0]=adaptationRules.get( i ).getId( );
			info[i][1]=String.valueOf( adaptationRules.get( i ).getUOLProperties( ).size( ));
			if (adaptationRules.get( i ).getAdaptedState( ).getInitialScene( )==null)
				info[i][2]="<Not selected>";
			else
				info[i][2]=adaptationRules.get( i ).getAdaptedState( ).getInitialScene( );
			info[i][3]=String.valueOf( adaptationRules.get( i ).getAdaptedState( ).getFlags( ).size( ));
		}
		return info;
	}

	public void setAction( int rowIndex, String string ) {
		if (!initialState.getAction( rowIndex ).equals( string ))
			initialState.changeAction( rowIndex );
		
		
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
