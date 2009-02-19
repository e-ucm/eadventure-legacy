package es.eucm.eadventure.common.data.chapter.effects;

import es.eucm.eadventure.common.data.HasTargetId;

/**
 * An effect that triggers a cutscene
 */
public class TriggerCutsceneEffect implements Effect, HasTargetId {

	/**
	 * Id of the cutscene to be played
	 */
	private String targetCutsceneId;

	/**
	 * Creates a new TriggerCutsceneEffect
	 * 
	 * @param targetCutsceneId
	 *            the id of the cutscene to be triggered
	 */
	public TriggerCutsceneEffect( String targetCutsceneId ) {
		this.targetCutsceneId = targetCutsceneId;
	}

	public int getType( ) {
		return TRIGGER_CUTSCENE;
	}

	/**
	 * Returns the targetCutsceneId
	 * 
	 * @return String containing the targetCutsceneId
	 */
	public String getTargetId( ) {
		return targetCutsceneId;
	}

	/**
	 * Sets the new targetCutsceneId
	 * 
	 * @param targetCutsceneId
	 *            New targetCutsceneId
	 */
	public void setTargetId( String targetCutsceneId ) {
		this.targetCutsceneId = targetCutsceneId;
	}
	
	public Object clone() throws CloneNotSupportedException {
		TriggerCutsceneEffect tce = (TriggerCutsceneEffect) super.clone();
		tce.targetCutsceneId = (targetCutsceneId != null ? new String(targetCutsceneId) : null);
		return tce;
	}
}
