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
