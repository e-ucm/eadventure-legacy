package es.eucm.eadventure.editor.control.controllers.atrezzo;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.elements.Atrezzo;
import es.eucm.eadventure.common.gui.TextConstants;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;

import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class AtrezzoListDataControl extends DataControl {

	/**
	 * List of atrezzo items.
	 */
	private List<Atrezzo> atrezzoList;

	/**
	 * List of atrezzo item controllers.
	 */
	private List<AtrezzoDataControl> atrezzoDataControlList;
	
	/**
	 * Constructor.
	 * 
	 * @param itemsList
	 *            List of items
	 */
	public AtrezzoListDataControl( List<Atrezzo> atrezzoList ) {
		this.atrezzoList = atrezzoList;

		// Create subcontrollers
		atrezzoDataControlList = new ArrayList<AtrezzoDataControl>( );
		for( Atrezzo atrezzo : atrezzoList )
			atrezzoDataControlList.add( new AtrezzoDataControl( atrezzo ) );
	}
	
	/**
	 * Returns the list of atrezzo item controllers.
	 * 
	 * @return Atrezzo controllers
	 */
	public List<AtrezzoDataControl> getAtrezzoList( ) {
		return atrezzoDataControlList;
	}

	/**
	 * Returns the last atrezzo item controller from the list.
	 * 
	 * @return Last item controller
	 */
	public AtrezzoDataControl getLastAtrezzo( ) {
		return atrezzoDataControlList.get( atrezzoDataControlList.size( ) - 1 );
	}
	
	/**
	 * Returns the info of the atrezzo items contained in the list.
	 * 
	 * @return Array with the information of the atrezzo items. It contains the identifier of each atrezzo item.
	 */
	public String[][] getItemsInfo( ) {
		String[][] atrezzoInfo = null;

		// Create the list for the items
		atrezzoInfo = new String[atrezzoList.size( )][1];

		// Fill the array with the info
		for( int i = 0; i < atrezzoList.size( ); i++ ) 
			atrezzoInfo[i][0] =  atrezzoList.get( i ).getId( );
			
		

		return atrezzoInfo;
	}
	
	
	
	@Override
	public boolean addElement(int type) {
		boolean elementAdded = false;

		if( type == Controller.ATREZZO ) {

			// Show a dialog asking for the item id
			String atrezzoId = controller.showInputDialog( TextConstants.getText( "Operation.AddAtrezzoTitle" ), TextConstants.getText( "Operation.AddAtrezzoMessage" ), TextConstants.getText( "Operation.AddAtrezzoDefaultValue" ) );

			// If some value was typed and the identifier is valid
			if( atrezzoId != null && controller.isElementIdValid( atrezzoId ) ) {
				// Add thew new item
				Atrezzo newAtrezzo = new Atrezzo( atrezzoId );
				atrezzoList.add( newAtrezzo );
				atrezzoDataControlList.add( new AtrezzoDataControl( newAtrezzo ) );
				controller.getIdentifierSummary( ).addAtrezzoId( atrezzoId );
				//controller.dataModified( );
				elementAdded = true;
			}
		}

		return elementAdded;
	}

	

	@Override
	public boolean canAddElement(int type) {
		// It can always add new atrezzo items
		return type == Controller.ATREZZO;
	}

	@Override
	public boolean canBeDeleted() {
		return false;
	}

	@Override
	public boolean canBeDuplicated() {
		return false;
	}

	@Override
	public boolean canBeMoved() {
		return false;
	}

	@Override
	public boolean canBeRenamed() {
		return false;
	}

	@Override
	public int countAssetReferences(String assetPath) {
		int count = 0;

		// Iterate through each atrezzo item
		for( AtrezzoDataControl atrezzoDataControl : atrezzoDataControlList )
			count += atrezzoDataControl.countAssetReferences( assetPath );

		return count;
	}

	@Override
	public int countIdentifierReferences(String id) {
		int count = 0;

		// Iterate through each atrezzo item
		for( AtrezzoDataControl atrezzoDataControl : atrezzoDataControlList )
			count += atrezzoDataControl.countIdentifierReferences( id );

		return count;
		
	}

	@Override
	public void deleteAssetReferences(String assetPath) {
		// Iterate through each atrezzo item
		for( AtrezzoDataControl atrezzoDataControl : atrezzoDataControlList )
			atrezzoDataControl.deleteAssetReferences( assetPath );
		
	}

	@Override
	public boolean deleteElement(DataControl dataControl, boolean askConfirmation) {
		boolean elementDeleted = false;
		String atrezzoId = ( (AtrezzoDataControl) dataControl ).getId( );
		String references = String.valueOf( controller.countIdentifierReferences( atrezzoId ) );

		// Ask for confirmation
		if(!askConfirmation || controller.showStrictConfirmDialog( TextConstants.getText( "Operation.DeleteElementTitle" ), TextConstants.getText( "Operation.DeleteElementWarning", new String[] { atrezzoId, references } ) ) ) {
			if( atrezzoList.remove( dataControl.getContent( ) ) ) {
				atrezzoDataControlList.remove( dataControl );
				controller.deleteIdentifierReferences( atrezzoId );
				controller.getIdentifierSummary( ).deleteItemId( atrezzoId );
				//controller.dataModified( );
				elementDeleted = true;
			}
		}

		return elementDeleted;
	}

	@Override
	public void deleteIdentifierReferences(String id) {
		// This method is empty
		
	}

	@Override
	public int[] getAddableElements() {
		return new int[] { Controller.ATREZZO };
	}

	@Override
	public void getAssetReferences(List<String> assetPaths,
			List<Integer> assetTypes) {
		// Iterate through each atrezzo item
		for( AtrezzoDataControl atrezzoDataControl : atrezzoDataControlList )
			atrezzoDataControl.getAssetReferences( assetPaths, assetTypes );
		
	}

	@Override
	public Object getContent() {
		return atrezzoList;
	}

	@Override
	public boolean isValid(String currentPath, List<String> incidences) {
		boolean valid = true;

		// Update the current path
		currentPath += " >> " + TextConstants.getElementName( Controller.ATREZZO_LIST );

		// Iterate through the atrezzo items
		for( AtrezzoDataControl atrezzoDataControl : atrezzoDataControlList ) {
			String itemPath = currentPath + " >> " + atrezzoDataControl.getId( );
			valid &= atrezzoDataControl.isValid( itemPath, incidences );
		}

		return valid;
	}

	@Override
	public boolean moveElementDown(DataControl dataControl) {
		boolean elementMoved = false;
		int elementIndex = atrezzoList.indexOf( dataControl.getContent( ) );

		if( elementIndex < atrezzoList.size( ) - 1 ) {
			atrezzoList.add( elementIndex + 1, atrezzoList.remove( elementIndex ) );
			atrezzoDataControlList.add( elementIndex + 1, atrezzoDataControlList.remove( elementIndex ) );
			//controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean moveElementUp(DataControl dataControl) {
		boolean elementMoved = false;
		int elementIndex = atrezzoList.indexOf( dataControl.getContent( ) );

		if( elementIndex > 0 ) {
			atrezzoList.add( elementIndex - 1, atrezzoList.remove( elementIndex ) );
			atrezzoDataControlList.add( elementIndex - 1, atrezzoDataControlList.remove( elementIndex ) );
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
	public void replaceIdentifierReferences(String oldId, String newId) {
		// Iterate through each item
		for( AtrezzoDataControl atrezzoDataControl : atrezzoDataControlList )
			atrezzoDataControl.replaceIdentifierReferences( oldId, newId );
	}
		
		
	

	@Override
	public void updateVarFlagSummary(VarFlagSummary varFlagSummary) {
		//This method is empty because the atrezzo items there aren´t interactive, and its haven´t associated flags/vars 
		
	}
	
}
