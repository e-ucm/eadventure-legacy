package es.eucm.eadventure.common.data.chapter.effects;

import es.eucm.eadventure.common.data.Documented;

/**
 * Group of effects named with an Id, so it can be refered to
 * in diverse points of the chapter
 * @author Javier
 *
 */
public class Macro extends Effects implements Documented{

	/**
	 * Id of the Effects group
	 */
	private String id;
	
	/**
	 * Documentation (not used in game engine)
	 */
	private String documentation;
	
	/**
	 * Constructor
	 */
	public Macro ( String id ){
		super ( );
		this.id = id;
		this.documentation = new String();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the documentation
	 */
	public String getDocumentation() {
		return documentation;
	}

	/**
	 * @param documentation the documentation to set
	 */
	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}
	
	public Object clone() throws CloneNotSupportedException {
		Macro m = (Macro) super.clone();
		m.documentation = (documentation != null ? new String(documentation) : null);
		m.id = (id != null ? new String(id) : null);
		return m;
	}
}