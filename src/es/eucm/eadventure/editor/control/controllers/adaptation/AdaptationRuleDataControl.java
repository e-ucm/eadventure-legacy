/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.controllers.adaptation;

import java.util.List;

import es.eucm.eadventure.common.data.adaptation.AdaptationProfile;
import es.eucm.eadventure.common.data.adaptation.AdaptationRule;
import es.eucm.eadventure.common.data.adaptation.AdaptedState;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.config.SCORMConfigData;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.tools.adaptation.AddActionTool;
import es.eucm.eadventure.editor.control.tools.adaptation.AddUOLPropertyTool;
import es.eucm.eadventure.editor.control.tools.adaptation.ChangeActionTool;
import es.eucm.eadventure.editor.control.tools.adaptation.ChangeUOLPropertyTool;
import es.eucm.eadventure.editor.control.tools.adaptation.ChangeVarFlagTool;
import es.eucm.eadventure.editor.control.tools.adaptation.DeleteActionTool;
import es.eucm.eadventure.editor.control.tools.adaptation.DeleteUOLPropertyTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeTargetIdTool;
import es.eucm.eadventure.editor.control.tools.generic.MoveObjectTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;
import es.eucm.eadventure.editor.gui.editdialogs.SCORMAttributeDialog;

public class AdaptationRuleDataControl extends DataControl {

    private AdaptationRule adaptationRule;
    
    private AdaptationProfile profile;

    public AdaptationRuleDataControl( AdaptationRule adpRule, AdaptationProfile profile ) {

        this.adaptationRule = adpRule;
        this.profile = profile;
    }

    @Override
    public boolean addElement( int type, String id ) {

        return false;
    }

    @Override
    public boolean canAddElement( int type ) {

        return false;
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

        return false;
    }

    @Override
    public int countAssetReferences( String assetPath ) {

        return 0;
    }

    @Override
    public int countIdentifierReferences( String id ) {

        if( adaptationRule.getId( ).equals( id ) ) {
            return 1;
        }
        else {
            return 0;
        }
    }

    @Override
    public void deleteAssetReferences( String assetPath ) {

    }

    @Override
    public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {

        return false;
    }

    @Override
    public void deleteIdentifierReferences( String id ) {

        // this action is done in adaptationProfileDataControl
    }

    @Override
    public int[] getAddableElements( ) {

        return new int[ 0 ];
    }

    @Override
    public Object getContent( ) {

        return adaptationRule;
    }

    @Override
    public boolean isValid( String currentPath, List<String> incidences ) {

        return true;
    }

    @Override
    public boolean moveElementDown( DataControl dataControl ) {

        return false;
    }

    @Override
    public boolean moveElementUp( DataControl dataControl ) {

        return false;
    }

    @Override
    public String renameElement( String name ) {

        return null;
    }

    @Override
    public void replaceIdentifierReferences( String oldId, String newId ) {

        // this action is done in adaptationProfileDataControl
    }

    @Override
    public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {

        for( String flag : adaptationRule.getAdaptedState( ).getFlagsVars( ) ) {
            if( isFlag( flag ) )
                varFlagSummary.addFlagReference( flag );
            else
                varFlagSummary.addVarReference( flag );
        }

    }

    public String getDescription( ) {

        return adaptationRule.getDescription( );
    }

    public void setInitialScene( String initScene ) {

        controller.addTool( new ChangeTargetIdTool( adaptationRule.getAdaptedState( ), initScene ) );
        //adaptationRule.getAdaptedState( ).setTargetId( initScene );
    }

    public String getInitialScene( ) {

        return adaptationRule.getAdaptedState( ).getTargetId( );
    }

    private AdaptedState getGameState( ) {

        return adaptationRule.getAdaptedState( );
    }

    public boolean moveUOLPropertyUp( int selectedRow ) {

        return controller.addTool( new MoveObjectTool( adaptationRule.getUOLProperties( ), selectedRow, MoveObjectTool.MODE_UP ) );
    }

    public boolean moveUOLPropertyDown( int selectedRow ) {

        return controller.addTool( new MoveObjectTool( adaptationRule.getUOLProperties( ), selectedRow, MoveObjectTool.MODE_DOWN ) );
    }

    public boolean addFlagAction( int selectedRow ) {

        return controller.addTool( new AddActionTool( adaptationRule.getAdaptedState( ), selectedRow ) );
    }

    public void deleteFlagAction( int selectedRow ) {

        controller.addTool( new DeleteActionTool( adaptationRule, selectedRow ) );
    }

    public int getFlagActionCount( ) {

        return adaptationRule.getAdaptedState( ).getFlagsVars( ).size( );
    }

    public void setFlag( int rowIndex, String flag ) {

        controller.addTool( new ChangeActionTool( adaptationRule, rowIndex, flag, ChangeActionTool.SET_ID ) );
    }

