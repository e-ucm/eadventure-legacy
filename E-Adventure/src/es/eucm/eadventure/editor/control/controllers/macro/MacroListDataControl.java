package es.eucm.eadventure.editor.control.controllers.macro;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.effects.Macro;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class MacroListDataControl extends DataControl {

	/**
	 * List of macros.
	 */
	private List<Macro> macrosList;

	/**
	 * List of macro controllers.
	 */
	private List<MacroDataControl> macrosDataControlList;

	/**
	 * Constructor.
	 * 
	 * @param macrosList
	 *            List of macros
	 */
	public MacroListDataControl( List<Macro> macrosList ) {
		this.macrosList = macrosList;

		// Create subcontrollers
		macrosDataControlList = new ArrayList<MacroDataControl>( );
		for( Macro macro : macrosList )
			macrosDataControlList.add( new MacroDataControl( macro ) );
	}

	/**
	 * Returns the list of macro controllers.
	 * 
	 * @return Macro controllers
	 */
	public List<MacroDataControl> getMacros( ) {
		return macrosDataControlList;
	}

	/**
	 * Returns the last macro controller from the list.
	 * 
	 * @return Last macro controller
	 */
	public MacroDataControl getLastMacro( ) {
		return macrosDataControlList.get( macrosDataControlList.size( ) - 1 );
	}

	/**
	 * Returns the info of the macros contained in the list.
	 * 
	 * @return Array with the information of the macros. It contains the identifier of each macro, and the number of
	 *         actions
	 */
	public String[][] getMacrosInfo( ) {
		String[][] macrosInfo = null;

		// Create the list for the macros
		macrosInfo = new String[macrosList.size( )][2];

		// Fill the array with the info
		for( int i = 0; i < macrosList.size( ); i++ ) {
			Macro macro = macrosList.get( i );
			macrosInfo[i][0] = macro.getId( );
			macrosInfo[i][1] = Integer.toString( Controller.getInstance().countIdentifierReferences( macro.getId() ) );
		}

		return macrosInfo;
	}

	@Override
	public Object getContent( ) {
		return macrosList;
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] { Controller.MACRO };
	}

	@Override
	public boolean canAddElement( int type ) {
		// It can always add new macros
		return type == Controller.MACRO;
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
	public boolean addElement( int type ) {
		boolean elementAdded = false;

		if( type == Controller.MACRO ) {

			// Show a dialog asking for the macro id
			String macroId = controller.showInputDialog( TextConstants.getText( "Operation.AddMacroTitle" ), TextConstants.getText( "Operation.AddMacroMessage" ), TextConstants.getText( "Operation.AddMacroDefaultValue" ) );

			// If some value was typed and the identifier is valid
			if( macroId != null && controller.isElementIdValid( macroId ) ) {
				// Add thew new macro
				Macro newMacro = new Macro( macroId );
				macrosList.add( newMacro );
				macrosDataControlList.add( new MacroDataControl( newMacro ) );
				controller.getIdentifierSummary( ).addMacroId( macroId );
				//controller.dataModified( );
				elementAdded = true;
			}
		}

		return elementAdded;
	}

	@Override
	public boolean deleteElement( DataControl dataControl , boolean askConfirmation) {
		boolean elementDeleted = false;
		String macroId = ( (MacroDataControl) dataControl ).getId( );
		String references = String.valueOf( controller.countIdentifierReferences( macroId ) );

		// Ask for confirmation
		if(!askConfirmation || controller.showStrictConfirmDialog( TextConstants.getText( "Operation.DeleteElementTitle" ), TextConstants.getText( "Operation.DeleteElementWarning", new String[] { macroId, references } ) ) ) {
			if( macrosList.remove( dataControl.getContent( ) ) ) {
				macrosDataControlList.remove( dataControl );
				controller.deleteIdentifierReferences( macroId );
				controller.getIdentifierSummary( ).deleteMacroId( macroId );
				//controller.dataModified( );
				elementDeleted = true;
			}
		}

		return elementDeleted;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = macrosList.indexOf( dataControl.getContent( ) );

		if( elementIndex > 0 ) {
			macrosList.add( elementIndex - 1, macrosList.remove( elementIndex ) );
			macrosDataControlList.add( elementIndex - 1, macrosDataControlList.remove( elementIndex ) );
			//controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = macrosList.indexOf( dataControl.getContent( ) );

		if( elementIndex < macrosList.size( ) - 1 ) {
			macrosList.add( elementIndex + 1, macrosList.remove( elementIndex ) );
			macrosDataControlList.add( elementIndex + 1, macrosDataControlList.remove( elementIndex ) );
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
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
		// Iterate through each macro
		for( MacroDataControl macroDataControl : macrosDataControlList )
			macroDataControl.updateVarFlagSummary( varFlagSummary );
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean valid = true;

		// Update the current path
		currentPath += " >> " + TextConstants.getElementName( Controller.GLOBAL_STATE_LIST );

		// Iterate through the macros
		for( MacroDataControl macroDataControl : macrosDataControlList ) {
			String macroPath = currentPath + " >> " + macroDataControl.getId( );
			valid &= macroDataControl.isValid( macroPath, incidences );
		}

		return valid;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		// Iterate through each macro
		for( MacroDataControl macroDataControl : macrosDataControlList )
			count += macroDataControl.countAssetReferences( assetPath );

		return count;
	}
	
	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		// Iterate through each macro
		for( MacroDataControl macroDataControl : macrosDataControlList )
			macroDataControl.getAssetReferences( assetPaths, assetTypes );
	}


	@Override
	public void deleteAssetReferences( String assetPath ) {
		// Iterate through each macro
		for( MacroDataControl macroDataControl : macrosDataControlList )
			macroDataControl.deleteAssetReferences( assetPath );
	}

	@Override
	public int countIdentifierReferences( String id ) {
		int count = 0;

		// Iterate through each macro
		for( MacroDataControl macroDataControl : macrosDataControlList )
			count += macroDataControl.countIdentifierReferences( id );

		return count;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		// Iterate through each macro
		for( MacroDataControl macroDataControl : macrosDataControlList )
			macroDataControl.replaceIdentifierReferences( oldId, newId );
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		// Spread the call to every macro
		for( MacroDataControl macroDataControl : macrosDataControlList )
			macroDataControl.deleteIdentifierReferences( id );
	}

	@Override
	public boolean canBeDuplicated( ) {
		// TODO Auto-generated method stub
		return false;
	}
}
