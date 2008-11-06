package es.eucm.eadventure.adventureeditor.data.chapterdata.effects;

/**
 * An effect that cancels the standard action
 */
public class CancelActionEffect implements Effect {

	public int getType( ) {
		return CANCEL_ACTION;
	}

}
