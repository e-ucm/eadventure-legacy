package es.eucm.eadventure.common.data.chapter.effects;

/**
 * An based on a reference to a macro
 * @author Javier
 *
 */
public class MacroReferenceEffect implements Effect{

	/**
	 * The id
	 */
	private String macroId;
	
	/**
	 * Constructor
	 * @param flagVar
	 * @param state
	 */
	public MacroReferenceEffect( String id ) {
		macroId = id;
	}

	public int getType() {
		return Effect.MACRO_REF;
	}

	/**
	 * @return the macroId
	 */
	public String getMacroId() {
		return macroId;
	}

	/**
	 * @param macroId the macroId to set
	 */
	public void setMacroId(String macroId) {
		this.macroId = macroId;
	}

}
