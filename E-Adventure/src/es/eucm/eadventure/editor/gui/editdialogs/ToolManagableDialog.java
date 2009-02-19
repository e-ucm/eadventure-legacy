package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;

import es.eucm.eadventure.editor.control.Controller;

public abstract class ToolManagableDialog extends JDialog{

	public ToolManagableDialog (Window window, String title){
		super (window, title, Dialog.ModalityType.TOOLKIT_MODAL);
		this.addKeyListener(createListener());
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

	protected KeyListener createListener(){
		return 	new KeyListener(){

			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				if (e.isControlDown() && e.getKeyCode()==KeyEvent.VK_Z){
					Controller.getInstance().undoTool();
					
				}
				else if (e.isControlDown() && e.getKeyCode()==KeyEvent.VK_Y){
					Controller.getInstance().redoTool();
				}
			}

			public void keyTyped(KeyEvent e) {
			}
			
		};

	}
	
}
