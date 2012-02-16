package es.eucm.eadventure.engine.gamelog;

import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import es.eucm.eadventure.engine.core.control.Game;

public class GameLog implements _GameLog{

	public static final int THRESHOLD=1000;
	
	public static final long DUMP_FREQ=15000;
	
	private List<GameLogEntry> log;
	
	private long lastDump;
	
	private int randomId;
	
	public List<GameLogEntry> getLog() {
		return log;
	}

	public long getStartTimeStamp() {
		return startTimeStamp;
	}

	private long startTimeStamp;
	private long lastLowLevelUpdate;
	private boolean logging;
	
	public GameLog ( boolean logging ){
		log = new ArrayList<GameLogEntry>();
		startTimeStamp = System.currentTimeMillis();
		this.logging=logging; 
		Random r = new Random();
		randomId = r.nextInt( 1000000 );
	}

	private String idToStr(int id){
	    String str="";
	    if (id==MouseEvent.MOUSE_CLICKED)
	        str="c";
	    else if (id==MouseEvent.MOUSE_DRAGGED)
	         str="d";
	    else if (id==MouseEvent.MOUSE_ENTERED)
            str="en";
	    else if (id==MouseEvent.MOUSE_EXITED)
            str="ex";
	    else if (id==MouseEvent.MOUSE_MOVED)
            str="m";
	    else if (id==MouseEvent.MOUSE_RELEASED || id ==KeyEvent.KEY_RELEASED)
            str="r";
	    else if (id==MouseEvent.MOUSE_PRESSED || id==KeyEvent.KEY_PRESSED)
            str="p";
	    else if (id==MouseEvent.MOUSE_WHEEL)
            str="w";
	    else if (id==KeyEvent.KEY_TYPED)
            str="t";
	    else if (id==FocusEvent.FOCUS_GAINED)
            str="g";
        else if (id==FocusEvent.FOCUS_LOST)
            str="l";
	    return str;
	}
	
	public void lowLevelEvent(MouseEvent e) {
	    if(!logging)return;
		long currentTime=System.currentTimeMillis();
		if (currentTime-lastLowLevelUpdate>=THRESHOLD || e.getID( )== MouseEvent.MOUSE_CLICKED || 
		        e.getID( ) == MouseEvent.MOUSE_PRESSED || e.getID( ) == MouseEvent.MOUSE_RELEASED){
			log.add(new GameLogEntry(startTimeStamp,"l", new String[]{"t=m", "i="+idToStr(e.getID()), "x="+e.getX(), "y="+e.getY(), "b="+e.getButton(),
				"c="+e.getClickCount(), "m="+e.getModifiersEx()}));
			lastLowLevelUpdate = currentTime; 
		}
	}

	public void lowLevelEvent(KeyEvent k) {
	    if(!logging)return;
		log.add(new GameLogEntry(startTimeStamp,"l", new String[]{"t=k", "i="+idToStr(k.getID()),"c="+k.getKeyCode(), "l="+k.getKeyLocation(),
				"m="+k.getModifiersEx()}));

	}

	public void lowLevelEvent(MouseWheelEvent e) {
	    if(!logging)return;
		long currentTime=System.currentTimeMillis();
		if (currentTime-lastLowLevelUpdate>=THRESHOLD){
			log.add(new GameLogEntry(startTimeStamp,"l", new String[]{"t=w", "i="+idToStr(e.getID()),"x="+e.getX(), "y="+e.getY(), "s="+e.getScrollAmount(),
					"r="+e.getWheelRotation(), "m="+e.getModifiersEx()}));
			lastLowLevelUpdate = currentTime; 
		}
			
	}

	public void lowLevelEvent(FocusEvent f) {
	    if(!logging)return;
	    log.add(new GameLogEntry(startTimeStamp,"l", new String[]{"t=f", "i="+idToStr(f.getID())}));
	}

	public void highLevelEvent(String action ) {
	    if(!logging)return;
		log.add(new GameLogEntry(startTimeStamp,"h", new String[]{"a="+action}));		
	}
	
	public void highLevelEvent(String action, String object) {
	    if(!logging)return;
		log.add(new GameLogEntry(startTimeStamp,"h", new String[]{"a="+action, "o="+object}));		
	}

	public void highLevelEvent(String action, String object, String target) {
	    if(!logging)return;
		log.add(new GameLogEntry(startTimeStamp,"h", new String[]{"a="+action, "o="+object, "t="+target}));
	}

    public void scheduledDump( ) {
        if(!logging)return;
        
        if ( System.currentTimeMillis( )-lastDump>DUMP_FREQ ){
            this.lastDump = System.currentTimeMillis( );
            forceDump();
        }
        
    }

    public void forceDump( ) {
        if(!logging)return;
        TimerTask task=new TimerTask(){
            @Override
            public void run( ) {
                GameLogWriter.writeToFile( GameLog.this, getFileName() );                
            }
        };
        Timer timer = new Timer();
        timer.schedule( task, 1 );
    }
	
	private String getFileName(){
	    return "gamelog-"+Game.getInstance( ).getAdventureName( )+"-"+randomId+".xml";
	}
}
