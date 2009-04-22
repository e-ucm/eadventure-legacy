package es.eucm.eadventure.common.data.chapter.effects;

/**
 * An effect to wait some time without do nothing
 */
public class WaitTimeEffect extends AbstractEffect{

    /**
     * The time to wait without do nothing
     */
    private int time;
    
    /**
     * Constructor
     * @param time
     */
    public WaitTimeEffect(int time){
	super();
	this.time = time;
    }
    
    
    
    /**
     * @return the time
     */
    public int getTime() {
        return time;
    }



    /**
     * @param time the time to set
     */
    public void setTime(int time) {
        this.time = time;
    }


    /**
     * Return the effect type
     */
    public int getType() {
	return WAIT_TIME;
    }
    
    public Object clone() throws CloneNotSupportedException {
	WaitTimeEffect wte = (WaitTimeEffect) super.clone();
	wte.time = time;
	return wte;
    }

}
