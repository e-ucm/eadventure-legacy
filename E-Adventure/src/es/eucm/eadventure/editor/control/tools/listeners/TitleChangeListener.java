package es.eucm.eadventure.editor.control.tools.listeners;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import es.eucm.eadventure.common.data.Titled;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeTitleTool;

public class TitleChangeListener implements DocumentListener {

	private Titled titled;
	
	private JTextComponent textComponent;
	
	public TitleChangeListener(JTextComponent textComponent, Titled titled) {
		this.textComponent = textComponent;
		this.titled = titled;
	}
	
	public void changedUpdate(DocumentEvent e) {
		// DO NOTHING
	}

	public void insertUpdate(DocumentEvent e) {
		Tool tool = new ChangeTitleTool(titled, textComponent.getText());
		Controller.getInstance().addTool(tool);
	}

	public void removeUpdate(DocumentEvent e) {
		Tool tool = new ChangeTitleTool(titled, textComponent.getText());
		Controller.getInstance().addTool(tool);
	}

}
