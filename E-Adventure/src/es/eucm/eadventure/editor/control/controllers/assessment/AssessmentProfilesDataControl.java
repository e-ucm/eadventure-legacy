package es.eucm.eadventure.editor.control.controllers.assessment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.assessment.AssessmentProfile;
import es.eucm.eadventure.common.data.assessment.AssessmentRule;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class AssessmentProfilesDataControl extends DataControl{

	private List<AssessmentProfileDataControl> profiles;
	
	private List<AssessmentProfile> data;
	
	public AssessmentProfilesDataControl (List<AssessmentProfile> data) {
		this.profiles = new ArrayList<AssessmentProfileDataControl>();
		this.data = data;
		for (AssessmentProfile ap: data){
			profiles.add(new AssessmentProfileDataControl(ap));
		}
	}
	
	@Override
	public boolean addElement( int type , String profileName) {
		boolean added = false;
		if (type == Controller.ASSESSMENT_PROFILE){
		
		// Show confirmation dialog. If yes selected, mainwindow changes to assessment mode
		//if (controller.showStrictConfirmDialog( TextConstants.getText( "Operation.CreateAssessmentFile" ), TextConstants.getText( "Operation.CreateAssessmentFile.Message" ) )){
			
			//Prompt for profile name:
			if (profileName == null)
			    profileName = controller.showInputDialog( TextConstants.getText( "Operation.CreateAssessmentFile.FileName" ), TextConstants.getText( "Operation.CreateAssessmentFile.FileName.Message" ), TextConstants.getText( "Operation.CreateAssessmentFile.FileName.DefaultValue" ) );
			if (profileName!=null&& controller.isElementIdValid( profileName )){
				//Checks if the profile exists. In that case, communicate it
			  //Checks if the profile exists. Always profile name is set as TextConstants.getText("Operation.CreateAdaptationFile.FileName.DefaultValue");
			    	// Increase the last number until create a not existing name
			    	int i=1;
			    	while (controller.getIdentifierSummary().isAssessmentProfileId(profileName )){
			    	    String lastIndex = profileName.substring(profileName.length()-1,profileName.length());
			    	    try{
			    		Integer.parseInt(lastIndex);
			    	    }catch (NumberFormatException e){
			    		profileName += i;
			    	    }
			    	    profileName = profileName.substring(0,profileName.length()-1);
			    	    profileName += i;
			    	   
			    	}
					List<AssessmentRule> newRules = new ArrayList<AssessmentRule>();
					this.profiles.add( new AssessmentProfileDataControl ( newRules, profileName) );
					data.add( (AssessmentProfile)profiles.get(profiles.size()-1).getContent() );
					controller.getIdentifierSummary().addAssessmentProfileId(profileName);
					//controller.dataModified( );
					added = true;

				/*}else {
				    controller.showErrorDialog(TextConstants.getText("Operation.CreateAdaptationFile.FileName.ExistValue.Title"), TextConstants.getText("Operation.CreateAdaptationFile.FileName.ExistValue.Message"));
				}*/
			}
			
		}
		//}
		return added;
	}

	@Override
	public boolean duplicateElement( DataControl dataControl ) {
		if (!(dataControl instanceof AssessmentProfileDataControl))
			return false;
		try {
			AssessmentProfile newElement = (AssessmentProfile) (((AssessmentProfile) (dataControl.getContent())).clone());
			String id = newElement.getName();
			int i = 1;
			do {
				id = newElement.getName() + i;
				i++;
			} while (controller.getIdentifierSummary().isAssessmentProfileId(id));
			newElement.setName(id);
			profiles.add(new AssessmentProfileDataControl(newElement));
			data.add( (AssessmentProfile)profiles.get(profiles.size()-1).getContent() );
			controller.getIdentifierSummary().addAssessmentProfileId(id);
			return true;
		} catch (CloneNotSupportedException e) {
			ReportDialog.GenerateErrorReport(e, true, "Could not clone assessment profile");	
			return false;
		} 
	}

	/*public boolean existName(String name){
	    for (AssessmentProfileDataControl profile: this.profiles){
		if (profile.getName().equals(name))
		    return true;
	    }
	    return false;
	}*/

	@Override
	public String getDefaultId(int type) {
		return TextConstants.getText( "Operation.CreateAssessmentFile.FileName.DefaultValue" );
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
		return 0;
	}
	
	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		
	}


	@Override
	public int countIdentifierReferences( String id ) {
		int count = 0;
		for (AssessmentProfileDataControl profile:profiles){
		    if (profile.getName().equals(id))
			count++;
		    count += profile.countIdentifierReferences( id ); 
		}
		return count;
	}

	@Override
	public void deleteAssetReferences( String assetPath ) {
		
	}

	@Override
	public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {
		boolean deleted = false;
		for (AssessmentProfileDataControl profile:profiles){
			if (dataControl == profile){
				String path = profile.getName( );
				int references = Controller.getInstance( ).countAssetReferences( path );
				if(!askConfirmation || controller.showStrictConfirmDialog( TextConstants.getText( "Operation.DeleteElementTitle" ), TextConstants.getText( "Operation.DeleteElementWarning", new String[] { 
						TextConstants.getElementName( Controller.ASSESSMENT_PROFILE ), Integer.toString( references ) } ) ) ) {
					data.remove(profiles.indexOf(dataControl));
					deleted = this.profiles.remove( dataControl );
					if (deleted){
					    controller.getIdentifierSummary( ).deleteAssessmentProfileId(profile.getName());
						
						break;
					}
				}
			}
		}
		return deleted;
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
	    AssessmentProfileDataControl profilesToDelete=null;
	    AssessmentProfileDataControl profile = null;
	    Iterator<AssessmentProfileDataControl> itera = this.profiles.iterator();
	    while(itera.hasNext())
		{
		profile = itera.next();
		if (id.equals(profile.getName())){
		    data.remove(profiles.indexOf(profile));    
		    itera.remove();

		}else 
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
			data.add( elementIndex + 1, data.remove( elementIndex ) );
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
			data.add( elementIndex - 1, data.remove( elementIndex ) );
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
		for (AssessmentProfileDataControl profile:profiles){
		    if (profile.getName().equals(oldId))
			profile.renameElement(newId);
		    profile.replaceIdentifierReferences( oldId, newId );
		}
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
		for (AssessmentProfileDataControl profile:profiles){
			profile.updateVarFlagSummary( varFlagSummary );
		}
	}

	/**
	 * @return the profiles
	 */
	public List<AssessmentProfileDataControl> getProfiles( ) {
		return profiles;
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
		for (AssessmentProfileDataControl profile:profiles){
			isScorm12 &= profile.isScorm12();
		}
		return false;
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
		for (AssessmentProfileDataControl profile:profiles){
			isScorm2004 &= profile.isScorm2004();
		}
		return false;
	}

	
	public AssessmentProfileDataControl getLastProfile(){
		return this.profiles.get( profiles.size( ) -1 );
	}

	@Override
	public boolean canBeDuplicated( ) {
		return false;
	}

	@Override
	public void recursiveSearch() {
		for (DataControl dc : this.profiles)
			dc.recursiveSearch();
	}

	/**
	 * Returns the AssessmentProfile which path matches the given one, null if not found
	 * @param adaptationPath
	 */
	public AssessmentProfileDataControl getProfileByPath(String adaptationPath) {
		for ( AssessmentProfileDataControl profile: profiles){
			if (profile.getName().equals(adaptationPath) ){
				return profile;
			}
		}
		return null;
		
	}
	
	@Override
	public List<Searchable> getPathToDataControl(Searchable dataControl) {
		return getPathFromChild(dataControl, profiles);
	}
	
	
}
