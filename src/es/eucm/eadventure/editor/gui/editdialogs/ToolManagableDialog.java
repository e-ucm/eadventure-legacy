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

public abstract class ToolManagableDialog extends JDialog implements Updateable, WindowListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean worksInLocal;
	
	private static final KeyEventDispatcher undoRedoDispatcher = new KeyEventDispatcher(){

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
		this.worksInLocal = worksInLocal;
		addUndoRedoDispatcher();
		this.addWindowListener(this);
	}

	public boolean updateFields() {
		//Do nothing
		return false;
	}
	
	public void setTitle(String title){
	    super.setTitle(title);
	}
	
	public void setVisible(boolean visible){
		Controller.getInstance( ).pushWindow( this );
		if (worksInLocal){
			if (visible){
				Controller.getInstance().pushLocalToolManager();
			} else {
				Controller.getInstance().popLocalToolManager();
			}
		}
		super.setVisible(visible);
	}
	
	public void windowClosing(WindowEvent e) {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(undoRedoDispatcher);
		Controller.getInstance( ).popWindow( );
		setVisible(false);
		dispose();
		//Controller.getInstance().updatePanel();
	}
	
	public void windowOpened(WindowEvent e) {}
	
	public void windowActivated(WindowEvent e) {}

	public void windowClosed(WindowEvent e) {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(undoRedoDispatcher);
		Controller.getInstance( ).popWindow( );
		setVisible(false);
	}

	public void windowDeactivated(WindowEvent e) {}

	public void windowDeiconified(WindowEvent e) {}

	public void windowIconified(WindowEvent e) {}
}
