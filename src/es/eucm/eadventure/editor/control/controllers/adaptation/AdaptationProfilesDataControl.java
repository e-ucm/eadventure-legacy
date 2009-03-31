package es.eucm.eadventure.editor.control.controllers.adaptation;

import java.io.IOException;
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
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class AdaptationProfilesDataControl extends DataControl{

	private List<AdaptationProfileDataControl> profiles;
	
	public AdaptationProfilesDataControl (List<AdaptationProfile> data) {
		this.profiles = new ArrayList<AdaptationProfileDataControl>();
		for (AdaptationProfile ap: data){
			profiles.add(new AdaptationProfileDataControl(ap));
		}
	}
	
	
	@Override
	public boolean addElement( int type , String fileName) {
		boolean added = false;
		if (type == Controller.ADAPTATION_PROFILE){
		
		// Show confirmation dialog. If yes selected, mainwindow changes to adaptation mode
		if (controller.showStrictConfirmDialog( TextConstants.getText( "Operation.CreateAdaptationFile" ), TextConstants.getText( "Operation.CreateAdaptationFile.Message" ) )){
			
			//Prompt for file name:
			if (fileName == null)
				fileName = controller.showInputDialog( TextConstants.getText( "Operation.CreateAdaptationFile.FileName" ), TextConstants.getText( "Operation.CreateAdaptationFile.FileName.Message" ), TextConstants.getText( "Operation.CreateAdaptationFile.FileName.DefaultValue" ) );
			if (fileName!=null){
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

				boolean create = true;
				//Checks if the file exists. In that case, ask to overwrite it
				File newFile = new File(controller.getProjectFolder( ), AssetsController.getCategoryFolder( AssetsController.CATEGORY_ADAPTATION )+"/"+fileName);
				if (newFile.exists( )){
					
					//Overwrite it?
					if (controller.showStrictConfirmDialog( TextConstants.getText( "Operation.OverwriteAdaptationFile" ), TextConstants.getText( "Operation.OverwriteAdaptationFile.Message" ) )){
						create = true;
						newFile.delete( );
						// Search the profile and delete it
						for (AdaptationProfileDataControl profile: this.profiles){
							if (profile.getPath( ).equals( AssetsController.getCategoryFolder( AssetsController.CATEGORY_ADAPTATION )+"/"+fileName )){
								controller.deleteAssetReferences( profile.getPath() );
								profiles.remove( profile );
							}
						}

					}else
						create = false;
					
				}
				
				if (create){
					try {
						File newAssFile = new File (Controller.getInstance( ).getProjectFolder( ),AssetsController.getCategoryFolder( AssetsController.CATEGORY_ADAPTATION )+"/"+fileName );
						newAssFile.createNewFile( );
						this.profiles.add( new AdaptationProfileDataControl ( new ArrayList<AdaptationRule>(), new AdaptedState(), AssetsController.getCategoryFolder( AssetsController.CATEGORY_ADAPTATION )+"/"+fileName) );
						//controller.dataModified( );
						added = true;
					} catch( IOException e ) {
						Controller.getInstance( ).showErrorDialog( "Error.CreateAdaptationFile.Title", "Error.CreateAdaptationFile.Message" );
					}

				}
			}
			
		}
		}
		return added;
	}
	
	@Override
	public String getDefaultId(int type) {
		return TextConstants.getText( "Operation.CreateAdaptationFile.FileName.DefaultValue" );
	}


	@Override
	public boolean canAddElement( int type ) {
		return type == Controller.ADAPTATION_PROFILE;
	}

	@Override
	public boolean canBeDeleted( ) {
		return false;
	}

	@Override
	public boolean canBeMoved( ) {
		return false;
	}

	@Override
	public boolean canBeRenamed( ) {
		return false;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;
		for (AdaptationProfileDataControl profile:profiles){
			count += profile.countAssetReferences( assetPath ); 
		}
		return count;
	}
	
	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		// Iterate through each profile
		for (AdaptationProfileDataControl profile:profiles){
			profile.getAssetReferences( assetPaths, assetTypes );
		}
	}


	@Override
	public int countIdentifierReferences( String id ) {
		int count = 0;
		for (AdaptationProfileDataControl profile:profiles){
			count += profile.countIdentifierReferences( id ); 
		}
		return count;
	}

	@Override
	public void deleteAssetReferences( String assetPath ) {
		for (AdaptationProfileDataControl profile:profiles){
			profile.deleteAssetReferences( assetPath ); 
		}
	}

	@Override
	public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {
		boolean deleted = false;
		for (AdaptationProfileDataControl profile:profiles){
			if (dataControl == profile){
				String path = profile.getPath( );
				int references = Controller.getInstance( ).countAssetReferences( path );
				if(!askConfirmation || controller.showStrictConfirmDialog( TextConstants.getText( "Operation.DeleteElementTitle" ), TextConstants.getText( "Operation.DeleteElementWarning", new String[] { 
						TextConstants.getElementName( Controller.ADAPTATION_PROFILE ), Integer.toString( references ) } ) ) ) {
					deleted = this.profiles.remove( dataControl );
					if (deleted){
						Controller.getInstance( ).deleteAssetReferences( path );
						
						// Delete the file
						//File deletedFile = new File (Controller.getInstance( ).getProjectFolder( )+"/"+path);
						//if (deletedFile.exists( ))
						//	deleted =deletedFile.delete( );
						//controller.dataModified( );
						break;
					}
				}
			}
		}
		return deleted;

	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		for (AdaptationProfileDataControl profile:profiles){
			if (profile.getPath( ).equals( id ))
			profiles.remove( profile );break; 
		}
		for (AdaptationProfileDataControl profile:profiles){
			profile.deleteIdentifierReferences( id ); 
		}
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[]{Controller.ADAPTATION_PROFILE};
	}

	@Override
	public Object getContent( ) {
		return profiles;
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean isValid = true;
		 
		for (int i=0; i<profiles.size( ); i++){
			currentPath = currentPath + ">>" + i;
			AdaptationProfileDataControl profile = profiles.get( i );
			isValid &= profile.isValid( currentPath, incidences );
		}
		return isValid;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = profiles.indexOf( dataControl );

		if( elementIndex < profiles.size( ) - 1 ) {
			profiles.add( elementIndex + 1, profiles.remove( elementIndex ) );
			//controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = profiles.indexOf( dataControl );

		if( elementIndex > 0 ) {
			profiles.add( elementIndex - 1, profiles.remove( elementIndex ) );
			//controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public String renameElement( String name ) {
		return null;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		for (AdaptationProfileDataControl profile:profiles){
			profile.replaceIdentifierReferences( oldId, newId );
		}
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
		for (AdaptationProfileDataControl profile:profiles){
			profile.updateVarFlagSummary( varFlagSummary );
		}
	}

	/**
	 * @return the profiles
	 */
	public List<AdaptationProfileDataControl> getProfiles( ) {
		return profiles;
	}

	public AdaptationProfileDataControl getLastProfile(){
		return this.profiles.get( profiles.size( ) -1 );
	}

	@Override
	public boolean canBeDuplicated( ) {
		return false;
	}

	@Override
	public void recursiveSearch( ) {
		for (DataControl dc : this.profiles) {
			dc.recursiveSearch( );
		}
	}

	
	/**
	 * Check if the assessment profile which has the specific "path" is scorm 1.2 profile
	 * 
	 * @param path
	 * 			the path of the assessment profile to confirm if it is or it isn´t scorm 1.2
	 * @return
	 */
	public boolean isScorm12Profile( ){
		boolean isScorm12 = true;
		for (AdaptationProfileDataControl profile:profiles){
			isScorm12 &= profile.isScorm12();
		}
		return isScorm12;
	}

	/**
	 * Check if the assessment profile which has the specific "path" is scorm 2004 profile
	 * 
	 * @param path
	 * 			the path of the assessment profile to confirm if it is or it isn´t scorm 2004
	 * @return
	 */
	public boolean isScorm2004Profile( ){
		boolean isScorm2004 = true;
		for (AdaptationProfileDataControl profile:profiles){
			isScorm2004 &= profile.isScorm2004();
		}
		return isScorm2004;
	}

	/**
	 * Returns the AdaptationProfile which path matches the given one, null if not found
	 * @param adaptationPath
	 */
	public AdaptationProfileDataControl getProfileByPath(String adaptationPath) {
		for ( AdaptationProfileDataControl profile: profiles){
			if (profile.getPath().equals(adaptationPath) ){
				return profile;
			}
		}
		return null;
		
	}


}
