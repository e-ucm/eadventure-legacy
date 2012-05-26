/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.controllers.adaptation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.adaptation.AdaptationProfile;
import es.eucm.eadventure.common.data.adaptation.AdaptationRule;
import es.eucm.eadventure.common.data.adaptation.AdaptedState;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class AdaptationProfilesDataControl extends DataControl {

    private List<AdaptationProfileDataControl> profiles;

    private List<AdaptationProfile> data;

    public AdaptationProfilesDataControl( List<AdaptationProfile> data ) {

        this.profiles = new ArrayList<AdaptationProfileDataControl>( );
        this.data = data;
        for( AdaptationProfile ap : data ) {
            profiles.add( new AdaptationProfileDataControl( ap ) );
        }
    }

    @Override
    public boolean addElement( int type, String profileName ) {

        boolean added = false;
        if( type == Controller.ADAPTATION_PROFILE ) {

            // Show confirmation dialog. If yes selected, mainwindow changes to assessment mode
            //if (controller.showStrictConfirmDialog( TextConstants.getText( "Operation.CreateAdaptationFile" ), TextConstants.getText( "Operation.CreateAdaptationFile.Message" ) )){

            //Prompt for profile name:
            if( profileName == null )
                profileName = controller.showInputDialog( TC.get( "Operation.CreateAdaptationFile.FileName" ), TC.get( "Operation.CreateAdaptationFile.FileName.Message" ), TC.get( "Operation.CreateAdaptationFile.FileName.DefaultValue" ) );
            if( profileName != null && controller.isElementIdValid( profileName ) ) {
                //Checks if the profile exists. Always profile name is set as TextConstants.getText("Operation.CreateAdaptationFile.FileName.DefaultValue");
                // Increase the last number until create a not existing name
                int i = 1;
                while( /*existName(profileName )*/controller.getIdentifierSummary( ).isAdaptationProfileId( profileName ) ) {
                    String lastIndex = profileName.substring( profileName.length( ) - 1, profileName.length( ) );
                    try {
                        Integer.parseInt( lastIndex );
                    }
                    catch( NumberFormatException e ) {
                        profileName += i;
                    }
                    profileName = profileName.substring( 0, profileName.length( ) - 1 );
                    profileName += i;

                }
                List<AdaptationRule> newRules = new ArrayList<AdaptationRule>( );
                AdaptedState initialState = new AdaptedState( );
                this.profiles.add( new AdaptationProfileDataControl( newRules, initialState, profileName ) );
                data.add( (AdaptationProfile) profiles.get( profiles.size( ) - 1 ).getContent( ) );
                controller.getIdentifierSummary( ).addAdaptationProfileId( profileName );
                if (controller.getSelectedChapterDataControl( ).getAdaptationName( ) == null
                        || controller.getSelectedChapterDataControl( ).getAdaptationName( ).equals( "" )) {
                    controller.getSelectedChapterDataControl( ).setAdaptationPath( profileName );
                }

                //controller.dataModified( );
                added = true;

            }
            //else {
            // controller.showErrorDialog(TextConstants.getText("Operation.CreateAdaptationFile.FileName.ExistValue.Title"), TextConstants.getText("Operation.CreateAdaptationFile.FileName.ExistValue.Message"));
            //}
        }

        //}
        return added;
    }

    @Override
    public boolean duplicateElement( DataControl dataControl ) {

        if( !( dataControl instanceof AdaptationProfileDataControl ) )
            return false;
        try {
            AdaptationProfile newElement = (AdaptationProfile) ( ( (AdaptationProfile) ( dataControl.getContent( ) ) ).clone( ) );
            String id = newElement.getName( );
            int i = 1;
            do {
                id = newElement.getName( ) + i;
                i++;
            } while( /*existName(id)*/controller.getIdentifierSummary( ).isAdaptationProfileId( id ) );
            newElement.setName( id );
            profiles.add( new AdaptationProfileDataControl( newElement ) );
            data.add( (AdaptationProfile) profiles.get( profiles.size( ) - 1 ).getContent( ) );
            controller.getIdentifierSummary( ).addAdaptationProfileId( id );
            //Add all rules id to the IdentifierSummary
            for (AdaptationRule ar: newElement.getRules())
        	controller.getIdentifierSummary( ).addAdaptationRuleId(ar.getId(), id);
            return true;
        }
        catch( CloneNotSupportedException e ) {
            ReportDialog.GenerateErrorReport( e, true, "Could not clone adaptation profile" );
            return false;
        }
    }

    /*public boolean existName(String name){
        for (AdaptationProfileDataControl profile: this.profiles){
    	if (profile.getName().equals(name))
    	    return true;
        }
        return false;
    }*/

    @Override
    public String getDefaultId( int type ) {

        return TC.get( "Operation.CreateAdaptationFile.FileName.DefaultValue" );
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

        return 0;
    }

    @Override
    public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {

        // Iterate through each profile

    }

    @Override
    public int countIdentifierReferences( String id ) {

        int count = 0;
        for( AdaptationProfileDataControl profile : profiles ) {
            if( profile.getName( ).equals( id ) )
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
        for( AdaptationProfileDataControl profile : profiles ) {
            if( dataControl == profile ) {
                String path = profile.getName( );
                int references = Controller.getInstance( ).countAssetReferences( path );
                if( !askConfirmation || controller.showStrictConfirmDialog( TC.get( "Operation.DeleteElementTitle" ), TC.get( "Operation.DeleteElementWarning", new String[] { TC.getElement( Controller.ADAPTATION_PROFILE ), Integer.toString( references ) } ) ) ) {
                    data.remove( profiles.indexOf( dataControl ) );
                    deleted = this.profiles.remove( dataControl );
                    if( deleted ) {
                	// Delete all rule Ids in IdentifiersSummary
                	for (AdaptationRuleDataControl ar: ((AdaptationProfileDataControl)dataControl).getAdaptationRules() )
                	    controller.getIdentifierSummary().deleteAdaptationRuleId(ar.getId(), profile.getName());
                        controller.getIdentifierSummary( ).deleteAdaptationProfileId( profile.getName( ) );

                        break;
                    }
                }
            }
        }
        return deleted;

    }

    @Override
    public void deleteIdentifierReferences( String id ) {

        AdaptationProfileDataControl profileToDelete = null;
        AdaptationProfileDataControl profile = null;
        Iterator<AdaptationProfileDataControl> itera = this.profiles.iterator( );
        while( itera.hasNext( ) ) {
            profile = itera.next( );
            if( id.equals( profile.getName( ) ) ) {
                data.remove( this.profiles.indexOf( profile ) );
                itera.remove( );
            }
            else
                profile.deleteIdentifierReferences( id );
        }

    }

    @Override
    public int[] getAddableElements( ) {

        return new int[] { Controller.ADAPTATION_PROFILE };
    }

    @Override
    public Object getContent( ) {

        return profiles;
    }

    @Override
    public boolean isValid( String currentPath, List<String> incidences ) {

        boolean isValid = true;

        for( int i = 0; i < profiles.size( ); i++ ) {
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

        for( AdaptationProfileDataControl profile : profiles ) {
            if( profile.getName( ).equals( oldId ) )
                profile.renameElement( newId );
            profile.replaceIdentifierReferences( oldId, newId );
        }
    }

    @Override
    public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {

        for( AdaptationProfileDataControl profile : profiles ) {
            profile.updateVarFlagSummary( varFlagSummary );
        }
    }

    /**
     * @return the profiles
     */
    public List<AdaptationProfileDataControl> getProfiles( ) {

        return profiles;
    }

    public AdaptationProfileDataControl getLastProfile( ) {

        return this.profiles.get( profiles.size( ) - 1 );
    }

    @Override
    public boolean canBeDuplicated( ) {

        return false;
    }

    @Override
    public void recursiveSearch( ) {

        for( DataControl dc : this.profiles ) {
            dc.recursiveSearch( );
        }
    }

    /**
     * Check if the assessment profile which has the specific "path" is scorm
     * 1.2 profile
     * 
     * @param path
     *            the path of the assessment profile to confirm if it is or it
     *            isn´t scorm 1.2
     * @return
     */
    public boolean isScorm12Profile( ) {

        boolean isScorm12 = true;
        for( AdaptationProfileDataControl profile : profiles ) {
            isScorm12 &= profile.isScorm12( );
        }
        return isScorm12;
    }

    /**
     * Check if the assessment profile which has the specific "path" is scorm
     * 2004 profile
     * 
     * @param path
     *            the path of the assessment profile to confirm if it is or it
     *            isn´t scorm 2004
     * @return
     */
    public boolean isScorm2004Profile( ) {

        boolean isScorm2004 = true;
        for( AdaptationProfileDataControl profile : profiles ) {
            isScorm2004 &= profile.isScorm2004( );
        }
        return isScorm2004;
    }

    /**
     * Returns the AdaptationProfile which path matches the given one, null if
     * not found
     * 
     * @param adaptationPath
     */
    public AdaptationProfileDataControl getProfileByPath( String adaptationPath ) {

        for( AdaptationProfileDataControl profile : profiles ) {
            if( profile.getName( ).equals( adaptationPath ) ) {
                return profile;
            }
        }
        return null;

    }

    @Override
    public List<Searchable> getPathToDataControl( Searchable dataControl ) {

        return getPathFromChild( dataControl, profiles );
    }

}
