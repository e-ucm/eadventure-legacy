package es.eucm.eadventure.common.data.chapter;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.effects.Effects;

public class Timer implements Cloneable, Documented {
	
	public static final long DEFAULT_SECONDS = 60L;

	private long seconds;
	
	private Conditions initCond;
	
	private Conditions endCond;
	
	private Effects effect;
	
	private Effects postEffect;
	
	private String documentation;
	
	public Timer(long time, Conditions init, Conditions end, Effects effect, Effects postEffect){
		this.seconds = time;
		this.initCond = init;
		this.endCond = end;
		this.effect = effect;
		this.postEffect = postEffect;
	}
	
	public Timer(long time){
		this(time, new Conditions(), new Conditions(), new Effects(), new Effects());
	}
	
	public Timer(){
		this(DEFAULT_SECONDS);
	}

	/**
	 * @return the seconds
	 */
	public Long getTime( ) {
		return seconds;
	}

	/**
	 * @param seconds the seconds to set
	 */
	public void setTime( Long seconds ) {
		this.seconds = seconds;
	}

	/**
	 * @return the initCond
	 */
	public Conditions getInitCond( ) {
		return initCond;
	}

	/**
	 * @param initCond the initCond to set
	 */
	public void setInitCond( Conditions initCond ) {
		this.initCond = initCond;
	}

	/**
	 * @return the endCond
	 */
	public Conditions getEndCond( ) {
		return endCond;
	}

	/**
	 * @param endCond the endCond to set
	 */
	public void setEndCond( Conditions endCond ) {
		this.endCond = endCond;
	}

	/**
	 * @return the effect
	 */
	public Effects getEffects( ) {
		return effect;
	}

	/**
	 * @param effect the effect to set
	 */
	public void setEffects( Effects effect ) {
		this.effect = effect;
	}

	/**
	 * @return the postEffect
	 */
	public Effects getPostEffects( ) {
		return postEffect;
	}

	/**
	 * @param postEffect the postEffect to set
	 */
	public void setPostEffects( Effects postEffect ) {
		this.postEffect = postEffect;
	}

	/**
	 * @return the documentation
	 */
	public String getDocumentation( ) {
		return documentation;
	}

	/**
	 * @param documentation the documentation to set
	 */
	public void setDocumentation( String documentation ) {
		this.documentation = documentation;
	}
	
	
	public Object clone() throws CloneNotSupportedException {
		Timer t = (Timer) super.clone();
		t.documentation = (documentation != null ? new String(documentation) : null);
		t.effect = (effect != null ? (Effects) effect.clone() : null);
		t.endCond = (endCond != null ? (Conditions) endCond.clone() : null);
		t.initCond = (initCond !=  null ? (Conditions) initCond.clone() : null);
		t.postEffect = (postEffect != null ? (Effects) postEffect.clone() : null);
		t.seconds = seconds;
		return t;
	}
}
