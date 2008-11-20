package es.eucm.eadventure.editor.control.controllers.assessment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import es.eucm.eadventure.common.auxiliar.File;
import es.eucm.eadventure.common.data.assessment.AssessmentRule;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.data.support.FlagSummary;

public class AssessmentProfilesDataControl extends DataControl{

	private List<AssessmentProfileDataControl> profiles;
	
	public AssessmentProfilesDataControl () {
		profiles = new ArrayList<AssessmentProfileDataControl> ();
	}
	
	@Override
	public boolean addElement( int type ) {
		boolean added = false;
		if (type == Controller.ASSESSMENT_PROFILE){
		
		// Show confirmation dialog. If yes selected, mainwindow changes to assessment mode
		if (controller.showStrictConfirmDialog( TextConstants.getText( "Operation.CreateAssessmentFile" ), TextConstants.getText( "Operation.CreateAssessmentFile.Message" ) )){
			
			//Prompt for file name:
			String fileName = controller.showInputDialog( TextConstants.getText( "Operation.CreateAssessmentFile.FileName" ), TextConstants.getText( "Operation.CreateAssessmentFile.FileName.Message" ), TextConstants.getText( "Operation.CreateAssessmentFile.FileName.DefaultValue" ) );
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
				File newFile = new File(controller.getProjectFolder( ), AssetsController.getCategoryFolder( AssetsController.CATEGORY_ASSESSMENT )+"/"+fileName);
				if (newFile.exists( )){
					
					//Overwrite it?
					if (controller.showStrictConfirmDialog( TextConstants.getText( "Operation.OverwriteAssessmentFile" ), TextConstants.getText( "Operation.OverwriteAssessmentFile.Message" ) )){
						create = true;
						newFile.delete( );
						
						// Search the profile and delete it
						for (AssessmentProfileDataControl profile: this.profiles){
							if (profile.getPath( ).equals( AssetsController.getCategoryFolder( AssetsController.CATEGORY_ASSESSMENT )+"/"+fileName )){
								controller.deleteAssetReferences( profile.getPath() );
								profiles.remove( profile );
							}
						}
					}else
						create = false;
					
				}
				
				if (create){
					try {
						File newAssFile = new File (Controller.getInstance( ).getProjectFolder( ),AssetsController.getCategoryFolder( AssetsController.CATEGORY_ASSESSMENT )+"/"+fileName );
						newAssFile.createNewFile( );
						this.profiles.add( new AssessmentProfileDataControl ( new ArrayList<AssessmentRule>(), AssetsController.getCategoryFolder( AssetsController.CATEGORY_ASSESSMENT )+"/"+fileName) );
						controller.dataModified( );
						added = true;
					} catch( IOException e ) {
						Controller.getInstance( ).showErrorDialog( "Error.CreateAssessmentFile.Title", "Error.CreateAssessmentFile.Message" );
					}

				}
			}
			
		}
		}
		return added;
	}

	@Override
	public boolean canAddElement( int type ) {
		return type == Controller.ASSESSMENT_PROFILE;
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
		for (AssessmentProfileDataControl profile:profiles){
			count += profile.countAssetReferences( assetPath ); 
		}
		return count;
	}
	
	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		// Iterate through each profile
		for (AssessmentProfileDataControl profile:profiles){
			profile.getAssetReferences( assetPaths, assetTypes );
		}
	}


	@Override
	public int countIdentifierReferences( String id ) {
		int count = 0;
		for (AssessmentProfileDataControl profile:profiles){
			count += profile.countIdentifierReferences( id ); 
		}
		return count;
	}

	@Override
	public void deleteAssetReferences( String assetPath ) {
		for (AssessmentProfileDataControl profile:profiles){
			profile.deleteAssetReferences( assetPath ); 
		}
	}

	@Override
	public boolean deleteElement( DataControl dataControl ) {
		boolean deleted = false;
		for (AssessmentProfileDataControl profile:profiles){
			if (dataControl == profile){
				String path = profile.getPath( );
				int references = Controller.getInstance( ).countAssetReferences( path );
				if( controller.showStrictConfirmDialog( TextConstants.getText( "Operation.DeleteElementTitle" ), TextConstants.getText( "Operation.DeleteElementWarning", new String[] { 
						TextConstants.getElementName( Controller.ASSESSMENT_PROFILE ), Integer.toString( references ) } ) ) ) {
					deleted = this.profiles.remove( dataControl );
					if (deleted){
						Controller.getInstance( ).deleteAssetReferences( path );
						// Delete the file
						File deletedFile = new File (Controller.getInstance( ).getProjectFolder( )+"/"+path);
						if (deletedFile.exists( ))
							deleted =deletedFile.delete( );

						controller.dataModified( );
						break;
					}
				}
			}
		}
		return deleted;
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		for (AssessmentProfileDataControl profile:profiles){
			if (profile.getPath( ).equals( id ))
			profiles.remove( profile );break; 
		}

		for (AssessmentProfileDataControl profile:profiles){
			profile.deleteIdentifierReferences( id ); 
		}
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[]{Controller.ASSESSMENT_PROFILE};
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
			AssessmentProfileDataControl profile = profiles.get( i );
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
			controller.dataModified( );
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
			controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean renameElement( ) {
		return false;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		for (AssessmentProfileDataControl profile:profiles){
			profile.replaceIdentifierReferences( oldId, newId );
		}
	}

	@Override
	public void updateFlagSummary( FlagSummary flagSummary ) {
		for (AssessmentProfileDataControl profile:profiles){
			profile.updateFlagSummary( flagSummary );
		}
	}

	/**
	 * @return the profiles
	 */
	public List<AssessmentProfileDataControl> getProfiles( ) {
		return profiles;
	}

	/**
	 * @param profiles the profiles to set
	 */
	public void setProfiles( List<AssessmentProfileDataControl> profiles ) {
		this.profiles = profiles;
	}
	
	public AssessmentProfileDataControl getLastProfile(){
		return this.profiles.get( profiles.size( ) -1 );
	}

	@Override
	public boolean canBeDuplicated( ) {
		return false;
	}
}
