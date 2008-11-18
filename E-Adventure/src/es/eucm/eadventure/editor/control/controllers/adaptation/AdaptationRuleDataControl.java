package es.eucm.eadventure.editor.control.controllers.adaptation;

import java.util.List;

import es.eucm.eadventure.common.data.adaptation.AdaptationRule;
import es.eucm.eadventure.common.data.adaptation.UOLProperty;
import es.eucm.eadventure.common.data.assessment.AssessmentProperty;
import es.eucm.eadventure.common.data.assessment.AssessmentRule;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.data.supportdata.FlagSummary;
import es.eucm.eadventure.editor.gui.TextConstants;

import java.util.regex.Pattern;

public class AdaptationRuleDataControl extends DataControl{

	private AdaptationRule adaptationRule;
	//TODO PANEL
	
	public AdaptationRuleDataControl (AdaptationRule adpRule){
		this.adaptationRule = adpRule;
	}
	
	@Override
	public boolean addElement( int type ) {
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
		return 0;
	}

	@Override
	public void deleteAssetReferences( String assetPath ) {
		
	}

	@Override
	public boolean deleteElement( DataControl dataControl ) {
		return false;
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[0];
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
	public boolean renameElement( ) {
		boolean renamed = false;
		return renamed;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		
	}

	@Override
	public void updateFlagSummary( FlagSummary flagSummary ) {
		for (String flag: adaptationRule.getAdaptedState( ).getFlags( )){
			flagSummary.addReference( flag );	
		}
		
	}

	public String getDescription( ) {
		return adaptationRule.getDescription( );
	}


	public void setDescription( String text ) {
		adaptationRule.setDescription( text );
		
	}

	public void setInitialScene( String initScene ) {
		adaptationRule.getAdaptedState( ).setInitialScene( initScene );
	}
	
	public String getInitialScene(  ) {
		return adaptationRule.getAdaptedState( ).getInitialScene( );
	}

	public boolean moveUOLPropertyUp( int selectedRow ) {
		boolean elementMoved = false;

		if( selectedRow > 0 ) {
			adaptationRule.getUOLProperties( ).add( selectedRow - 1, adaptationRule.getUOLProperties( ).remove( selectedRow ) );
			controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;

	}

	public boolean moveUOLPropertyDown( int selectedRow ) {
		boolean elementMoved = false;

		if( selectedRow < adaptationRule.getUOLProperties( ).size( ) - 1 ) {
			adaptationRule.getUOLProperties( ).add( selectedRow + 1, adaptationRule.getUOLProperties( ).remove( selectedRow ) );
			controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;	
	}

	public boolean addFlagAction( int selectedRow ) {
		
		boolean added=false;
		//Check there is at least one flag

		String[] flags = Controller.getInstance( ).getFlagSummary( ).getFlags( );
		if (flags!=null && flags.length>0){

			//	By default, the flag is activated. Default flag will be the first one
			adaptationRule.getAdaptedState( ).addActivatedFlag( flags[0] );
			Controller.getInstance( ).updateFlagSummary( );
			added=true;
		}
		
		//Otherwise, prompt error message
		// If the list had no elements, show an error message
		else
			Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Adaptation.ErrorNoFlags.Title" ), TextConstants.getText( "Adaptation.ErrorNoFlags" ) );
		
		return added;
	}

	public void deleteFlagAction( int selectedRow ) {
		if (selectedRow >=0 && selectedRow <adaptationRule.getAdaptedState( ).getFlags( ).size( )){
			adaptationRule.getAdaptedState( ).removeFlag( selectedRow );
			controller.updateFlagSummary( );
		}
	}

	public int getFlagActionCount( ) {
		return adaptationRule.getAdaptedState( ).getFlags( ).size( );
	}

	public void changeAction( int rowIndex ) {
		if (rowIndex >=0 && rowIndex <adaptationRule.getAdaptedState( ).getFlags( ).size( )){
			adaptationRule.getAdaptedState( ).changeAction( rowIndex );
		}

		
	}

	public void setFlag( int rowIndex, String flag ) {
		if (rowIndex >=0 && rowIndex <adaptationRule.getAdaptedState( ).getFlags(  ).size( )){
			adaptationRule.getAdaptedState( ).changeFlag( rowIndex, flag );
			controller.updateFlagSummary( );
		}
		
	}

	public String getFlag( int rowIndex ) {
		return this.adaptationRule.getAdaptedState( ).getFlag( rowIndex );
	}

	public String getAction( int rowIndex ) {
		return this.adaptationRule.getAdaptedState( ).getAction( rowIndex );
	}

	public String getId( ) {
		return adaptationRule.getId( );
	}

	
	public void addBlankUOLProperty( int selectedRow ) {
		adaptationRule.getUOLProperties( ).add( selectedRow, new UOLProperty("PropertyId", "PropertyValue") );
	}

	public void deleteUOLProperty( int selectedRow ) {
		if (selectedRow >=0 && selectedRow <adaptationRule.getUOLProperties( ).size( )){
			adaptationRule.getUOLProperties( ).remove( selectedRow );
		}
	}

	public int getUOLPropertyCount( ) {
		return adaptationRule.getUOLProperties( ).size( );
	}

	public void setUOLPropertyValue( int rowIndex, String string ) {
		if (rowIndex >=0 && rowIndex <adaptationRule.getUOLProperties( ).size( )){
			//Check only integers are set
			if (controller.isPropertyIdValid( string ))
				adaptationRule.getUOLProperties( ).get( rowIndex ).setValue( string );
			
		}

		
	}

	public void setUOLPropertyId( int rowIndex, String string ) {
		if (rowIndex >=0 && rowIndex <adaptationRule.getUOLProperties( ).size( )){
			if (controller.isPropertyIdValid( string ))
				adaptationRule.getUOLProperties( ).get( rowIndex ).setId( string );
		}
		
	}

	public String getUOLPropertyId( int rowIndex ) {
		return this.adaptationRule.getUOLProperties( ).get( rowIndex ).getId( );
	}

	public String getUOLPropertyValue( int rowIndex ) {
		return adaptationRule.getUOLProperties( ).get( rowIndex ).getValue( );
	}

	public void setAction( int rowIndex, String string ) {
		
		if (!adaptationRule.getAdaptedState( ).getAction( rowIndex ).equals( string ))
			adaptationRule.getAdaptedState( ).changeAction( rowIndex );
		
	}

	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		// Do nothing
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}


}
