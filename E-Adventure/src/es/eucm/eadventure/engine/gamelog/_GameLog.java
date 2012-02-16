package es.eucm.eadventure.engine.gamelog;

import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.List;

public interface _GameLog {

	public void lowLevelEvent ( MouseEvent e );
	
	public void lowLevelEvent ( KeyEvent k );
	
	public void lowLevelEvent ( MouseWheelEvent e );
	
	public void lowLevelEvent ( FocusEvent f );

	public void highLevelEvent(String action);
	
	public void highLevelEvent ( String action, String object );
	
	public void highLevelEvent ( String action, String object, String target );

    public long getStartTimeStamp( );

    public List<GameLogEntry> getLog( );
	
    public void scheduledDump();
    
    public void forceDump();
	
}
