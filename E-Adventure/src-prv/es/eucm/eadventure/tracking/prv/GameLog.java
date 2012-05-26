/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.tracking.prv;

import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.eucm.eadventure.tracking.pub._GameLog;

public class GameLog implements _GameLog{

	private List<GameLogEntry> allEntries;
	private List<GameLogEntry> newEntries;
	private long startTimeStamp;
	private long lastLowLevelUpdate;
	private boolean logging;
	private boolean effectVerbosity;
	
	private long threshold;
	 
	public List<GameLogEntry> getAllEntries() {
		return allEntries;
	}

    public List<GameLogEntry> getNewEntries( ) {
        return newEntries;
    }

	public GameLog ( boolean logging, boolean effectVerbosity, long startTimeStamp, long threshold ){
		newEntries = new ArrayList<GameLogEntry>();
		allEntries = new ArrayList<GameLogEntry>();
		this.logging=logging; 
		this.effectVerbosity=effectVerbosity;
		this.startTimeStamp = startTimeStamp;
		addStartTimeEntry();
		this.threshold = threshold; 
	}

	private void addStartTimeEntry(){
	    GameLogEntry newEntry = new GameLogEntry(startTimeStamp, "start", new String[]{"timestampms="+startTimeStamp, "timestamp="+DateFormat.getDateTimeInstance( ).format( new Date(startTimeStamp) )});
        addNewEntry(newEntry);
        addEntry(newEntry);
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
		if (currentTime-lastLowLevelUpdate>=threshold || e.getID( )== MouseEvent.MOUSE_CLICKED || 
		        e.getID( ) == MouseEvent.MOUSE_PRESSED || e.getID( ) == MouseEvent.MOUSE_RELEASED){
		    GameLogEntry newEntry = new GameLogEntry(startTimeStamp,"l", new String[]{"t=m", "i="+idToStr(e.getID()), "x="+e.getX(), "y="+e.getY(), "b="+e.getButton(),
	                "c="+e.getClickCount(), "m="+e.getModifiersEx()});
	        addNewEntry(newEntry);
	        addEntry(newEntry);
			lastLowLevelUpdate = currentTime; 
		}
	}

	public void lowLevelEvent(KeyEvent k) {
	    if(!logging)return;
	    GameLogEntry newEntry = new GameLogEntry(startTimeStamp,"l", new String[]{"t=k", "i="+idToStr(k.getID()),"c="+k.getKeyCode(), "l="+k.getKeyLocation(),
                "m="+k.getModifiersEx()});
        addNewEntry(newEntry);
        addEntry(newEntry);
	}

	public void lowLevelEvent(MouseWheelEvent e) {
	    if(!logging)return;
		long currentTime=System.currentTimeMillis();
		if (currentTime-lastLowLevelUpdate>=threshold){
		    GameLogEntry newEntry = new GameLogEntry(startTimeStamp,"l", new String[]{"t=w", "i="+idToStr(e.getID()),"x="+e.getX(), "y="+e.getY(), "s="+e.getScrollAmount(),
                    "r="+e.getWheelRotation(), "m="+e.getModifiersEx()});
	        addNewEntry(newEntry);
	        addEntry(newEntry);
			lastLowLevelUpdate = currentTime; 
		}
			
	}

	public void lowLevelEvent(FocusEvent f) {
	    if(!logging)return;
	    GameLogEntry newEntry = new GameLogEntry(startTimeStamp,"l", new String[]{"t=f", "i="+idToStr(f.getID())});
        addNewEntry(newEntry);
        addEntry(newEntry);
	}

	public void highLevelEvent(String action ) {
	    if(!logging)return;
	    GameLogEntry newEntry = new GameLogEntry(startTimeStamp,"h", new String[]{"a="+action});
        addNewEntry(newEntry);
        addEntry(newEntry);
	}
	
	public void highLevelEvent(String action, String object) {
	    if(!logging)return;
	    GameLogEntry newEntry = new GameLogEntry(startTimeStamp,"h", new String[]{"a="+action, "o="+object});
        addNewEntry(newEntry);
        addEntry(newEntry);
	}

	public void highLevelEvent(String action, String object, String target) {
	    if(!logging)return;
	    GameLogEntry newEntry = new GameLogEntry(startTimeStamp,"h", new String[]{"a="+action, "o="+object, "t="+target});
	    addNewEntry(newEntry);
	    addEntry(newEntry);
	}
    
	private void addNewEntry(GameLogEntry newEntry){
	    synchronized(newEntries){
	        newEntries.add(newEntry); 
	    }
	}
    private void addEntry(GameLogEntry entry){
        synchronized(allEntries){
            allEntries.add(entry); 
        }
    }

    public void effectEvent( String effectCode, String... arguments ) {
        if(!logging || !effectVerbosity)return;
        String[] args = null;
        if (arguments==null || arguments.length==0){
            args= new String[1];
        } else {
            args=new String[arguments.length+1];
        }
        args[0]="e="+effectCode;
        if (args.length>1){
            for (int i=1; i<args.length; i++){
                args[i]=arguments[i-1];
            }
        }
        GameLogEntry newEntry = new GameLogEntry(startTimeStamp,"h",args);
        addNewEntry(newEntry);
        addEntry(newEntry);
    }
	
	
}
