package es.eucm.eadventure.adventureeditor.data.supportdata;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds the name of all the active flags in the script.
 * 
 * @author Bruno Torijano Bueno
 */
public class FlagSummary {

	/**
	 * List of flags.
	 */
	private List<String> flags;

	/**
	 * List of flag references.
	 */
	private List<Integer> flagReferences;

	/**
	 * Constructor.
	 */
	public FlagSummary( ) {
		// Create the lists
		flags = new ArrayList<String>( );
		flagReferences = new ArrayList<Integer>( );
	}

	/**
	 * Clears the summary, deleting all flags and references.
	 */
	public void clear( ) {
		// Clear both lists
		flags.clear( );
		flagReferences.clear( );
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
			sortList( );
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
	 * Adds a new flag reference (creates the flag with one reference, or updates the references).
	 * 
	 * @param flag
	 *            New flag
	 */
	public void addReference( String flag ) {

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
			sortList( );
		}
	}

	/**
	 * Deletes the given flag from the list
	 * 
	 * @param flag
	 *            Flag to be deleted
	 */
	public void deleteReference( String flag ) {

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
	 * Returns the number of flags present in the summary.
	 * 
	 * @return Number of flags
	 */
	public int getFlagCount( ) {
		return flags.size( );
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
	 * Returns an array with all the flags of the list
	 * 
	 * @return Array with all the flags
	 */
	public String[] getFlags( ) {
		return flags.toArray( new String[] {} );
	}

	/**
	 * Sorts the lists of flags and resources, by the name of the flags.
	 */
	private void sortList( ) {
		// Bubble sorting
		try{
		for( int i = 0; i < flags.size( ) - 1; i++ ) {
			for( int j = 0; j < ( flags.size( ) - 1 ) - i; j++ ) {
				// If the current flag is greater than the next one, swap values (flag and references)
				if( flags.get( j ).compareTo( flags.get( j + 1 ) ) > 0 ) {
					flags.add( j + 1, flags.remove( j ) );
					flagReferences.add( j + 1, flagReferences.remove( j ) );
				}
			}
		}}
		catch(NullPointerException e){
			e.printStackTrace( );
		}
	}
}
