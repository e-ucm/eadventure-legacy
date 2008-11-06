package es.eucm.eadventure.engine.core.data.gamedata.effects;

import es.eucm.eadventure.engine.core.control.Game;

/**
 * An effect that raises a "bookscene".
 */
public class TriggerBookEffect implements Effect {

    /**
     * Id of the book to be shown
     */
    private String targetBookId;

    /**
     * Creates a new TriggerBookEffect
     * @param targetBookId the id of the book to be shown
     */
    public TriggerBookEffect( String targetBookId ) {
        this.targetBookId = targetBookId;
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        Game.getInstance( ).setBook( targetBookId );
        Game.getInstance( ).setState( Game.STATE_BOOK );
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#isInstantaneous()
     */
    public boolean isInstantaneous( ) {
        return false;
    }
    
    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.data.gamedata.effects.Effect#isStillRunning()
     */
    public boolean isStillRunning( ) {
        return false;
    }

}
