package es.eucm.eadventure.engine.core.control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.eucm.eadventure.engine.core.gui.DebugLogFrame;

public class DebugLog {

	private static final String DATE_FORMAT_NOW = "HH:mm:ss";

	public static final int GENERAL = 0;

	public static final int USER = 1;
	
	public static final int PLAYER = 2;
		
	private static DebugLog instance;
	
	private List<String> text;
	
	private DebugLogFrame debugFrameLog;
	
	public DebugLog() {
		text = new ArrayList<String>();
	}
	
	public static DebugLog getInstance() {
		if (instance == null)
			instance = new DebugLog();
		return instance;
	}
	
	public static void player(String text) {
		getInstance().logEntry(DebugLog.PLAYER, text);
	}
	
	public static void general(String text) {
		getInstance().logEntry(DebugLog.GENERAL, text);
	}
	
	public static void user(String text) {
		getInstance().logEntry(DebugLog.USER, text);
	}
	
	private void logEntry(int type, String line) {
		text.add(line);
		if (debugFrameLog != null)
			debugFrameLog.addLine(type, now(), line);
	}

    public String now() {
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	    return sdf.format(Calendar.getInstance().getTime());
	}
    
	public void setDebugFrameLog(DebugLogFrame debugFrameLog) {
		this.debugFrameLog = debugFrameLog;
	}

	public void clear() {
		text.clear();
		debugFrameLog = null;
	}
}
