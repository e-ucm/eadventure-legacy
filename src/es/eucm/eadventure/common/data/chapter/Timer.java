package es.eucm.eadventure.common.data.chapter;

import java.awt.Color;

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
	
	private Boolean usesEndCondition;
	
	private Boolean runsInLoop;
	
	private Boolean multipleStarts;
		
	private Boolean showTime;
	
	private String displayName;
	
	private Boolean countDown;
	
	private Boolean showWhenStopped;
	
	private Color fontColor = Color.BLACK;
	
	private Color borderColor = Color.WHITE;
	
	public Timer(long time, Conditions init, Conditions end, Effects effect, Effects postEffect){
		this.seconds = time;
		this.initCond = init;
		this.endCond = end;
		this.effect = effect;
		this.postEffect = postEffect;
		usesEndCondition = true;
		runsInLoop = true;
		multipleStarts = true;
		
		showTime = false;
		displayName = "timer";
		countDown = true;
		showWhenStopped = false;
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
	
	
	/**
	 * @return the usesEndCondition
	 */
	public Boolean isUsesEndCondition() {
		return usesEndCondition;
	}

	/**
	 * @param usesEndCondition the usesEndCondition to set
	 */
	public void setUsesEndCondition(Boolean usesEndCondition) {
		this.usesEndCondition = usesEndCondition;
	}
	
	/**
	 * @return the runsInLoop
	 */
	public Boolean isRunsInLoop() {
		return runsInLoop;
	}

	/**
	 * @param runsInLoop the runsInLoop to set
	 */
	public void setRunsInLoop(Boolean runsInLoop) {
		this.runsInLoop = runsInLoop;
	}

	/**
	 * @return the multipleStarts
	 */
	public Boolean isMultipleStarts() {
		return multipleStarts;
	}

	/**
	 * @param multipleStarts the multipleStarts to set
	 */
	public void setMultipleStarts(Boolean multipleStarts) {
		this.multipleStarts = multipleStarts;
	}


	/**
	 * @return the countDown
	 */
	public Boolean isCountDown() {
		return countDown;
	}

	/**
	 * @param countDown the countDown to set
	 */
	public void setCountDown(Boolean countDown) {
		this.countDown = countDown;
	}

	/**
	 * @return the showWhenStopped
	 */
	public Boolean isShowWhenStopped() {
		return showWhenStopped;
	}

	/**
	 * @param showWhenStopped the showWhenStopped to set
	 */
	public void setShowWhenStopped(Boolean showWhenStopped) {
		this.showWhenStopped = showWhenStopped;
	}

	/**
	 * @return the fontColor
	 */
	public Color getFontColor() {
		return fontColor;
	}

	/**
	 * @param fontColor the fontColor to set
	 */
	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}

	/**
	 * @return the borderColor
	 */
	public Color getBorderColor() {
		return borderColor;
	}

	/**
	 * @param borderColor the borderColor to set
	 */
	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Boolean isShowTime() {
		return showTime;
	}
	
	public void setShowTime(Boolean showTime) {
		this.showTime = showTime;
	}
	
	public Object clone() throws CloneNotSupportedException {
		Timer t = (Timer) super.clone();
		t.documentation = (documentation != null ? new String(documentation) : null);
		t.effect = (effect != null ? (Effects) effect.clone() : null);
		t.endCond = (endCond != null ? (Conditions) endCond.clone() : null);
		t.initCond = (initCond !=  null ? (Conditions) initCond.clone() : null);
		t.postEffect = (postEffect != null ? (Effects) postEffect.clone() : null);
		t.seconds = seconds;
		t.runsInLoop = runsInLoop;
		t.multipleStarts = multipleStarts;
		t.usesEndCondition = usesEndCondition;
		return t;
	}

}
