package es.eucm.eadventure.common.data.chapter.effects;

import es.eucm.eadventure.common.data.HasTargetId;

/**
 * An based on a reference to a macro
 * @author Javier
 *
 */
public class MacroReferenceEffect extends AbstractEffect implements HasTargetId{

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
	    	super();
		macroId = id;
	}

	public int getType() {
		return Effect.MACRO_REF;
	}

	/**
	 * @return the macroId
	 */
	public String getTargetId() {
		return macroId;
	}

	/**
	 * @param macroId the macroId to set
	 */
	public void setTargetId(String macroId) {
		this.macroId = macroId;
	}

	public Object clone() throws CloneNotSupportedException {
		MacroReferenceEffect mre = (MacroReferenceEffect) super.clone();
		mre.macroId = (macroId != null ? new String(macroId) : null);
		return mre;
	}

}
