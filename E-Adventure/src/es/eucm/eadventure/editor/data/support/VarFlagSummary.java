package es.eucm.eadventure.editor.data.support;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.auxiliar.ReportDialog;

/**
 * This class holds the name of all the active flags in the script.
 * 
 * @author Bruno Torijano Bueno
 */
public class VarFlagSummary {

	/**
	 * List of flags.
	 */
	private List<String> flags;

	/**
	 * List of flag references.
	 */
	private List<Integer> flagReferences;
	
	/**
	 * List of vars.
	 */
	private List<String> vars;

	/**
	 * List of var references.
	 */
	private List<Integer> varReferences;

	/**
	 * Constructor.
	 */
	public VarFlagSummary( ) {
		// Create the lists
		flags = new ArrayList<String>( );
		flagReferences = new ArrayList<Integer>( );
		vars = new ArrayList<String>( );
		varReferences = new ArrayList<Integer>( );
	}

	/**
	 * Clears the summary, deleting all flags and references.
	 */
	public void clear( ) {
		// Clear both lists
		flags.clear( );
		flagReferences.clear( );
		vars.clear( );
		varReferences.clear( );
	}

	/**
	 * Adds a new flag to the list (with zero references).
	 * 
	 * @param flag
	 *            New flag
	 * @return True if the flag was added, false otherwise
	 */
	public boolean addFlag( String flag ) {
		boolean addedFlag = false;

		// Add it only if it doesn't exist
		if( !existsFlag( flag ) ) {
			flags.add( flag );
			flagReferences.add( 0 );
			addedFlag = true;

			// Sort the list
			sortList( flags, flagReferences );
		}

		return addedFlag;
	}

	/**
	 * Deletes the given flag from the list.
	 * 
	 * @param flag
	 *            Flag to be deleted
	 * @return True if the flag was deleted, false otherwise
	 */
	public boolean deleteFlag( String flag ) {
		boolean deletedFlag = false;

		// Get the index of the flag
		int flagIndex = flags.indexOf( flag );

		// If the flag exists, delete the info
		if( flagIndex >= 0 ) {
			flags.remove( flagIndex );
			flagReferences.remove( flagIndex );
			deletedFlag = true;
		}

		return deletedFlag;
	}

	/**
	 * Adds a new var to the list (with zero references).
	 * 
	 * @param flag
	 *            New var
	 * @return True if the var was added, false otherwise
	 */
	public boolean addVar( String var ) {
		boolean addedVar = false;

		// Add it only if it doesn't exist
		if( !existsFlag( var ) ) {
			vars.add( var );
			varReferences.add( 0 );
			addedVar = true;

			// Sort the list
			sortList( vars, varReferences );
		}

		return addedVar;
	}

	/**
	 * Deletes the given var from the list.
	 * 
	 * @param var
	 *            Var to be deleted
	 * @return True if the var was deleted, false otherwise
	 */
	public boolean deleteVar( String var ) {
		boolean deletedVar = false;

		// Get the index of the flag
		int varIndex = vars.indexOf( var );

		// If the var exists, delete the info
		if( varIndex >= 0 ) {
			vars.remove( varIndex );
			varReferences.remove( varIndex );
			deletedVar = true;
		}

		return deletedVar;
	}
	
	/**
	 * Adds a new flag reference (creates the flag with one reference, or updates the references).
	 * 
	 * @param flag
	 *            New flag
	 */
	public void addFlagReference( String flag ) {

		// Get the index of the flag
		int flagIndex = flags.indexOf( flag );

		// If the flag was on the list, update the references
		if( flagIndex >= 0 ) {
			int references = flagReferences.get( flagIndex ) + 1;
			flagReferences.set( flagIndex, references );
		}

		// If the flag wasn't on the list, add it
		else {
			flags.add( flag );
			flagReferences.add( 1 );

			// Sort the list
			sortList( flags, flagReferences );
		}
	}

	/**
	 * Deletes the given flag from the list
	 * 
	 * @param flag
	 *            Flag to be deleted
	 */
	public void deleteFlagReference( String flag ) {

		// Get the index of the flag
		int flagIndex = flags.indexOf( flag );

		// If the flag is on the list
		if( flagIndex >= 0 ) {
			// Get the number of references, decrease it and update
			int references = flagReferences.get( flagIndex ) - 1;
			flagReferences.set( flagIndex, references );
		}

		// If it is not, show an error message
		else
			System.err.println( "Error: Trying to delete a nonexistent flag" );
	}
	
