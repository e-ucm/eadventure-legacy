package es.eucm.eadventure.editor.control.controllers;

import java.util.ArrayList;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.supportdata.FlagSummary;
import es.eucm.eadventure.editor.gui.TextConstants;

/**
 * Controller for the list of flags.
 * 
 * @author Bruno Torijano Bueno
 */
public class FlagsController {

	/**
	 * Link to the main controller.
	 */
	private Controller controller;

	/**
	 * Summary of flags.
	 */
	private FlagSummary flagSummary;

	/**
	 * Constructor.
	 * 
	 * @param flagSummary
	 *            Summary of flags.
	 */
	public FlagsController( FlagSummary flagSummary ) {
		controller = Controller.getInstance( );
		this.flagSummary = flagSummary;
	}

	/**
	 * Returns the number of flags.
	 * 
	 * @return Number of flags
	 */
	public int getFlagCount( ) {
		return flagSummary.getFlagCount( );
	}

	/**
	 * Returns the flag name in the given position.
	 * 
	 * @param index
	 *            Index for the flag
	 * @return Flag name
	 */
	public String getFlag( int index ) {
		return flagSummary.getFlag( index );
	}
	
	public boolean existsFlag ( String flag ){
		return flagSummary.existsFlag( flag );
	}

	/**
	 * Returns the flag references number in the given position.
	 * 
	 * @param index
	 *            Index for the flag
	 * @return Number of references of the flag
	 */
	public int getFlagReferences( int index ) {
		return flagSummary.getFlagReferences( index );
	}

	/**
	 * Adds a new flag, asking the name to the user.
	 * 
	 * @return True if the flag has been added, false otherwise
	 */
	public String addShortCutFlag( String flag ) {
		String flagAdded = null;
		
		if (flag == null || flag.contains( " " ) || flag.equals( "" )){
			controller.showErrorDialog( TextConstants.getText( "Flags.AddFlag" ), TextConstants.getText( "Flags.ErrorFlagWhitespaces" ) );
		}
		
		else {
			String[] similarFlags = getSimilarFlags( flag );
			if (similarFlags.length==0){
				//Controller.getInstance( ).getFlagSummary( ).addFlag( flag );
				Controller.getInstance( ).getFlagSummary( ).addFlag( flag );
				flagAdded = flag;
			}else {
				String[] options = new String[similarFlags.length+1];
				options[0]= flag+" (NEW)";
				for (int i=1; i<options.length; i++){
					options[i]=similarFlags[i-1];
				}
			
				//TODO TextConstants
				String option = Controller.getInstance( ).showInputDialog( "Confirm new flag", "You are about to create a new flag that is similar to others.\nIs this correct?\nPlease confirm you want to create that flag or select an existing one.", options );
				if (option != null){
					
					// If it contain white spaces, show an error
					if (option.equals( flag+" (NEW)" )){
						flagAdded = flag;
						Controller.getInstance( ).getFlagSummary( ).addFlag( flag );
					}else {
						flagAdded = option;
						Controller.getInstance( ).getFlagSummary( ).addFlag( option );
					}
					
											
				/*} else {
					flagAdded = null;
				}*/
		}}
	}

		//Controller.getInstance( ).updateFlagSummary( );
		return flagAdded;
	}

	
	
	/**
	 * Adds a new flag, asking the name to the user.
	 * 
	 * @return True if the flag has been added, false otherwise
	 */
	public boolean addFlag( ) {
		boolean addedFlag = false;

		// Ask for a flag name
		String flag = controller.showInputDialog( TextConstants.getText( "Flags.AddFlag" ), TextConstants.getText( "Flags.AddFlagMessage" ), TextConstants.getText( "Flags.DefaultFlagId" ) );

		// If some value was typed
		if( flag != null ) {

			// If the flag doesn't contain spaces
			if( !flag.contains( " " ) ) {

				// If the flag doesn't already exists
				if( !flagSummary.existsFlag( flag ) ) {
					// Add the flag
					addedFlag = flagSummary.addFlag( flag );
				}

				// If it exists, show an error
				else
					controller.showErrorDialog( TextConstants.getText( "Flags.AddFlag" ), TextConstants.getText( "Flags.ErrorFlagAlreadyExists" ) );
			}

			// If it contain white spaces, show an error
			else
				controller.showErrorDialog( TextConstants.getText( "Flags.AddFlag" ), TextConstants.getText( "Flags.ErrorFlagWhitespaces" ) );
		}

		// If the flag was added, set the data as modified
		if( addedFlag )
			controller.dataModified( );

		return addedFlag;
	}

	/**
	 * Deletes the flag in the given position. This is only done when the flag doesn't have any reference
	 * 
	 * @param index
	 *            Index of the flag to delete
	 * @return True if the flag was deleted, false otherwise
	 */
	public boolean deleteFlag( int index ) {
		boolean deletedFlag = false;

		// Delete the flag if it has no references
		if( flagSummary.getFlagReferences( index ) == 0 )
			deletedFlag = flagSummary.deleteFlag( flagSummary.getFlag( index ) );

		// If not, show an error
		else
			controller.showErrorDialog( TextConstants.getText( "Flags.DeleteFlag" ), TextConstants.getText( "Flags.ErrorFlagWithReferences" ) );

		// If the flag was added, set the data as modified
		if( deletedFlag )
			controller.dataModified( );

		return deletedFlag;
	}
	
	public String[] getSimilarFlags (String flag){
		ArrayList<String> similarFlags = new ArrayList<String>();
		for (String flag2: flagSummary.getFlags( )){
			for (SimilarityCriterium sc:SCSummary.getCriteriums( )){
				if (sc.areSimilar( flag, flag2 )){
					similarFlags.add( flag2 );
					break;
				}
			}
		}
		
		return similarFlags.toArray( new String[0] );
	}
	
	private static class SCSummary{
		public static SimilarityCriterium[] getCriteriums(){
			return new SimilarityCriterium[]{new Criterium1(), new Criterium2(), new Criterium3()};
		}
	}
	
	private static interface SimilarityCriterium {
		public boolean areSimilar(String flag1, String flag2);
	}
	
	private static class Criterium1 implements SimilarityCriterium{

		public boolean areSimilar( String flag1, String flag2 ) {
			char[] flag1Chars = flag1.toCharArray( );
			char[] flag2Chars = flag2.toCharArray( );
			int equalChars = 0;
			for (int i=0; i<Math.min( flag1Chars.length, flag2Chars.length ); i++){
				if (flag1Chars[i]==flag2Chars[i]){
					equalChars++;
				}
			}
			return (float)equalChars/(float)Math.max( flag1Chars.length, flag2Chars.length ) > 0.8f;
		}
		
	}
	
	private static class Criterium2 implements SimilarityCriterium{

		public boolean areSimilar( String flag1, String flag2 ) {
			char[] flag1Chars = flag1.toCharArray( );
			char[] flag2Chars = flag2.toCharArray( );
			int difChars = 0;
			for (int i=0; i<Math.max( flag1Chars.length, flag2Chars.length ); i++){
				if (i>=flag1Chars.length || i>=flag2Chars.length || flag1Chars[i]!=flag2Chars[i]){
					difChars++;
				}
			}
			return difChars<=2;
		}
		
	}
	
	private static class Criterium3 implements SimilarityCriterium{

		public boolean areSimilar( String flag1, String flag2 ) {
			return (flag1.startsWith( flag2 ) || flag2.startsWith( flag1 ) || flag1.endsWith( flag2 ) || flag2.endsWith( flag1 ) || flag1.contains( flag2 ) || flag2.contains( flag1 ));
		}
		
	}


	
}
