package es.eucm.eadventure.common.data.chapter.effects;

/**
 * An effect that cancels the standard action
 */
public class CancelActionEffect extends AbstractEffect {

    	public CancelActionEffect(){
    	    super();
    	}
    
	public int getType( ) {
		return CANCEL_ACTION;
	}

	public Object clone() throws CloneNotSupportedException {
		CancelActionEffect cae = (CancelActionEffect) super.clone();
		return cae;
	}
}
