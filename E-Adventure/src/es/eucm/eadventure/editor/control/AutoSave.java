/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.editor.control;

import java.util.TimerTask;

import es.eucm.eadventure.common.gui.TextConstants;

public class AutoSave extends TimerTask {
	
	SaveThread thread;
	
	public AutoSave() {
		super();
	}
	
	@Override
	public void run() {
		thread = new SaveThread();
		thread.start();
	}

	public void stop() {
		if (thread != null) {
			WindowThread windowThread = new WindowThread();
			windowThread.start();
			while(thread.isAlive()) {
			}
			windowThread.close();
		}
	}
	
	private class WindowThread extends Thread {
		
		private boolean finish = false;
		
		public void close() {
			finish = true;
		}
		
		@Override
		public void run() {
			Controller.getInstance().showLoadingScreen(TextConstants.getText("GeneralText.FinishingBackup"));
			while (!finish) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
			}
			Controller.getInstance().hideLoadingScreen();
		}		
	}
	
	private class SaveThread extends Thread {

		public void interrupt() {
			super.interrupt();
			try {
				this.finalize();
			} catch(Exception e) {
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			try {
				System.out.println("Started backup...");
				boolean temp = Controller.getInstance().createBackup(null);
				System.out.println("Finished backup " + (temp ? "OK" : "FAIL"));
			} catch (Exception e) {
				System.out.println("Backup interrupted");
			}
		}
		
	}
	
}
