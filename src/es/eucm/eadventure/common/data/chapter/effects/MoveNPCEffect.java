package es.eucm.eadventure.common.data.chapter.effects;

/**
 * An effect that makes a character to walk to a given position.
 */
public class MoveNPCEffect implements Effect {

	/**
	 * Id of the npc who will walk
	 */
	private String idTarget;

	/**
	 * The destination of the npc
	 */
	private int x;

	private int y;

	/**
	 * Creates a new FunctionalMoveNPCEffect.
	 * 
	 * @param idTarget
	 *            the id of the character who will walk
	 * @param x
	 *            X final position for the NPC
	 * @param y
	 *            Y final position for the NPC
	 */
	public MoveNPCEffect( String idTarget, int x, int y ) {
		this.idTarget = idTarget;
		this.x = x;
		this.y = y;
	}

	public int getType( ) {
		return MOVE_NPC;
	}

	/**
	 * Returns the id target.
	 * 
	 * @return Id target
	 */
	public String getIdTarget( ) {
		return idTarget;
	}

	/**
	 * Sets the new id target.
	 * 
	 * @param idTarget
	 *            New id target
	 */
	public void setIdTarget( String idTarget ) {
		this.idTarget = idTarget;
	}

	/**
	 * Returns the destiny x position.
	 * 
	 * @return Destiny x coord
	 */
	public int getX( ) {
		return x;
	}

	/**
	 * Returns the destiny y position.
	 * 
	 * @return Destiny y coord
	 */
	public int getY( ) {
		return y;
	}

	/**
	 * Sets the new destiny position
	 * 
	 * @param x
	 *            New destiny X coordinate
	 * @param y
	 *            New destiny Y coordinate
	 */
	public void setDestiny( int x, int y ) {
		this.x = x;
		this.y = y;
	}

	public Object clone() throws CloneNotSupportedException {
		MoveNPCEffect npe = (MoveNPCEffect) super.clone();
		npe.idTarget = (idTarget != null ? new String(idTarget) : null);
		npe.x = x;
		npe.y = y;
		return npe;
	}
}