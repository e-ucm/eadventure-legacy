package es.eucm.eadventure.editor.control.controllers;

import java.util.ArrayList;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

/**
 * Controller for the list of flags.
 * 
 * @author Bruno Torijano Bueno
 */
public class VarFlagsController {

	/**
	 * Link to the main controller.
	 */
	private Controller controller;

	/**
	 * Summary of flags.
	 */
	private VarFlagSummary varFlagSummary;

	/**
	 * Constructor.
	 * 
	 * @param varFlagSummary
	 *            Summary of flags.
	 */
	public VarFlagsController( VarFlagSummary varFlagSummary ) {
		controller = Controller.getInstance( );
		this.varFlagSummary = varFlagSummary;
	}

	/**
	 * Returns the number of flags.
	 * 
	 * @return Number of flags
	 */
	public int getFlagCount( ) {
		return varFlagSummary.getFlagCount( );
	}
	
	/**
	 * Returns the number of flags.
	 * 
	 * @return Number of flags
	 */
	public int getVarCount( ) {
		return varFlagSummary.getVarCount( );
	}


	/**
	 * Returns the flag name in the given position.
	 * 
	 * @param index
	 *            Index for the flag
	 * @return Flag name
	 */
	public String getFlag( int index ) {
		return varFlagSummary.getFlag( index );
	}
	
	/**
	 * Returns the flag name in the given position.
	 * 
	 * @param index
	 *            Index for the flag
	 * @return Flag name
	 */
	public String getVar( int index ) {
		return varFlagSummary.getVar( index );
	}
	
	public boolean existsFlag ( String flag ){
		return varFlagSummary.existsFlag( flag );
	}
	
	public boolean existsVar ( String var ){
		return varFlagSummary.existsVar( var );
	}

	public boolean existsId ( String id ){
		return varFlagSummary.existsId( id );
	}


	/**
	 * Returns the flag references number in the given position.
	 * 
	 * @param index
	 *            Index for the flag
	 * @return Number of references of the flag
	 */
	public int getFlagReferences( int index ) {
		return varFlagSummary.getFlagReferences( index );
	}
	
	/**
	 * Returns the flag references number in the given position.
	 * 
	 * @param index
	 *            Index for the flag
	 * @return Number of references of the flag
	 */
	public int getVarReferences( int index ) {
		return varFlagSummary.getVarReferences( index );
	}


	/**
	 * Adds a new flag, asking the name to the user.
	 * 
	 * @return True if the flag has been added, false otherwise
	 */
	public String addShortCutFlagVar( boolean flag, String id ) {
		String flagVarAdded = null;
		
		if (id == null || id.contains( " " ) || id.equals( "" )){
			if (flag)
				controller.showErrorDialog( TextConstants.getText( "Flags.AddFlag" ), TextConstants.getText( "Flags.ErrorFlagWhitespaces" ) );
			else
				controller.showErrorDialog( TextConstants.getText( "Vars.AddVar" ), TextConstants.getText( "Vars.ErrorVarWhitespaces" ) );
		}
		
		else {
			String[] similarFlags = null;
			if ( flag )
				similarFlags = getSimilarFlags( id );
			else
				similarFlags = getSimilarVars ( id );
			
			if (similarFlags.length==0){
				//Controller.getInstance( ).getFlagSummary( ).addFlag( flag );
				if ( flag )
					Controller.getInstance( ).getVarFlagSummary( ).addFlag( id );
				else
					Controller.getInstance( ).getVarFlagSummary( ).addVar( id );
				flagVarAdded = id;
			}else {
				String[] options = new String[similarFlags.length+1];
				options[0]= id+" ("+TextConstants.getText("GeneralText.New")+")";
				for (int i=1; i<options.length; i++){
					options[i]=similarFlags[i-1];
				}
			
				String option = null;
				
				if (flag)
					option = Controller.getInstance( ).showInputDialog( TextConstants.getText("Flags.ConfirmNewFlag.Title") 
						//"Confirm new flag", "You are about to create a new flag that is similar to others.\nIs this correct?\nPlease confirm you want to create that flag or select an existing one.", 
						, TextConstants.getText("Flags.ConfirmNewFlag.Text"), 
						options );
				else 
					option = Controller.getInstance( ).showInputDialog( TextConstants.getText("Vars.ConfirmNewVar.Title") 
							//"Confirm new flag", "You are about to create a new flag that is similar to others.\nIs this correct?\nPlease confirm you want to create that flag or select an existing one.", 
							, TextConstants.getText("Vars.ConfirmNewVar.Text"), 
							options );
				
				if (option != null){
					
					// If it contains white spaces, show an error
					if (option.equals( id+" ("+TextConstants.getText("GeneralText.New")+")" )){
						flagVarAdded = id;
						if (flag)
							Controller.getInstance( ).getVarFlagSummary( ).addFlag( id );
						else
							Controller.getInstance( ).getVarFlagSummary( ).addVar( id );
					}else {
						flagVarAdded = option;
						if (flag)
							Controller.getInstance( ).getVarFlagSummary( ).addFlag( option );
						else
							Controller.getInstance( ).getVarFlagSummary( ).addVar( option );
					}
					
											
				/*} else {
					flagAdded = null;
				}*/
		}}
	}

		//Controller.getInstance( ).updateFlagSummary( );
		return flagVarAdded;
	}

	
	
