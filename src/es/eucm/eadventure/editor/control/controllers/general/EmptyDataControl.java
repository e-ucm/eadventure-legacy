package es.eucm.eadventure.editor.control.controllers.general;

import java.util.List;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class EmptyDataControl extends DataControl{

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
		return false;
	}

	@Override
	public boolean canBeDuplicated( ) {
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
	public int countIdentifierReferences( String id ) {
		return 0;
	}

	@Override
	public void deleteAssetReferences( String assetPath ) {
		
	}

	@Override
	public boolean deleteElement( DataControl dataControl , boolean askConfirmation) {
		return false;
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[]{};
	}

	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
	}

	@Override
	public Object getContent( ) {
		return null;
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
	public String renameElement(String name ) {
		return null;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
		
	}

	@Override
	public void recursiveSearch() {
	}

}
