package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.Dialog;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.gui.Updateable;

public abstract class ToolManagableDialog extends JDialog implements Updateable{

	private static final KeyEventDispatcher undoRedoDispatcher = new KeyEventDispatcher(){

		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			boolean dispatched = false;
			if (e.getID() == KeyEvent.KEY_RELEASED && e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z){
				Controller.getInstance().undoTool();
				e.consume();
				dispatched = true;
			}
			else if (e.getID() == KeyEvent.KEY_RELEASED && e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Y){
				Controller.getInstance().redoTool();
				e.consume();
				dispatched = true;
			}

			return dispatched;
		}
		
	};
	
	private static void addUndoRedoDispatcher(){
		KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(undoRedoDispatcher);
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(undoRedoDispatcher);
	}
	
	public ToolManagableDialog (Window window, String title){
		this (window,title,true);
	}
	
	public ToolManagableDialog (Window window, String title, boolean worksInLocal){
		super (window, title, Dialog.ModalityType.TOOLKIT_MODAL);
		addUndoRedoDispatcher();
		if (worksInLocal){
			this.addWindowListener(new WindowListener(){
				
				public void windowActivated(WindowEvent e) {
				}
	
				public void windowClosed(WindowEvent e) {
				}
	
				public void windowClosing(WindowEvent e) {
					Controller.getInstance().popLocalToolManager();
				}
	
				public void windowDeactivated(WindowEvent e) {
				}
	
				public void windowDeiconified(WindowEvent e) {
				}
	
				public void windowIconified(WindowEvent e) {
				}
	
				public void windowOpened(WindowEvent e) {
					Controller.getInstance().pushLocalToolManager();
				}
				
			});
		}
	}

	@Override
	public boolean updateFields() {
		//Do nothing
		return false;
	}
	
}
