/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.editor.control.controllers.adaptation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.adaptation.AdaptationProfile;
import es.eucm.eadventure.common.data.adaptation.AdaptationRule;
import es.eucm.eadventure.common.data.adaptation.AdaptedState;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.tools.adaptation.AddActionTool;
import es.eucm.eadventure.editor.control.tools.adaptation.ChangeActionTool;
import es.eucm.eadventure.editor.control.tools.adaptation.ChangeAdaptationProfileTypeTool;
import es.eucm.eadventure.editor.control.tools.adaptation.ChangeVarFlagTool;
import es.eucm.eadventure.editor.control.tools.adaptation.DeleteActionTool;
import es.eucm.eadventure.editor.control.tools.adaptation.MoveRuleTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeTargetIdTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class AdaptationProfileDataControl extends DataControl {

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

    public AdaptationProfileDataControl( List<AdaptationRule> adpRules, AdaptedState initialState, String name ) {

        this( new AdaptationProfile( adpRules, initialState, name, false, false ) );
    }

    public AdaptationProfileDataControl( AdaptationProfile profile ) {

        number = 0;
        dataControls = new ArrayList<AdaptationRuleDataControl>( );
        if( profile == null )
            profile = new AdaptationProfile( );
        else
            this.profile = profile;

        if( profile != null && profile.getRules( ) != null )
            for( AdaptationRule rule : profile.getRules( ) ) {
                rule.setId( generateId( ) );
                dataControls.add( new AdaptationRuleDataControl( rule ) );
            }
    }

    private String generateId( ) {

        number++;
        return "#" + number;
    }

    @Override
    public boolean addElement( int type, String id ) {

        boolean added = false;

        if( type == Controller.ADAPTATION_RULE ) {
            // Auto generate the rule id
            String adpRuleId = generateId( );

            // Add thew new adp rule
            AdaptationRule adpRule = new AdaptationRule( );
            adpRule.setId( adpRuleId );
            profile.addRule( adpRule );
            dataControls.add( new AdaptationRuleDataControl( adpRule ) );
            controller.getIdentifierSummary( ).addAdaptationRuleId( adpRuleId );
            //controller.dataModified( );
            added = true;

        }
        return added;
    }

    public AdaptationRuleDataControl getLastAdaptationRule( ) {

        return dataControls.get( dataControls.size( ) - 1 );
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

        int count = 0;
        if( id.equals( profile.getName( ) ) )
            count++;
        for( AdaptationRuleDataControl rule : dataControls ) {
            count += rule.countIdentifierReferences( id );
        }
        return count;
    }

    @Override
    public void deleteAssetReferences( String assetPath ) {

    }

    @Override
    public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {

        boolean deleted = false;

        String adpRuleId = ( (AdaptationRuleDataControl) dataControl ).getId( );
        String references = String.valueOf( controller.countIdentifierReferences( adpRuleId ) );

        if( Integer.parseInt( references ) == 0 ) {
            askConfirmation = false;
        }
        // Ask for confirmation
        if( !askConfirmation || controller.showStrictConfirmDialog( TextConstants.getText( "Operation.DeleteElementTitle" ), TextConstants.getText( "Operation.DeleteElementWarning", new String[] { adpRuleId, references } ) ) ) {
            if( profile.getRules( ).remove( dataControl.getContent( ) ) ) {
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

        // profiles identifiers are deleted in adaptationProfilesDataControl
        AdaptationRuleDataControl ruleToDelete = null;
        AdaptationRuleDataControl rule = null;
        Iterator<AdaptationRuleDataControl> itera = this.dataControls.iterator( );
        while( itera.hasNext( ) ) {
            rule = itera.next( );
            if( id.equals( rule.getId( ) ) ) {
                itera.remove( );

            }
            else
                rule.deleteIdentifierReferences( id );
        }

    }

    @Override
    public int[] getAddableElements( ) {

        return new int[] { Controller.ADAPTATION_RULE };
    }

    @Override
    public Object getContent( ) {

        return profile;
    }

    public List<AdaptationRuleDataControl> getAdaptationRules( ) {

        return this.dataControls;
    }

    @Override
    public boolean isValid( String currentPath, List<String> incidences ) {

        return true;
    }

    public boolean moveElementDown( AdaptationRuleDataControl dataControl ) {

        return controller.addTool( new MoveRuleTool( this, dataControl, MoveRuleTool.MODE_DOWN ) );
    }

    public boolean moveElementUp( AdaptationRuleDataControl dataControl ) {

        return controller.addTool( new MoveRuleTool( this, dataControl, MoveRuleTool.MODE_UP ) );
    }

    @Override
    public boolean moveElementDown( DataControl dataControl ) {

        boolean elementMoved = false;
        int elementIndex = profile.getRules( ).indexOf( dataControl.getContent( ) );

        if( elementIndex < profile.getRules( ).size( ) - 1 ) {
            profile.getRules( ).add( elementIndex + 1, profile.getRules( ).remove( elementIndex ) );
            dataControls.add( elementIndex + 1, dataControls.remove( elementIndex ) );
            //controller.dataModified( );
            elementMoved = true;
        }

        return elementMoved;
    }

    @Override
    public boolean moveElementUp( DataControl dataControl ) {

        boolean elementMoved = false;
        int elementIndex = profile.getRules( ).indexOf( dataControl.getContent( ) );

        if( elementIndex > 0 ) {
            profile.getRules( ).add( elementIndex - 1, profile.getRules( ).remove( elementIndex ) );
            dataControls.add( elementIndex - 1, dataControls.remove( elementIndex ) );
            //controller.dataModified( );
            elementMoved = true;
        }

        return elementMoved;
    }

    public String getFileName( ) {

        return profile.getName( ).substring( Math.max( profile.getName( ).lastIndexOf( "/" ), profile.getName( ).lastIndexOf( "\\" ) ) + 1 );
    }

    @Override
    public String renameElement( String name ) {

        boolean renamed = false;
        String oldName = null;
        if( this.profile.getName( ) != null ) {
            oldName = this.profile.getName( );
        }

        // Show confirmation dialog.
        if( name != null || controller.showStrictConfirmDialog( TextConstants.getText( "Operation.RenameAssessmentFile" ), TextConstants.getText( "Operation.RenameAssessmentFile.Message" ) ) ) {

            //Prompt for file name:
            String fileName = name;
            if( name == null || name.equals( "" ) )
                fileName = controller.showInputDialog( TextConstants.getText( "Operation.RenameAssessmentFile.FileName" ), TextConstants.getText( "Operation.RenameAssessmentFile.FileName.Message" ), getFileName( ) );

            if( fileName != null && !fileName.equals( oldName ) && controller.isElementIdValid( fileName ) ) {
                if( !controller.getIdentifierSummary( ).isAdaptationProfileId( name ) ) {
                    //controller.dataModified( );
                    profile.setName( fileName );
                    controller.getIdentifierSummary( ).deleteAdaptationProfileId( oldName );
                    controller.getIdentifierSummary( ).addAdaptationProfileId( fileName );

                    renamed = true;
                }
                else {
                    controller.showErrorDialog( TextConstants.getText( "Operation.CreateAdaptationFile.FileName.ExistValue.Title" ), TextConstants.getText( "Operation.CreateAdaptationFile.FileName.ExistValue.Message" ) );
                }
            }

        }

        if( renamed )
            return oldName;

        return null;
    }

    @Override
    public void replaceIdentifierReferences( String oldId, String newId ) {

        for( AdaptationRuleDataControl rule : dataControls )
            if( rule.getId( ).equals( oldId ) ) {
                rule.renameElement( newId );
            }
    }

    @Override
    public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {

        for( AdaptationRuleDataControl dataControl : dataControls )
            dataControl.updateVarFlagSummary( varFlagSummary );

        //Update the initial state
        if( profile != null && profile.getAdaptedState( ) != null ) {
            for( String flag : profile.getAdaptedState( ).getFlagsVars( ) ) {
                if( profile.getAdaptedState( ).isFlag( flag ) )
                    varFlagSummary.addFlagReference( flag );
                else
                    varFlagSummary.addVarReference( flag );
            }
        }
    }

    @Override
    public boolean duplicateElement( DataControl dataControl ) {

        if( !( dataControl instanceof AdaptationRuleDataControl ) )
            return false;

        try {
            AdaptationRule newRule = (AdaptationRule) ( ( (AdaptationRule) ( dataControl.getContent( ) ) ).clone( ) );
            dataControls.add( new AdaptationRuleDataControl( newRule ) );
            controller.getIdentifierSummary( ).addAdaptationRuleId( newRule.getId( ) );
            return true;
        }
        catch( CloneNotSupportedException e ) {
            ReportDialog.GenerateErrorReport( e, true, "Could not clone adaptation rule" );
            return false;
        }
    }

    /**
     * @return the profile.getInitialState()
     */
    public AdaptedState getInitialState( ) {

        return profile.getAdaptedState( );
    }

    public void setInitialScene( String initScene ) {

        if( profile.getAdaptedState( ) == null )
            profile.setAdaptedState( new AdaptedState( ) );
        controller.addTool( new ChangeTargetIdTool( profile.getAdaptedState( ), initScene ) );
    }

    public String getInitialScene( ) {

        if( profile.getAdaptedState( ) == null )
            return null;
        else
            return profile.getAdaptedState( ).getTargetId( );
    }

    public boolean addFlagAction( int selectedRow ) {

        return controller.addTool( new AddActionTool( profile.getAdaptedState( ), selectedRow ) );
    }

    public void deleteFlagAction( int selectedRow ) {

        controller.addTool( new DeleteActionTool( profile, selectedRow ) );
    }

    public int getFlagActionCount( ) {

        return profile.getAdaptedState( ).getFlagsVars( ).size( );
    }

    public void setFlag( int rowIndex, String flag ) {

        controller.addTool( new ChangeActionTool( profile, rowIndex, flag, ChangeActionTool.SET_ID ) );
    }

    public void setAction( int rowIndex, String string ) {

        controller.addTool( new ChangeActionTool( profile, rowIndex, string, ChangeActionTool.SET_VALUE ) );
    }

    public String getFlag( int rowIndex ) {

        return profile.getAdaptedState( ).getFlagVar( rowIndex );
    }

    public String getAction( int rowIndex ) {

        return profile.getAdaptedState( ).getAction( rowIndex );
    }

    public int getValueToSet( int rowIndex ) {

        if( profile.getAdaptedState( ).getValueToSet( rowIndex ) == Integer.MIN_VALUE )
            return 0;
        else
            return profile.getAdaptedState( ).getValueToSet( rowIndex );

    }

    public String[][] getAdaptationRulesInfo( ) {

        String[][] info = new String[ profile.getRules( ).size( ) ][ 4 ];

        for( int i = 0; i < profile.getRules( ).size( ); i++ ) {
            info[i][0] = profile.getRules( ).get( i ).getId( );
            info[i][1] = String.valueOf( profile.getRules( ).get( i ).getUOLProperties( ).size( ) );
            if( profile.getRules( ).get( i ).getAdaptedState( ).getTargetId( ) == null )
                info[i][2] = "<Not selected>";
            else
                info[i][2] = profile.getRules( ).get( i ).getAdaptedState( ).getTargetId( );
            info[i][3] = String.valueOf( profile.getRules( ).get( i ).getAdaptedState( ).getFlagsVars( ).size( ) );
        }
        return info;
    }

    /**
     * @return the profile.getName()
     */
    public String getName( ) {

        return profile.getName( );
    }

    @Override
    public boolean canBeDuplicated( ) {

        return true;
    }

    @Override
    public void recursiveSearch( ) {

        for( DataControl dc : this.dataControls ) {
            dc.recursiveSearch( );
        }
        check( "" + number, TextConstants.getText( "Search.Number" ) );
        check( getFileName( ), TextConstants.getText( "Search.Name" ) );
        check( getInitialScene( ), TextConstants.getText( "Search.InitialScene" ) );
        for( int i = 0; i < this.getFlagActionCount( ); i++ ) {
            if( isFlag( i ) )
                check( getFlag( i ), TextConstants.getText( "Search.Flag" ) );
            else
                check( getFlag( i ), TextConstants.getText( "Search.Var" ) );

            check( getAction( i ), TextConstants.getText( "Search.ActionOverGameState" ) );
        }
        //check(getName(), TextConstants.getText("Search.Path"));
    }

    public void change( int rowIndex, String name ) {

        //profile.getAdaptedState().change(rowIndex, name);

        controller.addTool( new ChangeVarFlagTool( profile.getAdaptedState( ), rowIndex, name ) );
    }

    public boolean isFlag( int rowIndex ) {

        return this.profile.getAdaptedState( ).isFlag( rowIndex );
    }

    public boolean isScorm2004( ) {

        return profile.isScorm2004( );
    }

    public boolean isScorm12( ) {

        return profile.isScorm12( );
    }

    public void changeToScorm2004Profile( ) {

        controller.addTool( new ChangeAdaptationProfileTypeTool( profile, ChangeAdaptationProfileTypeTool.SCORM2004, profile.isScorm12( ), profile.isScorm2004( ) ) );
    }

    public void changeToScorm12Profile( ) {

        controller.addTool( new ChangeAdaptationProfileTypeTool( profile, ChangeAdaptationProfileTypeTool.SCORM12, profile.isScorm12( ), profile.isScorm2004( ) ) );
    }

    public void changeToNormalProfile( ) {

        controller.addTool( new ChangeAdaptationProfileTypeTool( profile, ChangeAdaptationProfileTypeTool.NORMAL, profile.isScorm12( ), profile.isScorm2004( ) ) );
    }

    @Override
    public List<Searchable> getPathToDataControl( Searchable dataControl ) {

        return getPathFromChild( dataControl, dataControls );
    }

    /**
     * @return the dataControls
     */
    public List<AdaptationRuleDataControl> getDataControls( ) {

        return dataControls;
    }

    /**
     * @param dataControls
     *            the dataControls to set
     */
    public void setDataControlsAndData( List<AdaptationRuleDataControl> dataControls ) {

        this.dataControls = dataControls;
        ArrayList<AdaptationRule> rules = new ArrayList<AdaptationRule>( );
        for( AdaptationRuleDataControl dataControl : dataControls )
            rules.add( (AdaptationRule) dataControl.getContent( ) );

        this.profile.setRules( rules );

    }
}