	/**
	 * Adds a new flag, asking the name to the user.
	 * 
	 * @return True if the flag has been added, false otherwise
	 */
	public boolean addVarFlag( boolean isFlag ) {
		boolean addedFlag = false;

		// Ask for a flag name
		String id = null;
		if (isFlag)
			id = controller.showInputDialog( TextConstants.getText( "Flags.AddFlag" ), TextConstants.getText( "Flags.AddFlagMessage" ), TextConstants.getText( "Flags.DefaultFlagId" ) );
		else
			id = controller.showInputDialog( TextConstants.getText( "Vars.AddVar" ), TextConstants.getText( "Vars.AddVarMessage" ), TextConstants.getText( "Vars.DefaultVarId" ) );

		// If some value was typed
		if( id != null ) {

			// If the flag doesn't contain spaces
			if( !id.contains( " " ) ) {

				// If the id doesn't already exists
				if( !varFlagSummary.existsId( id ) ) {
					// Add the flag
					if (isFlag)
						addedFlag = varFlagSummary.addFlag( id );
					else
						addedFlag = varFlagSummary.addVar( id );
				}

				// If it exists, show an error
				else
					controller.showErrorDialog( TextConstants.getText( "Ids.AddId" ), TextConstants.getText( "Ids.ErrorIdAlreadyExists" ) );
			}

			// If it contain white spaces, show an error
			else
				controller.showErrorDialog( TextConstants.getText( "Ids.AddId" ), TextConstants.getText( "Ids.ErrorIdWhitespaces" ) );
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
		if( varFlagSummary.getFlagReferences( index ) == 0 )
			deletedFlag = varFlagSummary.deleteFlag( varFlagSummary.getFlag( index ) );

		// If not, show an error
		else
			controller.showErrorDialog( TextConstants.getText( "Flags.DeleteFlag" ), TextConstants.getText( "Flags.ErrorFlagWithReferences" ) );

		// If the flag was added, set the data as modified
		if( deletedFlag )
			controller.dataModified( );

		return deletedFlag;
	}
	
	/**
	 * Deletes the var in the given position. This is only done when the var doesn't have any reference
	 * 
	 * @param index
	 *            Index of the var to delete
	 * @return True if the var was deleted, false otherwise
	 */
	public boolean deleteVar( int index ) {
		boolean deletedVar = false;

		// Delete the flag if it has no references
		if( varFlagSummary.getVarReferences( index ) == 0 )
			deletedVar = varFlagSummary.deleteVar( varFlagSummary.getVar( index ) );

		// If not, show an error
		else
			controller.showErrorDialog( TextConstants.getText( "Vars.DeleteVar" ), TextConstants.getText( "Vars.ErrorVarWithReferences" ) );

		// If the flag was added, set the data as modified
		if( deletedVar )
			controller.dataModified( );

		return deletedVar;
	}

	
	/**
	 * Returns a list with those vars similar to the one passed as an argument,
	 * according to a list of criteriums
	 */
	public String[] getSimilarFlags (String flag){
		return getSimilarIds ( flag, varFlagSummary.getFlags() );
	}

	/**
	 * Returns a list with those vars similar to the one passed as an argument,
	 * according to a list of criteriums
	 */
	public String[] getSimilarVars (String var){
		return getSimilarIds ( var, varFlagSummary.getVars() );
	}

	/**
	 * Returns a list with those ids similar to the one passed as an argument,
	 * according to a list of criteriums
	 */
	private String[] getSimilarIds (String id, String[] idList){
		ArrayList<String> similarIds = new ArrayList<String>();
		for (String id2: idList){
			for (SimilarityCriterium sc:SCSummary.getCriteriums( )){
				if (sc.areSimilar( id, id2 )){
					similarIds.add( id2 );
					break;
				}
			}
		}
		
		return similarIds.toArray( new String[0] );
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
