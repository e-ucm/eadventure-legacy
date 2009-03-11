package es.eucm.eadventure.editor.control;

import java.util.TimerTask;

public class AutoSave extends TimerTask {
	
	public AutoSave() {
		super();
	}
	
	@Override
	public void run() {
		System.out.println("Started backup...");
		boolean temp = Controller.getInstance().createBackup(null);
		System.out.println("Finished backup " + (temp ? "OK" : "FAIL"));
	}

}