    public void change( int rowIndex, String name ) {

        //profile.getAdaptedState().change(rowIndex, name);
        controller.addTool( new ChangeVarFlagTool( adaptationRule.getAdaptedState( ), rowIndex, name ) );
    }

    public String getFlag( int rowIndex ) {

        return this.adaptationRule.getAdaptedState( ).getFlagVar( rowIndex );
    }

    public String getAction( int rowIndex ) {

        return this.adaptationRule.getAdaptedState( ).getAction( rowIndex );
    }

    public boolean isFlag( int rowIndex ) {

        return this.adaptationRule.getAdaptedState( ).isFlag( rowIndex );
    }

    public boolean isFlag( String name ) {

        return this.adaptationRule.getAdaptedState( ).isFlag( name );
    }

    public String getId( ) {

        return adaptationRule.getId( );
    }

    public void addBlankUOLProperty( int selectedRow ) {

        controller.addTool( new AddUOLPropertyTool( adaptationRule, selectedRow ) );
    }

    public void deleteUOLProperty( int selectedRow ) {

        controller.addTool( new DeleteUOLPropertyTool( adaptationRule, selectedRow ) );
    }

    public int getUOLPropertyCount( ) {

        return adaptationRule.getUOLProperties( ).size( );
    }

    public void setUOLPropertyValue( int rowIndex, String string ) {

        controller.addTool( new ChangeUOLPropertyTool( adaptationRule, string, rowIndex, ChangeUOLPropertyTool.SET_VALUE ) );
    }

    public void setUOLPropertyId( int rowIndex, String string ) {
	if (SCORMConfigData.isArrayAttribute(string)){
	    //check if "string" has a previous value of the same kind of selected attribute
	    if (adaptationRule.getUOLProperties().get(rowIndex).getId().startsWith(string))
		string = adaptationRule.getUOLProperties().get(rowIndex).getId();
	    string = SCORMAttributeDialog.showAttributeDialogForRead(getProfileType(), string );
	}
	
	if (!SCORMConfigData.isArrayAttribute(string))
	    controller.addTool( new ChangeUOLPropertyTool( adaptationRule, string, rowIndex, ChangeUOLPropertyTool.SET_ID ) );
    }

    public void setUOLPropertyOp( int rowIndex, String string ) {

        controller.addTool( new ChangeUOLPropertyTool( adaptationRule, string, rowIndex, ChangeUOLPropertyTool.SET_OP ) );
    }

    public String getUOLPropertyId( int rowIndex ) {

        return this.adaptationRule.getUOLProperties( ).get( rowIndex ).getId( );
    }

    public String getUOLPropertyValue( int rowIndex ) {

        return adaptationRule.getUOLProperties( ).get( rowIndex ).getValue( );
    }

    public String getUOLPropertyOp( int rowIndex ) {

        return adaptationRule.getUOLProperties( ).get( rowIndex ).getOperation( );
    }

    public void setAction( int rowIndex, String string ) {

        controller.addTool( new ChangeActionTool( adaptationRule, rowIndex, string, ChangeActionTool.SET_VALUE ) );
    }

    public int getValueToSet( int rowIndex ) {

        if( adaptationRule.getAdaptedState( ).getValueToSet( rowIndex ) == Integer.MIN_VALUE )
            return 0;
        else
            return adaptationRule.getAdaptedState( ).getValueToSet( rowIndex );

    }

    @Override
    public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {

        // Do nothing
    }

    @Override
    public boolean canBeDuplicated( ) {

        return true;
    }

    @Override
    public void recursiveSearch( ) {

        check( getDescription( ), TC.get( "Search.Description" ) );
        check( getId( ), "ID" );
        check( getInitialScene( ), TC.get( "Search.InitialScene" ) );

        for( int i = 0; i < this.getFlagActionCount( ); i++ ) {
            if( isFlag( i ) )
                check( getFlag( i ), TC.get( "Search.Flag" ) );
            else
                check( getFlag( i ), TC.get( "Search.Var" ) );

            check( getAction( i ), TC.get( "Search.ActionOverGameState" ) );
        }
        for( int i = 0; i < this.getUOLPropertyCount( ); i++ ) {
            check( this.getUOLPropertyId( i ), TC.get( "Search.LMSPropertyID" ) );
            check( this.getUOLPropertyValue( i ), TC.get( "Search.LMSPropertyValue" ) );
        }
    }

    
    public int getProfileType(){
	if (profile.isScorm12())
	    return SCORMConfigData.SCORM_V12;
	else if (profile.isScorm2004())
	    return SCORMConfigData.SCORM_2004;
	else
	    return -1;
	
    }
    
    @Override
    public List<Searchable> getPathToDataControl( Searchable dataControl ) {

        return null;
    }
}