	/**
	 * Deletes the given if (either flag or var) from the list
	 * 
	 * @param id
	 *            Id to be deleted
	 */
	public void deleteReference( String id ) {

		if ( flags.contains( id ) ){
			deleteFlagReference ( id );
		} else if ( vars.contains( id ) ){
			deleteVarReference ( id );
		}
	}

	/**
	 * Adds a new var reference (creates the var with one reference, or updates the references).
	 * 
	 * @param var
	 *            New var
	 */
	public void addVarReference( String var ) {

		// Get the index of the var
		int varIndex = vars.indexOf( var );

		// If the var was on the list, update the references
		if( varIndex >= 0 ) {
			int references = varReferences.get( varIndex ) + 1;
			varReferences.set( varIndex, references );
		}

		// If the var wasn't on the list, add it
		else {
			vars.add( var );
			varReferences.add( 1 );

			// Sort the list
			sortList( vars, varReferences );
		}
	}

	/**
	 * Deletes the given var from the list
	 * 
	 * @param var
	 *            Var to be deleted
	 */
	public void deleteVarReference( String var ) {

		// Get the index of the var
		int varIndex = vars.indexOf( var );

		// If the var is on the list
		if( varIndex >= 0 ) {
			// Get the number of references, decrease it and update
			int references = varReferences.get( varIndex ) - 1;
			varReferences.set( varIndex, references );
		}

		// If it is not, show an error message
		else
			System.err.println( "Error: Trying to delete a nonexistent var" );
	}
	
	/**
	 * Returns if the flag summary contains the given flag.
	 * 
	 * @param flag
	 *            Flag to be checked
	 * @return True if the list contains the flag, false otherwise
	 */
	public boolean existsFlag( String flag ) {
		return flags.contains( flag );
	}
	
	/**
	 * Returns if the var summary contains the given var.
	 * 
	 * @param var
	 *            Var to be checked
	 * @return True if the list contains the var, false otherwise
	 */
	public boolean existsVar( String var ) {
		return vars.contains( var );
	}
	
	/**
	 * Returns if the summary contains the given id (both flags & vars are checked).
	 * 
	 * @param id
	 *            Id to be checked
	 * @return True if some of the lists contain the id, false otherwise
	 */
	public boolean existsId( String id ) {
		return existsFlag ( id ) || existsVar ( id );
	}

	/**
	 * Returns the number of flags present in the summary.
	 * 
	 * @return Number of flags
	 */
	public int getFlagCount( ) {
		return flags.size( );
	}
	
	/**
	 * Returns the number of varss present in the summary.
	 * 
	 * @return Number of vars
	 */
	public int getVarCount( ) {
		return vars.size( );
	}

	/**
	 * Returns the flag name in the given position.
	 * 
	 * @param index
	 *            Index for the flag
	 * @return Flag name
	 */
	public String getFlag( int index ) {
		return flags.get( index );
	}
	
	/**
	 * Returns the var name in the given position.
	 * 
	 * @param index
	 *            Index for the var
	 * @return Var name
	 */
	public String getVar( int index ) {
		return vars.get( index );
	}

	/**
	 * Returns the flag references number in the given position.
	 * 
	 * @param index
	 *            Index for the flag
	 * @return Number of references of the flag
	 */
	public int getFlagReferences( int index ) {
		return flagReferences.get( index );
	}
	
	/**
	 * Returns the var references number in the given position.
	 * 
	 * @param index
	 *            Index for the var
	 * @return Number of references of the var
	 */
	public int getVarReferences( int index ) {
		return varReferences.get( index );
	}

	/**
	 * Returns an array with all the flags of the list
	 * 
	 * @return Array with all the flags
	 */
	public String[] getFlags( ) {
		return flags.toArray( new String[] {} );
	}

	/**
	 * Returns an array with all the vars of the list
	 * 
	 * @return Array with all the vars
	 */
	public String[] getVars( ) {
		return vars.toArray( new String[] {} );
	}
	
	/**
	 * Sorts the lists of flags and resources, by the name of the flags.
	 */
	private void sortList( List<String> list, List<Integer> refsList ) {
		// Bubble sorting
		try{
		for( int i = 0; i < list.size( ) - 1; i++ ) {
			for( int j = 0; j < ( list.size( ) - 1 ) - i; j++ ) {
				// If the current flag is greater than the next one, swap values (flag and references)
				if( list.get( j ).compareTo( list.get( j + 1 ) ) > 0 ) {
					list.add( j + 1, list.remove( j ) );
					refsList.add( j + 1, refsList.remove( j ) );
				}
			}
		}}
		catch(NullPointerException e){
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}
	}
}
